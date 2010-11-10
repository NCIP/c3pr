package edu.duke.cabig.c3pr.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;


/**
 * Utility class to import XML extracts of study <p/> Created by IntelliJ IDEA. User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com Date: Jun 4, 2007 Time: 1:18:10 PM To change this
 *         template use File | Settings | File Templates.
 */

public class StudyXMLImporterServiceImpl implements
                edu.duke.cabig.c3pr.service.StudyXMLImporterService {

    private static final String ERROR_UNMARSHALLING_CODE = "C3PR.EXCEPTION.REGISTRATION.IMPORT.ERROR_UNMARSHALLING";

	public static final String NS = edu.duke.cabig.c3pr.utils.XMLUtils.CCTS_DOMAIN_NS;

	private StudyDao studyDao;
    
    private StudyRepository studyRepository;

    private XmlMarshaller marshaller;
    
    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;

    private Logger log = Logger.getLogger(StudyXMLImporterServiceImpl.class.getName());
    
	
	@Transactional
	public List<Study> importStudies(org.w3c.dom.Document doc, Errors errors)
			throws C3PRCodedException {
		DOMBuilder builder = new DOMBuilder();
        org.jdom.Document jdomDoc = builder.build(doc);
		return importStudies(jdomDoc, errors);
	}

	
	@Transactional
	public List<Study> importStudies(Document document, Errors errors)
			throws C3PRCodedException {
		List<Study> studyList = new ArrayList<Study>();
		try {
			Element rootElement = document.getRootElement();
			List<Element> studies = collectStudies(rootElement);

			for (int i = 0; i < studies.size(); i++) {
				Element studyNode = studies.get(i);
				Study study = null;
				try {
					study = (Study) marshaller.fromXML(new StringReader(
							new XMLOutputter().outputString(studyNode)));
					studyRepository.validate(study);
					// do any custom processing after validation
					study = processStudy(study);
					log.debug("Saving study with grid ID" + study.getGridId());
					studyRepository.buildAndSave(study);
					// once saved retrieve persisted study
					studyList.add(studyDao.getById(study.getId()));
				} catch (Exception e) {
					// ignore any other problem and continue to import
					final String errMsg = "Error while importing"
							+ (study != null ? " study with ID of '"
									+ study.getPrimaryIdentifier() + "'" : "")
							+ ": " + e.getMessage();
					log.error(errMsg, e);
					errors.reject(errMsg, errMsg);
				}
			}
		} catch (MalformedStudyImport e) {
			final String errMsg = "Error while importing: Missing root element tag 'studies' or 'study'. "
					+ "Make sure the top level xml tag is 'studies' or 'study'";
			errors.reject(errMsg, errMsg);
			return studyList;
		} catch (Exception e) {
			throw this.exceptionHelper
					.getException(
							getCode(ERROR_UNMARSHALLING_CODE),
							e);
		}
		return studyList;

	}

	/**
     * Will parse an xml stream and create 1..many studies XML should have one or many study
     * elements <study> //study serialization </study> <p/> Container to the <study/> element is not
     * important
     * 
     * @param xmlStream
     * @return
     * @throws Exception
     */
	@Transactional
	public List<Study> importStudies(InputStream xmlStream, Errors errors)
			throws C3PRCodedException {
		try {
			return importStudies(new SAXBuilder().build(xmlStream), errors);
		} catch (JDOMException e) {
			throw this.exceptionHelper
					.getException(
							getCode(ERROR_UNMARSHALLING_CODE),
							e);
		} catch (IOException e) {
			throw this.exceptionHelper
					.getException(
							getCode(ERROR_UNMARSHALLING_CODE),
							e);
		}

	}
    
    /**
     * @param rootElement
     * @return
     */
	private List<Element> collectStudies(Element rootElement) {
		if (rootElement.getName().equalsIgnoreCase("studies")) {
			return rootElement
					.getChildren(
							"study",
							Namespace
									.getNamespace(NS));
		} else if (rootElement.getName().equalsIgnoreCase("study")) {
			return Arrays.asList( rootElement );
		} else {
			throw new MalformedStudyImport();
		}
	}

	public Study processStudy(Study study){
    	// updating the study status before importing.
    	if ((study.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.OPEN)) {
			study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
			// CPR-2213: According to conversation with Kruttik, studies can now be imported in Open status if
			// they pass the biz checks.
			try {
				study.open();
			} catch (RuntimeException e) {
				log.error("The study "+study+" cannot be imported in open status", e);
				throw new IllegalStateException("the study cannot be opened. "+e.getMessage());
			}
		}
    	return study;
    }

    // setters for spring

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public XmlMarshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(XmlMarshaller marshaller) {
        this.marshaller = marshaller;
    }

	public StudyRepository getStudyRepository() {
		return studyRepository;
	}

	public void setStudyRepository(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}
	
	private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }
	
	private static final class MalformedStudyImport extends RuntimeException {

		public MalformedStudyImport() {
			super();
			// TODO Auto-generated constructor stub
		}

		public MalformedStudyImport(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public MalformedStudyImport(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public MalformedStudyImport(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}
		
	}
	
}

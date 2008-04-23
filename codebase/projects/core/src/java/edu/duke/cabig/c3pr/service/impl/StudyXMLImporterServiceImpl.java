package edu.duke.cabig.c3pr.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.acegisecurity.AccessDeniedException;
import org.apache.log4j.Logger;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Utility class to import XML extracts of study <p/> Created by IntelliJ IDEA. User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com Date: Jun 4, 2007 Time: 1:18:10 PM To change this
 *         template use File | Settings | File Templates.
 */

public class StudyXMLImporterServiceImpl implements
                edu.duke.cabig.c3pr.service.StudyXMLImporterService {

    private StudyDao studyDao;
    
    private StudyRepository studyRepository;

    private XmlMarshaller marshaller;
    
    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;

    private Logger log = Logger.getLogger(StudyXMLImporterServiceImpl.class.getName());

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
    public List<Study> importStudies(InputStream xmlStream,File importXMLResult) throws C3PRCodedException {
        List<Study> studyList = null;
        org.jdom.Document document = null;
        try {
            studyList = new ArrayList<Study>();
          
            document = new SAXBuilder().build(xmlStream);
            
            List<Element> studies = document.getRootElement().getChildren("study",Namespace.getNamespace("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain"));

            for (int i = 0; i < studies.size(); i++) {
            	Element studyNode = studies.get(i);
            	
                    Study study = null;
                    try {
                        study = (Study) marshaller.fromXML(new StringReader(new XMLOutputter().outputString(studyNode)));
                        studyRepository.validate(study);

                        log.debug("Saving study with grid ID" + study.getGridId());

                        studyRepository.buildAndSave(study);
                        // once saved retrieve persisted study
                        studyList.add(studyDao.getById(study.getId()));
                        studyNode.addContent(new Comment("Successfull Import"));
                    }
                  
                    catch (Exception e) {
                        // ignore any other problem and continue to import
                    	e.printStackTrace();                        
                        log.error(e.getMessage());
                        studyNode.addContent(new Comment("Error while importing: " + e.getMessage()));
                        
                    }
            }
            new XMLOutputter(Format.getPrettyFormat()).output(document, new FileWriter(
                    importXMLResult));
        }
        catch (Exception e) {
        	 throw this.exceptionHelper.getException(
                     getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.ERROR_UNMARSHALLING"), e);
        }
        return studyList;
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
}

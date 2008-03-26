package edu.duke.cabig.c3pr.service.impl;

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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
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
    public List<Study> importStudies(InputStream xmlStream) throws C3PRBaseRuntimeException {
        List<Study> studyList = null;
        try {
            studyList = new ArrayList<Study>();
            Document studyDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                            xmlStream);
            NodeList studies = studyDoc.getElementsByTagName("study");

            for (int i = 0; i < studies.getLength(); i++) {
                Node studyNode = studies.item(i);
                if (studyNode.getNodeType() == Node.ELEMENT_NODE) {
                    StringWriter sw = new StringWriter();
                    StreamResult sr = new StreamResult(sw);
                    TransformerFactory.newInstance().newTransformer().transform(
                                    new DOMSource(studyNode), sr);

                    Study study = null;
                    try {
                        study = (Study) marshaller.fromXML(new StringReader(sr.getWriter()
                                        .toString()));
                    }
                    catch (XMLUtilityException e) {
                        // ignore but log it. Cannot handle this in the UI
                        log.error("Error marshalling Study during import");
                    }

                    try {
                        studyRepository.validate(study);

                        log.debug("Saving study with grid ID" + study.getGridId());

                        studyRepository.buildAndSave(study);
                        // once saved retreive persisted study
                        studyList.add(studyDao.getByGridId(study.getGridId()));
                    }
                    catch (AccessDeniedException e) {
                        // if user cannot save a study then no point continuing
                        throw e;
                    }
                    catch (Exception e) {
                        // ignore any other problem and continue to import
                        study.setImportErrorString(e.getMessage());
                        studyList.add(study);
                        log.error(e.getMessage());
                    }
                }
            }
        }
        catch (Exception e) {
            throw new C3PRBaseRuntimeException("Could not import study", e);
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
}

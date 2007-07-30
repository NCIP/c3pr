package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Utility class to import XML extracts of study
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * @author kherm manav.kher@semanticbits.com
 * Date: Jun 4, 2007
 * Time: 1:18:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyXMLImporterService implements edu.duke.cabig.c3pr.service.StudyXMLImporterService {

    private StudyDao studyDao;
    private XmlMarshaller marshaller;
    private  Logger log = Logger.getLogger(StudyXMLImporterService.class.getName());

    /**
     * Will parse an xml stream and create 1..many studies
     * XML should have one or many study elements
     * <study>
     * //study serialization
     * </study>
     *
     * Container to the <study/> element is not importan
     * @param xmlStream
     * @return
     * @throws Exception
     */
    public List<Study> importStudies(InputStream xmlStream) throws Exception{
        List<Study> studyList = new ArrayList<Study>();
        Document studyDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlStream);
        NodeList studies = studyDoc.getElementsByTagName("study");

        for(int i=0;i<studies.getLength(); i++)
        {
            Node studyNode = studies.item(i);
            if(studyNode.getNodeType()  == Node.ELEMENT_NODE){
                StringWriter sw = new StringWriter();
                StreamResult sr = new StreamResult( sw );
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(studyNode),sr);
                try {
                        Study study = (Study)marshaller.fromXML(new StringReader(sr.getWriter().toString()));
                        this.validate(study);
                        //on validation save the study
                        studyDao.merge(study);
                        //once saved retreive persisted study
                        studyList.add(studyDao.getByGridId(study.getGridId()));
                    } catch (Exception e) {
                          log.debug(e.getMessage());
                    }
            }

        }
        return studyList;
    }

    /**
     * Validate a study against a set of validation rules
     * @param study
     * @throws StudyValidationException
     */
    public void validate(Study study) throws StudyValidationException {
        //make sure grid id exists
        if (study.getGridId()==null){
            throw new StudyValidationException("No valid GridID found.");
        }
        else if(studyDao.getByGridId(study.getGridId())!=null){
            throw new StudyValidationException("Study exists");
        }
    }

    //setters for spring

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }


    public XmlMarshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(XmlMarshaller marshaller) {
        this.marshaller = marshaller;
    }



}

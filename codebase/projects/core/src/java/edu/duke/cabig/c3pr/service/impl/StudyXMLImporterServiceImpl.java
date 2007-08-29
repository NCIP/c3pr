package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.acegisecurity.AccessDeniedException;

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

public class StudyXMLImporterServiceImpl implements edu.duke.cabig.c3pr.service.StudyXMLImporterService {

    private StudyDao studyDao;
    private HealthcareSiteDao healthcareSiteDao;
    private StudyValidator studyValidator;

    private XmlMarshaller marshaller;
    private  Logger log = Logger.getLogger(StudyXMLImporterServiceImpl.class.getName());

    /**
     * Will parse an xml stream and create 1..many studies
     * XML should have one or many study elements
     * <study>
     * //study serialization
     * </study>
     *
     * Container to the <study/> element is not important
     * @param xmlStream
     * @return
     * @throws Exception
     */
    public List<Study> importStudies(InputStream xmlStream) throws C3PRBaseRuntimeException {
        List<Study> studyList = null;
        try {
            studyList = new ArrayList<Study>();
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

                        log.debug("Saving study with grid ID" + study.getGridId());

                        for(StudyOrganization organization: study.getStudyOrganizations()){
                            HealthcareSite loadedSite = healthcareSiteDao.getByNciInstituteCode(organization.getHealthcareSite().getNciInstituteCode());
                            organization.setHealthcareSite(loadedSite);
                        }

                        for(OrganizationAssignedIdentifier identifier: study.getOrganizationAssignedIdentifiers()){
                            HealthcareSite loadedSite = healthcareSiteDao.getByNciInstituteCode(identifier.getHealthcareSite().getNciInstituteCode());
                            identifier.setHealthcareSite(loadedSite);
                        }

                        studyDao.save(study);
                        log.debug("Study saved with grid ID" + study.getGridId());
                        //once saved retreive persisted study
                        studyList.add(studyDao.getByGridId(study.getGridId()));
                    } catch(AccessDeniedException e){
                        //if user cannot save a study then no point continuing
                        throw e;
                    } catch (Exception e) {
                        //ignore any other problem and continue to import
                        log.debug(e.getMessage());
                    }
                }

            }
        } catch (Exception e) {
            throw new C3PRBaseRuntimeException("Could not import study",e);
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
        if (study.getGridId()!=null){
            if(studyDao.getByGridId(study.getGridId())!=null){
                throw new StudyValidationException("Study exists");
            }
        }

        for(StudyOrganization organization: study.getStudyOrganizations()){
            if(healthcareSiteDao.getByNciInstituteCode(organization.getHealthcareSite().getNciInstituteCode()) == null){
                throw new StudyValidationException("HealthcareSite does not exit for Study");
            }
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


    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }
}

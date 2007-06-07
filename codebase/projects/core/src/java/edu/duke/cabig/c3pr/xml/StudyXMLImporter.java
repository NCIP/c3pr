package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.exception.StudyValidationException;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.dao.StudyDao;
import gov.nih.nci.common.util.XMLUtility;


import java.io.File;
import java.io.Reader;

/**
 * Utility class to import XML extracts of study
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 4, 2007
 * Time: 1:18:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyXMLImporter {

    private StudyDao studyDao;
    private StudyService studyService;


    /**
     * Will validate and save a study 
     * @param study
     * @throws Exception
     */
    public void importStudy(Study study) throws Exception {
        validate(study);
        studyService.save(study);
    }


    public void importStudy(Reader studyXMLReader) throws Exception  {
        Study study = (Study)XMLUtility.fromXML(studyXMLReader);
        this.validate(study);
        studyService.save(study);
    }


    /**
     * Validate a study against a set of validation rules
     * @param study
     * @throws StudyValidationException
     */
    public void validate(Study study) throws StudyValidationException {

        if(studyDao.getByGridId(study)!=null){
            throw new StudyValidationException("Study exists");
        }
    }


    //setters for spring

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public void setStudyService(StudyService studyService) {
        this.studyService = studyService;
    }
}

package edu.duke.cabig.c3pr.service.impl;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;

import java.util.List;
import java.util.Date;

/**
 * Services for Study related domain object
 * 
 * @author Priyatam
 * @see edu.duke.cabig.c3pr.service.StudyService
 */
public class StudyServiceImpl extends CCTSWorkflowServiceImpl implements StudyService {

    private Logger log = Logger.getLogger(StudyService.class);
    private StudyDao studyDao;

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public List<Study> searchByExample(Study study, int maxResults) {
        return studyDao.searchByExample(study, maxResults);
    }

    public int countAcrrualsByDate(Study study, Date startDate, Date endDate) {
        return studyDao.countAcrrualsByDate(study, startDate, endDate);
    }
}

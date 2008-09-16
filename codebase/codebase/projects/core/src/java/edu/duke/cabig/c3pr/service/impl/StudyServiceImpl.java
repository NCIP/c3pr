package edu.duke.cabig.c3pr.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudyService;

/**
 * Services for Study related domain object
 * 
 * @author Priyatam
 * @see edu.duke.cabig.c3pr.service.StudyService
 */
public class StudyServiceImpl extends WorkflowServiceImpl implements StudyService {

    private Logger log = Logger.getLogger(StudyService.class);
    private StudyDao studyDao;
    private StudySiteDao studySiteDao;

    public void setStudySiteDao(StudySiteDao studySiteDao) {
        this.studySiteDao = studySiteDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public List<Study> searchByExample(Study study, int maxResults) {
        return studyDao.searchByExample(study, maxResults);
    }

    public int countAcrrualsByDate(Study study, Date startDate, Date endDate) {
        return studyDao.countAcrrualsByDate(study, startDate, endDate);
    }
    
    public List<Study> searchByExample(Study study, boolean isWildCard, int maxResults, String order, String orderBy) {
        return studyDao.searchByExample(study, isWildCard, maxResults, order, orderBy);
    }

    public void activateStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode) throws C3PRCodedException{
        StudySite studySite=getUniqueStudy(studyIdentifiers).getStudySiteFromNCICode(nciInstituteCode);
        studySite.setWorkFlowSiteStudyStatus(SiteStudyStatus.ACTIVE);
        studySiteDao.save(studySite);
        
    }

    public void amendStudy(List<Identifier> studyIdentifiers, Study amendedStudyDetails) throws C3PRCodedException{
        //TODO
    }

    public void approveStudySiteForActivation(List<Identifier> studyIdentifiers,
                    String nciInstituteCode) throws C3PRCodedException{
        StudySite studySite=getUniqueStudy(studyIdentifiers).getStudySiteFromNCICode(nciInstituteCode);
        studySite.setWorkFlowSiteStudyStatus(SiteStudyStatus.APPROVED_FOR_ACTIVTION);
        studySiteDao.save(studySite);
    }

    public void closeStudy(List<Identifier> studyIdentifiers) throws C3PRCodedException{
        Study study=getUniqueStudy(studyIdentifiers);
        study.setStatuses(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
        studyDao.save(study);
    }
    
    public void updateStudyStatus(List<Identifier> studyIdentifiers, CoordinatingCenterStudyStatus status) throws C3PRCodedException{
        Study study=getUniqueStudy(studyIdentifiers);
        study.setStatuses(status);
        studyDao.save(study);
    }

    public void closeStudySite(List<Identifier> studyIdentifiers, String nciInstituteCode) throws C3PRCodedException{
        StudySite studySite=getUniqueStudy(studyIdentifiers).getStudySiteFromNCICode(nciInstituteCode);
        studySite.setWorkFlowSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
        studySiteDao.save(studySite);
    }

    public void closeStudySites(List<Identifier> studyIdentifiers) throws C3PRCodedException{
        Study study=getUniqueStudy(studyIdentifiers);
        for (StudySite studySite1: study.getStudySites()){
            studySite1.setWorkFlowSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
            studySiteDao.save(studySite1);
        }
    }

    public void createStudy(Study study) {
        if(study.isCreatable()){
            studyDao.save(study);
        }else{
            throw new RuntimeException("cannot create incomplete study");
        }
        
    }

    public void openStudy(List<Identifier> studyIdentifiers) throws C3PRCodedException{
        Study study=getUniqueStudy(studyIdentifiers);
        study.setStatuses(CoordinatingCenterStudyStatus.OPEN);
        studyDao.save(study);
    }

    public void updateStudySiteProtocolVersion(List<Identifier> studyIdentifiers,
                    String nciInstituteCode, String version) {
        // TODO Auto-generated method stub
        
    }
    
    private Study getUniqueStudy(List<Identifier> studyIdentifiers) throws C3PRCodedException{
        List<Study> studies=studyDao.getByIdentifiers(studyIdentifiers);
        if(studies.size()==0){
            throw new RuntimeException("study not found");
        }else if(studies.size()>1){
            throw new RuntimeException("more than one study with the same identifier found");
        }
        return studies.get(0);
    }
    
}

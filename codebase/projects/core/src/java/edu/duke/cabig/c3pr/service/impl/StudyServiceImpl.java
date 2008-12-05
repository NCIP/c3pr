package edu.duke.cabig.c3pr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Error;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class StudyServiceImpl extends WorkflowServiceImpl implements StudyService{
    
    public String getLocalNCIInstituteCode(){
        return this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
    }

    @Override
    public ServiceName getMultisiteServiceName() {
        return ServiceName.STUDY;
    }
    
    public boolean isStudyOrganizationLocal(String nciInstituteCode){
        return getLocalNCIInstituteCode().equalsIgnoreCase(nciInstituteCode);
    }
    
    public void createStudyAtAffiliates(List<Identifier> studyIdentifiers) {
        Study study=getUniqueStudy(studyIdentifiers);
        for(StudySite studySite: study.getStudySites()){
            createStudyAtAffiliate(studyIdentifiers, studySite.getHealthcareSite().getNciInstituteCode());
        }
    }
    
    public void createStudyAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode) {
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        if(study.canMultisiteBroadcast() && study.isCoOrdinatingCenter(getLocalNCIInstituteCode())  && canMultisiteBroadcast(studySite)){
            List<AbstractMutableDomainObject> domainObjects= new ArrayList<AbstractMutableDomainObject>();
            domainObjects.add(study);
            handleAffiliateSitesBroadcast(study, APIName.CREATE_STUDY, domainObjects);
            updateCoordinatingCenterStatusForStudySite(studySite, APIName.CREATE_STUDY, CoordinatingCenterStudyStatus.READY_TO_OPEN);
        }
    }
    
    public void openStudyAtAffiliates(List<Identifier> studyIdentifiers) {
        Study study=getUniqueStudy(studyIdentifiers);
        for(StudySite studySite: study.getStudySites()){
            openStudyAtAffiliate(studyIdentifiers, studySite.getHealthcareSite().getNciInstituteCode());
        }
    }

    public void openStudyAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode) {
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        if(study.canMultisiteBroadcast() && study.isCoOrdinatingCenter(getLocalNCIInstituteCode()) && canMultisiteBroadcast(studySite)){
            handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.OPEN_STUDY, studyIdentifiers);
            EndPoint endPoint=studySite.getLastAttemptedEndpoint();
            if(endPoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_FAILED ){
                Error error=endPoint.getLastAttemptError();
                if(error!=null && error.getErrorCode().equals("337")){
                    createStudyAtAffiliate(studyIdentifiers, nciInstituteCode);
                    endPoint=studySite.getLastAttemptedEndpoint();
                    if(endPoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED ){
                        handleAffiliateSiteBroadcast(nciInstituteCode, study, APIName.OPEN_STUDY, studyIdentifiers);
                    }
                }
            }
            updateCoordinatingCenterStatusForStudySite(studySite, APIName.OPEN_STUDY, CoordinatingCenterStudyStatus.OPEN);
        }
    }

    public void amendStudyAtAffiliates(List<Identifier> studyIdentifiers, Study amendedStudyDetails) {
        // TODO Auto-generated method stub
        
    }

    public void closeStudyAtAffiliate(List<Identifier> studyIdentifiers, String nciInstituteCode) {
        Study study=getUniqueStudy(studyIdentifiers);
        StudySite studySite = study.getStudySite(
                        nciInstituteCode);
        if(study.canMultisiteBroadcast() && study.isCoOrdinatingCenter(getLocalNCIInstituteCode())  && canMultisiteBroadcast(studySite)){
            handleAffiliateSitesBroadcast(study, APIName.CLOSE_STUDY, studyIdentifiers);
            updateCoordinatingCenterStatusForStudySite(studySite, APIName.CLOSE_STUDY, CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
        }
    }

    public void closeStudyAtAffiliates(List<Identifier> studyIdentifiers) {
        Study study=getUniqueStudy(studyIdentifiers);
        for(StudySite studySite: study.getStudySites()){
            closeStudyAtAffiliate(studyIdentifiers, studySite.getHealthcareSite().getNciInstituteCode());
        }
    }

    public void updateAffliateProtocolVersion(List<Identifier> studyIdentifiers,
                    String nciInstituteCode, String version) {
        // TODO Auto-generated method stub
        
    }

    public void updateStudyStatusAtAffiliates(List<Identifier> studyIdentifiers,
                    CoordinatingCenterStudyStatus status) {
        // TODO Auto-generated method stub
        
    }

    public void updateCoordinatingCenterStatusForStudySite(StudySite studySite, APIName apiName, CoordinatingCenterStudyStatus status){
        if(!studySite.getHostedMode()){
            EndPoint endPoint= studySite.getEndPoint(getMultisiteServiceName(), apiName);
            if(endPoint!=null && endPoint.getStatus()==WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                studySite.setCoordinatingCenterStudyStatus(status);
                studyOrganizationDao.save(studySite);
            }
        }
    }
    
    public Study getUniqueStudy(List<Identifier> studyIdentifiers) {
        StudyDao studyDao=(StudyDao)dao;
        List<Study> studies = studyDao.getByIdentifiers(studyIdentifiers);
        if (studies.size() == 0) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.NOT_FOUND_GIVEN_IDENTIFIERS.CODE"));
        }
        else if (studies.size() > 1) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.MULTIPLE_STUDIES_FOUND.CODE"));
        }
        return studies.get(0);
    }
}
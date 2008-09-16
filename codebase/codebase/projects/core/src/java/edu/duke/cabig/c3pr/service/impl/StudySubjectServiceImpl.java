package edu.duke.cabig.c3pr.service.impl;

import java.io.StringReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * @author Kruttik
 * @version 1.0
 * 
 */
public class StudySubjectServiceImpl extends WorkflowServiceImpl implements StudySubjectService {

    private static final Logger logger = Logger.getLogger(StudySubjectServiceImpl.class);
    private StudySubjectDao studySubjectDao;
    
    private boolean hostedMode = true;

    private StudyTargetAccrualNotificationEmail notificationEmailer;
    
    private StudySubjectRepository studySubjectRepository;
    
    private StudySubjectFactory studySubjectFactory;
    
    public void setStudySubjectFactory(StudySubjectFactory studySubjectFactory) {
        this.studySubjectFactory = studySubjectFactory;
    }

    public void setNotificationEmailer(
                    StudyTargetAccrualNotificationEmail studyTargetAccrualNotificationEmail) {
        this.notificationEmailer = studyTargetAccrualNotificationEmail;
    }

    public StudySubject register(StudySubject studySubject){
        //save the registration record in the first step, so that if there is an exception
        //the user can resume the incomplete registration.
        studySubject=studySubjectRepository.save(studySubject);
        if(!studySubject.isDataEntryComplete()) return studySubject;
        if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED) {
            studySubject=manageRegistration(studySubject);
        }
        return studySubject;
    }
    
    @Transactional
    private StudySubject manageRegistration(StudySubject studySubject){
        ScheduledEpoch scheduledEpoch = studySubject.getScheduledEpoch();
        if (requiresExternalApprovalForRegistration(studySubject)) {
            try {
                sendRegistrationRequest(studySubject);
                scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
            }
            catch (RuntimeException e) {
                // TODO throw a C3PRCodedUncheckedException
                throw e;
            }
        }else{
            try {
                studySubject=studySubjectRepository.doLocalRegistration(studySubject);
            }
            catch (C3PRCodedException e) {
             // TODO throw a C3PRCodedUncheckedException
                throw new RuntimeException(e);
            }
        }
        return updateRegistrationStatus(studySubject);
    }
    
    private StudySubject updateRegistrationStatus(StudySubject studySubject){
        if (studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.REGISTERED) {
            return studySubject;
        }
        if(!studySubject.getStudySite().getStudy().getStandaloneIndicator() && studySubject.isDataEntryComplete()){
        	studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.READY_FOR_REGISTRATION);
        }else{
	        ScheduledEpoch scheduledEpoch = studySubject.getScheduledEpoch();
	        if (studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE) {
	            if (scheduledEpoch.getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.DISAPPROVED) {
	                studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.DISAPPROVED);
	            }
	            else if (scheduledEpoch.getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING) {
	                studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
	            }
	            else if (scheduledEpoch.getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.APPROVED) {
	            	if (scheduledEpoch.isReserving()) {
	                    studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
	                }
	                else if (scheduledEpoch.getEpoch().isEnrolling()) {
	                    studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED);
	                    for(StudySubject childStudySubject: studySubject.getChildStudySubjects()){
	                    	this.register(childStudySubject);
	                    	childStudySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED);
	                    }
	                    try {
	                        this.notificationEmailer.sendEmail(studySubject);
	                        broadcastMessage(studySubject);
	                    }
	                    catch (C3PRCodedException e) {
	                        logger.error(e.getMessage());
	                        studySubject
	                                        .setCctsWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_SEND_FAILED);
	                    }
	                    catch (Exception e) {
	                     // TODO throw a C3PRCodedUncheckedException
	                        throw new RuntimeException(e);
	                    }
	                    try{
	                        if(studySubject.requiresAffiliateSiteResponse()){
	                            sendRegistrationResponse(studySubject);
	                        }
	                    }catch(RuntimeException e){
	                        logger.error(e.getMessage());
	                        studySubject
	                                        .setMultisiteWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_REPLY_FAILED);
	                    }
	                }
	                else {
	                    studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.UNREGISTERED);
	                }
	            }
	            else {
	                studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.UNREGISTERED);
	            }
	        }
	        else {
	            studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.UNREGISTERED);
	        }
        }
        return studySubjectRepository.save(studySubject);
    }
    
    public void processAffliateSiteRegistrationRequest(StudySubject deserialisedStudySubject) {
        StudySubject studySubject=null;;
        try {
            studySubject = studySubjectFactory.buildStudySubject(deserialisedStudySubject);
            studySubject.updateDataEntryStatus();
            if (studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.INCOMPLETE) {
                throw getExceptionHelper().getException(
                                getCode("C3PR.EXCEPTION.REGISTRATION.DATA_ENTRY_INCOMPLETE.CODE"));
            }
            if (studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.INCOMPLETE) {
                throw getExceptionHelper()
                                .getException(
                                                getCode("C3PR.EXCEPTION.REGISTRATION.SCHEDULEDEPOCH.DATA_ENTRY_INCOMPLETE.CODE"));
            }
            studySubjectRepository.save(studySubject);
        }
        catch (C3PRCodedException e) {
            logger.error(e);
            deserialisedStudySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.DISAPPROVED);
            deserialisedStudySubject.setDisapprovalReasonText(e.getCodedExceptionMesssage());
            sendRegistrationResponse(deserialisedStudySubject);
            return;
        }
        if(!studySubject.getScheduledEpoch().getRequiresRandomization()||(studySubject.getScheduledEpoch().getRequiresRandomization() 
                        && studySubject.getStudySite().getStudy().getRandomizationType()==RandomizationType.BOOK)){
            try {
                this.register(studySubject);
            }
            catch (RuntimeException e) {
                logger.error(e);
                studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.DISAPPROVED);
                studySubject.setDisapprovalReasonText(e.getMessage());
                studySubjectRepository.save(studySubject);
                sendRegistrationResponse(studySubject);
                return;
            }
        }
    }
    
    public void processCoOrdinatingCenterResponse(StudySubject deserializedStudySubject) {
        StudySubject studySubject=studySubjectRepository.updateLocalRegistration(deserializedStudySubject);
        this.updateRegistrationStatus(studySubject);
    }
    
    public boolean isHostedMode() {
        return hostedMode;
    }

    public void setHostedMode(boolean hostedMode) {
        this.hostedMode = hostedMode;
    }

    public String getLocalInstanceNCICode() {
        return getConfiguration().get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
    }

    public void setStudySubjectRepository(StudySubjectRepository studySubjectRepository) {
        this.studySubjectRepository = studySubjectRepository;
    }

    public boolean requiresExternalApprovalForRegistration(StudySubject studySubject) {
        return studySubject.requiresCoordinatingCenterApproval()
        && !studySubject.isCoOrdinatingCenter(getLocalInstanceNCICode()) && !isHostedMode();
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public List<StudySubject> searchByExample(StudySubject ss, int maxResults) {
        return studySubjectDao.searchByExample(ss, maxResults);
    }
    
    public List<StudySubject> getIncompleteRegistrations(StudySubject registration, int maxResults) {
        return studySubjectDao.getIncompleteRegistrations(registration, maxResults);
    }
}

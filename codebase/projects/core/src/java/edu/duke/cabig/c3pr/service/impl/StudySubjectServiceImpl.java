package edu.duke.cabig.c3pr.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;

/**
 * @author Kruttik
 * @version 1.0
 * 
 */
public class StudySubjectServiceImpl extends CCTSWorkflowServiceImpl implements StudySubjectService {

    private static final Logger logger = Logger.getLogger(StudySubjectServiceImpl.class);

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
        return studySubjectRepository.save(studySubject);
    }
    
    @Transactional
    public StudySubject processAffliateSiteRegistrationRequest(StudySubject deserialisedStudySubject)
                    throws C3PRCodedException {
        StudySubject studySubject = studySubjectFactory.buildStudySubject(deserialisedStudySubject);
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
        return this.register(studySubject);
    }
    
    public void sendRegistrationRequest(StudySubject studySubject) {
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

    private boolean requiresExternalApprovalForRegistration(StudySubject studySubject){
        return studySubject.requiresCoordinatingCenterApproval()
        && !studySubject.isCoOrdinatingCenter(getLocalInstanceNCICode()) && !isHostedMode();
    }

    public void setStudySubjectRepository(StudySubjectRepository studySubjectRepository) {
        this.studySubjectRepository = studySubjectRepository;
    }
}

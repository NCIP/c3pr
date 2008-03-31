package edu.duke.cabig.c3pr.service.impl;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
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

    private StudySubjectDao studySubjectDao;

    private boolean hostedMode = true;

    private StudyTargetAccrualNotificationEmail notificationEmailer;
    
    private StudySubjectRepository studySubjectRepository;

    public void setNotificationEmailer(
                    StudyTargetAccrualNotificationEmail studyTargetAccrualNotificationEmail) {
        this.notificationEmailer = studyTargetAccrualNotificationEmail;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public void register(StudySubject studySubject){
        //save the registration record in the first step, so that if there is an exception
        //the user can resume the incomplete registration.
        studySubject=studySubjectRepository.save(studySubject);
        if(!studySubject.isDataEntryComplete()) return;
        if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED) {
            manageRegistration(studySubject);
        }
        updateRegistrationStatus(studySubject);
    }
    
    private void manageRegistration(StudySubject studySubject){
        ScheduledEpoch scheduledEpoch = studySubject.getScheduledEpoch();
        if (requiresExternalApprovalForRegistration(studySubject)) {
            try {
                sendRegistrationRequest(studySubject);
                scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
                studySubjectRepository.save(studySubject);
                return;
            }
            catch (RuntimeException e) {
                // TODO throw a C3PRCodedUncheckedException
                throw e;
            }
        }else{
            try {
                studySubjectRepository.doLocalRegistration(studySubject, true, true);
                return;
            }
            catch (C3PRCodedException e) {
             // TODO throw a C3PRCodedUncheckedException
                throw new RuntimeException(e);
            }
        }
    }
    
    private void updateRegistrationStatus(StudySubject studySubject){
        if (studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.REGISTERED) {
            return;
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
        studySubjectRepository.save(studySubject);
    }
    public void manageSchEpochWorkFlow(StudySubject studySubject, boolean triggerMultisite,
                    boolean randomize, boolean affiliateSiteRequest) throws C3PRCodedException {
        if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.UNAPPROVED) {
            return;
        }
        ScheduledEpoch scheduledEpoch = studySubject.getScheduledEpoch();
        if (studySubject.isDataEntryComplete()) {
            if (requiresExternalApprovalForRegistration(studySubject)) {
                // broadcase message to co-ordinating center
                try {
                    if (triggerMultisite) {
                        if (studySubject.getId() != null) {
                            Integer id = studySubjectDao.merge(studySubject).getId();
                            studySubject = studySubjectDao.getById(id);
                        }
                        else {
                            studySubjectDao.save(studySubject);
                        }
                        sendRegistrationRequest(studySubject);
                    }
                    scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
                }
                catch (Exception e) {
                    scheduledEpoch
                                    .setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.DISAPPROVED);
                    throw getExceptionHelper()
                                    .getException(
                                                    getCode("C3PR.EXCEPTION.REGISTRATION.ERROR_SEND_REGISTRATION.CODE"),
                                                    e);
                }
            }
            else {
                studySubjectRepository.doLocalRegistration(studySubject, randomize, affiliateSiteRequest);
            }
        }
        else {
            scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
        }
    }

    public void manageRegWorkFlow(StudySubject studySubject) throws C3PRCodedException {
        if (studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.REGISTERED) {
            return;
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
                    if (studySubject.getId() != null) {
                        Integer id = studySubjectDao.merge(studySubject).getId();
                        studySubject = studySubjectDao.getById(id);
                    }
                    else {
                        studySubjectDao.save(studySubject);

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
                        logger.error(e.getMessage());
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

    public void sendRegistrationRequest(StudySubject studySubject) {
    }

    public void manageSchEpochWorkFlow(StudySubject studySubject) throws C3PRCodedException {
        manageSchEpochWorkFlow(studySubject, true, true, false);
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
}

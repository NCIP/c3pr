package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StratumGroupDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.NonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.impl.StudySubjectXMLImporterServiceImpl;

public class StudySubjectRepositoryImpl implements StudySubjectRepository {

    private StudySubjectDao studySubjectDao;

    private ParticipantDao participantDao;

    private EpochDao epochDao;

    private StratumGroupDao stratumGroupDao;

    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;

    private StudySubjectFactory studySubjectFactory;

    private Logger log = Logger.getLogger(StudySubjectXMLImporterServiceImpl.class.getName());

    public void assignC3DIdentifier(StudySubject studySubject, String c3dIdentifierValue) {
        StudySubject loadedSubject = studySubjectDao.getByGridId(studySubject.getGridId());
        loadedSubject.setC3DIdentifier(c3dIdentifierValue);
        studySubjectDao.save(loadedSubject);
    }

    public void assignCoOrdinatingCenterIdentifier(StudySubject studySubject, String identifierValue) {
        StudySubject loadedSubject = studySubjectDao.getByGridId(studySubject.getGridId());
        loadedSubject.setCoOrdinatingCenterIdentifier(identifierValue);
        studySubjectDao.save(loadedSubject);
    }

    public boolean isEpochAccrualCeilingReached(int epochId) {
        Epoch epoch = epochDao.getById(epochId);
        if (epoch.isReserving()) {
            ScheduledEpoch scheduledEpoch = new ScheduledNonTreatmentEpoch(true);
            scheduledEpoch.setEpoch(epoch);
            List<StudySubject> list = studySubjectDao.searchByScheduledEpoch(scheduledEpoch);
            NonTreatmentEpoch nEpoch = (NonTreatmentEpoch) epoch;
            if (nEpoch.getAccrualCeiling() != null
                            && list.size() >= nEpoch.getAccrualCeiling().intValue()) {
                return true;
            }
        }
        return false;
    }

    private StudySubject doRandomization(StudySubject studySubject) throws C3PRBaseException {
        // randomize subject
        switch (studySubject.getStudySite().getStudy().getRandomizationType()) {
            case PHONE_CALL:
                break;
            case BOOK:
                doBookRandomization(studySubject);
                break;
            case CALL_OUT:
                break;
            default:
                break;
        }
        return studySubject;
    }

    private void doBookRandomization(StudySubject studySubject) throws C3PRBaseException {
        ScheduledArm sa = new ScheduledArm();
        ScheduledTreatmentEpoch ste = (ScheduledTreatmentEpoch) studySubject.getScheduledEpoch();
        sa.setArm(studySubject.getStratumGroup().getNextArm());
        if (sa.getArm() != null) {
            ste.addScheduledArm(sa);
            stratumGroupDao.merge(studySubject.getStratumGroup());
        }
    }

    @Transactional
    public StudySubject doLocalRegistration(StudySubject studySubject) throws C3PRCodedException {
        studySubject.updateDataEntryStatus();
        if (!studySubject.isDataEntryComplete()) return studySubject;
        ScheduledEpoch scheduledEpoch = studySubject.getScheduledEpoch();
        if (studySubject.getScheduledEpoch().getRequiresRandomization()) {
            try {
                this.doRandomization(studySubject);
            }
            catch (Exception e) {
                scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
                throw exceptionHelper.getException(
                                getCode("C3PR.EXCEPTION.REGISTRATION.RANDOMIZATION.CODE"), e);
            }
            if (((ScheduledTreatmentEpoch) studySubject.getScheduledEpoch()).getScheduledArm() == null) {
                scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
                throw exceptionHelper
                                .getException(getCode("C3PR.EXCEPTION.REGISTRATION.CANNOT_ASSIGN_ARM.CODE"));
            }
            else {
                // logic for accrual ceiling check
                scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
            }
        }
        else {
            // logic for accrual ceiling check
            scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
        }
        return this.save(studySubject);
    }

    /**
     * Saves the Imported StudySubject to the database. Moved it from the service as a part of the
     * refactoring effort.
     * 
     * @param deserialedStudySubject
     * @return
     * @throws C3PRCodedException
     */
    public StudySubject importStudySubject(StudySubject deserialedStudySubject)
                    throws C3PRCodedException {
        StudySubject studySubject = studySubjectFactory.buildStudySubject(deserialedStudySubject);
        if (studySubject.getScheduledEpoch().getEpoch().getRequiresArm()) {
            ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) studySubject
                            .getScheduledEpoch();
            if (scheduledTreatmentEpoch.getScheduledArm() == null
                            || scheduledTreatmentEpoch.getScheduledArm().getArm() == null
                            || scheduledTreatmentEpoch.getScheduledArm().getArm().getId() == null) throw this.exceptionHelper
                            .getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.REQUIRED.ARM.NOTFOUND.CODE"));
        }
        studySubject.updateDataEntryStatus();
        if (studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.INCOMPLETE) {
            throw this.exceptionHelper
                            .getException(getCode("C3PR.EXCEPTION.REGISTRATION.DATA_ENTRY_INCOMPLETE.CODE"));
        }
        if (studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.INCOMPLETE) {
            throw this.exceptionHelper
                            .getException(getCode("C3PR.EXCEPTION.REGISTRATION.SCHEDULEDEPOCH.DATA_ENTRY_INCOMPLETE.CODE"));
        }
        if (studySubject.getScheduledEpoch().isReserving()) {
            studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
        }
        else if (studySubject.getScheduledEpoch().getEpoch().isEnrolling()) {
            studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED);
        }
        else {
            studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.UNREGISTERED);
        }
        studySubject=save(studySubject);
        log.debug("Registration saved with grid ID" + studySubject.getGridId());
        return studySubject;
    }

    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
        this.participantDao = participantDao;
    }

    public void setEpochDao(EpochDao epochDao) {
        this.epochDao = epochDao;
    }

    public void setStratumGroupDao(StratumGroupDao stratumGroupDao) {
        this.stratumGroupDao = stratumGroupDao;
    }

    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    public StudySubject save(StudySubject studySubject) {
        studySubject.updateDataEntryStatus();
        if (studySubject.getId() != null) return studySubjectDao.merge(studySubject);
        studySubjectDao.save(studySubject);
        return studySubject;
    }

    public void setStudySubjectFactory(StudySubjectFactory studySubjectFactory) {
        this.studySubjectFactory = studySubjectFactory;
    }
}

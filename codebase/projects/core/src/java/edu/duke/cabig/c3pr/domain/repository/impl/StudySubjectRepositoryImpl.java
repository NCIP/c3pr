package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StratumGroupDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.NonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectService;

public class StudySubjectRepositoryImpl implements StudySubjectRepository{

    private StudySubjectDao studySubjectDao;

    private ParticipantDao participantDao;

    private EpochDao epochDao;

    private StratumGroupDao stratumGroupDao;
    
    private StudySubjectService studySubjectService;
    
    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;

    
    public void assignC3DIdentifier(StudySubject studySubject, String c3dIdentifierValue) {
        StudySubject loadedSubject = studySubjectDao.getByGridId(studySubject.getGridId());
        loadedSubject.setC3DIdentifier(c3dIdentifierValue);
        studySubjectDao.save(loadedSubject);
    }
    
    public void assignCoOrdinatingCenterIdentifier(String studySubjectGridId, String identifierValue) {
        StudySubject studySubject = studySubjectDao.getByGridId(studySubjectGridId);
        studySubject.setCoOrdinatingCenterIdentifier(identifierValue);
        studySubjectDao.save(studySubject);
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
    
    @Transactional
    public StudySubject createRegistration(StudySubject studySubject) throws C3PRCodedException {
        studySubject.setRegDataEntryStatus(studySubject.evaluateRegistrationDataEntryStatus());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        studySubject.evaluateScheduledEpochDataEntryStatus());
        // evaluate status
        if (studySubject.isCreatable()) {
            studySubjectService.manageSchEpochWorkFlow(studySubject, false, false, false);
            studySubjectService.manageRegWorkFlow(studySubject);
        }
        studySubject = studySubjectDao.merge(studySubject);
        return studySubject;
    }

    @Transactional
    public StudySubject registerSubject(StudySubject studySubject) throws C3PRCodedException {
        studySubject.setRegDataEntryStatus(studySubject.evaluateRegistrationDataEntryStatus());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        studySubject.evaluateScheduledEpochDataEntryStatus());
        studySubjectService.manageSchEpochWorkFlow(studySubject, true, true, false);
        studySubjectService.manageRegWorkFlow(studySubject);
        return studySubjectDao.merge(studySubject);
    }

    @Transactional
    public StudySubject processAffliateSiteRegistrationRequest(StudySubject studySubject)
                    throws C3PRCodedException {
        if (studySubject.getParticipant().getId() != null) {
            StudySubject exampleSS = new StudySubject(true);
            exampleSS.setParticipant(studySubject.getParticipant());
            exampleSS.setStudySite(studySubject.getStudySite());
            List<StudySubject> registrations = studySubjectDao
                            .searchBySubjectAndStudySite(exampleSS);
            if (registrations.size() > 0) {
                throw exceptionHelper
                                .getException(
                                                getCode("C3PR.EXCEPTION.REGISTRATION.STUDYSUBJECTS_ALREADY_EXISTS.CODE"));
            }
        }
        else {
            participantDao.save(studySubject.getParticipant());
        }
        studySubject.setRegDataEntryStatus(studySubject.evaluateRegistrationDataEntryStatus());
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(
                        studySubject.evaluateScheduledEpochDataEntryStatus());
        if (studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.INCOMPLETE) {
            throw exceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.REGISTRATION.DATA_ENTRY_INCOMPLETE.CODE"));
        }
        if (studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.INCOMPLETE) {
            throw exceptionHelper
                            .getException(
                                            getCode("C3PR.EXCEPTION.REGISTRATION.SCHEDULEDEPOCH.DATA_ENTRY_INCOMPLETE.CODE"));
        }
        studySubjectService.manageSchEpochWorkFlow(studySubject, true, true, true);
        studySubjectService.manageRegWorkFlow(studySubject);
        return studySubjectDao.merge(studySubject);
    }
    


    public StudySubject doRandomization(StudySubject studySubject) throws C3PRBaseException {
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

    private StudySubject doBookRandomization(StudySubject studySubject) throws C3PRBaseException {
        ScheduledArm sa = new ScheduledArm();
        ScheduledTreatmentEpoch ste = (ScheduledTreatmentEpoch) studySubject.getScheduledEpoch();
        sa.setArm(studySubject.getStratumGroup().getNextArm());
        if (sa.getArm() != null) {
            ste.addScheduledArm(sa);
            stratumGroupDao.merge(studySubject.getStratumGroup());
        }
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

    public void setStudySubjectService(StudySubjectService studySubjectService) {
        this.studySubjectService = studySubjectService;
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

}

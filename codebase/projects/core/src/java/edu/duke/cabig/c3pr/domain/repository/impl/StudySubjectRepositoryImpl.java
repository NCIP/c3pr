package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.Iterator;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StratumGroupDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.impl.StudySubjectXMLImporterServiceImpl;
import edu.duke.cabig.c3pr.utils.StringUtils;

@Transactional
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
            ScheduledEpoch scheduledEpoch = new ScheduledEpoch(true);
            scheduledEpoch.setEpoch(epoch);
            List<StudySubject> list = studySubjectDao.searchByScheduledEpoch(scheduledEpoch);
            Epoch nEpoch =  epoch;
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
        ScheduledEpoch ste = studySubject.getScheduledEpoch();
        if (studySubject.getStudySite().getStudy().getStratificationIndicator()){
	        	sa.setArm(studySubject.getStratumGroup().getNextArm());
	        if (sa.getArm() != null) {
	            ste.addScheduledArm(sa);
	            stratumGroupDao.merge(studySubject.getStratumGroup());
	        }
        } else {
        	sa.setArm(getNextArmForUnstratifiedStudy(studySubject));
	        if (sa.getArm() != null) {
	            ste.addScheduledArm(sa);
	        }
        }
    }

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
            if ((studySubject.getScheduledEpoch()).getScheduledArm() == null) {
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
    @Transactional(readOnly = false)
    public StudySubject importStudySubject(StudySubject deserialedStudySubject)
			throws C3PRCodedException {
		StudySubject studySubject = studySubjectFactory.buildStudySubject(deserialedStudySubject);
		if (studySubject.getParticipant().getId() != null) {
		    StudySubject exampleSS = new StudySubject(true);
		    exampleSS.setParticipant(studySubject.getParticipant());
		    exampleSS.setStudySite(studySubject.getStudySite());
		    List<StudySubject> registrations = studySubjectDao.searchBySubjectAndStudySite(exampleSS);
		    if (registrations.size() > 0) {
		        throw this.exceptionHelper
		        .getException(getCode("C3PR.EXCEPTION.REGISTRATION.STUDYSUBJECTS_ALREADY_EXISTS.CODE"));
		    }
		} else {
		    if (studySubject.getParticipant().validateParticipant())
		        participantDao.save(studySubject.getParticipant());
		    else {
		        throw this.exceptionHelper
		        .getException(getCode("C3PR.EXCEPTION.REGISTRATION.SUBJECTS_INVALID_DETAILS.CODE"));
		    }
		}
		if (studySubject.getScheduledEpoch().getEpoch().getRequiresArm()) {
			ScheduledEpoch scheduledTreatmentEpoch = studySubject
					.getScheduledEpoch();
			if (scheduledTreatmentEpoch.getScheduledArm() == null
					|| scheduledTreatmentEpoch.getScheduledArm().getArm() == null
					|| scheduledTreatmentEpoch.getScheduledArm().getArm().getId() == null)
				throw this.exceptionHelper
						.getException(getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.REQUIRED.ARM.NOTFOUND.CODE"));
		}
		studySubject.setRegDataEntryStatus(studySubject.evaluateRegistrationDataEntryStatus());
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(studySubject.evaluateScheduledEpochDataEntryStatus());
		if (studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.INCOMPLETE) {
			throw this.exceptionHelper
					.getException(getCode("C3PR.EXCEPTION.REGISTRATION.DATA_ENTRY_INCOMPLETE.CODE"));
		}
		if (studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.INCOMPLETE) {
			throw this.exceptionHelper
					.getException(getCode("C3PR.EXCEPTION.REGISTRATION.SCHEDULEDEPOCH.DATA_ENTRY_INCOMPLETE.CODE"));
		}
		studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
		if (studySubject.getScheduledEpoch().isReserving()) {
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
		} else if (studySubject.getScheduledEpoch().getEpoch().isEnrolling()) {
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED);
		} else {
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.UNREGISTERED);
		}
		studySubjectDao.save(studySubject);
		log.debug("Registration saved with grid ID" + studySubject.getGridId());
		return studySubject;
	}
    
    @Transient
    public Arm getNextArmForUnstratifiedStudy(StudySubject studySubject) throws C3PRBaseException {
	  Arm arm = null;
	  	if (studySubject.getScheduledEpoch() instanceof ScheduledEpoch){
	  		if ((studySubject.getScheduledEpoch()).getEpoch().hasBookRandomizationEntry()){
	  			Iterator<BookRandomizationEntry> iter = ((BookRandomization)(studySubject.getScheduledEpoch()).getEpoch().getRandomization()).getBookRandomizationEntry().iterator();
	  			BookRandomizationEntry breTemp;
		        
		        while (iter.hasNext()) {
		            breTemp = iter.next();
		            if (breTemp.getPosition().equals((studySubject.getScheduledEpoch()).getCurrentPosition())) {
		                synchronized (this) {
		                	(studySubject.getScheduledEpoch()).setCurrentPosition(breTemp.getPosition()+1);
		                    arm = breTemp.getArm();
		                    break;
		                }
		            }
		        }
	  		}
	  	}
        
        if (arm == null) {
            throw new C3PRBaseException(
                            "No Arm avalable for this Treatment Epoch. Maybe the Randomization Book is exhausted");
        }
        return arm;
    }
    
    
    public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
        this.studySubjectDao = studySubjectDao;
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

    public void setParticipantDao(ParticipantDao participantDao) {
	this.participantDao = participantDao;
    }

    public StudySubject updateLocalRegistration(StudySubject updatedStudySubject) {
        StudySubject referencedStudySubject=null;
        StudySubject studySubject=null;
        try {
            referencedStudySubject=studySubjectFactory.buildReferencedStudySubject(updatedStudySubject);
        }
        catch (C3PRCodedException e) {
            throw new RuntimeException(e);
        }
        if (referencedStudySubject.getParticipant().getId() != null) {
            List<StudySubject> registrations = findRegistrations(referencedStudySubject);
            if (registrations.size() == 1) {
                studySubject=registrations.get(0);
            }
        }
        if(studySubject==null){
            throw new RuntimeException("Error finding reistration record in database.");
        }
        if(studySubject.getScheduledEpoch().getScEpochWorkflowStatus()!=ScheduledEpochWorkFlowStatus.PENDING){
            throw new RuntimeException("Schedule epoch not in pending status.");
        }
        ScheduledEpoch scheduledEpoch = studySubject.getScheduledEpoch();
        if(referencedStudySubject.getScheduledEpoch().getScEpochWorkflowStatus()!=ScheduledEpochWorkFlowStatus.APPROVED){
            String disapprovalReason="";
            if(StringUtils.getBlankIfNull(referencedStudySubject.getScheduledEpoch().getDisapprovalReasonText()).equals(""))
                disapprovalReason="Registration was not approved by co-ordinating center. No error message was provided.";
            else
                disapprovalReason=updatedStudySubject.getScheduledEpoch().getDisapprovalReasonText();
            studySubject.disapprove(disapprovalReason);
        }else{
            if (studySubject.getScheduledEpoch().getRequiresRandomization()) {
                if ((referencedStudySubject.getScheduledEpoch()).getScheduledArm() == null) {
                    studySubject.disapprove("Registration was approved by co-ordinating center. However no arm was assigned.");
                }
                else {
                    ScheduledArm assignedScheduledArm=(referencedStudySubject.getScheduledEpoch()).getScheduledArm();
                    (scheduledEpoch).getScheduledArms().add(0,assignedScheduledArm);
                    scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
                }
            }
            else {
                // logic for accrual ceiling check
                scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
            }
        }
        return this.save(studySubject);
    }

    public List<StudySubject> findRegistrations(StudySubject exampleStudySubject) {
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(exampleStudySubject.getParticipant());
        exampleSS.setStudySite(exampleStudySubject.getStudySite());
        return studySubjectDao.searchBySubjectAndStudySite(exampleSS);
    }
	
}

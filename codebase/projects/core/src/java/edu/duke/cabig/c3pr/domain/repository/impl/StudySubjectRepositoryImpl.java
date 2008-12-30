package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.ArrayList;
import java.util.Date;
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
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.IdentifierGenerator;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.service.impl.WorkflowServiceImpl;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

@Transactional
public class StudySubjectRepositoryImpl implements StudySubjectRepository {

    private StudySubjectDao studySubjectDao;
    
    private ParticipantDao participantDao;

    private EpochDao epochDao;

    private StratumGroupDao stratumGroupDao;

    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;

    private StudySubjectFactory studySubjectFactory;
    
    private StudySubjectService studySubjectService;
    
    private StudyTargetAccrualNotificationEmail notificationEmailer;
    
    //private StudyService studyService;
    
    private Logger log = Logger.getLogger(StudySubjectRepositoryImpl.class.getName());

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
        if (ste.getEpoch().getStratificationIndicator()){
	        	sa.setArm(studySubject.getScheduledEpoch().getStratumGroup().getNextArm());
	        if (sa.getArm() != null) {
	            ste.addScheduledArm(sa);
	            stratumGroupDao.merge(studySubject.getScheduledEpoch().getStratumGroup());
	        }
        } else {
        	sa.setArm(getNextArmForUnstratifiedStudy(studySubject));
	        if (sa.getArm() != null) {
	            ste.addScheduledArm(sa);
	        }
        }
    }

    public StudySubject doLocalRegistration(StudySubject studySubject) throws C3PRCodedException {
    	continueEnrollment(studySubject);
    	return studySubject;
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
		studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
		if (studySubject.getScheduledEpoch().isReserving()) {
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
		} else if (studySubject.getScheduledEpoch().getEpoch().isEnrolling()) {
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
		} else {
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
		}
		studySubjectDao.save(studySubject);
		log.debug("Registration saved with grid ID" + studySubject.getGridId());
		return studySubject;
	}
    
    @Transient
    public Arm getNextArmForUnstratifiedStudy(StudySubject studySubject) throws C3PRBaseException {
	  Arm arm = null;
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
 //       studySubject.updateDataEntryStatus();
//        if (studySubject.getId() != null) return studySubjectDao.merge(studySubject);
//        studySubjectDao.save(studySubject);
//        return studySubject;
        return studySubjectDao.merge(studySubject);
    }
    
    public void setStudySubjectFactory(StudySubjectFactory studySubjectFactory) {
        this.studySubjectFactory = studySubjectFactory;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
	this.participantDao = participantDao;
    }

    public List<StudySubject> findRegistrations(StudySubject exampleStudySubject) {
        StudySubject exampleSS = new StudySubject(true);
        exampleSS.setParticipant(exampleStudySubject.getParticipant());
        exampleSS.setStudySite(exampleStudySubject.getStudySite());
        return studySubjectDao.searchBySubjectAndStudySite(exampleSS);
    }

	public StudySubject enroll(List<Identifier> studySubjectIdentifiers) {
		StudySubject studySubject = getUniqueStudySubjects(studySubjectIdentifiers);
		this.continueEnrollment(studySubject);
		studySubjectDao.save(studySubject);
		
		sendStudyAccrualNotification(studySubject);
		broadcastMessage(studySubject);
        
		return studySubject;
	}
	
	//Send out the CCTS broadcast Message
	private void broadcastMessage(StudySubject studySubjectAfterSave) {
		try {
			studySubjectService.broadcastMessage(studySubjectAfterSave);
        }
        catch (C3PRCodedException e) {
            log.error(e.getMessage());
            studySubjectAfterSave.setCctsWorkflowStatus(WorkFlowStatusType.MESSAGE_SEND_FAILED);
        }
        catch (Exception e) {
         // TODO throw a C3PRCodedUncheckedException
        	log.error(e.getMessage());
            throw new RuntimeException(e);
        }
	}

	//Send out the study accrual notification.
	private void sendStudyAccrualNotification(StudySubject studySubjectAfterSave) {
		try {
            this.notificationEmailer.sendEmail(studySubjectAfterSave);
        }
        catch (Exception e) {
        	// TODO throw a C3PRCodedUncheckedException
        	log.error(e.getMessage());
            throw new RuntimeException(e);
        }
	}

	public StudySubject enroll(StudySubject studySubject) {
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		studySubjects=findRegistrations(studySubject);
		if (studySubjects.size() > 1) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
        }
		this.continueEnrollment(studySubject);
		 studySubjectDao.save(studySubject);
		
		sendStudyAccrualNotification(studySubject);
		broadcastMessage(studySubject);
        
		return studySubject;
	}
	
	public void continueEnrollment(StudySubject studySubject) {
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.REGISTERED) {
			studySubject.prepareForEnrollment();
		}
		
		if (!studySubject.getStudySite().getHostedMode() && !studySubject.getStudySite().getIsCoordinatingCenter() && !studySubject.getStudySite().getStudy().isCoOrdinatingCenter(studySubjectService.getLocalNCIInstituteCode())){
			List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
            domainObjects.add(studySubject);
            EndPoint endPoint=handleCoordinatingCenterBroadcast(studySubject.getStudySite().getStudy(), APIName.ENROLL_SUBJECT, domainObjects);
            if(endPoint.getStatus()!=WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                throw this.exceptionHelper.getRuntimeException(endPoint.getLastAttemptError());
            }
            StudySubject multisiteReturnedStudySubject=(StudySubject)((List) endPoint.getReturnValue()).get(0);
			//StudySubject multisiteReturnedStudySubject = studySubjectServiceImpl.getArmAndCoordinatingAssignedIdentifier(studySubject);
			studySubject.doMutiSiteEnrollment(multisiteReturnedStudySubject.getCurrentScheduledEpoch(),multisiteReturnedStudySubject.getCoOrdinatingCenterIdentifier());
		}else{
			studySubject.doLocalEnrollment();
		}
	}

	public StudySubject register(StudySubject studySubject) {
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		studySubjects=findRegistrations(studySubject);
		if (studySubjects.size() > 1) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
        }
		studySubject.register();
		return save(studySubject);
	}

	public StudySubject register(List<Identifier> studySubjectIdentifiers) {
		StudySubject studySubject = getUniqueStudySubjects(studySubjectIdentifiers);
		studySubject.register();
		return save(studySubject);
	}

	public void takeSubjectOffStudy(List<Identifier> studySubjectIdentifiers, String offStudyReasonText,
			Date offStudyDate) {
		StudySubject studySubject = getUniqueStudySubjects(studySubjectIdentifiers);
		 studySubject.takeSubjectOffStudy(offStudyReasonText,offStudyDate);
		save(studySubject);
	}

	public StudySubject transferSubject(List<Identifier> studySubjectIdentifiers) {
		StudySubject studySubject = getUniqueStudySubjects(studySubjectIdentifiers);
		
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.REGISTERED) {
			studySubject.prepareForTransfer();
		}
		
		if (!studySubject.getStudySite().getHostedMode() && !studySubject.getStudySite().getIsCoordinatingCenter() && !studySubject.getStudySite().getStudy().isCoOrdinatingCenter(studySubjectService.getLocalNCIInstituteCode())){
			StudySubject multisiteReturnedStudySubject = studySubjectService.getArmAndCoordinatingAssignedIdentifier(studySubject);
			studySubject.doMutiSiteTransfer(multisiteReturnedStudySubject.getCurrentScheduledEpoch(),multisiteReturnedStudySubject.getCoOrdinatingCenterIdentifier());
		}else{
			studySubject.doLocalTransfer();
		}
		
		return save(studySubject);
	}
	
	public StudySubject create(StudySubject studySubject) {
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		studySubjects=findRegistrations(studySubject);
		if (studySubjects.size() > 0) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
        }
		
		if (!studySubject.hasC3PRSystemIdentifier()){
			studySubject.addIdentifier(IdentifierGenerator.generateSystemAssignedIdentifier(studySubject));
		}
		
		return save(studySubject);
	}

	public StudySubject reserve(StudySubject studySubject) {
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		studySubjects=findRegistrations(studySubject);
		if (studySubjects.size() > 1) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
        }
		studySubject.reserve();
		return studySubject;
	}

	public StudySubject reserve(List<Identifier> studySubjectIdentifiers) {
		StudySubject studySubject = getUniqueStudySubjects(studySubjectIdentifiers);
		studySubject.reserve();
		return save(studySubject);
	}
	
	 public StudySubject getUniqueStudySubjects(List<Identifier> studySubjectIdentifiers) {
	        List<StudySubject> studySubjects = studySubjectDao.getByIdentifiers(studySubjectIdentifiers);
	        if (studySubjects.size() == 0) {
	            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.NOT_FOUND_GIVEN_IDENTIFIERS.CODE"));
	        }
	        else if (studySubjects.size() > 1) {
	            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
	        }
	        return studySubjects.get(0);
	    }
	 
	 public EndPoint handleCoordinatingCenterBroadcast(Study study,
				APIName multisiteAPIName, List domainObjects) {
			for(EndPoint endPoint: study.getStudyCoordinatingCenters().get(0).getEndpoints()){
				endPoint.getErrors().size();
	        	studySubjectDao.evict(endPoint);
	        }
			return studySubjectService.handleMultiSiteBroadcast(study.getStudyCoordinatingCenters()
					.get(0), ServiceName.REGISTRATION, multisiteAPIName,
					domainObjects);
		}

	public void setStudySubjectService(StudySubjectService studySubjectService) {
		this.studySubjectService = studySubjectService;
	}

	public StudyTargetAccrualNotificationEmail getNotificationEmailer() {
		return notificationEmailer;
	}

	public void setNotificationEmailer(
			StudyTargetAccrualNotificationEmail notificationEmailer) {
		this.notificationEmailer = notificationEmailer;
	}

}

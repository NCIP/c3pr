/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.ReasonDao;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.StratumGroupDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.factory.StudySubjectFactory;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.IdentifierGenerator;
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
    
    private IdentifierGenerator identifierGenerator ;
    
    private ReasonDao reasonDao;
    
    private PersonUserDao personUserDao;
    
    //private StudyService studyService;
    
    private Logger log = Logger.getLogger(StudySubjectRepositoryImpl.class.getName());

	public void assignC3DIdentifier(StudySubject studySubject, String c3dIdentifierValue) {
		log.debug("loading study subject by grid id : "+studySubject.getGridId());
        StudySubject loadedSubject = studySubjectDao.getByGridId(studySubject.getGridId());
        log.debug("loaded study subject with database id : "+loadedSubject.getId());
        log.debug("assigning c3d identifier value: "+c3dIdentifierValue);
        loadedSubject.setC3DIdentifier(c3dIdentifierValue);
        log.debug("assigned c3d identifier: "+loadedSubject.getC3DIdentifier());
        studySubjectDao.save(loadedSubject);
    }
	
	public void assignMedidataIdentifier(StudySubject studySubject, String medidataIdentifierValue) {
		log.debug("loading study subject by grid id : "+studySubject.getGridId());
        StudySubject loadedSubject = studySubjectDao.getByGridId(studySubject.getGridId());
        log.debug("loaded study subject with database id : "+loadedSubject.getId());
        log.debug("assigning medidata identifier value: "+medidataIdentifierValue);
        loadedSubject.setMedidataIdentifier(medidataIdentifierValue);
        log.debug("assigned medidata identifier: "+loadedSubject.getC3DIdentifier());
        studySubjectDao.save(loadedSubject);
    }

	public void assignCoOrdinatingCenterIdentifier(StudySubject studySubject, String identifierValue) {
        StudySubject loadedSubject = studySubjectDao.getByGridId(studySubject.getGridId());
        loadedSubject.setCoOrdinatingCenterIdentifier(identifierValue);
        studySubjectDao.save(loadedSubject);
    }

    public boolean isEpochAccrualCeilingReached(int epochId) {
        Epoch epoch = epochDao.getById(epochId);
        if (epoch.getType() == EpochType.RESERVING) {
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

//    public StudySubject doLocalRegistration(StudySubject studySubject) throws C3PRCodedException {
//    	continueEnrollment(studySubject);
//    	return studySubject;
//    }

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
		if(studySubjectDao.getByIdentifiers(studySubject.getIdentifiers()).size()>0){
        	throw exceptionHelper.getException(
                    getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
        }
		if (studySubject.getStudySubjectDemographics().getMasterSubject().getId() != null) {
		    List<StudySubject> registrations = studySubjectDao.searchBySubjectAndStudyIdentifiers(studySubject.
		    		getStudySubjectDemographics().getMasterSubject().getPrimaryIdentifier(),studySubject.getStudySite().getStudy().
		    		getCoordinatingCenterAssignedIdentifier());
		    if (registrations.size() > 0) {
		        throw this.exceptionHelper
		        .getException(getCode("C3PR.EXCEPTION.REGISTRATION.STUDYSUBJECTS_ALREADY_EXISTS.CODE"));
		    }
		} else {
		    if (studySubject.getStudySubjectDemographics().getMasterSubject().validateParticipant())
		        participantDao.save(studySubject.getStudySubjectDemographics().getMasterSubject());
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
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(studySubject.evaluateScheduledEpochDataEntryStatus((List)new ArrayList<Error>()));
		if (studySubject.getRegDataEntryStatus() == RegistrationDataEntryStatus.INCOMPLETE) {
			throw this.exceptionHelper
					.getException(getCode("C3PR.EXCEPTION.REGISTRATION.DATA_ENTRY_INCOMPLETE.CODE"));
		}
		if (studySubject.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.INCOMPLETE) {
			throw this.exceptionHelper
					.getException(getCode("C3PR.EXCEPTION.REGISTRATION.SCHEDULEDEPOCH.DATA_ENTRY_INCOMPLETE.CODE"));
		}
		boolean hasC3PRAssignedIdentifier=false;
		for(SystemAssignedIdentifier systemAssignedIdentifier: studySubject.getSystemAssignedIdentifiers()){
			if(systemAssignedIdentifier.getSystemName().equals("C3PR")){
				hasC3PRAssignedIdentifier=true;
				break;
			}
		}
		if(!hasC3PRAssignedIdentifier){
			studySubject.addIdentifier(identifierGenerator.generateSystemAssignedIdentifier(studySubject));
		}
		studySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
		if (studySubject.getScheduledEpoch().isReserving()) {
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
		} else if (studySubject.getScheduledEpoch().getEpoch().isEnrolling()) {
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
			
		} else {
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
		}
		
		//make sure there is atleast one primaryIdentifier
		boolean hasPrimaryIdentifier = false;
		for(Identifier identifier : studySubject.getIdentifiers()){
			if(identifier.getPrimaryIndicator()){
				hasPrimaryIdentifier = true;
				break;
			}
		}
		if(!hasPrimaryIdentifier){
			Identifier identifier = studySubject.getCoOrdinatingCenterIdentifier();
			if(identifier != null){
				identifier.setPrimaryIndicator(true);
			}else{
				identifier = studySubject.getC3PRAssignedIdentifier();
				if(identifier != null){
					identifier.setPrimaryIndicator(true);
				}
			}
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
		            if (breTemp.getPosition().equals((studySubject.getScheduledEpoch().getEpoch().getCurrentBookRandomizationEntryPosition()))) {
		                synchronized (this) {
		                	(studySubject.getScheduledEpoch().getEpoch()).setCurrentBookRandomizationEntryPosition(breTemp.getPosition()+1);
		                    arm = breTemp.getArm();
		                    break;
		                }
		            }
		        }
	  		}
        
        if (arm == null) {
        	throw this.exceptionHelper.getException(
                    getCode("C3PR.EXCEPTION.REGISTRATION.NO.ARM.AVAILABLE.BOOK.EXHAUSTED.CODE"));
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
        return studySubjectDao.merge(studySubject);
    }
    
    public void setStudySubjectFactory(StudySubjectFactory studySubjectFactory) {
        this.studySubjectFactory = studySubjectFactory;
    }

    public void setParticipantDao(ParticipantDao participantDao) {
	this.participantDao = participantDao;
    }

    public List<StudySubject> findRegistrations(StudySubject exampleStudySubject) {
        return studySubjectDao.searchBySubjectAndStudyIdentifiers(exampleStudySubject.getStudySubjectDemographics().getMasterSubject().
        		getPrimaryIdentifier(), exampleStudySubject.getStudySite().getStudy().getCoordinatingCenterAssignedIdentifier());
    }

	public StudySubject enroll(Identifier studySubjectIdentifier) throws C3PRCodedException {
		StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
		return enroll(studySubject);
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

	public StudySubject enroll(StudySubject studySubject) throws C3PRCodedException {
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		studySubjects=findRegistrations(studySubject);
		studySubjectDao.initialize(studySubject);
		if (studySubjects.size() > 1) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
        }
		this.continueEnrollment(studySubject);
		
		this.saveStratumGroup(studySubject);
		this.updateEpoch(studySubject);
		
		// check for the validity of the demographics snap shot and update participant if invalid
		if (!studySubject.getStudySubjectDemographics().getValid()){
			takeSnapshotAndValidateStudySubjectDemographics(studySubject);
		}
		studySubject = studySubjectDao.merge(studySubject);
		
		sendStudyAccrualNotification(studySubject);
		broadcastMessage(studySubject);
        
		return studySubject;
	}
	
	public void continueEnrollment(StudySubject studySubject) throws C3PRCodedException {
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.ON_EPOCH) {
			studySubject.prepareForEnrollment();
		}
		
		if (!studySubject.getStudySite().getHostedMode() && !studySubject.getStudySite().getIsCoordinatingCenter() && !studySubject.getStudySite().getStudy().isCoOrdinatingCenter(studySubjectService.getLocalNCIInstituteCode())){
			List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
            domainObjects.add(studySubject);
            EndPoint endPoint=handleCoordinatingCenterBroadcast(studySubject.getStudySite().getStudy(), APIName.ENROLL_SUBJECT, domainObjects);
            if(endPoint.getStatus()!=WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                throw this.exceptionHelper.getMultisiteException(endPoint.getLastAttemptError());
            }
            StudySubject multisiteReturnedStudySubject=(StudySubject)((List) endPoint.getReturnValue()).get(0);
            studySubjectDao.initialize(multisiteReturnedStudySubject);
			//StudySubject multisiteReturnedStudySubject = studySubjectServiceImpl.getArmAndCoordinatingAssignedIdentifier(studySubject);
			studySubject.doMutiSiteEnrollment(multisiteReturnedStudySubject.getScheduledEpoch(),multisiteReturnedStudySubject.getCoOrdinatingCenterIdentifier());
		}else{
			if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.ON_STUDY) {
				studySubject.addIdentifier(identifierGenerator.generateOrganizationAssignedIdentifier(studySubject));
			}
			studySubject.doLocalEnrollment();
		}
		
		for (StudySubject childStudySubject : studySubject.getChildStudySubjects()) {
			if (childStudySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.PENDING_ON_STUDY && childStudySubject.getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH) {
				continueEnrollment(childStudySubject);
			}
		}
	}

	public StudySubject register(StudySubject studySubject) {
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		studySubjects=findRegistrations(studySubject);
		if (studySubjects.size() > 1) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
        }
		// check for the validity of the demographics snap shot and update participant if invalid
		if (!studySubject.getStudySubjectDemographics().getValid()){
			takeSnapshotAndValidateStudySubjectDemographics(studySubject);
		}
		studySubject.register();
		return save(studySubject);
	}

	public StudySubject register(Identifier studySubjectIdentifier) {
		StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
		return register(studySubject);
	}

	public StudySubject transferSubject(Identifier studySubjectIdentifier) {
		StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
		
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.ON_EPOCH) {
			studySubject.prepareForTransfer();
		}
		
		if (!studySubject.getStudySite().getHostedMode() && !studySubject.getStudySite().getIsCoordinatingCenter() && !studySubject.getStudySite().getStudy().isCoOrdinatingCenter(studySubjectService.getLocalNCIInstituteCode())){
			List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
            domainObjects.add(studySubject);
            EndPoint endPoint=handleCoordinatingCenterBroadcast(studySubject.getStudySite().getStudy(), APIName.CHANGE_EPOCH, domainObjects);
            if(endPoint.getStatus()!=WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                throw this.exceptionHelper.getMultisiteException(endPoint.getLastAttemptError());
            }
            StudySubject multisiteReturnedStudySubject=(StudySubject)((List) endPoint.getReturnValue()).get(0);
            studySubjectDao.initialize(multisiteReturnedStudySubject);
			//StudySubject multisiteReturnedStudySubject = studySubjectServiceImpl.getArmAndCoordinatingAssignedIdentifier(studySubject);
            studySubject.doMutiSiteTransfer(multisiteReturnedStudySubject.getScheduledEpoch());
		}else{
			studySubject.doLocalTransfer();
		}
		this.saveStratumGroup(studySubject);
		this.updateEpoch(studySubject);
		return save(studySubject);
	}
	
	public StudySubject transferSubject(StudySubject studySubject) {
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		studySubjects=findRegistrations(studySubject);
		if (studySubjects.size() > 1) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
        }
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.ON_EPOCH) {
			studySubject.prepareForTransfer();
		}
		
		if (!studySubject.getStudySite().getHostedMode() && !studySubject.getStudySite().getIsCoordinatingCenter() && !studySubject.getStudySite().getStudy().isCoOrdinatingCenter(studySubjectService.getLocalNCIInstituteCode())){
			List<AbstractMutableDomainObject> domainObjects = new ArrayList<AbstractMutableDomainObject>();
            domainObjects.add(studySubject);
            EndPoint endPoint=handleCoordinatingCenterBroadcast(studySubject.getStudySite().getStudy(), APIName.CHANGE_EPOCH, domainObjects);
            if(endPoint.getStatus()!=WorkFlowStatusType.MESSAGE_SEND_CONFIRMED){
                throw this.exceptionHelper.getMultisiteException(endPoint.getLastAttemptError());
            }
            StudySubject multisiteReturnedStudySubject=(StudySubject)((List) endPoint.getReturnValue()).get(0);
            studySubjectDao.initialize(multisiteReturnedStudySubject);
			//StudySubject multisiteReturnedStudySubject = studySubjectServiceImpl.getArmAndCoordinatingAssignedIdentifier(studySubject);
            studySubject.doMutiSiteTransfer(multisiteReturnedStudySubject.getScheduledEpoch());
		}else{
			studySubject.doLocalTransfer();
		}
		this.saveStratumGroup(studySubject);
		this.updateEpoch(studySubject);
		return save(studySubject);
	}
	
	public StudySubject create(StudySubject studySubject) {
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		studySubjects=findRegistrations(studySubject);
		if (studySubjects.size() > 0) {
            throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE_STUDYSUBJECTS_FOUND.CODE"));
        }
		
		if (!studySubject.hasC3PRSystemIdentifier()){
			studySubject.addIdentifier(identifierGenerator.generateSystemAssignedIdentifier(studySubject));
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
		// take a snapshot if not already taken (i.e not valid) and set the demographics record to valid
		if (!studySubject.getStudySubjectDemographics().getValid()){
			takeSnapshotAndValidateStudySubjectDemographics(studySubject);
		}
		return studySubject;
	}

	public StudySubject reserve(Identifier studySubjectIdentifier) {
		StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
		return reserve(studySubject);
	}
	
	 public StudySubject getUniqueStudySubject(Identifier studySubjectIdentifier) {
		 List<Identifier> studySubjectIdentifiers = new ArrayList<Identifier>();
		 studySubjectIdentifiers.add(studySubjectIdentifier);
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
	
	public void saveStratumGroup(StudySubject studySubject){
		if(studySubject.getScheduledEpoch().getEpoch().getRandomizedIndicator() && studySubject.getScheduledEpoch().getEpoch().getStratificationIndicator() && studySubject.getStudySite().getStudy().getRandomizationType()==RandomizationType.BOOK){
			try {
				stratumGroupDao.merge(studySubject.getScheduledEpoch().getStratumGroup());
			} catch (C3PRBaseException e) {
				e.printStackTrace();
				throw new C3PRBaseRuntimeException(e.getMessage());
			}
		} 
		for(StudySubject childStudySubject: studySubject.getChildStudySubjects()){
			if(childStudySubject.getScheduledEpoch().getEpoch().getRandomizedIndicator() && childStudySubject.getScheduledEpoch().getEpoch().getStratificationIndicator() && childStudySubject.getStudySite().getStudy().getRandomizationType()==RandomizationType.BOOK){
				try {
					stratumGroupDao.merge(childStudySubject.getScheduledEpoch().getStratumGroup());
				} catch (C3PRBaseException e) {
					e.printStackTrace();
					throw new C3PRBaseRuntimeException(e.getMessage());
				}
			}
		}
	}
	
	public void updateEpoch(StudySubject studySubject){
		if(studySubject.getScheduledEpoch().getEpoch().getRandomizedIndicator() && !studySubject.getScheduledEpoch().getEpoch().getStratificationIndicator() && studySubject.getStudySite().getStudy().getRandomizationType()==RandomizationType.BOOK){
			this.epochDao.merge(studySubject.getScheduledEpoch().getEpoch());
		}
		
	}

	public void setIdentifierGenerator(IdentifierGenerator identifierGenerator) {
		this.identifierGenerator = identifierGenerator;
	}

	public IdentifierGenerator getIdentifierGenerator() {
		return identifierGenerator;
	}

	public StudySubject invalidateRegistration(StudySubject studySubject) {
		studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.INVALID);
		return studySubjectDao.merge(studySubject);
	}
	
	public void takeSnapshotAndValidateStudySubjectDemographics(StudySubject studySubject){
		// set current studySubjectDemographics retiredIndicator to true;
		studySubject.getStudySubjectDemographics().setRetiredIndicatorAsTrue();
		Participant masterSubject = studySubject.getStudySubjectDemographics().getMasterSubject();
		StudySubjectDemographics snapShot = masterSubject.createStudySubjectDemographics();
		snapShot.setValid(true);
		studySubject.setStudySubjectDemographics(snapShot);
		
	}

	public StudySubject allowEligibilityWaiver(
			Identifier studySubjectIdentifier, List<EligibilityCriteria> eligibilityCrieteria, String waivedByPersonnelAssignedIdentifier) {
		StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
		PersonUser waivedBy = personUserDao.getByAssignedIdentifierFromLocal(waivedByPersonnelAssignedIdentifier);
		if(waivedBy == null){
			throw new C3PRBaseRuntimeException("Cannot allow waiver. Null or unassociated study personnel.");
		}
		studySubject.allowEligibilityWaiver(eligibilityCrieteria, waivedBy);
		return studySubjectDao.merge(studySubject);
	}

	public StudySubject waiveEligibility(
			Identifier studySubjectIdentifier, List<SubjectEligibilityAnswer> subjectEligibilityAnswers) {
		StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
		studySubject.waiveEligibility(subjectEligibilityAnswers);
		return studySubjectDao.merge(studySubject);
	}

	public StudySubject failScreening(Identifier studySubjectIdentifier,
			List<OffEpochReason> offScreeningReasons, Date failScreeningDate) {
		StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
		for(OffEpochReason offEpochReason : offScreeningReasons){
			if(offEpochReason.getReason().getId() != null){
				offEpochReason.setReason(reasonDao.getById(offEpochReason.getReason().getId()));
			}else{
				offEpochReason.setReason(reasonDao.getReasonByCode(offEpochReason.getReason().getCode()));
			}
		}
		studySubject.failScreening(offScreeningReasons, failScreeningDate);
		return studySubjectDao.merge(studySubject);
	}

	public StudySubject takeSubjectOffStudy(Identifier studySubjectIdentifier,
			List<OffEpochReason> offStudyReasons, Date offStudyDate) {
		StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
		for(OffEpochReason offEpochReason : offStudyReasons){
			if(offEpochReason.getReason().getId() != null){
				offEpochReason.setReason(reasonDao.getById(offEpochReason.getReason().getId()));
			}else{
				offEpochReason.setReason(reasonDao.getReasonByCode(offEpochReason.getReason().getCode()));
			}
		}
		studySubject.takeSubjectOffStudy(offStudyReasons,offStudyDate);
		return studySubjectDao.merge(studySubject);
	}
	
	public StudySubject takeSubjectOffCurrentEpoch(Identifier studySubjectIdentifier, List<OffEpochReason> offEpochReasons, Date offEpochDate) {
		StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
		for(OffEpochReason offEpochReason : offEpochReasons){
			if(offEpochReason.getReason().getId() != null){
				offEpochReason.setReason(reasonDao.getById(offEpochReason.getReason().getId()));
			}else{
				offEpochReason.setReason(reasonDao.getReasonByCode(offEpochReason.getReason().getCode()));
			}
		}
		studySubject.takeSubjectOffCurrentEpoch(offEpochReasons, offEpochDate);
		return studySubjectDao.merge(studySubject);
	}

	public void setReasonDao(ReasonDao reasonDao) {
		this.reasonDao = reasonDao;
	}

	public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}

	public StudySubject reConsent(String studyVersionName,
			List<StudySubjectConsentVersion> studySubjectConsentVersionsHolder,
			Identifier studySubjectIdentifier) {
		
		StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
		studySubject.reConsent(studyVersionName, studySubjectConsentVersionsHolder);
		return studySubjectDao.merge(studySubject);
	}

	public StudySubject discontinueEnrollment(
			Identifier studySubjectIdentifier,
			List<OffEpochReason> discontinueEpochReasons, Date discontinueEpochDate) {
			StudySubject studySubject = getUniqueStudySubject(studySubjectIdentifier);
			for(OffEpochReason offEpochReason : discontinueEpochReasons){
				if(offEpochReason.getReason().getId() != null){
					offEpochReason.setReason(reasonDao.getById(offEpochReason.getReason().getId()));
				}else{
					offEpochReason.setReason(reasonDao.getReasonByCode(offEpochReason.getReason().getCode()));
				}
			}
			studySubject.discontinueEnrollment(discontinueEpochReasons, discontinueEpochDate);
			return studySubjectDao.merge(studySubject);
	}
}

package edu.duke.cabig.c3pr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StratumGroupDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.NonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.StudyTargetAccrualNotificationEmail;

/**
 * @author Kruttik
 * @version 1.0
 *
 */
public class StudySubjectServiceImpl extends CCTSWorkflowServiceImpl
        implements StudySubjectService {

    private static final Logger logger = Logger.getLogger(StudySubjectServiceImpl.class);
    private StudySubjectDao studySubjectDao;
    private ParticipantDao participantDao;
    private EpochDao epochDao;
    private boolean hostedMode = true;
    private StratumGroupDao stratumGroupDao;
    private final String identifierTypeValueStr = "Coordinating Center Identifier";
    private final String prtIdentifierTypeValueStr = "MRN";
    private StudyService studyService;
    private ParticipantService participantService;
    private StudyTargetAccrualNotificationEmail notificationEmailer;
    
	public void setNotificationEmailer(
			StudyTargetAccrualNotificationEmail studyTargetAccrualNotificationEmail) {
		this.notificationEmailer = studyTargetAccrualNotificationEmail;
	}

	public StratumGroupDao getStratumGroupDao() {
		return stratumGroupDao;
	}

	public void setStratumGroupDao(StratumGroupDao stratumGroupDao) {
		this.stratumGroupDao = stratumGroupDao;
	}

	public String getIsBroadcastEnable() {
		return getConfiguration().get(Configuration.ESB_ENABLE);
	}

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	@Transactional
	public StudySubject createRegistration(StudySubject studySubject) throws C3PRCodedException {
        studySubject.setRegDataEntryStatus(evaluateRegistrationDataEntryStatus(studySubject));
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(evaluateScheduledEpochDataEntryStatus(studySubject));
        //evaluate status
        if (isCreatable(studySubject)) {
            manageSchEpochWorkFlow(studySubject, false, false, false);
            manageRegWorkFlow(studySubject);
        }
        studySubject = studySubjectDao.merge(studySubject);
        return studySubject;
    }

    @Transactional
    public StudySubject registerSubject(StudySubject studySubject) throws C3PRCodedException {
        studySubject.setRegDataEntryStatus(evaluateRegistrationDataEntryStatus(studySubject));
        studySubject.getScheduledEpoch().setScEpochDataEntryStatus(evaluateScheduledEpochDataEntryStatus(studySubject));
        manageSchEpochWorkFlow(studySubject, true, true, false);
        manageRegWorkFlow(studySubject);
        return studySubjectDao.merge(studySubject);
    }

    @Transactional
    public StudySubject processAffliateSiteRegistrationRequest(StudySubject studySubject) throws C3PRCodedException {
        if (studySubject.getParticipant().getId() != null) {
            StudySubject exampleSS = new StudySubject(true);
            exampleSS.setParticipant(studySubject.getParticipant());
            exampleSS.setStudySite(studySubject.getStudySite());
            List<StudySubject> registrations = studySubjectDao.searchBySubjectAndStudySite(exampleSS);
            if (registrations.size() > 0) {
                throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.STUDYSUBJECTS_ALREADY_EXISTS.CODE"));
            }
        } else {
            participantDao.save(studySubject.getParticipant());
        }
		studySubject.setRegDataEntryStatus(evaluateRegistrationDataEntryStatus(studySubject));
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(evaluateScheduledEpochDataEntryStatus(studySubject));
		if(studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.INCOMPLETE){
			throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.DATA_ENTRY_INCOMPLETE.CODE"));
		}
		if(studySubject.getScheduledEpoch().getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.INCOMPLETE){
			throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.SCHEDULEDEPOCH.DATA_ENTRY_INCOMPLETE.CODE"));
		}
		manageSchEpochWorkFlow(studySubject,true, true, true);
		manageRegWorkFlow(studySubject);
		return studySubjectDao.merge(studySubject);
	}
	
	private StudySubject doRandomization(StudySubject studySubject)throws C3PRBaseException{
		//randomize subject
		switch(studySubject.getStudySite().getStudy().getRandomizationType()){
		case PHONE_CALL: break;
		case BOOK:  doBookRandomization(studySubject);
					break;
		case CALL_OUT:	break;
		default: break;
		}
		return studySubject;
	}
	
	private StudySubject doBookRandomization(StudySubject studySubject) throws C3PRBaseException{
		ScheduledArm sa = new ScheduledArm();
		ScheduledTreatmentEpoch ste = (ScheduledTreatmentEpoch)studySubject.getScheduledEpoch();
		sa.setArm(studySubject.getStratumGroup().getNextArm());
		if(sa.getArm()!=null){
			ste.addScheduledArm(sa);
			stratumGroupDao.merge(studySubject.getStratumGroup());
		}
		return studySubject;
	}
	public RegistrationDataEntryStatus evaluateRegistrationDataEntryStatus(StudySubject studySubject){
		if(studySubject.getInformedConsentSignedDateStr().equals(""))
			return RegistrationDataEntryStatus.INCOMPLETE;
		if(StringUtils.getBlankIfNull(studySubject.getInformedConsentVersion()).equals(""))
			return RegistrationDataEntryStatus.INCOMPLETE;
		return RegistrationDataEntryStatus.COMPLETE;
	}
	
	public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(StudySubject studySubject){
		if(!studySubject.getIfTreatmentScheduledEpoch())
			return ScheduledEpochDataEntryStatus.COMPLETE;
		ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) studySubject.getScheduledEpoch();
		if(!evaluateStratificationIndicator(studySubject)){
			return ScheduledEpochDataEntryStatus.INCOMPLETE;
		}
		if(!scheduledTreatmentEpoch.getEligibilityIndicator()){
			return ScheduledEpochDataEntryStatus.INCOMPLETE;
		}
		if(scheduledTreatmentEpoch.getRequiresArm()
			&& !scheduledTreatmentEpoch.getRequiresRandomization()
			&& (scheduledTreatmentEpoch.getScheduledArm()==null || scheduledTreatmentEpoch.getScheduledArm().getArm()==null)){
			return ScheduledEpochDataEntryStatus.INCOMPLETE;
		}
			
		return ScheduledEpochDataEntryStatus.COMPLETE;
	}
	
	private void manageSchEpochWorkFlow(StudySubject studySubject, boolean triggerMultisite, boolean randomize, boolean affiliateSiteRequest) throws C3PRCodedException{
		if(studySubject.getScheduledEpoch().getScEpochWorkflowStatus()!=ScheduledEpochWorkFlowStatus.UNAPPROVED){
			return;
		}
		ScheduledEpoch scheduledEpoch=studySubject.getScheduledEpoch();
		if(scheduledEpoch.getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.COMPLETE &&
				studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.COMPLETE){
			if(this.requiresCoordinatingCenterApproval(studySubject) && !isLocalSiteCoOrdinatingCenterForStudy(studySubject)){
				//broadcase message to co-ordinating center
				try {
					if(triggerMultisite){
						if(studySubject.getId()!=null){
							Integer id=studySubjectDao.merge(studySubject).getId();
							studySubject=studySubjectDao.getById(id);
						}else{
							studySubjectDao.save(studySubject);
						}
						sendRegistrationRequest(studySubject);
					}
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
				} catch (Exception e) {
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.DISAPPROVED);
					throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.ERROR_SEND_REGISTRATION.CODE"),e);
				}
			}else{
				if(studySubject.getScheduledEpoch().getRequiresRandomization()){
					if(randomize){
						try {
							doRandomization(studySubject);
						} catch (Exception e) {
							throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.RANDOMIZATION.CODE"),e);
						}
						if(((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getScheduledArm()==null){
							scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
							if(affiliateSiteRequest && studySubject.getStudySite().getStudy().getRandomizationType()!=RandomizationType.PHONE_CALL){
								throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.CANNOT_ASSIGN_ARM.CODE"));
							}
						}else{
							//logic for accrual ceiling check
							scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
						}
					}else{
						scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
					}
				}else{
					//logic for accrual ceiling check
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
				}
			}
		}else{
			scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
		}
	}
	
	public void manageRegWorkFlow(StudySubject studySubject)throws C3PRCodedException{
		if(studySubject.getRegWorkflowStatus()==RegistrationWorkFlowStatus.REGISTERED){
			return;
		}
		ScheduledEpoch scheduledEpoch=studySubject.getScheduledEpoch();	
		if(studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.COMPLETE){
			if(scheduledEpoch.getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.DISAPPROVED){
				studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.DISAPPROVED);
			}else if(scheduledEpoch.getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.PENDING){
				studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING);
			}else if(scheduledEpoch.getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.APPROVED){
	/*				//logic for accrual ceiling at study level
				if(isAccrualCeilingReached()){
					studySubject.setRegistrationWorkFlowStatus(RegistrationWorkFlowStatus.DISAPPROVED);
				}else{
					// continue Here
				}
	*/			if(scheduledEpoch.isReserving()){
					studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
				}else if(scheduledEpoch.getEpoch().isEnrolling()){
					studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED);
					if(studySubject.getId()!=null){
						Integer id=studySubjectDao.merge(studySubject).getId();
						studySubject=studySubjectDao.getById(id);
					}else{
						studySubjectDao.save(studySubject);
						this.notificationEmailer.sendEmail(studySubject);
					}
					try {
						broadcastMessage(studySubject);
					} catch (C3PRCodedException e) {
						e.printStackTrace();
						studySubject.setCctsWorkflowStatus(CCTSWorkflowStatusType.MESSAGE_SEND_FAILED);
					}
				}else{
					studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.UNREGISTERED);
				}
			}else{
				studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.UNREGISTERED);
			}
		}else{
			studySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.UNREGISTERED);
		}
	}

	private static boolean evaluateStratificationIndicator(StudySubject studySubject){
		if(studySubject.getStratumGroupNumber()!=null)
			return true;
		ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)studySubject.getScheduledEpoch();
		List<SubjectStratificationAnswer> answers=scheduledTreatmentEpoch.getSubjectStratificationAnswers();
		for(SubjectStratificationAnswer subjectStratificationAnswer:answers){
			if(subjectStratificationAnswer.getStratificationCriterionAnswer()==null){
				return false;
			}
		}
		return true;
	}
	
	public void sendRegistrationRequest(StudySubject studySubject) throws C3PRCodedException{
		//TODO send registration request to Co ordinating center
	}
	
	public boolean canRandomize(StudySubject studySubject){
		return studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.COMPLETE && studySubject.getScheduledEpoch().getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.COMPLETE;
	}

	public boolean isRegisterable(StudySubject studySubject){
		if(!this.requiresCoordinatingCenterApproval(studySubject)
				&& studySubject.getRegWorkflowStatus()!=RegistrationWorkFlowStatus.REGISTERED
				&& isCreatable(studySubject)){
			return true;
		}
		return false;
	}

	public boolean requiresCoordinatingCenterApproval(StudySubject studySubject){
		return studySubject.getStudySite().getStudy().getMultiInstitutionIndicator()
				&& !isHostedMode()
				&& studySubject.getScheduledEpoch().getEpoch().isEnrolling();
	}
	
	private boolean isLocalSiteCoOrdinatingCenterForStudy(StudySubject studySubject){
		return studySubject.getStudySite().getStudy().getStudyCoordinatingCenters().get(0).getHealthcareSite().getNciInstituteCode().equals(getLocalInstanceNCICode());
	}

	private boolean isLocalinstanceStudySite(StudySubject studySubject){
		return studySubject.getStudySite().getHealthcareSite().getNciInstituteCode().equals(getLocalInstanceNCICode());
	}

	private boolean isCreatable(StudySubject studySubject){
		if(studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.COMPLETE
			&& studySubject.getScheduledEpoch().getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.COMPLETE){
			return true;
		}
		return false;
	}
	
	public void manageSchEpochWorkFlow(StudySubject studySubject) throws C3PRCodedException{
		manageSchEpochWorkFlow(studySubject, true, true, false);
	}

	public boolean isHostedMode() {
		return hostedMode;
	}

	public void setHostedMode(boolean hostedMode) {
		this.hostedMode = hostedMode;
	}

   @Transactional
    public void assignC3DIdentifier(StudySubject studySubject, String c3dIdentifierValue) {
        StudySubject loadedSubject = studySubjectDao.getByGridId(studySubject.getGridId());
        loadedSubject.setC3DIdentifier(c3dIdentifierValue);
		studySubjectDao.save(loadedSubject);
	}

	public void assignCoOrdinatingCenterIdentifier(String studySubjectGridId, String identifierValue) {
		StudySubject studySubject=studySubjectDao.getByGridId(studySubjectGridId);
		studySubject.setCoOrdinatingCenterIdentifier(identifierValue);
		studySubjectDao.save(studySubject);
	}

	public boolean isEpochAccrualCeilingReached(int epochId) {
		// TODO Auto-generated method stub
		Epoch epoch=epochDao.getById(epochId);
		if (epoch.isReserving()) {
			ScheduledEpoch scheduledEpoch=new ScheduledNonTreatmentEpoch(true);
			scheduledEpoch.setEpoch(epoch);
			List<StudySubject> list=studySubjectDao.searchByScheduledEpoch(scheduledEpoch);
			NonTreatmentEpoch nEpoch=(NonTreatmentEpoch)epoch;
			if(nEpoch.getAccrualCeiling()!=null && list.size()>=nEpoch.getAccrualCeiling().intValue()){
				return true;
			}
		}
		return false;
	}

	public EpochDao getEpochDao() {
		return epochDao;
	}

	public void setEpochDao(EpochDao epochDao) {
		this.epochDao = epochDao;
	}

	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}

	/*public void processMessage(String message) {
		StudySubject cctsStudySubject=new StudySubject(true);
		StringWriter sw=new StringWriter();
		sw.write(message);
		try {
			this.xmlUtility.toXML(cctsStudySubject, sw);
		} catch (XMLUtilityException e) {
			e.printStackTrace();
			return;
		}
		StudySubject studySubject=new StudySubject(true);
		try {
			studySubject.setStudySite(getPersistedStudySite(cctsStudySubject));
			studySubject.setParticipant(getPersistedParticipant(cctsStudySubject));
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			return;
		}
		List<StudySubject> list=studySubjectDao.searchBySubjectAndStudySite(studySubject);
		if(list.size()>1)
			throw new RuntimeException("Error processing esb message. More than one registration found.");
		if(list.size()==0)
			throw new RuntimeException("Error processing esb message. No registration found.");
		studySubject=list.get(0);
		if(isC3DResponse(cctsStudySubject)){
			for(Identifier identifier:cctsStudySubject.getIdentifiers()){
				if (identifier instanceof SystemAssignedIdentifier) {
					SystemAssignedIdentifier sId = (SystemAssignedIdentifier) identifier;
					if(sId.getSystemName().equalsIgnoreCase("C3D") && sId.getType().equals("Patient Position")){
						assignC3DIdentifier(studySubject, sId.getValue());
					}
				}
			}
		}
	}
	
	private boolean isC3DResponse(StudySubject studySubject){
		for(Identifier identifier:studySubject.getIdentifiers()){
			if (identifier instanceof SystemAssignedIdentifier) {
				SystemAssignedIdentifier sId = (SystemAssignedIdentifier) identifier;
				if(sId.getSystemName().equalsIgnoreCase("C3D") && sId.getType().equals("Patient Position"))
					return true;
			}
		}
		return false;
	}

	private StudySite getPersistedStudySite(StudySubject studySubject) throws C3PRCodedException{
		for (OrganizationAssignedIdentifier identifierType : studySubject.getOrganizationAssignedIdentifiers()) {
			if (identifierType.getType().equals(this.identifierTypeValueStr)) {
				HealthcareSite healthcareSite = this.healthcareSiteDao.getByNciInstituteCode(identifierType.getHealthcareSite().getNciInstituteCode());
				if (healthcareSite == null) {
					throw getExceptionHelper().getException(getCode("MULTISITE.EXCEPTION.NOTFOUND.HEALTHCARESITE_STUDY_CO_IDENTIFIER.CODE")
							,new String[]{identifierType.getHealthcareSite().getNciInstituteCode()});
				}
				identifierType.setHealthcareSite(healthcareSite);
				Study example=new Study(true);
				List<Study> studies = studyDao.searchByExample(example, true);
				if (studies.size() == 0) {
					throw getExceptionHelper().getException(getCode("MULTISITE.EXCEPTION.NOTFOUND.STUDY_WITH_IDENTIFIER.CODE")
							,new String[]{identifierType.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
				}
				if (studies.size() > 1) {
					throw getExceptionHelper().getException(getCode("MULTISITE.EXCEPTION.MULTIPLE.STUDY_SAME_CO_IDENTIFIER.CODE")
							,new String[]{identifierType.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
				}
				Study study = studies.get(0);
				for (StudySite temp : study.getStudySites()) {
					if (temp.getHealthcareSite().getNciInstituteCode().equals(studySubject.getStudySite().getHealthcareSite().getNciInstituteCode())) {
						return temp;
					}
				}
			}
		}
		throw getExceptionHelper().getException(-1);
		
	}
	private Participant getPersistedParticipant(StudySubject studySubject) throws C3PRCodedException{
		for (OrganizationAssignedIdentifier identifierType : studySubject.getParticipant().getOrganizationAssignedIdentifiers()) {
			if (identifierType.getType().equals(this.prtIdentifierTypeValueStr)) {
				HealthcareSite healthcareSite = this.healthcareSiteDao.getByNciInstituteCode(identifierType.getHealthcareSite().getNciInstituteCode());
				if (healthcareSite == null) {
					throw getExceptionHelper().getException(getCode("MULTISITE.EXCEPTION.INVALID.HEALTHCARESITE_SUBJECT_IDENTIFIER.CODE")
							,new String[]{healthcareSite.getNciInstituteCode(), this.prtIdentifierTypeValueStr});
				}
				identifierType.setHealthcareSite(healthcareSite);
				Participant temp=new Participant();
				temp.addIdentifier(identifierType);
				List<Participant> paList = participantDao.searchByExample(temp, true);
				if (paList.size() > 1) {
					throw getExceptionHelper().getException(getCode("MULTISITE.EXCEPTION.MULTIPLE.SUBJECTS_SAME_MRN.CODE")
							,new String[]{identifierType.getValue()});
				}
				if (paList.size() == 1) {
					System.out.println("Participant with the same MRN found in the database");
					return temp;
				}
			}
		}
		throw getExceptionHelper().getException(-1);
	}
	*/
	public StudySubject buildStudySubject(StudySubject deserializedStudySubject) throws C3PRCodedException{
		StudySubject built=new StudySubject();
		Participant participant=buildParticipant(deserializedStudySubject.getParticipant());
		StudySite studySite=buildStudySite(deserializedStudySubject.getStudySite(),buildStudy(deserializedStudySubject.getStudySite().getStudy()));
        built.setStudySite(studySite);
        built.setParticipant(participant);
        Epoch epoch = buildEpoch(studySite.getStudy().getEpochs(), deserializedStudySubject.getScheduledEpoch());
        ScheduledEpoch scheduledEpoch=buildScheduledEpoch(deserializedStudySubject.getScheduledEpoch(), epoch);
        built.getScheduledEpochs().add(0, scheduledEpoch);
        fillStudySubjectDetails(built, deserializedStudySubject);
        return built;
	}

	private Participant buildParticipant(Participant participant)throws C3PRCodedException{
		if (participant.getIdentifiers()==null || participant.getIdentifiers().size() == 0) {
            throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.MISSING.SUBJECT_IDENTIFIER.CODE"));
        }
        for (OrganizationAssignedIdentifier organizationAssignedIdentifier: participant.getOrganizationAssignedIdentifiers()) {
            if (organizationAssignedIdentifier.getType().equals(this.prtIdentifierTypeValueStr)) {
                List<Participant> paList = participantService.searchByMRN(organizationAssignedIdentifier);
                if (paList.size() > 1) {
                    throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE.SUBJECTS_SAME_MRN.CODE")
                            ,new String[]{organizationAssignedIdentifier.getValue()});
                }else if (paList.size() == 1) {
                    System.out.println("Participant with the same MRN found in the database");
                    Participant temp = paList.get(0);
                    if (temp.getFirstName().equals(participant.getFirstName())
                            && temp.getLastName().equals(participant.getLastName())
                            && temp.getBirthDate().getTime()==participant.getBirthDate().getTime()) {
                        return temp;
                    }else{
                        throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.INVALID.ANOTHER_SUBJECT_SAME_MRN.CODE")
                                ,new String[]{organizationAssignedIdentifier.getValue()});
                    }
                }
            }
        }
        return participant;
	}
	
	private Study buildStudy(Study study) throws C3PRCodedException{
        if(study.getIdentifiers()==null || study.getIdentifiers().size()==0){
            throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.MISSING.STUDY_IDENTIFIER.CODE"));
        }
        List<Study> studies = null;
        OrganizationAssignedIdentifier identifier=null;
        for (OrganizationAssignedIdentifier organizationAssignedIdentifier: study.getOrganizationAssignedIdentifiers()) {
            if (organizationAssignedIdentifier.getType().equals(this.identifierTypeValueStr)) {
            	identifier=organizationAssignedIdentifier;
            	studies=studyService.searchByCoOrdinatingCenterId(organizationAssignedIdentifier);
                break;
            }
        }
        if(identifier==null){
        	throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.MISSING.STUDY_COORDINATING_IDENTIFIER.CODE"));
        }
        if(studies==null){
            throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.STUDY_WITH_IDENTIFIER.CODE")
            		,new String[]{identifier.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
        }
        if (studies.size() == 0) {
            throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.STUDY_WITH_IDENTIFIER.CODE")
            		,new String[]{identifier.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
        }
        if (studies.size() > 1) {
            throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.MULTIPLE.STUDY_SAME_CO_IDENTIFIER.CODE")
                    ,new String[]{identifier.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
        }
        if(studies.get(0).getCoordinatingCenterStudyStatus()!=CoordinatingCenterStudyStatus.ACTIVE){
        	throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.STUDY_NOT_ACTIVE")
                    ,new String[]{identifier.getHealthcareSite().getNciInstituteCode(),identifier.getValue()});
        }
        return studies.get(0);
	}

	private StudySite buildStudySite(StudySite studySite, Study study) throws C3PRCodedException{
		for (StudySite temp : study.getStudySites()) {
            if (temp.getHealthcareSite().getNciInstituteCode().equals(studySite.getHealthcareSite().getNciInstituteCode())) {
            	if(temp.getSiteStudyStatus()!=SiteStudyStatus.ACTIVE){
            		throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.STUDYSITE_NOT_ACTIVE")
                            ,new String[]{temp.getHealthcareSite().getNciInstituteCode()});
            	}
                return temp;
            }
        }
        throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.STUDYSITE_WITH_NCICODE.CODE")
                ,new String[]{studySite.getHealthcareSite().getNciInstituteCode()});
	}
	
	private Epoch buildEpoch(List<Epoch> epochs,ScheduledEpoch scheduledEpoch)throws C3PRCodedException{
		for (Epoch epochCurr : epochs) {
            if (epochCurr.getName().equalsIgnoreCase(scheduledEpoch.getEpoch().getName())) {
                return epochCurr;
            }
        }
        throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.EPOCH_NAME.CODE")
                ,new String[]{scheduledEpoch.getEpoch().getName()});
	}
	
	private ScheduledEpoch buildScheduledEpoch(ScheduledEpoch source, Epoch epoch) throws C3PRCodedException{
		ScheduledEpoch scheduledEpoch=null;
        if (epoch instanceof TreatmentEpoch) {
            ScheduledTreatmentEpoch scheduledTreatmentEpochSource=(ScheduledTreatmentEpoch) source;
            scheduledEpoch=new ScheduledTreatmentEpoch();
            ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch) scheduledEpoch;
//            scheduledTreatmentEpoch.setEligibilityIndicator(scheduledTreatmentEpochSource.getEligibilityIndicator());
            scheduledTreatmentEpoch.setEligibilityIndicator(true);
            if(epoch.getRequiresArm()){
	            if(scheduledTreatmentEpochSource.getScheduledArm()!=null
	                    &&scheduledTreatmentEpochSource.getScheduledArm().getArm()!=null
	                    &&scheduledTreatmentEpochSource.getScheduledArm().getArm().getName()!=null){
	                Arm arm=null;
	                for(Arm a: ((TreatmentEpoch)epoch).getArms()){
	                    if(a.getName().equals(scheduledTreatmentEpochSource.getScheduledArm().getArm().getName())){
	                        arm=a;
	                    }
	                }
	                if(arm==null){
	                    throw getExceptionHelper().getException(getCode("C3PR.EXCEPTION.REGISTRATION.NOTFOUND.ARM_NAME.CODE")
	                            ,new String[]{scheduledTreatmentEpochSource.getScheduledArm().getArm().getName(),scheduledTreatmentEpochSource.getEpoch().getName()});
	                }
	                scheduledTreatmentEpoch.getScheduledArms().get(0).setArm(arm);
	
	            }
            }
        } else {
        	scheduledEpoch=new ScheduledNonTreatmentEpoch();
        }
        scheduledEpoch.setEpoch(epoch);
        scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.APPROVED);
        return scheduledEpoch;
	}
	private void fillStudySubjectDetails(StudySubject studySubject, StudySubject source) {
		studySubject.setInformedConsentSignedDate(source.getInformedConsentSignedDate());
		studySubject.setInformedConsentVersion(source.getInformedConsentVersion());
		studySubject.setStartDate(source.getStartDate());
		studySubject.setStratumGroupNumber(source.getStratumGroupNumber());
		studySubject.getIdentifiers().addAll(source.getIdentifiers());
	}
	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}

	public void setStudyService(StudyService studyService) {
		this.studyService = studyService;
	}

	public String getLocalInstanceNCICode(){
		return getConfiguration().get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
	}

}

package edu.duke.cabig.c3pr.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.dao.StratumGroupDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
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
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.ESBMessageConsumer;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * @author Kulasekaran, Ramakrishna
 * @version 1.0
 *
 */
public class StudySubjectServiceImpl implements StudySubjectService, ESBMessageConsumer{

	private static final Logger logger = Logger.getLogger(StudySubjectServiceImpl.class);
	private StudySubjectDao studySubjectDao;
	private ParticipantDao participantDao;
	private EpochDao epochDao;
	private String isBroadcastEnable="false";
	private boolean hostedMode=true;
	private MessageBroadcastServiceImpl messageBroadcaster;
	private StratumGroupDao stratumGroupDao;
	private XmlMarshaller registrationXmlUtility;
	private String localInstanceNCICode;
	private C3PRExceptionHelper exceptionHelper;
	private MessageSource c3prErrorMessages;
	private XmlMarshaller xmlUtility;
	private final String identifierTypeValueStr = "Coordinating Center Identifier";
	private final String prtIdentifierTypeValueStr = "MRN";
	private HealthcareSiteDao healthcareSiteDao;
	private StudyDao studyDao;
	
	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public void setXmlUtility(XmlMarshaller xmlUtility) {
		this.xmlUtility = xmlUtility;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}

	public void setLocalInstanceNCICode(String localInstanceNCICode) {
		this.localInstanceNCICode = localInstanceNCICode;
	}

	public XmlMarshaller getRegistrationXmlUtility() {
		return registrationXmlUtility;
	}

	public void setRegistrationXmlUtility(XmlMarshaller registrationXmlUtility) {
		this.registrationXmlUtility = registrationXmlUtility;
	}

	public StratumGroupDao getStratumGroupDao() {
		return stratumGroupDao;
	}

	public void setStratumGroupDao(StratumGroupDao stratumGroupDao) {
		this.stratumGroupDao = stratumGroupDao;
	}

	public MessageBroadcastServiceImpl getMessageBroadcaster() {
		return messageBroadcaster;
	}

	public void setMessageBroadcaster(
			MessageBroadcastServiceImpl messageBroadcaster) {
		this.messageBroadcaster = messageBroadcaster;
	}

	public String getIsBroadcastEnable() {
		return isBroadcastEnable;
	}

	public void setIsBroadcastEnable(String isBroadcastEnable) {
		this.isBroadcastEnable = isBroadcastEnable;
	}

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	@Transactional
	public StudySubject createRegistration(StudySubject studySubject) throws C3PRCodedException{
		studySubject.setRegDataEntryStatus(evaluateRegistrationDataEntryStatus(studySubject));
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(evaluateScheduledEpochDataEntryStatus(studySubject));
		//evaluate status
		if(isCreatable(studySubject)){
			manageSchEpochWorkFlow(studySubject, false, false, false);
			manageRegWorkFlow(studySubject);
		}
		studySubject=studySubjectDao.merge(studySubject);
		return studySubject;
	}
	
	@Transactional
	public StudySubject registerSubject(StudySubject studySubject) throws C3PRCodedException{
		studySubject.setRegDataEntryStatus(evaluateRegistrationDataEntryStatus(studySubject));
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(evaluateScheduledEpochDataEntryStatus(studySubject));
		manageSchEpochWorkFlow(studySubject,true, true, false);
		manageRegWorkFlow(studySubject);
		return studySubjectDao.merge(studySubject);
	}
	
	@Transactional
	public StudySubject processAffliateSiteRegistrationRequest(StudySubject studySubject) throws Exception {
		if(studySubject.getParticipant().getId()==null){
			participantDao.save(studySubject.getParticipant());
		}
		studySubject.setRegDataEntryStatus(evaluateRegistrationDataEntryStatus(studySubject));
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(evaluateScheduledEpochDataEntryStatus(studySubject));
		if(studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.INCOMPLETE){
			throw new Exception("Registration data entry status evalutes to incomplete");
		}
		if(studySubject.getScheduledEpoch().getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.INCOMPLETE){
			throw new Exception("Scheduled epoch data entry status evalutes to incomplete");
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
						Integer id=studySubjectDao.merge(studySubject).getId();
						studySubject=studySubjectDao.getById(id);
						sendRegistrationRequest(studySubject);
					}
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
				} catch (Exception e) {
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.DISAPPROVED);
					throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.ERROR_SEND_REGISTRATION.CODE"),e);
				}
			}else{
				if(studySubject.getScheduledEpoch().getRequiresRandomization()){
					if(randomize){
						try {
							doRandomization(studySubject);
						} catch (Exception e) {
							throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.RANDOMIZATION.CODE"),e);
						}
						if(((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getScheduledArm()==null){
							scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
							if(affiliateSiteRequest && studySubject.getStudySite().getStudy().getRandomizationType()!=RandomizationType.PHONE_CALL){
								throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.CANNOT_ASSIGN_ARM.CODE"));
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
					Integer id=studySubjectDao.merge(studySubject).getId();
					studySubject=studySubjectDao.getById(id);
					try {
						sendRegistrationEvent(studySubject);
					} catch (C3PRCodedException e) {
						if(e.getExceptionCode()!=227)
							throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.ERROR_SEND_REGISTRATION.CODE"),e);
						else
							e.printStackTrace();
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
	
	public void sendRegistrationEvent(StudySubject studySubject) throws C3PRCodedException{
		if(isBroadcastEnable.equalsIgnoreCase("true")){
			String xml = "";
			try {
				xml = registrationXmlUtility.toXML(studySubject);
			} catch (XMLUtilityException e) {
				e.printStackTrace();
				throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.BROADCAST.XML_ERROR"),e);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" - XML for Registration"); //$NON-NLS-1$
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" - " + xml); //$NON-NLS-1$
			}
			try {
				messageBroadcaster.initialize();
				messageBroadcaster.broadcast(xml);
			} catch (BroadcastException e) {
				e.printStackTrace();
				throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.BROADCAST.SEND_ERROR"),e);
			}
		}else{
			throw this.exceptionHelper.getException(getCode("C3PR.EXCEPTION.REGISTRATION.BROADCAST.DISABLED"));
		}
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
		return studySubject.getStudySite().getStudy().getStudyCoordinatingCenters().get(0).getHealthcareSite().getNciInstituteCode().equals(this.localInstanceNCICode);
	}

	private boolean isLocalinstanceStudySite(StudySubject studySubject){
		return studySubject.getStudySite().getHealthcareSite().getNciInstituteCode().equals(this.localInstanceNCICode);
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

	public void assignC3DIdentifier(StudySubject studySubject, String c3dIdentifierValue) {
		studySubject.setC3DIdentifier(c3dIdentifierValue);
		studySubjectDao.save(studySubject);
	}

	public void assignCoOrdinatingCenterIdentifier(String studySubjectGridId, String identifierValue) {
		StudySubject studySubject=studySubjectDao.getByGridId(studySubjectGridId);
		studySubject.setCoOrdinatingCenterIdentifier(identifierValue);
		studySubjectDao.merge(studySubject);
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

	private int getCode(String errortypeString){
		return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
	}

	public void processMessage(String message) {
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
					throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.NOTFOUND.HEALTHCARESITE_STUDY_CO_IDENTIFIER.CODE")
							,new String[]{identifierType.getHealthcareSite().getNciInstituteCode()});
				}
				identifierType.setHealthcareSite(healthcareSite);
				Study example=new Study(true);
				List<Study> studies = studyDao.searchByExample(example, true);
				if (studies.size() == 0) {
					throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.NOTFOUND.STUDY_WITH_IDENTIFIER.CODE")
							,new String[]{identifierType.getHealthcareSite().getNciInstituteCode(),this.identifierTypeValueStr});
				}
				if (studies.size() > 1) {
					throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.MULTIPLE.STUDY_SAME_CO_IDENTIFIER.CODE")
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
		throw this.exceptionHelper.getException(-1);
		
	}
	private Participant getPersistedParticipant(StudySubject studySubject) throws C3PRCodedException{
		for (OrganizationAssignedIdentifier identifierType : studySubject.getParticipant().getOrganizationAssignedIdentifiers()) {
			if (identifierType.getType().equals(this.prtIdentifierTypeValueStr)) {
				HealthcareSite healthcareSite = this.healthcareSiteDao.getByNciInstituteCode(identifierType.getHealthcareSite().getNciInstituteCode());
				if (healthcareSite == null) {
					throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.INVALID.HEALTHCARESITE_SUBJECT_IDENTIFIER.CODE")
							,new String[]{healthcareSite.getNciInstituteCode(), this.prtIdentifierTypeValueStr});
				}
				identifierType.setHealthcareSite(healthcareSite);
				Participant temp=new Participant();
				temp.addIdentifier(identifierType);
				List<Participant> paList = participantDao.searchByExample(temp, true);
				if (paList.size() > 1) {
					throw this.exceptionHelper.getException(getCode("MULTISITE.EXCEPTION.MULTIPLE.SUBJECTS_SAME_MRN.CODE")
							,new String[]{identifierType.getValue()});
				}
				if (paList.size() == 1) {
					System.out.println("Participant with the same MRN found in the database");
					return temp;
				}
			}
		}
		throw this.exceptionHelper.getException(-1);
	}

}

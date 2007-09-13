package edu.duke.cabig.c3pr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.StratumGroupDao;
import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;

/**
 * @author Kulasekaran, Ramakrishna
 * @version 1.0
 *
 */
public class StudySubjectServiceImpl implements StudySubjectService {

	private static final Logger logger = Logger.getLogger(StudySubjectServiceImpl.class);
	StudySubjectDao studySubjectDao;
	private String isBroadcastEnable="false";
	private MessageBroadcastServiceImpl messageBroadcaster;
	private StratumGroupDao stratumGroupDao;

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
	public StudySubject createRegistration(StudySubject studySubject) throws Exception{
		studySubject.setRegDataEntryStatus(evaluateRegistrationDataEntryStatus(studySubject));
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(evaluateScheduledEpochDataEntryStatus(studySubject));
		//evaluate status
		if(isCreatable(studySubject)){
			manageSchEpochWorkFlowIfUnApp(studySubject, false, false);
			manageRegWorkFlow(studySubject);
		}
		studySubject=studySubjectDao.merge(studySubject);
		return studySubject;
	}
	
	public StudySubject registerSubject(StudySubject studySubject) throws Exception{
		studySubject.setRegDataEntryStatus(evaluateRegistrationDataEntryStatus(studySubject));
		studySubject.getScheduledEpoch().setScEpochDataEntryStatus(evaluateScheduledEpochDataEntryStatus(studySubject));
		manageSchEpochWorkFlowIfUnApp(studySubject,true, true);
		manageRegWorkFlow(studySubject);
		return studySubjectDao.merge(studySubject);
	}
	
	private StudySubject doLocalRandomization(StudySubject studySubject)throws C3PRBaseException{
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
		if(!evaluateStratificationIndicator(scheduledTreatmentEpoch)){
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
	
	private void manageSchEpochWorkFlowIfUnApp(StudySubject studySubject, boolean triggerMultisite, boolean randomize) throws Exception{
		if(studySubject.getScheduledEpoch().getScEpochWorkflowStatus()!=ScheduledEpochWorkFlowStatus.UNAPPROVED){
			return;
		}
		ScheduledEpoch scheduledEpoch=studySubject.getScheduledEpoch();
		if(scheduledEpoch.getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.COMPLETE &&
				studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.COMPLETE){
			if(studySubject.getStudySite().getStudy().getMultiInstitutionIndicator().equalsIgnoreCase("true")
					&& studySubject.getScheduledEpoch().getEpoch().isEnrolling()){
				//broadcase message to co-ordinating center
				try {
					if(triggerMultisite){
						sendRegistrationRequest(studySubject);
					}
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.PENDING);
				} catch (Exception e) {
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.DISAPPROVED);
					throw e;
				}
			}else{
				if(studySubject.getScheduledEpoch().getRequiresRandomization()){
					if(randomize){
						doLocalRandomization(studySubject);
						if(((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getScheduledArm()==null){
							scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.UNAPPROVED);
							throw new C3PRBaseException("Unable to assign arm");
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
	
	public void manageRegWorkFlow(StudySubject studySubject)throws Exception{
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

	private static boolean evaluateStratificationIndicator(ScheduledTreatmentEpoch scheduledTreatmentEpoch){
		List<SubjectStratificationAnswer> answers=scheduledTreatmentEpoch.getSubjectStratificationAnswers();
		for(SubjectStratificationAnswer subjectStratificationAnswer:answers){
			if(subjectStratificationAnswer.getStratificationCriterionAnswer()==null){
				return false;
			}
		}
		return true;
	}
	
	private void sendRegistrationRequest(StudySubject studySubject) throws Exception{
		if(isBroadcastEnable.equalsIgnoreCase("true")){
			String xml = "";
			xml = XMLUtils.toXml(studySubject);
			if (logger.isDebugEnabled()) {
				logger.debug(" - XML for Registration"); //$NON-NLS-1$
			}
			if (logger.isDebugEnabled()) {
				logger.debug(" - " + xml); //$NON-NLS-1$
			}
			messageBroadcaster.initialize();
			messageBroadcaster.broadcast(xml);
		}
	}
	
	public boolean canRandomize(StudySubject studySubject){
		return studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.COMPLETE && studySubject.getScheduledEpoch().getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.COMPLETE;
	}

	public boolean isRegisterable(StudySubject studySubject){
		if(studySubject.getStudySite().getStudy().getMultiInstitutionIndicator().equalsIgnoreCase("false") 
				&& studySubject.getRegWorkflowStatus()!=RegistrationWorkFlowStatus.REGISTERED
				&& isCreatable(studySubject)){
			return true;
		}
		return false;
	}

	private boolean isCreatable(StudySubject studySubject){
		if(studySubject.getRegDataEntryStatus()==RegistrationDataEntryStatus.COMPLETE
			&& studySubject.getScheduledEpoch().getScEpochDataEntryStatus()==ScheduledEpochDataEntryStatus.COMPLETE){
			return true;
		}
		return false;
	}
	
	public void manageSchEpochWorkFlow(StudySubject studySubject) throws Exception{
		manageSchEpochWorkFlowIfUnApp(studySubject, true, true);
	}
}

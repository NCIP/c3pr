package edu.duke.cabig.c3pr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.dao.ParticipantDao;
import edu.duke.cabig.c3pr.domain.DiseaseHistory;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;
import edu.duke.cabig.c3pr.service.ParticipantService;
import edu.duke.cabig.c3pr.utils.XMLUtils;

/**
 * @author Kulasekaran, Ramakrishna
 * @version 1.0
 *
 */
public class ParticipantServiceImpl implements ParticipantService {

	private static final Logger logger = Logger.getLogger(ParticipantServiceImpl.class);
	private String isBroadcastEnable="true";
	private MessageBroadcastServiceImpl messageBroadcaster;

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

	ParticipantDao participantDao;
	
	public ParticipantDao getParticipantDao() {
		return participantDao;
	}


	public void setParticipantDao(ParticipantDao participantDao) {
		this.participantDao = participantDao;
	}
	
	/**
	 * Search using a sample. Populate a Participant object
	 * @param  Participant object
	 * @return List of Participant objects based on the sample participant object
	 * @throws Runtime exception 
	 */
	public List<Participant> search(Participant participant) throws Exception {		
		return participantDao.searchByExample(participant, true);
	}


	public void createRegistration(StudyParticipantAssignment studyParticipantAssignment) {
		if (logger.isDebugEnabled()) {
			logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - in process finish"); //$NON-NLS-1$
		}
		studyParticipantAssignment.getParticipant().getStudyParticipantAssignments().size();
		studyParticipantAssignment.getParticipant().addStudyParticipantAssignment(studyParticipantAssignment);
		studyParticipantAssignment.setRegistrationStatus(evaluateStatus(studyParticipantAssignment));
		if(!hasDiseaseHistory(studyParticipantAssignment.getDiseaseHistory())){
			studyParticipantAssignment.setDiseaseHistory(null);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - Calling participant service"); //$NON-NLS-1$
		}
		participantDao.save(studyParticipantAssignment.getParticipant());
		studyParticipantAssignment.setStudyParticipantIdentifier(studyParticipantAssignment.getId()+ "");
		if(isBroadcastEnable.equalsIgnoreCase("true")){
			String xml = "";
			try {
				xml = XMLUtils.toXml(studyParticipantAssignment);
				if (logger.isDebugEnabled()) {
					logger.debug(" - XML for Registration"); //$NON-NLS-1$
				}
				if (logger.isDebugEnabled()) {
					logger.debug(" - " + xml); //$NON-NLS-1$
				}
				messageBroadcaster.initialize();
				messageBroadcaster.broadcast(xml);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("", e); //$NON-NLS-1$
			}
		}
	}
	
	private String evaluateStatus(StudyParticipantAssignment studyParticipantAssignment){
		String status="Complete";
		if(studyParticipantAssignment.getInformedConsentSignedDateStr().equals("")){
			return "Incomplete";
		}else if(studyParticipantAssignment.getTreatingPhysician()==null){
			return "Incomplete";
		}else if(studyParticipantAssignment.getScheduledArms().get(studyParticipantAssignment.getScheduledArms().size()-1).getArm()==null){
			studyParticipantAssignment.getScheduledArms().remove(studyParticipantAssignment.getScheduledArms().size()-1);
			return "Incomplete";
		}else if(!evaluateStratificationIndicator(studyParticipantAssignment)){
			return "Incomplete";
		}else if(studyParticipantAssignment.getEligibilityIndicator()){
			List<SubjectEligibilityAnswer> criterias=studyParticipantAssignment.getSubjectEligibilityAnswers();
			if (logger.isDebugEnabled()) {
				logger.debug("evaluateStatus(StudyParticipantAssignment) - studyParticipantAssignment.getEligibilityIndicator():" + studyParticipantAssignment.getEligibilityIndicator()); //$NON-NLS-1$
			}
			studyParticipantAssignment.setEligibilityWaiverReasonText("");
			if (logger.isDebugEnabled()) {
				logger.debug("evaluateStatus(StudyParticipantAssignment) - printing answers....."); //$NON-NLS-1$
			}
			for(int i=0 ; i<criterias.size() ; i++){
				if (logger.isDebugEnabled()) {
					logger.debug("evaluateStatus(StudyParticipantAssignment) - question : " + criterias.get(i).getEligibilityCriteria().getQuestionText()); //$NON-NLS-1$
				}
				if (logger.isDebugEnabled()) {
					logger.debug("evaluateStatus(StudyParticipantAssignment) - ----- answer : " + criterias.get(i).getAnswerText()); //$NON-NLS-1$
				}
			}
		}else if(!studyParticipantAssignment.getEligibilityIndicator()){
			//if(studyParticipantAssignment.getEligibilityWaiverReasonText()==null||studyParticipantAssignment.getEligibilityWaiverReasonText().equals(""))
			return "Incomplete";
		}
		return status;
	}
	private boolean evaluateStratificationIndicator(StudyParticipantAssignment studyParticipantAssignment){
		List<SubjectStratificationAnswer> answers=studyParticipantAssignment.getSubjectStratificationAnswers();
		for(SubjectStratificationAnswer subjectStratificationAnswer:answers){
			if(subjectStratificationAnswer.getStratificationCriterionAnswer()==null){
				return false;
			}
		}
		return true;
	}
	private boolean hasDiseaseHistory(DiseaseHistory diseaseHistory){
		if(diseaseHistory.getAnatomicSite()==null&&(diseaseHistory.getOtherPrimaryDiseaseSiteCode()==null||diseaseHistory.getOtherPrimaryDiseaseSiteCode().equals(""))&&
				(diseaseHistory.getOtherPrimaryDiseaseCode()==null||diseaseHistory.getOtherPrimaryDiseaseCode().equals(""))&&diseaseHistory.getStudyDisease()==null)
				return false;
		return true;
	}

}

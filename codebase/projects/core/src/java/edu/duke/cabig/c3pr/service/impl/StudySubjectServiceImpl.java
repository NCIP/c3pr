package edu.duke.cabig.c3pr.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;
import edu.duke.cabig.c3pr.service.StudySubjectService;
import edu.duke.cabig.c3pr.utils.XMLUtils;

/**
 * @author Kulasekaran, Ramakrishna
 * @version 1.0
 *
 */
public class StudySubjectServiceImpl implements StudySubjectService {

	private static final Logger logger = Logger.getLogger(StudySubjectServiceImpl.class);
	StudySubjectDao studySubjectDao;
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

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public StudySubject createRegistration(StudySubject studySubject) {
		studySubject.setRegistrationStatus(evaluateStatus(studySubject));
//        studySubject.getParticipant().addStudySubject(studySubject);
//        studySubject.getStudySite().addStudySubject(studySubject);
		ScheduledEpoch current=studySubject.getScheduledEpoch();
		if (current instanceof ScheduledTreatmentEpoch) {
			ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) current;
			if(scheduledTreatmentEpoch.getScheduledArm()!=null&&scheduledTreatmentEpoch.getScheduledArm().getArm()==null){
				scheduledTreatmentEpoch.removeScheduledArm();
			}
		}
		studySubject=studySubjectDao.merge(studySubject);
		if(isBroadcastEnable.equalsIgnoreCase("true")){
			String xml = "";
			try {
				xml = XMLUtils.toXml(studySubject);
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
		return studySubject;
	}
	
	public static String evaluateStatus(StudySubject studySubject){
		String status="Complete";
		ScheduledEpoch current=studySubject.getScheduledEpoch();
		if(studySubject.getInformedConsentSignedDateStr().equals("")){
			return "Incomplete";
		}else if(studySubject.getTreatingPhysicianFullName()==null){
			return "Incomplete";
		}else if(!evaluateStratificationIndicator(studySubject)){
			return "Incomplete";
		}else if (studySubject.getIfTreatmentScheduledEpoch()) {
			ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) current; 
			if(!scheduledTreatmentEpoch.getEligibilityIndicator()){
				return "Incomplete";
			}else if(scheduledTreatmentEpoch.getScheduledArm()==null || scheduledTreatmentEpoch.getScheduledArm()==null){
				return "Incomplete";
			}
		}
		return status;
	}
	private static boolean evaluateStratificationIndicator(StudySubject studySubject){
		ScheduledEpoch current=studySubject.getScheduledEpoch();
		if (studySubject.getIfTreatmentScheduledEpoch()) {
			ScheduledTreatmentEpoch scheduledTreatmentEpoch = (ScheduledTreatmentEpoch) current; 
			List<SubjectStratificationAnswer> answers=scheduledTreatmentEpoch.getSubjectStratificationAnswers();
			for(SubjectStratificationAnswer subjectStratificationAnswer:answers){
				if(subjectStratificationAnswer.getStratificationCriterionAnswer()==null){
					return false;
				}
			}
		}
		return true;
	}
}

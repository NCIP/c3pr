package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.NonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledNonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.web.registration.CreateRegistrationController;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class SearchStudySubjectTab extends RegistrationTab<StudySubject>{
	
	private static final Logger logger = Logger.getLogger(SearchStudySubjectTab.class);
	private EpochDao epochDao;

	public EpochDao getEpochDao() {
		return epochDao;
	}

	public void setEpochDao(EpochDao epochDao) {
		this.epochDao = epochDao;
	}

	public SearchStudySubjectTab() {
		super("Search Subject or Study", "SearchSubjectStudy","registration/reg_selectStudySubject");
		setShowSummary("false");
	}
	
	@Override
	public Map<String, Object> referenceData() {
		Map<String, List<Lov>> configMap = configurationProperty.getMap();
		Map<String, Object> refdata = new HashMap<String, Object>();
		refdata.put("searchTypeRefDataStudy", configMap.get("studySearchType"));
		refdata.put("searchTypeRefDataPrt", configMap.get("participantSearchType"));
		return refdata;
	}
	@Override
	protected String postProcessAsynchronous(HttpServletRequest request, StudySubject command, Errors error) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void postProcessSynchronous(HttpServletRequest request, StudySubject command, Errors error) {
		if(command.getScheduledEpoch()!=null)
			return;
		Integer id;
		try {
			id=Integer.parseInt(request.getParameter("epoch"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return;
		}
		Epoch epoch=epochDao.getById(id);
		for(ScheduledEpoch scheduledEpoch:command.getScheduledEpochs()){
			if(scheduledEpoch.getEpoch().getId()==epoch.getId())
				return;
		}
		ScheduledEpoch scheduledEpoch;
		if (epoch instanceof TreatmentEpoch) {
			((TreatmentEpoch)epoch).getArms().size();
			scheduledEpoch=new ScheduledTreatmentEpoch();
		}else{
			scheduledEpoch=new ScheduledNonTreatmentEpoch();
		}
		scheduledEpoch.setEpoch(epoch);
		command.setScheduledEpoch(scheduledEpoch);
		buildCommandObject(command);
	}
	private void buildCommandObject(StudySubject studySubject){
		if(studySubject.getIfTreatmentScheduledEpoch()){
			ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)studySubject.getScheduledEpoch();
			List criterias=scheduledTreatmentEpoch.getTreatmentEpoch().getInclusionEligibilityCriteria();
			for(int i=0 ; i<criterias.size() ; i++){
				SubjectEligibilityAnswer subjectEligibilityAnswer=new SubjectEligibilityAnswer();
				subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria)criterias.get(i));
				scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
			}
			criterias=scheduledTreatmentEpoch.getTreatmentEpoch().getExclusionEligibilityCriteria();
			for(int i=0 ; i<criterias.size() ; i++){
				SubjectEligibilityAnswer subjectEligibilityAnswer=new SubjectEligibilityAnswer();
				subjectEligibilityAnswer.setEligibilityCriteria((EligibilityCriteria)criterias.get(i));
				scheduledTreatmentEpoch.addSubjectEligibilityAnswers(subjectEligibilityAnswer);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("buildCommandObject(StudySubject studySubject) - studySubject.getParticipant().getPrimaryIdentifier()" + studySubject.getParticipant().getPrimaryIdentifier()); //$NON-NLS-1$
			}
			List<StratificationCriterion> stratifications=scheduledTreatmentEpoch.getTreatmentEpoch().getStratificationCriteria();
			for(StratificationCriterion stratificationCriterion : stratifications){
				stratificationCriterion.getPermissibleAnswers().size();
				SubjectStratificationAnswer subjectStratificationAnswer=new SubjectStratificationAnswer();
				subjectStratificationAnswer.setStratificationCriterion(stratificationCriterion);
				scheduledTreatmentEpoch.addSubjectStratificationAnswers(subjectStratificationAnswer);
			}
		}
	}

}

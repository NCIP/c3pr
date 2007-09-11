package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class EligibilityCriteriaTab extends RegistrationTab<StudySubject>{

	public EligibilityCriteriaTab() {
		super("Check Eligibility", "Check Eligibility","registration/reg_check_eligibility");
	}

	@Override
	public Map<String, Object> referenceData(StudySubject command) {
		Map ref=new HashMap();
		boolean requiresEligibility=false;
		if(command.getIfTreatmentScheduledEpoch())
			if(((TreatmentEpoch)command.getScheduledEpoch().getEpoch()).getEligibilityCriteria().size()>0)
				requiresEligibility=true;
		ref.put("requiresEligibility", requiresEligibility);
		return ref;
	}
	@Override
	public void postProcess(HttpServletRequest request, StudySubject command, Errors error) {
		// TODO Auto-generated method stub
		if(command.getIfTreatmentScheduledEpoch()){
			((ScheduledTreatmentEpoch)command.getScheduledEpoch()).setEligibilityIndicator(evaluateEligibilityIndicator(command));
		}
	}
	private boolean evaluateEligibilityIndicator(StudySubject studySubject){
		boolean flag=true;
		List<SubjectEligibilityAnswer> answers=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getInclusionEligibilityAnswers();
		for(SubjectEligibilityAnswer subjectEligibilityAnswer:answers){
			String answerText=subjectEligibilityAnswer.getAnswerText();
			if(answerText==null||answerText.equalsIgnoreCase("")||(!answerText.equalsIgnoreCase("Yes")&&!answerText.equalsIgnoreCase("NA"))){
				flag=false;
				break;
			}
		}
		if(flag){
			answers=((ScheduledTreatmentEpoch)studySubject.getScheduledEpoch()).getExclusionEligibilityAnswers();
			for(SubjectEligibilityAnswer subjectEligibilityAnswer:answers){
				String answerText=subjectEligibilityAnswer.getAnswerText();
				if(answerText==null||answerText.equalsIgnoreCase("")||(!answerText.equalsIgnoreCase("No")&&!answerText.equalsIgnoreCase("NA"))){
					flag=false;
					break;
				}
			}
		}
		return flag;
	}
}

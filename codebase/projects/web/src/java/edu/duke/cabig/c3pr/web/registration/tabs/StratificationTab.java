package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.ScheduledTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SubFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class StratificationTab extends RegistrationTab<StudySubject>{

	public StratificationTab() {
		super("Stratify", "Stratify","registration/reg_stratify");
	}

	@Override
	protected String postProcessAsynchronous(HttpServletRequest request, StudySubject command, Errors error) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void postProcessSynchronous(HttpServletRequest request, StudySubject command, Errors error) {
/*		StudySubject studySubject=(StudySubject)command;
		handleStratification(request,studySubject);
*/		
	}
/*	private void handleStratification(HttpServletRequest request, StudySubject studySubject){
		ScheduledTreatmentEpoch scheduledTreatmentEpoch=(ScheduledTreatmentEpoch)studySubject.getScheduledEpoch();
		for(int i=0 ; i<scheduledTreatmentEpoch.getSubjectStratificationAnswers().size() ; i++){
			String id=request.getParameter("subjectStratificationAnswers["+i+"].stratificationCriterionAnswer");
			if(StringUtils.isEmpty(id))
				return;
			int tempId=Integer.parseInt(id);
			for(StratificationCriterionPermissibleAnswer answer : scheduledTreatmentEpoch.getSubjectStratificationAnswers().get(i).getStratificationCriterion().getPermissibleAnswers()){
				if(answer.getId()==tempId)
					scheduledTreatmentEpoch.getSubjectStratificationAnswers().get(i).setStratificationCriterionAnswer(answer);
			}
		}
	}
*/}

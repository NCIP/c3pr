package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import sun.reflect.generics.visitor.Reifier;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To
 * change this template use File | Settings | File Templates.
 */
public class ReviewSubmitTab extends RegistrationTab<StudySubject>{

	public ReviewSubmitTab() {
		super("Review & Submit", "Review & Submit","registration/reg_submit");
		setShowSummary("false");
	}
	@Override
	public Map referenceData(StudySubject command) {
		// TODO Auto-generated method stub
		Map<String, Boolean> map=new HashMap<String, Boolean>();
		map.put("registerable", isRegisterable(command));
		return map;
	}
	
	public boolean isRegisterable(StudySubject studySubject){
		return this.studySubjectService.isRegisterable(studySubject) && studySubject.getScheduledEpoch().getEpoch().isEnrolling() && !studySubject.getScheduledEpoch().getRequiresRandomization();
	}

}

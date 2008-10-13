package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

public class ManageCompanionRegistrationTab<C extends StudySubjectWrapper> extends RegistrationTab<C> {
	
	public ManageCompanionRegistrationTab() {
		super("Manage Companion Registration", "Companion Registration", "registration/reg_companion_reg");
	}
	
	public Map<String,Object> referenceData(C command) {
		StudySubjectWrapper wrapper = (StudySubjectWrapper) command ;
    	StudySubject studySubject = wrapper.getStudySubject();
		Map map = registrationControllerUtils.buildMap(studySubject);
		boolean actionRequired = false;
		String actionLabel = "";
		if (studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.UNAPPROVED && studySubject.isDataEntryComplete()) {
			actionRequired = true;
			if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.REGISTERED) {
				if (studySubject.getScheduledEpoch().getEpoch().isEnrolling())
					actionLabel = "Register";
				else
					actionLabel = "Save";
			} else
				actionLabel = "Transfer Subject";
			if (studySubject.getScheduledEpoch().getRequiresRandomization()) {
				actionLabel += " & Randomize";
			}
		}
		map.put("actionRequired", actionRequired);
		map.put("actionLabel", actionLabel);
		map.put("requiresMultiSite", studySubjectService.requiresExternalApprovalForRegistration(studySubject));
		return map;
	}
	
}

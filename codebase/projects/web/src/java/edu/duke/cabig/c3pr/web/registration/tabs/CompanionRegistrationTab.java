package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

public class CompanionRegistrationTab<C extends StudySubjectWrapper> extends RegistrationTab<C> {
	
	private RegistrationControllerUtils registrationControllerUtils;
	
	public CompanionRegistrationTab() {
		super("Companion Registrations", "Companion Registrations", "registration/reg_companion_reg");
	}
	
	@Override
	public Map<String,Object> referenceData(HttpServletRequest request, StudySubjectWrapper wrapper) {
		Map map = new HashMap();
		map.put("companions", registrationControllerUtils.getCompanionStudySubject(ControllerTools.getIdentifierInRequest(request)));
		return map;
	}

	public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}
	
		
}

package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class ReviewSubmitTab extends RegistrationTab<StudySubjectWrapper> {

	private RegistrationControllerUtils registrationControllerUtils;
    public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}

	public ReviewSubmitTab() {
        super("Review & Submit", "Review & Submit", "registration/reg_submit");
        setShowSummary("false");
    }

    @Override
    public Map referenceData(StudySubjectWrapper command) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("registerable", registrationControllerUtils.isRegisterableOnPage(command.getStudySubject()));
        return map;
    }

}

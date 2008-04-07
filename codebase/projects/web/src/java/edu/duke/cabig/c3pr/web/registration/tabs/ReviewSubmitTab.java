package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class ReviewSubmitTab extends RegistrationTab<StudySubject> {

    public ReviewSubmitTab() {
        super("Review & Submit", "Review & Submit", "registration/reg_submit");
        setShowSummary("false");
    }

    @Override
    public Map referenceData(StudySubject command) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("registerable", isRegisterableOnPage(command));
        return map;
    }

    public boolean isRegisterableOnPage(StudySubject studySubject) {
        return studySubject.isRegisterable()
                        && !studySubject.getScheduledEpoch().getRequiresRandomization()
                        && !studySubjectService.requiresExternalApprovalForRegistration(studySubject);
    }

}

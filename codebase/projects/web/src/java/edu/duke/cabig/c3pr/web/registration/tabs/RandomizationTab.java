package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class RandomizationTab extends RegistrationTab<StudySubjectWrapper> {

    public RandomizationTab() {
        super("Randomize", "Randomize", "registration/reg_randomize");
    }

    @Override
    public Map<String, Object> referenceData(StudySubjectWrapper command) {
        Map ref = new HashMap();
        ref.put("canRandomize", command.getStudySubject().readyForRandomization());
        return ref;
    }

}

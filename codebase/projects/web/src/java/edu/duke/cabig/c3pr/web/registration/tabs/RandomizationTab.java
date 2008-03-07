package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class RandomizationTab extends RegistrationTab<StudySubject> {

    public RandomizationTab() {
        super("Randomize", "Randomize", "registration/reg_randomize");
    }

    @Override
    public Map<String, Object> referenceData(StudySubject command) {
        Map ref = new HashMap();
        ref.put("canRandomize", this.studySubjectService.canRandomize(command));
        return ref;
    }

}

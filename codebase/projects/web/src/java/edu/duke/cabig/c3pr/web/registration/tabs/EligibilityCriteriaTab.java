package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class EligibilityCriteriaTab extends RegistrationTab<StudySubject> {

    public EligibilityCriteriaTab() {
        super("Check Eligibility", "Check Eligibility", "registration/reg_check_eligibility");
    }

    @Override
    public Map<String, Object> referenceData(StudySubject command) {
        Map ref = new HashMap();
        boolean requiresEligibility = false;
        if (command.getScheduledEpoch()!=null) if (( command
                        .getScheduledEpoch().getEpoch()).getEligibilityCriteria().size() > 0) requiresEligibility = true;
        ref.put("requiresEligibility", requiresEligibility);
        return ref;
    }

    @Override
    public void postProcess(HttpServletRequest request, StudySubject command, Errors error) {
        // TODO Auto-generated method stub
        if (command.getScheduledEpoch()!=null) {
            (command.getScheduledEpoch()).setEligibilityIndicator(registrationControllerUtils.evaluateEligibilityIndicator(command));
        }
    }

    
}

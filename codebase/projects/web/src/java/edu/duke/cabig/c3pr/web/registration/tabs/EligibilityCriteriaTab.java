/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class EligibilityCriteriaTab extends RegistrationTab<StudySubjectWrapper> {

    public EligibilityCriteriaTab() {
        super("Eligibility", "Eligibility", "registration/reg_check_eligibility");
    }

    @Override
    public Map<String, Object> referenceData(StudySubjectWrapper command) {
        Map ref = new HashMap();
        boolean requiresEligibility = false;
        if (command.getStudySubject().getScheduledEpoch()!=null) if (( command.getStudySubject()
                        .getScheduledEpoch().getEpoch()).getEligibilityCriteria().size() > 0) requiresEligibility = true;
        ref.put("requiresEligibility", requiresEligibility);
        return ref;
    }

}

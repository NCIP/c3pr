package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import org.springframework.validation.Errors;
import edu.duke.cabig.c3pr.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:24:28 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyEligibilityChecklistTab extends StudyTab {

    public StudyEligibilityChecklistTab() {
        super("Study Eligibility Checklist", "Eligibility Checklist", "study/study_eligibility_checklist");
    }


    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {
    }

}

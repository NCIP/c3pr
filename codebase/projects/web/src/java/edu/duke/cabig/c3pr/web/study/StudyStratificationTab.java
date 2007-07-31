package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.StringUtils;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:28:05 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyStratificationTab extends StudyTab {

    public StudyStratificationTab() {
        super("Study Stratification Factors", "Stratification Factors", "study/study_stratifications");
    }


    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {
    }

}

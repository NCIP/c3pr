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
    	
    	String epochIndex=httpServletRequest.getParameter("_selectedEpoch");
        int selectedEpoch = StringUtils.getBlankIfNull(epochIndex).equals("")?-1:Integer.parseInt(epochIndex);
        String action = httpServletRequest.getParameter("_action");
        String selected = httpServletRequest.getParameter("_selected");

        if ("addInclusionCriteria".equals(action)) {
            log.debug("Requested - Add a Inclusion Eligibility Criteria");
            httpServletRequest.setAttribute("currentOperation", "inclusion");
            createDefaultInclusion(study,selectedEpoch);
        } else if ("removeInclusionCriteria".equals(action)) {
            log.debug("Requested - Remove an Inclusion Eligibility Criteria");
            httpServletRequest.setAttribute("currentOperation", "inclusion");
           // study.getIncCriterias().remove(Integer.parseInt(selected));
            ((TreatmentEpoch)study.getEpochs().get(selectedEpoch)).getInclusionEligibilityCriteria().remove(Integer.parseInt(selected));
        }
        if ("addExclusionCriteria".equals(action)) {
            log.debug("Requested - Add an Exclusion Eligibility Criteria");
            httpServletRequest.setAttribute("currentOperation", "exclusion");
            createDefaultExclusion(study,selectedEpoch);
        } else if ("removeExclusionCriteria".equals(action)) {
            log.debug("Requested - Remove an Exclusion Eligibility Criteria");
            httpServletRequest.setAttribute("currentOperation", "exclusion");
          //  study.getExcCriterias().remove(Integer.parseInt(selected));
            ((TreatmentEpoch)study.getEpochs().get(selectedEpoch)).getExclusionEligibilityCriteria().remove(Integer.parseInt(selected));
        }

    }

    protected void createDefaultInclusion(Study study,int epochIndex) {
        InclusionEligibilityCriteria inc = new InclusionEligibilityCriteria();
        inc.setQuestionNumber(((TreatmentEpoch)study.getEpochs().get(epochIndex)).getInclusionEligibilityCriteria().size() + 1);
        inc.setQuestionText("");
        ((TreatmentEpoch)study.getEpochs().get(epochIndex)).addEligibilityCriterion(inc);
    //    study.addInclusionEligibilityCriteria(inc);
    }

    protected void createDefaultExclusion(Study study,int epochIndex) {
        ExclusionEligibilityCriteria exc = new ExclusionEligibilityCriteria();
        exc.setQuestionNumber(((TreatmentEpoch)study.getEpochs().get(epochIndex)).getExclusionEligibilityCriteria().size() + 1);
        exc.setQuestionText("");
        ((TreatmentEpoch)study.getEpochs().get(epochIndex)).addEligibilityCriterion(exc);
       // study.addExclusionEligibilityCriteria(exc);
    }


}

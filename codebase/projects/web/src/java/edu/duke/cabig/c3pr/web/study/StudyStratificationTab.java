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
        String action = httpServletRequest.getParameter("_action");
        String epochIndex=httpServletRequest.getParameter("_selectedEpoch");
        int selectedEpoch = StringUtils.getBlankIfNull(epochIndex).equals("")?-1:Integer.parseInt(epochIndex);
        int selectedStratification = 0;
        int selectedAnswer = 0;

        if (StringUtils.isNotEmpty(httpServletRequest.getParameter("_selectedStratification"))) {
            selectedStratification = Integer.parseInt(httpServletRequest.getParameter("_selectedStratification"));
        }
        if (StringUtils.isNotEmpty(httpServletRequest.getParameter("_selectedAnswer"))) {
            selectedAnswer = Integer.parseInt(httpServletRequest.getParameter("_selectedAnswer"));
        }
        if ("addStratificationQuestion".equals(action)) {
            log.debug("Requested - Add a Stratication Question");
            createDefaultStratification(study, selectedEpoch);
//            httpServletRequest.getSession().setAttribute("selectedStratification", selectedStratification + 1);
//            httpServletRequest.getSession().setAttribute("selectedAnswer", 0);
        } else if ("removeStratificationQuestion".equals(action)) {
            log.debug("Requested - Remove a Stratication Question");
            ((TreatmentEpoch)study.getEpochs().get(selectedEpoch)).getStratificationCriteria().remove(selectedStratification);
            selectedStratification = (selectedStratification != 0) ? selectedStratification : 1;
//            httpServletRequest.getSession().setAttribute("selectedStratification", selectedStratification - 1);
        } else if ("addPermissibleAnswer".equals(action)) {
            log.debug("Requested - Add a Permissible Answer");
            StratificationCriterion cri = ((TreatmentEpoch)study.getEpochs().get(selectedEpoch)).getStratificationCriteria().get(selectedStratification);
            cri.addPermissibleAnswer(new StratificationCriterionPermissibleAnswer());
//            httpServletRequest.getSession().setAttribute("selectedAnswer", selectedAnswer + 1);
        } else if ("removePermissibleAnswer".equals(action)) {
            log.debug("Requested - Remove a Permissible Answer");
            StratificationCriterion cri = ((TreatmentEpoch)study.getEpochs().get(selectedEpoch)).getStratificationCriteria().get(selectedStratification);
            cri.getPermissibleAnswers().remove(selectedAnswer);
            selectedAnswer = (selectedAnswer != 0) ? selectedAnswer : 1;
//            httpServletRequest.getSession().setAttribute("selectedAnswer", selectedAnswer - 1);
        } else {
//            httpServletRequest.getSession().setAttribute("selectedStratification", 0);
//            httpServletRequest.getSession().setAttribute("selectedAnswer", 0);
        }

    }

    protected void createDefaultStratification(Study study, int epochIndex) {
        StratificationCriterionPermissibleAnswer ans = new StratificationCriterionPermissibleAnswer();
        StratificationCriterion cri = new StratificationCriterion();
        cri.addPermissibleAnswer(ans);
        cri.setQuestionNumber(((TreatmentEpoch)study.getEpochs().get(epochIndex)).getStratificationCriteria().size() + 1);
        ((TreatmentEpoch)study.getEpochs().get(epochIndex)).addStratificationCriterion(cri);
    }
}

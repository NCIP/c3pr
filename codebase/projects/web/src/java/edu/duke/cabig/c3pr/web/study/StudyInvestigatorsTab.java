package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:02:39 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyInvestigatorsTab extends StudyTab {

    public StudyInvestigatorsTab() {
        super("Study Investigators", "Investigators", "study/study_investigators");
    }

    @Override
    public Map<String, Object> referenceData(Study study) {
        Map<String, Object> refdata = super.referenceData(study);    //To change body of overridden methods use File | Settings | File Templates.
        addConfigMapToRefdata(refdata, "studyInvestigatorRoleRefData");
        addConfigMapToRefdata(refdata, "studyInvestigatorStatusRefData");

        return refdata;
    }

    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {

        handleStudyInvestigatorAction(study, httpServletRequest);
    }

    private void handleStudyInvestigatorAction(Study study, HttpServletRequest request)
    {
        String action =request.getParameter("_action");
        String selectedSite = request.getParameter("_selectedSite");
        String selectedInvestigator = request.getParameter("_selectedInvestigator");

        if ("addInv".equals(action))
        {
            StudyInvestigator studyInvestigator = new StudyInvestigator();
            StudySite studySite = study.getStudySites().get(Integer.parseInt(selectedSite));
            studySite.addStudyInvestigator(studyInvestigator);

        }
        else if ("removeInv".equals(action))
        {
            study.getStudySites().get(Integer.parseInt(selectedSite)).getStudyInvestigators()
                    .remove(Integer.parseInt(selectedInvestigator));
        }

    }
}


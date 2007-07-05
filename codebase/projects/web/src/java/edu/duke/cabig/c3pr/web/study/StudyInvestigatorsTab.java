package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
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
        this(false);
    }

    public StudyInvestigatorsTab(boolean editMode) {
            super("Study Investigators", "Investigators", editMode?"study/study_investigators_edit":"study/study_investigators");
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
        if ("siteChange".equals(httpServletRequest.getParameter("_action"))) {
            httpServletRequest.getSession().setAttribute("selectedSite", httpServletRequest.getParameter("_selectedSite"));

            StudySite studySite = study.getStudySites().get(Integer.parseInt(httpServletRequest.getParameter("_selectedSite")));

        }

    }

    
}


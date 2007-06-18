package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;

import edu.duke.cabig.c3pr.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 1:39:34 PM
 * To change this template use File | Settings | File Templates.
 */
class StudySitesTab extends StudyTab {

    public StudySitesTab() {
        super("Study Sites", "Sites", "study/study_studysites");
    }

    @Override
    public Map<String, Object> referenceData(Study study) {
        Map<String, Object> refdata = super.referenceData(study);
        addConfigMapToRefdata(refdata, "studySiteStatusRefData");
        addConfigMapToRefdata(refdata, "studySiteRoleCodeRefData");
        refdata.put("healthCareSitesRefData", getHealthcareSiteDao().getAll());

        return refdata;

    }

    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {
        String action =    httpServletRequest.getParameter("_action");
        String selected =    httpServletRequest.getParameter("_selected");

        if ("addSite".equals(action)) {
            StudySite studySite = new StudySite();
            study.addStudySite(studySite);
            studySite.setRoleCode("<role code>");
            studySite.setStatusCode("<status code>");

            List<HealthcareSite> healthcareSites  = getHealthcareSiteDao().getAll();
            for (HealthcareSite site : healthcareSites) {
                //associate all
                studySite.setSite(site);
            }

        } else if ("removeSite".equals(action)) {
            study.getStudySites().remove(Integer.parseInt(selected));
        }
    }


}



    


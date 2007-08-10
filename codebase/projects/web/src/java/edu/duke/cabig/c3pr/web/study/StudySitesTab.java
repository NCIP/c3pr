package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 1:39:34 PM
 * To change this template use File | Settings | File Templates.
 */
class StudySitesTab extends StudyTab {

    public StudySitesTab() {
        super("Sites", "Sites", "study/study_studysites");
    }

    @Override
    public Map<String, Object> referenceData(Study study) {
        Map<String, Object> refdata = super.referenceData(study);
        addConfigMapToRefdata(refdata, "studySiteStatusRefData");
        addConfigMapToRefdata(refdata, "studySiteRoleCodeRefData");
        refdata.put("healthCareSites", getHealthcareSiteDao().getAll());

        return refdata;

    }

}



    


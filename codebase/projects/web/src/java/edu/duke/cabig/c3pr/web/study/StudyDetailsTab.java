package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 13, 2007
 * Time: 7:27:09 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyDetailsTab extends StudyTab {

    public StudyDetailsTab() {
        super("Study Details", "Details", "study/study_details");
    }

    @Override
    public Map<String, Object> referenceData(Study study) {
        Map<String, Object> refdata = super.referenceData();
        addConfigMapToRefdata(refdata, "studySearchType");
        addConfigMapToRefdata(refdata, "diseaseCodeRefData");
        addConfigMapToRefdata(refdata, "monitorCodeRefData");
        addConfigMapToRefdata(refdata, "phaseCodeRefData");
        addConfigMapToRefdata(refdata, "sponsorCodeRefData");
        addConfigMapToRefdata(refdata, "statusRefData");
        addConfigMapToRefdata(refdata, "typeRefData");
        addConfigMapToRefdata(refdata, "coordinatingCenters");
        addConfigMapToRefdata(refdata, "yesNo");

        return refdata;
    }


}


 
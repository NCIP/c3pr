package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.Lov;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 13, 2007
 * Time: 7:27:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudyDetailsTab extends StudyTab {

    public StudyDetailsTab() {
        super("Study Details", "Details", "study/study_details");
    }

    public Map<String, Object> referenceData(Study study) {
         Map<String, Object> refdata = super.referenceData();
        addConfigMapToRefdata(refdata, "phaseCodeRefData");
      
        return refdata;

    }


}


 
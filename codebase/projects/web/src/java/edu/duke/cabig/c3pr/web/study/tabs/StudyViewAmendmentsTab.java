package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.Map;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:32:32 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyViewAmendmentsTab extends StudyTab {

    private StudyDao studyDao;

    public StudyViewAmendmentsTab() {
        super("Manage Amendments", "Amendments", "study/study_view_amendments");
    }

    @Override
    public Map<String, Object> referenceData(StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        refdata.put("applyAmendment", wrapper.applyAmendment());
        return refdata;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }
}

package edu.duke.cabig.c3pr.web.study.tabs;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.utils.web.WebUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:32:32 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyRegistrationsTab extends StudyTab {

    private StudyDao studyDao;

    public StudyRegistrationsTab() {
        super("Study Registrations", "Registrations", "study/study_registrations");
    }

    @Override
    public Map<String, Object> referenceData(StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        Boolean isOnlyStudyCoordinator = false;
        if(WebUtils.isStudyCoordinator()&& !WebUtils.isSiteCoordinator() && !WebUtils.isAdmin() && !WebUtils.isRegistrar()){
        	isOnlyStudyCoordinator = true;
        }
        refdata.put("isOnlyStudyCoordinator", isOnlyStudyCoordinator);
        refdata.put("participantAssignments", this.getStudyDao().getStudySubjectsForStudy(wrapper.getStudy().getId()));

        return refdata;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }
}

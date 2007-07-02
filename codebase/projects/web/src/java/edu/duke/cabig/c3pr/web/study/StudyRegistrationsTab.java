package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:32:32 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyRegistrationsTab extends StudyTab {

    private StudyDao studyDao;

    public StudyRegistrationsTab() {
        super("Study Registrations", "Registrations", "study/study_registrations");
    }

    @Override
    public Map<String, Object> referenceData(Study study) {
        Map<String, Object> refdata = super.referenceData(study);
        refdata.put("participantAssignments", this.getStudyDao().getStudyParticipantAssignmentsForStudy(study.getId()));

        return refdata;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }
}

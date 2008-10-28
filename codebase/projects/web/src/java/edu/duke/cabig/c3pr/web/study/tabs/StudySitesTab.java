package edu.duke.cabig.c3pr.web.study.tabs;

import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 1:39:34 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudySitesTab extends StudyTab {

    private StudyValidator studyValidator;

    public StudySitesTab() {
        super("Study Sites", "Study Sites", "study/study_studysites");
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        addConfigMapToRefdata(refdata, "studySiteStatusRefData");
        addConfigMapToRefdata(refdata, "studySiteRoleCodeRefData");
        boolean isAdmin = isAdmin();
        return refdata;
    }

    @Override
    public void validate(StudyWrapper wrapper, Errors errors) {
        super.validate(wrapper, errors);
        this.studyValidator.validateStudySites(wrapper.getStudy(), errors);
    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

}

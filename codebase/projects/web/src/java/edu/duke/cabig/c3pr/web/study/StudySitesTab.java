package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 1:39:34 PM To change this template
 * use File | Settings | File Templates.
 */
class StudySitesTab extends StudyTab {

    private StudyValidator studyValidator;

    public StudySitesTab() {
        super("Sites", "Sites", "study/study_studysites");
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, Study study) {
        Map<String, Object> refdata = super.referenceData(study);
        addConfigMapToRefdata(refdata, "studySiteStatusRefData");
        addConfigMapToRefdata(refdata, "studySiteRoleCodeRefData");
        refdata.put("healthCareSites", getHealthcareSiteDao().getAll());
        boolean isAdmin = isAdmin();

        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow")
                        .toString().equals("true"))
                        || (request.getAttribute("editFlow") != null && request.getAttribute(
                                        "editFlow").toString().equals("true"))) {
            if (request.getSession().getAttribute(DISABLE_FORM_SITES) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_SITES));
            }
            else {
                refdata.put("disableForm", new Boolean(false));
            }
        }
        return refdata;

    }

    @Override
    public void validate(Study study, Errors errors) {
        super.validate(study, errors);
        this.studyValidator.validateStudySites(study, errors);
    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

}

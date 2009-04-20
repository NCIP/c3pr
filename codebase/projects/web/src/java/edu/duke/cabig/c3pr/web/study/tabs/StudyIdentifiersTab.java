package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 12:51:05 PM To change this
 * template use File | Settings | File Templates.
 */
public class StudyIdentifiersTab extends StudyTab {

    private StudyValidator studyValidator;

    public StudyIdentifiersTab() {
        super("Identifiers", "Identifiers", "study/study_identifiers");
    }

    @Override
    public Map<String, Object> referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData();
        addConfigMapToRefdata(refdata, "identifiersTypeRefData");
        return refdata;
    }

    @Override
    public void validate(StudyWrapper wrapper, Errors errors) {
        super.validate(wrapper, errors);
        this.studyValidator.validateStudyIdentifiers(wrapper.getStudy(), errors);

    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }
}

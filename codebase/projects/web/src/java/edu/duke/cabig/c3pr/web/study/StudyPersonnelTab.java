package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:11:26 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyPersonnelTab extends StudyTab {
	
	private StudyValidator studyValidator;

    public StudyPersonnelTab() {
        this(false);
    }

    public StudyPersonnelTab(boolean editMode){
        super("Personnel", "Personnel", editMode?"study/study_personnel_edit":"study/study_personnel");
    }

    @Override
    public Map<String, Object> referenceData(Study study) {
        Map<String, Object> refdata = super.referenceData(study);    //To change body of overridden methods use File | Settings | File Templates.
        addConfigMapToRefdata(refdata, "studyPersonnelRoleRefData");
        addConfigMapToRefdata(refdata, "studyPersonnelStatusRefData");

        return refdata;
    }
    

    @Override
	public void validate(Study study, Errors errors) {
		// TODO Auto-generated method stub
		super.validate(study, errors);
		this.studyValidator.validateStudyPersonnel(study, errors);
	}

    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {
        if ("siteChange".equals(httpServletRequest.getParameter("_action"))) {
            httpServletRequest.getSession().setAttribute("selectedSite", httpServletRequest.getParameter("_selectedSite"));
        }
    }

	public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}
    
}
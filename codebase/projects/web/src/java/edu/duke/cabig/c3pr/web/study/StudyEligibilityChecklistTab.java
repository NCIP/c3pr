package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Study;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jun 15, 2007
 * Time: 3:24:28 PM
 * To change this template use File | Settings | File Templates.
 */
class StudyEligibilityChecklistTab extends StudyTab {

    public StudyEligibilityChecklistTab() {
        super("Study Eligibility Checklist", "Eligibility", "study/study_eligibility_checklist");
    }

    @Override
	public Map referenceData(HttpServletRequest request, Study study) {
		Map<String, Object> refdata = super.referenceData(study);
		if( (request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow").toString().equals("true")) ||
			    (request.getAttribute("editFlow") != null && request.getAttribute("editFlow").toString().equals("true")) ) 
    	{
			if(request.getSession().getAttribute(DISABLE_FORM_ELIGIBILITY) != null){
				refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_ELIGIBILITY));
			} else {
				refdata.put("disableForm", new Boolean(false));
			}
    	}
		return refdata;
	}

    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {
    }

}

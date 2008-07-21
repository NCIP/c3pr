package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Study;

public class CompanionStudyTab extends StudyTab{
	
	public CompanionStudyTab() {
        super("Companion Studies", "Companion Studies", "study/study_companions");
    }
	
	@Override
    public Map referenceData(HttpServletRequest request, Study study) {
		request.getSession().setAttribute("studyObj", study);
        Map<String, Object> refdata = super.referenceData(study);
        addConfigMapToRefdata(refdata, "yesNo");
        boolean isAdmin = isAdmin();
        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow")
                        .toString().equals("true"))
                        || (request.getAttribute("editFlow") != null && request.getAttribute(
                                        "editFlow").toString().equals("true"))) {
            if (request.getSession().getAttribute(DISABLE_FORM_COMPANION) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_COMPANION));
            }
            else {
                refdata.put("disableForm", new Boolean(false));
            }
        }
        return refdata;
    }
	
	@Override
	public Map<String, Object> referenceData(Study command) {
		return super.referenceData(command);
	}
	
	@Override
    public void postProcessOnValidation(HttpServletRequest req, Study study, Errors errors) {
        super.postProcessOnValidation(req, study, errors);
   
	}
	

}

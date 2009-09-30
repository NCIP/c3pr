package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.domain.validator.ConsentValidator;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.WebUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

public class StudyConsentTab extends StudyTab {
	public StudyConsentTab() {
        super("Consent", "Consent", "study/study_consents");
    }

	 private ConsentValidator consentValidator;

	    public ConsentValidator getConsentValidator() {
			return consentValidator;
		}

		public void setConsentValidator(ConsentValidator consentValidator) {
			this.consentValidator = consentValidator;
		}

	private StudyValidator studyValidator;

	public StudyValidator getStudyValidator() {
		return studyValidator;
	}
	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}

	@SuppressWarnings("unchecked")
	@Override
    public Map referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        Map<String, List<Lov>> configMap = configurationProperty.getMap();
        boolean isAdmin = isAdmin();
        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow").toString().equals("true"))
                || (request.getAttribute("editFlow") != null && request.getAttribute("editFlow").toString().equals("true"))) {
            if (request.getSession().getAttribute(DISABLE_FORM_CONSENT) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_CONSENT));
            } else {
                refdata.put("disableForm", new Boolean(false));
            }
        }
        refdata.put("consentRequired", WebUtils.collectOptions(ConsentRequired.values(), "Please select"));
        refdata.put("openSections",request.getParameter("openSections"));
        return refdata;
    }

	 @Override
	    public void validate(StudyWrapper wrapper, Errors errors) {
	       super.validate(wrapper, errors);
	        this.studyValidator.validateConsents(wrapper.getStudy(), errors);
	    }
}
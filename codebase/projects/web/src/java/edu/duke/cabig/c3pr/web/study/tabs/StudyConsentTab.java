package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

public class StudyConsentTab extends StudyTab {
	public StudyConsentTab() {
        super("Consent", "Consent", "study/study_consents");
    }
	
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
        refdata.put("consentRequired", configMap.get("consentRequiredRefData"));
        refdata.put("openSections",request.getParameter("openSections"));
        return refdata;
    }

}

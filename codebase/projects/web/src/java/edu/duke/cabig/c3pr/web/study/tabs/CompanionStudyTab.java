package edu.duke.cabig.c3pr.web.study.tabs;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CompanionStudyTab extends StudyTab {

    public CompanionStudyTab() {
        super("Companion Studies", "Companion Studies", "study/study_companions");
    }

    @Override
    public Map referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        request.getSession().setAttribute("studyObj", wrapper.getStudy());
        Map<String, Object> refdata = super.referenceData(wrapper);
        addConfigMapToRefdata(refdata, "yesNo");
        boolean isAdmin = isAdmin();
        if ((request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow")
                .toString().equals("true"))
                || (request.getAttribute("editFlow") != null && request.getAttribute(
                "editFlow").toString().equals("true"))) {
            if (request.getSession().getAttribute(DISABLE_FORM_COMPANION) != null && !isAdmin) {
                refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_COMPANION));
            } else {
                refdata.put("disableForm", new Boolean(false));
            }
        }
        return refdata;
    }

    @Override
    public Map<String, Object> referenceData(StudyWrapper command) {
        return super.referenceData(command);
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest req, StudyWrapper wrapper, Errors errors) {
        super.postProcessOnValidation(req, wrapper, errors);

    }


}

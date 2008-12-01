package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

public class CompanionStudyTab extends StudyTab {

	public CompanionStudyTab() {
        super("Companion Studies", "Companion Studies", "study/study_companions");
    }

    @SuppressWarnings("unchecked")
	@Override
    public Map referenceData(HttpServletRequest request, StudyWrapper wrapper) {
        request.getSession().setAttribute("studyObj", wrapper.getStudy());
        
        Map<String, Object> refdata = super.referenceData(wrapper);
        addConfigMapToRefdata(refdata, "phaseCodeRefData");
        addConfigMapToRefdata(refdata, "statusRefData");
        addConfigMapToRefdata(refdata, "typeRefData");
        addConfigMapToRefdata(refdata, "yesNo");
        boolean isAdmin = isAdmin();
        refdata.put("dataFromParent", dataForCompanionStudies(wrapper.getStudy()));
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
    
    @SuppressWarnings("unchecked")
	private Map dataForCompanionStudies(Study study) {
		Map map = new HashMap();
		map.put("shortTitle", study.getShortTitleText());
		map.put("longTitle", study.getLongTitleText());
		map.put("fundingSponsorsList", study.getStudyFundingSponsors());
		map.put("coordinatingCenterList", study.getStudyCoordinatingCenters());
    	return map;
	}
}

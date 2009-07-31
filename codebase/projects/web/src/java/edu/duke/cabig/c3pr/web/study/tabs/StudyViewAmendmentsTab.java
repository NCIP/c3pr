package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:32:32 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyViewAmendmentsTab extends StudyTab {

    private StudyDao studyDao;

    public StudyViewAmendmentsTab() {
        super("Manage Amendments", "Amendments", "study/study_view_amendments");
    }

    @Override
    public Map<String, Object> referenceData(StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        refdata.put("applyAmendment", wrapper.applyAmendment());
        return refdata;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
    }

    public ModelAndView applyAmendment(HttpServletRequest request, Object obj,Errors errors) {
		StudyWrapper wrapper = (StudyWrapper) obj;
		Study study = wrapper.getStudy();
		study = studyRepository.applyAmendment(study.getIdentifiers());
		Map map = new HashMap();
		map.put("amendment", study.getLatestStudyVersion());
		map.put("amendmentIndex", request.getParameter("statusIndex"));
		wrapper.setStudy(study);
		return new ModelAndView(AjaxableUtils.getAjaxViewName(request), map);
	}
}
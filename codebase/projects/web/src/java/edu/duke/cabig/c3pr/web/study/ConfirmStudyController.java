package edu.duke.cabig.c3pr.web.study;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.BaseCommandController;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;

public class ConfirmStudyController extends BaseCommandController {
	
	protected StudyDao studyDao;

    public ConfirmStudyController() {
    }
    

    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
    	HashMap<String, Study> map = new HashMap<String, Study>() ;
        if (study != null) {
        	map.put("command", study);
        }
        ModelAndView mav = new ModelAndView("study/study_confirmation", map);
        return mav;
    }


	public StudyDao getStudyDao() {
		return studyDao;
	}


	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
}

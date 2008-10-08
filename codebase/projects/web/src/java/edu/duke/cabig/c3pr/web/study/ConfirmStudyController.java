package edu.duke.cabig.c3pr.web.study;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.BaseCommandController;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.web.navigation.Task;

public class ConfirmStudyController extends BaseCommandController {

	protected StudyDao studyDao;
	private edu.duke.cabig.c3pr.utils.web.navigation.Task editTask;

	public ConfirmStudyController() {
	}


	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		GrantedAuthority[] groups = auth.getAuthorities();
		boolean isRegistrarOnly = true;
		for (GrantedAuthority ga : groups) {
			if (ga.getAuthority().endsWith("admin") || ga.getAuthority().endsWith("ordinator")) {
				isRegistrarOnly = false;
			}
		}
		request.setAttribute("isRegistrar", isRegistrarOnly);
		request.setAttribute("editAuthorizationTask", editTask);
		
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

	public Task getEditTask() {
		return editTask;
	}

	public void setEditTask(Task editTask) {
		this.editTask = editTask;
	}

}

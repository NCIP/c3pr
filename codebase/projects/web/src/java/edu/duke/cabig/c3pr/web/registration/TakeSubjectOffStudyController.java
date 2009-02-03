package edu.duke.cabig.c3pr.web.registration;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;

public class TakeSubjectOffStudyController extends SimpleFormController {
	
	 private StudySubjectDao studySubjectDao;
	 
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		StudySubjectWrapper wrapper = new StudySubjectWrapper();
		String registartionId = request.getParameter("registartionId")  ; 
		if (registartionId != null) {
            StudySubject studySubject = studySubjectDao.getById(Integer.parseInt(registartionId));
            wrapper.setStudySubject(studySubject);
		}
		return wrapper;
	}

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}
	
	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(false));
	}

}

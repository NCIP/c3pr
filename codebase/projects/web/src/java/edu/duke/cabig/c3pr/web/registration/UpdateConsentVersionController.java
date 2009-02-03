/**
 * 
 */
package edu.duke.cabig.c3pr.web.registration;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;

/**
 * @author Himanshu
 *
 */
public class UpdateConsentVersionController extends SimpleFormController {
	
	 private static Log log = LogFactory.getLog(UpdateConsentVersionController.class);
	 private StudySubjectDao studySubjectDao;


	@Override
	protected StudySubjectWrapper formBackingObject(HttpServletRequest request) throws Exception {
		StudySubjectWrapper wrapper = new StudySubjectWrapper();
		String registartionId = request.getParameter("registartionId")  ; 
		if (registartionId != null) {
            StudySubject studySubject = studySubjectDao.getById(Integer.parseInt(registartionId));
            wrapper.setStudySubject(studySubject);
		}
		return wrapper;
	}
	
	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(false));
	}
	
	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}
}

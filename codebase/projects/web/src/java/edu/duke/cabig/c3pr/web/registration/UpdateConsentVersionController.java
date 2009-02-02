/**
 * 
 */
package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.StudySubject;

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
	protected ModelAndView processFormSubmission(HttpServletRequest request,HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		return super.processFormSubmission(request, response, command, errors);
	}

	public void setStudySubjectDao(StudySubjectDao studySubjectDao) {
		this.studySubjectDao = studySubjectDao;
	}

	public StudySubjectDao getStudySubjectDao() {
		return studySubjectDao;
	}
}

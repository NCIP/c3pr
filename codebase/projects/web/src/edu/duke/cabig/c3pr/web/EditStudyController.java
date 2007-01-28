package edu.duke.cabig.c3pr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.Lov;

/**
 * Controller class to handle the work flow in the Updation of a Study Design
 * This uses AbstractWizardController to implement tabbed workflow
 * @author Priyatam
 */
public class EditStudyController extends StudyController {
   		
	protected static final Log log = LogFactory.getLog(EditStudyController.class);
		
	public EditStudyController()
	{
		setBindOnNewForm(true);
	}
	
	/**
	 * Create a nested object graph that Create Study Design needs 
	 * @param request - HttpServletRequest
	 * @throws ServletException
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {	
		Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
		if (study !=null)
		{
			log.debug("Retrieving Study Details for Id: "+study.getId());
		}
		return study;
	}
				
	@Override
	protected void postProcessPage(HttpServletRequest request, Object oCommand,
			Errors errors, int page) throws Exception {
		Study study = (Study) oCommand;
		if (page == 1) {
			handleIdentifierAction(study, request.getParameter("_action"), request
				.getParameter("_selected"));
		}
		if (page ==2) {
			handleStudySiteAction((Study)oCommand,
				request.getParameter("_action"),
				request.getParameter("_selected"));							
		}
		if (page ==3) {
			handleStudyDesignAction((Study)oCommand, 
				request.getParameter("_action"),
				request.getParameter("_selectedEpoch"),
				request.getParameter("_selectedArm"));	
		}		
	
		//update Study
		try {
			log.debug("Updating Study");
			studyDao.save(study);
		} catch (RuntimeException e) {
			log.debug("Unable to update Study");
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish
	 * (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, 
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {
		// Redirect to Search page
		ModelAndView modelAndView= new ModelAndView(new RedirectView("searchstudy.do"));
    	return modelAndView;
	}	
}
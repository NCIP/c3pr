package edu.duke.cabig.c3pr.web;

import java.util.HashMap;

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
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;

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
		Study study = studyDao.getStudyDesignById(Integer.parseInt(request.getParameter("studyId")));
		if (study !=null)
		{
			log.debug("Retrieving Study Details for Id: "+study.getId());
		}
		return study;
	}
	
	protected void layoutTabs(Flow flow, HashMap tabsMap){
		
		flow.addTab((Tab<Study>)tabsMap.get("Overview"));
		flow.addTab((Tab<Study>)tabsMap.get("Registrations"));
		flow.addTab((Tab<Study>)tabsMap.get("Details"));
		flow.addTab((Tab<Study>)tabsMap.get("Identifiers"));
		flow.addTab((Tab<Study>)tabsMap.get("Sites"));
		flow.addTab((Tab<Study>)tabsMap.get("Investigators"));
		flow.addTab((Tab<Study>)tabsMap.get("Personnel"));
		flow.addTab((Tab<Study>)tabsMap.get("Eligibility Checklist"));
		flow.addTab((Tab<Study>)tabsMap.get("Stratifications"));
		flow.addTab((Tab<Study>)tabsMap.get("Diseases"));
		flow.addTab((Tab<Study>)tabsMap.get("Epochs & Arms"));
	}
	
	/**
	 * Overriden here to perform futher processing
	 * @param request
	 * @param command
	 * @param arg2
	 * @param pageNo
	 * @throws Exception
	 */
	protected void postPostProcessPage(HttpServletRequest request, Object command,
			Errors arg2,String tabShortTitle) throws Exception{
		
		Study study = (Study) command;
		System.out.println("request.getParameter(action) - "+request.getParameter("_action"));
		if ("update".equals(request.getParameter("_action"))){
			try {
				log.debug("Updating Study");
				studyDao.save(study);
			} catch (RuntimeException e) {
				log.debug("Unable to update Study");
				throw e;
			}
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
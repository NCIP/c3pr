package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;

/**
 * Controller class to handle the work flow in the Creation of a Study Design
 * This uses AbstractWizardController to implement tabbed workflow
 * @author Priyatam
 */
public class CreateStudyController extends StudyController {
  

	/**
	 * Create a nested object graph that Create Study Design needs 
	 * @param request - HttpServletRequest
	 * @throws ServletException
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {	
		return createDefaultStudyWithDesign();		         
	}	
	
	protected void layoutTabs(Flow flow, HashMap tabsMap){
		flow.addTab((Tab<Study>)tabsMap.get("Details"));
		flow.addTab((Tab<Study>)tabsMap.get("Identifiers"));
		flow.addTab((Tab<Study>)tabsMap.get("Sites"));
		flow.addTab((Tab<Study>)tabsMap.get("Investigators"));
		flow.addTab((Tab<Study>)tabsMap.get("Personnel"));
		flow.addTab((Tab<Study>)tabsMap.get("Eligibility Checklist"));
		flow.addTab((Tab<Study>)tabsMap.get("Stratifications"));
		flow.addTab((Tab<Study>)tabsMap.get("Diseases"));
		flow.addTab((Tab<Study>)tabsMap.get("Epochs & Arms"));
		flow.addTab((Tab<Study>)tabsMap.get("Overview"));				
	}
	
	
	/* (non-Javadoc) 
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish
	 * (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, 
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {		
		Study study = (Study) command;
	
		studyService.save(study);
	
		return new ModelAndView("forward:confirm?type=confirm", errors.getModel());
//	    Map model = new ModelMap("type", "confirm");
//	    request.setAttribute("study", study); 
//	    return new ModelAndView(new RedirectView("confirm", true), model);
	}	
	
}
package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.Lov;

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
	
	@Override
	protected void postProcessPage(HttpServletRequest request, Object command,
			Errors arg2, int pageNo) throws Exception {
		
		switch (pageNo)
		{
			case 1:
				handleIdentifierAction((Study)command,
					request.getParameter("_action"),
					request.getParameter("_selected"));		
				break;
			case 2:
				handleStudySiteAction((Study)command,
					request.getParameter("_action"),
					request.getParameter("_selected"));		
				break;
			case 3:			
				handleStudyDesignAction((Study)command, 
					request.getParameter("_action"),
					request.getParameter("_selectedEpoch"),
					request.getParameter("_selectedArm"));	
				break;			
			case 4:
				Study study = (Study) command;
				
				//cleanup identifiers page if user selected 'please select'
				List<Identifier> newList = new ArrayList<Identifier>();			
				for (Identifier identifier : study.getIdentifiers()) {
					if(!StringUtils.isEmpty(identifier.getSource()) &&
						!StringUtils.isEmpty(identifier.getType()) )
					{
						newList.add(identifier);
					}	
				}					
				study.setIdentifiers(newList);
				
				//review and submit Tab, save study and proceed to final page					
				studyService.save(study);
				break;
			default:
				//do nothing						
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
		ModelAndView modelAndView= new ModelAndView(new RedirectView("searchStudy"));
    	return modelAndView;
	}	
	
}
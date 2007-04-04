package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;

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
	
		
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish
	 * (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, 
	 * java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {		
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
		
		//save study and proceed to final page					
		studyService.save(study);
		
		ModelAndView modelAndView= new ModelAndView("study_confirmation");
    	modelAndView.addAllObjects(errors.getModel());
    	RequestDispatcher rd = request.getRequestDispatcher("confirm?type=confirm");
    	rd.forward(request, response);
    	return null;
	}	
	
}
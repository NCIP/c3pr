package edu.duke.cabig.c3pr.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySite;
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
		
		flow.addTab((Tab<Study>)tabsMap.get("summary"));
		flow.addTab((Tab<Study>)tabsMap.get("registrations"));
		flow.addTab((Tab<Study>)tabsMap.get("details"));
		flow.addTab((Tab<Study>)tabsMap.get("identifiers"));
		flow.addTab((Tab<Study>)tabsMap.get("studysites"));
		flow.addTab((Tab<Study>)tabsMap.get("investigators"));
		flow.addTab((Tab<Study>)tabsMap.get("personnel"));
		flow.addTab((Tab<Study>)tabsMap.get("eligibilityChecklist"));
		flow.addTab((Tab<Study>)tabsMap.get("epochsArms"));
		
	}
	
	@Override
	protected void postProcessPage(HttpServletRequest request, Object command,
			Errors arg2, int pageNo) throws Exception {
		
		switch (pageNo)
		{
			case 2:
				handleIdentifierAction((Study)command,
					request.getParameter("_action"),
					request.getParameter("_selected"));		
				break;	
			case 3:
				handleStudySiteAction((Study)command,
					request.getParameter("_action"),
					request.getParameter("_selected"));		
				break;
			case 4:				
				if("siteChange".equals(request.getParameter("_action")))
				{
					request.getSession().setAttribute("site_id", request.getParameter("_selected"));
					StudySite studySite = ((Study)command).getStudySites().get(Integer.parseInt(request.getParameter("_selected")));
					if(studySite.getStudyInvestigators().size() == 0 )
					{						
						StudyInvestigator studyInvestigator = new StudyInvestigator();	
						studyInvestigator.setSiteInvestigator(new HealthcareSiteInvestigator());
						studySite.addStudyInvestigator(studyInvestigator);
					}
				}
				else {
					handleStudyInvestigatorAction((Study)command, request);
				}					
				
				break;				
			case 5:				
				if("siteChange".equals(request.getParameter("_action")))
				{
					request.getSession().setAttribute("site_id", request.getParameter("_selected"));
					
					StudySite studySite = ((Study)command).getStudySites().get(Integer.parseInt(request.getParameter("_selected")));
					if(studySite.getStudyPersonnels().size() == 0 )
					{						
						StudyPersonnel studyPersonnel = new StudyPersonnel();
						studyPersonnel.setStudySite(studySite);								
						studySite.addStudyPersonnel(studyPersonnel);
					}										
				}
				else {
					handleStudyPersonnelAction((Study)command, request.getParameter("_action"),
						request.getParameter("_selected"), request.getParameter("_studysiteindex"));
				}					
											
				break;				
							
			case 6:			
				handleEligibilityChecklist((Study)command, request);								
				break;	
				
			case 7:			
				handleStudyDesignAction((Study)command, 
					request.getParameter("_action"),
					request.getParameter("_selectedEpoch"),
					request.getParameter("_selectedArm"));	
				break;	
		
			default:
				//do nothing						
		}		
		
		postPostProcessPage(request, command, arg2,pageNo);
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
			Errors arg2, int pageNo) throws Exception{
		
		Study study = (Study) command;
		System.out.println("request.getParameter(action) - "+request.getParameter("_action"));
		if ("update".equals(request.getParameter("_action"))){
			try {
				log.debug("Updating Study");
				studyDao.save(study);
			} catch (RuntimeException e) {
				log.debug("Unable to update Study");
				e.printStackTrace();
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
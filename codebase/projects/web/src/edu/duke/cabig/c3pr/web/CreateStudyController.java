package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySite;
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
		flow.addTab((Tab<Study>)tabsMap.get("details"));
		flow.addTab((Tab<Study>)tabsMap.get("identifiers"));
		flow.addTab((Tab<Study>)tabsMap.get("studysites"));
		flow.addTab((Tab<Study>)tabsMap.get("investigators"));
		flow.addTab((Tab<Study>)tabsMap.get("personnel"));
		flow.addTab((Tab<Study>)tabsMap.get("eligibilityChecklist"));
		flow.addTab((Tab<Study>)tabsMap.get("epochsArms"));
		flow.addTab((Tab<Study>)tabsMap.get("summary"));				
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
			case 4:				
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
							
			case 5:			
				handleEligibilityChecklist((Study)command, request);								
				break;	
				
			case 6:			
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
		
		RequestDispatcher rd = request.getRequestDispatcher("confirm?type=confirm");
		request.setAttribute("command", command);
    	rd.forward(request, response);
    	return null;
	}	
	
}
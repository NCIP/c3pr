package edu.duke.cabig.c3pr.web;

import java.util.HashMap;
import java.util.List;
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

import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.utils.Lov;
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
	
	protected void intializeFlows(Flow<Study> flow)	
	{	   
		 flow.addTab(new Tab<Study>("Study Details", "Study Details", "study/study_edit_details") {
	            public Map<String, Object> referenceData() {
	           	 	Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        
	       	  
	            	Map<String, Object> refdata = new HashMap<String, Object>();
	        		  	            	
	            	refdata.put("diseaseCodeRefData", configMap.get("diseaseCodeRefData"));
	    	  		refdata.put("monitorCodeRefData",  configMap.get("monitorCodeRefData"));
	    	  		refdata.put("phaseCodeRefData",  configMap.get("phaseCodeRefData"));
	    	  		refdata.put("sponsorCodeRefData",  configMap.get("sponsorCodeRefData"));
	    	  		refdata.put("statusRefData",  configMap.get("statusRefData"));
	    	  		refdata.put("typeRefData",  configMap.get("typeRefData"));
	    	  		refdata.put("randomizedIndicatorRefData", configMap.get("yesNo"));
	    	  		refdata.put("multiInstitutionIndicatorRefData", configMap.get("yesNo"));
	    	  		refdata.put("blindedIndicatorRefData", configMap.get("yesNo"));
	    	  	
	    	  		return refdata;
	             }        	
	        });
	        flow.addTab(new Tab<Study>("Study Identifiers", "Study Identifiers", "study/study_edit_identifiers"){
	            
	        	public Map<String, Object> referenceData() {
	        		Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        
	  	       	  
	        		Map<String, Object> refdata = new HashMap<String, Object>();
	        		
	        		refdata.put("identifiersSourceRefData", getHealthcareSites());
	    	  		refdata.put("identifiersTypeRefData", configMap.get("identifiersType"));	
	    	  		return refdata;
	    	 	}
	        });                 
	        flow.addTab(new Tab<Study>("Study Sites", "Study Sites", "study/study_edit_studysites") {
	            
	        	public Map<String, Object> referenceData() {
	        		Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        
	  	       	  
	        		Map<String, Object> refdata = new HashMap<String, Object>();
	        		
	        		refdata.put("healthCareSitesRefData", getHealthcareSites());	  			  	
	    	  		refdata.put("studySiteStatusRefData", configMap.get("studySiteStatusRefData"));
	    	  		refdata.put("studySiteRoleCodeRefData", configMap.get("studySiteRoleCodeRefData"));	  		
	    	  		return refdata;	  		
	         	}        	
	        });

			flow.addTab(new Tab<Study>("Epochs & Arms", "Epochs & Arms", "study/study_edit_design") {
	            
	        	public Map<String, Object> referenceData() {
	        		
	                Map<String, Object> refdata = super.referenceData();
	                return refdata;
	           
	        	}        	
	        });
			
		  setFlow(flow);       
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
				
	@Override
	protected void postProcessPage(HttpServletRequest request, Object oCommand,
			Errors errors, int page) throws Exception {
		Study study = (Study) oCommand;
		
		if (page ==0){
			log.debug("Retrieving Identifiers for Study Id: "+study.getId());
		}
		if (page == 1) {
			log.debug("Retrieving StudySites for Study Id: "+study.getId());			
			handleIdentifierAction(study, request.getParameter("_action"), request
				.getParameter("_selected"));
		}
		else if (page ==2) {
			log.debug("Retrieving Epochs for Study Id: "+study.getId());
			handleStudySiteAction((Study)oCommand,
			request.getParameter("_action"),
			request.getParameter("_selected"));							
		}
		else if (page ==3) {
			handleStudyDesignAction((Study)oCommand, 
			request.getParameter("_action"),
			request.getParameter("_selectedEpoch"),
			request.getParameter("_selectedArm"));	
		}		
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
	
	protected final Object getCommandOnly(HttpServletRequest request) throws Exception {		
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new HttpSessionRequiredException("Must have session when trying to bind (in session-form mode)");
		}
		String formAttrName = getFormSessionAttributeName(request);
		Object sessionFormObject = session.getAttribute(formAttrName);
		if (sessionFormObject == null) {
			throw new HttpSessionRequiredException("Form object not found in session (in session-form mode)");
		}

		return currentFormObject(request, sessionFormObject);
	}
	

}
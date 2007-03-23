package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * Controller class to handle the work flow in the Creation of a Study Design
 * This uses AbstractWizardController to implement tabbed workflow
 * @author Priyatam
 */
public class CreateStudyController extends StudyController {
  
	protected void intializeFlows(Flow<Study> flow)
	{	   
		 flow.addTab(new Tab<Study>("Study Details", "Details", "study/study_details") {
	            public Map<String, Object> referenceData() {
	           	 	Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        
	       	  
	            	Map<String, Object> refdata = new HashMap<String, Object>();
	        		  	            	
	            	refdata.put("searchTypeRefData", configMap.get("studySearchType"));	  	     
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
	        flow.addTab(new Tab<Study>("Study Identifiers", "Identifiers", "study/study_identifiers"){
	            
	        	public Map<String, Object> referenceData() {
	        		Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        
	  	       	  
	        		Map<String, Object> refdata = new HashMap<String, Object>();
	        		
	                refdata.put("identifiersSourceRefData", getHealthcareSites());
	    	  		refdata.put("identifiersTypeRefData", configMap.get("identifiersType"));	  		
	    	  		return refdata;
	        	}
	        });                 
	        flow.addTab(new Tab<Study>("Study Sites", "Sites", "study/study_studysites") {
	            
	        	public Map<String, Object> referenceData() {
	        		Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        
	  	       	  
	        		Map<String, Object> refdata = new HashMap<String, Object>();
	        		
	        		refdata.put("healthCareSitesRefData", getHealthcareSites());	  			  	
	    	  		refdata.put("studySiteStatusRefData", configMap.get("studySiteStatusRefData"));
	    	  		refdata.put("studySiteRoleCodeRefData", configMap.get("studySiteRoleCodeRefData"));	  		
	    	  		return refdata;	           
	        	}        	
	        });
	        
	        flow.addTab(new Tab<Study>("Study Investigators", "Investigators", "study/study_investigators")); 
	        
	        flow.addTab(new Tab<Study>("Study Personnel", "Personnel", "study/study_personnels") {
	            
	        	public Map<String, Object> referenceData() {
	                Map<String, Object> refdata = super.referenceData();  
	                //List<StudySite> studySites = new ArrayList<StudySite>();
	                refdata.put("researchStaffRefData", getResearchStaff());
	                
	                return refdata;           
	        	}        	
	        });

	        flow.addTab(new Tab<Study>("Study Eligibility Checklist", "Eligibility Checklist", 
	        		"study/study_eligibility_checklist") {
	            
	        	public Map<String, Object> referenceData() {
	                Map<String, Object> refdata = super.referenceData();  
	                 
	                return refdata;           
	        	}        	
	        });

			flow.addTab(new Tab<Study>("Epochs & Arms", "Epochs & Arms", "study/study_design") {
	            
	        	public Map<String, Object> referenceData() {
	        		
	                Map<String, Object> refdata = super.referenceData();
	                return refdata;
	           
	        	}        	
	        });
			
			flow.addTab(new Tab<Study>("Review and Submit ", "Review & Submit", "study/study_reviewsummary"));
               
	        setFlow(flow);       
	}	
	
	private List<ResearchStaff> getResearchStaff()
	{
		List<ResearchStaff> researchStaffs = new ArrayList<ResearchStaff>();
		
		ResearchStaff researchStaff1 = new ResearchStaff();
		researchStaff1.setId(1);
		researchStaff1.setFirstName("Research Staff 1");
		researchStaffs.add(researchStaff1);
		
		ResearchStaff researchStaff2 = new ResearchStaff();
		researchStaff2.setId(2);
		researchStaff2.setFirstName("Research Staff 2");
		researchStaffs.add(researchStaff2);
		
		return researchStaffs;  	
	}
		
	
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
				if("siteChange".equals(request.getParameter("_action")))
				{
					request.getSession().setAttribute("site_id", request.getParameter("_selected"));
					StudySite studySite = ((Study)command).getStudySites().get(Integer.parseInt(request.getParameter("_selected")));
					if(studySite.getStudyInvestigators().size() == 0 )
					{						
						StudyInvestigator studyInvestigator = new StudyInvestigator();	
						studyInvestigator.setSiteInvestigator(new HealthcareSiteInvestigator());
						studySite.addStudyInvestigators(studyInvestigator);
					}
				}
				else {
					handleStudyInvestigatorAction((Study)command, request.getParameter("_action"),
							request.getParameter("_selected"), request.getParameter("_studysiteindex"));
				}					
				
				break;				
			case 4:				
				if("siteChange".equals(request.getParameter("_action")))
				{
					request.getSession().setAttribute("site_id_for_per", request.getParameter("_selected"));
					
					StudySite studySite = ((Study)command).getStudySites().get(Integer.parseInt(request.getParameter("_selected")));
					if(studySite.getStudyPersonnels().size() == 0 )
					{						
						StudyPersonnel studyPersonnel = new StudyPersonnel();						
						studySite.addStudyPersonnel(studyPersonnel);
					}										
				}
				else {
					handleStudyPersonnelAction((Study)command, request.getParameter("_action"),
							request.getParameter("_selected"), request.getParameter("_studysiteindex"));
				}					
				
				break;				
							
			case 5:			
				
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
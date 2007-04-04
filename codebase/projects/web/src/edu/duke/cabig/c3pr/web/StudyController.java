package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.NullIdDaoBasedEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AbstractTabbedFlowFormController;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;


/**
 * Base Controller class to handle the basic work flow in the Creation / Updation of a Study Design
 * This uses AbstractWizardController to implement tabbed workflow
 * @author Priyatam
 */
public abstract class StudyController extends AbstractTabbedFlowFormController<Study>{
    protected static final Log log = LogFactory.getLog(StudyController.class);
	protected StudyService studyService;
	protected StudyDao studyDao;
	protected HealthcareSiteDao healthcareSiteDao;
	protected HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
	protected ResearchStaffDao researchStaffDao;	
	
	protected StudyValidator studyValidator;
	protected ConfigurationProperty configurationProperty;
	protected static List<HealthcareSite> healthcareSites;

	public StudyController(ConfigurationProperty configurationProperty) {		
		setCommandClass(Study.class);        	       
		this.configurationProperty=configurationProperty;        
        Flow<Study> flow = new Flow<Study>("Create Study");               
        intializeFlows(flow);
    }
	
	public StudyController() {		
		setCommandClass(Study.class);        	       
	    Flow<Study> flow = new Flow<Study>("Create Study");               
        intializeFlows(flow);
    }
	
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
	        
	        flow.addTab(new Tab<Study>("Study Investigators", "Investigators", 
	        	"study/study_investigators") {	            
	        	public Map<String, Object> referenceData() {
	        		Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        	  	       	  
	        		Map<String, Object> refdata = new HashMap<String, Object>();
	        		refdata.put("studyInvestigatorRoleRefData", configMap.get("studyInvestigatorRole"));
		            refdata.put("studyInvestigatorStatusRefData", configMap.get("studyInvestigatorStatus"));		            
	        		return refdata;	           
	        	}        	
	        });
	        
	        flow.addTab(new Tab<Study>("Study Personnel", "Personnel", "study/study_personnels") {	            
	        	public Map<String, Object> referenceData() {
	        		Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        		  	       	  
	                Map<String, Object> refdata = super.referenceData();  
	                refdata.put("studyPersonnelRoleRefData", configMap.get("studyPersonnelRole"));
	                refdata.put("studyPersonnelStatusRefData", configMap.get("studyPersonnelStatus"));	                          
	                return refdata;           
	        	}        	
	        });

	        flow.addTab(new Tab<Study>("Study Eligibility Checklist", "Eligibility Checklist", 
	        		"study/study_eligibility_checklist"){	            
	        	public Map<String, Object> referenceData() {	        		
	                Map<String, Object> refdata = super.referenceData();
	                return refdata;	           
	        	}        	
	        });

			flow.addTab(new Tab<Study>("Epochs & Arms", "Epochs & Arms", "study/study_design") {	            
	        	public Map<String, Object> referenceData() {	        		
	        		Map<String, Object> refdata = super.referenceData();  
		            refdata.put("currentOperation", "inclusion");		               
	                return refdata;	           
	        	}        	
	        });
			
			flow.addTab(new Tab<Study>("Review and Submit ", "Review & Submit", 
				"study/study_reviewsummary"));
               
	        setFlow(flow);       
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
	
	/**
	 * Override this in Edit Study Controller to perform futher processing
	 * @param request
	 * @param command
	 * @param arg2
	 * @param pageNo
	 * @throws Exception
	 */
	protected void postPostProcessPage(HttpServletRequest request, Object command,
			Errors arg2, int pageNo) throws Exception{
		//empty default implementation
	}
	
	
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(false));
        binder.registerCustomEditor(healthcareSiteDao.domainClass(),
        	new CustomDaoEditor(healthcareSiteDao));
        binder.registerCustomEditor(healthcareSiteInvestigatorDao.domainClass(),
            new NullIdDaoBasedEditor(healthcareSiteInvestigatorDao));       
        binder.registerCustomEditor(researchStaffDao.domainClass(),
            new NullIdDaoBasedEditor(researchStaffDao));           
     }
	
	/**
	 * Hook to imlement this in subclass (depending on create/edit)
	 * @param request - HttpServletRequest
	 * @throws ServletException
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {	
		// implement this in sub class
		return null;
	}
	
	/**
	* Method for custom validation logic for individual pages
	* @param command - form object with the current wizard state
	* @param errors - validation errors holder
	* @param page - number of page to validate
	*/
	
	protected void validatePage(Object command, Errors errors, int page, boolean finish) {
		Study study = (Study) command;
		switch (page) {
		case 0:
			studyValidator.validateStudy(study, errors);
			break;
		case 1:
		//	studyValidator.validateIdentifiers(study, errors);
			break;
		case 2:
		//	studyValidator.validateStudySites(study, errors);
			break;
		case 3:
		//	studyValidator.validateStudyDesign(study, errors);
			break;
		}
	}	

	protected void handleIdentifierAction(Study study, String action, String selected)
	{				
		if ("addIdentifier".equals(action))
		{	
			log.debug("Requested Add Identifier");	
			Identifier id = new Identifier();
			id.setValue("<enter value>");
			study.addIdentifier(id);		
		}
		else if ("removeIdentifier".equals(action))
		{
			log.debug("Requested Remove Identifier");	
			study.getIdentifiers().remove(Integer.parseInt(selected));
		}					
	}
	
	protected void handleStudySiteAction(Study study, String action, String selected)
	{
		if ("addStudySite".equals(action))
		{	
			log.debug("Requested Add Study Site");																														
			createDefaultStudySite(study);		
		}
		else if ("removeStudySite".equals(action))
		{
			log.debug("Requested Remove Study Site");		
			study.getStudySites().remove(Integer.parseInt(selected));
		}		
	}
	
	protected void handleStudyDesignAction(Study study, String action,
		String selectedEpoch, String selectedArm)
	{
		if ("addEpoch".equals(action))
		{	
			log.debug("Requested Add Epoch");																														
			study.addEpoch(Epoch.create("New Epoch", "Arm A", "Arm B", "Arm C"));				
		}
		else if ("addArm".equals(action))
		{
			log.debug("Requested Add Arm");																																		
			Epoch epoch = study.getEpochs().get(Integer.parseInt(selectedEpoch));
			epoch.addArm(new Arm());
		}
		else if ("removeEpoch".equals(action))				
		{	
			log.debug("Requested Remove Epoch");		
			study.getEpochs().remove(Integer.parseInt(selectedEpoch));
		}
		else if ("removeArm".equals(action))
		{
			log.debug("Requested Remove Arm");																																		
			Epoch epoch = study.getEpochs().get(Integer.parseInt(selectedEpoch));
			epoch.getArms().remove(Integer.parseInt(selectedArm));			
		}		
	}
	
	protected void handleStudyInvestigatorAction(Study study, HttpServletRequest request)
	{				
		String action =request.getParameter("_action");
		String selectedSite = request.getParameter("_selected"); 
		String studysiteindex = request.getParameter("_studysiteindex");
		
		if ("addInv".equals(action))
		{				
			StudyInvestigator studyInvestigator = new StudyInvestigator();
			StudySite studySite = study.getStudySites().get(Integer.parseInt(selectedSite));			
			studySite.addStudyInvestigator(studyInvestigator);														
		}
		else if ("removeInv".equals(action))
		{	
			study.getStudySites().get(Integer.parseInt(studysiteindex)).getStudyInvestigators().remove(Integer.parseInt(selectedSite));
		}										
	}	
	
	protected void handleStudyPersonnelAction(Study study, String action, String selected, String studysiteindex)
	{				
		if ("addStudyPersonnel".equals(action))
		{	
			StudyPersonnel studyPersonnel = new StudyPersonnel();
			studyPersonnel.setResearchStaff(new ResearchStaff());
			StudySite studySite = study.getStudySites().get(Integer.parseInt(selected));
			studyPersonnel.setStudySite(studySite);		
			studySite.addStudyPersonnel(studyPersonnel);														
		}
		else if ("removeStudyPersonnel".equals(action))
		{	
			study.getStudySites().get(Integer.parseInt(studysiteindex)).getStudyPersonnels().remove(Integer.parseInt(selected));
		}					
					
	}	
	
	protected void handleEligibilityChecklist(Study study, HttpServletRequest request)
	{
		String action =request.getParameter("_action");
		String selected = request.getParameter("_selected"); 
		String target = request.getParameter("_target6"); 
		
		if ("addInclusionCriteria".equals(action))
		{	
			log.debug("Requested - Add a Inclusion Eligibility Criteria");	
			request.setAttribute("currentOperation", "inclusion");
			createDefaultInclusion(study);		
		}
		else if ("removeInclusionCriteria".equals(action))
		{
			log.debug("Requested - Remove an Inclusion Eligibility Criteria");		
			request.setAttribute("currentOperation", "inclusion");			
			study.getIncCriterias().remove(Integer.parseInt(selected));
		}	
		if ("addExclusionCriteria".equals(action))
		{	
			log.debug("Requested - Add an Exclusion Eligibility Criteria");	
			request.setAttribute("currentOperation", "exclusion");			
			createDefaultExclusion(study);		
		}
		else if ("removeExclusionCriteria".equals(action))
		{
			log.debug("Requested - Remove an Exclusion Eligibility Criteria");		
			request.setAttribute("currentOperation", "exclusion");			
			study.getExcCriterias().remove(Integer.parseInt(selected));
		}	
	}
		
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {
		// implement in subclass
		 return null;
	}
	
	/**
	 * Need to create a default study with 3 epochs and associated arms
	 * this is shown to the User for the first time
	 * @return Study the default study
	 */
	protected Study createDefaultStudyWithDesign()
	{
		Study study = new Study(); 
	
		createDefaultEpochs(study);
		createDefaultStudySite(study);		
		createDefaultIdentifiers(study);
							
		return study;
	}	
	
	protected void createDefaultEpochs(Study study)
	{
		study.addEpoch(Epoch.create("Screening"));
		study.addEpoch(Epoch.create("Treatment", "Arm A", "Arm B", "Arm C"));
		study.addEpoch(Epoch.create("Follow up"));          						
	}
	
	protected void createDefaultStudySite(Study study)
	{
		StudySite studySite = new StudySite();
		createDefaultHealthcareSite(studySite);	
		//createDefaultStudyInvestigators(studySite);
		//createDefaultStudyPersonnel(studySite);
		study.addStudySite(studySite);					
	}	
	
	protected void createDefaultStudyInvestigators(StudySite studySite)
	{
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		studySite.addStudyInvestigator(studyInvestigator);
	}
	protected void createDefaultStudyPersonnel(StudySite studySite)
	{
		StudyPersonnel studyPersonnel = new StudyPersonnel();
		studySite.addStudyPersonnel(studyPersonnel);
	}
	
	protected void createDefaultHealthcareSite(StudySite studySite)
	{
		healthcareSites = getHealthcareSites();
		for (HealthcareSite site : healthcareSites) {
			studySite.setSite(site);
		}		
	}
	
	protected void createDefaultIdentifiers(Study study)
	{
		List<Identifier> identifiers = new ArrayList<Identifier>();
		Identifier id1 = new Identifier();	
		id1.setPrimaryIndicator(true);
		identifiers.add(id1);
		Identifier id2 = new Identifier();	
		identifiers.add(id2);		
		study.setIdentifiers(identifiers);		
	}
	
	protected void createDefaultExclusion(Study study)
	{
		ExclusionEligibilityCriteria exc = new ExclusionEligibilityCriteria();	
		exc.setQuestionNumber(study.getExcCriterias().size()+1);
		exc.setQuestionText("");
	
		study.addExclusionEligibilityCriteria(exc);			
	}
	
	protected void createDefaultInclusion(Study study)
	{
		InclusionEligibilityCriteria inc = new InclusionEligibilityCriteria();	
		inc.setQuestionNumber(study.getIncCriterias().size()+1);
		inc.setQuestionText("");
		study.addInclusionEligibilityCriteria(inc);			
	}
		
	protected List<HealthcareSite> getHealthcareSites()
	{
  		return healthcareSiteDao.getAll();  	
	}	

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}
	
	
	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public StudyService getStudyService() {
		return studyService;
	}

	public void setStudyService(StudyService studyService) {
		this.studyService = studyService;
	}	
		
	public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}

	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}

	public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}

	public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
		return healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

	public ResearchStaffDao getResearchStaffDao() {
		return researchStaffDao;
	}

	public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}

}
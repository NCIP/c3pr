package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.DiseaseTermDao;
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
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.NullIdDaoBasedEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.ObjectGraphBasedEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AbstractTabbedFlowFormController;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab;


/**
 * Base Controller class to handle the basic work flow in the Creation / Updation of a Study Design
 * This uses AbstractTabbedFlowFormController to implement tabbed workflow
 * @author Priyatam
 */
public abstract class StudyController extends AbstractTabbedFlowFormController<Study>{
    protected static final Log log = LogFactory.getLog(StudyController.class);
	protected StudyService studyService;
	protected StudyDao studyDao;
	protected HealthcareSiteDao healthcareSiteDao;
	protected HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
	protected ResearchStaffDao researchStaffDao;
	private DiseaseTermDao diseaseTermDao;
	
	protected StudyValidator studyValidator;
	protected ConfigurationProperty configurationProperty;
	protected static List<HealthcareSite> healthcareSites;
	
	public StudyController() {		
		setCommandClass(Study.class);        	       
	    Flow<Study> flow = new Flow<Study>("Create Study");    
        intializeTabs(flow);
    }
	
	protected void intializeTabs(Flow<Study> flow)
	{	   
		Tab details = new Tab<Study>("Study Details", "Details", "study/study_details") {
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
    	  		refdata.put("coordinatingCenters", configMap.get("coordinatingCenters"));
    	  		refdata.put("randomizedIndicatorRefData", configMap.get("yesNo"));
    	  		refdata.put("multiInstitutionIndicatorRefData", configMap.get("yesNo"));
    	  		refdata.put("blindedIndicatorRefData", configMap.get("yesNo"));	    	  		
    	  		return refdata;
            }        	
		};
			
		Tab identifiers = new Tab<Study>("Study Identifiers", "Identifiers", "study/study_identifiers"){	            
        	public Map<String, Object> referenceData() {
        		Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        	  	       	  
        		Map<String, Object> refdata = new HashMap<String, Object>();	        		
                refdata.put("identifiersSourceRefData", getHealthcareSites());
    	  		refdata.put("identifiersTypeRefData", configMap.get("identifiersType"));	  		
    	  		return refdata;
        	}
        };	
			
			
	   Tab studySites = new Tab<Study>("Study Sites", "Sites", "study/study_studysites") {
           
       	public Map<String, Object> referenceData() {
       		Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        	  	       	  
       		Map<String, Object> refdata = new HashMap<String, Object>();	        		
       		refdata.put("healthCareSitesRefData", getHealthcareSites());	
       		refdata.put("studySiteStatusRefData", configMap.get("studySiteStatusRefData"));
   	  		refdata.put("studySiteRoleCodeRefData", configMap.get("studySiteRoleCodeRefData"));	  			    	  	
   	  		return refdata;	           
       	}        	
       };              
	      
       Tab investigators = new Tab<Study>("Study Investigators", "Investigators", "study/study_investigators") {	            
       	public Map<String, Object> referenceData() {
       		Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        	  	       	  
       		Map<String, Object> refdata = new HashMap<String, Object>();
       		refdata.put("studyInvestigatorRoleRefData", configMap.get("studyInvestigatorRole"));
	        refdata.put("studyInvestigatorStatusRefData", configMap.get("studyInvestigatorStatus"));		            
       		return refdata;	           
       	}        	
       };
	      
       Tab personnel = new Tab<Study>("Study Personnel", "Personnel", "study/study_personnel") {	            
       	public Map<String, Object> referenceData() {
    		Map <String, List<Lov>> configMap = configurationProperty.getMap();        		        		  	       	  
            Map<String, Object> refdata = super.referenceData();  
            refdata.put("studyPersonnelRoleRefData", configMap.get("studyPersonnelRole"));
            refdata.put("studyPersonnelStatusRefData", configMap.get("studyPersonnelStatus"));	                          
            return refdata;           
    	}        	
       };
	      
       Tab eligibilityChecklist = new Tab<Study>("Study Eligibility Checklist", "Eligibility Checklist", 
		"study/study_eligibility_checklist"){	            
       	public Map<String, Object> referenceData() {	        		
               Map<String, Object> refdata = super.referenceData();
               return refdata;	           
       	}        	
       };
       
       Tab diseases = new Tab<Study>("Diseases", "Diseases", "study/study_diseases") {
       	public Map<String, Object> referenceData() {
               Map<String, Object> refdata = super.referenceData();
               return refdata;
       	}        	
       };
       
       Tab stratifications = new Tab<Study>("Stratification Factors", "Stratification Factors", "study/study_stratifications") {
      	public Map<String, Object> referenceData() {
              Map<String, Object> refdata = super.referenceData();
              return refdata;
      	}        	
       };

	   Tab epochsArms = new Tab<Study>("Epochs & Arms", "Epochs & Arms", "study/study_design") {	            
        	public Map<String, Object> referenceData() {	        		
        		Map<String, Object> refdata = super.referenceData();  
	            refdata.put("currentOperation", "inclusion");		               
                return refdata;	           
        	}        	
        };
        
		
		Tab registrations = new Tab<Study>("Registrations", "Registrations","study/study_registrations"){	            
			public Map<String, Object> referenceData(Study study) {
				Map<String, Object> refdata = super.referenceData();  
				refdata.put("participantAssignments", getParticipantAssignments(study));		               
                return refdata;	           
        	}          	
        };
			
    	Tab overview =new Tab<Study>("Overview", "Overview", "study/study_reviewsummary"){	            
			public Map<String, Object> referenceData(Study study) {
				return super.referenceData();  
		           
        	}          	
			 public boolean isAllowDirtyForward() {
				 return false;
			 }
        };
       
		HashMap<String, Tab> tabsMap = new HashMap<String, Tab>();
		tabsMap.put("Details", details);
		tabsMap.put("Identifiers", identifiers);
		tabsMap.put("Sites", studySites);
		tabsMap.put("Investigators", investigators);
		tabsMap.put("Personnel", personnel);
		tabsMap.put("Eligibility Checklist", eligibilityChecklist);
		tabsMap.put("Diseases", diseases);
		tabsMap.put("Stratifications", stratifications);
		tabsMap.put("Epochs & Arms", epochsArms);
		tabsMap.put("Overview", overview);
		tabsMap.put("Registrations", registrations);
		
		layoutTabs(flow, tabsMap);
		
		setFlow(flow);       
	}	
	
	protected void postProcessPage(HttpServletRequest request, Object command,
			Errors arg2, String tabShortTitle) throws Exception {
/*		Study study = (Study) command;
		System.out.println("request.getParameter(action) - "+request.getParameter("_action"));
		try {
			log.debug("Updating Study");
			studyDao.save(study);
		} catch (RuntimeException e) {
			log.debug("Unable to update Study");
			throw e;
		}
*//*		HealthcareSite temp=healthcareSiteDao.getById(10001);
		HealthcareSiteInvestigator temp2=healthcareSiteInvestigatorDao.getById(10002);
*///		Study command=studyDao.getById(((Study)studyCommand).getId());
		if ("Identifiers".equals(tabShortTitle)){
			handleIdentifierAction((Study)command,
				request.getParameter("_action"),
				request.getParameter("_selected"));			
		}
		else if ("Sites".equals(tabShortTitle)){
			handleStudySiteAction((Study)command,
				request.getParameter("_action"),
				request.getParameter("_selected"));		
		}
		else if ("Investigators".equals(tabShortTitle)){
				if("siteChange".equals(request.getParameter("_action")))
				{
					request.getSession().setAttribute("selectedSite", request.getParameter("_selectedSite"));
					
					StudySite studySite = ((Study)command).getStudySites().get(Integer.parseInt(request.getParameter("_selectedSite")));
					
				}
				else {
					handleStudyInvestigatorAction((Study)command, request);
				}							
		}
		else if ("Personnel".equals(tabShortTitle)){
			if("siteChange".equals(request.getParameter("_action")))
			{
				request.getSession().setAttribute("selectedSite", request.getParameter("_selectedSite"));
				
				StudySite studySite = ((Study)command).getStudySites().get(Integer.parseInt(request.getParameter("_selectedSite")));
				if(studySite.getStudyPersonnels().size() == 0 )
				{						
					StudyPersonnel studyPersonnel = new StudyPersonnel();
					studyPersonnel.setStudySite(studySite);								
					studySite.addStudyPersonnel(studyPersonnel);
				}										
			}
			else {
				handleStudyPersonnelAction((Study)command, request);
			}		
		}			
							
		else if ("Eligibility Checklist".equals(tabShortTitle)){		
				handleEligibilityChecklist((Study)command, request);								
		}
		else if ("Stratification Factors".equals(tabShortTitle)){	
			String selected = request.getParameter("_selected");
			
			if("displayStratification".equals(request.getParameter("_action"))) {
				request.getSession().setAttribute("selectedStratification", request.getParameter("_selectedStratification"));
				request.getSession().setAttribute("selectedAnswer", 0);
			}
			else if("displayAnswer".equals(request.getParameter("_action"))) {
				request.getSession().setAttribute("selectedStratification", request.getParameter("_selectedStratification"));
				request.getSession().setAttribute("selectedAnswer", request.getParameter("_selectedAnswer"));
			}
			else				
				handleStratificationAction((Study)command, request);							
		}
		else if ("Diseases".equals(tabShortTitle)){		
			handleDiseasesAction((Study)command, request.getParameter("_action"),
					request.getParameter("_selected"));							
		}
				
		else if ("Epochs & Arms".equals(tabShortTitle)){			
				handleStudyDesignAction((Study)command, 
					request.getParameter("_action"),
					request.getParameter("_selectedEpoch"),
					request.getParameter("_selectedArm"));	
		}
		
//		postPostProcessPage(request, command, arg2, tabShortTitle);
		postPostProcessPage(request, command, arg2, tabShortTitle);
	}
	/**
	 * Template method to let the subclass decide the order of tabs
	 *
	 */
	protected abstract void layoutTabs(Flow flow, HashMap tabsMap);
	
	
	
	/**
	 * Template method to perform futher processing in postProcessPage
	 * @param request
	 * @param command
	 * @param arg2
	 * @param pageNo
	 * @throws Exception
	 */
	protected void postPostProcessPage(HttpServletRequest request, Object command,
			Errors arg2, String tabShortTitle) throws Exception{
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

	protected void handleIdentifierAction(Study study, String action, String selected)
	{				
		if ("addIdentifier".equals(action))
		{	
			log.debug("Requested Add Identifier");	
			Identifier id = new Identifier();
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
		String selectedSite = request.getParameter("_selectedSite"); 
		String selectedInvestigator = request.getParameter("_selectedInvestigator");
		
		if ("addInv".equals(action))
		{				
			StudyInvestigator studyInvestigator = new StudyInvestigator();
			StudySite studySite = study.getStudySites().get(Integer.parseInt(selectedSite));			
			studySite.addStudyInvestigator(studyInvestigator);	
		}
		else if ("removeInv".equals(action))
		{	
			study.getStudySites().get(Integer.parseInt(selectedSite)).getStudyInvestigators()
				.remove(Integer.parseInt(selectedInvestigator));
		}										
	}	
	
	protected void handleStudyPersonnelAction(Study study, HttpServletRequest request)
	{			
		String action =request.getParameter("_action");
		String selectedSite = request.getParameter("_selectedSite"); 
		String selectedPersonnel = request.getParameter("_selectedPersonnel");
		
		if ("addStudyPersonnel".equals(action))
		{	
			StudyPersonnel studyPersonnel = new StudyPersonnel();
			studyPersonnel.setResearchStaff(new ResearchStaff());
			StudySite studySite = study.getStudySites().get(Integer.parseInt(selectedSite));
			studyPersonnel.setStudySite(studySite);		
			studySite.addStudyPersonnel(studyPersonnel);														
		}
		else if ("removeStudyPersonnel".equals(action))
		{	
			study.getStudySites().get(Integer.parseInt(selectedSite)).getStudyPersonnels()
				.remove(Integer.parseInt(selectedPersonnel));
		}					
					
	}	
	
	protected void handleEligibilityChecklist(Study study, HttpServletRequest request)
	{
		String action =request.getParameter("_action");
		String selected = request.getParameter("_selected"); 
		
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
	
	protected void handleStratificationAction(Study study, HttpServletRequest request)
	{
		String action =request.getParameter("_action");
		int selectedStratification= 0;
		int selectedAnswer = 0;
		
		if (StringUtils.isNotEmpty(request.getParameter("_selectedStratification")))
		{
			selectedStratification = Integer.parseInt(request.getParameter("_selectedStratification")); 
		}
		if (StringUtils.isNotEmpty(request.getParameter("_selectedAnswer")))
		{
			selectedAnswer = Integer.parseInt(request.getParameter("_selectedAnswer"));
		}
		if ("addStratificationQuestion".equals(action))
		{	
			log.debug("Requested - Add a Stratication Question");	
			createDefaultStratification(study);		
			request.getSession().setAttribute("selectedStratification", selectedStratification+1);
			request.getSession().setAttribute("selectedAnswer",0);
		}
		else if ("removeStratificationQuestion".equals(action))
		{
			log.debug("Requested - Remove a Stratication Question");		
			study.getStratificationCriteria().remove(selectedStratification);
			selectedStratification = (selectedStratification != 0) ? selectedStratification : 1;
			request.getSession().setAttribute("selectedStratification", selectedStratification-1);
		}
		else if ("addPermissibleAnswer".equals(action))
		{
			log.debug("Requested - Add a Permissible Answer");
			StratificationCriterion cri = study.getStratificationCriteria().get(selectedStratification);
			cri.addPermissibleAnswer(new StratificationCriterionPermissibleAnswer());
			request.getSession().setAttribute("selectedAnswer",selectedAnswer+1);
		}
		else if ("removePermissibleAnswer".equals(action))
		{
			log.debug("Requested - Remove a Permissible Answer");																																		
			StratificationCriterion cri = study.getStratificationCriteria().get(selectedStratification);
			cri.getPermissibleAnswers().remove(selectedAnswer);
			selectedAnswer = (selectedAnswer != 0) ? selectedAnswer : 1;
			request.getSession().setAttribute("selectedAnswer",selectedAnswer-1);
		}		
		else
		{
			request.getSession().setAttribute("selectedStratification", 0);
			request.getSession().setAttribute("selectedAnswer",0);
		}
	}
	
	private void handleDiseasesAction(Study study, String action, String selected)
	{				
		if ("addStudyDisease".equals(action))
		{
			String[] diseases = study.getDiseaseTermIds();
			log.debug("Study Diseases Size : " + study.getStudyDiseases().size());
			for (String diseaseId : diseases) {
				log.debug("Disease Id : " + diseaseId);
				StudyDisease studyDisease = new StudyDisease();
				studyDisease.setDiseaseTerm(diseaseTermDao.getById(Integer.parseInt(diseaseId)));
				study.addStudyDisease(studyDisease);
			}
		}
		else if ("removeStudyDisease".equals(action))
		{				
			study.getStudyDiseases().remove(Integer.parseInt(selected));
		}					
	}
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {
		// implement in subclass
		 return null;
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
	
	/**
	 * Need to create a default study with default associated Collection objects
	 * Need this to bind empty collections in Spring <form> tag
	 * @return Study with the defaults
	 */
	protected Study createDefaultStudyWithDesign()
	{
		Study study = new Study(); 
	
		createDefaultEpochs(study);
		createDefaultStudySite(study);		
		createDefaultIdentifiers(study);
		createDefaultExclusion(study);
		createDefaultInclusion(study);
//		createDefaultStratification(study);
		
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
	//	createDefaultStudyInvestigators(studySite);
	//	createDefaultStudyPersonnel(studySite);
		study.addStudySite(studySite);					
	}	
	
	protected void createDefaultStudyInvestigators(StudySite studySite)
	{
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		studySite.addStudyInvestigator(studyInvestigator);
		createDefaultHealthcareSiteInvestigator(studyInvestigator);
	}
	
	protected void createDefaultStudyPersonnel(StudySite studySite)
	{
		StudyPersonnel studyPersonnel = new StudyPersonnel();
		studySite.addStudyPersonnel(studyPersonnel);
		createDefaultResearchStaff(studyPersonnel);
	}
	
	protected void createDefaultHealthcareSiteInvestigator(StudyInvestigator si)
	{
		HealthcareSiteInvestigator hsi = new HealthcareSiteInvestigator();
		hsi.setHealthcareSite(si.getStudySite().getSite());
		hsi.setInvestigator(new Investigator());
		hsi.addStudyInvestigator(si);			
		//need to save
		healthcareSiteInvestigatorDao.save(hsi);
	}
	
	protected void createDefaultResearchStaff(StudyPersonnel sp)
	{
		ResearchStaff staff = new ResearchStaff();
		staff.addStudyPersonnel(sp);
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
		Identifier id2 = new Identifier();	
		identifiers.add(id1);	
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
	
	protected void createDefaultStratification(Study study)
	{
		StratificationCriterionPermissibleAnswer ans = new StratificationCriterionPermissibleAnswer();
		StratificationCriterion cri = new StratificationCriterion();
		cri.addPermissibleAnswer(ans);
		cri.setQuestionNumber(study.getStratificationCriteria().size()+1);
		study.addStratificationCriteria(cri);
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
	
	protected final List<StudyParticipantAssignment> getParticipantAssignments(Study study)
	{
  		return studyDao.getStudyParticipantAssignmentsForStudy(study.getId());		
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

	public DiseaseTermDao getDiseaseTermDao() {
		return diseaseTermDao;
	}

	public void setDiseaseTermDao(DiseaseTermDao diseaseTermDao) {
		this.diseaseTermDao = diseaseTermDao;
	}

}
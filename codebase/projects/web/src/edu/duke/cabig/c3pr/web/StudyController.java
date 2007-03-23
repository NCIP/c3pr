package edu.duke.cabig.c3pr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AbstractTabbedFlowFormController;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Flow;


import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;


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
	
	abstract protected void intializeFlows(Flow<Study> flow);
	
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(false));
        binder.registerCustomEditor(healthcareSiteDao.domainClass(),
        	new CustomDaoEditor(healthcareSiteDao));
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
	
	protected void handleStudyInvestigatorAction(Study study, String action, String selected, String studysiteindex)
	{				
		if ("addInv".equals(action))
		{	
			StudyInvestigator studyInvestigator = new StudyInvestigator();
			studyInvestigator.setSiteInvestigator(new HealthcareSiteInvestigator());
			StudySite studySite = study.getStudySites().get(Integer.parseInt(studysiteindex));
			studySite.addStudyInvestigators(studyInvestigator);														
		}
		else if ("removeInv".equals(action))
		{	
			study.getStudySites().get(Integer.parseInt(studysiteindex)).getStudyInvestigators().remove(Integer.parseInt(selected));
		}										
	}	
	
	protected void handleStudyPersonnelAction(Study study, String action, String selected, String studysiteindex)
	{				
		if ("addInv".equals(action))
		{	
			StudyPersonnel studyPersonnel = new StudyPersonnel();
			StudySite studySite = study.getStudySites().get(Integer.parseInt(studysiteindex));
			studySite.addStudyPersonnel(studyPersonnel);														
		}
		else if ("removeInv".equals(action))
		{	
			study.getStudySites().get(Integer.parseInt(studysiteindex)).getStudyPersonnels().remove(Integer.parseInt(selected));
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
		createDefaultStudyInvestigators(studySite);
		
		study.addStudySite(studySite);					
	}
	
	protected void createDefaultStudyInvestigators(StudySite studySite)
	{
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		HealthcareSiteInvestigator hc = new HealthcareSiteInvestigator();
		hc.setInvestigator(new Investigator());
		studyInvestigator.setHealthcareSiteInvestigator(hc);
		studySite.addStudyInvestigators(studyInvestigator);
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

}
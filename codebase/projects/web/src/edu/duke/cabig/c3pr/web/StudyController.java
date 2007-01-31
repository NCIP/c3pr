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
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;

/**
 * Base Controller class to handle the basic work flow in the Creation / Updation of a Study Design
 * This uses AbstractWizardController to implement tabbed workflow
 * @author Priyatam
 */
public abstract class StudyController extends AbstractWizardFormController {
    protected static final Log log = LogFactory.getLog(StudyController.class);
	protected StudyService studyService;
	protected StudyDao studyDao;
	protected HealthcareSiteDao healthcareSiteDao;
	protected StudyValidator studyValidator;
	protected ConfigurationProperty configurationProperty;
	protected static List<HealthcareSite> healthcareSites;

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
	
	protected void validatePage(Object command, Errors errors, int page) {
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
	
	protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest, int page) 
		throws Exception {
		// Currently the static data is a hack for an LOV this will be replaced with 
		// LOVDao to get the static data from individual tables
		Map<String, Object> refdata = new HashMap<String, Object>();
		Map <String, List<Lov>> configMap = configurationProperty.getMap();
		
	  	if (page == 0) {
	  		refdata.put("studySearchTypeRefData", configMap.get("studySearchType"));	  	     
	  		refdata.put("diseaseCodeRefData", configMap.get("diseaseCodeRefData"));
	  		refdata.put("monitorCodeRefData",  configMap.get("monitorCodeRefData"));
	  		refdata.put("phaseCodeRefData",  configMap.get("phaseCodeRefData"));
	  		refdata.put("sponsorCodeRefData",  configMap.get("sponsorCodeRefData"));
	  		refdata.put("statusRefData",  configMap.get("statusRefData"));
	  		refdata.put("typeRefData",  configMap.get("typeRefData"));
	  		return refdata;
	  	}	  	
		if (page == 1) {
	  		refdata.put("identifiersSourceRefData", getHealthcareSites());
	  		refdata.put("identifiersTypeRefData", configMap.get("identifiersType"));	  		
	  		return refdata;	  		
	  	}
	  	if (page == 2) {
	  		refdata.put("healthCareSitesRefData", getHealthcareSites());	  			  	
	  		refdata.put("studySiteStatusRefData", configMap.get("studySiteStatusRefData"));
	  		refdata.put("studySiteRoleCodeRefData", configMap.get("studySiteRoleCodeRefData"));	  		
	  		return refdata;	  		
	  	}	  	
	  	
	  	return refdata;
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
		study.addStudySite(studySite);					
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
		id1.setSource("source");
		id1.setType("type");
		id1.setValue("<enter value>");
		id1.setPrimaryIndicator(true);
		identifiers.add(id1);
		Identifier id2 = new Identifier();	
		id2.setSource("source2");
		id2.setType("type2");
		id2.setValue("<enter value>");
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
	
	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}

	public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}

	public StudyValidator getStudyValidator() {
		return studyValidator;
	}

	public void setStudyValidator(StudyValidator studyValidator) {
		this.studyValidator = studyValidator;
	}
	
}
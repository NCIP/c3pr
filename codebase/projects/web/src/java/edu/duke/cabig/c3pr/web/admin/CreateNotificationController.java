package edu.duke.cabig.c3pr.web.admin;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.constants.NotificationEventTypeEnum;
import edu.duke.cabig.c3pr.constants.NotificationFrequencyEnum;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.PlannedNotificationDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismBasedRecipient;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.domain.repository.impl.CSMUserRepositoryImpl.C3PRNoSuchUserException;
import edu.duke.cabig.c3pr.domain.scheduler.runtime.job.ScheduledNotificationJob;
import edu.duke.cabig.c3pr.service.OrganizationService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;

/*
 * @author Vinay Gangoli
 * 
 * This controller persists the created Notification to the planned_notfns table.
 * called from the admin/createNotification flow.
 * Uses the Organization as the mapped hibernate object for persistence.
 */
public class CreateNotificationController extends SimpleFormController {

    private static Log log = LogFactory.getLog(CreateNotificationController.class);

    private HealthcareSiteDao healthcareSiteDao;
    private PersonUserDao personUserDao;
    private InvestigatorDao investigatorDao;
    private PlannedNotificationDao plannedNotificationDao;

    private OrganizationService organizationService;
    
    private ConfigurationProperty configurationProperty;
    
    private Configuration configuration;
    
    private UserDao userDao;
    
    private InPlaceEditableTab<NotificationWrapper> page;
    
    //constants for the Cron Triggers
    public static final String WEEKLY = "0 00 12 ? * FRI";
	public static final String MONTHLY ="0 0 12 L * ?";
	public static final String ANNUAL ="0 0 12 L DEC ?";
	
	public static final Long REPEAT_INTERVAL_IN_MILLI_SECONDS= 10*60*1000L;
	public static final Integer REPEAT_COUNT= 3;
	
	//job related declarations
	private Scheduler scheduler;

    protected Object formBackingObject(HttpServletRequest request) throws Exception {    	
	    NotificationWrapper wrapper = new NotificationWrapper();   	
    	gov.nih.nci.security.authorization.domainobjects.User user = (gov.nih.nci.security.authorization.domainobjects.User) request.getSession().getAttribute("userObject");
    	try {
    		if(WebUtils.hasSubmitParameter(request, "organization")){
    			HealthcareSite healthcareSite = healthcareSiteDao.getById(Integer.parseInt(request.getParameter("organization")));
    			wrapper.setHealthcareSite(healthcareSite);
    		} else{
    		//get the logged in users site.If the logged in user is not yet associated to a site then get the hosting site.
	    		PersonUser personUser = (PersonUser)userDao.getByLoginId(user.getUserId().longValue());
	    		if(personUser.getHealthcareSites().size()>0){
	    			wrapper.setHealthcareSite(personUser.getHealthcareSites().get(0));
	    		} else{
	    			log.debug("Logged in User is not associated to a site, so getting the hosting site organization");
	    			String localNciCode = this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
	    			HealthcareSite hcs = healthcareSiteDao.getByPrimaryIdentifier(localNciCode);
	    			wrapper.setHealthcareSite(hcs);
	    		}
	    	}
		} catch (C3PRNoSuchUserException e) {
			log.debug(e.getMessage());
			//if logged in user has no site(in future) get the hosting site.
			String localNciCode = this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);
			HealthcareSite hcs = healthcareSiteDao.getByPrimaryIdentifier(localNciCode);
			wrapper.setHealthcareSite(hcs);
		}
		return wrapper ;
    }

    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
    	Map<String, Object> configMap = configurationProperty.getMap();
        Map<String, Object> refdata = new HashMap<String, Object>();
        refdata.put("notificationEventsRefData", configMap.get("notificationEventsRefData"));
        refdata.put("notificationFrequencyRefData", configMap.get("notificationFrequencyRefData"));
        refdata.put("notificationPersonnelRoleRefData", configMap.get("notificationPersonnelRoleRefData"));
        refdata.put("notificationEmailSubstitutionVariablesRefData", configMap.get("notificationEmailSubstitutionVariablesRefData"));
        refdata.put("notificationStudyAccrualRefData", configMap.get("notificationStudyAccrualRefData"));
        refdata.put("notificationStudySiteAccrualRefData", configMap.get("notificationStudySiteAccrualRefData"));
        refdata.put("notificationReportEventsRefData", configMap.get("notificationReportEventsRefData"));
        gov.nih.nci.security.authorization.domainobjects.User user = (gov.nih.nci.security.authorization.domainobjects.User) request
		.getSession().getAttribute("userObject");
        PersonUser personUser = (PersonUser)userDao.getByLoginId(user.getUserId().longValue());
        refdata.put("assignedIdentifier", personUser.getAssignedIdentifier());
        return refdata;
    }
    
	@Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
    	super.initBinder(request, binder);
    	binder.registerCustomEditor(NotificationEventTypeEnum.class, new EnumByNameEditor(
    			NotificationEventTypeEnum.class));
    	binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(healthcareSiteDao));
    	binder.registerCustomEditor(PersonUser.class, new CustomDaoEditor(personUserDao));
    }
    
    /*
     * This is the method that gets called on form submission. All it does it cast the command into
     * Organization and call the service to persist.
     * On successful submission it sets the type attribute to confirm which is used to show the
     * confirmation screen.
     */
    protected ModelAndView processFormSubmission(HttpServletRequest request,
                    HttpServletResponse response, Object command, BindException errors)
                    throws Exception {
    	
//    	gov.nih.nci.security.authorization.domainobjects.User user = (gov.nih.nci.security.authorization.domainobjects.User) request
//																		.getSession().getAttribute("userObject");
//    	PersonUser personUser = null;
    	NotificationWrapper notificationWrapper = (NotificationWrapper) command;
    	HealthcareSite healthcareSite = notificationWrapper.getHealthcareSite();
    	if (isAjaxRequest(request)) {
			request.getParameter("_asynchronous");
			ModelAndView modelAndView = page.postProcessAsynchronous(request,
					(NotificationWrapper) command, errors);
			if(healthcareSite.getId() != null){
				notificationWrapper.setHealthcareSite((HealthcareSite) organizationService.merge(((NotificationWrapper) command).getHealthcareSite()));
				command= notificationWrapper;
	        }else {
	        	organizationService.saveNotification(((NotificationWrapper) command).getHealthcareSite());
	        }
			request.getSession().setAttribute(getFormSessionAttributeName(), command);
			if (isAjaxResponseFreeText(modelAndView)) {
				respondAjaxFreeText(modelAndView, response);
				return null;
			}
			return modelAndView;
		} else {
			
	        //assign the Rs or Inv to the userBasedRecpients
	        PersonUser rs = null;
	        Investigator investigator = null; 
	        Trigger trigger = null;
	        
	        PlannedNotification pn = null;
	        HealthcareSite hcs = null;
				
	        for(int i = 0; i < healthcareSite.getPlannedNotifications().size(); i++){
	        	pn = healthcareSite.getPlannedNotifications().get(i);
	        	if(pn.getHealthcareSite() == null && hcs != null){
	        		pn.setHealthcareSite(hcs);
	        	}
	        	//set the freq to Immediate as default. It is null sometimes when the select is manipulated by the javascript.
	        	//fix this so the javascript manipulation doesnt nullify the freq value.
	        	if(pn.getFrequency() == null){
	        		pn.setFrequency(NotificationFrequencyEnum.IMMEDIATE);
	        	}
	        	
	        	//set the contactMechnism Type for the contactMechanismBasedRecipient Emails 
	        	if(pn.getContactMechanismBasedRecipient() != null){
	        		for(ContactMechanismBasedRecipient cmbr: pn.getContactMechanismBasedRecipient()){
		        		for(ContactMechanism cm: cmbr.getContactMechanisms()){
		        			if(cm != null && cm.getValue() != null){
		        				cm.setType(ContactMechanismType.EMAIL);
		        			}
		        		}
		        	}
	        	}
	        	
	        	for(UserBasedRecipient ubr: pn.getUserBasedRecipient()){
	        		if(!StringUtils.isBlank(ubr.getEmailAddress())){
	        			//TODO: This method is removed from researchstaff dao. Check CPR-1570.
	        			//rs = personUserDao.getByEmailAddressFromLocal(ubr.getEmailAddress());
	        			
	        			//TODO: This method is removed from investigator dao. Check CPR-1568. 
	        			//investigator = investigatorDao.getByEmailAddressFromLocal(ubr.getEmailAddress());
	        		}
	        		if(rs != null){
	        			ubr.setPersonUser(rs);
	        		}else if(investigator != null){
	        			ubr.setInvestigator(investigator);
	        		}
	        	}
	        	
	        	if(pn.getFrequency() != NotificationFrequencyEnum.IMMEDIATE && pn.getId() == null){
	        		//generate the cron Triggers and jobs for Report based events if the pn is new.
	        		
	        		plannedNotificationDao.saveOrUpdate(pn);
	        		trigger = generateTriggerForReportBasedEvents(pn);
	        		scheduleJobsForReportBasedEvents(pn, trigger);
	        	} else {
	        		//do not create triggers/jobs if the pn was pre-existing
	        		plannedNotificationDao.saveOrUpdate(pn);
	        	}
	        }
	        
	        Map<String, Object> map = errors.getModel();
	        map.put("command", notificationWrapper);
	        ModelAndView mv = new ModelAndView(getSuccessView(), map);
	        return mv;
		}

    }
    
    /*	Create the Cron Triggers for (non-event based)report notifications.
     */
    private Trigger generateTriggerForReportBasedEvents(PlannedNotification pn){
    	Trigger t = null;
    	try {
			if(pn.getFrequency().equals(NotificationFrequencyEnum.WEEKLY)){
				//all ids are of the form  "TW:Event-Freq-id:"
				//every Friday at 12:00pm
				t = new CronTrigger("TW: " + getUniqueIdForTrigger(pn), "TGW"  + getUniqueIdForTrigger(pn), WEEKLY);
			}
			if(pn.getFrequency().equals(NotificationFrequencyEnum.MONTHLY)){
				//every last day of month at 12:00pm
				t = new CronTrigger("TM" + getUniqueIdForTrigger(pn), "TGM" + getUniqueIdForTrigger(pn), MONTHLY);
			}
			if(pn.getFrequency().equals(NotificationFrequencyEnum.ANNUAL)){
				//every last day December at 12:00pm
				t = new CronTrigger("TA" + getUniqueIdForTrigger(pn), "TGA" + getUniqueIdForTrigger(pn), ANNUAL);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		return t;
    }
    
    /*
     * Generates the unique key for Trigger based on plannedNotification Object
     */
    private String getUniqueIdForTrigger(PlannedNotification pn){
    	return pn.getEventName().getDisplayName() + "-" + pn.getFrequency().getDisplayName() + "-" + pn.getId().toString();
    }

    private void scheduleJobsForReportBasedEvents(PlannedNotification pn, Trigger trigger){
    	 // create job detail and set the map values
        String jobName = "J:" + pn.getEventName().getDisplayName() + "-" + pn.getFrequency().getDisplayName() + "-" + pn.getId().toString();
        String jobGroupName = "JG:" + pn.getEventName().getDisplayName() + "-" + pn.getFrequency().getDisplayName() + "-" + pn.getId().toString();
        JobDetail jobDetail = new JobDetail(jobName, jobGroupName, ScheduledNotificationJob.class);
                        
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("plannedNotificationId", pn.getId());

        // schedule the jobs
        log.info("Scheduling the job (jobFullName : " + jobDetail.getFullName() + ")");
        try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			log.error(e.getMessage());
		}	
    }
    
    
    
    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    
	public ConfigurationProperty getConfigurationProperty() {
		return configurationProperty;
	}


	public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
		this.configurationProperty = configurationProperty;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public InPlaceEditableTab<NotificationWrapper> getPage() {
		return page;
	}

	public void setPage(InPlaceEditableTab<NotificationWrapper> page) {
		this.page = page;
	}

	protected ModelAndView onSynchronousSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
	throws Exception {
		if (getSuccessView() == null) {
			throw new ServletException("successView isn't set");
		}
		return new ModelAndView(getSuccessView(), errors.getModel());
	}

	protected boolean isAjaxRequest(HttpServletRequest request) {
		if ("true".equalsIgnoreCase(request.getParameter(getAjaxRequestParamName()))){
			return true;
		}
		return false;
	}

	protected void setAjaxModelAndView(HttpServletRequest request, ModelAndView modelAndView) {
		request.setAttribute(getAjaxModelAndViewAttr(), modelAndView);
	}

	protected ModelAndView getAjaxModelAndView(HttpServletRequest request) {
		return (ModelAndView) request.getAttribute(getAjaxModelAndViewAttr());
	}

	protected boolean isAjaxResponseFreeText(ModelAndView modelAndView) {
		if (StringUtils.isBlank(modelAndView.getViewName())) {
			return true;
		}
		return false;
	}

	protected void respondAjaxFreeText(ModelAndView modelAndView, HttpServletResponse response)
	throws Exception {
		PrintWriter pr = response.getWriter();
		pr.println(modelAndView.getModel().get(getFreeTextModelName()));
		pr.flush();
	}

	protected String getAjaxRequestParamName() {
		return "_asynchronous";
	}

	protected String getAjaxModelAndViewAttr() {
		return "async_model_and_view";
	}

	protected String getFreeTextModelName() {
		return "free_text";
	}

	protected ModelAndView postProcessAsynchronous(HttpServletRequest request,
			NotificationWrapper command, Errors error) throws Exception {
		return new ModelAndView(getAjaxViewName(request));
	}

	protected String getAjaxViewName(HttpServletRequest request) {
		return request.getParameter(getAjaxViewParamName());
	}

	protected String getAjaxViewParamName() {
		return "_asyncViewName";
	}

	protected boolean shouldSave(HttpServletRequest request, NotificationWrapper command) {
		return true;
	}

	public InvestigatorDao getInvestigatorDao() {
		return investigatorDao;
	}

	public void setInvestigatorDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}

	public PersonUserDao getPersonUserDao() {
		return personUserDao;
	}

	public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}

	public PlannedNotificationDao getPlannedNotificationDao() {
		return plannedNotificationDao;
	}

	public void setPlannedNotificationDao(
			PlannedNotificationDao plannedNotificationDao) {
		this.plannedNotificationDao = plannedNotificationDao;
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}

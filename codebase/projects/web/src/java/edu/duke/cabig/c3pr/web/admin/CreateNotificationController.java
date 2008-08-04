package edu.duke.cabig.c3pr.web.admin;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.constants.NotificationEventType;
import edu.duke.cabig.c3pr.constants.NotificationFrequencyEnum;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.service.OrganizationService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.StringUtils;
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

    private OrganizationDao organizationDao;
    private ResearchStaffDao researchStaffDao;
    private InvestigatorDao investigatorDao;

    private OrganizationService organizationService;
    
    private ConfigurationProperty configurationProperty;
    
    private Configuration configuration;
    
    private InPlaceEditableTab<Organization> page;

    protected Object formBackingObject(HttpServletRequest request) throws Exception {    	
    	String localNciCode = this.configuration.get(Configuration.LOCAL_NCI_INSTITUTE_CODE);    	
        Organization org = organizationDao.getByNciIdentifier(localNciCode).get(0);
        return org;
    }

    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
    	Map<String, Object> configMap = configurationProperty.getMap();
        Map<String, Object> refdata = new HashMap<String, Object>();
        refdata.put("notificationEventsRefData", configMap.get("notificationEventsRefData"));
        refdata.put("notificationFrequencyRefData", configMap.get("notificationFrequencyRefData"));
        refdata.put("notificationPersonnelRoleRefData", configMap.get("notificationPersonnelRoleRefData"));
        return refdata;
    }
    
    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
    	super.initBinder(request, binder);
    	binder.registerCustomEditor(NotificationEventType.class, new EnumByNameEditor(
    			NotificationEventType.class));
    	binder.registerCustomEditor(NotificationFrequencyEnum.class, new EnumByNameEditor(
    			NotificationFrequencyEnum.class));    	
    }
    
    /*
     * This is the method that gets called on form submission. All it does it cast the command into
     * Organization and call the service to persist.
     * On succesful submission it sets the type attribute to confirm which is used to show the
     * confirmation screen.
     */
    protected ModelAndView processFormSubmission(HttpServletRequest request,
                    HttpServletResponse response, Object command, BindException errors)
                    throws Exception {
    	
    	if (isAjaxRequest(request)) {
			request.getParameter("_asynchronous");
			ModelAndView modelAndView = page.postProcessAsynchronous(request,
					(Organization) command, errors);
			if(((Organization)command).getId() != null){
	        	organizationService.mergeNotification((Organization) command);	        	
	        }else {
	        	organizationService.saveNotification((Organization) command);
	        }
			request.setAttribute(getFormSessionAttributeName(), command);
			if (isAjaxResponseFreeText(modelAndView)) {
				respondAjaxFreeText(modelAndView, response);
				return null;
			}
			return modelAndView;
		} else {
			
			Organization organization = null;
	        log.debug("Inside the CreateNotificationController:");
	        if (command instanceof Organization) {
	            organization = (Organization) command;
	        }
	        else {
	            log.error("Incorrect Command object passsed into CreateNotificationController.");
	            return new ModelAndView(getFormView());
	        }
	        
	        //assign the Rs or Inv to the userBasedRe
	        List<ResearchStaff> rsList = new ArrayList<ResearchStaff>();
	        List<Investigator> invList = new ArrayList<Investigator>(); 
	        
	        for(PlannedNotification pn: organization.getPlannedNotifications()){
	        	for(UserBasedRecipient ubr: pn.getUserBasedRecipient()){
	        		if(ubr.getEmailAddress() != null && ubr.getEmailAddress() != ""){
	        			rsList = researchStaffDao.getByEmailAddress(ubr.getEmailAddress());
	        			invList = investigatorDao.getByEmailAddress(ubr.getEmailAddress());
	        		}
	        		if(rsList.size() > 0){
	        			ubr.setResearchStaff(rsList.get(0));
	        		}else if(invList.size() > 0){
	        			ubr.setInvestigator(invList.get(0));
	        		}
	        	}
	        }
	        
	        if(organization.getId() != null){
	        	organizationService.mergeNotification(organization);	        	
	        }else {
	        	organizationService.saveNotification(organization);
	        }
	        
	        
	        Map map = errors.getModel();
	        map.put("command", organization);
	        ModelAndView mv = new ModelAndView(getSuccessView(), map);
	        return mv;
		}

    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public OrganizationDao getOrganizationDao() {
        return organizationDao;
    }

    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
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

	public InPlaceEditableTab<Organization> getPage() {
		return page;
	}

	public void setPage(InPlaceEditableTab<Organization> page) {
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
			Organization command, Errors error) throws Exception {
		return new ModelAndView(getAjaxViewName(request));
	}

	protected String getAjaxViewName(HttpServletRequest request) {
		return request.getParameter(getAjaxViewParamName());
	}

	protected String getAjaxViewParamName() {
		return "_asyncViewName";
	}

	protected boolean shouldSave(HttpServletRequest request, Organization command) {
		return true;
	}

	public InvestigatorDao getInvestigatorDao() {
		return investigatorDao;
	}

	public void setInvestigatorDao(InvestigatorDao investigatorDao) {
		this.investigatorDao = investigatorDao;
	}

	public ResearchStaffDao getResearchStaffDao() {
		return researchStaffDao;
	}

	public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
		this.researchStaffDao = researchStaffDao;
	}

}

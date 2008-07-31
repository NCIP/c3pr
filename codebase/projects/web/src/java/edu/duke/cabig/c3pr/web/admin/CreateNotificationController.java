package edu.duke.cabig.c3pr.web.admin;

import java.util.HashMap;
import java.util.Map;

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
import edu.duke.cabig.c3pr.dao.C3PRBaseDao;
import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.service.OrganizationService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;

/*
 * @author Vinay Gangoli
 * 
 * This controller persists the created Notification to the planned_notfns table.
 * called from the admin/createNotification flow.
 * Uses the Organization as the mapped hibernate object for persistence.
 */
public class CreateNotificationController extends SimpleFormController{

    private static Log log = LogFactory.getLog(CreateNotificationController.class);

    private OrganizationDao organizationDao;

    private OrganizationService organizationService;
    
    private ConfigurationProperty configurationProperty;
    
    private Configuration configuration;

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

    	Organization organization = null;
        log.debug("Inside the CreateNotificationController:");
        if (command instanceof Organization) {
            organization = (Organization) command;
        }
        else {
            log.error("Incorrect Command object passsed into CreateNotificationController.");
            return new ModelAndView(getFormView());
        }

        organizationService.saveNotification(organization);
        
        Map map = errors.getModel();
        map.put("command", organization);
        ModelAndView mv = new ModelAndView(getSuccessView(), map);
        return mv;
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
    
    protected Organization getPrimaryDomainObject(Organization command){
    	return command;
    }

    protected C3PRBaseDao getDao() {
    	return organizationDao;
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
}

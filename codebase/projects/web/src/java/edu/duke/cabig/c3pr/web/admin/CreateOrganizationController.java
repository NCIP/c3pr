package edu.duke.cabig.c3pr.web.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.constants.EndPointType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalContactMechanism;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.repository.OrganizationRepository;
import edu.duke.cabig.c3pr.service.OrganizationService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;

/*
 * @author Vinay Gangoli
 * 
 * This controller persists the created organization to the organizations table.
 * called from the admin/createOrganization flow.
 * Uses the HealthcareSite as the mapped hibernate object for persistence.
 */
public class CreateOrganizationController extends SimpleFormController {

	private static Log log = LogFactory.getLog(CreateOrganizationController.class);

	private OrganizationRepository organizationRepository;

	private Configuration configuration;

	private HealthcareSiteDao healthcareSiteDao;

	private OrganizationService organizationService;

	private String EDIT_FLOW = "EDIT_FLOW";
	private String SAVE_FLOW = "SAVE_FLOW";
	private String FLOW = "FLOW";
	
	private ConfigurationProperty configurationProperty;

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception  {
    	Map<String, Object> refdata = new HashMap<String, Object>();
    	Map<String, List<Lov>> configMap = configurationProperty.getMap();
    	
    	refdata.put("orgIdentifiersTypeRefData",  configMap.get("orgIdentifiersTypeRefData"));
    	refdata.put("coppaEnable", configuration.get(Configuration.COPPA_ENABLE));
        return refdata;
    }
    
    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
            throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(healthcareSiteDao.domainClass(), new CustomDaoEditor(
                healthcareSiteDao));
    }
    
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		HealthcareSite hcs = null;

		if (request.getParameter("primaryIdentifier") != null) {
			log.info(" Request URl  is:" + request.getRequestURL().toString());
			hcs = healthcareSiteDao.getByPrimaryIdentifier(request.getParameter("primaryIdentifier"));
			request.getSession().setAttribute(FLOW, EDIT_FLOW);
			log.info(" HCS's ID is:" + hcs.getId());
		} else {
			hcs = new LocalHealthcareSite();
			request.getSession().setAttribute(FLOW, SAVE_FLOW);
		}
		
		int cmSize = hcs.getContactMechanisms().size();
        if (cmSize == 0) {
            addContactsToSite(hcs);
        }
        if (cmSize == 1) {
            ContactMechanism contactMechanismPhone = new LocalContactMechanism();
            ContactMechanism contactMechanismFax = new LocalContactMechanism();
            contactMechanismPhone.setType(ContactMechanismType.PHONE);
            contactMechanismFax.setType(ContactMechanismType.Fax);
            hcs.addContactMechanism(contactMechanismPhone);
            hcs.addContactMechanism(contactMechanismFax);
        }
        if (cmSize == 2) {
            ContactMechanism contactMechanismFax = new LocalContactMechanism();
            contactMechanismFax.setType(ContactMechanismType.Fax);
            hcs.addContactMechanism(contactMechanismFax);
        }
		return hcs;
	}

	
    private void addContactsToSite(Organization hcs) {
        ContactMechanism contactMechanismEmail = new LocalContactMechanism();
        ContactMechanism contactMechanismPhone = new LocalContactMechanism();
        ContactMechanism contactMechanismFax = new LocalContactMechanism();
        contactMechanismEmail.setType(ContactMechanismType.EMAIL);
        contactMechanismPhone.setType(ContactMechanismType.PHONE);
        contactMechanismFax.setType(ContactMechanismType.Fax);
        hcs.addContactMechanism(contactMechanismEmail);
        hcs.addContactMechanism(contactMechanismPhone);
        hcs.addContactMechanism(contactMechanismFax);
    }
    
    
	@Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		super.onBindAndValidate(request, command, errors);
		
		HealthcareSite healthcareSite = (HealthcareSite) command;
		if (request.getSession().getAttribute(FLOW).equals(SAVE_FLOW)) {
			Organization existingOrg = healthcareSiteDao.getByPrimaryIdentifierFromLocal(healthcareSite.getPrimaryIdentifier());
			if(existingOrg != null){
				errors.reject("duplicate.organizationname.error");
				return;
			}
		}
		
		if ((!StringUtils.isBlank(request.getParameter("_action")) && !request.getParameter("_action").equals("saveRemoteOrg"))
				|| (request.getParameter("_action").equals("syncOrganization") && request
						.getSession().getAttribute(FLOW).equals(EDIT_FLOW))) {
			if (!request.getParameter("_action").equals("syncOrganization")) {
				HealthcareSite hcsFromDB = healthcareSiteDao
						.getByPrimaryIdentifierFromLocal(healthcareSite
								.getPrimaryIdentifier());
				if (hcsFromDB != null
						&& !hcsFromDB.getId().equals(healthcareSite.getId())) {
					errors.reject("LOCAL_ORG_EXISTS",
							"Organization with NCI Institute Code "
									+ healthcareSite.getPrimaryIdentifier()
									+ " already exists");
					return;
				}
			}
			List<HealthcareSite> remoteOrgs = healthcareSiteDao
					.getRemoteOrganizations(healthcareSite);
			boolean matchingExternalHealthcareSitePresent = false;
			for (HealthcareSite remoteOrg : remoteOrgs) {
				if (remoteOrg.getPrimaryIdentifier().equalsIgnoreCase(
						healthcareSite.getPrimaryIdentifier())) {
					healthcareSite.addExternalOrganization(remoteOrg);
					matchingExternalHealthcareSitePresent = true;
				}
			}
			if (matchingExternalHealthcareSitePresent
					&& ((request.getSession().getAttribute(FLOW)
							.equals(SAVE_FLOW)) || ("syncOrganization"
							.equals(request.getParameter("_action")) && request
							.getSession().getAttribute(FLOW).equals(EDIT_FLOW)))) {
				if (remoteOrgs != null && remoteOrgs.size() > 0) {
					healthcareSite.setExternalOrganizations(remoteOrgs);
					errors.reject("REMOTE_ORG_EXISTS",
							"Organization with NCI Institute Code "
									+ healthcareSite.getPrimaryIdentifier()
									+ " exists in external system");
				}
			}
		}
	}

	/*
	 * This is the method that gets called on form submission. All it does it
	 * case the command into HealthcareSite and call the service to persist.
	 * 
	 * On successful submission it sets the type attribute to confirm which is
	 * used to show the confirmation screen.
	 */
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		HealthcareSite organization = null;
		boolean saveExternalHealthcareSite = false;
		RemoteHealthcareSite remoteHealthcareSiteSelected = null;
		log.debug("Inside the CreateOrganizationController:");
		if (command instanceof HealthcareSite) {
			organization = (HealthcareSite) command;
		} else {
			log
					.error("Incorrect Command object passsed into CreateOrganizationController.");
			return new ModelAndView(getFormView());
		}

		if (!errors.hasErrors()) {
			if (WebUtils.hasSubmitParameter(request, "setAdvancedProperty")
					&& request.getParameter("setAdvancedProperty")
							.equalsIgnoreCase("ON")) {
				if (!organization.getHasEndpointProperty())
					organization
							.initializeEndPointProperties(EndPointType.GRID);
				organization.getStudyEndPointProperty().setUrl(
						request.getParameter("studyServiceURL"));
				organization.getRegistrationEndPointProperty().setUrl(
						request.getParameter("registrationServiceURL"));
				if (WebUtils.hasSubmitParameter(request,
						"authenticationRequired")
						&& request.getParameter("authenticationRequired")
								.equalsIgnoreCase("ON"))
					organization.setEndPointAuthenticationRequired(true);
				else
					organization.setEndPointAuthenticationRequired(false);
			}
			if (request.getSession().getAttribute(FLOW).equals(SAVE_FLOW)) {
				if ("saveRemoteOrg".equals(request.getParameter("_action"))) {
					saveExternalHealthcareSite = true;
					remoteHealthcareSiteSelected = (RemoteHealthcareSite) organization
							.getExternalOrganizations().get(
									Integer.parseInt(request
											.getParameter("_selected")));
					organizationService.save(remoteHealthcareSiteSelected);
				} else {
					organizationService.save(organization);
				}
			} else if ("saveRemoteOrg".equals(request.getParameter("_action"))) {

				healthcareSiteDao.evict(organization);

				if (organization.getExternalOrganizations() != null
						&& organization.getExternalOrganizations().size() > 0) {
					saveExternalHealthcareSite = true;
					remoteHealthcareSiteSelected = (RemoteHealthcareSite) organization
							.getExternalOrganizations().get(
									Integer.parseInt(request
											.getParameter("_selected")));
					organizationRepository.convertLocalToRemote(
							(LocalHealthcareSite) organization,
							remoteHealthcareSiteSelected);

				}
			} else {
				organizationService.merge(organization);
			}
			Map map = errors.getModel();
			map.put("command", saveExternalHealthcareSite? remoteHealthcareSiteSelected
					: organization);
			ModelAndView mv = new ModelAndView(getSuccessView(), map);
			return mv;
		}

		return super.processFormSubmission(request, response, command, errors);
	}


	@Required
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public void setOrganizationRepository(
			OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
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
}

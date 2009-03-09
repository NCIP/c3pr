package edu.duke.cabig.c3pr.web.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.EndPointType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.service.OrganizationService;

/*
 * @author Vinay Gangoli
 * 
 * This controller persists the created organization to the organizations table.
 * called from the admin/createOrganization flow.
 * Uses the HealthcareSite as the mapped hibernate object for persistence.
 */
public class CreateOrganizationController extends SimpleFormController {

    private static Log log = LogFactory.getLog(CreateOrganizationController.class);

  //  private OrganizationDao organizationDao;
    
    private HealthcareSiteDao healthcareSiteDao;

    public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	private OrganizationService organizationService;

    private String EDIT_FLOW = "EDIT_FLOW";

    private String SAVE_FLOW = "SAVE_FLOW";

    private String FLOW = "FLOW";

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        HealthcareSite hcs = null;

        if (request.getParameter("nciIdentifier") != null) {
            log.info(" Request URl  is:" + request.getRequestURL().toString());
            hcs = healthcareSiteDao.getByNciIdentifier(request.getParameter("nciIdentifier")).get(0);
            request.getSession().setAttribute(FLOW, EDIT_FLOW);
            log.info(" HCS's ID is:" + hcs.getId());
        }
        else {
            hcs = new LocalHealthcareSite();
            request.getSession().setAttribute(FLOW, SAVE_FLOW);
        }
        return hcs;
    }
    
    @Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		super.onBindAndValidate(request, command, errors);
		HealthcareSite healthcareSite = (HealthcareSite) command;
    	if(healthcareSite.getId() == null){
    		if(!"saveRemoteOrg".equals(request.getParameter("_action"))){
    			HealthcareSite hcsFromDB = healthcareSiteDao.getLocalOrganizationsOnlyByNciInstituteCode(healthcareSite.getNciInstituteCode());
    			if(hcsFromDB != null){
    				errors.reject("LOCAL_ORG_EXISTS","Organization with NCI Institute Code " +healthcareSite.getNciInstituteCode()+ " already exisits");
    				return;
    			}
        		List<HealthcareSite> remoteOrgs = healthcareSiteDao.getRemoteOrganizations(healthcareSite);
        		if(remoteOrgs != null && remoteOrgs.size() > 0){
        			healthcareSite.setExternalOrganizations(remoteOrgs);
        			errors.reject("REMOTE_ORG_EXISTS","Organization with NCI Institute Code " +healthcareSite.getNciInstituteCode()+ " exisits in external system");
        		}
        	}
        }
	}

    /*
     * This is the method that gets called on form submission. All it does it case the command into
     * HealthcareSite and call the service to persist.
     * 
     * On succesful submission it sets the type attribute to confirm which is used to show the
     * confirmation screen.
     */
    protected ModelAndView processFormSubmission(HttpServletRequest request,
                    HttpServletResponse response, Object command, BindException errors)
                    throws Exception {

        HealthcareSite organization = null;
        log.debug("Inside the CreateOrganizationController:");
        if (command instanceof HealthcareSite) {
            organization = (HealthcareSite) command;
        }
        else {
            log.error("Incorrect Command object passsed into CreateOrganizationController.");
            return new ModelAndView(getFormView());
        }
        
        
        if(!errors.hasErrors()){
			if ("saveRemoteOrg".equals(request.getParameter("_action"))) {
				RemoteHealthcareSite remoteHealthcareSiteToSave;
				remoteHealthcareSiteToSave = (RemoteHealthcareSite) organization
						.getExternalOrganizations().get(
								Integer.parseInt(request
										.getParameter("_selected")));
				organization.setName(remoteHealthcareSiteToSave.getName());
				organization.setNciInstituteCode(remoteHealthcareSiteToSave
						.getNciInstituteCode());
				organization.setDescriptionText(remoteHealthcareSiteToSave
						.getDescriptionText());
			}
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
				organizationService.save(organization);
			} else {
				organizationService.merge(organization);
			}
			Map map = errors.getModel();
	        map.put("command", organization);
	        ModelAndView mv = new ModelAndView(getSuccessView(),map);
	        return mv;
		} 
        
        return super.processFormSubmission(request, response, command, errors);
		
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
}

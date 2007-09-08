package edu.duke.cabig.c3pr.web.admin;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.service.OrganizationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @author Vinay Gangoli
 * 
 * This controller persists the created organization to the organizations table.
 * called from the admin/createOrganization flow.
 * Uses the HealthcareSite as the mapped hibernate object for persistence.
 */
public class CreateOrganizationController extends SimpleFormController {

    private static Log log = LogFactory.getLog(CreateOrganizationController.class);
    private OrganizationService organizationService;


    /*
      * This is the method that gets called on form submission.
      * All it does it case the command into HealthcareSite and call the service to persist.
      *
      * On succesful submission it sets the type attribute to confirm which is used to
      * show the confirmation screen.
      */
    protected ModelAndView processFormSubmission(HttpServletRequest request,
                                                 HttpServletResponse response, Object command, BindException errors) throws Exception {

        HealthcareSite organization = null;
        log.debug("Inside the CreateOrganizationController:");
        if (command instanceof HealthcareSite) {
            organization = (HealthcareSite) command;
        } else {
            log.error("Incorrect Command object passsed into CreateOrganizationController.");
            return new ModelAndView(getFormView());
        }


        organizationService.save(organization);

        ModelAndView mv = new ModelAndView(getSuccessView());
        mv.addObject(organization);
        return mv;
    }


    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
}

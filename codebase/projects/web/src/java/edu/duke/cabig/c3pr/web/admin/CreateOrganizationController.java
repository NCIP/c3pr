package edu.duke.cabig.c3pr.web.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
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

    private OrganizationDao organizationDao;

    private OrganizationService organizationService;

    private String EDIT_FLOW = "EDIT_FLOW";

    private String SAVE_FLOW = "SAVE_FLOW";

    private String FLOW = "FLOW";

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        HealthcareSite hcs = null;

        if (request.getParameter("nciIdentifier") != null) {
            log.info(" Request URl  is:" + request.getRequestURL().toString());
            hcs = organizationDao.getByNciIdentifier(request.getParameter("nciIdentifier")).get(0);
            request.getSession().setAttribute(FLOW, EDIT_FLOW);
            log.info(" HCS's ID is:" + hcs.getId());
        }
        else {
            hcs = new HealthcareSite();
            request.getSession().setAttribute(FLOW, SAVE_FLOW);
        }
        return hcs;
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

        if (request.getSession().getAttribute(FLOW).equals(SAVE_FLOW)) {
            organizationService.save(organization);
        }
        else {
            organizationService.merge(organization);
        }
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
}

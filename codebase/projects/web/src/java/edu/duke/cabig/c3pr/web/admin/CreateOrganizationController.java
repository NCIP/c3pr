package edu.duke.cabig.c3pr.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.web.SearchStudyController;

/*
 * @author Vinay Gangoli
 * 
 * This controller persists the created organization to the organizations table.
 * called from the admin/createOrganization flow.
 * Uses the HealthcareSite as the mapped hibernate object for persistence.
 */
public class CreateOrganizationController extends SimpleFormController {

	private static Log log = LogFactory.getLog(SearchStudyController.class);
	private OrganizationDao organizationDao;
	
	//request parameter that helps determine 
	//whether to display info section or confirmation section.
	public static final String TYPE = "type";
	public static final String ORGNAME = "orgName";
	public static final String CONFIRM = "confirm";
	public static final String DISPLAYLOCALBUTTONS = "displayLocalButtons";
	
	@Override
	protected boolean isFormSubmission(HttpServletRequest request) {
		if(request.getAttribute(TYPE)!=null && (request.getAttribute(TYPE).equals(CONFIRM)))
			return false;
		return super.isFormSubmission(request);
	}

	/*
	 * This is the method that gets called on form submission.
	 * All it does it case the command into HealthcareSite and call the OrganizationDao to persist.
	 * 
	 * On succesful submission it sets the type attribute to confirm which is used to 
	 * show the confirmation screen.
	 */
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)	throws Exception  {
		
		HealthcareSite healthCareSite = null;
		log.debug("Inside the CreateOrganizationController:");
		if(command instanceof HealthcareSite){
			healthCareSite = (HealthcareSite) command;
		} else {
			log.error("Incorrect Command object passsed into CreateOrganizationController.");
			request.setAttribute(TYPE, "");
			return new ModelAndView(getFormView());
		}				
		organizationDao.save(healthCareSite);

		request.setAttribute(TYPE , CONFIRM);
		request.setAttribute(ORGNAME , healthCareSite.getName());
		
		request.setAttribute(DISPLAYLOCALBUTTONS, "false");
    	return new ModelAndView("forward:createOrganization");
    	
    }

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}	    	
	
}

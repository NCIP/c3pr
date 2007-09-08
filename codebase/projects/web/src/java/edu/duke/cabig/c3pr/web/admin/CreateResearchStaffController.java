package edu.duke.cabig.c3pr.web.admin;

import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

/**
 * @author Ramakrishna
 */
public class CreateResearchStaffController extends AbstractCreateC3PRUserController<ResearchStaff> {

    private PersonnelService personnelService;


    private Logger log = Logger.getLogger(CreateResearchStaffController.class);

    public CreateResearchStaffController() {
    }

    /**
     * Create a nested object graph that Create Research Staff needs
     *
     * @param request -
     *                HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        ResearchStaff rs = new ResearchStaff();

        rs.setVersion(Integer.parseInt("1"));
        ContactMechanism contactMechanismEmail = new ContactMechanism();
        ContactMechanism contactMechanismPhone = new ContactMechanism();
        ContactMechanism contactMechanismFax = new ContactMechanism();
        contactMechanismEmail.setType(ContactMechanismType.EMAIL);
        contactMechanismPhone.setType(ContactMechanismType.PHONE);
        contactMechanismFax.setType(ContactMechanismType.Fax);
        rs.addContactMechanism(contactMechanismEmail);
        rs.addContactMechanism(contactMechanismPhone);
        rs.addContactMechanism(contactMechanismFax);

        rs.addGroup(C3PRUserGroupType.C3PR_ADMIN);
        rs.addGroup(C3PRUserGroupType.REGISTRAR);
        rs.addGroup(C3PRUserGroupType.STUDY_COORDINATOR);

        return rs;
    }

    /*
    * (non-Javadoc)
    *
    * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit
    *      (HttpServletRequest request, HttpServletResponse response, Object
    *      command, BindException errors)
    */

    @Override
    protected ModelAndView processFormSubmission(HttpServletRequest request,
                                                 HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        ResearchStaff researchStaff = (ResearchStaff) command;

        Iterator<ContactMechanism> cMIterator = researchStaff
                .getContactMechanisms().iterator();
        while (cMIterator.hasNext()) {
            ContactMechanism contactMechanism = cMIterator.next();
            if (StringUtils.isBlank(contactMechanism.getValue()))
                cMIterator.remove();
        }

        Iterator<C3PRUserGroupType> gIterator = researchStaff
                .getGroups().iterator();
        while (gIterator.hasNext()) {
            C3PRUserGroupType group = gIterator.next();
            if (group == null)
                gIterator.remove();
        }

        try {
            personnelService.save(researchStaff);
        } catch (C3PRBaseException e) {
            logger.error(e);
            //TODO should be in validator
            if (e.getLocalizedMessage().contains("uq_login_name")) {
                errors.reject("Username already exists");
            }
        } catch (C3PRBaseRuntimeException e) {
            if (e.getRootCause().getMessage().contains("MailException")) {
                //no problem
                log.info("Error saving Research staff.Probably failed to send email", e);
            }
        }


        ModelAndView mv = new ModelAndView(getSuccessView());
        mv.addObject(researchStaff);

        return mv;

    }


    public PersonnelService getPersonnelService() {
        return personnelService;
    }

    public void setPersonnelService(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }
}
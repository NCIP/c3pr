package edu.duke.cabig.c3pr.web.admin;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.C3PRBaseDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * @author Ramakrishna
 * @author kherm
 */
public class CreateInvestigatorController<C extends Investigator> extends
                AbstractCreateC3PRUserController<C, C3PRBaseDao<C>> {

    private PersonnelService personnelService;

    private InvestigatorDao investigatorDao;

    private String EDIT_FLOW = "EDIT_FLOW";

    private String SAVE_FLOW = "SAVE_FLOW";

    private String FLOW = "FLOW";

    private Logger log = Logger.getLogger(CreateInvestigatorController.class);

    public CreateInvestigatorController() {
    }

    /**
     * Create a nested object graph that Create Investigator Design needs Incase the flow is coming
     * from search...we get the id and get the corresponding investigator obj.
     * 
     * @param request -
     *            HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        Investigator inv;
        if (request.getParameter("id") != null && request.getParameter("id") != "") {
            log.info(" Request URl  is:" + request.getRequestURL().toString());
            inv = investigatorDao.getLoadedInvestigatorById(Integer.parseInt(request
                            .getParameter("id")));
            int cmSize = inv.getContactMechanisms().size();
            if (cmSize == 0) {
                inv = createInvestigatorWithContacts(inv);
            }
            if (cmSize == 1) {
                ContactMechanism contactMechanismPhone = new ContactMechanism();
                ContactMechanism contactMechanismFax = new ContactMechanism();
                contactMechanismPhone.setType(ContactMechanismType.PHONE);
                contactMechanismFax.setType(ContactMechanismType.Fax);
                inv.addContactMechanism(contactMechanismPhone);
                inv.addContactMechanism(contactMechanismFax);
            }

            if (cmSize == 2) {
                ContactMechanism contactMechanismFax = new ContactMechanism();
                contactMechanismFax.setType(ContactMechanismType.Fax);
                inv.addContactMechanism(contactMechanismFax);
            }
            request.getSession().setAttribute(FLOW, EDIT_FLOW);
            log.info(" Investigator's ID is:" + inv.getId());
        }
        else {
            inv = createInvestigatorWithDesign();
            request.getSession().setAttribute(FLOW, SAVE_FLOW);
        }
        return inv;
    }

    @Override
    protected boolean shouldSave(HttpServletRequest request, Investigator command) {
        return true;
    }

    @Override
    protected ModelAndView onSynchronousSubmit(HttpServletRequest request,
                    HttpServletResponse response, Object command, BindException errors)
                    throws Exception {

        Investigator inv = (Investigator) command;

        Iterator<ContactMechanism> cMIterator = inv.getContactMechanisms().iterator();
        StringUtils strUtil = new StringUtils();
        while (cMIterator.hasNext()) {
            ContactMechanism contactMechanism = cMIterator.next();
            if (strUtil.isBlank(contactMechanism.getValue())) cMIterator.remove();
        }

        try {
            if (request.getSession().getAttribute(FLOW).equals(SAVE_FLOW)) {
                personnelService.save(inv);
            }
            else {
                for (HealthcareSiteInvestigator hcsInv : inv.getHealthcareSiteInvestigators()) {
                    if (hcsInv.getStatusCode() != null && !hcsInv.getStatusCode().equals("AC")) {
                        for (SiteInvestigatorGroupAffiliation sInvGrAff : hcsInv
                                        .getSiteInvestigatorGroupAffiliations()) {
                            sInvGrAff.setEndDate(new Date());
                        }
                        for (StudyInvestigator studyInv : hcsInv.getStudyInvestigators()) {
                            studyInv.setEndDate(new Date());
                        }
                    }
                }

                inv = personnelService.merge(inv);
            }

        }
        catch (C3PRBaseException e) {
            e.printStackTrace(); // To change body of catch statement use File | Settings | File
                                    // Templates.
        }
        catch (C3PRBaseRuntimeException e) {
            if (e.getRootCause().getMessage().contains("MailException")) {
                // no problem
                log.info("Error saving Investigator.Probably failed to send email", e);
            }
        }
        Map map = errors.getModel();
        map.put("command", inv);
        ModelAndView mv = new ModelAndView(getSuccessView(), map);
        return mv;
    }

    private Investigator createInvestigatorWithDesign() {

        Investigator investigator = new Investigator();
        HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
        investigator.addHealthcareSiteInvestigator(healthcareSiteInvestigator);
        investigator = createInvestigatorWithContacts(investigator);

        return investigator;
    }

    private Investigator createInvestigatorWithContacts(Investigator inv) {

        ContactMechanism contactMechanismEmail = new ContactMechanism();
        ContactMechanism contactMechanismPhone = new ContactMechanism();
        ContactMechanism contactMechanismFax = new ContactMechanism();
        contactMechanismEmail.setType(ContactMechanismType.EMAIL);
        contactMechanismPhone.setType(ContactMechanismType.PHONE);
        contactMechanismFax.setType(ContactMechanismType.Fax);
        inv.addContactMechanism(contactMechanismEmail);
        inv.addContactMechanism(contactMechanismPhone);
        inv.addContactMechanism(contactMechanismFax);
        return inv;
    }

    public PersonnelService getPersonnelService() {
        return personnelService;
    }

    public void setPersonnelService(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    public InvestigatorDao getInvestigatorDao() {
        return investigatorDao;
    }

    public void setInvestigatorDao(InvestigatorDao investigatorDao) {
        this.investigatorDao = investigatorDao;
    }

    @Override
    protected C3PRBaseDao getDao() {
        return this.investigatorDao;
    }

    @Override
    protected C getPrimaryDomainObject(C command) {
        return command;
    }

}
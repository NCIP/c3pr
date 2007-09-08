package edu.duke.cabig.c3pr.web.admin;

import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Ramakrishna
 * @author kherm
 */
public class CreateInvestigatorController extends
        AbstractCreateC3PRUserController<Investigator> {

    private PersonnelService personnelService;

    private Logger log = Logger.getLogger(CreateInvestigatorController.class);

    public CreateInvestigatorController() {
    }


    /**
     * Create a nested object graph that Create Investigator Design needs
     *
     * @param request -
     *                HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
        return createInvestigatorWithDesign();
    }

    /*
    * (non-Javadoc)
    *
    * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish
    *      (javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse, java.lang.Object,
    *      org.springframework.validation.BindException)
    */
    @Override
    protected ModelAndView processFormSubmission(HttpServletRequest request,
                                                 HttpServletResponse response, Object command, BindException errors)
            throws Exception {

        Investigator inv = (Investigator) command;

        Iterator<ContactMechanism> cMIterator = inv.getContactMechanisms()
                .iterator();
        StringUtils strUtil = new StringUtils();
        while (cMIterator.hasNext()) {
            ContactMechanism contactMechanism = cMIterator.next();
            if (strUtil.isBlank(contactMechanism.getValue()))
                cMIterator.remove();
        }
        inv.addGroup(C3PRUserGroupType.INVESTIGATOR);


        try {
            personnelService.save(inv);
        } catch (C3PRBaseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (C3PRBaseRuntimeException e) {
            if (e.getRootCause().getMessage().contains("MailException")) {
                //no problem
                log.info("Error saving Research staff.Probably failed to send email", e);
            }
        }

        ModelAndView mv = new ModelAndView(getSuccessView());
        mv.addObject(inv);

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

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
        // TODO Auto-generated method stub
        super.onBind(request, command, errors);
        handleRowDeletion(request, command);
    }

    public void handleRowDeletion(HttpServletRequest request, Object command) throws Exception {
        Enumeration enumeration = request.getParameterNames();
        Hashtable<String, List<Integer>> table = new Hashtable<String, List<Integer>>();
        while (enumeration.hasMoreElements()) {
            String param = (String) enumeration.nextElement();
            if (param.startsWith("_deletedRow-")) {
                String[] params = param.split("-");
                if (table.get(params[1]) == null)
                    table.put(params[1], new ArrayList<Integer>());
                table.get(params[1]).add(new Integer(params[2]));
            }
        }
        deleteRows(command, table);
    }

    public void deleteRows(Object command, Hashtable<String, List<Integer>> table) throws Exception {
        Enumeration<String> e = table.keys();
        while (e.hasMoreElements()) {
            String path = e.nextElement();
            List col = (List) new DefaultObjectPropertyReader(command, path).getPropertyValueFromPath();
            List<Integer> rowNums = table.get(path);
            List temp = new ArrayList();
            for (int i = 0; i < col.size(); i++) {
                if (!rowNums.contains(new Integer(i)))
                    temp.add(col.get(i));
            }
            col.removeAll(col);
            col.addAll(temp);
        }
    }


    public PersonnelService getPersonnelService() {
        return personnelService;
    }

    public void setPersonnelService(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }


}
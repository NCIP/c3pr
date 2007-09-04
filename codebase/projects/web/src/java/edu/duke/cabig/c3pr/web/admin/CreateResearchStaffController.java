package edu.duke.cabig.c3pr.web.admin;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;

/**
 * @author Ramakrishna
 */
public class CreateResearchStaffController extends SimpleFormController {

    private PersonnelService personnelService;

    private ResearchStaffDao researchStaffDao;

    private HealthcareSiteDao healthcareSiteDao;

    private ConfigurationProperty configurationProperty;

    public CreateResearchStaffController() {
        setCommandClass(ResearchStaff.class);
        this.setCommandName("command");
        this.setFormView("admin/research_staff_details");
        this.setSuccessView("admin/research_staff_details");
    }

    protected Map<String, Object> referenceData(HttpServletRequest request) {

        Map<String, Object> configMap = configurationProperty.getMap();
        Map<String, Object> refdata = new HashMap<String, Object>();
        refdata.put("studySiteStatusRefData", configMap
                .get("studySiteStatusRefData"));
        refdata.put("healthcareSites", healthcareSiteDao.getAll());
        return refdata;
    }

    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(healthcareSiteDao.domainClass(),
                new CustomDaoEditor(healthcareSiteDao));

    }

    /**
     * Create a nested object graph that Create Research Staff needs
     *
     * @param request -
     *            HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {

        ResearchStaff researchStaff = new ResearchStaff();
        researchStaff = createResearchStaffWithContacts(researchStaff);

        return researchStaff;
    }

    private ResearchStaff createResearchStaffWithContacts(ResearchStaff rs) {

        ContactMechanism contactMechanismEmail = new ContactMechanism();
        ContactMechanism contactMechanismPhone = new ContactMechanism();
        ContactMechanism contactMechanismFax = new ContactMechanism();
        contactMechanismEmail.setType(ContactMechanismType.EMAIL);
        contactMechanismPhone.setType(ContactMechanismType.PHONE);
        contactMechanismFax.setType(ContactMechanismType.Fax);
        rs.addContactMechanism(contactMechanismEmail);
        rs.addContactMechanism(contactMechanismPhone);
        rs.addContactMechanism(contactMechanismFax);

        addUserGroups(rs);

        return rs;
    }


    private void addUserGroups(ResearchStaff rs) {
        rs.addGroup(C3PRUserGroupType.C3PR_ADMIN);
        rs.addGroup(C3PRUserGroupType.REGISTRAR);
        rs.addGroup(C3PRUserGroupType.STUDY_COORDINATOR);
    }

    protected boolean isFormSubmission(HttpServletRequest request)
    {
        if((request.getAttribute("type") != null)&& (request.getAttribute("type").equals("confirm")))
            return false;
        return super.isFormSubmission(request);
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
        // TODO Auto-generated method stub
        Map<String, Object> configMap = configurationProperty.getMap();
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
            if (group==null)
                gIterator.remove();
        }

        try {
            personnelService.save(researchStaff);
        } catch (C3PRBaseException e) {
            logger.error(e);
            //TODO should be in validator
            if(e.getLocalizedMessage().contains("uq_login_name")){
            errors.reject("Username already exists");
            }
        }

        Map map = errors.getModel();
        map.put("studySiteStatusRefData", configMap
                .get("studySiteStatusRefData"));
        map.put("healthcareSites", healthcareSiteDao.getAll());

        request.setAttribute("fullName", researchStaff.getFullName());
        request.setAttribute("type", "confirm");

        ModelAndView mv = new ModelAndView(getSuccessView(), errors.getModel());
        mv.addObject("fullName",researchStaff.getFullName());

        return mv;

    }


    private void handleRowAction(ResearchStaff researchStaff, String action,
                                 String selected) {
        if ("addContact".equals(action)) {
            ContactMechanism contactMechanism = new ContactMechanism();
            researchStaff.addContactMechanism(contactMechanism);
        } else if ("removeContact".equals(action)) {
            researchStaff.getContactMechanisms().remove(
                    Integer.parseInt(selected));
        }

    }


    public PersonnelService getPersonnelService() {
        return personnelService;
    }

    public void setPersonnelService(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    protected List<HealthcareSite> getHealthcareSites() {
        return healthcareSiteDao.getAll();
    }

    public ConfigurationProperty getConfigurationProperty() {
        return configurationProperty;
    }

    public void setConfigurationProperty(
            ConfigurationProperty configurationProperty) {
        this.configurationProperty = configurationProperty;
    }

    public ResearchStaffDao getResearchStaffDao() {
        return researchStaffDao;
    }

    public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
        this.researchStaffDao = researchStaffDao;
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

}

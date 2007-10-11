package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.utils.ContextTools;
import gov.nih.nci.security.UserProvisioningManager;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.AbstractAnnotationAwareTransactionalTests;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.mail.MailException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 7, 2007
 * Time: 2:06:36 PM
 * To change this template use File | Settings | File Templates.
 */


public class PersonnelServiceTestCase extends AbstractAnnotationAwareTransactionalTests {

    private PersonnelService service;
    private UserProvisioningManager upm;
    private OrganizationService orgService;
    String strValue;

    private ContactMechanism email;

    private ResearchStaff staff;

    public PersonnelServiceTestCase() {
        setAutowireMode(AUTOWIRE_BY_TYPE);
        strValue = "test" + String.valueOf(Math.random()).substring(0, 5);
    }


    public void testCreateReasearchStaff()throws Exception{
        //first create the organization
        HealthcareSite site = new HealthcareSite();
        site.setNciInstituteCode(strValue);
        site.setName(strValue);
        site.setDescriptionText(strValue);
        orgService.save(site);

        //now save the research staff
        staff = new ResearchStaff();
        staff.setHealthcareSite(site);
        staff.setNciIdentifier("test-user");
        staff.setFirstName("test");
        staff.setLastName("user");
        email = new ContactMechanism();
        email.setType(ContactMechanismType.EMAIL);
        email.setValue("test-user@test.org");
        staff.addContactMechanism(email);
        service.save(staff);

        //now change the staff details
        staff.setFirstName("changed");
        staff.removeContactMechanism(email);
        email.setValue("changed");
        staff.addContactMechanism(email);
        staff.setNciIdentifier("changed");
        service.merge(staff);
    }


    protected void onTearDownAfterTransaction() throws Exception {
        jdbcTemplate.execute("delete from csm_user where login_name='" + email.getValue() + "'");
        jdbcTemplate.execute("delete from csm_user where login_name='" + email.getValue() + "'");
        
    }

    protected ConfigurableApplicationContext loadContext(Object object) throws Exception {

        return (ConfigurableApplicationContext) ContextTools.createDeployedApplicationContext();
    }


    public OrganizationService getOrgService() {
        return orgService;
    }

    public void setOrgService(OrganizationService orgService) {
        this.orgService = orgService;
    }

    public PersonnelService getService() {
        return service;
    }

    public void setService(PersonnelService service) {
        this.service = service;
    }

    public UserProvisioningManager getUpm() {
        return upm;
    }

    public void setUpm(UserProvisioningManager upm) {
        this.upm = upm;
    }
}

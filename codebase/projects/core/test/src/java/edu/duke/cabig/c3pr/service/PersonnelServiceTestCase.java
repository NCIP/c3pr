package edu.duke.cabig.c3pr.service;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.AbstractAnnotationAwareTransactionalTests;

import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.utils.ContextTools;
import gov.nih.nci.security.UserProvisioningManager;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 7, 2007 Time: 2:06:36 PM To change this template
 * use File | Settings | File Templates.
 */

public class PersonnelServiceTestCase extends AbstractAnnotationAwareTransactionalTests {

    private PersonnelService personnelService;

    private UserProvisioningManager csmUserProvisioningManager;

    private OrganizationService organizationService ;

    String strValue;

    private ContactMechanism email;

    private ResearchStaff researchStaff ;

    public PersonnelServiceTestCase() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        strValue = "test" + String.valueOf(Math.random()).substring(0, 5);
    }

    public void testCreateReasearchStaff() throws Exception {
        // first create the organization
        HealthcareSite site = new HealthcareSite();
        site.setNciInstituteCode(strValue);
        site.setName(strValue);
        site.setDescriptionText(strValue);
        organizationService.save(site);

        // now save the research staff
        researchStaff = new ResearchStaff();
        researchStaff.setHealthcareSite(site);
        researchStaff.setNciIdentifier("test-user");
        researchStaff.setFirstName("test");
        researchStaff.setLastName("user");
        email = new ContactMechanism();
        email.setType(ContactMechanismType.EMAIL);
        email.setValue("test-user@test.org");
        researchStaff.addContactMechanism(email);
        personnelService.save(researchStaff);

        // now change the staff details
        researchStaff.setFirstName("changed");
        researchStaff.removeContactMechanism(email);
        email.setValue("changed");
        researchStaff.addContactMechanism(email);
        researchStaff.setNciIdentifier("changed");
        personnelService.merge(researchStaff);

        assertNotNull(personnelService.getGroups(researchStaff));
    }

    protected void onTearDownAfterTransaction() throws Exception {
        jdbcTemplate.execute("delete from csm_user_group");
        jdbcTemplate.execute("delete from csm_user");
        jdbcTemplate.execute("delete from csm_user_group_role_pg");

        jdbcTemplate.execute("delete from csm_group");
        jdbcTemplate.execute("delete from csm_pg_pe");
        jdbcTemplate.execute("delete from csm_protection_element");
        jdbcTemplate.execute("delete from csm_protection_group");
    }

    protected ConfigurableApplicationContext loadContext(Object object) throws Exception {

        return (ConfigurableApplicationContext) ContextTools.createDeployedApplicationContext();
    }

	public PersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	public UserProvisioningManager getCsmUserProvisioningManager() {
		return csmUserProvisioningManager;
	}

	public void setCsmUserProvisioningManager(
			UserProvisioningManager csmUserProvisioningManager) {
		this.csmUserProvisioningManager = csmUserProvisioningManager;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

}

package edu.duke.cabig.c3pr.service;

import java.util.Calendar;

import org.springframework.context.ConfigurableApplicationContext;

import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalContactMechanism;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.utils.ContextTools;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.DateUtil;
import gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo;
import gov.nih.nci.security.UserProvisioningManager;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 7, 2007 Time: 2:06:36 PM To change this template
 * use File | Settings | File Templates.
 */

public class PersonnelServiceTestCase extends DaoTestCase {

	 public static final DataAuditInfo INFO = new DataAuditInfo("user", "127.0.0.0", DateUtil
             .createDate(2004, Calendar.NOVEMBER, 2), "c3pr/study");
	 
    private PersonnelService personnelService;

    private UserProvisioningManager csmUserProvisioningManager;

    private OrganizationService organizationService ;

    String strValue;

    private ContactMechanism email;

    private ResearchStaff researchStaff ;

    public PersonnelServiceTestCase() {
//        setAutowireMode(AUTOWIRE_BY_NAME);
        strValue = "test" + String.valueOf(Math.random()).substring(0, 5);
        organizationService = (OrganizationService) getApplicationContext()
    	.getBean("organizationService");
        personnelService = (PersonnelService) getApplicationContext()
    	.getBean("personnelService");
    }
    
    public void testCreateReasearchStaff() throws Exception {
        // first create the organization
        HealthcareSite site = new LocalHealthcareSite();
        site.setCtepCode(strValue);
        site.setName(strValue);
        site.setDescriptionText(strValue);
        organizationService.save(site);

        // now save the research staff
        researchStaff = new LocalResearchStaff();
        researchStaff.setHealthcareSite(site);
        researchStaff.setNciIdentifier("test-user");
        researchStaff.setFirstName("test");
        researchStaff.setLastName("user");
//        email = new LocalContactMechanism();
//        email.setType(ContactMechanismType.EMAIL);
//        email.setValue("test-user@test.org");
//        researchStaff.addContactMechanism(email);
        researchStaff.setEmail("test-user@test.org");
        researchStaff.setLoginId("test-user@test.org");
        personnelService.save(researchStaff);

        // now change the staff details
        researchStaff.setFirstName("changed");
//        researchStaff.removeContactMechanism(email);
//        email.setValue("changed");
//        researchStaff.addContactMechanism(email);
        researchStaff.setEmail("test-user-changed@test.org");
        researchStaff.setNciIdentifier("changed");
        personnelService.merge(researchStaff);

        assertNotNull(personnelService.getGroups(researchStaff));
    }
    
    @Override
    protected void setUp() throws Exception {
    	super.setUp();
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

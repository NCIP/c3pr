package edu.duke.cabig.c3pr.security;

import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import org.springframework.test.annotation.ExpectedException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 13, 2007
 * Time: 3:19:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SitePrivilegeAndObjectIdGeneratorTest extends ApplicationTestCase {

    private SitePrivilegeAndObjectIdGenerator sitePrivilegeAndObjectIdGenerator;
    private HealthcareSite site;

    protected void setUp() throws Exception {
        sitePrivilegeAndObjectIdGenerator = (SitePrivilegeAndObjectIdGenerator) getDeployedCoreApplicationContext().getBean("sitePrivilegeAndObjectIdGenerator");

        site = new HealthcareSite();
        site.setNciInstituteCode("testSite");
    }

    public void testPrivilegeGenerator(){
        String privilege = sitePrivilegeAndObjectIdGenerator.generatePrivilege(site);
        assertNotNull(privilege);
        assertEquals(site.getClass().getName() + ".ACCESS",privilege);
    }

    @ExpectedException(Exception.class)
    public void testNullObjectGenerator(){
        try {
            sitePrivilegeAndObjectIdGenerator.generateId(null);
            fail("Should throw an exception for null object");
        } catch (Exception e) {
            //should happen
        }
    }

    public void testObjectIdGenerator(){
        String id = sitePrivilegeAndObjectIdGenerator.generateId(site);
        assertNotNull(id);
        assertEquals(id,site.getClass().getName() + "." + site.getNciInstituteCode());
    }

}

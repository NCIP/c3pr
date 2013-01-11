/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.security;

import org.springframework.test.annotation.ExpectedException;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import gov.nih.nci.cabig.ctms.suite.authorization.ScopeType;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 13, 2007 Time: 3:19:07 PM To change this template
 * use File | Settings | File Templates.
 */
public class SitePrivilegeAndObjectIdGeneratorTest extends ApplicationTestCase {

    private SitePrivilegeAndObjectIdGenerator sitePrivilegeAndObjectIdGenerator;

    private HealthcareSite site;

    protected void setUp() throws Exception {
        sitePrivilegeAndObjectIdGenerator = (SitePrivilegeAndObjectIdGenerator) getDeployedCoreApplicationContext()
                        .getBean("sitePrivilegeAndObjectIdGenerator");

        site = new LocalHealthcareSite();
        site.setCtepCode("testSite");
    }

    @ExpectedException(Exception.class)
    public void testNullObjectGenerator() {
        try {
            sitePrivilegeAndObjectIdGenerator.generateId(null);
            fail("Should throw an exception for null object");
        }
        catch (Exception e) {
            // should happen
        }
    }

    public void testObjectIdGenerator() {
        String id = sitePrivilegeAndObjectIdGenerator.generateId(site);
        assertNotNull(id);
        assertEquals(id, ScopeType.SITE.getScopeCsmNamePrefix() + site.getPrimaryIdentifier());
    }

}

/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_ORGANIZATION;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.ExpectedException;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.ContextTools;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import gov.nih.nci.security.dao.DIAuthorizationDao;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 7, 2007 Time: 2:06:36 PM To change this template
 * use File | Settings | File Templates.
 */

@C3PRUseCases( { CREATE_ORGANIZATION })
public class OrganizationServiceTest extends DaoTestCase {

    private OrganizationService organizationService;

    private DIAuthorizationDao csmAuthorizationDao;

    private HealthcareSite dummySite;

    public static final String HCS_NAME = "Duke";

    public OrganizationServiceTest() {
    	organizationService = (OrganizationService) getApplicationContext()
    	.getBean("organizationService");
        String strValue = "test" + String.valueOf(Math.random());

        dummySite = new LocalHealthcareSite();
        dummySite.setName(HCS_NAME);
        dummySite.setDescriptionText(strValue);
        dummySite.setCtepCode(strValue);
        dummySite.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown(); 
    }

    public void testUPM() throws Exception {
        organizationService.save(dummySite);
        assertEquals(organizationService.getSiteNameByNciIdentifier(dummySite.getCtepCode()),dummySite.getName());
//        assertEquals(1, jdbcTemplate.queryForInt("Select count(*) from csm_group where group_name='edu.duke.cabig.c3pr.domain.HealthcareSite."
//                                                        + dummySite.getNciInstituteCode() + "'"));

    }

    @ExpectedException(C3PRBaseException.class)
    public void testDuplicatesNotAllowed() throws Exception {
        organizationService.save(dummySite);
        try{
        	organizationService.save(dummySite);
        	fail("Should not save duplicate site");
        } catch(Exception e){
        	
        }
    }

//    protected void onTearDownAfterTransaction() throws Exception {
//        jdbcTemplate.execute("Delete from csm_group where group_name='"
//                        + dummySite.getNciInstituteCode() + "'");
//    }

    protected ConfigurableApplicationContext loadContext(Object object) throws Exception {
        return (ConfigurableApplicationContext) ContextTools.createDeployedCoreApplicationContext();
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public DIAuthorizationDao getCsmAuthorizationDao() {
        return csmAuthorizationDao;
    }

    public void setCsmAuthorizationDao(DIAuthorizationDao csmAuthorizationDao) {
        this.csmAuthorizationDao = csmAuthorizationDao;
    }

}

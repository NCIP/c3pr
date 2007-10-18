package edu.duke.cabig.c3pr.service;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_ORGANIZATION;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.ContextTools;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.dao.DIAuthorizationDao;
import gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.AbstractAnnotationAwareTransactionalTests;
import org.springframework.test.annotation.ExpectedException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 7, 2007
 * Time: 2:06:36 PM
 * To change this template use File | Settings | File Templates.
 */

@C3PRUseCases({
        CREATE_ORGANIZATION
        })
public class OrganizationServiceTest extends AbstractAnnotationAwareTransactionalTests {

    private OrganizationService organizationService;
    private DIAuthorizationDao csmAuthorizationDao;
    private HealthcareSite dummySite;
    private OrganizationDao organizationDao;

    public OrganizationServiceTest() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        String strValue = "test" + String.valueOf(Math.random());


        dummySite = new HealthcareSite();
        dummySite.setName(strValue);
        dummySite.setDescriptionText(strValue);
        dummySite.setNciInstituteCode(strValue);

    }


    protected void onSetUp() throws Exception {
        super.onSetUp();    //To change body of overridden methods use File | Settings | File Templates.
        DataAuditInfo.setLocal(DaoTestCase.INFO);

    }


    protected void onTearDown() throws Exception {
        super.onTearDown();    //To change body of overridden methods use File | Settings | File Templates.
        DataAuditInfo.setLocal(null);
    }

    public void testCreateOrganization() throws Exception {
        int initialSize = organizationDao.getAll().size();
        organizationService.save(dummySite);
        assertEquals(initialSize, organizationDao.getAll().size() - 1);
    }


    public void testUPM() throws Exception {
        organizationService.save(dummySite);
        assertEquals(1, jdbcTemplate.queryForInt("Select count(*) from csm_group where group_name='edu.duke.cabig.c3pr.domain.HealthcareSite." + dummySite.getNciInstituteCode() + "'"));

    }

    @ExpectedException(C3PRBaseException.class)
    public void testDuplicatesNotAllowed() throws Exception {
        organizationService.save(dummySite);

        organizationService.save(dummySite);
        fail("Should not save duplicate site");

    }

//    @ExpectedException(C3PRBaseException.class)
//    public void testTransactionCapability() throws Exception{
//        dummySite.setNciInstituteCode(null);
//        organizationService.save(dummySite);
//        fail("Should not save site");
//    }
//

    protected void onTearDownAfterTransaction() throws Exception {
        jdbcTemplate.execute("Delete from csm_group where group_name='" + dummySite.getNciInstituteCode() + "'");
    }

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

    public OrganizationDao getOrganizationDao() {
        return organizationDao;
    }

    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }
}

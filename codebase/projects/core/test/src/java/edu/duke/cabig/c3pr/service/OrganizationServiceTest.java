package edu.duke.cabig.c3pr.service;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_ORGANIZATION;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.ContextTools;
import gov.nih.nci.security.UserProvisioningManager;
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

    private OrganizationService service;
    private UserProvisioningManager upm;
    private HealthcareSite dummySite;
    private OrganizationDao dao;

    public OrganizationServiceTest() {
        setAutowireMode(AUTOWIRE_BY_TYPE);
        String strValue = "test" + String.valueOf(Math.random()).substring(0, 5);


        dummySite = new HealthcareSite();
        dummySite.setName(strValue);
        dummySite.setDescriptionText(strValue);
        dummySite.setNciInstituteCode(strValue);

    }


    public void testCreateOrganization() throws Exception {
        int initialSize = dao.getAll().size();
        service.save(dummySite);
        assertEquals(initialSize, dao.getAll().size() - 1);
    }


    public void testUPM() throws Exception {
        service.save(dummySite);
        assertEquals(1, jdbcTemplate.queryForInt("Select count(*) from csm_group where group_name='edu.duke.cabig.c3pr.domain.HealthcareSite." + dummySite.getNciInstituteCode() + "'"));

    }

    @ExpectedException(C3PRBaseException.class)
    public void testDuplicatesNotAllowed() throws Exception {
        service.save(dummySite);

        service.save(dummySite);
        fail("Should not save duplicate site");

    }

//    @ExpectedException(C3PRBaseException.class)
//    public void testTransactionCapability() throws Exception{
//        dummySite.setNciInstituteCode(null);
//        service.save(dummySite);
//        fail("Should not save site");
//    }
//

    protected void onTearDownAfterTransaction() throws Exception {
        jdbcTemplate.execute("Delete from csm_group where group_name='" + dummySite.getNciInstituteCode() + "'");
    }

    protected ConfigurableApplicationContext loadContext(Object object) throws Exception {
        return (ConfigurableApplicationContext) ContextTools.createDeployedCoreApplicationContext();
    }


    public OrganizationService getService() {
        return service;
    }

    public void setService(OrganizationService service) {
        this.service = service;
    }

    public UserProvisioningManager getUpm() {
        return upm;
    }

    public void setUpm(UserProvisioningManager upm) {
        this.upm = upm;
    }


    public OrganizationDao getDao() {
        return dao;
    }

    public void setDao(OrganizationDao dao) {
        this.dao = dao;
    }
}

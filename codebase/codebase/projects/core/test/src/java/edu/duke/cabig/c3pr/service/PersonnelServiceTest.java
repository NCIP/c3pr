package edu.duke.cabig.c3pr.service;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_RESEARCH_STAFF;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_STUDY_INVESTIGATOR;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 26, 2007 Time: 12:33:44 PM To change this
 * template use File | Settings | File Templates.
 */
@C3PRUseCases( { CREATE_RESEARCH_STAFF, CREATE_STUDY_INVESTIGATOR })
public class PersonnelServiceTest extends DaoTestCase {

    private PersonnelService service;

    Investigator inv;

    protected void setUp() throws Exception {
        super.setUp(); // To change body of overridden methods use File | Settings | File
                        // Templates.
        service = (PersonnelService) getApplicationContext().getBean("personnelService");

        inv = new Investigator();
        inv.setFirstName("Dummy");
        inv.setLastName("User");

        ContactMechanism cm = new ContactMechanism();
        cm.setType(ContactMechanismType.EMAIL);
        cm.setValue("dummy@dummy.org");

        inv.addContactMechanism(cm);

    }

    @Override
    protected void tearDown() throws Exception {
    	super.tearDown();
    }
    public void testCreateUser() throws Exception {
        service.save(inv);

    }

    /**
     * What dao class is the test trying to Masquerade
     * 
     * @return
     */
    public Class<HealthcareSiteInvestigatorDao> getMasqueradingDaoClassName() {
        return HealthcareSiteInvestigatorDao.class;
    }
}

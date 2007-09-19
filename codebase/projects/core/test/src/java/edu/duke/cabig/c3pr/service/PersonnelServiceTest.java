package edu.duke.cabig.c3pr.service;

import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_RESEARCH_STAFF;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_STUDY_INVESTIGATOR;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.service.impl.PersonnelServiceImpl;
import edu.duke.cabig.c3pr.utils.MasqueradingDaoTestCase;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import org.springframework.mail.MailSender;


/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 26, 2007
 * Time: 12:33:44 PM
 * To change this template use File | Settings | File Templates.
 */
@C3PRUseCases({CREATE_RESEARCH_STAFF, CREATE_STUDY_INVESTIGATOR})
public class PersonnelServiceTest extends MasqueradingDaoTestCase<HealthcareSiteInvestigatorDao> {

    private PersonnelServiceImpl
            service;
    private UserProvisioningManager mockUPM;
    Investigator inv;
    MailSender mockMailSender;


    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
        service = (PersonnelServiceImpl) getApplicationContext().getBean("personnelService");
        mockUPM = EasyMock.createMock(UserProvisioningManager.class);
        mockMailSender = EasyMock.createNiceMock(MailSender.class);


        service.setUserProvisioningManager(mockUPM);
        service.setMailSender(mockMailSender);

        inv = new Investigator();
        inv.setFirstName("Dummy");
        inv.setLastName("User");

        ContactMechanism cm = new ContactMechanism();
        cm.setType(ContactMechanismType.EMAIL);
        cm.setValue("dummy@dummy.org");

        inv.addContactMechanism(cm);


    }

    public void testCreateUser() throws Exception {

        mockUPM.createUser(isA(User.class));
        replay(mockUPM);

        service.save(inv);

        verify(mockUPM);
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

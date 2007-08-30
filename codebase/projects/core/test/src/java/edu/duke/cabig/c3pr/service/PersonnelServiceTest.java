package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDaoTest;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.service.impl.PersonnelServiceImpl;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;
import edu.nwu.bioinformatics.commons.ResourceRetriever;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;

import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 26, 2007
 * Time: 12:33:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonnelServiceTest extends ContextDaoTestCase<HealthcareSiteInvestigatorDao> {

    private PersonnelServiceImpl service;
    private UserProvisioningManager mockUPM;



    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
        service = (PersonnelServiceImpl)getApplicationContext().getBean("personnelService");
        mockUPM = EasyMock.createMock(UserProvisioningManager.class);

        service.setUserProvisioningManager(mockUPM);

    }

    public void testCreateInvestigator() throws Exception{


        Investigator inv = new Investigator();
        inv.setFirstName("Dummy");
        inv.setLastName("User");

        ContactMechanism cm = new ContactMechanism();
        cm.setType(ContactMechanismType.EMAIL);
        cm.setValue("dummy@dummy.org");

        inv.addContactMechanism(cm);

        mockUPM.createUser(isA(User.class));
        replay(mockUPM);

        service.save(inv);

        verify(mockUPM);


    }


//Mock to be some other test

    protected InputStream handleTestDataFileNotFound() throws FileNotFoundException {
        return ResourceRetriever.getResource(InvestigatorDaoTest.class.getPackage(),getTestDataFileName());
    }


    protected String getClassNameWithoutPackage() {
        return InvestigatorDaoTest.class.getSimpleName();
    }
}

package gov.nih.nci.cabig.c3pr.grid.test;

import junit.framework.TestCase;
import gov.nih.nci.cabig.ctms.service.CRPRV2RegistrationConsumer;
import gov.nih.nci.cagrid.common.Utils;

import java.io.StringReader;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import edu.duke.cabig.c3pr.service.StudyService;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 14, 2007
 * Time: 12:08:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3PRV2RegistrationConsumerTest extends ApplicationTestCase {
    CRPRV2RegistrationConsumer gridService = new CRPRV2RegistrationConsumer();

    private String sampleMessage="SampleRegistrationMessage.xml";
    private StudyService studyService;


    protected void setUp() throws Exception {
        studyService = registerMockFor(StudyService.class);
        gridService.setStudyService(studyService);

    }

    public void testRegister(){
        try {
            gov.nih.nci.cabig.ctms.grid.RegistrationType registrationMessage =
                    (gov.nih.nci.cabig.ctms.grid.RegistrationType)
                            Utils.deserializeDocument(sampleMessage,gov.nih.nci.cabig.ctms.grid.RegistrationType.class);


            gridService.register(registrationMessage);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }



}

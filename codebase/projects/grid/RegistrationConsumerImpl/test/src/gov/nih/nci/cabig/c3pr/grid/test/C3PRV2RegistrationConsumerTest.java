package gov.nih.nci.cabig.c3pr.grid.test;

import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import gov.nih.nci.cabig.ctms.service.C3PRV2RegistrationConsumer;
import gov.nih.nci.cagrid.common.Utils;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Tests the grid service impl for c3prv2
 *
 * @testType unit
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 14, 2007
 * Time: 12:08:09 AM                                        
 * To change this template use File | Settings | File Templates.
 */
public class C3PRV2RegistrationConsumerTest extends ApplicationTestCase {
    C3PRV2RegistrationConsumer gridService = new C3PRV2RegistrationConsumer();

    private String sampleMessage="SampleRegistrationMessage.xml";


    protected void setUp() throws Exception {
        StudyService studyService = registerMockFor(StudyService.class);
        gridService.setStudyService(studyService);

    }
   
    public void testRegister(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(sampleMessage);
            InputStreamReader reader = new InputStreamReader(is);

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeDocument(reader,gov.nih.nci.ccts.grid.Registration.class);
            gridService.register(registrationMessage);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }



}

package gov.nih.nci.cabig.c3pr.grid.test;

import junit.framework.TestCase;
import gov.nih.nci.cabig.ctms.service.CRPRV2RegistrationConsumer;
import gov.nih.nci.cagrid.common.Utils;

import java.io.StringReader;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 14, 2007
 * Time: 12:08:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3PRV2RegistrationConsumerTest extends AbstractDependencyInjectionSpringContextTests {

    private String sampleMessage="resources/SampleRegistrationMessage.xml";
    private CRPRV2RegistrationConsumer gridService;

    
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


    public CRPRV2RegistrationConsumer getGridService() {
        return gridService;
    }

    public void setGridService(CRPRV2RegistrationConsumer gridService) {
        this.gridService = gridService;
    }

    protected String[] getConfigLocations() {
             return new String[] { "classpath:applicationContext-grid.xml" };
    }
}

package gov.nih.nci.cabig.c3pr.grid;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.ccts.grid.common.RegistrationConsumer;
import gov.nih.nci.ccts.grid.stubs.types.InvalidRegistrationException;

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
public class CoOrdinatingSiteRegistrationConsumerTest extends DaoTestCase {
    RegistrationConsumer gridService;
    ApplicationContext ctx =    new ClassPathXmlApplicationContext (new String[] {
            "classpath*:applicationContext-grid.xml",
    	});
    
    private String sampleMessage="SampleRegistrationMessage.xml";
    protected void setUp() throws Exception {
    	super.setUp();
    	gridService= (RegistrationConsumer)ctx.getBean("registrationConsumer");
    }
   
    /*
     * No participant identifier
     */
    public void testRegisterCase0(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-0.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","100", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");

    }
    
    /*
     * participant identifier with invalid healthcaresite as source
     */
    public void testRegisterCase1(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-1.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","101", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");

    }

    /*
     * Multiple Participant with same MRN
     */
    public void testRegisterCase2(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-2.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","102", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");
    }

    /*
     * Different Participant with same MRN
     */
    public void testRegisterCase3(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-3.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","103", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");
    }

    /*
     * study co ordinating center identifier with invalid healthcaresite as source
     */
    public void testRegisterCase4(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-4.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","104", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");
    }

    /*
     * No Study identifier
     */
    public void testRegisterCase5(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-5.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","105", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");
    }
    /*
     * No Study with Co ordinating center identifier
     */
    public void testRegisterCase6(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-6.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","106", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");
    }


    /*
     * multiple Study with Co ordinating center identifier
     */
    public void testRegisterCase7(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-7.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","107", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");
    }

    /*
     * No StudySite with NCI code
     */
    public void testRegisterCase8(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-8.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        }catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","108", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");
    }
    /*
     * Multiple study subjects with same participant at same study site
     */
    public void testRegisterCase9(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-9.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","109", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");
    }
    

    /*
     * No Epoch with same name
     */
    public void testRegisterCase11(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-11.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","111", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");
    }
    /*
     * No Arm with name
     */
    public void testRegisterCase12(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleRegistrationMessage-12.xml");
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (InvalidRegistrationException e) {
        	e.printStackTrace();
        	assertEquals("Wrong exception code-","112", e.getFaultCode().getLocalPart());
        	return;
        } catch (Exception e) {
        	e.printStackTrace();
        	fail("Wrong exception.");
        	return;
        }
        fail("Should have thrown an exception");
    }
    
    public void testRegisterSuccess(){
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(sampleMessage);
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("gov/nih/nci/ccts/grid/client/client-config.wsdd");

            gov.nih.nci.ccts.grid.Registration registrationMessage =
                    (gov.nih.nci.ccts.grid.Registration)
                            Utils.deserializeObject(reader,gov.nih.nci.ccts.grid.Registration.class,resourceAsStream);
            gridService.register(registrationMessage);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @Override
    public ApplicationContext getApplicationContext() {
    	// TODO Auto-generated method stub
    	return ctx;
    }


}

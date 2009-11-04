package edu.duke.cabig.c3pr.test.system;

import com.atomicobject.haste.framework.Story;
import edu.duke.cabig.c3pr.test.system.steps.*;
import edu.duke.cabig.c3pr.test.system.util.ESBHelper;
import gov.nci.nih.cagrid.tests.core.steps.*;
import gov.nci.nih.cagrid.tests.core.util.GlobusHelper;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import java.io.File;
import java.util.Vector;

/**
 * Integration tests does the following
 * 1. Deploy the ESB
 * 2. Deploy Service Assembly for CTMSI into the ESB container
 * 3. Deploy Globus
 * 4. Start ESB and Globus
 * 5. Send a test message to the ESB
 * @testType integration
 * @steps ESBCreateStep,GlobusCreateStep,ESBStartStep,GlobusDeployServiceStep,RegistrationGridServiceConfigStep
 * @steps GlobusStartStep,ESBSendMessageStep,ESBStopStep,ESBCleanupStep,GlobusCleanupStep
 */
public class CTMISystemTest
        extends Story {

    private GlobusHelper globus;
    private int port;
    private ESBHelper esb;

    public CTMISystemTest()
    {
        super();
    }

    protected boolean storySetUp()
            throws Throwable
    {
        return true;
    }

    protected void storyTearDown()
            throws Throwable
    {
        if (globus != null) {
            globus.stopGlobus(port);
            globus.cleanupTempGlobus();
        }


        if(esb!=null){
            esb.getEsbProcess().destroy();
            esb.cleanupTempESB();
        }
    }

    @SuppressWarnings("unchecked")
    protected Vector steps(){
        globus = new GlobusHelper();
        port = Integer.parseInt(System.getProperty("test.globus.port", "8015"));

        File sampleMsg = new File("test","resources" + File.separator + "esb"
                + File.separator + "SampleRegistration.xml");
        String sendQueue="registration-message.inputQueue";
        String recvQueue="registration-message.outputQueue";
        String esbURL = "tcp://localhost:61616";

        //required property. Defaults by navigating the filesystem
        String c3prProjectsHome = System.getProperty("c3pr.projects.home",".." + File.separator);

        File esbDir = new File(c3prProjectsHome,".." + File.separator + "antfiles");
        esb = new ESBHelper(esbDir);

        File serviceDir =   new File(c3prProjectsHome,"grid" + File.separator + "RegistrationConsumer");
        File serviceImplDir =   new File(c3prProjectsHome,"grid" + File.separator + "RegistrationConsumerImpl");

        Vector steps = new Vector();

        //init containers
        steps.add(new ESBCreateStep(esb));
        steps.add(new GlobusCreateStep(globus));
        steps.add(new ESBStartStep(esb));
        //deploy service
        steps.add(new GlobusDeployServiceStep(globus, serviceDir));
        steps.add(new RegistrationGridServiceConfigStep(globus,serviceImplDir));
        //start containers
        steps.add(new GlobusStartStep(globus, port));
        //send message
        steps.add(new ESBSendMessageStep(esbURL,sendQueue,recvQueue,sampleMsg));
        //shutdown containers
        steps.add(new ESBStopStep(esb));
        steps.add(new GlobusStopStep(globus, port));
        //cleanup
        steps.add(new ESBCleanupStep(esb));
        steps.add(new GlobusCleanupStep(globus));

        return steps;
    }

    public String getDescription() {
        return "RegistrationGridServiceTest";

    }


    /**
     * used to make sure that if we are going to use a junit testsuite to test
     * this that the test suite will not error out looking for a single test......
     */
    public void testDummy() throws Throwable {
    }


    /**
     * Convenience method for running all the Steps in this Story.
     */
    public static void main(String args[]) {
        TestRunner runner = new TestRunner();
        TestResult result = runner.doRun(new TestSuite(CTMISystemTest.class));
        System.exit(result.errorCount() + result.failureCount());
    }
}

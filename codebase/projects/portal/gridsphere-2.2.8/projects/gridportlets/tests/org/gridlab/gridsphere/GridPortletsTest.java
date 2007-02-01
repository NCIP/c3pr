/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: GridPortletsTest.java,v 1.1.1.1 2007-02-01 20:42:25 kherm Exp $
 */
package org.gridlab.gridsphere;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.cactus.ServletTestCase;
import org.apache.log4j.PropertyConfigurator;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.gridforum.jgss.ExtendedGSSCredential;
import org.gridforum.jgss.ExtendedGSSManager;
import org.gridlab.gridsphere.services.job.types.gram.impl.GramJobSubmissionServiceImplTest;
import org.gridlab.gridsphere.services.job.types.gram.impl.GramJobSpecImplTest;
import org.gridlab.gridsphere.services.security.gss.CredentialException;
import org.gridlab.gridsphere.services.security.gss.CredentialRetrievalTest;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.gridlab.gridsphere.portletcontainer.GridSphereServletTest;
import org.gridlab.gridsphere.services.core.user.SetupRootUserTest;
import org.gridlab.gridsphere.services.file.FileLocationTest;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Simple class to build a TestSuite out of the individual test classes.
 */
public class GridPortletsTest extends ServletTestCase {

    protected static PortletLog log = SportletLog.getInstance(GridPortletsTest.class);
    private static Properties testProperties = new Properties();
    private static boolean testPropertiesLoaded = false;
    
    public GridPortletsTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.main(new String[] {GridPortletsTest.class.getName()});
    }

    public static Test suite() {
        URL propsUrl = GridPortletsTest.class.getResource("/gridsphere/log4j.properties");
        PropertyConfigurator.configure(propsUrl);


        TestSuite suite = new TestSuite();
        suite.addTest(new CredentialRetrievalTest("testRetrieveCredential"));
        suite.addTest(new GridSphereServletTest("testInitGridSphere"));
        suite.addTest(new SetupRootUserTest("testLoginRootUser"));


        // GRAM Job Submission Service Tests
        suite.addTest(new FileLocationTest("testFileLocationClass"));
        suite.addTest(new GramJobSpecImplTest("testRSLGeneration"));

        Properties testProperties = GridPortletsTest.getTestProperties();

        String hostName = testProperties.getProperty("forkHostName");
        if (hostName != null && hostName.length() > 0){
            suite.addTest(new GramJobSubmissionServiceImplTest("testForkSimpleEchoJob"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testForkBlocking"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testForkStdOut"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testForkStageIn"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testForkStageOut"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testForkStageLargeTx"));
        }
                
        hostName = testProperties.getProperty("condorHostName");
        if (hostName != null && hostName.length() > 0){
            suite.addTest(new GramJobSubmissionServiceImplTest("testCondorSimpleEchoJob"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testCondorBlocking"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testCondorStdOut"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testCondorStageIn"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testCondorStageOut"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testCondorStageLargeTx"));
        }
        
        hostName = testProperties.getProperty("pbsHostName");
        if (hostName != null && hostName.length() > 0){
            suite.addTest(new GramJobSubmissionServiceImplTest("testPbsSimpleEchoJob"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testPbsBlocking"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testPbsStdOut"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testPbsStageIn"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testPbsStageOut"));
            suite.addTest(new GramJobSubmissionServiceImplTest("testPbsStageLargeTx"));
        }
        
        // GRMS Job Submission Service Tests
//        suite.addTest(new GrmsJobSubmissionServiceImplTest("testBinDateJob"));
        return suite;
    }






    /*** Helper methods ***/
    
    /**
     * Get the properties specified in the /gridportlets/test.properties file.
     * @return
     */
    public static Properties getTestProperties(){
        if (testPropertiesLoaded == false){
            System.out.println("Loading test properties.");
            InputStream in = GridPortletsTest.class.getResourceAsStream("/gridportlets/test.properties");
            try {
                testProperties.load(in);
                testPropertiesLoaded = true;
            }
            catch (IOException e) {
                e.printStackTrace();
                fail("Could not load /gridportlets/test.properties");
            }
        }
        System.out.println("Returning test properties.");
        return testProperties;
    }

    /**
     * Get credentialContext depending on test.properties configuration.
     * @return
     * @throws CredentialException
     */
    public static GSSCredential createCredential()
            throws CredentialException {
        Properties testProperties = GridPortletsTest.getTestProperties();
        GSSCredential gssCred = null;
        
        String hostProxyFileLocation = testProperties.getProperty("hostProxyFileLocation");
        String hostCertFileLocation = testProperties.getProperty("hostCertFileLocation");
        String hostKeyFileLocation = testProperties.getProperty("hostKeyFileLocation");
        String myProxyFileLocation = testProperties.getProperty("myProxyFileLocation");

        // try locating the host proxy file
        if (hostProxyFileLocation != null && hostProxyFileLocation.length() > 0){
            System.out.println("Loading GSSCredential using: " + hostProxyFileLocation );
            gssCred = createCredential( hostProxyFileLocation, hostProxyFileLocation);
        }       
        // try locating the host cert/key files
        else if (hostCertFileLocation != null && hostCertFileLocation.length() > 0 &&
            hostKeyFileLocation != null && hostKeyFileLocation.length() > 0){
            System.out.println("Loading GSSCredential using: " + hostCertFileLocation + " and " + hostKeyFileLocation );
            gssCred = createCredential( hostCertFileLocation, hostKeyFileLocation);
        }
        // try locating the proxy file
        else if (myProxyFileLocation != null && myProxyFileLocation.length() > 0){
            System.out.println("Loading GSSCredential using: " + myProxyFileLocation );
            gssCred = createCredential( myProxyFileLocation );
        }
        
        if (gssCred == null){
            System.out.println("No cred location specified in test.properties!" );
        }
        
        return gssCred;
    }

    /**
     * Gets a GSSCredential using a proxy file.
     * @param proxyFile
     * @return
     */
    private static GSSCredential createCredential(String proxyFile)
            throws CredentialException {
         GSSCredential cred = null;
        try {
            File f = new File(proxyFile);
            byte[] data = new byte[(int) f.length()];
            FileInputStream in = new FileInputStream(f);
            // read in the credentialContext data
            in.read(data);
            in.close();

            ExtendedGSSManager manager = (ExtendedGSSManager) ExtendedGSSManager.getInstance();
                cred =
                    manager
                        .createCredential(
                            data,
                            ExtendedGSSCredential.IMPEXP_OPAQUE,
                            GSSCredential.DEFAULT_LIFETIME,
                            null,// use default mechanism - GSI
                            GSSCredential.INITIATE_AND_ACCEPT);
        }
        catch (IOException e) {
            log.error("Unable to access portal credential proxy file", e);
            throw new CredentialException(e.getMessage());
        }
        catch (GSSException e) {
            log.error("Unable to create portal credential", e);
            throw new CredentialException(e.getMessage());
        }
        return cred;
    }

    /**
     * Get a GSSCredential from a certificate and private key file. Note the private key must not
     * be encrypted.
     * @param certFile
     * @param keyFile
     * @return
     * @throws CredentialException
     */
    private static GSSCredential createCredential(String certFile, String keyFile)
            throws CredentialException {
        GSSCredential cred = null;
        try {
            GlobusCredential hostCred = new GlobusCredential(certFile, keyFile);
            cred = new GlobusGSSCredentialImpl(hostCred, GSSCredential.INITIATE_AND_ACCEPT);
        }
        catch (GSSException e) {
            log.error("Unable to create portal credential!", e);
            throw new CredentialException(e.getMessage());
        }
        catch (GlobusCredentialException e) {
            log.error("Unable to access file: " + certFile + " or " + keyFile, e);
            throw new CredentialException(e.getMessage());
        }
        return cred;
    }

}


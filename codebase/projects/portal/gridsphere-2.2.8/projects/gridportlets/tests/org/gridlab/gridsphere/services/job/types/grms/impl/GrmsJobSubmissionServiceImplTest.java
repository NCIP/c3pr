/*
 * Created on Oct 27, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.gridlab.gridsphere.services.job.types.grms.impl;

import java.net.MalformedURLException;

import org.gridlab.gridsphere.GridPortletsTest;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.core.user.SetupRootUserTest;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowserService;
import org.gridlab.gridsphere.services.job.JobException;
import org.gridlab.gridsphere.services.job.JobSpec;
import org.gridlab.gridsphere.services.resources.grms.GrmsJobSubmissionService;
import org.gridlab.gridsphere.services.resources.grms.GrmsJobType;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.gridlab.gridsphere.services.security.gss.CredentialException;
import org.gridlab.gridsphere.services.security.gss.CredentialManagerService;
import org.ietf.jgss.GSSCredential;

/**
 * @author Coteje
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GrmsJobSubmissionServiceImplTest extends SetupRootUserTest {

    private CredentialManagerService credentialManager = null;

    /**
     * Constructor for GrmsJobSubmissionServiceImplTest.
     * @param arg0
     */
    public GrmsJobSubmissionServiceImplTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(GrmsJobSubmissionServiceImplTest.class);
    }

    /*
     * @see SetupRootUserTest#setUp()
     */
    protected void setUp() {
        super.setUp();
        try {
            super.testInitGridSphere();
            super.testLoginRootUser();
            credentialManager =
                    (CredentialManagerService)
                        factory.createPortletService(CredentialManagerService.class, null, true);
        }
        catch (PortletServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Unable to initialize test case");
        }
        
        try {
            GSSCredential gssCred = GridPortletsTest.createCredential();
            CredentialContext credentialContext
                    = credentialManager.createCredentialContext(rootUser, gssCred);
            credentialContext.setLabel("Root Credential");
            credentialManager.saveCredentialContext(credentialContext);
        } catch (CredentialException e) {
            log.error(e.getMessage());
            fail("Credential Exception " + e.getMessage());
        }

    }

    /*
     * @see SetupRootUserTest#tearDown()
     */
    protected void tearDown() {
        super.tearDown();
    }

    public void testBinDateJob() {
        GrmsJobSubmissionService jobSub = null;
        try {
            jobSub = (GrmsJobSubmissionService) factory.createPortletService(GrmsJobSubmissionService.class, null, true);
        }
        catch (PortletServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Unable to initialize GramJobSubmissionService");
        }
        
        JobSpec spec = null;
        try {
            spec = jobSub.createJobSpec(GrmsJobType.INSTANCE);
        }
        catch (JobException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        spec.setUser(rootUser);

        FileBrowserService fileManager = new BaseFileBrowserService();
        FileLocation dirLoc = null;
        FileLocation execLoc = null;
        
        try {
            dirLoc = fileManager.createFileLocation("file:////tmp");
            execLoc = fileManager.createFileLocation("file:////bin/date");
        }
        catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        spec.setDirectory(dirLoc);
        spec.setExecutable(execLoc);

        try {
            jobSub.submitJob(spec);
        }
        catch (JobException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            fail(e2.getMessage());
        }
        
    }
}

/*
 * Created on Oct 22, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.gridlab.gridsphere.services.job.types.gram.impl;

import java.net.MalformedURLException;

import org.gridlab.gridsphere.GridPortletsTest;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.core.user.SetupRootUserTest;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowserService;
import org.gridlab.gridsphere.services.job.ArgumentSpec;
import org.gridlab.gridsphere.services.job.ExecutionMethod;
import org.gridlab.gridsphere.services.job.FileStageParameter;
import org.gridlab.gridsphere.services.job.FileStageSpec;
import org.gridlab.gridsphere.services.job.JobException;
import org.gridlab.gridsphere.services.job.JobSpec;
import org.gridlab.gridsphere.services.job.arguments.StringArgument;
import org.gridlab.gridsphere.services.job.impl.BaseJobSpec;
import org.gridlab.gridsphere.services.resources.gram.GramJobSubmissionService;
import org.gridlab.gridsphere.services.resources.gram.GramJobType;
import org.gridlab.gridsphere.services.security.gss.CredentialManagerService;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.ietf.jgss.GSSCredential;

/**
 * @author Coteje
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GramJobSpecImplTest extends SetupRootUserTest {

    private CredentialManagerService credentialManager = null;
    /**
     * Constructor for GramJobSpecImplTest1.
     * @param arg0
     */
    public GramJobSpecImplTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(GramJobSpecImplTest.class);
    }

    protected void setUp() {
        
        try {
            super.testInitGridSphere();
            super.testLoginRootUser();
            credentialManager =
                    (CredentialManagerService)
                        factory.createPortletService(CredentialManagerService.class, context, true);
        }
        catch (PortletServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Unable to initialize test case");
        }
    }

    /*
     * Test for String toString()
     */
    final public void testRSLGeneration() {

        CredentialContext credentialContext = null;

        try {
            GSSCredential gssCred = GridPortletsTest.createCredential();
            credentialContext
                    = credentialManager.createCredentialContext(rootUser, gssCred);
            credentialContext.setLabel("Root Credential");
            credentialManager.saveCredentialContext(credentialContext);
        } catch (org.gridlab.gridsphere.services.security.gss.CredentialException e) {
            log.error(e.getMessage());
            fail("Credential Exception " + e.getMessage());
        }

        GramJobSubmissionService jobSub = null;
        JobSpec spec = null;
        FileBrowserService fileManager = null;
        FileLocation dirLoc = null;
        FileLocation execLoc = null;
        FileLocation inFileLoc = null;
        FileLocation outFileLoc = null;
        FileLocation errFileLoc = null;
        
        try {
            try {
                jobSub = (GramJobSubmissionService) factory.createPortletService(GramJobSubmissionService.class, context, true);
            }
            catch (PortletServiceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                fail("Unable to initialize GramJobSubmissionService");
            }
            spec = jobSub.createJobSpec(GramJobType.INSTANCE);
            spec.setUser(rootUser);
            fileManager = new BaseFileBrowserService();

            dirLoc = fileManager.createFileLocation("file:///mydir");
            execLoc = fileManager.createFileLocation("file:///bin/date");
            inFileLoc = fileManager.createFileLocation("file:///mydir/localFileNameA.txt?name=remoteFileNameA.txt&type=in");
            outFileLoc = fileManager.createFileLocation("file:///mydir/remoteFileNameB.txt?name=localFileNameB.txt&type=out");
            errFileLoc = fileManager.createFileLocation("file:///mydir/remoteFileNameC.txt?name=localFileNameC.txt");
            
        }
        catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            fail( "MalformedURLException: " + e1.getMessage() );
        }
        catch (JobException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail( "JobException: " + e.getMessage() );
        }

/*
        try {
            fileManager.createFileLocation("/mydir/localFileName4.txt");
            fail("CreateLocation should not accept url with no PROTOCOLS.");
        }
        catch (MalformedURLException e1) {
            fail( "MalformedURLException: " + e1.getMessage() );
        }*/

        spec.setDirectory(dirLoc);
        spec.setExecutable(execLoc);
        spec.setExecutionMethod(ExecutionMethod.SINGLE);
        spec.setStdout(outFileLoc);
        spec.setStderr(errFileLoc);

        ArgumentSpec argumentSpec = spec.getArgumentSpec();

        StringArgument outArg = argumentSpec.createStringArgument();
        outArg.setValue("outFileNameStringArgument.txt");
        argumentSpec.addArgument(outArg);

        //executable -option1=value1 -flag1 argument1 argument2
        StringArgument a = argumentSpec.createStringArgument();
        StringArgument b = argumentSpec.createStringArgument();
        StringArgument c = argumentSpec.createStringArgument();
        StringArgument d = argumentSpec.createStringArgument();
        a.setValue("-option1=value1");
        b.setValue("-flag");
        c.setValue("argument1");
        d.setValue("argument2");
        argumentSpec.addArgument(a);
        argumentSpec.addArgument(b);
        argumentSpec.addArgument(c);
        argumentSpec.addArgument(d);

        FileStageSpec fileStageSpec = spec.getFileStageSpec();

        try {
            FileStageParameter fileOutArg = fileStageSpec.createFileStageParameter();
            fileOutArg.setFileStageUrl("file:///mydir/remoteFileNameB.txt?name=localFileNameB.txt&type=out");
            fileStageSpec.addFileStageParameter(fileOutArg);
    
            FileStageParameter fileInArg = fileStageSpec.createFileStageParameter();
            fileInArg.setFileStageUrl("file:///mydir/localFileNameA.txt?name=remoteFileNameA.txt&type=in");
            fileStageSpec.addFileStageParameter(fileInArg);
    
            FileStageParameter fileArg1 = fileStageSpec.createFileStageParameter();
            fileArg1.setFileStageUrl("file:///mydir/localFileName1.txt");
            fileStageSpec.addFileStageParameter(fileArg1);
    
            FileStageParameter fileArg2 = fileStageSpec.createFileStageParameter();
            fileArg2.setFileStageUrl("file:///mydir/localFileName2.txt?name=remoteFileName2.txt");
            fileStageSpec.addFileStageParameter(fileArg2);
    
            FileStageParameter fileArg3 = fileStageSpec.createFileStageParameter();
            fileArg3.setFileStageUrl("file:///mydir/localFileName3.txt?type=in&name=remoteFileName3.txt");
            fileStageSpec.addFileStageParameter(fileArg3);
    
            FileStageParameter fileArg4 = fileStageSpec.createFileStageParameter();
            fileArg4.setFileStageUrl("http:///mydir/localFileName4.txt");
            fileStageSpec.addFileStageParameter(fileArg4);
    
            FileStageParameter gassFileInArg = fileStageSpec.createFileStageParameter();
            gassFileInArg.setFileStageUrl("file://127.0.0.1//mydir/gassFileNameIn.txt?type=in");
            fileStageSpec.addFileStageParameter(gassFileInArg);
    
            FileStageParameter gassFileOutArg = fileStageSpec.createFileStageParameter();
            gassFileOutArg.setFileStageUrl("file://127.0.0.1//mydir/gassFileNameOut.txt?type=out");
            fileStageSpec.addFileStageParameter(gassFileOutArg);
        
        }
        catch (MalformedURLException e1) {
            fail("MalformedURLException: " + e1.getMessage());
        }


        BaseJobSpec baseSpec = (BaseJobSpec)spec;
        try {
            baseSpec.preProcess(credentialContext);
        }
        catch (JobException e4) {
            e4.printStackTrace();
            fail("JobException");
        }
        String result = spec.marshalToString();
        System.out.println("         results: " + result);
        
        // Replace gass server addresses with a generic one 127.0.0.1:9999
        result = result.replaceAll("https://\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{1,6}", "https://127.0.0.1:9999");
        System.out.println("generic  results: " + result);
        
        String exptectedResult = 

       "&( arguments = \"outFileNameStringArgument.txt\" \"-o"+
       "ption1=value1\" \"-flag\" \"argument1\" \"argument2\" )( directory = \"/mydir\" )( execut"+
       "able = \"/bin/date\" )( file_stage_in = ( \"/mydir/localFileNameA.txt\" \"remoteFileN"+
       "ameA.txt\" ) ( \"/mydir/localFileName1.txt\" \"localFileName1.txt\" ) ( \"/mydir/local"+
       "FileName2.txt\" \"remoteFileName2.txt\" ) ( \"/mydir/localFileName3.txt\" \"remoteFile"+
       "Name3.txt\" ) ( \"http:///mydir/localFileName4.txt\" \"localFileName4.txt\" ) ( \"http"+
       "s://127.0.0.1:9999//mydir/gassFileNameIn.txt\" \"gassFileNameIn.txt\" ) )( file_sta"+
       "ge_out = ( \"localFileNameB.txt\" \"/mydir/remoteFileNameB.txt\" ) ( \"gassFileNameOu"+
       "t.txt\" \"https://127.0.0.1:9999//mydir/gassFileNameOut.txt\" ) )( stderr = \"/mydir"+
       "/remoteFileNameC.txt\" )( stdout = \"/mydir/remoteFileNameB.txt\" )( jobtype = \"sin"+
       "gle\" )";


        System.out.println("Expected results: " + exptectedResult);
        
        if (result.compareTo(exptectedResult) != 0 ){
            fail("RSL string does not match expected results.");
        }
        
        
        JobSpec spec2 = null;
        try {
            spec2 = jobSub.createJobSpec(spec);
        }
        catch (JobException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
            fail( "JobException: " + e3.getMessage() );
        }
        
        if (spec.marshalToString().compareTo(spec2.marshalToString()) != 0) {
            fail("Copy constructor failure");
        }
        
    }


}

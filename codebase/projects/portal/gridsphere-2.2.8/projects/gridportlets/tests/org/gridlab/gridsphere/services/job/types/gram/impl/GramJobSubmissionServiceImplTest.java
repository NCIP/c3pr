/*
 * Created on Oct 27, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.gridlab.gridsphere.services.job.types.gram.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import org.gridlab.gridsphere.GridPortletsTest;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.core.user.SetupRootUserTest;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowserService;
import org.gridlab.gridsphere.services.file.impl.BaseFileLocation;
import org.gridlab.gridsphere.services.job.ArgumentSpec;
import org.gridlab.gridsphere.services.job.ExecutionMethod;
import org.gridlab.gridsphere.services.job.FileStageParameter;
import org.gridlab.gridsphere.services.job.FileStageSpec;
import org.gridlab.gridsphere.services.job.Job;
import org.gridlab.gridsphere.services.job.JobSchedulerType;
import org.gridlab.gridsphere.services.job.JobSpec;
import org.gridlab.gridsphere.services.job.JobStatus;
import org.gridlab.gridsphere.services.job.arguments.StringArgument;
import org.gridlab.gridsphere.services.resources.gram.GramJobSubmissionService;
import org.gridlab.gridsphere.services.resources.gram.GramJobType;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.gridlab.gridsphere.services.security.gss.CredentialManagerService;
import org.gridlab.gridsphere.services.security.gss.CredentialException;
import org.gridlab.gridsphere.services.task.TaskStatusListener;
import org.ietf.jgss.GSSCredential;

/**
 * @author Coteje
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GramJobSubmissionServiceImplTest extends SetupRootUserTest {

    private CredentialManagerService credentialManager = null;
    protected static String hostName = "";
    protected static ExecutionMethod execMethod = ExecutionMethod.SINGLE;
    protected static JobSchedulerType jobSchedulerType = JobSchedulerType.FORK;

    /**
     * Constructor for GramJobSubmissionServiceImplTest.
     * @param arg0
     */
    public GramJobSubmissionServiceImplTest(String arg0){
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(GramJobSubmissionServiceImplTest.class);
    }

    /*
     * @see SetupRootUserTest#setUp()
     */
    protected void setUp() {
        super.setUp();
        try {
            super.testInitGridSphere();
            super.testLoginRootUser();
            credentialManager
                    = (CredentialManagerService)
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

        System.out.println("Sending GRAM job to: " + hostName );
    }


    private void setupFork(){
        Properties testProperties = GridPortletsTest.getTestProperties();
        hostName = testProperties.getProperty("forkHostName");
        execMethod = ExecutionMethod.SINGLE;
        jobSchedulerType = JobSchedulerType.FORK;
    }
    public void testForkSimpleEchoJob() {
        setupFork();
        testSimpleEchoJob();
    }
    public void testForkBlocking() {
        setupFork();
        testBlocking();
    }
    public void testForkStdOut() {
        setupFork();
        testStdOut();
    }
    public void testForkStageIn() {
        setupFork();
        testStageIn();
    }
    public void testForkStageOut() {
        setupFork();
        testStageOut();
    }
    public void testForkStageLargeTx() {
        setupFork();
        testStageLargeTx();
    }

    private void setupCondor(){
        Properties testProperties = GridPortletsTest.getTestProperties();
        hostName = testProperties.getProperty("condorHostName");
        execMethod = ExecutionMethod.SINGLE;
        jobSchedulerType = JobSchedulerType.CONDOR;
    }
    public void testCondorSimpleEchoJob() {
        setupCondor();
        testSimpleEchoJob();
    }
    public void testCondorBlocking() {
        setupCondor();
        testBlocking();
    }
    public void testCondorStdOut() {
        setupCondor();
        testStdOut();
    }
    public void testCondorStageIn() {
        setupCondor();
        testStageIn();
    }
    public void testCondorStageOut() {
        setupCondor();
        testStageOut();
    }
    public void testCondorStageLargeTx() {
        setupCondor();
        testStageLargeTx();
    }

    private void setupPbs(){
        Properties testProperties = GridPortletsTest.getTestProperties();
        hostName = testProperties.getProperty("pbsHostName");
        execMethod = ExecutionMethod.SINGLE;
        jobSchedulerType = JobSchedulerType.PBS;
    }
    public void testPbsSimpleEchoJob() {
        setupPbs();
        testSimpleEchoJob();
    }
    public void testPbsBlocking() {
        setupPbs();
        testBlocking();
    }
    public void testPbsStdOut() {
        setupPbs();
        testStdOut();
    }
    public void testPbsStageIn() {
        setupPbs();
        testStageIn();
    }
    public void testPbsStageOut() {
        setupPbs();
        testStageOut();
    }
    public void testPbsStageLargeTx() {
        setupPbs();
        testStageLargeTx();
    }





    public void testSimpleEchoJob() {
        try {
            GramJobSubmissionService jobSub = (GramJobSubmissionService) factory.createPortletService(GramJobSubmissionService.class, null, true);
            JobSpec spec = jobSub.createJobSpec(GramJobType.INSTANCE);
            spec.getResourceSpec().setJobHostName(hostName);
            spec.setUser(rootUser);
    
            FileBrowserService fileManager = new BaseFileBrowserService();
            FileLocation dirLoc = fileManager.createFileLocation("file:///tmp");
            FileLocation execLoc = fileManager.createFileLocation("file:///bin/echo");
    
            spec.setDirectory(dirLoc);
            spec.setExecutable(execLoc);
            spec.setExecutionMethod(execMethod);
            
            jobSub.submitJob(spec);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        
    }

    public void testBlocking() {
        try {
            GramJobSubmissionService jobSub = (GramJobSubmissionService) factory.createPortletService(GramJobSubmissionService.class, null, true);
            JobSpec spec = jobSub.createJobSpec(GramJobType.INSTANCE);
            spec.getResourceSpec().setJobHostName(hostName);
            spec.setUser(rootUser);
            log.debug("Adding taskStatus listener to job spec.");
            RequestStatusListenerImpl statusListener = new RequestStatusListenerImpl();
            spec.addTaskStatusListener(statusListener);
    
            FileBrowserService fileManager = new BaseFileBrowserService();
            FileLocation dirLoc = fileManager.createFileLocation("file:///tmp");
            FileLocation execLoc = fileManager.createFileLocation("file:///bin/sleep");

            spec.setDirectory(dirLoc);
            spec.setExecutable(execLoc);
            spec.setExecutionMethod(execMethod);

            ArgumentSpec argumentSpec = spec.getArgumentSpec();
            StringArgument a = argumentSpec.createStringArgument();
            a.setValue("2");
            argumentSpec.addArgument(a);

            log.debug("Sumitting new job spec.");
            Job job = jobSub.submitJob(spec);

            // wait for end
            synchronized(statusListener) {
                statusListener.wait();
                if (job.getJobStatus().equals(JobStatus.JOB_COMPLETED)) {
                    if (statusListener.getOutputData().length() > 0){
                        fail("Should not return any data.");
                    }
                }
                else {
                    fail("Job submission failed: " + job.getJobStatus().getName() );
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public void testStdOut() {
        try {
            GramJobSubmissionService jobSub = (GramJobSubmissionService) factory.createPortletService(GramJobSubmissionService.class, null, true);
            JobSpec spec = jobSub.createJobSpec(GramJobType.INSTANCE);
            spec.getResourceSpec().setJobHostName(hostName);
            spec.setUser(rootUser);
            log.debug("Adding taskStatus listener to job spec.");
            RequestStatusListenerImpl statusListener = new RequestStatusListenerImpl();
            spec.addTaskStatusListener(statusListener);
    
            FileBrowserService fileManager = new BaseFileBrowserService();
            FileLocation dirLoc = fileManager.createFileLocation("file:///tmp");
            FileLocation execLoc = fileManager.createFileLocation("file:///bin/date");
    
            spec.setDirectory(dirLoc);
            spec.setExecutable(execLoc);
            spec.setExecutionMethod(execMethod);
    
            log.debug("Sumitting new job spec.");
            Job job = jobSub.submitJob(spec);
            
            // wait for end
            synchronized(statusListener) {
                statusListener.wait();
                if (job.getJobStatus().equals(JobStatus.JOB_COMPLETED)) {
                    if (statusListener.getOutputData().length() <= 0){
                        fail("Should return the date.");
                    }
                }
                else {
                    fail("Job submission failed: " + job.getJobStatus().getName() );
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }


    public void testStageIn() {
        try {
            GramJobSubmissionService jobSub = (GramJobSubmissionService) factory.createPortletService(GramJobSubmissionService.class, null, true);
            JobSpec spec = jobSub.createJobSpec(GramJobType.INSTANCE);
            spec.getResourceSpec().setJobHostName(hostName);
            spec.setUser(rootUser);
            
            log.debug("Adding taskStatus listener to job spec.");
            RequestStatusListenerImpl statusListener = new RequestStatusListenerImpl();
            spec.addTaskStatusListener(statusListener);
    
            FileBrowserService fileManager = new BaseFileBrowserService();
            FileLocation dirLoc = fileManager.createFileLocation("file:///tmp");
            FileLocation execLoc = fileManager.createFileLocation("file:///bin/cat");
            spec.setDirectory(dirLoc);
            spec.setExecutable(execLoc);
            spec.setExecutionMethod(execMethod);
    
            String serverInFileName = new Date().getTime() + "server_in.txt";
            ArgumentSpec argumentSpec = spec.getArgumentSpec();
            StringArgument b = argumentSpec.createStringArgument();
            b.setValue(serverInFileName);
            argumentSpec.addArgument(b);
    
            String data = "Data from the client";
            File f = new File("_data_.txt");
            f.delete();
            if (f.createNewFile()){
                PrintWriter out = new PrintWriter(new FileOutputStream(f));
                out.write(data);
                out.close();
            }
            else {
                fail("Failed to create _data_.txt file.");
            }
            
            FileStageSpec fileStageSpec = spec.getFileStageSpec();
            FileStageParameter fileInArg = fileStageSpec.createFileStageParameter();
            fileInArg.setFileStageUrl("file://127.0.0.1/" + f.getPath() + "?name=" + serverInFileName + "&type=in");
            fileStageSpec.addFileStageParameter(fileInArg);
    
            // submit job
            Job job = jobSub.submitJob(spec);
            
            // wait for end
            synchronized(statusListener) {
                statusListener.wait();
                if (job.getJobStatus().equals(JobStatus.JOB_COMPLETED)) {
                    if(!statusListener.getOutputData().equals(data)){
                        fail("Returned data is not what was expected: expected: " + data + " returned data was: " + statusListener.getOutputData());
                    }
                }
                else {
                    fail("Job submission failed: " + job.getJobStatus().getName() );
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        finally {
            File f1 = new File("_data_.txt");
            f1.delete();
        }
    }
    public void testStageOut() {
        try {
            GramJobSubmissionService jobSub = (GramJobSubmissionService) factory.createPortletService(GramJobSubmissionService.class, null, true);
            JobSpec spec = jobSub.createJobSpec(GramJobType.INSTANCE);
            spec.getResourceSpec().setJobHostName(hostName);
            spec.setUser(rootUser);
            
            log.debug("Adding taskStatus listener to job spec.");
            RequestStatusListenerImpl statusListener = new RequestStatusListenerImpl();
            spec.addTaskStatusListener(statusListener);
    
            FileBrowserService fileManager = new BaseFileBrowserService();
            FileLocation dirLoc = fileManager.createFileLocation("file:///tmp");
            FileLocation execLoc = fileManager.createFileLocation("file:///bin/cp");
            spec.setDirectory(dirLoc);
            spec.setExecutable(execLoc);
            spec.setExecutionMethod(execMethod);
    
            String serverInFileName = new Date().getTime() + "server_in.txt";
            String serverCopiedFileName = new Date().getTime() + "server_copied.txt";
            ArgumentSpec argumentSpec = spec.getArgumentSpec();
            StringArgument b = argumentSpec.createStringArgument();
            b.setValue(serverInFileName);
            argumentSpec.addArgument(b);
            StringArgument c = argumentSpec.createStringArgument();
            c.setValue(serverCopiedFileName);
            argumentSpec.addArgument(c);
    
            String data = "Data from the client";
            File f = new File("_data_.txt");
            f.delete();
            if (f.createNewFile()){
                PrintWriter out = new PrintWriter(new FileOutputStream(f));
                out.write(data);
                out.close();
            }
            else {
                fail("Failed to create _data_.txt file.");
            }

            FileStageSpec fileStageSpec = spec.getFileStageSpec();
            FileStageParameter fileInArg = fileStageSpec.createFileStageParameter();
            fileInArg.setFileStageUrl("file://127.0.0.1/" + f.getPath() + "?name=" + serverInFileName + "&type=in");
            fileStageSpec.addFileStageParameter(fileInArg);
            FileStageParameter fileOutArg = fileStageSpec.createFileStageParameter();
            fileOutArg.setFileStageUrl("file://127.0.0.1/copied_file_on_client.txt?name=" + serverCopiedFileName + "&type=out");
            fileStageSpec.addFileStageParameter(fileOutArg);
    
            // submit job
            Job job = jobSub.submitJob(spec);
            
            // wait for end
            synchronized(statusListener) {
                statusListener.wait();
                if (job.getJobStatus().equals(JobStatus.JOB_COMPLETED)) {
                    String dataRet = readData("copied_file_on_client.txt");
                    if(!dataRet.equals(data)){
                        fail("Returned data is not what was expected: expected: " + data + " returned data was: " + dataRet);
                    }
                }
                else {
                    fail("Job submission failed: " + job.getJobStatus().getName() );
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        finally{
            File f1 = new File("_data_.txt");
            f1.delete();
            File f2 = new File("copied_file_on_client.txt");
            f2.delete();
        }
    }
    public void testStageLargeTx() {
        try {
            GramJobSubmissionService jobSub = (GramJobSubmissionService) factory.createPortletService(GramJobSubmissionService.class, null, true);
            JobSpec spec = jobSub.createJobSpec(GramJobType.INSTANCE);
            spec.getResourceSpec().setJobHostName(hostName);
            spec.setUser(rootUser);
            
            log.debug("Adding taskStatus listener to job spec.");
            RequestStatusListenerImpl statusListener = new RequestStatusListenerImpl();
            spec.addTaskStatusListener(statusListener);
    
            FileBrowserService fileManager = new BaseFileBrowserService();
            FileLocation dirLoc = fileManager.createFileLocation("file:///tmp");
            FileLocation execLoc = fileManager.createFileLocation("file:///bin/mv");
            spec.setDirectory(dirLoc);
            spec.setExecutable(execLoc);
            spec.setExecutionMethod(execMethod);
    
            String serverInFileName = new Date().getTime() + "server_in.txt";
            String serverCopiedFileName = new Date().getTime() + "server_copied.txt";
            ArgumentSpec argumentSpec = spec.getArgumentSpec();
            StringArgument b = argumentSpec.createStringArgument();
            b.setValue(serverInFileName);
            argumentSpec.addArgument(b);
            StringArgument c = argumentSpec.createStringArgument();
            c.setValue(serverCopiedFileName);
            argumentSpec.addArgument(c);
    
            String data = "----------------------------------------Data from the client----------------------------------------";
            long totalLines = 100000;
            
            File fData = new File("_data_.txt");
            fData.delete();
            if (fData.createNewFile()){
                PrintWriter out = new PrintWriter(new FileOutputStream(fData));
                for (long i = 0; i < totalLines; i++) {
                    out.println(data);
                }
                out.close();
            }
            else {
                fail("Failed to create _data_.txt file.");
            }

            FileStageSpec fileStageSpec = spec.getFileStageSpec();
            FileStageParameter fileInArg = fileStageSpec.createFileStageParameter();
            fileInArg.setFileStageUrl("file://127.0.0.1/" + fData.getPath() + "?name=" + serverInFileName + "&type=in");
            fileStageSpec.addFileStageParameter(fileInArg);
            FileStageParameter fileOutArg = fileStageSpec.createFileStageParameter();
            fileOutArg.setFileStageUrl("file://127.0.0.1/copied_file_on_client.txt?name=" + serverCopiedFileName + "&type=out");
            fileStageSpec.addFileStageParameter(fileOutArg);
    
            // submit job
            Job job = jobSub.submitJob(spec);
            
            // wait for end
            synchronized(statusListener) {
                statusListener.wait();
                if (job.getJobStatus().equals(JobStatus.JOB_COMPLETED)) {
                    File fRet = new File("copied_file_on_client.txt");
                    long retFileSize = fRet.length();
                    long originalFileSize = fData.length();
                    if(originalFileSize != retFileSize){
                        fail("Expected file size: " + originalFileSize + " returned file size: " + retFileSize);
                    }
                }
                else {
                    fail("Job submission failed: " + job.getJobStatus().getName() );
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        finally{
            File f1 = new File("_data_.txt");
            f1.delete();
            File f2 = new File("copied_file_on_client.txt");
            f2.delete();
        }
    }

    
    public static String readData(String loc) {
        String data = "";    
        try {
            InputStream in = new FileInputStream(loc);
            BufferedReader buf = new BufferedReader( new InputStreamReader(in) );
            String line = buf.readLine();
            while( line != null ){
                data += line;
                line = buf.readLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


}

class RequestStatusListenerImpl implements TaskStatusListener {
    private static PortletLog log = SportletLog.getInstance(RequestStatusListenerImpl.class);
    private String data = "";
    
    public RequestStatusListenerImpl(){
    }
    
    public String getOutputData() {
        return data;
    }
    
    public synchronized void statusChanged(org.gridlab.gridsphere.services.task.Task request){
		org.gridlab.gridsphere.services.task.TaskStatus status = request.getTaskStatus();
        log.debug("StatusChanged: " + status.toString());

        if (status.equals(org.gridlab.gridsphere.services.task.TaskStatus.COMPLETED)){
            Job job = (Job)request;
            log.debug("Getting stdout of job: " + job.getJobId());
            FileLocation fileLoc = job.getJobSpec().getStdout();
            if (fileLoc instanceof BaseFileLocation){
                BaseFileLocation baseLoc = (BaseFileLocation) fileLoc;
                String loc = baseLoc.getFilePath();
                log.debug("Getting stream to stdout of job: " + job.getJobId());
                data = GramJobSubmissionServiceImplTest.readData(loc);
                String message = "Content of stdout: ";
                log.debug(message + data);
            }
            else {
                GramJobSubmissionServiceImplTest.fail("Stdout not a BaseFileLocation!!!");
            }
        }
        
        if (status.equals(org.gridlab.gridsphere.services.task.TaskStatus.COMPLETED) ||
            status.equals(org.gridlab.gridsphere.services.task.TaskStatus.CANCELED) ||
            status.equals(org.gridlab.gridsphere.services.task.TaskStatus.FAILED)){
            synchronized(this){
                notifyAll();
            }                        
        }
    }
    
}

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobExamples.java,v 1.1.1.1 2007-02-01 20:39:32 kherm Exp $
 */
package org.gridlab.gridsphere.examples.services.job;

import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileHandle;
import org.gridlab.gridsphere.services.task.TaskStatus;

import java.net.MalformedURLException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Provides a number of useful examples illustrating how to use Grid Portlets
 * to get job history for given user, submit jobs, and get job output.
 */
public class JobExamples {

    private PortletLog log = SportletLog.getInstance(JobExamples.class);
    private JobSubmissionService jobSubmissionService  = null;

    /**
     * Constructs an instance of JobExamples.
     * @throws PortletServiceUnavailableException If unable to get required portlet services.
     */
    public JobExamples() throws PortletServiceUnavailableException {
        log.info("Creating JobExamples");
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        try {
            jobSubmissionService = (JobSubmissionService)
                    factory.createPortletService(JobSubmissionService.class, null, true);
        } catch (PortletServiceNotFoundException e) {
            log.error("Unable to initialize required portlet services", e);
            throw new PortletServiceUnavailableException(e);
        }
    }

    /**
     * Returns all the jobs submitted by the given user.
     * @param user The user
     * @return A list of <code>Job</code> objects.
     */
    public List getJobList(User user) {
        // Note one could specify a particular job type if so desired
        // so that this method only returned jobs of the given type.
        return jobSubmissionService.getJobs(user, JobType.INSTANCE);
    }

    /**
     * Returns all the "active" jobs for the given user. Active jobs are jobs
     * with a task status of "ACTIVE".
     * @param user The user
     * @return A list of <code>Job</code> objects.
     */
    public List getActiveJobList(User user) {
        // Note one could specify a particular job type if so desired
        // so that this method only returned jobs of the given type.
        List jobList = jobSubmissionService.getJobs(user, JobType.INSTANCE);
        Iterator jobIterator = jobList.iterator();
        List activeJobList = new ArrayList(jobList.size());
        while (jobIterator.hasNext()) {
            Job job = (Job)jobIterator.next();
            if (job.getTaskStatus().equals(TaskStatus.ACTIVE)) {
                activeJobList.add(job);
            }
        }
        return activeJobList;
    }

    /**
     * Provides an example of submitting "/bin/date" for a given user to
     * a given host. Illustrates how to do a simple job submission and
     * get the job output as a string. Returns null if the job status
     * is failed when the job ends.
     * @param user The user
     * @param host The host on which to run /bin/date
     * @return Returns the value return by the job, null if the job failed.
     * @throws PortletException If a exception occurs during job submission or getting job output.
     */
    public String doBinDate(User user, String host) throws PortletException {
        String answer = null;
        try {
            // Create a job spec using the generic job type
            // Note one could specify a particular job type if so desired
            log.debug("Creating job spec for " + user.getUserName());
            JobSpec jobSpec = jobSubmissionService.createJobSpec(JobType.INSTANCE);
            jobSpec.setUser(user);
            FileLocation executable = new FileLocation("/bin/date");
            jobSpec.setExecutableLocation(executable);
            jobSpec.setHostName(host);
            log.debug("Submiting /bin/date to  " + host);
            Job job = jobSubmissionService.submitJob(jobSpec);
            job.waitFor();
            if (job.getTaskStatus().equals(TaskStatus.FAILED)) {
                log.error("Job failed with message " + job.getTaskStatusMessage());
                return null;
            }
            FileLocation stdout = job.getStdoutLocation();
            if (stdout != null) {
                log.debug("Reading stdout from " + stdout.getUrl());
                FileHandle fileHandle = new FileHandle(stdout);
                answer = fileHandle.readContents(user);
            }
        } catch (JobException e) {
            log.error("Unable to submit job", e);
            throw new PortletException(e.getMessage());
        } catch (MalformedURLException e) {
            log.error("Unable to create file location", e);
            throw new PortletException(e.getMessage());
        } catch (IOException e) {
            log.error("Unable to read stdout location", e);
            throw new PortletException(e.getMessage());
        }
        log.debug("answer = " + answer);
        return answer;
    }

    /**
     * Provides an example of submitting "/bin/ls" for a given user to
     * a given host with a given directory. Illustrates how to set the
     * directory in which to run a job and get the job output as a string.
     * Returns null if the job status is failed when the job ends.
     * @param user The user
     * @param host The host on which to run /bin/ls
     * @param directory The directory in which to run /bin/ls
     * @return Returns the value return by the job, null if the job failed.
     * @throws PortletException If a exception occurs during job submission or getting job output.
     */
    public String doBinLs(User user, String host, String directory) throws PortletException {
        PortletLog log = SportletLog.getInstance(getClass());
        String answer = null;
        try {
            // Create a job spec using the generic job type
            // Note one could specify a particular job type if so desired
            log.debug("Creating job spec for " + user.getUserName());
            JobSpec jobSpec = jobSubmissionService.createJobSpec(JobType.INSTANCE);
            jobSpec.setUser(user);
            FileLocation executable = new FileLocation("/bin/ls");
            jobSpec.setDirectory(directory);
            jobSpec.setExecutableLocation(executable);
            jobSpec.setHostName(host);
            log.debug("Submiting /bin/ls to  " + host + " in " + directory);
            Job job = jobSubmissionService.submitJob(jobSpec);
            job.waitFor();
            if (job.getTaskStatus().equals(TaskStatus.FAILED)) {
                log.error("Job failed with message " + job.getTaskStatusMessage());
                return null;
            }
            FileLocation stdout = job.getStdoutLocation();
            if (stdout != null) {
                log.debug("Reading stdout from " + stdout.getUrl());
                FileHandle fileHandle = new FileHandle(stdout);
                answer = fileHandle.readContents(user);
            }
        } catch (JobException e) {
            log.error("Unable to submit job", e);
            throw new PortletException(e.getMessage());
        } catch (MalformedURLException e) {
            log.error("Unable to create file location", e);
            throw new PortletException(e.getMessage());
        } catch (IOException e) {
            log.error("Unable to read stdout location", e);
            throw new PortletException(e.getMessage());
        }
        log.debug("answer = " + answer);
        return answer;
    }

    /**
     * Provides an example of submitting "/bin/echo" for a given user to
     * a given host with a given message. Illustrates how to pass an argument
     * to a job and get the job output as a string. Returns null if
     * the job status is failed when the job ends.
     * @param user The user
     * @param host The host on which to run /bin/ls
     * @param message The message to echo
     * @return Returns the value return by the job, null if the job failed.
     * @throws PortletException If a exception occurs during job submission or getting job output.
     */
    public String doBinEcho(User user, String host, String message) throws PortletException {
        PortletLog log = SportletLog.getInstance(getClass());
        String answer = null;
        try {
            // Create a job spec using the generic job type
            // Note one could specify a particular job type if so desired
            log.debug("Creating job spec for " + user.getUserName());
            JobSpec jobSpec = jobSubmissionService.createJobSpec(JobType.INSTANCE);
            jobSpec.setUser(user);
            FileLocation executable = new FileLocation("/bin/echo");
            jobSpec.setExecutableLocation(executable);
            jobSpec.addArgument(message);
            jobSpec.setHostName(host);
            log.debug("Submiting /bin/echo " + message + " to " + host);
            Job job = jobSubmissionService.submitJob(jobSpec);
            job.waitFor();
            if (job.getTaskStatus().equals(TaskStatus.FAILED)) {
                log.error("Task failed with message " + job.getTaskStatusMessage());
                return null;
            }
            FileLocation stdout = job.getStdoutLocation();
            if (stdout != null) {
                log.debug("Reading stdout from " + stdout.getUrl());
                FileHandle fileHandle = new FileHandle(stdout);
                answer = fileHandle.readContents(user);
            }
        } catch (JobException e) {
            log.error("Unable to submit job", e);
            throw new PortletException(e.getMessage());
        } catch (MalformedURLException e) {
            log.error("Unable to create file location", e);
            throw new PortletException(e.getMessage());
        } catch (IOException e) {
            log.error("Unable to read stdout location", e);
            throw new PortletException(e.getMessage());
        }
        log.debug("answer = " + answer);
        return answer;
    }

    /**
     * Provides an example of submitting an mpi job to run a given executable
     * and a given parameter file on a given number of cpus. In this case, we
     * don't want to get job output. Instead we simply want to get a handle
     * to the job that results.
     * @param user The user
     * @param host The host on which to run the executable
     * @param numCpus The number of cpus
     * @param executable The executable file location
     * @param executable The parameter file location
     * @return Returns the value return by the job, null if the job failed.
     * @throws PortletException If a exception occurs during job submission or getting job output.
     */
    public Job submitMpiJob(User user,
                            String host,
                            int numCpus,
                            FileLocation executable,
                            FileLocation parameterFile) throws PortletException {
        PortletLog log = SportletLog.getInstance(getClass());
        Job job = null;
        try {
            // Create a job spec using the generic job type
            // Note one could specify a particular job type if so desired
            log.debug("Creating job spec for " + user.getUserName());
            JobSpec jobSpec = jobSubmissionService.createJobSpec(JobType.INSTANCE);
            jobSpec.setUser(user);
            // Set the executable location
            // The executable could be on the local host
            // on the same host as job, or some other host
            jobSpec.setExecutableLocation(executable);
            // Add the parameter file name as an argument
            jobSpec.addArgument(parameterFile.getFileName());
            // Add the parameter file as a file stage parameter
            // The parameter file will be staged to same directory
            // in which job is run
            jobSpec.addFileStageParameter(parameterFile);
            // Set host name
            jobSpec.setHostName(host);
            // Set execution method to mpi
            jobSpec.setExecutionMethod(ExecutionMethod.MPI);
            // Set cpu count to given num of cpus
            jobSpec.setCpuCount(new Integer(numCpus));
            // Submit the job
            log.debug("Executing " + executable.getUrl() + " on " + host);
            job = jobSubmissionService.submitJob(jobSpec);
        } catch (JobException e) {
            log.error("Unable to submit job", e);
            throw new PortletException(e.getMessage());
        }
        return job;
    }
}

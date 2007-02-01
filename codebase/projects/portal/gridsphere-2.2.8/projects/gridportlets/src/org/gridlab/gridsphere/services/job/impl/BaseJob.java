package org.gridlab.gridsphere.services.job.impl;

import org.gridlab.gridsphere.services.job.Job;
import org.gridlab.gridsphere.services.job.JobSpec;
import org.gridlab.gridsphere.services.job.JobType;
import org.gridlab.gridsphere.services.job.JobStatus;
import org.gridlab.gridsphere.services.task.impl.BaseTask;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseJob.java,v 1.1.1.1 2007-02-01 20:40:45 kherm Exp $
 * <p>
 * Provides base implementation for jobs
 */

public class BaseJob extends BaseTask implements Job {

    protected static PortletLog log = SportletLog.getInstance(BaseJob.class);

    protected String jobId = null;
    protected String jobSubmissionString = null;
    protected JobStatus jobStatus = JobStatus.JOB_NEW;
    protected String jobStatusName = JobStatus.JOB_NEW.getName();
    protected String hostName = null;
    protected String schedulerName = null;
    protected String queueName = null;
    protected int numNodes = 0;
    protected String userId = null;
    protected FileLocation stdout = null;
    protected FileLocation stderr = null;

    private final Integer lock = new Integer(0);

    public BaseJob() {
        super();
        // Set job id to current time
        long now = (new Date()).getTime();
        setJobId(String.valueOf(now));
    }

    public BaseJob(BaseJobSpec spec) {
        super(spec);
        // Set job id to current time
        long now = (new Date()).getTime();
        setJobId(String.valueOf(now));
    }

    public BaseJob(String jobId, BaseJobSpec spec) {
        super(spec);
        // Use given job id
        setJobId(jobId);
    }

    public String getLabel() {
        return jobId;
    }

    public void setLabel(String label) {
        jobId = label;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public JobType getJobType() {
        return (JobType) getTaskType();
    }

    public TaskType getTaskType() {
        if (taskSpec == null) {
            return JobType.INSTANCE;
        }
        return taskSpec.getTaskType();
    }

    public JobSpec getJobSpec() {
        return (JobSpec) getTaskSpec();
    }

    public void setJobSpec(BaseJobSpec jobSpec) {
        setSpec(jobSpec);
    }

    public JobStatus getJobStatus() {
        synchronized (lock) {
            return jobStatus;
        }
    }

    public void setJobStatus(JobStatus newStatus) {
        setJobStatus(newStatus, null);
    }

    public void setJobStatus(JobStatus newStatus, String newMessage) {
        synchronized (lock) {
            log.debug("Setting job status " + newStatus.getName() + " for job " + jobId);
            jobStatus = newStatus;
            jobStatusName = newStatus.getName();
            setTaskStatus(newStatus.toTaskStatus(), newMessage);
        }
    }

    /**
     * For use by persistence manager only!
     */
    public String getJobStatusName() {
        return jobStatusName;
    }

    /**
     * For use by persistence manager only!
     */
    public void setJobStatusName(String statusName) {
        jobStatusName = statusName;
        jobStatus = JobStatus.toJobStatus(statusName);
    }

    public String getJobSubmissionString() {
        return jobSubmissionString;
    }

    public void setJobSubmissionString(String jobSubmissionString) {
        this.jobSubmissionString = jobSubmissionString;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public void setSchedulerName(String scheddulerName) {
        schedulerName = scheddulerName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FileLocation getStdoutLocation() {
        if (stdout == null && hostName != null && taskSpec != null) {
            stdout= ((JobSpec)taskSpec).getStdoutLocation();
            if (stdout != null && stdout.getHost().equals("")) {
                stdout.setHost(hostName);
            }
        }
        return stdout;
    }

    public void setStdoutLocation(FileLocation stdout) {
        this.stdout = stdout;
    }

    public FileLocation getStderrLocation() {
        if (stderr == null && hostName != null && taskSpec != null) {
            stderr= ((JobSpec)taskSpec).getStdoutLocation();
            if (stderr != null && stderr.getHost().equals("")) {
                stderr.setHost(hostName);
            }
        }
        return stderr;
    }

    public void setStderrLocation(FileLocation stderr) {
        this.stderr = stderr;
    }

    /**
     * For use by persistence manager only!
     */
    public FileLocation getStdout() {
        return stdout;
    }

    /**
     * For use by persistence manager only!
     */
    public void setStdout(FileLocation stdout) {
        this.stdout = stdout;
    }

    /**
     * For use by persistence manager only!
     */
    public FileLocation getStderr() {
        return stderr;
    }

    /**
     * For use by persistence manager only!
     */
    public void setStderr(FileLocation stderr) {
        this.stderr = stderr;
    }
}

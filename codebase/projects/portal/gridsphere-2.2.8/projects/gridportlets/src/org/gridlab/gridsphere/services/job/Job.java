package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.task.Task;
import org.gridlab.gridsphere.services.file.FileLocation;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Job.java,v 1.1.1.1 2007-02-01 20:40:40 kherm Exp $
 * <p>
 * Describes a job. Jobs are requests to execute applications
 * on remote resources and quite naturally inherit all the
 * semantics of Task.
 */

public interface Job extends Task {

    /**
     * Returns this job's type. This is a convenience
     * method as it should return the same value as
     * Task.getTaskType().
     * @return The job type
     */
    public JobType getJobType();

    /**
     * Returns this job's spec. This is a convenience
     * method as it should return the same value as
     * Task.getTaskSpec().
     * @return The job spec
     */
    public JobSpec getJobSpec();

    /**
     * Returns this job's status.
     * @return The job status
     */
    public JobStatus getJobStatus();

    /**
     * Returns the job submission string that was
     * used to submit the job to its job submission
     * service.
     * @return The job submission string
     */
    public String getJobSubmissionString();

    /**
     * Returns the identifier assigned by the job
     * submission service to this job. This is
     * not to be confused with the object identifier.
     * @return The job id
     */
    public String getJobId();

    /**
     * Sets the identifier assigned by the job
     * submission service to this job. This is
     * not to be confused with the object identifier.
     * @param jobId The job id
     */
    public void setJobId(String jobId);

    /**
     * Returns the host on which this job was submitted.
     * @return The host
     */
    public String getHostName();

    /**
     * Sets the host on which this job was submitted.
     * @param hostName The host
     */
    public void setHostName(String hostName);

    /**
     * Returns the name of the queue to which this job was submitted.
     * @return The queue name
     */
    public String getSchedulerName();

    /**
     * Sets the name of the queue to which this job was submitted.
     * @param queueName The queue name
     */
    public void setSchedulerName(String queueName);

    /**
     * Returns the name of the queue to which this job was submitted.
     * @return The queue name
     */
    public String getQueueName();

    /**
     * Sets the name of the queue to which this job was submitted.
     * @param queueName The queue name
     */
    public void setQueueName(String queueName);

    /**
     * Returns the local user id under which this job was submitted, if known.
     * @return The local user id
     */
    public String getUserId();

    /**
     * Sets the local id under which this job was submitted.
     * @param userId The local user id
     */
    public void setUserId(String userId);

    /**
     * Returns the number of nodes allocated to this job.
     * @return The number of nodes
     */
    public int getNumNodes();

    /**
     * Returns the stdout location of this job, if known.
     * @return The stdout location
     */
    public FileLocation getStdoutLocation();

    /**
     * Returns the stderr location of this job, if known.
     * @return The stderr location
     */
    public FileLocation getStderrLocation();
}

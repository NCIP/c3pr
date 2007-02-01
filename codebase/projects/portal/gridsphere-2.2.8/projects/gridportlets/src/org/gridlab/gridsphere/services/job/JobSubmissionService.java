package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.task.TaskSubmissionService;
import org.gridlab.gridsphere.portlet.User;

import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobSubmissionService.java,v 1.1.1.1 2007-02-01 20:40:43 kherm Exp $
 * <p>
 * Describes an interface for submitting jobs.
 */

public interface JobSubmissionService extends TaskSubmissionService {

    /**
     * Returns the job types handled by this service.
     * @return The list of supported job types
     */
    public List getJobTypes();

    /**
     * Returns the job resources associated with the given job type.
     * @param type The job type
     * @return The list of job resources
     */
    public List getJobResources(JobType type);

    /**
     * Returns the job resources associated with the given job type that are available to the given user.
     * @param type The job type
     * @return The list of job resources
     */
    public List getJobResources(JobType type, User user);

    /**
     * Creates a new job spec of the given job type.
     * @param type The type of job spec to create
     * @return The new job spec
     * @throws JobException
     */
    public JobSpec createJobSpec(JobType type) throws JobException;

    /**
     * Creates a new job spec initialized with the values in the given spec.
     * @param spec The job spec to copy
     * @return The new job spec
     * @throws JobException
     */
    public JobSpec createJobSpec(JobSpec spec) throws JobException;

    /**
     * Creates a new job spec of the given job type initialized.
     * @param type The type of job spec to create
     * @param spec The job spec to copy
     * @return The new job spec
     * @throws JobException
     */
    public JobSpec createJobSpec(JobType type, JobSpec spec) throws JobException;

    /**
     * Submits a job with the given job spec.
     * @param spec The job spec
     * @return The job
     * @throws JobException
     */
    public Job submitJob(JobSpec spec) throws JobException;

    /**
     * Cancels the given job.
     * @param job The job to cancel
     * @throws JobException
     */
    public void cancelJob(Job job) throws JobException;

    /**
     * Returns all the job records of the given type.
     * If type is null, returns all job records known
     * to the service.
     * @param type The job type
     * @return The list of jobs of the given type
     */
    public List getJobs(JobType type);

    /**
     * Returns all the job records of the given type
     * associated with the given user. If type is null,
     * returns all job records associated with the given
     * user.
     * @param user The user
     * @param type The job type
     * @return The list of jobs of the given type
     */
    public List getJobs(User user, JobType type);

    /**
     * Returns the job record with the given object identifier.
     * @param oid The job oid
     * @return The job
     */
    public Job getJob(String oid);

    /**
     * Returns the job record with the given  job id.
     * @param jobId The job id
     * @return The job
     */
    public Job getJobByJobId(String jobId);

    /**
     * Deletes the job record with the given record identifier.
     * @param job The job
     */
    public void deleteJob(Job job);
}

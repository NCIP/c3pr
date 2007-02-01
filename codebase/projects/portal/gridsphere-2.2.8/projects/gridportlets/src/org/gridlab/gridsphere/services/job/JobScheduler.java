package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.resource.Resource;

import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobScheduler.java,v 1.1.1.1 2007-02-01 20:40:41 kherm Exp $
 * <p>
 * Describes a job scheduler. Jobs can only be submitted to
 * hardware resources that contain one or more job schedulers.
 * Job schedulers contain one or more job queues.
 */

public interface JobScheduler extends Resource {

    /**
     * Returns the type of job scheduler this is.
     * @return The job scheduler type
     */
    public JobSchedulerType getJobSchedulerType();

    /**
     * Returns the job queues in this job scheduler.
     * @return The list of job queues
     * @see JobQueue
     */
    public List getJobQueues();

    /**
     * Returns the job queue with the given name. Returns
     * null if no queue found with that name.
     * @param name The name of the job queue
     * @return The job queue with the given name
     */
    public JobQueue getJobQueue(String name);

    /**
     * Adds a job queue with the given name.
     * @param name The name of the queue
     * @return The job queue that with that name
     */
    public JobQueue putJobQueue(String name);

    /**
     * Adds the given job queue to this scheduler
     * @param queue The job queue
     */
    public void putJobQueue(JobQueue queue);

    /**
     * Removes the job queue with the given name.
     * @param name The name of the queue
     * @return The job queue that was removed
     */
    public JobQueue removeJobQueue(String name);

    /**
     * Removes all the job queues on this scheduler.
     */
    public void clearsJobQueues();
}

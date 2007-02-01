package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.resource.Resource;

import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobQueue.java,v 1.1.1.1 2007-02-01 20:40:41 kherm Exp $
 * <p>
 * Describes a job queue. Every job scheduler has one or more
 * job queues. Job queues contain jobs.
 */

public interface JobQueue extends Resource {

    public static final String NAME_ATTRIBUTE = "Name";

    public static final String PRIORITY_ATTRIBUTE = "Priority";
    public static final String IS_ENABLED_ATTRIBUTE = "Enabled";
    public static final String STATUS_ATTRIBUTE = "Status";
    public static final String DISPATCH_TYPE_ATTRIBUTE = "DispatchType";

    public static final String MAX_NUM_NODES_ATTRIBUTE = "MaxNumNodes";
    public static final String MIN_NUM_NODES_ATTRIBUTE = "MinNumNodes";
    public static final String NUM_FREE_NODES_ATTRIBUTE = "NumFreeNodes";
    public static final String MAX_COUNT_ATTRIBUTE = "MaxCount";

    public static final String MEMORY_SIZE_MB_ATTRIBUTE = "MemorySizeMB";

    public static final String MAX_CPU_TIME_ATTRIBUTE = "MaxCpuTime";
    public static final String MIN_CPU_TIME_ATTRIBUTE = "MinCpuTime";
    public static final String DEFAULT_CPU_TIME_ATTRIBUTE = "DefaultCpuTime";

    public static final String MAX_WALL_TIME_ATTRIBUTE = "MaxWallTime";
    public static final String MIN_WALL_TIME_ATTRIBUTE = "MinWallTime";
    public static final String DEFAULT_WALL_TIME_ATTRIBUTE = "DefaultWallTime";

    public static final String MAX_TIME_ATTRIBUTE = "MaxTime";

    public static final String MAX_RUNNING_JOBS_ATTRIBUTE = "MaxRunningJobs";
    public static final String MAX_JOBS_ATTRIBUTE = "MaxJobs";

    public static final String JOBS_LIVE_ATTRIBUTE = "NumLiveJobs";
    public static final String JOBS_ACTIVE_ATTRIBUTE = "NumActiveJobs";
    public static final String JOBS_PENDING_ATTRIBUTE = "NumJobsPending";
    public static final String JOBS_SUSPENDED_ATTRIBUTE = "NumJobsSuspended";
    /**
     * Returns the job scheduler to which this queue belongs
     * @return The job scheduler that contains this queue
     */
    public JobScheduler getJobScheduler();

    /**
     * Returns the name of this queue.
     * @return The name of the job queue
     */
    public String getName();

    /**
     * Returns whether this queue is enabled or not.
     * @return True if enabled, false otherwise
     */
    public boolean getIsEnabled();

    /**
     * Sets whether this queue is enabled or not.
     * @param isEnabled True if enabled, false otherwise
     */
    public void setIsEnabled(boolean isEnabled);

    /**
     * Returns the status message for this queue.
     * @return The status message
     */
    public String getStatus();

    /**
     * Sets the status message for this queue.
     * @param status The status message
     */
    public void setStatus(String status);


    /**
     * Returns the dispatch type for this queue.
     * @return The dispatch type
     */
    public String getDispatchType();

    /**
     * Sets the dispatch type for this queue.
     * @param dispatchType The dispatch type
     */
    public void setDispatchType(String dispatchType);

    /**
     * Returns the maximum number of available nodes in this queue.
     * @return The number of available nodes
     */
    public int getMaxNumNodes();

    /**
     * Sets the maximum number of available nodes in this queue.
     * @param numNodes The number of available nodes
     */
    public void setMaxNumNodes(int numNodes);

    /**
     * Returns the minimum number of available nodes in this queue.
     * @return The number of available nodes
     */
    public int getMinNumNodes();

    /**
     * Sets the minimum number of available nodes in this queue.
     * @param numNodes The number of available nodes
     */
    public void setMinNumNodes(int numNodes);

    /**
     * Returns the number of free nodes in this queue.
     * @return The number of available nodes
     */
    public int getNumFreeNodes();

    /**
     * Sets the number of free nodes in this queue.
     * @param numNodes The number of available nodes
     */
    public void setNumFreeNodes(int numNodes);

    /**
     * Returns the maximum time for a job in this queue.
     * @return The number of available nodes
     */
    public String getMaxTime();

    /**
     * Sets the maximum time for a job in this queue.
     * @param time The number of available nodes
     */
    public void setMaxTime(String time);


    /**
     * Returns the maximum wall time for a job in this queue.
     * @return The number of available nodes
     */
    public String getMaxWallTime();

    /**
     * Sets the maximum wall time for a job in this queue.
     * @param time The number of available nodes
     */
    public void setMaxWallTime(String time);

    /**
     * Returns the minimum wall time for a job in this queue.
     * @return The number of available nodes
     */
    public String getMinWallTime();

    /**
     * Sets the minimum wall time for a job in this queue.
     * @param time The number of available nodes
     */
    public void setMinWallTime(String time);

    /**
     * Returns the detault wall time for a job in this queue.
     * @return The number of available nodes
     */
    public String getDefaultWallTime();

    /**
     * Sets the detault wall time for a job in this queue.
     * @param time The number of available nodes
     */
    public void setDefaultWallTime(String time);

    /**
     * Returns the maximum cpu time for a job in this queue.
     * @return The number of available nodes
     */
    public String getMaxCpuTime();

    /**
     * Sets the maximum cpu time for a job in this queue.
     * @param time The number of available nodes
     */
    public void setMaxCpuTime(String time);

    /**
     * Returns the minimum cpu time for a job in this queue.
     * @return The number of available nodes
     */
    public String getMinCpuTime();

    /**
     * Sets the minimum cpu time for a job in this queue.
     * @param time The number of available nodes
     */
    public void setMinCpuTime(String time);

    /**
     * Returns the default cpu time for a job in this queue.
     * @return The number of available nodes
     */
    public String getDefaultCpuTime();

    /**
     * Sets the default cpu time for a job in this queue.
     * @param time The number of available nodes
     */
    public void setDefaultCpuTime(String time);

    /**
     * Returns the memory size in MB in this queue.
     * @return The number of live jobs
     */
    public int getMemorySizeInMB();

    /**
     * Sets the memory size in MB in this queue.
     * @param memorySizeInMB The memory size in MB
     */
    public void setMemorySizeInMB(int memorySizeInMB);

    /**
     * Returns the number of live jobs in this queue.
     * @return The number of live jobs
     */
    public int getNumLiveJobs();

    /**
     * Returns the number of live jobs in this queue.
     * @param numLiveJobs The number of live jobs
     */
    public void setNumLiveJobs(int numLiveJobs);

    /**
     * Returns the number of active jobs in this queue.
     * @return The number of active jobs
     */
    public int getNumActiveJobs();

    /**
     * Sets the number of active jobs in this queue.
     * @param numActiveJobs The number of active jobs
     */
    public void setNumActiveJobs(int numActiveJobs);

    /**
     * Returns the number of pending jobs in this queue.
     * @return The number of pending jobs
     */
    public int getNumPendingJobs();

    /**
     * Sets the number of pending jobs in pending queue.
     * @param numPendingJobs The number of pending jobs
     */
    public void setNumPendingJobs(int numPendingJobs);

    /**
     * Returns the number of pending jobs in this queue.
     * @return The number of suspended jobs
     */
    public int getNumSuspendedJobs();

    /**
     * Sets the number of pending jobs in pending queue.
     * @param numSuspendedJobs The number of suspended jobs
     */
    public void setNumSuspendedJobs(int numSuspendedJobs);

    /**
     * Returns the jobs currently on this queue
     * @return List of jobs
     */
    public List getJobs();

    /**
     * Returns the job on this queue with the given job id
     * @param jobId The job id
     * @return The job
     */
    public Job getJob(String jobId);

    /**
     * Adds the given job to this queue
     * @param job The job
     */
    public void putJob(Job job);

    /**
     * Removes the job on this queue with the given job id
     * @param jobId The job id
     * @return The job that was removed
     */
    public Job removeJob(String jobId);

    /**
     * Removes all the jobs currently on this queue
     */
    public void clearJobs();

}

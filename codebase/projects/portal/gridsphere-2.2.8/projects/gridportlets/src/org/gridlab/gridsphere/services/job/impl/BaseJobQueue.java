package org.gridlab.gridsphere.services.job.impl;

import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.resource.impl.BaseResource;

import java.util.List;
import java.util.Iterator;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseJobQueue.java,v 1.1.1.1 2007-02-01 20:40:47 kherm Exp $
 * <p>
 * Base implementatinon of job queue.
 */

public class BaseJobQueue extends BaseResource implements JobQueue {
    
    private String name = null;

    public BaseJobQueue() {
        super();
        setResourceType(JobQueueType.INSTANCE);
    }

    public BaseJobQueue(BaseJobScheduler scheduler, String name) {
        super(scheduler);
        setResourceType(JobQueueType.INSTANCE);
        this.name = name;
    }

    public String getLabel() {
        return name;
    }

    public void setLabel(String label) {
        name = label;
    }

    public JobScheduler getJobScheduler() {
        return (JobScheduler)getParentResource();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return getResourceAttributeValue(STATUS_ATTRIBUTE, "-");
    }

    public void setStatus(String status) {
        setResourceAttributeValue(STATUS_ATTRIBUTE, status);
    }

    public String getDispatchType() {
        return getResourceAttributeValue(DISPATCH_TYPE_ATTRIBUTE, "-");
    }

    public void setDispatchType(String dispatchType) {
        setResourceAttributeValue(DISPATCH_TYPE_ATTRIBUTE, dispatchType);
    }

    public boolean getIsEnabled() {
        return getResourceAttributeAsBool(STATUS_ATTRIBUTE, true);
    }

    public void setIsEnabled(boolean enabled) {
        setResourceAttributeAsBool(IS_ENABLED_ATTRIBUTE, enabled);
    }

    public void setIsEnabled(String enabled) {
        if (enabled.equalsIgnoreCase("yes")) {
            setIsEnabled(true);
        } else if (enabled.equalsIgnoreCase("no")) {
            setIsEnabled(false);
        } else {
            setResourceAttributeValue(IS_ENABLED_ATTRIBUTE, enabled);
        }

    }

    public int getMaxNumNodes() {
        return getResourceAttributeAsInt(MAX_NUM_NODES_ATTRIBUTE, 0);
    }

    public void setMaxNumNodes(int numNodes) {
        setResourceAttributeAsInt(MAX_NUM_NODES_ATTRIBUTE, numNodes);
    }

    public int getMinNumNodes() {
        return getResourceAttributeAsInt(MIN_NUM_NODES_ATTRIBUTE, 0);
    }

    public void setMinNumNodes(int numNodes) {
        setResourceAttributeAsInt(MIN_NUM_NODES_ATTRIBUTE, numNodes);
    }

    public int getNumFreeNodes() {
        return getResourceAttributeAsInt(NUM_FREE_NODES_ATTRIBUTE, 0);
    }

    public void setNumFreeNodes(int numNodes) {
        setResourceAttributeAsInt(NUM_FREE_NODES_ATTRIBUTE, numNodes);
    }

    public int getMemorySizeInMB() {
        return getResourceAttributeAsInt(MEMORY_SIZE_MB_ATTRIBUTE, 0);
    }

    public void setMemorySizeInMB(int memorySizeInMB) {
        setResourceAttributeAsInt(MEMORY_SIZE_MB_ATTRIBUTE, memorySizeInMB);
    }

    public String getMaxTime() {
        return getResourceAttributeValue(MAX_TIME_ATTRIBUTE, "-");
    }

    public void setMaxTime(String value) {
        setResourceAttributeValue(MAX_TIME_ATTRIBUTE, value);
    }

    public String getMaxCpuTime() {
        return getResourceAttributeValue(MAX_CPU_TIME_ATTRIBUTE, "-");
    }

    public void setMaxCpuTime(String value) {
        setResourceAttributeValue(MAX_CPU_TIME_ATTRIBUTE, value);
    }

    public String getMinCpuTime() {
        return getResourceAttributeValue(MIN_CPU_TIME_ATTRIBUTE, "-");
    }

    public void setMinCpuTime(String value) {
        setResourceAttributeValue(MIN_CPU_TIME_ATTRIBUTE, value);
    }

    public String getDefaultCpuTime() {
        return getResourceAttributeValue(DEFAULT_CPU_TIME_ATTRIBUTE, "-");
    }

    public void setDefaultCpuTime(String value) {
        setResourceAttributeValue(DEFAULT_CPU_TIME_ATTRIBUTE, value);
    }

    public String getMaxWallTime() {
        return getResourceAttributeValue(MAX_WALL_TIME_ATTRIBUTE, "-");
    }

    public void setMaxWallTime(String value) {
        setResourceAttributeValue(MAX_WALL_TIME_ATTRIBUTE, value);
    }

    public String getMinWallTime() {
        return getResourceAttributeValue(MIN_WALL_TIME_ATTRIBUTE, "-");
    }

    public void setMinWallTime(String value) {
        setResourceAttributeValue(MIN_WALL_TIME_ATTRIBUTE, value);
    }

    public String getDefaultWallTime() {
        return getResourceAttributeValue(DEFAULT_WALL_TIME_ATTRIBUTE, "-");
    }

    public void setDefaultWallTime(String value) {
        setResourceAttributeValue(DEFAULT_WALL_TIME_ATTRIBUTE, value);
    }

    public int getNumLiveJobs() {
        return getResourceAttributeAsInt(JOBS_LIVE_ATTRIBUTE, -1);
    }

    public void setNumLiveJobs(int numLiveJobs) {
        setResourceAttributeAsInt(JOBS_LIVE_ATTRIBUTE, numLiveJobs);
    }

    public int getNumActiveJobs() {
        return getResourceAttributeAsInt(JOBS_ACTIVE_ATTRIBUTE, -1);
    }

    public void setNumActiveJobs(int numActiveJobs) {
        setResourceAttributeAsInt(JOBS_ACTIVE_ATTRIBUTE, numActiveJobs);
    }

    public int getNumPendingJobs() {
        return getResourceAttributeAsInt(JOBS_PENDING_ATTRIBUTE, -1);
    }

    public void setNumPendingJobs(int numPendingJobs) {
        setResourceAttributeAsInt(JOBS_PENDING_ATTRIBUTE, numPendingJobs);
    }

    public int getNumSuspendedJobs() {
        return getResourceAttributeAsInt(JOBS_SUSPENDED_ATTRIBUTE, -1);
    }

    public void setNumSuspendedJobs(int numSuspendedJobs) {
        setResourceAttributeAsInt(JOBS_SUSPENDED_ATTRIBUTE, numSuspendedJobs);
    }

    public List getJobs() {
        return getChildResources(JobType.INSTANCE);
    }

    public Job getJob(String jobId) {
        int position = findJob(jobId);
        if (position < 0) {
            return null;
        }
        Job job = (Job)getJobs().get(position);
        return job;
    }

    public void putJob(Job job) {
        // TODO: Need a better way to do this, not thread safe!!!! And very slow!!!
        String jobId = job.getJobId();
        int position = findJob(jobId);
        if (position == -1) {
            addChildResource(job);
        } else {
            getJobs().set(position, job);
            ((BaseJob)job).setParentResource(this);
        }
    }

    public Job removeJob(String jobId) {
        Job job = null;
        int position = findJob(jobId);
        if (position > -1) {
            job = (Job)getJobs().remove(position);
        }
        return job;
    }

    public void clearJobs() {
        // TODO: Need clearChildResources(ResourceType type)
        clearChildResources();
    }

    /**
     * Returns the position of the job in
     * the job list with the given label.
     * Returns -1 if job not found with
     * that label.
     * @param jobId
     * @return
     */
    private int findJob(String jobId) {
        List jobList = getJobs();
        int ii = 0;
        for (Iterator jobs = jobList.iterator(); jobs.hasNext(); ++ii) {
            Job job = (Job) jobs.next();
            if (job.getJobId().equals(jobId)) {
                return ii;
            }
        }
        return -1;
    }
}

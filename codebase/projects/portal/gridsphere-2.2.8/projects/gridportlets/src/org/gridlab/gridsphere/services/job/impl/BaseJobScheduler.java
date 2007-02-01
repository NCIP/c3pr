package org.gridlab.gridsphere.services.job.impl;

import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.resource.impl.BaseResource;
import org.gridlab.gridsphere.services.resource.HardwareResource;

import java.util.List;
import java.util.Iterator;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseJobScheduler.java,v 1.1.1.1 2007-02-01 20:40:47 kherm Exp $
 * <p>
 * Base implementation of job scheduler.
 */

public class BaseJobScheduler
        extends BaseResource
        implements JobScheduler {
    
    private JobSchedulerType jobSchedulerType = JobSchedulerType.INSTANCE;
    private String jobSchedulerTypeName = null;

    public BaseJobScheduler() {
        super();
        setJobSchedulerType(jobSchedulerType);
    }

    public BaseJobScheduler(HardwareResource resource) {
        super(resource);
        setJobSchedulerType(jobSchedulerType);
    }

    public BaseJobScheduler(JobResource resource) {
        super(resource);
        setJobSchedulerType(jobSchedulerType);
    }

    public String getLabel() {
        return jobSchedulerTypeName;
    }

    public void setLabel(String label) {
        jobSchedulerTypeName = label;
    }

    public JobSchedulerType getJobSchedulerType() {
        return jobSchedulerType;
    }
    
    public void setJobSchedulerType(JobSchedulerType type) {
        jobSchedulerType = type;
        jobSchedulerTypeName = type.getName();
        setResourceType(jobSchedulerType);
    }

    /**
     * Used by persistence manager.
     * @return The job scheduler type name
     */        
    public String getJobSchedulerTypeName() {
        return jobSchedulerTypeName;
    }

    /**
     * Used by persistence manager.
     * @param name The job scheduler type name
     */
    public void setJobSchedulerTypeName(String name) {
        jobSchedulerTypeName = name;
        jobSchedulerType = JobSchedulerType.toJobSchedulerType(name);
    }

    public List getJobQueues() {
        return getChildResources(JobQueueType.INSTANCE);
    }

    public JobQueue getJobQueue(String name) {
        synchronized (this) {
            int position = findJobQueue(name);
            if (position < 0) {
                return null;
            }
            JobQueue queue = (JobQueue)getJobQueues().get(position);
            return queue;
        }
    }
    
    public JobQueue putJobQueue(String name) {
        synchronized (this) {
            BaseJobQueue queue = null;
            int position = findJobQueue(name);
            if (position == -1) {
                queue = new BaseJobQueue(this, name);
                addChildResource(queue);
            } else {
                queue = (BaseJobQueue)getJobQueues().get(position);
            }
            return queue;
        }
    }

    public void putJobQueue(JobQueue queue) {
        synchronized (this) {
            // TODO: Need a better way to do this, very slow!!!
            String name = queue.getName();
            int position = findJobQueue(name);
            if (position == -1) {
                addChildResource(queue);
//            } else {
//                ((BaseResource)queue).setParentResource(this);
//                getJobQueues().set(position, queue);
            }
        }
    }

    public JobQueue removeJobQueue(String name) {
        synchronized (this) {
            JobQueue queue = null;
            int position = findJobQueue(name);
            if (position > -1) {
                queue = (JobQueue)getChildResources().remove(position);
            }
            return queue;
        }
    }

    public void clearsJobQueues() {
        // TODO: Need clearChildResources(ResourceType type)
        clearChildResources();
    }

    private int findJobQueue(String name) {
        List jobQueueList = getJobQueues();
        int ii = 0;
        for (Iterator jobQueues = jobQueueList.iterator(); jobQueues.hasNext(); ++ii) {
            JobQueue jobQueue = (JobQueue) jobQueues.next();
            if (jobQueue.getName().equals(name)) {
                return ii;
            }
        }
        return -1;
    }
}

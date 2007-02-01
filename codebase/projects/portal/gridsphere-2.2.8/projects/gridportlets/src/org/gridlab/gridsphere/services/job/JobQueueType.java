package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.file.FileResourceType;
import org.gridlab.gridsphere.services.job.impl.BaseJobQueue;

public class JobQueueType extends ResourceType {

    public static JobQueueType INSTANCE = new JobQueueType("JobQueue", JobQueue.class, BaseJobQueue.class);
    private String id = null;

    protected JobQueueType(String id, Class resourceClass, Class resourceImplementation) {
        super(id, resourceClass, resourceImplementation);
    }
}

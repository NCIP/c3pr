package org.gridlab.gridsphere.services.resources.gram;

import org.gridlab.gridsphere.services.job.JobSchedulerType;

public class GramJobManagerType extends JobSchedulerType {

    public static final GramJobManagerType INSTANCE
        = new GramJobManagerType("GramJobManager", GramJobManager.class);

    protected GramJobManagerType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(JobSchedulerType.INSTANCE);
    }
}

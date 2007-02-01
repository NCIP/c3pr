package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.resource.ServiceResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;

public class JobResourceType extends ResourceType {

    public static JobResourceType INSTANCE = new JobResourceType("JobResource", JobResource.class);

    protected JobResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(ServiceResourceType.INSTANCE);
    }
}

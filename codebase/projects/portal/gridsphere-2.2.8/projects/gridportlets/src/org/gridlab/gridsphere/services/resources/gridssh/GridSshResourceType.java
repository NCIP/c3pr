package org.gridlab.gridsphere.services.resources.gridssh;

import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.security.gss.GssConnectionResourceType;

public class GridSshResourceType extends ResourceType {

    public static GridSshResourceType INSTANCE
            = new GridSshResourceType("GridSshResource", GridSshResource.class);

    protected GridSshResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(GssConnectionResourceType.INSTANCE);
    }
}

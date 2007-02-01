package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileResourceType;
import org.gridlab.gridsphere.services.security.gss.GssEnabledResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;

public class GridFtpResourceType extends ResourceType {

    public static GridFtpResourceType INSTANCE
            = new GridFtpResourceType("GridFtpResource", GridFtpResource.class);

    protected GridFtpResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(FileResourceType.INSTANCE);
        addSuperType(GssEnabledResourceType.INSTANCE);
    }
}

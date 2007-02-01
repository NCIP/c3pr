/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.FileResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;

/**
 * Describes a type of gridlab logical file resource.
 */
public class LocalHostResourceType extends ResourceType {

    public static LocalHostResourceType INSTANCE
            = new LocalHostResourceType("LocalHostResource", LocalHostResource.class);

    protected LocalHostResourceType() {}

    protected LocalHostResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(FileResourceType.INSTANCE);
    }
}

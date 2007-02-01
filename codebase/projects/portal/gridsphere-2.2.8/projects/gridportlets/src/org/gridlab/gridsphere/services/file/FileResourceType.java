/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.services.resource.ServiceResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;

/**
 * Describes a type of file resource.
 */
public class FileResourceType extends ResourceType {

    public static FileResourceType INSTANCE = new FileResourceType("FileResource", FileResource.class);

    protected FileResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(ServiceResourceType.INSTANCE);
    }
}

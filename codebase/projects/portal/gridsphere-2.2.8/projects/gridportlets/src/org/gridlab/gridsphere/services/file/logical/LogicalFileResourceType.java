/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.logical;

import org.gridlab.gridsphere.services.file.FileResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;

/**
 * Describes a type of logical file resource.
 */
public class LogicalFileResourceType extends ResourceType {

    public static LogicalFileResourceType INSTANCE
            = new LogicalFileResourceType("LogicalFileResource", LogicalFileResource.class);

    protected LogicalFileResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(FileResourceType.INSTANCE);
    }
}

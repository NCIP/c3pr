/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.mds2;

import org.gridlab.gridsphere.services.resource.ResourceType;

/**
 * Describes a type of grid information index service (giis) resource.
 */
public class GIISResourceType extends ResourceType {

    public static GIISResourceType INSTANCE = new GIISResourceType("GIISResource", GIISResource.class);

    protected GIISResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(Mds2ResourceType.INSTANCE);
    }
}

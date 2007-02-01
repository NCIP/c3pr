/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.mds2;

import org.gridlab.gridsphere.services.resource.ResourceType;

/**
 * Describes a type of grid resource information service (gris) resource.
 */
public class GRISResourceType extends ResourceType {

    public static GRISResourceType INSTANCE = new GRISResourceType("GRISResource", GRISResource.class);

    protected GRISResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(Mds2ResourceType.INSTANCE);
    }
}
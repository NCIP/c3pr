package org.gridlab.gridsphere.services.resources.map;

import org.gridlab.gridsphere.services.resource.ResourceType;

public class MapResourceType extends ResourceType {

    public static MapResourceType INSTANCE = new MapResourceType("MapResource", MapResource.class);

    protected MapResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
    }
}

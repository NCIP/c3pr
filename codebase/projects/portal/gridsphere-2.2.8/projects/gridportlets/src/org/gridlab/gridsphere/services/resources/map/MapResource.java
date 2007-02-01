package org.gridlab.gridsphere.services.resources.map;

import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.impl.BaseServiceResource;

public class MapResource extends BaseServiceResource {

    public static final String DEFAULT_PORT = "8080";
    public static final String DEFAULT_PROTOCOL = "http";

    public MapResource() {
        super();
        setPort(DEFAULT_PORT);
        setProtocol(DEFAULT_PROTOCOL);
        setResourceType(MapResourceType.INSTANCE);
    }

    public void copy(Resource resource) {
        super.copy(resource);
    }

    public String getMapUrl(String host) {
        // Job map url
        String mapUrl = "http://www.aei.mpg.de/~ikelley/Maps/blank_map.png";
        if (host != null) {
            mapUrl = "http://www.aei.mpg.de/~ikelley/Maps/"
                   + host
                   + ".png";
        }
        return mapUrl;
    }
}

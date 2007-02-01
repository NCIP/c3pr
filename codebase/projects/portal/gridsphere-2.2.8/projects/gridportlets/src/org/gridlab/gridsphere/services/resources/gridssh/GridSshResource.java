/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.gridssh;

import org.gridlab.gridsphere.services.resource.impl.BaseServiceResource;

/**
 * Implements a grid ssh resource.
 */
public class GridSshResource extends BaseServiceResource {

    public static final String DEFAULT_PORT = "2222";
    public static final String DEFAULT_PROTOCOL = "gsissh";

    public GridSshResource() {
        super();
        setPort(DEFAULT_PORT);
        setProtocol(DEFAULT_PROTOCOL);
        setResourceType(GridSshResourceType.INSTANCE);
    }
}

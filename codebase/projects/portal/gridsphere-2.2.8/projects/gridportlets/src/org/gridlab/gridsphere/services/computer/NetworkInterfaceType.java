/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: NetworkInterfaceType.java,v 1.1.1.1 2007-02-01 20:39:56 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.resource.HardwareResourceType;

/**
 * Describes a type of gridlab logical file resource.
 */
public class NetworkInterfaceType extends ResourceType {

    public static NetworkInterfaceType INSTANCE
            = new NetworkInterfaceType("NetworkInterface", NetworkInterface.class);

    protected NetworkInterfaceType() {}

    protected NetworkInterfaceType(String id, Class resourceClass) {
        super(id, resourceClass);
    }
}

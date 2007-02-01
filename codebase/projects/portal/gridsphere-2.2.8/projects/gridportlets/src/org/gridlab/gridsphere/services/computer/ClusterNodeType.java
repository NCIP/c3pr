/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ClusterNodeType.java,v 1.1.1.1 2007-02-01 20:39:51 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.resource.HardwareResourceType;
import org.gridlab.gridsphere.services.resource.HostResourceType;

/**
 * Describes a type of gridlab logical file resource.
 */
public class ClusterNodeType extends ResourceType {

    public static ClusterNodeType INSTANCE
            = new ClusterNodeType("ClusterNode", ClusterNode.class);

    protected ClusterNodeType() {}

    protected ClusterNodeType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(HostResourceType.INSTANCE);
    }
}

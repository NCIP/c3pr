/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ClusterResourceType.java,v 1.1.1.1 2007-02-01 20:39:51 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.ResourceType;

public class ClusterResourceType extends ResourceType {

    public static ClusterResourceType INSTANCE
            = new ClusterResourceType("ClusterResource", ClusterResource.class);

    protected ClusterResourceType() {}

    protected ClusterResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
    }
}

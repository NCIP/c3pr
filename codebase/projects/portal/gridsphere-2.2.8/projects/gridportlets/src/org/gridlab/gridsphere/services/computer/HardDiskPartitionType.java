/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardDiskPartitionType.java,v 1.1.1.1 2007-02-01 20:39:53 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.ResourceType;

public class HardDiskPartitionType extends ResourceType {

    public static HardDiskPartitionType INSTANCE
            = new HardDiskPartitionType("HardDiskPartition", HardDiskPartition.class);

    protected HardDiskPartitionType() {}

    protected HardDiskPartitionType(String id, Class resourceClass) {
        super(id, resourceClass);
    }
}

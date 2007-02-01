/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardDiskType.java,v 1.1.1.1 2007-02-01 20:39:54 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.ResourceType;

public class HardDiskType extends ResourceType {

    public static HardDiskType INSTANCE
            = new HardDiskType("HardDisk", HardDisk.class);

    protected HardDiskType() {}

    protected HardDiskType(String id, Class resourceClass) {
        super(id, resourceClass);
    }
}

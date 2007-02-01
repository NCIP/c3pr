package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.resource.impl.HardwareAccountImpl;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardwareAccountType.java,v 1.1.1.1 2007-02-01 20:40:50 kherm Exp $
 */

public class HardwareAccountType extends ResourceType {

    public static final HardwareAccountType INSTANCE
            = new HardwareAccountType("HardwareAccount", HardwareAccount.class, HardwareAccountImpl.class);

    protected HardwareAccountType(String id, Class resourceClass, Class resourceImplementation) {
        super(id, resourceClass, resourceImplementation);
        addSuperType(ResourceAccountType.INSTANCE);
    }
}

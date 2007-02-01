package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.resource.impl.BaseResourceAccount;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceAccountType.java,v 1.1.1.1 2007-02-01 20:40:51 kherm Exp $
 */

public class ResourceAccountType extends ResourceType {

    public static final ResourceAccountType INSTANCE
            = new ResourceAccountType("ResourceAccount", ResourceAccount.class, BaseResourceAccount.class);

    protected ResourceAccountType(String id, Class resourceClass, Class resourceImplementation) {
        super(id, resourceClass, resourceImplementation);
    }
}

package org.gridlab.gridsphere.services.resources.myproxy;

import org.gridlab.gridsphere.services.security.gss.CredentialRepository;
import org.gridlab.gridsphere.services.security.gss.CredentialRepositoryType;
import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.resource.ServiceResourceType;

/**
 * Describes a MyProxy resource type. MyProxy is a credential repository
 * that is quite popular for use with Grid portals. For more information
 * about MyProxy, refer to <a
 * href="http://grid.ncsa.uiuc.edu/myproxy/">http://grid.ncsa.uiuc.edu/myproxy/</a>.
 * @see CredentialRepository
 */
public class MyProxyResourceType extends ResourceType {

    public static MyProxyResourceType INSTANCE
            = new MyProxyResourceType("MyProxyResource", MyProxyResource.class);

    protected MyProxyResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(ServiceResourceType.INSTANCE);
        addSuperType(CredentialRepositoryType.INSTANCE);
    }
}

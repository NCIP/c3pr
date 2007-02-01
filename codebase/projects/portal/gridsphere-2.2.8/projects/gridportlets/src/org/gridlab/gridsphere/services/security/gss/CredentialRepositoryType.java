/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.resource.ServiceResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.resources.myproxy.MyProxyResourceType;

/**
 * Describes a type of credential repository. There is currently one type of
 * credential respository available in Grid Portlets, the "MyProxy Credential
 * Repository". See the "Grid Portlets Administrator's Guide" for more details.
 * @see MyProxyResourceType
 */
public class CredentialRepositoryType extends ResourceType {

    public static CredentialRepositoryType INSTANCE
            = new CredentialRepositoryType("CredentialRepository", CredentialRepository.class);

    protected CredentialRepositoryType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(ServiceResourceType.INSTANCE);
    }
}

package org.gridlab.gridsphere.services.resources.ldap;

import org.gridlab.gridsphere.services.info.InformationResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;

public class LdapResourceType extends ResourceType {

    public static LdapResourceType LDAP_TYPE
            = new LdapResourceType("LdapResource", LdapResource.class);

    protected LdapResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(InformationResourceType.INSTANCE);
    }
}

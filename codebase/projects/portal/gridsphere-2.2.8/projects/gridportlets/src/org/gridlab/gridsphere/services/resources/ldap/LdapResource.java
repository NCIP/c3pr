package org.gridlab.gridsphere.services.resources.ldap;

import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.impl.BaseServiceResource;
import org.gridlab.gridsphere.services.info.InformationResourceType;
import org.gridlab.gridsphere.services.info.InformationResource;

public class LdapResource extends BaseServiceResource implements InformationResource {

    public static final String AUTHENTICATION_NONE = "none";
    public static final String AUTHENTICATION_SIMPLE = "simple";
    public static final String TIMEOUT_DEFAULT = "5000";

    public static final String DEFAULT_PORT = "389";
    public static final String DEFAULT_PROTOCOL = "ldap";
    public static final String AUTHENTICATION = "authentication";
    public static final String TIMEOUT = "timeout";

    protected String baseDN = "";

    public LdapResource() {
        super();
        setPort(DEFAULT_PORT);
        setProtocol(DEFAULT_PROTOCOL);
        setResourceType(LdapResourceType.LDAP_TYPE);
    }

    /**
     * Returns the base dn of this ldap resource.
     * @return
     */
    public String getBaseDN() {
        return baseDN;
    }

    /**
     * Sets the base dn of this ldap resource.
     * @param baseDN
     */
    public void setBaseDN(String baseDN) {
        this.baseDN = baseDN;
    }

    public void copy(Resource resource) {
        super.copy(resource);
        LdapResource ldapResource = (LdapResource)resource;
        this.baseDN = ldapResource.getBaseDN();
    }

    public String getAuthentication() {
        return getResourceAttributeValue(AUTHENTICATION, AUTHENTICATION_NONE);
    }

    public void setAuthentication(String authentication) {
        setResourceAttributeValue(AUTHENTICATION, authentication);
    }

    public String getTimeout() {
        return getResourceAttributeValue(TIMEOUT, "5000");
    }

    public void setTimeout(String timeout) {
        setResourceAttributeValue(TIMEOUT, timeout);
    }
}

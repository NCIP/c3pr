/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingImpl.java,v 1.1.1.1 2007-02-01 20:41:45 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.security.gss.CredentialMapping;

/**
 * Maps a credential to a resource location
 */
public class CredentialMappingImpl implements CredentialMapping {

    private String oid = null;
    private String credentialDn = null;
    private String resourceDn = null;

    public CredentialMappingImpl() {
    }

    public CredentialMappingImpl(String dn, String resourceDn) {
        this.credentialDn = dn;
        this.resourceDn = resourceDn;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getCredentialDn() {
        return credentialDn;
    }

    public void setCredentialDn(String credentialDn) {
        this.credentialDn = credentialDn;
    }

    public String getResourceDn() {
        return resourceDn;
    }

    public void setResourceDn(String resourceDn) {
        this.resourceDn = resourceDn;
    }
}

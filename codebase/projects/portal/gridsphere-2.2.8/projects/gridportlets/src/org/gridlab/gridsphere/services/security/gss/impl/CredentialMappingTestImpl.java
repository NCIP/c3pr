/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTestImpl.java,v 1.1.1.1 2007-02-01 20:41:47 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.security.gss.CredentialMappingTest;

public class CredentialMappingTestImpl implements CredentialMappingTest {

    private String oid = null;
    private String credentialDn = null;
    private String resourceDn = null;
    private int errorCode = 0;
    private String errorMessage = null;

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

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }
}

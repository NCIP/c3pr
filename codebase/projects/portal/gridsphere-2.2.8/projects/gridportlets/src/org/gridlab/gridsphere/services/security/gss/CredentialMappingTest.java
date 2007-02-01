/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTest.java,v 1.1.1.1 2007-02-01 20:41:30 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

public interface CredentialMappingTest {

    public static final int SUCCESS = 0;
    public static final int ERROR_RESOURCE_NOT_FOUND = -1;
    public static final int ERROR_RESOURCE_NOT_GSS_ENABLED = -2;
    public static final int ERROR_CREDENTIAL_EXCEPTION = -3;
    public static final int ERROR_RESOURCE_EXCEPTION = -3;
    public static final int ERROR_UNKNOWN = -3;

    public String getCredentialDn();

    public String getResourceDn();

    public int getErrorCode();

    public String getErrorMessage();
}

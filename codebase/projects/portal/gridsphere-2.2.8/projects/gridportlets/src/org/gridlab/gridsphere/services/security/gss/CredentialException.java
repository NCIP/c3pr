/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id: CredentialException.java,v 1.1.1.1 2007-02-01 20:41:28 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.portlet.service.PortletServiceException;

/**
 * Describes a credential related exception.
 */
public class CredentialException extends PortletServiceException {

    public CredentialException() {
        super();
    }

    public CredentialException(String message) {
        super(message);
    }

    public CredentialException(String message, Throwable ex) {
        super(message + ' ' + ex.getMessage());
    }
}

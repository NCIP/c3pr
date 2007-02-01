/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id: CredentialRetrievalException.java,v 1.1.1.1 2007-02-01 20:41:31 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

/**
 * Describes a credential retrieval related exception.
 */
public class CredentialRetrievalException extends CredentialException {

    public CredentialRetrievalException() {
        super();
    }

    public CredentialRetrievalException(String message) {
        super(message);
    }

    public CredentialRetrievalException(String message, Throwable ex) {
        super(message, ex);
    }
}

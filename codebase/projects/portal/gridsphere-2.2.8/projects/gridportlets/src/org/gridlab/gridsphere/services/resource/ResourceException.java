package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.portlet.service.PortletServiceException;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceException.java,v 1.1.1.1 2007-02-01 20:40:53 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class ResourceException extends PortletServiceException {

    public ResourceException() {
        super();
    }

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(Throwable cause) {
        super(cause);
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}

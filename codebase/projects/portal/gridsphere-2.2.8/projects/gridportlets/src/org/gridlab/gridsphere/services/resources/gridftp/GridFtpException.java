package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.resource.ResourceException;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpException.java,v 1.1.1.1 2007-02-01 20:41:11 kherm Exp $
 * <p>
 * An exception thrown while using grid ftp connecions.
 */

public class GridFtpException extends ResourceException {

    public GridFtpException() {
        super();
    }

    public GridFtpException(String message) {
        super(message);
    }

    public GridFtpException(Throwable cause) {
        super(cause);
    }

    public GridFtpException(String message, Throwable cause) {
        super(message, cause);
    }
}

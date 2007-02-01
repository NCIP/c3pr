/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileException.java,v 1.1.1.1 2007-02-01 20:39:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.services.resource.ResourceException;

/**
 * Describes a file related exception.
 */
public class FileException extends ResourceException {

    public FileException() {
        super();
    }

    public FileException(String message) {
        super(message);
    }

    public FileException(Throwable cause) {
        super(cause);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}

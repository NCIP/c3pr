/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.FileException;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: LocalHostException.java,v 1.1.1.1 2007-02-01 20:41:26 kherm Exp $
 * <p>
 * An exception thrown while using gdms file connections.
 */

public class LocalHostException extends FileException {

    public LocalHostException() {
        super();
    }

    public LocalHostException(String message) {
        super(message);
    }

    public LocalHostException(Throwable cause) {
        super(cause);
    }

    public LocalHostException(String message, Throwable cause) {
        super(message, cause);
    }
}

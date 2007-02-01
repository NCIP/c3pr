/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileTaskException.java,v 1.1.1.1 2007-02-01 20:40:02 kherm Exp $
 * <p>
 * Describes an exception that occurs during a file operation.
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.services.task.TaskException;

/**
 * Describes a file task related exception.
 */
public class FileTaskException extends TaskException {

    /**
     * Default constructor
     */
    public FileTaskException() {
        super();
    }

    /**
     * Constructs a file task exception with the given message.
     * @param message The exception message
     */
    public FileTaskException(String message) {
        super(message);
    }
}

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobException.java,v 1.1.1.1 2007-02-01 20:40:40 kherm Exp $
 * <p>
 * Describes an exception that occurs during job submission.
 */
package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.task.TaskException;

public class JobException extends TaskException {

    public JobException() {
        super();
    }

    public JobException(String message) {
        super(message);
    }
}

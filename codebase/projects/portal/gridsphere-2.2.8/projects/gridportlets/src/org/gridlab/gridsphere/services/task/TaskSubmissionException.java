/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: TaskSubmissionException.java,v 1.1.1.1 2007-02-01 20:41:51 kherm Exp $
 * <p>
 * Describes an exception that occurs during task submission.
 */
package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.services.task.TaskException;

public class TaskSubmissionException extends TaskException {

    public TaskSubmissionException() {
        super();
    }

    public TaskSubmissionException(String message) {
        super(message);
    }
}

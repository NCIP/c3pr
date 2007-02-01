/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: TaskException.java,v 1.1.1.1 2007-02-01 20:41:50 kherm Exp $
 * <p>
 * Describes an exception that occurs during task submission.
 */
package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.portlet.service.PortletServiceException;

public class TaskException extends PortletServiceException {

    public TaskException() {
        super();
    }

    public TaskException(String message) {
        super(message);
    }
}

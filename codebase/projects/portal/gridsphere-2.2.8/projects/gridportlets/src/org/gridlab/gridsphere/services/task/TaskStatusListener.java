package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.services.task.Task;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: TaskStatusListener.java,v 1.1.1.1 2007-02-01 20:41:50 kherm Exp $
 * <p>
 * Any class that requires notification of a change in the taskStatus
 * of a task should implement this interface.
 */

public interface TaskStatusListener {

    /**
     * Signifies the taskStatus of a task has changed. Both the new
     * taskStatus and the original task are provided, since calling
     * getTaskStatus() on task might return a different value at
     * any given point in time.
     * @param task The task
     */
    public void statusChanged(Task task);
}

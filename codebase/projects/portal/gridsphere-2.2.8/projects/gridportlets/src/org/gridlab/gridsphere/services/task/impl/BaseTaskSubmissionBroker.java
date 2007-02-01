package org.gridlab.gridsphere.services.task.impl;

import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.task.TaskSubmissionService;

import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Nov 24, 2003
 * Time: 12:17:33 PM
 * To change this template use Options | File Templates.
 */
public class BaseTaskSubmissionBroker
        extends SimpleTaskSubmissionBroker
        implements TaskSubmissionService {

    /**
     * See TaskSubmissionService
     * @return The task types
     */
    public List getTaskTypes() {
        List types = new Vector();
        types.add(TaskType.INSTANCE);
        return types;
    }
}

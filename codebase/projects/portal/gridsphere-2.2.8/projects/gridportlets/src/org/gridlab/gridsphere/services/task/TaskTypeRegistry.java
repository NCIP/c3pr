package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.portlet.service.PortletService;

import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: TaskTypeRegistry.java,v 1.1.1.1 2007-02-01 20:41:52 kherm Exp $
 * <p>
 */

public interface TaskTypeRegistry extends PortletService {

    /**
     * Returns all the task types for all the registered submission services.
     * @return The available task types
     * @see TaskType
     */
    public List getTaskTypes();

    /**
     * Returns all the submission services that have registered with this manager.
     * @return The available task submission services
     * @see TaskSubmissionService
     */
    public List getTaskSubmissionServices();

    /**
     * Returns the submission service that submits tasks of the given type.
     * @param type The task type
     * @return The associated task submission service
     */
    public TaskSubmissionService getTaskSubmissionService(TaskType type);

    /**
     * Registers the given service with this manager. Once registered,
     * the task submission manager can use the given service to
     * submit all tasks supported by that service.
     * @param service The task submission service
     */
    public void registerTaskTypes(TaskSubmissionService service);

    /**
     * Unregisters the given service from this manager.
     * @param service The task submission service
     */
    public void unregisterTaskTypes(TaskSubmissionService service);
}

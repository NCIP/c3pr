package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.task.Task;
import org.gridlab.gridsphere.services.task.TaskException;
import org.gridlab.gridsphere.services.task.TaskSpec;

import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: TaskSubmissionService.java,v 1.1.1.1 2007-02-01 20:41:51 kherm Exp $
 * <p>
 * Describes a service for submitting tasks.
 */

public interface TaskSubmissionService extends PortletService {

    /**
     * Returns the types of tasks supported by
     * this submission service.
     * @return The list of task types
     */
    public List getTaskTypes();

    /**
     * Returns true if this service supports the given task type.
     * @param type The task type
     * @return true if supports the given task type, fale otherwise
     */
    public boolean hasTaskType(TaskType type);

    /**
     * Returns a task spec of the given type.
     * @param type The type of task spec
     * @return The task spec
     */
    public TaskSpec createTaskSpec(TaskType type) throws TaskException;

    /**
     * Returns a task spec editor initialized with the values of the given task spec.
     * @param spec The task spec to copy
     * @return The task spec
     */
    public TaskSpec createTaskSpec(TaskSpec spec) throws TaskException;

    /**
     * Creates a new task spec of the given taske type initialized with the values of the given task spec.
     * @param type The type of task spec to create
     * @param spec The task spec to copy
     * @return The new task spec
     * @throws TaskException
     */
    public TaskSpec createTaskSpec(TaskType type, TaskSpec spec) throws TaskException;

    /**
     * Submitting a specification generates a task.
     * If the specification is not set to block on
     * completion, this method should throws an exception
     * if a task cannot be generated for some reason.
     * If the specification is set to block on completion,
     * then this method should throw an exception if
     * either a task cannot be generated or the task
     * does not complete successfully.
     * @param spec The task spec
     * @return The resulting task
     * @throws TaskException
     */
    public Task submitTask(TaskSpec spec)
            throws TaskException;

    /**
     * Cancels the given task. Throws an exception if
     * this operation cannot be successfully carried out.
     * @param task The task to cancel
     * @throws TaskException
     */
    public void cancelTask(Task task)
            throws TaskException;

    /**
     * Returns all the tasks of the given type submitted by this service.
     * @param type The task type
     * @return The list of tasks of the given type
     */
    public List getTasks(TaskType type);

    /**
     * Returns all the tasks of the given type submitted by this service that belong to the given user.
     * @param user The user
     * @param type The task type
     * @return The list of tasks of the given type
     */
    public List getTasks(User user, TaskType type);

    /**
     * Returns the task with the given object id.
     * @param oid The task oid
     * @return The task
     */
    public Task getTask(String oid);

    /**
     * Deletes the record for the given task.
     * @param task The task to delete
     */
    public void deleteTask(Task task);

}

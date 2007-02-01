package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.gridlab.gridsphere.services.resource.Resource;

import java.util.Date;
import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Task.java,v 1.1.1.1 2007-02-01 20:41:49 kherm Exp $
 * <p>
 * Describes a task to perform some operation, for example,
 * a task to transfer a set of files from one location to
 * another.
 */

public interface Task extends Resource {

    /**
     * Returns task type. Used for runtime type-checking.
     * @return The task type
     */
    public TaskType getTaskType();

    /**
     * Returns whether this task is of the given task type.
     * @param taskType The task type
     * @return True if task type, false otherwise
     */
    public boolean isTaskType(TaskType taskType);

    /**
     * Returns the object identifier.
     * @return The task oid
     */
    public String getOid();

    /**
     * Returns the user associated with the task.
     * @return The task user
     */
    public User getUser();

    /**
     * Returns the credential context used for this task
     * @return The task credential context
     */
    public CredentialContext getCredentialContext();

    /**
     * Returns a description of this task.
     * @return The task description
     */
    public String getDescription();

    /**
     * Returns the specification that generated the task.
     * @return The task spec
     */
    public TaskSpec getTaskSpec();

    /**
     * Returns the taskStatus of the task, for example,
     * whether it is active, has completed or failed.
     * @return The task status
     */
    public TaskStatus getTaskStatus();

    /**
     * Returns the message associated with the last taskStatus.
     * If no message associated with last taskStatus, returns null.
     * @return The task status message
     */
    public String getTaskStatusMessage();

    /**
     * Returns true if this task is still live, false
     * otherwise. See TaskStatus for more information.
     * @return True if the task is live, false otherwise
     */
    public boolean isTaskLive();

    /**
     * Returns a list of all the task status listeners for this task.
     * @return The task status listeners
     * @see TaskStatusListener
     */
    public List getTaskStatusListeners();

    /**
     * Adds the given task status listener to this task.
     * @param listener The task status listener
     */
    public void addTaskStatusListener(TaskStatusListener listener);

    /**
     * Returns the list of additional attributes associated with the task.
     * In general, these task attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the task interface.
     * @return The list of task attributes
     * @see TaskAttribute
     */
    public List getTaskAttributes();

    /**
     * Sets the list of additional attributes associated with the task.
     * In general, these task attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the task interface.
     * @param taskAttributes The list of task of attributes
     * @see TaskAttribute
     */
    public void setTaskAttributes(List taskAttributes);

    /**
     * Returns the task attribute with the given name.
     * Returns null if no task attribute exists with the given name.
     * In general, these task attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the task interface.
     * @param name The name of the task attribute
     * @return The task attribute.
     */
    public TaskAttribute getTaskAttribute(String name);

    /**
     * Returns the task attribute with the given name as a string.
     * Returns null if no task attribute exists with the given name.
     * In general, these task attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the task interface.
     * @param name The name of the task attribute
     * @return The task attribute value as a string.
     */
    public String getTaskAttributeValue(String name);

    /**
     * Adds the given task attribute to the list of task attributes.
     * In general, these task attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the task interface.
     * @param attribute The task attribute
     */
    public void putTaskAttribute(TaskAttribute attribute);

    /**
     * Adds the given task name value pair as a task attribute.
     * Returns the task attribute that the method creates.
     * In general, these task attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the task interface.
     * @param name The name of the task attribute
     * @param value The value of the task attribute
     * @return The task attribute
     */
    public TaskAttribute putTaskAttribute(String name, String value);

    /**
     * Removes the task attribute with the given name.
     * Returns the task attribute that was removed.
     * In general, these task attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the task interface.
     * @param name The name of the task attribute
     * @return The task attribute that was removed
     */
    public TaskAttribute removeTaskAttribute(String name);

    /**
     * Clears all the additional attributes associated with this task.
     * In general, these task attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the task interface.
     */
    public void clearTaskAttributes();

    /**
     * Returns the list of metrics associated with the task.
     * For example, the rate at which a file transfer is
     * occuring. Not all task types have taskMetrics associated
     * with them, however, in which case this should return
     * an empty list.
     * @return The list of task metrics
     * @see TaskMetric
     */
    public List getTaskMetrics();

    /**
     * Returns the metric with the given name.
     * @param name The name of the task metric
     * @return The task metric
     */
    public TaskMetric getTaskMetric(String name);

    /**
     * Returns true if the task has taskMetrics that can be
     * monitored.
     * @return True if has task metrics, false otherwise
     */
    public boolean hasTaskMetrics();

    /**
     * Returns the date this task was submitted.
     * @return The date the task was submitted
     */
    public Date getDateTaskSubmitted();
    
    /**
     * Returns the date this task first became active.
     * @return The date task started
     */
    public Date getDateTaskStarted();

    /**
     * Returns the date the taskStatus of this task last changed.
     * @return The date task last changed.
     */
    public Date getDateTaskStatusChanged();
    
    /**
     * Returns the date this task ended (i.e. completed, canceled, or failed)
     * @return The date the task ended
     */
    public Date getDateTaskEnded();

    /**
     * Returns true if this task should delete it's own record when
     * it ends. This is useful when it is not desired to keep a
     * record of a task.
     * @return True if the task should be deleted when it is done, false otherwise.
     */
    public boolean getDeleteWhenTaskEndsFlag();

    /**
     * Waits for the task to end
     * (i.e. isTaskLive() == false)
     */
    public void waitFor() throws TaskException;
}

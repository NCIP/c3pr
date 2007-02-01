package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;

import java.util.List;


/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: TaskSpec.java,v 1.1.1.1 2007-02-01 20:41:50 kherm Exp $
 * <p>
 * Describes a specification for a task.
 */

public interface TaskSpec {

    /**
     * Returns the type of task this specification should generate.
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
     * Returns the record identifier.
     * @return The task spec oid
     */
    public String getOid();

    /**
     * Returns a description of the task.
     * @return The task description
     */
    public String getDescription();

    /**
     * Returns a description of the task.
     * @param description The task description
     */
    public void setDescription(String description);

    /**
     * Returns the user associated with the task.
     * @return The task user
     */
    public User getUser();

    /**
     * Sets the user associated that should be associated with the task.
     * @param user The task user
     */
    public void setUser(User user);

    /**
     * Returns the credential context associated with this task.
     * @return The task credential context
     */
    public CredentialContext getCredentialContext();

    /**
     * Sets the credentialContext associated with this task.
     * @param credentialContext The task credential context
     */
    public void setCredentialContext(CredentialContext credentialContext);

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
     * Returns true if this task should delete it's own record when
     * it is done. This is useful when it is not desired to keep a
     * record of a task.
     * @return True if the task should be deleted when its done, false otherwise.
     */
    public boolean getDeleteWhenTaskEndsFlag();

    /**
     * Sets whether this task should delete it's own rccord when it is done.
     * @param flag The task deletion flag
     */
    public void setDeleteWhenTaskEndsFlag(boolean flag);
}

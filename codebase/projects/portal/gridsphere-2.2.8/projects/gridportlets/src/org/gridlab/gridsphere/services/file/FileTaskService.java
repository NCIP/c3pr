/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileTaskService.java,v 1.1.1.1 2007-02-01 20:40:02 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.task.TaskSubmissionService;

import java.util.List;

/**
 * Describes a service for performing file tasks.
 */
public interface FileTaskService extends TaskSubmissionService {

    /**
     * Returns a list of the file task types supported by this service.
     * @return The list of file task types
     * @see FileTaskType
     */
    public List getFileTaskTypes();

    /**
     * Creates a file task spec of the given file task type.
     * Throws an exception if the given file task type is not supportd
     * by this service.
     * @return The file task spec
     * @throws FileTaskException
     */
    public FileTaskSpec createFileTaskSpec(FileTaskType type)
            throws FileTaskException;

    /**
     * Creates a file task spec initialized with the values provided in the given
     * file task spec. Throws an exception if the file task type of the
     * given file task spec is not supportd by this service.
     * @param spec The file task spec to copy
     * @return The new file task spec
     * @throws FileTaskException
     */
    public FileTaskSpec createFileTaskSpec(FileTaskSpec spec)
            throws FileTaskException;

    /**
     * Submits the file task described by the given file task spec. Throws an
     * exception if the file task type of given file task spec is not supportd
     * by this service or if an error occurs while submiting the file task.
     * @param spec The file task spec.
     * @return The file task
     * @throws FileTaskException
     */
    public FileTask submitFileTask(FileTaskSpec spec)
            throws FileTaskException;

    /**
     * Cancels the given file task. Throws an exception if an error occurs
     * while attempting to cancel the file task.
     * @param task The file task to cancel
     * @throws FileTaskException
     */
    public void cancelFileTask(FileTask task)
            throws FileTaskException;

    /**
     * Returns all the file tasks of the given file task type for which records exist .
     * If the file task type is null, returns all file tasks for which records exist.
     * @param type The file task type
     * @return The list of file tasks
     * @see FileTask
     */
    public List getFileTasks(FileTaskType type);

    /**
     * Returns all the file tasks of the given file task type owned by the given user for
     * which records exist. If file task type is null, returns ll file task records
     * associated with the given user.
     * @param user The owner of the file tasks
     * @param type The file task type
     * @return The list of file tasks
     * @see FileTask
     */
    public List getFileTasks(User user, FileTaskType type);

    /**
     * Returns the file task with the given object id.
     * @param oid The object id
     * @return The file task
     */
    public FileTask getFileTask(String oid);

    /**
     * Deletes the given file task.
     * @param task The file task
     */
    public void deleteFileTask(FileTask task);
}

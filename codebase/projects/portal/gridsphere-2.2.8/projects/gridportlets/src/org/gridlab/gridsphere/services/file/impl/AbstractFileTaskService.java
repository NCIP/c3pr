/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: AbstractFileTaskService.java,v 1.1.1.1 2007-02-01 20:40:06 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.impl;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.task.Task;
import org.gridlab.gridsphere.services.task.TaskException;
import org.gridlab.gridsphere.services.task.TaskSpec;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.task.impl.AbstractTaskSubmissionService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;

import java.util.List;
import java.net.InetAddress;

/**
 * Provides an abstract base implementation of a file task service. It extends
 * abstract task submission service so that it's functionality is incorporated
 * into the task submission system.
 */
public abstract class AbstractFileTaskService
        extends AbstractTaskSubmissionService
        implements FileTaskService {

    protected static PortletLog log = SportletLog.getInstance(AbstractFileTaskService.class);

    public List getTaskTypes() {
        return getFileTaskTypes();
    }

    public TaskSpec createTaskSpec(TaskType type) throws TaskException {
        return createFileTaskSpec((FileTaskType)type);
    }

    public TaskSpec createTaskSpec(TaskSpec spec) throws TaskException {
        return createFileTaskSpec((FileTaskSpec) spec);
    }

    public Task submitTask(TaskSpec spec) throws TaskException {
        return submitFileTask((FileTaskSpec) spec);
    }

    public void cancelTask(Task request) throws TaskException {
        cancelFileTask((FileTask) request);
    }

    public abstract List getFileTaskTypes();

    public abstract FileTaskSpec createFileTaskSpec(FileTaskType type) throws FileTaskException;

    public abstract FileTaskSpec createFileTaskSpec(FileTaskSpec spec) throws FileTaskException;

    public abstract FileTask submitFileTask(FileTaskSpec spec) throws FileTaskException;

    public abstract void cancelFileTask(FileTask op) throws FileTaskException;

    public List getFileTasks(FileTaskType type) {
        return getTasks(type);
    }

    public List getFileTasks(User user, FileTaskType type) {
        return getTasks(user, type);
    }

    public FileTask getFileTask(String id) {
        return (FileTask)getTask(id);
    }

    public void deleteFileTask(FileTask task) {
        deleteTask(task);
    }
}

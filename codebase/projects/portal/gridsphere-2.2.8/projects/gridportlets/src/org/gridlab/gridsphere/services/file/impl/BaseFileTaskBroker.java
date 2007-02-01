/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileTaskBroker.java,v 1.1.1.1 2007-02-01 20:40:06 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.impl;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.task.TaskException;
import org.gridlab.gridsphere.services.task.impl.SimpleTaskSubmissionBroker;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Vector;

/**
 * Implements the file task service as a "broker" for other file task services.
 * This service will invoke the appropriate file task service depending upon
 * the type of file tasks passed to its methods.
 */
public class BaseFileTaskBroker
        extends SimpleTaskSubmissionBroker
        implements FileTaskService {
    protected static PortletLog log = SportletLog.getInstance(BaseFileTaskBroker.class);

    public List getTaskTypes() {
        List types = new Vector(10);
        types.add(FileTaskType.INSTANCE);
        return types;
    }

    public List getFileTaskTypes() {
        return getBrokeredTaskTypes();
    }

    public FileTaskSpec createFileTaskSpec(FileTaskType type) throws FileTaskException {
        try {
            return (FileTaskSpec) createTaskSpec(type);
        } catch (TaskException e) {
            throw new FileTaskException(e.getMessage());
        }
    }

    public FileTaskSpec createFileTaskSpec(FileTaskSpec spec) throws FileTaskException {
        try {
            return (FileTaskSpec) createTaskSpec(spec);
        } catch (TaskException e) {
            throw new FileTaskException(e.getMessage());
        }
    }

    public FileTask submitFileTask(FileTaskSpec spec) throws FileTaskException {
        try {
            return (FileTask) submitTask(spec);
        } catch (TaskException e) {
            throw new FileTaskException(e.getMessage());
        }
    }

    public void cancelFileTask(FileTask op) throws FileTaskException {
        try {
            cancelTask(op);
        } catch (TaskException e) {
            throw new FileTaskException(e.getMessage());
        }
    }

    public List getFileTasks(FileTaskType type) {
        return getTasks(type);
    }

    public List getFileTasks(User user, FileTaskType type) {
        return getTasks(user, type);
    }

    public FileTask getFileTask(String id) {
        return (FileTask)getTask(id);
    }

    public void saveFileTask(FileTask operation) {
        saveTask(operation);
    }

    public void deleteFileTask(FileTask operation) {
        deleteTask(operation);
    }
}

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingServiceImpl.java,v 1.1.1.1 2007-02-01 20:41:45 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.task.impl.AbstractTaskSubmissionService;
import org.gridlab.gridsphere.services.task.TaskSpec;
import org.gridlab.gridsphere.services.task.Task;
import org.gridlab.gridsphere.services.task.TaskException;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingTaskType;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingService;

import java.util.List;
import java.util.Vector;

public class CredentialMappingServiceImpl extends AbstractTaskSubmissionService implements CredentialMappingService {

    public List getTaskTypes() {
        List types = new Vector(1);
        types.add(CredentialMappingTaskType.INSTANCE);
        return types;
    }

    public TaskSpec createTaskSpec(TaskType type) throws TaskException {
        return new CredentialMappingSpecImpl();
    }

    public TaskSpec createTaskSpec(TaskSpec spec) throws TaskException {
        return new CredentialMappingSpecImpl((CredentialMappingSpecImpl)spec);
    }

    public Task submitTask(TaskSpec spec) throws TaskException {
        CredentialMappingTaskImpl task = new CredentialMappingTaskImpl((CredentialMappingSpecImpl)spec);
        task.start();
        return task;
    }

    public void cancelTask(Task task) throws TaskException {
        return;
    }
}

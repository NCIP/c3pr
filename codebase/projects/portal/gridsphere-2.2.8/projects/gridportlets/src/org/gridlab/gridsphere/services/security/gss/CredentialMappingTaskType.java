/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTaskType.java,v 1.1.1.1 2007-02-01 20:41:30 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.security.gss.impl.CredentialMappingTaskImpl;
import org.gridlab.gridsphere.services.security.gss.impl.CredentialMappingSpecImpl;


public class CredentialMappingTaskType extends TaskType {

    public static final CredentialMappingTaskType INSTANCE =
            new CredentialMappingTaskType(CredentialMappingTask.class, CredentialMappingSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected CredentialMappingTaskType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

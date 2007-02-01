/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.logical;


/**
 * Describes a type of replicate file task
 */
public class ReplicateFileTaskType extends LogicalFileTaskType {

    public static final ReplicateFileTaskType INSTANCE
            = new ReplicateFileTaskType(ReplicateFileTask.class, ReplicateFileTaskSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected ReplicateFileTaskType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

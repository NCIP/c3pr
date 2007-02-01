/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.logical;


/**
 * Describes a type of make logical file task
 */
public class MakeLogicalFileTaskType extends LogicalFileTaskType {

    public static final MakeLogicalFileTaskType INSTANCE
            = new MakeLogicalFileTaskType(MakeLogicalFileTask.class, MakeLogicalFileTaskSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected MakeLogicalFileTaskType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

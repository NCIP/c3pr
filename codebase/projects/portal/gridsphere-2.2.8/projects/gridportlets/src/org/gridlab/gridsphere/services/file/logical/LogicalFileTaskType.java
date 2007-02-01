/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.logical;

import org.gridlab.gridsphere.services.file.tasks.FileBrowserTaskType;

/**
 * Describes a type of logical file task
 */
public class LogicalFileTaskType extends FileBrowserTaskType {

    public static final LogicalFileTaskType INSTANCE
            = new LogicalFileTaskType(LogicalFileTask.class, LogicalFileTaskSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected LogicalFileTaskType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

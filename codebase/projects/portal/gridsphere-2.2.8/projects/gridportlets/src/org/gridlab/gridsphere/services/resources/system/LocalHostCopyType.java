/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.FileCopyType;

/**
 * Describes a type of gridlab logical file copy task.
 */
public class LocalHostCopyType extends FileCopyType {

    public static final LocalHostCopyType INSTANCE
            = new LocalHostCopyType(LocalHostCopy.class, LocalHostCopySpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected LocalHostCopyType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

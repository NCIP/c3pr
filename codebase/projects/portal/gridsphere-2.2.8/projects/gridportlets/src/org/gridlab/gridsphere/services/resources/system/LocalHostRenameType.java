/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.FileTaskType;

/**
 * Describes a type of gridlab logical rename task.
 */
public class LocalHostRenameType extends FileTaskType {

    public static final LocalHostRenameType INSTANCE
            = new LocalHostRenameType(LocalHostRename.class, LocalHostRenameSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected LocalHostRenameType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

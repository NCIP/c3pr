/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.FileMakeDirType;

/**
 * Describes a type of gridlab logical make directory task.
 */
public class LocalHostMakeDirType extends FileMakeDirType {

    public static final LocalHostMakeDirType INSTANCE
            = new LocalHostMakeDirType(LocalHostMakeDir.class, LocalHostMakeDirSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected LocalHostMakeDirType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

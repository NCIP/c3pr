/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.FileDeletionType;

/**
 * Describes a type of gridlab logical file deletion task.
 */
public class LocalHostDeleteType extends FileDeletionType {

    public static final LocalHostDeleteType INSTANCE
            = new LocalHostDeleteType(LocalHostDelete.class, LocalHostDeleteSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected LocalHostDeleteType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

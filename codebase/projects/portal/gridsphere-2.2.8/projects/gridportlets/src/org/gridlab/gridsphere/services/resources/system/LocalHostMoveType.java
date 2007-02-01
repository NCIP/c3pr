/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.FileMoveType;

/**
 * Describes a type of gridlab logical file move task.
 */
public class LocalHostMoveType extends FileMoveType  {

    public static final LocalHostMoveType INSTANCE
            = new LocalHostMoveType(LocalHostMove.class, LocalHostMoveSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected LocalHostMoveType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

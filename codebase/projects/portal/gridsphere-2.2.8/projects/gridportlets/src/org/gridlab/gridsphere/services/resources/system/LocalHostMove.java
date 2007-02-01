/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileMove;
import org.gridlab.gridsphere.services.file.tasks.FileMoveSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical file move task.
 */
public class LocalHostMove extends BaseFileMove {

    /**
     * Default constructor
     */
    public LocalHostMove() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostMove(FileMoveSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return LocalHostMoveType.INSTANCE;
    }
}

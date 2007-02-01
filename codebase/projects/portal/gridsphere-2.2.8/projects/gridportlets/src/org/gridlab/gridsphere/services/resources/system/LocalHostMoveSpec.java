/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileMoveSpec;
import org.gridlab.gridsphere.services.file.tasks.FileMoveSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical file move specification.
 */
public class LocalHostMoveSpec extends BaseFileMoveSpec {

    /**
     * Default constructor
     */
    public LocalHostMoveSpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostMoveSpec(FileMoveSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return LocalHostMoveType.INSTANCE;
    }
}

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileDeletion;
import org.gridlab.gridsphere.services.file.tasks.FileDeletionSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical file deletion task.
 */
public class LocalHostDelete extends BaseFileDeletion {

    /**
     * Default constructor
     */
    public LocalHostDelete() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostDelete(FileDeletionSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return LocalHostDeleteType.INSTANCE;
    }
}

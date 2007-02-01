/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileNameChange;
import org.gridlab.gridsphere.services.file.tasks.FileNameChangeSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical file rename task.
 */
public class LocalHostRename extends BaseFileNameChange {

    /**
     * Default constructor
     */
    public LocalHostRename() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostRename(FileNameChangeSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return LocalHostRenameType.INSTANCE;
    }
}

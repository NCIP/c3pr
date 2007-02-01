/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileNameChangeSpec;
import org.gridlab.gridsphere.services.file.tasks.FileNameChangeSpec;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical file rename specification.
 */
public class LocalHostRenameSpec extends BaseFileNameChangeSpec {

    /**
     * Default constructor
     */
    public LocalHostRenameSpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostRenameSpec(FileNameChangeSpec spec) {
        super(spec);
    }

    public TaskType getTaskType() {
        return LocalHostRenameType.INSTANCE;
    }

    public FileTaskType getFileTaskType() {
        return LocalHostRenameType.INSTANCE;
    }
}

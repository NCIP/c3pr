/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileCopy;
import org.gridlab.gridsphere.services.file.tasks.FileCopySpec;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical file copy task.
 */
public class LocalHostCopy extends BaseFileCopy {

    /**
     * Default constructor
     */
    public LocalHostCopy() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostCopy(FileCopySpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return LocalHostCopyType.INSTANCE;
    }
}

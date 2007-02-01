/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileUploadSpec;
import org.gridlab.gridsphere.services.file.tasks.FileUploadSpec;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.resources.system.LocalHostMoveType;
import org.gridlab.gridsphere.services.resources.system.LocalHostUploadType;

/**
 * Implements the gridlab logical file move specification.
 */
public class LocalHostUploadSpec extends BaseFileUploadSpec {

    /**
     * Default constructor
     */
    public LocalHostUploadSpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostUploadSpec(FileUploadSpec spec) {
        super(spec);
    }

    public TaskType getTaskType() {
        return LocalHostUploadType.INSTANCE;
    }

    public FileTaskType getFileTaskType() {
        return LocalHostUploadType.INSTANCE;
    }
}

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileUpload;
import org.gridlab.gridsphere.services.file.tasks.FileUploadSpec;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.resources.system.LocalHostUploadType;

/**
 * Implements the gridlab logical file copy task.
 */
public class LocalHostUpload extends BaseFileUpload {

    /**
     * Default constructor
     */
    public LocalHostUpload() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostUpload(FileUploadSpec spec) {
        super(spec);
    }

}

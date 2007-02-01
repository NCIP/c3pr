/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileUploadSpec;
import org.gridlab.gridsphere.services.file.tasks.FileUploadSpec;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.resources.system.LocalHostUploadType;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpUploadType;

/**
 * Implements the grid ftp upload specification.
 */
public class GridFtpUploadSpec
        extends BaseFileUploadSpec {

    /**
     * Default constructor
     */
    public GridFtpUploadSpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpUploadSpec(FileUploadSpec spec) {
        super(spec);
    }

    public TaskType getTaskType() {
        return GridFtpUploadType.INSTANCE;
    }
}

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileDownloadSpec;
import org.gridlab.gridsphere.services.file.tasks.FileDownloadSpec;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpDownloadType;

/**
 * Implements the grid ftp download specification.
 */
public class GridFtpDownloadSpec
        extends BaseFileDownloadSpec {

    /**
     * Default constructor
     */
    public GridFtpDownloadSpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpDownloadSpec(FileDownloadSpec spec) {
        super(spec);
    }

    public TaskType getTaskType() {
        return GridFtpDownloadType.INSTANCE;
    }
}

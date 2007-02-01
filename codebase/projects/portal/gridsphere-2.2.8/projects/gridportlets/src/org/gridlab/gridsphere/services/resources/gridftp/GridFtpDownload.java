/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileDownload;
import org.gridlab.gridsphere.services.file.tasks.FileDownloadSpec;

/**
 * Implements the grid ftp download task.
 */
public class GridFtpDownload
        extends BaseFileDownload {

    /**
     * Default constructor
     */
    public GridFtpDownload() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpDownload(FileDownloadSpec spec) {
        super(spec);
    }

}

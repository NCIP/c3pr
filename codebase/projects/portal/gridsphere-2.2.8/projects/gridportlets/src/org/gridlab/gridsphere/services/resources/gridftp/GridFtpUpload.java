/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileUpload;
import org.gridlab.gridsphere.services.file.tasks.FileUploadSpec;

/**
 * Implements the grid ftp upload task.
 */
public class GridFtpUpload
        extends BaseFileUpload {

    /**
     * Default constructor
     */
    public GridFtpUpload() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpUpload(FileUploadSpec spec) {
        super(spec);
    }

}

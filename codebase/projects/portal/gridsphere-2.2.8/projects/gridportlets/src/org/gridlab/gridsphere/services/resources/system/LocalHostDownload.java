/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileDownload;
import org.gridlab.gridsphere.services.file.tasks.FileDownloadSpec;

/**
 * Implements the gridlab logical file copy task.
 */
public class LocalHostDownload extends BaseFileDownload {

    /**
     * Default constructor
     */
    public LocalHostDownload() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostDownload(FileDownloadSpec spec) {
        super(spec);
    }

}

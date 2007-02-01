/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileDownload.java,v 1.1.1.1 2007-02-01 20:40:37 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileDownload;
import org.gridlab.gridsphere.services.file.tasks.FileDownloadSpec;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.resource.RegistryUtil;

/**
 * Provides a base implementation for file transfer tasks.
 */
public abstract class BaseFileDownload
        extends BaseFileBrowserTask
        implements FileDownload {


    /**
     * Default constructor
     */
    public BaseFileDownload() {
        super();
    }

    /**
     * Constructs a base file transfer task containing the
     * given file transfer spec.
     * @param spec The file transfer spec.
     */
    public BaseFileDownload(FileDownloadSpec spec) {
        super(spec);
        setFileResource(spec.getSrcResource());
    }

    public FileResource getSrcResource() {
        return getFileResource();
    }
}

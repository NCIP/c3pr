/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileUpload.java,v 1.1.1.1 2007-02-01 20:40:38 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileUpload;
import org.gridlab.gridsphere.services.file.tasks.FileUploadSpec;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.resource.RegistryUtil;

/**
 * Provides a base implementation for file transfer tasks.
 */
public abstract class BaseFileUpload
        extends BaseFileBrowserTask
        implements FileUpload {

    /**
     * Default constructor
     */
    public BaseFileUpload() {
        super();
    }

    /**
     * Constructs a base file transfer task containing the
     * given file transfer spec.
     * @param spec The file transfer spec.
     */
    public BaseFileUpload(FileUploadSpec spec) {
        super(spec);
        setFileResource(spec.getDstResource());
    }

    public FileResource getDstResource() {
        return getFileResource();
    }
}

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileTransfer.java,v 1.1.1.1 2007-02-01 20:40:38 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileTransferSpec;
import org.gridlab.gridsphere.services.file.tasks.FileTransfer;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.resource.RegistryUtil;

/**
 * Provides a base implementation for file transfer tasks.
 */
public abstract class BaseFileTransfer
        extends BaseFileBrowserTask
        implements FileTransfer {

    private FileResource dstResource = null;
    private String dstResourceOid = null;


    /**
     * Default constructor
     */
    public BaseFileTransfer() {
        super();
    }

    /**
     * Constructs a base file transfer task containing the
     * given file transfer spec.
     * @param spec The file transfer spec.
     */
    public BaseFileTransfer(FileTransferSpec spec) {
        super(spec);
        setDstResource(spec.getDstResource());
    }

    public FileResource getSrcResource() {
        return getFileResource();
    }

    public FileResource getDstResource() {
        if (dstResource == null && dstResourceOid != null) {
            dstResource = (FileResource)RegistryUtil.getResource(getUser(), dstResourceOid);
        }
        return dstResource;
    }

    /**
     * Sets the destination resource used for this file transfer,
     * @param fileResource The destination resource
     */
    public void setDstResource(FileResource fileResource) {
        this.dstResource = fileResource;
        if (fileResource != null) {
            dstResourceOid = fileResource.getOid();
        }
    }

    /**
     * Returns the destination resource object id.
     * Used by persistence manager.
     * @return  The object id
     */
    public String getDstResourceOid() {
        return dstResourceOid;
    }

    /**
     * Sets the destination resource object id.
     * Used by persistence manager.
     * @param oid The object id
     */
    public void setDstResourceOid(String oid) {
        dstResourceOid = oid;
    }
}

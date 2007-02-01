/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileTransferSpec.java,v 1.1.1.1 2007-02-01 20:40:38 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.file.impl.PersistedFileSet;
import org.gridlab.gridsphere.services.file.tasks.FileTransferSpec;
import org.gridlab.gridsphere.services.file.tasks.FileTransferType;
import org.gridlab.gridsphere.services.resource.RegistryUtil;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for file transfer specifications.
 */
public class BaseFileTransferSpec
        extends BaseFileBrowserTaskSpec
        implements FileTransferSpec {

    protected FileSet fileSet = null;
    protected PersistedFileSet persistedFileSet = null;
    protected FileLocation destination = null;
    protected FileResource dstResource = null;
    protected String dstResourceOid = null;

    /**
     * Default constructor
     */
    public BaseFileTransferSpec() {
        super();
    }

    /**
     * Constructs a base file transfer spec with values obtained
     * from the given file transfer spec.
     * @param spec
     */
    public BaseFileTransferSpec(FileTransferSpec spec) {
        super(spec);
        setFileSet(spec.getFileSet());
        setDestination(spec.getDestination());
        setDstResource(spec.getDstResource());
    }

    public TaskType getTaskType() {
        return FileTransferType.INSTANCE;
    }

    public FileSet getFileSet() {
        return fileSet;
    }

    public void setFileSet(FileSet fileSet) {
        this.fileSet = fileSet;
    }

    public FileLocation getDestination() {
        return destination;
    }

    public void setDestination(FileLocation destination) {
        this.destination = destination;
    }

    public FileResource getSrcResource() {
        return getFileResource();
    }

    public void setSrcResource(FileResource fileResource) {
        setFileResource(fileResource);
    }

    public FileResource getDstResource() {
        if (dstResource == null && dstResourceOid != null) {
            dstResource = (FileResource)RegistryUtil.getResource(getUser(), dstResourceOid);
        }
        return dstResource;
    }

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

    public PersistedFileSet getPersistedFileSet() {
        if (persistedFileSet == null) {
            persistedFileSet = PersistedFileSet.createPersistedFileSet(fileSet);
        } else {
            persistedFileSet.fromFileSet(fileSet);
        }
        return persistedFileSet;
    }

    public void setPersistedFileSet(PersistedFileSet persistedFileSet) {
        if (persistedFileSet != null) {
            if (fileSet == null) {
                fileSet = persistedFileSet.toFileSet();
            } else {
                persistedFileSet.updateFileSet(fileSet);
            }
        }
        this.persistedFileSet = persistedFileSet;
    }

    public Object clone() {
        BaseFileTransferSpec spec = new BaseFileTransferSpec(this);
        return spec;
    }
}

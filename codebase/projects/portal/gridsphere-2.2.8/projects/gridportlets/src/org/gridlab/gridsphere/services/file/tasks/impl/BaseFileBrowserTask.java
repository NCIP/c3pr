/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.*;
import org.gridlab.gridsphere.services.file.impl.BaseFileTask;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.resource.RegistryUtil;


/**
 * Provides a base implmentation for file browser tasks.
 */
public class BaseFileBrowserTask
        extends BaseFileTask
        implements FileBrowserTask {

    protected FileResource fileResource = null;
    protected String fileResourceOid = null;

    /**
     * Default constructor
     */
    public BaseFileBrowserTask() {
        super();
    }

    /**
     * Constructs a base file browser task containing the
     * given base file browser task spec
     * @param spec The file browser task spec
     */
    public BaseFileBrowserTask(FileBrowserTaskSpec spec) {
        super(spec);
        setFileResource(spec.getFileResource());
    }

    public FileResource getFileResource() {
        if (fileResource == null && fileResourceOid != null) {
            fileResource = (FileResource)RegistryUtil.getResource(getUser(), fileResourceOid);
        }
        return fileResource;
    }

    /**
     * Sets the file resource used by this task.
     * @param fileResource The file resource
     */
    public void setFileResource(FileResource fileResource) {
        this.fileResource = fileResource;
        if (fileResource != null) {
            fileResourceOid = fileResource.getOid();
        }
    }

    /**
     * Returns the file resource object id
     * Used by persistence manager.
     * @return  The object id
     */
    public String getFileResourceOid() {
        return fileResourceOid;
    }

    /**
     * Sets the file resource object id
     * Used by persistence manager.
     * @param oid The object id
     */
    public void setFileResourceOid(String oid) {
        fileResourceOid = oid;
    }
}

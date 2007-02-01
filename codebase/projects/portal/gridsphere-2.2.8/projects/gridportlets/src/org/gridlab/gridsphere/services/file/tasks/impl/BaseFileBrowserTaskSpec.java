/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileBrowserTaskSpec.java,v 1.1.1.1 2007-02-01 20:40:37 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.impl.BaseFileTaskSpec;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserTaskSpec;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserTaskType;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.resource.RegistryUtil;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for file browser task specifications.
 */
public class BaseFileBrowserTaskSpec
        extends BaseFileTaskSpec implements FileBrowserTaskSpec {

    protected FileResource fileResource = null;
    protected String fileResourceOid = null;

    /**
     * Default constructor
     */
    public BaseFileBrowserTaskSpec() {
        super();
    }

    /**
     * Constructs a base file browser spec with values obtained
     * from the given browser spec.
     * @param spec The file browser task spec
     */
    public BaseFileBrowserTaskSpec(FileBrowserTaskSpec spec) {
        super(spec);
        setFileResource(spec.getFileResource());
    }

    public FileResource getFileResource() {
        if (fileResource == null && fileResourceOid != null) {
            fileResource = (FileResource)RegistryUtil.getResource(getUser(), fileResourceOid);
        }
        return fileResource;
    }

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

    public Object clone() {
        BaseFileBrowserTaskSpec spec = new BaseFileBrowserTaskSpec(this);
        return spec;
    }
}

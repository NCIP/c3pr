/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileUploadSpec.java,v 1.1.1.1 2007-02-01 20:40:38 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.file.tasks.FileUploadSpec;
import org.gridlab.gridsphere.services.file.tasks.FileUploadType;
import org.gridlab.gridsphere.services.resource.RegistryUtil;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for file transfer specifications.
 */
public class BaseFileUploadSpec
        extends BaseFileBrowserTaskSpec
        implements FileUploadSpec {

    private String filePath = null;
    private FileLocation uploadLocation = null;

    /**
     * Default constructor
     */
    public BaseFileUploadSpec() {
        super();
    }

    /**
     * Constructs a base file upload spec with values obtained from the given file upload spec.
     * @param spec The file upload spec
     */
    public BaseFileUploadSpec(FileUploadSpec spec) {
        super(spec);
        setFilePath(spec.getFilePath());
        setUploadLocation(spec.getUploadLocation());
        setDstResource(spec.getDstResource());
    }

    public TaskType getTaskType() {
        return FileUploadType.INSTANCE;
    }

    public String getFilePath() {
        return filePath;

    }

    public void setFilePath(String localFilePath) {
        this.filePath = localFilePath;
    }

    public FileLocation getUploadLocation() {
        return uploadLocation;
    }

    public void setUploadLocation(FileLocation destination) {
        this.uploadLocation = destination;
    }

    public FileResource getDstResource() {
        return getFileResource();
    }

    public void setDstResource(FileResource fileResource) {
        setFileResource(fileResource);
    }


    public Object clone() {
        BaseFileUploadSpec spec = new BaseFileUploadSpec(this);
        return spec;
    }
}

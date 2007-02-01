/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileDownloadSpec.java,v 1.1.1.1 2007-02-01 20:40:37 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.tasks.FileDownloadSpec;
import org.gridlab.gridsphere.services.file.tasks.FileDownloadType;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for file transfer specifications.
 */
public class BaseFileDownloadSpec
        extends BaseFileBrowserTaskSpec
        implements FileDownloadSpec {

    private FileLocation fileLocation = null;
    private String downloadPath = null;

    /**
     * Default constructor
     */
    public BaseFileDownloadSpec() {
        super();
    }

    /**
     * Constructs a base file transfer spec with values obtained
     * from the given file transfer spec.
     * @param spec
     */
    public BaseFileDownloadSpec(FileDownloadSpec spec) {
        super(spec);
        setDownloadPath(spec.getDownloadPath());
        setFileLocation(spec.getFileLocation());
        setSrcResource(spec.getSrcResource());
    }

    public TaskType getTaskType() {
        return FileDownloadType.INSTANCE;
    }

    public String getDownloadPath() {
        return downloadPath;

    }

    public void setDownloadPath(String localFilePath) {
        this.downloadPath = localFilePath;
    }

    public FileLocation getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(FileLocation source) {
        this.fileLocation = (FileLocation)source;
    }

    public FileResource getSrcResource() {
        return getFileResource();
    }

    public void setSrcResource(FileResource fileResource) {
        setFileResource(fileResource);
    }

    public Object clone() {
        BaseFileDownloadSpec spec = new BaseFileDownloadSpec(this);
        return spec;
    }
}

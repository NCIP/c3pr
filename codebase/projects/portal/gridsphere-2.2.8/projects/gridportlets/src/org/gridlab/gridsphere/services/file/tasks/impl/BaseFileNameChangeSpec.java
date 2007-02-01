/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.file.tasks.FileNameChangeSpec;
import org.gridlab.gridsphere.services.file.tasks.FileNameChangeType;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for file name change specifications.
 */
public class BaseFileNameChangeSpec
        extends BaseFileBrowserTaskSpec
        implements FileNameChangeSpec {

    private FileLocation fileLocation = null;
    private String newFileName = null;

    /**
     * Default constructor
     */
    public BaseFileNameChangeSpec() {
        super();
        // Delete record when done by default
        setDeleteWhenTaskEndsFlag(true);
    }

    /**
     * Constructs a base file name change spec with values obtained
     * from the given file name change spec.
     * @param spec The file name spec
     */
    public BaseFileNameChangeSpec(FileNameChangeSpec spec) {
        super(spec);
        setFileLocation(spec.getFileLocation());
        setNewFileName(spec.getNewFileName());
    }

    public TaskType getTaskType() {
        return FileNameChangeType.INSTANCE;
    }

    public FileTaskType getFileTaskType() {
        return FileNameChangeType.INSTANCE;
    }

    public FileLocation getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(FileLocation location) {
        fileLocation = location;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String name) {
        newFileName = name;
    }

    public Object clone() {
        BaseFileNameChangeSpec spec = new BaseFileNameChangeSpec(this);
        return spec;
    }
}

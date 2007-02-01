/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */

package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileMakeDirSpec;
import org.gridlab.gridsphere.services.file.tasks.FileMakeDirType;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for make directory specifications.
 */
public class BaseFileMakeDirSpec
        extends BaseFileBrowserTaskSpec
        implements FileMakeDirSpec {

    private FileLocation parentLocation = null;
    private String directoryName = null;

    /**
     * Default constructor
     */
    public BaseFileMakeDirSpec() {
        super();
        // Delete record when done by default
        setDeleteWhenTaskEndsFlag(true);
    }

    /**
     * Constructs a base make directory spec with values obtained
     * from the given make directory spec.
     * @param spec The make directory spec
     */
    public BaseFileMakeDirSpec(FileMakeDirSpec spec) {
        super(spec);
        parentLocation = (FileLocation)spec.getParentLocation();
        directoryName = spec.getDirectoryName();
    }

    public TaskType getTaskType() {
        return FileMakeDirType.INSTANCE;
    }

    public FileLocation getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(FileLocation directory) {
        parentLocation = (FileLocation)directory;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String dirName) {
        this.directoryName = dirName;
    }

    public Object clone() {
        BaseFileMakeDirSpec spec = new BaseFileMakeDirSpec(this);
        return spec;
    }
}

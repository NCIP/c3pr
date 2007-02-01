/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileListingSpec.java,v 1.1.1.1 2007-02-01 20:40:37 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.file.impl.PersistedFileSet;
import org.gridlab.gridsphere.services.file.tasks.FileListingSpec;
import org.gridlab.gridsphere.services.file.tasks.FileListingType;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for file listing specifications.
 */
public class BaseFileListingSpec
        extends BaseFileBrowserTaskSpec
        implements FileListingSpec {

    protected boolean browserFlag = false;
    protected FileSet fileSet = null;
    protected PersistedFileSet persistedFileSet = null;

    /**
     * Default constructor
     */
    public BaseFileListingSpec() {
        super();
        // Delete record when done by default
        setDeleteWhenTaskEndsFlag(true);
    }

    /**
     * Constructs a base file listing spec with values obtained
     * from the given file listing spec.
     * @param spec The file listing spec
     */
    public BaseFileListingSpec(FileListingSpec spec) {
        super(spec);
        browserFlag = spec.getBrowserFlag();
        fileSet = spec.getFileSet();
    }

    public TaskType getTaskType() {
        return FileListingType.INSTANCE;
    }

    public boolean getBrowserFlag() {
        return browserFlag;
    }

    public void setBrowserFlag(boolean flag) {
        browserFlag = flag;
    }

    public FileSet getFileSet() {
        return fileSet;
    }

    public void setFileSet(FileSet fileSet) {
        this.fileSet = fileSet;
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
        BaseFileListingSpec spec = new BaseFileListingSpec(this);
        return spec;
    }
}

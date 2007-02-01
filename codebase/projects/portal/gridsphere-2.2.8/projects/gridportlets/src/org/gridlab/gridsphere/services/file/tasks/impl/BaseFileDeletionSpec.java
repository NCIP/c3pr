/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileDeletionSpec.java,v 1.1.1.1 2007-02-01 20:40:37 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.file.impl.PersistedFileSet;
import org.gridlab.gridsphere.services.file.tasks.FileDeletionSpec;
import org.gridlab.gridsphere.services.file.tasks.FileDeletionType;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for file deletion specifications.
 */
public class BaseFileDeletionSpec
        extends BaseFileBrowserTaskSpec
        implements FileDeletionSpec {

    protected FileSet fileSet = null;
    protected PersistedFileSet persistedFileSet = null;

    /**
     * Default constructor
     */
    public BaseFileDeletionSpec() {
        super();
        // Delete record when done by default
        setDeleteWhenTaskEndsFlag(true);
    }

    /**
     * Constructs a base file copy spec with values obtained
     * from the given file copy spec.
     * @param spec The file deletion spec
     */
    public BaseFileDeletionSpec(FileDeletionSpec spec) {
        super(spec);
        fileSet = spec.getFileSet();
    }

    public TaskType getTaskType() {
        return FileDeletionType.INSTANCE;
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
        BaseFileDeletionSpec spec = new BaseFileDeletionSpec(this);
        return spec;
    }
}

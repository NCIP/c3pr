/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileMoveSpec;
import org.gridlab.gridsphere.services.file.tasks.FileMoveType;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for file move specifications.
 */
public class BaseFileMoveSpec
        extends BaseFileTransferSpec
        implements FileMoveSpec {

    /**
     * Default constructor
     */
    public BaseFileMoveSpec() {
        super();
    }

    /**
     * Constructs a base file move spec with values obtained
     * from the given file move spec.
     * @param spec The file move spec
     */
    public BaseFileMoveSpec(FileMoveSpec spec) {
        super(spec);
    }

    public TaskType getTaskType() {
        return FileMoveType.INSTANCE;
    }

    public Object clone() {
        BaseFileMoveSpec spec = new BaseFileMoveSpec(this);
        return spec;
    }
}

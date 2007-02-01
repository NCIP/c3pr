/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileCopySpec.java,v 1.1.1.1 2007-02-01 20:40:37 kherm Exp ${VERSION} Nov 26, 2003 10:10:48 PM russell $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileCopySpec;
import org.gridlab.gridsphere.services.file.tasks.FileCopyType;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for file copy task specifications.
 */
public class BaseFileCopySpec
        extends BaseFileTransferSpec
        implements FileCopySpec {

    /**
     * Default constructor
     */
    public BaseFileCopySpec() {
        super();
    }

    /**
     * Constructs a base file copy spec with values obtained
     * from the given file copy spec.
     * @param spec The file copy spec
     */
    public BaseFileCopySpec(FileCopySpec spec) {
        super(spec);
    }

    public TaskType getTaskType() {
        return FileCopyType.INSTANCE;
    }

    public Object clone() {
        BaseFileCopySpec spec = new BaseFileCopySpec(this);
        return spec;
    }
}

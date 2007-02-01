/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseLogicalFileTaskSpec.java,v 1.1.1.1 2007-02-01 20:40:21 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.logical.impl;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowserTaskSpec;
import org.gridlab.gridsphere.services.file.logical.LogicalFileTaskSpec;
import org.gridlab.gridsphere.services.file.logical.LogicalFileTaskType;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Base implementation for logical file task specifications.
 */
public class BaseLogicalFileTaskSpec extends BaseFileBrowserTaskSpec {

    /**
     * Default constructor
     */
    public BaseLogicalFileTaskSpec() {
        super();
    }

    /**
     * Constructs a base logical file task containing the
     * given logical file task spec
     * @param spec The logical file task spec
     */
    public BaseLogicalFileTaskSpec(LogicalFileTaskSpec spec) {
        super(spec);
    }

    public TaskType getTaskType() {
        return LogicalFileTaskType.INSTANCE;
    }
}

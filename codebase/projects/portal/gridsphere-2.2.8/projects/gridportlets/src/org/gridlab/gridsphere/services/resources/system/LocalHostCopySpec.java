/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileCopySpec;
import org.gridlab.gridsphere.services.file.tasks.FileCopySpec;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical file copy specification.
 */
public class LocalHostCopySpec extends BaseFileCopySpec {

    /**
     * Default constructor
     */
    public LocalHostCopySpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostCopySpec(FileCopySpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return LocalHostCopyType.INSTANCE;
    }
}

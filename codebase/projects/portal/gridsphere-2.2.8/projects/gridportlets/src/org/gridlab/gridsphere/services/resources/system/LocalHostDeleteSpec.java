/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileDeletionSpec;
import org.gridlab.gridsphere.services.file.tasks.FileDeletionSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical file delete specification.
 */
public class LocalHostDeleteSpec extends BaseFileDeletionSpec {

    /**
     * Default constructor
     */
    public LocalHostDeleteSpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostDeleteSpec(FileDeletionSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return LocalHostDeleteType.INSTANCE;
    }
}

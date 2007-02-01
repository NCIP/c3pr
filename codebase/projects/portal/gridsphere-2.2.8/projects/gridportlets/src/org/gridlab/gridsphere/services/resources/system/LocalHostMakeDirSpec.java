/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileMakeDirSpec;
import org.gridlab.gridsphere.services.file.tasks.FileMakeDirSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical make directory specification.
 */
public class LocalHostMakeDirSpec extends BaseFileMakeDirSpec {

    /**
     * Default constructor
     */
    public LocalHostMakeDirSpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostMakeDirSpec(FileMakeDirSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return LocalHostMakeDirType.INSTANCE;
    }
}

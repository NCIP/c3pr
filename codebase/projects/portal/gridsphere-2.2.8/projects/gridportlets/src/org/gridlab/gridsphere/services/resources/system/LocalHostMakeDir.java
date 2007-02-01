/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileMakeDir;
import org.gridlab.gridsphere.services.file.tasks.FileMakeDirSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical make directory task.
 */
public class LocalHostMakeDir extends BaseFileMakeDir {

    /**
     * Default constructor
     */
    public LocalHostMakeDir() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostMakeDir(FileMakeDirSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return LocalHostMakeDirType.INSTANCE;
    }
}

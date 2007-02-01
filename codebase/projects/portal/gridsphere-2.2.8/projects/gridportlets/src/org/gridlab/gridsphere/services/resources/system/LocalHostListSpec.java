/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileListingSpec;
import org.gridlab.gridsphere.services.file.tasks.FileListingSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Implements the gridlab logical file listing specification.
 */
public class LocalHostListSpec extends BaseFileListingSpec {

    /**
     * Default constructor
     */
    public LocalHostListSpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostListSpec(FileListingSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return LocalHostListType.INSTANCE;
    }
}

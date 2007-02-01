/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileDownloadSpec;
import org.gridlab.gridsphere.services.file.tasks.FileDownloadSpec;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.resources.system.LocalHostDownloadType;

/**
 * Implements the local host down specification.
 */
public class LocalHostDownloadSpec extends BaseFileDownloadSpec {

    /**
     * Default constructor
     */
    public LocalHostDownloadSpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public LocalHostDownloadSpec(FileDownloadSpec spec) {
        super(spec);
    }

    public TaskType getTaskType() {
        return LocalHostDownloadType.INSTANCE;
    }
}

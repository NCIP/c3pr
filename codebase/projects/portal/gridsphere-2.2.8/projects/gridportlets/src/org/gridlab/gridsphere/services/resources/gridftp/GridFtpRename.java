package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileNameChange;
import org.gridlab.gridsphere.services.file.tasks.FileNameChangeSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpRename.java,v 1.1.1.1 2007-02-01 20:41:13 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class GridFtpRename extends BaseFileNameChange {

    /**
     * Default constructor
     */
    public GridFtpRename() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpRename(FileNameChangeSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return GridFtpRenameType.INSTANCE;
    }
}

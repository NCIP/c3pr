package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileMoveSpec;
import org.gridlab.gridsphere.services.file.tasks.FileMoveSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpMoveSpec.java,v 1.1.1.1 2007-02-01 20:41:13 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class GridFtpMoveSpec extends BaseFileMoveSpec {

    /**
     * Default constructor
     */
    public GridFtpMoveSpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpMoveSpec(FileMoveSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return GridFtpMoveType.INSTANCE;
    }
}

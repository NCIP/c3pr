package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileMove;
import org.gridlab.gridsphere.services.file.tasks.FileMoveSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpMove.java,v 1.1.1.1 2007-02-01 20:41:13 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class GridFtpMove extends BaseFileMove {

    /**
     * Default constructor
     */
    public GridFtpMove() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpMove(FileMoveSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return GridFtpMoveType.INSTANCE;
    }
}

package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileDeletion;
import org.gridlab.gridsphere.services.file.tasks.FileDeletionSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpDelete.java,v 1.1.1.1 2007-02-01 20:41:10 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class GridFtpDelete extends BaseFileDeletion {

    /**
     * Default constructor
     */
    public GridFtpDelete() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpDelete(FileDeletionSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return GridFtpDeleteType.INSTANCE;
    }
}

package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileCopy;
import org.gridlab.gridsphere.services.file.tasks.FileCopySpec;
import org.gridlab.gridsphere.services.task.TaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpCopy.java,v 1.1.1.1 2007-02-01 20:41:10 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class GridFtpCopy extends BaseFileCopy {

    /**
     * Default constructor
     */
    public GridFtpCopy() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpCopy(FileCopySpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return GridFtpCopyType.INSTANCE;
    }
}

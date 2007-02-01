package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileMakeDir;
import org.gridlab.gridsphere.services.file.tasks.FileMakeDirSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpMakeDir.java,v 1.1.1.1 2007-02-01 20:41:11 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class GridFtpMakeDir extends BaseFileMakeDir {

    /**
     * Default constructor
     */
    public GridFtpMakeDir() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpMakeDir(FileMakeDirSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return GridFtpMakeDirType.INSTANCE;
    }
}

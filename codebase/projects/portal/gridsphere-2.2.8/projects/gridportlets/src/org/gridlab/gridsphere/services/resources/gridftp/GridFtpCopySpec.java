package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileCopySpec;
import org.gridlab.gridsphere.services.file.tasks.FileCopySpec;
import org.gridlab.gridsphere.services.task.TaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpCopySpec.java,v 1.1.1.1 2007-02-01 20:41:10 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class GridFtpCopySpec extends BaseFileCopySpec {

    /**
     * Default constructor
     */
    public GridFtpCopySpec() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpCopySpec(FileCopySpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return GridFtpCopyType.INSTANCE;
    }
}

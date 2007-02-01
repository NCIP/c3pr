package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileListing;
import org.gridlab.gridsphere.services.file.tasks.FileListingSpec;
import org.gridlab.gridsphere.services.task.TaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpList.java,v 1.1.1.1 2007-02-01 20:41:11 kherm Exp $
 * <p>
 * Specifies a transfer operation.
 */

public class GridFtpList extends BaseFileListing {

    /**
     * Default constructor
     */
    public GridFtpList() {
        super();
    }

    /**
     * Obtains values from given sepc
     * @param spec
     */
    public GridFtpList(FileListingSpec spec) {
        super(spec);
    }

    /**
     * See TaskSpec.
     */
    public TaskType getTaskType() {
        return GridFtpListType.INSTANCE;
    }
}

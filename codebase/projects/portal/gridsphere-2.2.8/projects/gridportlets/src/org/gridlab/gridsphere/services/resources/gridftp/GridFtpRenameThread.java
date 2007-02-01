package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpRename;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpRenameSpec;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpException;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpConnection;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpRenameSpec;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpRenameThread.java,v 1.1.1.1 2007-02-01 20:41:13 kherm Exp $
 * <p>
 * Implements a grid ftp rename thread.
 */

public class GridFtpRenameThread extends GridFtpThread {

    private GridFtpRename task = null;

    public GridFtpRenameThread(GridFtpConnection connection, GridFtpRename task) {
        super(connection);
        this.task = task;
    }

    public void runCommand() {
        try {
            run(task);
            task.setTaskStatus(TaskStatus.COMPLETED);
        } catch (Exception e) {
            task.setTaskStatus(TaskStatus.FAILED, e.getMessage());
        }
    }

    public void run(GridFtpRename task)
            throws GridFtpException {
        GridFtpRenameSpec taskSpec = (GridFtpRenameSpec)task.getTaskSpec();
        FileLocation fileLoc = taskSpec.getFileLocation();
        String fileName = taskSpec.getNewFileName();
        rename(fileLoc, fileName);
    }
}

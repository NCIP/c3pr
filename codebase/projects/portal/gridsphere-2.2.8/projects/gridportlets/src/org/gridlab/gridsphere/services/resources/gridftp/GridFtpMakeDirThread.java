package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpMakeDir;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpMakeDirSpec;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpException;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpConnection;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpMakeDir;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpMakeDirThread.java,v 1.1.1.1 2007-02-01 20:41:12 kherm Exp $
 * <p>
 * Implements a grid ftp make dir thread.
 */

public class GridFtpMakeDirThread extends GridFtpThread {

    private GridFtpMakeDir task = null;

    public GridFtpMakeDirThread(GridFtpConnection connection, GridFtpMakeDir task) {
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

    public void run(GridFtpMakeDir task)
            throws GridFtpException {
        GridFtpMakeDirSpec taskSpec = (GridFtpMakeDirSpec)task.getTaskSpec();
        FileLocation fileLoc = taskSpec.getParentLocation();
        String dirName = taskSpec.getDirectoryName();
        mkdir(fileLoc, dirName);
    }
}

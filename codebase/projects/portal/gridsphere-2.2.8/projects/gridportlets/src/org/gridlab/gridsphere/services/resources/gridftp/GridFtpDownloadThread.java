package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileDownload;
import org.gridlab.gridsphere.services.file.tasks.FileDownloadSpec;
import org.gridlab.gridsphere.services.task.TaskStatus;

import java.io.File;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpDownloadThread.java,v 1.1.1.1 2007-02-01 20:41:11 kherm Exp $
 * <p>
 * Implements a grid ftp transfer thread.
 */

public class GridFtpDownloadThread extends GridFtpThread {

    private BaseFileDownload task = null;

    public GridFtpDownloadThread(GridFtpConnection srcConnection, BaseFileDownload task) {
         super(srcConnection, null);
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

    public void run(BaseFileDownload download)
            throws GridFtpException {
        FileDownloadSpec downloadSpec = (FileDownloadSpec)download.getTaskSpec();
        FileLocation srcLoc = downloadSpec.getFileLocation();
        String dstPath = downloadSpec.getDownloadPath();
        File dstFile = new File(dstPath);
        get(srcLoc, dstFile);
    }
}

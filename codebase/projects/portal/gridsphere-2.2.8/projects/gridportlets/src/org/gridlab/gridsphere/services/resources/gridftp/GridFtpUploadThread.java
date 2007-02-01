package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileUpload;
import org.gridlab.gridsphere.services.file.tasks.FileUploadSpec;
import org.gridlab.gridsphere.services.task.TaskStatus;

import java.io.File;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpUploadThread.java,v 1.1.1.1 2007-02-01 20:41:16 kherm Exp $
 * <p>
 * Implements a grid ftp transfer thread.
 */

public class GridFtpUploadThread extends GridFtpThread {

    private BaseFileUpload task = null;

    public GridFtpUploadThread(GridFtpConnection dstConnection,
                               BaseFileUpload task) {
        super(null, dstConnection);
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

    public void run(BaseFileUpload upload)
            throws GridFtpException {
        FileUploadSpec uploadSpec = (FileUploadSpec)upload.getTaskSpec();
        FileLocation dstLoc = uploadSpec.getUploadLocation();
        String srcLoc = uploadSpec.getFilePath();
        File srcFile = new File(srcLoc);
        put(srcFile, dstLoc);
    }
}

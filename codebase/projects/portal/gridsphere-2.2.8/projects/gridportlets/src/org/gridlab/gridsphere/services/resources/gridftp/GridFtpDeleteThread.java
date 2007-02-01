package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.FileSetType;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.file.sets.FilePatternSet;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpException;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpConnection;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpDelete;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpDeleteSpec;

import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpDeleteThread.java,v 1.1.1.1 2007-02-01 20:41:10 kherm Exp $
 * <p>
 * Implements a grid ftp delete thread.
 */

public class GridFtpDeleteThread extends GridFtpThread {

    private GridFtpDelete task = null;

    public GridFtpDeleteThread(GridFtpConnection connection, GridFtpDelete task) {
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

    public void run(GridFtpDelete task)
            throws GridFtpException {
        log.debug("Running delete task");
        GridFtpDeleteSpec taskSpec = (GridFtpDeleteSpec)task.getTaskSpec();
        FileSet fileSet = taskSpec.getFileSet();
        FileSetType fileSetType = fileSet.getFileSetType();
        if (fileSet instanceof FilePatternSet) {
            log.debug("Running delete task on reg ex file set");
            FilePatternSet filePatternSet = (FilePatternSet)fileSet;
            FileLocation fileLoc = filePatternSet.getFileLocation();
            delete(fileLoc);
        } else if (fileSet instanceof FileLocationSet) {
            log.debug("Running delete task on file location set");
            FileLocationSet fileLocationSet = (FileLocationSet)fileSet;
            List fileLocations = fileLocationSet.getFileLocations();
            delete(fileLocations);
        } else {
            throw new GridFtpException("File set type " + fileSetType.getLabel() + " not supported!");
        }
    }
}

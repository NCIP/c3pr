package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.FileException;
import org.gridlab.gridsphere.services.file.sets.FilePatternSetType;
import org.gridlab.gridsphere.services.file.sets.FilePatternSet;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpList;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpListSpec;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpException;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpConnection;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpList;

import java.util.List;
import java.util.Vector;
import java.util.Iterator;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpListThread.java,v 1.1.1.1 2007-02-01 20:41:11 kherm Exp $
 * <p>
 * Implements a grid ftp list thread.
 */

public class GridFtpListThread extends GridFtpThread {

    private GridFtpList task = null;

    public GridFtpListThread(GridFtpConnection connection) {
        super(connection);
    }

    public GridFtpListThread(GridFtpConnection connection, GridFtpList task) {
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

    public void run(GridFtpList task)
            throws GridFtpException {
        GridFtpListSpec taskSpec = (GridFtpListSpec)task.getTaskSpec();
        FileSet fileSet = taskSpec.getFileSet();
        if (fileSet instanceof FilePatternSet) {
            FilePatternSet filePatternSet = (FilePatternSet)fileSet;
            FileLocation fileLoc = filePatternSet.getFileLocation();
            if (fileLoc == null) {
                throw new GridFtpException("File location must be specified in reg expression file set");
            }
            try {
                List fileLocList = list(fileLoc, taskSpec.getBrowserFlag());
                task.setFileLocations(fileLocList);
            } catch (GridFtpException e) {
                throw new GridFtpException(e.getMessage());
            }
        } else if (fileSet instanceof FileLocationSet) {
            FileLocationSet fileLocSet = (FileLocationSet)fileSet;
            List fileLocList = new Vector();
            Iterator fileLocIter = fileLocSet.getFileLocations().iterator();
            while (fileLocIter.hasNext()) {
                FileLocation fileLoc = (FileLocation)fileLocIter.next();
                try {
                    fileLocList.addAll(list(fileLoc, false));
                } catch (GridFtpException e) {
                    throw new GridFtpException(e.getMessage());
                }
            }
            task.setFileLocations(fileLocList);
        } else {
            throw new GridFtpException("File set not supported " + fileSet.getClass().getName());
        }
    }
}

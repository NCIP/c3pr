package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.resources.system.LocalHostBrowserService;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileTransfer;
import org.gridlab.gridsphere.services.file.tasks.FileTransferSpec;
import org.gridlab.gridsphere.services.file.tasks.FileMove;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.sets.FilePatternSet;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.User;

import java.util.List;
import java.util.Iterator;
import java.io.File;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpTransferThread.java,v 1.1.1.1 2007-02-01 20:41:16 kherm Exp $
 * <p>
 * Implements a grid ftp transfer thread.
 */

public class GridFtpTransferThread extends GridFtpThread {

    private BaseFileTransfer task = null;

    public GridFtpTransferThread(GridFtpConnection srcConnection,
                                 GridFtpConnection dstConnection,
                                 BaseFileTransfer task) {
        super(srcConnection, dstConnection);
        this.task = task;
    }

    public void runCommand() {
        try {
            runTask();
            task.setTaskStatus(TaskStatus.COMPLETED);
        } catch (Exception e) {
            e.printStackTrace();
            task.setTaskStatus(TaskStatus.FAILED, e.getMessage());
        }
    }

    public void runTask() throws GridFtpException {
        FileTransferSpec transferSpec = (FileTransferSpec)task.getTaskSpec();
        FileLocation dstLoc = transferSpec.getDestination();
        String dstUrl = dstLoc.getUrlWithoutQuery();
        FileSet srcSet = transferSpec.getFileSet();
        if (srcSet instanceof FilePatternSet) {
            FilePatternSet filePatternSet = (FilePatternSet)srcSet;
            FileLocation srcLoc = filePatternSet.getFileLocation();
            if (srcLoc == null) {
                throw new GridFtpException("Source location must be specified!");
            }
//            String srcFileName = srcLoc.getFileName();
//            if (!dstUrl.endsWith(srcFileName)) {
//                if (! dstUrl.endsWith("/")) {
//                    dstUrl += "/";
//                }
//                dstUrl += srcFileName;
//            }
            log.debug("Transferring " + srcLoc.getUrl());
            FileLocation dstNameLoc = new FileLocation();
            dstNameLoc.setUrlString(dstUrl);
            transferFile(srcLoc, dstNameLoc);
        } else if (srcSet instanceof FileLocationSet) {
            if (! dstUrl.endsWith("/")) {
                dstUrl += "/";
            }
            FileLocationSet srcLocationSet = (FileLocationSet)srcSet;
            List srcLocList = srcLocationSet.getFileLocations();
            log.debug("Transferring " + srcLocList.size() + " file locations");
            for (Iterator srcLocs = srcLocList.iterator(); srcLocs.hasNext();) {
                FileLocation srcLoc = (FileLocation) srcLocs.next();
                String srcFileName = srcLoc.getFileName();
                FileLocation dstNameLoc = new FileLocation();
                dstNameLoc.setUrlString(dstUrl + srcFileName);
                transferFile(srcLoc, dstNameLoc);
            }
        } else {
            throw new GridFtpException("File set type not supported "
                    + srcSet.getClass().getName());
        }
    }

    public void transferFile(FileLocation srcLoc, FileLocation dstLoc) throws GridFtpException {
        User user = task.getUser();
        LocalHostBrowserService localHostBrowserService = null;
        localHostBrowserService =
                (LocalHostBrowserService)FileUtil.getPortletService(user,
                                                                    LocalHostBrowserService.class);
        if (dstConnection == null) {
            try {
                String dstPath = dstLoc.getFilePath();
                String realPath = localHostBrowserService.getRealPath(user, dstPath);
                log.error("Getting to file = " + realPath);
                dstLoc.setFilePath(realPath);
            } catch (PortletServiceException e) {
                throw new GridFtpException(e.getMessage());
            }
            File dstFile = new File(dstLoc.getFilePath());
            get(srcLoc, dstFile);
            if (task instanceof FileMove) {
                delete(srcLoc);
            }
        } else if (srcConnection == null) {
            try {
                String srcPath = srcLoc.getFilePath();
                String realPath = localHostBrowserService.getRealPath(user, srcPath);
                log.error("Putting from file = " + realPath);
                srcLoc.setFilePath(realPath);
            } catch (PortletServiceException e) {
                throw new GridFtpException(e.getMessage());
            }
            File srcFile = new File(srcLoc.getFilePath());
            put(srcFile, dstLoc);
            if (task instanceof FileMove) {
                if (task instanceof FileMove) {
                    try {
                        FileBrowser fileBrowser =
                                localHostBrowserService.createFileBrowser(user, "localhost");
                        fileBrowser.delete(srcLoc);
                    } catch (Exception e) {
                        throw new GridFtpException(e.getMessage());
                    }
                }
            }
        } else {
            transfer(srcLoc, dstLoc);
            if (task instanceof FileMove) {
                delete(srcLoc);
            }
        }
    }
}

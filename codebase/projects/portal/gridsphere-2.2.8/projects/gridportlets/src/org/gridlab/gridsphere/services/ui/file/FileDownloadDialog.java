package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionDialogType;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.tasks.FileCopy;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.resources.system.LocalHostBrowser;
import org.gridlab.gridsphere.services.task.TaskStatus;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.MessageBoxBean;

import java.util.Map;
import java.util.Date;
import java.io.File;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileDownloadDialog.java,v 1.1.1.1 2007-02-01 20:42:00 kherm Exp $
 */

public class FileDownloadDialog extends FileDialog {

   private transient static PortletLog log = SportletLog.getInstance(FileDownloadDialog.class);

   private FileLocation fileLocation = null;
   private String fileUrl = null;
   private String fileName = null;
   private String fileRealPath = null;
   private boolean wasRemoteFile = false;

   public FileDownloadDialog(ActionComponentFrame container, String compName) {
        super(container, compName);
        okPage = "/jsp/file/FileDownloadDialog.jsp";
        log.debug("Setting file download dialog page to " + okPage);
        setDialogType(ActionDialogType.OK_TYPE);
    }

    public FileLocation getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(FileLocation loc) {
        fileLocation = loc;
    }

    public void onStore() throws PortletException {
        log.debug("onStore(" + compId + ")");
        log.debug("File url = " + fileUrl);
        setPageAttribute("fileUrl", fileUrl);
        log.debug("File name = " + fileName);
        setPageAttribute("fileName", fileName);
        log.debug("Real path = " + fileRealPath);
    }

    public boolean doViewAction(Map parameters) throws PortletException {
        wasRemoteFile = false;
        setNextState(okPage);
        log.debug("Downloading file " + fileLocation.getUrl());
        User user = getUser();
        MessageBoxBean messageBox = getMessageBox();
        fileName = null;
        fileUrl = null;
        fileRealPath = null;
        try {
            FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, fileLocation);
            if (fileBrowser instanceof LocalHostBrowser) {
                log.debug("Downloading from local host");
                LocalHostBrowser localHostBrowser = (LocalHostBrowser)fileBrowser;
                fileName = fileLocation.getFileName(); // The name we display
                fileUrl = localHostBrowser.getDownloadUrl(fileLocation); // The file url
                fileRealPath = localHostBrowser.getRealPath(fileLocation); // The real local path
            } else {
                wasRemoteFile = true;
                log.debug("Downloading from remote host");
                LocalHostBrowser localHostBrowser =
                        (LocalHostBrowser)fileBrowserService.createFileBrowser(user, new FileLocation("secdir://localhost"));
                FileLocation localFileLoc = localHostBrowser.createFileLocation("/tmp/" + fileLocation.getFileName());
                log.debug("From remote location " + fileLocation.getUrl() + " to local location = " + localFileLoc.getUrl());
                FileCopy fileCopy = fileBrowser.copy(fileLocation, localFileLoc);
                fileCopy.waitFor();
                if (!fileCopy.getTaskStatus().equals(TaskStatus.COMPLETED)) {
                    log.error("Unable to download file." + fileCopy.getTaskStatusMessage());
                    messageBox.appendText("Unable to download file. " + fileCopy.getTaskStatusMessage());
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                }
                fileName = fileLocation.getFileName(); // The name we display
                fileUrl = localHostBrowser.getDownloadUrl(localFileLoc); // The file url
                fileRealPath = localHostBrowser.getRealPath(localFileLoc); // The real local path
            }
        } catch (Exception e) {
            log.error("Unable to download file." + e.getMessage());
            messageBox.appendText("Unable to download file. " + e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return false;
        }
        return true;
    }

    public boolean doOkAction(Map parameters) throws PortletException {
        if (wasRemoteFile) {
            try {
                File f = new File(fileRealPath);
                if (f.exists()) {
                    f.delete();
                }
            } catch (Exception e) {
                log.error("Unable to delete download file", e);
            }
            wasRemoteFile = false;
        }
        return true;
    }
}

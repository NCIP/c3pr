package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionDialogType;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.tasks.FileMakeDir;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.task.TaskStatus;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileMakeDirDialog.java,v 1.1.1.1 2007-02-01 20:42:01 kherm Exp $
 */

public class FileMakeDirDialog extends FileDialog {

    public static final String NEW_DIRECTORY_NAME = "portlets.file.message_new_directory";
    public static final String DEFAULT_ACTION = "doMakeDir";

    private transient static PortletLog log = SportletLog.getInstance(FileMakeDirDialog.class);

    private FileLocation fileResourceLoc = null;
    private String filePath = "";
    private TextFieldBean fileHostField = null;
    private TextFieldBean filePathField = null;
    private TextFieldBean dirNameField = null;
    private String newDirName = "";

    public FileMakeDirDialog(ActionComponentFrame container, String compName) {
        super(container, compName);
        // File host field
        fileHostField = createTextFieldBean("fileHostField");
        fileHostField.setReadOnly(true);
        // File path field
        filePathField = createTextFieldBean("filePathField");
        filePathField.setReadOnly(true);
        // Dir name field
        dirNameField = createTextFieldBean("dirNameField");
        // Set ok cancel page
        okCancelPage = "/jsp/file/FileMakeDirDialog.jsp";
        log.debug("Setting file make dir dialog page to " + okCancelPage);
        setDialogType(ActionDialogType.OK_CANCEL_TYPE);
    }

    public FileLocation getFileResourceLoc() {
        return fileResourceLoc;
    }

    public void setFileResourceLoc(FileLocation fileResourceLoc) {
        this.fileResourceLoc = fileResourceLoc;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String path) {
        filePath = path;
    }

    public String getNewDirName() {
        return newDirName;
    }

    public boolean doViewAction(Map parameters) throws PortletException {
        if (fileResourceLoc != null) {
            String fileHost = fileResourceLoc.getHost();
            fileHostField.setValue(fileHost);
        }
        filePathField.setValue(filePath);
        return true;
    }

    public boolean doOkAction(Map parameters) throws PortletException {
        // Get new dir name
        String newDirName = dirNameField.getValue();
        if (newDirName == null) {
            newDirName = "";
        } else {
            newDirName = newDirName.trim();
        }
        // If new dir name is empty, complain...
        if (newDirName.equals("")) {
            log.debug("newDirName is empty..");
            messageBox.appendText(getResourceString(NEW_DIRECTORY_NAME));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return false;
        } else {
            try {
                FileBrowserService fileBrowserService
                        = (FileBrowserService)getPortletService(FileBrowserService.class);
                User user = getUser();
                FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, fileResourceLoc);
                FileLocation dirLoc = fileBrowser.createFileLocation(filePath);
                FileMakeDir makeDir = fileBrowser.makeDirectory(dirLoc, newDirName);
                makeDir.waitFor();
                if (makeDir.getTaskStatus().equals(TaskStatus.FAILED)) {
                    messageBox.appendText(makeDir.getTaskStatusMessage());
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    return false;
                } else {
                    this.newDirName = newDirName;
                }
            } catch (Exception e) {
                messageBox.appendText(e.getMessage());
                messageBox.setMessageType(TextBean.MSG_ERROR);
                return false;
            }
        }
        log.debug("Exiting doOkAction()");
        return true;
    }
}

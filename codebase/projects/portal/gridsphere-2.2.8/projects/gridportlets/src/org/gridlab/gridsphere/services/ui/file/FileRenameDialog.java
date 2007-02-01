/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileRenameDialog.java,v 1.1.1.1 2007-02-01 20:42:01 kherm Exp $
 */
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
import org.gridlab.gridsphere.services.file.tasks.FileNameChange;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileException;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.services.task.TaskException;

import java.util.Map;

public class FileRenameDialog extends FileDialog {

    public static final String NEW_FILE_NAME = "portlets.file.message_new_filename";
    private transient static PortletLog log = SportletLog.getInstance(FileRenameDialog.class);

    private FileLocation fileResourceLoc = null;
    private TextFieldBean fileHostField = null;
    private TextFieldBean filePathField = null;
    private TextFieldBean fileNameField = null;
    private String filePath = "";
    private String newFileName = null;

    public FileRenameDialog(ActionComponentFrame container, String compName) {
        super(container, compName);
        // File host field
        fileHostField = createTextFieldBean( "fileHostField");
        fileHostField.setReadOnly(true);
        // File path field
        filePathField = createTextFieldBean( "filePathField");
        filePathField.setReadOnly(true);
        // File name field
        fileNameField = createTextFieldBean("fileNameField");
        // Set ok cancel page
        okCancelPage = "/jsp/file/FileRenameDialog.jsp";
        log.debug("Setting file rename dialog page to " + okCancelPage);
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

    public String getNewFileName() {
        return newFileName;
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
        // Get new file name
        newFileName = fileNameField.getValue();
        if (newFileName == null) {
            newFileName = "";
        } else {
            newFileName = newFileName.trim();
        }
        // If new file name is empty, complain...
        if (newFileName.equals("")) {
            messageBox.appendText(getResourceString(NEW_FILE_NAME));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return false;
        } else {
            // Rename file to new file name
            String filePath = filePathField.getValue();
            try {
                User user = getUser();
                FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, fileResourceLoc);
                FileLocation fileLoc = fileBrowser.createFileLocation(filePath);
                FileNameChange rename = fileBrowser.rename(fileLoc, newFileName);
                log.debug("Submitted rename task...");
                rename.waitFor();
                if (rename.getTaskStatus().equals(TaskStatus.FAILED)) {
                    messageBox.appendText(rename.getTaskStatusMessage());
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    return false;
                } else {
                    // Set next state
                    parameters.clear();
                    parameters.put("selectedFileName", newFileName);
                    setNextState(FileListComp.class, "reListFiles", parameters);
                }
            } catch (FileException e) {
                messageBox.appendText(e.getMessage());
                messageBox.setMessageType(TextBean.MSG_ERROR);
                return false;
            } catch (TaskException e) {
                messageBox.appendText(e.getMessage());
                messageBox.setMessageType(TextBean.MSG_ERROR);
                return false;
            }
        }
        log.debug("Exiting doOkAction()");
        return true;
    }
}

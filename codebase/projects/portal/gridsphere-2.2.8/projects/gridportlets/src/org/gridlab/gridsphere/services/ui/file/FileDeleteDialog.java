/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileDeleteDialog.java,v 1.1.1.1 2007-02-01 20:42:00 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.portlet.PortletLog;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionDialogType;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.tasks.FileDeletion;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.task.TaskStatus;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class FileDeleteDialog extends FileDialog {

    private transient static PortletLog log = SportletLog.getInstance(FileDeleteDialog.class);

    private FileLocation fileResourceLoc = null;
    private TextFieldBean fileHostField = null;
    private TextFieldBean filePathField = null;
    private ListBoxBean filePathList = null;
    private String filePath = "";
    private List deleteFileList = new ArrayList();

    public FileDeleteDialog(ActionComponentFrame container, String compName) {
        super(container, compName);
        // File host field
        fileHostField = createTextFieldBean( "fileHostField");
        fileHostField.setReadOnly(true);
        // File list box
        filePathList = createListBoxBean( "filePathList");
        // File path field
        filePathField = createTextFieldBean( "filePathField");
        filePathField.setReadOnly(true);
        // Set ok cancel page
        okCancelPage = "/jsp/file/FileDeleteDialog.jsp";
        log.debug("Setting file delete dialog page to " + okCancelPage);
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

    public List getFileList() {
        return deleteFileList;
    }

    public void setFileList(List fileList) {
        deleteFileList = fileList;
    }

    public boolean doViewAction(Map parameters) throws PortletException {
        if (fileResourceLoc != null) {
            String fileHost = fileResourceLoc.getHost();
            fileHostField.setValue(fileHost);
        }
        filePathField.setValue(filePath);
        FileComp.loadFilePathList(filePathList, deleteFileList, "");
        return true;
    }

    public boolean doOkAction(Map parameters) throws PortletException {
        log.debug("Entering doOkAction()");
        try {
            log.debug("Initiating delete task... for " + deleteFileList.size() + " files");
            FileBrowser fileBrowser = fileBrowserService.createFileBrowser(getUser(), fileResourceLoc);
            FileLocationSet fileLocationSet = new FileLocationSet(deleteFileList);
            FileDeletion delete = fileBrowser.delete(fileLocationSet);
            log.debug("Submitted delete task...");
            delete.waitFor();
            if (delete.getTaskStatus().equals(TaskStatus.FAILED)) {
                messageBox.appendText(delete.getTaskStatusMessage());
                messageBox.setMessageType(TextBean.MSG_ERROR);
                setNextState("/jsp/file/FileDeleteComp.jsp");
            } else {
                // Clear delete file list
                deleteFileList = new Vector();
                // Set next state
                parameters.clear();
                parameters.put("filePathParam", filePath);
                setNextState(FileListComp.class, "reListFiles", parameters);
            }
        } catch (Exception e) {
            log.error("Unable to delete files", e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState("/jsp/file/FileDeleteComp.jsp");
        } finally {
        }
        log.debug("Exiting doOkAction()");
        return true;
    }
}

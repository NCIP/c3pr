package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionDialogType;
import org.gridlab.gridsphere.services.core.file.FileManagerService;
import org.gridlab.gridsphere.services.file.FileLocation;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.FileInputBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;
import java.io.File;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileUploadDialog.java,v 1.1.1.1 2007-02-01 20:42:02 kherm Exp $
 */

public class FileUploadDialog extends FileDialog {

    public static final String MESSAGE_MAKE_SELECTION = "portlets.file.message_select";
    private transient static PortletLog log = SportletLog.getInstance(FileUploadDialog.class);

    private FileManagerService localFileManager = null;

    private FileLocation fileResourceLoc = null;
    private TextFieldBean fileHostField = null;
    private TextFieldBean filePathField = null;
    private TextFieldBean fileNameField = null;
    private FileInputBean fileUploadBean = null;
    private String uploadedFileName = null;
    private String uploadedFilePath = null;
    private boolean isDirectory = false;
    private String filePath = "";
    private String fileName = "";
    private boolean saveAsFlag = false;

    public FileUploadDialog(ActionComponentFrame container, String compName) {
        super(container, compName);
        // File host field
        fileHostField = createTextFieldBean("fileHostField");
        fileHostField.setReadOnly(true);
        // File path field
        filePathField = createTextFieldBean("filePathField");
        filePathField.setReadOnly(true);
        // File name field
        fileNameField = createTextFieldBean("fileNameField");
        // Create file upload bean
        fileUploadBean = createFileInputBean("fileUploadBean");
        // Notify container we contain our own forms...
        containsForms = true;
        // Set ok cancel page
        okCancelPage = "/jsp/file/FileUploadDialog.jsp";
        log.debug("Setting file upload dialog page to " + okCancelPage);
        setDialogType(ActionDialogType.OK_CANCEL_TYPE);
    }

    public void onStore() throws PortletException {
        setPageAttribute("saveAsFlag", Boolean.valueOf(saveAsFlag));
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean getSaveAsFlag() {
        return saveAsFlag;
    }

    public void setSaveAsFlag(boolean flag) {
        saveAsFlag = flag;
    }

    public void onInit() throws PortletException {
        super.onInit();
        localFileManager = (FileManagerService)getPortletService(FileManagerService.class);
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
        uploadedFileName = fileUploadBean.getFileName();
        int size = fileUploadBean.getSize();
        log.debug("Uploading file " + uploadedFileName + " of size " + size);
        if (uploadedFileName.equals("")) {
            messageBox.appendText(getResourceString(MESSAGE_MAKE_SELECTION));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return false;
        }
        if (saveAsFlag) {
            fileName = fileNameField.getValue();
            if (fileName == null || fileName.equals("")) {
                fileName = uploadedFileName;
            }
        }
        User user = getUser();
        String userLoc = localFileManager.getLocationPath(user, "");
        File f = new File(userLoc);
        if (!f.exists()) {
            if (!f.mkdirs()) {
                messageBox.appendText("Unable to get home path on portal : " + userLoc);
                messageBox.setMessageType(TextBean.MSG_ERROR);
                return false;
            }
        }
        uploadedFilePath = localFileManager.getLocationPath(user, uploadedFileName);
        File u = new File(uploadedFilePath);
        isDirectory = u.isDirectory();

        log.info("uploadedFilePath: " + uploadedFilePath);
        try {
            fileUploadBean.saveFile(uploadedFilePath);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new PortletException(e.getMessage());
        }
        log.debug("fileUploadBean value=" + fileUploadBean.getValue());
        return true;
    }

    public String getUploadedFilePath() {
        return uploadedFilePath;
    }

    public boolean getIsDirectory() {
        return isDirectory;
    }
}

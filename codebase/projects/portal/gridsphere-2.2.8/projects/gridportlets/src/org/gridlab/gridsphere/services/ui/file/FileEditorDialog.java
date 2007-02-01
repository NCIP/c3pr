package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionDialogType;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileHandle;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextAreaBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileEditorDialog.java,v 1.1.1.1 2007-02-01 20:42:00 kherm Exp $
 */

public class FileEditorDialog extends FileDialog {

    private transient static PortletLog log = SportletLog.getInstance(FileEditorDialog.class);

    private FileLocation fileLocation = null;
    private FileLocation dirLocation = null;
    private String dirUrlNoQuery = null;
    private String fileName = "";
    private boolean isNewFlag = false;
    private boolean saveAsFlag = false;
    private TextFieldBean fileHostField = null;
    private TextFieldBean filePathField = null;
    private TextFieldBean fileNameField = null;
    private TextAreaBean fileTextArea = null;

    public FileEditorDialog(ActionComponentFrame container, String compName) {
        super(container, compName);
        fileHostField = createTextFieldBean("fileHostField");
        fileHostField.setReadOnly(true);
        fileHostField.setDisabled(true);
        filePathField = createTextFieldBean("filePathField");
        filePathField.setReadOnly(true);
        filePathField.setDisabled(true);
        fileNameField = createTextFieldBean("fileNameField");
        fileNameField.setReadOnly(true);
        fileNameField.setDisabled(true);
        fileTextArea = createTextAreaBean("fileTextArea");
        okCancelPage = "/jsp/file/FileEditorDialog.jsp";
        log.debug("Setting file editor dialog page to " + okCancelPage);
        setDialogType(ActionDialogType.OK_CANCEL_TYPE);
    }

    public void onStore() throws PortletException {
        setPageAttribute("saveAsFlag", Boolean.valueOf(saveAsFlag));
    }

    public FileLocation getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(FileLocation loc) {
        fileLocation = loc;
        fileName = fileLocation.getFileName();
        String fileUrlNoQuery = fileLocation.getUrlWithoutQuery();
        int index = fileUrlNoQuery.indexOf("/" + fileName);
        if (index > -1) {
            dirUrlNoQuery = fileUrlNoQuery.substring(0, index);
            dirLocation = new FileLocation();
            dirLocation.setUrlString(dirUrlNoQuery);
        } else {
            dirLocation = fileLocation;
        }
    }

    public FileLocation getDirLocation() {
        return dirLocation;
    }

    public void setDirLocation(FileLocation loc) {
        dirLocation = loc;
        dirUrlNoQuery = dirLocation.getUrlWithoutQuery();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean getIsNewFlag() {
        return isNewFlag;
    }

    public void setIsNewFlag(boolean flag) {
        isNewFlag = flag;
    }

    public boolean getSaveAsFlag() {
        return saveAsFlag;
    }

    public void setSaveAsFlag(boolean flag) {
        saveAsFlag = flag;
    }

    public boolean doViewAction(Map parameters) throws PortletException {
        // Setup fields...
        fileHostField.setValue(dirLocation.getHost());
        filePathField.setValue(dirLocation.getFilePath());
        fileNameField.setValue(fileName);
        fileNameField.setReadOnly(!saveAsFlag);
        fileNameField.setDisabled(!saveAsFlag);
        // Return here if both new flag and save as flag are set
        if (isNewFlag && saveAsFlag) {
            fileTextArea.setValue("");
            return true;
        }
        try {
            // Setup file location
            fileLocation = new FileLocation(dirUrlNoQuery + "/" + fileName);
            log.debug("Reading from file location " + fileLocation.getUrl());
            FileHandle fileHandle = new FileHandle(fileLocation);
            String fileText = fileHandle.readContents(getUser());
            fileTextArea.setValue(fileText);
        } catch (Exception e) {
            log.error("Unable to edit file location ", e);
            messageBox.appendText("Unable to read contents of file: " + e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
            return false;
        }
        return true;
    }

    public boolean doOkAction(Map parameters) throws PortletException {
        // Only validate if save as flag is true...
        if (saveAsFlag) {
            String newFileName = fileNameField.getValue();
            if (newFileName == null || newFileName.equals("")) {
                messageBox.appendText("Please provide a name for the file");
                messageBox.setMessageType(TextBean.MSG_ERROR);
            } else if (newFileName.startsWith("/") || newFileName.startsWith("&")) {
                messageBox.appendText("File name is invalid");
                messageBox.setMessageType(TextBean.MSG_ERROR);
            }
            fileName = newFileName;
        }
        String fileText = fileTextArea.getValue();
        try {
            fileLocation = new FileLocation(dirUrlNoQuery + "/" + fileName);
            log.debug("Writing to file location " + fileLocation.getUrl());
            FileHandle fileHandle = new FileHandle(fileLocation);
            fileHandle.writeContents(getUser(), fileText);
        } catch (Exception e) {
            log.error("Unable to write to file location ", e);
            messageBox.appendText("Unable to save changes to file: " + e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
            return false;
        }
        return true;
    }

}

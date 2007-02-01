package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionDialogType;
import org.gridlab.gridsphere.services.file.FileLocation;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileChooserDialog.java,v 1.1.1.1 2007-02-01 20:41:59 kherm Exp $
 */

public class FileChooserDialog extends FileDialog {

    private transient static PortletLog log = SportletLog.getInstance(FileChooserDialog.class);

    public static final String MESSAGE_MAKE_SELECTION = "portlets.file.message_select";
    public static final String MESSAGE_FILE_SELECT = "portlets.file.message_file_select";
    public static final String MESSAGE_DIRECTORY_SELECT = "portlets.file.message_directory_select";

    protected ActionComponentFrame dialogBean = null;
    protected FileChooserComp fileChooser = null;
    protected FileLocation selectedFileLocation = null;
    protected boolean isDirectoryOnly = false;
    protected boolean isFileOnly = false;

    public FileChooserDialog(ActionComponentFrame container, String compName) {
        super(container, compName);
        dialogBean = createActionComponentFrame("dialogBean");
        dialogBean.setNextState(FileChooserComp.class, DEFAULT_ACTION);
        setDialogType(ActionDialogType.OK_CANCEL_TYPE);
    }

    public boolean doViewAction(Map parameters) throws PortletException {
        log.debug("doViewAction");
        return true;
    }

    public FileLocation getSelectedFileLocation() {
        return selectedFileLocation;
    }

    public boolean getIsDirectoryOnly() {
        return isDirectoryOnly;
    }

    public void setIsDirectoryOnly(boolean isDirectoryOnly) {
        this.isDirectoryOnly = isDirectoryOnly;
        if (isDirectoryOnly && isFileOnly) {
            isFileOnly = false;
        }
    }

    public boolean getIsFileOnly() {
        return isFileOnly;
    }

    public void setIsFileOnly(boolean isFileOnly) {
        this.isFileOnly = isFileOnly;
        if (isFileOnly && isDirectoryOnly) {
            isDirectoryOnly = false;
        }
    }

    public boolean doOkAction(Map parameters) throws PortletException {
        boolean isValid = true;
        fileChooser = (FileChooserComp)dialogBean.getActiveComponent();
        selectedFileLocation = fileChooser.getSelectedFileLocation();
        if (selectedFileLocation == null) {
            messageBox.appendText(getResourceString(MESSAGE_MAKE_SELECTION));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            isValid = false;
        } else {
            if (isDirectoryOnly && ! selectedFileLocation.getIsDirectory()) {
                messageBox.appendText(getResourceString(MESSAGE_DIRECTORY_SELECT));
                messageBox.setMessageType(TextBean.MSG_ERROR);
                isValid = false;
            } else if (isFileOnly && ! selectedFileLocation.getIsFile()) {
                messageBox.appendText(getResourceString(MESSAGE_FILE_SELECT));
                messageBox.setMessageType(TextBean.MSG_ERROR);
                isValid = false;
            }
        }
        return isValid;
    }
}

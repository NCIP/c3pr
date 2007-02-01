package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionDialogType;
import org.gridlab.gridsphere.services.ui.file.FileDialog;
import org.gridlab.gridsphere.services.ui.file.FileSaveComp;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.file.FileLocation;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileSaveDialog.java,v 1.1.1.1 2007-02-01 20:42:02 kherm Exp $
 */

public class FileSaveDialog extends FileDialog {

    private transient static PortletLog log = SportletLog.getInstance(FileSaveDialog.class);

    public static final String MESSAGE_MAKE_SELECTION = "portlets.file.message_saveas";

    protected ActionComponentFrame dialogBean = null;
    protected FileSaveComp fileSaver = null;
    protected FileLocation selectedFileLocation = null;

    public FileSaveDialog(ActionComponentFrame container, String compName) {
        super(container, compName);
        dialogBean = createActionComponentFrame("dialogBean");
        dialogBean.setNextState(FileSaveComp.class, DEFAULT_ACTION);
        setDialogType(ActionDialogType.OK_CANCEL_TYPE);
    }

    public boolean doViewAction(Map parameters) throws PortletException {
        log.debug("doViewAction");
        return true;
    }

    public FileLocation getSaveAsFile() {
        return selectedFileLocation;
    }

    public boolean doOkAction(Map parameters) throws PortletException {
        boolean isValid = true;
        fileSaver = (FileSaveComp)dialogBean.getActiveComponent();
        selectedFileLocation = fileSaver.getSaveAsFile();
        if (selectedFileLocation == null) {
            log.debug("No file name provided");
            messageBox.appendText(getResourceString(MESSAGE_MAKE_SELECTION));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            isValid = false;
        }
        log.debug("Is valid? " + isValid);
        return isValid;
    }
}

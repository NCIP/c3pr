/**
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileBrowserComp.java,v 1.1.1.1 2007-02-01 20:41:59 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.security.gss.ActiveCredentialFilter;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.tasks.FileCopy;
import org.gridlab.gridsphere.services.file.tasks.FileMove;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.InputBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;
import java.util.List;


public class FileBrowserComp extends FileComp {

    private transient static PortletLog log = SportletLog.getInstance(FileBrowserComp.class);

    public static final String PHYSICAL_BROWSER_MODE = "PHYSICAL_BROWSER_MODE";
    public static final String LOGICAL_BROWSER_MODE = "LOGICAL_BROWSER_MODE";
    public static final String COPY_TASK_TYPE = "copy";
    public static final String MOVE_TASK_TYPE = "move";

    public static final String TRANSFER_COMPLETED_MESSAGE_KEY = "portlets.file.message_transfer_completed";
    public static final String TRANSFER_FAILED_MESSAGE_KEY = "portlets.file.message_transfer_failed";
    public static final String TRANSFER_SUBMITTED_MESSAGE_KEY = "portlets.file.message_transfer_submitted";

    protected TextBean modeText = null;

    protected ActionComponentFrame fileBrowser1Bean = null;
    protected ActionComponentFrame fileBrowser2Bean = null;

    protected static final int COPY_TASK = 0;
    protected static final int MOVE_TASK = 1;

    public FileBrowserComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        // File browser 1
        fileBrowser1Bean = createActionComponentFrame("fb1");
        fileBrowser1Bean.setNextState(FileListComp.class, DEFAULT_ACTION);
        // File browser 2
        fileBrowser2Bean = createActionComponentFrame("fb2");
        fileBrowser2Bean.setNextState(FileListComp.class, DEFAULT_ACTION);

        // Set default action
        setDefaultAction("doFileBrowser");
        setDefaultJspPage("/jsp/file/FileBrowserComp.jsp");

        // Register credential actions
        String activeCredentialMethods[] = { ActionComponent.RENDER_ACTION,
                                             "doCopyFilesFb1Fb2",
                                             "doMoveFilesFb1Fb2",
                                             "doCopyFilesFb2Fb1",
                                             "doMoveFilesFb2Fb1" };
        container.addActionFilter(this,  activeCredentialMethods, ActiveCredentialFilter.getInstance());

        // Set file browser mode
        modeText = createTextBean("modeText");
        modeText.setValue(PHYSICAL_BROWSER_MODE);
        doSetMode(null);
    }

    public void doFileBrowser(Map parameters) throws PortletException {
        // Fb1 browser - Set render action to null, browser comp will handle re-render
        FileListComp fb1ListComp = (FileListComp)fileBrowser2Bean.getActiveComponent();
        fb1ListComp.setResourceAlign("left");
        fb1ListComp.setRenderAction(null);
        // Fb2 browser - Set render action to null, browser comp will handle re-render
        FileListComp fb2ListComp = (FileListComp)fileBrowser2Bean.getActiveComponent();
        fb2ListComp.setResourceAlign("right");
        fb2ListComp.setRenderAction(null);
        // Set next state
        setNextState(defaultJspPage);
    }

    public void reFileBrowser(Map parameters) throws PortletException {
        try {
            // Fb1 browser....
            FileListComp fb1ListComp = (FileListComp)fileBrowser1Bean.getActiveComponent();
            fb1ListComp.reListFiles(parameters);
            // Fb2 browser....
            FileListComp fb2ListComp = (FileListComp)fileBrowser2Bean.getActiveComponent();
            fb2ListComp.reListFiles(parameters);
        } catch (Exception e) {
            messageBox.appendText(e.getMessage());
        }

        setNextState(defaultJspPage);
    }

    public void doSetMode(Map parameters) throws PortletException {

        String oldMode = modeText.getValue();

        // Get selected mode
        String mode = null;
        if (parameters == null) {
            mode = PHYSICAL_BROWSER_MODE;
        } else {
            mode = (String)parameters.get("modeParam");
            if (mode == null) mode = oldMode;
        }

        // Which mode are in?
        modeText.setValue(mode);

        Boolean isLogicalFileMode = (mode.equals(LOGICAL_BROWSER_MODE)) ? Boolean.TRUE : Boolean.FALSE;
        setIsLogicalFileMode(isLogicalFileMode);

        // If parameters != null then we've already created our file list components
        if (parameters != null && !oldMode.equals(mode)) {
            // Get file browsers
            FileListComp fb1ListComp = (FileListComp)fileBrowser1Bean.getActiveComponent();
            FileListComp fb2ListComp = (FileListComp)fileBrowser2Bean.getActiveComponent();
            try {
                fb1ListComp.setIsLogicalFileMode(isLogicalFileMode);
                fb1ListComp.newListFiles(parameters);
                fb2ListComp.setIsLogicalFileMode(isLogicalFileMode);
                fb2ListComp.newListFiles(parameters);
            } catch (Exception e) {
                messageBox.appendText(e.getMessage());
            }
        }
    }

    public void doCopyFilesFb1Fb2(Map parameters) throws PortletException {
        doTransferFiles(parameters,
                        FileTransferComp.FILE_BROWSER_1,
                        FileTransferComp.COPY_TASK_TYPE);
    }

    public void doMoveFilesFb1Fb2(Map parameters) throws PortletException {
        doTransferFiles(parameters,
                        FileTransferComp.FILE_BROWSER_1,
                        FileTransferComp.MOVE_TASK_TYPE);
    }

    public void doCopyFilesFb2Fb1(Map parameters) throws PortletException {
        doTransferFiles(parameters,
                        FileTransferComp.FILE_BROWSER_2,
                        FileTransferComp.COPY_TASK_TYPE);
    }

    public void doMoveFilesFb2Fb1(Map parameters) throws PortletException {
        doTransferFiles(parameters,
                        FileTransferComp.FILE_BROWSER_2,
                        FileTransferComp.MOVE_TASK_TYPE);
    }

    public void doTransferFiles(Map parameters,
                                String srcFileBrowser,
                                String transferType) throws PortletException {

        ActionComponentFrame srcBrowserBean = null;
        ActionComponentFrame dstBrowserBean = null;

        log.debug("Source browser is " + srcFileBrowser);

        // Get source and destination browsers..
        if (srcFileBrowser.equals(FileTransferComp.FILE_BROWSER_1)) {
            srcBrowserBean = fileBrowser1Bean;
            dstBrowserBean = fileBrowser2Bean;
        }  else {
            srcBrowserBean = fileBrowser2Bean;
            dstBrowserBean = fileBrowser1Bean;
        }

        // Source browser....
        FileListComp srcListComp = (FileListComp)srcBrowserBean.getActiveComponent();

        FileLocation srcResourceLoc = srcListComp.getFileResourceLoc();
        InputBean srcFilePathField = srcListComp.getCurrentPathField();
        ListBoxBean srcFilePathList = srcListComp.getFilePathList();

        log.debug("Source file host " + srcResourceLoc.getHost());
        log.debug("Source file path " + srcFilePathField.getValue());
        log.debug("Source file list " + srcFilePathList.getSelectedName());

        // Destination browser....
        FileListComp dstListComp = (FileListComp)dstBrowserBean.getActiveComponent();

        FileLocation dstResourceLoc = dstListComp.getFileResourceLoc();
        InputBean dstFilePathField = dstListComp.getCurrentPathField();

        log.debug("Destination file host " + dstResourceLoc.getHost());
        log.debug("Destination file path " + dstFilePathField.getValue());

        // Validate and invoke transfer form
        List filePaths = srcFilePathList.getSelectedNames();
        if (filePaths.size() == 0) {
            // Set message to display
            messageBox.setMessageType("error");
            messageBox.appendText("No files were selected for " + transferType + "ing");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
        } else {
            String srcFilePathParam = srcFilePathField.getValue();
            if (srcResourceLoc == null || srcFilePathParam.equals("")) {
                messageBox.appendText("Please specify files to " + transferType) ;
                setNextState(defaultJspPage);
                return;
            }
            // Source files to transfer
            List srcFileLocationList = unloadFilePathLocations(srcFilePathList);
            String dstFilePathParam = dstFilePathField.getValue();
            if (dstResourceLoc == null || dstFilePathParam.equals("")) {
                messageBox.appendText("Please specify a destination.");
                messageBox.setMessageType(TextBean.MSG_ERROR);
                setNextState(defaultJspPage);
                return;
            }
              transferFiles(parameters,
                            transferType,
                            srcResourceLoc,
                            srcFileLocationList,
                            dstResourceLoc,
                            dstFilePathParam);
        }
    }

    public void transferFiles(Map parameters,
                              String transferType,
                              FileLocation srcResourceLoc,
                              List srcFileLocationList,
                              FileLocation dstResourceLoc,
                              String dstPath) throws PortletException {
        log.debug("Entering doTransferFilesApply");

        setNextState(defaultJspPage);

        try {

            log.debug("Initiating transfer task...");
            User user = getUser();
            FileBrowser srcBrowser = fileBrowserService.createFileBrowser(user, srcResourceLoc);
            FileLocationSet srcFileSet = new FileLocationSet(srcFileLocationList);
            FileBrowser dstBrowser = fileBrowserService.createFileBrowser(user, dstResourceLoc);
            FileLocation dstLocation = dstBrowser.createFileLocation(dstPath);
            log.debug("Transferring " + srcFileLocationList.size() + " files");
            if (transferType.equals(COPY_TASK_TYPE)) {
                FileCopy copy = srcBrowser.copy(srcFileSet, dstLocation);
                log.debug("Submitted copy task...");
                if (copy.getTaskStatus().equals(TaskStatus.FAILED)) {
                    messageBox.appendText(getResourceString(TRANSFER_FAILED_MESSAGE_KEY));
                    messageBox.appendText(copy.getTaskStatusMessage());
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                } else if (copy.getTaskStatus().equals(TaskStatus.COMPLETED)) {
                    messageBox.appendText(getResourceString(TRANSFER_COMPLETED_MESSAGE_KEY));
                    messageBox.setMessageType(TextBean.MSG_SUCCESS);
                    reFileBrowser(parameters);
                } else {
                    messageBox.appendText(getResourceString(TRANSFER_SUBMITTED_MESSAGE_KEY));
                    messageBox.setMessageType(TextBean.MSG_SUCCESS);
                    reFileBrowser(parameters);
                }
            } else {
                FileMove move = srcBrowser.move(srcFileSet, dstLocation);
                log.debug("Submitted move task...");
                if (move.getTaskStatus().equals(TaskStatus.FAILED)) {
                    messageBox.appendText(getResourceString(TRANSFER_FAILED_MESSAGE_KEY));
                    messageBox.appendText(move.getTaskStatusMessage());
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                } else if (move.getTaskStatus().equals(TaskStatus.COMPLETED)) {
                    messageBox.appendText(getResourceString(TRANSFER_COMPLETED_MESSAGE_KEY));
                    messageBox.setMessageType(TextBean.MSG_SUCCESS);
                    reFileBrowser(parameters);
                } else {
                    messageBox.appendText(getResourceString(TRANSFER_SUBMITTED_MESSAGE_KEY));
                    messageBox.setMessageType(TextBean.MSG_SUCCESS);
                    reFileBrowser(parameters);
                }
            }
        } catch (Exception e) {
            log.error("Unable to transfer files", e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
        }
        log.debug("Exiting doTransferFilesApply");
    }
}

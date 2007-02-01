/**
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileTransferComp.java,v 1.1.1.1 2007-02-01 20:42:02 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.provider.portletui.beans.*;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.tasks.FileCopy;
import org.gridlab.gridsphere.services.file.tasks.FileMove;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.security.gss.ActiveCredentialFilter;
import org.gridlab.gridsphere.services.task.TaskStatus;

import java.util.List;
import java.util.Map;
import java.util.Vector;


public class FileTransferComp extends FileComp {

    public static final String DEFAULT_ACTION = "doTransferFiles";

    private transient static PortletLog log = SportletLog.getInstance(FileTransferComp.class);

    protected HiddenFieldBean srcCurrentHostField = null;
    protected HiddenFieldBean srcCurrentPathField = null;
    protected TextFieldBean srcFileHostField = null;
    protected TextFieldBean srcFilePathField = null;
    protected ListBoxBean srcFilePathList = null;
    protected List srcTransferFileList = new Vector();

    protected HiddenFieldBean dstCurrentHostField = null;
    protected HiddenFieldBean dstCurrentPathField = null;
    protected TextFieldBean dstFileHostField = null;
    protected TextFieldBean dstFilePathField = null;

    protected TextBean transferText = null;

    protected static final String COPY_TASK_TYPE = "copy";
    protected static final String MOVE_TASK_TYPE = "move";
    protected String transferType = COPY_TASK_TYPE;
    protected static final String FILE_BROWSER_1 = "fb1";
    protected static final String FILE_BROWSER_2 = "fb2";
    protected String srcFileBrowser = FILE_BROWSER_1;

    public FileTransferComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        /* Form objects */

        // Src current host field
        srcCurrentHostField = createHiddenFieldBean("srcCurrentHostField");
        if (srcCurrentHostField.getValue() == null) {
            srcCurrentHostField.setValue("");
        }
        // Src currrent path field
        srcCurrentPathField = createHiddenFieldBean("srcCurrentPathField");
        if (srcCurrentPathField.getValue() == null) {
            srcCurrentPathField.setValue("");
        }

        // Src file path field
        srcFileHostField = createTextFieldBean("srcFileHostField");
        srcFileHostField.setReadOnly(true);
        // Src file path field
        srcFilePathField = createTextFieldBean("srcFilePathField");
        srcFilePathField.setReadOnly(true);
        // Src selected files
        srcFilePathList = createListBoxBean("srcFilePathList");
        srcFilePathList.setReadOnly(true);
        srcFilePathList.setMultipleSelection(true);
        srcFilePathList.setSize(10);

        // Dst current host field
        dstCurrentHostField = createHiddenFieldBean("dstCurrentHostField");
        if (dstCurrentHostField.getValue() == null) {
            dstCurrentHostField.setValue("");
        }
        // Dst currrent path field
        dstCurrentPathField = createHiddenFieldBean("dstCurrentPathField");
        if (dstCurrentPathField.getValue() == null) {
            dstCurrentPathField.setValue("");
        }

        // Dst file path field
        dstFileHostField = createTextFieldBean("dstFileHostField");
        dstFileHostField.setReadOnly(true);
        // Src file path field
        dstFilePathField = createTextFieldBean("dstFilePathField");
        dstFilePathField.setReadOnly(true);

        // Transfer text
        transferText = createTextBean("transferText");

        // Set default action
        setDefaultAction(DEFAULT_ACTION);
        // Register credential actions
        container.addActionFilter(this, "doTransferFilesApply", ActiveCredentialFilter.getInstance());
    }

    public void onLoad() throws PortletException {
        super.onLoad();
        log.debug("onLoad(" + compId + ")");
        if (srcCurrentHostField.getValue() == null) {
            srcCurrentHostField.setValue("");
        }
        if (srcCurrentPathField.getValue() == null) {
            srcCurrentPathField.setValue("");
        }
        if (dstCurrentHostField.getValue() == null) {
            dstCurrentHostField.setValue("");
        }
        if (dstCurrentPathField.getValue() == null) {
            dstCurrentPathField.setValue("");
        }
    }

    public void doCopyFilesFb1Fb2(Map parameters) throws PortletException {
        parameters.put("srcFileBrowser", FILE_BROWSER_1);
        parameters.put("transferType", COPY_TASK_TYPE);
        doTransferFiles(parameters);
    }

    public void doMoveFilesFb1Fb2(Map parameters) throws PortletException {
        parameters.put("srcFileBrowser", FILE_BROWSER_1);
        parameters.put("transferType", MOVE_TASK_TYPE);
        doTransferFiles(parameters);
    }

    public void doCopyFilesFb2Fb1(Map parameters) throws PortletException {
        parameters.put("srcFileBrowser", FILE_BROWSER_2);
        parameters.put("transferType", COPY_TASK_TYPE);
        doTransferFiles(parameters);
    }

    public void doMoveFilesFb2Fb1(Map parameters) throws PortletException {
        parameters.put("srcFileBrowser", FILE_BROWSER_2);
        parameters.put("transferType", MOVE_TASK_TYPE);
        doTransferFiles(parameters);
    }

    public void doTransferFiles(Map parameters) throws PortletException {

        // Src file host name
        String srcFileHostParam = (String)parameters.get("srcFileHostParam");
        if (srcFileHostParam != null) {
            srcCurrentHostField.setValue(srcFileHostParam);
        }
        srcFileHostField.setValue(srcCurrentHostField.getValue());
        // Src file path
        String srcFilePathParam = (String)parameters.get("srcFilePathParam");
        if (srcFilePathParam != null) {
            srcCurrentPathField.setValue(srcFilePathParam);
        }
        srcFilePathField.setValue(srcCurrentPathField.getValue());
        // Src file path list
        srcTransferFileList.clear();
        List fileLocationList = (List)parameters.get("srcFileLocationList");
        if (fileLocationList != null) {
            srcTransferFileList.addAll(fileLocationList);
            loadFilePathList(srcFilePathList, srcTransferFileList, "");
        }
        parameters.remove("srcFileLocationList");

        // Dst file host name
        String dstFileHostParam = (String)parameters.get("dstFileHostParam");
        if (dstFileHostParam != null) {
            dstCurrentHostField.setValue(dstFileHostParam);
        }
        dstFileHostField.setValue(dstCurrentHostField.getValue());
        // Src file path
        String dstFilePathParam = (String)parameters.get("dstFilePathParam");
        if (dstFilePathParam != null) {
            dstCurrentPathField.setValue(dstFilePathParam);
        }
        dstFilePathField.setValue(dstCurrentPathField.getValue());

        // Get transfer type
        String transferType = (String)parameters.get("transferType");
        if (transferType == null) {
            transferType = this.transferType;
        } else {
            this.transferType = transferType;

        }
        // Display transfer text...
        if (transferType.equals(COPY_TASK_TYPE)) {
            transferText.setValue("Copy Files");
        } else {
            transferText.setValue("Move Files");
        }

        // Get name of source...
        String srcFileBrowser = (String)parameters.get("srcFileBrowser");
        if (srcFileBrowser == null) {
            srcFileBrowser = this.srcFileBrowser;
        } else {
            this.srcFileBrowser = srcFileBrowser;
        }
        // Set next state
        setNextJspPage(srcFileBrowser);
    }

    public void doTransferFilesApply(Map parameters) throws PortletException {
        log.debug("Entering doTransferFilesApply");
        try {

            // Don't want session reference to file transfer list...
//            List dirLocationSet = new Vector();
//            for (int ii = 0; ii < srcTransferFileList.size(); ++ii) {
//                FileLocation nextLocation = (FileLocation)srcTransferFileList.get(ii);
//                String nextUrl = nextLocation.getUrl();
//                dirLocationSet.add(new FileLocation(nextUrl));
//            }

            log.debug("Initiating transfser task...");
            String srcHost = srcCurrentHostField.getValue();
            User user = getUser();
            FileBrowser srcBrowser = fileBrowserService.createFileBrowser(user, srcHost);
//            FileLocationSet srcFileSet = new FileLocationSet(dirLocationSet);
            FileLocationSet srcFileSet = new FileLocationSet(srcTransferFileList);
            String dstHost = dstCurrentHostField.getValue();
            String dstPath = dstCurrentPathField.getValue();
            FileBrowser dstBrowser = fileBrowserService.createFileBrowser(user, dstHost);
            FileLocation dstLocation = dstBrowser.createFileLocation(dstPath);
            log.debug("Transferring " + srcTransferFileList.size() + " files");
            if (transferType.equals(COPY_TASK_TYPE)) {
                FileCopy copy = srcBrowser.copy(srcFileSet, dstLocation);
                log.debug("Submitted copy task...");
                copy.waitFor();
                if (copy.getTaskStatus().equals(TaskStatus.FAILED)) {
                    messageBox.appendText(copy.getTaskStatusMessage());
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    setNextJspPage(srcFileBrowser);
                } else {
                    setNextState(FileBrowserComp.class, "reFileBrowser", parameters);
                }
            } else {
                FileMove move = srcBrowser.move(srcFileSet, dstLocation);
                log.debug("Submitted move task...");
                move.waitFor();
                if (move.getTaskStatus().equals(TaskStatus.FAILED)) {
                    messageBox.appendText(move.getTaskStatusMessage());
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    setNextJspPage(srcFileBrowser);
                } else {
                    setNextState(FileBrowserComp.class, "reFileBrowser", parameters);
                }
            }
        } catch (Exception e) {
            log.error("Unable to transfer files", e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextJspPage(srcFileBrowser);
        }
        log.debug("Exiting doTransferFilesApply");
    }

    public void doTransferFilesCancel(Map parameters) throws PortletException {
        setNextState(FileBrowserComp.class);
    }

    protected void setNextJspPage(String srcFileBrowser) {
        if (srcFileBrowser.equals(FILE_BROWSER_1)) {
            // Set next state
            setNextState("/jsp/file/Fb1Fb2TransferComp.jsp");
        } else {
            // Set next state
            setNextState("/jsp/file/Fb2Fb1TransferComp.jsp");

        }
    }
}

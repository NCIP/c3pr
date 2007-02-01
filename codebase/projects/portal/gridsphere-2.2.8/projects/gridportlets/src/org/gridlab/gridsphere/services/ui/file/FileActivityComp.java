package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.file.tasks.*;
import org.gridlab.gridsphere.services.file.FileTask;
import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.file.sets.FilePatternSet;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileActivityComp.java,v 1.1.1.1 2007-02-01 20:41:59 kherm Exp $
 */

public class FileActivityComp extends FileComp {

    public static final String DELETED_MESSAGE_KEY = "portlets.file.message_tasks_deleted";

    private transient static PortletLog log = SportletLog.getInstance(FileActivityComp.class);
    CheckBoxBean fileTaskOidCheckBox = null;
    Boolean deletedFlag = Boolean.FALSE;

    public FileActivityComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        fileTaskOidCheckBox = createCheckBoxBean("fileTaskOidCheckBox");
        // Set default action
        setDefaultAction("doShowActivity");
        setRenderAction("doShowActivity");
        setDefaultJspPage("/jsp/file/FileActivityComp.jsp");
    }

    public void doShowActivity(Map parameters) throws PortletException {
        log.debug("doShowActivity");
        // Set next state
        setPageAttribute("fileTaskList", null);
        setNextState(defaultJspPage);
        // Set file task list
        List fileTaskList = fileBrowserService.getFileTasks(getUser(), FileBrowserTaskType.INSTANCE);
        setPageAttribute("fileTaskList", fileTaskList);
        // Set deleted flag false
        deletedFlag = Boolean.FALSE;
        setPageAttribute("deletedFlag", deletedFlag);
    }

    public void doDeleteActivity(Map parameters) throws PortletException {
        log.debug("doDeleteActivity");

        // Set next state
        setPageAttribute("fileTaskList", null);
        setNextState(defaultJspPage);
        // Delete file task list...
        List fileTaskList = new Vector();
        List fileTaskOidList = fileTaskOidCheckBox.getSelectedValues();
        Iterator fileTaskOidIterator = fileTaskOidList.iterator();
        while (fileTaskOidIterator.hasNext()) {
            String fileTaskOid = (String)fileTaskOidIterator.next();
            FileTask fileTask = fileBrowserService.getFileTask(fileTaskOid);
            if (fileTask != null) {
                try {
                    fileBrowserService.deleteFileTask(fileTask);
                    fileTaskList.add(fileTask);
                } catch (Exception e) {
                    log.error("Unable to delete file task " + fileTaskOid, e);
                }
            }
        }
        // Set message box
        messageBox.appendText(getResourceString(DELETED_MESSAGE_KEY));
        messageBox.setMessageType(TextBean.MSG_SUCCESS);
        // Clear selections
        fileTaskOidCheckBox.clearSelectedValues();
        // Set file task list
        setPageAttribute("fileTaskList", fileTaskList);
        // Set file task list
        deletedFlag = Boolean.TRUE;
        setPageAttribute("deletedFlag", deletedFlag);
    }

    public static String getFileTaskSummary(FileTask task, String defaultValue) {
        String summary = defaultValue;
        if (task instanceof FileTransfer) {
            FileTransfer fileTransfer = (FileTransfer)task;
            FileTransferSpec fileTransferSpec = (FileTransferSpec)fileTransfer.getTaskSpec();
            if (fileTransferSpec != null) {
                summary = getFileSetSummary(fileTransferSpec.getFileSet(), defaultValue);
            }
        } else if (task instanceof FileUpload) {
            FileUpload fileUpload = (FileUpload)task;
            FileUploadSpec fileUploadSpec = (FileUploadSpec)fileUpload.getTaskSpec();
            if (fileUploadSpec != null) {
                FileLocation fileLocation = fileUploadSpec.getUploadLocation();
                if (fileLocation != null) {
                    summary = fileLocation.getUrlWithoutQuery();
                }
            }
        } else if (task instanceof FileDownload) {
            FileDownload fileDownload = (FileDownload)task;
            FileDownloadSpec fileDownloadSpec = (FileDownloadSpec)fileDownload.getTaskSpec();
            if (fileDownloadSpec != null) {
                FileLocation fileLocation = fileDownloadSpec.getFileLocation();
                if (fileLocation != null) {
                    summary = fileLocation.getUrlWithoutQuery();
                }
            }
        } else if (task instanceof FileDeletion) {
            FileDeletion fileDeletion = (FileDeletion)task;
            FileDeletionSpec fileDeletionSpec = (FileDeletionSpec)fileDeletion.getTaskSpec();
            if (fileDeletionSpec != null) {
                summary = getFileSetSummary(fileDeletionSpec.getFileSet(), defaultValue);
            }
        } else if (task instanceof FileMakeDir) {
            FileMakeDir fileMakeDir = (FileMakeDir)task;
            FileMakeDirSpec fileMakeDirSpec = (FileMakeDirSpec)fileMakeDir.getTaskSpec();
            if (fileMakeDirSpec != null) {
                FileLocation fileLocation = fileMakeDirSpec.getParentLocation();
                if (fileLocation != null) {
                    summary = fileLocation.getUrlWithoutQuery();
                    if (summary.endsWith("/")) {
                        summary += '/' + fileMakeDirSpec.getDirectoryName();
                    } else {
                        summary += fileMakeDirSpec.getDirectoryName();
                    }
                }
            }
        } else if (task instanceof FileNameChange) {
            FileNameChange fileNameChange = (FileNameChange)task;
            FileNameChangeSpec fileNameChangeSpec = (FileNameChangeSpec)fileNameChange.getTaskSpec();
            if (fileNameChangeSpec != null) {
                FileLocation fileLocation = fileNameChangeSpec.getFileLocation();
                if (fileLocation != null) {
                    summary = fileLocation.getUrlWithoutQuery();
                    summary += "->" + fileNameChangeSpec.getNewFileName();
                }
            }
        } else if (task instanceof FileListing) {
            FileListing fileListing = (FileListing)task;
            FileListingSpec fileListingSpec = (FileListingSpec)fileListing.getTaskSpec();
            if (fileListingSpec != null) {
                summary = getFileSetSummary(fileListingSpec.getFileSet(), defaultValue);
            }
        }
        return summary;
    }

    public static String getFileSetSummary(FileSet fileSet, String defaultValue) {
        String summary = defaultValue;
        if (fileSet == null) {
            return summary;
        } else if (fileSet instanceof FileLocationSet) {
            FileLocationSet fileLocationSet = (FileLocationSet)fileSet;
            List fileLocations = fileLocationSet.getFileLocations();
            int size = fileLocations.size();
            if (size > 0) {
                FileLocation fileLocation = (FileLocation)fileLocations.get(0);
                summary = fileLocation.getUrlWithoutQuery();
                if (size > 1) {
                    summary += "...";
                }
            }
        } else {
            FilePatternSet filePatternSet = (FilePatternSet)fileSet;
            FileLocation fileLocation = filePatternSet.getFileLocation();
            if (fileLocation != null) {
                summary = fileLocation.getUrlWithoutQuery();
                String includePattern = filePatternSet.getIncludePattern();
                if (includePattern != null) {
                   summary += "; +" + includePattern;
                }
                String excludePattern = filePatternSet.getExcludePattern();
                if (excludePattern != null) {
                   summary += "; -" + excludePattern;
                }
            }
        }
        return summary;
    }
}

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileListComp.java,v 1.1.1.1 2007-02-01 20:42:01 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.services.file.FileException;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileType;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentState;
import org.gridlab.gridsphere.services.ui.security.gss.ActiveCredentialFilter;

import java.util.List;
import java.util.Vector;
import java.util.Map;
import java.io.File;


public class FileListComp extends FileComp {

    public static final String DEFAULT_ACTION = "doListFiles";

    public static final String SHOW_MAKE_DIR_ACTION = "showMakeDirAction";
    public static final String SHOW_RENAME_ACTION = "showRenameAction";
    public static final String SHOW_DELETE_ACTION = "showDeleteAction";
    public static final String SHOW_VIEW_ACTION = "showViewAction";
    public static final String SHOW_UPLOAD_ACTION = "showUploadAction";
    public static final String SHOW_DOWNLOAD_ACTION = "showDownloadAction";

    public static final String RESOURCE_ALIGN = "resourceAlign";

    public static final String PHYSICAL_BROWSER_MODE = "PHYSICAL_BROWSER_MODE";
    public static final String LOGICAL_BROWSER_MODE = "LOGICAL_BROWSER_MODE";

    public static final String MAKEDIR_MESSAGE_KEY = "portlets.file.message_makedir";
    public static final String MAKEDIR_SELECT_MESSAGE_KEY = "portlets.file.message_makedir_select";
    public static final String RENAME_MESSAGE_KEY = "portlets.file.message_rename";
    public static final String RENAME_SELECT_MESSAGE_KEY = "portlets.file.message_rename_select";
    public static final String DELETE_MESSAGE_KEY = "portlets.file.message_delete";
    public static final String DELETE_SELECT_MESSAGE_KEY = "portlets.file.message_delete_select";
    public static final String EDIT_MESSAGE_KEY = "portlets.file.message_edit";
    public static final String EDIT_SELECT_MESSAGE_KEY = "portlets.file.message_edit_select";
    public static final String EDIT_DIRECTORY_MESSAGE_KEY = "portlets.file.message_edit_directory";
    public static final String UPLOAD_MESSAGE_KEY = "portlets.file.message_upload";
    public static final String UPLOAD_SELECT_MESSAGE_KEY = "portlets.file.message_upload_select";
    public static final String DOWNLOAD_SELECT_MESSAGE_KEY = "portlets.file.message_download_select";

    private transient static PortletLog log = SportletLog.getInstance(FileListComp.class);

    private TextBean modeText = null;

    protected static final int LIST_FILES_IN_DIR = 0;
    protected static final int LIST_FILES_IN_SELECTION = 1;
    protected static final int LIST_FILES_IN_PATH = 2;

    private boolean errorOccured = false;

    private FileLocation fileResourceLoc = null;

    // Form objects
    protected HiddenFieldBean currentHostField = null;
    private HiddenFieldBean currentPathField = null;
    private ListBoxBean fileHostList = null;
    private ListBoxBean fileDirList = null;
    private ListBoxBean filePathList = null;
    private TextFieldBean filePathField = null;
    private String resourceAlign = "left";

    private Boolean showMakeDirAction = Boolean.TRUE;
    private Boolean showRenameAction = Boolean.TRUE;
    private Boolean showDeleteAction = Boolean.TRUE;
    private Boolean showViewAction = Boolean.TRUE;
    private Boolean showUploadAction = Boolean.TRUE;
    private Boolean showDownloadAction = Boolean.TRUE;

    public FileListComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Set mode text
        modeText = createTextBean("modeText");
        modeText.setValue(PHYSICAL_BROWSER_MODE);
        // Current host
        currentHostField = createHiddenFieldBean("currentHostField");
        // Currrent path
        currentPathField = createHiddenFieldBean("currentPathField");
        // File host list
        fileHostList = createListBoxBean("fileHostList");
        fileHostList.setReadOnly(false);
        fileHostList.setSize(1);
        // File dir list
        fileDirList = createListBoxBean("fileDirList");
        // Selected files
        filePathList = createListBoxBean("filePathList");
        filePathList.setReadOnly(false);
        filePathList.setMultipleSelection(true);
        filePathList.setSize(10);
        // File path field
        filePathField = createTextFieldBean("filePathField");
        filePathField.setReadOnly(false);
        // Register credential actions
        String activeCredentialMethods[] = { "doListFiles",
                                             "doListFilesInDir",
                                             "doListFilesInPath",
                                             "doMakeDir",
                                             "doRenameFile",
                                             "doDeleteFiles" };
        container.addActionFilter(this,  activeCredentialMethods, ActiveCredentialFilter.getInstance());
        // Set default action
        setDefaultAction(DEFAULT_ACTION);
        setDefaultJspPage("/jsp/file/FileListComp.jsp");
    }

    public void onLoad() throws PortletException {
        super.onLoad();
        log.debug("onLoad(" + compId + ")");
        if (currentHostField.getValue() == null) {
            currentHostField.setValue("");
        }
        if (currentPathField.getValue() == null) {
            currentPathField.setValue("");
        }
    }

    public void onStore() throws PortletException {
        super.onStore();
        setPageAttribute(RESOURCE_ALIGN, resourceAlign);
        setPageAttribute(SHOW_MAKE_DIR_ACTION, showMakeDirAction);
        setPageAttribute(SHOW_RENAME_ACTION, showRenameAction);
        setPageAttribute(SHOW_DELETE_ACTION, showDeleteAction);
        setPageAttribute(SHOW_VIEW_ACTION, showViewAction);
        setPageAttribute(SHOW_UPLOAD_ACTION, showUploadAction);
        setPageAttribute(SHOW_DOWNLOAD_ACTION, showDownloadAction);
    }

    public String getResourceAlign() {
        return resourceAlign;
    }

    public void setResourceAlign(String resourceAlign) {
        this.resourceAlign = resourceAlign;
    }

    public Boolean getShowMakeDirAction() {
        return showMakeDirAction;
    }

    public void setShowMakeDirAction(Boolean showMakeDirAction) {
        this.showMakeDirAction = showMakeDirAction;
    }

    public Boolean getShowRenameAction() {
        return showRenameAction;
    }

    public void setShowRenameAction(Boolean showRenameAction) {
        this.showRenameAction = showRenameAction;
    }

    public Boolean getShowDeleteAction() {
        return showDeleteAction;
    }

    public void setShowDeleteAction(Boolean showDeleteAction) {
        this.showDeleteAction = showDeleteAction;
    }

    public Boolean getShowViewAction() {
        return showViewAction;
    }

    public void setShowViewAction(Boolean showViewAction) {
        this.showViewAction = showViewAction;
    }

    public Boolean getShowUploadAction() {
        return showUploadAction;
    }

    public void setShowUploadAction(Boolean showUploadAction) {
        this.showUploadAction = showUploadAction;
    }

    public Boolean getShowDownloadAction() {
        return showDownloadAction;
    }

    public void setShowDownloadAction(Boolean showDownloadAction) {
        this.showDownloadAction = showDownloadAction;
    }

    public void doSetActions(Map parameters) throws PortletException {
        // Make dir action
        Boolean answer = (Boolean)parameters.get(SHOW_MAKE_DIR_ACTION);
        if (answer != null) showMakeDirAction = answer;
        // Rename action
        answer = (Boolean)parameters.get(SHOW_RENAME_ACTION);
        if (answer != null) showRenameAction = answer;
        // Delete action
        answer = (Boolean)parameters.get(SHOW_DELETE_ACTION);
        if (answer != null) showDeleteAction = answer;
        // View action
        answer = (Boolean)parameters.get(SHOW_VIEW_ACTION);
        if (answer != null) showViewAction = answer;
        // Upload action
        answer = (Boolean)parameters.get(SHOW_UPLOAD_ACTION);
        if (answer != null) showUploadAction = answer;
        // Download action
        answer = (Boolean)parameters.get(SHOW_DOWNLOAD_ACTION);
        if (answer != null) showDownloadAction = answer;
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
            try {
                newListFiles(parameters);
            } catch (Exception e) {
                messageBox.appendText(e.getMessage());
            }
        }
    }

    public void doListFiles(Map parameters) throws PortletException {
        try {
            // List files for now
            doListFiles(LIST_FILES_IN_SELECTION, parameters);
        } catch (Exception e) {
            log.error("Error performing doListFiles()", e);
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
        }
    }

    public void doListFilesInDir(Map parameters) throws PortletException {
        log.debug("Calling doListFilesInDir");
        try {
            // List files for now
            doListFiles(LIST_FILES_IN_DIR, parameters);
        } catch (Exception e) {
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
        }
    }

    public void doListFilesInPath(Map parameters) throws PortletException {
        log.debug("Calling doListFilesInPath");
        try {
            // List files for now
            doListFiles(LIST_FILES_IN_PATH, parameters);
        } catch (Exception e) {
            messageBox.appendText(e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
        }
    }

    protected void doListFiles(int source, Map parameters) throws Exception {
        log.debug("doListFiles(" + compId + ")");

        errorOccured = false;

        // Set actions to display
        doSetActions(parameters);

        // Get selected resource
        FileLocation fileResourceLocation = unloadFileResourceLocation(fileHostList);

        // (Re)load file resource list
        User user = getUser();
        List fileResources = fileBrowserService.getFileResources(user);
        loadFileResourceList(fileHostList, fileResources, fileResourceLocation);
        // If no file resource selected...
        if (fileResourceLocation == null) {
            fileResourceLoc = null;
            currentPathField.setValue("");
            fileDirList.clear();
            filePathList.clear();
            filePathField.setValue("");
        // Else setup file list...
        } else {

            // Unload selected file from list
            FileLocation dirLocation = null;
            if (fileResourceLoc == null || !fileResourceLoc.equals(fileResourceLocation)) {
                log.debug("Host name has changed to " + fileResourceLocation.getHost());
                fileResourceLoc = fileResourceLocation;
                currentPathField.setValue("");
                filePathField.setValue("");
                dirLocation = new FileLocation("", FileType.DIRECTORY);
            } else {
                if (source == LIST_FILES_IN_DIR) {
                    dirLocation = unloadFileDirLocation(fileDirList);
                } else if (source == LIST_FILES_IN_PATH) {
                    String filePath = filePathField.getValue().trim();
                    log.debug("Listing files in directory " + filePath);
                    dirLocation = new FileLocation(filePath, FileType.DIRECTORY);
                } else {
                    log.debug("Listing files in current selection..." + filePathList.getSelectedName());
                    dirLocation = unloadFilePathLocation(filePathList, currentPathField);
                }
            }
            String selectedFileName = "";
            // If selected path is a normal file, re-list current path
            if (dirLocation.getIsFile()) {
                log.debug("Selected path is a normal file, re-listing current path");
                selectedFileName = dirLocation.getFileName();
                String currentPath = currentPathField.getValue();
                dirLocation = new FileLocation(currentPath, FileType.DIRECTORY);
            }
            // List files in the selected directory
            List fileLocations = null;
            try {
                log.debug("List files at " + dirLocation.getUrl());
                FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, fileResourceLocation);
                fileLocations = fileBrowser.list(dirLocation);
                int numLocations = fileLocations.size() ;
                if (numLocations > 1) { // Means we have at least "." and ".."
                    dirLocation = (FileLocation)fileLocations.get(0);
                }
            } catch (Exception e) {
                log.error("Error occured during list", e);
                messageBox.appendText(e.getMessage());
                messageBox.setMessageType(TextBean.MSG_ERROR);
                fileLocations = new Vector();

                errorOccured = true;
            }
            log.debug("Dir location is " + dirLocation.getUrl());

            String dirPath = dirLocation.getFilePath();


            // Get rid of "." if is in dir location
            if (dirPath.endsWith("/.")) {
                int length = dirPath.length();
                if (length == 2) {
                    dirPath = "/";
                } else {
                    dirPath = dirPath.substring(0, length-2);
                }
            }

            // (Re)load current path field
            currentPathField.setValue(dirPath);
            // (Re)load directory list
            loadFileDirList(fileDirList, dirPath);
            // (Re)load file path field
            filePathField.setValue(dirPath);
            // Load file path list
            loadFilePathList(filePathList, fileLocations, selectedFileName);
        }
        setNextState(defaultJspPage);
    }

    public void reListFiles(Map parameters) throws PortletException {

        // Set next state
        setNextState(defaultJspPage);

        // Don't relist if an error occured previously
        if (errorOccured) return;

        // Set actions to display
        doSetActions(parameters);

        // File resource Location
        FileLocation fileResourceLocParam = (FileLocation)parameters.get("fileResourceLocParam");
        if (fileResourceLocParam != null) {
            fileResourceLoc = fileResourceLocParam;
        }
        // File path
        String filePathParam = (String)parameters.get("filePathParam");
        if (filePathParam != null) {
            currentPathField.setValue(filePathParam);
        }
        // Load file host list
        User user = getUser();
        List fileResources = fileBrowserService.getFileResources(user);
        //loadHostList(fileHostList, fileResources, fileHost);
        loadFileResourceList(fileHostList, fileResources, fileResourceLoc);

        // Load file dir list
        String filePath = currentPathField.getValue();
        loadFileDirList(fileDirList, filePath);

        // Load file path list
        List fileLocations = null;
        //if (fileHost.equals("")) {
        if (fileResourceLoc == null) {
            fileLocations = new Vector();
        } else {
            try {
                FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, fileResourceLoc);
                fileLocations = fileBrowser.list(filePath);
            } catch (FileException e) {
                messageBox.appendText(e.getMessage());
                messageBox.setMessageType(TextBean.MSG_ERROR);
                fileLocations = new Vector();
            }
        }
        String selectedFileName = (String)parameters.get("selectedFileName");
        if (selectedFileName == null) {
            loadFilePathList(filePathList, fileLocations, "");
        } else {
            loadFilePathList(filePathList, fileLocations, selectedFileName);
        }

        // Load file path field
        filePathField.setValue(filePath);

    }

    public void newListFiles(Map parameters) throws PortletException {
        currentPathField.setValue("");
        fileHostList.clear();
        fileDirList.clear();
        filePathList.clear();
        filePathField.setValue("");
        doListFiles(parameters);
    }

    public void doRenderAction(Map parameters) throws PortletException {

        if (errorOccured)  {
            // Re list files if no error
            reListFiles(parameters);
        }

    }

    public void doMakeDir(Map parameters) throws PortletException {
        log.debug("doMakeDir(" + compId + ")");
        if (fileResourceLoc == null) {
            messageBox.appendText(getResourceString(MAKEDIR_SELECT_MESSAGE_KEY));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
        } else {

            FileMakeDirDialog dialog
                    = (FileMakeDirDialog)getFileDialog(FileMakeDirDialog.class);
            dialog.setFileResourceLoc(fileResourceLoc);
            dialog.setFilePath(currentPathField.getValue());
            dialog.setMessageValue(getResourceString(MAKEDIR_MESSAGE_KEY));
            dialog.showOkCancelDialog(new ActionComponentState(getClass(), "doMakeDirApply"),
                                      new ActionComponentState(getClass(), RENDER_ACTION));
        }
    }

    public void doMakeDirApply(Map parameters) throws PortletException {
        log.debug("doMakeDirApply(" + compId + ")");

        FileMakeDirDialog dialog
                = (FileMakeDirDialog)getFileDialog(FileMakeDirDialog.class);
        String newDirName = dialog.getNewDirName();
        log.debug("New dir name is " + newDirName);
        String dirPath = currentPathField.getValue();
        if (dirPath.endsWith("/")) {
            dirPath += newDirName;
        } else {
            dirPath += '/' + newDirName;
        }
        parameters.put("filePathParam", dirPath);
        reListFiles(parameters);
    }

    public void doRenameFile(Map parameters) throws PortletException {
        log.debug("doRenameFile(" + compId + ")");
        if (fileResourceLoc == null) {
            messageBox.appendText(getResourceString(RENAME_SELECT_MESSAGE_KEY));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
        } else {
            // File path field
            FileLocation fileLocation = unloadFileLocation(filePathList);
            if (fileLocation == null) {
                messageBox.appendText(getResourceString(RENAME_SELECT_MESSAGE_KEY));
                messageBox.setMessageType(TextBean.MSG_ERROR);
                setNextState(defaultJspPage);
            } else {
                String filePath = fileLocation.getFilePath();
                if (filePath.equals("") || filePath.endsWith("/..") || filePath.endsWith("/.")) {
                    messageBox.appendText(getResourceString(RENAME_SELECT_MESSAGE_KEY));
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                    setNextState(defaultJspPage);
                } else {

                    FileRenameDialog dialog
                            = (FileRenameDialog)getFileDialog(FileRenameDialog.class);
                    dialog.setFileResourceLoc(fileResourceLoc);
                    dialog.setFilePath(filePath);
                    dialog.setMessageValue(getResourceString(RENAME_MESSAGE_KEY));
                    dialog.showOkCancelDialog(new ActionComponentState(getClass(), "doRenameFileApply"),
                                              new ActionComponentState(getClass(), RENDER_ACTION));
                }
            }
        }
    }

    public void doRenameFileApply(Map parameters) throws PortletException {
        log.debug("doRenameFileApply(" + compId + ")");

        FileRenameDialog dialog
                = (FileRenameDialog)getFileDialog(FileRenameDialog.class);
        String newFileName = dialog.getNewFileName();
        log.debug("New file name is " + newFileName);
        parameters.put("selectedFileName", newFileName);
        reListFiles(parameters);
    }

    public void doDeleteFiles(Map parameters) throws PortletException {
        log.debug("doDeleteFiles(" + compId + ")");
        if (fileResourceLoc == null) {
            messageBox.appendText(getResourceString(DELETE_SELECT_MESSAGE_KEY));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
        } else {
            // File path field
            List fileLocationList = unloadFilePathLocations(filePathList);
            if (fileLocationList.size() == 0) {
                messageBox.appendText(getResourceString(DELETE_SELECT_MESSAGE_KEY));
                messageBox.setMessageType(TextBean.MSG_ERROR);
                setNextState(defaultJspPage);
            } else {

                FileDeleteDialog dialog
                        = (FileDeleteDialog)getFileDialog(FileDeleteDialog.class);
                dialog.setFileResourceLoc(fileResourceLoc);
                dialog.setFilePath(currentPathField.getValue());
                dialog.setFileList(fileLocationList);
                dialog.setMessageValue(getResourceString(DELETE_MESSAGE_KEY));
                dialog.showOkCancelDialog(new ActionComponentState(getClass(), "reListFiles"),
                                          new ActionComponentState(getClass(), RENDER_ACTION));
            }
        }
    }

    public void doUploadFile(Map parameters) throws PortletException {
        log.debug("doUploadFile");

        if (fileResourceLoc == null) {
            messageBox.appendText(getResourceString(UPLOAD_SELECT_MESSAGE_KEY));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
        } else {
            FileUploadDialog dialog
                    = (FileUploadDialog)getFileDialog(FileUploadDialog.class);
            dialog.setFileResourceLoc(fileResourceLoc);
            dialog.setFilePath(currentPathField.getValue());
            dialog.setMessageValue(getResourceString(UPLOAD_MESSAGE_KEY));
            dialog.showOkCancelDialog(new ActionComponentState(getClass(), "doUploadSelect"),
                                      new ActionComponentState(getClass(), RENDER_ACTION));
        }
    }

    public void doUploadSelect(Map parameters) throws PortletException {
        log.debug("doUploadSelect");

        setNextState(defaultJspPage);

        FileUploadDialog dialog
                = (FileUploadDialog)getFileDialog(FileUploadDialog.class);
        String uploadedFilePath = dialog.getUploadedFilePath();
        log.debug("Uploaded file path is " + uploadedFilePath);

        // Load file host list
        User user = getUser();
        List fileResources = fileBrowserService.getFileResources(user);
        //loadHostList(fileHostList, fileResources, fileHost);
        loadFileResourceList(fileHostList, fileResources, fileResourceLoc);

        // Load file dir list
        String filePath = currentPathField.getValue();
        filePathField.setValue(filePath);

        loadFileDirList(fileDirList, filePath);

        // Load file path list
        List fileLocations = null;
        if (fileResourceLoc == null) {
            fileLocations = new Vector();
        } else {
            try {
                //FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, fileHost);
                FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, fileResourceLoc);
                File uploadedFile = new File(uploadedFilePath);
                String uploadedFileName = uploadedFile.getName();
                String targetFilePath = filePath;
                if (targetFilePath.endsWith("/"))  {
                    targetFilePath += uploadedFileName;
                } else {
                    targetFilePath += '/' + uploadedFileName;
                }
                FileLocation targetFileLoc = fileBrowser.createFileLocation(targetFilePath);
                fileBrowser.upload(uploadedFilePath, targetFileLoc).waitFor();
                fileLocations = fileBrowser.list(filePath);
            } catch (Exception e) {
                log.error("Unable to relist files ", e);
                messageBox.appendText(e.getMessage());
                messageBox.setMessageType(TextBean.MSG_ERROR);
                fileLocations = new Vector();
            }
        }

        // Load file path list
        loadFilePathList(filePathList, fileLocations, "");
    }

    public void doDownloadFile(Map parameters) throws PortletException {
        log.debug("doDownloadFile");

        if (fileResourceLoc == null) {
            messageBox.appendText(getResourceString(DOWNLOAD_SELECT_MESSAGE_KEY));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
            return;
        }
        // File path field
        FileLocation fileLocation = unloadFileLocation(filePathList);
        if (fileLocation == null) {
            messageBox.appendText(getResourceString(DOWNLOAD_SELECT_MESSAGE_KEY));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
            return;
        }
        if (fileLocation.getIsDirectory()) {
            messageBox.appendText("Directories cannot be downloaded");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
            return;
        }
//        downloadFile(fileLocation);
//        setNextState(defaultJspPage);
        FileDownloadDialog dialog
                = (FileDownloadDialog)getFileDialog(FileDownloadDialog.class);
        dialog.setFileLocation(fileLocation);
        dialog.setMessageValue(getResourceString(DOWNLOAD_SELECT_MESSAGE_KEY));
        dialog.showOkDialog(new ActionComponentState(getClass(), RENDER_ACTION));
    }

    public void doEditFile(Map parameters) throws PortletException {
        log.debug("doEditFile");
        if (fileResourceLoc == null) {
            messageBox.appendText(getResourceString(EDIT_SELECT_MESSAGE_KEY));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
            return;
        }
        FileLocation fileLocation = unloadFileLocation(filePathList);
        if (fileLocation == null) {
            messageBox.appendText(getResourceString(EDIT_SELECT_MESSAGE_KEY));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
            return;
        }
        if (fileLocation.getIsDirectory()) {
            messageBox.appendText(getResourceString(EDIT_DIRECTORY_MESSAGE_KEY));
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
            return;
        }
        FileEditorDialog dialog
                = (FileEditorDialog)getFileDialog(FileEditorDialog.class);
        dialog.setMessageValue(getResourceString(EDIT_MESSAGE_KEY));
        dialog.setFileLocation(fileLocation);
        dialog.showOkCancelDialog(new ActionComponentState(getClass(), "doEditFileApply"),
                                  new ActionComponentState(getClass(), RENDER_ACTION));
    }

    public void doEditFileApply(Map parameters) throws PortletException {
        setNextState(defaultJspPage);
    }

    public FileLocation getFileResourceLoc() {
        return fileResourceLoc;
    }

    public void setFileResourceLoc(FileLocation fileResourceLoc) {
        this.fileResourceLoc = fileResourceLoc;
    }

    public HiddenFieldBean getCurrentPathField() {
        return currentPathField;
    }

    public void setCurrentPathField(HiddenFieldBean currentPathField) {
        this.currentPathField = currentPathField;
    }

    public ListBoxBean getFileHostList() {
        return fileHostList;
    }

    public void setFileHostList(ListBoxBean fileHostList) {
        this.fileHostList = fileHostList;
    }

    public ListBoxBean getFileDirList() {
        return fileDirList;
    }

    public void setFileDirList(ListBoxBean fileDirList) {
        this.fileDirList = fileDirList;
    }

    public ListBoxBean getFilePathList() {
        return filePathList;
    }

    public void setFilePathList(ListBoxBean filePathList) {
        this.filePathList = filePathList;
    }

    public TextFieldBean getFilePathField() {
        return filePathField;
    }

    public void setFilePathField(TextFieldBean filePathField) {
        this.filePathField = filePathField;
    }

    public boolean isErrorOccured() {
        return errorOccured;
    }

    public void setErrorOccured(boolean errorOccured) {
        this.errorOccured = errorOccured;
    }

    public TextBean getModeText() {
        return modeText;
    }

    public void setModeText(TextBean modeText) {
        this.modeText = modeText;
    }
}

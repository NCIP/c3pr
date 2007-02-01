package org.gridlab.gridsphere.services.ui.file;

import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.tasks.FileCopy;
import org.gridlab.gridsphere.services.file.logical.LogicalFileBrowserService;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileType;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.resources.system.LocalHostBrowser;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.services.common.Location;
import org.gridlab.gridsphere.provider.portletui.beans.*;

import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.MalformedURLException;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileComp.java,v 1.1.1.1 2007-02-01 20:42:00 kherm Exp $
 */

public class FileComp extends ActionComponent {

    private transient static PortletLog log = SportletLog.getInstance(FileComp.class);

    public static final String DOWNLOAD_FILE_PATH_ATTRIBUTE = "downloadFilePath";
    public static final String DOWNLOAD_FILE_URL_ATTRIBUTE = "downloadFileUrl";

    protected Boolean displayModeFlag = Boolean.FALSE;
    protected Boolean isLogicalMode = Boolean.FALSE;

    protected FileBrowserService fileBrowserService = null;

    public FileComp(ActionComponentFrame container, String compName) {
        super(container, compName);
    }

    public void onInit()
            throws PortletException {
        setDisplayModeFlag();
        if (isLogicalMode.booleanValue()) {
            log.debug("Getting instance of logical file browser service");
            fileBrowserService = (LogicalFileBrowserService)
                    getPortletService(LogicalFileBrowserService.class);
        } else {
            log.debug("Getting instance of physical file browser service");
            fileBrowserService = (FileBrowserService)
                    getPortletService(FileBrowserService.class);
        }
    }

    public void onStore()
            throws PortletException {
        setPageAttribute("displayModeFlag", displayModeFlag);
    }

    /**
     * Only display physical/logical file options if we have logical file resources
     */
    public void setDisplayModeFlag() {
        log.debug("Setting display mode flag");
        displayModeFlag = Boolean.FALSE;
        try {
            LogicalFileBrowserService logicalFileBrowserService =
                    (LogicalFileBrowserService)getPortletService(LogicalFileBrowserService.class);
            List fileResources = logicalFileBrowserService.getFileResources(getUser());
            if (fileResources.size() > 0) {
                log.debug("There are " + fileResources.size() + " logical file resources");
                displayModeFlag = Boolean.TRUE;
            }
        } catch (Exception e) {
            log.error("Unable to get logical file browser service", e);
        }
    }

    public Boolean getIsLogicalFileMode()
            throws PortletException {
        return isLogicalMode;
    }

    public void setIsLogicalFileMode(Boolean flag)
            throws PortletException {
        isLogicalMode = flag;
        if (isLogicalMode.booleanValue()) {
            log.debug("Getting instance of logical file browser service");
            fileBrowserService = (LogicalFileBrowserService)
                    getPortletService(LogicalFileBrowserService.class);
        } else {
            log.debug("Getting instance of physical file browser service");
            fileBrowserService = (FileBrowserService)
                    getPortletService(FileBrowserService.class);
        }
    }

    public FileDialog getFileDialog(Class dialogClass) throws PortletException {
        FileDialog fileDialog = (FileDialog)getActionDialog(dialogClass);
        fileDialog.setIsLogicalFileMode(isLogicalMode);
        return fileDialog;
    }

    protected static void loadFileResourceList(ListBoxBean fileResourceListBox,
                                               List fileResourceList,
                                               Location selectedLocation) {
        log.debug("Adding "+ fileResourceList.size() + " resources to list box");
        // (Re)set file resource list properties
        fileResourceListBox.setMultipleSelection(false);
        fileResourceListBox.setSize(1);
        fileResourceListBox.clear();
        // Add default (none) selection
        ListBoxItemBean fileResourceItem = new ListBoxItemBean();
          fileResourceItem.setValue("&lt;Select Resource&gt;");
        fileResourceListBox.addBean(fileResourceItem);
        // (Re)build list with given file resources
        for (Iterator fileResources = fileResourceList.iterator(); fileResources.hasNext();) {
            FileResource fileResource = (FileResource) fileResources.next();
            fileResourceItem = new ListBoxItemBean();
            HardwareResource hardwareResource = fileResource.getHardwareResource();
            Location fileResourceLocation = fileResource.getLocation();
            log.debug("Adding "+ fileResourceLocation.getUrl() + " to list box " + fileResourceLocation.getClass().getName());
            String fileResourceLabel = hardwareResource.getLabel();
            fileResourceItem.setName(fileResourceLocation.getUrl());
            fileResourceItem.setValue(fileResourceLabel);
            // Set selected if resource name equals provided selection
            if (selectedLocation != null && fileResourceLocation.equals(selectedLocation)) {
                fileResourceItem.setSelected(true);
            }
            fileResourceListBox.addBean(fileResourceItem);
        }
    }

    protected static FileLocation unloadFileResourceLocation(ListBoxBean fileHostList) {
        // Selected resource name
        String urlString = fileHostList.getSelectedName();
        if (urlString == null || urlString.equals("none")) {
            return null;
        }
        FileLocation location = new FileLocation();
        location.setUrlString(urlString);
        return location;
    }

    protected static void loadHostList(ListBoxBean fileHostList,
                              List fileResourceList,
                              String selectedResourceName) {
        log.debug("Adding "+ fileResourceList.size() + " resources to list box");
        // (Re)set file resource list properties
        fileHostList.setMultipleSelection(false);
        fileHostList.setSize(1);
        fileHostList.clear();
        // Add default (none) selection
        ListBoxItemBean fileResourceItem = new ListBoxItemBean();
          fileResourceItem.setValue("&lt;Select Resource&gt;");
        fileHostList.addBean(fileResourceItem);
        // (Re)build list with given file resources
        for (Iterator fileResources = fileResourceList.iterator(); fileResources.hasNext();) {
            FileResource fileResource = (FileResource) fileResources.next();
            fileResourceItem = new ListBoxItemBean();
            HardwareResource hardwareResource = fileResource.getHardwareResource();
            String fileResourceName = hardwareResource.getHostName();
            String fileResourceLabel = hardwareResource.getLabel();
            fileResourceItem.setName(fileResourceName);
            fileResourceItem.setValue(fileResourceLabel);
            // Set selected if resource name equals provided selection
            if (fileResourceName.equals(selectedResourceName)) {
                fileResourceItem.setSelected(true);
            }
            fileHostList.addBean(fileResourceItem);
        }
    }

    protected static String unloadHostName(ListBoxBean fileHostList) {
        // Selected resource name
        String resourceName = fileHostList.getSelectedName();
        if (resourceName == null || resourceName.equals("none")) {
            return "";
        }
        return resourceName;
    }

    protected static void loadFileDirList(ListBoxBean fileDirList, String currentDir) {
        log.debug("Loading file dir list for " + currentDir);
        // Clear file path list
        fileDirList.clear();
        if (currentDir != null && currentDir.length() > 0) {
            ListBoxItemBean fileDirItem = new ListBoxItemBean();
            fileDirItem.setName("/");
            fileDirItem.setValue("/");
            fileDirList.addBean(fileDirItem);
            String restOfDir = currentDir.substring(1);
            String fileDir = "";
            StringTokenizer tokenizer = new StringTokenizer(restOfDir, "/");
            int numTokens = tokenizer.countTokens();
            log.debug("Number of path tokens is " + numTokens);
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                log.debug("Next path token is " + token);
                fileDir += '/' + token;
                log.debug("Adding dir to list " + fileDir);
                fileDirItem = new ListBoxItemBean();
                fileDirItem.setName(fileDir);
                fileDirItem.setValue(fileDir);
                fileDirList.addBean(fileDirItem);
            }
            // Set the last item to be selected
            fileDirItem.setSelected(true);
        }
    }

    protected static FileLocation unloadFileDirLocation(ListBoxBean fileDirList)
            throws MalformedURLException {
        FileLocation fileLocation = null;
        // If nothing selected, it must be empty dir list
        // so just return default directory handle
        // Otherwise, return selected dir handle.
         if (fileDirList.getSelectedItems().size() == 0) {
            fileLocation = new FileLocation("", FileType.DIRECTORY);
        } else {
            // Create dir handle for the given dir string
            String selectedFilePath = fileDirList.getSelectedName();
            fileLocation = new FileLocation(selectedFilePath, FileType.DIRECTORY);
        }
        return fileLocation;
    }

    public static void loadFilePathList(ListBoxBean filePathList,
                                    List fileLocationList,
                                    String selectedFileName) {
        log.debug("Loading file path list");
        // Clear file path list
        filePathList.setMultipleSelection(true);
        filePathList.setSize(10);
        filePathList.clear();
        // (Re)build list with given file names
        for (Iterator fileLocations = fileLocationList.iterator(); fileLocations.hasNext();) {
            FileLocation fileLocation = (FileLocation) fileLocations.next();
            ListBoxItemBean filePathItem = new ListBoxItemBean();
            String fileName = fileLocation.getFileName();
            if (fileName.equals(selectedFileName)) {
                filePathItem.setSelected(true);
            }
            FileType fileType = fileLocation.getFileType();
            if (fileType.equals(FileType.FILE)) {
                log.debug("Adding file location " + fileName);
                fileName = "- " + fileName;
            } else if (fileType.equals(FileType.DIRECTORY)) {
                log.debug("Adding directory location " + fileName);
                fileName = "+ " + fileName;
            } else if (fileType.equals(FileType.SOFT_LINK))  {
                log.debug("Adding soft link " + fileName);
                fileName = "@ " + fileName + " -> " + fileLocation.getSoftLinkPath();
            } else {
                log.debug("Adding device " + fileName);
                fileName = "* " + fileName;
            }
            String fileUrl = fileLocation.getUrl();
            filePathItem.setName(fileUrl);
            filePathItem.setValue(fileName);
            filePathList.addBean(filePathItem);
        }
    }

    protected static FileLocation unloadFileLocation(ListBoxBean filePathList) {
        FileLocation fileLocation = null;
        // Get selected file url
        String fileUrl = filePathList.getSelectedName();
        if (fileUrl == null || fileUrl.equals("")) {
            return null;
        }
        try {
            // Get file location from file url
            fileLocation = new FileLocation(fileUrl);
        } catch (MalformedURLException e) {
            log.error("Unable to unload file handle from file path list ", e);
            return null;
        }
        return fileLocation;
    }

    protected static FileLocation unloadFilePathLocation(ListBoxBean filePathList, TextFieldBean currentPathField)
            throws MalformedURLException {
        FileLocation fileLocation = null;
        if (filePathList.getSelectedItems().size() == 0) {
            fileLocation = new FileLocation(currentPathField.getValue(), FileType.DIRECTORY);
        } else {
            // Get selected file url
            String fileUrl = filePathList.getSelectedName();
            // Get file location from file url
            fileLocation = new FileLocation(fileUrl);
        }
        return fileLocation;
    }

    protected static List unloadFilePathLocations(ListBoxBean filePathList) {
        List fileLocations = new ArrayList();
        List selectedUrls = filePathList.getSelectedNames();
        for (int ii = 0; ii < selectedUrls.size(); ++ii) {
            String selectedUrl = (String)selectedUrls.get(ii);
            FileLocation fileLocation = null;
            try {
                // Get file location from file url
                fileLocation = new FileLocation(selectedUrl);
                // Check file path...
                String filePath = fileLocation.getFilePath();
                if (filePath.endsWith("/.") || filePath.endsWith("/..")) {
                    continue;
                }
                // Add to file location list
                fileLocations.add(fileLocation);
            } catch (MalformedURLException e) {
                log.error("Error getting file handle from file path list!", e);
            }
        }
        return fileLocations;
    }

    public void downloadFile(FileLocation fileLocation) throws PortletException {
        downloadFile(this, fileBrowserService, fileLocation);
    }
    
    public static void downloadFile(ActionComponent component,
                                    FileBrowserService fileBrowserService, 
                                    FileLocation fileLocation) throws PortletException {
        log.debug("Downloading file " + fileLocation.getUrl());
        User user = component.getUser();
        MessageBoxBean messageBox = component.getMessageBox();
        try {
            FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, fileLocation);
            String fileName = null;
            String filePath = null;
            String fileUrl = null;
            String fileRealPath = null;
            if (fileBrowser instanceof LocalHostBrowser) {
                log.debug("Downloading from local host");
                LocalHostBrowser localHostBrowser = (LocalHostBrowser)fileBrowser;
                fileName = fileLocation.getFileName();
                filePath = fileLocation.getFilePath();
                fileUrl = localHostBrowser.getDownloadUrl(fileLocation);
                fileRealPath = localHostBrowser.getRealPath(fileLocation);
            } else {
                log.debug("Downloading from remote host");
                LocalHostBrowser localHostBrowser =
                        (LocalHostBrowser)fileBrowserService.createFileBrowser(user, new FileLocation("secdir://localhost"));
                FileLocation localFileLoc = localHostBrowser.createFileLocation('/' + fileLocation.getFileName());
                log.debug("From remote location " + fileLocation.getUrl() + " to local location = " + localFileLoc.getUrl());
                FileCopy fileCopy = fileBrowser.copy(fileLocation, localFileLoc);
                fileCopy.waitFor();
                if (!fileCopy.getTaskStatus().equals(TaskStatus.COMPLETED)) {
                    log.error("Unable to download file." + fileCopy.getTaskStatusMessage());
                    messageBox.appendText("Unable to download file. " + fileCopy.getTaskStatusMessage());
                    messageBox.setMessageType(TextBean.MSG_ERROR);
                }
                fileName = localFileLoc.getFileName();
                filePath = localFileLoc.getFilePath();
                fileUrl = localHostBrowser.getDownloadUrl(localFileLoc);
                fileRealPath = localHostBrowser.getRealPath(localFileLoc);
            }
            component.setPageAttribute(DOWNLOAD_FILE_PATH_ATTRIBUTE, filePath);
            component.setPageAttribute(DOWNLOAD_FILE_URL_ATTRIBUTE, fileUrl);
            component.setFileDownloadEvent(fileName, fileRealPath);
        } catch (Exception e) {
            log.error("Unable to download file." + e.getMessage());
            messageBox.appendText("Unable to download file. " + e.getMessage());
            messageBox.setMessageType(TextBean.MSG_ERROR);
        }
    }
}

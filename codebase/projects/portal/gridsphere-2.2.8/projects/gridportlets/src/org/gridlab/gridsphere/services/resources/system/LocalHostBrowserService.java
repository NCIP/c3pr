/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.file.sets.FilePatternSet;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.file.tasks.*;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileTransfer;
import org.gridlab.gridsphere.services.file.tasks.impl.AbstractFileBrowserService;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowser;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.core.secdir.SecureDirectoryService;
import org.gridlab.gridsphere.services.core.secdir.FileLocationID;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;

/**
 * Implements the gridlab logical file browser service.
 */
public class LocalHostBrowserService extends AbstractFileBrowserService {

    protected static PortletLog log = SportletLog.getInstance(LocalHostBrowserService.class);

    protected ResourceRegistryService registry = null;
    protected SecureDirectoryService secureDirectoryService = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        super.init(config);
        setFileResourceType(LocalHostResourceType.INSTANCE);
        try {
            registry = (ResourceRegistryService)
                    factory.createPortletService(ResourceRegistryService.class, null, true);
            secureDirectoryService = (SecureDirectoryService)
                    factory.createPortletService(SecureDirectoryService.class, null, true);
        } catch (PortletServiceException e) {
            log.error(e.getMessage());
            throw new PortletServiceUnavailableException("Unable to get required portlet services", e);
        }
    }

    public boolean supportsFileLocation(FileLocation fileLocation) {
        String protocol = fileLocation.getProtocol();
        if (protocol.equalsIgnoreCase("secdir")) {
            return true;
        }
        if (protocol.equalsIgnoreCase("file") || protocol.equalsIgnoreCase("http") || protocol.equalsIgnoreCase("https") ) {
            int port = fileLocation.getPort();
            if (port > 0) {
                FileResource fileResource = getFileResource(fileLocation);
                if (fileResource == null) {
                    return false;
                }
                return fileResource.getPort().equals(String.valueOf(port));
            }
            return true;
        }
        return false;
    }

    public String getHomeDirectory(User user)
            throws FileException {
        FileLocationID homeDirId
                = secureDirectoryService.createFileLocationID(user.getID(), LocalHostBrowser.CATEGORY, "");
        File homeDir = secureDirectoryService.getFile(homeDirId);
        String homeDirPath = homeDir.getPath();
        if (!homeDir.exists()) {
            log.debug("Creating home directory " + homeDirPath);
            if (!homeDir.mkdir()) {
                throw new FileException("Unable to create home directory " + homeDirPath);
            }
        }
        return homeDirPath;
    }

    public List getFileResources() {
        return registry.getResources(LocalHostResourceType.INSTANCE);
    }

    public List getFileResources(User user) {
        return registry.getResources(LocalHostResourceType.INSTANCE);
    }

    public FileResource getFileResource(String hostName) {
        HardwareResource hostResource = registry.getHardwareResourceByHost(hostName);
        if (hostResource == null) {
            log.debug("No hardware resource with host " + hostName);
            return null;
        }
        return (FileResource)hostResource.getChildResource(LocalHostResourceType.INSTANCE);
    }

    public BaseFileBrowser createFileBrowser() {
        return new LocalHostBrowser();
    }

    public List getFileTaskTypes() {
        List types = new Vector(8);
        types.add(LocalHostListType.INSTANCE);
        types.add(LocalHostRenameType.INSTANCE);
        types.add(LocalHostMakeDirType.INSTANCE);
        types.add(LocalHostCopyType.INSTANCE);
        types.add(LocalHostMoveType.INSTANCE);
        types.add(LocalHostDeleteType.INSTANCE);
        types.add(LocalHostUploadType.INSTANCE);
        types.add(LocalHostDownloadType.INSTANCE);
        return types;
    }

    public FileTaskSpec createFileTaskSpec(FileTaskType type)
            throws FileTaskException {
        if (type.equals(LocalHostListType.INSTANCE)) {
            return createFileListingSpec();
        } else if (type.equals(LocalHostMakeDirType.INSTANCE)) {
            return createFileMakeDirSpec();
        } else if (type.equals(LocalHostRenameType.INSTANCE)) {
            return createFileNameChangeSpec();
        } else if (type.equals(LocalHostCopyType.INSTANCE)) {
            return createFileCopySpec();
        } else if (type.equals(LocalHostMoveType.INSTANCE)) {
            return createFileMoveSpec();
        } else if (type.equals(LocalHostDeleteType.INSTANCE)) {
            return createFileDeletionSpec();
        } else if (type.equals(LocalHostUploadType.INSTANCE)) {
            return createFileUploadSpec();
        } else {
            throw new FileTaskException ("Task type not supported " + type.getClassName());
        }
    }

    public FileTaskSpec createFileTaskSpec(FileTaskSpec spec)
            throws FileTaskException {
        TaskType type = spec.getTaskType();
        if (type.equals(LocalHostListType.INSTANCE)) {
            return createFileListingSpec();
        } else if (type.equals(LocalHostMakeDirType.INSTANCE)) {
            return createFileMakeDirSpec();
        } else if (type.equals(LocalHostRenameType.INSTANCE)) {
            return createFileNameChangeSpec();
        } else if (type.equals(LocalHostCopyType.INSTANCE)) {
            return createFileCopySpec();
        } else if (type.equals(LocalHostMoveType.INSTANCE)) {
            return createFileMoveSpec();
        } else if (type.equals(LocalHostDeleteType.INSTANCE)) {
            return createFileDeletionSpec();
        } else if (type.equals(LocalHostUploadType.INSTANCE)) {
            return createFileUploadSpec();
        } else {
            throw new FileTaskException ("Task type not supported " + type.getClassName());
        }
    }

    public FileTask submitFileTask(FileTaskSpec spec)
            throws FileTaskException {
        TaskType type = spec.getTaskType();
        if (type.equals(LocalHostListType.INSTANCE)) {
            return submitFileListing((FileListingSpec)spec);
        } else if (type.equals(LocalHostMakeDirType.INSTANCE)) {
            return submitFileMakeDir((FileMakeDirSpec)spec);
        } else if (type.equals(LocalHostRenameType.INSTANCE)) {
            return submitFileNameChange((FileNameChangeSpec)spec);
        } else if (type.equals(LocalHostCopyType.INSTANCE)) {
            return submitFileCopy((FileCopySpec)spec);
        } else if (type.equals(LocalHostMoveType.INSTANCE)) {
            return submitFileMove((FileMoveSpec)spec);
        } else if (type.equals(LocalHostDeleteType.INSTANCE)) {
            return submitFileDeletion((FileDeletionSpec)spec);
        } else if (type.equals(LocalHostUploadType.INSTANCE)) {
            return submitFileUpload((FileUploadSpec)spec);
        } else {
            throw new FileTaskException ("Task type not supported " + type.getClassName());
        }
    }

    public void cancelFileTask(FileTask task)
            throws FileTaskException {
        throw new FileTaskException("Grid ftp cancel task not supported");
    }

    public FileListingSpec createFileListingSpec()
            throws FileTaskException {
        return new LocalHostListSpec();
    }

    public FileListing submitFileListing(FileListingSpec spec)
            throws FileTaskException {
        log.debug("Submiting local list task");
        // Check user
        User user = spec.getUser();
        if (user == null) {
            throw new FileTaskException("User must be specified!");
        }
        // Check file set
        FileSet fileSet = spec.getFileSet();
        if (fileSet == null) {
            throw new FileTaskException("File set must be specified!");
        }

        LocalHostList localList = new LocalHostList(spec);
        localList.setTaskStatus(TaskStatus.SUBMITTED);

        if (fileSet instanceof FilePatternSet) {
            FilePatternSet filePatternSet = (FilePatternSet)fileSet;
            FileLocation fileLoc = filePatternSet.getFileLocation();
            if (fileLoc == null) {
                throw new FileTaskException("File location must be specified!");
            }
            try {
                List fileLocList = listFileLocations(user, fileLoc, spec.getBrowserFlag());
                localList.setFileLocations(fileLocList);
                localList.setTaskStatus(TaskStatus.COMPLETED);
            } catch (Exception e) {
                localList.setTaskStatus(TaskStatus.FAILED, e.getMessage());
            }
        } else if (fileSet instanceof FileLocationSet) {
            FileLocationSet fileLocSet = (FileLocationSet)fileSet;
            List fileLocList = new Vector();
            Iterator fileLocIter = fileLocSet.getFileLocations().iterator();
            try {
                while (fileLocIter.hasNext()) {
                    FileLocation nextLoc = (FileLocation)fileLocIter.next();
                    fileLocList.add(listFileLocations(user, nextLoc, false));
                }
                localList.setTaskStatus(TaskStatus.COMPLETED);
            } catch (Exception e) {
                localList.setTaskStatus(TaskStatus.FAILED, e.getMessage());
            }
        } else {
            throw new FileTaskException("File set not supported " + fileSet.getClass().getName());
        }
        return localList;
    }

    protected List listFileLocations(User user, FileLocation fileLoc, boolean browserFlag) throws FileTaskException {
        List fileLocList = new Vector();
        // Prepare file path
        String filePath = prepareFilePath(fileLoc.getFilePath());
        try {
            FileLocationID fileLocId
                    = secureDirectoryService.createFileLocationID(user.getID(),
                                                                  LocalHostBrowser.CATEGORY,
                                                                  filePath);
            File file = secureDirectoryService.getFile(fileLocId);
            if (file == null) {
                throw new FileTaskException("Invalid file location " + filePath);
            }
            if (!filePath.endsWith("/")) {
                filePath += "/";
            }
            // If our file location is a directory...
            if (file.isDirectory()) {
                FileLocation nextLoc = null;
                if (browserFlag) {
                    String nextUrl = "file://localhost/" + filePath + '.';
                    nextLoc = new FileLocation();
                    nextLoc.setUrlString(nextUrl);
                    nextLoc.setFileType(FileType.DIRECTORY);
                    fileLocList.add(nextLoc);
                    if (!filePath.equals("/")) {
                        nextUrl = "file://localhost/" + filePath + "..";
                        nextLoc = new FileLocation();
                        nextLoc.setUrlString(nextUrl);
                        nextLoc.setFileType(FileType.DIRECTORY);
                        fileLocList.add(nextLoc);
                    }
                }

                // Get home directory
                String homeDir = getHomeDirectory(user);
                log.debug("homeDir = " + homeDir);
                int homeDirLen = homeDir.length();

                File[] fileArray = file.listFiles();
                for (int ii = 0; ii < fileArray.length; ++ii) {
                    String nextFilePath = fileArray[ii].getPath();
                    log.debug("Listed file " + nextFilePath);
                    nextFilePath = nextFilePath.substring(homeDirLen).replaceAll(File.separator, "/");
                    String nextUrl = "file://localhost/" + nextFilePath;
                    nextLoc = new FileLocation();
                    nextLoc.setUrlString(nextUrl);
                    if (!fileArray[ii].isFile()) {
                        nextLoc.setFileType(FileType.DIRECTORY);
                    }
                    fileLocList.add(nextLoc);
                }
            } else {
                String nextUrl = "file://localhost/" + filePath;
                FileLocation nextLoc = new FileLocation();
                nextLoc.setUrlString(nextUrl);
                fileLocList.add(nextLoc);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileTaskException(e.getMessage());
        }
        return fileLocList;
    }

    public static String prepareFilePath(String filePath) {
        if (filePath == null || filePath.equals("")) {
            filePath = "";
        } else if (filePath.endsWith("/.")) {
            if (filePath.equals("/.")) {
                filePath = "/";
            } else {
                filePath = filePath.substring(0, filePath.length()-2);
            }
        } else if (filePath.endsWith("/..")) {
            if (filePath.equals("/..")) {
                filePath = "/";
            } else {
                filePath = filePath.substring(0, filePath.length()-3);
                filePath = FileLocation.getParentPath(filePath);
            }
        }
        return filePath;
    }

    public FileNameChangeSpec createFileNameChangeSpec()
            throws FileTaskException {
        return new LocalHostRenameSpec();
    }

    public FileNameChange submitFileNameChange(FileNameChangeSpec spec)
            throws FileTaskException {
        log.debug("Submiting local rename task");
        User user = spec.getUser();
        if (user == null) {
            throw new FileTaskException("User must be specified!");
        }
        FileLocation fileLoc = spec.getFileLocation();
        if (fileLoc == null) {
            throw new FileTaskException("File location must be specified!");
        }
        String fileName = spec.getNewFileName();
        if (fileName == null) {
            throw new FileTaskException("File name must be specified!");
        }
        LocalHostRename localRename = new LocalHostRename(spec);
        localRename.setTaskStatus(TaskStatus.SUBMITTED);
        log.debug("Started local rename task");
        String filePath = prepareFilePath(fileLoc.getFilePath());
        try {

            FileLocationID fileLocId
                    = secureDirectoryService.createFileLocationID(user.getID(),
                                                                  LocalHostBrowser.CATEGORY,
                                                                  filePath);
            String oldFileName = fileLoc.getFileName();
            int index = filePath.lastIndexOf(oldFileName);
            String newFilePath = filePath.substring(0, index) + fileName;

            if (secureDirectoryService.moveFile(fileLocId, newFilePath)) {
                localRename.setTaskStatus(TaskStatus.COMPLETED);
            } else {
                localRename.setTaskStatus(TaskStatus.FAILED, "Unable to rename " + filePath
                                                              + " to " + newFilePath);
            }

       } catch (Exception e) {
            log.error(e.getMessage());
            localRename.setTaskStatus(TaskStatus.FAILED, e.getMessage());
        }
        return localRename;
    }

    public FileMakeDirSpec createFileMakeDirSpec()
            throws FileTaskException {
        return new LocalHostMakeDirSpec();
    }

    public String getRealPath(User user, String filePath) throws FileException {
        FileLocationID fileLocId
                = secureDirectoryService.createFileLocationID(user.getID(),
                                                              LocalHostBrowser.CATEGORY,
                                                              filePath);
        File file = secureDirectoryService.getFile(fileLocId);
        if (file == null) {
            throw new FileException("Unable to create file for " + filePath);
        }
        return file.getPath();
    }

    public FileMakeDir submitFileMakeDir(FileMakeDirSpec spec)
            throws FileTaskException {
        log.debug("Submiting local make dir task");
        User user = spec.getUser();
        if (user == null) {
            throw new FileTaskException("User must be specified!");
        }
        FileLocation fileLoc = spec.getParentLocation();
        if (fileLoc == null) {
            throw new FileTaskException("Directory location must be specified!");
        }
        String dirName = spec.getDirectoryName();
        if (dirName == null) {
            throw new FileTaskException("Directory name must be specified!");
        }
        LocalHostMakeDir localMakeDir = new LocalHostMakeDir(spec);
        localMakeDir.setTaskStatus(TaskStatus.SUBMITTED);
        log.debug("Started local make dir task");
        String filePath = prepareFilePath(fileLoc.getFilePath());
        if (!filePath.endsWith("/")) filePath += "/";
        filePath += dirName;
        try {

            FileLocationID fileLocId
                    = secureDirectoryService.createFileLocationID(user.getID(),
                                                                  LocalHostBrowser.CATEGORY,
                                                                  filePath);
            File file = secureDirectoryService.getFile(fileLocId);
            log.debug("Creating directory = " + file.getPath());

            if (file.mkdir()) {
                localMakeDir.setTaskStatus(TaskStatus.COMPLETED);
            } else {
                localMakeDir.setTaskStatus(TaskStatus.FAILED, "Unable to mkdir " + filePath);
            }
       } catch (Exception e) {
            log.error(e.getMessage());
            localMakeDir.setTaskStatus(TaskStatus.FAILED, e.getMessage());
        }
        return localMakeDir;
    }

    public FileCopySpec createFileCopySpec()
            throws FileTaskException {
        return new LocalHostCopySpec();
    }

    public FileCopy submitFileCopy(FileCopySpec spec)
            throws FileTaskException {
        return (FileCopy)performFileTransfer(spec);
    }

    public FileMoveSpec createFileMoveSpec()
            throws FileTaskException {
        return new LocalHostMoveSpec();
    }

    public FileMove submitFileMove(FileMoveSpec spec)
            throws FileTaskException {
        return (FileMove)performFileTransfer(spec);
    }

    protected FileTransfer performFileTransfer(FileTransferSpec spec)
            throws FileTaskException {
        log.debug("Submitting local transfer task");
        // Validate copy spec
        User user = spec.getUser();
        if (user == null) {
            throw new FileTaskException("User must be specified!");
        }
        // Source file set
        FileSet srcFileSet = spec.getFileSet();
        if (srcFileSet == null) {
            throw new FileTaskException("Source file set must be specified!");
        }

        // Destination location
        FileLocation dstLocation = spec.getDestination();
        if (dstLocation == null) {
            throw new FileTaskException("Destination must be specified!");
        }
        String dstFilePath = prepareFilePath(dstLocation.getFilePath());
        // Start the transfer...
        BaseFileTransfer fileTransfer = null;

        if (spec instanceof FileCopySpec) {

            fileTransfer = new LocalHostCopy((LocalHostCopySpec)spec);

        } else {

            fileTransfer = new LocalHostMove((LocalHostMoveSpec)spec);
        }

        fileTransfer.setTaskStatus(TaskStatus.SUBMITTED);

        log.debug("Submitted local transfer task");

        if (srcFileSet instanceof FilePatternSet) {
            FilePatternSet filePatternSet =  (FilePatternSet)srcFileSet;
            FileLocation srcLocation = filePatternSet.getFileLocation();
            if (srcLocation == null) {
                throw new FileTaskException("Source location must be specified in regular expression file sets!");
            }
            try {
               transferFile(fileTransfer, srcLocation, dstFilePath);
            } catch (FileTaskException e) {
                fileTransfer.setTaskStatus(TaskStatus.FAILED, e.getMessage());
            }
            fileTransfer.setTaskStatus(TaskStatus.COMPLETED);
            return fileTransfer;

        } else {

            FileLocationSet srcLocationSet = (FileLocationSet)srcFileSet;
            List srcLocationList = srcLocationSet.getFileLocations();

            StringBuffer errorMesssage = new StringBuffer();
            boolean succeeded = true;

            for (Iterator srcLocations = srcLocationList.iterator(); srcLocations.hasNext();) {
                FileLocation nextSrcLocation = (FileLocation) srcLocations.next();
                try {
                   transferFile(fileTransfer, nextSrcLocation, dstFilePath);
                } catch (FileTaskException e) {
                    succeeded = false;
                    errorMesssage.append(e.getMessage());
                }
            }

            if (succeeded) {
                fileTransfer.setTaskStatus(TaskStatus.COMPLETED);
            } else {
                fileTransfer.setTaskStatus(TaskStatus.FAILED, errorMesssage.toString());
            }

            return fileTransfer;
        }
    }

    public void transferFile(BaseFileTransfer fileTransfer,
                             FileLocation srcLocation,
                             String dstFilePath) throws FileTaskException {
        boolean success = true;
        User user = fileTransfer.getUser();

        // Get source info
        String srcFilePath = prepareFilePath(srcLocation.getFilePath());
        String srcFileName = srcLocation.getFileName();

        // Fix destination path
        if (!dstFilePath.endsWith(srcFileName)) {
            if (!dstFilePath.endsWith("/")) {
                dstFilePath += "/";
            }
            dstFilePath += srcFileName;
        }

        log.debug("Transferring file at " + srcFilePath + " to " + dstFilePath);

        // Source location
        FileLocationID srcLocId =
                 secureDirectoryService.createFileLocationID(user.getID(),
                                                             LocalHostBrowser.CATEGORY,
                                                             srcFilePath);

        File srcFile = secureDirectoryService.getFile(srcLocId);
        if (srcFile == null) {
            throw new FileTaskException( "Invalid source file path " + srcFilePath);
        }

        // Destination location
        FileLocationID dstLocId =
                 secureDirectoryService.createFileLocationID(user.getID(),
                                                             LocalHostBrowser.CATEGORY,
                                                             dstFilePath);
        File dstFile = secureDirectoryService.getFile(dstLocId);
        if (dstFile == null) {
            throw new FileTaskException( "Invalid destination file path " + dstFilePath);
        }

        // Transfer file
        if (fileTransfer instanceof FileCopy) {
            log.debug("Copying " + srcFile.getPath() + " to " + dstFile.getPath());

            success = secureDirectoryService.copyFile(srcLocId, dstFilePath);
        } else {
            log.debug("Moving " + srcFile.getPath() + " to " + dstFile.getPath());

            success = secureDirectoryService.moveFile(srcLocId, dstFilePath);
        }
        if (!success) {
            throw new FileTaskException( "Unable to transfer file");
        }
    }

    /**
     * Creates a file deletion spec.
     * @return
     */
    public FileDeletionSpec createFileDeletionSpec()
            throws FileTaskException {
        return new LocalHostDeleteSpec();
    }

    /**
     * Submits a file deletion task.
     * @param spec
     * @return
     */
    public FileDeletion submitFileDeletion(FileDeletionSpec spec)
            throws FileTaskException {
        log.debug("Submiting local delete task");
        User user = spec.getUser();
        if (user == null) {
            throw new FileTaskException("User must be specified!");
        }
        FileSet fileSet = spec.getFileSet();
        if (fileSet == null) {
            throw new FileTaskException("File set must be specified!");
        }
        LocalHostDelete localDelete = new LocalHostDelete(spec);
        localDelete.setTaskStatus(TaskStatus.SUBMITTED);
        log.debug("Submitted local delete task");
        StringBuffer errorMesssage = new StringBuffer();
        boolean succeeded = true;
        if (fileSet instanceof FilePatternSet) {
            FilePatternSet filePatternSet = (FilePatternSet)fileSet;
            // File location
            FileLocation fileLoc = filePatternSet.getFileLocation();
            if (fileLoc == null) {
                throw new FileTaskException("File location must be specified in regular expression file sets!");
            }

            String filePath = fileLoc.getFilePath();
            FileLocationID fileLocId
                    = secureDirectoryService.createFileLocationID(user.getID(),
                                                                  LocalHostBrowser.CATEGORY,
                                                                  filePath);
            File file = secureDirectoryService.getFile(fileLocId);
            if (file == null) {
                localDelete.setTaskStatus(TaskStatus.FAILED, "Invalid file path " + filePath);
                return localDelete;
            }
            // Delete file
            boolean recursive = fileLoc.getIsDirectory();
            if (secureDirectoryService.deleteFile(fileLocId, recursive)) {
                localDelete.setTaskStatus(TaskStatus.COMPLETED);
            } else {
                localDelete.setTaskStatus(TaskStatus.FAILED, "Unable to delete " + filePath + '.');
            }
        } else if (fileSet instanceof FileLocationSet) {
            FileLocationSet fileLocationSet = (FileLocationSet)fileSet;
            List fileLocationList = fileLocationSet.getFileLocations();
            for (Iterator fileLocations = fileLocationList.iterator(); fileLocations.hasNext();) {

                FileLocation nextFileLoc = (FileLocation) fileLocations.next();
                String nextFilePath = nextFileLoc.getFilePath();

                FileLocationID fileLocId
                        = secureDirectoryService.createFileLocationID(user.getID(),
                                                                      LocalHostBrowser.CATEGORY,
                                                                      nextFilePath);
                File file = secureDirectoryService.getFile(fileLocId);
                if (file == null) {
                    localDelete.setTaskStatus(TaskStatus.FAILED, "Invaid path " + nextFilePath);
                    succeeded = false;
                    errorMesssage.append("Invaid path " + nextFilePath + ".\n");
                } else {
                    // Delete next file
                    boolean recursive = nextFileLoc.getIsDirectory();
                    if (!secureDirectoryService.deleteFile(fileLocId, recursive)) {
                        succeeded = false;
                        errorMesssage.append("Unable to delete " + nextFilePath + ".\n");
                    }
                }
            }

            if (succeeded) {
                localDelete.setTaskStatus(TaskStatus.COMPLETED);
            } else {
                localDelete.setTaskStatus(TaskStatus.FAILED, errorMesssage.toString());
            }
        }

        return localDelete;
    }

    public FileUploadSpec createFileUploadSpec()
            throws FileTaskException {
        return new LocalHostUploadSpec();
    }

    public FileUpload submitFileUpload(FileUploadSpec spec)
            throws FileTaskException {
        log.debug("Submitting local upload task");
        // Validate copy spec
        User user = spec.getUser();
        if (user == null) {
            throw new FileTaskException("User must be specified!");
        }
        String localFilePath = spec.getFilePath();
        if (localFilePath == null || localFilePath.equals("")) {
            throw new FileTaskException("Source file set must be specified!");
        }
        FileLocation dstLocation = spec.getUploadLocation();
        if (dstLocation == null) {
            throw new FileTaskException("Destination must be specified!");
        }
        // Start the transfer...
        LocalHostUpload fileUpload = new LocalHostUpload(spec);
        fileUpload.setTaskStatus(TaskStatus.SUBMITTED);
        log.debug("Started local upload task");
        File localFile = new File(localFilePath);
        if (!localFile.exists()) {
            fileUpload.setTaskStatus(TaskStatus.FAILED, "Invalid local file path " + localFilePath);
            return fileUpload;
        }
        String dstFilePath = prepareFilePath(dstLocation.getFilePath());
        FileLocationID dstLocId =
                 secureDirectoryService.createFileLocationID(user.getID(),
                                                             LocalHostBrowser.CATEGORY,
                                                             dstFilePath);
        try {
            secureDirectoryService.addFile(dstLocId, localFile);
        } catch (IOException e) {
            log.error("Unable to add file to securre directory service", e);
            fileUpload.setTaskStatus(TaskStatus.FAILED, e.getMessage());
            return fileUpload;
        }
        fileUpload.setTaskStatus(TaskStatus.COMPLETED);
        return fileUpload;
    }

    public FileDownloadSpec createFileDownloadSpec()
            throws FileTaskException {
        return new LocalHostDownloadSpec();
    }

    public FileDownload submitFileDownload(FileDownloadSpec spec)
            throws FileTaskException {
        log.debug("Submitting local download task");
        // Validate copy spec
        User user = spec.getUser();
        if (user == null) {
            throw new FileTaskException("User must be specified!");
        }
        String localFilePath = spec.getDownloadPath();
        if (localFilePath == null || localFilePath.equals("")) {
            throw new FileTaskException("Source file set must be specified!");
        }
        FileLocation srcLocation = spec.getFileLocation();
        if (srcLocation == null) {
            throw new FileTaskException("Source must be specified!");
        }
        // Start the transfer...
        LocalHostDownload fileDownload = new LocalHostDownload(spec);
        fileDownload.setTaskStatus(TaskStatus.SUBMITTED);
        log.debug("Started local download task");
        File localFile = new File(localFilePath);
        if (!localFile.exists()) {
            fileDownload.setTaskStatus(TaskStatus.FAILED, "Invalid local file path " + localFilePath);
            return fileDownload;
        }
        String srcFilePath = prepareFilePath(srcLocation.getFilePath());
        FileLocationID srcLocId =
                 secureDirectoryService.createFileLocationID(user.getID(),
                                                             LocalHostBrowser.CATEGORY,
                                                             srcFilePath);
        File srcFile = secureDirectoryService.getFile(srcLocId);
        fileDownload.setTaskStatus(TaskStatus.FAILED, "Download not fully implemented");
        return fileDownload;
    }
}

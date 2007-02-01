package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.impl.AbstractFileBrowserService;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowser;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileTransfer;
import org.gridlab.gridsphere.services.file.tasks.impl.FileBrowserServiceImpl;
import org.gridlab.gridsphere.services.file.tasks.*;
import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Vector;
import java.io.File;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpBrowserService.java,v 1.1.1.1 2007-02-01 20:41:10 kherm Exp $
 * <p>
 */

public class GridFtpBrowserService extends AbstractFileBrowserService {

    protected static PortletLog log = SportletLog.getInstance(GridFtpBrowserService.class);

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        super.init(config);
        setFileResourceType(GridFtpResourceType.INSTANCE);
        PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
        FileBrowserServiceImpl broker = null;
        try {
            log.info("Getting instance of file browser service");
            broker  = (FileBrowserServiceImpl)
                    serviceFactory.createPortletService(FileBrowserService.class, null, true);
            broker.addFileBrowserService(GridFtpResourceType.INSTANCE, this);
        } catch (PortletServiceNotFoundException e) {
            log.error("File browser service not found", e);
        }
    }

    public boolean supportsFileLocation(FileLocation fileLocation) {
        String protocol = fileLocation.getProtocol();
        if (protocol.equalsIgnoreCase("ftp") ||
            protocol.equalsIgnoreCase("gsiftp") ||
            protocol.equalsIgnoreCase("gridftp")) {
            return true;
        }
        if (protocol.equalsIgnoreCase("file")) {
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

    /**
     * See FileTaskService
     * @return
     */
    public List getFileTaskTypes() {
        List types = new Vector(8);
        types.add(GridFtpListType.INSTANCE);
        types.add(GridFtpRenameType.INSTANCE);
        types.add(GridFtpMakeDirType.INSTANCE);
        types.add(GridFtpCopyType.INSTANCE);
        types.add(GridFtpMoveType.INSTANCE);
        types.add(GridFtpDeleteType.INSTANCE);
        types.add(GridFtpUploadType.INSTANCE);
        types.add(GridFtpDownloadType.INSTANCE);
        return types;
    }

    /**
     * See FileTaskService
     * @param type
     * @return
     */
    public FileTaskSpec createFileTaskSpec(FileTaskType type)
            throws FileTaskException {
        if (type.equals(GridFtpListType.INSTANCE)) {
            return createFileListingSpec();
        } else if (type.equals(GridFtpMakeDirType.INSTANCE)) {
            return createFileMakeDirSpec();
        } else if (type.equals(GridFtpRenameType.INSTANCE)) {
            return createFileNameChangeSpec();
        } else if (type.equals(GridFtpCopyType.INSTANCE)) {
            return createFileCopySpec();
        } else if (type.equals(GridFtpMoveType.INSTANCE)) {
            return createFileMoveSpec();
        } else if (type.equals(GridFtpDeleteType.INSTANCE)) {
            return createFileDeletionSpec();
        } else if (type.equals(GridFtpUploadType.INSTANCE)) {
            return createFileUploadSpec();
        } else {
            throw new FileTaskException ("Task type not supported " + type.getClassName());
        }
    }

    /**
     * See FileTaskService
     * @param spec
     * @return
     */
    public FileTaskSpec createFileTaskSpec(FileTaskSpec spec)
            throws FileTaskException {
        TaskType type = spec.getTaskType();
        if (type.equals(GridFtpListType.INSTANCE)) {
            return createFileListingSpec();
        } else if (type.equals(GridFtpMakeDirType.INSTANCE)) {
            return createFileMakeDirSpec();
        } else if (type.equals(GridFtpRenameType.INSTANCE)) {
            return createFileNameChangeSpec();
        } else if (type.equals(GridFtpCopyType.INSTANCE)) {
            return createFileCopySpec();
        } else if (type.equals(GridFtpMoveType.INSTANCE)) {
            return createFileMoveSpec();
        } else if (type.equals(GridFtpDeleteType.INSTANCE)) {
            return createFileDeletionSpec();
        } else if (type.equals(GridFtpUploadType.INSTANCE)) {
            return createFileUploadSpec();
        } else {
            throw new FileTaskException ("Task type not supported " + type.getClassName());
        }
    }

    /**
     * See FileTaskService
     * @param spec
     * @return
     */
    public FileTask submitFileTask(FileTaskSpec spec)
            throws FileTaskException {
        TaskType type = spec.getTaskType();
        if (type.equals(GridFtpListType.INSTANCE)) {
            return submitFileListing((FileListingSpec)spec);
        } else if (type.equals(GridFtpMakeDirType.INSTANCE)) {
            return submitFileMakeDir((FileMakeDirSpec)spec);
        } else if (type.equals(GridFtpRenameType.INSTANCE)) {
            return submitFileNameChange((FileNameChangeSpec)spec);
        } else if (type.equals(GridFtpCopyType.INSTANCE)) {
            return submitFileCopy((FileCopySpec)spec);
        } else if (type.equals(GridFtpMoveType.INSTANCE)) {
            return submitFileMove((FileMoveSpec)spec);
        } else if (type.equals(GridFtpDeleteType.INSTANCE)) {
            return submitFileDeletion((FileDeletionSpec)spec);
        } else if (type.equals(GridFtpUploadType.INSTANCE)) {
            return submitFileUpload((FileUploadSpec)spec);
        } else {
            throw new FileTaskException ("Task type not supported " + type.getClassName());
        }
    }

    /**
     * See FileTaskService
     * @param task
     */
    public void cancelFileTask(FileTask task)
            throws FileTaskException {
        throw new FileTaskException("Grid ftp cancel task not supported");
    }

    /**
     * See FileBrowserService
     * @return
     */
    public List getFileResources() {
        List resources = null;
        try {
            ResourceRegistryService registry = (ResourceRegistryService)
                    factory.createPortletService(ResourceRegistryService.class, null, true);
            resources = registry.getResources(GridFtpResourceType.INSTANCE);
        } catch (PortletServiceException e) {
            log.warn("Unable to get grid ftp resources");
            resources = new Vector(0);
        }
        return resources;
    }

    /**
     * See FileBrowserService
     * @param user
     * @return
     */
    public List getFileResources(User user) {
        List resources = null;
        try {
            ResourceRegistryService registry = (ResourceRegistryService)
                    factory.createPortletService(ResourceRegistryService.class, null, true);
            resources = registry.getResources(GridFtpResourceType.INSTANCE);
        } catch (PortletServiceException e) {
            log.warn("Unable to get grid ftp resources for user" + user.getUserID());
            resources = new Vector(0);
        }
        log.debug("Returning " + resources.size() + " grid ftp resources");
        return resources;
    }

    /**
     * See FileBrowserService
     * @param hostName
     * @return
     */
    public FileResource getFileResource(String hostName) {
        return getGridFtpResource(hostName);
    }

    /**
     * Returns the grid ftp resource on the given host
     * @param hostName
     * @return
     */
    public GridFtpResource getGridFtpResource(String hostName) {
        GridFtpResource fileResource = null;
        ResourceRegistryService registry = null;
        HardwareResource host = null;
        log.debug("Getting hardware resource for host " + hostName);
        try {
            registry = (ResourceRegistryService)
                    factory.createPortletService(ResourceRegistryService.class, null, true);
            host = registry.getHardwareResourceByHostName(hostName);
            if (host != null)  {
//                fileResource = (GridFtpResource)host.getChildResource(GridFtpResourceType.INSTANCE);
                log.debug("Getting grid ftp resources for " + host.getDn());
                List fileResources = registry.getChildResources(host, GridFtpResourceType.INSTANCE);
                log.debug("Found " + fileResources.size() + " grid ftp resources.");
                if (fileResources.size() > 0) {
                    fileResource = (GridFtpResource)fileResources.get(0);
                }
            }
        } catch (PortletServiceException e) {
            log.error("Unable to get grid ftp resource on " + hostName, e);
        }
        return fileResource;
    }

    public BaseFileBrowser createFileBrowser() {
        return new GridFtpBrowser();
    }

    /**
     * Creates a file listing spec.
     * @return
     */
    public FileListingSpec createFileListingSpec()
            throws FileTaskException {
        return new GridFtpListSpec();
    }

    public FileListing submitFileListing(FileListingSpec spec)
            throws FileTaskException {
        log.debug("Submiting grid ftp list task");
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
        // Get grid ftp file resource
        GridFtpResource gridFtpResource = (GridFtpResource)spec.getFileResource();
        if (gridFtpResource == null) {
            gridFtpResource = (GridFtpResource)getFileResource(fileSet);
            if (gridFtpResource == null) {
                throw new FileTaskException("No grid ftp resource found for given file set");
            }
            spec.setFileResource(gridFtpResource);
        }
        // Get grid ftp conneciton
        log.debug("Getting grid ftp connection for resource");
        GridFtpConnection gridFtpConnection = null;
        try {
            gridFtpConnection = gridFtpResource.createGridFtpConnection(user);
        } catch (Exception e) {
            log.error("Unable to create grid ftp srcConnection", e);
            throw new FileTaskException(e.getMessage());
        }
        GridFtpList gridFtpList = new GridFtpList(spec);
        gridFtpList.setTaskStatus(TaskStatus.SUBMITTED);
        GridFtpListThread gridFtpListThread = new GridFtpListThread(gridFtpConnection, gridFtpList);
        gridFtpListThread.start();
        log.debug("Started grid ftp list task");
        return gridFtpList;
    }

    /**
     * Creates a file name change spec.
     * @return
     */
    public FileNameChangeSpec createFileNameChangeSpec()
            throws FileTaskException {
        return new GridFtpRenameSpec();
    }

    /**
     * Submits a file name change task.
     * @param spec
     * @return
     */
    public FileNameChange submitFileNameChange(FileNameChangeSpec spec)
            throws FileTaskException {
        log.debug("Submiting grid ftp rename task");
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
        GridFtpResource gridFtpResource = (GridFtpResource)spec.getFileResource();
        if (gridFtpResource == null) {
            String hostName = fileLoc.getHost();
            gridFtpResource = getGridFtpResource(hostName);
            if (gridFtpResource == null) {
                log.error("No grid ftp resource available on " + hostName);
                throw new FileTaskException("No grid ftp resource available on " + hostName);
            }
            spec.setFileResource(gridFtpResource);
        }
        log.debug("Getting grid ftp srcConnection for resource");
        GridFtpConnection gridFtpConnection = null;
        try {
            gridFtpConnection = gridFtpResource.createGridFtpConnection(user);
        } catch (Exception e) {
            log.error("Unable to create grid ftp srcConnection", e);
            throw new FileTaskException(e.getMessage());
        }
        GridFtpRename gridFtpRename = new GridFtpRename(spec);
        gridFtpRename.setTaskStatus(TaskStatus.SUBMITTED);
        GridFtpRenameThread gridFtpRenameThread = new GridFtpRenameThread(gridFtpConnection, gridFtpRename);
        gridFtpRenameThread.start();
        log.debug("Started grid ftp rename task");
        return gridFtpRename;
    }

    /**
     * Creates a file make dir spec.
     * @return
     */
    public FileMakeDirSpec createFileMakeDirSpec()
            throws FileTaskException {
        return new GridFtpMakeDirSpec();
    }

    /**
     * Submits a file make dir task.
     * @param spec
     * @return
     */
    public FileMakeDir submitFileMakeDir(FileMakeDirSpec spec)
            throws FileTaskException {
        log.debug("Submiting grid ftp make dir task");
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
        GridFtpResource gridFtpResource = (GridFtpResource)spec.getFileResource();
        if (gridFtpResource == null) {
            String hostName = fileLoc.getHost();
            gridFtpResource = getGridFtpResource(hostName);
            if (gridFtpResource == null) {
                log.error("No grid ftp resource available on " + hostName);
                throw new FileTaskException("No grid ftp resource available on " + hostName);
            }
            spec.setFileResource(gridFtpResource);
        }
        log.debug("Getting grid ftp srcConnection for resource");
        GridFtpConnection gridFtpConnection = null;
        try {
            gridFtpConnection = gridFtpResource.createGridFtpConnection(user);
        } catch (Exception e) {
            log.error("Unable to create grid ftp srcConnection", e);
            throw new FileTaskException(e.getMessage());
        }
        GridFtpMakeDir gridFtpMakeDir = new GridFtpMakeDir(spec);
        gridFtpMakeDir.setTaskStatus(TaskStatus.SUBMITTED);
        GridFtpMakeDirThread gridFtpMakeDirThread = new GridFtpMakeDirThread(gridFtpConnection, gridFtpMakeDir);
        gridFtpMakeDirThread.start();
        log.debug("Started grid ftp make dir task");
        return gridFtpMakeDir;
    }

    /**
     * Creates a file copy spec.
     * @return
     */
    public FileCopySpec createFileCopySpec()
            throws FileTaskException {
        return new GridFtpCopySpec();
    }

    /**
     * Submits a file copy task.
     * @param spec
     * @return
     */
    public FileCopy submitFileCopy(FileCopySpec spec)
            throws FileTaskException {
        return (FileCopy)performFileTransfer(spec);
    }

    /**
     * Creates a file move spec.
     * @return
     */
    public FileMoveSpec createFileMoveSpec()
            throws FileTaskException {
        return new GridFtpMoveSpec();
    }

    /**
     * Submits a file move task.
     * @param spec
     * @return
     */
    public FileMove submitFileMove(FileMoveSpec spec)
            throws FileTaskException {
        return (FileMove)performFileTransfer(spec);
    }

    protected FileTransfer performFileTransfer(FileTransferSpec spec)
            throws FileTaskException {
        log.debug("Submitting grid ftp transfer task");

        // Check user
        User user = spec.getUser();
        if (user == null) {
            throw new FileTaskException("User must be specified!");
        }
        // Check source
        FileSet srcFileSet = spec.getFileSet();
        if (srcFileSet == null) {
            throw new FileTaskException("Source file set must be specified!");
        }
        // Check destination
        FileLocation dstLocation = spec.getDestination();
        if (dstLocation == null) {
            throw new FileTaskException("Destination must be specified!");
        }
        // Get source resource...
        FileResource srcResource = spec.getSrcResource();
        if (srcResource == null) {
            srcResource = getFileResource(srcFileSet);
        }
        // Now if we have a grid ftp source resource try to connect to it.
        GridFtpConnection srcConnection = null;
        if (srcResource != null && srcResource instanceof GridFtpResource) {
            // Save reference
            spec.setFileResource(srcResource);
            // Get connection...
            //log.debug("Getting grid ftp connection for source resource");
            try {
                srcConnection = ((GridFtpResource)srcResource).createGridFtpConnection(user);
            } catch (Exception e) {
                log.error("Unable to create grid ftp srcConnection", e);
                throw new FileTaskException(e.getMessage());
            }
        }
        // Get destination resource...
        FileResource dstResource = spec.getDstResource();
        if (dstResource == null) {
            dstResource = getFileResource(dstLocation);
        }
        // Now if we have a grid ftp  dest resource try to connect to it.
        GridFtpConnection dstConnection = null;
        if (dstResource != null && dstResource instanceof GridFtpResource) {
            // Save reference....
            spec.setDstResource(dstResource);
            // Get connection...
            log.debug("Getting grid ftp connection for destination resource");
            try {
                dstConnection = ((GridFtpResource)dstResource).createGridFtpConnection(user);
            } catch (Exception e) {
                log.error("Unable to create grid ftp srcConnection", e);
                throw new FileTaskException(e.getMessage());
            }
        }
        // If no src and dst resources (i.e. no connections)...
        if (srcResource == null && dstResource == null) {
            throw new FileTaskException("No grid ftp resources found for given file set and destination");
        }

        // Start the transfer...
        BaseFileTransfer fileTransfer = null;
        if (spec instanceof FileCopySpec) {
            fileTransfer = new GridFtpCopy((FileCopySpec)spec);
        } else {
            fileTransfer = new GridFtpMove((FileMoveSpec)spec);
        }
        fileTransfer.setTaskStatus(TaskStatus.SUBMITTED);
        GridFtpTransferThread transferThread
                = new GridFtpTransferThread(srcConnection, dstConnection, fileTransfer);
        transferThread.start();
        log.debug("Started grid ftp transfer task");
        return fileTransfer;
    }

    /**
     * Creates a file deletion spec.
     * @return
     */
    public FileDeletionSpec createFileDeletionSpec()
            throws FileTaskException {
        return new GridFtpDeleteSpec();
    }

    /**
     * Submits a file deletion task.
     * @param spec
     * @return
     */
    public FileDeletion submitFileDeletion(FileDeletionSpec spec)
            throws FileTaskException {
        log.debug("Submiting grid ftp delete task");
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
        // Get grid ftp file resource
        GridFtpResource gridFtpResource = (GridFtpResource)spec.getFileResource();
        if (gridFtpResource == null) {
            gridFtpResource = (GridFtpResource)getFileResource(fileSet);
            if (gridFtpResource == null) {
                throw new FileTaskException("No grid ftp resource found for given file set");
            }
            spec.setFileResource(gridFtpResource);
        }
        // Get grid ftp conneciton
        log.debug("Getting grid ftp connection for resource");
        GridFtpConnection gridFtpConnection = null;
        try {
            gridFtpConnection = gridFtpResource.createGridFtpConnection(user);
        } catch (Exception e) {
            log.error("Unable to create grid ftp connection", e);
            throw new FileTaskException(e.getMessage());
        }
        GridFtpDelete gridFtpDelete = new GridFtpDelete(spec);
        gridFtpDelete.setTaskStatus(TaskStatus.SUBMITTED);
        GridFtpDeleteThread gridFtpDeleteThread = new GridFtpDeleteThread(gridFtpConnection, gridFtpDelete);
        gridFtpDeleteThread.start();
        log.debug("Started grid ftp delete task");
        return gridFtpDelete;
    }

    public FileUploadSpec createFileUploadSpec()
            throws FileTaskException {
        return new GridFtpUploadSpec();
    }

    public FileUpload submitFileUpload(FileUploadSpec spec)
            throws FileTaskException {
        log.debug("Submitting grid ftp upload task");
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
        GridFtpResource gridFtpResource = (GridFtpResource)spec.getFileResource();
        if (gridFtpResource == null) {
            String hostName = dstLocation.getHost();
            gridFtpResource = getGridFtpResource(hostName);
            if (gridFtpResource == null) {
                log.error("No grid ftp resource available on " + hostName);
                throw new FileTaskException("No grid ftp resource available on " + hostName);
            }
            spec.setFileResource(gridFtpResource);
        }
        log.debug("Getting grid ftp connection for resource");
        GridFtpConnection gridFtpConnection = null;
        try {
            gridFtpConnection = gridFtpResource.createGridFtpConnection(user);
        } catch (Exception e) {
            log.error("Unable to create grid ftp srcConnection", e);
            throw new FileTaskException(e.getMessage());
        }
        // Start the upload...
        GridFtpUpload fileUpload = new GridFtpUpload(spec);
        fileUpload.setTaskStatus(TaskStatus.SUBMITTED);
        log.debug("Started local upload task");
        File localFile = new File(localFilePath);
        if (!localFile.exists()) {
            fileUpload.setTaskStatus(TaskStatus.FAILED, "Invalid local file path " + localFilePath);
            return fileUpload;
        }
        fileUpload.setTaskStatus(TaskStatus.SUBMITTED);
        GridFtpUploadThread gridFtpUploadThread = new GridFtpUploadThread(gridFtpConnection, fileUpload);
        gridFtpUploadThread.start();
        log.debug("Started grid ftp upload task");
        return fileUpload;
    }

    public FileDownloadSpec createFileDownloadSpec()
            throws FileTaskException {
        return new GridFtpDownloadSpec();
    }

    public FileDownload submitFileDownload(FileDownloadSpec spec)
            throws FileTaskException {
        log.debug("Submitting grid ftp download task");
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
        GridFtpResource gridFtpResource = (GridFtpResource)spec.getFileResource();
        if (gridFtpResource == null) {
            String hostName = srcLocation.getHost();
            gridFtpResource = getGridFtpResource(hostName);
            if (gridFtpResource == null) {
                log.error("No grid ftp resource available on " + hostName);
                throw new FileTaskException("No grid ftp resource available on " + hostName);
            }
            spec.setFileResource(gridFtpResource);
        }
        log.debug("Getting grid ftp connection for resource");
        GridFtpConnection gridFtpConnection = null;
        try {
            gridFtpConnection = gridFtpResource.createGridFtpConnection(user);
        } catch (Exception e) {
            log.error("Unable to create grid ftp srcConnection", e);
            throw new FileTaskException(e.getMessage());
        }
        // Start the download...
        GridFtpDownload fileDownload = new GridFtpDownload(spec);
        fileDownload.setTaskStatus(TaskStatus.SUBMITTED);
        log.debug("Started local download task");
        File localFile = new File(localFilePath);
        fileDownload.setTaskStatus(TaskStatus.SUBMITTED);
        GridFtpDownloadThread gridFtpDownloadThread = new GridFtpDownloadThread(gridFtpConnection, fileDownload);
        gridFtpDownloadThread.start();
        log.debug("Started grid ftp download task");
        return fileDownload;
    }
}
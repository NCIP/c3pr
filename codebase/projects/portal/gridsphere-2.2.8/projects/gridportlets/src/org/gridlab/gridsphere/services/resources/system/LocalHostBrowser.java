/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowser;
import org.gridlab.gridsphere.services.file.tasks.*;
import org.gridlab.gridsphere.services.core.secdir.SecureDirectoryService;
import org.gridlab.gridsphere.services.core.secdir.FileLocationID;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.io.*;

/**
 * Implements a local host file browser.
 */
public class LocalHostBrowser extends BaseFileBrowser {

    public static final String CATEGORY = "LocalHost";
    private static PortletLog log = SportletLog.getInstance(LocalHostBrowser.class);
    protected SecureDirectoryService secureDirectoryService = null;

    public LocalHostBrowser() {
    }


    public InputStream createInputStream(FileLocation loc)
            throws IOException, FileException {
        String realPath = getRealPath(loc);
        File file = new File(realPath);
        return new FileInputStream(file);
    }

    public OutputStream createOutputStream(FileLocation loc)
            throws IOException, FileException {
        String realPath = getRealPath(loc);
        File file = new File(realPath);
        return new FileOutputStream(file);
    }

    public void init(FileBrowserService service, User user, FileResource resource)
            throws FileException {
        super.init(service, user, resource);
        try {
            PortletServiceFactory factory = SportletServiceFactory.getInstance();
            secureDirectoryService = (SecureDirectoryService)
                    factory.createPortletService(SecureDirectoryService.class, null, true);
        } catch (PortletServiceException e) {
            log.error(e.getMessage());
            throw new FileException("Unable to get required portlet services");
        }
        try {
            String userId = user.getID();
            if (!secureDirectoryService.categoryExistsForUser(userId, CATEGORY)) {
                secureDirectoryService.createCategoryForUser(userId, CATEGORY);
            }
            // Create tmp dir if necessary
            // TODO: TEMPORARY PLACE FOR DOWNLOADING REMOTE FILES, NOT IDEAL
            FileLocationID tmpDirId = secureDirectoryService.createFileLocationID(user.getID(),
                                                                        LocalHostBrowser.CATEGORY,
                                                                        "/tmp");
            File tmpDir = secureDirectoryService.getFile(tmpDirId);
            if (!tmpDir.exists()) {
                tmpDir.mkdir();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileException("Unable to get required portlet services", e);
        }
    }

    public String getCurrentDownloadUrl()
            throws FileException {
        return getDownloadUrl(location);
    }

    public String getDownloadUrl(FileLocation location)
            throws FileException {
        String path = location.getFilePath();
        FileLocationID pathId =
                 secureDirectoryService.createFileLocationID(user.getID(),
                                                             LocalHostBrowser.CATEGORY,
                                                             path);
        String fileName = location.getFileName();
        String url = secureDirectoryService.getDownloadFileUrl(pathId, fileName, null);
        log.error("Download user is " + pathId.getUserID());
        log.error("Download category is " + pathId.getCategory());
        log.error("Download path is " + pathId.getFilePath());
        log.error("Download url is " + url);
        if (url == null) {
          log.error("Unable to get download url from secure directory service");
            throw new FileException("Unable to get download url from secure directory service");
        }
        return url;
    }


    public String getRealPath(String path)
            throws FileException {
        FileLocationID pathId =
                 secureDirectoryService.createFileLocationID(user.getID(),
                                                             LocalHostBrowser.CATEGORY,
                                                             path);
        File file = secureDirectoryService.getFile(pathId);
        if (file == null) {
            log.error("Unable to get real path to file in secure directory service");
            throw new FileException("Unable to get real path to file in secure directory service");
        }
        return file.getPath();
    }

    public String getRealPath(FileLocation location)
            throws FileException {
        String path = location.getFilePath();
        return getRealPath(path);
    }

    public String doGetHomeDir()
            throws FileException {
        return "/";
    }

    protected FileLocation doChangeDir(String dir)
            throws FileException {
        log.debug("doChangeDir " + dir);
        dir = LocalHostBrowserService.prepareFilePath(dir);
        if (!dir.startsWith("/")) {
            dir = '/' + dir;
        }
        FileLocationID dirLocId
                = secureDirectoryService.createFileLocationID(user.getID(), CATEGORY, dir);
        File file = secureDirectoryService.getFile(dirLocId);
        if (file == null) {
            throw new FileException("Invalid directory " + dir);
        }
        if (file.isDirectory()) {
            location.setFilePath(dir);
        } else {
            throw new FileException(dir + " is not a directory");
        }
        return location;
    }

    protected FileLocation doGoUpDir()
            throws FileException {
        log.debug("doGoUpDir");
        String dir = location.getFilePath();
        dir = FileLocation.getParentPath(dir);
        location.setFilePath(dir);
        return location;
    }

    public FileLocation info(String path)
            throws FileException {
        FileLocationID pathLocId
            = secureDirectoryService.createFileLocationID(user.getID(), CATEGORY, path);
        File file = secureDirectoryService.getFile(pathLocId);
        if (file == null) {
            throw new FileException("Invalid file path " + path);
        }
        FileLocation location = createFileLocation(path);
        if (file.isDirectory()) {
            location.setFileType(FileType.DIRECTORY);
        }
        location.setFileSize(new Long(file.length()));
        location.setDateLastModified(new Long(file.lastModified()));
        return location;
    }

    public boolean exists(String path) {
        FileLocationID pathLocId
            = secureDirectoryService.createFileLocationID(user.getID(), CATEGORY, path);
        File file = secureDirectoryService.getFile(pathLocId);
        return (file != null);
    }

    public FileCopy copy(FileSet src, FileLocation dst)
            throws FileTaskException {
        // Check source resource
        FileResource srcResource = getFileResource(src);
        if (srcResource == null) {
            throw new FileTaskException("No file resource found for given source file set");
        }
        // If source resource is remote, use a remote browser to do the copy
        boolean isSrcLocalHost = FileLocation.isLocalHost(srcResource.getHostName());
        if (!isSrcLocalHost) {
            try {
                FileBrowser fileBrowser = srcResource.createFileBrowser(user);
                return fileBrowser.copy(src, dst);
            } catch (FileException e) {
                throw new FileTaskException("Unable to perform remote transfer " + e.getMessage());
            }
        }
        // Check destination resource
        FileResource dstResource = getFileResource(dst);
        if (dstResource == null) {
            throw new FileTaskException("No file resource found for given destination");
        }
        // If destination resource is remote, use a remote browser to do the copy
        boolean isDstLocalHost = FileLocation.isLocalHost(dstResource.getHostName());
        if (!isDstLocalHost) {
            try {
                FileBrowser fileBrowser = dstResource.createFileBrowser(user);
                return fileBrowser.copy(src, dst);
            } catch (FileException e) {
                throw new FileTaskException("Unable to perform remote transfer " + e.getMessage());
            }
        }
        // Perform the copy
        FileCopySpec fileCopySpec = fileBrowserService.createFileCopySpec();
        fileCopySpec.setUser(user);
        fileCopySpec.setFileSet(src);
        fileCopySpec.setSrcResource(srcResource);
        fileCopySpec.setDestination(dst);
        fileCopySpec.setDstResource(dstResource);
        return fileBrowserService.submitFileCopy(fileCopySpec);
    }

    public FileMove move(FileSet src, FileLocation dst)
            throws FileTaskException {
        // Check source resource
        FileResource srcResource = getFileResource(src);
        if (srcResource == null) {
            throw new FileTaskException("No file resource found for given source file set");
        }
        // If source resource is remote, use a remote browser to do the copy
        boolean isSrcLocalHost = FileLocation.isLocalHost(srcResource.getHostName());
        if (!isSrcLocalHost) {
            try {
                FileBrowser fileBrowser = srcResource.createFileBrowser(user);
                return fileBrowser.move(src, dst);
            } catch (FileException e) {
                throw new FileTaskException("Unable to perform remote transfer " + e.getMessage());
            }
        }
        // Check destination resource
        FileResource dstResource = getFileResource(dst);
        if (dstResource == null) {
            throw new FileTaskException("No file resource found for given destination");
        }
        // If destination resource is remote, use a remote browser to do the copy
        boolean isDstLocalHost = FileLocation.isLocalHost(dstResource.getHostName());
        if (!isDstLocalHost) {
            try {
                FileBrowser fileBrowser = dstResource.createFileBrowser(user);
                return fileBrowser.move(src, dst);
            } catch (FileException e) {
                throw new FileTaskException("Unable to perform remote transfer " + e.getMessage());
            }
        }
        // Perform the copy
        FileMoveSpec fileMoveSpec = fileBrowserService.createFileMoveSpec();
        fileMoveSpec.setUser(user);
        fileMoveSpec.setFileSet(src);
        fileMoveSpec.setSrcResource(srcResource);
        fileMoveSpec.setDestination(dst);
        fileMoveSpec.setDstResource(dstResource);
        return fileBrowserService.submitFileMove(fileMoveSpec);
    }
}

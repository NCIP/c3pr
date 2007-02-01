/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.file.sets.FilePatternSet;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.file.tasks.*;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.util.ServiceUtil;

import java.net.MalformedURLException;
import java.util.List;
import java.io.*;

/**
 * Base abstract implementation of a file browser.
 */
public abstract class BaseFileBrowser implements FileBrowser {

    private static PortletLog log = SportletLog.getInstance(BaseFileBrowser.class);
    protected FileBrowserService fileBrowserService = null;
    protected User user = null;
    protected String resourceDn = null;
    protected FileResourceType resourceType = null;
    protected FileLocation location = null;
    protected String homeDir = null;

    public BaseFileBrowser() {
    }

    public void init(FileBrowserService service, User user, FileResource resource)
            throws FileException {
        log.debug("Initializing file browser");
        fileBrowserService = service;
        this.user = user;
        setResource(resource);
        location = createDirectoryLocation("");
        homeDir = null;
    }

    public void destroy() {
    }

    public User getUser() {
        return user;
    }

    public FileResource getFileResource() {
        ResourceRegistryService registry = (ResourceRegistryService)
                ServiceUtil.getPortletService(user, ResourceRegistryService.class);
        return (FileResource)registry.getResourceByDn(resourceDn);
    }


    public String getFileHostName() {
        return getFileResource().getHostName();
    }

    public FileLocation getCurrentLocation() {
        return location;
    }

    public String getCurrentDirectory() {
        return location.getFilePath();
    }

    public String getHomeDirectory()
            throws FileException {
        if (homeDir == null) {
            homeDir = doGetHomeDir();
        }
        return homeDir;
    }

    protected abstract String doGetHomeDir() throws FileException;

    public void changeLocation(FileLocation loc)
            throws FileException {
        String dir = null;
        if (loc.getIsSoftLink()) {
            dir = loc.getSoftLinkPath();
            log.debug("Changing location to soft link " + dir);
        } else {
            dir = loc.getFilePath();
            log.debug("Changing location to file path " + dir);
        }
        changeDirectory(dir);
    }

    public void changeDirectory(String dir)
            throws FileException {
        log.debug("doChangeDir " + dir);
        // If dir is not null...
        if (dir != null) {
            dir = dir.trim();
            // If empty dir given to us, reset to null
            if (dir.equals("")) {
                dir = null;
            // If ".", assume current directory....
            } else if (dir.equals(".") || dir.endsWith("/.")) {
                dir = location.getFilePath();
                // Same thing... change dir to null
                // if it is empty string
                if (dir.equals("")) {
                    dir = null;
                }
            // If "..", assume we want to go up dir.
            } else if (dir.equals("..") || dir.endsWith("/..")) {
                goUpDirectory();
                return;
            }
        }
        log.debug("Changing dir to " + dir);
        location = doChangeDir(dir);
    }

    protected abstract FileLocation doChangeDir(String dir) throws FileException;

    public void goUpDirectory()
            throws FileException {
        location = doGoUpDir();
    }

    protected abstract FileLocation doGoUpDir() throws FileException;

    protected String createUrl(String dir) {
        StringBuffer urlBuffer = new StringBuffer("file://");
        String hostName = getFileResource().getHostName();
        urlBuffer.append(hostName);
        urlBuffer.append('/');
        urlBuffer.append(dir);
        return urlBuffer.toString();
    }

    public FileLocation createFileLocation(String path)
            throws FileException {
        try {
            String url = createUrl(path);
            return new FileLocation(url);
        } catch (MalformedURLException e) {
            throw new FileException(e.getMessage());
        }
    }

    public FileLocation createDirectoryLocation(String path)
            throws FileException {
        try {
            String url = createUrl(path);
            return new FileLocation(url, FileType.DIRECTORY);
        } catch (MalformedURLException e) {
            throw new FileException(e.getMessage());
        }
    }

    public boolean exists(String path) {
        try {
            info(path);
            return true;
        } catch (FileException e) {
            return false;
        }
    }

    public InputStream createInputStream(String path)
            throws IOException, FileException {
        FileLocation fileLocation = createFileLocation(path);
        return createInputStream(fileLocation);
    }

    public InputStream createInputStream(FileLocation loc)
            throws IOException, FileException {
        throw new FileException("createInputStream not yet implemented for this file resource type!");
    }

    public OutputStream createOutputStream(String path)
            throws IOException, FileException {
        FileLocation fileLocation = createFileLocation(path);
        return createOutputStream(fileLocation);
    }

    public OutputStream createOutputStream(FileLocation loc)
            throws IOException, FileException {
        throw new FileException("createOutputStream not yet implemented for this file resource type!");
    }

    public List list()
            throws FileException {
        try {
            FileListingSpec fileListSpec = fileBrowserService.createFileListingSpec();
            fileListSpec.setUser(user);
            fileListSpec.setFileResource(getFileResource());
            FileSet fileSet = new FilePatternSet(location);
            fileListSpec.setFileSet(fileSet);
            fileListSpec.setBrowserFlag(true);
            FileListing fileList = fileBrowserService.submitFileListing(fileListSpec);
            fileList.waitFor();
            return fileList.getFileLocations();
        } catch (Exception e) {
            log.error("Unable to perform list", e);
            throw new FileException(e.getMessage());
        }
    }

    public List list(String path)
            throws FileException {
        try {
            FileListingSpec fileListSpec = fileBrowserService.createFileListingSpec();
            fileListSpec.setUser(user);
            fileListSpec.setFileResource(getFileResource());
            FileLocation location = createFileLocation(path);
            FileSet fileSet = new FilePatternSet(location);
            fileListSpec.setFileSet(fileSet);
            fileListSpec.setBrowserFlag(true);
            FileListing fileList = fileBrowserService.submitFileListing(fileListSpec);
            fileList.waitFor();
            return fileList.getFileLocations();
        } catch (Exception e) {
            log.error("Unable to perform list", e);
            throw new FileException(e.getMessage());
        }
    }

    public List list(FileLocation loc)
            throws FileException {
        try {
            FileListingSpec fileListSpec = fileBrowserService.createFileListingSpec();
            fileListSpec.setUser(user);
            fileListSpec.setFileResource(getFileResource(loc));
            FileSet fileSet = new FilePatternSet(loc);
            fileListSpec.setFileSet(fileSet);
            fileListSpec.setBrowserFlag(true);
            FileListing fileList = fileBrowserService.submitFileListing(fileListSpec);
            fileList.waitFor();
            return fileList.getFileLocations();
        } catch (Exception e) {
            log.error("Unable to perform list", e);
            throw new FileException(e.getMessage());
        }
    }

    public List list(FileSet fileSet)
            throws FileException {
        try {
            FileListingSpec fileListSpec = fileBrowserService.createFileListingSpec();
            fileListSpec.setUser(user);
            fileListSpec.setFileResource(getFileResource(fileSet));
            fileListSpec.setFileSet(fileSet);
            fileListSpec.setBrowserFlag(true);
            FileListing fileList = fileBrowserService.submitFileListing(fileListSpec);
            fileList.waitFor();
            return fileList.getFileLocations();
        } catch (Exception e) {
            log.error("Unable to perform list", e);
            throw new FileException(e.getMessage());
        }
    }

    public FileMakeDir makeDirectory(String dirName)
            throws FileTaskException {
        FileMakeDirSpec fileMakeDirSpec = fileBrowserService.createFileMakeDirSpec();
        fileMakeDirSpec.setUser(user);
        fileMakeDirSpec.setFileResource(getFileResource());
        fileMakeDirSpec.setParentLocation(location);
        fileMakeDirSpec.setDirectoryName(dirName);
        return fileBrowserService.submitFileMakeDir(fileMakeDirSpec);
    }

    public FileMakeDir makeDirectory(FileLocation loc)
            throws FileTaskException {
        FileMakeDirSpec fileMakeDirSpec = fileBrowserService.createFileMakeDirSpec();
        fileMakeDirSpec.setUser(user);
        fileMakeDirSpec.setFileResource(getFileResource(loc));
        fileMakeDirSpec.setParentLocation(loc);
        fileMakeDirSpec.setDirectoryName("");
        return fileBrowserService.submitFileMakeDir(fileMakeDirSpec);
    }

    public FileMakeDir makeDirectory(FileLocation loc, String dirName)
            throws FileTaskException {
        FileMakeDirSpec fileMakeDirSpec = fileBrowserService.createFileMakeDirSpec();
        fileMakeDirSpec.setUser(user);
        fileMakeDirSpec.setFileResource(getFileResource(loc));
        fileMakeDirSpec.setParentLocation(loc);
        fileMakeDirSpec.setDirectoryName(dirName);
        return fileBrowserService.submitFileMakeDir(fileMakeDirSpec);
    }

    public FileNameChange rename(FileLocation loc, String newName)
            throws FileTaskException {
        FileNameChangeSpec fileRenameSpec = fileBrowserService.createFileNameChangeSpec();
        fileRenameSpec.setUser(user);
        fileRenameSpec.setFileResource(getFileResource(loc));
        fileRenameSpec.setFileLocation(loc);
        fileRenameSpec.setNewFileName(newName);
        return fileBrowserService.submitFileNameChange(fileRenameSpec);
    }

    public FileCopy copy(FileLocation loc, FileLocation dst)
            throws FileTaskException {
        FilePatternSet src = new FilePatternSet();
        src.setFileLocation(loc);
        return copy(src, dst);
    }

    public FileCopy copy(FileSet src, FileLocation dst)
            throws FileTaskException {
        FileCopySpec fileCopySpec = fileBrowserService.createFileCopySpec();
        fileCopySpec.setUser(user);
        fileCopySpec.setFileSet(src);
        fileCopySpec.setSrcResource(getFileResource(src));
        fileCopySpec.setDestination(dst);
        fileCopySpec.setDstResource(getFileResource(dst));
        return fileBrowserService.submitFileCopy(fileCopySpec);
    }

    public FileMove move(FileLocation loc, FileLocation dst)
            throws FileTaskException {
        FilePatternSet src = new FilePatternSet();
        src.setFileLocation(loc);
        return move(src, dst);
    }

    public FileMove move(FileSet src, FileLocation dst)
            throws FileTaskException {
        FileMoveSpec fileMoveSpec = fileBrowserService.createFileMoveSpec();
        fileMoveSpec.setUser(user);
        fileMoveSpec.setFileSet(src);
        fileMoveSpec.setSrcResource(getFileResource(src));
        fileMoveSpec.setDestination(dst);
        fileMoveSpec.setDstResource(getFileResource(dst));
        return fileBrowserService.submitFileMove(fileMoveSpec);
    }

    public FileDeletion delete(FileLocation loc)
            throws FileTaskException {
        FilePatternSet set = new FilePatternSet();
        set.setFileLocation(loc);
        return delete(set);
    }

    public FileDeletion delete(FileSet set)
            throws FileTaskException {
        FileDeletionSpec fileDeleteSpec = fileBrowserService.createFileDeletionSpec();
        fileDeleteSpec.setUser(user);
        fileDeleteSpec.setFileResource(getFileResource(set));
        fileDeleteSpec.setFileSet(set);
        return fileBrowserService.submitFileDeletion(fileDeleteSpec);
    }

    public FileUpload upload(String localFilePath, FileLocation destination)
            throws FileTaskException {
        FileUploadSpec fileUploadSpec = fileBrowserService.createFileUploadSpec();
        fileUploadSpec.setUser(user);
        fileUploadSpec.setFileResource(getFileResource());
        fileUploadSpec.setFilePath(localFilePath);
        fileUploadSpec.setUploadLocation(destination);
        return fileBrowserService.submitFileUpload(fileUploadSpec);
    }

    public FileDownload download(FileLocation source, String localFilePath)
            throws FileTaskException {
        FileDownloadSpec fileDownloadSpec = fileBrowserService.createFileDownloadSpec();
        fileDownloadSpec.setUser(user);
        fileDownloadSpec.setFileLocation(source);
        fileDownloadSpec.setFileResource(getFileResource());
        fileDownloadSpec.setDownloadPath(localFilePath);
        return fileBrowserService.submitFileDownload(fileDownloadSpec);
    }

    protected FileResource getFileResource(FileSet fileSet)
            throws FileTaskException {
        FileLocation fileLoc = null;
        if (fileSet instanceof FilePatternSet) {
            FilePatternSet filePatternSet = (FilePatternSet)fileSet;
            fileLoc = filePatternSet.getFileLocation();
            return getFileResource(fileLoc);
        } else if (fileSet instanceof FileLocationSet) {
            FileLocationSet fileLocSet = (FileLocationSet)fileSet;
            List fileLocList = fileLocSet.getFileLocations();
            if (fileLocList.size() > 0) {
                fileLoc = (FileLocation)fileLocList.get(0);
            }
        } else {
            throw new FileTaskException("File set not supported " + fileSet.getClass().getName());
        }
        return getFileResource(fileLoc);
    }

    protected FileResource getFileResource(FileLocation loc) {
        FileResource fileResource = getFileResource();
        if (loc != null) {
            String host = loc.getHost();
            if ( !host.equals("") && !host.equals(fileResource.getHostName()) ) {
                fileResource = FileUtil.getFileBrowserService(user).getFileResource(loc);
            }
        }

        return fileResource;
    }

    protected void setResource(FileResource resource) {
        resourceDn = resource.getDn();
    }
}

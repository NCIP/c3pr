package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileType;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.globus.ftp.GridFTPClient;
import org.globus.ftp.FileInfo;
import org.globus.ftp.exception.ServerException;
import org.globus.ftp.*;
import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

/*
* @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
* @version $Id: GridFtpThread.java,v 1.1.1.1 2007-02-01 20:41:16 kherm Exp $
* <p>
* A thread for performing grid ftp client operations with a given
* grid ftp source and destination connections.
*/

public class GridFtpThread extends Thread {

    protected static PortletLog log = SportletLog.getInstance(GridFtpThread.class);
    protected GridFtpConnection srcConnection = null;
    protected GridFtpConnection dstConnection = null;

    public GridFtpThread(GridFtpConnection connection) {
        super();
        srcConnection = connection;
    }

    public GridFtpThread(GridFtpConnection srcConnection, GridFtpConnection dstConnection) {
        super();
        this.srcConnection = srcConnection;
        this.dstConnection = dstConnection;
    }

    public void destroy() {
        if (srcConnection != null)  {
            srcConnection.release();
            srcConnection = null;
        }
        if (dstConnection != null) {
            dstConnection.release();
            dstConnection = null;
        }
    }

    public void run() {
        runCommand();
        if (srcConnection != null)  {
            srcConnection.release();
            srcConnection = null;
        }
        if (dstConnection != null) {
            dstConnection.release();
            dstConnection = null;
        }
    }

    public void runCommand() {}

    public FileLocation info(FileLocation location)
            throws GridFtpException {
        try {
            GridFTPClient client = srcConnection.getGridFTPClient();
            // Get info about list location, to be used below
            log.debug("grid ftp 0");
            client.setPassive();
            client.setLocalActive();
            String path = location.getFilePath();
            List fileInfoList = client.list(path);
            log.debug("grid ftp 1");
            FileInfo fileInfo = (FileInfo)fileInfoList.get(0);
            String fileUrl = location.getUrlWithoutQuery();
            FileLocation info = null;
            if (fileInfo.isSoftLink()) {
                info = createSoftLink(fileUrl, false, fileInfo);
            } else {
                info = createFileLocation(fileUrl, fileInfo);
            }
            srcConnection.close();
            return info;
        } catch (Exception e) {
            log.error("Failed to execute grid ftp info", e);
            throw new GridFtpException(e.getMessage(), e);
        }
    }

    public boolean exists(String path) {
        try {
            GridFTPClient client = srcConnection.getGridFTPClient();
            return client.exists(path);
        } catch (Exception e) {
            log.error("Failed to execute grid ftp exists", e);
            return false;
        }
    }

    public List list(FileLocation listLocation, boolean browserFlag)
            throws GridFtpException {
        log.debug("Listing files at " + listLocation.getUrl());
        // If file location is a soft link, we must get use
        // the soft link path to list, rather than the file path
        if (listLocation.getIsSoftLink()) {
            String softLinkPath = listLocation.getSoftLinkPath();
            log.debug("Using soft link path " + softLinkPath);
            FileLocation linkLocation = new FileLocation(listLocation);
            linkLocation.setFileType(FileType.FILE);
            linkLocation.setFilePath(softLinkPath);
            listLocation = linkLocation;
        }
        // Get location url without query and file path
        String listPath = listLocation.getFilePath();
        // Okay, get file information about list path
        //FileInfo fileInfo = getFileInfo(listPath);
        //boolean isDirectory = fileInfo.isDirectory();
        boolean isDirectory = true;
        // Then list contents...
        log.debug("Initiating grid ftp list in " + listPath);
        FileInfo fileInfo = null;
        List fileInfoList = null;
        try {
            GridFTPClient client = srcConnection.getGridFTPClient();
            // Get info about list location, to be used below

            client.setDataChannelAuthentication(DataChannelAuthentication.NONE);
            log.debug("grid ftp 0");
            client.setPassive();
            client.setLocalActive();
            fileInfoList = client.list(listPath);
            if (fileInfoList.isEmpty()) {
                isDirectory = true;
            } else {
                fileInfo = (FileInfo)fileInfoList.get(0);

                isDirectory = fileInfo.isDirectory();

                // Some non-Globus FTP servers ignore the -d flag in LIST
                // [issued by FTPClient.list(char*)]. In this case the
                // above list command either returns the contents of a
                // directory, or the information about a single file
                // Deal with them appropriately - Shreyas                
                if (!isDirectory) {
                    log.debug("Changing directory " + listPath);
                    try {
                        client.changeDir(listPath);
                        isDirectory=true;
                    } catch (ServerException se) {
                        // If we caught a ServerException, check the code:
                        // 550 is the ftp protocol code for cd failure
                        if (se.getCode()==550) {
                            log.debug("Caught 550 error " + listPath);
                            isDirectory=false;
                        } else {
                            // Unknown failure
                            log.error("Failed to change directory", se);
                            throw new GridFtpException(se.getMessage(), se);
                        }
                    }
                }
            }
            log.debug("grid ftp 1 " + listPath);
            // Only perform the list if our list path was a directory
            if (isDirectory) {
                // If list path is blank, set to "~" to force
                // change to home directory....
                if (listPath.equals("")) listPath = "~";
                // Change to directory...
                if (listPath.endsWith("/..")) {
                    listPath = FileLocation.getParentPath(listPath);
                    listPath = FileLocation.getParentPath(listPath);
                } else if (listPath.endsWith("/.")) {
                    listPath = FileLocation.getParentPath(listPath);
                }
                client.changeDir(listPath);
                log.debug("grid ftp 2 " + listPath);
                // Reset our list path with the correct dir info
                String currentDir = client.getCurrentDir();
                // TODO: BUG IN GT4???!!!!! I see this when listing on "/"
                if (currentDir != null && !currentDir.trim().equals("")) {
                    listPath = currentDir;
                }
                log.debug("grid ftp 3 " + listPath);
                // Then perform list
                client.setPassive();
                client.setLocalActive();
                fileInfoList = client.list();
                log.debug("grid ftp 4");
            }
            srcConnection.close();
        } catch (Exception e) {
            log.error("Failed to execute grid ftp list", e);
            throw new GridFtpException(e.getMessage(), e);
        }
        // Set hostname on list location if not already set
        if (listLocation.getHost().equals("")) {
            listLocation.setHost(srcConnection.getHostName());
        }
        log.debug("Listed " + fileInfoList.size() + " files at " + listLocation.getUrlWithoutQuery());
        // Reset file path with latest value from above
        listLocation.setFilePath(listPath);
        // Get list url with out query... will be used as base url below
        String listUrl = listLocation.getUrlWithoutQuery();
        log.debug("Listed " + fileInfoList.size() + " files at " + listUrl);
        List fileLocations = new Vector();
        if (isDirectory) {
            // If list location is directory, list its contents
            // Append "/" to list url for use below
            if (!listUrl.endsWith("/")) listUrl += "/";
            // If browser flag, first add "." and ".."
            if (browserFlag && fileInfo != null) {
                // Some Grid FTP servers return "." and ".." (acting like ls -a).
                if (fileInfoList.size() > 0 && !((FileInfo)fileInfoList.get(0)).getName().equals(".")) {
                    // First add "."
                    log.debug("Adding . to file list");
                    String fileUrl = listUrl + '.';
                    FileLocation fileLocation = createFileLocation(fileUrl, fileInfo);
                    fileLocations.add(fileLocation);
                    // If not at root dir then ".."
                    if (!fileInfo.getName().equals("/")) {
                        log.debug("Adding .. to file list");
                        fileUrl = listUrl + "..";
                        fileLocation = new FileLocation();
                        fileLocation.setUrlString(fileUrl);
                        fileLocation.setFileType(FileType.DIRECTORY);
                        fileLocations.add(fileLocation);
                    }
                }
            }
            // Now add files to list...
            for (int ii = 0; ii < fileInfoList.size(); ++ii) {
                fileInfo = (FileInfo)fileInfoList.get(ii);

                // Shreyas Hack - Trim the fileInfo for leading whitepace:
                fileInfo.setName(fileInfo.getName().trim());

                log.debug("Next file info... " + fileInfo.toString());
                FileLocation fileLocation = null;
                if (fileInfo.isSoftLink()) {
                    fileLocation = createSoftLink(listUrl, true, fileInfo);
                } else {
                    String fileUrl = listUrl + fileInfo.getName();
                    fileLocation = createFileLocation(fileUrl, fileInfo);
                }
                fileLocations.add(fileLocation);
            }
        } else {
            // Else just return the list location with its new file info
            fileInfo.setName(fileInfo.getName().trim());
            log.debug("File info... " + fileInfo.toString());
            FileLocation fileLocation = null;
            if (fileInfo.isSoftLink()) {
                fileLocation = createSoftLink(listUrl, false, fileInfo);
            } else {
                fileLocation = createFileLocation(listUrl, fileInfo);
            }
            fileLocations.add(fileLocation);
        }
        log.debug("Returning file list of size " + fileLocations.size());
        return fileLocations;
    }

    protected FileInfo getFileInfo(String filePath)
            throws GridFtpException {
        log.debug("Getting grid ftp info " + filePath);
        List fileInfoList = null;
        try {
            GridFTPClient client = srcConnection.getGridFTPClient();
            fileInfoList = client.list(filePath);
            srcConnection.close();
        } catch (Exception e) {
            log.error("Failed to execute grid ftp info", e);
            throw new GridFtpException(e.getMessage(), e);
        }
        FileInfo fileInfo = (FileInfo)fileInfoList.get(0);
        log.debug("Completed grid ftp info " + filePath);
        return fileInfo;
    }


    protected static FileLocation createFileLocation(String fileUrl, FileInfo fileInfo) {
        log.debug("Creating file location for url " + fileUrl);
        //log.debug("file mode is? = " + fileInfo.getModeAsString());
        FileLocation fileLocation = new FileLocation();
        fileLocation.setUrlString(fileUrl);
        if (fileInfo.isDirectory()) {
            fileLocation.setFileType(FileType.DIRECTORY);
        } else if (fileInfo.isSoftLink()) {
            fileLocation.setFileType(FileType.SOFT_LINK);
        } else if (fileInfo.isDevice()) {
            fileLocation.setFileType(FileType.DEVICE);
        }
        long fileSize = fileInfo.getSize();
        if (fileSize > 0) {
            fileLocation.setFileSize(new Long(fileSize));
        }
        return fileLocation;
    }

    protected FileLocation createSoftLink(String fileUrl, boolean appendFileName, FileInfo fileInfo) {
        String nextInfoString = fileInfo.toString();
        int nameIndex = nextInfoString.indexOf(" ->");
        // File info looks like "FileInfo: <name> -> <path> <date> softlink"
        String fileName = nextInfoString.substring(10, nameIndex);
        String softLinkPath = nextInfoString.substring(nameIndex+4);
        // Not totally reliable but... for initial impl we'll try...
        int linkIndex = softLinkPath.indexOf(" ");
        softLinkPath = softLinkPath.substring(0, linkIndex);
        log.debug("Adding soft link " + fileName + " -> " + softLinkPath);
        FileLocation softLink = null;
        if (appendFileName) {
            fileUrl += fileName;
            softLink = createFileLocation(fileUrl, fileInfo);
        } else {
            softLink = createFileLocation(fileUrl, fileInfo);
            softLink.setFileName(fileName);
        }
        softLink.setSoftLinkPath(softLinkPath);
        return softLink;
    }

    public void rename(FileLocation fileLoc, String fileName)
            throws GridFtpException {
        String filePath = fileLoc.getFilePath();
        log.debug("Initiating grid ftp rename " + fileLoc.getUrl() + " ->" + fileName);
        try {
            GridFTPClient client = srcConnection.getGridFTPClient();
            client.rename(filePath, fileName);
            srcConnection.close();
        } catch (Exception e) {
            log.error("Failed to execute grid ftp rename", e);
            throw new GridFtpException(e.getMessage(), e);
        }
        log.debug("Completed grid ftp rename " + fileLoc.getUrl() + " ->" + fileName);
    }

    public void mkdir(FileLocation fileLoc, String dirName)
            throws GridFtpException {
        // Concatenate dirs...
        String filePath = fileLoc.getFilePath();
        if (!filePath.equals("") && !filePath.endsWith("/")) {
            filePath += "/";
        }
        filePath += dirName;
        // Go for it...
        mkdirParent(srcConnection, filePath);
        log.debug("Completed grid ftp mkdir " + filePath) ;
    }

    public void mkdirParent(GridFtpConnection connection, String filePath)
            throws GridFtpException {
        GridFTPClient client = null;
        // Test if parent dir exists
        log.debug("Testing if parent dir exists " + filePath) ;
        boolean parentExists = false;
        try {
            client = connection.getGridFTPClient();
            parentExists = client.exists(filePath);
            connection.close();
        } catch (Exception e) {
            log.error("Failed to execute parent dir exists " + filePath, e);
            throw new GridFtpException(e.getMessage(), e);
        }
        log.debug("Parent dir exists? " + parentExists) ;
        if (!parentExists) {
            // Now create this dir
            log.debug("Entering mkdir -p " + filePath);
            int index = filePath.lastIndexOf("/");
            // If no "/", then try to create dir as is (it is
            // probably relative to home dir then...), otherwise...
            if (index > -1) {
                // Get parent dir
                int length = filePath.length();
                boolean isRoot = false;
                if (index == length-1) {
                    isRoot = filePath.equals("/");
                    if (!isRoot) {
                        filePath = filePath.substring(0, length);
                        index = filePath.lastIndexOf("/");
                    }
                }
                // If parent dir is not root...
                if (!isRoot) {
                    String parentPath = filePath.substring(0, index);
                    // Test if parent dir exists
                    log.debug("Testing if parent dir exists " + parentPath) ;
                    try {
                        client = connection.getGridFTPClient();
                        parentExists = client.exists(parentPath);
                        connection.close();
                    } catch (Exception e) {
                        log.error("Failed to execute parent dir exists " + filePath, e);
                        throw new GridFtpException(e.getMessage(), e);
                    }
                    log.debug("Parent dir exists? " + parentExists) ;
                    // If parent dir does not exist, create parent dir
                    if (!parentExists) {
                        mkdirParent(connection, parentPath);
                    }
                }
                // Now create this dir
                log.debug("Initiating grid ftp mkdir " + filePath) ;
                try {
                    client = connection.getGridFTPClient();
                    client.makeDir(filePath);
                    connection.close();
                } catch (Exception e) {
                    log.error("Failed to execute grid ftp mkdir", e);
                    throw new GridFtpException(e.getMessage(), e);
                }
                log.debug("Completed grid ftp mkdir " + filePath) ;
            }
        }
    }

    public void get(FileLocation src, File dstFile)
            throws GridFtpException {
        if (src.getFileType().equals(FileType.DIRECTORY)) {
            getDirectory(src, dstFile);
        } else {
            String srcPath = src.getFilePath();
            log.debug("Initiating grid ftp get file" + src.getUrl() + " -> " + dstFile.getPath());
            try {
                GridFTPClient client = srcConnection.getGridFTPClient();
                client.setType(Session.TYPE_IMAGE);
                client.get(srcPath, dstFile);
                srcConnection.close();
            } catch (Exception e) {
                log.error("Failed to execute grid ftp get", e);
                throw new GridFtpException(e.getMessage(), e);
            }
            log.debug("Completed grid ftp get file" + src.getUrl() + " -> " + dstFile.getPath());
        }
    }

    public void getDirectory(FileLocation src, File dst)
            throws GridFtpException {
        log.debug("Initiating grid ftp get directory" + src.getUrl() + " -> " + dst.getPath());
        if (!dst.mkdir()) {
            throw new GridFtpException("Failed to make directory " + dst.getPath());
        }
        // Get contents of directory
        List fileLocations = list(src, false);
        // Transfer contents of directory
        String dstPath = dst.getPath();
        if (!dstPath.endsWith("/")) dstPath += "/";
        Iterator fileIterator = fileLocations.iterator();
        while (fileIterator.hasNext()) {
            FileLocation nextLocation = (FileLocation)fileIterator.next();
            String nextPath = dstPath + nextLocation.getFileName();
            File nextFile = new File(nextPath);
            get(nextLocation, nextFile);
        }
        log.debug("Completed grid ftp get directory" + src.getUrl() + " -> " + dst.getPath());
    }

    public void put(File srcFile, FileLocation dst)
            throws GridFtpException {
        if (srcFile.isDirectory()) {
            putDirectory(srcFile, dst);
        } else {
            log.debug("Initiating grid ftp put file" + srcFile.getPath() + " -> " + dst.getUrl());
            String dstPath = dst.getFilePath();
            try {
                GridFTPClient client = dstConnection.getGridFTPClient();
                client.setType(Session.TYPE_IMAGE);
                client.put(srcFile, dstPath, false); // Don't append
                dstConnection.close();
            } catch (Exception e) {
                log.error("Failed to execute grid ftp put", e);
                throw new GridFtpException(e.getMessage(), e);
            }
            log.debug("Initiating grid ftp put file" + srcFile.getPath() + " -> " + dst.getUrl());
        }
    }

    public void putDirectory(File src, FileLocation dst)
            throws GridFtpException {
        log.debug("Initiating grid ftp put directory" + src.getPath() + " -> " + dst.getUrl());
        // Make directory at destination
        mkdirParent(dstConnection, dst.getFilePath());
        // Get contents of directory
        String[] fileArray = src.list();
        // Put contents of directory
        String srcPath = src.getPath();
        if (!srcPath.endsWith("/")) srcPath += "/";
        String dstUrl = dst.getUrlWithoutQuery();
        if (!dstUrl.endsWith("/")) dstUrl += "/";
        for (int ii = 0; ii < fileArray.length; ++ii) {
            String nextPath = srcPath + fileArray[ii];
            File nextFile = new File(nextPath);
            String nextUrl = dstUrl + fileArray[ii];
            FileLocation nextDestination = new FileLocation();
            nextDestination.setUrlString(nextUrl);
            put(nextFile, nextDestination);
        }
        log.debug("Completed grid ftp transfer directory" + src.getPath() + " -> " + dst.getUrl());
    }

    public void transfer(FileLocation src, FileLocation dst)
            throws GridFtpException {
        if (src.getIsDirectory()) {
            transferDirectory(src, dst);
        } else {
            log.debug("Initiating grid ftp transfer file" + src.getUrl() + " -> " + dst.getUrl());
            String srcPath = src.getFilePath();
            String dstPath = dst.getFilePath();
            try {
                GridFTPClient srcClient = srcConnection.getGridFTPClient();
                GridFTPClient dstClient = dstConnection.getGridFTPClient();

                // put this here to make sure that if one service has DCAU turned off the other one
                // is turned off to for third-party transfer to work
                if (srcClient.getDataChannelAuthentication().equals(DataChannelAuthentication.NONE)) {
                    dstClient.setDataChannelAuthentication(DataChannelAuthentication.NONE);
                }
                if (dstClient.getDataChannelAuthentication().equals(DataChannelAuthentication.NONE)) {
                    srcClient.setDataChannelAuthentication(DataChannelAuthentication.NONE);
                }

                HostPort hp = dstClient.setPassive();
                srcClient.setActive(hp);

                srcClient.setType(Session.TYPE_IMAGE);
                dstClient.setType(Session.TYPE_IMAGE);

                log.debug("Attempting to copy " + srcPath + " to " + dstPath );

                srcClient.transfer(srcPath, dstClient, dstPath, false, null);

                log.debug("Tranfer complete");
                srcConnection.close();
                dstConnection.close();
            } catch (Exception e) {
                log.error("Failed to execute grid ftp transfer", e);
                throw new GridFtpException(e.getMessage(), e);
            }
            log.debug("Completed grid ftp transfer file" + src.getUrl() + " -> " + dst.getUrl());
        }
    }

    public void transferDirectory(FileLocation src, FileLocation dst)
            throws GridFtpException {
        log.debug("Initiating grid ftp transfer directory" + src.getUrl() + " -> " + dst.getUrl());
        // Make directory at destination
        mkdirParent(dstConnection, dst.getFilePath());
        // Get contents of directory
        List fileLocations = list(src, false);
        // Transfer contents of directory
        String dstUrl = dst.getUrlWithoutQuery();
        if (!dstUrl.endsWith("/")) dstUrl += "/";
        Iterator fileIterator = fileLocations.iterator();
        while (fileIterator.hasNext()) {
            FileLocation nextLocation = (FileLocation)fileIterator.next();
            String nextUrl = dstUrl + nextLocation.getFileName();
            FileLocation nextDestination = new FileLocation();
            nextDestination.setUrlString(nextUrl);
            transfer(nextLocation, nextDestination);
        }
        log.debug("Completed grid ftp transfer directory" + src.getUrl() + " -> " + dst.getUrl());
    }

    public void delete(List fileLocationList)
            throws GridFtpException {
        for (Iterator fileLocations = fileLocationList.iterator(); fileLocations.hasNext();) {
            FileLocation fileLocation = (FileLocation) fileLocations.next();
            delete(fileLocation);
        }
    }

    public void delete(FileLocation fileLoc)
            throws GridFtpException {
        log.debug("Initiating grid ftp delete " + fileLoc.getUrl());
        String filePath = fileLoc.getFilePath();
        try {
            if (fileLoc.getIsDirectory() && !fileLoc.getIsSoftLink()) {
                List fileList = list(fileLoc, false);
                if (fileList.size() > 0) {
                    log.debug("Deleting contents of dir " + fileLoc.getUrl());
                    delete(fileList);
                }
                log.debug("Deleting empty dir " + fileLoc.getUrl());
                GridFTPClient client = srcConnection.getGridFTPClient();
                client.deleteDir(filePath);
                srcConnection.close();
            } else {
                GridFTPClient client = srcConnection.getGridFTPClient();
                client.deleteFile(filePath);
                srcConnection.close();
            }
        } catch (Exception e) {
            log.error("Failed to execute grid ftp delete", e);
            throw new GridFtpException(e.getMessage(), e);
        }
        log.debug("Completed grid ftp delete " + fileLoc.getUrl());
    }
}

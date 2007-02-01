/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileExamples.java,v 1.1.1.1 2007-02-01 20:39:31 kherm Exp $
 */
package org.gridlab.gridsphere.examples.services.file;

import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileException;
import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.file.tasks.*;
import org.gridlab.gridsphere.services.task.TaskException;
import org.gridlab.gridsphere.services.task.TaskStatus;

import java.util.List;
import java.util.ArrayList;
import java.net.MalformedURLException;

/**
 * Provides a number of useful examples illustrating how to use Grid Portlets
 * to get manage files on remote resources.
 */
public class FileExamples {

    // Portlet log is very good for debugging, we make ample use of it here!
    private PortletLog log = SportletLog.getInstance(FileExamples.class);
    // The file browser service is used in all the examples below
    private FileBrowserService fileBrowserService  = null;

    /**
     * Constructs an instance of FileExamples.
     * @throws PortletServiceUnavailableException If unable to get required portlet services.
     */
    public FileExamples() throws PortletServiceUnavailableException {
        log.info("Creating JobExamples");
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        try {
            fileBrowserService = (FileBrowserService)
                    factory.createPortletService(FileBrowserService.class, null, true);
        } catch (PortletServiceNotFoundException e) {
            log.error("Unable to initialize required portlet services", e);
            throw new PortletServiceUnavailableException(e);
        }
    }

    /**
     * Returns whether there is a file on the given host at the given path.
     * @param user The user
     * @param host The host
     * @param path The path (can be absolute or relative)
     * @return True if a file object exists on the given host at the given path, false otherwise
     * @throws FileException If there there is no file resource entry for the given host
     */
    public boolean exists(User user, String host, String path) throws FileException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, host);
        return fileBrowser.exists(path);
    }

    /**
     * Returns a file location containg info about the associated
     * file object if there exists a file object on the given host
     * at the given path. If there is no file object at
     * the given path, this method will throw an exception.
     * @param user The user
     * @param host The host
     * @param path The path (can be absolute or relative)
     * @return A file location containing info about the associated file object
     * @throws FileException If there there is no file resource entry for the given host
     */
    public FileLocation info(User user, String host, String path) throws FileException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, host);
        return fileBrowser.info(path);
    }

    /**
     * Returns the home directory of the given user on the given host.
     * @param user The user
     * @param host The host
     * @return A list of <code>FileLocation</code> objects
     */
    public String getHomeDirectory(User user, String host) throws FileException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, host);
        // Simply return the home directory...
        return fileBrowser.getHomeDirectory();
    }

    /**
     * Returns a list of file locations at at the given host and path.
     * @param user The user
     * @param host The host
     * @param path The path (can be absolute or relative)
     * @return A list of <code>FileLocation</code> objects
     * @see FileLocation
     * @throws FileException If there there is no file resource entry for the given host
     */
    public List listFiles(User user, String host, String path) throws FileException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, host);
        return fileBrowser.list(path);
    }

    /**
     * Returns a list of file locations in the home directory of the given user on the given host.
     * @param user The user
     * @param host The host
     * @return A list of <code>FileLocation</code> objects
     * @see FileLocation
     * @throws FileException If there there is no file resource entry for the given host
     */
    public List listFilesInHomeDir(User user, String host) throws FileException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, host);
        // If null or "" is given, then the user's home directory is assumed...
        return fileBrowser.list("");
    }

    /**
     * Creates a directory at the given path on the given host.
     * @param user The user
     * @param host The host
     * @param path The path of the directory (can be absolute or relative)
     * @throws FileException If there there is no file resource entry for the given host
     * @throws TaskException If the make directory task fails
     */
    public void makeDirectory(User user, String host, String path)
            throws FileException, TaskException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, host);
        FileMakeDir fileMakeDir = fileBrowser.makeDirectory(path);
        fileMakeDir.waitFor();
        if (fileMakeDir.getTaskStatus().equals(TaskStatus.FAILED)) {
            throw new TaskException("Failed to create directory with error " + fileMakeDir.getTaskStatusMessage());
        }
    }

    /**
     * Renames the file or directory at the given path on the given host with the given name.
     * @param user The user
     * @param host The host
     * @param path The path of the file to rename (can be absolute or relative)
     * @param newName The new name for the file
     * @throws MalformedURLException If the path results in an invalid url on the host
     * @throws FileException If there there is no file resource entry for the given host
     * @throws TaskException If the rename task fails
     */
    public void rename(User user, String host, String path, String newName)
            throws MalformedURLException, FileException, TaskException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, host);
        // Create a file location for the given path
        FileLocation fileLocation = new FileLocation("file:///" + path);
        FileNameChange fileNameChange = fileBrowser.rename(fileLocation, newName);
        fileNameChange.waitFor();
        if (fileNameChange.getTaskStatus().equals(TaskStatus.FAILED)) {
            throw new TaskException("Failed to rename file with error " + fileNameChange.getTaskStatusMessage());
        }
    }

    /**
     * Copies files at the source host and path to the destination host and path.
     * @param user The user
     * @param srcHost The source host
     * @param srcPath The source path (can be absolute or relative)
     * @param dstHost The destination host
     * @param dstPath The destination path (can be absolute or relative)
     * @throws FileException If there there is no file resource entry for the given host
     * @throws TaskException If the copy task fails
     */
    public void copy(User user, String srcHost, String srcPath, String dstHost, String dstPath)
            throws FileException, TaskException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser srcBrowser = fileBrowserService.createFileBrowser(user, srcHost);
        FileLocation srcLocation = srcBrowser.createFileLocation(srcPath);
        FileBrowser dstBrowser = fileBrowserService.createFileBrowser(user, dstHost);
        FileLocation dstLocation = dstBrowser.createFileLocation(dstPath);
        FileCopy copy = srcBrowser.copy(srcLocation, dstLocation);
        copy.waitFor();
        if (copy.getTaskStatus().equals(TaskStatus.FAILED)) {
            throw new TaskException("Failed to copy file with error " + copy.getTaskStatusMessage());
        }
    }

    /**
     * Moves files at the source host and path to the destination host and path.
     * @param user The user
     * @param srcHost The source host
     * @param srcPaths A list of source paths (as strings, can be absolute or relative)
     * @param dstHost The destination host
     * @param dstPath The destination path (can be absolute or relative)
     * @throws FileException If there there is no file resource entry for the given host
     * @throws TaskException If the move task fails
     */
    public void move(User user, String srcHost, List srcPaths, String dstHost, String dstPath)
            throws FileException, TaskException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser srcBrowser = fileBrowserService.createFileBrowser(user, srcHost);
        List srcLocations = new ArrayList(srcPaths.size());
        for (int ii = 0; ii < srcPaths.size(); ++ii) {
            String srcPath = (String)srcPaths.get(ii);
            FileLocation srcLocation = srcBrowser.createFileLocation(srcPath);
            srcLocations.add(srcLocation);
        }
        FileSet srcFileSet = new FileLocationSet(srcLocations);
        FileBrowser dstBrowser = fileBrowserService.createFileBrowser(user, dstHost);
        FileLocation dstLocation = dstBrowser.createFileLocation(dstPath);
        FileMove move = srcBrowser.move(srcFileSet, dstLocation);
        move.waitFor();
        if (move.getTaskStatus().equals(TaskStatus.FAILED)) {
            throw new TaskException("Failed to move files with error " + move.getTaskStatusMessage());
        }
    }

    /**
     * Deletes the file or directory at the given path on the given host.
     * @param user The user
     * @param host The host
     * @param path The path of the directory (can be absolute or relative)
     * @throws FileException If there there is no file resource entry for the given host
     * @throws TaskException If the delete task fails
     */
    public void delete(User user, String host, String path)
            throws FileException, TaskException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, host);
        FileLocation fileLocation = fileBrowser.createFileLocation(path);
        FileDeletion delete = fileBrowser.delete(fileLocation);
        delete.waitFor();
        if (delete.getTaskStatus().equals(TaskStatus.FAILED)) {
            throw new TaskException("Failed to delete file with error " + delete.getTaskStatusMessage());
        }
    }

    /**
     * Uploads the file at the given source path to the given destination host and path
     * @param user The user
     * @param srcPath The source path (can be absolute or relative)
     * @param dstHost The destination host
     * @param dstPath The destination path (can be absolute or relative)
     * @throws FileException If there there is no file resource entry for the given host
     * @throws TaskException If the upload task fails
     */
    public void upload(User user, String srcPath, String dstHost, String dstPath)
            throws FileException, TaskException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, dstHost);
        FileLocation dstLocation = fileBrowser.createFileLocation(dstPath);
        FileUpload upload = fileBrowser.upload(srcPath, dstLocation);
        upload.waitFor();
        if (upload.getTaskStatus().equals(TaskStatus.FAILED)) {
            throw new TaskException("Failed to upload file with error " + upload.getTaskStatusMessage());
        }
    }

    /**
     * Downloads the file from the given source host and path to the given local path
     * @param user The user
     * @param srcHost The destination host
     * @param srcPath The source path (can be absolute or relative)
     * @param dstPath The destination path (can be absolute or relative)
     * @throws FileException If there there is no file resource entry for the given host
     * @throws TaskException If the download task fails
     */
    public void download(User user, String srcHost, String srcPath, String dstPath)
            throws FileException, TaskException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser fileBrowser = fileBrowserService.createFileBrowser(user, srcHost);
        FileLocation srcLocation = fileBrowser.createFileLocation(srcPath);
        FileDownload download = fileBrowser.download(srcLocation, dstPath);
        download.waitFor();
        if (download.getTaskStatus().equals(TaskStatus.FAILED)) {
            throw new TaskException("Failed to download file with error " + download.getTaskStatusMessage());
        }
    }

    /**
     * Copies from a remote resource to GridSphere's "secure directory". Note that the actual root
     * path of GridSphere's secure directory lies somewhere in ${WEBSERVER_HOME}/gridsphere/WEB-INF!
     * @param user The user
     * @param srcHost The source host
     * @param srcPath The source path (can be absolute or relative)
     * @param dstPath The destination path in the secure directory (can be absolute or relative)
     * @throws FileException If there there is no file resource entry for the given host
     * @throws TaskException If the copy task fails
     */
    public void copyToSecureDirectory(User user, String srcHost, String srcPath, String dstPath)
            throws FileException, TaskException {
        // Create a file browser on the given host.
        // This will fail if there is no file resource entry for the given host.
        FileBrowser srcBrowser = fileBrowserService.createFileBrowser(user, srcHost);
        FileLocation srcLocation = srcBrowser.createFileLocation(srcPath);
        // Create a file browser on the local host.
        // The local host is represented by the <localhost-resource> entry in Resources.xml
        // This will fail if there is no file resource entry for the given host.
        FileBrowser dstBrowser = fileBrowserService.createFileBrowser(user, "localhost");
        FileLocation dstLocation = dstBrowser.createFileLocation(dstPath);
        FileCopy copy = srcBrowser.copy(srcLocation, dstLocation);
        copy.waitFor();
        if (copy.getTaskStatus().equals(TaskStatus.FAILED)) {
            throw new TaskException("Failed to copy file with error " + copy.getTaskStatusMessage());
        }
    }

}

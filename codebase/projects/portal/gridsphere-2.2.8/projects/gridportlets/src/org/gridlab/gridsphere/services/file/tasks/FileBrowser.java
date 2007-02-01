/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileBrowser.java,v 1.1.1.1 2007-02-01 20:40:29 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.file.*;

import java.util.List;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Describes an interface for "browsing" file systems on hardware resoruces
 * that are made available by file resources. File browers maintain state
 * about their current location and use a file browser sevice to perform
 * their file browsing tasks.
 * @see FileResource For more information about file resources
 * @see FileBrowserService For more information file browsing services
 */
public interface FileBrowser {

    /**
     * Returns the user who is browsing.
     * @return The user
     */
    public User getUser();

    /**
     * Returns the current resource being browsed.
     * @return The current resource
     */
    public FileResource getFileResource();

    /**
     * Returns the hostname of the current file resource being browed.
     * @return The current hostname
     */
    public String getFileHostName();

    /**
     * Returns the current file location being browsed.
     * @return The current file location
     */
    public FileLocation getCurrentLocation();

    /**
     * Returns the current file directory being browsed.
     * @return The current file directory
     */
    public String getCurrentDirectory();

    /**
     * Returns the home directory on the current resource for
     * the current user of this file browser.
     * @return The home directory
     * @throws FileException
     */
    public String getHomeDirectory() throws FileException;

    /**
     * Creates a file location that contains the given path
     * appended to the location of the current resource.
     * Throws a file exception if the given path results in
     * a malformed url.
     * @param path The path on the current resource
     * @return A file location
     * @throws FileException
     */
    public FileLocation createFileLocation(String path)
            throws FileException;


    public InputStream createInputStream(FileLocation loc)
            throws IOException, FileException;

    public InputStream createInputStream(String path)
            throws IOException, FileException;

    public OutputStream createOutputStream(FileLocation loc)
            throws IOException, FileException;

    public OutputStream createOutputStream(String path)
            throws IOException, FileException;

    /**
     * Returns a file location containg info about the associated
     * file object if there exists a file object on the current
     * resource at the given path. If there is no file object at
     * the given path, this method will throw an exception.
     * @param path The path to the file object in question
     * @return The file location
     * @throws FileException
     */
    public FileLocation info(String path)
            throws FileException;

    /**
     * Returns true if there exists a file object at the given path
     * on the current resource, false otherwise. This method can be
     * used, for example, to test if a file or directory exists at
     * the given path, and to take some action based on the results.
     * @param path The path on the current resource
     * @return True if the path results in a valid file location, false otherwise.
     */
    public boolean exists(String path);

    /**
     * Returns a list of file locations at the current location.
     * @return The list of file locations
     * @throws FileException
     */
    public List list()
           throws FileException;

    /**
     * Returns the list of file locations at given path.
     * @param path The path
     * @return The list of file locations
     * @see FileLocation
     * @throws FileException
     */
    public List list(String path)
           throws FileException;

    /**
     * Returns the list of file locations at the given file location.
     * @param loc The file location
     * @return The list of file locations
     * @see FileLocation
     * @throws FileException
     */
    public List list(FileLocation loc)
           throws FileException;

    /**
     * Returns the list of file handles in the given file set.
     * @param set The file set
     * @return The list of file handles
     * @see FileLocation
     * @throws FileException
     */
    public List list(FileSet set)
           throws FileException;

    /**
     * Change the current location to the given file location
     * @param loc The file location to change to.
     * @throws FileException
     */
    public void changeLocation(FileLocation loc)
            throws FileException;

    /**
     * Change the current location to the given directory
     * on the current resource.
     * @param dir The directory
     * @throws FileException
     */
    public void changeDirectory(String dir)
            throws FileException;

    /**
     * Go up one directory on the current resource.
     * @throws FileException
     */
    public void goUpDirectory()
            throws FileException;

    /**
     * Makes a directory in the currrent dir with the given name.
     * @param dirName The name of the directory
     * @return The make dir task
     * @throws FileTaskException
     */
    public FileMakeDir makeDirectory(String dirName)
            throws FileTaskException;

    /**
     * Submits a task to make a new directory at the given file location.
     * Callers should monitor the returned task to see if it fails
     * or succeeds.
     * @param loc The location at which to make the new directory
     * @return The make directory task
     * @throws FileTaskException
     */
    public FileMakeDir makeDirectory(FileLocation loc)
            throws FileTaskException;


    /**
     * Submits a task to make a new directory in the given parent location
     * with the given directory name. Callers should monitor the
     * returned task to see if it fails or succeeds.
     * @param parentLoc The parent location
     * @param dirName The directory name
     * @return The make directory task
     * @throws FileTaskException
     */
    public FileMakeDir makeDirectory(FileLocation parentLoc, String dirName)
            throws FileTaskException;

    /**
     * Submits a task to rename the file at the given location to the given name.
     * Callers should monitor the returned task to see if it fails or succeeds.
     * @param loc The file lcoation
     * @param newName The new file name
     * @return The file name change task
     * @throws FileTaskException
     */
    public FileNameChange rename(FileLocation loc, String newName)
            throws FileTaskException;

    /**
     * Submits a task to copy the file at the given source location to the destination location.
     * Callers should monitor the returned task to see if it fails or succeeds.
     * @param src The location of the file to copy
     * @param dst The destination location
     * @return The file copy task
     * @throws FileTaskException
     */
    public FileCopy copy(FileLocation src, FileLocation dst)
            throws FileTaskException;

    /**
     * Submits a task to copy the given file set to the destination location.
     * Callers should monitor the returned task to see if it fails or succeeds.
     * @param src The file set to copy
     * @param dst The destination location
     * @return The file copy task
     * @throws FileTaskException
     */
    public FileCopy copy(FileSet src, FileLocation dst)
            throws FileTaskException;

    /**
     * Submits a task to move the file at the given source location to the destination location.
     * Callers should monitor the returned task to see if it fails or succeeds.
     * @param src The location of the file to copy
     * @param dst The destination location
     * @return The file copy task
     * @throws FileTaskException
     */
    public FileMove move(FileLocation src, FileLocation dst)
            throws FileTaskException;

    /**
     * Submits a task to move the given file set to the destination location.
     * Callers should monitor the returned task to see if it fails or succeeds.
     * @param src The file set to move
     * @param dst The destination location
     * @return The file move task
     * @throws FileTaskException
     */
    public FileMove move(FileSet src, FileLocation dst)
            throws FileTaskException;

    /**
     * Submits a task to delete the file at the given location. Callers should monitor
     * the returned task to see if it fails or succeeds.
     * @param fileLoc The file to delete
     * @return The file deletion task
     * @throws FileTaskException
     */
    public FileDeletion delete(FileLocation fileLoc)
            throws FileTaskException;

    /**
     * Submits a task to delete the given file set. Callers should monitor
     * the returned task to see if it fails or succeeds.
     * @param fileSet The file set to delete
     * @return The file deletion task
     * @throws FileTaskException
     */
    public FileDeletion delete(FileSet fileSet)
            throws FileTaskException;

    /**
     * Submits a task to upload the file at the given local path to the given
     * desintation. Callers should monitor the returned task to see if it fails
     * or succeeds.
     * @param localFilePath The file to upload
     * @param destination The destination
     * @return The file upload task
     * @throws FileTaskException
     */
    public FileUpload upload(String localFilePath, FileLocation destination)
            throws FileTaskException;

    /**
     * Submits a task to download the file at the given local path to the given
     * desintation. Callers should monitor the returned task to see if it fails
     * or succeeds.
     * @param source The file to download
     * @param localFilePath The destination
     * @return The file upload task
     * @throws FileTaskException
     */
    public FileDownload download(FileLocation source, String localFilePath)
            throws FileTaskException;
}

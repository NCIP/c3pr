/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileBrowserService.java,v 1.1.1.1 2007-02-01 20:40:29 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.portlet.User;

import java.util.List;

/**
 * Describes a service for performing file browsing tasks. Callers
 * are recommended to use a file browsing service to create a file
 * browser, rather than submitting file browsing tasks directly
 * with this service. File browsers provide more convenient methods
 * for performing file browsing tasks and maintain state about their
 * current lcoation.
 * @see FileBrowser For more information about file browsers
 */
public interface FileBrowserService extends FileTaskService {

    /**
     * Returns the list of file resources "supported" by this service.
     * Support for file resources is determined by the underlying
     * implementation but is generally related to file access
     * protocols supported by the underlying implementation.
     * @return The list of file resources
     * @see FileResource For more information about file resources
     */
    public List getFileResources();

    /**
     * Returns the list of file resources supported by this service
     * to which the given user has access. The user's access
     * privileges are determined by the underlying implementation
     * but is generally related to whether the user has
     * credential resource mappings for the file resources
     * supported by this service.
     * @param user The user
     * @return The list of file resources to which the user has access
     * @see FileResource For more information about file resources
     */
    public List getFileResources(User user);

    /**
     * Returns the file resource supported by this service
     * on the given hostname. Returns null if no file
     * resource is supported.
     * @param hostname The host in question.
     * @return The file resource
     */
    public FileResource getFileResource(String hostname);

    /**
     * Returns the file resource supported by this service
     * on the given file location. Returns null if no file
     * resource is supported.
     * @param fileLocation The file location in question.
     * @return The file resource
     */
    public FileResource getFileResource(FileLocation fileLocation);

    /**
     * Returns whether this file browser service supports the given file location.
     * @param fileLocation The file location in question.
     * @return The file resource
     */
    public boolean supportsFileLocation(FileLocation fileLocation);

    /**
     * Returns a file browser for the given user on the given host.
     * @param user  The user
     * @param hostname The host
     * @return The file browser
     * @throws FileException
     */
    public FileBrowser createFileBrowser(User user, String hostname)
            throws FileException;

    /**
     * Returns a file browser for the given user at the given location.
     * @param user The user
     * @param location The location
     * @return The file browser
     * @throws FileException
     */
    public FileBrowser createFileBrowser(User user, FileLocation location)
            throws FileException;

    /**
     * Creates a file listing spec.
     * @return The file listing spec
     * @throws FileTaskException
     */
    public FileListingSpec createFileListingSpec()
            throws FileTaskException;

    /**
     * Submits a file listing task described by the given file listing spec.
     * @param spec The file listing spec
     * @return The file listing task
     * @throws FileTaskException
     */
    public FileListing submitFileListing(FileListingSpec spec)
            throws FileTaskException;

    /**
     * Creates a file name change spec.
     * @return The file name change spec
     * @throws FileTaskException
     */
    public FileNameChangeSpec createFileNameChangeSpec()
            throws FileTaskException;

    /**
     * Submits a file name change task described by the given file name change spec.
     * @param spec The file name change spec
     * @return The file name change task
     * @throws FileTaskException
     */
    public FileNameChange submitFileNameChange(FileNameChangeSpec spec)
            throws FileTaskException;

    /**
     * Creates a file make dir spec.
     * @return The make dir spec
     * @throws FileTaskException
     */
    public FileMakeDirSpec createFileMakeDirSpec()
            throws FileTaskException;

    /**
     * Submits a make dir task described by the given make dir spec.
     * @param spec The make dir spec
     * @return The make dir task
     * @throws FileTaskException
     */
    public FileMakeDir submitFileMakeDir(FileMakeDirSpec spec)
            throws FileTaskException;

    /**
     * Creates a file copy spec.
     * @return The file copy spec
     * @throws FileTaskException
     */
    public FileCopySpec createFileCopySpec()
            throws FileTaskException;

    /**
     * Submits a file copy task described by the given file copy spec.
     * @param spec The file copy spec
     * @return The file copy task
     * @throws FileTaskException
     */
    public FileCopy submitFileCopy(FileCopySpec spec)
            throws FileTaskException;

    /**
     * Creates a file move spec.
     * @return The file move spec
     * @throws FileTaskException
     */
    public FileMoveSpec createFileMoveSpec()
            throws FileTaskException;

    /**
     * Submits a file move task described by the given file move spec.
     * @param spec The file move spec
     * @return The file move task
     * @throws FileTaskException
     */
    public FileMove submitFileMove(FileMoveSpec spec)
            throws FileTaskException;

    /**
     * Creates a file deletion spec.
     * @return The file deletion spec
     * @throws FileTaskException
     */
    public FileDeletionSpec createFileDeletionSpec()
            throws FileTaskException;

    /**
     * Submits a file deletion task described by the given file deletion spec.
     * @param spec The file deletion spec
     * @return The file deletion task
     * @throws FileTaskException
     */
    public FileDeletion submitFileDeletion(FileDeletionSpec spec)
            throws FileTaskException;

    /**
     * Creates a file upload spec.
     * @return The file upload spec
     * @throws FileTaskException
     */
    public FileUploadSpec createFileUploadSpec()
            throws FileTaskException;

    /**
     * Submits a file upload task described by the given file upload spec.
     * @param spec The file upload spec
     * @return The file upload task
     * @throws FileTaskException
     */
    public FileUpload submitFileUpload(FileUploadSpec spec)
            throws FileTaskException;

    /**
     * Creates a file download spec.
     * @return The file download spec
     * @throws FileTaskException
     */
    public FileDownloadSpec createFileDownloadSpec()
            throws FileTaskException;

    /**
     * Submits a file download task described by the given file download spec.
     * @param spec The file download spec
     * @return The file download task
     * @throws FileTaskException
     */
    public FileDownload submitFileDownload(FileDownloadSpec spec)
            throws FileTaskException;
}

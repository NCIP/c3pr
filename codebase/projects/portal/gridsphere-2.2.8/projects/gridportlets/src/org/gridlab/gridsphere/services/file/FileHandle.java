/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileHandle.java,v 1.1.1.1 2007-02-01 20:39:58 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.services.file.io.FileWriterUtil;
import org.gridlab.gridsphere.services.file.io.FileReaderUtil;
import org.gridlab.gridsphere.services.security.gss.CredentialUtil;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.globus.io.streams.GassInputStream;
import org.globus.io.streams.GassOutputStream;
import org.globus.gsi.gssapi.auth.NoAuthorization;
import org.ietf.jgss.GSSCredential;

import java.io.*;
import java.net.MalformedURLException;

/**
 * Represents a handle to a file. Provides methods for creating input
 * and output streams, as well as easy-to-use methods for writing and
 * reading strings from files.
 */
public class FileHandle {

    private transient static PortletLog log = SportletLog.getInstance(FileHandle.class);

    public final static int BUFFER_SIZE = 8192; //8 kB
    private FileLocation fileLocation = null;

    /**
     * Creates a new file handle with a file url that conforms to
     * the conventions of <class>FileLocation</class>.
     * @param fileUrl The url to the file
     * @throws MalformedURLException If the file url is invalid
     * @see FileLocation For more information about file urls
     */
    public FileHandle(String fileUrl) throws MalformedURLException {
       fileLocation = new FileLocation(fileUrl);
    }

    /**
     * Creates a file handle to the given file location.
     * @param fileLocation The file location
     */
    public FileHandle(FileLocation fileLocation) {
        this.fileLocation = fileLocation;
    }

    /**
     * Creates an input stream to the file represented by this handle.
     * A user object is required in order to obtain the proper security
     * context for accessing the file (i.e. <class>GSSCredenial</class>).
     * @param user The user who is accessing the file
     * @return The input stream
     * @throws IOException If an IO error occurs accessing the file
     * @throws FileException For all other errors that occur accessing the file
     */
    public InputStream createInputStream(User user) throws IOException, FileException {
        // TODO: Need proper implementation of GASS resource / browser
        if (fileLocation.getProtocol().equals("https")) {
            if (fileLocation.isLocalHost()) {
                File file = new File(fileLocation.getFilePath());
                FileInputStream inputStream = new FileInputStream(file);
                return inputStream;
            } else {
                String host = fileLocation.getHost();
                int port = fileLocation.getPort();
                String path = fileLocation.getFilePath();
                try {
                    GSSCredential credential = CredentialUtil.getDefaultCredential(user);
                    if (credential == null) {
                        throw new FileException("Active credentials required!");
                    }
                    return new GassInputStream(credential, NoAuthorization.getInstance(), host, port, path);
                } catch (Exception e) {
                    log.error("Unable to create gass input stream for " + fileLocation.getUrl(), e);
                    throw new IOException(e.getMessage());
                }
            }
        }
        FileBrowser fileBrowser = getFileBrowser(user, fileLocation);
        return fileBrowser.createInputStream(fileLocation);
    }

    /**
     * Creates an output stream to the file represented by this handle.
     * A user object is required in order to obtain the proper security
     * context for accessing the file (i.e. <class>GSSCredenial</class>).
     * @param user The user who is accessing the file
     * @return The output stream
     * @throws IOException If an IO error occurs accessing the file
     * @throws FileException For all other errors that occur accessing the file
     */
    public OutputStream createOutputStream(User user) throws IOException, FileException {
        // TODO: Need proper implementation of GASS resource / browser
        if (fileLocation.getProtocol().equals("https")) {
            if (fileLocation.isLocalHost()) {
                File file = new File(fileLocation.getFilePath());
                FileOutputStream outputStream = new FileOutputStream(file);
                return outputStream;
            } else {
                String host = fileLocation.getHost();
                int port = fileLocation.getPort();
                String path = fileLocation.getFilePath();
                GSSCredential credential = CredentialUtil.getDefaultCredential(user);
                if (credential == null) {
                    throw new FileException("Active credentials required!");
                }
                try {
                    return new GassOutputStream(credential, NoAuthorization.getInstance(), host, port, path, -1, false);
                } catch (Exception e) {
                    log.error("Unable to create gass input stream for " + fileLocation.getUrl(), e);
                    throw new IOException(e.getMessage());
                }
            }
        }
        FileBrowser fileBrowser = getFileBrowser(user, fileLocation);
        return fileBrowser.createOutputStream(fileLocation);
    }

    /**
     * Creates a file browser for accessing the file at the given location for the given user.
     * @param user The user accessing the file
     * @param fileLocation The location of the file
     * @return The file browser
     * @throws FileException If an error occurs creating a file browser
     */
    private FileBrowser getFileBrowser(User user, FileLocation fileLocation) throws FileException {
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        try {
            FileBrowserService fileBrowserService = (FileBrowserService)
                    factory.createPortletService(FileBrowserService.class, null, true);
            return fileBrowserService.createFileBrowser(user, fileLocation);
        } catch (PortletServiceException e) {
            log.error("Unable to create file browser for " + fileLocation.getUrl(), e);
            throw new FileException("Unable to create file browser for " + fileLocation.getUrl());
        }
    }

    /**
     * Reads the contents of the file represented by this handle into a string.
     * A user object is required in order to obtain the proper security
     * context for accessing the file (i.e. <class>GSSCredenial</class>).
     * @param user The user accessing the file
     * @return The string contents of the file
     * @throws IOException If an IO error occurs accessing the file
     * @throws FileException For all other errors that occur accessing the file
     */
    public String readContents(User user) throws IOException, FileException {
        InputStream inputStream = createInputStream(user);
        String contents = FileReaderUtil.toString(inputStream);
        inputStream.close();
        return contents;
    }

    /**
     * Writes the contents of the string to the file represented by this handle.
     * A user object is required in order to obtain the proper security
     * context for accessing the file (i.e. <class>GSSCredenial</class>).
     * @param user The user accessing the file
     * @param text The string to write to the file
     * @throws IOException If an IO error occurs accessing the file
     * @throws FileException For all other errors that occur accessing the file
     */
    public void writeContents(User user, String text) throws IOException, FileException {
        OutputStream outputStream = createOutputStream(user);
        FileWriterUtil.write(text, outputStream);
        outputStream.close();
    }

    /**
     * Writes the contents of the file represented by this handle to a local file.
     * A user object is required in order to obtain the proper security
     * context for accessing the file (i.e. <class>GSSCredenial</class>).
     * @param user The user accessing the file
     * @param file The local file to write the contents of the remote file
     * @throws IOException If an IO error occurs accessing the remote or local file
     * @throws FileException For all other errors that occur accessing the remote file
     */
    public void writeToFile(User user, File file) throws IOException, FileException {
        InputStream inputStream = createInputStream(user);
        FileWriterUtil.toFile(inputStream, file);
        inputStream.close();
    }
}

package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.file.sets.FilePatternSetType;
import org.gridlab.gridsphere.services.file.sets.FilePatternSet;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowser;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSCredential;
import org.globus.io.streams.GridFTPInputStream;
import org.globus.io.streams.GridFTPOutputStream;
import org.globus.ftp.exception.FTPException;

import java.util.List;
import java.util.Iterator;
import java.util.Vector;
import java.io.*;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpBrowser.java,v 1.1.1.1 2007-02-01 20:41:09 kherm Exp $
 * <p>
 * Implementation of grid ftp file browser.
 */

public class GridFtpBrowser extends BaseFileBrowser {

    private static PortletLog log = SportletLog.getInstance(GridFtpBrowser.class);
    protected GridFtpConnection connection = null;

    public GridFtpBrowser() {
    }

    public InputStream createInputStream(FileLocation loc)
            throws IOException, FileException {
        GridFtpConnection connection = getConnection();
        GSSCredential credential = connection.getCredential();
        String host = connection.getHostName();
        String port = connection.getPort();
        String path = loc.getFilePath();
        try {
            InputStream is = new GridFTPInputStream(credential, host, Integer.parseInt(port), path);
            connection.release();
            return is;
        } catch (FTPException e) {
            log.error("Error creating grid ftp input stream", e);
            connection.release();
            throw new FileException(e.getMessage());
        }
    }

    public OutputStream createOutputStream(FileLocation loc)
            throws IOException, FileException {
        GridFtpConnection connection = getConnection();
        GSSCredential credential = connection.getCredential();
        String host = connection.getHostName();
        String port = connection.getPort();
        String path = loc.getFilePath();
        try {
            OutputStream os = new GridFTPOutputStream(credential, host, Integer.parseInt(port), path, false);
            connection.release();
            return os;
        } catch (FTPException e) {
            log.error("Error creating grid ftp output stream", e);
            connection.release();
            throw new FileException(e.getMessage());
        }
    }

    public void destroy() {
        if (connection != null) {
            connection.release();
            connection = null;
        }
    }

    protected GridFtpConnection getConnection()
            throws FileException {
        if (connection == null) {
            log.debug("Creating grid ftp connection");
            try {
                connection = (GridFtpConnection)((GridFtpResource)getFileResource()).createGssConnection(user);
            } catch (GSSException e) {
                throw new FileException(e.getMessage());
            } catch (ResourceException e) {
                throw new FileException(e.getMessage());
            }
        }
        return connection;
    }

    public String doGetHomeDir()
            throws FileException {
        GridFtpConnection connection = getConnection();
        String homeDir = null;
        try {
            connection.changeDir("~");
            homeDir = connection.getCurrentDir();
            String dir = location.getFilePath();
            if (dir.equals("")) {
                location.setFilePath(homeDir);
            } else {
                connection.changeDir(dir);
            }
        } catch (GridFtpException e) {
            throw new FileException(e.getMessage());
        }
        return homeDir;
    }

    protected FileLocation doChangeDir(String dir)
            throws FileException {
        // Now if dir is null, we assume this means change to
        // user's home directory on current resource,
        // otherwise change to given dir...
        if (dir == null) {
            if (homeDir == null) {
                GridFtpConnection connection = getConnection();
                try {
                    connection.changeDir("~");
                    homeDir = connection.getCurrentDir();
                } catch (GridFtpException e) {
                    throw new FileException(e.getMessage());
                }
            }
            dir = homeDir;
        } else if (homeDir == null) {
            try {
                GridFtpConnection connection = getConnection();
                connection.changeDir(dir);
                dir = connection.getCurrentDir();
            } catch (GridFtpException e) {
                throw new FileException(e.getMessage());
            }
        } else if ( !dir.equals(homeDir) ) {
            try {
                GridFtpConnection connection = getConnection();
                connection.changeDir(dir);
                dir = connection.getCurrentDir();
            } catch (GridFtpException e) {
                throw new FileException(e.getMessage());
            }
        }
        String url = createUrl(dir);
        FileLocation location = new FileLocation();
        location.setUrlString(url);
        location.setFileType(FileType.DIRECTORY);
        return location;
    }

    protected FileLocation doGoUpDir()
            throws FileException {
        GridFtpConnection connection = getConnection();
        String dir = this.location.getFilePath();
        if (dir.equals("")) {
            dir = null;
        }
        try {
            connection.changeDir(dir);
            connection.goUpDir();
            dir = connection.getCurrentDir();
        } catch (GridFtpException e) {
            throw new FileException(e.getMessage());
        }
        String url = createUrl(dir);
        FileLocation location = new FileLocation();
        location.setUrlString(url);
        location.setFileType(FileType.DIRECTORY);
        return location;
    }

    public FileLocation info(String path)
            throws FileException {
        GridFtpThread thread = new GridFtpThread(getConnection());
        FileLocation location = createFileLocation(path);
        try {
            return thread.info(location);
        } catch (GridFtpException e) {
            throw new FileException(e.getMessage());
        }
    }

    public boolean exists(String path) {
        try {
            GridFtpThread thread = new GridFtpThread(getConnection());
            return thread.exists(path);
        } catch (Exception e) {
            return false;
        }
    }

    public List list(String filePath)
            throws FileException {
        GridFtpListThread gridFtpThread = new GridFtpListThread(getConnection());
        FileLocation fileLoc = createFileLocation(filePath);
        try {
            return gridFtpThread.list(fileLoc, true);
        } catch (GridFtpException e) {
            throw new FileException(e.getMessage());
        }
    }

    public List list(FileLocation fileLoc)
            throws FileException {
        GridFtpListThread gridFtpThread = new GridFtpListThread(getConnection());
        try {
            return gridFtpThread.list(fileLoc, true);
        } catch (GridFtpException e) {
            throw new FileException(e.getMessage());
        }
    }

    public List list(FileSet fileSet)
            throws FileException {
        GridFtpListThread gridFtpThread = new GridFtpListThread(getConnection());
        if (fileSet.getFileSetType().equals(FilePatternSetType.INSTANCE)) {
            FilePatternSet filePatternSet = (FilePatternSet)fileSet;
            FileLocation fileLoc = filePatternSet.getFileLocation();
            if (fileLoc == null) {
                throw new FileException("File location must be specified in reg expression file set");
            }
            try {
                return gridFtpThread.list(fileLoc, true);
            } catch (GridFtpException e) {
                throw new FileException(e.getMessage());
            }
        } else {
            List fileLocList = new Vector();
            FileLocationSet fileLocSet = (FileLocationSet)fileSet;
            Iterator fileLocIter = fileLocSet.getFileLocations().iterator();
            while (fileLocIter.hasNext()) {
                FileLocation fileLoc = (FileLocation)fileLocIter.next();
                try {
                    fileLocList.addAll(gridFtpThread.list(fileLoc, false));
                } catch (GridFtpException e) {
                    throw new FileException(e.getMessage());
                }
            }
            return fileLocList;
        }
    }
}

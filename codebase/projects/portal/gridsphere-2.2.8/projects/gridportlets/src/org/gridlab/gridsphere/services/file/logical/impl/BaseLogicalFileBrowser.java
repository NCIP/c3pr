/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.logical.impl;

import org.gridlab.gridsphere.services.file.logical.*;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowser;
import org.gridlab.gridsphere.services.file.FileTaskException;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/**
 * Base logical file browser implmentation
 */
public abstract class BaseLogicalFileBrowser
        extends BaseFileBrowser
        implements LogicalFileBrowser {

    private static PortletLog log = SportletLog.getInstance(BaseLogicalFileBrowser.class);

    public BaseLogicalFileBrowser() {
    }

    protected String createUrl(String dir) {
        StringBuffer urlBuffer = new StringBuffer("lfn://");
        String hostName = getFileResource().getHostName();
        urlBuffer.append(hostName);
        urlBuffer.append('/');
        urlBuffer.append(dir);
        return urlBuffer.toString();
    }


    public MakeLogicalFileTask makeLogicalFile(String filePath)
            throws FileTaskException {
        MakeLogicalFileTaskSpec makeLogicalFileTaskSpec
                = ((LogicalFileBrowserService)fileBrowserService).createMakeLogicalFileTaskSpec();
        makeLogicalFileTaskSpec.setUser(user);
        makeLogicalFileTaskSpec.setFileResource(getFileResource());
        //LogicalFileLocation fileLoc = null;
        FileLocation fileLoc = null;
        try {
            //fileLoc = createLogicalFileLocation(filePath);
            fileLoc = createFileLocation(filePath);
        } catch (FileException e) {
            throw new FileTaskException(e.getMessage());
        }
        makeLogicalFileTaskSpec.setFileLocation(fileLoc);
        return ((LogicalFileBrowserService)fileBrowserService).submitMakeLogicalFileTask(makeLogicalFileTaskSpec);
    }

    public MakeLogicalFileTask makeLogicalFile(FileLocation parentLoc, String fileName)
            throws FileTaskException {
        MakeLogicalFileTaskSpec makeLogicalFileTaskSpec
                = ((LogicalFileBrowserService)fileBrowserService).createMakeLogicalFileTaskSpec();
        makeLogicalFileTaskSpec.setUser(user);
        makeLogicalFileTaskSpec.setFileResource(getFileResource());
        //LogicalFileLocation fileLoc = null;
        FileLocation fileLoc = null;
        String filePath = parentLoc.getFilePath();
        if (filePath.equals("")) {
            filePath = fileName;
        } else if (filePath.endsWith("/")) {
            filePath += fileName;
        } else {
            filePath += '/' + fileName;
        }
        try {
            //fileLoc = createLogicalFileLocation(filePath);
            fileLoc = createFileLocation(filePath);
        } catch (FileException e) {
            throw new FileTaskException(e.getMessage());
        }
        makeLogicalFileTaskSpec.setFileLocation(fileLoc);
        return ((LogicalFileBrowserService)fileBrowserService).submitMakeLogicalFileTask(makeLogicalFileTaskSpec);
    }

    public ReplicateFileTask replicateFile(FileLocation logicalLoc, FileLocation replicaLoc)
            throws FileTaskException {
        ReplicateFileTaskSpec replicateFileTaskSpec
                = ((LogicalFileBrowserService)fileBrowserService).createReplicateFileTaskSpec();
        replicateFileTaskSpec.setUser(user);
        replicateFileTaskSpec.setFileResource(getFileResource());
        replicateFileTaskSpec.setLogicalLocation(logicalLoc);
        replicateFileTaskSpec.setReplicaLocation(replicaLoc);
        return ((LogicalFileBrowserService)fileBrowserService).submitReplicateFileTask(replicateFileTaskSpec);
    }
}

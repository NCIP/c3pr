/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: LogicalFileBrowser.java,v 1.1.1.1 2007-02-01 20:40:18 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.logical;

import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.FileTaskException;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileException;

/**
 * Describes a browser for logical file resources.
 */
public interface LogicalFileBrowser extends FileBrowser {

    /**
     * Submits a task to make a logical file at the given file path.
     * Callers should monitor the returned task to see if it fails
     * or succeeds.
     * @param filePath The file path
     * @return The make logical file task
     * @throws FileTaskException
     */
    public MakeLogicalFileTask makeLogicalFile(String filePath)
            throws FileTaskException;

    /**
     * Submits a task to make a logical file in the given parent location
     * with the given name. Callers should monitor the returned task to see
     * if it fails or succeeds.
     * @param parentLoc The parent location
     * @param fileName The file name
     * @return The make logical file task
     * @throws FileTaskException
     */
    public MakeLogicalFileTask makeLogicalFile(FileLocation parentLoc, String fileName)
            throws FileTaskException;

    /**
     * Submits a task to replicate a logical file to the given replica (physical)
     * location. Callers should monitor the returned task to see if it fails
     * or succeeds.
     * @param logicalLoc The logical location
     * @param replicaLoc The replica location
     * @return The make logical file task
     * @throws FileTaskException
     */
    public ReplicateFileTask replicateFile(FileLocation logicalLoc, FileLocation replicaLoc)
            throws FileTaskException;
}

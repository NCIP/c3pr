/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: LogicalFileBrowserService.java,v 1.1.1.1 2007-02-01 20:40:18 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.logical;

import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.services.file.FileException;
import org.gridlab.gridsphere.services.file.FileTaskException;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.portlet.User;

import java.net.MalformedURLException;

/**
 * Describes a service for browing logical file resources. The logical
 * file browser service extends the file browser service to support
 * logical file browsing tasks.
 */
public interface LogicalFileBrowserService extends FileBrowserService {


    /**
     * Returns the logical file resource this service is configured to use.
     * @return The logical file resource
     */
    public LogicalFileResource getLogicalFileResource()
            throws FileException;

    /**
     * Returns a file browser for the given user.
     * @param user The user
     * @return The logical file browser
     */
    public LogicalFileBrowser createLogicalFileBrowser(User user)
            throws FileException;

    /**
     * Returns a file browser at the given user at the given location.
     * @param user The user
     * @param location The location
     * @return The logical file browser
     */
    public LogicalFileBrowser createLogicalFileBrowser(User user, FileLocation location)
            throws FileException;

    /**
     * Creates a make logical file spec.
     * @return The make logical file spec
     * @throws FileTaskException
     */
    public MakeLogicalFileTaskSpec createMakeLogicalFileTaskSpec()
            throws FileTaskException;

    /**
     * Submits a make logical file task described by the given make logical file spec.
     * @param spec The make logical file spec
     * @return The make logical file task
     * @throws FileTaskException
     */
    public MakeLogicalFileTask submitMakeLogicalFileTask(MakeLogicalFileTaskSpec spec)
            throws FileTaskException;

    /**
     * Creates a replicate file spec.
     * @return The replicate file file spec
     * @throws FileTaskException
     */
    public ReplicateFileTaskSpec createReplicateFileTaskSpec()
            throws FileTaskException;

    /**
     * Submits a replicate file task described by the given replicate file spec.
     * @param spec The replicate file spec
     * @return The replicate file task
     * @throws FileTaskException
     */
    public ReplicateFileTask submitReplicateFileTask(ReplicateFileTaskSpec spec)
            throws FileTaskException;
}

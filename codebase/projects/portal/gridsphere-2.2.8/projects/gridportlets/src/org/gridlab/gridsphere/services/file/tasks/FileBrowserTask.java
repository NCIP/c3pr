/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileTask;
import org.gridlab.gridsphere.services.file.FileResource;

/**
 * Describes a file browser task.
 */
public interface FileBrowserTask extends FileTask {

    /**
     * Returns the file resource used for this task
     * @return The file resource
     */
    public FileResource getFileResource();
}

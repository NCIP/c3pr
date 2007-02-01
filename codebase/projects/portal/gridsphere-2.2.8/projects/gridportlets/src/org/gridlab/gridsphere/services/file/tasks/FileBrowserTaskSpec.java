/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileTaskSpec;
import org.gridlab.gridsphere.services.file.FileResource;


/**
 * Describes a file browser task specification.
 */
public interface FileBrowserTaskSpec extends FileTaskSpec {

    /**
     * Returns the file resource required for the task
     * described by this specification.
     * @return The file resource
     */
    public FileResource getFileResource();

    /**
     * Sets the file resource required for the task
     * described by this specification.
     * @param resource The file resource
     */
    public void setFileResource(FileResource resource);

}

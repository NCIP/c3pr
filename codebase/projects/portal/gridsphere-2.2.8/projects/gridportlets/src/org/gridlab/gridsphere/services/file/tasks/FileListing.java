/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileListing.java,v 1.1.1.1 2007-02-01 20:40:31 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import java.util.List;

/**
 * Describes a task for listing files at a file location.
 */
public interface FileListing extends FileBrowserTask {

    /**
     * Returns the list of file locations returned by this task.
     * @return The file locations returned by this task
     * @see org.gridlab.gridsphere.services.file.FileLocation
     */
    public List getFileLocations();
}

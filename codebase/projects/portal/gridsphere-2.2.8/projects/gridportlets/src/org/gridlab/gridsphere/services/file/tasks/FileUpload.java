/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileUpload.java,v 1.1.1.1 2007-02-01 20:40:34 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserTask;

/**
 * Describes a task to upload a local file to a remote location.
 */
public interface FileUpload extends FileBrowserTask {

    /**
     * Returns the destination resource used for this file transfer
     * @return The destination resource
     */
    public FileResource getDstResource();
 }

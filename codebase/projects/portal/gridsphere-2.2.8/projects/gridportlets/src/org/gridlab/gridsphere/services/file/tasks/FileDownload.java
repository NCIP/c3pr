/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileDownload.java,v 1.1.1.1 2007-02-01 20:40:31 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserTask;

/**
 * Describes a task to download a remote file to a local location.
 */
public interface FileDownload extends FileBrowserTask {

    /**
     * Returns the source resource used for this file transfer
     * @return The source resource
     */
    public FileResource getSrcResource();
 }

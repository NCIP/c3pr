/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileTransfer.java,v 1.1.1.1 2007-02-01 20:40:34 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileResource;

/**
 * Describes a task to transfer a specified set of files to a
 * specified file location.
 */
public interface FileTransfer extends FileBrowserTask {

    /**
     * Returns the source resource used for this file transfer.
     * @return The source resource
     */
    public FileResource getSrcResource();

    /**
     * Returns the destination resource used for this file transfer
     * @return The destination resource
     */
    public FileResource getDstResource();
 }

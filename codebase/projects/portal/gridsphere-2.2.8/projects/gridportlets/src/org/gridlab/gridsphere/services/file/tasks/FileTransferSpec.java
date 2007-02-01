/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileTransferSpec.java,v 1.1.1.1 2007-02-01 20:40:34 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.FileResource;

/**
 * Describes a file transfer task to perform. The file transfer
 * task will transfer the specified set of files to the
 * specified file location.
 */
public interface FileTransferSpec extends FileBrowserTaskSpec {

    /**
     * Returns the source resource to use for this file task.
     * If null, then file task service will find the appropriate
     * resource to use. Note that this method is equivalent to
     * calling <code>getFileResource</code>.
     * @return The source resource
     */
    public FileResource getSrcResource();

    /**
     * Sets the source resource to use for this file task.
     * Set to null if you wish to let the file task service
     * find the appropriate to use for this file listing.
     * By default it is null. Note that this method is equivalent to
     * calling <code>setFileResource</code>.
     * @param fileResource The source resource
     */
    public void setSrcResource(FileResource fileResource);

    /**
     * Returns the file set to transfer.
     * @return  The file set
     */
    public FileSet getFileSet();

    /**
     * Sets the file set to transfer.
     * @param fileSet The file set
     */
    public void setFileSet(FileSet fileSet);

    /**
     * Returns the destination resource to use for this file task.
     * If null, then file task service will find the appropriate
     * resource to use.
     * @return The destination resource
     */
    public FileResource getDstResource();

    /**
     * Sets the destination resource to use for this file task.
     * Set to null if you wish to let the file task service
     * find the appropriate to use for this file listing.
     * By default it is null.
     * @param fileResource The destination resource
     */
    public void setDstResource(FileResource fileResource);


    /**
     * Returns the destination (file location) to which the file set should be transfered.
     * @return The destination
     */
    public FileLocation getDestination();

    /**
     * Sets the destination (file location) to which the file set should be transfered.
     * @param destination The destination
     */
    public void setDestination(FileLocation destination);
}

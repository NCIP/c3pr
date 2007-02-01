/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileUploadSpec.java,v 1.1.1.1 2007-02-01 20:40:35 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserTaskSpec;

/**
 * Describes a file transfer task to perform. The file transfer
 * task will transfer the specified set of files to the
 * specified file location.
 */
public interface FileUploadSpec extends FileBrowserTaskSpec {

    /**
     * Returns local path to the file to uploaded.
     * @return The local file path
     */
    public String getFilePath();

    /**
     * Sets the local path to the file to uploaded.
     * @param filePath The local file path
     */
    public void setFilePath(String filePath);

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
     * Returns the location to which the file should be uploaded.
     * @return The destination
     */
    public FileLocation getUploadLocation();

    /**
     * Sets the location to which the file should be uploaded.
     * @param destination The destination
     */
    public void setUploadLocation(FileLocation destination);
}

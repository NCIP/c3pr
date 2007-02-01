/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileDownloadSpec.java,v 1.1.1.1 2007-02-01 20:40:31 kherm Exp $
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
public interface FileDownloadSpec extends FileBrowserTaskSpec {

    /**
     * Returns the source resource to use for this file task.
     * If null, then file task service will find the appropriate
     * resource to use.
     * @return The source resource
     */
    public FileResource getSrcResource();

    /**
     * Sets the source resource to use for this file task.
     * Set to null if you wish to let the file task service
     * find the appropriate to use for this file listing.
     * By default it is null.
     * @param fileResource The source resource
     */
    public void setSrcResource(FileResource fileResource);


    /**
     * Returns the location of the file to be downloaded.
     * @return The file location
     */
    public FileLocation getFileLocation();

    /**
     * Sets the location of the file to be downloaded.
     * @param fileLocation The file location
     */
    public void setFileLocation(FileLocation fileLocation);

    /**
     * Returns local path to the file to downloaded.
     * @return The local file path
     */
    public String getDownloadPath();

    /**
     * Sets the local path to the file to downloaded.
     * @param downloadPath The local file path
     */
    public void setDownloadPath(String downloadPath);

}

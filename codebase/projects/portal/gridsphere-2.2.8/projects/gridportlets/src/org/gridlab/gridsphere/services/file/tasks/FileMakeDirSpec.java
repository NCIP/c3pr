/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileMakeDirSpec.java,v 1.1.1.1 2007-02-01 20:40:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileLocation;

/**
 * Describes a make directory task to perform. The make directory
 * task will create a directory with the specified name in the
 * specified (parent) directory location.
 */
public interface FileMakeDirSpec extends FileBrowserTaskSpec {

    /**
     * Returns the parent location of the new directory to be created.
     * @return The directory location
     */
    public FileLocation getParentLocation();

    /**
     * Sets the parent location of the new directory to be created.
     * @param location The directory location
     */
    public void setParentLocation(FileLocation location);

    /**
     * Returns the name of the new directory to create.
     * @return The name of the new directory
     */
    public String getDirectoryName();

    /**
     * Returns the name of the new directory to create.
     * @param dirName The name of the new directory
     */
    public void setDirectoryName(String dirName);
}

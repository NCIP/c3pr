/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.logical;

import org.gridlab.gridsphere.services.file.FileLocation;

/**
 * Describes a make logical file task to perform.
 */
public interface MakeLogicalFileTaskSpec extends LogicalFileTaskSpec {

    /**
     * Returns the location for the logical file to be created.
     * @return The location for the logical file
     */
    public FileLocation getFileLocation();

    /**
     * Sets the location for the logical file to be created.
     * @param loc The location for the logical file
     */
    public void setFileLocation(FileLocation loc);
}

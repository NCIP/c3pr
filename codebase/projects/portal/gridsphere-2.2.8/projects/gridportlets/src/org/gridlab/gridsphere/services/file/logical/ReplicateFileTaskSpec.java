/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.logical;

import org.gridlab.gridsphere.services.file.FileLocation;

/**
 * Describes a replicate file task to perform.
 */
public interface ReplicateFileTaskSpec extends LogicalFileTaskSpec {

    /**
     * Returns the location for the logical file to be replicated.
     * @return The location for the logical file
     */
    public FileLocation getLogicalLocation();

    /**
     * Sets the location for the logical file to be replicated.
     * @param loc The location for the logical file
     */
    public void setLogicalLocation(FileLocation loc);

    /**
     * Returns the location for where to replicate the logical file.
     * @return The location for the repilca file
     */
    public FileLocation getReplicaLocation();

    /**
     * Sets the location for where to replicate the logical file.
     * @param loc The location for the repilca file
     */
    public void setReplicaLocation(FileLocation loc);
}

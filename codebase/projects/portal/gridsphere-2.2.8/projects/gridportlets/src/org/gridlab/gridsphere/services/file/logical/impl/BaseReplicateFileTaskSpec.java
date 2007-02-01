/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */

package org.gridlab.gridsphere.services.file.logical.impl;

import org.gridlab.gridsphere.services.file.logical.ReplicateFileTaskSpec;
import org.gridlab.gridsphere.services.file.logical.ReplicateFileTaskType;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for replicate logical file task specifications.
 */
public class BaseReplicateFileTaskSpec
        extends BaseLogicalFileTaskSpec
        implements ReplicateFileTaskSpec {

    protected FileLocation logicalLocation = null;
    protected FileLocation replicaLocation = null;

    /**
     * Default constructor
     */
    public BaseReplicateFileTaskSpec() {
        super();
    }

    public TaskType getTaskType() {
        return ReplicateFileTaskType.INSTANCE;
    }

    /**
     * Constructs a base file replicate logical file task containing the
     * given replicate logical file spec.
     * @param spec The file name change spec.
     */
    public BaseReplicateFileTaskSpec(ReplicateFileTaskSpec spec) {
        super(spec);
    }


    public FileLocation getLogicalLocation() {
        return logicalLocation;

    }

    public void setLogicalLocation(FileLocation loc) {
        logicalLocation = (FileLocation)loc;
    }

    public FileLocation getReplicaLocation() {
        return replicaLocation;

    }

    public void setReplicaLocation(FileLocation loc) {
        replicaLocation = (FileLocation)loc;
    }
}

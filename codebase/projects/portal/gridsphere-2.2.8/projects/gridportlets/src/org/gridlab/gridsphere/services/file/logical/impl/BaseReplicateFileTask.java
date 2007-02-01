/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */

package org.gridlab.gridsphere.services.file.logical.impl;

import org.gridlab.gridsphere.services.file.logical.ReplicateFileTaskSpec;
import org.gridlab.gridsphere.services.file.logical.ReplicateFileTask;

/**
 * Provides a base implementation for replicate logical file tasks.
 */
public class BaseReplicateFileTask
        extends BaseLogicalFileTask
        implements ReplicateFileTask {

    /**
     * Default constructor
     */
    public BaseReplicateFileTask() {
        super();
    }

    /**
     * Constructs a base file make logical file task containing the
     * given make logical file spec.
     * @param spec The file name change spec.
     */
    public BaseReplicateFileTask(ReplicateFileTaskSpec spec) {
        super(spec);
    }
}

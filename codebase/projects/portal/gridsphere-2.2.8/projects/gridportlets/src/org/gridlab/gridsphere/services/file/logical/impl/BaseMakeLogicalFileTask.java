/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */

package org.gridlab.gridsphere.services.file.logical.impl;

import org.gridlab.gridsphere.services.file.logical.MakeLogicalFileTask;
import org.gridlab.gridsphere.services.file.logical.MakeLogicalFileTaskSpec;

/**
 * Provides a base implementation for make logical file tasks.
 */
public class BaseMakeLogicalFileTask
        extends BaseLogicalFileTask
        implements MakeLogicalFileTask {

    /**
     * Default constructor
     */
    public BaseMakeLogicalFileTask() {
        super();
    }

    /**
     * Constructs a base file make logical file task containing the
     * given make logical file spec.
     * @param spec The file name change spec.
     */
    public BaseMakeLogicalFileTask(MakeLogicalFileTaskSpec spec) {
        super(spec);
    }
}

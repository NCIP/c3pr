/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileCopySpec;
import org.gridlab.gridsphere.services.file.tasks.FileCopy;

/**
 * Provides a base implementation for file copy tasks.
 */
public class BaseFileCopy
        extends BaseFileTransfer
        implements FileCopy {

    /**
     * Default constructor
     */
    public BaseFileCopy() {
        super();
    }

    /**
     * Constructs a base file copy task containing the given file copy spec.
     * @param spec The file copy task spec
     */
    public BaseFileCopy(FileCopySpec spec) {
        super(spec);
    }
}

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileDeletion;
import org.gridlab.gridsphere.services.file.tasks.FileDeletionSpec;

/**
 * Provides a base implementation for file deletion tasks.
 */
public class BaseFileDeletion
        extends BaseFileBrowserTask
        implements FileDeletion {

    /**
     * Default constructor
     */
    public BaseFileDeletion() {
        super();
    }

    /**
     * Constructs a base file deletion task containing the
     * given file deletion spec.
     * @param spec The file deletion spec.
     */
    public BaseFileDeletion(FileDeletionSpec spec) {
        super(spec);
    }
}

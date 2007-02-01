/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileMakeDirSpec;
import org.gridlab.gridsphere.services.file.tasks.FileMakeDir;

/**
 * Provides a base implementation for make directory tasks.
 */
public class BaseFileMakeDir
        extends BaseFileBrowserTask
        implements FileMakeDir {

    /**
     * Default constructor
     */
    public BaseFileMakeDir() {
        super();
    }

    /**
     * Constructs a base make directory task containing the
     * given make directory spec.
     * @param spec The make directory spec.
     */
    public BaseFileMakeDir(FileMakeDirSpec spec) {
        super(spec);
    }
}

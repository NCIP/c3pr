/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */

package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileNameChangeSpec;
import org.gridlab.gridsphere.services.file.tasks.FileNameChange;

/**
 * Provides a base implementation for file name change tasks.
 */
public class BaseFileNameChange
        extends BaseFileBrowserTask
        implements FileNameChange {

    /**
     * Default constructor
     */
    public BaseFileNameChange() {
        super();
    }

    /**
     * Constructs a base file name change task containing the
     * given file name change spec.
     * @param spec The file name change spec.
     */
    public BaseFileNameChange(FileNameChangeSpec spec) {
        super(spec);
    }
}

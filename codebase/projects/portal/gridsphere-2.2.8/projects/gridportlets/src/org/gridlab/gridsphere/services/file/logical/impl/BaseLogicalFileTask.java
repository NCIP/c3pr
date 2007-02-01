/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseLogicalFileTask.java,v 1.1.1.1 2007-02-01 20:40:21 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.logical.impl;

import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowserTask;
import org.gridlab.gridsphere.services.file.logical.LogicalFileTaskSpec;

/**
 * Base implementation for logical file tasks
 */
public class BaseLogicalFileTask extends BaseFileBrowserTask {

    /**
     * Default constructor
     */
    public BaseLogicalFileTask() {
        super();
    }

    /**
     * Constructs a base logical file task containing the
     * given logical file task spec
     * @param spec The logical file task spec
     */
    public BaseLogicalFileTask(LogicalFileTaskSpec spec) {
        super(spec);
    }
}

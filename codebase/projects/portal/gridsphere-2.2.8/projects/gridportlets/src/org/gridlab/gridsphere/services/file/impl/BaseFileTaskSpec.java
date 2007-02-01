/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileTaskSpec.java,v 1.1.1.1 2007-02-01 20:40:09 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.impl;

import org.gridlab.gridsphere.services.task.impl.BaseTaskSpec;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.file.FileTaskSpec;
import org.gridlab.gridsphere.services.file.FileTaskType;


/**
 * Provides a base implementation for (all) file task specifications.
 */
public class BaseFileTaskSpec extends BaseTaskSpec
        implements FileTaskSpec {

    /**
     * Default constructor
     */
    public BaseFileTaskSpec() {
        super();
    }

    /**
     * Obtains values from given deletion sepc
     * @param spec
     */
    public BaseFileTaskSpec(FileTaskSpec spec) {
        super(spec);
    }
    
    public TaskType getTaskType() {
        return FileTaskType.INSTANCE;
    }

    public Object clone() {
        BaseFileTaskSpec spec = new BaseFileTaskSpec(this);
        return spec;
    }
}


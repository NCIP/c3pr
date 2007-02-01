/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileTask.java,v 1.1.1.1 2007-02-01 20:40:06 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.impl;

import org.gridlab.gridsphere.services.task.impl.BaseTask;
import org.gridlab.gridsphere.services.file.FileTask;
import org.gridlab.gridsphere.services.file.FileTaskSpec;

/**
 * Base implementation for (all) file tasks.
 */
public class BaseFileTask
        extends BaseTask
        implements FileTask {

    private String fileTaskId = null;

    /**
     * Default constructor
     */
    public BaseFileTask() {
        super();
    }

    /**
     * Constructs a base file task with the file task given spec
     * @param spec
     */
    public BaseFileTask(FileTaskSpec spec) {
        super(spec);
    }

    public String getFileTaskId() {
        return fileTaskId;
    }

    /**
     * Sets the file task id
     * @param fileTaskId The file task id
     */
    public void setFileTaskId(String fileTaskId) {
        this.fileTaskId = fileTaskId;
    }
}

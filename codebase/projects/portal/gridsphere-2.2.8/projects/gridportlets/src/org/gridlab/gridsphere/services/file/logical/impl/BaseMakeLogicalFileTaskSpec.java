/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */

package org.gridlab.gridsphere.services.file.logical.impl;

import org.gridlab.gridsphere.services.file.logical.MakeLogicalFileTaskSpec;
import org.gridlab.gridsphere.services.file.logical.MakeLogicalFileTaskType;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.task.TaskType;

/**
 * Provides a base implementation for make logical file task specifications.
 */
public class BaseMakeLogicalFileTaskSpec
        extends BaseLogicalFileTaskSpec
        implements MakeLogicalFileTaskSpec {

    protected FileLocation fileLocation = null;

    /**
     * Default constructor
     */
    public BaseMakeLogicalFileTaskSpec() {
        super();
    }

    public TaskType getTaskType() {
        return MakeLogicalFileTaskType.INSTANCE;
    }

    /**
     * Constructs a base file make logical file task containing the
     * given make logical file spec.
     * @param spec The file name change spec.
     */
    public BaseMakeLogicalFileTaskSpec(MakeLogicalFileTaskSpec spec) {
        super(spec);
    }


    public FileLocation getFileLocation() {
        return fileLocation;

    }

    public void setFileLocation(FileLocation loc) {
        fileLocation = (FileLocation)loc;
    }
}

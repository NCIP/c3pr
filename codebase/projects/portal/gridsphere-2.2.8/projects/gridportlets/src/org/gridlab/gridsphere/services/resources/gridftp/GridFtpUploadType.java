/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileTaskType;

/**
 * Describes a type of grid ftp upload task.
 */
public class GridFtpUploadType extends FileTaskType {

    public static final GridFtpUploadType INSTANCE
            = new GridFtpUploadType(GridFtpUpload.class, GridFtpUploadSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected GridFtpUploadType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

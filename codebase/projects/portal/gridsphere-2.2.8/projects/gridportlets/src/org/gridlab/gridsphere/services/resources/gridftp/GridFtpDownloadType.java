/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileTaskType;

/**
 * Describes a type of grid ftp fownload task.
 */
public class GridFtpDownloadType extends FileTaskType {

    public static final GridFtpDownloadType INSTANCE
            = new GridFtpDownloadType(GridFtpDownload.class, GridFtpDownloadSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected GridFtpDownloadType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

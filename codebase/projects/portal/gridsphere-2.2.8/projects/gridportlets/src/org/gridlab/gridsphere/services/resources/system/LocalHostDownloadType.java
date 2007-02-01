/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.FileTaskType;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpUpload;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpUploadSpec;

/**
 * Describes a type of gridlab logical rename task.
 */
public class LocalHostDownloadType extends FileTaskType {

    public static final LocalHostDownloadType INSTANCE
            = new LocalHostDownloadType(LocalHostDownload.class, LocalHostDownloadSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected LocalHostDownloadType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

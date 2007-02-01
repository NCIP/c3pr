/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileUploadType.java,v 1.1.1.1 2007-02-01 20:40:35 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

/**
 * Describes a type of file upload task.
 */
public class FileUploadType extends FileBrowserTaskType {

    public static final FileUploadType INSTANCE
            = new FileUploadType(FileUpload.class, FileUploadSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected FileUploadType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

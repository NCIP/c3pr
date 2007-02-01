/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileTransferType.java,v 1.1.1.1 2007-02-01 20:40:34 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

/**
 * Describes a type of file transfer task.
 */
public class FileTransferType extends FileBrowserTaskType {

    public static final FileTransferType INSTANCE
            = new FileTransferType(FileTransfer.class, FileTransferSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected FileTransferType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileCopyType.java,v 1.1.1.1 2007-02-01 20:40:29 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

/**
 * Describes a type of file copy task
 */
public class FileCopyType extends FileTransferType {

    public static final FileCopyType INSTANCE
            = new FileCopyType(FileCopy.class, FileCopySpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected FileCopyType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

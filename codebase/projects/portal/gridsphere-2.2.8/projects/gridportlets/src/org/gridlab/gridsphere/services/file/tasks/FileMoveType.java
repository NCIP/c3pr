/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileMoveType.java,v 1.1.1.1 2007-02-01 20:40:33 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

/**
 * Describes a file move type.
 */
public class FileMoveType extends FileTransferType  {

    public static final FileMoveType INSTANCE
            = new FileMoveType(FileMove.class, FileMoveSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected FileMoveType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileMakeDirType.java,v 1.1.1.1 2007-02-01 20:40:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

/**
 * Describes a type of file make dir task.
 */
public class FileMakeDirType extends FileBrowserTaskType {

    public static final FileMakeDirType INSTANCE
            = new FileMakeDirType(FileMakeDir.class, FileMakeDirSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected FileMakeDirType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

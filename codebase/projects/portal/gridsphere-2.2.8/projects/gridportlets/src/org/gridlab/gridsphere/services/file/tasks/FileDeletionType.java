/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileDeletionType.java,v 1.1.1.1 2007-02-01 20:40:31 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

/**
 * Describes a type of file deletion task
 */
public class FileDeletionType extends FileBrowserTaskType {

    public static final FileDeletionType INSTANCE
            = new FileDeletionType(FileDeletion.class, FileDeletionSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected FileDeletionType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

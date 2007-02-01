/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileNameChangeType.java,v 1.1.1.1 2007-02-01 20:40:34 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

/**
 * Describes a type of file name change task.
 */
public class FileNameChangeType extends FileBrowserTaskType {

    public static final FileNameChangeType INSTANCE
            = new FileNameChangeType(FileNameChange.class, FileNameChangeSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected FileNameChangeType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

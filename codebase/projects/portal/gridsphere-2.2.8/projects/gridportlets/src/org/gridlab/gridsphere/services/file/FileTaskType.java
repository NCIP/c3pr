/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileTaskType.java,v 1.1.1.1 2007-02-01 20:40:02 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.services.task.TaskType;


/**
 * Describes a type of file task
 */
public class FileTaskType extends TaskType {

    public static final FileTaskType INSTANCE
            = new FileTaskType(FileTask.class, FileTaskSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected FileTaskType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

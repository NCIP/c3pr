/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileBrowserTaskType.java,v 1.1.1.1 2007-02-01 20:40:29 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileTaskType;

/**
 * Describes a type of file browser task
 */
public class FileBrowserTaskType extends FileTaskType {

    public static final FileBrowserTaskType INSTANCE
            = new FileBrowserTaskType(FileBrowserTask.class, FileBrowserTaskSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected FileBrowserTaskType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

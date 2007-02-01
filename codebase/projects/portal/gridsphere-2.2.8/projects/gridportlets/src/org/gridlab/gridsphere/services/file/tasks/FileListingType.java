/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileListingType.java,v 1.1.1.1 2007-02-01 20:40:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

/**
 * Describes a type of file listing task.
 */
public class FileListingType extends FileBrowserTaskType {

    public static final FileListingType INSTANCE
            = new FileListingType(FileListing.class, FileListingSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected FileListingType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

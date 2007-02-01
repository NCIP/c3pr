/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.file.tasks.FileListingType;

/**
 * Describes a type of gridlab logical file listing task.
 */
public class LocalHostListType extends FileListingType {

    public static final LocalHostListType INSTANCE
            = new LocalHostListType(LocalHostListType.class, LocalHostListSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected LocalHostListType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

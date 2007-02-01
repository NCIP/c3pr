package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.FileTaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpRenameType.java,v 1.1.1.1 2007-02-01 20:41:13 kherm Exp $
 * <p>
 */
public class GridFtpRenameType extends FileTaskType {

    public static final GridFtpRenameType INSTANCE
            = new GridFtpRenameType(GridFtpRename.class, GridFtpRenameSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected GridFtpRenameType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.FileMakeDirType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpMakeDirType.java,v 1.1.1.1 2007-02-01 20:41:13 kherm Exp $
 * <p>
 */
public class GridFtpMakeDirType extends FileMakeDirType {

    public static final GridFtpMakeDirType INSTANCE
            = new GridFtpMakeDirType(GridFtpMakeDir.class, GridFtpMakeDirSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected GridFtpMakeDirType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

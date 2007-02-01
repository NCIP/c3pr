package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.FileCopyType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpCopyType.java,v 1.1.1.1 2007-02-01 20:41:10 kherm Exp $
 * <p>
 */
public class GridFtpCopyType extends FileCopyType {

    public static final GridFtpCopyType INSTANCE
            = new GridFtpCopyType(GridFtpCopy.class, GridFtpCopySpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected GridFtpCopyType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

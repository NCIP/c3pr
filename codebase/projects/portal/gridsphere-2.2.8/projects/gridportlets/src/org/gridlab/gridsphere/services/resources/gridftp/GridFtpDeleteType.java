package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.FileDeletionType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpDeleteType.java,v 1.1.1.1 2007-02-01 20:41:11 kherm Exp $
 * <p>
 */
public class GridFtpDeleteType extends FileDeletionType {

    public static final GridFtpDeleteType INSTANCE
            = new GridFtpDeleteType(GridFtpDelete.class, GridFtpDeleteSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected GridFtpDeleteType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

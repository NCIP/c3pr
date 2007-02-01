package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.FileMoveType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpMoveType.java,v 1.1.1.1 2007-02-01 20:41:13 kherm Exp $
 * <p>
 */
public class GridFtpMoveType extends FileMoveType  {

    public static final GridFtpMoveType INSTANCE
            = new GridFtpMoveType(GridFtpMove.class, GridFtpMoveSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected GridFtpMoveType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

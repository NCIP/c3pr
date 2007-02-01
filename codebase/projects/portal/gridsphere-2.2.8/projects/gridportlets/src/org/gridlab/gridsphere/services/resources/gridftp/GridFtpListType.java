package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.file.tasks.FileListingType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpListType.java,v 1.1.1.1 2007-02-01 20:41:11 kherm Exp $
 * <p>
 */
public class GridFtpListType extends FileListingType {

    public static final GridFtpListType INSTANCE
            = new GridFtpListType(GridFtpListType.class, GridFtpListSpec.class);

    /**
     * Protected so only one instance can be created.
     * @param taskClass
     * @param taskSpecClass
     */
    protected GridFtpListType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

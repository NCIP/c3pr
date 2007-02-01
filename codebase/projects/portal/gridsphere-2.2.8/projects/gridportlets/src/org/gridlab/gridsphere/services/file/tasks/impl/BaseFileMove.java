/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseFileMove.java,v 1.1.1.1 2007-02-01 20:40:38 kherm Exp $
 */

package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileMoveSpec;
import org.gridlab.gridsphere.services.file.tasks.FileMove;

/**
 * Provides a base implementation for file move tasks.
 */
public class BaseFileMove
        extends BaseFileTransfer
        implements FileMove {

    /**
     * Default constructor
     */
    public BaseFileMove() {
        super();
    }

    /**
     * Constructs a base file move task containing the
     * given file move spec.
     * @param spec The file move spec.
     */
    public BaseFileMove(FileMoveSpec spec) {
        super(spec);
    }
}

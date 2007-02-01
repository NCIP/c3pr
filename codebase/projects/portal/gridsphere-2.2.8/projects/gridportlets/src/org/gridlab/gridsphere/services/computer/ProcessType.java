/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ProcessType.java,v 1.1.1.1 2007-02-01 20:39:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.ResourceType;

/**
 * Describes a type of gridlab logical file resource.
 */
public class ProcessType extends ResourceType {

    public static ProcessType INSTANCE
            = new ProcessType("Process", org.gridlab.gridsphere.services.computer.Process.class);

    protected ProcessType() {}

    protected ProcessType(String id, Class resourceClass) {
        super(id, resourceClass);
    }
}

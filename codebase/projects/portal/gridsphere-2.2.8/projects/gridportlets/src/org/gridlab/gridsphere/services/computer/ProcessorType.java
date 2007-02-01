/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ProcessorType.java,v 1.1.1.1 2007-02-01 20:39:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.resource.HardwareResourceType;

/**
 * Describes a type of gridlab logical file resource.
 */
public class ProcessorType extends ResourceType {

    public static ProcessorType INSTANCE
            = new ProcessorType("Processor", Processor.class);

    protected ProcessorType() {}

    protected ProcessorType(String id, Class resourceClass) {
        super(id, resourceClass);
    }
}

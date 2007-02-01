/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: HardwareResourceType.java,v 1.1.1.1 2007-02-01 20:40:50 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.resource.impl.HardwareResourceImpl;

public class HardwareResourceType extends ResourceType {

    public static final HardwareResourceType INSTANCE
            = new HardwareResourceType("HardwareResource", HardwareResource.class, HardwareResourceImpl.class);

    protected HardwareResourceType(String id, Class resourceClass, Class resourceImplementation) {
        super(id, resourceClass, resourceImplementation);
        addSuperType(HostResourceType.INSTANCE);
    }
}

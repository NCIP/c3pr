/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ServiceResourceType.java,v 1.1.1.1 2007-02-01 20:40:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.resource.impl.BaseServiceResource;

public class ServiceResourceType extends ResourceType {

    public static final ServiceResourceType INSTANCE
            = new ServiceResourceType("ServiceResource", ServiceResource.class, BaseServiceResource.class);

    protected ServiceResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
    }

    protected ServiceResourceType(String id, Class resourceClass, Class resourceImplementation) {
        super(id, resourceClass, resourceImplementation);
    }
}

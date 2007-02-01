/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: HostResourceType.java,v 1.1.1.1 2007-02-01 20:40:51 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.resource.impl.BaseHostResource;

public class HostResourceType extends ResourceType {

    public static final HostResourceType INSTANCE
            = new HostResourceType("HostResource", HostResource.class, BaseHostResource.class);

    protected HostResourceType(String id, Class resourceClass, Class resourceImplementation) {
        super(id, resourceClass, resourceImplementation);
    }
}

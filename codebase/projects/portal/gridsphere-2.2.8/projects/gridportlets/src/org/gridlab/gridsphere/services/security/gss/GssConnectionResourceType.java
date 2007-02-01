/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GssConnectionResourceType.java,v 1.1.1.1 2007-02-01 20:41:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.resource.ServiceResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;

/**
 * Describes a type of gss service resource.
 */
public class GssConnectionResourceType extends ResourceType {

    public static GssConnectionResourceType INSTANCE
            = new GssConnectionResourceType("GssConnectionResource", GssConnectionResource.class);

    protected GssConnectionResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(GssEnabledResourceType.INSTANCE);
        addSuperType(ServiceResourceType.INSTANCE);
    }
}

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.mds2;

import org.gridlab.gridsphere.services.info.InformationResourceType;
import org.gridlab.gridsphere.services.security.gss.GssEnabledResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;

/**
 * Describes a type of mds2 resource.
 */
public class Mds2ResourceType extends ResourceType {

    public static Mds2ResourceType INSTANCE = new Mds2ResourceType("Mds2Resource", Mds2Resource.class);

    protected Mds2ResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(InformationResourceType.INSTANCE);
        addSuperType(GssEnabledResourceType.INSTANCE);
    }
}

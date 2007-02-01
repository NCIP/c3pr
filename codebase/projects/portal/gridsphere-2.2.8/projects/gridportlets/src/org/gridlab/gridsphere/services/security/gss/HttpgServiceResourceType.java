/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.resource.WebServiceResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;

/**
 * Describes a type of httpg service resource.
 */
public class HttpgServiceResourceType extends ResourceType {

    public static final HttpgServiceResourceType INSTANCE
            = new HttpgServiceResourceType("HttpgServiceResource", HttpgServiceResource.class);

    protected HttpgServiceResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(WebServiceResourceType.INSTANCE);
        addSuperType(GssEnabledResourceType.INSTANCE);
    }
}

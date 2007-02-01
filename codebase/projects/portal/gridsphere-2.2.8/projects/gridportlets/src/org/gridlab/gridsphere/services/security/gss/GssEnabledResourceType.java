/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GssEnabledResourceType.java,v 1.1.1.1 2007-02-01 20:41:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.resource.ResourceType;

/**
 * Describes a type of gss service resource.
 */
public class GssEnabledResourceType extends ResourceType {

    public static GssEnabledResourceType INSTANCE
            = new GssEnabledResourceType("GssEnabledResource", GssEnabledResource.class);

    protected GssEnabledResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
    }
}

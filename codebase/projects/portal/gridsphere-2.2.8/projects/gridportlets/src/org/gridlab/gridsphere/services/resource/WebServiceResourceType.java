/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: WebServiceResourceType.java,v 1.1.1.1 2007-02-01 20:40:58 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.resource.impl.BaseWebServiceResource;

public class WebServiceResourceType extends ResourceType {

    public static final WebServiceResourceType INSTANCE
            = new WebServiceResourceType("WebServiceResource", WebServiceResource.class, BaseWebServiceResource.class);

    protected WebServiceResourceType(String id, Class resourceClass, Class resourceImplementation) {
        super(id, resourceClass, resourceImplementation);
        addSuperType(ServiceResourceType.INSTANCE);
    }
}

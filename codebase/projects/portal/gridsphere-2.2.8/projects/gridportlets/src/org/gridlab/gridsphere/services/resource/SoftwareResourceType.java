/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SoftwareResourceType.java,v 1.1.1.1 2007-02-01 20:40:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.resource.impl.BaseSoftwareResource;

public class SoftwareResourceType extends ResourceType {

    public static final SoftwareResourceType INSTANCE
            = new SoftwareResourceType("SoftwareResource", SoftwareResource.class, BaseSoftwareResource.class);

    protected SoftwareResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
    }

    protected SoftwareResourceType(String id, Class resourceClass, Class resourceImplementation) {
        super(id, resourceClass, resourceImplementation);
    }
}
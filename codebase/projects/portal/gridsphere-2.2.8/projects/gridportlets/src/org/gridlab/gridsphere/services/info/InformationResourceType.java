package org.gridlab.gridsphere.services.info;

import org.gridlab.gridsphere.services.resource.ResourceType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: InformationResourceType.java,v 1.1.1.1 2007-02-01 20:40:39 kherm Exp ${FILE}, ${VERSION} Mar 18, 2004 10:21:14 PM russell $
 * <p>
 * Describes an information resource type.
 */

public class InformationResourceType extends ResourceType {

    public static InformationResourceType INSTANCE
            = new InformationResourceType("InformationResource", InformationResource.class);

    protected InformationResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
    }
}

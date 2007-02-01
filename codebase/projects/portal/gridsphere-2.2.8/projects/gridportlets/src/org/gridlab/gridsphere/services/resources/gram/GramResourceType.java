package org.gridlab.gridsphere.services.resources.gram;

import org.gridlab.gridsphere.services.job.JobResourceType;
import org.gridlab.gridsphere.services.security.gss.GssEnabledResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GramResourceType.java,v 1.1.1.1 2007-02-01 20:41:09 kherm Exp $
 * <p>
 */

public class GramResourceType extends ResourceType {

    public static GramResourceType INSTANCE
            = new GramResourceType("GramResource", GramResource.class);

    protected GramResourceType(String id, Class resourceClass) {
        super(id, resourceClass);
        addSuperType(JobResourceType.INSTANCE);
        addSuperType(GssEnabledResourceType.INSTANCE);
    }
}

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.security.gss.GssEnabledResource;
import org.gridlab.gridsphere.services.security.gss.GssEnabledResourceType;
import org.gridlab.gridsphere.services.resource.impl.BaseServiceResource;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/**
 * Base abstract implementation for httpg service resources.
 */
public abstract class BaseGssServiceResource
        extends BaseServiceResource
        implements GssEnabledResource {

    private static PortletLog log = SportletLog.getInstance(BaseGssServiceResource.class);

    public BaseGssServiceResource() {
        super();
        setResourceType(GssEnabledResourceType.INSTANCE);
    }
}

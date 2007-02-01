/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceRegistryPortlet.java,v 1.1.1.1 2007-02-01 20:39:46 kherm Exp $
 */
package org.gridlab.gridsphere.portlets.resource;

import org.gridlab.gridsphere.portlets.ActionComponentPortlet;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.resource.ResourceRegistryComp;

import javax.portlet.PortletException;
import javax.portlet.PortletConfig;


public class ResourceRegistryPortlet extends ActionComponentPortlet {

    private static PortletLog log = SportletLog.getInstance(ResourceRegistryPortlet.class);

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        setViewModeComp(ResourceRegistryComp.class);
    }
}

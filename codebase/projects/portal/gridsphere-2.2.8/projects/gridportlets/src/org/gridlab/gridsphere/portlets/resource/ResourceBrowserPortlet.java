/**
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceBrowserPortlet.java,v 1.3 2004/06/29 09:40:16 russell Exp
 */
package org.gridlab.gridsphere.portlets.resource;

import org.gridlab.gridsphere.portlets.ActionComponentPortlet;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.resource.browser.ResourceBrowser;

import javax.portlet.PortletException;
import javax.portlet.PortletConfig;

public class ResourceBrowserPortlet extends ActionComponentPortlet {

    private static PortletLog log = SportletLog.getInstance(ResourceBrowserPortlet.class);

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        setViewModeComp(ResourceBrowser.class);
    }
}

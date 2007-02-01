/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ServiceResourceListViewPage.java,v 1.1.1.1 2007-02-01 20:42:15 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser.profiles;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.ServiceResourceType;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;

public class ServiceResourceListViewPage extends HardwareChildListViewPage {

    private transient static PortletLog log = SportletLog.getInstance(ServiceResourceListViewPage.class);

    public ServiceResourceListViewPage(ActionComponentFrame container, String compName) {
         super(container, compName);
        log.debug("ServiceResourceListViewPage()");
        // Resource type
        setResourceType(ServiceResourceType.INSTANCE);
        // Default page
        setDefaultJspPage("/jsp/resource/browser/profiles/ServiceResourceListViewPage.jsp");
     }
}

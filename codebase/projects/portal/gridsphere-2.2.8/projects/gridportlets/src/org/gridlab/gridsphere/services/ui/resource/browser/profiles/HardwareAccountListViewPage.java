/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardwareAccountListViewPage.java,v 1.1.1.1 2007-02-01 20:42:14 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser.profiles;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.resource.HardwareAccountType;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

public class HardwareAccountListViewPage extends HardwareChildListViewPage {

    private transient static PortletLog log = SportletLog.getInstance(HardwareAccountListViewPage.class);

    public HardwareAccountListViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Resource type
        setResourceType(HardwareAccountType.INSTANCE);
        setDefaultJspPage("/jsp/resource/browser/profiles/HardwareAccountListViewPage.jsp");
     }
}

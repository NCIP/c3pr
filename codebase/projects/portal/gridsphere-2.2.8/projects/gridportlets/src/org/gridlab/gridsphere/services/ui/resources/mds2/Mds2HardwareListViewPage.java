/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Mds2HardwareListViewPage.java,v 1.1.1.1 2007-02-01 20:42:15 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resources.mds2;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.profiles.HardwareResourceListViewPage;

public class Mds2HardwareListViewPage extends HardwareResourceListViewPage {

    private transient static PortletLog log = SportletLog.getInstance(Mds2HardwareListViewPage.class);

    public Mds2HardwareListViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        log.debug("Mds2HardwareListViewPage()");
        // Default page
        setDefaultJspPage("/jsp/resources/mds2/Mds2HardwareListViewPage.jsp");
    }
}

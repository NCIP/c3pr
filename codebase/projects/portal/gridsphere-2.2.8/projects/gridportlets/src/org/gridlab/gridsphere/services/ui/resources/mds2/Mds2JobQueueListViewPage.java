/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Mds2JobQueueListViewPage.java,v 1.1.1.1 2007-02-01 20:42:16 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resources.mds2;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.profiles.JobQueueListViewPage;

public class Mds2JobQueueListViewPage extends JobQueueListViewPage {

    private transient static PortletLog log = SportletLog.getInstance(Mds2JobQueueListViewPage.class);

    public Mds2JobQueueListViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        log.debug("Mds2JobQueueListViewPage()");
        // Default page
        setDefaultJspPage("/jsp/resources/mds2/Mds2JobQueueListViewPage.jsp");
    }
}

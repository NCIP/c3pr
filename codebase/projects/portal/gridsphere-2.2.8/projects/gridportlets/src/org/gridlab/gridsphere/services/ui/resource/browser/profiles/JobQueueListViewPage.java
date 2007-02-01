/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobQueueListViewPage.java,v 1.1.1.1 2007-02-01 20:42:14 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser.profiles;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.*;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

public class JobQueueListViewPage extends HardwareChildListViewPage {

    private transient static PortletLog log = SportletLog.getInstance(JobQueueListViewPage.class);

    public JobQueueListViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        log.debug("JobQueueListViewPage()");
        // Resource type
        setResourceType(JobQueueType.INSTANCE);
        setDefaultJspPage("/jsp/resource/browser/profiles/JobQueueListViewPage.jsp");
    }
}

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobListViewPage.java,v 1.1.1.1 2007-02-01 20:42:14 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser.profiles;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.ResourceProfile;
import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.resource.HardwareResource;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;

import java.util.*;

public class JobListViewPage extends HardwareChildListViewPage {

    private transient static PortletLog log = SportletLog.getInstance(JobListViewPage.class);

    public JobListViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        log.debug("JobListViewPage()");
        // Resource type
        setResourceType(JobType.INSTANCE);
        setDefaultJspPage("/jsp/resource/browser/profiles/JobListViewPage.jsp");
        setChildLevel(3);
    }

    public void onInit()
            throws PortletException {
        super.onInit();
    }
}

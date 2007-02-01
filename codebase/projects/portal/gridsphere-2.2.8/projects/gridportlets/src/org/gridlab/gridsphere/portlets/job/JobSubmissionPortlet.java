package org.gridlab.gridsphere.portlets.job;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.services.ui.job.JobListViewComp;
import org.gridlab.gridsphere.services.ui.job.JobComponentFrame;
import org.gridlab.gridsphere.services.ui.job.JobComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.portlets.ActionComponentPortlet;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletException;

public class JobSubmissionPortlet extends ActionComponentPortlet {

    private transient static PortletLog log = SportletLog.getInstance(JobSubmissionPortlet.class);

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        setViewModeComp(JobListViewComp.class);
    }

    protected ActionComponentFrame createActionComponentFrame(PortletRequest portletRequest, String cid) {
        return new JobComponentFrame(portletRequest, cid);
    }
}

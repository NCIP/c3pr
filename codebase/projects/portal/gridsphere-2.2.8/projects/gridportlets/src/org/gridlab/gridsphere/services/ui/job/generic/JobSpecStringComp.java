package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.JobComponent;
import org.gridlab.gridsphere.services.ui.job.JobComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.Job;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextAreaBean;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobSpecStringComp.java,v 1.1.1.1 2007-02-01 20:42:09 kherm Exp $
 */

public class JobSpecStringComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobSpecStringComp.class);

    protected TextAreaBean jobStringArea = null;

    public JobSpecStringComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        jobStringArea = createTextAreaBean("jobStringArea");
        setDefaultAction("doJobStringView");
        setDefaultJspPage("/jsp/job/generic/JobSpecStringComp.jsp");
    }

    public void doJobStringView(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);
        // Get job parameter
        Job job = getJob(parameters);
        if (job == null) {
            messageBox.appendText("No job parameter was provided!");
            messageBox.setMessageType(TextBean.MSG_ERROR);
        } else {
            String jobString = job.getJobSubmissionString();
            if (jobString == null) jobString = "Job string not available at this time.";
            jobStringArea.setValue(jobString + '\n');
        }
    }
}

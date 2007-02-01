package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.JobComponent;
import org.gridlab.gridsphere.services.job.Job;
import org.gridlab.gridsphere.services.job.JobSpec;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileHandle;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobOutputComp.java,v 1.1.1.1 2007-02-01 20:42:08 kherm Exp $
 */

public class JobOutputComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobOutputComp.class);

    protected TextBean jobStdoutText = null;
    protected TextBean jobStderrText = null;
    protected boolean isLoaded = false;

    public JobOutputComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        jobStdoutText = createTextBean("jobStdoutText");
        jobStderrText = createTextBean("jobStderrText");
        setDefaultAction("doJobOutputView");
        setDefaultJspPage("/jsp/job/generic/JobOutputComp.jsp");
    }

    public void doJobOutputView(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);
        // Get job parameter
        Job job = getJob(parameters);
        if (job == null) {
            messageBox.appendText("No job parameter was provided!");
            messageBox.setMessageType(TextBean.MSG_ERROR);
        } else {
            loadJobOutput(job);
        }
    }

    public void onLoad() throws PortletException {
        isLoaded = false;
    }

    public void onStore() throws PortletException {
        super.onStore();

        if (!isLoaded) {
            // Get job parameter
            if (jobOid == null) {
                messageBox.appendText("No job parameter was provided!");
                messageBox.setMessageType(TextBean.MSG_ERROR);
            } else {
                Job job = jobSubmissionService.getJob(jobOid);
                loadJobOutput(job);
            }
        }
    }

    public void loadJobOutput(Job job) throws PortletException {

        isLoaded = true;

        jobStdoutText.setValue("<br>");
        jobStderrText.setValue("<br>");


        /* Output */
        // Display stdout if not null
        FileLocation stdout = job.getStdoutLocation();
        if (stdout != null) {
            try {
                FileHandle stdoutHandle = new FileHandle(stdout);
                String stdoutText = stdoutHandle.readContents(getUser());
                jobStdoutText.setValue(stdoutText + "<br>");
            } catch (Exception e) {
                log.error("Unable to read job stdout", e);
                jobStdoutText.setValue(stdout.getUrl() + "<br>");
            }
        }

        // Display stderr if not null and not equal to stdout url
        FileLocation stderr = job.getStderrLocation();
        if (stderr != null && ! stderr.getUrl().equals(stdout.getUrl())) {
            try {
                log.debug("Reading job stdout");
                FileHandle stderrHandle = new FileHandle(stderr);
                String stderrText = stderrHandle.readContents(getUser());
                jobStderrText.setValue(stderrText + "<br>");
            } catch (Exception e) {
                log.error("Unable to read job stderr", e);
                jobStderrText.setValue(stdout.getUrl() + "<br>");
            }
        }
    }
}

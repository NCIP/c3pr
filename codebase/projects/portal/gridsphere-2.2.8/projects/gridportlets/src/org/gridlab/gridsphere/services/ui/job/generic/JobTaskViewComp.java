package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.JobComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.JobStatus;
import org.gridlab.gridsphere.services.job.Job;
import org.gridlab.gridsphere.services.job.JobSpec;
import org.gridlab.gridsphere.services.job.JobType;
import org.gridlab.gridsphere.services.core.utils.DateUtil;
import org.gridlab.gridsphere.services.task.TaskMetric;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Vector;
import java.text.DateFormat;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobTaskViewComp.java,v 1.1.1.1 2007-02-01 20:42:09 kherm Exp $
 */

public class JobTaskViewComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobTaskViewComp.class);

    protected TextBean jobTypeText = null;
    protected TextBean jobOidText = null;
    protected TextBean jobIdText = null;
    protected TextBean jobDescriptionText = null;
    protected TextBean jobStatusText = null;
    protected TextBean jobHostNameText = null;
    protected TextBean jobSchedulerNameText = null;
    protected TextBean jobQueueNameText = null;
    protected TextBean dateSubmittedText = null;
    protected TextBean dateStatusChangedText = null;
    protected TextBean dateEndedText = null;
    protected boolean isLoaded = false;

    public JobTaskViewComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        jobTypeText = createTextBean("jobTypeText");
        jobOidText = createTextBean("jobOidText");
        jobIdText = createTextBean("jobIdText");
        jobDescriptionText = createTextBean("jobDescriptionText");
        jobStatusText = createTextBean("jobStatusText");
        jobHostNameText = createTextBean("jobHostNameText");
        jobSchedulerNameText = createTextBean("jobSchedulerNameText");
        jobQueueNameText = createTextBean("jobQueueNameText");
        dateSubmittedText = createTextBean("dateSubmittedText");
        dateStatusChangedText = createTextBean("dateStatusChangedText");
        dateEndedText = createTextBean("dateEndedText");
        setDefaultAction("doJobTaskView");
        setDefaultJspPage("/jsp/job/generic/JobTaskComp.jsp");
    }

    public void doJobTaskView(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);
        // Get job parameter
        Job job = getJob(parameters);
        if (job == null) {
            messageBox.appendText("No job parameter was provided!");
            messageBox.setMessageType(TextBean.MSG_ERROR);
        } else {
            loadJobTaskView(job);
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
                loadJobTaskView(job);
            }
        }
    }

    protected void loadJobTaskView(Job job) throws PortletException {

        isLoaded = true;

        // Job type
        JobType jobType = job.getJobType();
        jobTypeText.setValue(jobType.getLabel(locale));
        // Job oid
        jobOidText.setValue(job.getOid());
        // Job oid
        jobIdText.setValue(job.getJobId());
        // Job description
        jobDescriptionText.setValue(job.getDescription());
        // Job status
        JobStatus jobStatus = job.getJobStatus();
        String statusMessage = job.getTaskStatusMessage();
        if (statusMessage == null) {
            jobStatusText.setValue(jobStatus.getName());
        } else {
            jobStatusText.setValue(statusMessage);
        }
        User user = getUser();
        // Date submitted
        Date dateSubmitted = job.getDateTaskSubmitted();
        if (dateSubmitted == null) {
            dateSubmittedText.setValue("");
        } else {
            long timeSubmitted = dateSubmitted.getTime();
            String textSubmitted = DateUtil.getLocalizedDate(user,
                                                             portletRequest.getLocale(),
                                                             timeSubmitted,
                                                             DateFormat.FULL,
                                                             DateFormat.FULL);
            dateSubmittedText.setValue(textSubmitted);
        }
        // Date status last changed
        Date dateStatusChanged = job.getDateTaskStatusChanged();
        if (dateStatusChanged == null) {
            dateStatusChangedText.setValue("");
        } else {
            long timeStatusChanged = dateStatusChanged.getTime();
            String textStatusChanged = DateUtil.getLocalizedDate(user,
                                                                 portletRequest.getLocale(),
                                                                 timeStatusChanged,
                                                                 DateFormat.FULL,
                                                                 DateFormat.FULL);
            dateStatusChangedText.setValue(textStatusChanged);
        }
        // Date ended
        Date dateEnded = job.getDateTaskEnded();
        if (dateEnded == null) {
            dateEndedText.setValue("");
        } else {
            long timeEnded = dateEnded.getTime();
            String textEnded = DateUtil.getLocalizedDate(user,
                                                         portletRequest.getLocale(),
                                                         timeEnded,
                                                         DateFormat.FULL,
                                                         DateFormat.FULL);
            dateEndedText.setValue(textEnded);
        }

        // Load job resource
        loadJobResource(job);

        // Load job metric list
        loadJobMetricList(job);
    }

    protected void loadJobResource(Job job) {

        JobSpec jobSpec = job.getJobSpec();

        // Job host
        String hostName = job.getHostName();
        if (hostName == null) {
            jobHostNameText.setValue("");
            jobSchedulerNameText.setValue("");
            jobQueueNameText.setValue("");
        } else {
            jobHostNameText.setValue(hostName);
            // Job scheduler
            String jobSchedulerName = jobSpec.getJobSchedulerName();
            if (jobSchedulerName == null) {
                jobSchedulerNameText.setValue("");
            } else {
                jobSchedulerNameText.setValue(jobSchedulerName);
            }
            // Job queue
            String jobQueueName = jobSpec.getJobQueueName();
            if (jobQueueName == null) {
                jobQueueNameText.setValue("");
            } else {
                jobQueueNameText.setValue(jobQueueName);
            }
        }
    }

    protected void loadJobMetricList(Job job) {

        List jobMetricList = new Vector();

        jobMetricList.addAll(job.getTaskMetrics());
        if (jobMetricList.size() == 0) {
            TaskMetric metric = new TaskMetric();
            metric.setName("job.monitoring.status");
            metric.setValue("No metrics available at this time");
            jobMetricList.add(metric);
        }

        setPageAttribute("jobMetricList", jobMetricList);
    }
}

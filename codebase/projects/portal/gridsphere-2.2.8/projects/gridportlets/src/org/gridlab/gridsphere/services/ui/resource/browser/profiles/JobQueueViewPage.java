/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobQueueViewPage.java,v 1.1.1.1 2007-02-01 20:42:14 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser.profiles;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.BaseResourceViewPage;
import org.gridlab.gridsphere.services.job.JobQueueType;
import org.gridlab.gridsphere.services.job.JobQueue;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import javax.portlet.PortletException;

public class JobQueueViewPage extends BaseResourceViewPage {

    private transient static PortletLog log = SportletLog.getInstance(JobQueueViewPage.class);

    protected TextBean queueNameText = null;
    protected TextBean queueStatusText = null;
    protected TextBean nodeCountText = null;
    protected TextBean jobWaitText = null;
    protected TextBean dispatchTypeText = null;
    protected TextBean maxJobsText = null;
    protected TextBean maxRunningJobsText = null;
    protected TextBean maxTimeText = null;
    protected TextBean maxCpuTimeText = null;
    protected TextBean maxCountText = null;
    protected TextBean memorySizeMBText = null;

    public JobQueueViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        log.debug("JobQueueViewPage()");
        
        // Hardware resource attributes
        queueNameText = createTextBean("queueNameText");
        queueStatusText = createTextBean("queueStatusText");
        nodeCountText = createTextBean("nodeCountText");
        jobWaitText = createTextBean("jobWaitText");
        dispatchTypeText = createTextBean("dispatchTypeText");
        maxJobsText = createTextBean("maxJobsText");
        maxRunningJobsText = createTextBean("maxRunningJobsText");
        maxTimeText = createTextBean("maxTimeText");
        maxCpuTimeText = createTextBean("maxCpuTimeText");
        maxCountText = createTextBean("maxCountText");
        memorySizeMBText = createTextBean("memorySizeMBText");

        // Resource type
        setResourceType(JobQueueType.INSTANCE);
        // Default page
        setDefaultJspPage("/jsp/resource/browser/profiles/JobQueueViewPage.jsp");
    }

    protected void loadResource(Resource resource) throws PortletException {

        super.loadResource(resource);

        JobQueue jobQueue = (JobQueue)resource;

        String jobQueueName = jobQueue.getName();
        queueNameText.setValue(jobQueueName);

        String status = jobQueue.getResourceAttributeValue(JobQueue.STATUS_ATTRIBUTE);
        queueStatusText.setValue(status);

        String jobWait = jobQueue.getResourceAttributeValue(JobQueue.JOBS_PENDING_ATTRIBUTE);
        jobWaitText.setValue(jobWait);

        String dispatchType = jobQueue.getResourceAttributeValue(JobQueue.DISPATCH_TYPE_ATTRIBUTE);
        dispatchTypeText.setValue(dispatchType);

        String nodeCount = jobQueue.getResourceAttributeValue(JobQueue.MAX_NUM_NODES_ATTRIBUTE);
        nodeCountText.setValue(nodeCount);

        String maxJobs = jobQueue.getResourceAttributeValue(JobQueue.MAX_JOBS_ATTRIBUTE);
        maxJobsText.setValue(maxJobs);

        String maxRunningJobs = jobQueue.getResourceAttributeValue(JobQueue.MAX_RUNNING_JOBS_ATTRIBUTE);
        maxRunningJobsText.setValue(maxRunningJobs);

        String maxTime = jobQueue.getResourceAttributeValue(JobQueue.MAX_TIME_ATTRIBUTE);
        maxTimeText.setValue(maxTime);

        String maxCpuTime = jobQueue.getResourceAttributeValue(JobQueue.MAX_CPU_TIME_ATTRIBUTE);
        maxCpuTimeText.setValue(maxCpuTime);

        String memorySizeMB = jobQueue.getResourceAttributeValue(JobQueue.MEMORY_SIZE_MB_ATTRIBUTE);
        memorySizeMBText.setValue(memorySizeMB);

        String maxCount = jobQueue.getResourceAttributeValue(JobQueue.MAX_COUNT_ATTRIBUTE);
        maxCountText.setValue(maxCount);
    }
}

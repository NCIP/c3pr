package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.JobComponent;
import org.gridlab.gridsphere.services.job.Job;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Iterator;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobListDeleteComp.java,v 1.1.1.1 2007-02-01 20:42:04 kherm Exp $
 */

public class JobListDeleteComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobListDeleteComp.class);

    protected List jobOidList = null;
    protected boolean isStoredJobList = false;

    public JobListDeleteComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        // Default action and page
        setDefaultAction("doView");
        setDefaultJspPage("/jsp/job/JobListDeleteComp.jsp");
    }

    public void onInit() throws PortletException {
        super.onInit();
        // Register action links
        container.registerActionLink(this, "doJobListView", JobListViewComp.class, DEFAULT_ACTION);
        container.registerActionLink(this, "doJobSubmitNew", JobSubmitWizard.class);
        container.registerActionLink(this, "doCancel", JobListViewComp.class, DEFAULT_ACTION);
    }

    public void onLoad() throws PortletException {
        super.onInit();
        isStoredJobList = false;
    }

    public void onStore() throws PortletException {
        if (!isStoredJobList) {
            List jobList = new Vector();
            for (int ii = 0; ii < jobOidList.size(); ++ii) {
                String jobOid = (String)jobOidList.get(ii);
                Job job = jobSubmissionService.getJob(jobOid);
                if (job != null) {
                    jobList.add(job);
                }
            }
            setPageAttribute("jobList", jobList);
            isStoredJobList = true;
        }
    }

    public void doView(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);

        jobOidList = (List)parameters.get(JOB_OID_LIST_PARAM);
        if (jobOidList == null) {
            messageBox.appendText("No jobs were specified for deletion");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            jobOidList = new Vector();
        }
    }

    public void doApply(Map parameters) throws PortletException {

        // Set next state
        setNextState(defaultJspPage);

        List jobList = new Vector();
        log.debug("Deleting " + jobList.size() + " jobs");
        Iterator jobOidIterator = jobOidList.iterator();
        while (jobOidIterator.hasNext()) {
            String jobOid = (String)jobOidIterator.next();
            Job job = jobSubmissionService.getJob(jobOid);
            if (job != null) {
                try {
                    log.debug("Deleting job " + jobOid);
                    jobSubmissionService.deleteJob(job);
                    log.debug("Deleted job " + jobOid);
                    jobList.add(job);
                } catch (Exception e) {
                    log.error("Error deleting job " + jobOid, e);
                }
            }
        }
        setPageAttribute("jobList", jobList);
        isStoredJobList = true;
        messageBox.appendText("The following jobs were successfully deleted");
        messageBox.setMessageType(TextBean.MSG_SUCCESS);
        setPageAttribute("deletedFlag", Boolean.TRUE);
    }

    public void doCancel(Map parameters) throws PortletException {
        setNextState(JobListViewComp.class, DEFAULT_ACTION, parameters);
    }
}

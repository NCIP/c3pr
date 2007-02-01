package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.generic.JobTaskViewComp;
import org.gridlab.gridsphere.services.ui.job.DeletedJobViewPage;
import org.gridlab.gridsphere.services.ui.job.JobComponent;
import org.gridlab.gridsphere.services.job.Job;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobDeleteComp.java,v 1.1.1.1 2007-02-01 20:42:04 kherm Exp $
 */

public class JobDeleteComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobDeleteComp.class);

    public static final String JOB_LIST_VIEW_ACTION = "doJobListView";
    public static final String JOB_DELETE_ACTION = "doJobDelete";
    public static final String JOB_NEW_ACTION = "doJobSubmitNew";

    protected ActionComponentFrame jobViewBean = null;

    public JobDeleteComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        // Job view bean
        jobViewBean = createActionComponentFrame("jobViewBean");
        jobViewBean.setNextState(JobViewPage.class);
        // Default action and page
        setDefaultAction(JOB_DELETE_ACTION);
        setDefaultJspPage("/jsp/job/JobDeleteComp.jsp");
    }

    public void onInit() throws PortletException {
        super.onInit();
        // Register action links
        container.registerActionLink(this, JOB_LIST_VIEW_ACTION, JobListViewComp.class);
        container.registerActionLink(this, JOB_NEW_ACTION, JobSubmitWizard.class);
    }

    public void doJobDelete(Map parameters) throws PortletException {
        log.error("doJobDelete");
        // Set next state
        setNextState(defaultJspPage);
        Job job = getJob(parameters);
        jobViewBean.doAction(DeletedJobViewPage.class, DEFAULT_ACTION, parameters);
        setPageAttribute("deletedFlag", Boolean.FALSE);
    }

    public void doApply(Map parameters) throws PortletException {
        log.error("doApply");
        // Set next state
        setNextState(defaultJspPage);
        // Get job from parameters
        Job job = getJob(parameters);
        if (job == null) {
            messageBox.appendText("No job parameter was provided");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }

//        if (jobOid == null) {
//            messageBox.appendText("No job parameter was provided!");
//            messageBox.setMessageType(TextBean.MSG_ERROR);
//            return;
//        }
//        Job job = jobSubmissionService.getJob(jobOid);
//        parameters.put(JOB_PARAM, job);
        try {
            jobSubmissionService.deleteJob(job);
            setPageAttribute("deletedFlag", Boolean.TRUE);
            messageBox.appendText("Job was successfully deleted");
            messageBox.setMessageType(TextBean.MSG_SUCCESS);
            jobViewBean.doAction(DeletedJobViewPage.class, DEFAULT_ACTION, parameters);
        } catch (Exception e) {
            log.error("Failed to delete job " + jobOid, e);
            setPageAttribute("deletedFlag", Boolean.FALSE);
            messageBox.appendText("No job parameter was provided!");
            messageBox.setMessageType(TextBean.MSG_ERROR);
        }
    }

    public void doCancel(Map parameters) throws PortletException {
        log.error("doCancel");
//        parameters.put(JOB_OID_PARAM, jobOid);
        Job job = getJob(parameters);
        setNextState(JobViewComp.class, DEFAULT_ACTION, parameters);
    }
}

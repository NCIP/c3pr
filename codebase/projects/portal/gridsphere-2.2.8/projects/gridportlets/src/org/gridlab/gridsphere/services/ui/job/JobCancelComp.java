package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.CanceledJobViewPage;
import org.gridlab.gridsphere.services.job.Job;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobCancelComp.java,v 1.1.1.1 2007-02-01 20:42:03 kherm Exp $
 */

public class JobCancelComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobCancelComp.class);

    public static final String JOB_LIST_VIEW_ACTION = "doJobListView";
    public static final String JOB_CANCEL_ACTION = "doJobCancel";
    public static final String JOB_NEW_ACTION = "doJobSubmitNew";

    protected ActionComponentFrame jobViewBean = null;

    public JobCancelComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        // Job view bean
        jobViewBean = createActionComponentFrame("jobViewBean");
        jobViewBean.setNextState(JobViewPage.class);
        // Default action and page
        setDefaultAction(JOB_CANCEL_ACTION);
        setDefaultJspPage("/jsp/job/JobCancelComp.jsp");
    }

    public void onInit() throws PortletException {
        super.onInit();
        // Register action links
        container.registerActionLink(this, JOB_LIST_VIEW_ACTION, JobListViewComp.class);
        container.registerActionLink(this, JOB_NEW_ACTION, JobSubmitWizard.class);
    }

    public void doJobCancel(Map parameters) throws PortletException {
        log.error("doJobCancel");
        // Set next state
        setNextState(defaultJspPage);
        Job job = getJob(parameters);
        jobViewBean.doAction(CanceledJobViewPage.class, DEFAULT_ACTION, parameters);
        setPageAttribute("canceledFlag", Boolean.FALSE);
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
            jobSubmissionService.cancelJob(job);
            setPageAttribute("canceledFlag", Boolean.TRUE);
            messageBox.appendText("Job was successfully canceled");
            messageBox.setMessageType(TextBean.MSG_SUCCESS);
            jobViewBean.doAction(CanceledJobViewPage.class, DEFAULT_ACTION, parameters);
        } catch (Exception e) {
            log.error("Failed to cancel job " + jobOid, e);
            setPageAttribute("canceledFlag", Boolean.FALSE);
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

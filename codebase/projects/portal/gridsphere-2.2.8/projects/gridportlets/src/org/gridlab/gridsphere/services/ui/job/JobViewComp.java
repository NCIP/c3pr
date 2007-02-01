package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.security.gss.ActiveCredentialFilter;
import org.gridlab.gridsphere.services.job.Job;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobViewComp.java,v 1.1.1.1 2007-02-01 20:42:06 kherm Exp $
 */

public class JobViewComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobViewComp.class);

    protected ActionComponentFrame jobViewBean = null;
    protected Boolean cancelFlag = Boolean.FALSE;
    protected Boolean migrateFlag = Boolean.FALSE;

    public JobViewComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Job view bean
        jobViewBean = createActionComponentFrame("jobViewBean");
        jobViewBean.setNextState(JobViewPage.class);
        // Default action and page
        setDefaultAction("doJobView");
        setRenderAction("doJobRefresh");
        setDefaultJspPage("/jsp/job/JobViewComp.jsp");
    }

    public void onInit() throws PortletException {
        super.onInit();
        // Register action links
        container.registerActionLink(this, "doJobListView", JobListViewComp.class);
        // Register credential actions
        String activeCredentialMethods[] = { RENDER_ACTION,
                                             "doJobNew",
                                             "doJobStage",
                                             "doJobCancel" };
        container.addActionFilter(this,  activeCredentialMethods, ActiveCredentialFilter.getInstance());
    }

    public void onStore() throws PortletException {
        super.onStore();
        setPageAttribute("cancelFlag", cancelFlag);
        setPageAttribute("migrateFlag", migrateFlag);
    }

    public void doJobView(Map parameters) throws PortletException {
        log.debug("doJobView()");
        Job job = getJob(parameters);
        jobViewBean.doAction(JobViewPage.class, DEFAULT_ACTION, parameters);
        cancelFlag = Boolean.FALSE;
        migrateFlag = Boolean.FALSE;
        if (job.isTaskLive()) {
            cancelFlag = Boolean.TRUE;
            if (job.getJobType().getSupportsJobMigration()) {
                migrateFlag = Boolean.TRUE;
            }
        }

        setNextState(defaultJspPage);
    }

    public void doJobRefresh(Map parameters) throws PortletException {
        log.debug("doJobRefresh(" + jobOid + ")");
        // Set next state
        setNextState(defaultJspPage);
        // Reset flags then proceed
        cancelFlag = Boolean.FALSE;
        migrateFlag = Boolean.FALSE;
        // Get job from parameters
        parameters.put(JOB_OID_PARAM, jobOid);
        Job job = getJob(parameters);
        if (job == null) {
            messageBox.appendText("No job parameter was provided");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            return;
        }
        // Set flags based on job information
        if (job.isTaskLive()) {
            cancelFlag = Boolean.TRUE;
            if (job.getJobType().getSupportsJobMigration()) {
                migrateFlag = Boolean.TRUE;
            }
        }
//        Job job = null;
//        cancelFlag = Boolean.FALSE;
//        cancelFlag = Boolean.FALSE;
//        migrateFlag = Boolean.FALSE;
//        if (jobOid != null) {
//            job = jobSubmissionService.getJob(jobOid);
//            if (job.isTaskLive()) {
//                cancelFlag = Boolean.TRUE;
//                if (job.getJobType().getSupportsJobMigration()) {
//                    migrateFlag = Boolean.TRUE;
//                }
//            }
//        }
//        parameters.put(JOB_PARAM, job);

        jobViewBean.doAction(JobViewPage.class, "doJobRefresh", parameters);
    }

    public void doJobNew(Map parameters) throws PortletException {
//        log.debug("doJobNew(" + jobOid + ")");
//        parameters.put(JOB_OID_PARAM, jobOid);
        setNextState(JobSubmitWizard.class, JobSubmitWizard.JOB_NEW_ACTION, parameters);
    }

    public void doJobStage(Map parameters) throws PortletException {
//        log.debug("doJobStage(" + jobOid + ")");
//        parameters.put(JOB_OID_PARAM, jobOid);
        setNextState(JobSubmitWizard.class, JobSubmitWizard.JOB_STAGE_ACTION, parameters);
    }

    public void doJobMigrate(Map parameters) throws PortletException {
//        log.debug("doJobMigrate(" + jobOid + ")");
//        parameters.put(JOB_OID_PARAM, jobOid);
        setNextState(JobMigrateWizard.class, DEFAULT_ACTION, parameters);
    }

    public void doJobCancel(Map parameters) throws PortletException {
//        log.debug("doJobCancel(" + jobOid + ")");
//        parameters.put(JOB_OID_PARAM, jobOid);
        setNextState(JobCancelComp.class, DEFAULT_ACTION, parameters);
    }

    public void doJobDelete(Map parameters) throws PortletException {
//        log.debug("doJobDelete(" + jobOid + ")");
//        parameters.put(JOB_OID_PARAM, jobOid);
        setNextState(JobDeleteComp.class, DEFAULT_ACTION, parameters);
    }
}

package org.gridlab.gridsphere.services.ui.job.generic;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.JobComponent;
import org.gridlab.gridsphere.services.ui.job.JobComponent;
import org.gridlab.gridsphere.services.ui.job.generic.*;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.Job;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobViewComp.java,v 1.1.1.1 2007-02-01 20:42:09 kherm Exp $
 */

public class JobViewComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobViewComp.class);

    public static final String JOB_VIEW_ACTION = "doJobView";
    public static final String JOB_REFRESH_ACTION = "doJobRefresh";

    public static final String JOB_SPEC_VIEW = "JOB_SPEC_VIEW";
    public static final String JOB_SPEC_STRING = "JOB_SPEC_STRING";
    public static final String JOB_LOCATION = "JOB_LOCATION";
    public static final String JOB_OUTPUT = "JOB_OUTPUT";

    protected ActionComponentFrame jobTaskBean = null;

    protected TextBean jobDetailsText = null;
    protected ActionComponentFrame jobDetailsBean = null;

    public JobViewComp(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);

        jobTaskBean = createActionComponentFrame("jobTaskBean");
        jobTaskBean.setNextState(JobTaskViewComp.class, DEFAULT_ACTION);

        jobDetailsText = createTextBean("jobDetailsText");
        jobDetailsText.setValue(JOB_SPEC_VIEW);
        jobDetailsBean = createActionComponentFrame("jobDetailsBean");

        loadJobProfileView(null);

        setDefaultAction("doJobView");
        setDefaultJspPage("/jsp/job/generic/JobViewComp.jsp");
    }

    public void doJobView(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);
        // Get job parameter
        Job job = getJob(parameters);
        if (job == null) {
            messageBox.appendText("No job parameter was provided!");
            messageBox.setMessageType(TextBean.MSG_ERROR);
        } else {
            jobTaskBean.doAction(JobTaskViewComp.class, DEFAULT_ACTION, parameters);
            loadJobProfileView(parameters);
        }
    }

    public void doJobRefresh(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);

        // Get job parameter
        Job job = getJob(parameters);
        if (job == null) {
            messageBox.appendText("No job parameter was provided!");
            messageBox.setMessageType(TextBean.MSG_ERROR);
        } else {
            jobTaskBean.doAction(JobTaskViewComp.class, DEFAULT_ACTION, parameters);
            loadJobProfileView(parameters);
        }

        // Get job parameter
//        if (jobOid == null) {
//            messageBox.appendText("No job parameter was provided!");
//            messageBox.setMessageType(TextBean.MSG_ERROR);
//        } else {
//            Job job = jobSubmissionService.getJob(jobOid);
//            parameters.put(JOB_PARAM, job);
//
//            jobTaskBean.doAction(JobTaskViewComp.class, DEFAULT_ACTION, parameters);
//            loadJobProfileView(parameters);
//        }
    }

    protected void loadJobProfileView(Map parameters) throws PortletException {

        // Get selected view
        String jobProfile = null;
        if (parameters == null) {
            jobProfile = JOB_SPEC_VIEW;
        } else {
            jobProfile = (String)parameters.get("jobProfileParam");
            if (jobProfile == null) jobProfile = jobDetailsText.getValue();
        }

        // Job details text
        jobDetailsText.setValue(jobProfile);

        // Job details component
        Class viewComponentClass = null;
        if (jobProfile.equals(JOB_OUTPUT)) {
            viewComponentClass = JobOutputComp.class;
        } else if (jobProfile.equals(JOB_SPEC_VIEW)) {
            viewComponentClass = GenericJobSpecViewPage.class;
        } else if (jobProfile.equals(JOB_SPEC_STRING)) {
            viewComponentClass = JobSpecStringComp.class;
        } else if (jobProfile.equals(JOB_LOCATION)) {
            viewComponentClass = JobLocationComp.class;
        }
        // If parameters == null, we're in constructor, set default state...
        if (parameters == null) {
            jobDetailsBean.setNextState(viewComponentClass, DEFAULT_ACTION);
        // Otherwise, invoke the appropriate action with the given parameters.
        } else {
            jobDetailsBean.doAction(viewComponentClass, DEFAULT_ACTION, parameters);
        }
    }
}

package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.security.gss.ActiveCredentialFilter;
import org.gridlab.gridsphere.services.job.JobType;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;
import java.util.List;
import java.util.Vector;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobListViewComp.java,v 1.1.1.1 2007-02-01 20:42:04 kherm Exp $
 */

public class JobListViewComp extends JobComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobListViewComp.class);

    public static final String JOB_LIST_VIEW_ACTION = "doJobListView";
    public static final String JOB_LIST_REFRESH_ACTION = "doJobListRefresh";
    public static final String JOB_SUBMIT_NEW_ACTION = "doJobSubmitNew";
    public static final String JOB_LIST_DELETE_ACTION = "doJobListDelete";
    public static final String JOB_VIEW_ACTION = "doJobView";
    protected CheckBoxBean jobOidCheckBox = null;

    public JobListViewComp(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Default action and page
        setDefaultAction(JOB_LIST_VIEW_ACTION);
        setRenderAction(JOB_LIST_VIEW_ACTION);
        setDefaultJspPage("/jsp/job/JobListViewComp.jsp");
    }

    public void onInit() throws PortletException {
        super.onInit();
        // Create new job oid check box
        jobOidCheckBox = createCheckBoxBean("jobOidCheckBox");
        // Register credential actions
        String activeCredentialMethods[] = { RENDER_ACTION, JOB_SUBMIT_NEW_ACTION };
        container.addActionFilter(this,  activeCredentialMethods, ActiveCredentialFilter.getInstance());
    }

    public void onStore() throws PortletException {
        // Get job list
        List jobList = jobSubmissionService.getJobs(getUser(), JobType.INSTANCE);
        setPageAttribute("jobList", jobList);
    }

    public void doJobListView(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);
    }

    public void doJobListRefresh(Map parameters) throws PortletException {
        // Set next state
        setNextState(defaultJspPage);
    }

    public void doJobListDelete(Map parameters) throws PortletException {
        List values = new Vector(jobOidCheckBox.getSelectedValues());
        if (values.size() == 0) {
            messageBox.appendText("Please select which jobs you want to delete");
            messageBox.setMessageType(TextBean.MSG_ERROR);
            setNextState(defaultJspPage);
        } else {
            parameters.put(JOB_OID_LIST_PARAM, values);
            setNextState(JobListDeleteComp.class, DEFAULT_ACTION, parameters);
        }
    }

    public void doJobSubmitNew(Map parameters) throws PortletException {
        // Check if we have any job profiles or not
        setNextState(defaultJspPage);
        List jobComponentTypeList = jobProfileRegistryService.getJobProfiles();
        int numJobComponents = jobComponentTypeList.size();
        if (numJobComponents == 0) {
            messageBox.setMessageType(TextBean.MSG_ERROR);
            messageBox.setValue("There are no job profiles currently available.");
            return;
        }
        // Check if we have any job services or not
        List jobServiceTypeList = jobSubmissionService.getJobTypes();
        int numJobServices = jobServiceTypeList.size();
        if (numJobServices == 0) {
            messageBox.setMessageType(TextBean.MSG_ERROR);
            messageBox.setValue("There are no job submission services currently available.");
            return;
        }
        // Check if we have any job resources
        boolean hasJobResources = false;
        for (int ii = 0; ii < jobServiceTypeList.size(); ++ii) {
            JobType jobServiceType = (JobType)jobServiceTypeList.get(ii);
            List jobResources = jobSubmissionService.getJobResources(jobServiceType, getUser());
            if (jobResources.size() > 0) {
                hasJobResources = true;
                break;
            }
        }
        if (!hasJobResources) {
            messageBox.setMessageType(TextBean.MSG_ERROR);
            messageBox.setValue("There are no job resources currently available.");
            return;
        }
        setNextState(JobSubmitWizard.class, JOB_SUBMIT_NEW_ACTION, parameters);
    }

    public void doJobView(Map parameters) throws PortletException {
        // Check if we have any job profiles or not
        setNextState(defaultJspPage);
        List jobComponentTypeList = jobProfileRegistryService.getJobProfiles();
        int numJobComponents = jobComponentTypeList.size();
        if (numJobComponents == 0) {
            messageBox.setMessageType(TextBean.MSG_ERROR);
            messageBox.setValue("There are no job profiles currently available.");
            return;
        }
        setNextState(JobViewComp.class, JOB_VIEW_ACTION, parameters);
    }
}

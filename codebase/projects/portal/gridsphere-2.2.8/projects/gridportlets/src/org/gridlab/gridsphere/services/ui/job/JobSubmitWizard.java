package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.generic.GenericJobProfile;
import org.gridlab.gridsphere.services.ui.wizard.ActionPageListWizard;
import org.gridlab.gridsphere.services.job.*;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import java.util.Map;
import java.util.List;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobSubmitWizard.java,v 1.1.1.1 2007-02-01 20:42:05 kherm Exp $
 */

public class JobSubmitWizard extends ActionPageListWizard {

    private transient static PortletLog log = SportletLog.getInstance(JobSubmitWizard.class);

    public static final String JOB_NEW_ACTION = "doJobSubmitNew";
    public static final String JOB_STAGE_ACTION = "doJobSubmitStage";

    // Portlet beans
    protected TextBean jobTypeText = null;

    // Portlet services
    protected JobSubmissionService jobSubmissionService = null;
    protected JobProfileRegistryService jobProfileRegistryService = null;
    protected JobProfile jobProfile = GenericJobProfile.INSTANCE;
    protected JobSpec jobSpec = null;
    private boolean copyFlag = false;

    public JobSubmitWizard(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        // Get our services
        jobSubmissionService = (JobSubmissionService)
                getPortletService(JobSubmissionService.class);
        jobProfileRegistryService = (JobProfileRegistryService)
                getPortletService(JobProfileRegistryService.class);
        // Setup portlet beans
        jobTypeText = createTextBean("jobTypeText");
        // Setup job submit pages
        newJobSubmitPages(false);
        // Set default action and page...
        setDefaultAction("doJobSubmit");
        setDefaultJspPage("/jsp/job/JobSubmitWizard.jsp");
    }

    public ActionComponentFrame createActionComponentFrame(String beanId) {
        log.debug("Create job component widget with bean id " + beanId);
        JobComponentFrame bean = new JobComponentFrame(this, beanId);
        actionComponentBeans.put(beanId, bean);
        return bean;
    }

    private void newJobSubmitPages(boolean copyFlag) throws PortletException {
        // Clear current set of pages
        clearPages();
        // Get number of job profiles
        List jobProfileList = jobProfileRegistryService.getJobProfiles();
        int numJobProfiles = jobProfileList.size();
        // Get number of job services
        List jobServiceTypeList = jobSubmissionService.getJobTypes();
        int numJobServices = jobServiceTypeList.size();
        // Determine whether to add page for selecting job type / job component
        if (numJobProfiles > 1 || numJobServices > 1) {
            // Add job type edit page
            addPage(JobTypeEditPage.class);
            // Add job spec pags
            addJobSpecEditPages();
            // Set page to first job spec edit page if staging
            if (pageList.size() > 1 && copyFlag) {
                pageBean.setNextState((Class)pageList.get(1));
            } else {
                // Otherwise set to job type edit page
                pageBean.setNextState((Class)pageList.get(0));
            }
        } else {
            if (numJobProfiles == 1) {
                jobProfile = (JobProfile)jobProfileRegistryService.getJobProfiles().get(0);
            }
            // Add job spec pags
            addJobSpecEditPages();
            // Set page to first job spec edit page
            pageBean.setNextState((Class)pageList.get(0));
        }
    }

    private void addJobSpecEditPages() {
        log.debug("Job component type is " + jobProfile.getKey());
        List jobSpecEditPageClassList = jobProfile.getJobSpecEditPageClassList();
        for (int ii = 0; ii < jobSpecEditPageClassList.size(); ++ii) {
            Class jobSpecEditPageClass = (Class)jobSpecEditPageClassList.get(ii);
            log.debug("Adding job spec edit page " + jobSpecEditPageClass.getName());
            addPage(jobSpecEditPageClass);
        }
    }

    private JobSpec createJobSpec(JobType jobType) throws PortletException {
        // Create job spec
        try {
            JobSpec jobSpec = null;
            if (copyFlag) {
                jobSpec = jobSubmissionService.createJobSpec(jobType, this.jobSpec);
            } else {
                jobSpec = jobSubmissionService.createJobSpec(jobType);
            }
            jobSpec.setUser(getUser());
            JobComponent.setJobProfile(jobSpec, jobProfile);
            return jobSpec;
        } catch (JobException e) {
            messageBox.setValue("Unable to create job spec)");
            log.error("Unable to create job spec", e);
            throw new PortletException(e);
        }
    }

    public void doJobSubmit(Map parameters) throws PortletException {
        // Set submit and cancel state...
        setSubmitState(JobViewComp.class, JobViewComp.DEFAULT_ACTION, parameters);
        setCancelState(JobListViewComp.class, JobListViewComp.DEFAULT_ACTION, parameters);
        // Do wizard....
        doWizard(parameters);
        // Set next state
        setNextState(defaultJspPage);
    }

    public void doJobSubmitNew(Map parameters) throws PortletException {
        log.error("doJobSubmitNew()...");
        // Get job parameters...
        Job job = JobComponent.getJob(jobSubmissionService, parameters);
        // New job pages
        copyFlag = false;
        newJobSubmitPages(false);
        // Create job spec and set parameters
        jobSpec = createJobSpec(JobType.INSTANCE);
        parameters.put(JobComponent.JOB_SPEC_PARAM, jobSpec);
        // Set submit and cancel state...
        setSubmitState(JobViewComp.class, JobViewComp.DEFAULT_ACTION, parameters);
        if (job == null) {
            setCancelState(JobListViewComp.class, JobListViewComp.DEFAULT_ACTION, parameters);
        } else {
            setCancelState(JobViewComp.class, JobViewComp.DEFAULT_ACTION, parameters);
        }
        // Do wizard....
        doWizard(parameters);
        // Set next state
        setNextState(defaultJspPage);
    }

    public void doJobSubmitStage(Map parameters) throws PortletException {
        // Get job parameters...
        Job job = JobComponent.getJob(jobSubmissionService, parameters);
        jobProfile = JobComponent.getJobProfile(job);
        try {
            jobSpec = jobSubmissionService.createJobSpec(job.getJobSpec());
        } catch (JobException e) {
            log.error("Unable to create job spec", e);
            throw new PortletException(e);
        }
        parameters.put(JobComponent.JOB_SPEC_PARAM, jobSpec);
        // Stage job pages
        copyFlag = true;
        newJobSubmitPages(copyFlag);
        // Set submit and cancel state...
        setSubmitState(JobViewComp.class, JobViewComp.DEFAULT_ACTION, parameters);
        setCancelState(JobViewComp.class, JobViewComp.DEFAULT_ACTION, parameters);
        // Do wizard....
        doWizard(parameters);
        // Set next state
        setNextState(defaultJspPage);
    }

    public void setPreviousPageParameters(int pageNumber, Map parameters)
            throws PortletException {
        jobTypeText.setValue("");
        log.debug("Setting job component type parameter " + jobProfile.getKey());
        parameters.put(JobComponent.JOB_COMP_PARAM, jobProfile);
        if (pageNumber != 0 && jobSpec != null) {
            parameters.put(JobComponent.JOB_SPEC_PARAM, jobSpec);
            parameters.put(JobComponent.JOB_TYPE_PARAM, jobSpec.getJobType());
            jobTypeText.setValue(jobSpec.getJobType().getLabel(locale));
            log.debug("Previous page job spec of type " + jobSpec.getJobType().getClassName());
        }
    }

    public void setCurrentPageParameters(int pageNumber, Map parameters)
            throws PortletException {
        jobTypeText.setValue("");
        if  ( ! (currentPage instanceof JobTypeEditPage) ) {
            log.debug("Setting job component type parameter " + jobProfile.getKey());
            parameters.put(JobComponent.JOB_COMP_PARAM, jobProfile);
            if (jobSpec != null) {
                parameters.put(JobComponent.JOB_SPEC_PARAM, jobSpec);
                parameters.put(JobComponent.JOB_TYPE_PARAM, jobSpec.getJobType());
                jobTypeText.setValue(jobSpec.getJobType().getLabel(locale));
                log.debug("Current page job spec of type " + jobSpec.getJobType().getClassName());
            }
        }
    }

    public void setNextPageParameters(int pageNumber, Map parameters)
            throws PortletException {
        jobTypeText.setValue("");
        if (currentPage instanceof JobTypeEditPage) {
            JobTypeEditPage jobTypeEditPage = (JobTypeEditPage)currentPage;
            // Get job component type
            JobProfile newProfile = jobTypeEditPage.getSelectedJobProfile();
            if (!newProfile.equals(jobProfile)) {
                log.error("New job component type selected " + newProfile.getDescription());
                jobProfile = newProfile;
                newJobSubmitPages(false);
            }
            // Get job submission type
            JobType jobType = jobTypeEditPage.getSelectedJobServiceType();
            // Create appropriate job spec...
            jobSpec = createJobSpec(jobType);
        }
        log.debug("Setting job component type parameter " + jobProfile.getKey());
        parameters.put(JobComponent.JOB_COMP_PARAM, jobProfile);
        if (jobSpec != null) {
            parameters.put(JobComponent.JOB_SPEC_PARAM, jobSpec);
            parameters.put(JobComponent.JOB_TYPE_PARAM, jobSpec.getJobType());
            jobTypeText.setValue(jobSpec.getJobType().getLabel(locale));
            log.debug("Setting job spec parameter " + jobSpec.getDescription());
            log.debug("Setting job service type parameter " + jobSpec.getJobType().getClassName());
        }
    }

    public boolean doSubmitPages(Map parameters) throws PortletException {
        boolean success = false;
        try {
            // Create new job spec from ours...
            JobSpec newJobSpec = jobSubmissionService.createJobSpec(jobSpec);
            // Submit job
            Job job = jobSubmissionService.submitJob(newJobSpec);
            // Pass parameters...
            parameters.put(MESSAGE_BOX_TEXT_PARAM, "This job is being submitted...");
            parameters.put(MESSAGE_BOX_TYPE_PARAM, TextBean.MSG_SUCCESS);
            parameters.put(JobComponent.JOB_OID_PARAM, job.getOid());
            // Success!
            success = true;
        } catch(JobException e) {
            log.error("Unable to submit job", e);
            // Display error messsage
            StringBuffer errorBuffer = new StringBuffer();
            errorBuffer.append("Unable to submit job.<br>");
            errorBuffer.append(e.getMessage());
            errorBuffer.append("<br>");
            messageBox.appendText(errorBuffer.toString());
            messageBox.setMessageType(TextBean.MSG_ERROR);
        }
        return success;
    }
}

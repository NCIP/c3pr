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
 * @version $Id: JobMigrateWizard.java,v 1.1.1.1 2007-02-01 20:42:04 kherm Exp $
 */

public class JobMigrateWizard extends ActionPageListWizard {

    private transient static PortletLog log = SportletLog.getInstance(JobSubmitWizard.class);

    // Portlet services
    protected JobSubmissionService jobSubmissionService = null;
    protected JobMigrationService jobMigrationService = null;
    protected JobProfileRegistryService jobProfileRegistryService = null;
    protected JobProfile jobProfile = GenericJobProfile.INSTANCE;
    // Migration attributes
    protected String jobOid = null;
    protected JobSpec jobSpec = null;

    // Portlet beans
    protected TextBean jobTypeText = null;

    public JobMigrateWizard(ActionComponentFrame container, String compName)
            throws PortletException {
        super(container, compName);
        // Setup portlet beans
        jobTypeText = createTextBean("jobTypeText");
        // Setup job submit pages
        setupJobMigratePages();
        // Set default action and page...
        setDefaultAction("doJobMigrate");
        setDefaultJspPage("/jsp/job/JobMigrationWizard.jsp");
    }

    public ActionComponentFrame createActionComponentFrame(String beanId) {
        log.debug("Create job component widget with bean id " + beanId);
        JobComponentFrame bean = new JobComponentFrame(this, beanId);
        actionComponentBeans.put(beanId, bean);
        return bean;
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        jobSubmissionService = (JobSubmissionService)
                getPortletService(JobSubmissionService.class);
        jobMigrationService = (JobMigrationService)
                getPortletService(JobMigrationService.class);
    }

    private void setupJobMigratePages() throws PortletException {
        log.debug("Job component type is " + jobProfile.getKey());
        clearPages();
        jobSubmissionService = (JobSubmissionService)
                getPortletService(JobSubmissionService.class);
        jobProfileRegistryService = (JobProfileRegistryService)
                getPortletService(JobProfileRegistryService.class);
        List jobSpecEditPageClassList = jobProfile.getJobSpecEditPageClassList();
        for (int ii = 0; ii < jobSpecEditPageClassList.size(); ++ii) {
            Class jobSpecEditPageClass = (Class)jobSpecEditPageClassList.get(ii);
            log.debug("Adding job spec edit page " + jobSpecEditPageClass.getName());
            addPage(jobSpecEditPageClass);
        }
        pageBean.setNextState((Class)pageList.get(0));
    }

    public void doJobMigrate(Map parameters) throws PortletException {
        log.debug("doJobMigrate");
        // Set submit and cancel state...
        setSubmitState(JobViewComp.class, JobViewComp.DEFAULT_ACTION, parameters);
        setCancelState(JobViewComp.class, JobViewComp.DEFAULT_ACTION, parameters);
        // Get job parameters...
        Job job = JobComponent.getJob(jobSubmissionService, parameters);
        jobProfile = JobComponent.getJobProfile(job);
        jobOid = job.getOid();
        try {
            jobSpec = jobSubmissionService.createJobSpec(job.getJobSpec());
        } catch (JobException e) {
            log.error("Unable to create job spec", e);
            throw new PortletException(e);
        }
        jobTypeText.setValue(jobSpec.getJobType().getLabel(locale));
        parameters.put(JobComponent.JOB_SPEC_PARAM, jobSpec);
        log.debug("doJobMigration->doWizard");
        // Do wizard....
        doWizard(parameters);
        log.debug("doJobMigration->setNextState");
        // Set next state
        setNextState(defaultJspPage);
    }

    public void setPreviousPageParameters(int pageNumber, Map parameters)
            throws PortletException {
        jobTypeText.setValue("");
        parameters.put(JobComponent.JOB_SPEC_PARAM, jobSpec);
    }

    public void setCurrentPageParameters(int pageNumber, Map parameters)
            throws PortletException {
        jobTypeText.setValue("");
        parameters.put(JobComponent.JOB_SPEC_PARAM, jobSpec);
    }

    public void setNextPageParameters(int pageNumber, Map parameters)
            throws PortletException {
        jobTypeText.setValue("");
        parameters.put(JobComponent.JOB_SPEC_PARAM, jobSpec);
    }

    public boolean doSubmitPages(Map parameters) throws PortletException {
        boolean success = false;
        try {
            // Create migration spec
            JobMigrationSpec migrationSpec
                    = jobMigrationService.createJobMigrationSpec(JobMigrationType.INSTANCE);
            migrationSpec.setUser(getUser());
            Job job = jobSubmissionService.getJob(jobOid);
            JobSpec newJobSpec = jobSubmissionService.createJobSpec(jobSpec);
            migrationSpec.setJobId(job.getJobId());
            migrationSpec.setJobSpec(newJobSpec);
            // Migrate job
            jobMigrationService.submitJobMigration(migrationSpec);
            // Pass parameters...
            parameters.put(MESSAGE_BOX_TEXT_PARAM, "This job is being migrated...");
            parameters.put(MESSAGE_BOX_TYPE_PARAM, TextBean.MSG_SUCCESS);
            parameters.put(JobComponent.JOB_OID_PARAM, jobOid);
            // Success!
            success = true;
        } catch(JobException e) {
            log.error("Unable to migrate job", e);
            // Display error messsage
            StringBuffer errorBuffer = new StringBuffer();
            errorBuffer.append("Unable to migrate job.<br>");
            errorBuffer.append(e.getMessage());
            errorBuffer.append("<br>");
            messageBox.appendText(errorBuffer.toString());
            messageBox.setMessageType(TextBean.MSG_ERROR);
        }
        return success;
    }
}

package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.job.generic.GenericJobProfile;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.services.job.*;

import javax.portlet.PortletException;
import java.util.Map;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobComponent.java,v 1.1.1.1 2007-02-01 20:42:04 kherm Exp $
 */

public class JobComponent extends ActionComponent {

    private transient static PortletLog log = SportletLog.getInstance(JobComponent.class);

    public final static String JOB_PARAM = "jobParam";
    public final static String JOB_OID_PARAM = "jobOidParam";
    public final static String JOB_TYPE_PARAM = "jobTypeParam";
    public final static String JOB_COMP_PARAM = "jobCompParam";
    public final static String JOB_SPEC_PARAM = "jobSpecParam";
    public final static String JOB_SPEC_OID_PARAM = "jobSpecOidParam";
    public final static String JOB_OID_LIST_PARAM = "jobOidListParam";

    // Portlet services
    protected JobProfileRegistryService jobProfileRegistryService = null;
    protected JobSubmissionService jobSubmissionService = null;
    protected JobMigrationService jobMigrationService = null;
    protected FileBrowserService fileBrowserService = null;

    protected String jobOid = null;
    protected JobType jobType = JobType.INSTANCE;

    public JobComponent(ActionComponentFrame container, String compName) {
        super(container, compName);
        log.debug("JobSubmissionComp()");
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        jobProfileRegistryService = (JobProfileRegistryService)
                getPortletService(JobProfileRegistryService.class);
        fileBrowserService = (FileBrowserService)
                getPortletService(FileBrowserService.class);
        jobSubmissionService = (JobSubmissionService)
                getPortletService(JobSubmissionService.class);
        jobMigrationService = (JobMigrationService)
                getPortletService(JobMigrationService.class);
    }

    public void onStore() throws PortletException {
        setPageAttribute("jobOid", jobOid);
        setPageAttribute("jobType", jobType);
    }

    public ActionComponentFrame createActionComponentFrame(String beanId) {
        JobComponentFrame bean = new JobComponentFrame(this, beanId);
        actionComponentBeans.put(beanId, bean);
        return bean;
    }

    public Job getJob(Map parameters) {
        Job job = getJob(jobSubmissionService, parameters);
        if (job != null) {
            // Save job oid and job type for use in jsps
            jobOid = job.getOid();
            jobType = job.getJobType();
//            log.debug("DEBUG jobOid = " + jobOid);
        }
        return job;
    }

    public static Job getJob(JobSubmissionService jobSubmissionService, Map parameters) {
        // Get job parameter
        Job job = (Job)parameters.get(JOB_PARAM);
        if (job == null) {
//            log.debug("No job param in parameters");
            // Get job oid parameter...
            String jobOid = (String)parameters.get(JOB_OID_PARAM);
            if (jobOid == null) {
//                log.error("No job oid param in parameters");
            } else {
                job = jobSubmissionService.getJob(jobOid);
                parameters.put(JOB_PARAM, job);
            }
        }
        return job;
    }

    public JobSpec getJobSpec(Map parameters) {
        JobSpec jobSpec = getJobSpec(jobSubmissionService, parameters);
        if (jobSpec != null) {
            // Save job type for use in jsps
            jobType = jobSpec.getJobType();
        }
        return jobSpec;
    }

    public static JobSpec getJobSpec(JobSubmissionService jobSubmissionService, Map parameters) {
        // Get job spec parameter
        JobSpec jobSpec = (JobSpec)parameters.get(JOB_SPEC_PARAM);
        // If job spec is null...
        if (jobSpec == null) {
//            log.debug("No job spec param in parameters");
            // Get job spec parameter
            String jobSpecOid = (String)parameters.get(JOB_SPEC_OID_PARAM);
            // If job spec oid is null...
            if (jobSpecOid == null) {
//                log.debug("No job spec param in parameters");
                // Get job parameter
                Job job = (Job)parameters.get(JOB_PARAM);
                if (job == null) {
//                    log.debug("No job param in parameters");
                    // Get job oid parameter...
                    String jobOid = (String)parameters.get(JOB_OID_PARAM);
                    if (jobOid != null) {
//                        log.debug("No job oid param in parameters");
                        job = jobSubmissionService.getJob(jobOid);
                        jobSpec = job.getJobSpec();
                    }
                } else {
                    jobSpec = job.getJobSpec();
                    parameters.put(JOB_SPEC_PARAM, jobSpec);
                }
            } else {
                // No get job spec by oid yet....
            }
        }
        return jobSpec;
    }

    public JobType getJobType(Map parameters) {
        // Save job type for use in jsps
        jobType = getJobType(jobSubmissionService, parameters);
        return jobType;
    }

    public static JobType getJobType(JobSubmissionService jobSubmissionService, Map parameters) {
        // Get job type parameter
        JobType jobType = (JobType)parameters.get(JOB_TYPE_PARAM);
        // If job spec is null...
        if (jobType == null) {
//            log.debug("No job type param in parameters");
            Job job = getJob(jobSubmissionService, parameters);
            if (job == null) {
//                log.debug("No job param in parameters");
                JobSpec jobSpec = (JobSpec)parameters.get(JOB_SPEC_PARAM);
                if (jobSpec == null) {
//                    log.debug("No job spec param in parameters, using default job type");
                    jobType = JobType.INSTANCE;
                } else {
                    jobType = jobSpec.getJobType();
                }
            } else {
                jobType = job.getJobType();
            }
        }
        return jobType;
    }

    public JobProfile getJobProfile(Map parameters) {
        return getJobProfile(jobSubmissionService, parameters);
    }

    public static JobProfile getJobProfile(JobSubmissionService jobSubmissionService, Map parameters) {
        // Get job type parameter
        JobProfile jobProfile = (JobProfile)parameters.get(JOB_COMP_PARAM);
        // If job spec is null...
        if (jobProfile == null) {
//            log.debug("No job type param in parameters");
            Job job = getJob(jobSubmissionService, parameters);
            if (job == null) {
//                log.debug("No job param in parameters");
                JobSpec jobSpec = (JobSpec)parameters.get(JOB_SPEC_PARAM);
                if (jobSpec == null) {
//                    log.debug("No job spec param in parameters, using default job component type");
                    jobProfile = GenericJobProfile.INSTANCE;
                } else {
                    // Get job component type
                    jobProfile = getJobProfile(jobSpec);
                }
            } else {
                // Get job component type
                jobProfile = getJobProfile(job);
            }
        }
        return jobProfile;
    }

    public static JobProfile getJobProfile(Job job) {
        JobProfile type = null;
        String className = job.getTaskAttributeValue(JobProfile.class.getName());
        if (className != null) {
            try {
                PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
                JobProfileRegistryService jobProfileTypeRegistryService = (JobProfileRegistryService)
                        serviceFactory.createPortletService(JobProfileRegistryService.class, null, true);
                type = jobProfileTypeRegistryService.getJobProfile(className);
            } catch (PortletServiceException e) {
                log.error("Unable to get job component type registry", e);
            }
        }
        if (type == null) {
            return GenericJobProfile.INSTANCE;
        }
        return type;
    }

    public static JobProfile getJobProfile(JobSpec jobSpec) {
        JobProfile profile = null;
        String className = jobSpec.getTaskAttributeValue(JobProfile.class.getName());
        if (className != null) {
            try {
                PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
                JobProfileRegistryService jobProfileRegistryService = (JobProfileRegistryService)
                        serviceFactory.createPortletService(JobProfileRegistryService.class, null, true);
                profile = jobProfileRegistryService.getJobProfile(className);
            } catch (PortletServiceException e) {
                log.error("Unable to get job component type registry", e);
            }
        }
        if (profile == null) {
            return GenericJobProfile.INSTANCE;
        }

        return profile;
    }

    public static void setJobProfile(JobSpec jobSpec, JobProfile type) {
        jobSpec.putTaskAttribute(JobProfile.class.getName(), type.getKey());
    }
}

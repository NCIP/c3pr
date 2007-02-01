package org.gridlab.gridsphere.services.job.impl;

import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.task.TaskException;
import org.gridlab.gridsphere.services.task.impl.SimpleTaskSubmissionBroker;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import java.util.List;
import java.util.Vector;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseJobSubmissionBroker.java,v 1.1.1.1 2007-02-01 20:40:48 kherm Exp $
 * <p>
 * Base job submission broker determines which job submission service
 * to use to create job spec's and submit jobs.
 */

public class BaseJobSubmissionBroker
        extends SimpleTaskSubmissionBroker
        implements JobSubmissionService {

    protected static PortletLog log = SportletLog.getInstance(BaseJobSubmissionBroker.class);

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        // Register our service
        log.debug("Registering job submission broker");
        super.init(config);
    }

    public void destroy() {
        // Register our service
        log.debug("Unregistering job submission broker");
        super.destroy();
    }

    public List getTaskTypes() {
        List types = new Vector(1);
        types.add(JobType.INSTANCE);
        return types;
    }

    public List getJobTypes() {
        return getBrokeredTaskTypes();
    }

    public List getJobResources(JobType type) {
        // Translate to a default type if this is our base type
        if (JobType.INSTANCE.equals(type)) {
            try {
                type = (JobType)getDefaultTaskType(type);
            } catch (TaskException e) {
                return new Vector(0);
            }
        }
        // Get the service for the given job type
        JobSubmissionService service
                = (JobSubmissionService)registry.getTaskSubmissionService(type);
        if (service == null) {
            return new Vector(0);
        }
        return service.getJobResources(type);
    }

    public List getJobResources(JobType type, User user) {
        // Translate to a default type if this is our base type
        if (JobType.INSTANCE.equals(type)) {
            try {
                type = (JobType)getDefaultTaskType(type);
            } catch (TaskException e) {
                return new Vector(0);
            }
        }
        // Get the service for the given job type
        JobSubmissionService service
                = (JobSubmissionService)registry.getTaskSubmissionService(type);
        if (service == null) {
            return new Vector(0);
        }
        return service.getJobResources(type, user);
    }

    public JobSpec createJobSpec(JobType type) throws JobException {
        try {
            return (JobSpec) createTaskSpec(type);
        } catch (TaskException e) {
            throw new JobException(e.getMessage());
        }
    }

    public JobSpec createJobSpec(JobSpec spec) throws JobException {
        try {
            return (JobSpec) createTaskSpec(spec);
        } catch (TaskException e) {
            throw new JobException(e.getMessage());
        }
    }

    public JobSpec createJobSpec(JobType type, JobSpec spec) throws JobException {
        try {
            return (JobSpec) createTaskSpec(type, spec);
        } catch (TaskException e) {
            throw new JobException(e.getMessage());
        }
    }

    public Job submitJob(JobSpec spec) throws JobException {
        try {
            return (Job) submitTask(spec);
        } catch (TaskException e) {
            throw new JobException(e.getMessage());
        }
    }

    public void cancelJob(Job job) throws JobException {
        try {
            cancelTask(job);
        } catch (TaskException e) {
            throw new JobException(e.getMessage());
        }
    }

    public List getJobs(JobType type) {
        return getTasks(type);
    }

    public List getJobs(User user, JobType type) {
        return getTasks(user, type);
    }

    public Job getJob(String id) {
        return (Job)getTask(id);
    }

    public Job getJobByJobId(String jobId) {
        Job job = null;
        try {
            job = (Job) pm.restore("from " + Job.class.getName()
                                         + " as r where r.JobId='" + jobId + "'");
        } catch (PersistenceManagerException e) {
            log.error("FATAL: could not retrieve job by job id " + jobId + "!\n" + e.getMessage(), e);
        }
        return job;
    }

    public void saveJob(Job job) {
        saveTask(job);
    }

    public void deleteJob(Job job) {
        deleteTask(job);
    }
}

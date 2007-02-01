package org.gridlab.gridsphere.services.job.impl;

import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.task.Task;
import org.gridlab.gridsphere.services.task.TaskException;
import org.gridlab.gridsphere.services.task.TaskSpec;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.task.impl.AbstractTaskSubmissionService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: AbstractJobSubmissionService.java,v 1.1.1.1 2007-02-01 20:40:45 kherm Exp $
 * <p>
 * Abstract implementation of job submission service. It extends
 * abstract request submission service so that it's functionality
 * is incorporated into the request submission system.
 */

public abstract class AbstractJobSubmissionService
        extends AbstractTaskSubmissionService
        implements JobSubmissionService {

    protected static PortletLog log = SportletLog.getInstance(AbstractJobSubmissionService.class);

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        super.init(config);
        PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
        BaseJobSubmissionBroker broker = null;
        try {
            log.info("Getting instance of job submission broker");
            broker  = (BaseJobSubmissionBroker)
                    serviceFactory.createPortletService(JobSubmissionService.class, null, true);
            log.info("Registering task types for " + getClass().getName());
            broker.addBrokeredTaskTypes(this);
        } catch (PortletServiceNotFoundException e) {
            log.error("Job submission broker not found", e);
        }
    }
 
    public List getTaskTypes() {
        return getJobTypes();
    }

    public TaskSpec createTaskSpec(TaskType type) throws TaskException {
        return createJobSpec((JobType)type);
    }

    public TaskSpec createTaskSpec(TaskSpec spec) throws TaskException {
        return createJobSpec((JobSpec) spec);
    }

    public TaskSpec createTaskSpec(TaskType type, TaskSpec spec) throws TaskException {
        return createJobSpec((JobSpec) spec);
    }

    public Task submitTask(TaskSpec spec) throws TaskException {
        return submitJob((JobSpec) spec);
    }

    public void cancelTask(Task request) throws TaskException {
        cancelJob((Job) request);
    }

    public abstract List getJobTypes();

    public abstract JobSpec createJobSpec(JobType type) throws JobException;

    public abstract JobSpec createJobSpec(JobSpec spec) throws JobException;

    public JobSpec createJobSpec(JobType type, JobSpec spec) throws JobException {
        return createJobSpec(spec);
    }

    public abstract Job submitJob(JobSpec spec) throws JobException;

    public abstract void cancelJob(Job job) throws JobException;

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
            job = (Job) pm.restore("from " + BaseJob.class.getName()
                                         + " as r where r.JobId='" + jobId + "'");
        } catch (PersistenceManagerException e) {
            log.error("FATAL: could not retrieve job by job id " + jobId + "!\n", e);
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

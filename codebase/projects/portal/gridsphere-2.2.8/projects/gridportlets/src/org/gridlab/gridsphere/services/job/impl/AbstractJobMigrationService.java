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

import java.util.Iterator;
import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: AbstractJobMigrationService.java,v 1.1.1.1 2007-02-01 20:40:45 kherm Exp $
 * <p>
 * Abstract implementation of job migration service. It extends
 * abstract request submission service so that it's functionality
 * is incorporated into the request submission system.
 */

public abstract class AbstractJobMigrationService
        extends AbstractTaskSubmissionService
        implements JobMigrationService {

    protected static PortletLog log = SportletLog.getInstance(AbstractJobMigrationService.class);

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        super.init(config);
        PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
        BaseJobMigrationBroker broker = null;
        try {
            log.info("Getting instance of job migration broker");
            broker  = (BaseJobMigrationBroker)
                    serviceFactory.createPortletService(JobMigrationService.class, null, true);
            log.info("Registering task types for " + getClass().getName());
            broker.addBrokeredTaskTypes(this);
        } catch (PortletServiceNotFoundException e) {
            log.error("Job submission broker not found", e);
        }
    }
 
    public List getTaskTypes() {
        return getJobMigrationTypes();
    }

    public TaskSpec createTaskSpec(TaskType type) throws TaskException {
        return createJobMigrationSpec((JobMigrationType)type);
    }

    public TaskSpec createTaskSpec(TaskSpec spec) throws TaskException {
        return createJobMigrationSpec((JobMigrationSpec) spec);
    }

    public Task submitTask(TaskSpec spec) throws TaskException {
        return submitJobMigration((JobMigrationSpec) spec);
    }

    public void cancelTask(Task request) throws TaskException {
        cancelJobMigration((JobMigration) request);
    }

    public abstract List getSupportedJobTypes();

    public boolean supportsJobType(Class type) {
        Iterator types = getSupportedJobTypes().iterator();
        while (types.hasNext()) {
            JobType nextType = (JobType)types.next();
            if (nextType.getClass().equals(type)) {
                return true;     
            }
        }
        return false;
        
    }

    public abstract List getJobMigrationTypes();

    public abstract JobMigrationSpec createJobMigrationSpec(JobMigrationType type) throws JobException;

    public abstract JobMigrationSpec createJobMigrationSpec(JobMigrationSpec spec) throws JobException;

    public abstract JobMigration submitJobMigration(JobMigrationSpec spec) throws JobException;

    public abstract void cancelJobMigration(JobMigration jobMigrartion) throws JobException;

    public List getJobMigrations(JobMigrationType type) {
        return getTasks(type);
    }

    public List getJobMigrations(User user, JobMigrationType type) {
        return getTasks(user, type);
    }

    public JobMigration getJobMigration(String id) {
        return (JobMigration)getTask(id);
    }

    public void saveJobMigration(JobMigration job) {
        saveTask(job);
    }

    public void deleteJobMigration(JobMigration job) {
        deleteTask(job);
    }
}

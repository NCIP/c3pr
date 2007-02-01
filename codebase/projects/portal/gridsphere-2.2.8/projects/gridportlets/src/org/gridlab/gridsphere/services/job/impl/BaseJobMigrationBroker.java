package org.gridlab.gridsphere.services.job.impl;

import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.task.impl.SimpleTaskSubmissionBroker;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Vector;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseJobMigrationBroker.java,v 1.1.1.1 2007-02-01 20:40:45 kherm Exp $
 * <p>
 * Base job submission broker determines which job submission service
 * to use to create job spec's and submit jobs.
 */

public class BaseJobMigrationBroker
        extends SimpleTaskSubmissionBroker
        implements JobMigrationService {

    protected static PortletLog log = SportletLog.getInstance(BaseJobMigrationBroker.class);

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        // Register our service
        log.debug("Registering job migration broker");
        super.init(config);
    }

    public void destroy() {
        // Register our service
        log.debug("Unregistering job migration broker");
        super.destroy();
    }

    public List getTaskTypes() {
        List types = new Vector(1);
        types.add(JobMigrationType.INSTANCE);
        return types;
    }

    public List getJobMigrationTypes() {
        return getBrokeredTaskTypes();
    }

    public JobMigrationSpec createJobMigrationSpec(JobMigrationType type) throws JobException {
        try {
            return (JobMigrationSpec) createTaskSpec(type);
        } catch (org.gridlab.gridsphere.services.task.TaskException e) {
            throw new JobException(e.getMessage());
        }
    }

    public JobMigrationSpec createJobMigrationSpec(JobMigrationSpec spec) throws JobException {
        try {
            return (JobMigrationSpec) createTaskSpec(spec);
        } catch (org.gridlab.gridsphere.services.task.TaskException e) {
            throw new JobException(e.getMessage());
        }
    }

    public JobMigration submitJobMigration(JobMigrationSpec spec) throws JobException {
        try {
            return (JobMigration) submitTask(spec);
        } catch (org.gridlab.gridsphere.services.task.TaskException e) {
            throw new JobException(e.getMessage());
        }
    }

    public void cancelJobMigration(JobMigration job) throws JobException {
        try {
            cancelTask(job);
        } catch (org.gridlab.gridsphere.services.task.TaskException e) {
            throw new JobException(e.getMessage());
        }
    }

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

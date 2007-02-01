package org.gridlab.gridsphere.services.job.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.job.JobMigration;
import org.gridlab.gridsphere.services.job.JobMigrationType;
import org.gridlab.gridsphere.services.task.impl.BaseTask;
import org.gridlab.gridsphere.services.task.TaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseJobMigration.java,v 1.1.1.1 2007-02-01 20:40:45 kherm Exp $
 * <p>
 * Provides base implementation for jobs
 */

public class BaseJobMigration
        extends BaseTask
        implements JobMigration {

    protected static PortletLog log = SportletLog.getInstance(BaseJobMigration.class);

    /**
     * Default constructor
     */
    public BaseJobMigration() {
        super();
    }

    /**
     * Constructs a job migration with the given spec
     * @param spec The job migration spec
     */
    public BaseJobMigration(BaseJobMigrationSpec spec) {
        super(spec);
    }

    public TaskType getTaskType() {
        return JobMigrationType.INSTANCE;
    }

    public JobMigrationType getJobMigrationType() {
        return JobMigrationType.INSTANCE;
    }
}

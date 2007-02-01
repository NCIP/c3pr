package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.job.impl.BaseJob;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobType.java,v 1.1.1.1 2007-02-01 20:40:43 kherm Exp $
 * <p>
 * Describes a job type.
 */

public class JobType extends TaskType {

    public static final JobType INSTANCE
            = new JobType(Job.class, JobSpec.class, BaseJob.class);

    protected JobType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }

    protected JobType(Class taskClass, Class taskSpecClass, Class taskClassImpl) {
        super(taskClass, taskSpecClass, taskClassImpl);
    }

    /**
     * Returns true if the service for this job type supports setting
     * the directory in which the job will run, false otherwise.
     * All sub job types should override this as required.
     * @return True if supports directories, false otherwise
     */
    public boolean getSupportsDirectory() {
        return true;
    }

    /**
     * Returns true if the service for this job type supports check
     * pointing, false otherwise. All sub job types should override
     * this as required.
     * @return True if supports checkpointing, false otherwise
     */
    public boolean getSupportsCheckPointing() {
        return false;
    }

    /**
     * Returns true if the service for this job type supports resource
     * brokering, false otherwise. All sub job types should override
     * this as required.
     * @return True if supports brokering, false otherwise
     */
    public boolean getSupportsResourceBrokering() {
        return false;
    }

    /**
     * Returns true if the service for this job type supports job
     * migration, false otherwise. All sub job types should override
     * this as required.
     * @return True if supports migration, false otherwise
     */
    public boolean getSupportsJobMigration() {
        return false;
    }
}

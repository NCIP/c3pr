package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.task.TaskType;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobMigrationType.java,v 1.1.1.1 2007-02-01 20:40:40 kherm Exp $
 * <p>
 * Describes a job migration type.
 */

public class JobMigrationType extends TaskType {

    public static final JobMigrationType INSTANCE = new JobMigrationType(JobMigration.class, JobMigrationSpec.class);

    protected JobMigrationType(Class taskClass, Class taskSpecClass) {
        super(taskClass, taskSpecClass);
    }
}

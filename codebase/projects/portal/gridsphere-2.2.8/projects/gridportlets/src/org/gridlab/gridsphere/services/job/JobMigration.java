package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.task.Task;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobMigration.java,v 1.1.1.1 2007-02-01 20:40:40 kherm Exp $
 * <p>
 * Describes a job migration request.
 */

public interface JobMigration extends Task {
 
    /**
     * Returns the type of job migration this is.
     * @return The job migration type
     */
    public JobMigrationType getJobMigrationType();
}

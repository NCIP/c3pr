package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.task.TaskSpec;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobMigrationSpec.java,v 1.1.1.1 2007-02-01 20:40:40 kherm Exp $
 * <p>
 * Specifies a "job migration".
 */

public interface JobMigrationSpec extends TaskSpec {
    
    /**
     * Returns the type of job migration this is.
     * @return The job migration type
     */
    public JobMigrationType getJobMigrationType();

    /**
     * Returns the job id of the job to migrate.
     * @return The job id
     */
    public String getJobId();

    /**
     * Sets the job id of the job to migrate.
     * @param jobId The job id
     */
    public void setJobId(String jobId);

    /**
     * Returns the new job spec used to migrate the job.
     * @return The new job spec
     */
    public JobSpec getJobSpec();
    
    /**
     * Sets the new job spec to migrate the job.
     * @param jobSpec The new job spec
     */
    public void setJobSpec(JobSpec jobSpec);
}

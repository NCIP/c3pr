package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.task.TaskSubmissionService;
import org.gridlab.gridsphere.portlet.User;

import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobMigrationService.java,v 1.1.1.1 2007-02-01 20:40:40 kherm Exp $
 * <p>
 * Describes an interface for submitting jobs.
 */

public interface JobMigrationService extends TaskSubmissionService {

    /**
     * Returns the job types that can be migrated by this service.
     * @return
    public List getSupportedJobTypes();
     */
    
    /**
     * Returns true if supports the given job type
     * @param jobType
     * @return
    public boolean supportsJobType(Class type);
     */

    /**
     * Returns the job types handled by this service.
     * @return The list of supported job migration types
     * @see JobMigrationType
     */
    public List getJobMigrationTypes();

    /**
     * Creates a new job spec of the given job type.
     * @param type The job migration type
     * @return The job migration spec
     * @throws JobException
     */
    public JobMigrationSpec createJobMigrationSpec(JobMigrationType type) throws JobException;

    /**
     * Creates a new job spec initialized with the values in the given spec.
     * @param spec The job migration spec to copoy
     * @return The new job migration spec
     * @throws JobException
     */
    public JobMigrationSpec createJobMigrationSpec(JobMigrationSpec spec) throws JobException;

    /**
     * Submits a job migration with the given a job migration spec.
     * @param spec The job migration spec
     * @return The job migration
     * @throws JobException
     */
    public JobMigration submitJobMigration(JobMigrationSpec spec) throws JobException;

    /**
     * Cancels the given job migration.
     * @param jobMigration The job migration to cancel
     * @throws JobException
     */
    public void cancelJobMigration(JobMigration jobMigration) throws JobException;

    /**
     * Returns all the job migrations of the given type.
     * If type is null, returns all job records known
     * to the service.
     * @param type The job migration type
     * @return The list of job migrations
     * @see JobMigration
     */
    public List getJobMigrations(JobMigrationType type);

    /**
     * Returns all the job migrations of the given type
     * associated with the given user. If type is null,
     * returns all job records associated with the given
     * user.
     * @param user The user
     * @param type The job migration type
     * @return The job migrations belonging to the given user
     */
    public List getJobMigrations(User user, JobMigrationType type);

    /**
     * Returns the migration record with the given object identifier.
     * @param oid The job migration oid
     * @return The job migration
     */
    public JobMigration getJobMigration(String oid);

    /**
     * Deletes the job migration.
     * @param migration The job migration to delete
     */
    public void deleteJobMigration(JobMigration migration);
}

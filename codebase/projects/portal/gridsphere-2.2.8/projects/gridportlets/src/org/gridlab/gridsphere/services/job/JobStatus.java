/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobStatus.java,v 1.1.1.1 2007-02-01 20:40:43 kherm Exp $
 */
package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.task.TaskStatus;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Represents the status of a job.
 */
public class JobStatus extends TaskStatus {

    private static HashMap constants = new HashMap(22);

    public static final JobStatus JOB_NEW = new JobStatus(0,"job.new",TaskStatus.NEW);
    public static final JobStatus JOB_SUBMITTING = new JobStatus(1,"job.submitting",TaskStatus.SUBMITTING);
    public static final JobStatus JOB_SUBMITTED = new JobStatus(2,"job.submitted",TaskStatus.SUBMITTED);
    public static final JobStatus JOB_QUEUED = new JobStatus(3,"job.queued",TaskStatus.QUEUED);
    public static final JobStatus JOB_PENDING = new JobStatus(4,"job.pending",TaskStatus.PENDING);
    public static final JobStatus JOB_PREPROCESS = new JobStatus(5,"job.preprocess",TaskStatus.PREPROCESS);
    public static final JobStatus JOB_ACTIVE = new JobStatus(6,"job.active",TaskStatus.ACTIVE);
    public static final JobStatus JOB_SUSPENDED = new JobStatus(7,"job.suspended",TaskStatus.SUSPENDED);
    public static final JobStatus JOB_POSTPROCESS = new JobStatus(8,"job.postprocess",TaskStatus.POSTPROCESS);
    public static final JobStatus JOB_COMPLETED = new JobStatus(9,"job.completed",TaskStatus.COMPLETED);
    public static final JobStatus JOB_CANCELING = new JobStatus(10,"job.canceling",TaskStatus.CANCELING);
    public static final JobStatus JOB_CANCELED = new JobStatus(11,"job.canceled",TaskStatus.CANCELED);
    public static final JobStatus JOB_FAILED = new JobStatus(12,"job.failed",TaskStatus.FAILED);
    public static final JobStatus MIGRATION_SUBMITTING = new JobStatus(13,"migration.submitting",TaskStatus.ACTIVE);
    public static final JobStatus MIGRATION_SUBMITTED = new JobStatus(14,"migration.submitted",TaskStatus.ACTIVE);
    public static final JobStatus MIGRATION_CHECKPOINT = new JobStatus(15,"migration.checkpoint",TaskStatus.ACTIVE);
    public static final JobStatus MIGRATION_STAGE_OUT = new JobStatus(16,"migration.stageout",TaskStatus.ACTIVE);
    public static final JobStatus MIGRATION_ACTIVE = new JobStatus(17,"migration.active",TaskStatus.ACTIVE);
    public static final JobStatus MIGRATION_JOB_SUBMITTING = new JobStatus(18,"migration.job.submitting",TaskStatus.ACTIVE);
    public static final JobStatus MIGRATION_JOB_SUBMITTED = new JobStatus(19,"migration.job.submitted",TaskStatus.ACTIVE);
    public static final JobStatus MIGRATION_JOB_FAILED = new JobStatus(20,"migration.job.failed",TaskStatus.FAILED);
    public static final JobStatus UNKNOWN = new JobStatus(-1,"unknown",TaskStatus.UNKNOWN);

    protected TaskStatus taskStatus = null;

    protected JobStatus() {}

    protected JobStatus(int value, String name, TaskStatus taskStatus) {
        this.value = value;
        this.name = name;
        this.taskStatus = taskStatus;
        constants.put(name, this);
    }

    public boolean equals(JobStatus status) {
        return (this.value == status.value);
    }

    public boolean equals(String name) {
        return (this.name.equals(name));
    }

    /**
     * Returns the string value of this status.
     * @return The string value
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the task status equivalent
     * @return The equivalent task status
     */
    public TaskStatus toTaskStatus() {
        return taskStatus;
    }

    public static Iterator iterateConstants() {
        return constants.values().iterator();
    }

    /**
     * Converts the given string value to a job status.
     * @param name The string value
     * @return The equivalent job status
     */
    public static JobStatus toJobStatus(String name) {
        JobStatus constant =
                (JobStatus) constants.get(name);
        if (constant == null) {
            System.err.println("Job status " + name + " is unknown");
            return UNKNOWN;
        }
        return constant;
    }

    /**
     * Returns true if this status is a "live" status.
     * @return Eeturns true if live, false otherwise.
     * @see TaskStatus
     */
    public boolean isLive() {
        return taskStatus.isLive();
    }
}

package org.gridlab.gridsphere.services.job.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.job.Job;
import org.gridlab.gridsphere.services.job.JobException;
import org.gridlab.gridsphere.services.job.JobMigrationSpec;
import org.gridlab.gridsphere.services.job.JobMigrationType;
import org.gridlab.gridsphere.services.job.JobSpec;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.task.impl.BaseTaskSpec;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseJobMigrationSpec.java,v 1.1.1.1 2007-02-01 20:40:45 kherm Exp $
 * <p>
 * Base implementation of job specification.
 */

public class BaseJobMigrationSpec extends BaseTaskSpec implements JobMigrationSpec {

    protected static PortletLog log = SportletLog.getInstance(BaseJobSpec.class);

    protected String jobId = null;
    protected JobSpec jobSpec = null;

    public BaseJobMigrationSpec() {
        super();
    }

    public BaseJobMigrationSpec(JobMigrationSpec spec) {
        super(spec);
        setJobId(spec.getJobId());
        setJobSpec(spec.getJobSpec());
    }

    public org.gridlab.gridsphere.services.task.TaskType getTaskType() {
        return JobMigrationType.INSTANCE;
    }

    public JobMigrationType getJobMigrationType() {
        return JobMigrationType.INSTANCE;
    }
    
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public JobSpec getJobSpec() {
        return jobSpec;
    }

    public void setJobSpec(JobSpec jobSpec) {
        this.jobSpec = (BaseJobSpec)jobSpec;  
    }

    public String marshalToString() {
        return jobSpec.marshalToString();
    }

    public Object clone() {
        BaseJobMigrationSpec spec = new BaseJobMigrationSpec(this);
        return spec;
    }
}

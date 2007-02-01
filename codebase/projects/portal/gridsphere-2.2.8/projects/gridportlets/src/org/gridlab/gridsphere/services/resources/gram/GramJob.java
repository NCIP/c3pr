/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GramJob.java,v 1.1.1.1 2007-02-01 20:41:05 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.gram;


import org.globus.gram.GramJobListener;
import org.globus.gram.GramException;
import org.globus.gram.Gram;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.job.impl.BaseJob;
import org.gridlab.gridsphere.services.job.JobStatus;
import org.gridlab.gridsphere.services.job.JobException;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.gridlab.gridsphere.services.task.TaskException;
import org.ietf.jgss.GSSException;


public class GramJob extends BaseJob implements Runnable, GramJobListener  {

    private static PortletLog log = SportletLog.getInstance(GramJob.class);
    private org.globus.gram.GramJob globusJob = null;
    private Thread thread = null;
    private final Long gramJobLock = new Long(0);
    private final Long globusJobLock = new Long(0);

    /**
     * Default constructor
     */
    public GramJob() {
        super();
    }

    /**
     * Constructs a gram job with the given globus job and spec
     * @param spec
     */
    public GramJob(GramJobSpec spec) {
        super(spec);
        setHostName(spec.getHostName());
    }

    /**
     * Constructs a gram job with the given globus job and spec
     * @param spec
     */
    public GramJob(String jobId, GramJobSpec spec) {
        super(jobId, spec);
        setHostName(spec.getHostName());
    }

    public void start() {
        if (thread == null) {
            log.debug("Starting job in thread");
            thread = new Thread(this, "GramJobImpl");
            thread.start();
        }
    }

    public void run() {
        try {
            startJob();
        } catch (Exception e) {
            log.error("Error starting job in thread", e);
        }
    }

    public void startJob() throws JobException {
        synchronized(gramJobLock) {
            // Set job status to submitting...
            setJobStatus(JobStatus.JOB_SUBMITTING, "Preparing job");
            // Set initial job id to our object id
            setJobId(oid);
            // Gram spec
            GramJobSpec gramSpec = (GramJobSpec)taskSpec;
            // CredentialContext
            CredentialContext context = gramSpec.getCredentialContext();
            if (context == null) {
                try {
                    context = getDefaultCredentialContext();
                } catch (TaskException e) {
                    setJobStatus(JobStatus.JOB_FAILED, e.getMessage());
                    throw new JobException(e.getMessage());
                }
            }
            // Preprocess gram spec
            try {
                gramSpec.preProcess(context);
            } catch (Exception e) {
                setJobStatus(JobStatus.JOB_FAILED, e.getMessage());
                throw new JobException(e.getMessage());
            }
            // Marshal gram spec to rsl string
            String rslString = gramSpec.marshalToString();
            log.debug("Submitting rsl string: " + rslString);
            // Save job submission string
            setJobSubmissionString(rslString);
            log.debug("Creating globus gram job");
            // Create globus job
            globusJob = new org.globus.gram.GramJob(context.getCredential(), rslString);
//            GramJobProxy gramJobProxy = new GramJobProxy(oid);
//            globusJob.addListener(gramJobProxy);
            globusJob.addListener(this);
            // Get resource manager contact
            String resourceManagerContact = gramSpec.getResourceManagerContact();
            // Get batch job flag
            boolean isBatchJob = gramSpec.isBatchJob();
            // Limited delegation is always false
            boolean isLimitedDelegation = false;
            log.debug("Sending globus gram request");
            // Set job status to submitting...
            setJobStatus(JobStatus.JOB_SUBMITTING, "Submitting job");
            synchronized (globusJobLock) {
                // Submit globus job
                try {
                    globusJob.request(resourceManagerContact, isBatchJob, isLimitedDelegation);
                } catch (GSSException e) {
                    log.error("GSS exception thrown while submitting job", e);
                    setJobStatus(JobStatus.JOB_FAILED, e.getMessage());
                    throw new JobException(e.getMessage());
                } catch (GramException e) {
                    log.error("Gram exception thrown while submitting job", e);
                    setJobStatus(JobStatus.JOB_FAILED, e.getMessage());
                    throw new JobException(e.getMessage());
                }
                // Set job id to globus job id
                String globusJobId = globusJob.getIDAsString();
                log.debug("Submitted job " + globusJobId);
                setJobId(globusJobId);
                // Set job status to submitted...
                setJobStatus(JobStatus.JOB_SUBMITTED, "Job submitted");
                // Add job to job status monitor if batch job
                if (isBatchJob) {
                    GramJobStatusMonitor.getInstance().addJob(this);
                }
            }
        }
    }

    public void updateJob() throws JobException {
        synchronized(gramJobLock) {
            if (globusJob == null) {
                // If no gram job associated with job, nothing we can do...
                log.debug("No gram job for job id " + jobId);
            } else {
                log.debug("Retrieving status for gram job " + jobId
                        + " with current status " + globusJob.getStatusAsString());
                try {
                    // Then get globus status
                    Gram.jobStatus(globusJob);
                    // Call the job's globus job listener interface to update the status
                    statusChanged(globusJob);
                } catch (GSSException e) {
                    log.error("GSS exception thrown while getting gram status", e);
                    String eMessage = e.getMessage();
                    setJobStatus(JobStatus.UNKNOWN, eMessage);
                    throw new JobException(e.getMessage());
                } catch (GramException e) {
                    log.error("Gram exception thrown while getting gram status", e);
                    String eMessage = e.getMessage();
                    if (eMessage.indexOf("Connection refused") > -1) {
                        setJobStatus(JobStatus.JOB_COMPLETED, "Job no longer active");
                    } else {
                        setJobStatus(JobStatus.JOB_FAILED, eMessage);
                    }
                    throw new JobException(e.getMessage());
                }
            }
        }
    }

    public void stopJob() throws JobException {
        synchronized(gramJobLock) {
            if (globusJob == null) {
                // If no gram job associated with job, nothing we can do...
                log.debug("No gram job with job id " + jobId);
            } else {
                log.debug("Canceling gram job" + jobId
                        + " with current status " + globusJob.getStatusAsString());
                try {
                    // Then get globus status
                    Gram.cancel(globusJob);
                    // Then get globus status
                    Gram.jobStatus(globusJob);
                    // Call the job's globus job listener interface to update the status
                    statusChanged(globusJob);
                } catch (GSSException e) {
                    log.error("GSS exception thrown while getting gram status", e);
                    String eMessage = e.getMessage();
                    setJobStatus(JobStatus.UNKNOWN, eMessage);
                    throw new JobException(e.getMessage());
                } catch (GramException e) {
                    log.error("Gram exception thrown while getting gram status", e);
                    String eMessage = e.getMessage();
                    setJobStatus(JobStatus.UNKNOWN, eMessage);
                    throw new JobException(e.getMessage());
                }
            }
        }
    }

    /**
     * Returns the globus associated with this object
     * @return
     */
    public org.globus.gram.GramJob getGlobusJob() {
        synchronized (globusJobLock) {
            return globusJob;
        }
    }

    /**
     * @param gramJob
     */
    public void setGlobusJob(org.globus.gram.GramJob gramJob) {
        synchronized (globusJobLock) {
            // Set gram job and id
            globusJob = gramJob;
            if (globusJob == null) {
                setJobStatus(JobStatus.JOB_COMPLETED, "Job no longer active");
            } else {
                // Add ourselves as a listener
                globusJob.addListener(this);
            }
        }
    }

    public void statusChanged(org.globus.gram.GramJob job) {
        synchronized (globusJobLock) {
            log.debug("Entering statusChanged.");

            jobId = job.getIDAsString();

            int errorCode = job.getError();
            log.debug("Gram job " + jobId + " has gram job status "
                      + job.getStatusAsString()
                      + " and error code " + errorCode);

            // Convert job taskStatus
            switch(globusJob.getStatus()){
                case org.globus.gram.GramJob.STATUS_ACTIVE:
                    setJobStatus(JobStatus.JOB_ACTIVE,
                                 "Job is active with message " + GramJobError.getErrorMessage(errorCode));
                break;
                case org.globus.gram.GramJob.STATUS_STAGE_IN:
                    setJobStatus(JobStatus.JOB_PREPROCESS,
                                 "Job is staging in with message " + GramJobError.getErrorMessage(errorCode));
                break;
                case org.globus.gram.GramJob.STATUS_STAGE_OUT:
                    setJobStatus(JobStatus.JOB_POSTPROCESS,
                                 "Job is staging out with message " + GramJobError.getErrorMessage(errorCode));
                break;
                case org.globus.gram.GramJob.STATUS_DONE:
                    setJobStatus(JobStatus.JOB_COMPLETED,
                                 "Job completed with message " + GramJobError.getErrorMessage(errorCode));
                break;
                case org.globus.gram.GramJob.STATUS_PENDING:
                    setJobStatus(JobStatus.JOB_PENDING,
                                 "Job is pending with message " + GramJobError.getErrorMessage(errorCode));
                break;
                case org.globus.gram.GramJob.STATUS_FAILED:
                    setJobStatus(JobStatus.JOB_FAILED,
                                 "Job failed with error code " + errorCode + "; " + GramJobError.getErrorMessage(errorCode));
                break;
                case org.globus.gram.GramJob.STATUS_SUSPENDED:
                    setJobStatus(JobStatus.JOB_SUSPENDED,
                                 "Job is suspended with message " + GramJobError.getErrorMessage(errorCode));
                break;
                case org.globus.gram.GramJob.STATUS_UNSUBMITTED:
                    setJobStatus(JobStatus.JOB_NEW);
                break;
                default:
                    setJobStatus(JobStatus.UNKNOWN,
                                 "Job status is unknown with message " + GramJobError.getErrorMessage(errorCode));
                break;
            }
        }
    }

}

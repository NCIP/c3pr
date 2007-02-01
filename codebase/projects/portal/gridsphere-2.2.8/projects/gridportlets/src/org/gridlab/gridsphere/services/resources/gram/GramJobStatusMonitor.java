/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GramJobStatusMonitor.java,v 1.1.1.1 2007-02-01 20:41:07 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.gram;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.job.Job;
import org.gridlab.gridsphere.services.job.JobException;
import org.gridlab.gridsphere.services.job.impl.BaseJobStatusMonitor;
import org.gridlab.gridsphere.services.resources.gram.GramJobSpec;
import org.gridlab.gridsphere.services.resources.gram.GramJob;
import org.gridlab.gridsphere.services.resources.gram.GramJob;

import java.util.Hashtable;

public class GramJobStatusMonitor extends BaseJobStatusMonitor {

    private static PortletLog log = SportletLog.getInstance(BaseJobStatusMonitor.class);
    private static GramJobStatusMonitor instance = null;
    private static Hashtable globusJobs = new Hashtable();

    protected GramJobStatusMonitor() {
        super();
    }

    public static GramJobStatusMonitor getInstance() {
        if (instance == null) {
            log.debug("Creating new instance.");
            instance = new GramJobStatusMonitor();
            log.debug("Starting monitor thread.");
            instance.start();
            log.debug("Ready to monitor...");
        }
        return instance;
    }

    public void addJob(Job job) {
        super.addJob(job);
        GramJob gramJob = (GramJob)job;
        String jobId = gramJob.getJobId();
        org.globus.gram.GramJob globusJob = gramJob.getGlobusJob();
        globusJobs.put(jobId, globusJob);
    }

    public void updateJob(Job job)
            throws JobException {
        GramJob gramJob = (GramJob)job;
        String jobId = gramJob.getJobId();
        org.globus.gram.GramJob globusJob = (org.globus.gram.GramJob)globusJobs.get(jobId);
        gramJob.setGlobusJob(globusJob);
        gramJob.updateJob();
    }

    public void removeJob(String jobId) {
        super.removeJob(jobId);
        globusJobs.remove(jobId);
    }

    public boolean canMonitorJob(Job job) {
        GramJobSpec gramSpec = (GramJobSpec)job.getJobSpec();
        // If batch job, then see if job is not yet finished...
        if (gramSpec.isBatchJob()) {
            return super.canMonitorJob(job);
        }
        // Otherwise, it is being updated through
        // the gram job listener interface
        return false;
    }
}

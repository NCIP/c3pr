/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseJobStatusMonitor.java,v 1.1.1.1 2007-02-01 20:40:48 kherm Exp $
 */
package org.gridlab.gridsphere.services.job.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.services.util.GridPortletsDatabase;

import org.gridlab.gridsphere.services.job.Job;
import org.gridlab.gridsphere.services.job.JobException;
import org.gridlab.gridsphere.services.job.JobStatus;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public abstract class BaseJobStatusMonitor extends Thread {

    private PortletLog log = SportletLog.getInstance(BaseJobStatusMonitor.class);
    private Long period = new Long(05);
    private boolean active = false;
    private boolean cancel = false;
    private Map jobMap = null;
    private GridPortletsDatabase pm = null;

    protected BaseJobStatusMonitor() {
        log.debug("Creating new monitor instance");
        pm = GridPortletsDatabase.getInstance();
        jobMap = Collections.synchronizedMap(new TreeMap());
    }

    public Iterator getJobs() {
        List jobList = new Vector(jobMap.size());
        synchronized (jobMap) {
            Iterator jobIterator = jobMap.values().iterator();
            while (jobIterator.hasNext()) {
                //Job job = (Job)jobIterator.next();
                String oid = (String)jobIterator.next();
                Job job = getJobRecord(oid);
                // TODO: Delete bad references
                if (job != null) {
                    jobList.add(job);
                } 
            }
        }
        return jobList.iterator();
    }

    public Job getJob(String jobId) {
        String oid = (String)jobMap.get(jobId);
        Job job = getJobRecord(oid);
        if (job == null) jobMap.remove(jobId);
        return job;
        //return (Job)this.jobMap.get(jobId);
    }

    public boolean hasJob(String jobId) {
        return this.jobMap.containsKey(jobId);
    }

    public void addJob(Job job) {
        String jobId = job.getJobId();
        removeJob(jobId);
        log.debug("Adding object [" + job.getOid() + "] with jobId [" + jobId + "] with jobStatus " + job.getJobStatus().getName());
        //this.jobMap.put(jobId, job);
        this.jobMap.put(jobId, job.getOid());
    }

    public void removeJob(String jobId) {
        String oid = (String)jobMap.get(jobId);
        Job job = getJobRecord(jobId);
        //Job job = (Job)jobMap.get(jobId);
        if (job != null) {
            log.debug("Removing object [" + job.getOid() + "] with jobId [" + jobId + "] with jobStatus " + job.getJobStatus().getName());
        }
        jobMap.remove(jobId);
    }

    public boolean canMonitorJob(Job job) {
        JobStatus jobStatus = job.getJobStatus();
        // Cannot monitor if job is completed, failed, migrated or canceled
        return (jobStatus.isLive());
    }

    public abstract void updateJob(Job job) throws JobException;
    
    public void run() {
        while (true) {
            // Check if have been canceled
            synchronized (this.jobMap) {
                if (this.cancel) {
                    break;
                }
            }
            // Loop through job list and update job taskStatus
            Iterator jobIterator = getJobs();
            while (jobIterator.hasNext()) {
                Job job = (Job)jobIterator.next();
                String jobId = job.getJobId();
                // Check if we can monitor job
                if (canMonitorJob(job)) {
                    log.debug("Updating object [" + job.getOid() + "] with jobId [" + jobId + "]");
                    // If so, update job taskStatus
                    try {
                        updateJob(job);
                    } catch (JobException e) {
                        // If we caught an exception, remove job from list
                        log.error("Error updating job", e);
                        removeJob(jobId);
                    }
                } else {
                    // Othwerwise, remove job from this monitor
                    removeJob(jobId);
                }
            }
            try {
                // Sleep for specified period
                sleep(this.period.longValue() * 1000);
            } catch (InterruptedException e) {
                log.error("Error putting thread to sleep", e);
                break;
            }
        }
    }

    public void cancel() {
        log.debug("BaseJobMonitor: Shutting down job status monitor");
        synchronized (this.jobMap) {
            this.cancel = true;
        }
    }
    
    private Job getJobRecord(String oid) {
        Job job = null;
        try {
            job = (Job) pm.restore("from " + Job.class.getName()
                                  + " as j where j.oid='" + oid + "'");
        } catch (PersistenceManagerException e) {
            log.error("FATAL: could not retrieve job by oid " + oid + "!\n" + e.getMessage(), e);
        }
        return job;
    }
}

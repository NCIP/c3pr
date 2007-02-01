/**
 * @author <a href="mailto:jean-claude.cote@nrc.ca">Jean-Claude Cote</a>
 * @version $Id: GramJobSubmissionService.java,v 1.1.1.1 2007-02-01 20:41:07 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.gram;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.services.job.impl.AbstractJobSubmissionService;
import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;

import java.util.List;
import java.util.Vector;
import java.util.Iterator;

public class GramJobSubmissionService extends AbstractJobSubmissionService {

    private static PortletLog log = SportletLog.getInstance(GramJobSubmissionService.class);

    public GramJobSubmissionService() {
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("Registering gram job submission service");
        super.init(config);
        StartJobMonitorThread thread = new StartJobMonitorThread(this);
        thread.start();
    }


    public void destroy() {
        log.debug("Unregistering gram job submission service");
        super.destroy();
    }

    public List getJobTypes() {
        List types = new Vector(1);
        types.add(GramJobType.INSTANCE);
        return types;
    }

    public List getJobResources(JobType type) {
        List resources = null;
        try {
            ResourceRegistryService registry = (ResourceRegistryService)
                    factory.createPortletService(ResourceRegistryService.class, null, true);
            resources = registry.getResources(GramResourceType.INSTANCE);
        } catch (PortletServiceException e) {
            log.error("Unable to get service resources for job type " + type.getTaskClassName(), e);
            resources = new Vector(0);
        }
        return resources;
    }

    public List getJobResources(JobType type, User user) {
        List resources = null;
        try {
            ResourceRegistryService registry = (ResourceRegistryService)
                    factory.createPortletService(ResourceRegistryService.class, null, true);
            resources = registry.getResources(GramResourceType.INSTANCE);
        } catch (PortletServiceException e) {
            log.error("Unable to get service resources for job type " + type.getTaskClassName(), e);
            resources = new Vector(0);
        }
        return resources;
    }

    public JobSpec createJobSpec(JobType type)
            throws JobException {
        return new GramJobSpec();
    }

    public JobSpec createJobSpec(JobSpec spec)
            throws JobException {
        return new GramJobSpec(spec);
    }

    public Job submitJob(JobSpec spec)
            throws JobException {
        GramJob gramJob = new GramJob((GramJobSpec)spec);
        gramJob.startJob();
        return gramJob;
   }

    public void cancelJob(Job job)
            throws JobException {
        ((GramJob)job).stopJob();
    }
}

class StartJobMonitorThread extends Thread {

    protected JobSubmissionService service = null;

    public StartJobMonitorThread(JobSubmissionService service) {
        this.service = service;
    }

    public void run() {
        GramJobStatusMonitor monitor = GramJobStatusMonitor.getInstance();
        List jobList = service.getJobs(GramJobType.INSTANCE);
        Iterator jobIterator = jobList.iterator();
        synchronized (jobList) {
            while (jobIterator.hasNext()) {
                GramJob job = (GramJob)jobIterator.next();
                if (monitor.canMonitorJob(job)) {
                    monitor.addJob(job);
                }
            }
        }
    }
}

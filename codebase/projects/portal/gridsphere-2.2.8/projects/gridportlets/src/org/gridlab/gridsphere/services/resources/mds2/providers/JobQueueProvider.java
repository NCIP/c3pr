/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobQueueProvider.java,v 1.1.1.1 2007-02-01 20:41:21 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.mds2.providers;

import org.gridlab.gridsphere.services.resources.gram.GramJobManager;
import org.gridlab.gridsphere.services.resources.mds2.Mds2ResourceProvider;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.job.JobQueue;
import org.gridlab.gridsphere.services.job.Job;
import org.gridlab.gridsphere.services.job.impl.BaseJob;
import org.gridlab.gridsphere.services.job.impl.BaseJobQueue;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.naming.directory.SearchControls;


/***
 dn: Mds-Job-Queue-name=dque,Mds-Software-deployment=jobmanager-pbs,Mds-Host-h
  n=peyote.aei.mpg.de, Mds-Vo-name=local,o=Grid
 Mds-Gram-Job-Queue-whenactive: 0
 Mds-Gram-Job-Queue-jobwait: NULL
 Mds-validto: 200405032033.23Z
 Mds-Gram-Job-Queue-dispatchtype: batch
 Mds-Computer-Total-Free-nodeCount: 0
 objectClass: Mds
 objectClass: MdsSoftware
 objectClass: MdsJobQueue
 objectClass: MdsComputerTotal
 objectClass: MdsComputerTotalFree
 objectClass: MdsGramJobQueue
 Mds-keepto: 200405032033.23Z
 Mds-Computer-Total-nodeCount: 4
 Mds-Job-Queue-name: dque
 Mds-validfrom: 200405032032.53Z
 Mds-Gram-Job-Queue-schedulerSpecific: NULL
 Mds-Memory-Ram-Total-sizeMB: 0
 Mds-Gram-Job-Queue-maxrunningjobs: 0
 Mds-Gram-Job-Queue-maxcputime: 0
 Mds-Gram-Job-Queue-maxcount: 4
 Mds-Gram-Job-Queue-maxtime: 0
 Mds-Memory-Ram-sizeMB: 0
 Mds-Gram-Job-Queue-priority: NULL
 Mds-Gram-Job-Queue-status: enabled
 Mds-Gram-Job-Queue-maxjobsinqueue: 0
 */
public class JobQueueProvider extends Mds2ResourceProvider {

    private static PortletLog log = SportletLog.getInstance(JobQueueProvider.class);

    public JobQueueProvider(ResourceRegistryService resourceRegistry) {

        super(resourceRegistry);

        addResourceAttribute("Mds-Computer-Total-nodeCount", JobQueue.MAX_NUM_NODES_ATTRIBUTE);
        addResourceAttribute("Mds-Computer-Total-Free-nodeCount", JobQueue.NUM_FREE_NODES_ATTRIBUTE);
        addResourceAttribute("Mds-Gram-Job-Queue-maxrunningjobs", JobQueue.MAX_RUNNING_JOBS_ATTRIBUTE);
        addResourceAttribute("Mds-Gram-Job-Queue-maxcputime", JobQueue.MAX_CPU_TIME_ATTRIBUTE);
        addResourceAttribute("Mds-Gram-Job-Queue-maxcount", JobQueue.MAX_COUNT_ATTRIBUTE);
        addResourceAttribute("Mds-Gram-Job-Queue-maxtime", JobQueue.MAX_TIME_ATTRIBUTE);
        addResourceAttribute("Mds-Memory-Ram-sizeMB", JobQueue.MEMORY_SIZE_MB_ATTRIBUTE);
        addResourceAttribute("Mds-Gram-Job-Queue-priority", JobQueue.PRIORITY_ATTRIBUTE);
        addResourceAttribute("Mds-Gram-Job-Queue-status", JobQueue.STATUS_ATTRIBUTE);
        addResourceAttribute("Mds-Gram-Job-Queue-maxjobsinqueue", JobQueue.MAX_JOBS_ATTRIBUTE);
        addResourceAttribute("Mds-Gram-Job-Queue-jobwait", JobQueue.JOBS_PENDING_ATTRIBUTE);
        addResourceAttribute("Mds-Gram-Job-Queue-dispatchtype", JobQueue.DISPATCH_TYPE_ATTRIBUTE);
    }

    public Resource createResource() {
        GramJobManager gramJobManager = (GramJobManager)parentProvider.getResource();
        // Get the job queue with the same name from our gram job manager
        //JobQueue jobQueue = gramJobManager.getJobQueue(resourceName);

        JobQueue jobQueue = gramJobManager.getJobQueue(resourceName);
        if (jobQueue == null) {
            jobQueue = new BaseJobQueue(gramJobManager, resourceName);
            gramJobManager.addChildResource(jobQueue);
            try {
                resourceRegistry.saveResource(gramJobManager);
            } catch (ResourceException e) {
                log.error("Unable to add gram resource ", e);
                return null;
            }
        } else {
            // Clear existing jobs (for now)
            jobQueue.clearJobs();
            try {
                resourceRegistry.saveResource(jobQueue);
            } catch (ResourceException e) {
                log.error("Unable to add gram resource ", e);
                return null;
            }
        }


        // If one doesn't exist on our gram job manager, then create one.
//        if (jobQueue == null) {
//            //log.debug("Creating job queue " + resourceName);
//            jobQueue = gramJobManager.putJobQueue(resourceName);
//            try {
//                log.debug("Saving job queue " + jobQueue.getName());
//                resourceRegistry.saveResource(gramJobManager);
//            } catch (ResourceException e) {
//                log.error("Unable to save resource ", e);
//            }
//        } else {
//            //log.debug("Found job queue " + resourceName
//            //          + " on " + gramJobManager.getName());
//        }
        return jobQueue;
    }

    public String getResourceSearchDn() {
        return parentProvider.getResourceDn();
    }

    public String getResourceSearchFilter() {
        return "(objectclass=MdsJobQueue)";
    }

    public int getResourceSearchScope() {
        return SearchControls.ONELEVEL_SCOPE;
    }
}

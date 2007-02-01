package org.gridlab.gridsphere.services.resources.gram;

import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.job.*;
import org.gridlab.gridsphere.services.security.gss.impl.BaseGssServiceResource;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.globus.gram.Gram;
import org.globus.gram.GramException;

import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GramResource.java,v 1.1.1.1 2007-02-01 20:41:08 kherm Exp $
 * <p>
 * Implementation of the gram resource.
 */

public class GramResource extends BaseGssServiceResource implements JobResource {

    private static PortletLog log = SportletLog.getInstance(GramResource.class);

    public static final String DEFAULT_PORT = "2119";
    public static final String DEFAULT_PROTOCOL = "gram";

    public GramResource() {
        super();
        setPort(DEFAULT_PORT);
        setProtocol(DEFAULT_PROTOCOL);
        setResourceType(GramResourceType.INSTANCE);
    }

    public String getResourceManagerContact() {
        String rmc = getHost();
        if (getPort() != null) {
            rmc += ':' + getPort();
        }
        return rmc;
    }

    public String getResourceManagerContact(String jobscheduler) {
        String resourceManagerContact = null;
        String hostName = getHostName();
        if (hostName == null || hostName.equals("")) {
            log.debug("No host provided for rmc");
        } else {
            if (jobscheduler == null || jobscheduler.trim().equals("")) {
                resourceManagerContact = hostName;
            } else {
                resourceManagerContact = hostName
                                       + "/jobmanager-"
                                       + jobscheduler;
            }
        }
        return resourceManagerContact;
    }

    public void authenticates(GSSCredential credential)
            throws GSSException, ResourceException {
        String rmc = getHostName();
        if (getPort() != null) {
            rmc += ':' + getPort();
        }
        try {
            Gram.ping(credential, rmc);
        } catch (GramException e) {
            log.error("Error authenticating credential with gram ping ", e);
            throw new ResourceException(e);
        }
    }

    public List getJobSchedulers() {
//        return getChildResources();
        return getHardwareResource().getChildResources(GramJobManagerType.INSTANCE);
    }

    public List getGramJobManagers() {
//        return getChildResources();
        return getHardwareResource().getChildResources(GramJobManagerType.INSTANCE);
    }

    public GramJobManager createGramJobManager(String name) {
//        return new GramJobManager(this, name);
        return new GramJobManager(getHardwareResource(), name);
    }

    public GramJobManager getGramJobManager(String name) {
        synchronized(this) {
            int position = findGramJobManager(name);
            if (position < 0) {
                return null;
            }
//            return (GramJobManager)getChildResources().get(position);
            return (GramJobManager)getGramJobManagers().get(position);
        }
    }

    public void putGramJobManager(GramJobManager manager) {
        synchronized(this) {

            HardwareResource hardwareResource = getHardwareResource();
            JobSchedulerType jobSchedulerType = manager.getJobSchedulerType();

//            BaseJobScheduler jobScheduler
//                    = (BaseJobScheduler)JobSchedulerType.getJobScheduler(hardwareResource, jobSchedulerType);
//            if (jobScheduler == null) {
//                jobScheduler = new BaseJobScheduler();
//                jobScheduler.setJobSchedulerType(jobSchedulerType);
//                hardwareResource.addChildResource(jobScheduler);
//            }

            JobScheduler jobScheduler = JobSchedulerType.getJobScheduler(hardwareResource, jobSchedulerType);
            // Delete existing scheduler (for now)
            if (jobScheduler != null) {
                log.error("Found gram job manager " + jobScheduler.getJobSchedulerType().getName());
                hardwareResource.removeChildResource(jobScheduler);
                if (jobScheduler != null) {
                    hardwareResource.removeChildResource(jobScheduler);
                    try {
                        log.error("Removing gram job manager from host");
                        hardwareResource.save();
//                        log.error("Deleting gram job manager from host");
//                        jobScheduler.delete();
                    } catch (ResourceException e) {
                        log.error("Error removing gram job manager", e);
                    }
                }
            }
            // Add new scheduler object (for now)
            hardwareResource.addChildResource(manager);
            try {
                hardwareResource.save();
            } catch (ResourceException e) {
                log.error("Error adding job scheduler", e);
            }


//            int position = findGramJobManager(manager.getName());
//            if (position < 0) {
//                getChildResources().add(manager);
////                addChildResource(manager);
//            } else {
//                getChildResources().set(position, manager);
////                manager.setParentResource(this);
//            }
        }
    }

    public void removeGramJobManager(GramJobManager manager) {
        synchronized(this) {

            HardwareResource hardwareResource = getHardwareResource();
            JobSchedulerType jobSchedulerType = manager.getJobSchedulerType();
            JobScheduler jobScheduler = JobSchedulerType.getJobScheduler(hardwareResource, jobSchedulerType);
            if (jobScheduler != null) {
                hardwareResource.removeChildResource(jobScheduler);
                try {
                    jobScheduler.delete();
                    hardwareResource.save();
                } catch (ResourceException e) {
                    log.error("Error removing job scheduler", e);
                }
            }

//            List gramJobManagerList = getChildResources();
//            int position = findGramJobManager(manager.getName());
//            if (position > -1) {
//                gramJobManagerList.remove(position);
//            }
        }
    }

    /**
     * Returns the position of the mgr in
     * the mgr list with the given name.
     * Returns -1 if queue not found with
     * that label.
     * @param name
     * @return
     */
    private int findGramJobManager(String name) {

//        List gramJobManagerList = getChildResources();
        List gramJobManagerList = getGramJobManagers();
        for (int ii = 0; ii < gramJobManagerList.size(); ++ii) {
            GramJobManager jobmanager = (GramJobManager)gramJobManagerList.get(ii);
            if (jobmanager.getName().equals(name)) {
                return ii;
            }
        }
        return -1;
    }

    public Job submitJob(JobSpec jobSpec) throws JobException {
        return null;
    }

    public void cancelJob(Job job) throws JobException {
    }
}

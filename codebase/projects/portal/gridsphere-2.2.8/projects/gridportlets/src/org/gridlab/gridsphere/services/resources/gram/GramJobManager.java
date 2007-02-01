package org.gridlab.gridsphere.services.resources.gram;

import org.gridlab.gridsphere.services.job.impl.BaseJobScheduler;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.resource.Resource;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GramJobManager.java,v 1.1.1.1 2007-02-01 20:41:05 kherm Exp $
 * <p>
 * Implementation of the gram job manager
 * TODO: This implementation really ought to be a wrapper around
 * TODO: job scheduler which is child of hardware resource
 */

public class GramJobManager extends BaseJobScheduler {

    private String name = null;

    public GramJobManager() {
        super();
        setResourceType(GramJobManagerType.INSTANCE);
    }

    public GramJobManager(GramResource resource, String name) {
        super(resource);
        setResourceType(GramJobManagerType.INSTANCE);
        this.name = name;
        // Get job scheduler type by removing "jobmanager-" from name
        int index = name.indexOf("-");
        if (index > -1) {
            setJobSchedulerTypeName(name.substring(index+1));
        } else {
            setJobSchedulerTypeName(name);
        }
    }

    public GramJobManager(HardwareResource resource, String name) {
        super(resource);
        setResourceType(GramJobManagerType.INSTANCE);
        this.name = name;
        // Get job scheduler type by removing "jobmanager-" from name
        int index = name.indexOf("-");
        if (index > -1) {
            setJobSchedulerTypeName(name.substring(index+1));
        } else {
            setJobSchedulerTypeName(name);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        // Get job scheduler type by removing "jobmanager-" from name
        // Doing this for backwards compatability
        // TODO: Remove this once aei portal is updated, only needed in constructor
        int index = name.indexOf("-");
        if (index > -1) {
            setJobSchedulerTypeName(name.substring(index+1));
        } else {
            setJobSchedulerTypeName(name);
        }
    }
}

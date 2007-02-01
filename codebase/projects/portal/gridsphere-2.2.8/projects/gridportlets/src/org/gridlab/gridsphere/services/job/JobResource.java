package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.resource.ServiceResource;

import java.util.List;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobResource.java,v 1.1.1.1 2007-02-01 20:40:41 kherm Exp $
 * <p>
 * Describes a resource for submitting jobs to.
 */

public interface JobResource extends ServiceResource {

    /**
     * Returns the job schedulers supported by this resource.
     * @return The job schedulers
     * @see JobScheduler
     */
    public List getJobSchedulers();
}

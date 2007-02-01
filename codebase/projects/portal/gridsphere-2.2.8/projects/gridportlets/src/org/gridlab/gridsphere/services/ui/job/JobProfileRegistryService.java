package org.gridlab.gridsphere.services.ui.job;

import java.util.List;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobProfileRegistryService.java,v 1.1.1.1 2007-02-01 20:42:05 kherm Exp $
 */

public interface JobProfileRegistryService {

    public List getJobProfiles();

    public JobProfile getJobProfile(String className);

    public void addJobProfiles(List types);

    public void addJobProfile(JobProfile type);
}

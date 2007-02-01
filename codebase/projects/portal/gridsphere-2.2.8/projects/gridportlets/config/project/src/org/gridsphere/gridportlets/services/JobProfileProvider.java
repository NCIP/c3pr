/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobProfileProvider.java,v 1.1.1.1 2007-02-01 20:15:11 kherm Exp $
 */
package org.gridsphere.gridportlets.services;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.job.JobProfileProvider;

public class JobProfileProvider_@_PROJECT_NAME_@ extends JobProfileProvider {
             
    private static PortletLog log = SportletLog.getInstance(JobProfileProvider_@_PROJECT_NAME_@.class);

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("JobProfileProvider_@_PROJECT_NAME_@.init()");
        super.init(config);
    }
}

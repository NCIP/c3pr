/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobProfileProvider.java,v 1.1.1.1 2007-02-01 20:42:05 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * Factory for creating Job profiles.
 */
public class JobProfileProvider implements PortletServiceProvider {
             
    private static PortletLog log = SportletLog.getInstance(JobProfileProvider.class);
    private boolean inited = false;
    private ServletContext servletContext = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("init()");

        if (inited) return;

        log.info("Initing job profile provider");

        inited = true;
        servletContext = config.getServletContext();
        ServletContext gpContext = servletContext.getContext("/gridportlets");

        String componentsPath = servletContext.getRealPath("/WEB-INF/JobProfiles.xml");
        String mappingPath = servletContext.getRealPath("/WEB-INF/mapping/JobProfile.xml");

        JobProfileRegistry jobProfileRegistry = null;
        try {
            jobProfileRegistry = JobProfileRegistry.load(componentsPath, mappingPath, Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            log.error("FATAL PMXML: Unable to deserialize JobProfiles.xml", e);
            throw new PortletServiceUnavailableException("FATAL PMXML: Unable to deserialize JobProfiles.xml");
        }
        PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
        JobProfileRegistryService jobProfileRegistryService = null;
        try {
            jobProfileRegistryService  = (JobProfileRegistryService)
                                        serviceFactory.createPortletService(JobProfileRegistryService.class, null, true);
            List jobComponentTypeList = jobProfileRegistry.getJobProfiles();
            // Force loading of job spec edit page class list...
            for (int ii = 0; ii < jobComponentTypeList.size(); ++ii) {
                JobProfile type =  (JobProfile)jobComponentTypeList.get(ii);
                type.getJobSpecEditPageClassList();
            }
            jobProfileRegistryService.addJobProfiles(jobComponentTypeList);
        } catch (PortletServiceNotFoundException e) {
            log.error("Job component type registry not found", e);
        }
    }

    public void destroy() {

    }
}

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceProfileProvider.java,v 1.1.1.1 2007-02-01 20:42:13 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser;

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

public class ResourceProfileProvider implements PortletServiceProvider {
             
    private static PortletLog log = SportletLog.getInstance(ResourceProfileProvider.class);
    private boolean inited = false;
    private ServletContext servletContext = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("init()");

        if (inited) return;

        log.info("Initing resource browser provider");

        inited = true;
        servletContext = config.getServletContext();
        ServletContext gpContext = servletContext.getContext("/gridportlets");

        String componentsPath = servletContext.getRealPath("/WEB-INF/ResourceProfiles.xml");
        String mappingPath = servletContext.getRealPath("/WEB-INF/mapping/ResourceProfile.xml");

        ResourceProfileRegistry resourceProfileRegistry = null;
        try {
            resourceProfileRegistry = ResourceProfileRegistry.load(componentsPath, mappingPath, Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            log.error("FATAL PMXML: Unable to deserialize ResourceProfiles.xml", e);
            throw new PortletServiceUnavailableException("FATAL PMXML: Unable to deserialize ResourceProfiles.xml");
        }
        PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
        ResourceProfileRegistryService resourceProfileRegistryService = null;
        try {
            resourceProfileRegistryService  = (ResourceProfileRegistryService)
                                        serviceFactory.createPortletService(ResourceProfileRegistryService.class, null, true);
            List resourceProfileList = resourceProfileRegistry.getResourceProfileList();
            // Force loading of job spec edit page class list...
            for (int ii = 0; ii < resourceProfileList.size(); ++ii) {
                ResourceProfile type =  (ResourceProfile)resourceProfileList.get(ii);
                type.getResourceEditClassList();
            }
            resourceProfileRegistryService.addResourceProfiles(resourceProfileList);
        } catch (PortletServiceNotFoundException e) {
            log.error("Resource browser registry not found", e);
        }
    }

    public void destroy() {

    }
}

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseResourceProviderService.java,v 1.1.1.1 2007-02-01 20:41:00 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Base resource provider service class.
 */
public class BaseResourceProviderService implements ResourceProviderService, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(BaseResourceProviderService.class);
    protected ResourceRegistryService resourceRegistryService = null;
    private boolean isActive = false;
    private List resourceEventListenerList = new ArrayList();
    private final Integer lock = new Integer(0);

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
        try {
            resourceRegistryService = (ResourceRegistryService)
                serviceFactory.createPortletService(ResourceRegistryService.class, null, true);
            resourceRegistryService.addResourceProviderService(this);
        } catch (Exception e) {
            log.error("Unable to get instance of required portlets services", e);
            return;
        }
    }

    public void destroy() {
        deactivate();
        resourceEventListenerList.clear();
    }

    public boolean getIsActive() {
        synchronized (lock) {
            return isActive;
        }
    }

    public final void activate() {
        synchronized (lock) {
            log.debug("Activating resource provider service " + getClass());
            PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
            try {
                resourceRegistryService = (ResourceRegistryService)
                    serviceFactory.createPortletService(ResourceRegistryService.class, null, true);
            } catch (Exception e) {
                log.error("Unable to get instance of required portlet services", e);
            }
            activateService(serviceFactory);
            isActive = true;
        }
    }

    protected boolean activateService(PortletServiceFactory serviceFactory) {
        return true;
    }

    public final void deactivate() {
        synchronized (lock) {
            log.debug("Deactivating resource provider service " + getClass());
            deactivateService();
            notifyResourceEventListeners(new ResourceEvent());
            resourceRegistryService = null;
            isActive = false;
        }
    }

    protected void deactivateService() {

    }

    public ResourceRegistryService getResourceRegistryService() {
        return resourceRegistryService;
    }

    public void addResourceEventListener(ResourceEventListener listener) {
        synchronized (lock) {
            resourceEventListenerList.add(listener);
        }
    }

    public void removeResourceEventListener(ResourceEventListener listener) {
        synchronized (lock) {
            resourceEventListenerList.remove(listener);
        }
    }

    public void notifyResourceEventListeners(ResourceEvent event) {
        synchronized (lock) {
            for (Iterator listeners = resourceEventListenerList.iterator(); listeners.hasNext();) {
                ResourceEventListener resourceEventListener = (ResourceEventListener) listeners.next();
                resourceEventListener.resourceEventOccured(event);
            }
        }
    }
}

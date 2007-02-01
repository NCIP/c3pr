/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Mds2ResourceProviderService.java,v 1.1.1.1 2007-02-01 20:41:20 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.mds2;

import org.gridlab.gridsphere.services.resources.mds2.providers.HardwareResourceProvider;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.impl.BaseResourceProviderService;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.naming.NamingException;
import java.util.List;
import java.util.Iterator;

/**
 * The mds2 resource provider service registers new resources it finds
 * in the GIIS with the resource registry and updates the attributes
 * of previously registered resources with latest information it obtains
 * from the GRIS that running on each resource. Simply polls every 10 minutes
 * (can be made configurable later) the gris resources that are in the resource
 * registry about their parent hardware resources.
 */
public class Mds2ResourceProviderService extends BaseResourceProviderService {

    private static PortletLog log = SportletLog.getInstance(Mds2ResourceProviderService.class);
    protected Mds2ChartService chartService = null;
    protected long monitorInterval = 3600000; // 3,600,000 milliseconds = 60 X 60 X 1 second = 1 hour
    protected GRISMonitorThread grisMonitorThread = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        super.init(config);
    }

    public void destroy() {
        deactivate();
    }

    public boolean activateService(PortletServiceFactory serviceFactory) {
        log.debug("Activating mds2 provider service");
        try {
            chartService = (Mds2ChartService)
                serviceFactory.createPortletService(Mds2ChartService.class, null, true);
        } catch (Exception e) {
            log.error("Unable to get instance of required portlet services", e);
            return false;
        }
        grisMonitorThread = new GRISMonitorThread(this);
        grisMonitorThread.start();
        return true;
    }

    public void deactivateService () {
        log.debug("Deactivating mds2 provider service");
        grisMonitorThread.deactivate();
    }

    /**
     * Returns the number of milliseconds the service should wait
     * before polling the GIIS for more resource information.
     * @return
     */
    public long getMonitorInterval() {
        return monitorInterval;
    }

    /**
     * Sets the number of milliseconds the service should wait
     * before polling the GIIS for more resource information.
     * @param milliseconds
     */
    public void setMonitorInterval(long milliseconds) {
        monitorInterval = milliseconds;
    }
}

class GRISMonitorThread extends Thread {

    protected static PortletLog log = SportletLog.getInstance(GRISMonitorThread.class);
    private Mds2ResourceProviderService resourceProviderService = null;
    private ResourceRegistryService resourceRegistry = null;
    private Mds2ChartService chartService = null;
    private long pollingInterval = 3600000; // 3,600,000 milliseconds = 60 X 60 X 1 second = 1 hour
    //private long pollingInterval = 5000;
    protected boolean isActive = false;
    protected Mds2ResourceProvider resourceProvider = null;
    protected final Long lock = new Long(0);

    public GRISMonitorThread(Mds2ResourceProviderService resourceProviderService) {
        this.resourceProviderService = resourceProviderService;
        resourceRegistry = resourceProviderService.getResourceRegistryService();
        chartService = resourceProviderService.chartService;
        pollingInterval = resourceProviderService.monitorInterval;
        resourceProvider = new HardwareResourceProvider(resourceRegistry);
    }

    public void deactivate() {
        log.debug("Deactivating");
        synchronized(lock) {
            isActive = false;
            resourceProvider.destroy();
        }
    }

    public void run() {
        log.info("Starting gris monitor thread");
        synchronized(lock) {
            isActive = true;
        }
        try {
            while (true) {
                synchronized(lock) {
                    if (!isActive) {
                        log.info("Stopping gris monitor thread");
                        return;
                    }
                }
                List grisResourceList = resourceRegistry.getResources(GRISResourceType.INSTANCE);
                for (Iterator grisResources = grisResourceList.iterator(); grisResources.hasNext();) {
                    synchronized(lock) {
                        if (!isActive) {
                            log.info("Stopping gris monitor thread");
                            return;
                        }
                    }
                    GRISResource grisResource = (GRISResource) grisResources.next();
                    //log.debug("Querying gris resource " + grisResource.getFileHostName());
                    try {
                        resourceProvider.updateResources(grisResource);
                        resourceProviderService.notifyResourceEventListeners(Mds2ResourceEvent.INSTANCE);
                    } catch (Exception e) {
                        log.error("Error in performing gris query on " + grisResource.getHost(), e);
                    }
                    synchronized(lock) {
                        if (!isActive) {
                            log.info("Stopping gris monitor thread");
                            return;
                        }
                    }
                }
                sleep(pollingInterval);
            }
        } catch (Exception e) {
            synchronized(lock) {
                log.info("Stopping gris monitor thread");
            }
            log.error("Unknown error", e);
        }
    }
}

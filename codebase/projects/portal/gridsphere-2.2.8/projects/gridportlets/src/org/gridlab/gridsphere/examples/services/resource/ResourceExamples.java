/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceExamples.java,v 1.1.1.1 2007-02-01 20:39:32 kherm Exp $
 */
package org.gridlab.gridsphere.examples.services.resource;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Provides a number of useful examples illustrating how to manage the
 * resources that Grid Portlets makes available to users.
 */
public class ResourceExamples {

    private PortletLog log = SportletLog.getInstance(ResourceExamples.class);
    private ResourceRegistryService resourceRegistryService = null;

    /**
     * Constructs an instance of ResourceExamples.
     * @throws PortletServiceUnavailableException If unable to get required portlet services.
     */
    public ResourceExamples() throws PortletServiceUnavailableException {
        log.info("Creating JobExamples");
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        try {
            resourceRegistryService = (ResourceRegistryService)
                    factory.createPortletService(ResourceRegistryService.class, null, true);
        } catch (PortletServiceNotFoundException e) {
            log.error("Unable to initialize required portlet services", e);
            throw new PortletServiceUnavailableException(e);
        }
    }

    /**
     * Returns all the resources of type <code>HardwareResourceType</code> in the resource registry.
     * @return The list of hardware resources in the resource registry
     * @see HardwareResource
     */
    public List getHardwareResources() {
        return resourceRegistryService.getResources(HardwareResourceType.INSTANCE);
    }

    /**
     * Returns all the resources of type <code>ServiceResourceType</code> in the resource registry.
     * @return The list of service resources in the resource registry
     * @see ServiceResource
     */
    public List getServiceResources() {
        return resourceRegistryService.getResources(ServiceResourceType.INSTANCE);
    }

    /**
     * Returns all the resources of type <code>SoftwareResource</code> in the resource registry.
     * @return The list of software resources in the resource registry
     * @see SoftwareResource
     */
    public List getSoftwareResources() {
        return resourceRegistryService.getResources(SoftwareResourceType.INSTANCE);
    }

    /**
     * Returns all the software resources on the hardware resource with the the given host.
     * Returns an empty list if no hardware resource is found for the given host.
     * @param host The hostname or internet address of the hardware resource
     * @return The list of service resources on the given host
     */
    public List getServiceResources(String host) {
        HardwareResource resource = resourceRegistryService.getHardwareResourceByHost(host);
        if (resource == null) return new ArrayList(0);
        return resource.getServiceResources();
    }

    /**
     * Returns all the software resources on the hardware resource with the the given host.
     * Returns an empty list if no hardware resource is found for the given host.
     * @param host The hostname or internet address of the hardware resource
     * @return The list of software resources
     */
    public List getSoftwareResources(String host) {
        HardwareResource resource = resourceRegistryService.getHardwareResourceByHost(host);
        if (resource == null) return new ArrayList(0);
        return resource.getSoftwareResources();
    }

    /**
     * Returns all the hardware accounts on the hardware resource with the the given host.
     * Returns an empty list if no hardware resource is found for the given host.
     * @param host The hostname or internet address of the hardware resource
     * @return The list of software resources
     */
    public List getHardwareAccounts(String host) {
        HardwareResource resource = resourceRegistryService.getHardwareResourceByHost(host);
        if (resource == null) return new ArrayList(0);
        return resource.getHardwareAccounts();
    }
}

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ResourceRegistryService.java,v 1.1.1.1 2007-02-01 20:40:55 kherm Exp $
 * <p>
 * The resource registry keeps track of all the resources known
 * to a portal instance.
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.portlet.service.PortletService;

import java.util.List;

public interface ResourceRegistryService extends PortletService {

    /**
     * Returns the active resource registry
     *
     * @return The active resource registry
     */
    public ResourceRegistry getResourceRegistry();

    /**
     * Sets the active resource registry
     * @param registry The active resource registry
     * @throws ResourceException If an error occurs
     */
    public void setResourceRegistry(ResourceRegistry registry) throws ResourceException;

    /**
     * Returns the resource type registry
     * @return the resource type registry
     */
    public ResourceTypeRegistry getResourceTypeRegistry();

    /**
     * Returns a list of all resources.
     * @return a list of all resources
     */
    public List getResources();

   /**
     * Returns all the resources of the given type.
     *
     * @param resourcetype the resource type
     * @return a list of resources matching the supplied type
     */
    public List getResources(ResourceType resourcetype);

    /**
     * Returns the resource with the given object id.
     * @param oid the oid of the resource
     * @return the resource belong to that resource
     */
    public Resource getResource(String oid);

    /**
     * Returns the resource with the given distinguished name.
     * @param dn the distinguished name of the resource
     * @return the resource with that distinguished name
     */
    public Resource getResourceByDn(String dn);

    /**
     * Adds/Updates a resource to the list
     * @param resource the resource to save to the resource registry
     * @throws ResourceException If there is an error while saving the resource
     */
    public void saveResource(Resource resource) throws ResourceException;

    /**
     * Deletes a resource known by its internal id
     *
     * @param oid the internal id of the resource
     */
    public void deleteResource(String oid) throws ResourceException;

    /**
     * Returns the hardware resource with a hostname or internet address matching the given string.
     * Returns null if no corresponding hardware resource is found in the resource registry.
     * @param host The hostname or internet address
     * @return The hardware resource with a hostname or internet address matching the given string
     */
    public HardwareResource getHardwareResourceByHost(String host);

    /**
     * Returns the hardware resource with the given hostname.
     * Returns null if no hardware resource exists in the resource
     * registry with the given hostname.
     * @param hostName The hostname
     * @return The hardware resource with the given hostname
     */
    public HardwareResource getHardwareResourceByHostName(String hostName);

    /**
     * Returns the hardware resource with the given internet address.
     * Returns null if no hardware resource exists in the resource
     * registry with the given internet address.
     * @param inetAddress The internet address
     * @return The hardware resource with the given internet address
     */
    public HardwareResource getHardwareResourceByInetAddress(String inetAddress);

    /**
     * Returns the registered resource provider services
     * @return List of <code>ResourceProviderService</code>
     */
    public List getResourceProviderServices();

    /**
     * Registeres the given registered resource provider service
     * @param service The resource provider service
     */
    public void addResourceProviderService(ResourceProviderService service);

    /**
     * Unregisteres the given registered resource provider service
     * @param service The resource provider service
     */
    public void removeResourceProviderService(ResourceProviderService service);

    /**
     * Adds the given resource event listener to list of event listeners
     * @param listener Resource event listener
     */
    public void addResourceEventListener(ResourceEventListener listener);

    /**
     * Remove the given resource event listener to list of event listeners
     * @param listener Resource event listener
     */
    public void removeResourceEventListener(ResourceEventListener listener);

    /**
     * A more efficient way to retrieve child resources from a parent resource
     * for any resource that is stored in the database. This is only useful
     * when it is certain the given parent resource is stored in the database
     * and has not been altered by the caller in any way. When in doubt, use
     * the getChildResouces method on the Resoure interface itself.
     * @param parentResource The parent resource
     * @param resourceType The type of child resource to retrieve from the parent resource
     * @return The list of child resource objects
     */
    public List getChildResources(Resource parentResource, ResourceType resourceType);
}

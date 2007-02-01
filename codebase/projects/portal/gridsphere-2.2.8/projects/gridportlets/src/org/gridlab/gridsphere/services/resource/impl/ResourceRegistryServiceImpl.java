/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ResourceRegistryServiceImpl.java,v 1.1.1.1 2007-02-01 20:41:01 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.services.util.GridPortletsDatabase;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.resource.*;

import javax.servlet.ServletContext;
import java.util.*;

public class ResourceRegistryServiceImpl
        implements PortletServiceProvider, ResourceRegistryService, ResourceEventListener {

    public static PortletLog log = SportletLog.getInstance(ResourceRegistryServiceImpl.class);
    private static boolean inited = false;
    private static GridPortletsDatabase pm = null;
    private ServletContext servletContext = null;
    private static ResourceRegistry resourceRegistry;
    private static ResourceTypeRegistry resourceTypeRegistry;
    private static List resourceProviderServices = new Vector();
    private final Integer lock = new Integer(0);
    private List resourceEventListenerList = new ArrayList();

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        synchronized (this) {
            if (!inited) {

                inited = true;

                pm = GridPortletsDatabase.getInstance();

                // Save servlet context
                servletContext = config.getServletContext();

                // Load resource type registry
                try {
                    resourceTypeRegistry = ResourceTypeRegistry.load(servletContext);
                } catch (Exception e) {
                    throw new PortletServiceUnavailableException(e);
                }

                // Load resource provider services
                loadResourceProviderServices(config);

                // Load resource registry
                try {
                    ResourceRegistry resourceRegistry = ResourceRegistry.load(servletContext);
                    setResourceRegistry(resourceRegistry);
                } catch (Exception e) {
                    throw new PortletServiceUnavailableException(e);
                }
            }
        }
    }

    public void destroy() {
        try {
            deactivateResourceProviderServices();
            pm.destroy();
        } catch (PersistenceManagerException e) {
            log.error("Error: Could not destroy PM" + e);
        }
    }

    public void resourceEventOccured(ResourceEvent event) {
        // Notify listeners
        notifyResourceEventListeners(event);
    }

    public List getResourceProviderServices() {
        return resourceProviderServices;
    }

    public void addResourceProviderService(ResourceProviderService service) {
        int index = findResourceProviderService(service);
        if (index == -1) {
            log.debug("Adding resource provider service " + service.getClass().getName());
            resourceProviderServices.add(service);
            service.addResourceEventListener(this);
            service.activate();
        }
    }

    public void removeResourceProviderService(ResourceProviderService service) {
        int index = findResourceProviderService(service);
        if (index > -1) {
            log.debug("Removing resource provider service " + service.getClass().getName());
            resourceProviderServices.remove(index);
            service.deactivate();
        }
    }

    public int findResourceProviderService(ResourceProviderService service) {
        log.debug("Searching for resource provider service " + service.getClass().getName());
        for (int ii = 0; ii < resourceProviderServices.size();++ii) {
            ResourceProviderService nextService = (ResourceProviderService)resourceProviderServices.get(ii);
            if (nextService.getClass().equals(service.getClass())) {
                log.debug("Found resource provider service " + service.getClass().getName());
                return ii;
            }
        }
        return -1;
    }

    private void loadResourceProviderServices(PortletServiceConfig config) {
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        Enumeration paramNames = config.getInitParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            log.info("Activating resource provider service " + paramName);
            String serviceName = config.getInitParameter(paramName);
            Class serviceClass = null;
            try {
                serviceClass = Class.forName(serviceName);
            } catch (ClassNotFoundException e) {
                log.error("Service class not found: " + serviceName, e);
            } finally {
                try {
                    resourceProviderServices.add(factory.createPortletService(serviceClass, config.getServletContext(), true));
                } catch (PortletServiceException e) {
                    log.error("Error creating portlet service: " + serviceName, e);
                } finally {
                }
            }
        }
    }

    private void activateResourceProviderServices() {
        Iterator iterator = resourceProviderServices.iterator();
        while (iterator.hasNext()) {
            ResourceProviderService service = (ResourceProviderService)iterator.next();
            try {
                service.activate();
            } catch (Exception e) {
                log.error("Unable to activate " + service.getClass().getName(), e);
            }
        }
    }

    private void deactivateResourceProviderServices() {
        Iterator iterator = resourceProviderServices.iterator();
        while (iterator.hasNext()) {
            ResourceProviderService service = (ResourceProviderService)iterator.next();
            try {
                service.deactivate();
            } catch (Exception e) {
                log.error("Unable to deactivate " + service.getClass().getName(), e);
            }
        }
    }

    public ResourceRegistry getResourceRegistry() {
        return resourceRegistry;
    }

    public void setResourceRegistry(ResourceRegistry registry) throws ResourceException {
        synchronized (lock) {
            // Deactivate resource provider services
            deactivateResourceProviderServices();
            // Delete existing resources
            deleteHardwareResources();
            // Reset our registry reference
            resourceRegistry = registry;
            // Load resources from registry into database
            List resourceList = resourceRegistry.getHardwareResources();
            for (Iterator resources = resourceList.iterator(); resources.hasNext();) {
                BaseResource parentResource = (BaseResource) resources.next();
                setParentResourceForChildResource(parentResource);
                saveResource(parentResource);
            }
            // Activate resource provider services
            activateResourceProviderServices();
            // Notify our listeners
            notifyResourceEventListeners(ResourceEvent.INSTANCE);
        }
    }

    void setParentResourceForChildResource(BaseResource parentResource) {
        List childResourceList = parentResource.getChildResources();
        for (Iterator childResources = childResourceList.iterator(); childResources.hasNext();) {
            BaseResource childResource = (BaseResource) childResources.next();
            childResource.setParentResource(parentResource);
            setParentResourceForChildResource(childResource);
        }
    }

    public ResourceTypeRegistry getResourceTypeRegistry() {
        return resourceTypeRegistry;
    }

    public List getResources() {
        return getNeededResources(Resource.class.getName());
    }

    public List getResources(ResourceType resourceType) {
        Class resourceClass = resourceType.getResourceImplementation();
        if (resourceClass == null) {
            resourceClass = resourceType.getResourceClass();
        }
        String className = resourceClass.getName();
        return getNeededResources(className);
    }

    public List getNeededResources(String classname) {
        synchronized (lock) {
            List result = null;
            try {
                String query = "from " + classname + " r";
                result = pm.restoreList(query);
            } catch (PersistenceManagerException e) {
                log.error("FATAL: could not retrieve list of " + classname + " resources!" + e);
                result = new ArrayList(0);
            }
            return result;
        }
    }

    public Resource getResource(String id) {
        synchronized (lock) {
            Resource resource = null;
            try {
                resource = (Resource) pm.restore("from " + BaseResource.class.getName() + " as u where u.oid='" + id + "'");
            } catch (PersistenceManagerException e) {
                log.error("Could not retrieve resource by oid " + id + "!\n" + e);
            }
            return resource;
        }
    }

    public Resource getResourceByDn(String dn) {
        synchronized (lock) {
            Resource resource = null;
            try {
                resource = (Resource) pm.restore("from " + BaseResource.class.getName() + " as r where r.Dn='" + dn + "'");
            } catch (PersistenceManagerException e) {
                log.error("Could not retrieve resource by dn " + dn + "!\n" + e);
            }
            return resource;
        }
    }

    public void saveResource(Resource resource) throws ResourceException {
        synchronized (lock) {
            try {
                if (resource.getOid() != null) {
                    pm.update(resource);
                } else {
                    pm.create(resource);
                }
            } catch (PersistenceManagerException e) {
                log.error("FATAL: could not store resource in database!\n" + e);
                throw new ResourceException(e);
            }
        }
    }

    public void deleteHardwareResources() throws ResourceException {
        synchronized (lock) {
            String query = "from " + HardwareResourceImpl.class.getName();
            try {
                pm.deleteList(query);
            } catch (PersistenceManagerException e) {
                log.error("FATAL: could not delete resource!\n" + e);
                throw new ResourceException(e);
            }
        }
    }

    public void deleteResource(String oid) throws ResourceException {
        synchronized (lock) {
            String query = "from " + BaseResource.class.getName() + " as u where u.oid='" + oid + "'";
            try {
                pm.delete(query);
            } catch (PersistenceManagerException e) {
                log.error("FATAL: could not delete resource!\n" + e);
                throw new ResourceException(e);
            }
        }
    }

    public HardwareResource getHardwareResourceByHost(String host) {
        synchronized (lock) {
            HardwareResource hardwareResource = null;
            String query =
                    "from " + HardwareResourceImpl.class.getName()
                    + " hr where hr.HostName='" + host + "' or hr.InetAddress='" + host + "'";

            try {
                hardwareResource = (HardwareResource) pm.restore(query);
            } catch (PersistenceManagerException e) {
                log.error("Could not retrieve list of hardware resources with hostname or internet address '" + host + "'");
            }
            return hardwareResource;
        }
    }

    public HardwareResource getHardwareResourceByHostName(String hostName) {
        synchronized (lock) {
            HardwareResource hardwareResource = null;
            String query =
                    "from " + HardwareResourceImpl.class.getName() + " hr where hr.HostName='" + hostName + "'";

            try {
                hardwareResource = (HardwareResource) pm.restore(query);
            } catch (PersistenceManagerException e) {
                log.error("Could not retrieve list of hardware resources with hostname '" + hostName + "'");
            }
            return hardwareResource;
        }
    }

    public HardwareResource getHardwareResourceByInetAddress(String inetAddress) {
        synchronized (lock) {
            HardwareResource hardwareResource = null;
            String query =
                    "from " + HardwareResourceImpl.class.getName() + " hr where hr.InetAddress='" + inetAddress + "'";

            try {
                hardwareResource = (HardwareResource) pm.restore(query);
            } catch (PersistenceManagerException e) {
                log.error("Could not retrieve list of hardware resources with InetAddress '" + inetAddress + "'");
            }
            return hardwareResource;
        }
    }

    public void addResourceEventListener(ResourceEventListener listener) {
        synchronized (this) {
            resourceEventListenerList.add(listener);
        }
    }

    public void removeResourceEventListener(ResourceEventListener listener) {
        synchronized (this) {
            resourceEventListenerList.remove(listener);
        }
    }

    public void notifyResourceEventListeners(ResourceEvent event) {
        synchronized (this) {
            for (Iterator listeners = resourceEventListenerList.iterator(); listeners.hasNext();) {
                ResourceEventListener resourceEventListener = (ResourceEventListener) listeners.next();
                resourceEventListener.resourceEventOccured(event);
            }
        }
    }

    public List getChildResources(Resource resource, ResourceType resourceType) {

        synchronized (lock) {
            List result = null;
            try {
                StringBuffer query = new StringBuffer("from ");
                query.append(getClassName(resourceType));
                query.append(" r where r.Dn like '%");
                query.append(resource.getDn());
                query.append("'");
                String queryString = query.toString();
                log.debug("SUPER QUERY " + queryString);
                result = pm.restoreList(queryString);
            } catch (PersistenceManagerException e) {
                log.error("FATAL: could not retrieve list of " + resourceType.getResourceClass().getName() + " resources!" + e);
                result = new ArrayList(0);
            }
            return result;
        }
    }

    public List getChildResources(Resource parentResource, ResourceType resourceType, int level) {

        synchronized (lock) {
            List result = null;
            try {
                StringBuffer query = new StringBuffer("from ");
                query.append(getClassName(resourceType));
                query.append(" r where r.ParentResource");
                for (int ii = 1; ii < level; ++ii) {
                    query.append(".ParentResource");
                }
                query.append(".oid='" + parentResource.getOid() + "''");
                String queryString = query.toString();
                log.debug("SUPER QUERY " + queryString);
                result = pm.restoreList(queryString);
            } catch (PersistenceManagerException e) {
                log.error("FATAL: could not retrieve list of " + resourceType.getResourceClass().getName() + " resources!" + e);
                result = new ArrayList(0);
            }
            return result;
        }
    }

    public Resource getChildResource(Resource resource, String label) {
        synchronized (lock) {
            Resource childResource = null;
            try {
                childResource = (Resource)
                        pm.restore("from " + BaseResource.class.getName() + " as r where r.Label='" + label
                                   + "' and r.ParentResource.oid='" + resource.getOid() + "'");
            } catch (PersistenceManagerException e) {
                log.error("Could not retrieve resource by label " + label + "!\n" + e);
            }
            return childResource;
        }
    }

    private static String getClassName(ResourceType resourceType) {
        Class resourceClass = resourceType.getResourceImplementation();
        if (resourceClass == null) {
            resourceClass = resourceType.getResourceClass();
        }
        String className = resourceClass.getName();
        return className;
    }

}

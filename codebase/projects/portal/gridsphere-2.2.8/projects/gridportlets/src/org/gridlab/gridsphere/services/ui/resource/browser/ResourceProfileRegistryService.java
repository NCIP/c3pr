package org.gridlab.gridsphere.services.ui.resource.browser;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.resource.HardwareResource;

import java.util.*;
import java.lang.reflect.Constructor;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceProfileRegistryService.java,v 1.1.1.1 2007-02-01 20:42:13 kherm Exp $
 */

public class ResourceProfileRegistryService
        extends ResourceProfileRegistry
        implements PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(ResourceProfileRegistryService.class);

    protected HashMap resourceProfileMap = new HashMap();
    protected HashMap resourceTypeMap = new HashMap();

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("ResourceProfileServiceImpl.init()");
        Enumeration paramNames = config.getInitParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String paramValue = config.getInitParameter(paramName);
            Class resourceProfileClass = null;
            try {
                resourceProfileClass = Class.forName(paramValue, true, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e) {
                log.error("Resource browser class not found: " + paramValue);
            } finally {
                ResourceProfile resourceProfile = null;
                try {
                    Class[] parameterTypes
                            = new Class[] {};
                    Object[] arguments = new Object[] {};
                    Constructor constructor = resourceProfileClass.getConstructor(parameterTypes);
                    resourceProfile = (ResourceProfile)constructor.newInstance(arguments);
                    addResourceProfile(resourceProfile);
                } catch (Exception e) {
                    String msg = "Unable to create resource browser " + resourceProfileClass.getName();
                    log.error(msg, e);
                }
            }
        }
    }

    public void destroy() {
    }

    public ResourceProfile getResourceProfile(ResourceType resourceType) {
        List resourceProfiles = getResourceProfiles(resourceType.getResourceClass());
        if (resourceProfiles.size() > 0) {
            return (ResourceProfile)resourceProfiles.get(0);
        } else {
            return null;
        }
    }

    public List getResourceProfiles(ResourceType resourceType) {
        String resourceClassName = resourceType.getResourceClass().getName();
        log.debug("Getting resource browser list for resource " + resourceClassName);
        ArrayList resourceProfileList = (ArrayList)resourceTypeMap.get(resourceClassName);
        if (resourceProfileList == null) {
            log.debug("Creating resource browser list for resource " + resourceClassName);
            resourceProfileList = new ArrayList();
            resourceTypeMap.put(resourceClassName, resourceProfileList);
        }

        return resourceProfileList;
    }

    public List getResourceProfiles(Class resourceClass) {
        String resourceClassName = resourceClass.getName();
        log.debug("Getting resource browser list for resource " + resourceClassName);
        ArrayList resourceProfileList = (ArrayList)resourceTypeMap.get(resourceClassName);
        if (resourceProfileList == null) {
            log.debug("Creating resource browser list for resource " + resourceClassName);
            resourceProfileList = new ArrayList();
            resourceTypeMap.put(resourceClassName, resourceProfileList);
        }

        return resourceProfileList;
    }

    public ResourceProfile getResourceProfile(String key) {
        return (ResourceProfile) resourceProfileMap.get(key);
    }

    public void addResourceProfiles(List resourceProfiles) {
        for (int ii = 0; ii < resourceProfiles.size(); ++ii) {
            ResourceProfile resourceProfile = (ResourceProfile)resourceProfiles.get(ii);
            addResourceProfile(resourceProfile);
        }
    }

    public void addResourceProfile(ResourceProfile resourceProfile) {
        resourceProfileList.add(resourceProfile);
        putResourceTypeMap(resourceProfile);
        putResourceProfileMap(resourceProfile);
    }

    public void removeResourceProfile(ResourceProfile resourceProfile) {
        this.resourceProfileList.remove(resourceProfile);
        removeResourceProfileMap(resourceProfile);
        removeResourceTypeMap(resourceProfile);
    }

    protected void putResourceTypeMap(ResourceProfile resourceProfile) {
        String resourceClassName = resourceProfile.getResourceClassName();
        ArrayList resourceProfileList = (ArrayList)resourceTypeMap.get(resourceClassName);
        if (resourceProfileList == null) {
            log.debug("Creating resource browser list for resource " + resourceClassName);
            resourceProfileList = new ArrayList();
            resourceTypeMap.put(resourceClassName, resourceProfileList);
        }
        log.debug("Adding resource browser " + resourceProfile.getKey() + " for resource " + resourceClassName);
        resourceProfileList.add(resourceProfile);
    }

    protected void putResourceProfileMap(ResourceProfile resourceProfile) {
        String key = resourceProfile.getKey();
        resourceProfileMap.put(key, resourceProfile);
    }

    protected void removeResourceTypeMap(ResourceProfile resourceProfile) {
        String className = resourceProfile.getResourceClassName();
        ArrayList resourceTypeList = (ArrayList)resourceTypeMap.get(className);
        if (resourceTypeList != null) {
            int index = findInResourceTypeList(resourceTypeList, resourceProfile);
            if (index > -1) {
                resourceTypeList.remove(index);
            }
        }
    }

    protected void removeResourceProfileMap(ResourceProfile resourceProfile) {
        String key = resourceProfile.getKey();
        resourceProfileMap.remove(key);
    }

    protected static int findInResourceTypeList(List resourceTypeList, ResourceProfile resourceProfile) {
        int index = -1;
        for (int ii = 0; ii < resourceTypeList.size(); ++ii) {
            ResourceProfile nextProfile = (ResourceProfile) resourceTypeList.get(ii);
            if (nextProfile.equals(resourceProfile)) {
                index = ii;
                break;
            }
        }
        return index;
    }

    public static List getSupportedResources(ResourceProfile profile, List hardwareResourceList) {

        List filteredList = null;

        // Check if our profile requires hardware resources to contain certain (info) resouces
        List requiredResourceList = profile.getRequiredResourceList();
        log.debug(profile.getKey() + " requires " + requiredResourceList.size() + " resources");
        if (requiredResourceList.size() == 0) {
            filteredList = hardwareResourceList;
        } else {
            filteredList = new ArrayList();
            for (Iterator hardwareResources = hardwareResourceList.iterator(); hardwareResources.hasNext();) {
                HardwareResource hardwareResource = (HardwareResource) hardwareResources.next();
                for (Iterator requiredResources = requiredResourceList.iterator(); requiredResources.hasNext();) {
                    Class resourceClass = (Class) requiredResources.next();
                    if (hardwareResource.hasChildResources(resourceClass)) {
                        filteredList.add(hardwareResource);
                    }
                }
            }
        }

        return filteredList;
    }

    public static boolean supports(HardwareResource resource, ResourceProfile profile) {
        String host = resource.getHost();

        List requiredResourceList = profile.getRequiredResourceList();
        log.debug(profile.getKey() + " requires " + requiredResourceList.size() + " resources");
        if (requiredResourceList.size() >0) {
            for (Iterator requiredResources = requiredResourceList.iterator(); requiredResources.hasNext();) {
                Class resourceClass = (Class) requiredResources.next();
                if (!resource.hasChildResources(resourceClass)) {
                    log.debug(host + " does not have child resource " + resourceClass.getName());
                    return false;
                }
                log.debug(host + " has child resource " + resourceClass.getName());
            }
        }
        // Otherwise we assume yes.
        return true;
    }
}

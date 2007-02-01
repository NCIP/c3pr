/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceComponent.java,v 1.1.1.1 2007-02-01 20:42:12 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser;

import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.HardwareResource;

import javax.portlet.PortletException;
import java.util.Map;

public class ResourceComponent extends ActionComponent {

    private transient static PortletLog log = SportletLog.getInstance(ResourceComponent.class);

    public final static String RESOURCE_OID_LIST_PARAM = "resourceOidList";
    public final static String RESOURCE_PARAM = "resource";
    public final static String RESOURCE_OID_PARAM = "resourceOid";
    public final static String RESOURCE_DN_PARAM = "resourceDn";
    public final static String RESOURCE_TYPE_PARAM = "resourceType";
    public final static String RESOURCE_PROFILE_PARAM = "resourceProfile";
    public final static String RESOURCE_PROFILE_KEY_PARAM = "resourceProfileKey";
    public final static String PARENT_OID_PARAM = "parentOid";
    public final static String PARENT_DN_PARAM = "parentDn";
    public final static String PARENT_TYPE_PARAM = "parentType";
    public final static String PARENT_PROFILE_PARAM = "parentProfile";

    // Portlet services
    protected ResourceRegistryService resourceRegistryService = null;
    protected ResourceProfileRegistryService resourceProfileRegistryService = null;

    private String resourceOid = null;
    private String resourceDn = null;
    private ResourceType resourceType = ResourceType.INSTANCE;
    private String selectedParentOid = null;
    private String selectedParentDn = null;
    private ResourceType selectedParentType = ResourceType.INSTANCE;

    public ResourceComponent(ActionComponentFrame container, String compName) {
        super(container, compName);
        try {
            resourceRegistryService = (ResourceRegistryService)
                    getPortletService(ResourceRegistryService.class);
            resourceProfileRegistryService = (ResourceProfileRegistryService)
                    getPortletService(ResourceProfileRegistryService.class);
        } catch (PortletException e) {
            log.error("Unable to get required services");
        }
    }

    public void onInit()
            throws PortletException {
        super.onInit();
    }

    public void onStore() throws PortletException {
        // Store current resource info
        setPageAttribute(RESOURCE_OID_PARAM, getResourceOid());
        setPageAttribute(RESOURCE_DN_PARAM, getResourceDn());
        setPageAttribute(RESOURCE_TYPE_PARAM, getResourceType());
        // Store parent resource info
        setPageAttribute(PARENT_OID_PARAM, getSelectedParentOid());
        setPageAttribute(PARENT_DN_PARAM, getSelectedParentDn());
        setPageAttribute(PARENT_TYPE_PARAM, getSelectedParentType());
    }

    public String getResourceOid() {
        return resourceOid;
    }

    public void setResourceOid(String resourceOid) {
        this.resourceOid = resourceOid;
    }

    public String getResourceDn() {
        return resourceDn;
    }

    public void setResourceDn(String resourceDn) {
        this.resourceDn = resourceDn;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getSelectedParentOid() {
        return selectedParentOid;
    }

    public void setSelectedParentOid(String selectedParentOid) {
        this.selectedParentOid = selectedParentOid;
    }

    public String getSelectedParentDn() {
        return selectedParentDn;
    }

    public void setSelectedParentDn(String selectedParentDn) {
        this.selectedParentDn = selectedParentDn;
    }

    public ResourceType getSelectedParentType() {
        return selectedParentType;
    }

    public void setSelectedParentType(ResourceType selectedParentType) {
        this.selectedParentType = selectedParentType;
    }

    public ActionComponentFrame createActionComponentFrame(String beanId) {
        ResourceComponentFrame bean = new ResourceComponentFrame(this, beanId);
        actionComponentBeans.put(beanId, bean);
        return bean;
    }


    public Resource getResource() {
        if (resourceOid == null) {
            return null;
        } else {
            return resourceRegistryService.getResource(resourceOid);
        }
    }

    public Resource getResource(Map parameters) {
        Resource resource = getResource(resourceRegistryService, parameters);
        if (resource != null) {
            // Save resource oid and resource type for use in jsps
            setResourceOid(resource.getOid());
            setResourceDn(resource.getDn());
            setResourceType(resource.getResourceType());
        }
        return resource;
    }

    public static Resource getResource(ResourceRegistryService resourceRegistryService, Map parameters) {
        log.debug("getResource()");
        // Get resource parameter
        Resource resource = (Resource)parameters.get(RESOURCE_PARAM);
        if (resource == null) {
            log.debug("Resource param is null");
            // Get resource dn parameter...
            String resourceDn = (String)parameters.get(RESOURCE_DN_PARAM);
            log.debug("Resource dn param is " + resourceDn);
            if (resourceDn != null) {
                resource = resourceRegistryService.getResourceByDn(resourceDn);
                parameters.put(RESOURCE_PARAM, resource);
            } else {
                // Get resource oid parameter...
                String resourceOid = (String)parameters.get(RESOURCE_OID_PARAM);
                log.debug("Resource oid param is " + resourceOid);
                if (resourceOid != null) {
                    resource = resourceRegistryService.getResource(resourceOid);
                    parameters.put(RESOURCE_PARAM, resource);
                }
            }
        }
        return resource;
    }

    public static ResourceType getResourceType(Map parameters) {
        return (ResourceType)parameters.get(RESOURCE_TYPE_PARAM);
    }

    public static ResourceProfile getResourceProfile(String key) {
        ResourceProfile profile = null;
        try {
            PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
            ResourceProfileRegistryService resourceProfileRegistryService = (ResourceProfileRegistryService)
                    serviceFactory.createPortletService(ResourceProfileRegistryService.class, null, true);
            profile = resourceProfileRegistryService.getResourceProfile(key);
        } catch (PortletServiceException e) {
            log.error("Unable to get resource browser registry service", e);
        }
        return profile;
    }

    public static ResourceProfile getResourceProfile(ResourceType type) {
        try {
            PortletServiceFactory serviceFactory = SportletServiceFactory.getInstance();
            ResourceProfileRegistryService resourceProfileRegistryService = (ResourceProfileRegistryService)
                    serviceFactory.createPortletService(ResourceProfileRegistryService.class, null, true);
            return resourceProfileRegistryService.getResourceProfile(type);
        } catch (PortletServiceException e) {
            log.error("Unable to get resource browser registry service", e);
        }
        return null;
    }

    public Resource getParentResource() throws PortletException {
        ResourceComponent component = (ResourceComponent)container.getParentComponent();
        selectedParentDn = component.getResourceDn();
        selectedParentType = component.getResourceType();
        if (selectedParentDn == null) {
            log.debug("Parent resource dn is null");
            return null;
        } else {
            log.debug("Parent resource dn is " + selectedParentDn);
            return resourceRegistryService.getResourceByDn(selectedParentDn);
        }
    }
}

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: BaseResource.java,v 1.1.1.1 2007-02-01 20:41:00 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.ResourceAttribute;
import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.services.util.GridPortletsDatabase;

import java.util.*;

public abstract class BaseResource implements Resource {

    private static PortletLog log = SportletLog.getInstance(BaseResource.class);

    protected String oid = null;
    protected String dn = "";
    protected String label = "";
    protected String description = "";
    protected boolean isAvailable;
    protected Map resourceAttributeMap = new HashMap();
    protected Resource parentResource = null;
    protected List childResourceList = new ArrayList();
    protected ResourceType resourceType = ResourceType.INSTANCE;

    public BaseResource() {
    }

    public BaseResource(Resource parentResource) {
        setParentResource(parentResource);
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    private String makeDn() {
        String dn = resourceType.getID() + '=' + getLabel();
        if (getParentResource() != null) {
            dn += ',' + getParentResource().getDn();
        }
        return dn;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        if (label == null) {
            dn = null;
        } else {
            dn = makeDn();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        description = desc;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    protected void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public boolean isResourceType(ResourceType r) {
        return resourceType.isResourceType(r);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Resource getParentResource() {
        return parentResource;
    }

    public void setParentResource(Resource parentResource) {
        this.parentResource = parentResource;
        dn = makeDn(); // Reset dn...
    }

    public List getChildResources() {
        return childResourceList;
    }

    public void setChildResources(List resources) {
        childResourceList = resources;
    }

    public List getChildResources(ResourceType type) {
        List resourceList = new Vector(childResourceList.size());
        for (Iterator childResources = childResourceList.iterator(); childResources.hasNext();) {
            Resource resource = (Resource) childResources.next();
            if (resource.isResourceType(type)) {
                resourceList.add(resource);
            }
        }
        return resourceList;
    }

    public List getChildResources(Class resourceClass) {
        List resourceList = new Vector(childResourceList.size());
        for (Iterator childResources = childResourceList.iterator(); childResources.hasNext();) {
            Resource resource = (Resource) childResources.next();
            if (resource.getResourceType().isResourceType(resourceClass)) {
                resourceList.add(resource);
            }
        }
        return resourceList;
    }

    public Resource getChildResource(ResourceType type) {
        log.debug("Looking for child resource type " + type.getID() + " on " + getDn());
        for (Iterator childResources = childResourceList.iterator(); childResources.hasNext();) {
            Resource nextResource = (Resource) childResources.next();
            log.debug("Checking child resource " + nextResource.getDn() + " with resource type " + nextResource.getResourceType().getID());
            if (nextResource.isResourceType(type)) {
                log.debug("Yes...");
                return nextResource;
            }
            log.debug("No...");
        }
        return null;
    }

    public Resource getChildResource(Class resourceClass) {
        for (Iterator childResources = childResourceList.iterator(); childResources.hasNext();) {
            Resource nextResource = (Resource) childResources.next();
            if (nextResource.getResourceType().isResourceType(resourceClass)) {
                return nextResource;
            }
        }
        return null;
    }

    public boolean hasChildResources(ResourceType type) {
        for (Iterator childResources = childResourceList.iterator(); childResources.hasNext();) {
            Resource nextResource = (Resource) childResources.next();
            if (nextResource.isResourceType(type)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasChildResources(Class resourceClass) {
        for (Iterator childResources = childResourceList.iterator(); childResources.hasNext();) {
            Resource nextResource = (Resource) childResources.next();
            if (nextResource.getResourceType().isResourceType(resourceClass)) {
                return true;
            }
        }
        return false;
    }

    public void addChildResource(Resource resource) {
        ((BaseResource) resource).setParentResource(this);
        childResourceList.add(resource);
    }

    public void addChildResources(List resourceList) {
        for (Iterator resources = resourceList.iterator(); resources.hasNext();) {
            BaseResource resource = (BaseResource) resources.next();
            resource.setParentResource(this);
            childResourceList.add(resource);
        }
    }

    public void clearChildResources() {
        childResourceList.clear();
    }

    public void removeChildResource(Resource resource) {
        int ii = 0;
        for (Iterator childResources = childResourceList.iterator(); childResources.hasNext(); ++ii) {
            BaseResource nextResource = (BaseResource) childResources.next();
            if (resource.getOid().equals(nextResource.getOid())) {
                nextResource.setParentResource(null);
                break;
            }
        }
        if (ii < childResourceList.size()) {
            childResourceList.remove(ii);
        }
    }

    public void removeChildResources(ResourceType resourceType) {
        List resourceList = new Vector(childResourceList.size());
        for (Iterator childResources = childResourceList.iterator(); childResources.hasNext();) {
            Resource resource = (Resource) childResources.next();
            if (resource.getResourceType().isResourceType(resourceType)) {
                resourceList.add(resource);
            }
        }
        childResourceList.removeAll(resourceList);
    }

    public void copy(Resource resource) {
        setDn(resource.getDn());
        setLabel(resource.getLabel());
        setDescription(resource.getDescription());
        clearChildResources();
        addChildResources(resource.getChildResources());
    }

    public void save() throws ResourceException {
        GridPortletsDatabase pm = GridPortletsDatabase.getInstance();
        save(pm);
    }

    protected void save(GridPortletsDatabase pm) throws ResourceException {
        try {
            if (getOid() == null) {
                pm.create(this);
            } else {
                pm.update(this);
            }
        } catch (PersistenceManagerException e) {
            log.error("Unable to save resource " + oid, e);
        }
    }

    public void delete() throws ResourceException {
        GridPortletsDatabase pm = GridPortletsDatabase.getInstance();
        // Delete this object
        try {
            pm.delete(this);
        } catch (PersistenceManagerException e) {
            log.error("Unable to delete resource " + oid, e);
        }
    }

    public Map getResourceAttributeMap() {
        return resourceAttributeMap;
    }

    public void setResourceAttributeMap(Map resourceAttributes) {
        resourceAttributeMap = resourceAttributes;
    }

    public List getResourceAttributes() {
        return new ArrayList(resourceAttributeMap.values());
    }

    public void setResourceAttributes(List attributeList) {
        resourceAttributeMap.clear();
        for (Iterator attributes = attributeList.iterator(); attributes.hasNext();) {
            ResourceAttribute attribute = (ResourceAttribute) attributes.next();
            resourceAttributeMap.put(attribute.getName(), attribute);
        }
    }

    public ResourceAttribute getResourceAttribute(String name) {
        return (ResourceAttribute) resourceAttributeMap.get(name);
    }

    public void putResourceAttribute(ResourceAttribute attr) {
        resourceAttributeMap.put(attr.getName(), attr);
    }

    public ResourceAttribute putResourceAttribute(String name, String value) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            attribute = new ResourceAttribute(name, value);
            resourceAttributeMap.put(name, attribute);
        } else {
            attribute.setValue(value);
        }
        return attribute;
    }

    public ResourceAttribute removeResourceAttribute(String name) {
        return (ResourceAttribute) resourceAttributeMap.remove(name);
    }

    public void clearResourceAttributes() {
        resourceAttributeMap.clear();
    }

    public String getResourceAttributeValue(String name) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    public String getResourceAttributeValue(String name, String defaultValue) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            return defaultValue;
        }
        return attribute.getValue();
    }

    public void setResourceAttributeValue(String name, String value) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            attribute = new ResourceAttribute(name, value);
            resourceAttributeMap.put(name, attribute);
        } else {
            attribute.setValue(value);
        }
    }

    public List getResourceAttributeValueAsList(String name, String delim) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            return new ArrayList(0);
        }
        return attribute.getValueAsList(delim);
    }

    public void setResourceAttributeValueAsList(String name, List valueList, String delim) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            attribute = new ResourceAttribute();
            attribute.setName(name);
            attribute.setValueAsList(valueList, delim);
            resourceAttributeMap.put(name, attribute);
        } else {
            attribute.setValueAsList(valueList, delim);
        }
    }

    public boolean getResourceAttributeAsBool(String name, boolean defaultValue) {
        ResourceAttribute attr = getResourceAttribute(name);
        if (attr == null) {
            return defaultValue;
        } else {
            return attr.getValueAsBool();
        }
    }

    public void setResourceAttributeAsBool(String name, boolean enabled) {
        ResourceAttribute attr = getResourceAttribute(name);
        if (attr == null) {
            attr = new ResourceAttribute(name, String.valueOf(enabled));
            resourceAttributeMap.put(name, attr);
        } else {
            attr.setValue(String.valueOf(enabled));
        }
    }

    public int getResourceAttributeAsInt(String name, int defaultValue) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            return defaultValue;
        } else {
            return attribute.getValueAsInt(defaultValue);
        }
    }

    public void setResourceAttributeAsInt(String name, int value) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            attribute = new ResourceAttribute(name, String.valueOf(value));
            resourceAttributeMap.put(name, attribute);
        } else {
            attribute.setValue(String.valueOf(value));
        }
    }

    public long getResourceAttributeAsLng(String name, long defaultValue) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            return defaultValue;
        } else {
            return attribute.getValueAsLng(defaultValue);
        }
    }

    public void setResourceAttributeAsLng(String name, long value) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            attribute = new ResourceAttribute(name, String.valueOf(value));
            resourceAttributeMap.put(name, attribute);
        } else {
            attribute.setValue(String.valueOf(value));
        }
    }

    public double getResourceAttributeAsDbl(String name, double defaultValue) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            return defaultValue;
        } else {
            return attribute.getValueAsDbl(defaultValue);
        }
    }

    public void setResourceAttributeAsDbl(String name, double value) {
        ResourceAttribute attribute = getResourceAttribute(name);
        if (attribute == null) {
            attribute = new ResourceAttribute(name, String.valueOf(value));
            resourceAttributeMap.put(name, attribute);
        } else {
            attribute.setValue(String.valueOf(value));
        }
    }
}

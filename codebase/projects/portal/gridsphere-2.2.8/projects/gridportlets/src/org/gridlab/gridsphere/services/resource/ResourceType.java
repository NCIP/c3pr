/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ResourceType.java,v 1.1.1.1 2007-02-01 20:40:55 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.util.GridPortletsResourceBundle;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceType {

    public static final ResourceType INSTANCE = new ResourceType();
    private static PortletLog log = SportletLog.getInstance(ResourceType.class);

    private String id = null;
    private Class resourceClass = null;
    private Class resourceImplementation = null;
    private HashMap superTypeIdMap = new HashMap();
    private HashMap superTypeClassMap = new HashMap();

    protected ResourceType() {
        this.id = "Resource";
        this.resourceClass = Resource.class;
    }

    protected ResourceType(String id, Class resourceClass) {
        //log.debug("Constructing resource type " + id + "," + resourceClass.getName());
        this.id = id;
        this.resourceClass = resourceClass;
        superTypeIdMap.put(INSTANCE.getID(), INSTANCE);
        superTypeClassMap.put(INSTANCE.getResourceClass(), INSTANCE);
    }

    protected ResourceType(String id, Class resourceClass, Class resourceImplementation) {
        //log.debug("Constructing resource type " + id + "," + resourceClass.getName());
        this.id = id;
        this.resourceClass = resourceClass;
        this.resourceImplementation = resourceImplementation;
        superTypeIdMap.put(INSTANCE.getID(), INSTANCE);
        superTypeClassMap.put(INSTANCE.getResourceClass(), INSTANCE);
    }

    public String getID() {
        return id;
    }

    public Class getResourceClass() {
        return resourceClass;
    }

    public Class getResourceImplementation() {
        return resourceImplementation;
    }

    protected void setResourceImplementation(Class resourceImplementation) {
        this.resourceImplementation = resourceImplementation;
    }

    public Iterator getSuperTypes() {
        return superTypeIdMap.values().iterator();
    }

    protected void addSuperType(ResourceType superResourceType) {
        String superResourceId = superResourceType.getID();
        Class superResourceClass = superResourceType.getResourceClass();
        log.debug("Adding super type " + superResourceId + "," + superResourceClass.getName());
        // We do this in order to prevent circular dependencies...
        if (superResourceClass.isAssignableFrom(resourceClass)) {
            log.debug("Super type is assignable from " + id + "," + resourceClass);
            superTypeIdMap.put(superResourceId, superResourceType);
            superTypeClassMap.put(superResourceClass, superResourceType);
            Iterator superSuperTypes = superResourceType.getSuperTypes();
            while(superSuperTypes.hasNext()) {
                ResourceType superSuperType = (ResourceType)superSuperTypes.next();
                addSuperType(superSuperType);
            }
        }
    }

    public boolean equalsResourceType(ResourceType type) {
        return type.id.equals(id);
    }
    public boolean isResourceType(ResourceType resourceType) {
        return (resourceType.getID().equals(id) || superTypeIdMap.containsKey(resourceType.getID()));
    }

    public boolean isResourceType(Resource resource) {
        Class resourceClass = resource.getClass();
        return (this.resourceClass.equals(resourceClass) || superTypeClassMap.containsKey(resourceClass));
    }

    public boolean isResourceType(Class resourceClass) {
        return (this.resourceClass.equals(resourceClass) || superTypeClassMap.containsKey(resourceClass));
    }

    public String getLabel(Resource resource, Locale locale) {
        String label = resource.getLabel();
        log.debug("Resource " + resource.getDn() + " Label = " + label);
        if (label == null || label.equals("")) {
            label = getName(locale);
        }
        return label;
    }

    public String getDescription(Locale locale, Resource resource) {
        String description = resource.getDescription();
        if (description == null || description.equals("")) {
            description = getLabel(locale);
        }
        return description;
    }

    public String getName(Locale locale) {
        String className = getClass().getName();
        return getResourceString(locale, className + ".name", className);
    }

    public String getLabel(Locale locale) {
        String className = getClass().getName();
        return getResourceString(locale, className + ".label", className);
    }

    public String getResourceString(Locale locale, String key, String defaultValue) {
        String value = null;
        try {
            ResourceBundle bundle = getResourceBundle(locale);
            value = bundle.getString(key);
            if (value == null) {
                value = defaultValue;
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return value;
    }

    public ResourceBundle getResourceBundle(Locale locale) {
        return GridPortletsResourceBundle.getResourceBundle(locale);
    }
}

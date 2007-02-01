/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Resource.java,v 1.1.1.1 2007-02-01 20:40:51 kherm Exp $
 * <p>
 * A resource is anything that can be utilized, in particular by
 * users and portlet services.
 */
package org.gridlab.gridsphere.services.resource;

import java.util.List;

public interface Resource {

    /**
     * Returns the object id of this resource.
     * @return The resource oid
     */
    public String getOid();

    /**
     * Returns the distinguished name of this resource.
     * @return The resource dn
     */
    public String getDn();

    /**
     * Returns the type of resource this is.
     * @return The resource type
     */
    public ResourceType getResourceType();

    /**
     * Returns true if this resource is given type.
     * @param r The resource type
     * @return True if is a resource of the given type, false otherwise
     */
    public boolean isResourceType(ResourceType r);

    /**
     * Returns a label for this resource.
     * @return The resource label
     */
    public String getLabel();

    /**
     * Sets a label for this resource.
     * @param label The resource label
     */
    public void setLabel(String label);

    /**
     * Returns a description for this resource.
     * @return The resource description
     */
    public String getDescription();

    /**
     * Sets a description for this resource.
     * @param description The resource description
     */
    public void setDescription(String description);

    /**
     * Returns true if this resource is available for use.
     * @return True if available, false otherwise
     */
    public boolean isAvailable();

    /**
     * Sets whether this resource is available for use.
     * @param available True if available, false otherwise
     */
    public void setAvailable(boolean available);

    /**
     * Returns this resource's parent resource.
     * @return The parent resource
     */
    public Resource getParentResource();

    /**
     * Returns this resource's child resources.
     * @return The child resources
     * @see Resource
     */
    public List getChildResources();

    /**
     * Returns this resource's child resources of the given type.
     * @return The child resources of the given type
     */
    public List getChildResources(ResourceType type);

    /**
     * Returns this resource's child resources of the given class.
     * @return The child resources of the given resource class
     */
    public List getChildResources(Class resourceClass);

    /**
     * Returns the default (first) child resource of the given type.
     * @return The child resource
     */
    public Resource getChildResource(ResourceType type);

    /**
     * Returns the default child resource of the given type.
     * @return The child resource
     */
    public Resource getChildResource(Class resourceClass);

    /**
     * Returns whether thie resource has a child resource of the given type.
     * @return True if has, false otherwise
     */
    public boolean hasChildResources(ResourceType type);

    /**
     * Returns whether thie resource has a child resource of the given class.
     * @return True if has, false otherwise
     */
    public boolean hasChildResources(Class resourceClass);

    /**
     * Adds the given resource to this resources's child resources.
     * @param resource The child resource to add
     */
    public void addChildResource(Resource resource);

    /**
     * Adds the given resources to this resources's child resources.
     * @param resources The child resources to add
     */
    public void addChildResources(List resources);

    /**
     * Removes the given resource from this resources's child resources.
     * @param resource The child resource to remove
     */
    public void removeChildResource(Resource resource);

    /**
     * Removes the all the child resources of the given type from this resource.
     * @param resourceType The resource type
     */
    public void removeChildResources(ResourceType resourceType);

    /**
     * Removes all the child resources on this resource.
     */
    public void clearChildResources();

    /**
     * Copies properties and attributes of the given resource to this resource.
     * @param resource The resource to copy
     */
    public void copy(Resource resource);

    /**
     * Saves this object to the database
     * @throws ResourceException
     */
    public void save() throws ResourceException;

    /**
     * Deletes this object from the database
     * @throws ResourceException
     */
    public void delete() throws ResourceException;

    /**
     * Returns this resource's attributes as a list of
     * resource atttribute objects.
     * In general, these resource attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the resource interface.
     * @return List of resource attributes
     */
    public List getResourceAttributes();

    /**
     * Returns the first resource attribute with the given name.
     * In general, these resource attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the resource interface.
     * @param name The resource attribute name
     */
    public ResourceAttribute getResourceAttribute(String name);

    /**
     * Puts the given resource attribute to this resource.
     * In general, these resource attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the resource interface.
     * @param attr The resource attribute
     */
    public void putResourceAttribute(ResourceAttribute attr);

    /**
     * Sets the given resource name value pair as a resource attribute.
     * Returns the resource attribute that the method creates.
     * In general, these resource attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the resource interface.
     * @param name The name of the task attribute
     * @param value The value of the task attribute
     * @return The resulting resource attribute
     */
    public ResourceAttribute putResourceAttribute(String name, String value);

    /**
     * Removes the given resource attribute from this resource.
     * In general, these resource attribute methods are provided for users
     * that wish to specify information in addition to the information
     * represented by the resource interface.
     * @param name The name of the attribute
     */
    public ResourceAttribute removeResourceAttribute(String name);

    /**
     * Clears this resource's attributes.
     */
    public void clearResourceAttributes();

    /**
     * Returns the string value of the first resource attribute with
     * the given name, null if no attribute exists with the
     * given name.
     * @param name Name of resource attribute
     */
    public String getResourceAttributeValue(String name);

    public String getResourceAttributeValue(String name, String defaultValue);

    public void setResourceAttributeValue(String name, String value);

    public List getResourceAttributeValueAsList(String name, String delim);

    public void setResourceAttributeValueAsList(String name, List valueList, String delim);

    public boolean getResourceAttributeAsBool(String name, boolean defaultValue);

    public void setResourceAttributeAsBool(String name, boolean enabled);

    public int getResourceAttributeAsInt(String name, int defaultValue);

    public void setResourceAttributeAsInt(String name, int value);

    public long getResourceAttributeAsLng(String name, long defaultValue);

    public void setResourceAttributeAsLng(String name, long value);

    public double getResourceAttributeAsDbl(String name, double defaultValue);

    public void setResourceAttributeAsDbl(String name, double value);
}

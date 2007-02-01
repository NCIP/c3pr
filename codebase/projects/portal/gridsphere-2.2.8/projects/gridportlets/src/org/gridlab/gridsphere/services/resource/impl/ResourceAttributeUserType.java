package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.services.resource.ResourceAttribute;
import org.gridlab.gridsphere.services.util.impl.StringAttributeUserType;
import org.gridlab.gridsphere.services.util.StringAttribute;

/**
 * To change this template use File | Settings | File Templates.
 */
public class ResourceAttributeUserType extends StringAttributeUserType {

    public Class returnedClass() {
        return ResourceAttribute.class;
    }

    public Object deepCopy(Object value) {
        if (value == null) return null;
        return new ResourceAttribute((ResourceAttribute)value);
    }

    protected StringAttribute createStringAttribute(String nameValuePair) {
        return  new ResourceAttribute(nameValuePair);
    }
}

package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.util.StringAttribute;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceAttribute.java,v 1.1.1.1 2007-02-01 20:40:51 kherm Exp $
 * <p>
 * Describes an attribute of a resource
 */

public class ResourceAttribute extends StringAttribute {

    public ResourceAttribute() {

    }

    public ResourceAttribute(ResourceAttribute attribute) {
        super(attribute);
    }

    public ResourceAttribute(String nameEqualsValue) {
        super(nameEqualsValue);
    }

    public ResourceAttribute(String name, String value) {
        super(name, value);
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass().equals(ResourceAttribute.class)) {
            ResourceAttribute attribute = (ResourceAttribute)o;
            return getNameEqualsValue().equals(attribute.getNameEqualsValue());
        }
        return false;
    }
}

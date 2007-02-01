package org.gridlab.gridsphere.services.resource;

import java.util.ArrayList;
import java.util.List;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: ResourceTypeDescription.java,v 1.1.1.1 2007-02-01 20:40:55 kherm Exp $
 */

/**
 * This Class describes a resourcetype with some addtional information which are needed in portlets.
 *
 */
public class ResourceTypeDescription {

    private String name = null;
    private String id = null;
    private String type = null;
    private String classname = null;
    private List attributes = new ArrayList();

    public List getAttributes() {
        return attributes;
    }

    public void setAttributes(List attributes) {
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String toString() {
        return "Number of Attributes :" + attributes.size();
    }
}

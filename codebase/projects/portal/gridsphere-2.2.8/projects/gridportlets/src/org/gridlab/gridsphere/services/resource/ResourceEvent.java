/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceEvent.java,v 1.1.1.1 2007-02-01 20:40:53 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

public class ResourceEvent {

    public static final ResourceEvent INSTANCE = new ResourceEvent();

    public static final String NAME = "ResourceEvent";

    private String name = NAME;

    public ResourceEvent() {
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}

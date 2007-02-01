package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.services.resource.*;

/**
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseSoftwareResource.java,v 1.1.1.1 2007-02-01 20:41:01 kherm Exp $
 */

public class BaseSoftwareResource
        extends BaseResource
        implements SoftwareResource {

    protected String path = "";

    public BaseSoftwareResource() {
        super();
        setResourceType(SoftwareResourceType.INSTANCE);
    }

    public BaseSoftwareResource(HardwareResource resource) {
        super(resource);
        setResourceType(SoftwareResourceType.INSTANCE);
    }

    public HardwareResource getHardwareResource() {
        return (HardwareResource)getParentResource();
    }

    public void setHardwareResource(HardwareResource resource) {
        setParentResource(resource);
    }

    public String getHost() {
        if (getParentResource() == null) {
            return "";
        }
        return ((HardwareResource)getParentResource()).getHost();
    }

    public String getHostName() {
        if (getParentResource() == null) {
            return "";
        }
        return ((HardwareResource)getParentResource()).getHostName();
    }

    public String getInetAddress() {
        if (getParentResource() == null) {
            return "";
        }
        return ((HardwareResource)getParentResource()).getInetAddress();
    }

    public void copy(Resource resource) {
        super.copy(resource);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

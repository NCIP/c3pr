/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: BaseServiceResource.java,v 1.1.1.1 2007-02-01 20:41:00 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.services.common.Location;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

public abstract class BaseServiceResource extends BaseResource implements ServiceResource {

    private static PortletLog log = SportletLog.getInstance(BaseServiceResource.class);

    private String protocol = ServiceLocation.DEFAULT_PROTOCOL;
    private String port = "";
    private String servicePath = "";

    public BaseServiceResource() {
        super();
        //log.debug("Constructing service resource");
        setResourceType(ServiceResourceType.INSTANCE);
    }

    public void init() {}

    public void destroy() {}

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public Location getLocation() {
        int portNum = 0;
        String port = getPort();
        log.debug("Creating a service location for " + getDn() +  " " + getProtocol() + "://" + getHost() + ":" + port);
        if (port != null && !port.equals("")) {
            try {
                portNum = Integer.parseInt(port);
            } catch (Exception e) {
                log.warn("Invalid port number " + port);
            }
        }
        return new ServiceLocation(getProtocol(), getHost(), portNum, null);
    }

    public HardwareResource getHardwareResource() {
        return (HardwareResource)getParentResource();
    }

    public void setHardwareResource(HardwareResource hardwareResource) {
        setParentResource(hardwareResource);
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
        ServiceResource serviceResource = (ServiceResource)resource;
        setPort(serviceResource.getPort());
        setProtocol(serviceResource.getProtocol());
        setServicePath(serviceResource.getServicePath());
    }
}

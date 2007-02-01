/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ServiceResource.java,v 1.1.1.1 2007-02-01 20:40:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.common.Location;

public interface ServiceResource extends Resource {

    public void init();

    public void destroy();

    public String getProtocol();

    public void setProtocol(String protocol);

    public String getHost();

    public String getHostName();

    public String getInetAddress();

    public String getPort();

    public void setPort(String port);

    public String getServicePath();

    public void setServicePath(String servicePath);

    public HardwareResource getHardwareResource();

    public void setHardwareResource(HardwareResource hardware);

    public boolean isAvailable();

    /**
     * Returns this resource's location.
     * @return The location
     */
    public Location getLocation();
}

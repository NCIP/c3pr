/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SoftwareResource.java,v 1.1.1.1 2007-02-01 20:40:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.resource.Resource;

public interface SoftwareResource extends Resource {

    /**
     * Returns the parent hardware resource.
     * @return The parent hardware resource
     */
    public HardwareResource getHardwareResource();

    /**
     * Sets the parent hardware resource.
     * @param resource The parent hardware resource
     */
    public void setHardwareResource(HardwareResource resource);

    /**
     * Returns the path of the software resource
     * @return The path to this resource
     */
    public String getPath();

    /**
     * Sets the path of the software resource
     * @param path The path to this resource
     */
    public void setPath(String path);
}
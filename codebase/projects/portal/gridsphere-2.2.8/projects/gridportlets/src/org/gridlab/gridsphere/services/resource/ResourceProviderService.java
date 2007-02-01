package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.portlet.service.PortletService;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceProviderService.java,v 1.1.1.1 2007-02-01 20:40:53 kherm Exp $
 * <p>
 * Provides resources to the resource registry service, Resource provider
 * services can be activated by adding them as service config parameters
 * to the resource registry service.
 */

public interface ResourceProviderService extends PortletService {

    /**
     * Returns true if this service is active.
     * @return True if active, false otherwise
     */
    public boolean getIsActive();

    /**
     * Activates this service.
     */
    public void activate();

    /**
     * Deactivates this service.
     */
    public void deactivate();

    /**
     * Adds the given resource event listener to list of event listeners
     * @param listener Resource event listener
     */
    public void addResourceEventListener(ResourceEventListener listener);

    /**
     * Remove the given resource event listener to list of event listeners
     * @param listener Resource event listener
     */
    public void removeResourceEventListener(ResourceEventListener listener);
}

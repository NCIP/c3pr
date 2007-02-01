/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: SingleProfileBrowser.java,v 1.1.1.1 2007-02-01 20:42:13 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser;

import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.resource.HardwareResource;

import javax.portlet.PortletException;
import java.util.Iterator;

public class SingleProfileBrowser extends ResourceBrowser {

    private String resourceProfileGroup = "General";

    public SingleProfileBrowser(ActionComponentFrame actionComponentFrame, String compId) {
        super(actionComponentFrame, compId);
    }

    protected void loadResourceProfileListBox(HardwareResource hardwareResource, Class resourceClass, String selectedProfileKey) throws PortletException {
        resourceProfileListSize = 1;
    }

    protected ResourceProfile getDefaultResourceProfile(ResourceType resourceType) {
        Iterator resourceProfiles = resourceProfileRegistryService.getResourceProfiles(resourceType).iterator();
        while (resourceProfiles.hasNext()) {
            ResourceProfile nextResourceProfile = (ResourceProfile)resourceProfiles.next();
            if (nextResourceProfile.getDescription().equals(getResourceProfileGroup())) {
                return  nextResourceProfile;
            }
        }
        return null;
    }

    protected ResourceProfile getDefaultResourceProfile(ResourceType resourceType, ResourceProfile currentProfile) {
        Iterator resourceProfiles = resourceProfileRegistryService.getResourceProfiles(resourceType).iterator();
        while (resourceProfiles.hasNext()) {
            ResourceProfile nextResourceProfile = (ResourceProfile)resourceProfiles.next();
            if (nextResourceProfile.getDescription().equals(getResourceProfileGroup())) {
                return  nextResourceProfile;
            }
        }
        return null;
    }

    protected ResourceProfile getNextResourceProfile(ResourceType resourceType, ResourceProfile currentProfile) {
        return getSelectedTabPageProfile(resourceType);
    }

    public String getResourceProfileGroup() {
        return resourceProfileGroup;
    }

    public void setResourceProfileGroup(String resourceProfileGroup) {
        this.resourceProfileGroup = resourceProfileGroup;
    }
}

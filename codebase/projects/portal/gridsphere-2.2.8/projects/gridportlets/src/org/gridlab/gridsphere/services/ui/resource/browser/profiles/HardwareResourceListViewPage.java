/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardwareResourceListViewPage.java,v 1.1.1.1 2007-02-01 20:42:14 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser.profiles;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.HardwareResourceType;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.resource.ResourceProviderService;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.BaseResourceListViewPage;
import org.gridlab.gridsphere.services.ui.resource.browser.ResourceProfile;
import org.gridlab.gridsphere.services.ui.resource.browser.ResourceComponentFrame;
import org.gridlab.gridsphere.services.resources.mds2.Mds2ResourceProviderService;

import javax.portlet.PortletException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;

public class HardwareResourceListViewPage extends BaseResourceListViewPage {

    private transient static PortletLog log = SportletLog.getInstance(HardwareResourceListViewPage.class);

    public HardwareResourceListViewPage(ActionComponentFrame container, String compName) {
         super(container, compName);
        log.debug("HardwareResourceListViewPage()");
        // Resource type
        setResourceType(HardwareResourceType.INSTANCE);
        // Default page
        setDefaultJspPage("/jsp/resource/browser/profiles/HardwareResourceListViewPage.jsp");
    }

    public Resource getParentResource() {
        setSelectedParentOid(null);
        setSelectedParentType(null);
        return null;
    }

    protected List loadResourceList(Resource parentResource, Map parameters) throws PortletException {
        List resourceList = null;
        ResourceProfile ourProfile = ((ResourceComponentFrame)container).getResourceProfile();
        resourceList = resourceRegistryService.getResources(HardwareResourceType.INSTANCE);
        if (ourProfile == null) {
            log.warn("Container does not have resource profile");
        } else {
            resourceList = resourceProfileRegistryService.getSupportedResources(ourProfile, resourceList);
        }
        return resourceList;
    }
}

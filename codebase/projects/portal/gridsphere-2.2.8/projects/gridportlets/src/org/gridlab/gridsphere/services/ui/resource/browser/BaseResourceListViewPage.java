/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseResourceListViewPage.java,v 1.1.1.1 2007-02-01 20:42:11 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.resource.Resource;

import java.util.List;
import java.util.Map;

public class BaseResourceListViewPage extends BaseResourcePage implements ResourceListViewPage {

    private transient static PortletLog log = SportletLog.getInstance(BaseResourceListViewPage.class);
    private String lastParentResourceDn = null;

    public BaseResourceListViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        // Default action and page
        setDefaultAction("doResourceListView");
        setRenderAction("doResourceListRefresh");
        setDefaultJspPage("/jsp/resource/browser/BaseResourceListViewPage.jsp");
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        container.registerActionLink(this, "doResourceView", ResourceViewPage.class);
        container.registerActionLink(this, "doResourceEdit", ResourceEditPage.class);
    }

    public void doResourceListView(Map parameters) throws PortletException {
        log.debug("doResourceListView " + getClass().getName());

        // Set next state
        setNextState(defaultJspPage);

        // Check if we need to refresh content
        boolean redisplay = getRefreshContent();

        ResourceBrowser browser = (ResourceBrowser)container.getParentComponent();
        String parentDn = browser.getHardwareResourceDn();

        if (redisplay) {
            // If refresh view flag was set, turn off flag
            setRefreshContent(false);
        } else {
            // Otherwise check if we have new parent resource selection...
            if (parentDn == null) {
                if (lastParentResourceDn != null) {
                    redisplay = true;
                }
            } else if (lastParentResourceDn == null || !parentDn.equals(lastParentResourceDn)) {
                redisplay = true;
            }
        }

        lastParentResourceDn = parentDn;

        // If set to redisplay...
        if (redisplay) {

            // Notify jsp page to refresh content
            setPageAttribute("refreshContent", Boolean.TRUE);

            // Get parent resource from parameters
            Resource parentResource = getParentResource();

            // Load resource list
            List resourceList = loadResourceList(parentResource, parameters);
            log.debug("Loaded " + resourceList.size() + " resources");
            // Save as page attribute
            setPageAttribute("resourceList", resourceList);

        }

        // Notify parent component
        ResourceBrowser parentComponent = (ResourceBrowser)getContainer().getParentComponent();
        parentComponent.pageLoaded(this, ResourcePage.LIST_PAGE_NAME);
    }

    public void doResourceListRefresh(Map parameters) throws PortletException {
        log.debug("doResourceListRefresh " + getClass().getName());

        doResourceListView(parameters);
    }

    protected List loadResourceList(Resource parentResource, Map parameters) throws PortletException {
        log.debug("loadResourceList");
        // If we have parent resource then return its child resources
        // of our type, otherwise return all resources of this type
        List resourceList = null;
        if (parentResource == null) {
            log.debug("Parent resource is null, listing all resources of type " + getResourceType().getID());
            resourceList = resourceRegistryService.getResources(getResourceType());
        } else {
            log.debug("Gettting child resources of " + parentResource.getDn());
            resourceList = parentResource.getChildResources(getResourceType());
        }
        return resourceList;
    }
}

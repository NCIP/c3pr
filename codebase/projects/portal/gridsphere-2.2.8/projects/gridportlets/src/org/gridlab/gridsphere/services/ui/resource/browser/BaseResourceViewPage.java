/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseResourceViewPage.java,v 1.1.1.1 2007-02-01 20:42:11 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.resource.Resource;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class BaseResourceViewPage extends BaseResourcePage implements ResourceViewPage {

    private transient static PortletLog log = SportletLog.getInstance(BaseResourceViewPage.class);

    protected TextBean dnText = null;
    protected TextBean labelText = null;
    protected TextBean descriptionText = null;
    protected List resourceAttributes = new ArrayList();
    protected ListBoxBean parentResourceListBox = null;

    public BaseResourceViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);

        // Resource attributes
        dnText = createTextBean("dnText");
        labelText = createTextBean("labelText");
        descriptionText = createTextBean("descriptionText");

        // Default action and page
        setDefaultAction("doResourceView");
        setRenderAction("doResourceRefresh");
        setDefaultJspPage("/jsp/resource/browser/BaseResourceViewPage.jsp");
    }

    protected void initTagBeans() {
        dnText.setValue("");
        labelText.setValue("");
        descriptionText.setValue("");
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        // Register action links
        container.registerActionLink(this, "doResourceListView", ResourceListViewPage.class);
    }

    public void doResourceView(Map parameters) throws PortletException {
        log.debug("doResourceView " + getClass().getName());
        // Set next state
        setNextState(defaultJspPage);
        // Check if we need to refresh view or not...
        String resourceDn = (String)parameters.get(RESOURCE_DN_PARAM);
        //String resourceOid = (String)parameters.get(RESOURCE_OID_PARAM);
        if (getRefreshContent() ||
            (resourceDn != null && !resourceDn.equals(getResourceDn()))) {
            // Notify jsp page to refresh content
            setPageAttribute("refreshContent", Boolean.TRUE);
            // Turn off internal flag
            setRefreshContent(false);
            // Clear tab bean values
            initTagBeans();
            // Get resource from parameters
            Resource resource = getResource(parameters);
            // If no resource complain...
            if (resource == null) {
                messageBox.appendText("No resource parameter was provided!");
                messageBox.setMessageType(TextBean.MSG_ERROR);
            } else {
                // Set resource parameter
                parameters.put(RESOURCE_PARAM, resource);
                // Load resource attributes
                loadResource(resource);
                // Give to page (experimental!)
                setPageAttribute(RESOURCE_PARAM, resource);
            }
        }


        // Notify parent component
        ResourceBrowser parentComponent = (ResourceBrowser)getContainer().getParentComponent();
        parentComponent.pageLoaded(this, ResourcePage.VIEW_PAGE_NAME);
    }

    public void doResourceRefresh(Map parameters) throws PortletException {
        log.debug("doResourceRefresh " + getClass().getName());
        if (getRefreshContent()) {
            // Notify jsp page to refresh content
            setPageAttribute("refreshContent", Boolean.TRUE);
            // Turn off internal flag
            setRefreshContent(false);
            log.debug("Resource event occured. Refreshing view.");
            refreshResource(parameters);
        } else {
            log.debug("No resource event occured. Not refreshing view.");
            // Notify parent component
            ResourceBrowser parentComponent = (ResourceBrowser)getContainer().getParentComponent();
            parentComponent.pageLoaded(this, ResourcePage.VIEW_PAGE_NAME);
            // Set next state
            setNextState(defaultJspPage);
        }
    }

    public void refreshResource(Map parameters) throws PortletException {
        log.debug("doResourceRefresh()");
        setNextState(defaultJspPage);
        // If no resource oid, complain...
        if (getResourceOid() == null) {
            messageBox.appendText("No resource oid was provided!");
            messageBox.setMessageType(TextBean.MSG_ERROR);
        } else {
            // Otherwise get resource
            Resource resource = resourceRegistryService.getResource(getResourceOid());
            if (resource == null) {
                messageBox.appendText("Invalid resource oid was provided... " + getResourceOid());
                messageBox.setMessageType(TextBean.MSG_ERROR);
            } else {
                parameters.put(RESOURCE_PARAM, resource);
                loadResource(resource);
                // Give to page (experimental!)
                setPageAttribute(RESOURCE_PARAM, resource);
            }
        }
        // Notify parent component
        ResourceBrowser parentComponent = (ResourceBrowser)getContainer().getParentComponent();
        parentComponent.pageLoaded(this, ResourcePage.VIEW_PAGE_NAME);
    }

    protected void loadResource(Resource resource) throws PortletException {

        String dn = resource.getDn();
        dnText.setValue(dn);

        String label = resource.getLabel();
        labelText.setValue(label);

        String description = resource.getDescription();
        descriptionText.setValue(description);
    }

    protected void loadParentResourceListBox() throws PortletException {
        log.debug("Loading parent resource list box");
        parentResourceListBox.clear();
        String parentOid = getSelectedParentOid();
        // Get parent resources
        boolean isSelected = false;
        List parentResourceList = resourceRegistryService.getResources(getSelectedParentType());
        int ii = 0;
        for (Iterator parentResources = parentResourceList.iterator(); parentResources.hasNext(); ++ii) {
            Resource nextResource = (Resource) parentResources.next();
            String nextOid = nextResource.getOid();
            String nextHost = nextResource.getLabel();
            // Create parent resource list box item
            ListBoxItemBean parentResourceItem = new ListBoxItemBean();
            parentResourceItem.setName(parentOid);
            parentResourceItem.setValue(nextHost);
            if (!isSelected) {
                if (ii == 0 && parentOid.equals("")) {
                    // Select first item in list by default
                    parentResourceItem.setSelected(true);
                } else if (nextOid.equals(parentOid)) {
                    // Select this item if hostnames are equal
                    parentResourceItem.setSelected(true);
                    isSelected = true;
                }
            }
            // Add to parent resource list box
            parentResourceListBox.addBean(parentResourceItem);
        }
    }
}

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceBrowser.java,v 1.1.1.1 2007-02-01 20:42:12 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resource.browser;

import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.job.JobQueueType;
import org.gridlab.gridsphere.services.job.JobType;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxItemBean;
import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ResourceBrowser extends ResourceComponent implements ResourceEventListener {

    public static final String RESOURCE_PROFILE_LIST_SIZE = "resourceProfileListSize";
    public static final String DEFAULT_VALUE_WEB = "&lt;All&gt;";
    public static final String DEFAULT_VALUE = "All";
    public static String PAGE_TYPE_PARAM = "pageType";

    private transient static PortletLog log = SportletLog.getInstance(ResourceBrowser.class);

    private Map resourceTypePageMap = new HashMap();
    private Map resourceTypeProfileMap = new HashMap();
    private Map resourceTypeMenuMap = new HashMap();
    private String pageType = ResourcePage.LIST_PAGE_NAME;
    private ResourceComponentFrame resourceProfileBean = null;
    private String hardwareResourceOid = null;
    private String hardwareResourceDn = null;
    private ListBoxBean hardwareResourceListBox = null;
    private ResourceProfile resourceProfile = ResourceProfile.INSTANCE;
    protected ListBoxBean resourceProfileListBox = null;
    protected int resourceProfileListSize = 0;
    protected boolean sameMenuFlag = false;
    protected boolean refreshContent = true;

    public ResourceBrowser(ActionComponentFrame container, String compName) {
         super(container, compName);

        // Listen for resource registry events if no provider service
        if (resourceRegistryService != null) {
            resourceRegistryService.addResourceEventListener(this);
        }

        // Default to hardware resource type
        setResourceType(HardwareResourceType.INSTANCE);

        // Create action beans
        hardwareResourceListBox = createListBoxBean("hardwareResourceListBox");
        resourceProfileListBox = createListBoxBean("resourceProfileListBox");

        // Action component frame
        resourceProfileBean = (ResourceComponentFrame)createActionComponentFrame("resourceProfileBean");
        resourceProfileBean.setNextState(ResourceListViewPage.class);

        // Default actions and page
        setDefaultAction("doSelectTab");
        setDefaultJspPage("/jsp/resource/browser/ResourceBrowser.jsp");
    }

    public void resourceEventOccured(ResourceEvent event) {
        log.debug("Resource event occured " + getClass().getName());
        refreshContent = true;
    }

    public void onStore() throws PortletException {
        super.onStore();
        // Store current resource info
        setPageAttribute(PAGE_TYPE_PARAM, pageType);
        setPageAttribute(RESOURCE_PROFILE_LIST_SIZE, new Integer(resourceProfileListSize));
        setPageAttribute(RESOURCE_PROFILE_PARAM, getResourceProfile());
        // Refresh view flag
        setPageAttribute("refreshContent", Boolean.valueOf(refreshContent));
    }

    public void doSelectTab(Map parameters) throws PortletException {

        log.debug("doSelectTab()");
        // Get resource type from parameters

        ResourceType resourceType = getResourceType();
        ResourceType lastResourceType = resourceType;
        String resourceTypeID = (String)parameters.get("resourceTypeID");
        if (resourceTypeID == null) {
            resourceType = HardwareResourceType.INSTANCE;
        } else if (resourceTypeID.equals(JobQueueType.INSTANCE.getID())) {
            resourceType = JobQueueType.INSTANCE;
        } else if (resourceTypeID.equals(JobType.INSTANCE.getID())) {
            resourceType = JobType.INSTANCE;
        } else if (resourceTypeID.equals(ServiceResourceType.INSTANCE.getID())) {
            resourceType = ServiceResourceType.INSTANCE;
        } else if (resourceTypeID.equals(HardwareAccountType.INSTANCE.getID())) {
            resourceType = HardwareAccountType.INSTANCE;
        } else {
            resourceType = HardwareResourceType.INSTANCE;
        }

        // Save resource type
        setResourceType(resourceType);

        // Get seleted tab page
        getSelectedTabPage(lastResourceType, resourceType, parameters);
    }

    public void doResourceListViewPage(Map parameters) throws PortletException {
        log.debug("doResourceListViewPage()");
        // Load the list view page
        ResourceComponent page
                = (ResourceComponent)resourceProfileBean.getActionComponent(ResourceListViewPage.class, parameters);
        resourceProfileBean.doAction(page.getClass(), RENDER_ACTION, parameters);
    }

    public void doResourceViewPage(Map parameters) throws PortletException {
        log.debug("doResourceViewPage()");
        // Load the resource view page
        ResourceComponent page
                = (ResourceComponent)resourceProfileBean.getActionComponent(ResourceViewPage.class, parameters);
        resourceProfileBean.doAction(page.getClass(), DEFAULT_ACTION, parameters);
    }

    public void doResourceRefreshPage(Map parameters) throws PortletException {
        log.debug("doResourceRefreshPage()");
        // Reload the resource view page

        sameMenuFlag = true;

        ResourceComponent page = (ResourceComponent)resourceProfileBean.getActiveComponent();
        resourceProfileBean.doAction(page.getClass(), RENDER_ACTION, parameters);
    }

    public void pageLoaded(ResourceComponent page, String pageType) throws PortletException {
        log.debug("pageLoaded(" + page.getClass().getName() + "," + pageType +")");

        String pageResourceOid = page.getResourceOid();
        String pageResourceDn = page.getResourceDn();
        ResourceType pageResourceType = page.getResourceType();

        // Save active resource oid and type
        setResourceOid(pageResourceOid);
        setResourceDn(pageResourceDn);
        setResourceType(pageResourceType);

        // Save active page and menu for resource type
        setPageType(pageType);
        setSelectedTabPage(pageResourceType, page);
        setSelectedTabPageType(pageResourceType, pageType);

        log.debug("Browser resource type is " + getResourceType().getID());
        log.debug("Page resource type is " + pageResourceType.getID());

        // Check our same menu flag
        if (sameMenuFlag) {

            sameMenuFlag = false;

            refreshContent = false;

        } else {

            refreshContent = true;

            // Get loaded resource profile
            ResourceProfile pageResourceProfile = resourceProfileBean.getResourceProfile();
            String resourceProfileKey = pageResourceProfile.getKey();

            setSelectedTabPageProfile(pageResourceType, pageResourceProfile);


            HardwareResource selectedHardware = null;

            // If we are currently viewing hardware resources...
            if (pageResourceType.isResourceType(HardwareResourceType.INSTANCE)) {
                if (pageType.equals(ResourcePage.LIST_PAGE_NAME)) {
                    // Clear hardware resource oid if we are in list view
                    setHardwareResourceDn(null);
                } else {
                    // Save hardware resource oid if we are viewing a hardware resource
                    setHardwareResourceOid(pageResourceOid);
                    setHardwareResourceDn(pageResourceDn);
                    // Reload hardware resource list box based on current profile selection
                    selectedHardware = loadHardwareResourceListBox(pageResourceDn, pageResourceProfile);
                }
            } else {
                // Reload hardware resource list box based on current profile selection
                selectedHardware =loadHardwareResourceListBox(getHardwareResourceDn(), pageResourceProfile);
            }

            // Reload resource profile list box
            Class resourceClass = pageResourceType.getResourceClass();
            loadResourceProfileListBox(selectedHardware, resourceClass, resourceProfileKey);

        }

        // Pass new attributes onto jsp...
        onStore();

        // Set next state to default page
        setNextState(defaultJspPage);
    }

    public void doSelectHardwareResource(Map parameters) throws PortletException {
        // Get active page
        ResourceComponent page = (ResourceComponent)resourceProfileBean.getActiveComponent();
        // Get new hardware selection
        String selectedDn = hardwareResourceListBox.getSelectedName();

        log.debug("Selected hardware resource " + selectedDn);

        if (selectedDn.equals(DEFAULT_VALUE)) {

            // Clear hardware oid
            setHardwareResourceDn(null);

            log.debug("Showing full resource list...");

            // Reload current resource page
            resourceProfileBean.doAction(page.getClass(), DEFAULT_ACTION, parameters);
        } else {

            // Reload if new resource selected
            if (selectedDn.equals(hardwareResourceOid)) {

                log.debug("Same hardware resource, only refreshing...");

                sameMenuFlag = true;

            } else {

                log.debug("New hardware resource selected");

                // Save new hardware resource oid
                setHardwareResourceDn(selectedDn);

                parameters.put(RESOURCE_DN_PARAM, selectedDn);

                // Reload current resource page
                resourceProfileBean.doAction(page.getClass(), DEFAULT_ACTION, parameters);
            }
        }
    }

    public void doSelectResourceProfile(Map parameters) throws PortletException {
        // Get active page
        ResourceComponent page = (ResourceComponent)resourceProfileBean.getActiveComponent();
        String resourceProfileKey = resourceProfileListBox.getSelectedName();
        ResourceProfile currentProfile = getResourceProfile();
        // Simply re-render if the same profile was selected
        if (resourceProfileKey.equals(currentProfile.getKey())) {

            sameMenuFlag = true;
        } else {

            ResourceProfile nextProfile = resourceProfileRegistryService.getResourceProfile(resourceProfileKey);
            setResourceProfile(nextProfile);
            resourceTypeProfileMap.put(getResourceType().getID(), nextProfile);
            if (pageType == null || pageType.equals(ResourcePage.LIST_PAGE_NAME)) {
                page = (ResourceComponent)resourceProfileBean.getActionComponent(ResourceListViewPage.class);
            } else {
                page = (ResourceComponent)resourceProfileBean.getActionComponent(ResourceViewPage.class);
            }

            parameters.put(RESOURCE_DN_PARAM, getResourceDn());

            // View with new profile
            resourceProfileBean.doAction(page.getClass(), DEFAULT_ACTION, parameters);
        }
    }

    public void getSelectedTabPage(ResourceType lastResourceType, ResourceType resourceType, Map parameters) throws PortletException {
        log.debug("getSelectedTabPage(" + resourceType.getID() + ")");
        ResourceComponent page = (ResourceComponent)resourceTypePageMap.get(resourceType.getID());
        ResourceProfile currentProfile = getResourceProfile();
        String action = RENDER_ACTION;
        //Resource resource = getResource(parameters);
        String resourceDn = (String)parameters.get(ResourceComponent.RESOURCE_DN_PARAM);
//        log.debug("Resource dn = " + resourceDn);
        // If this is the first time creating a page for this resource type
        if (page == null) {
            // Set default resource profile....
            ResourceProfile resourceProfile = getDefaultResourceProfile(resourceType, currentProfile);
            setResourceProfile(resourceProfile);
            if (resourceDn == null || resourceDn.equals("") || resourceDn.equals("null")) {
//            if (resource == null) {
                log.debug("Creating new list page for " + resourceType.getID());
                page = (ResourceComponent)resourceProfileBean.getActionComponent(ResourceListViewPage.class, parameters);
                resourceTypePageMap.put(resourceType.getID(), page);
                resourceTypeProfileMap.put(resourceType.getID(), resourceProfile);
            } else {
                log.debug("Creating new view page for " + resourceType.getID());
                page = (ResourceComponent)resourceProfileBean.getActionComponent(ResourceViewPage.class, parameters);
                resourceTypePageMap.put(resourceType.getID(), page);
                resourceTypeProfileMap.put(resourceType.getID(), resourceProfile);
                action = DEFAULT_ACTION;
            }
        } else {
            // Reset selected resource profile....
            ResourceProfile resourceProfile = getNextProfile(resourceType, currentProfile);
            setResourceProfile(resourceProfile);
            if (resourceDn == null || resourceDn.equals("") ||  resourceDn.equals("null")) {
//            if (resource == null) {
                log.debug("Getting next page for " + resourceType.getID());
                // If viewing hardware resource type...
                if (resourceType.equals(HardwareResourceType.INSTANCE)) {
                    // Force relist if no hardware resource selected.
                    if (hardwareResourceDn == null) {
                        page = (ResourceComponent)resourceProfileBean.getActionComponent(ResourceListViewPage.class, parameters);
                    } else {
                        page.setResourceDn(hardwareResourceDn);
                    }
                }
            } else {
                log.debug("Getting view page for " + resourceType.getID());
                page = (ResourceComponent)resourceProfileBean.getActionComponent(ResourceViewPage.class, parameters);
                //resourceTypePageMap.put(resourceType.getID(), page);
                action = DEFAULT_ACTION;
            }
        }

        // Set same menu flag if...
        if (refreshContent) {
            sameMenuFlag = false;// Reset always if need to refresh content
        } else if (!resourceType.equals(lastResourceType) &&
                    (lastResourceType.equals(HardwareResourceType.INSTANCE) ||
                     resourceType.equals(HardwareResourceType.INSTANCE))) {
            sameMenuFlag = false; // Reset always to/from hardware pages
        } else {
            sameMenuFlag = true;  // Else same menu...
        }

        // Load the page
        resourceProfileBean.doAction(page.getClass(), action, parameters);

        log.debug("Resource page for " + resourceType.getID() + " is " + page.getClass().getName());
    }

    protected ResourceProfile getDefaultResourceProfile(ResourceType resourceType, ResourceProfile currentProfile) {
        if (currentProfile != null) {
            Iterator resourceProfiles = resourceProfileRegistryService.getResourceProfiles(resourceType).iterator();
            String currentGroup = currentProfile.getDescription();
            while (resourceProfiles.hasNext()) {
                ResourceProfile nextProfile = (ResourceProfile)resourceProfiles.next();
                String nextGroup = nextProfile.getDescription();
                if (nextGroup.equals(currentGroup)) {
                    return nextProfile;
                }
            }
        }
        return  resourceProfileRegistryService.getResourceProfile(resourceType);
    }

    protected ResourceProfile getNextProfile(ResourceType resourceType, ResourceProfile currentProfile) {
        if (currentProfile != null) {
            Iterator resourceProfiles = resourceProfileRegistryService.getResourceProfiles(resourceType).iterator();
            String currentGroup = currentProfile.getDescription();
            while (resourceProfiles.hasNext()) {
                ResourceProfile nextProfile = (ResourceProfile)resourceProfiles.next();
                String nextGroup = nextProfile.getDescription();
                if (nextGroup.equals(currentGroup)) {
                    return nextProfile;
                }
            }
        }
        return  (ResourceProfile)resourceTypeProfileMap.get(resourceType.getID());
    }

    public void setSelectedTabPage(ResourceType resourceType, ResourceComponent page) {
        resourceTypePageMap.put(resourceType.getID(), page);
    }

    public String getSelectedTabPageType(ResourceType resourceType) throws PortletException {
        String menu = (String)resourceTypeMenuMap.get(resourceType.getID());
        if (menu == null) {
            menu = ResourcePage.LIST_PAGE_NAME;
        }
        return menu;
    }

    public void setSelectedTabPageType(ResourceType resourceType,  String pageType) {
        resourceTypeMenuMap.put(resourceType.getID(), pageType);
    }

    public ResourceProfile getSelectedTabPageProfile(ResourceType resourceType) {
        return (ResourceProfile)resourceTypeProfileMap.get(resourceType.getID());
    }

    public void setSelectedTabPageProfile(ResourceType resourceType, ResourceProfile resourceProfile) {
        resourceTypeProfileMap.put(resourceType.getID(), resourceProfile);
    }

    public ResourceProfile getResourceProfile() {
        return resourceProfile;
    }

    public void setResourceProfile(ResourceProfile resourceProfile) {
        this.resourceProfile = resourceProfile;
        resourceProfileBean.setResourceProfile(resourceProfile);
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getHardwareResourceOid() {
        return hardwareResourceOid;
    }

    public void setHardwareResourceOid(String hardwareResourceOid) {
        this.hardwareResourceOid = hardwareResourceOid;
    }

    public String getHardwareResourceDn() {
        return hardwareResourceDn;
    }

    public void setHardwareResourceDn(String hardwareResourceDn) {
        this.hardwareResourceDn = hardwareResourceDn;
    }

    protected HardwareResource loadHardwareResourceListBox(String selectedDn, ResourceProfile profile) throws PortletException {
        log.debug("Loading parent resource list box");
        if (selectedDn == null) selectedDn = DEFAULT_VALUE;

        // Clear current list
        hardwareResourceListBox.clear();

        if (pageType.equals(ResourcePage.LIST_PAGE_NAME)) {
            // Add <all> item
            ListBoxItemBean hardwareResourceItem = new ListBoxItemBean();
            hardwareResourceItem.setName(DEFAULT_VALUE);
            hardwareResourceItem.setValue(DEFAULT_VALUE_WEB);
            // Select first item in list by default
            if (selectedDn.equals(DEFAULT_VALUE)) {
                hardwareResourceItem.setSelected(true);
            }
            hardwareResourceListBox.addBean(hardwareResourceItem);
        }

        // Add all hardware resources that are supported by the current selected profile to our list box
        HardwareResource selectedResource = null;
        boolean isSelected = false;
        List hardwareResourceList = resourceRegistryService.getResources(HardwareResourceType.INSTANCE);
        List supportedList = resourceProfileRegistryService.getSupportedResources(profile, hardwareResourceList);
        for (Iterator hardwareResources = supportedList.iterator(); hardwareResources.hasNext();) {
            HardwareResource nextResource = (HardwareResource) hardwareResources.next();
            String nextDn = nextResource.getDn();
            String nextHost = nextResource.getLabel();
            // New hardware resource list box item
            ListBoxItemBean hardwareResourceItem = new ListBoxItemBean();
            hardwareResourceItem.setName(nextDn);
            hardwareResourceItem.setValue(nextHost);
            if (!isSelected) {
                if (nextDn.equals(selectedDn)) {
                    // Select this item if hostnames are equal
                    selectedResource = nextResource;
                    hardwareResourceItem.setSelected(true);
                    isSelected = true;
                }
            }
            // Add to hardware resource item to list box
            hardwareResourceListBox.addBean(hardwareResourceItem);
        }

        return selectedResource;
    }

    protected void loadResourceProfileListBox(HardwareResource hardwareResource, Class resourceClass, String selectedProfileKey) throws PortletException {

        // Clear current list
        resourceProfileListBox.clear();
        resourceProfileListSize = 0;

        // Get resource profiles
        boolean isSelected = false;
        Iterator resourceProfiles = resourceProfileRegistryService.getResourceProfiles(resourceClass).iterator();
        while (resourceProfiles.hasNext()) {

            ResourceProfile nextResourceProfile = (ResourceProfile)resourceProfiles.next();

            if (hardwareResource == null ||
                                 resourceProfileRegistryService.supports(hardwareResource, nextResourceProfile)) {

                ++resourceProfileListSize;

                String nextKey = nextResourceProfile.getKey();
                String nextDesc = nextResourceProfile.getDescription();
                // New hardware resource list box item
                ListBoxItemBean resourceProfileItem = new ListBoxItemBean();
                resourceProfileItem.setName(nextKey);
                resourceProfileItem.setValue(nextDesc);
                if (!isSelected) {
                    if (nextKey.equals(selectedProfileKey)) {
                        // Select this item if hostnames are equal
                        resourceProfileItem.setSelected(true);
                        isSelected = true;
                    }
                }
                // Add to hardware resource item to list box
                resourceProfileListBox.addBean(resourceProfileItem);
            }
        }
    }
}

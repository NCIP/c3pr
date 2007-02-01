/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Mds2HardwareViewPage.java,v 1.1.1.1 2007-02-01 20:42:16 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.resources.mds2;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.resource.browser.BaseResourceViewPage;
import org.gridlab.gridsphere.services.resources.mds2.Mds2ChartService;
import org.gridlab.gridsphere.services.resources.mds2.Mds2ResourceProviderService;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.ImageBean;

import javax.portlet.PortletException;

public class Mds2HardwareViewPage extends BaseResourceViewPage {

    private transient static PortletLog log = SportletLog.getInstance(Mds2HardwareViewPage.class);

    private Mds2ChartService mds2ChartService = null;

    protected TextBean hostNameText = null;
    protected TextBean hostLabelText = null;
    protected TextBean hostDescriptionText = null;

    protected TextBean osNameText = null;
    protected TextBean osVersionText = null;
    protected TextBean osReleaseText = null;

    protected TextBean computerPlatformText = null;
    protected TextBean computerIsAText = null;

    protected TextBean cpuVendorText = null;
    protected TextBean cpuVersionText = null;
    protected TextBean cpuModelText = null;
    protected TextBean cpuFeaturesText = null;
    protected TextBean cpuCache12KBText = null;
    protected TextBean cpuCountText = null;
    protected TextBean cpuSpeedText = null;

    protected TextBean cpuLoad1MinText = null;
    protected TextBean cpuLoad5MinText = null;
    protected TextBean cpuLoad15MinText = null;

    protected TextBean memorySizeMBText = null;
    protected TextBean memoryFreeMBText = null;

    protected TextBean virtualMemorySizeMBText = null;
    protected TextBean virtualMemoryFreeMBText = null;

    protected ImageBean cpuLoadChart = null;
    protected ImageBean memoryChart = null;
    protected ImageBean virtualMemoryChart = null;

    public Mds2HardwareViewPage(ActionComponentFrame container, String compName) {
        super(container, compName);
        log.debug("Mds2HardwareViewPage()");

        // Charts
        cpuLoadChart = createImageBean("cpuLoadChart");
        memoryChart = createImageBean("memoryChart");
        virtualMemoryChart = createImageBean("virtualMemoryChart");

        // Hardware resource attributes
        hostNameText = createTextBean("hostNameText");
        hostLabelText = createTextBean("hostLabelText");
        hostDescriptionText = createTextBean("hostDescriptionText");

        osNameText = createTextBean("osNameText");
        osVersionText = createTextBean("osVersionText");
        osReleaseText = createTextBean("osReleaseText");

        computerPlatformText = createTextBean("computerPlatformText");
        computerIsAText = createTextBean("computerIsAText");

        cpuVendorText = createTextBean("cpuVendorText");
        cpuVersionText = createTextBean("cpuVersionText");
        cpuModelText = createTextBean("cpuModelText");
        cpuFeaturesText = createTextBean("cpuFeaturesText");
        cpuCache12KBText = createTextBean("cpuCache12KBText");
        cpuCountText = createTextBean("cpuCountText");
        cpuSpeedText = createTextBean("cpuSpeedText");

        cpuLoad1MinText = createTextBean("cpuLoad1MinText");
        cpuLoad5MinText = createTextBean("cpuLoad5MinText");
        cpuLoad15MinText = createTextBean("cpuLoad15MinText");

        memorySizeMBText = createTextBean("memorySizeMBText");
        memoryFreeMBText = createTextBean("memoryFreeMBText");

        virtualMemorySizeMBText = createTextBean("virtualMemorySizeMBText");
        virtualMemoryFreeMBText = createTextBean("virtualMemoryFreeMBText");

        // Resource type
        setResourceType(HardwareResourceType.INSTANCE);
        // Default page
        setDefaultJspPage("/jsp/resources/mds2/Mds2HardwareViewPage.jsp");
    }

    public void initTagBeans() {
        super.initTagBeans();
        hostNameText.setValue("");
        hostLabelText.setValue("");
        hostDescriptionText.setValue("");
        osNameText.setValue("");
        osVersionText.setValue("");
        osReleaseText.setValue("");
        computerPlatformText.setValue("");
        computerIsAText.setValue("");
        cpuVendorText.setValue("");
        cpuVersionText.setValue("");
        cpuModelText.setValue("");
        cpuFeaturesText.setValue("");
        cpuCache12KBText.setValue("");
        cpuCountText.setValue("");
        cpuSpeedText.setValue("");
        cpuLoad1MinText.setValue("");
        cpuLoad5MinText.setValue("");
        cpuLoad15MinText.setValue("");
        memorySizeMBText.setValue("");
        memoryFreeMBText.setValue("");
        virtualMemorySizeMBText.setValue("");
        virtualMemoryFreeMBText.setValue("");
        cpuLoadChart.setSrc("");
        memoryChart.setSrc("");
        virtualMemoryChart.setSrc("");
    }

    protected ResourceProviderService loadResourceProviderService() {
        ResourceProviderService resourceProviderService = null;
        try {
            resourceProviderService = (ResourceProviderService)getPortletService(Mds2ResourceProviderService.class);
        } catch (Exception e) {
            log.error("Unable to get mds2 resource provider service", e);
        }
        return resourceProviderService;
    }

    public void onInit() throws PortletException {
        super.onInit();
        mds2ChartService = (Mds2ChartService)getPortletService(Mds2ChartService.class);
    }

//    public void doResourceView(Map parameters) throws PortletException {
//        log.debug("doResourceView " + getClass().getName());
//        String resourceOid = (String)parameters.get(RESOURCE_OID_PARAM);
//        if (getResourceProviderService() == null || getRefreshViewFlag() ||
//            (resourceOid != null && !resourceOid.equals(getResourceOid()))) {
//            // Notify jsp page to refresh content
//            setPageAttribute("refreshContent", Boolean.TRUE);
//            // Turn off internal flag
//            setRefreshViewFlag(false);
//            // Load new resource view
//            super.doResourceView(parameters);
//        } else {
//            log.debug("No resource event occured. Not refreshing view.");
//            // Notify parent component
//            ResourceBrowser parentComponent = (ResourceBrowser)getContainer().getParentComponent();
//            parentComponent.pageLoaded(this, ResourcePage.VIEW_PAGE_NAME);
//            // Set next state
//            setNextState(defaultJspPage);
//        }
//    }
//
//    public void doResourceRefresh(Map parameters) throws PortletException {
//        log.debug("doResourceRefresh " + getClass().getName());
//        if (getResourceProviderService() == null || getRefreshViewFlag()) {
//            // Notify jsp page to refresh content
//            setPageAttribute("refreshContent", Boolean.TRUE);
//            // Turn off internal flag
//            setRefreshViewFlag(false);
//            log.debug("Resource event occured. Refreshing view.");
//            refreshResource(parameters);
//        } else {
//            log.debug("No resource event occured. Not refreshing view.");
//            setNextState(defaultJspPage);
//        }
//    }

    protected void loadResource(Resource resource) throws PortletException {

        HardwareResource hardwareResource = (HardwareResource)resource;

        String hostName = hardwareResource.getHostName();
        hostNameText.setValue(hostName);

        String hostLabel = hardwareResource.getLabel();
        hostLabelText.setValue(hostLabel);

        String hostDescription = hardwareResource.getDescription();
        hostDescriptionText.setValue(hostDescription);

        ResourceAttribute resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.OS_NAME_ATTRIBUTE);
        if (resourceAttribute != null) osNameText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.OS_VERSION_ATTRIBUTE);
        if (resourceAttribute != null) osVersionText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.OS_RELEASE_ATTRIBUTE);
        if (resourceAttribute != null) osReleaseText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.COMPUTER_PLATFORM_ATTRIBUTE);
        if (resourceAttribute != null) computerPlatformText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.COMPUTER_ISA_ATTRIBUTE);
        if (resourceAttribute != null) computerIsAText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_VENDOR_ATTRIBUTE);
        if (resourceAttribute != null) cpuVendorText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_VERSION_ATTRIBUTE);
        if (resourceAttribute != null) cpuVersionText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_MODEL_ATTRIBUTE);
        if (resourceAttribute != null) cpuModelText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_FEATURES_ATTRIBUTE);
        if (resourceAttribute != null) cpuFeaturesText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_CACHE_12KB_ATTRIBUTE);
        if (resourceAttribute != null) cpuCache12KBText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_COUNT_ATTRIBUTE);
        if (resourceAttribute != null) cpuCountText.setValue(resourceAttribute.getValue());

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_SPEED_MHZ_ATTRIBUTE);
        if (resourceAttribute != null) cpuSpeedText.setValue(resourceAttribute.getValue());

        String cpuLoad1 = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_LOAD_1_MIN_ATTRIBUTE);
        if (resourceAttribute != null) {
            cpuLoad1 = resourceAttribute.getValue();
            cpuLoad1MinText.setValue(cpuLoad1);
        }

        String cpuLoad5 = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_LOAD_5_MIN_ATTRIBUTE);
        if (resourceAttribute != null) {
            cpuLoad5 = resourceAttribute.getValue();
            cpuLoad5MinText.setValue(cpuLoad5);
        }

        String cpuLoad15 = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_LOAD_15_MIN_ATTRIBUTE);
        if (resourceAttribute != null) {
            cpuLoad15 = resourceAttribute.getValue();
            cpuLoad15MinText.setValue(cpuLoad15);
        }

        String memorySize = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.MEMORY_SIZE_MB_ATTRIBUTE);
        if (resourceAttribute != null) {
            memorySize = resourceAttribute.getValue();
            memorySizeMBText.setValue(memorySize);
        }

        String memoryFree = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.MEMORY_FREE_MB_ATTRIBUTE);
        if (resourceAttribute != null) {
            memoryFree = resourceAttribute.getValue();
            memoryFreeMBText.setValue(memoryFree);
        }

        String vmSize = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.VIRTUAL_MEMORY_SIZE_MB_ATTRIBUTE);
        if (resourceAttribute != null) {
            vmSize = resourceAttribute.getValue();
            virtualMemorySizeMBText.setValue(vmSize);
        }

        String vmFree = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.VIRTUAL_MEMORY_FREE_MB_ATTRIBUTE);
        if (resourceAttribute != null) {
            vmFree = resourceAttribute.getValue();
            virtualMemoryFreeMBText.setValue(vmFree);
        }

        String cpuLoadChartUrl = mds2ChartService.getCpuLoadChart(hardwareResource);
        cpuLoadChart.setSrc(cpuLoadChartUrl);
        String memoryChartUrl = mds2ChartService.getMemoryChart(hardwareResource);
        memoryChart.setSrc(memoryChartUrl);
        String vmChartUrl = mds2ChartService.getVirtualMemoryChart(hardwareResource);
        virtualMemoryChart.setSrc(vmChartUrl);
    }
}

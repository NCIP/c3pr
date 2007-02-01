/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Mds2ChartService.java,v 1.1.1.1 2007-02-01 20:41:20 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.mds2;

import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.resource.ResourceAttribute;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.jfree.data.DefaultCategoryDataset;
import org.jfree.data.DefaultPieDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.PlotOrientation;

import javax.portlet.PortletException;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

public class Mds2ChartService implements PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(Mds2ChartService.class);
    private ServletContext servletContext = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        servletContext = config.getServletContext();
    }

    public void destroy() {
    }

    public String getCpuLoadChart(HardwareResource resource) throws PortletException {
        return "/gridportlets/jsp/resources/mds2/" + resource.getHost() + ".HardwareCpuLoadChart.jpeg";
    }

    public String getMemoryChart(HardwareResource resource) throws PortletException {
        return "/gridportlets/jsp/resources/mds2/" + resource.getHost() + ".MemoryChart.jpeg";
    }

    public String getVirtualMemoryChart(HardwareResource resource) throws PortletException {
        return "/gridportlets/jsp/resources/mds2/" + resource.getHost() + ".VirtualMemoryChart.jpeg";
    }

    public void createCharts(HardwareResource hardwareResource) throws PortletException {

        String host = hardwareResource.getHost();

        String cpuLoad1 = "0";

//        int cpuLoad1Int =  hardwareResource.getResourceAttributeAsInt(HardwareResource.CPU_LOAD_1_MIN_ATTRIBUTE, 0);
//        log.debug("Cpu Load 1 int " + cpuLoad1Int);

        ResourceAttribute resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_LOAD_1_MIN_ATTRIBUTE);
        if (resourceAttribute != null) {
            cpuLoad1 = resourceAttribute.getValue();
        }

        String cpuLoad5 = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_LOAD_5_MIN_ATTRIBUTE);
        if (resourceAttribute != null) {
            cpuLoad5 = resourceAttribute.getValue();
        }

        String cpuLoad15 = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.CPU_LOAD_15_MIN_ATTRIBUTE);
        if (resourceAttribute != null) {
            cpuLoad15 = resourceAttribute.getValue();
        }

        String memorySize = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.MEMORY_SIZE_MB_ATTRIBUTE);
        if (resourceAttribute != null) {
            memorySize = resourceAttribute.getValue();
        }

        String memoryFree = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.MEMORY_FREE_MB_ATTRIBUTE);
        if (resourceAttribute != null) {
            memoryFree = resourceAttribute.getValue();
        }

        String vmSize = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.VIRTUAL_MEMORY_SIZE_MB_ATTRIBUTE);
        if (resourceAttribute != null) {
            vmSize = resourceAttribute.getValue();
        }

        String vmFree = "0";

        resourceAttribute
                = hardwareResource.getResourceAttribute(HardwareResource.VIRTUAL_MEMORY_FREE_MB_ATTRIBUTE);
        if (resourceAttribute != null) {
            vmFree = resourceAttribute.getValue();
        }


        // Create charts
        createCpuLoadChart(host, cpuLoad1, cpuLoad5, cpuLoad15);
        createMemoryChart(host, memorySize, memoryFree);
        createVirtualMemoryChart(host, vmSize, vmFree);
    }
    public void createCpuLoadChart(String host, String cpuLoad1, String cpuLoad5, String cpuLoad15) throws PortletException {

        int cpuLoad1Int = Integer.parseInt(cpuLoad1);
        int cpuLoad5Int = Integer.parseInt(cpuLoad5);
        int cpuLoad15Int = Integer.parseInt(cpuLoad15);

        String cpuLoadChartPath = "/jsp/resources/mds2/" + host + ".HardwareCpuLoadChart.jpeg";

//        log.debug("Mds2 cpu load 1 " + cpuLoad1Int);
//        log.debug("Mds2 cpu load 5 " + cpuLoad5Int);
//        log.debug("Mds2 cpu load 15 " + cpuLoad15Int);

        String cpuLoadChartRealPath = servletContext.getRealPath(cpuLoadChartPath);

        log.debug("Creating cpu load dataset");

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(cpuLoad1Int, "Load", "1 Minute Avg");
        dataset.setValue(cpuLoad5Int, "Load", "5 Minute Avg");
        dataset.setValue(cpuLoad15Int, "Load", "15 Minute Avg");

        log.debug("Creating chart at " + cpuLoadChartRealPath);

        JFreeChart chart = ChartFactory.createBarChart("CPU Load",
                                                       "Averages",
                                                       "Load",
                                                       dataset,
                                                       PlotOrientation.VERTICAL,
                                                       false,
                                                       true,
                                                       false);

        log.debug("Saving as JPEG to " + cpuLoadChartRealPath);

        try {
            ChartUtilities.saveChartAsJPEG(new File(cpuLoadChartRealPath), chart, 300, 200);
        } catch (IOException e) {
           log.error("Unable to save chart as JPEG", e);
           return;
        }

        log.debug("Creating url for " + cpuLoadChartRealPath);

        String chartUrl = "/gridportlets" + cpuLoadChartPath;

        log.debug("Cpu load chart set to " + chartUrl);
    }

    public void createMemoryChart(String host, String memorySize, String memoryFree) throws PortletException {


        int memorySizeInt = Integer.parseInt(memorySize);
        int memoryFreeInt = Integer.parseInt(memoryFree);
        int memoryUsedInt = memorySizeInt - memoryFreeInt;

        String memoryChartPath = "/jsp/resources/mds2/" + host + ".MemoryChart.jpeg";

//        log.debug("Mds2 memory size " + memorySizeInt);
//        log.debug("Mds2 memory used " + memoryUsedInt);
//        log.debug("Mds2 memory free " + memoryFreeInt);

        String memoryChartRealPath = servletContext.getRealPath(memoryChartPath);

        log.debug("Creating cpu load dataset");

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Memory Used", memoryUsedInt);
        dataset.setValue("Memory Free", memoryFreeInt);

        log.debug("Creating chart at " + memoryChartRealPath);

        JFreeChart chart = ChartFactory.createPieChart("Memory Usage",
                                                       dataset,
                                                       true,
                                                       true,
                                                       false);

        log.debug("Saving as JPEG to " + memoryChartRealPath);

        try {
            ChartUtilities.saveChartAsJPEG(new File(memoryChartRealPath), chart, 200, 200);
        } catch (IOException e) {
           log.error("Unable to save chart as JPEG", e);
           return;
        }

        log.debug("Creating url for " + memoryChartRealPath);

        String chartUrl = "/gridportlets" + memoryChartPath;

        log.debug("Cpu load chart set to " + chartUrl);
    }

    public void createVirtualMemoryChart(String host, String vmSize, String vmFree) throws PortletException {

        int vmSizeInt = Integer.parseInt(vmSize);
        int vmFreeInt = Integer.parseInt(vmFree);
        int vmUsedInt = vmSizeInt - vmFreeInt;

        String vmChartPath = "/jsp/resources/mds2/" + host + ".VirtualMemoryChart.jpeg";

        String vmChartRealPath = servletContext.getRealPath(vmChartPath);

        log.debug("Creating cpu load dataset");

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("VM Used", vmUsedInt);
        dataset.setValue("VM Free", vmFreeInt);

        log.debug("Creating chart at " + vmChartRealPath);

        JFreeChart chart = ChartFactory.createPieChart("Virtual Memory Usage",
                                                       dataset,
                                                       true,
                                                       true,
                                                       false);

        log.debug("Saving as JPEG to " + vmChartRealPath);

        try {
            ChartUtilities.saveChartAsJPEG(new File(vmChartRealPath), chart, 200, 200);
        } catch (IOException e) {
           log.error("Unable to save chart as JPEG", e);
           return;
        }

        log.debug("Creating url for " + vmChartRealPath);

        String chartUrl = "/gridportlets" + vmChartPath;

        log.debug("Cpu load chart set to " + chartUrl);
    }
}
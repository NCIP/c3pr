/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardwareResourceProvider.java,v 1.1.1.1 2007-02-01 20:41:21 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.mds2.providers;

import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.services.resources.mds2.Mds2ResourceProvider;
import org.gridlab.gridsphere.services.resources.mds2.Mds2ChartService;
import org.gridlab.gridsphere.services.resources.mds2.Mds2Resource;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.NamingException;


/**
dn: Mds-Host-hn=peyote.aei.mpg.de, Mds-Vo-name=local,o=Grid
Mds-Cpu-speedMHz: 2799
Mds-Memory-Ram-Total-freeMB: 3749
Mds-Fs-freeMB: 1021
Mds-Fs-freeMB: 1502
Mds-Fs-freeMB: 1586
Mds-Fs-freeMB: 2019
Mds-Fs-freeMB: 42387
Mds-Fs-freeMB: 5046
Mds-Fs-freeMB: 76
Mds-Cpu-Free-5minX100: 398
Mds-Net-Total-count: 5
Mds-validfrom: 20040503202836Z
Mds-Cpu-Total-count: 4
Mds-Memory-Vm-sizeMB: 1027
Mds-Cpu-vendor: GenuineIntel
Mds-Net-name: eth0
Mds-Net-name: eth1
Mds-Net-name: eth2
Mds-Net-name: eth3
Mds-Net-name: lo
Mds-validto: 20040503202836Z
Mds-Cpu-version: 15.2.7
Mds-Cpu-features: fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cm
 ov pat pse36 clflush dts acpi mmx fxsr sse sse2 ss ht tm pbe cid
Mds-Host-hn: peyote.aei.mpg.de
Mds-Os-release: 2.4.25
Mds-Net-addr: 10.10.1.1
Mds-Net-addr: 10.11.1.1
Mds-Net-addr: 10.12.1.1
Mds-Net-addr: 127.0.0.1
Mds-Net-addr: 194.94.224.99
Mds-Cpu-Cache-l2kB: 512
Mds-Memory-Vm-Total-sizeMB: 1027
Mds-Memory-Ram-sizeMB: 4038
Mds-Cpu-model: Intel(R) Xeon(TM) CPU 2
Mds-Memory-Vm-freeMB: 551
Mds-keepto: 20040503202836Z
Mds-Os-name: Linux
Mds-Net-netaddr: 10.10.0.0/16
Mds-Net-netaddr: 10.11.0.0/16
Mds-Net-netaddr: 10.12.0.0/16
Mds-Net-netaddr: 127.0.0.0/8
Mds-Net-netaddr: 194.94.224.0/24
Mds-Computer-platform: i686
Mds-Memory-Vm-Total-freeMB: 551
Mds-Cpu-Total-Free-1minX100: 393
Mds-Computer-Total-nodeCount: 1
Mds-Memory-Ram-freeMB: 3749
Mds-Computer-isa: IA32
Mds-Os-version: 6 SMP Fri Feb 27 12:41:05 CET 2004
Mds-Fs-Total-sizeMB: 70929
Mds-Cpu-Free-1minX100: 393
Mds-Memory-Ram-Total-sizeMB: 4038
Mds-Fs-sizeMB: 1024
Mds-Fs-sizeMB: 2019
Mds-Fs-sizeMB: 45623
Mds-Fs-sizeMB: 6045
Mds-Fs-sizeMB: 8060
Mds-Fs-sizeMB: 98
Mds-Cpu-Total-Free-5minX100: 398
Mds-Fs-Total-count: 7
Mds-Cpu-Free-15minX100: 393
Mds-Cpu-Total-Free-15minX100: 393
Mds-Fs-Total-freeMB: 53637
Mds-Cpu-Smp-size: 4
*/
public class HardwareResourceProvider extends Mds2ResourceProvider {

    private static PortletLog log = SportletLog.getInstance(HardwareResourceProvider.class);

    public HardwareResourceProvider(ResourceRegistryService resourceRegistry) {

        super(resourceRegistry);

        // OS attributes
        addResourceAttribute("Mds-Os-name", HardwareResource.OS_NAME_ATTRIBUTE);
        addResourceAttribute("Mds-Os-version", HardwareResource.OS_VERSION_ATTRIBUTE);
        addResourceAttribute("Mds-Os-release", HardwareResource.OS_RELEASE_ATTRIBUTE);
        // Computer attributes
        addResourceAttribute("Mds-Computer-platform", HardwareResource.COMPUTER_PLATFORM_ATTRIBUTE);
        addResourceAttribute("Mds-Computer-isa", HardwareResource.COMPUTER_ISA_ATTRIBUTE);
        // CPU attributes
        addResourceAttribute("Mds-Cpu-vendor", HardwareResource.CPU_VENDOR_ATTRIBUTE);
        addResourceAttribute("Mds-Cpu-version", HardwareResource.CPU_VERSION_ATTRIBUTE);
        addResourceAttribute("Mds-Cpu-model", HardwareResource.CPU_MODEL_ATTRIBUTE);
        addResourceAttribute("Mds-Cpu-features", HardwareResource.CPU_FEATURES_ATTRIBUTE);
        addResourceAttribute("Mds-Cpu-Cache-l2kB", HardwareResource.CPU_CACHE_12KB_ATTRIBUTE);
        addResourceAttribute("Mds-Cpu-Total-count", HardwareResource.CPU_COUNT_ATTRIBUTE);
        addResourceAttribute("Mds-Cpu-speedMHz", HardwareResource.CPU_SPEED_MHZ_ATTRIBUTE);
        // CPU load attributes
        addResourceAttribute("Mds-Cpu-Total-Free-1minX100", HardwareResource.CPU_LOAD_1_MIN_ATTRIBUTE);
        addResourceAttribute("Mds-Cpu-Total-Free-5minX100", HardwareResource.CPU_LOAD_5_MIN_ATTRIBUTE);
        addResourceAttribute("Mds-Cpu-Total-Free-15minX100", HardwareResource.CPU_LOAD_15_MIN_ATTRIBUTE);
        // Memory attributes
        addResourceAttribute("Mds-Memory-Ram-sizeMB", HardwareResource.MEMORY_SIZE_MB_ATTRIBUTE);
        addResourceAttribute("Mds-Memory-Ram-freeMB", HardwareResource.MEMORY_FREE_MB_ATTRIBUTE);
        // Virtual memory attributes
        addResourceAttribute("Mds-Memory-Vm-sizeMB", HardwareResource.VIRTUAL_MEMORY_SIZE_MB_ATTRIBUTE);
        addResourceAttribute("Mds-Memory-Vm-freeMB", HardwareResource.VIRTUAL_MEMORY_FREE_MB_ATTRIBUTE);

        // Gram job manager provider
        addChildResourceProvider(new GramJobManagerProvider(resourceRegistry));
        // Hardware account provider
        addChildResourceProvider(new HardwareAccountProvider(resourceRegistry));
    }

    public Resource createResource() {
        //log.debug("Retrieving hardware resource " + resourceName);
        return resourceRegistry.getHardwareResourceByHostName(resourceName);
    }

    public String getResourceSearchDn() {
        StringBuffer baseDn = new StringBuffer();
        baseDn.append("Mds-Vo-name=local,o=grid");
        return baseDn.toString();
    }

    public String getResourceSearchFilter() {
        return "(objectclass=MdsHost)";
    }

    public int getResourceSearchScope() {
        return SearchControls.ONELEVEL_SCOPE;
    }

    public void updateResources(Mds2Resource infoResource)
            throws NamingException {

        super.updateResources(infoResource);

        HardwareResource resource = (HardwareResource)getResource();

        try {
            PortletServiceFactory factory = SportletServiceFactory.getInstance();
            Mds2ChartService chartService = (Mds2ChartService)factory.createPortletService(Mds2ChartService.class, null, true);
            chartService.createCharts(resource);
        } catch (Exception e) {
            log.error("Unable to create mds2 hardware resource charts", e);
        }
    }

    public void updateResourceAttributes(Resource resource, SearchResult resourceObject)
            throws NamingException {
        super.updateResourceAttributes(resource, resourceObject);
//
//        NamingEnumeration mds2Attributes = resourceObject.getAttributes().getAll();
//        // Iterate through the mds attr names
//        while (mds2Attributes.hasMoreElements()) {
//            // Get the next mds attr name
//            BasicAttribute mds2Attribute = (BasicAttribute)mds2Attributes.nextElement();
//            String mds2AttrName = mds2Attribute.getID();
//            log.debug("Next hardware mds2 attribute " + mds2AttrName);
//            // Get the resource attribute to which this mds2 attribute maps
//            String resourceAttrName = getResourceAttributeName(mds2AttrName);
//            if (resourceAttrName != null) {
//                log.debug("Maps to resource attribute " + resourceAttrName);
//                ResourceAttribute resourceAttribute = resource.getResourceAttribute(resourceAttrName);
//                if (resourceAttribute == null) {
//                    log.debug("Creating hardware mds2 attribute " + resourceAttrName);
//                    resourceAttribute = new ResourceAttribute(resourceAttrName, null);
//                    resource.addResourceAttribute(resourceAttribute);
//                }
//                // Get the values for this attribute
//                NamingEnumeration mds2AttrValues = mds2Attribute.getAll();
//                if (mds2AttrValues.hasMoreElements()) {
//                    // Get next value
//                    String mds2AttrValue = mds2AttrValues.nextElement().toString();
//                    log.debug("Hardware mds2 attribute value " + mds2AttrValue);
//                    if (mds2AttrValue.equals("NULL")) {
//                        mds2AttrValue = null;
//                    }
//                    resourceAttribute.setValue(mds2AttrValue);
//                }
//            }
//        }
//        log.debug("Hardware resource has" + resource.getResourceAttributes().size() + " attributes");

    }
}

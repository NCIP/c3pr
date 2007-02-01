/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: HostResource.java,v 1.1.1.1 2007-02-01 20:40:51 kherm Exp $
 * <p>
 * Hardware resources represent computers and have the attributes typically
 * associated with computers. Hardware resources contain software resources
 * and service resources.
 */
package org.gridlab.gridsphere.services.resource;

import java.util.List;

public interface HostResource extends Resource {

    // OS attributes
    public static final String OS_NAME_ATTRIBUTE = "OsName";
    public static final String OS_VERSION_ATTRIBUTE = "OsVersion";
    public static final String OS_RELEASE_ATTRIBUTE = "OsRelease";

    // Computer attributes
    public static final String COMPUTER_PLATFORM_ATTRIBUTE = "ComputerPlatform";
    public static final String COMPUTER_ISA_ATTRIBUTE = "ComputerISA";

    // CPU attributes
    public static final String CPU_VENDOR_ATTRIBUTE = "CpuVendor";
    public static final String CPU_VERSION_ATTRIBUTE = "CpuVersion";
    public static final String CPU_MODEL_ATTRIBUTE = "CpuModel";
    public static final String CPU_FEATURES_ATTRIBUTE = "CpuFeatures";
    public static final String CPU_CACHE_12KB_ATTRIBUTE = "CpuCache12kB";
    public static final String CPU_COUNT_ATTRIBUTE = "CpuCount";
    public static final String CPU_SPEED_MHZ_ATTRIBUTE = "CpuSpeedMHZ";

    // CPU load attributes
    public static final String CPU_LOAD_1_MIN_ATTRIBUTE = "CpuLoad1Min";
    public static final String CPU_LOAD_5_MIN_ATTRIBUTE = "CpuLoad5Min";
    public static final String CPU_LOAD_15_MIN_ATTRIBUTE = "CpuLoad15Min";

    // Memory attributes
    public static final String MEMORY_SIZE_MB_ATTRIBUTE = "MemorySizeMB";
    public static final String MEMORY_FREE_MB_ATTRIBUTE = "MemoryFreeMB";

    // Virtual memory attributes
    public static final String VIRTUAL_MEMORY_FREE_MB_ATTRIBUTE = "VirtualMemoryFreeMB";
    public static final String VIRTUAL_MEMORY_SIZE_MB_ATTRIBUTE = "VirtualMemorySizeMB";

    /**
     * Returns hostname if hostname is not null, otherwise returns inetaddress
     * @return hostname if not null, else returns inetaddress
     */
    public String getHost();

    /**
     * Returns the host name of this resource.
     * @return The host name
     */
    public String getHostName();

    /**
     * Sets the hostname of this resource.
     * @param hostname The host name
     */
    public void setHostName(String hostname);

    /**
     * Returns the inetaddress of this resource.
     * @return The inetaddress
     */
    public String getInetAddress();

    /**
     * Sets the inetaddress of this resource.
     * @param address The inetaddresss
     */
    public void setInetAddress(String address);

    /**
     * Returns the service resources available on this
     * hardware resource.
     * @return The service resources
     * @see ServiceResource
     */
    public List getServiceResources();

    /**
     * Returns the software resources available on this
     * hardware resource.
     * @return The software resources
     * @see SoftwareResource
     */
    public List getSoftwareResources();

    /**
     * Returns the hardware accounts available on this resource.
     * @return The hardware accounts
     * @see HardwareAccount
     */
    public List getHardwareAccounts();

    /**
     * Creates a hardware account on this hardware resource with the given user id.
     * @param userId The user id
     * @return The resulting hardware account
     */
    public HardwareAccount createHardwareAccount(String userId);

    /**
     * Returns the hardware account with this user id.
     * @param userId The user id
     * @return The hardware account
     */
    public HardwareAccount getHardwareAccount(String userId);
}

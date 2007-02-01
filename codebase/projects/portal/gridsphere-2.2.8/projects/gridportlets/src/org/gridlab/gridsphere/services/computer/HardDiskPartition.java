/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardDiskPartition.java,v 1.1.1.1 2007-02-01 20:39:53 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.impl.BaseResource;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

public class HardDiskPartition extends BaseResource {

    public static final String NAME = "HARD_DISK_PARTITION_NAME";
    public static final String MOUNT_POINT = "HARD_DISK_PARTITION_MOUNT_POINT";
    public static final String TOTAL_SPACE = "HARD_DISK_PARTITION_TOTAL_SPACE";
    public static final String FREE_SPACE = "HARD_DISK_PARTITION_FREE_SPACE";
    public static final String USED_SPACE = "HARD_DISK_PARTITION_USED_SPACE";

    private static PortletLog log = SportletLog.getInstance(HardDiskPartition.class);
    private String name = null;

    public HardDiskPartition() {
        super();
        setResourceType(HardDiskPartitionType.INSTANCE);
    }

    public HardDiskPartition(String name) {
        super();
        setResourceType(HardDiskPartitionType.INSTANCE);
        setName(name);
    }

    public String getLabel() {
        return getName();
    }

    public void setLabel(String label) {
        setName(label);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        putResourceAttribute(NAME, name);
    }

    public String getMountPoint() {
        return getResourceAttributeValue(MOUNT_POINT, "/");
    }

    public void setMountPoint(String value) {
        putResourceAttribute(MOUNT_POINT, value);
    }

    public long getTotalSpace() {
        return getResourceAttributeAsLng(TOTAL_SPACE, 0);
    }

    public void setTotalSpace(long value) {
        setResourceAttributeAsLng(TOTAL_SPACE, value);
    }

    public long getFreeSpace() {
        return getResourceAttributeAsLng(FREE_SPACE, 0);
    }

    public void setFreeSpace(long value) {
        setResourceAttributeAsLng(FREE_SPACE, value);
    }

    public long getUsedSpace() {
        return getResourceAttributeAsLng(USED_SPACE, 0);
    }

    public void setUsedSpace(long value) {
        setResourceAttributeAsLng(USED_SPACE, value);
    }
}

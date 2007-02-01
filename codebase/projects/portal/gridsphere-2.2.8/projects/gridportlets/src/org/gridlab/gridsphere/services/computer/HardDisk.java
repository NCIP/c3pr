/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardDisk.java,v 1.1.1.1 2007-02-01 20:39:53 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.impl.BaseResource;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Iterator;

public class HardDisk extends BaseResource {

    public static final String NAME = "HARD_DISK_NAME";
    public static final String MODEL = "HARD_DISK_MODEL";

    private static PortletLog log = SportletLog.getInstance(HardDisk.class);
    private String name = null;

    public HardDisk() {
        super();
        setResourceType(HardDiskType.INSTANCE);
    }

    public HardDisk(String name) {
        super();
        setResourceType(HardDiskType.INSTANCE);
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

    public String getModel() {
        return getResourceAttributeValue(MODEL);
    }

    public void setModel(String model) {
        putResourceAttribute(MODEL, model);
    }

    public List getHardDiskPartitions() {
        return getChildResources(HardDiskPartitionType.INSTANCE);
    }

    public HardDiskPartition getHardDiskPartition(String name) {
        HardDiskPartition partition = null;
        int index = findHardDiskPartition(name);
        if (index > -1) {
            partition = (HardDiskPartition) getHardDiskPartitions().get(index);
        }
        return partition;
    }

    public HardDiskPartition createHardDiskPartition(String name) {
        HardDiskPartition partition = null;
        int index = findHardDiskPartition(name);
        if (index > -1) {
            partition = (HardDiskPartition) getHardDiskPartitions().get(index);
        } else {
            partition = new HardDiskPartition(name);
        }
        return partition;
    }

    public void putHardDiskPartition(HardDiskPartition partition) {
        int index = findHardDiskPartition(partition.getName());
        if (index > -1) {
            addChildResource(partition);
        } else {
            getChildResources().set(index, partition);
        }
    }

    public void removeHardDiskPartition(String name) {
        HardDiskPartition partition = null;
        int index = findHardDiskPartition(name);
        if (index > -1) {
            partition = (HardDiskPartition) getHardDiskPartitions().get(index);
            removeChildResource(partition);
        }
    }

    public void clearHardDiskPartitions() {
        clearChildResources();
    }

    private int findHardDiskPartition(String name) {
        int ii = -1, index = -1;
        for (Iterator partitions = getHardDiskPartitions().iterator(); partitions.hasNext();) {
            ++ii;
            HardDiskPartition partition = (HardDiskPartition) partitions.next();
            if (name.equals(partition.getName())) {
                index = ii;
                break;
            }
        }
        return index;
    }
}

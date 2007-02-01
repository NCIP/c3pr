/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ClusterNode.java,v 1.1.1.1 2007-02-01 20:39:51 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.impl.BaseHostResource;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

public class ClusterNode extends BaseHostResource {

    public static final String NAME = "CLUSTER_NODE_NAME";
    public static final String TOTAL_RAM = "CLUSTER_MEMORY_TOTAL";
    public static final String FREE_RAM = "CLUSTER_MEMORY_FREE";
    public static final String USED_RAM = "CLUSTER_MEMORY_USED";
    public static final String TOTAL_SWAP = "CLUSTER_VIRTUAL_MEMORY_TOTAL";
    public static final String FREE_SWAP = "CLUSTER_VIRTUAL_MEMORY_FREE";
    public static final String USED_SWAP = "CLUSTER_VIRTUAL_MEMORY_USED";

    private static PortletLog log = SportletLog.getInstance(ClusterNode.class);
    private String name = null;

    public ClusterNode() {
        super();
        setResourceType(ClusterNodeType.INSTANCE);
    }

    public ClusterNode(String name) {
        super();
        setResourceType(ClusterNodeType.INSTANCE);
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

    public long getTotalRam() {
        return getResourceAttributeAsLng(TOTAL_RAM, 0);
    }

    public void setTotalRam(long value) {
        setResourceAttributeAsLng(TOTAL_RAM, value);
    }

    public long getFreeRam() {
        return getResourceAttributeAsLng(FREE_RAM, 0);
    }

    public void setFreeRam(long value) {
        setResourceAttributeAsLng(FREE_RAM, value);
    }

    public long getUsedRam() {
        return getResourceAttributeAsLng(USED_RAM, 0);
    }

    public void setUsedRam(long value) {
        setResourceAttributeAsLng(USED_RAM, value);
    }

    public long getTotalSwap() {
        return getResourceAttributeAsLng(TOTAL_SWAP, 0);
    }

    public void setTotalSwap(long value) {
        setResourceAttributeAsLng(TOTAL_SWAP, value);
    }

    public long getFreeSwap() {
        return getResourceAttributeAsLng(FREE_SWAP, 0);
    }

    public void setFreeSwap(long value) {
        setResourceAttributeAsLng(FREE_SWAP, value);
    }

    public long getUsedSwap() {
        return getResourceAttributeAsLng(USED_SWAP, 0);
    }

    public void setUsedSwap(long value) {
        setResourceAttributeAsLng(USED_SWAP, value);
    }
}

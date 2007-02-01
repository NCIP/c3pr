/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ClusterResource.java,v 1.1.1.1 2007-02-01 20:39:51 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.impl.BaseResource;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Iterator;

public class ClusterResource extends BaseResource {

    public static final String NAME = "CLUSTER_NAME";

    private static PortletLog log = SportletLog.getInstance(ClusterResource.class);
    private String name = null;

    public ClusterResource() {
        super();
        setResourceType(ClusterResourceType.INSTANCE);
        setName("ClusterResource");
    }

    public String getHost() {
        String host = null;
        HardwareResource hardware = (HardwareResource)getParentResource();
        if (hardware != null) {
            host = hardware.getHost();
        }
        return host;
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

    public List getClusterNodes() {
        return getChildResources(ClusterNodeType.INSTANCE);
    }

    public ClusterNode getClusterNode(String name) {
        ClusterNode clusterNode = null;
        int index = findClusterNode(name);
        if (index > -1) {
            clusterNode = (ClusterNode) getClusterNodes().get(index);
        }
        return clusterNode;
    }

    public ClusterNode createClusterNode(String name) {
        ClusterNode clusterNode = null;
        int index = findClusterNode(name);
        if (index > -1) {
            clusterNode = (ClusterNode) getClusterNodes().get(index);
        } else {
            clusterNode = new ClusterNode(name);
            addChildResource(clusterNode);
        }
        return clusterNode;
    }

    public void putClusterNode(ClusterNode clusterNode) {
        int index = findClusterNode(clusterNode.getName());
        if (index > -1) {
            addChildResource(clusterNode);
        } else {
            getChildResources().set(index, clusterNode);
        }
    }

    public void removeClusterNode(String name) {
        ClusterNode clusterNode = null;
        int index = findClusterNode(name);
        if (index > -1) {
            clusterNode = (ClusterNode) getClusterNodes().get(index);
            removeChildResource(clusterNode);
        }
    }

    public void clearClusterNodes() {
        clearChildResources();
    }

    private int findClusterNode(String name) {
        int ii = -1, index = -1;
        for (Iterator clusterNodes = getClusterNodes().iterator(); clusterNodes.hasNext();) {
            ++ii;
            ClusterNode clusterNode = (ClusterNode) clusterNodes.next();
            if (name.equals(clusterNode.getName())) {
                index = ii;
                break;
            }
        }
        return index;
    }
}

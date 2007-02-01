/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: NetworkInterface.java,v 1.1.1.1 2007-02-01 20:39:56 kherm Exp $
 */
package org.gridlab.gridsphere.services.computer;

import org.gridlab.gridsphere.services.resource.impl.BaseResource;
import org.gridlab.gridsphere.services.resource.impl.HardwareResourceImpl;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

public class NetworkInterface extends BaseResource {

    public static final String NAME = "NETWORK_INTERFACE_NAME";
    public static final String INET_ADDRESS = "NETWORK_INTERFACE_INET_ADDRESS";
    public static final String MAC_ADDRESS = "NETWORK_INTERFACE_MAC_ADDRESS";

    private static PortletLog log = SportletLog.getInstance(NetworkInterface.class);
    private String name = null;

    public NetworkInterface() {
        super();
        setResourceType(NetworkInterfaceType.INSTANCE);
    }

    public NetworkInterface(String name) {
        super();
        setResourceType(NetworkInterfaceType.INSTANCE);
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

    public String getInetAddress() {
        return getResourceAttributeValue(INET_ADDRESS);
    }

    public void setInetAddress(String value) {
        putResourceAttribute(INET_ADDRESS, value);
    }

    public String getMacAddress() {
        return getResourceAttributeValue(MAC_ADDRESS);
    }

    public void setMacAddress(String value) {
        putResourceAttribute(MAC_ADDRESS, value);
    }
}

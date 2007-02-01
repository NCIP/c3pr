/**
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: BaseHostResource.java,v 1.1.1.1 2007-02-01 20:41:00 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;

public class BaseHostResource extends BaseResource implements HostResource {

    private static PortletLog log = SportletLog.getInstance(BaseResource.class);

    private String hostName = null;
    private String inetAddress = null;

    public BaseHostResource() {
        super();
        setResourceType(HostResourceType.INSTANCE);
    }

    public String getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(String inetAddress) {
        this.inetAddress = inetAddress;
    }

    public String getHost() {
        return (hostName == null) ? inetAddress : hostName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostname) {
        this.hostName = hostname;
    }

    public List getServiceResources() {
        return getChildResources(ServiceResourceType.INSTANCE);
    }

    public List getSoftwareResources() {
        return getChildResources(SoftwareResourceType.INSTANCE);
    }

    public List getHardwareAccounts() {
        return getChildResources(HardwareAccountType.INSTANCE);
    }

    public HardwareAccount createHardwareAccount(String userId) {
        return new HardwareAccountImpl(this, userId);
    }

    public HardwareAccount getHardwareAccount(String userId) {
        List hardwareAccountList = getChildResources(HardwareAccountType.INSTANCE);
        HardwareAccount hardwareAccount = null;
        for (int ii = 0; ii < hardwareAccountList.size(); ++ii) {
            HardwareAccount nextAccount = (HardwareAccount)hardwareAccountList.get(ii);
            if (nextAccount.getUserId().equals(userId)) {
                hardwareAccount = nextAccount;
                break;
            }
        }
        return hardwareAccount;
    }

   public void copy(Resource resource) {
        super.copy(resource);
        HostResource hardware = (HostResource)resource;
        this.hostName = hardware.getHostName();
    }
}

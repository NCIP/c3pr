package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardwareAccountImpl.java,v 1.1.1.1 2007-02-01 20:41:01 kherm Exp $
 */

public class HardwareAccountImpl extends BaseResourceAccount implements HardwareAccount {

    private static PortletLog log = SportletLog.getInstance(HardwareAccountImpl.class);

    public HardwareAccountImpl() {
        super();
        setResourceType(HardwareAccountType.INSTANCE);
    }

    public HardwareAccountImpl(HostResource resource, String userId) {
        super(resource, userId);
        setResourceType(HardwareAccountType.INSTANCE);
    }

    public String getHost() {
        if (getHostResource() == null) {
            return "";
        }
        return getHostResource().getHost();
    }

    public String getHostName() {
        if (getHostResource() == null) {
            return "";
        }
        return getHostResource().getHostName();
    }

    public String getInetAddress() {
        if (getHostResource() == null) {
            return "";
        }
        return getHostResource().getInetAddress();
    }

    public HostResource getHostResource() {
        return (HostResource)getParentResource();
    }

    public String getHomeDir() {
        return getResourceAttributeValue(HOME_DIR_ATTRIBUTE);
    }

    public void setHomeDir(String homeDir) {
        putResourceAttribute(HOME_DIR_ATTRIBUTE, homeDir);
    }

    public String getUserShell() {
        return getResourceAttributeValue(USER_SHELL_ATTRIBUTE);
    }

    public void setUserShell(String userShell) {
        putResourceAttribute(USER_SHELL_ATTRIBUTE, userShell);
    }

    public List getUserDns() {
        return getResourceAttributeValueAsList(USER_DN_ATTRIBUTE, ":::");
    }

    public void addUserDn(String userDn) {
        List userDnList = getUserDns();
        for (int ii = 0; ii < userDnList.size(); ++ii) {
            String nextDn = (String)userDnList.get(ii);
            if (nextDn.equals(userDn)) {
                return;
            }
        }
        userDnList.add(userDn);
        setResourceAttributeValueAsList(USER_DN_ATTRIBUTE, userDnList, ":::");
    }

    public void setUserDns(List userDnList) {
        setResourceAttributeValueAsList(USER_DN_ATTRIBUTE, userDnList, ":::");
    }

    public boolean hasUserDn(String userDn) {
        List userDns = getUserDns();
        for (int ii = 0; ii < userDns.size(); ++ii) {
            String nextDn = (String)userDns.get(ii);
            if (nextDn.equals(userDn)) {
                return true;
            }
        }
        return false;
    }
}

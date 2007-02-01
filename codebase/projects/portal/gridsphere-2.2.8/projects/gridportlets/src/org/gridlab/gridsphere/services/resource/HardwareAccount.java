package org.gridlab.gridsphere.services.resource;

import java.util.List;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardwareAccount.java,v 1.1.1.1 2007-02-01 20:40:50 kherm Exp $
 */

public interface HardwareAccount extends ResourceAccount {

    public final static String HOME_DIR_ATTRIBUTE = "HomeDir";
    public final static String USER_SHELL_ATTRIBUTE = "UserShell";
    public final static String USER_DN_ATTRIBUTE = "UserDn";

    public String getHost();

    public String getHostName();

    public String getInetAddress();

    public HostResource getHostResource();

    public List getUserDns();

    public boolean hasUserDn(String userDn);

    public void addUserDn(String dn);

    public void setUserDns(List userDns);

    public String getHomeDir();

    public void setHomeDir(String homeDir);

    public String getUserShell();

    public void setUserShell(String userShell);
}

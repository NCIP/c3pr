/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HardwareAccountProvider.java,v 1.1.1.1 2007-02-01 20:41:21 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.mds2.providers;

import org.gridlab.gridsphere.services.resources.mds2.Mds2ResourceProvider;
import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.naming.directory.SearchControls;


/**
 * dn: GridLab-Mds-User-ID=glab010,GridLab-Mds-User-Group=users,Mds-Host-hn=peyo
 te.aei.mpg.de, Mds-Vo-name=local,o=Grid
GridLab-Mds-User-Mapped-DN: /C=US/O=National Computational Science Alliance/C
 N=Miroslav Ruda
GridLab-Mds-User-Mapped-DN: /O=CESNET/O=Masaryk University/CN=Miroslav Ruda
GridLab-Mds-User-shell: /bin/bash
objectClass: GridLabUserUniqueID
objectClass: GridLabUser
GridLab-Mds-User-homedir: /home/glab010
Mds-keepto: 20040503203354Z
GridLab-Mds-User-ID: glab010
Mds-validto: 20040503203354Z
Mds-validfrom: 20040503203254Z
GridLab-Mds-User-GID: 110
GridLab-Mds-User-UID: 10010
 */
public class HardwareAccountProvider extends Mds2ResourceProvider {

    private static PortletLog log = SportletLog.getInstance(HardwareAccountProvider.class);

    public HardwareAccountProvider(ResourceRegistryService resourceRegistry) {

        super(resourceRegistry);

        addResourceAttribute("GridLab-Mds-User-ID", HardwareAccount.USER_ID_ATTRIBUTE);
        addResourceAttribute("GridLab-Mds-User-Mapped-DN", HardwareAccount.USER_DN_ATTRIBUTE);
        addResourceAttribute("GridLab-Mds-User-homedir", HardwareAccount.HOME_DIR_ATTRIBUTE);
        addResourceAttribute("GridLab-Mds-User-shell", HardwareAccount.USER_SHELL_ATTRIBUTE);
    }

    public Resource createResource() {
        HardwareResource hardwareResource = (HardwareResource)parentProvider.getResource();
        HardwareAccount hardwareAccount = hardwareResource.getHardwareAccount(resourceName);
        //log.debug("Retrieving hardware account " + resourceName
        //          + " on " + hardwareResource.getFileHostName());
        if (hardwareAccount == null) {
            //log.debug("Creating hardware account " + resourceName
            //          + " on " + hardwareResource.getHost());
            hardwareAccount = hardwareResource.createHardwareAccount(resourceName);
            hardwareResource.addChildResource(hardwareAccount);
            try {
                resourceRegistry.saveResource(hardwareResource);
            } catch (ResourceException e) {
                log.error("Unable to save resource ", e);
            }
        } else {
            //log.debug("Found hardware account " + resourceName
            //          + " on " + hardwareResource.getHost());
        }
        return hardwareAccount;
    }

    public String getResourceSearchDn() {
        StringBuffer baseDn = new StringBuffer();
        baseDn.append("GridLab-Mds-User-Group=users,");
        baseDn.append(parentProvider.getResourceDn());
        return baseDn.toString();
    }

    public String getResourceSearchFilter() {
        return "(objectclass=GridLabUserUniqueID)";
    }

    public int getResourceSearchScope() {
        return SearchControls.ONELEVEL_SCOPE;
    }
}

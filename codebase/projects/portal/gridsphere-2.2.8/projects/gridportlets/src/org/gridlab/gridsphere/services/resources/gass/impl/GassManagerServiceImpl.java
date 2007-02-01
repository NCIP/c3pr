package org.gridlab.gridsphere.services.resources.gass.impl;

import org.gridlab.gridsphere.services.resources.gass.GassManagerService;
import org.gridlab.gridsphere.services.resources.gass.GassResource;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.services.util.GridPortletsDatabase;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

import java.io.IOException;
import java.util.Hashtable;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GassManagerServiceImpl.java,v 1.1.1.1 2007-02-01 20:41:04 kherm Exp $
 */

public class GassManagerServiceImpl
        implements GassManagerService,
        PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(GassManagerServiceImpl.class);
    private static Hashtable gassResources = new Hashtable();
    private GridPortletsDatabase pm = null;


    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        pm = GridPortletsDatabase.getInstance();
        String portRange = config.getInitParameter("TcpPortRange");
        if (portRange != null && portRange.length() > 0){
            System.setProperty("org.globus.tcp.port.range", portRange);
        }
    }

    public void destroy() {
    }

    /**
     * Returns a gass resource for the given credential.
     * @param credential
     * @return
     * @throws IOException
     */
    public GassResource createGassResource(GSSCredential credential)
            throws GSSException, IOException {
        GassResourceImpl gassResource = null;
        String dn = credential.getName().toString();
        if (gassResources.containsKey(dn)) {
            // If a gass resource for this credential has already been created...
            gassResource = (GassResourceImpl)gassResources.get(dn);
            GSSCredential gassCredential = gassResource.getCredential();
            // Restart gass resource if the gass credential is null or our the credential
            // given to this method is newer than the gass credential
            if (gassCredential == null ||
                (gassCredential.getRemainingLifetime() < credential.getRemainingLifetime())) {
                gassResource.shutdown();
                gassResource.startup(credential);
            }
        } else {
            // Otherwise get an instance of the resource from the database.
            gassResource = getGassResourceByDn(dn);
            if (gassResource == null) {
                // If none exists yet, create one...
                gassResource = new GassResourceImpl(credential);
            } else {
                // Otherwise startup the gass resource
                gassResource.startup(credential);
            }
            // Keep instance of gass resource
            gassResources.put(dn, gassResource);
        }
        // Update record of this resource...
        saveGassResource(gassResource);
        return gassResource;
    }

    /**
     * Creates a gass location for the given file path
     * @param credential
     * @return
     * @throws GSSException
     * @throws IOException
     */
    public FileLocation createGassFileLocation(GSSCredential credential, String path)
            throws GSSException, IOException {
        return createGassResource(credential).createGassFileLocation(path);
    }

    /**
     * Creates a gass location for the given file
     * @param credential
     * @param prefix
     * @param suffix
     * @return
     * @throws GSSException
     * @throws IOException
     */
    public FileLocation createGassTempLocation(GSSCredential credential, String prefix, String suffix)
            throws GSSException, IOException {
        return createGassResource(credential).createGassTempLocation(prefix, suffix);
    }

    private GassResourceImpl getGassResourceByDn(String dn) {
        GassResourceImpl gassResource = null;
        try {
            gassResource = (GassResourceImpl)
                    pm.restore("from " + GassResourceImpl.class.getName()
                               + " as r where r.Dn='" + dn + "'");
        } catch (Exception e) {
            log.warn("Could not retrieve gass resource by dn " + dn + "!\n" + e);
        }
        return gassResource;
    }

    private void saveGassResource(GassResourceImpl resource) {
        try {
            if (resource.getOid() == null) {
                pm.create(resource);
            } else {
                pm.update(resource);
            }
        } catch (Exception e) {
            log.warn("Could not save gass resource " + resource.getOid() + "!\n" + e);
        }
    }
}

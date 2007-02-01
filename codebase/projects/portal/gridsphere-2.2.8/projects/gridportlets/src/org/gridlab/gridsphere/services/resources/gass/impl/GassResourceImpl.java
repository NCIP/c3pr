package org.gridlab.gridsphere.services.resources.gass.impl;

import org.gridlab.gridsphere.services.resources.gass.GassResource;
import org.gridlab.gridsphere.services.resources.system.LocalHostBrowserService;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.globus.io.gass.server.GassServer;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

import java.io.IOException;
import java.io.File;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GassResourceImpl.java,v 1.1.1.1 2007-02-01 20:41:04 kherm Exp $
 * <p>
 * Implements the gass resource interface.
 */

public class GassResourceImpl implements GassResource {

    private static PortletLog log = SportletLog.getInstance(GassResourceImpl.class);
    private String oid = null;
    private String dn = null;
    private int port = 0;
    private GSSCredential credential = null;
    private GassServer gassServer = null;

    public GassResourceImpl() {
    }

    public GassResourceImpl(GSSCredential credential)
            throws GSSException, IOException {
        startup(credential);
    }

    public void startup(GSSCredential credential)
            throws GSSException, IOException {
        this.credential = credential;
        this.dn = credential.getName().toString();
        gassServer = null;
        try {
            log.debug("Starting up gass server");
            //gassServer = new GassServer(credential, port);
            gassServer = new GassServer(credential,0);
            gassServer.setOptions(GassServer.STDOUT_ENABLE |
                                  GassServer.STDERR_ENABLE |
                                  GassServer.READ_ENABLE |
                                  GassServer.WRITE_ENABLE);
            log.debug("GASS server started at " + gassServer.getURL());
            port = gassServer.getPort();
        } catch (IOException e) {
            log.error("Unable to create gass server for credential " + credential, e);
            throw e;
        }
    }

    public void shutdown() {
        if (gassServer != null) {
            gassServer.shutdown();
        }
        credential = null;
    }

    /**
     * Returns the object id of this gass resource.
     * @return
     */
    public String getOid() {
        return oid;
    }

    /**
     * Sets the object id of this gass resource.
     * @param oid
     */
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * Returns the dn associated with this resource.
     * @return
     */
    public String getDn() {
        return dn;
    }

    /**
     * Sets the dn associated with this resource.
     * @param dn
     */
    public void setDn(String dn) {
        this.dn = dn;
    }

    /**
     * Returns the port associated with this resource.
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port associated with this resource.
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }


    /**
      * Returns the credential that was used to create this
      * gass resource.
      * @return
      */
     public GSSCredential getCredential() {
        return credential;
    }

    /**
      * Returns the gass server associated with this resource.
      * @return
      * @throws java.io.IOException
      */
     public GassServer getGassServer()
             throws GSSException, IOException {
        if (credential == null || credential.getRemainingLifetime() == 0) {
            // Should refresh credential and restart server somehow
            credential = null;
            gassServer.shutdown();
            gassServer = null;
            throw new IOException("Credential has expired");
        }
        return gassServer;
     }

     /**
      * Returns the gass location for the given file path
      * @param path
      * @return
      * @throws java.io.IOException
      */
     public FileLocation createGassFileLocation(String path)
             throws GSSException, IOException {
         GassServer gassServer = getGassServer();
         String url = gassServer.getURL() + "/" + path;
         log.debug("Gass location = " + url);
         FileLocation gassLocation = new FileLocation(url);
         return gassLocation;
     }

    /**
     * Returns the temp gass location with the given tempId
     * @param prefix
     * @param suffix
     * @return
     * @throws java.io.IOException
     */
    public FileLocation createGassTempLocation(String prefix, String suffix)
            throws GSSException, IOException {
        File file = File.createTempFile(prefix, suffix);
        String path = file.getPath();
        GassServer gassServer = getGassServer();
        String url = gassServer.getURL() + "/" + path;
        FileLocation gassLocation = new FileLocation(url);
        return gassLocation;
    }
}

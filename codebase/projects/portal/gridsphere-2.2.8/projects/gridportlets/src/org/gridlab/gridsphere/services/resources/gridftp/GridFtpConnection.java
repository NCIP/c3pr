package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.security.gss.impl.BaseGssConnection;
import org.gridlab.gridsphere.services.security.gss.impl.GssConnectionManager;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.io.IOException;

import org.ietf.jgss.GSSException;

import org.globus.ftp.GridFTPClient;
import org.globus.ftp.DataChannelAuthentication;
import org.globus.ftp.exception.ServerException;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpConnection.java,v 1.1.1.1 2007-02-01 20:41:10 kherm Exp $
 * <p>
 * Represents a connection to a gridftp resource.
 */

public class GridFtpConnection extends BaseGssConnection {

    private static PortletLog log = SportletLog.getInstance(GridFtpConnection.class);
    private GridFTPClient gridFtpClient = null;
    private boolean dcau = true;

    public GridFtpConnection(GssConnectionManager manager) {
        super(manager);
    }

    public void setDataChannelAuth(boolean dcau) {
        this.dcau = dcau;
    }

    public void openConnection()
            throws GSSException, ResourceException {
        try {
            if (port == null || port.equals("")) {
                port = "2811";
            }
            Integer portNum = new Integer(port);
            log.debug("Opening grid ftp connection to " + hostName);
            gridFtpClient = new GridFTPClient(hostName, portNum.intValue());
            log.debug("Authenticating with crdential");
            gridFtpClient.authenticate(credential);
            if (!dcau) {
                log.debug("Turning off data channel authentication");
                gridFtpClient.setDataChannelAuthentication(DataChannelAuthentication.NONE);
            }
            //log.debug("Setting extended mode block");
            //gridFtpClient.setMode(GridFTPSession.MODE_EBLOCK);
        } catch (IOException e) {
            log.error("Unable to connect to grid ftp resource", e);
            throw new GridFtpException("Unable to connect to grid ftp resource", e);
        } catch (ServerException e) {
            log.error("Unable to connect to grid ftp resource", e);
            throw new GridFtpException("Unable to connect to grid ftp resource", e);
        }
    }

    public void closeConnection() {
        try {
            log.debug("Closing grid ftp connection to " + hostName);
            gridFtpClient.close();
        } catch (IOException e) {
            log.error("Unable to close grid ftp connection", e);
        } catch (ServerException e) {
            log.error("Unable to close grid ftp connection", e);
        }
        gridFtpClient = null;
    }

    public void release() {
        // Force a close on release for now...
        close();
        super.release();
    }

    public GridFTPClient getGridFTPClient()
            throws GSSException, ResourceException {
        open();
        return gridFtpClient;
    }

    public String getCurrentDir()
            throws GridFtpException {
        try {
            return gridFtpClient.getCurrentDir();
        } catch (Exception e) {
            throw new GridFtpException(e.getMessage());
        }
    }

    public void changeDir(String dir)
            throws GridFtpException {
        try {
            gridFtpClient.changeDir(dir);
        } catch (Exception e) {
            throw new GridFtpException(e.getMessage());
        }
    }

    public void goUpDir()
            throws GridFtpException {
        try {
            gridFtpClient.goUpDir();
        } catch (Exception e) {
            throw new GridFtpException(e.getMessage());
        }
    }

}

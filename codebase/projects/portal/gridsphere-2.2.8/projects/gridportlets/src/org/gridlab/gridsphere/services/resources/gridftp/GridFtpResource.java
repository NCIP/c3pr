package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.security.gss.GssConnection;
import org.gridlab.gridsphere.services.security.gss.impl.GssConnectionManager;
import org.gridlab.gridsphere.services.security.gss.impl.BaseGssConnectionResource;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.resource.ServiceLocation;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileException;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowser;
import org.gridlab.gridsphere.services.common.Location;
import org.gridlab.gridsphere.services.util.ServiceUtil;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSCredential;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpResource.java,v 1.1.1.1 2007-02-01 20:41:13 kherm Exp $
 * <p>
 * Implements a grid ftp resource.
 */

public class GridFtpResource extends BaseGssConnectionResource implements FileResource {

    private static PortletLog log = SportletLog.getInstance(GridFtpResource.class);

    public static final String DEFAULT_PORT = "2811";
    public static final String DEFAULT_PROTOCOL = "gridftp";
   // normally data channel authentication is turned on, but in case of HPSS this should be false
    private boolean dcau = true;

    public GridFtpResource() {
        super();
        setPort(DEFAULT_PORT);
        setProtocol(DEFAULT_PROTOCOL);
        setResourceType(GridFtpResourceType.INSTANCE);
    }

    public FileBrowser createFileBrowser(User user) throws FileException {
        GridFtpBrowser fileBrowser = new GridFtpBrowser();
        GridFtpBrowserService fileBrowserService = (GridFtpBrowserService)
                ServiceUtil.getPortletService(user, GridFtpBrowserService.class);
        fileBrowser.init(fileBrowserService, user, this);
        return fileBrowser;
    }

    public Location getLocation() {
        int portNum = 0;
        String port = getPort();
        log.debug("Creating a location for " + getDn() +  " " + getProtocol() + "://" + getHost() + ":" + port);
        if (port != null && !port.equals("")) {
            try {
                portNum = Integer.parseInt(port);
            } catch (Exception e) {
                log.warn("Invalid port number " + port);
            }
        }
        return new FileLocation(getProtocol(), getHost(), portNum, null);
    }

    public boolean getDataChannelAuth() {
        return dcau;
    }

    public void setDataChannelAuth(boolean dcau) {
        this.dcau = dcau;
    }

    public GssConnectionManager getConnectionManager() {
        return new GridFtpConnectionManager(this);
    }

    public GssConnection createGssConnection(User user)
            throws GSSException, ResourceException {
        return connectionManager.createGssConnection(user);
    }

    public GssConnection createGssConnection(GSSCredential credential)
            throws GSSException, ResourceException {
        return connectionManager.createGssConnection(credential);
    }

    public void destroyGssConnections(User user) {
        connectionManager.destroyGssConnections(user);
    }

    public void destroyGssConnection(GSSCredential credential) {
        connectionManager.destroyGssConnection(credential);
    }

    public GridFtpConnection createGridFtpConnection(User user)
            throws GSSException, ResourceException {
        return (GridFtpConnection)connectionManager.createGssConnection(user);
    }
}

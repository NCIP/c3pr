/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.services.resource.impl.BaseWebServiceResource;
import org.gridlab.gridsphere.services.security.gss.HttpgServiceResource;
import org.gridlab.gridsphere.services.security.gss.*;
import org.gridlab.gridsphere.services.common.Location;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.globus.util.Util;

/**
 * Base abstract implementation for httpg service resources.
 */
public class BaseHttpgServiceResource
        extends BaseWebServiceResource
        implements HttpgServiceResource {

    private static PortletLog log = SportletLog.getInstance(BaseHttpgServiceResource.class);

    protected String authorization = "none";
    protected String delegation = "full";
    protected String serviceDn = null;
    protected String timeOut = "5";
    protected HttpgServiceConnectionManager connectionManager = null;

    public BaseHttpgServiceResource() {
        super();
        setResourceType(HttpgServiceResourceType.INSTANCE);
        setProtocol(HttpgServiceLocation.DEFAULT_PROTOCOL);
        setPort(String.valueOf(HttpgServiceLocation.DEFAULT_PORT));
        connectionManager = getConnectionManager();
        connectionManager.setResource(this);
    }

    public String getDelegation() {
        return delegation;
    }

    public void setDelegation(String deleg) {
        delegation  = deleg;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String auth) {
        authorization  = auth;
    }

    public String getServiceDn() {
        return serviceDn;
    }

    public void setServiceDn(String dn) {
        serviceDn  = dn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String min) {
        timeOut  = min;
    }

    protected HttpgServiceConnectionManager getConnectionManager() {
        return new HttpgServiceConnectionManager();
    }

    public void authenticates(GSSCredential credential)
            throws GSSException, ResourceException {
        try {
            GssConnection connection = createGssConnection(credential);
            connection.release();
        } catch (GSSException e) {
            log.error("Authentication of credential failed ", e);
            throw e;

        } catch (ResourceException e) {
            log.error("Authentication of credential failed ", e);
            throw e;
        }
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

    public Location getLocation() {
            String host = getHost();
        if (host.equalsIgnoreCase("localhost") || host.equals("127.0.0.1")) {
            host = Util.getLocalHostAddress();
        }
        int portNum = 0;
        try {
            portNum = Integer.valueOf(getPort()).intValue();
        } catch (Exception e) {
            log.error("Bad port num" + getPort());
        }
        return new HttpgServiceLocation(getHost(), portNum, getServicePath());
    }

    public void copy(Resource resource) {
        super.copy(resource);
        HttpgServiceResource httpgResource = (HttpgServiceResource)resource;
        authorization = httpgResource.getAuthorization();
        delegation = httpgResource.getDelegation();
        timeOut = httpgResource.getTimeOut();
        serviceDn = httpgResource.getServiceDn();
    }
}

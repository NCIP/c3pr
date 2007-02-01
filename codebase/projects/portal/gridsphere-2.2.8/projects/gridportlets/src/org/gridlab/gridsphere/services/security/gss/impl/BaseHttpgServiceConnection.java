/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.security.gss.HttpgServiceConnection;
import org.gridlab.gridsphere.services.security.gss.HttpgServiceResource;
import org.gridlab.gridsphere.services.security.gss.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;

import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSCredential;

import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.SimpleTargetedChain;
import org.globus.axis.transport.GSIHTTPSender;
import org.globus.axis.transport.GSIHTTPTransport;
import org.globus.axis.util.Util;
import org.globus.axis.gsi.GSIConstants;
import org.globus.gsi.gssapi.auth.NoAuthorization;
import org.globus.gsi.gssapi.auth.Authorization;
import org.globus.gsi.gssapi.auth.IdentityAuthorization;
import org.globus.gsi.gssapi.auth.HostAuthorization;

import javax.xml.rpc.ServiceException;

/**
 * Provides a base implementation for a connection to an httpg service resource.
 */
public abstract class BaseHttpgServiceConnection
        extends BaseGssConnection
        implements HttpgServiceConnection {

    private static PortletLog log = SportletLog.getInstance(BaseHttpgServiceConnection.class);
    protected String url = null;
    protected String authorization = "none";
    protected String delegation = "full";
    protected String serviceDn = null;
    protected String timeOut = "5";
    protected Remote remoteClient = null;

    public BaseHttpgServiceConnection() {
        super();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url  = url;
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

    public void setConnectionParams(GssConnectionResource resource) {
        super.setConnectionParams(resource);
        HttpgServiceResource httpgResource = (HttpgServiceResource)resource;
        url = httpgResource.getUrl();
        authorization = httpgResource.getAuthorization();
        delegation = httpgResource.getDelegation();
        serviceDn = httpgResource.getServiceDn();
        timeOut = httpgResource.getTimeOut();
        log.debug("setConnectionParams " + url);
    }

    public void openConnection()
            throws GSSException, ResourceException {
        try {
            remoteClient = createRemoteClient(url, credential);
        } catch (ServiceException e) {
        } catch (MalformedURLException e) {
            String message = "Unable to open connection to "
                    + url + "; " + e.getMessage();
            throw new ResourceException(message, e);
        }
    }

    public void closeConnection() {
        remoteClient = null;
    }

    public Remote createRemoteClient(String url, GSSCredential credential)
            throws ServiceException, MalformedURLException, GSSException {
        Remote remoteClient = null;
        Service service = createServiceLocator();
        prepareService(service);
        log.debug("createRemoteClient " + url);
        remoteClient = createRemoteClient(service, new URL(url));
        preparePort(remoteClient, credential);
        return remoteClient;
    }

    public abstract Service createServiceLocator();

    public abstract Remote createRemoteClient(Service service, URL url) throws ServiceException;

    public void prepareService(Service s) {
        SimpleProvider p = new SimpleProvider();
        p.deployTransport("httpg", new SimpleTargetedChain(new GSIHTTPSender()));
        s.setEngineConfiguration(p);
        Util.registerTransport();
    }

    public void preparePort(Remote ws, GSSCredential cred) throws GSSException {
        Stub stub = (Stub) ws;
        // Credential...
        stub._setProperty(GSIHTTPTransport.GSI_CREDENTIALS, cred);
        // Delegation...
        stub._setProperty(GSIHTTPTransport.GSI_MODE, getDelegType(authorization, delegation));
        // Authorization...
        stub._setProperty(GSIHTTPTransport.GSI_AUTHORIZATION, getAuthorization(authorization, serviceDn));
        // Time out...
        stub.setTimeout(getTimeOut(timeOut)); // 5 minutes
    }

    private Authorization getAuthorization(String auth, String dn) {
        Authorization authorization = null;

        if (auth == null || auth.equals("") || auth.equals("none") ) {
            authorization = new NoAuthorization();
            log.debug("Authorization: NONE");
        } else if (auth.equalsIgnoreCase("identity")) {
            authorization = new IdentityAuthorization(dn);
            log.debug("Authorization Identity: " + dn);
        } else if (auth.equalsIgnoreCase("host")) {
            authorization = new HostAuthorization(dn);
            log.debug("Authorization Host: " + dn);
        } else {
            log.debug("Unknown authorization, using NONE");
            authorization = new NoAuthorization();
        }

        return authorization;
    }

    private int getTimeOut(String timeout) {
        int timeOut = 5;
        try {
            timeOut = Integer.parseInt(timeout);
            log.debug("TimeOut: " + timeout + " minute(s)");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return timeOut * 60 * 1000;
    }

    private String getDelegType(String auth, String argDelegType) {
        if (auth == null || auth.equals("") || auth.equals("none")) {
            return  GSIConstants.GSI_MODE_NO_DELEG;
        }
        if (argDelegType == null ||
            argDelegType.equalsIgnoreCase("full")) {
            log.debug("Delegation mode: GSIConstants.GSI_MODE_FULL_DELEG");
            return GSIConstants.GSI_MODE_FULL_DELEG;
        } else if (argDelegType.equalsIgnoreCase("limited")) {
            log.debug("Delegation mode: GSIConstants.GSI_MODE_LIMITED_DELEG");
            return GSIConstants.GSI_MODE_LIMITED_DELEG;
        } else if (argDelegType.equalsIgnoreCase("none")) {
            log.debug("Delegation mode: GSIConstants.GSI_MODE_NO_DELEG");
            return GSIConstants.GSI_MODE_NO_DELEG;
        } else {
            return GSIConstants.GSI_MODE_FULL_DELEG;
        }
    }

    public void release() {
        // Force a close on release for now...
        close();
        super.release();
    }

    public Remote getRemoteClient()
            throws GSSException, ResourceException {
        if (!isOpen) {
            open();
        }
        return remoteClient;
    }
}

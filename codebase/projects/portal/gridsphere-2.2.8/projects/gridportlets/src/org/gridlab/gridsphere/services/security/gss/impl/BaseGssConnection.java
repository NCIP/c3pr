/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseGssConnection.java,v 1.1.1.1 2007-02-01 20:41:41 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.security.gss.GssConnection;
import org.gridlab.gridsphere.services.security.gss.GssEnabledResource;
import org.gridlab.gridsphere.services.security.gss.GssConnectionResource;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.common.Location;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

/**
 * Implements the gss connection interface.
 */
public class BaseGssConnection implements GssConnection {

    protected GssConnectionManager manager = null;
    protected GSSCredential credential = null;
    protected String userName = null;
    protected String protocol = null;
    protected String hostName = null;
    protected String port = null;
    protected String servicePath = null;
    protected boolean isOpen = false;

    public BaseGssConnection() {
    }

    public BaseGssConnection(GssConnectionManager manager) {
        this.manager = manager;
    }

    public GssConnectionManager getConnectionManager() {
        return manager;
    }

    public void setConnectionManager(GssConnectionManager manager) {
        this.manager = manager;
    }

    public void init() {
        credential = null;
        userName = null;
        protocol = null;
        hostName = null;
        port = null;
        servicePath = null;
    }

    public void destroy() {
        release();
        init();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open()
            throws GSSException, ResourceException {
        if (!isOpen) {
            openConnection();
        }
        isOpen = true;
    }

    public void openConnection()
            throws GSSException, ResourceException {
        throw new ResourceException("BaseGssConnection.openConnection() needs to be extended");
    }

    public void close() {
        if (isOpen) {
            isOpen = false;
            closeConnection();
        }
    }

    public void closeConnection() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        StringBuffer buffer = new StringBuffer();
        if (protocol != null && !protocol.equals("")) {
            buffer.append(protocol);
            buffer.append("//");
        }
        if (hostName != null && !hostName.equals("")) {
            buffer.append(hostName);
        }
        if (port != null && !port.equals("")) {
            buffer.append(':');
            buffer.append(port);
        }
        if (servicePath != null && !servicePath.equals("")) {
            if (!servicePath.startsWith("/")) {
                buffer.append('/');
            }
            buffer.append(servicePath);
        }
        return buffer.toString();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setConnectionParams(GssConnectionResource resource) {
        protocol = resource.getProtocol();
        hostName = resource.getHostName();
        port = resource.getPort();
        servicePath = resource.getServicePath();
    }

    public GSSCredential getCredential() {
        return credential;
    }

    public void setCredential(GSSCredential credential) {
        this.credential = credential;
    }

    public void release() {
        manager.releaseGssConnection(this);
    }
}

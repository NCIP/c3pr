/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GssConnection.java,v 1.1.1.1 2007-02-01 20:41:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.resource.ResourceException;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

/**
 * Represents a connection to a gss secured resource.
 */
public interface GssConnection {

    /**
     * Returns the user to which this connection belongs.
     * @return The user name
     */
    public String getUserName();

    /**
     * Returns the credential used to create this connection.
     * @return The user credential
     */
    public GSSCredential getCredential();

    /**
     * Returns the url for this connection
     * @return The url
     */
    public String getUrl();

    /**
     * Returns the protocol for this connection
     * @return The protocol
     */
    public String getProtocol();

    /**
     * Sets the protocol for this connection
     * @param protocol The protocol
     */
    public void setProtocol(String protocol);

    /**
     * Returns the host name for this connection
     * @return The host name
     */
    public String getHostName();

    /**
     * Sets the host name for this connection
     * @param host The host name
     */
    public void setHostName(String host);

    /**
     * Returns the port for this connection
     * @return The port number
     */
    public String getPort();

    /**
     * Sets the port for this connection.
     * @param port The port number
     */
    public void setPort(String port);

    /**
     * Returns the service path for this connection
     * @return The service path
     */
    public String getServicePath();

    /**
     * Sets the service path for this connection
     * @param servicePath The service path
     */
    public void setServicePath(String servicePath);

    /**
     * Returns true if this connection is open.
     * @return True if open, false otherwise
     */
    public boolean isOpen();

    /**
     * Opens the connection
     * @throws GSSException
     * @throws ResourceException
     */
    public void open()
            throws GSSException, ResourceException;


    /**
     * Closes the connection
     */
    public void close();

    /**
     * Releases this connection back to its resource.
     */
    public void release();
}

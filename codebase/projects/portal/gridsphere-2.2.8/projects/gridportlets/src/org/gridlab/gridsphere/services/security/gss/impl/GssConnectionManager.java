/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GssConnectionManager.java,v 1.1.1.1 2007-02-01 20:41:49 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.security.gss.GssConnection;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.security.gss.CredentialUtil;
import org.gridlab.gridsphere.services.security.gss.GssConnectionResource;
import org.gridlab.gridsphere.portlet.User;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

import java.util.List;

/**
 * Manages connections for a particular resource.
 * TODO: Simply returns a new connection for now....
 */
public abstract class GssConnectionManager {

    protected GssConnectionResource resource = null;

    public GssConnectionManager() {
    }

    public GssConnectionManager(GssConnectionResource resource) {
        this.resource = resource;
    }

    public GssConnectionResource getResource() {
        return resource;
    }

    public void setResource(GssConnectionResource resource) {
        this.resource = resource;
    }

     /**
     * Returns a gss connection for the given user.
     * @param user The user
     * @return The gss connection
     */
    public GssConnection createGssConnection(User user)
            throws GSSException, ResourceException {
        ResourceException error = null;
        boolean errorFlag = false;
        //List credentials = CredentialUtil.getActiveCredentials(user, resource);
        List credentials = CredentialUtil.getActiveCredentials(user);
        for (int ii = 0; ii < credentials.size(); ++ii) {
            GSSCredential credential = (GSSCredential)credentials.get(ii);
            if (credential == null) {
                throw new ResourceException("No active user credentials for " + user.getUserID());
            }
            try {
                return getGssConnection(user, credential);
            } catch (GSSException e) {
                if (error == null) {
                    error = new ResourceException(e.getMessage(), e);
                }
            } catch (ResourceException e) {
                if (error == null) {
                    error = new ResourceException(e.getMessage(), e);
                }
            }
        }
        throw error;
    }

    /**
     * Returns a gss connection for the given credential.
     * TODO: Simply returns a new connection for now....
     * @param credential The credential
     * @return The gss connection
     */
    public GssConnection getGssConnection(User user, GSSCredential credential)
                throws GSSException, ResourceException {
           return createGssConnection(user, credential);
    }

    /**
     * Creates a gss connection for the given credential.
     * @param credential The credential
     * @return The gss connection
     */
    public GssConnection createGssConnection(User user, GSSCredential credential)
                throws GSSException, ResourceException {
        BaseGssConnection connection = createGssConnection(credential);
        connection.setUserName(user.getUserName());
        return connection;
    }

    /**
     * Creates a gss connection for the given credential.
     * @param credential The credential
     * @return The gss connection
     */
    public abstract BaseGssConnection createGssConnection(GSSCredential credential)
                throws GSSException, ResourceException;

    /**
     * Destroys all the gss connections for the given user.
     * @param user The user
     */
    public void destroyGssConnections(User user) {
        ResourceException error = null;
        //List credentials = CredentialUtil.getActiveCredentials(user, resource);
        List credentials = CredentialUtil.getActiveCredentials(user);
        for (int ii = 0; ii < credentials.size(); ++ii) {
            GSSCredential credential = (GSSCredential)credentials.get(ii);
            destroyGssConnection(credential);
        }
    }

    /**
     * Destroys the gss connection for the given credential
     * @param credential The credential
     */
    public void destroyGssConnection(GSSCredential credential) {
        // Does nothing at the moment...
    }

    /**
     * Releases the gss connection for the given credential
     * @param connection The connection
     */
    public void releaseGssConnection(GssConnection connection) {
        // Does nothing at the moment...
    }
}

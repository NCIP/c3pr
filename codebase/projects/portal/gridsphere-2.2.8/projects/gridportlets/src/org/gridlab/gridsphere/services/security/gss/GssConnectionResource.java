/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GssConnectionResource.java,v 1.1.1.1 2007-02-01 20:41:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.resource.ServiceResource;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

/**
 * Describes a service resource to which gss secured connections can be made.
 */
public interface GssConnectionResource extends ServiceResource, GssEnabledResource {

    /**
     * Returns a gss connection for the given user.
     * @param user The user
     * @return The gss connection
     */
    public GssConnection createGssConnection(User user)
            throws GSSException, ResourceException;

    /**
     * Returns a secure connection for the given credential.
     * @param credential The credential
     * @return The gss connnection
     */
    public GssConnection createGssConnection(GSSCredential credential)
            throws GSSException, ResourceException;

    /**
     * Destroys all the secure connections for the given user.
     * @param user The user
     */
    public void destroyGssConnections(User user);

    /**
     * Destroys the secure srcConnection for the given credential
     * @param credential The credential
     */
    public void destroyGssConnection(GSSCredential credential);
}

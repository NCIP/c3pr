/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.security.gss.impl.GssConnectionManager;
import org.gridlab.gridsphere.services.security.gss.impl.BaseGssConnection;
import org.gridlab.gridsphere.services.security.gss.GssEnabledResource;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

/**
 * Base implementation for a connection manager to an httpg service resource.
 */
public class HttpgServiceConnectionManager extends GssConnectionManager {

    public HttpgServiceConnectionManager() {
        super();
    }

    public BaseGssConnection createGssConnection(GSSCredential credential)
            throws GSSException, ResourceException {
        BaseGssConnection connection = createGssConnection();
        connection.setConnectionManager(this);
        connection.setConnectionParams(resource);
        connection.setCredential(credential);
        connection.open();
        return connection;
    }

    public BaseGssConnection createGssConnection() {
        return new BaseGssConnection();    
    }
}

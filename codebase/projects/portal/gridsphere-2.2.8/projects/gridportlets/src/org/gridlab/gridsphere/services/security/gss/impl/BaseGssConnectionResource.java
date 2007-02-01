/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.security.gss.GssConnection;
import org.gridlab.gridsphere.services.security.gss.GssConnectionResource;
import org.gridlab.gridsphere.services.security.gss.GssConnectionResourceType;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

/**
 * Base abstract implementation for httpg service resources.
 */
public abstract class BaseGssConnectionResource
        extends BaseGssServiceResource
        implements GssConnectionResource {

    private static PortletLog log = SportletLog.getInstance(BaseGssConnectionResource.class);

    protected GssConnectionManager connectionManager = null;

    public BaseGssConnectionResource() {
        super();
        setResourceType(GssConnectionResourceType.INSTANCE);
        connectionManager = getConnectionManager();
        connectionManager.setResource(this);
    }

    protected abstract GssConnectionManager getConnectionManager();

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
}

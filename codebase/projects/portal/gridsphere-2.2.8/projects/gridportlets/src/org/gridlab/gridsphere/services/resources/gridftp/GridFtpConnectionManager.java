package org.gridlab.gridsphere.services.resources.gridftp;

import org.gridlab.gridsphere.services.security.gss.impl.GssConnectionManager;
import org.gridlab.gridsphere.services.security.gss.impl.BaseGssConnection;
import org.gridlab.gridsphere.services.security.gss.GssConnectionResource;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.resources.gridftp.GridFtpConnection;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridFtpConnectionManager.java,v 1.1.1.1 2007-02-01 20:41:10 kherm Exp $
 * <p>
 * Manages connections to a grid ftp resource.
 */

public class GridFtpConnectionManager extends GssConnectionManager {

    public GridFtpConnectionManager(GssConnectionResource resource) {
        super(resource);
    }

    public BaseGssConnection createGssConnection(GSSCredential credential)
            throws GSSException, ResourceException {
        GridFtpConnection connection = new GridFtpConnection(this);
        connection.setConnectionParams(resource);
        connection.setDataChannelAuth(((GridFtpResource)resource).getDataChannelAuth());
        connection.setCredential(credential);
        connection.open();
        return connection;
    }
}

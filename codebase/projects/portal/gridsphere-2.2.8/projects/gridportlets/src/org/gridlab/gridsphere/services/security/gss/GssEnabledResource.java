/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GssEnabledResource.java,v 1.1.1.1 2007-02-01 20:41:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.resource.ServiceResource;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.resource.Resource;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

/**
 * Describes a service resource to which gss secured connections can be made.
 */
public interface GssEnabledResource extends Resource {

    /**
     * Tests whether the given credential successfully
     * authenticates to this service resource. Throws
     * exception if authentication fails. If no exception
     * is thrown, we assume the credential succesfully
     * authenticated a user to the service.
     * @param credential The credential
     */
    public void authenticates(GSSCredential credential)
            throws GSSException, ResourceException;
}

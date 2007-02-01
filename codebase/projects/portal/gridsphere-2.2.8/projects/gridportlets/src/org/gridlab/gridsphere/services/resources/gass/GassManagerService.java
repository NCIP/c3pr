package org.gridlab.gridsphere.services.resources.gass;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

import java.io.IOException;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GassManagerService.java,v 1.1.1.1 2007-02-01 20:41:04 kherm Exp $
 * <p>
 * Describes a service for creating gass resources.
 */

public interface GassManagerService extends PortletService {

    /**
     * Returns a gass resource for the given credential.
     * @param credential
     * @return
     * @throws IOException
     */
    public GassResource createGassResource(GSSCredential credential)
            throws GSSException, IOException;

    /**
     * Creates a gass location for the given file path
     * @param credential
     * @return
     * @throws GSSException
     * @throws IOException
     */
    public FileLocation createGassFileLocation(GSSCredential credential, String path)
            throws GSSException, IOException;

    /**
     * Creates a gass location for the given file
     * @param credential
     * @param prefix
     * @param suffix
     * @return
     * @throws GSSException
     * @throws IOException
     */
    public FileLocation createGassTempLocation(GSSCredential credential, String prefix, String suffix)
            throws GSSException, IOException;
}

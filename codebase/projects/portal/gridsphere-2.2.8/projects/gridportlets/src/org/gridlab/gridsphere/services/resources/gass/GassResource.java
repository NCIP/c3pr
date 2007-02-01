package org.gridlab.gridsphere.services.resources.gass;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.globus.io.gass.server.GassServer;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

import java.io.IOException;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GassResource.java,v 1.1.1.1 2007-02-01 20:41:04 kherm Exp $
 * <p>
 * Describes a gass resource.
 */

public interface GassResource {

    /**
     * Returns the credential that was used to create this
     * gass resource.
     * @return
     */
    public GSSCredential getCredential();

   /**
     * Returns the gass server associated with this resource.
     * @return
     * @throws java.io.IOException
     */
    public GassServer getGassServer()
            throws GSSException, IOException;

    /**
     * Returns the gass location for the given file path
     * @param path
     * @return
     * @throws java.io.IOException
     */
    public FileLocation createGassFileLocation(String path)
            throws GSSException, IOException;

    /**
     * Creates a temporary file and returns the gass location to it
     * @param prefix
     * @param suffix
     * @return
     * @throws GSSException
     * @throws IOException
     */
    public FileLocation createGassTempLocation(String prefix, String suffix)
            throws GSSException, IOException;
}

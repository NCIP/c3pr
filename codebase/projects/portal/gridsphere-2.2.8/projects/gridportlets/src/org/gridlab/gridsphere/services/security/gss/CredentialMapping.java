/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMapping.java,v 1.1.1.1 2007-02-01 20:41:30 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.common.Location;
import org.gridlab.gridsphere.services.common.Location;

/**
 * Maps a credential to a resource location
 */
public interface CredentialMapping {

    public String getCredentialDn();

    public String getResourceDn();
}

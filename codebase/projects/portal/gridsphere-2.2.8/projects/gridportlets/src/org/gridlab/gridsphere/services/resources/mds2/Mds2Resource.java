/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.mds2;

import org.gridlab.gridsphere.services.resources.ldap.LdapResource;
import org.gridlab.gridsphere.services.resources.ldap.LdapResource;
import org.gridlab.gridsphere.services.resources.mds2.Mds2ResourceType;

/**
 * Provides a base implementation for mds2 resources.
 */
public class Mds2Resource extends LdapResource {

    public static final String DEFAULT_PORT = "2135";

    public Mds2Resource() {
        super();
        setPort(DEFAULT_PORT);
        setResourceType(Mds2ResourceType.INSTANCE);
    }
}

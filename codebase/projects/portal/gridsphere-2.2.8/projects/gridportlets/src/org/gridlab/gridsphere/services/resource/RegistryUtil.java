/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: RegistryUtil.java,v 1.1.1.1 2007-02-01 20:40:51 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.services.util.ServiceUtil;
import org.gridlab.gridsphere.portlet.User;

/**
 * Utility class for util operations in the registry package
 */
public class RegistryUtil extends ServiceUtil {

    public static Resource getResource(User user, String oid) {
        return ((ResourceRegistryService)getPortletService(user, ResourceRegistryService.class)).getResource(oid);
    }
}

package org.gridlab.gridsphere.services.util;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ServiceUtil.java,v 1.1.1.1 2007-02-01 20:42:24 kherm Exp $
 * <p>
 * Provides the base for utility classes that simplify the use of
 * other packages in grid portlets.
 */

public class ServiceUtil {

    protected static PortletLog log = SportletLog.getInstance(ServiceUtil.class);
    protected static PortletServiceFactory portletServiceFactory = null;

    public static PortletService getPortletService(User user, Class serviceClass) {
        try {
            return getPortletServiceFactory().createPortletService(serviceClass, null, true);
        } catch (PortletServiceException e) {
            log.error("Unable to create portlet service " + serviceClass, e);
        }
        return null;
    }

    public static PortletServiceFactory getPortletServiceFactory() {
        if (portletServiceFactory == null) {
            portletServiceFactory = SportletServiceFactory.getInstance();
        }
        return portletServiceFactory;
    }
}

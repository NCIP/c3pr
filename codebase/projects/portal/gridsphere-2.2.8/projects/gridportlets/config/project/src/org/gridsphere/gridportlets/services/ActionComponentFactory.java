/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentFactory.java,v 1.1.1.1 2007-02-01 20:15:10 kherm Exp $
 */
package org.gridsphere.gridportlets.services;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.services.ui.impl.BaseActionComponentFactory;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Enumeration;
import java.lang.reflect.Constructor;

public class ActionComponentFactory_@_PROJECT_NAME_@ extends BaseActionComponentFactory {
             
    private static PortletLog log = SportletLog.getInstance(ActionComponentFactory_@_PROJECT_NAME_@.class);

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("ActionComponentFactory_@_PROJECT_NAME_@.init()");
        super.init(config);
    }
}

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GridPortletsComponentFactory.java,v 1.1.1.1 2007-02-01 20:42:03 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.*;

public class GridPortletsComponentFactory extends BaseActionComponentFactory {

    private static PortletLog log = SportletLog.getInstance(GridPortletsComponentFactory.class);
    protected Map actionComponentContexts = new HashMap();

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("Initializing Grid Portlets Action Component Factory");
        super.init(config);
    }
}

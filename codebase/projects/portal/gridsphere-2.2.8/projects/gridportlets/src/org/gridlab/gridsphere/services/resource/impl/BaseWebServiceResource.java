/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: BaseWebServiceResource.java,v 1.1.1.1 2007-02-01 20:41:01 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.services.common.Location;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.globus.util.Util;

public class BaseWebServiceResource extends BaseServiceResource implements WebServiceResource {

    private static PortletLog log = SportletLog.getInstance(BaseWebServiceResource.class);

    protected String serviceUrl = null;

    public BaseWebServiceResource() {
        super();
        setResourceType(WebServiceResourceType.INSTANCE);
        setProtocol(WebServiceLocation.DEFAULT_PROTOCOL);
        setPort(String.valueOf(WebServiceLocation.DEFAULT_PORT));
    }

    public Location getLocation() {
        String host = getHost();
        if (host.equalsIgnoreCase("localhost") || host.equals("127.0.0.1")) {
            host = Util.getLocalHostAddress();
        }
        int portNum = 0;
        try {
            portNum = Integer.valueOf(getPort()).intValue();
        } catch (Exception e) {
            log.error("Bad port num" + getPort());
        }
        return new WebServiceLocation(getProtocol(), getHost(), portNum, getServicePath());
    }

    public String getUrl() {
        return getLocation().getUrl();
    }
}

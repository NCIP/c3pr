/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: HttpgServiceLocation.java,v 1.1.1.1 2007-02-01 20:41:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.resource.WebServiceLocation;

import java.net.MalformedURLException;

/**
 * Describes the location of a web sevice resource. Service resource
 * url strings look like "httpg://<host>[:port][/<path>]", where:
 * host is either the host name or host address of the httpg service
 * resource; port is the port on which the httpg service resource is
 * running; path is the path for accessing the httpg service resource.
 */
public class HttpgServiceLocation extends WebServiceLocation {

    private static PortletLog log = SportletLog.getInstance(HttpgServiceLocation.class);

    public static final String DEFAULT_PROTOCOL = "httpg";

    public HttpgServiceLocation() {
        super();
    }
    
    public HttpgServiceLocation(String url)
            throws MalformedURLException {
        super(url);
    }

    public HttpgServiceLocation(String host, int port, String path) {
        super(DEFAULT_PROTOCOL, host, port, path);
    }

    public HttpgServiceLocation(HttpgServiceLocation location) {
        super(location);
    }

    /**
     * Returns the default protocol for service locations.
     * @return <code>"host"</code>
     */
    public String getDefaultProtocol() {
        return DEFAULT_PROTOCOL;
    }

    /**
     * Returns true since host is required for hardware locations.
     * @return <code>true</code>
     */
    public boolean acceptsProtocol(String protocol) {
        return (protocol != null && (protocol.equalsIgnoreCase("httpg")));
    }
}

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: WebServiceLocation.java,v 1.1.1.1 2007-02-01 20:40:58 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.net.MalformedURLException;

/**
 * Describes the location of a web sevice resource. Service resource
 * url strings look like "http[s|g]://<host>[:port][/<path>]", where:
 * host is either the host name or host address of the web service
 * resource; port is the port on which the web service resource is
 * running; path is the path for accessing the web service resource.
 */
public class WebServiceLocation extends ServiceLocation {

    private static PortletLog log = SportletLog.getInstance(WebServiceLocation.class);

    public static final String DEFAULT_PROTOCOL = "http";
    public static final int DEFAULT_PORT = 8443;

    public WebServiceLocation() {
        super();
    }

    public WebServiceLocation(String url)
            throws MalformedURLException {
        super(url);
    }

    public WebServiceLocation(String protocol, String host, int port, String path) {
        super(protocol, host, port, path);
    }

    public WebServiceLocation(WebServiceLocation location) {
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
        return (protocol != null
                && (protocol.equalsIgnoreCase("http")
                    || protocol.equalsIgnoreCase("https")
                    || protocol.equalsIgnoreCase("httpg")));
    }

    /**
     * Returns the default port for the given protocol.
     * @param protocol The protocol
     * @return The default port
     */
    public int getDefaultPort(String protocol) {
        return DEFAULT_PORT;
    }

    /**
     * Returns false since web service resources have default ports.
     * @return True if port required, false otherwise
     */
    public boolean requiresPort(String protocol) {
        return false;
    }
}

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ServiceLocation.java,v 1.1.1.1 2007-02-01 20:40:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.common.Location;

import java.net.MalformedURLException;

/**
 * Describes the location of a sevice resource. Service resources
 * url strings look like "{serv|<protocol>}://<host>[:port][/<path>]",
 * where: host is either the host name or host address of the service
 * resource; port is the port on which the service resource is
 * running; path is the path for accessing the service resource.
 */
public class ServiceLocation extends Location {

    private static PortletLog log = SportletLog.getInstance(ServiceLocation.class);

    public static final String DEFAULT_PROTOCOL = "service";

    public ServiceLocation() {
        super();
    }

    public ServiceLocation(String url) throws MalformedURLException {
        super(url);
    }

    public ServiceLocation(String host, int port, String path) {
        String url = buildUrl(DEFAULT_PROTOCOL, host, port, path, null, null);
        setUrlString(url);
    }


    public ServiceLocation(String protocol, String host, int port, String path) {
        String url = buildUrl(protocol, host, port, path, null, null);
        log.debug("ServiceLocation url string is " + url);
        setUrlString(url);
    }

    public ServiceLocation(ServiceLocation location) {
        super(location);
    }

    /**
     * Returns the default protocol for service locations.
     * @return <code>"host"</code>
     */
    public String getDefaultProtocol() {
        return DEFAULT_PROTOCOL;
    }

//    public boolean acceptsProtocol(String protocol) {
//        return (protocol != null && protocol.equalsIgnoreCase(DEFAULT_PROTOCOL));
//    }

    /**
     * Returns true since host is required for hardware locations.
     * @return <code>true</code>
     */
    public boolean requiresProtocol() {
        return true;
    }

    /**
     * Returns true.
     * @return <code>true</code>
     */
    public boolean requiresHost() {
        return true;
    }

    /**
     * Returns true if protocol equals "serv" or super says port is required.
     * @param protocol The protocol
     * @return True if port required, false otherwise
     */
    public boolean requiresPort(String protocol) {
        return (protocol.equalsIgnoreCase(DEFAULT_PROTOCOL) || super.requiresPort(protocol));
    }

    /**
     * Returns true since service locations don't contain queries.
     * @return <code>true</code>
     */
    public boolean ignoresQuery() {
        return true;
    }

    /**
     * Overrides super-class to make this method public.
     */
    public void setProtocol(String protocol) {
        super.setProtocol(protocol);
    }

    /**
     * Overrides super-class to make this method public.
     */
    public void setProtocol(String protocol, int port) {
        super.setProtocol(protocol, port);
    }

    /**
     * Overrides super-class to make this method public.
     */
    public void setHost(String host) {
        super.setHost(host);
    }

    /**
     * Overrides super-class to make this method public.
     */
    public void setPort(int port) {
        super.setPort(port);
    }

    /**
     * Overrides super-class to make this method public.
     */
    public void setPath(String path) {
        super.setPath(path);
    }
}

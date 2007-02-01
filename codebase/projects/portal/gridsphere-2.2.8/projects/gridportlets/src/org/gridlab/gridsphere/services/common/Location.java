/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Location.java,v 1.1.1.1 2007-02-01 20:39:50 kherm Exp $
 */
package org.gridlab.gridsphere.services.common;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.util.PortNumbers;

import org.globus.util.Util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.InetAddress;
import java.util.*;

/**
 * Describes the location of a resource object.  A resource location is essentially
 * a url, though resource location urls are handled somewhat differently
 * from the Java URL class. When in doubt, refer to the following syntax:<br>
 * <code><pre>
 *     &lt;protocol&gt;://[&lt;hostname&gt;[:&lt;port&gt;]]/&lt;path&gt;
 * </pre></code>
 * <p>
 * Note that, at minimum, there should be 3 '/' in the url string. Moreover,
 * the <code>&lt;path&gt;</code> may itself begin with a '/', resulting in 4 '/'
 * in the url, in which case the path is considered to be an <i>absolute path</i>.
 * If the path does not begin with a "/",  it will be considered to be a
 * <i>relative path</i>. For many resources, the <i>default context</i> is the root
 * directory for that resource, in which case a relative path will be considered
 * relative to the root directory. However, for many file access protocols, the
 * default context is the <i>home directory</i> for the user account corresponding
 * to the portal user accessing the file. In these cases, a relative path will be
 * considered relative to the home directory for a given user account.
 * <p>
 * Note, also, the hostname (and hence port) can be left blank. In this case,
 * the hostname is assumed to be the same as that of the host on which a particular
 * task is being performed. This is especially useful in the case of job submission,
 * where the choice of where to run job may be left to the job submission service.
 * <p>
 * Here are some useful examples:
 * <ul>
 *     <li><b>file://peyote.aei.mpg.de/stdout.txt</b> - Refers to the file object located
 *     at "stdout.txt" in a user's home directory on a host named "peyote.aei.mpg.de".</li>
 *     <li><b>file://peyote.aei.mpg.de//bin/ls</b> - Refers to the file object located at
 *     "/bin/ls" on a host named "peyote.aei.mpg.de".</li>
 *     <li><b>file:////bin/ls</b> - Refers to the file object located at "/bin/ls" on an
 *     unspecified host.</li>
 * </ul>
 * </p>
 */
public class Location {

    private static PortletLog log = SportletLog.getInstance(Location.class);

    public static final String DEFAULT_PROTOCOL = "resource";
    private static final String SUBSTITUTE_PROTOCOL = "http";

    protected URL url = null;
    protected String protocol = DEFAULT_PROTOCOL;
    protected Hashtable queryItems = new Hashtable(1);
    protected String urlString = "";

    public Location() {
        protocol = getDefaultProtocol();
    }

    public Location(String url)
            throws MalformedURLException {
        setUrl(url);
    }

    public Location(Location location) {
        url = location.url;
        protocol = location.protocol;
        urlString = location.urlString;
        queryItems.clear();
        queryItems.putAll(location.queryItems);
    }

    public String getUrl() {
        if (urlString == null) return "";
        return stripOffQueryString(urlString) + unloadQueryItems();
    }

    public void setUrl(String newUrlString) throws MalformedURLException {
        log.debug("Set url with " + newUrlString);
        int index = newUrlString.indexOf("://");
        String newProtocol = null;
        String host = "";
        if (index == -1) {
            if (requiresProtocol()) {
                throw new MalformedURLException("Protocol is required in the url string");
            } else {
                StringBuffer urlBuffer = new StringBuffer(SUBSTITUTE_PROTOCOL);
                urlBuffer.append(":///");
                urlBuffer.append(newUrlString);
                urlString = urlBuffer.toString();
                url = new URL(urlString);
                protocol = getDefaultProtocol();
            }
        } else {
            newProtocol = newUrlString.substring(0, index);
            if (acceptsProtocol(newProtocol)) {
                urlString = newUrlString;
                protocol = newProtocol;
                StringBuffer urlBuffer = new StringBuffer(SUBSTITUTE_PROTOCOL);
                urlBuffer.append(newUrlString.substring(index));
                url = new URL(urlBuffer.toString());
                host = url.getHost();
                if (requiresHost() && host.equals("")) {
                    throw new MalformedURLException("Host is required in url string");
                }
            } else {
                throw new MalformedURLException("Protocol " + newProtocol + " not accepted");
            }
        }
        log.debug("Url string is " + urlString);
        // Flag whether we need to rebuild url
        boolean modify = false;
        // Apply port rules
        int port = url.getPort();
        if (port == 0) {
            if (requiresPort(protocol)) {
                port = getDefaultPort(protocol);
                if (port == 0) {
                    throw new MalformedURLException("Port is required in url string for protocol " + protocol);
                }
            }
        } else if (ignoresPort()) {
            modify = true;
            port = 0;
        }
        // Apply path rules
        String path = url.getPath();
        if (path == null || path.equals("")) {
            if (requiresPath()) {
                throw new MalformedURLException("Path is required in url string");
            }
        } else if (ignoresPath()) {
            modify = true;
            path = "";
        }
        // Apply query rules
        String query = url.getQuery();
        if (query == null || query.equals("")) {
            if (requiresQuery()) {
                throw new MalformedURLException("Query is required in url string");
            }
        } else if (ignoresQuery()) {
            modify = true;
            query = "";
        } else {
            loadQueryItems(query);
            testQuery();
        }
        // Rebuild url if necessary
        if (modify) {
            newUrlString = buildUrl("", host, port, path, null, query);
            urlString = protocol + "://" + newUrlString;
            url = new URL(SUBSTITUTE_PROTOCOL + "://" + newUrlString);
        }
        log.debug("Url string is " + urlString);
    }

    /**
     * Returns the default protocol for this location.
     * @return The default protocol
     */
    public String getDefaultProtocol() {
        return DEFAULT_PROTOCOL;
    }

    /**
     * Returns whether or not the given protocol is acceptable by this location.
     * @param protocol The protocol
     * @return True if protocol is accepted, false otherwise
     */
    public boolean acceptsProtocol(String protocol) {
        return true;
    }

    /**
     * Returns whether or not a protocol is required in the url.
     * @return True if protocol required, false otherwise.
     */
    public boolean requiresProtocol() {
        return false;
    }

    /**
     * Returns true if a host is required in the url, false otherwisee.
     * @return True if host required, false otherwise
     */
    public boolean requiresHost() {
        return false;
    }

    /**
     * Returns the default port for the given protocol.
     * @param protocol The protocol
     * @return The default port
     */
    public int getDefaultPort(String protocol) {
        return PortNumbers.getDefaultPort(protocol);
    }

    /**
     * Returns whether or not a port for the given protocol.
     * @param protocol The protocol
     * @return True if port required, false otherwise
     */
    public boolean requiresPort(String protocol) {
        return (PortNumbers.getDefaultPort(protocol) == 0);
    }

    /**
     * Returns whether or not this location should remove the port in the url string if it is specified.
     * @return True if port is ignored, false otherwise
     */
    public boolean ignoresPort() {
        return false;
    }

    /**
     * Returns whether or not a path is required in the url.
     * @return True if path required, false otherwise
     */
    public boolean requiresPath() {
        return false;
    }

    /**
     * Returns whether or not this location should remove the path in the url string if it is specified.
     * @return True if path is ignored, false otherwise
     */
    public boolean ignoresPath() {
        return false;
    }

    /**
     * Returns whether or not a query is required in the url.
     * @return True if query required, false otherwise
     */
    public boolean requiresQuery() {
        return false;
    }

    /**
     * Returns whether or not this location should remove the query in the url string if it is specified.
     * @return True if query is ignored, false otherwise
     */
    public boolean ignoresQuery() {
        return false;
    }

    /**
     * Determines whether or not the query is accepted.
     * If the query is not accepted, an exception with an appropriate message is thrown.
     * @throws MalformedURLException If the given query is not accepted
     */
    public void testQuery() throws MalformedURLException {
    }

    public String getProtocol() {
        if (protocol == null) {
            return "";
        }
        return protocol;
    }

    protected void setProtocol(String protocol) {
        String url = buildUrl(protocol,
                     getHost(),
                     getPort(),
                     getPath(),
                     null,
                     getQuery());
        setUrlString(url);
    }

    protected void setProtocol(String protocol, int port) {
        String url = buildUrl(protocol,
                     getHost(),
                     port,
                     getPath(),
                     null,
                     getQuery());
        setUrlString(url);
    }

    public String getHost() {
        if (url == null) {
            return "";
        }
        return url.getHost();
    }

    protected void setHost(String host) {
        String url = buildUrl(getProtocol(),
                     host,
                     getPort(),
                     getPath(),
                     null,
                     getQuery());
        setUrlString(url);
    }

    public int getPort() {
        if (url == null) {
            return 0;
        }
        return url.getPort();
    }

    protected void setPort(int port) {
        String url = buildUrl(getProtocol(),
                     getHost(),
                     port,
                     getPath(),
                     null,
                     getQuery());
        setUrlString(url);
    }

    public String getPath() {
        String path = "";
        if (url != null) {
            path = url.getPath().substring(1);
        }
        return path;
    }

    protected void setPath(String path) {
        String url = buildUrl(getProtocol(),
                     getHost(),
                     getPort(),
                     path,
                     null,
                     getQuery());
        setUrlString(url);
    }

    public String getUrlWithoutQuery() {
        if (url == null) {
            return "";
        }
        return stripOffQueryString(urlString);
    }

    protected void removeQueryString() {
       try {
           setUrl(stripOffQueryString(urlString));
       } catch (MalformedURLException e) {
           log.error("Error removing query string from url ", e);
       }
    }

    /**
     * Gets rid of everything in the url after "?".
     * @param in The input url
     * @return The resulting url
     */
    protected static String stripOffQueryString( String in ) {
        String out = in;
        int foundAt = in.indexOf("?");
        if (foundAt >= 0){
            out = in.substring(0, foundAt);
        }
        return out;
    }

    public String getQuery() {
        String query = "";
        // getQuery can return null so make sure not to.
        if (url != null && url.getQuery() != null){
            query = url.getQuery();
        }
        return query;
    }

    public String getQueryItem(String name) {
        return (String)queryItems.get(name);
    }

    protected void setQueryItem(String name, String value) {
        if (name == null) return;
        if (value == null) {
            queryItems.remove(name);
        } else {
            queryItems.put(name, value);
        }
    }

    /**
     * Poor (wo)man's way to (re)build a url
     * @param protocol The protocol
     * @param host The hostname
     * @param port The port
     * @param path The file path
     * @param name The file name
     * @param query The query string
     * @return The resulting url
     */
    protected String buildUrl(String protocol,
                                     String host,
                                     int port,
                                     String path,
                                     String name,
                                     String query) {
        log.debug("Build url");
        StringBuffer newUrl = new StringBuffer();
        // Protocol
        if (protocol !=null && !protocol.equals("")) {
            newUrl.append(protocol);
            newUrl.append("://");
        }
        // Host
        if (host !=null && !host.equals("")){
            newUrl.append(host);
            if (port > 0) {
                newUrl.append(':');
                newUrl.append(port);
            }
        }
        // Path and name
        if (path != null) {
            if(!path.startsWith("/")) {
                newUrl.append('/');
            }
            newUrl.append(path);
            if (name != null && !name.equals("")) {
                if (!path.equals("") && !path.endsWith("/")) {
                    newUrl.append('/');
                }
                newUrl.append(name);
            }
        }
        // Query
        if (query != null && !query.equals("")) {
            if(!query.startsWith("?")) {
                newUrl.append('?');
            }
            newUrl.append(query);
        }
        return newUrl.toString();
    }

    public boolean isLocalHost() {
        if (url == null) return false;
        String host = getHost();
        return isLocalHost(host);
    }

    public static boolean isLocalHost(String host) {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            if (host.equalsIgnoreCase(localHost.getHostName())   ||
                host.equals(localHost.getHostAddress()) ||
                host.equalsIgnoreCase("localhost") ||
                host.equals("127.0.0.1")) {
                return true;
            }
            String cogAddress = Util.getLocalHostAddress();
            return (host.equals(cogAddress));
        } catch (Exception e) {
            log.error("Unable to get local host name", e);
            return false;
        }
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass().equals(Location.class)) {
            Location fileLocation = (Location)o;
            return (urlString != null &&
                    fileLocation.urlString != null &&
                    urlString.equals(fileLocation.urlString));
        }
        return false;
    }

    public int hashCode() {
        if (urlString == null) return 0;
        return urlString.hashCode();
    }

    public Object clone() {
        Location newLocation = new Location();
        newLocation.protocol = protocol;
        newLocation.url = url;
        newLocation.urlString = urlString;
        newLocation.queryItems.putAll(queryItems);
        return newLocation;
    }

    protected void loadQueryItems(URL url) {
        queryItems.clear();
        if (url != null){
            String query = url.getQuery();
            if (query != null) {
                loadQueryItems(query);
            }
        }
    }

    protected void loadQueryItems(String query) {
        queryItems.clear();
        // getQuery can return null so make sure not to.
        if (!query.equals("")){
            //log.debug("Loading query items from " + query);
            StringTokenizer tokenizer = new StringTokenizer(query, "&");
            while (tokenizer.hasMoreTokens()) {
                String queryItem = tokenizer.nextToken();
                //log.debug("Next query item is " + queryItem);
                int index = queryItem.indexOf("=");
                String queryName = queryItem.substring(0, index);
                String queryValue = queryItem.substring(index+1);
                //log.debug("Adding query item " + queryName + "=" + queryValue);
                queryItems.put(queryName, queryValue);
            }
        }
    }

    protected String unloadQueryItems() {
        StringBuffer queryBuffer = new StringBuffer();
        Enumeration queryNames = queryItems.keys();
        while (queryNames.hasMoreElements()) {
            String queryName = (String)queryNames.nextElement();
            String queryValue = (String)queryItems.get(queryName);
            if (queryValue == null) continue;
            //log.debug("Unloading query item " + queryName + "=" + queryValue);
            queryBuffer.append('&');
            queryBuffer.append(queryName);
            queryBuffer.append('=');
            queryBuffer.append(queryValue);
        }
        if (queryBuffer.length() > 0) {
            queryBuffer.replace(0,1,"?");
        }
        String query = queryBuffer.toString();

        //log.debug("Unloaded query " + query);

        return query;
    }

    /**
     * Sets the url string represented in this object.
     * @param urlString The url string
     */
    protected void setUrlString(String urlString) {
        if (urlString == null) {
            log.warn("Calling set url string on null value");
        } else {
            try {
                setUrl(urlString);
            } catch (MalformedURLException e) {
                log.warn("Unable to set url string" + e.getMessage());
            }
        }
    }

    public static String getParentPath(String path) {
        log.debug("getParentPath " + path);
        if (path.equals("")) {
            return path;
        }
        int index = path.lastIndexOf("/");
        if (index > -1) {
            if (index == 0) {
                path = "/";
            } else {
                int length = path.length();
                if (index == length-1) {
                    log.debug("Path ends with /, removing...");
                    path = path.substring(0, length);
                    return getParentPath(path);
                } else {
                    path = path.substring(0, index);
                }
            }
        }
        log.debug("Parent path = " + path);
        return path;
    }
}

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileLocation.java,v 1.1.1.1 2007-02-01 20:40:02 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.common.Location;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Describes the location of a file object. A file location is essentially
 * a url, though file location urls are handled somewhat differently
 * than by the Java URL class. When in doubt, refer to the following syntax:<br>
 * <code><pre>
 *     &lt;protocol&gt;://[&lt;hostname&gt;[:&lt;port&gt;]]/&lt;path&gt;
 * </pre></code>
 * <p>
 * Note that, at minimum, there should be 3 "/" in the url. Moreover,
 * the <code>&lt;path&gt;</code> may itself begin with a "/", in which
 * case the path is considered to be an <i>absolute path</i>. If the path
 * does not begin with a "/",  it will be considered to be a <i>relative path</i>,
 * were for many file access protocols, relative paths are considered to be
 * <i>relative to a user's home directory</i>. That is, file objects are generally
 * accessed by a particular user with an account on the resource specified in the url.
 * Exceptions to this rule include file objects made accessible by web servers,
 * where relative paths may be relative to the root path of the web server or web
 * application on which the file object resides.
 * <p>
 * Note, also, the hostname (and hence port) can be left blank. In this case,
 * the hostname is assumed to be the same as that of the host on which a particular task
 * is being performed. This is especially useful in the case of job submission,
 * where the choice of where to run job is left to the job submission service.
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
 * <p>
 * The protocols recognized by file location include:
 * <ul>
 *     <li><b>file</b> - Default file access protocol</li>
 *     <li><b>gass</b> - Global access to secondary storage</li>
 *     <li><b>https</b> - Secure http</li>
 *     <li><b>gridftp</b> - Grid ftp</li>
 *     <li><b>gsiftp</b> - Gsi ftp</li>
 *     <li><b>gridssh</b> - Grid ssh</li>
 *     <li><b>gsissh</b> - Gsi ssh</li>
 *     <li><b>gridscp</b> - Grid scp</li>
 *     <li><b>gsiscp</b> - Gsi scp</li>
 * </ul>
 * </p>
 */
public class FileLocation extends Location {

    public static final String QUERY_ITEM_FILE_TYPE = "filetype";
    public static final String QUERY_ITEM_SOFT_LINK_PATH = "softlinkpath";
    public static final String QUERY_ITEM_FILE_SIZE = "filesize";
    public static final String QUERY_ITEM_FILE_DATE_CREATED = "datecreated";
    public static final String QUERY_ITEM_FILE_DATE_LAST_MODIFIED = "datemodified";

    public static final String FILE_PROTOCOL = "file";

    public static final Hashtable PROTOCOLS = new Hashtable(10);

    static {
        PROTOCOLS.put("file", "File");
        PROTOCOLS.put("lfn", "Logical File");
        PROTOCOLS.put("gass", "GASS");
        PROTOCOLS.put("gsiftp", "GSI File Transfer Protocol");
        PROTOCOLS.put("gsiscp", "GSI File Transfer Protocol");
        PROTOCOLS.put("gridftp", "Grid File Transfer Protocol");
        PROTOCOLS.put("gsiscp", "GSI Secure Copy");
        PROTOCOLS.put("gridscp", "Grid Secure Copy");
        PROTOCOLS.put("gram", "Grid Resource Allocation Manager");
        PROTOCOLS.put("https", "Secure HTTP");
        PROTOCOLS.put("secdir", "Secure Directory");
    }

    protected static PortletLog log = SportletLog.getInstance(FileLocation.class);

    /**
     * Default constructor.
     */
    public FileLocation() {

    }

    /**
     * Constructs a file location from the given url.
     * @param url The url to the file
     * @throws MalformedURLException If the url is invalid
     */
    public FileLocation(String url)
            throws MalformedURLException {
        setUrl(url);
    }

    /**
     * Constructs a file location for rom the given url and file type (FILE, DIRECTORY, etc).
     * @param url The url to the file
     * @param type The file type
     * @throws MalformedURLException If the url is invalid.
     */
    public FileLocation(String url, FileType type)
            throws MalformedURLException {
        setUrl(url);
        setFileType(type);
    }

    /**
     * Construct a file location using the values in the given file location
     * @param location The file location
     */
    public FileLocation(FileLocation location) {
        url = location.url;
        protocol = location.protocol;
        urlString = location.urlString;
        queryItems.clear();
        queryItems.putAll(location.queryItems);
    }

    public FileLocation(String protocol, String host, int port, String path) {
        String url = buildUrl(protocol, host, port, path, null, null);
        log.debug("Url string is " + url);
        setUrlString(url);
    }

    /**
     * Sets the url of this file location.
     * @param newUrlString The new url
     * @throws MalformedURLException If the url is invalid.
     */
    public void setUrl(String newUrlString) throws MalformedURLException {
        int index = newUrlString.indexOf("://");
        String newProtocol = null;
        if (index == -1) {
            // If no protocol provided... insert "file://"
            StringBuffer urlBuffer = new StringBuffer(FILE_PROTOCOL);
            urlBuffer.append(":///");
            urlBuffer.append(newUrlString);
            urlString = urlBuffer.toString();
            url = new URL(urlString);
            protocol = FILE_PROTOCOL;
            loadQueryItems(url);
        } else {
            newProtocol = newUrlString.substring(0, index);
            if (PROTOCOLS.containsKey(newProtocol)) {
                // If protocol is one of our protocols,
                // use "file" for URL protocol and keep
                // original protocol for use later...
                urlString = newUrlString;
                protocol = newProtocol;
                StringBuffer urlBuffer = new StringBuffer(FILE_PROTOCOL);
                urlBuffer.append(newUrlString.substring(index));
                url = new URL(urlBuffer.toString());
                loadQueryItems(url);
            } else {
                // Otherwise, let URL bug out if it wants to
                urlString = newUrlString;
                url = new URL(urlString);
                protocol = url.getProtocol();
                loadQueryItems(url);
            }
        }
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
    public void setQueryItem(String name, String value) {
        super.setQueryItem(name, value);
    }

    /**
     * Returns the file path in the url of this file location.
     * @return The file path
     */
    public String getFilePath() {
        return getPath();
    }

    /**
     * Sets the file path in the url of this file location.
     * @param path The file path
     */
    public void setFilePath(String path) {
        setPath(path);
    }

    /**
     * Overrides super-class to make this method public.
     */
    public void setUrlString(String urlString) {
        super.setUrlString(urlString);
    }

    /**
     * Returns the file name in the url of this file location.
     * @return The file name
     */
    public String getFileName() {
        String filePath = getFilePath();
        int index = filePath.lastIndexOf("/");
        if (index > -1) {
            if (index == filePath.length() - 1) {
                return "";
            } else {
                return stripOffQueryString( filePath.substring(index + 1) );
            }
        } else {
            return filePath;
        }
    }

    /**
     * Sets the file name in the url of this file location.
     * @param name The file name
     */
    public void setFileName(String name) {
        String url = buildUrl(getProtocol(),
                     getHost(),
                     getPort(),
                     getFilePath(),
                     name,
                     getQuery());
        setUrlString(url);
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
        newUrl.append(protocol);
        newUrl.append("://");
        if (!host.equals("")){
            newUrl.append(host);
            if (port > 0) {
                newUrl.append(':');
                newUrl.append(port);
            }
        }
        newUrl.append('/');
        log.debug("Appending path and name " + path + " to " + newUrl.toString());
        if (path != null) {
            log.debug("Path is not null " + path);
            newUrl.append(path);
        }
        if (name != null && !name.equals("")) {
            log.debug("Name is not null or empty " + name);
            if (path != null && !path.equals("") && !path.endsWith("/")) {
                newUrl.append('/');
            }
            newUrl.append(name);
        }
        if (query != null && !query.equals("")) {
            if(!query.startsWith("?")) {
                newUrl.append('?');
            }
            newUrl.append(query);
        }
        return newUrl.toString();
    }

    public boolean getIsFile() {
        return getFileType().equals(FileType.FILE);
    }

    public boolean getIsDirectory() {
        return getFileType().equals(FileType.DIRECTORY);
    }

    public boolean getIsSoftLink() {
        return getFileType().equals(FileType.SOFT_LINK);
    }

    public boolean getIsDevice() {
        return getFileType().equals(FileType.DEVICE);
    }

    public FileType getFileType() {
        String fileType = (String)queryItems.get(QUERY_ITEM_FILE_TYPE);
        if (fileType == null) return FileType.FILE;
        return FileType.toFileType(fileType);
    }

    public void setFileType(FileType type) {
        if (type == null) type = FileType.FILE;
        queryItems.put(QUERY_ITEM_FILE_TYPE, type.getName());
    }

    public String getSoftLinkPath() {
        if (!getFileType().equals(FileType.SOFT_LINK)) return null;
        String softLinkPath = (String)queryItems.get(QUERY_ITEM_SOFT_LINK_PATH);
        return softLinkPath;
    }

    public void setSoftLinkPath(String path) {
        queryItems.put(QUERY_ITEM_SOFT_LINK_PATH, path);
    }

    public Long getFileSize() {
        String value = getQueryItem(QUERY_ITEM_FILE_SIZE);
        if (value == null) return null;
        return new Long(value);
    }

    public void setFileSize(Long value) {
        if (value == null) return;
        setQueryItem(QUERY_ITEM_FILE_SIZE, value.toString());
    }

    public Long getDateCreated() {
        String value = getQueryItem(QUERY_ITEM_FILE_DATE_CREATED);
        if (value == null) return null;
        return new Long(value);
    }

    public void setDateCreated(Long value) {
        if (value == null) return;
        setQueryItem(QUERY_ITEM_FILE_DATE_CREATED, value.toString());
    }

    public Long getDateLastModified() {
        String value = getQueryItem(QUERY_ITEM_FILE_DATE_LAST_MODIFIED);
        if (value == null) return null;
        return new Long(value);
    }

    public void setDateLastModified(Long value) {
        if (value == null) return;
        setQueryItem(QUERY_ITEM_FILE_DATE_LAST_MODIFIED, value.toString());
    }


    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass().equals(FileLocation.class)) {
            FileLocation fileLocation = (FileLocation)o;
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
        FileLocation newLocation = new FileLocation();
        newLocation.protocol = protocol;
        newLocation.url = url;
        newLocation.urlString = urlString;
        newLocation.queryItems.putAll(queryItems);
        return newLocation;
    }
}

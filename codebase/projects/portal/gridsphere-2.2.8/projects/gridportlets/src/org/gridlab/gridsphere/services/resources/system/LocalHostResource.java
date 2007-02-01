/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.system;

import org.gridlab.gridsphere.services.resource.impl.BaseServiceResource;
import org.gridlab.gridsphere.services.file.FileResource;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileException;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.common.Location;
import org.gridlab.gridsphere.services.util.ServiceUtil;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/**
 * Implements a gdms logical file resource.
 */
public class LocalHostResource extends BaseServiceResource implements FileResource {

    private static PortletLog log = SportletLog.getInstance(LocalHostResource.class);

    public static final String DEFAULT_PORT = "8080";
    public static final String DEFAULT_PROTOCOL = "secdir";

    public LocalHostResource() {
        super();
        setPort(DEFAULT_PORT);
        setProtocol(DEFAULT_PROTOCOL);
        setResourceType(LocalHostResourceType.INSTANCE);
    }

    public FileBrowser createFileBrowser(User user) throws FileException {
        LocalHostBrowser fileBrowser = new LocalHostBrowser();
        LocalHostBrowserService fileBrowserService = (LocalHostBrowserService)
                ServiceUtil.getPortletService(user, LocalHostBrowserService.class);
        fileBrowser.init(fileBrowserService, user, this);
        return fileBrowser;
    }

    public Location getLocation() {
        int portNum = 0;
        String port = getPort();
        log.debug("Creating a location for " + getDn() +  " " + getProtocol() + "://" + getHost() + ":" + port);
        if (port != null && !port.equals("")) {
            try {
                portNum = Integer.parseInt(port);
            } catch (Exception e) {
                log.warn("Invalid port number " + port);
            }
        }
        return new FileLocation(getProtocol(), getHost(), portNum, null);
    }
}

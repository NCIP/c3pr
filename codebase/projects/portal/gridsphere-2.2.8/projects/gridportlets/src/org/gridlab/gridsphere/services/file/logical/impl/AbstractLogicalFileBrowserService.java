/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.logical.impl;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.file.logical.*;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowser;
import org.gridlab.gridsphere.services.file.tasks.impl.AbstractFileBrowserService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;

/**
 * Base abstract implementation for a logical file browser service.
 */
public abstract class AbstractLogicalFileBrowserService
        extends AbstractFileBrowserService
        implements LogicalFileBrowserService {

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        super.init(config);
    }


    public FileBrowser createFileBrowser(User user, String hostname)
            throws FileException {
        FileResource resource = getFileResource(hostname);
        if (resource == null) {
            throw new FileException("No logical file resource exists on " + hostname);
        }
        BaseLogicalFileBrowser fileBrowser = createLogicalFileBrowser();
        fileBrowser.init(this, user, resource);
        return fileBrowser;
    }

    public FileBrowser createFileBrowser(User user, FileLocation location)
            throws FileException {
        String hostname = location.getHost();
        FileResource resource = getFileResource(hostname);
        if (resource == null) {
            throw new FileException("No logical file resource exists");
        }
        BaseLogicalFileBrowser fileBrowser = createLogicalFileBrowser();
        fileBrowser.init(this, user, resource);
        return fileBrowser;
    }

    public BaseFileBrowser createFileBrowser() {
        return createLogicalFileBrowser();
    }


    public LogicalFileBrowser createLogicalFileBrowser(User user)
            throws FileException {
        LogicalFileResource resource = getLogicalFileResource();
        if (resource == null) {
            throw new FileException("No logical file resource exists");
        }
        BaseLogicalFileBrowser fileBrowser = createLogicalFileBrowser();
        fileBrowser.init(this, user, resource);
        return fileBrowser;
    }

    public LogicalFileBrowser createLogicalFileBrowser(User user, FileLocation location)
            throws FileException {
        String hostname = location.getHost();
        FileResource resource = getFileResource(hostname);
        if (resource == null) {
            throw new FileException("No logical file resource exists on " + hostname);
        }
        BaseLogicalFileBrowser fileBrowser = createLogicalFileBrowser();
        fileBrowser.init(this, user, resource);
        return fileBrowser;
    }


    protected abstract BaseLogicalFileBrowser createLogicalFileBrowser();
}

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.file.sets.FilePatternSet;
import org.gridlab.gridsphere.services.file.sets.*;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.services.file.impl.AbstractFileTaskService;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.resource.HardwareResource;
import org.gridlab.gridsphere.services.task.TaskTypeRegistry;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Base abstract implementation for a file browser service.
 */
public abstract class AbstractFileBrowserService
        extends AbstractFileTaskService
        implements FileBrowserService {

    protected static PortletLog log = SportletLog.getInstance(AbstractFileBrowserService.class);
    protected ResourceRegistryService resourceRegistryService = null;
    private ResourceType fileResourceType = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        super.init(config);
        try {
            resourceRegistryService = (ResourceRegistryService)
                factory.createPortletService(ResourceRegistryService.class,
                        config.getServletContext(), true);
        } catch (PortletServiceNotFoundException e) {
            log.error("Unable to get instance of resource registry service", e);
            throw new PortletServiceUnavailableException(e.getMessage());
        }
    }

    public FilePatternSet createRegExFileSet() {
        return new FilePatternSet();
    }

    public FileLocationSet createFileLocationSet() {
        return new FileLocationSet();
    }

    public FileBrowser createFileBrowser(User user, String hostname)
            throws FileException {
        FileResource resource = getFileResource(hostname);
        if (resource == null) {
            throw new FileException("No file resource exists on " + hostname);
        }
        BaseFileBrowser fileBrowser = createFileBrowser();
        fileBrowser.init(this, user, resource);
        return fileBrowser;
    }

    public FileBrowser createFileBrowser(User user, FileLocation location)
            throws FileException {
        String hostname = location.getHost();
        FileResource resource = getFileResource(location);
        if (resource == null) {
            throw new FileException("No file resource exists on " + hostname);
        }
        BaseFileBrowser fileBrowser = createFileBrowser();
        fileBrowser.init(this, user, resource);
        return fileBrowser;
    }

    public abstract BaseFileBrowser createFileBrowser();


    protected FileResource getFileResource(FileSet fileSet)
            throws FileTaskException {
        FileLocation fileLoc = null;
        if (fileSet instanceof FilePatternSet) {
            FilePatternSet filePatternSet = (FilePatternSet)fileSet;
            fileLoc = filePatternSet.getFileLocation();
            if (fileLoc == null) {
                throw new FileTaskException("File location must be provided in regular expresssion file sets");
            }
            return getFileResource(fileLoc);
        } else if (fileSet instanceof FileLocationSet) {
            FileLocationSet fileLocSet = (FileLocationSet)fileSet;
            List fileLocList = fileLocSet.getFileLocations();
            if (fileLocList.size() > 0) {
                fileLoc = (FileLocation)fileLocList.get(0);
            }
        } else {
            throw new FileTaskException("File set not supported " + fileSet.getClass().getName());
        }
        return getFileResource(fileLoc);
    }

    public abstract boolean supportsFileLocation(FileLocation fileLocation);

    public FileResource getFileResource(FileLocation fileLocation) {
        String host = fileLocation.getHost();
        if (host == null || host.equals("")) {
            log.error("No host provided in file location " + fileLocation.getUrl());
            return null;
        }
        HardwareResource hostResource = resourceRegistryService.getHardwareResourceByHost(host);
        if (hostResource != null) {
            return (FileResource)hostResource.getChildResource(fileResourceType);
        }
        log.error("No hardware resource found for host " + host);
        return null;
    }

    public ResourceType getFileResourceType() {
        return fileResourceType;
    }

    public void setFileResourceType(ResourceType fileResourceType) {
        this.fileResourceType = fileResourceType;
    }
}

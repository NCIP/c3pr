/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileBrowserServiceImpl.java,v 1.1.1.1 2007-02-01 20:40:39 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.file.impl.AbstractFileTaskService;
import org.gridlab.gridsphere.services.file.tasks.*;
import org.gridlab.gridsphere.services.resources.system.LocalHostBrowserService;
import org.gridlab.gridsphere.services.resources.system.LocalHostResourceType;
import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;

import java.util.*;

/**
 * Implements a file browser service that delegates tasks to other file browser services.
 */
public class FileBrowserServiceImpl
        extends AbstractFileTaskService
        implements FileBrowserService {

    protected static PortletLog log = SportletLog.getInstance(FileBrowserServiceImpl.class);
    protected List fileResourceTypes = new Vector(2);
    protected static LocalHostBrowserService localHostBrowserService = null;
    protected static SortedMap fileBrowserServices = new TreeMap();

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        super.init(config);
        fileResourceTypes.add(FileResourceType.INSTANCE.getID());
        try {
            localHostBrowserService
                    = (LocalHostBrowserService)
                        factory.createPortletService(LocalHostBrowserService.class, null, true);
        } catch (PortletServiceNotFoundException e) {
            log.error("Unable to get local host browser service", e);
            throw new PortletServiceUnavailableException("Unable to get local host browser service");
        }
        addFileBrowserService(LocalHostResourceType.INSTANCE, localHostBrowserService);
        Enumeration paramNames = config.getInitParameterNames();
        while (paramNames.hasMoreElements()) {
            String resourceType = (String)paramNames.nextElement();
            String serviceName = config.getInitParameter(resourceType);
            Class serviceClass = null;
            try {
                serviceClass = Class.forName(serviceName);
            } catch (ClassNotFoundException e) {
                log.error("Service class not found: " + serviceName);
            } finally {
                FileBrowserService service = null;
                try {
                    service  = (FileBrowserService)
                            factory.createPortletService(serviceClass, config.getServletContext(), true);
                } catch (PortletServiceNotFoundException e) {
                    log.error("Service class not found: " + serviceName);
                } finally {
                    log.info("Adding file browser service for resource type " + resourceType);
                    addFileBrowserService(resourceType, service);
                }
            }
        }

    }

    public void destroy() {
    }

    public List getFileTaskTypes() {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            log.warn("No file browser service exists");
            return new Vector(0);
        }
        return service.getFileTaskTypes();
    }

    public List getFileResources() {
        log.debug("Returning all file resources");

        List fileResources = new Vector(10);

        log.debug("Adding file resources to list from " + fileBrowserServices.size() + " file browser services");

        Iterator fbs = fileBrowserServices.values().iterator();
        while (fbs.hasNext()) {
            FileBrowserService fileBrowserService = (FileBrowserService)fbs.next();
            List nextList = fileBrowserService.getFileResources();
            log.debug("Adding " + nextList.size() + " file resources to list from " + fileBrowserService.getClass().getName());
            fileResources.addAll(nextList);
        }

        return fileResources;
    }

    public List getFileResources(User user) {
        log.debug("Returning file resources for " + user.getUserID());
        List fileResources = new Vector(10);

        log.debug("Adding file resources to list from " + fileBrowserServices.size() + " file browser services");

        Iterator fbs = fileBrowserServices.values().iterator();
        while (fbs.hasNext()) {
            FileBrowserService fileBrowserService = (FileBrowserService)fbs.next();
            List nextList = fileBrowserService.getFileResources(user);
            log.debug("Adding " + nextList.size() + " file resources to list from " + fileBrowserService.getClass().getName());
            fileResources.addAll(nextList);
        }

        return fileResources;
    }

    public FileResource getFileResource(String hostname) {

        if (FileLocation.isLocalHost(hostname)) {
            return localHostBrowserService.getFileResource(hostname);
        }

        Iterator fbs = fileBrowserServices.values().iterator();
        while (fbs.hasNext()) {
            FileBrowserService fileBrowserService = (FileBrowserService)fbs.next();
            FileResource fileResource = fileBrowserService.getFileResource(hostname);
            if (fileResource != null) {
                return fileResource;
            }
        }

        return null;
    }

    public boolean supportsFileLocation(FileLocation fileLocation) {
        Iterator fbs = fileBrowserServices.values().iterator();
        while (fbs.hasNext()) {
            FileBrowserService fileBrowserService = (FileBrowserService)fbs.next();
            if (fileBrowserService.supportsFileLocation(fileLocation)) {
                return true;
            }
        }
        return false;
    }

    public FileResource getFileResource(FileLocation fileLocation) {
        Iterator fbs = fileBrowserServices.values().iterator();
        while (fbs.hasNext()) {
            FileBrowserService fileBrowserService = (FileBrowserService)fbs.next();
            if (fileBrowserService.supportsFileLocation(fileLocation)) {
                return fileBrowserService.getFileResource(fileLocation);
            }
        }
        return null;
    }

    private FileBrowserService getFileBrowserService(ResourceType type) {
        FileBrowserService service = null;
        if (type.equalsResourceType(FileResourceType.INSTANCE)) {
            if (fileResourceTypes.size() > 1) {
                String stype = (String)fileResourceTypes.get(1);
                service = (FileBrowserService)fileBrowserServices.get(stype);
            }
        } else {
            service = (FileBrowserService)fileBrowserServices.get(type.getID());
        }
        return service;
    }

    public void addFileBrowserService(ResourceType type, FileBrowserService service) {
        log.info("Adding file browser service for resource type " + type.getID());
        fileResourceTypes.add(type.getID());
        fileBrowserServices.put(type.getID(), service);
    }

    public void addFileBrowserService(String type, FileBrowserService service) {
        log.info("Adding file browser service for resource type " + type);
        fileResourceTypes.add(type);
        fileBrowserServices.put(type, service);
    }

    public FileBrowser createFileBrowser(User user, String hostname)
            throws FileException {
        if (FileLocation.isLocalHost(hostname)) {
            return localHostBrowserService.createFileBrowser(user, hostname);
        }

        Iterator fbs = fileBrowserServices.values().iterator();
        while (fbs.hasNext()) {
            FileBrowserService fileBrowserService = (FileBrowserService)fbs.next();
            FileResource fileResource = fileBrowserService.getFileResource(hostname);
            if (fileResource != null) {
                return fileBrowserService.createFileBrowser(user, hostname);
            }
        }

        throw new FileException("No file browser supported for " + hostname);
    }

    public FileBrowser createFileBrowser(User user, FileLocation location)
            throws FileException {

        Iterator fbs = fileBrowserServices.values().iterator();
        while (fbs.hasNext()) {
            FileBrowserService fileBrowserService = (FileBrowserService)fbs.next();
            if (fileBrowserService.supportsFileLocation(location)) {
                try {
                    return fileBrowserService.createFileBrowser(user, location);
                } catch (FileException e) {
                    continue;
                }
            }
        }
        throw new FileException("No file browser supported for " + location.getUrlWithoutQuery());
    }

    public FileListingSpec createFileListingSpec()
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileListingSpec();
    }

    public FileListing submitFileListing(FileListingSpec spec)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileListing(spec);
    }

    public FileNameChangeSpec createFileNameChangeSpec()
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileNameChangeSpec();
    }

    public FileNameChange submitFileNameChange(FileNameChangeSpec spec)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileNameChange(spec);
    }

    public FileMakeDirSpec createFileMakeDirSpec()
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileMakeDirSpec();
    }

    public FileMakeDir submitFileMakeDir(FileMakeDirSpec spec)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileMakeDir(spec);
    }

    public FileDeletionSpec createFileDeletionSpec()
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileDeletionSpec();
    }

    public FileDeletion submitFileDeletion(FileDeletionSpec spec)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileDeletion(spec);
    }

    public FileCopySpec createFileCopySpec()
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileCopySpec();
    }

    public FileCopy submitFileCopy(FileCopySpec spec)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileCopy(spec);
    }

    public FileMoveSpec createFileMoveSpec()
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileMoveSpec();
    }

    public FileMove submitFileMove(FileMoveSpec spec)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileMove(spec);
    }

    public FileUploadSpec createFileUploadSpec()
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileUploadSpec();
    }

    public FileUpload submitFileUpload(FileUploadSpec spec)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileUpload(spec);
    }

    public FileDownloadSpec createFileDownloadSpec()
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileDownloadSpec();
    }

    public FileDownload submitFileDownload(FileDownloadSpec spec)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileDownload(spec);
    }

    public FileTaskSpec createFileTaskSpec(FileTaskType type)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileTaskSpec(type);
    }

    public FileTaskSpec createFileTaskSpec(FileTaskSpec spec)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileTaskSpec(spec);
    }

    public FileTask submitFileTask(FileTaskSpec spec)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileTask(spec);
    }

    public void cancelFileTask(FileTask task)
            throws FileTaskException {
        FileBrowserService service = getFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        service.cancelFileTask(task);
    }
}

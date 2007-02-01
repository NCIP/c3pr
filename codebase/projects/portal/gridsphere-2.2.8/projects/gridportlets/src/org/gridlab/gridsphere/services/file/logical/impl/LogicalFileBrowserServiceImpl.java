/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.logical.impl;

import org.gridlab.gridsphere.services.file.*;
import org.gridlab.gridsphere.services.file.logical.*;
import org.gridlab.gridsphere.services.file.impl.AbstractFileTaskService;
import org.gridlab.gridsphere.services.file.tasks.*;
import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;

import java.util.*;

/**
 * Implements a file browser service that delegates tasks to other file browser services.
 */
public class LogicalFileBrowserServiceImpl
        extends AbstractFileTaskService
        implements LogicalFileBrowserService {

    protected static PortletLog log = SportletLog.getInstance(LogicalFileBrowserServiceImpl.class);
    protected PortletServiceFactory factory = SportletServiceFactory.getInstance();
    protected static List fileResourceTypes = new Vector();
    protected static SortedMap fileBrowserServices = new TreeMap();

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        super.init(config);
        fileResourceTypes.add(FileResourceType.INSTANCE.getID());
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
                LogicalFileBrowserService service = null;
                try {
                    service  = (LogicalFileBrowserService)
                            factory.createPortletService(serviceClass, config.getServletContext(), true);
                } catch (PortletServiceNotFoundException e) {
                    log.error("Service class not found: " + serviceName);
                } finally {
                    addLogicalFileBrowserService(resourceType, service);
                }
            }
        }
    }

    public void destroy() {
    }

    public List getFileTaskTypes() {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            log.warn("No file browser service exists");
            return new Vector(0);
        }
        return service.getFileTaskTypes();
    }

    public List getFileResources() {
        log.debug("Returning all logical file resources");
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            log.warn("No file browser service exists");
            return new Vector(0);
        }
        return service.getFileResources();
    }

    public List getFileResources(User user) {
        log.debug("Returning logical file resources for " + user.getUserID());
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            log.warn("No file browser service exists");
            return new Vector(0);
        }
        return service.getFileResources(user);
    }

    public FileResource getFileResource(String hostname) {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            log.warn("No file browser service exists");
            return null;
        }
        return service.getFileResource(hostname);
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
    private LogicalFileBrowserService getLogicalFileBrowserService(ResourceType type) {
        LogicalFileBrowserService service = null;
        if (type.equalsResourceType(FileResourceType.INSTANCE)
            || type.equalsResourceType(LogicalFileResourceType.INSTANCE)) {
            if (type.equals(FileResourceType.INSTANCE)) {
                if (fileResourceTypes.size() > 1) {
                    String stype = (String)fileResourceTypes.get(1);
                    service = (LogicalFileBrowserService)fileBrowserServices.get(stype);
                }
            } else {
                service = (LogicalFileBrowserService)fileBrowserServices.get(type.getID());
            }
        }
        return service;
    }

    public void addLogicalFileBrowserService(ResourceType type, LogicalFileBrowserService service) {
        log.info("Adding file browser service for resource type " + type.getID());
        fileResourceTypes.add(type.getID());
        fileBrowserServices.put(type.getID(), service);
    }

    public void addLogicalFileBrowserService(String type, LogicalFileBrowserService service) {
        log.info("Adding file browser service for resource type " + type);
        fileResourceTypes.add(type);
        fileBrowserServices.put(type, service);
    }

    public FileBrowser createFileBrowser(User user, String hostname)
            throws FileException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileException("No logical file browser service exists for " + hostname);
        }
        return service.createFileBrowser(user, hostname);
    }

    public FileBrowser createFileBrowser(User user, FileLocation location)
            throws FileException {
        String hostname = location.getHost();
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileException("No logical file browser service exists for " + hostname);
        }
        return service.createFileBrowser(user, hostname);
    }

    public FileListingSpec createFileListingSpec()
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileListingSpec();
    }

    public FileListing submitFileListing(FileListingSpec spec)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileListing(spec);
    }

    public FileNameChangeSpec createFileNameChangeSpec()
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileNameChangeSpec();
    }

    public FileNameChange submitFileNameChange(FileNameChangeSpec spec)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileNameChange(spec);
    }

    public FileMakeDirSpec createFileMakeDirSpec()
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileMakeDirSpec();
    }

    public FileMakeDir submitFileMakeDir(FileMakeDirSpec spec)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileMakeDir(spec);
    }

    public FileDeletionSpec createFileDeletionSpec()
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileDeletionSpec();
    }

    public FileDeletion submitFileDeletion(FileDeletionSpec spec)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileDeletion(spec);
    }

    public FileCopySpec createFileCopySpec()
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileCopySpec();
    }

    public FileCopy submitFileCopy(FileCopySpec spec)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileCopy(spec);
    }

    public FileMoveSpec createFileMoveSpec()
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileMoveSpec();
    }

    public FileMove submitFileMove(FileMoveSpec spec)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileMove(spec);
    }

    public MakeLogicalFileTaskSpec createMakeLogicalFileTaskSpec()
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createMakeLogicalFileTaskSpec();
    }

    public MakeLogicalFileTask submitMakeLogicalFileTask(MakeLogicalFileTaskSpec spec)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitMakeLogicalFileTask(spec);
    }

    public ReplicateFileTaskSpec createReplicateFileTaskSpec()
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createReplicateFileTaskSpec();
    }

    public ReplicateFileTask submitReplicateFileTask(ReplicateFileTaskSpec spec)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitReplicateFileTask(spec);
    }

    public FileUploadSpec createFileUploadSpec()
            throws FileTaskException {
        throw new FileTaskException("File upload not supported!");
    }

    public FileUpload submitFileUpload(FileUploadSpec spec)
            throws FileTaskException {
        throw new FileTaskException("File upload not supported!");
    }

    public FileDownloadSpec createFileDownloadSpec()
            throws FileTaskException {
        throw new FileTaskException("File download not supported!");
    }

    public FileDownload submitFileDownload(FileDownloadSpec spec)
            throws FileTaskException {
        throw new FileTaskException("File download not supported!");
    }

    public FileTaskSpec createFileTaskSpec(FileTaskType type)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileTaskSpec(type);
    }

    public FileTaskSpec createFileTaskSpec(FileTaskSpec spec)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.createFileTaskSpec(spec);
    }

    public FileTask submitFileTask(FileTaskSpec spec)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        return service.submitFileTask(spec);
    }

    public void cancelFileTask(FileTask task)
            throws FileTaskException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(FileResourceType.INSTANCE);
        if (service == null) {
            throw new FileTaskException("No file browser service exists");
        }
        service.cancelFileTask(task);
    }

   private LogicalFileBrowserService getLogicalFileBrowserService(LogicalFileResourceType type) {
        LogicalFileBrowserService service = null;
        if (type.equals(LogicalFileResourceType.INSTANCE)) {
            if (fileResourceTypes.size() > 1) {
                String stype = (String)fileResourceTypes.get(1);
                service = (LogicalFileBrowserService)fileBrowserServices.get(stype);
            }
        } else {
            service = (LogicalFileBrowserService)fileBrowserServices.get(type.getID());
        }
        return service;
    }

    public LogicalFileBrowser createLogicalFileBrowser(User user)
            throws FileException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(LogicalFileResourceType.INSTANCE);
        if (service == null) {
            throw new FileException("No logical file browser service exists");
        }
        return service.createLogicalFileBrowser(user);
    }

    public LogicalFileBrowser createLogicalFileBrowser(User user, FileLocation location)
            throws FileException {
        String hostname = location.getHost();
        LogicalFileBrowserService service = getLogicalFileBrowserService(LogicalFileResourceType.INSTANCE);
        if (service == null) {
            throw new FileException("No logical file browser service exists for " + hostname);
        }
        return service.createLogicalFileBrowser(user, location);
    }

    public LogicalFileResource getLogicalFileResource()
            throws FileException {
        LogicalFileBrowserService service = getLogicalFileBrowserService(LogicalFileResourceType.INSTANCE);
        if (service == null) {
            throw new FileException("No logical file browser service exists");
        }
        return service.getLogicalFileResource();
    }
}

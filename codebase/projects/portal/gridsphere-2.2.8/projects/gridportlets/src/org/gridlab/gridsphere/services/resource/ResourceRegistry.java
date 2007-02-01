/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ResourceRegistry.java,v 1.1.1.1 2007-02-01 20:40:54 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.common.PersistenceManagerXml;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class ResourceRegistry {

    public static final String DEFAULT_WEBAPP_FILE_PATH = "/WEB-INF/Resources.xml";
    public static final String DEFAULT_WEBAPP_MAPPING_FILE_PATH = "/WEB-INF/mapping/resources";

    private static PortletLog log = SportletLog.getInstance(ResourceRegistry.class);

    private String filePath;
    private String mappingFilePath;
    private long lastModified = 0;
    private List resourceList = new ArrayList();

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String path) {
        filePath = path;
    }

    public String getMappingFilePath() {
        return mappingFilePath;
    }

    public void setMappingFilePath(String path) {
        mappingFilePath = path;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long date) {
        lastModified = date;
    }

    public List getHardwareResources() {
        return resourceList;
    }

    public void setHardwareResources(ArrayList resources) {
        resourceList = resources;
    }

    public void save() throws IOException {
        try {
            PersistenceManagerXml pmXML = new PersistenceManagerXml(filePath);
            loadMappingFilePaths(pmXML, mappingFilePath);
            pmXML.save(this);
            File file = new File(filePath);
            setLastModified(file.lastModified());
        } catch (PersistenceManagerException e) {
            log.error("Unable to serialize to " + filePath + " with mapping file " + mappingFilePath, e);
            throw new IOException(e.getMessage());
        }
    }

    public static ResourceRegistry load(String filePath, String mappingFilePath)
            throws IOException {
        ResourceRegistry registry = null;
        try {
            PersistenceManagerXml pmXML = new PersistenceManagerXml(filePath);
            loadMappingFilePaths(pmXML, mappingFilePath);
            registry = (ResourceRegistry) pmXML.load();
            registry.setFilePath(filePath);
            registry.setMappingFilePath(mappingFilePath);
            File file = new File(filePath);
            registry.setLastModified(file.lastModified());
        } catch (Exception e) {
            log.error("Unable to deserialize from " + filePath + " with mapping file " + mappingFilePath, e);
            throw new IOException(e.getMessage());
        }
        return registry;
    }

    public static ResourceRegistry load(ServletContext servletContext)
            throws IOException {
        String filePath = servletContext.getRealPath(DEFAULT_WEBAPP_FILE_PATH);
        String mappingFilePath = servletContext.getRealPath(DEFAULT_WEBAPP_MAPPING_FILE_PATH);
        return load(filePath, mappingFilePath);
    }

    private static void loadMappingFilePaths(PersistenceManagerXml pmXml, String mappingFileDirPath) {
         File mappingFileDir = new File(mappingFileDirPath);
         File[] mappingFiles = mappingFileDir.listFiles();
         for (int ii = 0; ii < mappingFiles.length; ++ii) {
             String mappingFilePath = mappingFiles[ii].getPath();
             pmXml.addMappingPath(mappingFilePath);
         }
     }
}

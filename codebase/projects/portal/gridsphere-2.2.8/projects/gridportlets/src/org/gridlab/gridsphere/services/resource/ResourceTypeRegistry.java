package org.gridlab.gridsphere.services.resource;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;
import org.gridlab.gridsphere.services.util.GridPortletsDatabase;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.File;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: ResourceTypeRegistry.java,v 1.1.1.1 2007-02-01 20:40:55 kherm Exp $
 */

/**
 * This object contains a list of <code>ResourceTypeDescriptions</code> of resourcetypes which are available.
 */
public class ResourceTypeRegistry {

    public static final String DEFAULT_WEBAPP_FILE_PATH = "/WEB-INF/ResourceTypes.xml";
    public static final String DEFAULT_WEBAPP_MAPPING_FILE_PATH = "/WEB-INF/mapping/resourcetype-mapping.xml";

    private static PortletLog log = SportletLog.getInstance(ResourceTypeRegistry.class);

    private String filePath;
    private String mappingFilePath;
    private long lastModified = 0;
    private List resourcetypes = new ArrayList();

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

    public List getResourcetypes() {
        return resourcetypes;
    }

    public void setResourcetypes(List resourcetypes) {
        this.resourcetypes = resourcetypes;
    }

    public ResourceTypeDescription getResourceTypeDescriptionById(String id) {
        for (int i = 0; i < resourcetypes.size(); i++) {
            ResourceTypeDescription rtd = (ResourceTypeDescription) resourcetypes.get(i);
            if (rtd.getId().equals(id)) {
                return rtd;
            }
        }
        return null;
    }

    public void save() throws IOException {
        try {
            PersistenceManagerXml pmXML = PersistenceManagerFactory.createPersistenceManagerXml(filePath, mappingFilePath);
            pmXML.save(this);
            File file = new File(filePath);
            setLastModified(file.lastModified());
        } catch (PersistenceManagerException e) {
            log.error("FATAL PMXML: Unable to serialize Resources.xml", e);
            throw new IOException(e.getMessage());
        }
    }

    public static ResourceTypeRegistry load(String filePath, String mappingFilePath)
            throws IOException {
        ResourceTypeRegistry registry = null;
        try {
            PersistenceManagerXml pmXml
                    = PersistenceManagerFactory.createPersistenceManagerXml(filePath, mappingFilePath);
            registry = (ResourceTypeRegistry) pmXml.load();
            registry.setFilePath(filePath);
            registry.setMappingFilePath(mappingFilePath);
            File file = new File(filePath);
            registry.setLastModified(file.lastModified());
        } catch (Exception e) {
            log.error("FATAL PMXML: Unable to deserialize Resources.xml", e);
            throw new IOException(e.getMessage());
        }
        return registry;
    }

    public static ResourceTypeRegistry load(ServletContext servletContext)
            throws IOException {
        String filePath = servletContext.getRealPath(DEFAULT_WEBAPP_FILE_PATH);
        String mappingFilePath = servletContext.getRealPath(DEFAULT_WEBAPP_MAPPING_FILE_PATH);
        return load(filePath, mappingFilePath);
    }

    public String toString() {
        return "Number of ResourceTypes : " + resourcetypes.size();
    }
}

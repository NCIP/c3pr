/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceProfileRegistry.java,v 1.1.1.1 2007-02-01 20:42:13 kherm Exp $
 */

package org.gridlab.gridsphere.services.ui.resource.browser;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
//import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;
//import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.services.common.PersistenceManagerXml;

import java.util.*;
import java.lang.reflect.Constructor;
import java.io.IOException;

public class ResourceProfileRegistry {

    private static PortletLog log = SportletLog.getInstance(ResourceProfileRegistry.class);
    protected String xmlFilePath = "/WEB-INF/ResourceProfiles.xml";
    protected String xmlMappingPath = "/WEB-INF/mapping/ResourceProfile.xml";
    protected ArrayList resourceProfileList = new ArrayList();

    public static ResourceProfileRegistry load(String componentPath,
                     String mappingPath,
                     ClassLoader classLoader) throws IOException {

        ResourceProfileRegistry components = null;
        try {
//            PersistenceManagerXml pmXml = PersistenceManagerFactory.createPersistenceManagerXml(componentPath, mappingPath, classLoader);

            Class pmfClass = Class.forName(PersistenceManagerXml.class.getName(), true, classLoader);
            Class[] pmfParamTypes = { String.class };
            Constructor constructor = pmfClass.getConstructor(pmfParamTypes);
            Object[] pmfParams = { componentPath };
            PersistenceManagerXml pmXml = (PersistenceManagerXml)constructor.newInstance(pmfParams);
            pmXml.addMappingPath(mappingPath);

            components = (ResourceProfileRegistry) pmXml.load();
            components.setXmlFilePath(componentPath);
            components.setXmlMappingPath(mappingPath);
        } catch (Exception e) {
            log.error("FATAL PMXML: Unable to deserialize ResourceProfiles.xml", e);
            throw new IOException("Unable to deserialize ResourceProfiles.xml");
        }
        return components;
    }

    public String getXmlFilePath() {
        return xmlFilePath;
    }

    public void setXmlFilePath(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public String getXmlMappingPath() {
        return xmlMappingPath;
    }

    public void setXmlMappingPath(String xmlMappingPath) {
        this.xmlMappingPath = xmlMappingPath;
    }

    public List getResourceProfileList() {
        return resourceProfileList;
    }

    public void setResourceProfileList(ArrayList resourceProfileList) {
        this.resourceProfileList = resourceProfileList;
    }
}

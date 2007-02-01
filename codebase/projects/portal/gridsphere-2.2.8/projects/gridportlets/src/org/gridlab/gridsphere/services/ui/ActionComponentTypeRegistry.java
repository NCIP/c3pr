/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentTypeRegistry.java,v 1.1.1.1 2007-02-01 20:41:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
//import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;
//import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.services.common.PersistenceManagerXml;

import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Constructor;

public class ActionComponentTypeRegistry {

    public static PortletLog log = SportletLog.getInstance(ActionComponentTypeRegistry.class);
    protected ArrayList componentTypeList = new ArrayList();
    protected String xmlFilePath = "/WEB-INF/ActionComponentTypes.xml";
    protected String xmlMappingPath = "/WEB-INF/mapping/ActionComponentType.xml";

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

    public ArrayList getComponentTypeList() {
        return componentTypeList;
    }

    public void setComponentTypeList(ArrayList componentTypeList) {
        this.componentTypeList = componentTypeList;
    }

    public void addComponentTypeList(ActionComponentType componentType) {
        componentTypeList.add(componentType);
    }

    public void removeHardwareResource(ActionComponentType componentType) {
        this.componentTypeList.remove(componentType);
    }

    public static ActionComponentTypeRegistry open(String componentPath,
                                                   String mappingPath,
                                                   ClassLoader classLoader) throws IOException {

        ActionComponentTypeRegistry components = null;
        try {
//            PersistenceManagerXml pmXml = PersistenceManagerFactory.createPersistenceManagerXml(componentPath, mappingPath, classLoader);

            Class pmfClass = Class.forName(PersistenceManagerXml.class.getName(), true, classLoader);
            Class[] pmfParamTypes = { String.class };
            Constructor constructor = pmfClass.getConstructor(pmfParamTypes);
            Object[] pmfParams = { componentPath };
            PersistenceManagerXml pmXml = (PersistenceManagerXml)constructor.newInstance(pmfParams);
            pmXml.addMappingPath(mappingPath);

            components = (ActionComponentTypeRegistry) pmXml.load();
            components.setXmlFilePath(componentPath);
            components.setXmlMappingPath(mappingPath);
        } catch (Exception e) {
            log.error("FATAL PMXML: Unable to deserialize ActionComponentTypes.xml", e);
            throw new IOException("Unable to deserialize ActionComponentTypes.xml");
        }
        return components;
    }
}

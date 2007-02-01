package org.gridlab.gridsphere.services.ui.job;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
//import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;
//import org.gridlab.gridsphere.core.persistence.PersistenceManagerFactory;
import org.gridlab.gridsphere.services.common.PersistenceManagerXml;

import java.util.*;
import java.lang.reflect.Constructor;
import java.io.IOException;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobProfileRegistry.java,v 1.1.1.1 2007-02-01 20:42:05 kherm Exp $
 */

public class JobProfileRegistry {

    private static PortletLog log = SportletLog.getInstance(JobProfileRegistry.class);
    protected String xmlFilePath = "/WEB-INF/JobProfiles.xml";
    protected String xmlMappingPath = "/WEB-INF/mapping/JobProfile.xml";
    protected ArrayList jobProfileList = new ArrayList();

    public List getJobProfiles() {
        return jobProfileList;
    }

    public static JobProfileRegistry load(String componentPath,
                     String mappingPath,
                     ClassLoader classLoader) throws IOException {

        JobProfileRegistry profiles = null;
        try {
//            PersistenceManagerXml pmXml = PersistenceManagerFactory.createPersistenceManagerXml(componentPath, mappingPath, classLoader);

            Class pmfClass = Class.forName(PersistenceManagerXml.class.getName(), true, classLoader);
            Class[] pmfParamTypes = { String.class };
            Constructor constructor = pmfClass.getConstructor(pmfParamTypes);
            Object[] pmfParams = { componentPath };
            PersistenceManagerXml pmXml = (PersistenceManagerXml)constructor.newInstance(pmfParams);
            pmXml.addMappingPath(mappingPath);

            profiles = (JobProfileRegistry) pmXml.load();
            profiles.setXmlFilePath(componentPath);
            profiles.setXmlMappingPath(mappingPath);
        } catch (Exception e) {
            log.error("FATAL PMXML: Unable to deserialize JobProfiles.xml", e);
            throw new IOException("Unable to deserialize JobProfiles.xml");
        }
        return profiles;
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

    public ArrayList getJobProfileList() {
        return jobProfileList;
    }

    public void setJobProfileList(ArrayList jobProfileList) {
        this.jobProfileList = jobProfileList;
    }

    public void addJobProfileList(JobProfile profile) {
        jobProfileList.add(profile);
    }

    public void removeJobProfile(JobProfile profile) {
        this.jobProfileList.remove(profile);
    }
}

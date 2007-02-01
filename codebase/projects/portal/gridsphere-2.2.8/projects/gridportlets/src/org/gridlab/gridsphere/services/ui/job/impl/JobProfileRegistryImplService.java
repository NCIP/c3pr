package org.gridlab.gridsphere.services.ui.job.impl;

import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.ui.job.JobProfile;
import org.gridlab.gridsphere.services.ui.job.JobProfileRegistryService;

import java.util.*;
import java.lang.reflect.Constructor;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: JobProfileRegistryImplService.java,v 1.1.1.1 2007-02-01 20:42:10 kherm Exp $
 */

public class JobProfileRegistryImplService
        implements JobProfileRegistryService, PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(JobProfileRegistryImplService.class);
    private ArrayList jobComponentTypeList = new ArrayList();
    private Map jobComponentKeyMap = new HashMap();
    private boolean sorted = false;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("JobProfileRegistryImplService.init()");
        Enumeration paramNames = config.getInitParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String paramValue = config.getInitParameter(paramName);
            Class jobComponentTypeClass = null;
            try {
                jobComponentTypeClass = Class.forName(paramValue, true, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e) {
                log.error("Job component type class not found: " + paramValue);
            } finally {
                JobProfile jobProfile = null;
                try {
                    Class[] parameterTypes
                            = new Class[] {};
                    Object[] arguments = new Object[] {};
                    Constructor constructor = jobComponentTypeClass.getConstructor(parameterTypes);
                    jobProfile = (JobProfile)constructor.newInstance(arguments);
                    addJobProfile(jobProfile);
                } catch (Exception e) {
                    String msg = "Unable to create job component type " + jobComponentTypeClass.getName();
                    log.error(msg, e);
                }
            }
        }
    }

    public void destroy() {
    }

    public List getJobProfiles() {
        return jobComponentTypeList;
    }

    public JobProfile getJobProfile(String className) {
        return (JobProfile)jobComponentKeyMap.get(className);
    }

    public void addJobProfiles(List types) {
        for (int ii = 0; ii < types.size(); ++ii) {
            JobProfile type = (JobProfile)types.get(ii);
            addJobProfile(type);
        }
     }

    public void addJobProfile(JobProfile type) {
        log.info("Adding job component type [" + type.getOrder() + "] " + type.getDescription());
        jobComponentTypeList.add(type);
        jobComponentKeyMap.put(type.getKey(), type);
     }
}

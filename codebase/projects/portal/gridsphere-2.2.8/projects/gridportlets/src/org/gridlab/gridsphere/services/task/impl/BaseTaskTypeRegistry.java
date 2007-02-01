package org.gridlab.gridsphere.services.task.impl;

import org.gridlab.gridsphere.services.task.TaskTypeRegistry;
import org.gridlab.gridsphere.services.task.TaskSubmissionService;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseTaskTypeRegistry.java,v 1.1.1.1 2007-02-01 20:41:55 kherm Exp $
 * <p>
 */

public class BaseTaskTypeRegistry
        implements TaskTypeRegistry, PortletServiceProvider {

    protected static PortletLog log = SportletLog.getInstance(BaseTaskTypeRegistry.class);
    protected List typeList = new Vector();
    protected List serviceList = new Vector();
    protected HashMap typeServiceMap = new HashMap();

    public BaseTaskTypeRegistry() {
        log.debug("Instantiating task submission registry");
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
    }

    public void destroy() {
    }

    public List getTaskTypes() {
        return typeList;
    }

    public List getTaskSubmissionServices() {
        return serviceList;
    }

    public TaskSubmissionService getTaskSubmissionService(TaskType type) {
        if (log.isDebugEnabled()) {
            //log.debug("Checking type list");
            // Return the service that maps to the given type
            Iterator rTypes = typeList.iterator();
            while (rTypes.hasNext()) {
                org.gridlab.gridsphere.services.task.TaskType rType = (TaskType)rTypes.next();
                //log.debug(" Found type = " + rType.getClass().getName());
            }
            //log.debug("Checking service list");
            // Return the service that maps to the given type
            Iterator services = serviceList.iterator();
            while (services.hasNext()) {
                TaskSubmissionService service = (TaskSubmissionService)services.next();
                //log.debug("  Checking service = " + service.getClass().getName());
                Iterator sTypes = service.getTaskTypes().iterator();
                while (sTypes.hasNext()) {
                    TaskType sType = (TaskType)sTypes.next();
                    //log.debug("    Supports type = " + sType.getClass().getName());
                }
            }
        }
        return (TaskSubmissionService)typeServiceMap.get(type.getClassName());
    }

    public void registerTaskTypes(TaskSubmissionService service) {
        String serviceName = service.getClass().getName();
        log.info("Registering new service " + serviceName);
        // Add the service to our list of services
        serviceList.add(service);
        // Add the types the service supports to our list of types
        List taskTypes = service.getTaskTypes();
        typeList.addAll(taskTypes);
        // Add the type to service mappings as well...
        for (int ii = 0; ii < taskTypes.size(); ++ii) {
            // A type maps to only one service
            TaskType type = (TaskType) taskTypes.get(ii);
            String key = type.getClassName();
            log.debug("Task type " + key + " maps to " + serviceName);
            typeServiceMap.put(key, service);
        }
    }

    public void unregisterTaskTypes(TaskSubmissionService service) {
    }
}

package org.gridlab.gridsphere.services.task.impl;

import org.gridlab.gridsphere.services.task.*;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.*;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: SimpleTaskSubmissionBroker.java,v 1.1.1.1 2007-02-01 20:41:55 kherm Exp $
 * <p>
 */

public abstract class SimpleTaskSubmissionBroker
        extends AbstractTaskSubmissionService {

    protected static PortletLog log = SportletLog.getInstance(SimpleTaskSubmissionBroker.class);
    protected List subTypeList = new Vector();
    protected HashMap subTypeMap = new HashMap();

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        super.init(config);
        Enumeration paramNames = config.getInitParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String)paramNames.nextElement();
            String serviceName = config.getInitParameter(paramName);
            Class serviceClass = null;
            try {
                serviceClass = Class.forName(serviceName);
            } catch (ClassNotFoundException e) {
                log.error("Service class not found: " + serviceName);
            } finally {
                TaskSubmissionService service = null;
                try {
                    service  = (TaskSubmissionService)
                            factory.createPortletService(serviceClass, config.getServletContext(), true);
                } catch (PortletServiceNotFoundException e) {
                    log.error("Service class not found: " + serviceName);
                } finally {
                    addBrokeredTaskTypes(service);
                }
            }
        }
    }

    public TaskSpec createTaskSpec(TaskType type)
            throws TaskException {
        log.debug("Creating task spec " + type.getTaskSpecClassName());
        // Translate to a default type if this is one of our base types
        if (hasTaskType(type)) {
            type = getDefaultTaskType(type);
        } else if (!brokersTaskType(type)) {
            throw new TaskException("Type not supported by this broker");
        }
        // Now get the service that maps to this type
        TaskSubmissionService service = registry.getTaskSubmissionService(type);
        if (service == null) {
            throw new TaskException("No service supports the given type");
        }
        // And create a task spec with it
        return service.createTaskSpec(type);
    }

    public TaskSpec createTaskSpec(TaskSpec spec)
            throws TaskException {
        TaskType type = spec.getTaskType();
        log.debug("Creating task spec " + type.getTaskSpecClassName());
        // Get the service for this spec's type
        TaskSubmissionService service = registry.getTaskSubmissionService(type);
        if (service == null) {
            throw new TaskTypeException("No service supports the given type.");
        }
        // Create a new spec with the service
        return service.createTaskSpec(spec);
    }

    public TaskSpec createTaskSpec(TaskType type, TaskSpec spec)
            throws TaskException {
        log.debug("Creating task spec " + type.getTaskSpecClassName());
        // Translate to a default type if this is one of our base types
        if (hasTaskType(type)) {
            type = getDefaultTaskType(type);
        } else if (!brokersTaskType(type)) {
            throw new TaskException("Type not supported by this broker");
        }
        // Now get the service that maps to this type
        TaskSubmissionService service = registry.getTaskSubmissionService(type);
        if (service == null) {
            throw new TaskException("No service supports the given type");
        }
        // And create a task spec with it
        return service.createTaskSpec(spec);
    }

    public Task submitTask(TaskSpec spec)
            throws TaskException {
        // Retrieve service that maps to given type
        TaskSubmissionService service = registry.getTaskSubmissionService(spec.getTaskType());
        if (service == null) {
            throw new TaskTypeException("No service supports the given type.");
        }
        // Submit a task with it
        return service.submitTask(spec);
    }

    public void cancelTask(Task task)
            throws TaskException {
        // Retrieve service that maps to given type
        TaskSubmissionService service = registry.getTaskSubmissionService(task.getTaskType());
        if (service == null) {
            throw new TaskTypeException("No service supports the given type.");
        }
        // Cancel the task with it
        service.cancelTask(task);
    }


    public List getBrokeredTaskTypes() {
        return subTypeList;
    }

    public boolean brokersTaskType(TaskType type) {
        return (subTypeMap.get(type.getClassName()) != null);
    }

    public TaskType getDefaultTaskType(TaskType type)
            throws TaskException {
        log.debug("Searching for default task type for " + type.getClassName());
        // Search through all our brokered types for assignable type instances
        Iterator brokeredTypes = getBrokeredTaskTypes().iterator();
        while (brokeredTypes.hasNext()) {
            TaskType brokeredType = (TaskType)brokeredTypes.next();
            // Return this instance if it is assignable from type class
            if (type.getClass().isAssignableFrom(brokeredType.getClass())) {
                 log.debug("Returning default task type " + brokeredType.getClassName());
                 return brokeredType;
            }
        }
        throw new TaskException("No default type found for task type " + type.getClassName());
    }

    public void addBrokeredTaskTypes(TaskSubmissionService service) {
        Iterator baseTypes = getTaskTypes().iterator();
        while (baseTypes.hasNext()) {
            TaskType baseType = (TaskType)baseTypes.next();
            Class baseClass = baseType.getClass();
            log.debug("Brokering all task types that extend " + baseClass.getName());
            Iterator types = service.getTaskTypes().iterator();
            while (types.hasNext()) {
                TaskType type = (TaskType)types.next();
                Class typeClass = type.getClass();
                String typeClassName = typeClass.getName();
                log.debug("Checking if can broker task type " + typeClassName);
                // If the taskUI type's extends our base type
                // add it to our list, otherwise keep moving on
                if (baseClass.isAssignableFrom(typeClass)) {
                    log.debug("Adding brokered task type " + typeClassName);
                    subTypeList.add(type);
                    subTypeMap.put(type.getClassName(), type);
                }
            }
        }
    }
}

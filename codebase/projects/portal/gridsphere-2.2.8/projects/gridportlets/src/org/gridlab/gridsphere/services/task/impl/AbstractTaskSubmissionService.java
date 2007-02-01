package org.gridlab.gridsphere.services.task.impl;

import org.gridlab.gridsphere.services.task.*;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.gridlab.gridsphere.services.security.gss.CredentialManagerService;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerRdbms;
import org.gridlab.gridsphere.services.util.GridPortletsDatabase;

import java.util.List;
import java.util.Vector;
import java.util.Iterator;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: AbstractTaskSubmissionService.java,v 1.1.1.1 2007-02-01 20:41:52 kherm Exp $
 * <p>
 */

public abstract class AbstractTaskSubmissionService
        implements TaskSubmissionService, PortletServiceProvider {

    protected static PortletLog log = SportletLog.getInstance(AbstractTaskSubmissionService.class);
    protected PortletServiceFactory factory = null;
    protected TaskTypeRegistry registry = null;
    protected GridPortletsDatabase pm = null;
    protected Boolean isInited = Boolean.FALSE;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        synchronized(isInited) {
            if (!isInited.booleanValue()) {
                isInited = Boolean.TRUE;
                factory = SportletServiceFactory.getInstance();
                pm = GridPortletsDatabase.getInstance();
                //log.debug("Getting task type registry");
                try {
                    registry = (TaskTypeRegistry)
                        factory.createPortletService(TaskTypeRegistry.class,
                                config.getServletContext(), true);
                } catch (PortletServiceNotFoundException e) {
                    log.error("Unable to get instance of task type registry", e);
                    throw new PortletServiceUnavailableException(e.getMessage());
                }
                registry.registerTaskTypes(this);
            }
        }
    }

    public void destroy() {
        registry.unregisterTaskTypes(this);
    }

    public TaskSpec createTaskSpec(TaskType type, TaskSpec spec)
            throws TaskException {
        return createTaskSpec(spec);
    }

    public boolean hasTaskType(TaskType type) {
        List taskTypes = getTaskTypes();
        for (int ii = 0; ii < taskTypes.size(); ++ii) {
            TaskType nextType = (TaskType)taskTypes.get(ii);
            if (nextType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public List getTasks(TaskType type) {
        String taskClassName = type.getTaskClassName();
        //log.debug("Getting task of type " + taskClassName);
        String query = "from " + taskClassName
                     + " as r order by r.oid desc";
        //log.debug(query);
        try {
            return pm.restoreList(query);
        } catch (PersistenceManagerException e) {
            log.error("FATAL: could not retrieve list of "
                      + taskClassName + " tasks!" + e.getMessage(), e);
            return new Vector();
        }
    }

    public List getTasks(User user, TaskType type) {
        String taskClassName = type.getTaskClassName();
        //log.debug("Getting tasks of type " + taskClassName);
        String query = "from " + taskClassName
                     + " as r where r.UserOid='" + user.getID() + "'"
                     + " order by r.oid desc";
        //log.debug(query);
        try {
            if (pm == null) log.warn("PM is null!!!");
            return pm.restoreList(query);
        } catch (Exception e) {
            log.error("FATAL: could not retrieve list of "
                      + taskClassName + " tasks!" + e.getMessage(), e);
            return new Vector();
        }
    }

    protected TaskType getTaskType(Class type) {
        Iterator taskTypes = getTaskTypes().iterator();
        while (taskTypes.hasNext()) {
            TaskType taskType = (TaskType)taskTypes.next();
            if (taskType.getClass().equals(type)) {
                return taskType;
            }
        }
        return null;
    }

    public Task getTask(String oid) {
        Task task = null;
        try {
            task = (Task) pm.restore("from " + Task.class.getName()
                                         + " as r where r.oid='" + oid + "'");
        } catch (Exception e) {
            log.error("FATAL: could not retrieve task by id " + oid + "!\n" + e.getMessage(), e);
        }
        return task;
    }

    public void saveTask(Task task) {
        try {
            if (task.getOid() == null) {
                pm.create(task);
            } else {
                pm.update(task);
            }
        } catch (Exception e) {
            log.error("FATAL: could not store task in database!\n" + e.getMessage(), e);
        }
    }

    public void deleteTask(Task task) {
        try {
            if (task.getOid() != null) {
                pm.delete(task);
            }
        } catch (Exception e) {
            log.error("FATAL: could not delete task in database!\n" + e.getMessage(), e);
        }
    }

    protected CredentialContext getDefaultCredentialContext(User user) throws TaskException {
        // Get credentialContext manager service
        CredentialManagerService credentialManager = null;
        try {
            credentialManager = (CredentialManagerService)
                factory.createPortletService(CredentialManagerService.class, null, true);
        } catch (Exception e) {
                log.error("Unable to get instance of credential manager service", e);
                throw new TaskException(e.getMessage());
        }
        // Get users's credentials
        CredentialContext cred = credentialManager.getDefaultCredentialContext(user);
        if (cred == null) {
            throw new TaskException("User has no active credentials");
        }
        return cred;
    }

}

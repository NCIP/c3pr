package org.gridlab.gridsphere.services.task.impl;

import org.gridlab.gridsphere.services.util.GridPortletsDatabase;
import org.gridlab.gridsphere.services.util.GridSphereUserUtil;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.task.*;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.gridlab.gridsphere.services.security.gss.CredentialUtil;
import org.gridlab.gridsphere.services.security.gss.CredentialManagerService;
import org.gridlab.gridsphere.services.resource.impl.BaseResource;
import org.gridlab.gridsphere.services.resource.ResourceType;

import java.util.*;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseTask.java,v 1.1.1.1 2007-02-01 20:41:53 kherm Exp $
 * <p>
 * Base implementation of Task. For compatability, all
 * implementations of Task shoujld extend from this one.
 */

public class BaseTask extends BaseResource implements Task {

    private static PortletLog log = SportletLog.getInstance(BaseTask.class);

    protected User user = null;
    protected String userOid = null;
    protected CredentialContext credentialContext = null;
    protected BaseTaskSpec taskSpec = null;
    protected Map taskAttributeMap = new HashMap();
    protected Map taskMetricMap = new HashMap();
    protected TaskStatus taskStatus = TaskStatus.NEW;
    protected String taskStatusName = TaskStatus.NEW.getName();
    protected String taskStatusMessage = null;
    protected transient List taskStatusListeners = null;
    protected List taskStatusListenerClassNames = new ArrayList();
    protected Date dateTaskStarted = null;
    protected long timeTaskStarted = 0;
    protected Date dateTaskSubmitted = null;
    protected long timeTaskSubmitted = 0;
    protected Date dateTaskStatusChanged = null;
    protected long timeTaskStatusChanged = 0;
    protected Date dateTaskEnded = null;
    protected long timeTaskEnded = 0;
    protected boolean deleteWhenTaskEndsFlag = false;

    private final Integer lock = new Integer(0);

    /**
     * Default constructor.
     */
    public BaseTask() {
    }

    /**
     * Obtains values from the given spec.
     * @param spec
     */
    public BaseTask(TaskSpec spec) {
        setSpec(spec);
    }

    public ResourceType getResourceType() {
        return getTaskType();
    }

    public boolean isResourceType(ResourceType type) {
        return getTaskType().isResourceType(type);
    }

    /**
     * See Task. We return the type of the spec
     * provided, to be sure we have the appropriate
     * type. But this means spec must be set before
     * this method can be used!
     * @return The task type
     */
    public TaskType getTaskType() {
        if (taskSpec == null) {
            return TaskType.INSTANCE;
        }
        return taskSpec.getTaskType();
    }

    public boolean isTaskType(TaskType taskType) {
        return getTaskType().isTaskType(taskType);
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String id) {
        oid = id;
    }

    public User getUser() {
        if (user == null && userOid != null) {
            user = GridSphereUserUtil.getUserByOid(userOid);
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user == null) {
            userOid = null;
        } else {
            userOid = user.getID();
        }
    }

    public String getUserOid() {
        return userOid;
    }

    public void setUserOid(String oid) {
        userOid = oid;
    }

    public CredentialContext getCredentialContext() {
        if (credentialContext == null && dn != null && userOid != null) {
            credentialContext = CredentialUtil.getCredentialContext(getUser(), dn);
        }
        return credentialContext;
    }

    public void setCredentialContext(CredentialContext credentialContext) {
        this.credentialContext = credentialContext;
        if (credentialContext != null) {
            dn = credentialContext.getDn();
        }
    }

    protected CredentialContext getDefaultCredentialContext() throws TaskException {
        // Get credentialContext manager service
        CredentialManagerService credentialManager = null;
        try {
            credentialManager = (CredentialManagerService)
                SportletServiceFactory.getInstance().createPortletService(CredentialManagerService.class, null, true);
        } catch (Exception e) {
                log.error("Unable to get instance of credential manager service", e);
                throw new TaskException(e.getMessage());
        }
        // Get users's credentials
        CredentialContext cred = null;
        if (userOid != null) {
            cred = credentialManager.getDefaultCredentialContext(user);
        }
        if (cred == null) {
            throw new TaskException("User has no active credentials");
        }
        return cred;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskSpec getTaskSpec() {
        return taskSpec;
    }

    public void setTaskSpec(TaskSpec spec) {
        taskSpec = (BaseTaskSpec)spec;
    }

    public void setSpec(TaskSpec spec) {
        if (spec == null) {
            taskSpec = null;
        } else {
            taskSpec = (BaseTaskSpec)spec;
            setUser(taskSpec.getUser());
            setCredentialContext(taskSpec.getCredentialContext());
            setDescription(taskSpec.getDescription());
            copyTaskStatusListenerClassNames(taskSpec.getTaskStatusListeners());
            copyTaskAttributes(taskSpec.getTaskAttributes());
            setDeleteWhenTaskEndsFlag(spec.getDeleteWhenTaskEndsFlag());
        }
    }

    public TaskStatus getTaskStatus() {
        synchronized (lock) {
            return taskStatus;
        }
    }

    public String getTaskStatusMessage() {
        return taskStatusMessage;
    }

    public void setTaskStatusMessage(String message) {
        taskStatusMessage = message;
    }

    public void setTaskStatus(TaskStatus newStatus) {
        setTaskStatus(newStatus, null);
    }

    public void setTaskStatus(TaskStatus newStatus, String newMessage) {
        synchronized (lock) {
            // Check to see if taskStatus changed
            if (newStatus.equals(taskStatus)) {
                log.debug("Status is same as before");
                taskStatusMessage = newMessage;
            } else {
                log.debug("Setting task status " + newStatus.getName() + " for task " + oid);
                // Set date taskStatus changed
                Date now = new Date();
                setDateTaskStatusChanged(now);
                // If still live
                if (newStatus.isLive()) {
                    // Handle canceling cases....
                    if (taskStatus.equals(TaskStatus.CANCELING))  {
                        if (newStatus.equals(TaskStatus.ACTIVE)) {
                            log.debug("Converting active status back to canceling");
                            newStatus = TaskStatus.CANCELING;
                        } else if (taskStatus.equals(TaskStatus.FAILED)) {
                            log.debug("Converting failed status to canceled");
                            newStatus = TaskStatus.CANCELED;
                        }
                    // Handle newly submitted cases...
                    } else if (newStatus.equals(TaskStatus.SUBMITTED)) {
                        setDateTaskSubmitted(now);
                        // Handle newly active cases...
                    } else if (newStatus.equals(TaskStatus.ACTIVE)) {
                        // If this first time active is called
                        if (!taskStatus.equals(TaskStatus.ACTIVE)) {
                            setDateTaskStarted(now);
                        }
                    }
                } else {
                    setDateTaskEnded(now);
                }
                // Set new taskStatus
                taskStatus = newStatus;
                taskStatusName = newStatus.getName();
                taskStatusMessage = newMessage;
                // Notify our listeners...
                notifyStatusListeners();
            }

           GridPortletsDatabase pm = GridPortletsDatabase.getInstance();
           try {
                if (deleteWhenTaskEndsFlag && oid != null && !taskStatus.isLive()) {
                    pm.delete(this);
                    log.debug("Deleted task " + oid);
                } else {
                   if (oid == null) {
                       pm.create(this);
                       log.debug("Created task " + oid);
                   } else {
                       pm.update(this);
                       log.debug("Updated task " + oid);
                   }
                }
            } catch(Exception e) {
                log.error("Unable to save task " + oid, e);
            }
        }
    }

    public String getTaskStatusName() {
        return taskStatusName;
    }

    public void setTaskStatusName(String taskStatusName) {
        this.taskStatusName = taskStatusName;
        taskStatus = TaskStatus.toTaskStatus(taskStatusName);
    }

    public boolean isTaskLive() {
        return taskStatus.isLive();
    }

    public List getTaskStatusListenerClassNames() {
        return taskStatusListenerClassNames;
    }

    public void setTaskStatusListenerClassNames(List taskStatusListenerClassNames) {
        this.taskStatusListenerClassNames = taskStatusListenerClassNames;
    }

    protected void copyTaskStatusListenerClassNames(List taskStatusListenerClassNameList) {
        taskStatusListenerClassNames.clear();
        for (Iterator taskStatusListenerClassNames = taskStatusListenerClassNameList.iterator(); taskStatusListenerClassNames.hasNext();) {
            String className = (String) taskStatusListenerClassNames.next();
            this.taskStatusListenerClassNames.add(className);
        }
    }

    public List getTaskStatusListeners() {
        if (taskStatusListeners == null) {
            BaseTaskStatusManager taskStatusManager = BaseTaskStatusManager.getInstance();
            taskStatusListeners = taskStatusManager.getTaskStatusListeners(taskStatusListenerClassNames);
        }
        return taskStatusListeners;
    }

    public void setTaskStatusListeners(List taskStatusListeners) {
        BaseTaskStatusManager taskStatusManager = BaseTaskStatusManager.getInstance();
        taskStatusListenerClassNames = taskStatusManager.getTaskStatusListenerClassNames(taskStatusListeners);
    }

    public void addTaskStatusListener(TaskStatusListener listener) {
        BaseTaskStatusManager taskStatusManager = BaseTaskStatusManager.getInstance();
        taskStatusManager.addTaskStatusListener(listener, taskStatusListeners, taskStatusListenerClassNames);
    }

    public void waitFor() throws TaskException {
        boolean isLive = true;
        while (isLive) {
            synchronized (lock) {
                isLive = taskStatus.isLive();
            }
            if (isLive) {
                //log.debug("Checking status....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("Unable to put thread to sleep", e);
                    throw new TaskException(e.getMessage());
                }
            }
        }
    }

    private void notifyStatusListeners() {
        Iterator listeners = getTaskStatusListeners().iterator();
        while (listeners.hasNext()) {
            TaskStatusListener listener = (TaskStatusListener)listeners.next();
            listener.statusChanged(this);
        }
    }

    public Date getDateTaskSubmitted() {
        return dateTaskSubmitted;
    }

    public void setDateTaskSubmitted(Date date) {
        dateTaskSubmitted = date;
        if (date != null) timeTaskSubmitted = date.getTime();
    }

    public long getTimeTaskSubmitted() {
        return timeTaskSubmitted;
    }

    public void setTimeTaskSubmitted(long time) {
        timeTaskSubmitted = time;
        if (time > 0) dateTaskSubmitted = new Date(time);
    }

    public Date getDateTaskStarted() {
        return dateTaskStarted;
    }

    public void setDateTaskStarted(Date date) {
        dateTaskStarted = date;
        if (date != null) timeTaskStarted = date.getTime();
    }

    public long getTimeTaskStarted() {
        return timeTaskStarted;
    }

    public void setTimeTaskStarted(long time) {
        timeTaskStarted = time;
        if (time > 0) dateTaskStarted = new Date(time);
    }

    public Date getDateTaskStatusChanged() {
        return dateTaskStatusChanged;
    }

    public void setDateTaskStatusChanged(Date date) {
        dateTaskStatusChanged = date;
        if (date != null) timeTaskStatusChanged = dateTaskStatusChanged.getTime();
    }

    public long getTimeTaskStatusChanged() {
        return timeTaskStatusChanged;
    }

    public void setTimeTaskStatusChanged(long time) {
        timeTaskStatusChanged = time;
        if (time > 0) dateTaskStatusChanged = new Date(time);
    }

    public Date getDateTaskEnded() {
        return dateTaskEnded;
    }

    public void setDateTaskEnded(Date date) {
        dateTaskEnded = date;
        if (date != null) timeTaskEnded = dateTaskEnded.getTime();
    }

    public long getTimeTaskEnded() {
        return timeTaskEnded;
    }

    public void setTimeTaskEnded(long time) {
        timeTaskEnded = time;
        if (time > 0) dateTaskEnded = new Date(time);
    }

    public boolean getDeleteWhenTaskEndsFlag() {
        return deleteWhenTaskEndsFlag;
    }

    public void setDeleteWhenTaskEndsFlag(boolean flag) {
        deleteWhenTaskEndsFlag = flag;
    }

    public Map getTaskAttributeMap() {
        return taskAttributeMap;
    }

    public void setTaskAttributeMap(Map taskAttributes) {
        taskAttributeMap = taskAttributes;
    }

    public List getTaskAttributes() {
        return new ArrayList(taskAttributeMap.values());
    }

    public void setTaskAttributes(List attributeList) {
        taskAttributeMap.clear();
        for (Iterator attributes = attributeList.iterator(); attributes.hasNext();) {
            TaskAttribute attribute = (TaskAttribute)attributes.next();
            taskAttributeMap.put(attribute.getName(), attribute);
        }
    }

    protected void copyTaskAttributes(List taskAttributeList) {
        taskAttributeMap.clear();
        for (Iterator taskAttributes = taskAttributeList.iterator(); taskAttributes.hasNext();) {
            TaskAttribute taskAttribute = (TaskAttribute) taskAttributes.next();
            TaskAttribute thisAttribute = new TaskAttribute(taskAttribute);
            taskAttributeMap.put(thisAttribute.getName(), thisAttribute);
        }
    }

    public TaskAttribute getTaskAttribute(String name) {
        return (TaskAttribute)taskAttributeMap.get(name);
    }

    public void putTaskAttribute(TaskAttribute attr) {
        taskAttributeMap.put(attr.getName(), attr);
    }

    public TaskAttribute putTaskAttribute(String name, String value) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            attribute = new TaskAttribute(name, value);
            taskAttributeMap.put(name, attribute);
        } else {
            attribute.setValue(value);
        }
        return attribute;
    }

    public TaskAttribute removeTaskAttribute(String name) {
        return (TaskAttribute)taskAttributeMap.remove(name);
    }

    public void removeTaskAttribute(TaskAttribute attr) {
        taskAttributeMap.remove(attr.getName());
    }

    public void clearTaskAttributes() {
        taskAttributeMap.clear();
    }

    public String getTaskAttributeValue(String name) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    public String getTaskAttributeValue(String name, String defaultValue) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            return defaultValue;
        }
        return attribute.getValue();
    }

    public void setTaskAttributeValue(String name, String value) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            attribute  = new TaskAttribute(name, value);
            taskAttributeMap.put(name, attribute);
        } else {
            attribute.setValue(value);
        }
    }

    public List getTaskAttributeValueAsList(String name, String delim) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            return new ArrayList(0);
        }
        return attribute.getValueAsList(delim);
    }

    public void setTaskAttributeValueAsList(String name, List valueList, String delim) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            attribute = new TaskAttribute();
            attribute.setName(name);
            attribute.setValueAsList(valueList, delim);
            taskAttributeMap.put(name, attribute);
        } else {
            attribute.setValueAsList(valueList, delim);
        }
    }

    public boolean getTaskAttributeAsBool(String name, boolean defaultValue) {
        TaskAttribute attr = getTaskAttribute(name);
        if (attr == null) {
            return defaultValue;
        } else {
            return attr.getValueAsBool();
        }
    }

    public void setTaskAttributeAsBool(String name, boolean enabled) {
        TaskAttribute attr = getTaskAttribute(name);
        if (attr == null) {
            attr = new TaskAttribute(name, String.valueOf(enabled));
            taskAttributeMap.put(name, attr);
        } else {
            attr.setValue(String.valueOf(enabled));
        }
    }

    public int getTaskAttributeAsInt(String name, int defaultValue) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            return defaultValue;
        } else {
            return attribute.getValueAsInt(defaultValue);
        }
    }

    public void setTaskAttributeAsInt(String name, int value) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            attribute  = new TaskAttribute(name, String.valueOf(value));
            taskAttributeMap.put(name, attribute);
        } else {
            attribute.setValue(String.valueOf(value));
        }
    }

    public long getTaskAttributeAsLng(String name, long defaultValue) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            return defaultValue;
        } else {
            return attribute.getValueAsLng(defaultValue);
        }
    }

    public void setTaskAttributeAsLng(String name, long value) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            attribute  = new TaskAttribute(name, String.valueOf(value));
            taskAttributeMap.put(name, attribute);
        } else {
            attribute.setValue(String.valueOf(value));
        }
    }

    public double getTaskAttributeAsDbl(String name, double defaultValue) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            return defaultValue;
        } else {
            return attribute.getValueAsDbl(defaultValue);
        }
    }

    public void setTaskAttributeAsDbl(String name, double value) {
        TaskAttribute attribute = getTaskAttribute(name);
        if (attribute == null) {
            attribute  = new TaskAttribute(name, String.valueOf(value));
            taskAttributeMap.put(name, attribute);
        } else {
            attribute.setValue(String.valueOf(value));
        }
    }

    public Map getTaskMetricMap() {
        return taskMetricMap;
    }

    public void setTaskMetricMap(Map taskMetrics) {
        taskMetricMap = taskMetrics;
    }

    public List getTaskMetrics() {
        return new ArrayList(taskMetricMap.values());
    }

    public void setTaskMetrics(List attributeList) {
        taskMetricMap.clear();
        for (Iterator attributes = attributeList.iterator(); attributes.hasNext();) {
            TaskMetric attribute = (TaskMetric)attributes.next();
            taskMetricMap.put(attribute.getName(), attribute);
        }
    }

    protected void copyTaskMetrics(List taskMetrics) {
        taskMetricMap.clear();
        for (int ii = 0; ii < taskMetrics.size(); ++ii) {
            TaskMetric attribute = (TaskMetric)taskMetrics.get(ii);
            TaskMetric thisMetric = new TaskMetric(attribute);
            taskMetricMap.put(thisMetric.getName(), thisMetric);
        }
    }

    public TaskMetric getTaskMetric(String name) {
        return (TaskMetric)taskMetricMap.get(name);
    }

    public void putTaskMetric(TaskMetric attr) {
        taskMetricMap.put(attr.getName(), attr);
    }

    public TaskMetric putTaskMetric(String name, String value) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            attribute = new TaskMetric(name, value);
            taskMetricMap.put(name, attribute);
        } else {
            attribute.setValue(value);
        }
        return attribute;
    }

    public TaskMetric removeTaskMetric(String name) {
        return (TaskMetric)taskMetricMap.remove(name);
    }

    public void clearTaskMetrics() {
        taskMetricMap.clear();
    }

    public boolean hasTaskMetrics() {
        return ! taskMetricMap.isEmpty();
    }

    public String getTaskMetricValue(String name) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    public String getTaskMetricValue(String name, String defaultValue) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            return defaultValue;
        }
        return attribute.getValue();
    }

    public void setTaskMetricValue(String name, String value) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            attribute  = new TaskMetric(name, value);
            taskMetricMap.put(name, attribute);
        } else {
            attribute.setValue(value);
        }
    }

    public List getTaskMetricValueAsList(String name, String delim) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            return new ArrayList(0);
        }
        return attribute.getValueAsList(delim);
    }

    public void setTaskMetricValueAsList(String name, List valueList, String delim) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            attribute = new TaskMetric();
            attribute.setName(name);
            attribute.setValueAsList(valueList, delim);
            taskMetricMap.put(name, attribute);
        } else {
            attribute.setValueAsList(valueList, delim);
        }
    }

    public boolean getTaskMetricAsBool(String name, boolean defaultValue) {
        TaskMetric attr = getTaskMetric(name);
        if (attr == null) {
            return defaultValue;
        } else {
            return attr.getValueAsBool();
        }
    }

    public void setTaskMetricAsBool(String name, boolean enabled) {
        TaskMetric attr = getTaskMetric(name);
        if (attr == null) {
            attr = new TaskMetric(name, String.valueOf(enabled));
            taskMetricMap.put(name, attr);
        } else {
            attr.setValue(String.valueOf(enabled));
        }
    }

    public int getTaskMetricAsInt(String name, int defaultValue) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            return defaultValue;
        } else {
            return attribute.getValueAsInt(defaultValue);
        }
    }

    public void setTaskMetricAsInt(String name, int value) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            attribute  = new TaskMetric(name, String.valueOf(value));
            taskMetricMap.put(name, attribute);
        } else {
            attribute.setValue(String.valueOf(value));
        }
    }

    public long getTaskMetricAsLng(String name, long defaultValue) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            return defaultValue;
        } else {
            return attribute.getValueAsLng(defaultValue);
        }
    }

    public void setTaskMetricAsLng(String name, long value) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            attribute  = new TaskMetric(name, String.valueOf(value));
            taskMetricMap.put(name, attribute);
        } else {
            attribute.setValue(String.valueOf(value));
        }
    }

    public double getTaskMetricAsDbl(String name, double defaultValue) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            return defaultValue;
        } else {
            return attribute.getValueAsDbl(defaultValue);
        }
    }

    public void setTaskMetricAsDbl(String name, double value) {
        TaskMetric attribute = getTaskMetric(name);
        if (attribute == null) {
            attribute  = new TaskMetric(name, String.valueOf(value));
            taskMetricMap.put(name, attribute);
        } else {
            attribute.setValue(String.valueOf(value));
        }
    }
}

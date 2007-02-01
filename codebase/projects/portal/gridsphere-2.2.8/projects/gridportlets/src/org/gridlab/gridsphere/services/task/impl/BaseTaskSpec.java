package org.gridlab.gridsphere.services.task.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.task.*;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.gridlab.gridsphere.services.security.gss.CredentialUtil;
import org.gridlab.gridsphere.services.util.GridSphereUserUtil;

import java.util.*;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseTaskSpec.java,v 1.1.1.1 2007-02-01 20:41:53 kherm Exp $
 * <p>
 * Base implemenation of task specification.
 */

public class BaseTaskSpec implements TaskSpec {

    protected static PortletLog log = SportletLog.getInstance(BaseTaskSpec.class);
 
    protected String oid = null;
    protected User user = null;
    protected String userOid = null;
    protected CredentialContext credentialContext = null;
    protected String dn = null;
    protected String description = null;
    protected Map taskAttributeMap = new HashMap();
    protected transient List taskStatusListeners = null;
    protected List taskStatusListenerClassNames = new ArrayList();
    protected boolean deleteWhenTaskEndsFlag = false;

    /**
     * Default constructor.
     */
    public BaseTaskSpec() {
    }

    /**
     * Obtains values from given spec.
     */
    public BaseTaskSpec(TaskSpec spec) {
        copy((BaseTaskSpec)spec);
    }

    /**
     * Obtains values from given spec.
     */
    public BaseTaskSpec(BaseTaskSpec spec) {
        copy(spec);
    }

    public void copy(BaseTaskSpec spec) {
        setUser(spec.getUser());
        setCredentialContext(spec.getCredentialContext());
        setDescription(spec.getDescription());
        copyTaskStatusListenerClassNames(spec.getTaskStatusListenerClassNames());
        copyTaskAttributes(spec.getTaskAttributes());
        setDeleteWhenTaskEndsFlag(spec.getDeleteWhenTaskEndsFlag());
    }

    protected void copyTaskStatusListenerClassNames(List taskStatusListenerClassNameList) {
        taskStatusListenerClassNames.clear();
        for (Iterator taskStatusListenerClassNames = taskStatusListenerClassNameList.iterator(); taskStatusListenerClassNames.hasNext();) {
            String className = (String) taskStatusListenerClassNames.next();
            this.taskStatusListenerClassNames.add(className);
        }
    }

    public TaskType getTaskType() {
        return TaskType.INSTANCE;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public CredentialContext getDefaultCredentialContext() {
        if (credentialContext == null && userOid != null) {
            credentialContext = CredentialUtil.getDefaultCredentialContext(getUser());
        }
        return credentialContext;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public List getTaskStatusListenerClassNames() {
        return taskStatusListenerClassNames;
    }

    public void setTaskStatusListenerClassNames(List taskStatusListenerClassNames) {
        this.taskStatusListenerClassNames = taskStatusListenerClassNames;
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

    public Object clone() {
        BaseTaskSpec spec = new BaseTaskSpec(this);
        return spec;
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
        copyTaskAttributes(attributeList);
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
}

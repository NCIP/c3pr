package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.util.GridPortletsResourceBundle;
import org.gridlab.gridsphere.services.resource.ResourceType;
import org.gridlab.gridsphere.services.task.impl.BaseTask;

import java.util.Locale;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ResourceBundle;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: TaskType.java,v 1.1.1.1 2007-02-01 20:41:51 kherm Exp $
 * <p>
 */

public class TaskType extends ResourceType {

    public static final TaskType INSTANCE = new TaskType(Task.class, TaskSpec.class, BaseTask.class);
    private static PortletLog log = SportletLog.getInstance(TaskType.class);

    private String taskClassName = null;
    private Class taskSpecClass = null;
    private String taskSpecClassName = null;
    private HashMap superTypeClassMap = new HashMap();
    private HashMap superTaskClassMap = new HashMap();
    private HashMap superTaskSpecClassMap = new HashMap();

    /**
     * Protected so only one instance can be created.
     *
     */
    protected TaskType() {
    }

    protected TaskType(Class taskClass, Class taskSpecClass) {
        super(taskClass.getName(), taskClass);
        this.taskClassName = taskClass.getName();
        this.taskSpecClass = taskSpecClass;
        this.taskSpecClassName = taskSpecClass.getName();
    }

    protected TaskType(Class taskClass, Class taskSpecClass, Class taskClassImplementation) {
        super(taskClass.getName(), taskClass);
        setResourceImplementation(taskClassImplementation);
        this.taskClassName = taskClass.getName();
        this.taskSpecClass = taskSpecClass;
        this.taskSpecClassName = taskSpecClass.getName();
    }

    public boolean equals(TaskType type) {
        return equalsResourceType(type);
    }

    public String getClassName() {
        return getClass().getName();
    }

    public Class getTaskClass() {
        return getResourceClass();
    }

    public String getTaskClassName() {
        return taskClassName;
    }

    public Class getTaskSpecClass() {
        return taskSpecClass;
    }

    public String getTaskSpecClassName() {
        return taskSpecClassName;
    }

    public Iterator getSuperTypes() {
        return superTypeClassMap.values().iterator();
    }

    protected void addSuperType(TaskType superTaskType) {
        String superClassName = superTaskType.getClassName();
        Class superTaskClass = superTaskType.getTaskClass();
        String superTaskClassName = superTaskClass.getName();
        Class superTaskSpecClass = superTaskType.getTaskSpecClass();
        String superTaskSpecClassName = superTaskSpecClass.getName();
        // We do this in order to prevent circular dependencies...
        if (superTaskClass.isAssignableFrom(getResourceClass()) && superTaskSpecClass.isAssignableFrom(taskSpecClass)) {
            superTypeClassMap.put(superClassName, superTaskType);
            superTaskClassMap.put(superTaskClassName, superTaskType);
            superTaskSpecClassMap.put(superTaskSpecClassName, superTaskType);
            Iterator superSuperTypes = superTaskType.getSuperTypes();
            while(superSuperTypes.hasNext()) {
                TaskType superSuperType = (TaskType)superSuperTypes.next();
                addSuperType(superSuperType);
            }
        }
    }

    public boolean isTaskType(TaskType taskType) {
        String taskTypeClassName = taskType.getClassName();
        return (taskTypeClassName.equals(getClassName()) || superTypeClassMap.containsKey(taskTypeClassName));
    }

    public boolean isTaskType(Task task) {
        String taskClassName = task.getClass().getName();
        return (this.taskClassName.equals(taskClassName) || this.superTaskClassMap.containsKey(taskClassName));
    }

    public boolean isTaskType(TaskSpec taskSpec) {
        String taskSpecClassName = taskSpec.getClass().getName();
        return (this.taskSpecClassName.equals(taskSpecClassName) || this.superTaskSpecClassMap.containsKey(taskSpecClassName));
    }

    public String getName(Locale locale) {
        return getResourceString(locale, getClassName() + ".name", getClassName());
    }

    public String getLabel(Locale locale) {
        return getResourceString(locale, getClassName() + ".label", getClassName());
    }

    public String getResourceString(Locale locale, String key, String defaultValue) {
        String value = null;
        try {
            ResourceBundle bundle = getResourceBundle(locale);
            value = bundle.getString(key);
            if (value == null) {
                value = defaultValue;
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return value;
    }

    public ResourceBundle getResourceBundle(Locale locale) {
        return GridPortletsResourceBundle.getResourceBundle(locale);
    }
}

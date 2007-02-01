package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.services.util.GridPortletsResourceBundle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: TaskStatus.java,v 1.1.1.1 2007-02-01 20:41:50 kherm Exp $
 * <p>
 * Describes a task status.
 */

public class TaskStatus {

    private static HashMap constants = new HashMap(14);

    public static final TaskStatus NEW = new TaskStatus(0, "new");
    public static final TaskStatus SUBMITTING = new TaskStatus(1, "submitting");
    public static final TaskStatus SUBMITTED = new TaskStatus(2, "submitted");
    public static final TaskStatus QUEUED = new TaskStatus(3, "queued");
    public static final TaskStatus PREPROCESS = new TaskStatus(4, "preprocess");
    public static final TaskStatus PENDING = new TaskStatus(5, "pending");
    public static final TaskStatus ACTIVE = new TaskStatus(6, "active");
    public static final TaskStatus SUSPENDED = new TaskStatus(7, "suspended");
    public static final TaskStatus POSTPROCESS = new TaskStatus(8, "postprocess");
    public static final TaskStatus COMPLETED = new TaskStatus(9, "completed");
    public static final TaskStatus CANCELING = new TaskStatus(10, "canceling");
    public static final TaskStatus CANCELED = new TaskStatus(11, "canceled");
    public static final TaskStatus FAILED = new TaskStatus(12, "failed");
    public static final TaskStatus UNKNOWN = new TaskStatus(-1, "unknown");

    protected int value = -1;
    protected String name = null;

    protected TaskStatus() {}

    protected TaskStatus(int value, String name) {
        this.value = value;
        this.name = name;
        constants.put(name, this);
    }

    public boolean equals(TaskStatus status) {
        return (this.value == status.value);
    }

    public boolean equals(String name) {
        return (this.name.equals(name));
    }

    public String getName() {
        return name;
    }

    public String getName(Locale locale) {
        String className = getClass().getName();
        return GridPortletsResourceBundle.getResourceString(locale, className + '.' + name, className);
    }

    public static Iterator iterateConstants() {
        return constants.values().iterator();
    }

    public static TaskStatus toTaskStatus(String name) {
        TaskStatus constant =
                (TaskStatus) constants.get(name);
        if (constant == null) {
            return UNKNOWN;
        }
        return constant;
    }

    public boolean isLive() {
        return (! (value == NEW.value ||
                   value == FAILED.value ||
                   value == COMPLETED.value ||
                   value == CANCELED.value ||
                   value == UNKNOWN.value) );
    }
}

package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.services.util.StringAttribute;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: TaskAttribute.java,v 1.1.1.1 2007-02-01 20:41:50 kherm Exp $
 * <p>
 * Describes a metric for a task, such as the rate of data
 * transmission during a file transfer task.
 */

public class TaskAttribute extends StringAttribute {

    public TaskAttribute() {
    }

    public TaskAttribute(TaskAttribute variable) {
        super(variable);
    }

    public TaskAttribute(String nameEqualsValue) {
        super(nameEqualsValue);
    }

    public TaskAttribute(String name, String value) {
        super(name, value);
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass().equals(TaskAttribute.class)) {
            TaskAttribute attribute = (TaskAttribute)o;
            return getNameEqualsValue().equals(attribute.getNameEqualsValue());
        }
        return false;
    }
}

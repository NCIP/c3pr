package org.gridlab.gridsphere.services.task.impl;

import org.gridlab.gridsphere.services.util.impl.StringAttributeUserType;
import org.gridlab.gridsphere.services.util.StringAttribute;
import org.gridlab.gridsphere.services.task.TaskMetric;
import org.gridlab.gridsphere.services.task.TaskAttribute;

/**
 * To change this template use File | Settings | File Templates.
 */
public class TaskAttributeUserType extends StringAttributeUserType {

    public Class returnedClass() {
        return TaskAttribute.class;
    }

    public Object deepCopy(Object value) {
        if (value == null) return null;
        return new TaskAttribute((TaskAttribute)value);
    }

    protected StringAttribute createStringAttribute(String nameValuePair) {
        return  new TaskAttribute(nameValuePair);
    }
}

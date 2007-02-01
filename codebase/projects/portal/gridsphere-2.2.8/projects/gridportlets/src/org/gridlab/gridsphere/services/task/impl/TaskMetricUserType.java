package org.gridlab.gridsphere.services.task.impl;

import org.gridlab.gridsphere.services.util.impl.StringAttributeUserType;
import org.gridlab.gridsphere.services.util.StringAttribute;
import org.gridlab.gridsphere.services.task.TaskMetric;

/**
 * To change this template use File | Settings | File Templates.
 */
public class TaskMetricUserType extends StringAttributeUserType {

    public Class returnedClass() {
        return TaskMetric.class;
    }

    public Object deepCopy(Object value) {
        if (value == null) return null;
        return new TaskMetric((TaskMetric)value);
    }

    protected StringAttribute createStringAttribute(String nameValuePair) {
        return  new TaskMetric(nameValuePair);
    }
}

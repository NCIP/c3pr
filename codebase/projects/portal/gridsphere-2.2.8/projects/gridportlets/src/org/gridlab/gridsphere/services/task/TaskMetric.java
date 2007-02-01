package org.gridlab.gridsphere.services.task;

import org.gridlab.gridsphere.services.util.StringAttribute;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: TaskMetric.java,v 1.1.1.1 2007-02-01 20:41:50 kherm Exp $
 * <p>
 * Describes a metric for a task, such as the rate of data
 * transmission during a file transfer task.
 */

public class TaskMetric extends StringAttribute {

    public TaskMetric() {
    }

    public TaskMetric(TaskMetric variable) {
        super(variable);
    }

    public TaskMetric(String nameEqualsValue) {
        super(nameEqualsValue);
    }

    public TaskMetric(String name, String value) {
        super(name, value);
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass().equals(TaskMetric.class)) {
            TaskMetric metric = (TaskMetric)o;
            return getNameEqualsValue().equals(metric.getNameEqualsValue());
        }
        return false;
    }
}

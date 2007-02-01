/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: EnvironmentVariable.java,v 1.1.1.1 2007-02-01 20:40:39 kherm Exp $
 */
package org.gridlab.gridsphere.services.job;

import org.gridlab.gridsphere.services.util.StringAttribute;

/**
 * Represents an environment variable.
 */
public class EnvironmentVariable extends StringAttribute {

    public EnvironmentVariable() {
    }

    public EnvironmentVariable(EnvironmentVariable variable) {
        super(variable);
    }

    public EnvironmentVariable(String nameEqualsValue) {
        super(nameEqualsValue);
    }

    public EnvironmentVariable(String name, String value) {
        super(name, value);
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass().equals(EnvironmentVariable.class)) {
            EnvironmentVariable variable = (EnvironmentVariable)o;
            return getNameEqualsValue().equals(variable.getNameEqualsValue());
        }
        return false;
    }
}

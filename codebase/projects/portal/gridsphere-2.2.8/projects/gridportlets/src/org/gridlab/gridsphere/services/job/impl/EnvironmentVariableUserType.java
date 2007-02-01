package org.gridlab.gridsphere.services.job.impl;

import net.sf.hibernate.UserType;
import net.sf.hibernate.HibernateException;
import org.gridlab.gridsphere.services.job.EnvironmentVariable;
import org.gridlab.gridsphere.services.util.StringAttribute;
import org.gridlab.gridsphere.services.util.impl.StringAttributeUserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;

/**
 * To change this template use File | Settings | File Templates.
 */
public class EnvironmentVariableUserType extends StringAttributeUserType {

    public Class returnedClass() {
        return EnvironmentVariable.class;
    }

    public Object deepCopy(Object value) {
        if (value == null) return null;
        return new EnvironmentVariable((EnvironmentVariable)value);
    }

    protected StringAttribute createStringAttribute(String nameValuePair) {
        return  new EnvironmentVariable(nameValuePair);
    }
}

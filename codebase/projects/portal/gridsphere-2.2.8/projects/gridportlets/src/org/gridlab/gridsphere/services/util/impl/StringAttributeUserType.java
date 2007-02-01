package org.gridlab.gridsphere.services.util.impl;

import net.sf.hibernate.UserType;
import net.sf.hibernate.HibernateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.io.Serializable;

import org.gridlab.gridsphere.services.util.StringAttribute;

/**
 * To change this template use File | Settings | File Templates.
 */
public abstract class StringAttributeUserType implements UserType {

    private static final int[] SQL_TYPES = {Types.VARCHAR};

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public abstract Class returnedClass();

    public boolean equals(Object x, Object y) {
        if (x == y) return true;
        if (x == null || y == null) return false;
        return x.equals(y);
    }

    public abstract Object deepCopy(Object value);

    public boolean isMutable() {
        return true;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
            throws HibernateException, SQLException {

        //if (resultSet.wasNull()) return null;

        String dbValue = resultSet.getString(names[0]);
        if (dbValue == null) return null;

        return createStringAttribute(dbValue);
    }

    protected abstract StringAttribute createStringAttribute(String nameValuePair);

    public void nullSafeSet(PreparedStatement statement, Object value, int index)
            throws HibernateException, SQLException {
        if (value == null) {
            statement.setNull(index, Types.VARCHAR);
        } else {
            // Get values
            StringAttribute attribute = (StringAttribute)value;
            String nameValuePair = attribute.getNameEqualsValue();
            // Set columns
            if (nameValuePair == null) {
                statement.setNull(index, Types.VARCHAR);
            } else {
                statement.setString(index, nameValuePair);
            }
        }
    }


    public Object assemble(Serializable arg0, Object arg1) throws
 HibernateException {
                 return deepCopy(arg0);
         }

         public Serializable disassemble(Object value) {
                 return (Serializable) deepCopy(value);
         }

    /* (non-Javadoc)
     *  (at) see org (dot) hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    public int hashCode(Object arg0) throws HibernateException {
        return arg0.hashCode();
    }

    /* (non-Javadoc)
     *  (at) see org (dot) hibernate.usertype.UserType#replace(java.lang.Object,
java.lang.Object, java.lang.Object)
     */
    public Object replace(Object arg0, Object arg1, Object arg2) throws
HibernateException {
        return deepCopy(arg0);
    }
}

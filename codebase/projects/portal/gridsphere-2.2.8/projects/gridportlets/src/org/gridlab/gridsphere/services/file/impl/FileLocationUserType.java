package org.gridlab.gridsphere.services.file.impl;

import net.sf.hibernate.UserType;
import net.sf.hibernate.HibernateException;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.io.Serializable;

/**
 * To change this template use File | Settings | File Templates.
 */
public class FileLocationUserType implements UserType {

    private static PortletLog log = SportletLog.getInstance(FileLocationUserType.class);

    private static final int[] SQL_TYPES = {Types.VARCHAR};

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return FileLocation.class;
    }

    public boolean equals(Object x, Object y) {
        if (x == y) return true;
        if (x == null || y == null) return false;
        return x.equals(y);
    }

    public Object deepCopy(Object value) {
        if (value == null) {
            return null;
        }
        return new FileLocation((FileLocation)value);
    }

    public boolean isMutable() {
        return true;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
            throws HibernateException, SQLException {
        //if (resultSet.wasNull()) return null;

        //log.debug("Invoking file location null safe get");

        String dbValue = resultSet.getString(names[0]);
        if (dbValue == null) {
            //log.debug("File location db value is null");
            return null;
        }

        FileLocation fileLocation = new FileLocation();
        fileLocation.setUrlString(dbValue);

        return fileLocation;
    }

    public void nullSafeSet(PreparedStatement statement, Object value, int index)
            throws HibernateException, SQLException {

        //log.debug("Invoking file location null safe set");

        if (value == null) {

            log.debug("Setting file location db value to null");

            statement.setNull(index, Types.VARCHAR);
        } else {

            // Get values
            FileLocation fileLocation = (FileLocation)value;
            String dbValue = fileLocation.getUrl();

            //log.debug("Setting file location db value " + dbValue);

            // Set columns
            if (dbValue == null || dbValue.equals("")) {
                statement.setNull(index, Types.VARCHAR);
            } else {
                statement.setString(index, dbValue);
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

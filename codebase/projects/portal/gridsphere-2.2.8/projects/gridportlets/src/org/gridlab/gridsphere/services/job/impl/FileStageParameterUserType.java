package org.gridlab.gridsphere.services.job.impl;

import net.sf.hibernate.UserType;
import net.sf.hibernate.HibernateException;
import org.gridlab.gridsphere.services.job.FileStageParameter;
import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.io.Serializable;

/**
 * To change this template use File | Settings | File Templates.
 */
public class FileStageParameterUserType implements UserType {

    private PortletLog log = SportletLog.getInstance(FileStageParameterUserType.class);

    private static final int[] SQL_TYPES = {Types.VARCHAR};

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return FileStageParameter.class;
    }

    public boolean equals(Object x, Object y) {
        if (x == y) return true;
        if (x == null || y == null) return false;
        return x.equals(y);
    }

    public Object deepCopy(Object value) {
        if (value == null) return null;
        return new FileStageParameter((FileStageParameter)value);
    }

    public boolean isMutable() {
        return true;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
            throws HibernateException, SQLException {

        //if (resultSet.wasNull()) return null;

//        log.debug("Invoking file stage parameter null safe get");

        String dbValue = resultSet.getString(names[0]);
        if (dbValue == null) {

//            log.debug("File stage parameter db value is null");

            return null;
        }

        FileLocation fileLocation = new FileLocation();
        fileLocation.setUrlString(dbValue);
        return new FileStageParameter(fileLocation);
    }

    public void nullSafeSet(PreparedStatement statement, Object value, int index)
            throws HibernateException, SQLException {

//        log.debug("Invoking file stage parameter null safe set");

        if (value == null) {
//            log.debug("Setting file stage parameter db value to null");
            statement.setNull(index, Types.VARCHAR);
        } else {

            // Get values
            FileStageParameter fileStageParameter = (FileStageParameter)value;
            String dbValue = fileStageParameter.getFileStageUrl();
            // Set columns
            if (dbValue == null || dbValue.equals("")) {
                statement.setNull(index, Types.VARCHAR);
            } else {
                statement.setString(index, dbValue);
            }
        }
    }


    public Object assemble(Serializable arg0, Object arg1)
            throws HibernateException {
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

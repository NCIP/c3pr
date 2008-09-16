package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:25:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class RegistrationDataEntryStatusFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        StudySubject registration = (StudySubject) object;
        return registration.getRegDataEntryStatus().toString();
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        registration.setRegDataEntryStatus(RegistrationDataEntryStatus.valueOf((String) value));

    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        registration.setRegDataEntryStatus(null);

    }

    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {
        // To change body of implemented methods use File | Settings | File Templates.
    }

    public Object newInstance(Object object) throws IllegalStateException {
        return null; // To change body of implemented methods use File | Settings | File
                        // Templates.
    }
}

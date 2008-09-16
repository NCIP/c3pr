package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class StudyDataEntryStatusFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        Study study = (Study) object;
        return study.getDataEntryStatus().toString();
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
        Study study = (Study) object;
        study.setDataEntryStatus(StudyDataEntryStatus.valueOf((String) value));

    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        Study study = (Study) object;
        study.setDataEntryStatus(null);
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

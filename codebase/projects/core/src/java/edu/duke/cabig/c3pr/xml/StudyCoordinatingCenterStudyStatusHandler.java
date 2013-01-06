package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:35:33 AM To change this
 * template use File | Settings | File Templates.
 */
public class StudyCoordinatingCenterStudyStatusHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        Study study = (Study) object;
        try {
            return study.getCoordinatingCenterStudyStatus().toString();
        }
        catch (Exception e) {
            return "";
        }
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
        Study study = (Study) object;
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus
                        .valueOf((String) value));
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        Study study = (Study) object;
        study.setCoordinatingCenterStudyStatus(null);
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

package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.constants.StudyPart;
import edu.duke.cabig.c3pr.domain.AmendmentReason;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyVersion;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class StudyPartFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        AmendmentReason amendmentReason = (AmendmentReason) object;
        return amendmentReason.getStudyPart().toString();
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	AmendmentReason amendmentReason = (AmendmentReason) object;
//        study.setDataEntryStatus(StudyDataEntryStatus.valueOf((String) value));
        amendmentReason.setStudyPart(StudyPart.valueOf((String) value));

    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
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

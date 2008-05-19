package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Mar 19, 2007 Time: 12:21:07 AM To change this
 * template use File | Settings | File Templates.
 */
public class ScEpochWorkFlowStatusFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        return ((ScheduledEpoch)object).getScEpochWorkflowStatus().toString();
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
        ((ScheduledEpoch)object).setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.valueOf((String) value));
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        ((ScheduledEpoch)object).setScEpochWorkflowStatus(null);
    }

    public void checkValidity(Object arg0) throws ValidityException, IllegalStateException {
        // TODO Auto-generated method stub
        
    }

    public Object newInstance(Object arg0) throws IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

}

package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 19, 2007
 * Time: 12:21:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationWorkFlowStatusFieldHandler implements FieldHandler {


    public Object getValue(Object object) throws IllegalStateException {
        StudySubject registration = (StudySubject) object;
        return registration.getRegWorkflowStatus().toString();
    }

    public void setValue(Object object, Object value) throws IllegalStateException, IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        registration.setRegWorkflowStatus(RegistrationWorkFlowStatus.getByCode((String)value));
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        registration.setRegWorkflowStatus(null);
    }

    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object newInstance(Object object) throws IllegalStateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

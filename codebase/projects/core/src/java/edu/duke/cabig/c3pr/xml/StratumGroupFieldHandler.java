package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.domain.StudySubject;
import org.apache.log4j.Logger;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 19, 2007
 * Time: 3:29:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class StratumGroupFieldHandler implements FieldHandler {

    Logger log = Logger.getLogger(StratumGroupFieldHandler.class);

    public Object getValue(Object object) throws IllegalStateException {
        StudySubject registration = (StudySubject)object;
        try {
            return registration.getStratumGroup().toString();
        } catch (Exception e) {
            log.warn(e);
        }
        return null;
    }

    public void setValue(Object object, Object object1) throws IllegalStateException, IllegalArgumentException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        //To change body of implemented methods use File | Settings | File Templates.
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

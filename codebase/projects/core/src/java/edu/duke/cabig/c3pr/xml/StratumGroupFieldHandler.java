package edu.duke.cabig.c3pr.xml;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 19, 2007 Time: 3:29:25 PM To change this template
 * use File | Settings | File Templates.
 */
public class StratumGroupFieldHandler implements FieldHandler {

    Logger log = Logger.getLogger(StratumGroupFieldHandler.class);

    public Object getValue(Object object) throws IllegalStateException {
        StudySubject registration = (StudySubject) object;
        try {
            return registration.getStratumGroup().toString();
        }
        catch (Exception e) {
            log.warn(e);
            log.debug("returning stratum group number..");
            if(registration.getStratumGroupNumber()==null){
                log.debug("no startum group number found..");
                return null;
            }
            return registration.getStratumGroupNumber();
        }
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
        StudySubject registration = (StudySubject) object;
        String s = (String) value;
        int i = Integer.parseInt(s.split(":")[0].trim());
        registration.setStratumGroupNumber(i);

    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
        // To change body of implemented methods use File | Settings | File Templates.
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

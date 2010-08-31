package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class RaceCodeFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
    	RaceCodeAssociation raceCodeAssociation = (RaceCodeAssociation) object;
        try {
            return raceCodeAssociation.getRaceCode().getName().toString();
        }
        catch (Exception e) {
            return "";
        }
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	if(value==null) return;
    	RaceCodeAssociation raceCodeAssociation = (RaceCodeAssociation) object;
    	raceCodeAssociation.setRaceCode(RaceCodeEnum.valueOf((String) value));
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
    	RaceCodeAssociation raceCodeAssociation = (RaceCodeAssociation) object;
    	raceCodeAssociation.setRaceCode(null);
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

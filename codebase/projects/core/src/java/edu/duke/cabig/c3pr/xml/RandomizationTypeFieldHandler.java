package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class RandomizationTypeFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        if (object instanceof Study) {
        	Study study = (Study) object;
            return study.getRandomizationType()==null?null:study.getRandomizationType().getCode();
		}else if (object instanceof RandomizationHolder) {
			RandomizationHolder randomizationHolder = (RandomizationHolder) object;
            return randomizationHolder.getRandomizationType()==null?null:randomizationHolder.getRandomizationType().getCode();
		}
    	return null;
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	if(value==null) return;
    	if (object instanceof Study) {
        	Study study = (Study) object;
        	study.setRandomizationType(RandomizationType.getByCode((Integer) value));
		}else if (object instanceof RandomizationHolder) {
			RandomizationHolder randomizationHolder = (RandomizationHolder) object;
			randomizationHolder.setRandomizationType(RandomizationType.getByCode((Integer) value));
		}
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
    	if (object instanceof Study) {
        	Study study = (Study) object;
        	study.setRandomizationType(null);
		}else if (object instanceof RandomizationHolder) {
			RandomizationHolder randomizationHolder = (RandomizationHolder) object;
			randomizationHolder.setRandomizationType(null);
		}
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

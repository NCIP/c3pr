package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.CalloutRandomization;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.Study;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class RandomizationFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        Epoch epoch = (Epoch) object;
        RandomizationHolder randomizationHolder= new RandomizationHolder();
        if (epoch.getRandomization() !=null)
        	return null;
        if (epoch.getRandomization() instanceof PhoneCallRandomization) {
			PhoneCallRandomization phoneCallRandomization = (PhoneCallRandomization) epoch.getRandomization();
			randomizationHolder.setRandomizationType(RandomizationType.PHONE_CALL);
			randomizationHolder.setPhoneNumber(phoneCallRandomization.getPhoneNumber());
		}else if (epoch.getRandomization() instanceof BookRandomization) {
			randomizationHolder.setRandomizationType(RandomizationType.BOOK);
		}else if (epoch.getRandomization() instanceof CalloutRandomization) {
			randomizationHolder.setRandomizationType(RandomizationType.CALL_OUT);
		}
        return randomizationHolder;
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	if(value==null)
    		return;
    	Epoch epoch = (Epoch) object;
        RandomizationHolder randomizationHolder= (RandomizationHolder)value;
        if(randomizationHolder.getRandomizationType()==RandomizationType.PHONE_CALL){
        	PhoneCallRandomization phoneCallRandomization= new PhoneCallRandomization();
        	phoneCallRandomization.setPhoneNumber(randomizationHolder.getPhoneNumber());
        	epoch.setRandomization(phoneCallRandomization);
        }else if(randomizationHolder.getRandomizationType()==RandomizationType.BOOK){
        	epoch.setRandomization(new BookRandomization());
        }else if(randomizationHolder.getRandomizationType()==RandomizationType.CALL_OUT){
        	epoch.setRandomization(new CalloutRandomization());
        } 
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
    	Epoch epoch = (Epoch) object;
        epoch.setRandomization(null);
    }

    /**
     * @deprecated
     */
    public void checkValidity(Object object) throws ValidityException, IllegalStateException {
        // To change body of implemented methods use File | Settings | File Templates.
    }

    public Object newInstance(Object object) throws IllegalStateException {
        return new RandomizationHolder();
    }
}

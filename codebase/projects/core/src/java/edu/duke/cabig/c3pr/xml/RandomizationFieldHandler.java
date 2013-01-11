/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.PhoneCallRandomization;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:31:43 AM To change this
 * template use File | Settings | File Templates.
 */
public class RandomizationFieldHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
        Epoch epoch = (Epoch) object;
        if (epoch.getRandomization() ==null) return null;
        RandomizationHolder randomizationHolder= new RandomizationHolder();
        if (epoch.getRandomization() instanceof PhoneCallRandomization) {
			PhoneCallRandomization phoneCallRandomization = (PhoneCallRandomization) epoch.getRandomization();
			randomizationHolder.setRandomizationType(RandomizationType.PHONE_CALL);
			randomizationHolder.setPhoneNumber(phoneCallRandomization.getPhoneNumber());
		}else if (epoch.getRandomization() instanceof BookRandomization) {
			randomizationHolder.setRandomizationType(RandomizationType.BOOK);
		}
        return randomizationHolder;
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	if(value==null) return;
        RandomizationHolder randomizationHolder= (RandomizationHolder)value;
        if(randomizationHolder.getRandomizationType()==null) return;
        Epoch epoch = (Epoch) object;
        if(randomizationHolder.getRandomizationType()==RandomizationType.PHONE_CALL){
        	PhoneCallRandomization phoneCallRandomization= new PhoneCallRandomization();
        	phoneCallRandomization.setPhoneNumber(randomizationHolder.getPhoneNumber());
        	epoch.setRandomization(phoneCallRandomization);
        }else if(randomizationHolder.getRandomizationType()==RandomizationType.BOOK){
        	epoch.setRandomization(new BookRandomization());
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

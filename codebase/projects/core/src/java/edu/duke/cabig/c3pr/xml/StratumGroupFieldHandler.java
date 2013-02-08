/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.domain.ScheduledEpoch;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 19, 2007 Time: 3:29:25 PM To change this template
 * use File | Settings | File Templates.
 */
public class StratumGroupFieldHandler implements FieldHandler {

    Logger log = Logger.getLogger(StratumGroupFieldHandler.class);

    public Object getValue(Object object) throws IllegalStateException {
        ScheduledEpoch scheduledEpoch = (ScheduledEpoch) object;
        Integer stratumGroupNumber = scheduledEpoch.getStratumGroupNumber();
        if(stratumGroupNumber != null && !stratumGroupNumber.toString().equals("")){
        	return stratumGroupNumber.toString();
        }
    	try {    
    		return scheduledEpoch.getStratumGroup().toString();
    	}catch (Exception e) {
    		log.warn("Cannot get stratum group number. Exception thrown while computing stratuum group number.");
    		log.warn(e);
            return null;
        }
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	if(value==null){
    		log.warn("Cannot set stratum group number to null.");
    		return;
    	}
    	ScheduledEpoch scheduledEpoch = (ScheduledEpoch) object;
        String s = (String) value;
        int i = Integer.parseInt(s.split(":")[0].trim());
        scheduledEpoch.setStratumGroupNumber(i);

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

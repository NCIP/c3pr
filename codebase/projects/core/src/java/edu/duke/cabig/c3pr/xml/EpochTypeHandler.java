/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.domain.Epoch;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 25, 2007 Time: 10:35:33 AM To change this
 * template use File | Settings | File Templates.
 */
public class EpochTypeHandler implements FieldHandler {

    public Object getValue(Object object) throws IllegalStateException {
    	return ((Epoch)object).getType().toString();
    }

    public void setValue(Object object, Object value) throws IllegalStateException,
                    IllegalArgumentException {
    	((Epoch)object).setType(EpochType.valueOf((String)value));
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

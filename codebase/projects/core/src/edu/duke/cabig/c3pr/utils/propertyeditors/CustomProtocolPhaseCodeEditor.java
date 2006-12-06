/**
 * 
 */
package edu.duke.cabig.c3pr.utils.propertyeditors;

import java.beans.PropertyEditorSupport;

import edu.duke.cabig.c3pr.domain.ProtocolPhase;

/**
 * @author Priyatam
 *
 */
public class CustomProtocolPhaseCodeEditor extends PropertyEditorSupport{

	/**
	 * Parse ProtocolPhase from the given text
	 */
	 public void setAsText(String text) throws IllegalArgumentException {
		 ProtocolPhase newValue = new ProtocolPhase();
		 newValue.setCode(text);
	     setValue(newValue);
	    }

	/**
	 * 
	 */ 
    public String getAsText() {
        if (getValue() == null) {
            return null;
        } else {
        	ProtocolPhase value = (ProtocolPhase) getValue();
            return value.getId();
        }
    }
}

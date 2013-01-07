package edu.duke.cabig.c3pr.xml;

import java.util.ArrayList;
import java.util.List;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.Epoch;

/**
 * @author Denis G. Krylov
 * 
 */
public class ConsentingMethodHandler implements FieldHandler {

	public Object getValue(Object object) throws IllegalStateException {
		Consent consent = ((Consent) object);
		List<String> list = new ArrayList<String>();
		for (ConsentingMethod method : consent.getConsentingMethods()) {
			list.add(method.name());
		}
		return list;
	}

	public void setValue(Object object, Object value)
			throws IllegalStateException, IllegalArgumentException {
		Consent consent = ((Consent) object);
		final ConsentingMethod method = ConsentingMethod.valueOf(value
				.toString());
		if (!consent.getConsentingMethods().contains(method)) {
			consent.addConsentingMethod(method);
		}
	}

	public void resetValue(Object object) throws IllegalStateException,
			IllegalArgumentException {

	}

	/**
	 * @deprecated
	 */
	public void checkValidity(Object object) throws ValidityException,
			IllegalStateException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public Object newInstance(Object object) throws IllegalStateException {
		return null; // To change body of implemented methods use File |
						// Settings | File
						// Templates.
	}
}

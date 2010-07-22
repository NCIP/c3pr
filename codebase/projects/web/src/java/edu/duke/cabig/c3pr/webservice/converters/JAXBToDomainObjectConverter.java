package edu.duke.cabig.c3pr.webservice.converters;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject;

/**
 * Provides operations to convert JAXB objects, such as {@link Subject}, created during a Web service invocation into
 * C3PR domain objects, such as {@link Participant}.
 * @author dkrylov
 *
 */
public interface JAXBToDomainObjectConverter {
	
	
	/**
	 * Convert {@link Subject} to {@link Participant}.
	 * @param subject
	 * @return
	 * @throws ConversionException
	 */
	Participant convert(Subject subject) throws ConversionException;

}

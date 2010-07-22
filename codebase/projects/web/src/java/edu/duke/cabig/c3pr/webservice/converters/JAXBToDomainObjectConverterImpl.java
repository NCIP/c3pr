package edu.duke.cabig.c3pr.webservice.converters;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Person;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;

/**
 * Default implementation of {@link JAXBToDomainObjectConverter}.
 * 
 * @author dkrylov
 * 
 */
public class JAXBToDomainObjectConverterImpl implements
		JAXBToDomainObjectConverter {

	public static final int NO_SUBJECT_DATA_PROVIDED_CODE = 900;
	private static final int INVALID_SUBJECT_DATA_REPRESENTATION = 901;
	private static final int MISSING_SUBJECT_IDENTIFIER = 902;
	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	private static Log log = LogFactory
			.getLog(JAXBToDomainObjectConverterImpl.class);

	public C3PRExceptionHelper getExceptionHelper() {
		return exceptionHelper;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectCoverter#
	 * convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject)
	 */
	public Participant convert(Subject subject) throws ConversionException {
		if (subject != null && subject.getEntity() != null) {
			try {
				Participant participant = new Participant();
				// the following cast is reasonably safe: there is only on
				// subclass
				// of BiologicalEntity.
				Person person = (Person) subject.getEntity();
				Cd gender = CDTransformer.INSTANCE.toDto(person
						.getAdministrativeGenderCode());
				if (gender != null) {
					participant.setAdministrativeGenderCode(gender.getCode());
				}
				List<BiologicEntityIdentifier> identifiers = person
						.getBiologicEntityIdentifier();
				if (CollectionUtils.isNotEmpty(identifiers)) {
					processIdentifiers(identifiers, participant);
				} else {
					throw exceptionHelper
							.getConversionException(MISSING_SUBJECT_IDENTIFIER);
				}
				return participant;
			} catch (DtoTransformException e) {
				log.error(e.getMessage(), e);
				throw exceptionHelper
						.getConversionException(INVALID_SUBJECT_DATA_REPRESENTATION);
			}
		} else {
			throw exceptionHelper
					.getConversionException(NO_SUBJECT_DATA_PROVIDED_CODE);
		}
	}

	private void processIdentifiers(List<BiologicEntityIdentifier> identifiers,
			Participant participant) {		
		for (BiologicEntityIdentifier bioId : identifiers) {
			Identifier id = new OrganizationAssignedIdentifier();
		}
	}

}

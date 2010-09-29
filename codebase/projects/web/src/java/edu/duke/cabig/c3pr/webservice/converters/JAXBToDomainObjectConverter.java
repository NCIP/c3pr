package edu.duke.cabig.c3pr.webservice.converters;

import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject;

/**
 * Provides operations to convert JAXB objects, such as {@link Subject}, created during a Web service invocation into
 * C3PR domain objects, such as {@link Participant}.
 * @author dkrylov
 *
 */
public interface JAXBToDomainObjectConverter {
	
	
	/**
	 * Convert {@link Subject} to a new instance of {@link Participant}.
	 * @param subject
	 * @return
	 * @throws ConversionException
	 */
	Participant convert(Subject subject, boolean requireIdentifier) throws ConversionException;

	/**
	 * Updates the given instance of {@link Participant} with new values. Does not modify identifiers. 
	 * @param participant
	 * @param subject
	 */
	void convert(Participant participant, Subject subject);

	/**
	 * Converts {@link BiologicEntityIdentifier} into {@link OrganizationAssignedIdentifier}, enforces validation checks. 
	 * @param bioId
	 * @return
	 * @throws ConversionException
	 */
	OrganizationAssignedIdentifier convert(BiologicEntityIdentifier bioId) throws ConversionException;

	/**
	 * Converts {@link Participant} into its XML {@link Subject} representation.
	 * @param p
	 * @return
	 */
	Subject convert(Participant p);

	/**
	 * @param param
	 * @return
	 */
	AdvancedSearchCriteriaParameter convert(
			AdvanceSearchCriterionParameter param);

	List<OrganizationAssignedIdentifier> convert(List<StudyIdentifier> xmlIds);
	
	OrganizationAssignedIdentifier convert(StudyIdentifier studyIdentifier);

	Study convert(edu.duke.cabig.c3pr.webservice.studyutility.Study xmlStudy); 

}

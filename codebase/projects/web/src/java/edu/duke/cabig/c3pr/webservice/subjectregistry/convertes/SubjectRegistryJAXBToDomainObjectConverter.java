package edu.duke.cabig.c3pr.webservice.subjectregistry.convertes;

import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.subjectregistry.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.subjectregistry.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectregistry.Organization;
import edu.duke.cabig.c3pr.webservice.subjectregistry.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.subjectregistry.Person;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.subjectregistry.Subject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectIdentifier;

/**
 * Provides operations to convert JAXB objects, such as {@link Subject}, created during a Web service invocation into
 * C3PR domain objects, such as {@link Participant}.
 * @author dkrylov
 *
 */
public interface SubjectRegistryJAXBToDomainObjectConverter{
	
	
	/**
	 * Converts {@link BiologicEntityIdentifier} into {@link Identifier}, enforces validation checks.
	 * 
	 * @param biologicIdentifiers the biologic identifiers
	 * 
	 * @return the list< identifier>
	 */
	List<Identifier> convertBiologicIdentifiers(List<BiologicEntityIdentifier> biologicIdentifiers);
	
	/**
	 * Converts {@link SubjectIdentifier} into {@link Identifier}, enforces validation checks.
	 * 
	 * @param subjectIdentifiers the subject identifiers
	 * 
	 * @return the list< identifier>
	 */
	List<Identifier> convertSubjectIdentifiers(List<SubjectIdentifier> subjectIdentifiers);
	
	List<Identifier> convertDocumentIdentifiers(List<DocumentIdentifier> documentIdentifiers);
	
	String convertHealthcareSitePrimaryIdentifier(Organization org);
	
	List<StudySubjectConsentVersion> convertSubjectConsent(StudySubjectProtocolVersionRelationship studySubjectProtocolVersionRelationship);
	
	StudySubject convert(edu.duke.cabig.c3pr.domain.StudySubject domainObject);
	
	StudySubjectRegistryStatus convertRegistryStatus(PerformedStudySubjectMilestone status);
	
	Person convertSubjectDemographics(StudySubjectDemographics studySubjectDemographics);
	
	/**
	 * Updates the given instance of {@link StudySubjectDemographics} with new values. Does not modify identifiers. 
	 * @param participant
	 * @param subject
	 */
	void convertToSubjectDemographics(StudySubjectDemographics destination, Subject source);
	/**
	 * @param param
	 * @return
	 */
	AdvancedSearchCriteriaParameter convert(
			AdvanceSearchCriterionParameter param);
	
}

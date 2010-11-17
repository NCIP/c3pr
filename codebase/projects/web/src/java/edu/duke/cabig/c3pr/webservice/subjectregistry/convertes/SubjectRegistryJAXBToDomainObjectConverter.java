package edu.duke.cabig.c3pr.webservice.subjectregistry.convertes;

import java.util.Date;
import java.util.List;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;

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
	
	String convertHealthcareSitePrimaryIdentifier(OrganizationIdentifier orgId);
	
	List<StudySubjectConsentVersion> convertSubjectConsent(List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> subjectConsents);
	
	StudySubject convert(edu.duke.cabig.c3pr.domain.StudySubject domainObject);
	
	StudySubjectRegistryStatus convertRegistryStatus(PerformedStudySubjectMilestone status);
	
	Person convertSubjectDemographics(StudySubjectDemographics studySubjectDemographics);
	
	List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> convertToSubjectConsent(List<StudySubjectConsentVersion> studySubjectConsentVersions);
	
	edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion convertToSubjectConsent(StudySubjectConsentVersion studySubjectConsentVersion);
	
	List<PerformedStudySubjectMilestone> convertToRegistryStatus(List<StudySubjectRegistryStatus> statuses);
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
	
	Date convertToDate(TSDateTime tsDateTime);
	
}

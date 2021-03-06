/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectregistry.converters;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;

/**
 * Provides operations to convert JAXB objects, such as {@link Subject}, created during a Web service invocation into
 * C3PR domain objects, such as {@link Participant}.
 * @author dkrylov
 *
 */
public interface SubjectRegistryJAXBToDomainObjectConverter extends JAXBToDomainObjectConverter{
	
	
	/**
	 * Converts {@link SubjectIdentifier} into {@link Identifier}, enforces validation checks.
	 * 
	 * @param subjectIdentifiers the subject identifiers
	 * 
	 * @return the list< identifier>
	 */
	List<Identifier> convertSubjectIdentifiers(List<SubjectIdentifier> subjectIdentifiers);
	
	/**
	 * Convert healthcare site primary identifier.
	 *
	 * @param org the org
	 * @return the string
	 */
	String convertHealthcareSitePrimaryIdentifier(Organization org);
	
	/**
	 * Convert healthcare site primary identifier.
	 *
	 * @param orgId the org id
	 * @return the string
	 */
	String convertHealthcareSitePrimaryIdentifier(OrganizationIdentifier orgId);
	
	/**
	 * Convert subject consent.
	 *
	 * @param subjectConsents the subject consents
	 * @return the list
	 */
	List<StudySubjectConsentVersion> convertSubjectConsent(List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> subjectConsents);
	
	/**
	 * Convert.
	 *
	 * @param domainObject the domain object
	 * @return the study subject
	 */
	StudySubject convert(edu.duke.cabig.c3pr.domain.StudySubject domainObject);
	
	/**
	 * Convert registry status.
	 *
	 * @param status the status
	 * @return the study subject registry status
	 */
	StudySubjectRegistryStatus convertRegistryStatus(PerformedStudySubjectMilestone status);
	
	/**
	 * Convert subject demographics.
	 *
	 * @param studySubjectDemographics the study subject demographics
	 * @return the person
	 */
	Person convertSubjectDemographics(StudySubjectDemographics studySubjectDemographics);
	
	/**
	 * Convert to subject consent.
	 *
	 * @param studySubjectConsentVersions the study subject consent versions
	 * @return the list
	 */
	List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> convertToSubjectConsent(List<StudySubjectConsentVersion> studySubjectConsentVersions);
	
	/**
	 * Convert to subject consent.
	 *
	 * @param studySubjectConsentVersion the study subject consent version
	 * @return the edu.duke.cabig.c3pr.webservice.common. study subject consent version
	 */
	edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion convertToSubjectConsent(StudySubjectConsentVersion studySubjectConsentVersion);
	
	/**
	 * Convert to study subject registry status.
	 *
	 * @param statuses the statuses
	 * @return the list
	 */
	List<PerformedStudySubjectMilestone> convertToStudySubjectRegistryStatus(List<StudySubjectRegistryStatus> statuses);
	
	/**
	 * Updates the given instance of {@link StudySubjectDemographics} with new values. Does not modify identifiers.
	 *
	 * @param destination the destination
	 * @param source the source
	 */
	void convertToSubjectDemographics(StudySubjectDemographics destination, Subject source);
	
	DSETStudySubject optionallyLoadStudySubjectData(DSETStudySubject studySubjects, List<edu.duke.cabig.c3pr.domain.StudySubject> domainStudySubjects);
}

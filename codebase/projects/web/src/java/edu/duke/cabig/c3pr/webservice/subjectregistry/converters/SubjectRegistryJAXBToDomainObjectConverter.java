/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
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
	
	String convertHealthcareSitePrimaryIdentifier(Organization org);
	
	String convertHealthcareSitePrimaryIdentifier(OrganizationIdentifier orgId);
	
	List<StudySubjectConsentVersion> convertSubjectConsent(List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> subjectConsents);
	
	StudySubject convert(edu.duke.cabig.c3pr.domain.StudySubject domainObject);
	
	StudySubjectRegistryStatus convertRegistryStatus(PerformedStudySubjectMilestone status);
	
	Person convertSubjectDemographics(StudySubjectDemographics studySubjectDemographics);
	
	List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> convertToSubjectConsent(List<StudySubjectConsentVersion> studySubjectConsentVersions);
	
	edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion convertToSubjectConsent(StudySubjectConsentVersion studySubjectConsentVersion);
	
	List<PerformedStudySubjectMilestone> convertToStudySubjectRegistryStatus(List<StudySubjectRegistryStatus> statuses);
	/**
	 * Updates the given instance of {@link StudySubjectDemographics} with new values. Does not modify identifiers. 
	 * @param participant
	 * @param subject
	 */
	void convertToSubjectDemographics(StudySubjectDemographics destination, Subject source);
}

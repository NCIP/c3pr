package edu.duke.cabig.c3pr.domain.factory;

import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;

public interface ObjectFactory {

	public List<StudySubject> createStudySubjectsFromResultSet(List<Object> studySubjectResultList);
	
	public void buildContactMechanisms(List<Object> contactMechanismObjects, Map<Integer, StudySubjectDemographics> studySubjectDemographicsMap);
	
	public void buildAddresses(List<Object> addressObjects, Map<Integer, StudySubjectDemographics> studySubjectDemographicsMap);
	
	public void buildAddressUseAssociations(List<Object> addressUseObjects, Map<Integer, Address> addressMap);
	
	public void buildContactMechanismUseAssociations(List<Object> contactMechanismUseObjects, Map<Integer, ContactMechanism> contactMechanismsMap);
	
	public void buildDemographicsIdentifiers(List<Object> identifierObjects, Map<Integer, StudySubjectDemographics> studySubjectDemographicsMap);
	
	public void buildRaceCodes(List<Object> raceCodeObjects, Map<Integer, StudySubjectDemographics> studySubjectDemographicsMap);
	
	public void buildStudySubjectIdentifiers(List<Object> identifierObjects, Map<Integer, StudySubject> studySubjectsMap);
	
	public void buildOrganizationIdentifiers(List<Object> identifierObjects, Map<Integer, HealthcareSite> healthcareSiteIdentifiersMap);
	
	public void buildStudySubjectVersions(List<Object> studySubjectVersionObjects, Map<Integer, StudySubject> studySubjectsMap);
	
	public void buildStudySubjectConsentVersions(List<Object> studySubjectConsentObjects, Map<Integer, StudySubjectStudyVersion> studySubjectVersionsMap);
	
	public void buildStudyConsents(List<Object> consentObjects, Map<Integer, Consent> consentsMap);
	
	public void buildStudyConsentQuestionAnswers(List<Object> consentQuestionAnswersObjects,Map<Integer, StudySubjectConsentVersion> studySubjectConsentVersionsMap);
	
	public void buildStudyConsentQuestions(List<Object> consentQuestionsObjects,Map<Integer, ConsentQuestion> consentQuestionsMap);
	
	public void buildStudyVersions(List<Object> studyVersionObjects,Map<Integer, StudySiteStudyVersion> studySiteStudyVersionsMap);
	
	public List<Study> buildStudyIdentifiers(List<Object> identifierObjects, Map<Integer, Study> studiesMap);
	
	public void buildStudySiteHealthcareSiteIdentifiers(List<Object> studySiteHealthcareSiteIdentifierObjects,Map<Integer, StudySiteStudyVersion> studySiteStudyVersionsMap);
	
	public void buildRegistryStatusObjects(List<Object> registryStatusObjects, Map<Integer, StudySubject> studySubjectsMap);
	
	public void buildRegistryReasonObjects(List<Object> registryReasonObjects, Map<Integer, StudySubjectRegistryStatus> studySubjectRegistryStatusMap);
	
	public void buildPermissibleRegistryStatusObjects(List<Object> permissbleRegistryStatusObjects, Map<Integer, PermissibleStudySubjectRegistryStatus> permissibleRegistryStatusMap);
	
}

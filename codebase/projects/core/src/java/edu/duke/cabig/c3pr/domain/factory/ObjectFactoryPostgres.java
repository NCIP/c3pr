package edu.duke.cabig.c3pr.domain.factory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.constants.AddressUse;
import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.constants.ContactMechanismUse;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.AddressUseAssociation;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismUseAssociation;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

public class ObjectFactoryPostgres implements ObjectFactory{
	
	public List<StudySubject> createStudySubjectsFromResultSet(List<Object> studySubjectResultList){
		
		int noOfColumns = studySubjectResultList.size() > 0 ? ((Object[])studySubjectResultList.get(0)).length:0;
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		if (noOfColumns > 0){
		for (Object object : studySubjectResultList){
			
				Object[] studySubjectObject = (Object[]) (object);
				
				// create and copy attributes of study subject
				StudySubject studySubject = new StudySubject();
				studySubject.setId(((BigInteger)studySubjectObject[0]).intValue());
				studySubject.setPaymentMethod((String)studySubjectObject[1]);
				studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.valueOf((String)studySubjectObject[2]));
				// create and copy attributes of demographics
				StudySubjectDemographics demographics = new StudySubjectDemographics();
				demographics.setId(((Integer)studySubjectObject[3]).intValue());
				demographics.setFirstName((String)studySubjectObject[4]);
				demographics.setLastName((String)studySubjectObject[5]);
				demographics.setMiddleName((String)studySubjectObject[6]);
				demographics.setNamePrefix((String)studySubjectObject[7]);
				demographics.setNameSuffix((String)studySubjectObject[8]);
				demographics.setAdministrativeGenderCode((String)studySubjectObject[9]);
				demographics.setEthnicGroupCode((String)studySubjectObject[10]);
				demographics.setBirthDate((Date)studySubjectObject[11]);
				demographics.setMaritalStatusCode((String)studySubjectObject[12]);
				demographics.setValid(((Boolean)studySubjectObject[13]));

				studySubject.setStudySubjectDemographics(demographics);
				studySubjects.add(studySubject);
			}
		}
		
		return studySubjects;
	}
	
public void buildContactMechanisms(List<Object> contactMechanismObjects, Map<Integer, StudySubjectDemographics> studySubjectDemographicsMap){
		Integer demographicsId;
		for (Object object : contactMechanismObjects){
			Object[] contactMechanismObject = (Object[]) (object);
			demographicsId = ((Integer)contactMechanismObject[0]).intValue();
			ContactMechanism contactMechanism = new ContactMechanism();
			contactMechanism.setType(ContactMechanismType.valueOf((String)contactMechanismObject[1]));
			contactMechanism.setValue((String)contactMechanismObject[2]);
			contactMechanism.setId(((BigInteger)contactMechanismObject[3]).intValue());
			studySubjectDemographicsMap.get(demographicsId).getContactMechanisms().add(contactMechanism);
		}
	}

public void buildAddresses(List<Object> addressObjects, Map<Integer, StudySubjectDemographics> studySubjectDemographicsMap){
	Integer demographicsId;
	for (Object object : addressObjects){
		Object[] addressObject = (Object[]) (object);
		demographicsId = ((Integer)addressObject[0]).intValue();
		Address address = new Address();
		address.setStreetAddress((String)addressObject[1]);
		address.setCity((String)addressObject[2]);
		address.setStateCode((String)addressObject[3]);
		address.setCountryCode((String)addressObject[4]);
		address.setPostalCode((String)addressObject[5]);
		address.setId(((BigInteger)addressObject[6]).intValue());
		studySubjectDemographicsMap.get(demographicsId).getAddresses().add(address);
	}
}


public void buildAddressUseAssociations(List<Object> addressUseObjects, Map<Integer, Address> addressMap){
	Integer addressId;
	for (Object object : addressUseObjects){
		Object[] addressUseObject = (Object[]) (object);
		addressId = ((Integer)addressUseObject[0]).intValue();
		AddressUseAssociation addUseAssocn = new AddressUseAssociation();
		addUseAssocn.setUse(AddressUse.valueOf((String)(addressUseObject[1])));
		addressMap.get(addressId).getAddressUseAssociation().add(addUseAssocn);
	}
}


public void buildContactMechanismUseAssociations(List<Object> contactMechanismUseObjects, Map<Integer, ContactMechanism> contactMechanismsMap){
	Integer contactMechanismId;
	for (Object object : contactMechanismUseObjects){
		Object[] cmUseObject = (Object[]) (object);
		contactMechanismId = ((Integer)cmUseObject[0]).intValue();
		ContactMechanismUseAssociation cmUseAssocn = new ContactMechanismUseAssociation();
		cmUseAssocn.setUse(ContactMechanismUse.valueOf((String)(cmUseObject[1])));
		contactMechanismsMap.get(contactMechanismId).getContactMechanismUseAssociation().add(cmUseAssocn);
	}
}

public void buildDemographicsIdentifiers(List<Object> identifierObjects, Map<Integer, StudySubjectDemographics> studySubjectDemographicsMap){
	Integer demographicsId;
	Map<Integer,HealthcareSite> healthcareSitesMap = new HashMap<Integer,HealthcareSite>();
	for (Object object : identifierObjects){
		Object[] identifierObject = (Object[]) (object);
		demographicsId = ((Integer)identifierObject[0]).intValue();
		String dtype = (String)identifierObject[1];
		if(dtype.equals("OAI")){
			OrganizationAssignedIdentifier oid = new OrganizationAssignedIdentifier();
			oid.setType(OrganizationIdentifierTypeEnum.valueOf((String)(identifierObject[2])));
			oid.setPrimaryIndicator(((Boolean)identifierObject[4]));
			oid.setValue((String)(identifierObject[5]));
			if(healthcareSitesMap.get(((Integer)(identifierObject[6])).intValue()) != null){
				oid.setHealthcareSite(healthcareSitesMap.get(((Integer)(identifierObject[6])).intValue()));
			} else{
				HealthcareSite site = new LocalHealthcareSite();
				site.setId(((Integer)(identifierObject[6])).intValue());
				oid.setHealthcareSite(site);
				healthcareSitesMap.put(((Integer)(identifierObject[6])).intValue(), site);
			}
			studySubjectDemographicsMap.get(demographicsId).getIdentifiers().add(oid);
		} else if(dtype.equals("SAI")){
			SystemAssignedIdentifier sid = new SystemAssignedIdentifier();
			sid.setType((String)(identifierObject[2]));
			sid.setSystemName((String)(identifierObject[3]));
			sid.setPrimaryIndicator(((Boolean)identifierObject[4]));
			sid.setValue((String)(identifierObject[5]));
			studySubjectDemographicsMap.get(demographicsId).getIdentifiers().add(sid);
		}
	}
}

public void buildRaceCodes(List<Object> raceCodeObjects, Map<Integer, StudySubjectDemographics> studySubjectDemographicsMap){
	Integer demographicsId;
	for (Object object : raceCodeObjects){
		Object[] raceCodeObject = (Object[]) (object);
		demographicsId = ((Integer)raceCodeObject[0]).intValue();
		RaceCodeAssociation raceCodeAssociation = new RaceCodeAssociation();
		raceCodeAssociation.setRaceCode(RaceCodeEnum.valueOf((String)(raceCodeObject[1])));
		studySubjectDemographicsMap.get(demographicsId).getRaceCodeAssociations().add(raceCodeAssociation);
	}
}


public void buildStudySubjectIdentifiers(List<Object> identifierObjects, Map<Integer, StudySubject> studySubjectsMap){
	Integer studySubjectId;
	Map<Integer,HealthcareSite> healthcareSitesMap = new HashMap<Integer,HealthcareSite>();
	for (Object object : identifierObjects){
		Object[] identifierObject = (Object[]) (object);
		studySubjectId = ((BigInteger)identifierObject[0]).intValue();
		String dtype = (String)identifierObject[1];
		if(dtype.equals("OAI")){
			OrganizationAssignedIdentifier oid = new OrganizationAssignedIdentifier();
			oid.setType(OrganizationIdentifierTypeEnum.valueOf((String)(identifierObject[2])));
			oid.setPrimaryIndicator(((Boolean)identifierObject[4]));
			oid.setValue((String)(identifierObject[5]));
			if(healthcareSitesMap.get(((Integer)(identifierObject[6])).intValue()) != null){
				oid.setHealthcareSite(healthcareSitesMap.get(((Integer)(identifierObject[6])).intValue()));
			} else{
				HealthcareSite site = new LocalHealthcareSite();
				site.setId(((Integer)(identifierObject[6])).intValue());
				oid.setHealthcareSite(site);
				healthcareSitesMap.put(((Integer)(identifierObject[6])).intValue(), site);
			}
			studySubjectsMap.get(studySubjectId).getIdentifiers().add(oid);
		} else if(dtype.equals("SAI")){
			SystemAssignedIdentifier sid = new SystemAssignedIdentifier();
			sid.setType((String)(identifierObject[2]));
			sid.setSystemName((String)(identifierObject[3]));
			sid.setPrimaryIndicator(((Boolean)identifierObject[4]));
			sid.setValue((String)(identifierObject[5]));
			studySubjectsMap.get(studySubjectId).getIdentifiers().add(sid);
		}
	}
}

public void buildOrganizationIdentifiers(List<Object> identifierObjects, Map<Integer, HealthcareSite> healthcareSiteIdentifiersMap){
	Integer organizationId;
	for (Object object : identifierObjects){
		Object[] identifierObject = (Object[]) (object);
		organizationId = ((Integer)identifierObject[0]).intValue();
		String dtype = (String)identifierObject[1];
		if(dtype.equals("OAI")){
			OrganizationAssignedIdentifier oid = new OrganizationAssignedIdentifier();
			oid.setType(OrganizationIdentifierTypeEnum.valueOf((String)(identifierObject[2])));
			oid.setPrimaryIndicator(((Boolean)identifierObject[3]));
			oid.setValue((String)(identifierObject[4]));
			healthcareSiteIdentifiersMap.get(organizationId).getIdentifiersAssignedToOrganization().add(oid);
		} 
	}
}

public void buildStudySubjectVersions(List<Object> studySubjectVersionObjects, Map<Integer, StudySubject> studySubjectsMap){
	Integer studySubjectId;
	Map<Integer,StudySiteStudyVersion> studySiteVersionsHashMap = new HashMap<Integer,StudySiteStudyVersion>();
	for (Object object : studySubjectVersionObjects){
		Object[] studySubjectVersionObject = (Object[]) (object);
		StudySubjectStudyVersion studySubjectStudyVersion = new StudySubjectStudyVersion();
		studySubjectId = ((Integer)studySubjectVersionObject[0]).intValue();
		studySubjectStudyVersion.setId(((Integer)studySubjectVersionObject[1]).intValue());
		if(studySiteVersionsHashMap.get(((Integer)studySubjectVersionObject[2]).intValue()) != null){
			studySubjectStudyVersion.setStudySiteStudyVersion(studySiteVersionsHashMap.get(((Integer)studySubjectVersionObject[2]).intValue()));
		} else {
			StudySiteStudyVersion studySiteStudyVersion = new StudySiteStudyVersion();
			studySiteStudyVersion.setId(((Integer)studySubjectVersionObject[2]).intValue());
			studySiteVersionsHashMap.put(((Integer)studySubjectVersionObject[2]).intValue(),studySiteStudyVersion);
			studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
		}
		studySubjectsMap.get(studySubjectId).getStudySubjectStudyVersions().add(studySubjectStudyVersion);
	}
}

public void buildStudySubjectConsentVersions(List<Object> studySubjectConsentObjects, Map<Integer, StudySubjectStudyVersion> studySubjectVersionsMap){
	Integer studySubjectStudyVersionId;
	Map<Integer,Consent> consentsMap = new HashMap<Integer,Consent>();
	for (Object object : studySubjectConsentObjects){
		Object[] studySubjectConsentVersionObject = (Object[]) (object);
		StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
		studySubjectStudyVersionId = ((Integer)studySubjectConsentVersionObject[0]).intValue();
		studySubjectConsentVersion.setId(((Integer)studySubjectConsentVersionObject[1]).intValue());
		studySubjectConsentVersion.setInformedConsentSignedDate((Date)studySubjectConsentVersionObject[2]);
		if(consentsMap.get(((Integer)studySubjectConsentVersionObject[3]).intValue()) != null){
			studySubjectConsentVersion.setConsent(consentsMap.get(((Integer)studySubjectConsentVersionObject[3]).intValue()));
		} else {
			Consent consent = new Consent();
			consent.setId(((Integer)studySubjectConsentVersionObject[3]).intValue());
			studySubjectConsentVersion.setConsent(consent);
			consentsMap.put(((Integer)studySubjectConsentVersionObject[3]).intValue(), consent);
		}
		studySubjectConsentVersion.setConsentingMethod(ConsentingMethod.valueOf((String)studySubjectConsentVersionObject[4]));
		studySubjectConsentVersion.setConsentPresenter((String)studySubjectConsentVersionObject[5]);
		studySubjectConsentVersion.setConsentDeliveryDate((Date)studySubjectConsentVersionObject[6]);
		studySubjectConsentVersion.setDocumentId((String)studySubjectConsentVersionObject[7]);
		studySubjectConsentVersion.setConsentDeclinedDate((Date)studySubjectConsentVersionObject[8]);
		studySubjectVersionsMap.get(studySubjectStudyVersionId).getStudySubjectConsentVersionsInternal().add(studySubjectConsentVersion);
	}
}


public void buildStudyConsents(List<Object> consentObjects, Map<Integer, Consent> consentsMap){
	Integer consentId;
	Consent consent = null;
	for (Object object : consentObjects){
		Object[] consentObject = (Object[]) (object);
		consentId = ((Integer)consentObject[0]).intValue();
		consent = consentsMap.get(consentId);
		consent.setName((String)consentObject[1]);
		consent.setVersionId((String)consentObject[2]);
		consent.setMandatoryIndicator(((Boolean)consentObject[3]));
		consent.setDescriptionText((String)consentObject[4]);
	}
}

public void buildStudyConsentQuestionAnswers(List<Object> consentQuestionAnswersObjects,Map<Integer, StudySubjectConsentVersion> studySubjectConsentVersionsMap){
	Integer studySubjectConsentVersionId;
	Map<Integer,ConsentQuestion> consentQuestionsMap = new HashMap<Integer,ConsentQuestion>();
	for (Object object : consentQuestionAnswersObjects){
		Object[] consentQuestionAnswerObject = (Object[]) (object);
		studySubjectConsentVersionId = ((Integer)consentQuestionAnswerObject[0]).intValue();
		SubjectConsentQuestionAnswer scqa = new SubjectConsentQuestionAnswer();
		scqa.setId(((Integer)consentQuestionAnswerObject[1]).intValue());
		scqa.setAgreementIndicator(((Boolean)consentQuestionAnswerObject[2]));
		if(consentQuestionsMap.get(((Integer)consentQuestionAnswerObject[3]).intValue()) != null){
			scqa.setConsentQuestion(consentQuestionsMap.get(((Integer)consentQuestionAnswerObject[3]).intValue()));
		} else {
			ConsentQuestion cq = new ConsentQuestion();
			cq.setId(((Integer)consentQuestionAnswerObject[3]).intValue());
			scqa.setConsentQuestion(cq);
			consentQuestionsMap.put(((Integer)consentQuestionAnswerObject[3]).intValue(), cq);
		}
		studySubjectConsentVersionsMap.get(studySubjectConsentVersionId).addSubjectConsentAnswer(scqa);
	}
}
public void buildStudyConsentQuestions(List<Object> consentQuestionsObjects,Map<Integer, ConsentQuestion> consentQuestionsMap){
	Integer consentQuestionId;
	for (Object object : consentQuestionsObjects){
		Object[] consentQuestionObject = (Object[]) (object);
		consentQuestionId = ((Integer)consentQuestionObject[0]).intValue();
		consentQuestionsMap.get(consentQuestionId).setCode((String)consentQuestionObject[1]);
	}
}

public void buildStudyVersions(List<Object> studyVersionObjects,Map<Integer, StudySiteStudyVersion> studySiteStudyVersionsMap){
	Integer studySiteStudyVersionId;
	Map<Integer,StudyVersion> studyVersionsMap = new HashMap<Integer,StudyVersion>();
	Map<Integer,Study> studiesMap = new HashMap<Integer,Study>();
	
	for (Object object : studyVersionObjects){
		Object[] studyVersionObject = (Object[]) (object);
		studySiteStudyVersionId = ((Integer)studyVersionObject[0]).intValue();
		
		// 2 study site versions can refer to the same study version object, so the map needs to be checked first to see if the study version object already exists
		if(studyVersionsMap.get(((Integer)studyVersionObject[1]).intValue()) != null){
			studySiteStudyVersionsMap.get(studySiteStudyVersionId).setStudyVersion(studyVersionsMap.get(((Integer)studyVersionObject[1]).intValue()));
		} else {
			StudyVersion studyVersion = new StudyVersion();
			studyVersion.setId(((Integer)studyVersionObject[1]).intValue());
			studyVersionsMap.put(((Integer)studyVersionObject[1]).intValue(),studyVersion);
			studyVersion.setShortTitleText((String)studyVersionObject[2]);
			studyVersion.setLongTitleText((String)studyVersionObject[3]);
			studyVersion.setDescriptionText((String)studyVersionObject[4]);
			
			// 2 study versions can refer to the same study object, so the map needs to be checked first to see if the study object already exists
			if(studiesMap.get(((Integer)studyVersionObject[5]).intValue()) != null){
				studyVersion.setStudy(studiesMap.get(((Integer)studyVersionObject[5]).intValue()));
			} else {
				Study study = new LocalStudy();
				study.setId(((Integer)studyVersionObject[5]).intValue());
				study.addStudyVersion(studyVersion);
				studiesMap.put(((Integer)studyVersionObject[5]).intValue(), study);
			}
			
			studySiteStudyVersionsMap.get(studySiteStudyVersionId).setStudyVersion(studyVersion);
		}
	}
}

public List<Study> buildStudyIdentifiers(List<Object> identifierObjects, Map<Integer, Study> studiesMap){
	List<Study> studies = new ArrayList<Study>();
	Integer studyId;
	Map<Integer,HealthcareSite> healthcareSitesMap = new HashMap<Integer,HealthcareSite>();
	for (Object object : identifierObjects){
		Object[] identifierObject = (Object[]) (object);
		studyId = ((BigInteger)identifierObject[0]).intValue();
		String dtype = (String)identifierObject[1];
		if(dtype.equals("OAI")){
			OrganizationAssignedIdentifier oid = new OrganizationAssignedIdentifier();
			oid.setType(OrganizationIdentifierTypeEnum.valueOf((String)(identifierObject[2])));
			oid.setPrimaryIndicator(((Boolean)identifierObject[4]));
			oid.setValue((String)(identifierObject[5]));
			if(healthcareSitesMap.get(((Integer)(identifierObject[6])).intValue()) != null){
				oid.setHealthcareSite(healthcareSitesMap.get(((Integer)(identifierObject[6])).intValue()));
			} else{
				HealthcareSite site = new LocalHealthcareSite();
				site.setId(((Integer)(identifierObject[6])).intValue());
				oid.setHealthcareSite(site);
				healthcareSitesMap.put(((Integer)(identifierObject[6])).intValue(), site);
			}
			studiesMap.get(studyId).getIdentifiers().add(oid);
		} else if(dtype.equals("SAI")){
			SystemAssignedIdentifier sid = new SystemAssignedIdentifier();
			sid.setType((String)(identifierObject[2]));
			sid.setSystemName((String)(identifierObject[3]));
			sid.setPrimaryIndicator(((Boolean)identifierObject[4]));
			sid.setValue((String)(identifierObject[5]));
			studiesMap.get(studyId).getIdentifiers().add(sid);
		}
	}
	
	studies.addAll(studiesMap.values());
	return studies;
}

public void buildStudySiteHealthcareSiteIdentifiers(List<Object> studySiteHealthcareSiteIdentifierObjects,Map<Integer, StudySiteStudyVersion> studySiteStudyVersionsMap){
	Integer studySiteStudyVersionId;
	Map<Integer,StudySite> studySitesMap = new HashMap<Integer,StudySite>();
	Map<Integer,HealthcareSite> healthcareSitesMap = new HashMap<Integer,HealthcareSite>();
	for (Object object : studySiteHealthcareSiteIdentifierObjects){
		Object[] studySiteHcsIdObject = (Object[]) (object);
		studySiteStudyVersionId = ((Integer)studySiteHcsIdObject[0]).intValue();
		
		// 2 study site version objects can refer to the same study site object, so the map needs to be checked first to see if the study site object already exists
		if(studySitesMap.get(((BigInteger)studySiteHcsIdObject[1]).intValue()) != null){
			studySiteStudyVersionsMap.get(studySiteStudyVersionId).setStudySite(studySitesMap.get(((BigInteger)studySiteHcsIdObject[1]).intValue()));
		} else {
			StudySite studySite = new StudySite();
			studySite.setId(((BigInteger)studySiteHcsIdObject[1]).intValue());
			studySitesMap.put(((BigInteger)studySiteHcsIdObject[1]).intValue(), studySite);
			// 2 study site objects can refer to the same healthcare site object, so the map needs to be checked first to see if the healthcare site object already exists
			if(healthcareSitesMap.get(((BigInteger)studySiteHcsIdObject[2]).intValue()) != null){
				studySite.setHealthcareSite(healthcareSitesMap.get(((BigInteger)studySiteHcsIdObject[2]).intValue()));
			} else {
				HealthcareSite hcs = new LocalHealthcareSite();
				hcs.setId(((BigInteger)studySiteHcsIdObject[2]).intValue());
				OrganizationAssignedIdentifier orgId = new OrganizationAssignedIdentifier();
				orgId.setType(OrganizationIdentifierTypeEnum.valueOf((String)(studySiteHcsIdObject[3])));
				orgId.setValue(((String)(studySiteHcsIdObject[4])));
				orgId.setPrimaryIndicator((((Boolean)studySiteHcsIdObject[5])));
				hcs.getIdentifiersAssignedToOrganization().add(orgId);
				studySite.setHealthcareSite(hcs);
				healthcareSitesMap.put(((BigInteger)studySiteHcsIdObject[2]).intValue(), hcs);
			}
			studySiteStudyVersionsMap.get(studySiteStudyVersionId).setStudySite(studySite);
		}
	}
}

public void buildRegistryStatusObjects(List<Object> registryStatusObjects, Map<Integer, StudySubject> studySubjectsMap){
	Integer studySubjectId;
	Map<Integer, PermissibleStudySubjectRegistryStatus> permissibleRegistryStatusMap = new HashMap<Integer, PermissibleStudySubjectRegistryStatus>();
	for (Object object : registryStatusObjects){
		Object[] studySubjectRegistryStatusObject = (Object[]) (object);
		StudySubjectRegistryStatus studySubjectRegistryStatus = new StudySubjectRegistryStatus();
		studySubjectId = ((Integer)studySubjectRegistryStatusObject[0]).intValue();
		studySubjectRegistryStatus.setId(((Integer)studySubjectRegistryStatusObject[1]).intValue());
		studySubjectRegistryStatus.setCommentText(((String)(studySubjectRegistryStatusObject[2])));
		studySubjectRegistryStatus.setEffectiveDate(((Date)(studySubjectRegistryStatusObject[3])));
		
		if(permissibleRegistryStatusMap.get(((Integer)studySubjectRegistryStatusObject[4]).intValue()) != null){
			studySubjectRegistryStatus.setPermissibleStudySubjectRegistryStatus(permissibleRegistryStatusMap.get(((Integer)studySubjectRegistryStatusObject[4]).intValue()));
		} else {
			PermissibleStudySubjectRegistryStatus permissibleRegistryStatus = new PermissibleStudySubjectRegistryStatus();
			permissibleRegistryStatus.setId(((Integer)studySubjectRegistryStatusObject[4]).intValue());
			permissibleRegistryStatusMap.put(((Integer)studySubjectRegistryStatusObject[4]).intValue(), permissibleRegistryStatus);
			studySubjectRegistryStatus.setPermissibleStudySubjectRegistryStatus(permissibleRegistryStatus);
		}
		
		studySubjectsMap.get(studySubjectId).getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
	}
}

public void buildRegistryReasonObjects(List<Object> registryReasonObjects, Map<Integer, StudySubjectRegistryStatus> studySubjectRegistryStatusMap){
	Integer studySubjectRegistryStatusId;
	Map<Integer,RegistryStatusReason> registryReasonsMap = new HashMap<Integer,RegistryStatusReason>();
	for (Object object : registryReasonObjects){
		Object[] registryReasonObject = (Object[]) (object);
		studySubjectRegistryStatusId = ((Integer)registryReasonObject[2]).intValue();
		if(registryReasonsMap.get(((Integer)registryReasonObject[0]).intValue()) != null){
			studySubjectRegistryStatusMap.get(studySubjectRegistryStatusId).getReasons().add(registryReasonsMap.get(((Integer)registryReasonObject[0]).intValue()));
		} else {
			RegistryStatusReason reason = new RegistryStatusReason();
			reason.setId(((Integer)registryReasonObject[0]).intValue());
			reason.setCode(((String)(registryReasonObject[1])));
			registryReasonsMap.put(((Integer)registryReasonObject[0]).intValue(), reason);
			studySubjectRegistryStatusMap.get(studySubjectRegistryStatusId).getReasons().add(reason);
		}
	}
}

public void buildPermissibleRegistryStatusObjects(List<Object> permissbleRegistryStatusObjects, Map<Integer, PermissibleStudySubjectRegistryStatus> permissibleRegistryStatusMap){
	Integer  permissibleStudySubjectRegistryStatusId;
	Map<Integer,RegistryStatus> registryStatusMap = new HashMap<Integer,RegistryStatus>();
	for (Object object : permissbleRegistryStatusObjects){
		Object[] permissbleRegistryStatusObject = (Object[]) (object);
		permissibleStudySubjectRegistryStatusId = ((Integer)permissbleRegistryStatusObject[0]).intValue();
		if(registryStatusMap.get(((Integer)permissbleRegistryStatusObject[1]).intValue()) != null){
			permissibleRegistryStatusMap.get(permissibleStudySubjectRegistryStatusId).setRegistryStatus(registryStatusMap.get(((Integer)permissbleRegistryStatusObject[1]).intValue()));
		} else {
			RegistryStatus registryStatus = new RegistryStatus();
			registryStatus.setId(((Integer)permissbleRegistryStatusObject[1]).intValue());
			registryStatus.setCode(((String)(permissbleRegistryStatusObject[2])));
			registryStatusMap.put(((Integer)permissbleRegistryStatusObject[1]).intValue(), registryStatus);
			permissibleRegistryStatusMap.get(permissibleStudySubjectRegistryStatusId).setRegistryStatus(registryStatus);
		}
	}
}

}

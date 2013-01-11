/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.webservice.subjectregistry.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.StudySiteProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.StudySubjectProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;

/**
 * Default implementation of {@link JAXBToDomainObjectConverter}.
 * 
 * @author dkrylov
 * 
 */
public class SubjectRegistryJAXBToDomainObjectConverterImpl extends JAXBToDomainObjectConverterImpl implements
		SubjectRegistryJAXBToDomainObjectConverter {

	static final int MISSING_IDENTIFIER = 917;
	static final int IDENTIFIER_MISSING_ORGANIZATION = 918;
	static final int MISSING_OR_INVALID_CONSENT_NAME = 919;
	static final int INVALID_CONSENT_QUESTION_ANSWER_AGREEMENTINDICATOR = 920;
	static final int INVALID_CONSENT_QUESTION = 921;
	static final int MISSING_STATUS_CODE = 922;
	static final int MISSING_STATUS_DATE = 923;
	
	private static Log log = LogFactory
			.getLog(SubjectRegistryJAXBToDomainObjectConverterImpl.class);

	public String convertHealthcareSitePrimaryIdentifier(Organization org) {
		List<OrganizationIdentifier> idList = org.getOrganizationIdentifier();
		if (CollectionUtils.isEmpty(idList)) {
			throw exceptionHelper
					.getConversionException(ORGANIZATION_IDENTIFIER_MISSING_TYPECODE);
		}
		OrganizationIdentifier orgId = idList.get(0);
		II id = orgId.getIdentifier();
		if (id == null || StringUtils.isBlank(id.getExtension())) {
			throw exceptionHelper
					.getConversionException(ORGANIZATION_IDENTIFIER_MISSING_TYPECODE);
		}
		return id.getExtension();
	}
	
	public String convertHealthcareSitePrimaryIdentifier(OrganizationIdentifier orgId) {
		II id = orgId.getIdentifier();
		if (id == null || StringUtils.isBlank(id.getExtension())) {
			throw exceptionHelper
					.getConversionException(ORGANIZATION_IDENTIFIER_MISSING_TYPECODE);
		}
		return id.getExtension();
	}

	public List<StudySubjectConsentVersion> convertSubjectConsent(
			List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> subjectConsents) {
		List<StudySubjectConsentVersion> studySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
		for(edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion subjectConsent : subjectConsents){
			StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
			studySubjectConsentVersion.setConsentDeliveryDate(convertToDate(subjectConsent.getConsentDeliveryDate()));
			studySubjectConsentVersion.setConsentDeclinedDate(convertToDate(subjectConsent.getConsentDeclinedDate()));
			studySubjectConsentVersion.setDocumentId(subjectConsent.getIdentifier() !=null ?
					subjectConsent.getIdentifier().getExtension(): null);
			studySubjectConsentVersion.setConsentingMethod(subjectConsent.getConsentingMethod() !=null ? 
					ConsentingMethod.getByCode(subjectConsent.getConsentingMethod().getCode()) : null);
			studySubjectConsentVersion.setConsentPresenter(subjectConsent.getConsentPresenter() !=null ?
					subjectConsent.getConsentPresenter().getValue() : null);
			studySubjectConsentVersion.setInformedConsentSignedDate(convertToDate(subjectConsent.getInformedConsentDate()));
			Consent consent = new Consent();
			if(subjectConsent.getConsent() == null ||
					subjectConsent.getConsent().getOfficialTitle() == null ||
					StringUtils.isBlank(subjectConsent.getConsent().getOfficialTitle().getValue())){
				throw exceptionHelper
					.getConversionException(MISSING_OR_INVALID_CONSENT_NAME);
			}
			consent.setName(subjectConsent.getConsent().getOfficialTitle().getValue());
			if(subjectConsent.getConsent().getText() != null){
				consent.setDescriptionText(subjectConsent.getConsent().getText().getValue());
			}
			
			if(subjectConsent.getConsent().getVersionNumberText() != null &&
					!StringUtils.isBlank(subjectConsent.getConsent().getVersionNumberText().getValue())){
				consent.setVersionId(subjectConsent.getConsent().getVersionNumberText().getValue());
			}
			studySubjectConsentVersion.setConsent(consent);
			for(PerformedStudySubjectMilestone subjectConsetAnswer : subjectConsent.getSubjectConsentAnswer()){
				SubjectConsentQuestionAnswer subjectConsentQuestionAnswer = new SubjectConsentQuestionAnswer();
				if(subjectConsetAnswer.getMissedIndicator() == null ||
						subjectConsetAnswer.getMissedIndicator().isValue() == null){
					throw exceptionHelper
					.getConversionException(INVALID_CONSENT_QUESTION_ANSWER_AGREEMENTINDICATOR);
				}
				subjectConsentQuestionAnswer.setAgreementIndicator(subjectConsetAnswer.getMissedIndicator().isValue());
				subjectConsentQuestionAnswer.setConsentQuestion(new ConsentQuestion());
				if(subjectConsetAnswer.getConsentQuestion() == null ||
						subjectConsetAnswer.getConsentQuestion().getOfficialTitle() == null ||
						StringUtils.isBlank(subjectConsetAnswer.getConsentQuestion().getOfficialTitle().getValue())){
					throw exceptionHelper
					.getConversionException(INVALID_CONSENT_QUESTION);
				}
				subjectConsentQuestionAnswer.getConsentQuestion().setCode(subjectConsetAnswer.getConsentQuestion().getOfficialTitle().getValue());
				studySubjectConsentVersion.getSubjectConsentAnswers().add(subjectConsentQuestionAnswer);
			}
			studySubjectConsentVersions.add(studySubjectConsentVersion);
		}
		return studySubjectConsentVersions;
	}

	public List<Identifier> convertSubjectIdentifiers(
			List<SubjectIdentifier> subjectIdentifiers) {
		List<Identifier> identifiers = new ArrayList<Identifier>();
		for(SubjectIdentifier subId : subjectIdentifiers){
			final II ii = subId.getIdentifier();
			final CD typeCode = subId.getTypeCode();
			Organization org = subId.getAssigningOrganization();
			if (ii == null || typeCode == null) {
				throw exceptionHelper
						.getConversionException(MISSING_IDENTIFIER);
			}
			if (org == null) {
				throw exceptionHelper
						.getConversionException(MISSING_STATUS_CODE);
			}
			OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
			id.setPrimaryIndicator(subId.getPrimaryIndicator().isValue());
			id.setValue(ii.getExtension());
			id.setType(OrganizationIdentifierTypeEnum.valueOf(typeCode.getCode()));
			HealthcareSite healthcareSite = resolveHealthcareSite(org);
			id.setHealthcareSite(healthcareSite);
			identifiers.add(id);
		}
		return identifiers;
	}
	
	protected List<SubjectIdentifier> convertToSubjectIdentifier(
			List<Identifier> identifiers) {
		List<SubjectIdentifier> result = new ArrayList<SubjectIdentifier>();
		for (Identifier source : identifiers) {
			if (source instanceof OrganizationAssignedIdentifier) {
				SubjectIdentifier id = new SubjectIdentifier();
				id.setTypeCode(iso.CD(((OrganizationAssignedIdentifier)source).getType().getName()));
				id.setIdentifier(iso.II(source.getValue()));

				HealthcareSite site = ((OrganizationAssignedIdentifier) source)
						.getHealthcareSite();
				Organization org = new Organization();

				for (Identifier siteId : site
						.getIdentifiersAssignedToOrganization()) {
					OrganizationIdentifier orgId = new OrganizationIdentifier();
					orgId.setTypeCode(iso.CD(siteId.getTypeInternal()));
					orgId.setIdentifier(iso.II(siteId.getValue()));
					orgId.setPrimaryIndicator(iso.BL(siteId
							.getPrimaryIndicator()));
					org.getOrganizationIdentifier().add(orgId);
				}

				id.setAssigningOrganization(org);
				id.setPrimaryIndicator(iso.BL(source.getPrimaryIndicator()));
				result.add(id);
			}
		}
		return result;
	}
	
	protected List<OrganizationIdentifier> convertToOrganizationIdentifier(
			List<Identifier> identifiers) {
		List<OrganizationIdentifier> result = new ArrayList<OrganizationIdentifier>();
		for (Identifier source : identifiers) {
			if (source instanceof OrganizationAssignedIdentifier) {
				OrganizationIdentifier id = new OrganizationIdentifier();
				id.setTypeCode(iso.CD(source.getTypeInternal()));
				id.setIdentifier(iso.II(source.getValue()));
				id.setPrimaryIndicator(iso.BL(source
						.getPrimaryIndicator()));
				result.add(id);
			}
		}
		return result;
	}

	public StudySubject convert(
			edu.duke.cabig.c3pr.domain.StudySubject domainObject) {
		StudySubject studySubject = new StudySubject();
		//set person
		Person person = convertSubjectDemographics(domainObject.getStudySubjectDemographics());
		
		//copy enrollment
		studySubject.setEntity(person);
		studySubject.setPaymentMethodCode(iso.CD(domainObject.getPaymentMethod()));
		studySubject.setStatusCode(iso.CD(domainObject.getRegDataEntryStatus().getCode()));
		
		//copy identifiers
		studySubject.getSubjectIdentifier().addAll(convertToSubjectIdentifier(domainObject.getIdentifiers()));
		
		//set studySubjectProtocolVersion
		studySubject.setStudySubjectProtocolVersion(getStudySubjectProtocolVersion(domainObject.getStudySubjectStudyVersion()));
		
		//set status history
		studySubject.getStudySubjectStatus().addAll(convertToStudySubjectRegistryStatus(domainObject.getStudySubjectRegistryStatusHistory()));
		
		return studySubject;
	}
	
	public StudySubjectRegistryStatus convertRegistryStatus(
			PerformedStudySubjectMilestone status) {
		StudySubjectRegistryStatus studySubjectRegistryStatus = new StudySubjectRegistryStatus();
		if(status.getStatusCode() == null ||
				StringUtils.isBlank(status.getStatusCode().getCode())){
			throw exceptionHelper
				.getConversionException(MISSING_STATUS_CODE);
		}else if(status.getStatusDate() == null){
			throw exceptionHelper
				.getConversionException(MISSING_STATUS_DATE);
		}
		studySubjectRegistryStatus.setPermissibleStudySubjectRegistryStatus(new PermissibleStudySubjectRegistryStatus());
		studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().setRegistryStatus(new RegistryStatus());
		studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().setCode(status.getStatusCode().getCode());
		studySubjectRegistryStatus.setEffectiveDate(convertToDate(status.getStatusDate()));
		studySubjectRegistryStatus.setCommentText(status.getComment()==null ? null : status.getComment().getValue());
		if(status.getReasonCode() != null && status.getReasonCode().getItem() != null){
			for(CD reasonCode : status.getReasonCode().getItem()){
				if(reasonCode !=null && !StringUtils.isBlank(reasonCode.getCode())){
					studySubjectRegistryStatus.getReasons().add(new RegistryStatusReason(reasonCode.getCode(),"",null,false));
				}
			}
		}
		return studySubjectRegistryStatus;
	}

	public Person convertSubjectDemographics(
			StudySubjectDemographics studySubjectDemographics) {
		Person person = new Person();
		if (studySubjectDemographics != null) {
			for (Identifier id : studySubjectDemographics.getIdentifiers()) {
				if (id instanceof OrganizationAssignedIdentifier) {
					BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
					bioId.setTypeCode(iso.CD(((OrganizationAssignedIdentifier)id).getType().getName()));
					bioId.setIdentifier(iso.II(id.getValue()));
					bioId
							.setEffectiveDateRange(iso.IVLTSDateTime(
									NullFlavor.NI));

					HealthcareSite site = ((OrganizationAssignedIdentifier) id)
							.getHealthcareSite();
					Organization org = new Organization();

					for (Identifier siteId : site
							.getIdentifiersAssignedToOrganization()) {
						OrganizationIdentifier orgId = new OrganizationIdentifier();
						orgId.setTypeCode(iso.CD(siteId.getTypeInternal()));
						orgId.setIdentifier(iso.II(siteId.getValue()));
						orgId.setPrimaryIndicator(iso.BL(siteId
								.getPrimaryIndicator()));
						org.getOrganizationIdentifier().add(orgId);
					}

					bioId.setAssigningOrganization(org);
					bioId.setPrimaryIndicator(iso.BL(id.getPrimaryIndicator()));
					person.getBiologicEntityIdentifier().add(bioId);
				}
			}
			person
					.setAdministrativeGenderCode(studySubjectDemographics
							.getAdministrativeGenderCode() != null ? iso.CD(studySubjectDemographics
							.getAdministrativeGenderCode()) : iso.CD(
							NullFlavor.NI));
			person.setBirthDate(convertToTsDateTime(studySubjectDemographics.getBirthDate()));
			person.setEthnicGroupCode(getEthnicGroupCode(studySubjectDemographics));
			person
					.setMaritalStatusCode(studySubjectDemographics.getMaritalStatusCode() != null ? iso.CD(
							studySubjectDemographics.getMaritalStatusCode())
							: iso.CD(NullFlavor.NI));
			person.setName(getName(studySubjectDemographics));
			person.setPostalAddress(getPostalAddress(Arrays.asList(studySubjectDemographics.getAddress())));
			person.setRaceCode(getRaceCodes(studySubjectDemographics));
			person.setTelecomAddress(getTelecomAddress(studySubjectDemographics));
		}
		return person;
	}

	public void convertToSubjectDemographics(
			StudySubjectDemographics studySubjectDemographics, Subject subject) {
		if (subject != null && subject.getEntity() != null) {
			try {
				Person person = (Person) subject.getEntity();
				// gender
				CD gender = person.getAdministrativeGenderCode();
				studySubjectDemographics
						.setAdministrativeGenderCode(!isNull(gender) ? gender
								.getCode() : null);
				// birth date
				studySubjectDemographics.setBirthDate(convertToDate(person.getBirthDate()));
				studySubjectDemographics.setEthnicGroupCode(getEthnicGroupCode(person));
				studySubjectDemographics.setMaritalStatusCode(!isNull(person
						.getMaritalStatusCode()) ? person
						.getMaritalStatusCode().getCode() : null);
				studySubjectDemographics.setFirstName(getFirstName(person));
				studySubjectDemographics.setLastName(getLastName(person));
				studySubjectDemographics.setMiddleName(getMiddleName(person));
				studySubjectDemographics.setMaidenName(StringUtils.EMPTY);
				studySubjectDemographics.setAddress((Address)getAddresses(person).toArray()[0]);
				studySubjectDemographics.setRaceCodes(getRaceCodes(person));
				updateContactMechanism(studySubjectDemographics, person);
			} catch (IllegalArgumentException e) {
				throw exceptionHelper.getConversionException(
						INVALID_SUBJECT_DATA_REPRESENTATION, new Object[] { e
								.getMessage() });
			}
		} else {
			throw exceptionHelper
					.getConversionException(NO_SUBJECT_DATA_PROVIDED_CODE);
		}
	}
	
	protected StudySubjectProtocolVersionRelationship getStudySubjectProtocolVersion(StudySubjectStudyVersion studySubjectStudyVersion){
		StudySubjectProtocolVersionRelationship studySubjectProtocolVersion = new StudySubjectProtocolVersionRelationship();
		StudySite studySite = studySubjectStudyVersion.getStudySiteStudyVersion().getStudySite();
		studySubjectProtocolVersion.setStudySiteProtocolVersion(new StudySiteProtocolVersionRelationship());
		
		//setup study
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudyProtocolVersion(new StudyProtocolVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().setStudyProtocolDocument(new StudyProtocolDocumentVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicTitle(iso.ST(studySite.getStudy().getLongTitleText()));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setOfficialTitle(iso.ST(studySite.getStudy().getShortTitleText()));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicDescription(iso.ST(studySite.getStudy().getDescriptionText()));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setDocument(new Document());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().addAll(convertToDocumentIdentifier(studySite.getStudy().getIdentifiers()));
		
		//setup studysite
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudySite(new edu.duke.cabig.c3pr.webservice.common.StudySite());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().setOrganization(new Organization());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier().addAll(convertToOrganizationIdentifier(studySite.getHealthcareSite().getIdentifiersAssignedToOrganization()));
		
		studySubjectProtocolVersion.getStudySubjectConsentVersion().addAll(convertToSubjectConsent(studySubjectStudyVersion.getStudySubjectConsentVersions()));
		return studySubjectProtocolVersion;
	}
	
	public List<PerformedStudySubjectMilestone> convertToStudySubjectRegistryStatus(List<StudySubjectRegistryStatus> statuses){
		List<PerformedStudySubjectMilestone> result = new ArrayList<PerformedStudySubjectMilestone>();
		for(StudySubjectRegistryStatus studySubjectRegistryStatus : statuses){
			PerformedStudySubjectMilestone target = new PerformedStudySubjectMilestone();
			target.setStatusCode(iso.CD(studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode()));
			target.setStatusDate(convertToTsDateTime(studySubjectRegistryStatus.getEffectiveDate()));
			target.setComment(StringUtils.isEmpty(studySubjectRegistryStatus.getCommentText()) ? null : iso.ST(studySubjectRegistryStatus.getCommentText()));
			if(CollectionUtils.isNotEmpty(studySubjectRegistryStatus.getReasons())){
				target.setReasonCode(new DSETCD());
				for(RegistryStatusReason reason : studySubjectRegistryStatus.getReasons()){
					target.getReasonCode().getItem().add(iso.CD(reason.getCode()));
				}
			}
			result.add(target);
		}
		return result;
	}
	
	protected DSETCD getEthnicGroupCode(StudySubjectDemographics p) {
		DSETCD dsetcd = new DSETCD();
		if (StringUtils.isNotBlank(p.getEthnicGroupCode())) {
			dsetcd.getItem().add(iso.CD(p.getEthnicGroupCode()));
		} else {
			dsetcd.setNullFlavor(NullFlavor.NI);
		}
		return dsetcd;
	}
	
	protected DSETENPN getName(StudySubjectDemographics p) {
		DSETENPN dsetenpn = new DSETENPN();
		ENPN enpn = new ENPN();
		if (StringUtils.isNotBlank(p.getFirstName()))
			enpn.getPart()
					.add(
							iso.ENXP(p.getFirstName(), EntityNamePartType
									.valueOf(GIV)));
		if (StringUtils.isNotBlank(p.getMiddleName()))
			enpn.getPart()
					.add(
							iso.ENXP(p.getMiddleName(), EntityNamePartType
									.valueOf(GIV)));
		if (StringUtils.isNotBlank(p.getLastName()))
			enpn.getPart().add(
					iso.ENXP(p.getLastName(), EntityNamePartType.valueOf(FAM)));
		dsetenpn.getItem().add(enpn);
		return dsetenpn;
	}
	
	protected DSETCD getRaceCodes(StudySubjectDemographics p) {
		DSETCD dsetcd = new DSETCD();
		for (RaceCodeEnum raceCode : p.getRaceCodes()) {
			dsetcd.getItem().add(iso.CD(raceCode.getCode()));
		}
		return dsetcd;
	}

	public List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> convertToSubjectConsent(
			List<StudySubjectConsentVersion> studySubjectConsentVersions) {
		List<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion> returnList = new ArrayList<edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion>();
		for(StudySubjectConsentVersion studySubjectConsentVersion : studySubjectConsentVersions){
			returnList.add(convertToSubjectConsent(studySubjectConsentVersion));
		}
		return returnList;
	}
	
	public edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion convertToSubjectConsent(StudySubjectConsentVersion studySubjectConsentVersion){
		edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion target = new edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion();
		if(!StringUtils.isBlank(studySubjectConsentVersion.getDocumentId())){
			target.setIdentifier(iso.II(studySubjectConsentVersion.getDocumentId()));
		}
		if(studySubjectConsentVersion.getConsentDeliveryDate() != null){
			target.setConsentDeliveryDate(convertToTsDateTime(studySubjectConsentVersion.getConsentDeliveryDate()));
		}
		if(studySubjectConsentVersion.getConsentDeclinedDate() != null){
			target.setConsentDeclinedDate(convertToTsDateTime(studySubjectConsentVersion.getConsentDeclinedDate()));
		}
		if(studySubjectConsentVersion.getConsentingMethod() != null){
			target.setConsentingMethod(iso.CD(studySubjectConsentVersion.getConsentingMethod().getCode()));
		}
		target.setConsentPresenter(iso.ST(studySubjectConsentVersion.getConsentPresenter()));
		target.setInformedConsentDate(convertToTsDateTime(studySubjectConsentVersion.getInformedConsentSignedDate()));
		edu.duke.cabig.c3pr.webservice.common.Consent consent = new edu.duke.cabig.c3pr.webservice.common.Consent();
		consent.setOfficialTitle(iso.ST(studySubjectConsentVersion.getConsent().getName()));
		consent.setText(iso.ED(studySubjectConsentVersion.getConsent().getDescriptionText()));
		consent.setVersionNumberText(iso.ST(studySubjectConsentVersion.getConsent().getVersionId()));
		consent.setMandatoryIndicator(iso.BL(studySubjectConsentVersion.getConsent().getMandatoryIndicator()));
		target.setConsent(consent);
		for(SubjectConsentQuestionAnswer subjectAnswer : studySubjectConsentVersion.getSubjectConsentAnswers()){
			PerformedStudySubjectMilestone subjectAnswerTarget = new PerformedStudySubjectMilestone();
			subjectAnswerTarget.setMissedIndicator(iso.BL(subjectAnswer.getAgreementIndicator()));
			subjectAnswerTarget.setConsentQuestion(new DocumentVersion());
			subjectAnswerTarget.getConsentQuestion().setOfficialTitle(iso.ST(subjectAnswer.getConsentQuestion().getCode()));
			target.getSubjectConsentAnswer().add(subjectAnswerTarget);
		}
		return target;
	}
}

package edu.duke.cabig.c3pr.webservice.subjectregistration.coverters;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;

import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.DiseaseHistory;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.StudySubjectStudyVersion;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.domain.SubjectEligibilityAnswer;
import edu.duke.cabig.c3pr.domain.SubjectStratificationAnswer;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DefinedActivity;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.Drug;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PerformedActivity;
import edu.duke.cabig.c3pr.webservice.common.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.StudySiteProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter;
import edu.duke.cabig.c3pr.webservice.helpers.ISO21090Helper;
import edu.duke.cabig.c3pr.webservice.iso21090.AD;
import edu.duke.cabig.c3pr.webservice.iso21090.ADXP;
import edu.duke.cabig.c3pr.webservice.iso21090.ANY;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.BAGTEL;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETAD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENXP;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TEL;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedEligibilityCriterion;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedInclusionCriterion;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedExclusionCriterion;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedStratificationCriterion;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedStratificationCriterionPermissibleResult;
import edu.duke.cabig.c3pr.webservice.subjectregistration.Epoch;
import edu.duke.cabig.c3pr.webservice.subjectregistration.HealthcareProvider;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedDiagnosis;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedObservationResult;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ScheduledEpoch;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyCondition;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyInvestigator;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubjectProtocolVersionRelationship;

/**
 * Default implementation of {@link JAXBToDomainObjectConverter}.
 * 
 * @author dkrylov
 * 
 */
public class SubjectRegistrationJAXBToDomainObjectConverterImpl implements
		SubjectRegistrationJAXBToDomainObjectConverter  {

	private static final String SEMICOLON = ":";
	static final String X_TEXT_FAX = "x-text-fax";
	static final String TEL = "tel";
	static final String MAILTO = "mailto";
	private static final String NAME_SEP = " ";
	public static final String FAM = "FAM";
	public static final String GIV = "GIV";
	private static final String CTEP = "CTEP";
	private static final String NCI = "NCI";
	
	static final int SUBJECT_IDENTIFIER_MISSING_ORGANIZATION = 903;
	static final int NO_SUBJECT_DATA_PROVIDED_CODE = 900;
	static final int INVALID_SUBJECT_DATA_REPRESENTATION = 901;
	static final int MISSING_ELEMENT = 909;
	static final int WRONG_RACE_CODE = 907;
	static final int MISSING_IDENTIFIER = 917;
	static final int IDENTIFIER_MISSING_ORGANIZATION = 918;
	static final int ORGANIZATION_IDENTIFIER_MISSING_TYPECODE = 904;
	static final int UNABLE_TO_FIND_ORGANIZATION = 905;
	static final String TS_DATETIME_PATTERN = "yyyyMMddHHmmss";
	static final int WRONG_DATE_FORMAT = 906;
	static final int MISSING_OR_INVALID_CONSENT_NAME = 919;
	static final int INVALID_CONSENT_QUESTION_ANSWER_AGREEMENTINDICATOR = 920;
	static final int INVALID_CONSENT_QUESTION = 921;
	static final int MISSING_STATUS_CODE = 922;
	static final int MISSING_STATUS_DATE = 923;
	
	/** The exception helper. */
	private C3PRExceptionHelper exceptionHelper;
	private HealthcareSiteDao healthcareSiteDao;
	
	private static final ISO21090Helper iso = new ISO21090Helper();
	
	private static Log log = LogFactory
			.getLog(SubjectRegistrationJAXBToDomainObjectConverterImpl.class);

	public C3PRExceptionHelper getExceptionHelper() {
		return exceptionHelper;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}
	
	public List<Identifier> convertBiologicIdentifiers(
			List<BiologicEntityIdentifier> biologicIdentifiers) {
		List<Identifier> identifiers = new ArrayList<Identifier>();
		for(BiologicEntityIdentifier bioId : biologicIdentifiers){
			final II ii = bioId.getIdentifier();
			final CD typeCode = bioId.getTypeCode();
			Organization org = bioId.getAssigningOrganization();
			if (ii == null || typeCode == null) {
				throw exceptionHelper
						.getConversionException(MISSING_IDENTIFIER);
			}
			if (org == null) {
				throw exceptionHelper
						.getConversionException(IDENTIFIER_MISSING_ORGANIZATION);
			}
			OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
			id.setPrimaryIndicator(bioId.getPrimaryIndicator().isValue());
			id.setValue(ii.getExtension());
			id.setType(OrganizationIdentifierTypeEnum.valueOf(typeCode.getCode()));
			HealthcareSite healthcareSite = resolveHealthcareSite(org);
			id.setHealthcareSite(healthcareSite);
			identifiers.add(id);
		}
		return identifiers;
	}

	public List<Identifier> convertDocumentIdentifiers(
			List<DocumentIdentifier> documentIdentifiers) {
		List<Identifier> identifiers = new ArrayList<Identifier>();
		for(DocumentIdentifier docId : documentIdentifiers){
			final II ii = docId.getIdentifier();
			final CD typeCode = docId.getTypeCode();
			Organization org = docId.getAssigningOrganization();
			if (ii == null || typeCode == null) {
				throw exceptionHelper
						.getConversionException(MISSING_IDENTIFIER);
			}
			if (org == null) {
				throw exceptionHelper
						.getConversionException(IDENTIFIER_MISSING_ORGANIZATION);
			}
			OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
			id.setPrimaryIndicator(docId.getPrimaryIndicator().isValue());
			id.setValue(ii.getExtension());
			id.setType(OrganizationIdentifierTypeEnum.valueOf(typeCode.getCode()));
			HealthcareSite healthcareSite = resolveHealthcareSite(org);
			id.setHealthcareSite(healthcareSite);
			identifiers.add(id);
		}
		return identifiers;
	}
	
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
	
	private List<SubjectIdentifier> convertToSubjectIdentifier(
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
	
	private List<DocumentIdentifier> convertToDocumentIdentifier(
			List<Identifier> identifiers) {
		List<DocumentIdentifier> result = new ArrayList<DocumentIdentifier>();
		for (Identifier source : identifiers) {
			if (source instanceof OrganizationAssignedIdentifier) {
				DocumentIdentifier id = new DocumentIdentifier();
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
	
	private List<OrganizationIdentifier> convertToOrganizationIdentifier(
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
		studySubject.setStatusCode(iso.CD(domainObject.getRegWorkflowStatus().getCode()));
		
		//copy identifiers
		studySubject.getSubjectIdentifier().addAll(convertToSubjectIdentifier(domainObject.getIdentifiers()));
		
		//set studySubjectProtocolVersion
		studySubject.setStudySubjectProtocolVersion(getStudySubjectProtocolVersion(domainObject.getStudySubjectStudyVersion()));
		
		//set Disease History
		studySubject.setDiseaseHistory(convertToDiseaseHistory(domainObject.getDiseaseHistory()));
		
		//set Treating Physician
		if(domainObject.getTreatingPhysician()!=null || !StringUtils.isBlank(domainObject.getOtherTreatingPhysician())){
			studySubject.setTreatingPhysician(convertToStudyInvestigator(domainObject));
		}
		
		//set childstudysubjects
		for(edu.duke.cabig.c3pr.domain.StudySubject childDomainObject : domainObject.getChildStudySubjects()){
			studySubject.getChildStudySubject().add(convert(childDomainObject));
		}
		
		return studySubject;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverter
	 * #convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.
	 * AdvanceSearchCriterionParameter)
	 */
	public AdvancedSearchCriteriaParameter convert(
			AdvanceSearchCriterionParameter param) {
		String contextObjectName = (isNull(param.getObjectContextName()) || StringUtils
				.isBlank(param.getObjectContextName().getValue())) ? StringUtils.EMPTY : param
				.getObjectContextName().getValue();
		String objectName = convertAndErrorIfBlank(param.getObjectName(),
				"objectName");
		String attributeName = convertAndErrorIfBlank(param.getAttributeName(),
				"attributeName");
		String predicate = convertAndErrorIfBlank(param.getPredicate(),
				"predicate");
		List<String> values = new ArrayList<String>();
		for (ST st : param.getValues().getItem()) {
			values.add(st.getValue());
		}
		return AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(objectName,
						contextObjectName, attributeName, values, predicate);
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
			person.setPostalAddress(getPostalAddress(studySubjectDemographics));
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
				studySubjectDemographics.setAddress(getAddress(person));
				studySubjectDemographics.setRaceCodes(getRaceCodes(person));
				studySubjectDemographics.setEmail(getTelecomAddress(person, MAILTO));
				studySubjectDemographics.setPhone(getTelecomAddress(person, TEL));
				studySubjectDemographics.setFax(getTelecomAddress(person, X_TEXT_FAX));
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
	
	private StudySubjectProtocolVersionRelationship getStudySubjectProtocolVersion(StudySubjectStudyVersion studySubjectStudyVersion){
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
		studySubjectProtocolVersion.getScheduledEpoch().addAll(convertToScheduledEpochs(studySubjectStudyVersion.getScheduledEpochs()));
		return studySubjectProtocolVersion;
	}
	
	private String getEthnicGroupCode(Person person) {
		DSETCD codes = person.getEthnicGroupCode();
		return getFirstCode(codes);
	}
	
	/**
	 * @param codes
	 * @return
	 */
	private String getFirstCode(DSETCD codes) {
		String code = null;
		if (codes != null && CollectionUtils.isNotEmpty(codes.getItem())) {
			code = codes.getItem().get(0).getCode();
		}
		return code;
	}
	
	private String getMiddleName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.GIV);
		if (CollectionUtils.isNotEmpty(list) && list.size() > 1) {
			name = list.get(1);
		}
		return name;
	}
	
	private String getLastName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.FAM);
		if (CollectionUtils.isNotEmpty(list)) {
			name = StringUtils.join(list, NAME_SEP);
		}
		return name;
	}
	
	private String getFirstName(Person person) {
		String name = "";
		List<String> list = extractNameParts(person, EntityNamePartType.GIV);
		if (CollectionUtils.isNotEmpty(list)) {
			name = list.get(0);
		}
		return name;
	}

	private List<String> extractNameParts(Person person, EntityNamePartType type) {
		List<String> list = new ArrayList<String>();
		DSETENPN parts = person.getName();
		if (parts != null && CollectionUtils.isNotEmpty(parts.getItem())) {
			ENPN nameEntry = parts.getItem().get(0);
			if (nameEntry.getPart() != null) {
				for (ENXP nameEntryPart : nameEntry.getPart()) {
					if (type.equals(nameEntryPart.getType())) {
						list.add(nameEntryPart.getValue());
					}
				}
			}
		}
		return list;
	}
	
	private Address getAddress(Person person) {
		for (AD addr : person.getPostalAddress().getItem()) {
			if (!isNull(addr)) {
				Address address = new Address();
				address.setCity(getCity(addr));
				address.setCountryCode(getCountry(addr));
				address.setPostalCode(getZip(addr));
				address.setStateCode(getState(addr));
				address.setStreetAddress(getStreet(addr));
				return address;
			}
		}
		return null;
	}
	
	private String getCity(AD ad) {
		String city = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.CTY)) {
				city = adXp.getValue();
			}
		}
		return city;
	}

	private String getCountry(AD ad) {
		String ctry = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.CNT)) {
				ctry = adXp.getValue();
			}
		}
		return ctry;
	}

	private String getState(AD ad) {
		String state = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.STA)) {
				state = adXp.getValue();
			}
		}
		return state;
	}

	private String getStreet(AD ad) {
		String street = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.AL)
					|| adXp.getType().equals(AddressPartType.SAL)) {
				street = adXp.getValue();
			}
		}
		return street;
	}

	private String getZip(AD ad) {
		String zip = null;
		List<ADXP> adXps = ad.getPart();
		for (ADXP adXp : adXps) {
			if (adXp.getType().equals(AddressPartType.ZIP)) {
				zip = adXp.getValue();
			}
		}
		return zip;
	}
	
	private String getTelecomAddress(Person person, String type) {
		type = type.toLowerCase();
		String addr = null;
		BAGTEL bagtel = person.getTelecomAddress();
		if (!isNull(bagtel) && bagtel.getItem() != null) {
			for (TEL tel : bagtel.getItem()) {
				if (tel.getValue() != null
						&& tel.getValue().toLowerCase().startsWith(type)) {
					addr = tel.getValue().toLowerCase().replaceFirst(
							"^" + type + SEMICOLON, "");
				}
			}
		}
		return addr;
	}

	/**
	 * @param person
	 * @return
	 */
	private List<RaceCodeEnum> getRaceCodes(Person person) {
		List<RaceCodeEnum> list = new ArrayList<RaceCodeEnum>();
		DSETCD dsetcd = person.getRaceCode();
		if (!isNull(dsetcd) && dsetcd.getItem() != null) {
			for (CD cd : dsetcd.getItem()) {
				String raceCodeStr = cd.getCode();
				RaceCodeEnum raceCode = RaceCodeEnum.getByCode(raceCodeStr);
				if (raceCode != null) {
					list.add(raceCode);
				} else {
					throw exceptionHelper.getConversionException(
							WRONG_RACE_CODE, new Object[] { raceCodeStr });
				}
			}
		}
		return list;
	}
	
	public Date convertToDate(TSDateTime tsDateTime) {
		try {
			if (tsDateTime != null && tsDateTime.getNullFlavor() == null) {
				String value = tsDateTime.getValue();
				if (value != null) {
					return DateUtils.parseDate(value,
							new String[] { TS_DATETIME_PATTERN });
				}
			}
		} catch (ParseException e) {
			throw exceptionHelper.getConversionException(WRONG_DATE_FORMAT,
					new Object[] { tsDateTime.getValue() });
		}
		return null;
	}
	
	private TSDateTime convertToTsDateTime(Date date) {
		TSDateTime tsDateTime = new TSDateTime();
		if (date != null) {
			tsDateTime.setValue(DateFormatUtils.format(date,
					TS_DATETIME_PATTERN));
		} else {
			tsDateTime.setNullFlavor(NullFlavor.NI);
		}
		return tsDateTime;
	}
	
	private DSETCD getEthnicGroupCode(StudySubjectDemographics p) {
		DSETCD dsetcd = new DSETCD();
		if (StringUtils.isNotBlank(p.getEthnicGroupCode())) {
			dsetcd.getItem().add(iso.CD(p.getEthnicGroupCode()));
		} else {
			dsetcd.setNullFlavor(NullFlavor.NI);
		}
		return dsetcd;
	}
	
	private DSETENPN getName(StudySubjectDemographics p) {
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
	
	private BAGTEL getTelecomAddress(StudySubjectDemographics p) {
		BAGTEL addr = new BAGTEL();
		if (StringUtils.isNotBlank(p.getEmail()))
			addr.getItem().add(iso.TEL(MAILTO + SEMICOLON + p.getEmail()));
		if (StringUtils.isNotBlank(p.getPhone()))
			addr.getItem().add(iso.TEL(TEL + SEMICOLON + p.getPhone()));
		if (StringUtils.isNotBlank(p.getFax()))
			addr.getItem().add(iso.TEL(X_TEXT_FAX + SEMICOLON + p.getFax()));
		return addr;
	}

	private DSETCD getRaceCodes(StudySubjectDemographics p) {
		DSETCD dsetcd = new DSETCD();
		for (RaceCodeEnum raceCode : p.getRaceCodes()) {
			dsetcd.getItem().add(iso.CD(raceCode.getCode()));
		}
		return dsetcd;
	}

	/**
	 * @param p
	 * @return
	 */
	private DSETAD getPostalAddress(StudySubjectDemographics p) {
		DSETAD set = new DSETAD();
		Address address = p.getAddress();
		AD ad = new AD();
		if (address != null && !address.isBlank()) {
			if (StringUtils.isNotBlank(address.getStreetAddress()))
				ad.getPart().add(
						iso.ADXP(address.getStreetAddress(),
								AddressPartType.SAL));
			if (StringUtils.isNotBlank(address.getCity()))
				ad.getPart().add(
						iso.ADXP(address.getCity(), AddressPartType.CTY));
			if (StringUtils.isNotBlank(address.getStateCode()))
				ad.getPart().add(
						iso.ADXP(address.getStateCode(),
								AddressPartType.STA));
			if (StringUtils.isNotBlank(address.getPostalCode()))
				ad.getPart().add(
						iso.ADXP(address.getPostalCode(),
								AddressPartType.ZIP));
			if (StringUtils.isNotBlank(address.getCountryCode()))
				ad.getPart().add(
						iso.ADXP(address.getCountryCode(),
								AddressPartType.CNT));
		} else {
			ad.setNullFlavor(NullFlavor.NI);
		}
		set.getItem().add(ad);
		return set;
	}
	
	private String convertAndErrorIfBlank(ST st, String elementName) {
		if (isNull(st) || StringUtils.isBlank(st.getValue())) {
			throw exceptionHelper.getConversionException(MISSING_ELEMENT,
					new Object[] { elementName });
		} else {
			return st.getValue();
		}
	}

	private String convertAndErrorIfBlank(CD st, String elementName) {
		if (isNull(st) || StringUtils.isBlank(st.getCode())) {
			throw exceptionHelper.getConversionException(MISSING_ELEMENT,
					new Object[] { elementName });
		} else {
			return st.getCode();
		}
	}
	
	private boolean isNull(ANY cd) {
		return cd == null || cd.getNullFlavor() != null;
	}
	
	private HealthcareSite resolveHealthcareSite(Organization org) {
		List<OrganizationIdentifier> idList = org.getOrganizationIdentifier();
		if (CollectionUtils.isEmpty(idList)) {
			throw exceptionHelper
					.getConversionException(SUBJECT_IDENTIFIER_MISSING_ORGANIZATION);
		}
		OrganizationIdentifier orgId = idList.get(0);
		II id = orgId.getIdentifier();
		BL isPrimary = orgId.getPrimaryIndicator();
		CD typeCode = orgId.getTypeCode();
		if (id == null || StringUtils.isBlank(id.getExtension())) {
			throw exceptionHelper
					.getConversionException(SUBJECT_IDENTIFIER_MISSING_ORGANIZATION);
		}
		if (isPrimary != null && isPrimary.isValue()) {
			return healthcareSiteDao.getByPrimaryIdentifier(id.getExtension());
		}
		if (typeCode == null || StringUtils.isBlank(typeCode.getCode())) {
			throw exceptionHelper
					.getConversionException(ORGANIZATION_IDENTIFIER_MISSING_TYPECODE);
		}
		if (typeCode.getCode().contains(CTEP)) {
			return healthcareSiteDao.getByCtepCodeFromLocal(id.getExtension());
		} else if (typeCode.getCode().contains(NCI)) {
			return healthcareSiteDao.getByNciCodeFromLocal(id.getExtension());
		}
		throw exceptionHelper
				.getConversionException(UNABLE_TO_FIND_ORGANIZATION);
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
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
		target.setConsentDeliveryDate(convertToTsDateTime(studySubjectConsentVersion.getConsentDeliveryDate()));
		target.setConsentingMethod(iso.CD(studySubjectConsentVersion.getConsentingMethod().getCode()));
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
	
	public List<ScheduledEpoch> convertToScheduledEpochs(
			List<edu.duke.cabig.c3pr.domain.ScheduledEpoch> scheduledEpochs) {
		List<ScheduledEpoch> returnList = new ArrayList<ScheduledEpoch>();
		for(edu.duke.cabig.c3pr.domain.ScheduledEpoch scheduledEpoch : scheduledEpochs){
			returnList.add(convertToScheduledEpoch(scheduledEpoch));
		}
		return returnList;
	}
	
	public ScheduledEpoch convertToScheduledEpoch(
			edu.duke.cabig.c3pr.domain.ScheduledEpoch scheduledEpoch) {
		ScheduledEpoch covertedScheduledEpoch = new ScheduledEpoch();
		covertedScheduledEpoch.setStartDate(convertToTsDateTime(scheduledEpoch.getStartDate()));
		covertedScheduledEpoch.setOffEpochDate(convertToTsDateTime(scheduledEpoch.getOffEpochDate()));
		covertedScheduledEpoch.setStatus(iso.CD(scheduledEpoch.getScEpochWorkflowStatus().getCode()));
		covertedScheduledEpoch.setStratumGroupNumber(iso.INTPositive(scheduledEpoch.getStratumGroupNumber()));
		covertedScheduledEpoch.setEpoch(convertToEpoch(scheduledEpoch.getEpoch()));
		covertedScheduledEpoch.setScheduledArm(convertToScheduledArm(scheduledEpoch.getScheduledArm()));
		if(!CollectionUtils.isEmpty(scheduledEpoch.getOffEpochReasons())){
			covertedScheduledEpoch.setOffEpochReason(new DSETCD());
			for(OffEpochReason offEpochReason : scheduledEpoch.getOffEpochReasons()){
				covertedScheduledEpoch.getOffEpochReason().getItem().add(iso.CD(offEpochReason.getReason().getCode()));
			}
		}
		covertedScheduledEpoch.getSubjectEligibilityAnswer().addAll(convertToSubjectEligibilityAnswers(scheduledEpoch.getSubjectEligibilityAnswers()));
		covertedScheduledEpoch.getSubjectStartificationAnswer().addAll(convertToSubjectStratificationAnswers(scheduledEpoch.getSubjectStratificationAnswers()));
		return covertedScheduledEpoch;
	}
	
	public Epoch convertToEpoch(edu.duke.cabig.c3pr.domain.Epoch epoch){
		Epoch convertedEpoch = new Epoch();
		convertedEpoch.setDescription(iso.ST(epoch.getDescriptionText()));
		convertedEpoch.setName(iso.ST(epoch.getName()));
		convertedEpoch.setSequenceNumber(iso.INTPositive(epoch.getEpochOrder()));
		convertedEpoch.setTypeCode(iso.CD(epoch.getType().getCode()));
		return convertedEpoch;
	}
	
	public PerformedActivity convertToScheduledArm(ScheduledArm scheduledArm){
		PerformedActivity convertedArm = new PerformedActivity();
		if(scheduledArm.getArm() !=null){
			DefinedActivity arm = new DefinedActivity();
			arm.setNameCode(iso.CD(scheduledArm.getArm().getName()));
			convertedArm.setDefinedActivity(arm);
		}else if(!StringUtils.isBlank(scheduledArm.getKitNumber())){
			Drug drug = new Drug();
			drug.setKitNumber(iso.II(scheduledArm.getKitNumber()));
			convertedArm.setDrug(drug);
		}
		return convertedArm;
	}
	
	public List<PerformedObservationResult> convertToSubjectEligibilityAnswers(List<SubjectEligibilityAnswer> answers){
		List<PerformedObservationResult> convertedAnswers = new ArrayList<PerformedObservationResult>();
		for(SubjectEligibilityAnswer answer : answers){
			convertedAnswers.add(convertToSubjectEligibilityAnswer(answer));
		}
		return convertedAnswers;
	}
	
	public PerformedObservationResult convertToSubjectEligibilityAnswer(SubjectEligibilityAnswer answer){
		PerformedObservationResult convertedAnswer = new PerformedObservationResult();
		DefinedEligibilityCriterion eligibility = null;
		if(answer.getEligibilityCriteria() instanceof InclusionEligibilityCriteria){
			eligibility = new DefinedInclusionCriterion();
		}else{
			eligibility = new DefinedExclusionCriterion();
		}
		eligibility.setNameCode(iso.CD(answer.getEligibilityCriteria().getQuestionText()));
		eligibility.setDescription(iso.ST(answer.getEligibilityCriteria().getQuestionText()));
		if(answer.getEligibilityCriteria().getNotApplicableIndicator() == null){
			eligibility.setRequiredResponse(iso.BL(NullFlavor.NI));
		}else{
			eligibility.setRequiredResponse(iso.BL(answer.getEligibilityCriteria().getNotApplicableIndicator()));
		}
		convertedAnswer.setEligibilityCriterion(eligibility);
		convertedAnswer.setResult(iso.ST(answer.getAnswerText()));
		return convertedAnswer;
	}
	
	public List<PerformedObservationResult> convertToSubjectStratificationAnswers(List<SubjectStratificationAnswer> answers){
		List<PerformedObservationResult> convertedAnswers = new ArrayList<PerformedObservationResult>();
		for(SubjectStratificationAnswer answer : answers){
			convertedAnswers.add(convertToSubjectStratificationAnswer(answer));
		}
		return convertedAnswers;
	}
	
	public PerformedObservationResult convertToSubjectStratificationAnswer(SubjectStratificationAnswer answer){
		PerformedObservationResult convertedAnswer = new PerformedObservationResult();
		DefinedStratificationCriterion startification = new DefinedStratificationCriterion();
		startification.setNameCode(iso.CD(answer.getStratificationCriterion().getQuestionText()));
		startification.setDescription(iso.ST(answer.getStratificationCriterion().getQuestionText()));
		convertedAnswer.setStartificationCriterion(startification);
		DefinedStratificationCriterionPermissibleResult result = new DefinedStratificationCriterionPermissibleResult();
		result.setResult(iso.ST(answer.getStratificationCriterionAnswer().getPermissibleAnswer()));
		convertedAnswer.setStartificationCriterionPermissibleResult(result);
		return convertedAnswer;
	}
	
	public PerformedDiagnosis convertToDiseaseHistory(DiseaseHistory diseaseHistory){
		PerformedDiagnosis convertedDiseaseHistory = new PerformedDiagnosis();
		StudyCondition condition = new StudyCondition();
		condition.setConditionCode(iso.CD(diseaseHistory.getPrimaryDiseaseStr()));
		convertedDiseaseHistory.setDisease(condition);
		convertedDiseaseHistory.setTargetAnatomicSiteCode(iso.CD(diseaseHistory.getPrimaryDiseaseSiteStr()));
		return convertedDiseaseHistory;
	}
	
	public StudyInvestigator convertToStudyInvestigator(edu.duke.cabig.c3pr.domain.StudySubject domainObject){
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		Person investigator = new Person();
		studyInvestigator.setHealthcareProvider(new HealthcareProvider());
		studyInvestigator.getHealthcareProvider().setPerson(investigator);
		if(domainObject.getTreatingPhysician()!=null){
			Investigator inv = domainObject.getTreatingPhysician().getHealthcareSiteInvestigator().getInvestigator();
			DSETENPN dsetenpn = new DSETENPN();
			ENPN enpn = new ENPN();
			enpn.getPart().add(iso.ENXP(inv.getFirstName(), EntityNamePartType.valueOf(GIV)));
			enpn.getPart().add(iso.ENXP(inv.getLastName(), EntityNamePartType.valueOf(FAM)));
			dsetenpn.getItem().add(enpn);
			investigator.setName(dsetenpn);
			BAGTEL addr = new BAGTEL();
			addr.getItem().add(iso.TEL(MAILTO + SEMICOLON + inv.getEmail()));
			investigator.setTelecomAddress(addr);
			investigator.setAdministrativeGenderCode(iso.CD(NullFlavor.NI));
			investigator.setBirthDate(iso.TSDateTime(NullFlavor.NI));
			investigator.setEthnicGroupCode(new DSETCD());
			investigator.setMaritalStatusCode(iso.CD(NullFlavor.NI));
			investigator.setPostalAddress(new DSETAD());
			investigator.setRaceCode(new DSETCD());
			studyInvestigator.getHealthcareProvider().setIdentifier(iso.II(inv.getAssignedIdentifier()));
		}else{
			DSETENPN dsetenpn = new DSETENPN();
			ENPN enpn = new ENPN();
			enpn.getPart().add(iso.ENXP(domainObject.getOtherTreatingPhysician(), EntityNamePartType.valueOf(GIV)));
			dsetenpn.getItem().add(enpn);
			investigator.setName(dsetenpn);
			investigator.setAdministrativeGenderCode(iso.CD(NullFlavor.NI));
			investigator.setBirthDate(iso.TSDateTime(NullFlavor.NI));
			investigator.setEthnicGroupCode(new DSETCD());
			investigator.setMaritalStatusCode(iso.CD(NullFlavor.NI));
			investigator.setPostalAddress(new DSETAD());
			investigator.setRaceCode(new DSETCD());
			investigator.setTelecomAddress(new BAGTEL());
			studyInvestigator.getHealthcareProvider().setIdentifier(iso.II(NullFlavor.NA));
		}
		return studyInvestigator;
	}
	
}
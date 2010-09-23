package edu.duke.cabig.c3pr.webservice.helpers;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatus;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySiteStudyVersion;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.SubjectConsentQuestionAnswer;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import edu.duke.cabig.c3pr.webservice.iso21090.AD;
import edu.duke.cabig.c3pr.webservice.iso21090.ADXP;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.BAGTEL;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETAD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETST;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENXP;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.IVLTSDateTime;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TEL;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;
import edu.duke.cabig.c3pr.webservice.subjectregistry.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.subjectregistry.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectregistry.Document;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.subjectregistry.Organization;
import edu.duke.cabig.c3pr.webservice.subjectregistry.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectregistry.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.subjectregistry.Person;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySiteProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectregistry.convertes.SubjectRegistryJAXBToDomainObjectConverterImpl;

public class SubjectRegistryRelatedTestCase extends ApplicationTestCase {

	protected static final String BAD_STATE_CODE = "bad state code";
	protected static final String BAD_RACE_CODE = "invalid race code";
	protected static final String BAD_ISO_DATE = "1990-01-01";	
	protected static final String TS_DATETIME_PATTERN = "yyyyMMddHHmmss";
	protected static final String TEST_FAX = "555-555-5555";
	protected static final String TEST_FAX_ISO = "x-text-fax:" + TEST_FAX;
	protected static final String TEST_PHONE = "555-555-5555";
	protected static final String TEST_PHONE_ISO = "tel:" + TEST_PHONE;
	protected static final String TEST_EMAIL_ADDR = "johndoe@semanticbits.com";
	protected static final String TEST_EMAIL_ADDR_ISO = "mailto:"
			+ TEST_EMAIL_ADDR;
	protected static final String RACE_ASIAN = "Asian";
	protected static final String RACE_WHITE = "White";
	protected static final String TEST_COUNTRY = "USA";
	protected static final String TEST_ZIP_CODE = "22203-5555";
	protected static final String TEST_STATE_CODE = "VA";
	protected static final String TEST_CITY_NAME = "Arlington";
	protected static final String TEST_STREET_ADDRESS = "1029 N Stuart St Unit 999";
	protected static final String TEST_LAST_NAME = "Doe";
	protected static final String TEST_MID_NAME = "Z";
	protected static final String TEST_FIRST_NAME = "John";
	protected static final String MARITAL_STATUS_SINGLE = "Single";
	protected static final String ETHNIC_CODE_NOT_REPORTED = "Not Reported";
	protected static final String TEST_BIRTH_DATE_ISO = "19800101000000";
	protected static final Date TEST_BIRTH_DATE = parseISODate(TEST_BIRTH_DATE_ISO);
	protected static final String GENDER_MALE = "Male";
	protected static final String STATE_ACTIVE = "ACTIVE";
	protected static final String STATE_INACTIVE = "INACTIVE";
	protected static final String ORG_ID_TYPE_MRN = OrganizationIdentifierTypeEnum.MRN
			.getName();
	protected static final String ORG_ID_TYPE_STUDY = OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER
	.getName();
	protected static final String ORG_ID_TYPE_STUDYSUBJECT = OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER
	.getName();
	protected static final String TEST_BIO_ID = "001";
	protected static final Boolean TEST_BIO_ID_PRIMARYINDICATOR=true;
	protected static final String TEST_STUDYSUBJECT_ID = "002";
	protected static final Boolean TEST_STUDYSUBJECT_ID_PRIMARYINDICATOR=false;
	protected static final String TEST_STUDY_ID = "003";
	protected static final Boolean TEST_STUDY_ID_PRIMARYINDICATOR=true;
	protected static final String TEST_HEALTHCARESITE_ID = "004";
	protected static final Boolean TEST_HEALTHCARESITE_ID_PRIMARYINDICATOR=false;
	protected static final String ORG_ID_TYPE_CTEP = OrganizationIdentifierTypeEnum.CTEP
			.getName();
	protected static final String TEST_ORG_ID = "MN026";
	protected static final String TEST_CONSENT_DELIVERY_DATE1 = "20090101000000";
	protected static final String TEST_CONSENT_SIGNED_DATE1 = "20100101000000";
	protected static final String TEST_CONSENT_PRESENTER1 = "John Doe";
	protected static final String TEST_CONSENTING_METHOD1 = "Written";
	protected static final String TEST_CONSENT_NAME1 = "general1";
	protected static final String TEST_CONSENT_VERSION1 = "1.0";
	protected static final Boolean TEST_CONSENT_ANS11=true;
	protected static final Boolean TEST_CONSENT_ANS12=false;
	protected static final String TEST_CONSENT_QUES11="Q11";
	protected static final String TEST_CONSENT_QUES12="Q12";
	protected static final String TEST_CONSENT_DELIVERY_DATE2 = "20060101000000";
	protected static final String TEST_CONSENT_SIGNED_DATE2 = "20070101000000";
	protected static final String TEST_CONSENT_PRESENTER2 = "Deep Singh";
	protected static final String TEST_CONSENTING_METHOD2 = "Verbal";
	protected static final String TEST_CONSENT_NAME2 = "general2";
	protected static final String TEST_CONSENT_VERSION2 = "2.0";
	protected static final Boolean TEST_CONSENT_ANS21=true;
	protected static final Boolean TEST_CONSENT_ANS22=false;
	protected static final String TEST_CONSENT_QUES21="Q21";
	protected static final String TEST_CONSENT_QUES22="Q22";
	protected static final String TEST_REGISTRYSTATUS_CODE1="Enrolled";
	protected static final String TEST_REGISTRYSTATUS_DATE1 = "20080101000000";
	protected static final String TEST_REGISTRYSTATUS_REASON11 = "reason11";
	protected static final String TEST_REGISTRYSTATUS_REASON12 = "reason12";
	protected static final String TEST_REGISTRYSTATUS_CODE2="Off-Study";
	protected static final String TEST_REGISTRYSTATUS_DATE2 = "20070101000000";
	protected static final String TEST_REGISTRYSTATUS_REASON21 = "reason21";
	protected static final String TEST_REGISTRYSTATUS_REASON22 = "reason22";
	protected static final String TEST_PAYMENT_METHOD = "private insurance";
	protected static final String TEST_DATA_ENTRY_STATUS = "Incomplete";
	protected static final String TEST_SHORTTITLE = "short";
	protected static final String TEST_LONGTITLE = "long";
	protected static final String TEST_DESC = "desc";
	public static final String TEST_VALUE2 = "v2";
	public static final String TEST_VALUE1 = "v1";
	public static final String TEST_PREDICATE = "Predicate";
	public static final String TEST_OBJ_NAME = "objName";
	public static final String TEST_OBJ_CTX_NAME = "objCtxName";
	public static final String TEST_ATTRIBUTE_NAME = "attribute_name";
	
	protected static Date parseISODate(String isoDate) {
		try {
			return DateUtils.parseDate(isoDate,
					new String[] { TS_DATETIME_PATTERN });
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected SubjectRegistryJAXBToDomainObjectConverterImpl converter;

	protected HealthcareSiteDao healthcareSiteDao;

	protected HealthcareSite healthcareSite;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		healthcareSiteDao = createMock(HealthcareSiteDao.class);
		converter = new SubjectRegistryJAXBToDomainObjectConverterImpl();
		C3PRExceptionHelper exceptionHelper = new C3PRExceptionHelper(
				getMessageSourceMock());
		converter.setExceptionHelper(exceptionHelper);

		converter.setHealthcareSiteDao(healthcareSiteDao);

		healthcareSite = new LocalHealthcareSite();
		healthcareSite.setCtepCode(TEST_ORG_ID, true);
		expect(healthcareSiteDao.getByPrimaryIdentifier(TEST_ORG_ID))
				.andReturn(healthcareSite).anyTimes();
		replay(healthcareSiteDao);

	}
	
//	@Override
//	protected void tearDown() throws Exception {
//		verifyMocks();
//		super.tearDown();
//	}
	
	/**
	 * @return
	 */
	protected BiologicEntityIdentifier createBioEntityId() {
		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setAssigningOrganization(createOrganization());
		bioId.setIdentifier(new II(TEST_BIO_ID));
		bioId.setTypeCode(new CD(ORG_ID_TYPE_MRN));
		bioId.setEffectiveDateRange(new IVLTSDateTime(NullFlavor.NI));
		bioId.setPrimaryIndicator(new BL(TEST_BIO_ID_PRIMARYINDICATOR));
		return bioId;
	}
	
	/**
	 * @return
	 */
	protected DocumentIdentifier createDocumentId() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setAssigningOrganization(createOrganization());
		docId.setIdentifier(new II(TEST_STUDY_ID));
		docId.setTypeCode(new CD(ORG_ID_TYPE_STUDY));
		docId.setPrimaryIndicator(new BL(TEST_STUDY_ID_PRIMARYINDICATOR));
		return docId;
	}
	
	/**
	 * @return
	 */
	protected SubjectIdentifier createSubjectId() {
		SubjectIdentifier subId = new SubjectIdentifier();
		subId.setAssigningOrganization(createOrganization());
		subId.setIdentifier(new II(TEST_STUDYSUBJECT_ID));
		subId.setTypeCode(new CD(ORG_ID_TYPE_STUDYSUBJECT));
		subId.setPrimaryIndicator(new BL(TEST_STUDYSUBJECT_ID_PRIMARYINDICATOR));
		return subId;
	}
	
	/**
	 * @return
	 */
	protected MessageSource getMessageSourceMock() {
		return new MessageSource() {
			public String getMessage(String code, Object[] args,
					String defaultMessage, Locale locale) {
				// TODO Auto-generated method stub
				return "";
			}

			public String getMessage(String code, Object[] args, Locale locale)
					throws NoSuchMessageException {
				// TODO Auto-generated method stub
				return "";
			}

			public String getMessage(MessageSourceResolvable resolvable,
					Locale locale) throws NoSuchMessageException {
				// TODO Auto-generated method stub
				return "";
			}
		};
	}
	
	protected Organization createOrganization(){
		Organization organization = new Organization();
		OrganizationIdentifier oid = createOrgId();
		organization.getOrganizationIdentifier().add(oid);
		return organization;
	}
	
	protected OrganizationIdentifier createOrgId(){
		OrganizationIdentifier oid = new OrganizationIdentifier();
		oid.setIdentifier(new II(TEST_ORG_ID));
		oid.setPrimaryIndicator(new BL(true));
		oid.setTypeCode(new CD(ORG_ID_TYPE_CTEP));
		return oid;
	}
	
	protected void addSubjectConsent(StudySubjectProtocolVersionRelationship studySubjectProtocolVersionRelationship){
		//add 1st consent
		StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
		studySubjectConsentVersion.setConsentDeliveryDate(new TSDateTime(TEST_CONSENT_DELIVERY_DATE1));
		studySubjectConsentVersion.setInformedConsentDate(new TSDateTime(TEST_CONSENT_SIGNED_DATE1));
		studySubjectConsentVersion.setConsentingMethod(new CD(TEST_CONSENTING_METHOD1));
		studySubjectConsentVersion.setConsentPresenter(new ST(TEST_CONSENT_PRESENTER1));
		studySubjectConsentVersion.setConsent(new DocumentVersion());
		studySubjectConsentVersion.getConsent().setOfficialTitle(new ST(TEST_CONSENT_NAME1));
		studySubjectConsentVersion.getConsent().setVersionNumberText(new ST(TEST_CONSENT_VERSION1));
		
		PerformedStudySubjectMilestone subjectAnswer = new PerformedStudySubjectMilestone();
		subjectAnswer.setMissedIndicator(new BL(TEST_CONSENT_ANS11));
		subjectAnswer.setConsentQuestion(new DocumentVersion());
		subjectAnswer.getConsentQuestion().setOfficialTitle(new ST(TEST_CONSENT_QUES11));
		studySubjectConsentVersion.getSubjectConsentAnswer().add(subjectAnswer);
		subjectAnswer = new PerformedStudySubjectMilestone();
		subjectAnswer.setMissedIndicator(new BL(TEST_CONSENT_ANS12));
		subjectAnswer.setConsentQuestion(new DocumentVersion());
		subjectAnswer.getConsentQuestion().setOfficialTitle(new ST(TEST_CONSENT_QUES12));
		studySubjectConsentVersion.getSubjectConsentAnswer().add(subjectAnswer);
		
		studySubjectProtocolVersionRelationship.getStudySubjectConsentVersion().add(studySubjectConsentVersion);
		
		//add 2nd consent
		studySubjectConsentVersion = new StudySubjectConsentVersion();
		studySubjectConsentVersion.setConsentDeliveryDate(new TSDateTime(TEST_CONSENT_DELIVERY_DATE2));
		studySubjectConsentVersion.setInformedConsentDate(new TSDateTime(TEST_CONSENT_SIGNED_DATE2));
		studySubjectConsentVersion.setConsentingMethod(new CD(TEST_CONSENTING_METHOD2));
		studySubjectConsentVersion.setConsentPresenter(new ST(TEST_CONSENT_PRESENTER2));
		studySubjectConsentVersion.setConsent(new DocumentVersion());
		studySubjectConsentVersion.getConsent().setOfficialTitle(new ST(TEST_CONSENT_NAME2));
		studySubjectConsentVersion.getConsent().setVersionNumberText(new ST(TEST_CONSENT_VERSION2));
		
		subjectAnswer = new PerformedStudySubjectMilestone();
		subjectAnswer.setMissedIndicator(new BL(TEST_CONSENT_ANS21));
		subjectAnswer.setConsentQuestion(new DocumentVersion());
		subjectAnswer.getConsentQuestion().setOfficialTitle(new ST(TEST_CONSENT_QUES21));
		studySubjectConsentVersion.getSubjectConsentAnswer().add(subjectAnswer);
		subjectAnswer = new PerformedStudySubjectMilestone();
		subjectAnswer.setMissedIndicator(new BL(TEST_CONSENT_ANS22));
		subjectAnswer.setConsentQuestion(new DocumentVersion());
		subjectAnswer.getConsentQuestion().setOfficialTitle(new ST(TEST_CONSENT_QUES22));
		studySubjectConsentVersion.getSubjectConsentAnswer().add(subjectAnswer);
		
		studySubjectProtocolVersionRelationship.getStudySubjectConsentVersion().add(studySubjectConsentVersion);
	}
	
	protected void assertSubjectConsent(List<edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion> actual){
		assertEquals(parseISODate(TEST_CONSENT_DELIVERY_DATE1) ,actual.get(0).getConsentDeliveryDate());
		assertEquals(parseISODate(TEST_CONSENT_SIGNED_DATE1) ,actual.get(0).getInformedConsentSignedDate());
		assertEquals(TEST_CONSENT_PRESENTER1 ,actual.get(0).getConsentPresenter());
		assertEquals(TEST_CONSENTING_METHOD1 ,actual.get(0).getConsentingMethod().getCode());
		assertEquals(TEST_CONSENT_NAME1 ,actual.get(0).getConsent().getName());
		assertEquals(TEST_CONSENT_VERSION1 ,actual.get(0).getConsent().getVersionId());
		assertEquals(TEST_CONSENT_ANS11 ,actual.get(0).getSubjectConsentAnswers().get(0).getAgreementIndicator());
		assertEquals(TEST_CONSENT_QUES11 ,actual.get(0).getSubjectConsentAnswers().get(0).getConsentQuestion().getCode());
		assertEquals(TEST_CONSENT_ANS12 ,actual.get(0).getSubjectConsentAnswers().get(1).getAgreementIndicator());
		assertEquals(TEST_CONSENT_QUES12 ,actual.get(0).getSubjectConsentAnswers().get(1).getConsentQuestion().getCode());
		
		assertEquals(parseISODate(TEST_CONSENT_DELIVERY_DATE2) ,actual.get(1).getConsentDeliveryDate());
		assertEquals(parseISODate(TEST_CONSENT_SIGNED_DATE2) ,actual.get(1).getInformedConsentSignedDate());
		assertEquals(TEST_CONSENT_PRESENTER2 ,actual.get(1).getConsentPresenter());
		assertEquals(TEST_CONSENTING_METHOD2 ,actual.get(1).getConsentingMethod().getCode());
		assertEquals(TEST_CONSENT_NAME2 ,actual.get(1).getConsent().getName());
		assertEquals(TEST_CONSENT_VERSION2 ,actual.get(1).getConsent().getVersionId());
		assertEquals(TEST_CONSENT_ANS21 ,actual.get(1).getSubjectConsentAnswers().get(0).getAgreementIndicator());
		assertEquals(TEST_CONSENT_QUES21 ,actual.get(1).getSubjectConsentAnswers().get(0).getConsentQuestion().getCode());
		assertEquals(TEST_CONSENT_ANS22 ,actual.get(1).getSubjectConsentAnswers().get(1).getAgreementIndicator());
		assertEquals(TEST_CONSENT_QUES22 ,actual.get(1).getSubjectConsentAnswers().get(1).getConsentQuestion().getCode());
	}
	
	/**
	 * @return
	 */
	protected AdvanceSearchCriterionParameter createAdvaceSearchParam() {
		AdvanceSearchCriterionParameter param = new AdvanceSearchCriterionParameter();
		param.setAttributeName(new ST(TEST_ATTRIBUTE_NAME));
		param.setObjectContextName(new ST(TEST_OBJ_CTX_NAME));
		param.setObjectName(new ST(TEST_OBJ_NAME));
		param.setPredicate(new CD(TEST_PREDICATE));
		param.setValues(new DSETST(Arrays.asList(new ST[] {
				new ST(TEST_VALUE1), new ST(TEST_VALUE2) })));
		return param;
	}
	
	protected PerformedStudySubjectMilestone createStatus(){
		PerformedStudySubjectMilestone status = new PerformedStudySubjectMilestone();
		status.setStatusCode(new CD(TEST_REGISTRYSTATUS_CODE1));
		status.setStatusDate(new TSDateTime(TEST_REGISTRYSTATUS_DATE1));
		status.setReasonCode(new DSETCD());
		status.getReasonCode().getItem().add(new CD(TEST_REGISTRYSTATUS_REASON11));
		status.getReasonCode().getItem().add(new CD(TEST_REGISTRYSTATUS_REASON12));
		return status;
	}
	
	/**
	 * @return
	 */
	protected StudySubjectDemographics createSubjectDemographics() {
		StudySubjectDemographics s = new StudySubjectDemographics();

		OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
		id.setHealthcareSite(healthcareSite);
		id.setPrimaryIndicator(true);
		id.setType(OrganizationIdentifierTypeEnum.MRN);
		id.setValue(TEST_BIO_ID);
		s.addIdentifier(id);

		s.setAdministrativeGenderCode(GENDER_MALE);
		s.setBirthDate(TEST_BIRTH_DATE);
		s.setEthnicGroupCode(ETHNIC_CODE_NOT_REPORTED);
		s.setMaritalStatusCode(MARITAL_STATUS_SINGLE);
		s.setFirstName(TEST_FIRST_NAME);
		s.setMaidenName(StringUtils.EMPTY);
		s.setMiddleName(TEST_MID_NAME);
		s.setLastName(TEST_LAST_NAME);
		s.setAddress(new Address(TEST_STREET_ADDRESS, TEST_CITY_NAME,
				TEST_STATE_CODE, TEST_ZIP_CODE, TEST_COUNTRY));

		s.setRaceCodes(createRaceCodes());
		s.setEmail(TEST_EMAIL_ADDR);
		s.setPhone(TEST_PHONE);
		s.setFax(TEST_FAX);
		return s;
	}
	
	protected StudySubject createStudySubjectDomainObject(){
		StudySubject studySubject = new StudySubject();
		studySubject.setStudySubjectDemographics(createSubjectDemographics());
		studySubject.setPaymentMethod(TEST_PAYMENT_METHOD);
		studySubject.setRegDataEntryStatus(RegistrationDataEntryStatus.getByCode(TEST_DATA_ENTRY_STATUS));
		
		OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
		id.setHealthcareSite(healthcareSite);
		id.setPrimaryIndicator(true);
		id.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER);
		id.setValue(TEST_STUDYSUBJECT_ID);
		id.setPrimaryIndicator(TEST_STUDYSUBJECT_ID_PRIMARYINDICATOR);
		studySubject.addIdentifier(id);
		
		Study study = new LocalStudy();
		StudySite studySite = new StudySite();
		StudySiteStudyVersion studySiteStudyVersion = new StudySiteStudyVersion();
		studySiteStudyVersion.setStartDate(new Date());
		studySiteStudyVersion.setStudySite(studySite);
		studySite.addStudySiteStudyVersion(studySiteStudyVersion);
		studySite.setStudy(study);
		studySubject.setStudySite(studySite);
		study.setShortTitleText(TEST_SHORTTITLE);
		study.setLongTitleText(TEST_LONGTITLE);
		study.setDescriptionText(TEST_DESC);
		id = new OrganizationAssignedIdentifier();
		id.setHealthcareSite(healthcareSite);
		id.setPrimaryIndicator(true);
		id.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
		id.setValue(TEST_STUDY_ID);
		study.addIdentifier(id);
		HealthcareSite healthcareSite1 = new LocalHealthcareSite();
		id = new OrganizationAssignedIdentifier();
		id.setPrimaryIndicator(true);
		id.setType(OrganizationIdentifierTypeEnum.CTEP);
		id.setValue(TEST_HEALTHCARESITE_ID);
		id.setPrimaryIndicator(TEST_HEALTHCARESITE_ID_PRIMARYINDICATOR);
		healthcareSite1.getIdentifiersAssignedToOrganization().add(id);
		studySite.setHealthcareSite(healthcareSite1);
		
		edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion studySubjectConsentVersion = new edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion();
		studySubjectConsentVersion.setConsent(new Consent());
		studySubjectConsentVersion.getConsent().setName(TEST_CONSENT_NAME1);
		studySubjectConsentVersion.getConsent().setVersionId(TEST_CONSENT_VERSION1);
		studySubjectConsentVersion.setConsentDeliveryDate(parseISODate(TEST_CONSENT_DELIVERY_DATE1));
		studySubjectConsentVersion.setInformedConsentSignedDate(parseISODate(TEST_CONSENT_SIGNED_DATE1));
		studySubjectConsentVersion.setConsentingMethod(ConsentingMethod.getByCode(TEST_CONSENTING_METHOD1));
		studySubjectConsentVersion.setConsentPresenter(TEST_CONSENT_PRESENTER1);
		SubjectConsentQuestionAnswer ans = new SubjectConsentQuestionAnswer();
		ans.setAgreementIndicator(TEST_CONSENT_ANS11);
		ans.setConsentQuestion(new ConsentQuestion());
		ans.getConsentQuestion().setCode(TEST_CONSENT_QUES11);
		studySubjectConsentVersion.addSubjectConsentAnswer(ans);
		ans = new SubjectConsentQuestionAnswer();
		ans.setAgreementIndicator(TEST_CONSENT_ANS12);
		ans.setConsentQuestion(new ConsentQuestion());
		ans.getConsentQuestion().setCode(TEST_CONSENT_QUES12);
		studySubjectConsentVersion.addSubjectConsentAnswer(ans);
		studySubject.getStudySubjectStudyVersion().addStudySubjectConsentVersion(studySubjectConsentVersion);
		
		studySubjectConsentVersion = new edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion();
		studySubjectConsentVersion.setConsent(new Consent());
		studySubjectConsentVersion.getConsent().setName(TEST_CONSENT_NAME2);
		studySubjectConsentVersion.getConsent().setVersionId(TEST_CONSENT_VERSION2);
		studySubjectConsentVersion.setConsentDeliveryDate(parseISODate(TEST_CONSENT_DELIVERY_DATE2));
		studySubjectConsentVersion.setInformedConsentSignedDate(parseISODate(TEST_CONSENT_SIGNED_DATE2));
		studySubjectConsentVersion.setConsentingMethod(ConsentingMethod.getByCode(TEST_CONSENTING_METHOD2));
		studySubjectConsentVersion.setConsentPresenter(TEST_CONSENT_PRESENTER2);
		ans = new SubjectConsentQuestionAnswer();
		ans.setAgreementIndicator(TEST_CONSENT_ANS21);
		ans.setConsentQuestion(new ConsentQuestion());
		ans.getConsentQuestion().setCode(TEST_CONSENT_QUES21);
		studySubjectConsentVersion.addSubjectConsentAnswer(ans);
		ans = new SubjectConsentQuestionAnswer();
		ans.setAgreementIndicator(TEST_CONSENT_ANS22);
		ans.setConsentQuestion(new ConsentQuestion());
		ans.getConsentQuestion().setCode(TEST_CONSENT_QUES22);
		studySubjectConsentVersion.addSubjectConsentAnswer(ans);
		studySubject.getStudySubjectStudyVersion().addStudySubjectConsentVersion(studySubjectConsentVersion);
		
		StudySubjectRegistryStatus studySubjectRegistryStatus = new StudySubjectRegistryStatus();
		studySubjectRegistryStatus.setPermissibleStudySubjectRegistryStatus(new PermissibleStudySubjectRegistryStatus());
		studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().setRegistryStatus(new RegistryStatus(TEST_REGISTRYSTATUS_CODE1,"", null));
		studySubjectRegistryStatus.getReasons().addAll(Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason(TEST_REGISTRYSTATUS_REASON11, null, null, true), new RegistryStatusReason(TEST_REGISTRYSTATUS_REASON12, null, null, true)}));
		studySubjectRegistryStatus.setEffectiveDate(parseISODate(TEST_REGISTRYSTATUS_DATE1));
		studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
		
		studySubjectRegistryStatus = new StudySubjectRegistryStatus();
		studySubjectRegistryStatus.setPermissibleStudySubjectRegistryStatus(new PermissibleStudySubjectRegistryStatus());
		studySubjectRegistryStatus.getPermissibleStudySubjectRegistryStatus().setRegistryStatus(new RegistryStatus(TEST_REGISTRYSTATUS_CODE2,"", null));
		studySubjectRegistryStatus.getReasons().addAll(Arrays.asList(new RegistryStatusReason[]{new RegistryStatusReason(TEST_REGISTRYSTATUS_REASON21, null, null, true), new RegistryStatusReason(TEST_REGISTRYSTATUS_REASON22, null, null, true)}));
		studySubjectRegistryStatus.setEffectiveDate(parseISODate(TEST_REGISTRYSTATUS_DATE2));
		studySubject.getStudySubjectRegistryStatusHistoryInternal().add(studySubjectRegistryStatus);
		
		return studySubject;
	}
	
	protected edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject createStudySubjectJAXBObject(){
		edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject studySubject = new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject();
		studySubject.setEntity(createPerson());
		studySubject.setPaymentMethodCode(new CD(TEST_PAYMENT_METHOD));
		studySubject.setStatusCode(new CD(TEST_DATA_ENTRY_STATUS));
		
		studySubject.getSubjectIdentifier().add(createSubjectId());
		
		StudySubjectProtocolVersionRelationship studySubjectProtocolVersion = new StudySubjectProtocolVersionRelationship();
		studySubject.setStudySubjectProtocolVersion(studySubjectProtocolVersion);
		studySubjectProtocolVersion.setStudySiteProtocolVersion(new StudySiteProtocolVersionRelationship());
		
		//setup study
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudyProtocolVersion(new StudyProtocolVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().setStudyProtocolDocument(new StudyProtocolDocumentVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicTitle(new ST(TEST_LONGTITLE));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setOfficialTitle(new ST(TEST_SHORTTITLE));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicDescription(new ST(TEST_DESC));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setDocument(new Document());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().add(createDocumentId());
		
		//setup studysite
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudySite(new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySite());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().setOrganization(new Organization());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier().add(createOrgId());
		
		edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectConsentVersion target = new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectConsentVersion();
		target.setConsentDeliveryDate(new TSDateTime(TEST_CONSENT_DELIVERY_DATE1));
		target.setConsentingMethod(new CD(TEST_CONSENTING_METHOD1));
		target.setConsentPresenter(new ST(TEST_CONSENT_PRESENTER1));
		target.setInformedConsentDate(new TSDateTime(TEST_CONSENT_SIGNED_DATE1));
		target.setConsent(new DocumentVersion());
		target.getConsent().setOfficialTitle(new ST(TEST_CONSENT_NAME1));
		target.getConsent().setVersionNumberText(new ST(TEST_CONSENT_VERSION1));
		PerformedStudySubjectMilestone subjectAnswerTarget = new PerformedStudySubjectMilestone();
		subjectAnswerTarget.setMissedIndicator(new BL(TEST_CONSENT_ANS11));
		subjectAnswerTarget.setConsentQuestion(new DocumentVersion());
		subjectAnswerTarget.getConsentQuestion().setOfficialTitle(new ST(TEST_CONSENT_QUES11));
		target.getSubjectConsentAnswer().add(subjectAnswerTarget);
		subjectAnswerTarget = new PerformedStudySubjectMilestone();
		subjectAnswerTarget.setMissedIndicator(new BL(TEST_CONSENT_ANS12));
		subjectAnswerTarget.setConsentQuestion(new DocumentVersion());
		subjectAnswerTarget.getConsentQuestion().setOfficialTitle(new ST(TEST_CONSENT_QUES12));
		target.getSubjectConsentAnswer().add(subjectAnswerTarget);
		studySubjectProtocolVersion.getStudySubjectConsentVersion().add(target);
		
		target = new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectConsentVersion();
		target.setConsentDeliveryDate(new TSDateTime(TEST_CONSENT_DELIVERY_DATE2));
		target.setConsentingMethod(new CD(TEST_CONSENTING_METHOD2));
		target.setConsentPresenter(new ST(TEST_CONSENT_PRESENTER2));
		target.setInformedConsentDate(new TSDateTime(TEST_CONSENT_SIGNED_DATE2));
		target.setConsent(new DocumentVersion());
		target.getConsent().setOfficialTitle(new ST(TEST_CONSENT_NAME2));
		target.getConsent().setVersionNumberText(new ST(TEST_CONSENT_VERSION2));
		subjectAnswerTarget = new PerformedStudySubjectMilestone();
		subjectAnswerTarget.setMissedIndicator(new BL(TEST_CONSENT_ANS21));
		subjectAnswerTarget.setConsentQuestion(new DocumentVersion());
		subjectAnswerTarget.getConsentQuestion().setOfficialTitle(new ST(TEST_CONSENT_QUES21));
		target.getSubjectConsentAnswer().add(subjectAnswerTarget);
		subjectAnswerTarget = new PerformedStudySubjectMilestone();
		subjectAnswerTarget.setMissedIndicator(new BL(TEST_CONSENT_ANS22));
		subjectAnswerTarget.setConsentQuestion(new DocumentVersion());
		subjectAnswerTarget.getConsentQuestion().setOfficialTitle(new ST(TEST_CONSENT_QUES22));
		target.getSubjectConsentAnswer().add(subjectAnswerTarget);
		studySubjectProtocolVersion.getStudySubjectConsentVersion().add(target);
		
		return studySubject;
	}
	
	/**
	 * @param s
	 */
	protected void assertPerson(Person person) {
		assertTrue(CollectionUtils.isNotEmpty(person
				.getBiologicEntityIdentifier()));
		BiologicEntityIdentifier bioId = person.getBiologicEntityIdentifier()
				.get(0);
		assertEquals(TEST_BIO_ID, bioId.getIdentifier().getExtension());
		assertEquals(ORG_ID_TYPE_MRN, bioId.getTypeCode().getCode());
		Organization org = bioId.getAssigningOrganization();
		assertNotNull(org);
		assertTrue(CollectionUtils.isNotEmpty(org.getOrganizationIdentifier()));
		OrganizationIdentifier orgId = org.getOrganizationIdentifier().get(0);
		assertEquals(TEST_ORG_ID, orgId.getIdentifier().getExtension());
		assertTrue(orgId.getPrimaryIndicator().isValue());
		assertEquals(ORG_ID_TYPE_CTEP, orgId.getTypeCode().getCode());

		assertEquals(GENDER_MALE, person.getAdministrativeGenderCode()
				.getCode());
		assertEquals(TEST_BIRTH_DATE_ISO, person.getBirthDate().getValue());
		assertEquals(ETHNIC_CODE_NOT_REPORTED, person.getEthnicGroupCode()
				.getItem().get(0).getCode());
		assertEquals(MARITAL_STATUS_SINGLE, person.getMaritalStatusCode()
				.getCode());
		assertEquals(TEST_FIRST_NAME, person.getName().getItem().get(0)
				.getPart().get(0).getValue());
		assertEquals(EntityNamePartType.GIV, person.getName().getItem().get(0)
				.getPart().get(0).getType());
		assertEquals(TEST_MID_NAME, person.getName().getItem().get(0).getPart()
				.get(1).getValue());
		assertEquals(EntityNamePartType.GIV, person.getName().getItem().get(0)
				.getPart().get(1).getType());
		assertEquals(TEST_LAST_NAME, person.getName().getItem().get(0)
				.getPart().get(2).getValue());
		assertEquals(EntityNamePartType.FAM, person.getName().getItem().get(0)
				.getPart().get(2).getType());
		assertEquals(TEST_STREET_ADDRESS, person.getPostalAddress().getItem().get(0).getPart()
				.get(0).getValue());
		assertEquals(TEST_CITY_NAME, person.getPostalAddress().getItem().get(0).getPart().get(1)
				.getValue());
		assertEquals(TEST_STATE_CODE, person.getPostalAddress().getItem().get(0).getPart()
				.get(2).getValue());
		assertEquals(TEST_ZIP_CODE, person.getPostalAddress().getItem().get(0).getPart().get(3)
				.getValue());
		assertEquals(TEST_COUNTRY, person.getPostalAddress().getItem().get(0).getPart().get(4)
				.getValue());
		assertEquals(AddressPartType.SAL, person.getPostalAddress().getItem().get(0).getPart()
				.get(0).getType());
		assertEquals(AddressPartType.CTY, person.getPostalAddress().getItem().get(0).getPart()
				.get(1).getType());
		assertEquals(AddressPartType.STA, person.getPostalAddress().getItem().get(0).getPart()
				.get(2).getType());
		assertEquals(AddressPartType.ZIP, person.getPostalAddress().getItem().get(0).getPart()
				.get(3).getType());
		assertEquals(AddressPartType.CNT, person.getPostalAddress().getItem().get(0).getPart()
				.get(4).getType());
		assertEquals(RACE_WHITE, person.getRaceCode().getItem().get(0)
				.getCode());
		assertEquals(RACE_ASIAN, person.getRaceCode().getItem().get(1)
				.getCode());
		assertEquals(TEST_EMAIL_ADDR_ISO, person.getTelecomAddress().getItem()
				.get(0).getValue());
		assertEquals(TEST_PHONE_ISO, person.getTelecomAddress().getItem()
				.get(1).getValue());
		assertEquals(TEST_FAX_ISO, person.getTelecomAddress().getItem().get(2)
				.getValue());
	}
	
	/**
	 * @return
	 */
	protected List<RaceCodeEnum> createRaceCodes() {
		List<RaceCodeEnum> list = new ArrayList<RaceCodeEnum>();		
		list.add(RaceCodeEnum.White);		
		list.add(RaceCodeEnum.Asian);
		return list;
	}
	
	/**
	 * @return
	 */
	protected Person createPerson() {
		Person person = new Person();
		person.getBiologicEntityIdentifier().add(createBioEntityId());
		person.setAdministrativeGenderCode(new CD(GENDER_MALE));
		person.setBirthDate(new TSDateTime(TEST_BIRTH_DATE_ISO));
		person.setEthnicGroupCode(new DSETCD(new CD(ETHNIC_CODE_NOT_REPORTED)));
		person.setMaritalStatusCode(new CD(MARITAL_STATUS_SINGLE));
		person.setName(new DSETENPN(new ENPN(new ENXP(TEST_FIRST_NAME,
				EntityNamePartType.GIV), new ENXP(TEST_MID_NAME,
				EntityNamePartType.GIV), new ENXP(TEST_LAST_NAME,
				EntityNamePartType.FAM))));
		person.setPostalAddress(new DSETAD(new AD(new ADXP(TEST_STREET_ADDRESS,
				AddressPartType.SAL), new ADXP(TEST_CITY_NAME,
				AddressPartType.CTY), new ADXP(TEST_STATE_CODE,
				AddressPartType.STA), new ADXP(TEST_ZIP_CODE,
				AddressPartType.ZIP), new ADXP(TEST_COUNTRY,
				AddressPartType.CNT))));
		person.setRaceCode(new DSETCD(new CD(RACE_WHITE), new CD(RACE_ASIAN)));
		person.setTelecomAddress(new BAGTEL(new TEL(TEST_EMAIL_ADDR_ISO),
				new TEL(TEST_PHONE_ISO), new TEL(TEST_FAX_ISO)));
		return person;
	}
	
	/**
	 * @param s
	 */
	protected void assertSubjectDemographics(final StudySubjectDemographics s) {
		assertEquals(GENDER_MALE, s.getAdministrativeGenderCode());
		assertEquals(TEST_BIRTH_DATE, s.getBirthDate());
		assertEquals(ETHNIC_CODE_NOT_REPORTED, s.getEthnicGroupCode());
		assertEquals(MARITAL_STATUS_SINGLE, s.getMaritalStatusCode());
		assertEquals(TEST_FIRST_NAME, s.getFirstName());
		assertEquals(TEST_MID_NAME, s.getMiddleName());
		assertEquals(TEST_LAST_NAME, s.getLastName());
		assertEquals(StringUtils.EMPTY, s.getMaidenName());
		assertNotNull(s.getAddress());
		assertEquals(TEST_STREET_ADDRESS, s.getAddress()
				.getStreetAddress());
		assertEquals(TEST_CITY_NAME, s.getAddress().getCity());
		assertEquals(TEST_STATE_CODE, s.getAddress().getStateCode());
		assertEquals(TEST_ZIP_CODE, s.getAddress().getPostalCode());
		assertEquals(TEST_COUNTRY, s.getAddress().getCountryCode());
		assertEquals(Arrays.asList(new RaceCodeEnum[] { RaceCodeEnum.White,
				RaceCodeEnum.Asian }), (s.getRaceCodes()));
		assertEquals(TEST_EMAIL_ADDR, s.getEmail());
		assertEquals(TEST_PHONE, s.getPhone());
		assertEquals(TEST_FAX, s.getFax());
	}
	
	/**
	 * @param oaId
	 */
	protected void assertOrgAssId(OrganizationAssignedIdentifier oaId) {
		assertNotNull(oaId);
		assertEquals(TEST_BIO_ID, oaId.getValue());
		assertEquals(OrganizationIdentifierTypeEnum.MRN, oaId.getType());
		assertEquals(healthcareSite, oaId.getHealthcareSite());
		assertEquals(TEST_BIO_ID_PRIMARYINDICATOR, oaId.getPrimaryIndicator());
	}
	
	/**
	 * @param oaId
	 */
	protected void assertOrgAssIdDoc(OrganizationAssignedIdentifier oaId) {
		assertNotNull(oaId);
		assertEquals(TEST_STUDY_ID, oaId.getValue());
		assertEquals(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER, oaId.getType());
		assertEquals(healthcareSite, oaId.getHealthcareSite());
		assertEquals(TEST_STUDY_ID_PRIMARYINDICATOR, oaId.getPrimaryIndicator());
	}
	
	protected void assertOrgAssIdSub(OrganizationAssignedIdentifier oaId) {
		assertNotNull(oaId);
		assertEquals(TEST_STUDYSUBJECT_ID, oaId.getValue());
		assertEquals(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER, oaId.getType());
		assertEquals(healthcareSite, oaId.getHealthcareSite());
		assertEquals(TEST_STUDYSUBJECT_ID_PRIMARYINDICATOR, oaId.getPrimaryIndicator());
	}
}

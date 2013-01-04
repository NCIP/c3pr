/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
/**
 * 
 */
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.ParticipantStateCode;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.RegistryStatusDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Consent;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.RegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImplTest;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyUtilityImplTest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementImplTest;

/**
 * This class encapsulates methods common to web service testing. Methods of
 * this class are used in both unit and integration testing. <BR>
 * <BR>
 * It probably makes more sense to move object creation methods, such as {
 * {@link #createStudy()}, into a separate helper class not tied to a JUnit test
 * case. Will revisit this later.
 * 
 * @author dkrylov
 * @see JAXBToDomainObjectConverterImplTest
 * @see SubjectManagementImplTest
 */
public class WebServiceRelatedTestCase extends TestCase  {

	public static final String TEST_SECONDARY_REASON_DESCR = "Other";
	public static final String TEST_SECONDARY_REASON_CODE = "OTHER";
	public static final String BAD_STATE_CODE = "bad state code";
	public static final String BAD_RACE_CODE = "invalid race code";
	public static final String BAD_ISO_DATE = "1990-01-01";
	public static final String TS_DATETIME_PATTERN = "yyyyMMddHHmmss";
	public static final String TEST_FAX = "555-555-5555";
	public static final String TEST_FAX_ISO = "x-text-fax:" + TEST_FAX;
	public static final String TEST_PHONE = "555-555-5555";
	public static final String TEST_PHONE_ISO = "tel:" + TEST_PHONE;
	public static final String TEST_EMAIL_ADDR = "johndoe@semanticbits.com";
	public static final String TEST_EMAIL_ADDR_ISO = "mailto:"
			+ TEST_EMAIL_ADDR;
	public static final String RACE_ASIAN = "Asian";
	public static final String RACE_WHITE = "White";
	public static final String TEST_COUNTRY = "USA";
	public static final String TEST_ZIP_CODE = "22203-5555";
	public static final String TEST_STATE_CODE = "VA";
	public static final String TEST_CITY_NAME = "Arlington";
	public static final String TEST_STREET_ADDRESS = "1029 N Stuart St Unit 999";
	public static final String TEST_LAST_NAME = "Doe";
	public static final String TEST_MID_NAME = "Z";
	public static final String TEST_FIRST_NAME = "John";
	public static final String MARITAL_STATUS_SINGLE = "Single";
	public static final String ETHNIC_CODE_NOT_REPORTED = "Not Reported";
	public static final String TEST_DEATH_DATE_ISO = "19900101000000";
	public static final Date TEST_DEATH_DATE = parseISODate(TEST_DEATH_DATE_ISO);
	public static final String TEST_BIRTH_DATE_ISO = "19800101000000";
	public static final Date TEST_BIRTH_DATE = parseISODate(TEST_BIRTH_DATE_ISO);
	public static final String GENDER_MALE = "Male";
	public static final String STATE_ACTIVE = "ACTIVE";
	public static final String STATE_INACTIVE = "INACTIVE";
	public static final String ORG_ID_TYPE_MRN = OrganizationIdentifierTypeEnum.MRN
			.getName();
	public static final String TEST_BIO_ID = "001";
	public static final String ORG_ID_TYPE_CTEP = OrganizationIdentifierTypeEnum.CTEP
			.getName();
	public static final String TEST_ORG_ID = "MN026";
	public static final String TEST_VALUE2 = "v2";
	public static final String TEST_VALUE1 = "v1";
	public static final String TEST_PREDICATE = "Predicate";
	public static final String TEST_OBJ_NAME = "objName";
	public static final String TEST_OBJ_CTX_NAME = "objCtxName";
	public static final String TEST_ATTRIBUTE_NAME = "attribute_name";

	// study utility
	public static final String TEST_REGISTRY_STATUS = "ACTIVE";
	public static final String TEST_CONSENT_QUESTION_2 = "Question 2";
	public static final String TEST_CONSENT_QUESTION_1 = "Question 1";
	public static final String TEST_CONSENT_TITLE = "Consent";
	public static final String TEST_CONSENT_DESCRIPTION = "Consent Description";
	public static final String TEST_CONSENT_QUESTION_RELATIONSHIP = "CONSENT_QUESTION";
	public static final String TEST_CONSENT_RELATIONSHIP = "CONSENT";
	public static final String TEST_STUDY_ID = "Study_01";
	public static final String TEST_CTEP = "CTEP";
	public static final String TEST_VERSION_NUMBER = "1.0";
	public static final String TEST_VERSION_DATE_ISO = "20101005000000";
	public static final Date TEST_VERSION_DATE = parseISODate(TEST_VERSION_DATE_ISO);
	public static final String TEST_STUDY_DESCR = "Test Study";
	public static final String TEST_STUDY_SHORT_TITLE = "Test Study Short Title";
	public static final String TEST_TARGET_REG_SYS = "C3PR";
	public static final String TEST_STUDY_PHASE = "Phase 0 Trial";
	public static final Integer TEST_TARGET_ACCRUAL_NUMBER = 100;
	public static final String TEST_STUDY_TYPE = "Basic Science";
	public static final String TEST_PRIMARY_REASON_CODE = "OTHER";
	public static final String TEST_PRIMARY_REASON_DESCR = "Other Description";
	
	protected static final ISO21090Helper iso = new ISO21090Helper();

	protected static Date parseISODate(String isoDate) {
		try {
			return DateUtils.parseDate(isoDate,
					new String[] { TS_DATETIME_PATTERN });
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	protected JAXBToDomainObjectConverterImpl converter;

	protected HealthcareSiteDao healthcareSiteDao;

	protected RegistryStatusDao registryStatusDao;

	protected HealthcareSite healthcareSite;

	protected edu.duke.cabig.c3pr.domain.RegistryStatus registryStatus;

	protected edu.duke.cabig.c3pr.domain.RegistryStatus registryStatus2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		healthcareSiteDao = createMock(HealthcareSiteDao.class);
		registryStatusDao = createMock(RegistryStatusDao.class);

		converter = new JAXBToDomainObjectConverterImpl();
		C3PRExceptionHelper exceptionHelper = new C3PRExceptionHelper(
				getMessageSourceMock());
		converter.setExceptionHelper(exceptionHelper);
		converter.setHealthcareSiteDao(healthcareSiteDao);
		converter.setRegistryStatusDao(registryStatusDao);

		healthcareSite = new LocalHealthcareSite();
		healthcareSite.setCtepCode(TEST_ORG_ID, true);
		expect(healthcareSiteDao.getByPrimaryIdentifier(TEST_ORG_ID))
				.andReturn(healthcareSite).anyTimes();
		expect(healthcareSiteDao.getByTypeAndCodeFromLocal(ORG_ID_TYPE_CTEP,TEST_ORG_ID))
			.andReturn(healthcareSite).anyTimes();		
		expect(healthcareSiteDao.getByTypeAndCodeFromLocal(ORG_ID_TYPE_CTEP,TEST_ORG_ID,true))
			.andReturn(healthcareSite).anyTimes();		
		replay(healthcareSiteDao);

		registryStatus = new edu.duke.cabig.c3pr.domain.RegistryStatus();
		registryStatus.setCode(TEST_REGISTRY_STATUS);
		registryStatus.setDescription(TEST_REGISTRY_STATUS);
		registryStatus
				.setPrimaryReasons(Arrays
						.asList(new edu.duke.cabig.c3pr.domain.RegistryStatusReason[] { new edu.duke.cabig.c3pr.domain.RegistryStatusReason(
								TEST_PRIMARY_REASON_CODE,
								TEST_PRIMARY_REASON_DESCR, null, true) }));
		expect(registryStatusDao.getRegistryStatusByCode(TEST_REGISTRY_STATUS))
				.andReturn(registryStatus).anyTimes();

		registryStatus2 = new edu.duke.cabig.c3pr.domain.RegistryStatus();
		registryStatus2.setCode(TEST_REGISTRY_STATUS + "2");
		registryStatus2.setDescription(TEST_REGISTRY_STATUS + "2");
		registryStatus2
				.setPrimaryReasons(Arrays
						.asList(new edu.duke.cabig.c3pr.domain.RegistryStatusReason[] { new edu.duke.cabig.c3pr.domain.RegistryStatusReason(
								TEST_PRIMARY_REASON_CODE,
								TEST_PRIMARY_REASON_DESCR, null, true) }));
		expect(registryStatusDao.getAll()).andReturn(
				Arrays.asList(new edu.duke.cabig.c3pr.domain.RegistryStatus[] {
						registryStatus, registryStatus2 })).anyTimes();
		replay(registryStatusDao);

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

	/**
	 * 
	 */
	public WebServiceRelatedTestCase() {
	}

	/**
	 * @param person
	 * @return
	 */
	protected Subject createSubject(Person person) {
		Subject s = new Subject();
		s.setEntity(person);
		s.setStateCode(iso.ST(STATE_ACTIVE));
		return s;
	}

	/**
	 * @return
	 */
	protected Person createPerson() {
		Person person = new Person();
		person.getBiologicEntityIdentifier().add(createBioEntityId());
		person.setAdministrativeGenderCode(iso.CD(GENDER_MALE));
		person.setBirthDate(iso.TSDateTime(TEST_BIRTH_DATE_ISO));
		person.setDeathDate(iso.TSDateTime(TEST_DEATH_DATE_ISO));
		person.setDeathIndicator(iso.BL(true));
		person.setEthnicGroupCode(iso.DSETCD(iso.CD(ETHNIC_CODE_NOT_REPORTED)));
		person.setMaritalStatusCode(iso.CD(MARITAL_STATUS_SINGLE));
		person.setName(iso.DSETENPN(iso.ENPN(iso.ENXP(TEST_FIRST_NAME,
				EntityNamePartType.GIV), iso.ENXP(TEST_MID_NAME,
				EntityNamePartType.GIV), iso.ENXP(TEST_LAST_NAME,
				EntityNamePartType.FAM))));
		person.setPostalAddress(iso.DSETAD(iso.AD(iso.ADXP(TEST_STREET_ADDRESS,
				AddressPartType.SAL), iso.ADXP(TEST_CITY_NAME,
				AddressPartType.CTY), iso.ADXP(TEST_STATE_CODE,
				AddressPartType.STA), iso.ADXP(TEST_ZIP_CODE,
				AddressPartType.ZIP), iso.ADXP(TEST_COUNTRY,
				AddressPartType.CNT))));
		person.setRaceCode(iso.DSETCD(iso.CD(RACE_WHITE), iso.CD(RACE_ASIAN)));
		person.setTelecomAddress(iso.BAGTEL(iso.TEL(TEST_EMAIL_ADDR_ISO, null),
				iso.TEL(TEST_PHONE_ISO, null), iso.TEL(TEST_FAX_ISO, null)));
		return person;
	}

	/**
	 * @return
	 */
	protected BiologicEntityIdentifier createBioEntityId() {
		OrganizationIdentifier orgId = new OrganizationIdentifier();
		orgId.setIdentifier(iso.II(TEST_ORG_ID));
		orgId.setPrimaryIndicator(iso.BL(true));
		orgId.setTypeCode(iso.CD(ORG_ID_TYPE_CTEP));

		Organization org = new Organization();
		org.getOrganizationIdentifier().add(orgId);

		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setAssigningOrganization(org);
		bioId.setIdentifier(iso.II(TEST_BIO_ID));
		bioId.setTypeCode(iso.CD(ORG_ID_TYPE_MRN));
		bioId.setEffectiveDateRange(iso.IVLTSDateTime(NullFlavor.NI));
		bioId.setPrimaryIndicator(iso.BL(true));
		return bioId;
	}

	/**
	 * @param s
	 */
	protected void assertSubject(Subject s) {
		assertNotNull(s);
		assertNotNull(s.getEntity());
		Person person = (Person) s.getEntity();
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
		assertEquals(TEST_DEATH_DATE_ISO, person.getDeathDate().getValue());
		assertTrue(person.getDeathIndicator().isValue());
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
		assertEquals(TEST_STREET_ADDRESS, person.getPostalAddress().getItem()
				.get(0).getPart().get(0).getValue());
		assertEquals(TEST_CITY_NAME, person.getPostalAddress().getItem().get(0)
				.getPart().get(1).getValue());
		assertEquals(TEST_STATE_CODE, person.getPostalAddress().getItem()
				.get(0).getPart().get(2).getValue());
		assertEquals(TEST_ZIP_CODE, person.getPostalAddress().getItem().get(0)
				.getPart().get(3).getValue());
		assertEquals(TEST_COUNTRY, person.getPostalAddress().getItem().get(0)
				.getPart().get(4).getValue());
		assertEquals(AddressPartType.SAL, person.getPostalAddress().getItem()
				.get(0).getPart().get(0).getType());
		assertEquals(AddressPartType.CTY, person.getPostalAddress().getItem()
				.get(0).getPart().get(1).getType());
		assertEquals(AddressPartType.STA, person.getPostalAddress().getItem()
				.get(0).getPart().get(2).getType());
		assertEquals(AddressPartType.ZIP, person.getPostalAddress().getItem()
				.get(0).getPart().get(3).getType());
		assertEquals(AddressPartType.CNT, person.getPostalAddress().getItem()
				.get(0).getPart().get(4).getType());
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
	protected Participant createParticipant() {
		Participant p = new Participant();

		OrganizationAssignedIdentifier id = new OrganizationAssignedIdentifier();
		id.setHealthcareSite(healthcareSite);
		id.setPrimaryIndicator(true);
		id.setType(OrganizationIdentifierTypeEnum.MRN);
		id.setValue(TEST_BIO_ID);
		p.addIdentifier(id);

		p.setAdministrativeGenderCode(GENDER_MALE);
		p.setBirthDate(TEST_BIRTH_DATE);
		p.setDeathDate(TEST_DEATH_DATE);
		p.setDeathIndicator(true);
		p.setEthnicGroupCode(ETHNIC_CODE_NOT_REPORTED);
		p.setMaritalStatusCode(MARITAL_STATUS_SINGLE);
		p.setFirstName(TEST_FIRST_NAME);
		p.setMaidenName(StringUtils.EMPTY);
		p.setMiddleName(TEST_MID_NAME);
		p.setLastName(TEST_LAST_NAME);
		p.setAddress(new Address(TEST_STREET_ADDRESS, TEST_CITY_NAME,
				TEST_STATE_CODE, TEST_ZIP_CODE, TEST_COUNTRY));

		p.setRaceCodes(createRaceCodes());
		p.setEmail(TEST_EMAIL_ADDR);
		p.setPhone(TEST_PHONE);
		p.setFax(TEST_FAX);
		p.setStateCode(ParticipantStateCode.ACTIVE);
		return p;
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
	protected AdvanceSearchCriterionParameter createAdvaceSearchParam() {
		AdvanceSearchCriterionParameter param = new AdvanceSearchCriterionParameter();
		param.setAttributeName(iso.ST(TEST_ATTRIBUTE_NAME));
		param.setObjectContextName(iso.ST(TEST_OBJ_CTX_NAME));
		param.setObjectName(iso.ST(TEST_OBJ_NAME));
		param.setPredicate(iso.CD(TEST_PREDICATE));
		param.setValues(iso.DSETST(Arrays.asList(new ST[] {
				iso.ST(TEST_VALUE1), iso.ST(TEST_VALUE2) })));
		return param;
	}

	public Study createDomainStudy() {
		// study
		Study study = new LocalStudy();
		study.setTargetRegistrationSystem(TEST_TARGET_REG_SYS);
		study.setShortTitleText(TEST_STUDY_SHORT_TITLE);
		study.setLongTitleText(TEST_STUDY_DESCR);
		study.setDescriptionText(TEST_STUDY_DESCR);
		study.getStudyVersion().setVersionDate(TEST_VERSION_DATE);

		// consent
		edu.duke.cabig.c3pr.domain.Consent c = createDomainConsent();
		c.setStudyVersion(study.getStudyVersion());
		study.addConsent(c);

		// statuses
		edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus status = new edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus();
		status.setRegistryStatus(registryStatus);
		List<edu.duke.cabig.c3pr.domain.RegistryStatusReason> secondaryReasons = new ArrayList<edu.duke.cabig.c3pr.domain.RegistryStatusReason>();
		secondaryReasons
				.add(new edu.duke.cabig.c3pr.domain.RegistryStatusReason(
						TEST_SECONDARY_REASON_CODE,
						TEST_SECONDARY_REASON_DESCR, registryStatus.getPrimaryReasons().get(0), false));
		status.setSecondaryReasons(secondaryReasons);
		study.getPermissibleStudySubjectRegistryStatusesInternal().add(status);

		// study
		study.setBlindedIndicator(false);
		study.setMultiInstitutionIndicator(true);
		study.setPhaseCode(TEST_STUDY_PHASE);
		study.setRandomizedIndicator(false);
		study.setTargetAccrualNumber(TEST_TARGET_ACCRUAL_NUMBER);
		study.setType(TEST_STUDY_TYPE);
		study.setRetiredIndicatorAsFalse();
		study.setStratificationIndicator(false);
		study.setStandaloneIndicator(true);
		study.setCompanionIndicator(false);
		study.setConsentRequired(ConsentRequired.ONE);
		study.setTherapeuticIntentIndicator(false);

		// study version
		study.setPrecisText(StringUtils.EMPTY);
		study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		study.setDataEntryStatus(StudyDataEntryStatus.INCOMPLETE);
		study.setOriginalIndicator(true);

		// identifiers and study organizations
		StudyCoordinatingCenter scc = new StudyCoordinatingCenter();
		scc.setHealthcareSite(healthcareSite);
		study.addStudyOrganization(scc);
		OrganizationAssignedIdentifier oai = new OrganizationAssignedIdentifier();
		oai.setHealthcareSite(healthcareSite);
		oai.setPrimaryIndicator(true);
		oai.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
		oai.setValue(TEST_STUDY_ID);
		study.addIdentifier(oai);

		StudyFundingSponsor sfs = new StudyFundingSponsor();
		sfs.setHealthcareSite(healthcareSite);
		study.addStudyOrganization(sfs);
		oai = new OrganizationAssignedIdentifier();
		oai.setHealthcareSite(healthcareSite);
		oai.setPrimaryIndicator(false);
		oai.setType(OrganizationIdentifierTypeEnum.STUDY_FUNDING_SPONSOR);
		oai.setValue(TEST_STUDY_ID);
		study.addIdentifier(oai);

		return study;
	}

	/**
	 * @return
	 */
	protected edu.duke.cabig.c3pr.domain.Consent createDomainConsent() {
		edu.duke.cabig.c3pr.domain.Consent c = new edu.duke.cabig.c3pr.domain.Consent();
		c.setMandatoryIndicator(true);
		c.setVersionId("1.0");
		c.setName(TEST_CONSENT_TITLE);
		c.setDescriptionText(TEST_CONSENT_DESCRIPTION);
		c.addQuestion(new ConsentQuestion(TEST_CONSENT_QUESTION_1,
				TEST_CONSENT_QUESTION_1));
		c.addQuestion(new ConsentQuestion(TEST_CONSENT_QUESTION_2,
				TEST_CONSENT_QUESTION_2));
		return c;
	}

	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.webservice.helpers.TestDataCreationHelper#createStudy()
	 */
	public StudyProtocolVersion createStudy() {
		StudyProtocolVersion study = new StudyProtocolVersion();
		study.setTargetRegistrationSystem(iso.ST(TEST_TARGET_REG_SYS));
		study.setStudyProtocolDocument(createStudyProtocolDocument());
		study.getPermissibleStudySubjectRegistryStatus().add(
				createPermissibleStudySubjectRegistryStatus());
		return study;
	}

	protected PermissibleStudySubjectRegistryStatus createPermissibleStudySubjectRegistryStatus() {
		PermissibleStudySubjectRegistryStatus stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus());
		stat.getSecondaryReason().add(createSecondaryRegistryStatusReason());
		stat.getSecondaryReason().get(0).setPrimaryReason(stat.getRegistryStatus().getPrimaryReason().get(0));
		return stat;
	}

	protected RegistryStatus createRegistryStatus() {
		RegistryStatus stat = new RegistryStatus();
		stat.setCode(iso.CD(TEST_REGISTRY_STATUS));
		stat.setDescription(iso.ST(TEST_REGISTRY_STATUS));
		stat.getPrimaryReason().add(createPrimaryRegistryStatusReason());
		return stat;
	}

	protected RegistryStatusReason createSecondaryRegistryStatusReason() {
		RegistryStatusReason r = new RegistryStatusReason();
		r.setCode(iso.CD(TEST_SECONDARY_REASON_CODE));
		r.setDescription(iso.ST(TEST_SECONDARY_REASON_DESCR));
		r.setPrimaryIndicator(iso.BL(false));
		return r;
	}

	protected RegistryStatusReason createPrimaryRegistryStatusReason() {
		RegistryStatusReason r = new RegistryStatusReason();
		r.setCode(iso.CD(TEST_PRIMARY_REASON_CODE));
		r.setDescription(iso.ST(TEST_PRIMARY_REASON_DESCR));
		r.setPrimaryIndicator(iso.BL(true));
		return r;
	}

	protected StudyProtocolDocumentVersion createStudyProtocolDocument() {
		StudyProtocolDocumentVersion doc = new StudyProtocolDocumentVersion();
		doc.setOfficialTitle(iso.ST(TEST_STUDY_SHORT_TITLE));
		doc.setPublicDescription(iso.ST(TEST_STUDY_DESCR));
		doc.setPublicTitle(iso.ST(TEST_STUDY_DESCR));
		doc.setText(iso.ED(TEST_STUDY_DESCR));
		doc.setVersionDate(iso.TSDateTime(TEST_VERSION_DATE_ISO));
		doc.setVersionNumberText(iso.ST(TEST_VERSION_NUMBER));
		doc.setDocument(createStudyDocument());
		doc.getDocumentVersionRelationship().add(createConsentRelationship());
		return doc;
	}

	protected DocumentVersionRelationship createConsentRelationship() {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(iso.CD(TEST_CONSENT_RELATIONSHIP));
		rel.setTarget(createConsent());
		return rel;
	}

	protected Consent createConsent() {
		Consent consent = new Consent();
		consent.setMandatoryIndicator(iso.BL(true));
		consent.setOfficialTitle(iso.ST(TEST_CONSENT_TITLE));
		consent.setText(iso.ED(TEST_CONSENT_DESCRIPTION));
		consent.setVersionNumberText(iso.ST(TEST_VERSION_NUMBER));
		consent.setDocument(new Document());
		consent.getDocumentVersionRelationship().add(
				createConsentQuestionRelationship(TEST_CONSENT_QUESTION_1));
		consent.getDocumentVersionRelationship().add(
				createConsentQuestionRelationship(TEST_CONSENT_QUESTION_2));
		return consent;
	}

	protected DocumentVersionRelationship createConsentQuestionRelationship(
			String text) {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(iso.CD(TEST_CONSENT_QUESTION_RELATIONSHIP));
		rel.setTarget(createConsentQuestion(text));
		return rel;
	}

	protected DocumentVersion createConsentQuestion(String text) {
		DocumentVersion q = new DocumentVersion();
		q.setOfficialTitle(iso.ST(text));
		q.setText(iso.ED(text));
		// q.setVersionDate(iso.TSDateTime(TEST_VERSION_DATE_ISO));
		// q.setVersionNumberText(iso.ST(TEST_VERSION_NUMBER));
		q.setDocument(new Document());
		return q;
	}

	protected Document createStudyDocument() {
		Document doc = new Document();
		doc.getDocumentIdentifier().add(createStudyPrimaryIdentifier());
		doc.getDocumentIdentifier().add(createStudyProtocolAuthIdentifier());
		doc.getDocumentIdentifier().add(createStudyFundingSponsorIdentifier());
		return doc;
	}

	protected DocumentIdentifier createStudyFundingSponsorIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(TEST_STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(false));
		docId.setTypeCode(iso.CD(
				OrganizationIdentifierTypeEnum.STUDY_FUNDING_SPONSOR.name()));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	protected DocumentIdentifier createStudyPrimaryIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(TEST_STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(true));
		docId.setTypeCode(iso.CD(
				OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER
						.name()));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	protected DocumentIdentifier createStudyProtocolAuthIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(TEST_STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(false));
		docId.setTypeCode(iso.CD(
				OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER
						.name()));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	protected Organization createOrganization() {
		Organization org = new Organization();
		org.getOrganizationIdentifier().add(createOrganizationIdentifier());
		return org;
	}

	protected OrganizationIdentifier createOrganizationIdentifier() {
		OrganizationIdentifier orgId = new OrganizationIdentifier();
		orgId.setIdentifier(iso.II(TEST_ORG_ID));
		orgId.setPrimaryIndicator(iso.BL(true));
		orgId.setTypeCode(iso.CD(TEST_CTEP));
		return orgId;
	}

	public static void main(String[] args) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(StudyProtocolVersion.class,
				CD.class);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		final StudyProtocolVersion study = new StudyUtilityImplTest()
				.createStudy();
		JAXBElement<StudyProtocolVersion> jaxbElement = new JAXBElement<StudyProtocolVersion>(
				new QName("http://enterpriseservices.nci.nih.gov/Common",
						"study"), StudyProtocolVersion.class, study);
		m.marshal(jaxbElement, System.out);
	}

}

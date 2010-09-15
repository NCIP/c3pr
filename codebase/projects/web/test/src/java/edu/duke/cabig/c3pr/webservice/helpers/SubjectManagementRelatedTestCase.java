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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.ParticipantStateCode;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl;
import edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImplTest;
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
import edu.duke.cabig.c3pr.webservice.subjectmanagement.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Organization;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Person;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementImplTest;

/**
 * This class encapsulates methods common to subject management testing.
 * 
 * @author dkrylov
 * @see JAXBToDomainObjectConverterImplTest
 * @see SubjectManagementImplTest
 */
public abstract class SubjectManagementRelatedTestCase extends
		ApplicationTestCase {

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
	protected static final String TEST_DEATH_DATE_ISO = "19900101000000";
	protected static final Date TEST_DEATH_DATE = parseISODate(TEST_DEATH_DATE_ISO);
	protected static final String TEST_BIRTH_DATE_ISO = "19800101000000";
	protected static final Date TEST_BIRTH_DATE = parseISODate(TEST_BIRTH_DATE_ISO);
	protected static final String GENDER_MALE = "Male";
	protected static final String STATE_ACTIVE = "ACTIVE";
	protected static final String STATE_INACTIVE = "INACTIVE";
	protected static final String ORG_ID_TYPE_MRN = OrganizationIdentifierTypeEnum.MRN
			.getName();
	protected static final String TEST_BIO_ID = "001";
	protected static final String ORG_ID_TYPE_CTEP = OrganizationIdentifierTypeEnum.CTEP
			.getName();
	protected static final String TEST_ORG_ID = "MN026";
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

	protected JAXBToDomainObjectConverterImpl converter;

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
		converter = new JAXBToDomainObjectConverterImpl();
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

	/**
	 * @return
	 */
	private MessageSource getMessageSourceMock() {
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
	public SubjectManagementRelatedTestCase() {
	}

	/**
	 * @param person
	 * @return
	 */
	protected Subject createSubject(Person person) {
		Subject s = new Subject();
		s.setEntity(person);
		s.setStateCode(new ST(STATE_ACTIVE));
		return s;
	}

	/**
	 * @return
	 */
	protected Person createPerson() {
		Person person = new Person();
		person.getBiologicEntityIdentifier().add(createBioEntityId());
		person.setAdministrativeGenderCode(new CD(GENDER_MALE));
		person.setBirthDate(new TSDateTime(TEST_BIRTH_DATE_ISO));
		person.setDeathDate(new TSDateTime(TEST_DEATH_DATE_ISO));
		person.setDeathIndicator(new BL(true));
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
	 * @return
	 */
	protected BiologicEntityIdentifier createBioEntityId() {
		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setAssigningOrganization(new Organization(
				new OrganizationIdentifier(new II(TEST_ORG_ID), new BL(true),
						new CD(ORG_ID_TYPE_CTEP))));
		bioId.setIdentifier(new II(TEST_BIO_ID));
		bioId.setTypeCode(new CD(ORG_ID_TYPE_MRN));
		bioId.setEffectiveDateRange(new IVLTSDateTime(NullFlavor.NI));
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
		param.setAttributeName(new ST(TEST_ATTRIBUTE_NAME));
		param.setObjectContextName(new ST(TEST_OBJ_CTX_NAME));
		param.setObjectName(new ST(TEST_OBJ_NAME));
		param.setPredicate(new CD(TEST_PREDICATE));
		param.setValues(new DSETST(Arrays.asList(new ST[] {
				new ST(TEST_VALUE1), new ST(TEST_VALUE2) })));
		return param;
	}

}

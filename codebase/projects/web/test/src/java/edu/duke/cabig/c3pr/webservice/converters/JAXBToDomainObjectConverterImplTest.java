/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.converters;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.test.AssertThrows;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
//import edu.duke.cabig.c3pr.constants.RaceCode;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import edu.duke.cabig.c3pr.webservice.iso21090.AD;
import edu.duke.cabig.c3pr.webservice.iso21090.ADXP;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.BAGTEL;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENXP;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.IVLTSDateTime;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TEL;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Organization;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Person;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject;

/**
 * @author dkrylov
 * 
 */
public class JAXBToDomainObjectConverterImplTest extends ApplicationTestCase {

	private static final String BAD_ISO_DATE = "1990-01-01";

	private static final String TS_DATETIME_PATTERN = "yyyyMMddHHmmss";

	private static final String TEST_FAX = "555-555-5555";

	private static final String TEST_FAX_ISO = "x-text-fax:" + TEST_FAX;

	private static final String TEST_PHONE = "555-555-5555";

	private static final String TEST_PHONE_ISO = "tel:" + TEST_PHONE;

	private static final String TEST_EMAIL_ADDR = "johndoe@semanticbits.com";

	private static final String TEST_EMAIL_ADDR_ISO = "mailto:"
			+ TEST_EMAIL_ADDR;

	private static final String RACE_ASIAN = "Asian";

	private static final String RACE_WHITE = "White";

	private static final String TEST_COUNTRY = "USA";

	private static final String TEST_ZIP_CODE = "22203-5555";

	private static final String TEST_STATE_CODE = "VA";

	private static final String TEST_CITY_NAME = "Arlington";

	private static final String TEST_STREET_ADDRESS = "1029 N Stuart St Unit 999";

	private static final String TEST_LAST_NAME = "Doe";

	private static final String TEST_MID_NAME = "Z";

	private static final String TEST_FIRST_NAME = "John";

	private static final String MARITAL_STATUS_SINGLE = "Single";

	private static final String ETHNIC_CODE_NOT_REPORTED = "Not Reported";

	private static final String TEST_DEATH_DATE_ISO = "19900101000000";

	private static final Date TEST_DEATH_DATE = parseISODate(TEST_DEATH_DATE_ISO);

	private static final String TEST_BIRTH_DATE_ISO = "19800101000000";

	private static final Date TEST_BIRTH_DATE = parseISODate(TEST_BIRTH_DATE_ISO);

	private static final String GENDER_MALE = "Male";

	private static final String STATUS_ACTIVE = "ACTIVE";

	private static final String ORG_ID_TYPE_MRN = OrganizationIdentifierTypeEnum.MRN
			.getCode();

	private static final String TEST_BIO_ID = "001";

	private static final String ORG_ID_TYPE_CTEP = OrganizationIdentifierTypeEnum.CTEP
			.getCode();

	private static final String TEST_ORG_ID = "MN026";

	private JAXBToDomainObjectConverterImpl converter;

	private HealthcareSiteDao healthcareSiteDao;

	private HealthcareSite healthcareSite;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		converter = new JAXBToDomainObjectConverterImpl();
		C3PRExceptionHelper exceptionHelper = new C3PRExceptionHelper(
				getMessageSourceMock());
		converter.setExceptionHelper(exceptionHelper);
		healthcareSiteDao = createMock(HealthcareSiteDao.class);
		converter.setHealthcareSiteDao(healthcareSiteDao);

		healthcareSite = new LocalHealthcareSite();
		healthcareSite.setCtepCode(TEST_ORG_ID, true);
		expect(healthcareSiteDao.getByPrimaryIdentifier(TEST_ORG_ID))
				.andReturn(healthcareSite).anyTimes();
		replay(healthcareSiteDao);

	}

	private static Date parseISODate(String isoDate) {
		try {
			return DateUtils.parseDate(isoDate,
					new String[] { TS_DATETIME_PATTERN });
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject, boolean)}
	 * .
	 */
	public void testConvertSubjectBoolean() {
		//fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.domain.Participant, edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject)}
	 * .
	 */
	public void testConvertParticipantSubject() {
		final Participant p = new Participant();
		Person person = createPerson();
		Subject s = createSubject(person);

		converter.convert(p, s);
		assertEquals(GENDER_MALE, p.getAdministrativeGenderCode());
		assertEquals(TEST_BIRTH_DATE, p.getBirthDate());
		assertEquals(TEST_DEATH_DATE, p.getDeathDate());
		assertTrue(p.getDeathIndicator());
		assertEquals(ETHNIC_CODE_NOT_REPORTED, p.getEthnicGroupCode());
		assertEquals(MARITAL_STATUS_SINGLE, p.getMaritalStatusCode());
		assertEquals(TEST_FIRST_NAME, p.getFirstName());
		assertEquals(TEST_MID_NAME, p.getMiddleName());
		assertEquals(TEST_LAST_NAME, p.getLastName());
		assertEquals(StringUtils.EMPTY, p.getMaidenName());
		assertNotNull(p.getAddress());
		assertEquals(TEST_STREET_ADDRESS, p.getAddress().getStreetAddress());
		assertEquals(TEST_CITY_NAME, p.getAddress().getCity());
		assertEquals(TEST_STATE_CODE, p.getAddress().getStateCode());
		assertEquals(TEST_ZIP_CODE, p.getAddress().getPostalCode());
		assertEquals(TEST_COUNTRY, p.getAddress().getCountryCode());
		/**assertEquals(Arrays.asList(new RaceCode[] { RaceCode.White,
				RaceCode.Asian }), p.getRaceCodes()); **/
		assertEquals(TEST_EMAIL_ADDR, p.getEmail());
		assertEquals(TEST_PHONE, p.getPhone());
		assertEquals(TEST_FAX, p.getFax());

		// test exceptions
		final Person badDataPerson1 = createPerson();
		final Subject badDataSubj1 = createSubject(badDataPerson1);
		badDataPerson1.setBirthDate(new TSDateTime(BAD_ISO_DATE));
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(p, badDataSubj1);
			}
		}.runTest();

	}

	/**
	 * @param person
	 * @return
	 */
	private Subject createSubject(Person person) {
		Subject s = new Subject();
		s.setEntity(person);
		s.setStateCode(new ST(STATUS_ACTIVE));
		return s;
	}

	/**
	 * @return
	 */
	private Person createPerson() {
		Person person = new Person();
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
		person.setPostalAddress(new AD(new ADXP(TEST_STREET_ADDRESS,
				AddressPartType.SAL), new ADXP(TEST_CITY_NAME,
				AddressPartType.CTY), new ADXP(TEST_STATE_CODE,
				AddressPartType.STA), new ADXP(TEST_ZIP_CODE,
				AddressPartType.ZIP), new ADXP(TEST_COUNTRY,
				AddressPartType.CNT)));
		person.setRaceCode(new DSETCD(new CD(RACE_WHITE), new CD(RACE_ASIAN)));
		person.setTelecomAddress(new BAGTEL(new TEL(TEST_EMAIL_ADDR_ISO),
				new TEL(TEST_PHONE_ISO), new TEL(TEST_FAX_ISO)));
		return person;
	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier)}
	 * .
	 */
	public void testConvertBiologicEntityIdentifier() {
		BiologicEntityIdentifier bioId = createBioEntityId();

		OrganizationAssignedIdentifier oaId = converter.convert(bioId);
		assertNotNull(oaId);
		assertTrue(oaId.getPrimaryIndicator());
		assertEquals(TEST_BIO_ID, oaId.getValue());
		assertEquals(OrganizationIdentifierTypeEnum.MRN, oaId.getType());
		assertEquals(healthcareSite, oaId.getHealthcareSite());

		final BiologicEntityIdentifier badBioId = createBioEntityId();
		badBioId.setIdentifier(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(badBioId);
			}
		}.runTest();

		final BiologicEntityIdentifier badBioId2 = createBioEntityId();
		badBioId2.setTypeCode(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(badBioId2);
			}
		}.runTest();

		final BiologicEntityIdentifier badBioId3 = createBioEntityId();
		badBioId3.setAssigningOrganization(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(badBioId3);
			}
		}.runTest();

	}

	/**
	 * @return
	 */
	private BiologicEntityIdentifier createBioEntityId() {
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
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.domain.Participant)}
	 * .
	 */
	public void testConvertParticipant() {
		//fail("Not yet implemented");
	}

}

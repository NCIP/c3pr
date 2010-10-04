/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.converters;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.test.AssertThrows;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.ParticipantStateCode;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.helpers.SubjectManagementRelatedTestCase;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Person;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject;

/**
 * @author dkrylov
 * 
 */
public class JAXBToDomainObjectConverterImplTest extends
		SubjectManagementRelatedTestCase {

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject, boolean)}
	 * .
	 */
	public void testConvertSubjectBoolean() {
		Person person = createPerson();
		Subject subject = createSubject(person);
		Participant participant = converter.convert(subject, true,false);

		assertParticipant(participant);
		assertNotNull(participant.getPrimaryIdentifier());
		assertTrue(participant.getPrimaryIdentifier() instanceof OrganizationAssignedIdentifier);
		assertOrgAssId((OrganizationAssignedIdentifier) participant
				.getPrimaryIdentifier());
		assertNotNull(participant.getStateCode());
		assertEquals(ParticipantStateCode.ACTIVE, participant.getStateCode());

		// test exceptions
		Person badDataPerson1 = createPerson();
		badDataPerson1.getBiologicEntityIdentifier().clear();
		final Subject badDataSubject1 = createSubject(badDataPerson1);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(badDataSubject1, true,false);
			}
		}.runTest();

		Person badDataPerson2 = createPerson();
		final Subject badDataSubject2 = createSubject(badDataPerson2);
		badDataSubject2.setStateCode(new ST(BAD_STATE_CODE));
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(badDataSubject2, true,false);
			}
		}.runTest();
	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.domain.Participant, edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject)}
	 * .
	 */
	public void testConvertParticipantSubject() {
		final Participant participant = new Participant();
		Person person = createPerson();
		Subject subject = createSubject(person);

		converter.convert(participant, subject,false);

		assertParticipant(participant);

		// test exceptions
		final Person badDataPerson1 = createPerson();
		final Subject badDataSubj1 = createSubject(badDataPerson1);
		badDataPerson1.setBirthDate(new TSDateTime(BAD_ISO_DATE));
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(participant, badDataSubj1,false);
			}
		}.runTest();

		final Person badDataPerson2 = createPerson();
		final Subject badDataSubj2 = createSubject(badDataPerson2);
		badDataPerson2.setRaceCode(new DSETCD(new CD(RACE_WHITE), new CD(
				BAD_RACE_CODE)));
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(participant, badDataSubj2,false);
			}
		}.runTest();

	}

	/**
	 * @param participant
	 */
	protected void assertParticipant(final Participant participant) {
		assertEquals(GENDER_MALE, participant.getAdministrativeGenderCode());
		assertEquals(TEST_BIRTH_DATE, participant.getBirthDate());
		assertEquals(TEST_DEATH_DATE, participant.getDeathDate());
		assertTrue(participant.getDeathIndicator());
		assertEquals(ETHNIC_CODE_NOT_REPORTED, participant.getEthnicGroupCode());
		assertEquals(MARITAL_STATUS_SINGLE, participant.getMaritalStatusCode());
		assertEquals(TEST_FIRST_NAME, participant.getFirstName());
		assertEquals(TEST_MID_NAME, participant.getMiddleName());
		assertEquals(TEST_LAST_NAME, participant.getLastName());
		assertEquals(StringUtils.EMPTY, participant.getMaidenName());
		assertNotNull(participant.getAddress());
		assertEquals(TEST_STREET_ADDRESS, participant.getAddress()
				.getStreetAddress());
		assertEquals(TEST_CITY_NAME, participant.getAddress().getCity());
		assertEquals(TEST_STATE_CODE, participant.getAddress().getStateCode());
		assertEquals(TEST_ZIP_CODE, participant.getAddress().getPostalCode());
		assertEquals(TEST_COUNTRY, participant.getAddress().getCountryCode());
		assertEquals(Arrays.asList(new RaceCodeEnum[] { RaceCodeEnum.White,
				RaceCodeEnum.Asian }), (participant.getRaceCodes()));
		assertEquals(TEST_EMAIL_ADDR, participant.getEmail());
		assertEquals(TEST_PHONE, participant.getPhone());
		assertEquals(TEST_FAX, participant.getFax());
	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier)}
	 * .
	 */
	public void testConvertBiologicEntityIdentifier() {
		BiologicEntityIdentifier bioId = createBioEntityId();

		OrganizationAssignedIdentifier oaId = converter.convert(bioId);
		assertOrgAssId(oaId);

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
	 * @param oaId
	 */
	protected void assertOrgAssId(OrganizationAssignedIdentifier oaId) {
		assertNotNull(oaId);
		assertTrue(oaId.getPrimaryIndicator());
		assertEquals(TEST_BIO_ID, oaId.getValue());
		assertEquals(OrganizationIdentifierTypeEnum.MRN, oaId.getType());
		assertEquals(healthcareSite, oaId.getHealthcareSite());
	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.domain.Participant)}
	 * .
	 */
	public void testConvertParticipant() {
		Participant p = createParticipant();
		Subject s = converter.convert(p);
		assertSubject(s);
	}

	/**
	 * Test for
	 * {@link edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl#convert(AdvanceSearchCriterionParameter)}
	 */
	public void testConvertAdvanceSearchCriterionParameter() {
		AdvanceSearchCriterionParameter param = createAdvaceSearchParam();

		AdvancedSearchCriteriaParameter convParam = converter.convert(param);
		assertEquals(TEST_ATTRIBUTE_NAME, convParam.getAttributeName());
		assertEquals(TEST_OBJ_CTX_NAME, convParam.getContextObjectName());
		assertEquals(TEST_OBJ_NAME, convParam.getObjectName());
		assertEquals(TEST_PREDICATE, convParam.getPredicate());
		assertEquals(Arrays.asList(new String[] { TEST_VALUE1, TEST_VALUE2 }),
				convParam.getValues());

	}

}

/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.converters;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.test.AssertThrows;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.ParticipantStateCode;
import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.utils.BeanUtils;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.helpers.WebServiceRelatedTestCase;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject;

/**
 * @author dkrylov
 * 
 */
public class JAXBToDomainObjectConverterImplTest extends
		WebServiceRelatedTestCase {
	
	

	public void testTwoWayConversion() {
		StudyProtocolVersion xmlStudy = createStudy();
		Study study = converter.convert(xmlStudy);
		StudyProtocolVersion xmlStudy2 = converter.convert(study);
		assertTrue(BeanUtils.deepCompare(xmlStudy, xmlStudy2));
	}

	public void testConvertStudyProtocolVersion() {
		StudyProtocolVersion xmlStudy = createStudy();
		Study s = converter.convert(xmlStudy);
		assertTrue(s instanceof LocalStudy);
		assertEquals(TEST_STUDY_SHORT_TITLE, s.getShortTitleText());
		assertFalse(s.getBlindedIndicator());
		assertTrue(s.getMultiInstitutionIndicator());
		assertEquals("Phase 0 Trial", s.getPhaseCode());
		assertFalse(s.getRandomizedIndicator());
		assertEquals(100, s.getTargetAccrualNumber().intValue());
		assertEquals("Basic Science", s.getType());
		assertEquals(CoordinatingCenterStudyStatus.PENDING,
				s.getCoordinatingCenterStudyStatus());
		assertFalse(s.getStratificationIndicator());
		assertTrue(s.getStandaloneIndicator());
		assertFalse(s.getCompanionIndicator());
		assertEquals(ConsentRequired.ONE, s.getConsentRequired());
		assertFalse(s.getTherapeuticIntentIndicator());
		assertEquals(TEST_TARGET_REG_SYS, s.getTargetRegistrationSystem());

		// study orgs
		assertEquals(2, s.getStudyOrganizations().size());
		assertEquals(TEST_ORG_ID, s.getStudyCoordinatingCenter()
				.getHealthcareSite().getCtepCode());
		assertEquals(TEST_ORG_ID, s.getStudyFundingSponsors().get(0)
				.getHealthcareSite().getCtepCode());

		// study version
		assertEquals(TEST_STUDY_SHORT_TITLE, s.getShortTitleText());
		assertEquals(TEST_STUDY_DESCR, s.getLongTitleText());
		assertEquals(TEST_STUDY_DESCR, s.getDescriptionText());
		assertEquals(s.getDataEntryStatus(), StudyDataEntryStatus.INCOMPLETE);
		assertEquals(s.getVersionDateStr(), "10/05/2010");

		// registry status
		assertEquals(TEST_REGISTRY_STATUS, s
				.getPermissibleStudySubjectRegistryStatuses().get(0)
				.getRegistryStatus().getCode());
		assertEquals(TEST_SECONDARY_REASON_CODE, s
				.getPermissibleStudySubjectRegistryStatuses().get(0)
				.getSecondaryReasons().get(0).getCode());

		// consent and questions
		final Consent con = s.getConsents().get(0);
		assertEquals(TEST_CONSENT_TITLE, con.getName());
		assertTrue(con.getMandatoryIndicator());
		assertEquals(TEST_VERSION_NUMBER, con.getVersionId());
		assertEquals(2, con.getQuestions().size());
		assertEquals(TEST_CONSENT_QUESTION_1, con.getQuestions().get(0)
				.getCode());
		assertEquals(TEST_CONSENT_QUESTION_1, con.getQuestions().get(0)
				.getText());
		assertEquals(TEST_CONSENT_QUESTION_2, con.getQuestions().get(1)
				.getCode());
		assertEquals(TEST_CONSENT_QUESTION_2, con.getQuestions().get(1)
				.getText());

	}

	public void testConvertStudy() {
		Study study = createDomainStudy();
		StudyProtocolVersion s = converter.convert(study);
		assertEquals(TEST_TARGET_REG_SYS, s.getTargetRegistrationSystem()
				.getValue());

		final PermissibleStudySubjectRegistryStatus regSt = s
				.getPermissibleStudySubjectRegistryStatus().get(0);
		assertEquals(TEST_REGISTRY_STATUS, regSt.getRegistryStatus().getCode()
				.getCode());
		assertEquals(TEST_REGISTRY_STATUS, regSt.getRegistryStatus()
				.getDescription().getValue());
		assertEquals(TEST_PRIMARY_REASON_CODE, regSt.getRegistryStatus()
				.getPrimaryReason().get(0).getCode().getCode());
		assertEquals(TEST_PRIMARY_REASON_DESCR, regSt.getRegistryStatus()
				.getPrimaryReason().get(0).getDescription().getValue());
		assertEquals(TEST_SECONDARY_REASON_CODE, regSt.getSecondaryReason()
				.get(0).getCode().getCode());
		assertEquals(TEST_SECONDARY_REASON_DESCR, regSt.getSecondaryReason()
				.get(0).getDescription().getValue());

		final StudyProtocolDocumentVersion doc = s.getStudyProtocolDocument();
		assertEquals(TEST_STUDY_SHORT_TITLE, doc.getOfficialTitle().getValue());
		assertEquals(TEST_STUDY_DESCR, doc.getPublicTitle().getValue());
		assertEquals(TEST_STUDY_DESCR, doc.getPublicDescription().getValue());
		assertEquals(TEST_VERSION_DATE, parseISODate(doc.getVersionDate()
				.getValue()));
		assertEquals(TEST_CONSENT_RELATIONSHIP, doc
				.getDocumentVersionRelationship().get(0).getTypeCode()
				.getCode());
		final DocumentVersion consent = doc.getDocumentVersionRelationship()
				.get(0).getTarget();
		assertEquals(TEST_CONSENT_TITLE, consent.getOfficialTitle().getValue());
		assertEquals(2, consent.getDocumentVersionRelationship().size());
		assertEquals(TEST_CONSENT_QUESTION_1, consent
				.getDocumentVersionRelationship().get(0).getTarget()
				.getOfficialTitle().getValue());
		assertEquals(TEST_CONSENT_QUESTION_2, consent
				.getDocumentVersionRelationship().get(1).getTarget()
				.getOfficialTitle().getValue());

		List<DocumentIdentifier> ids = doc.getDocument()
				.getDocumentIdentifier();
		assertEquals(2, ids.size());
		final DocumentIdentifier id1 = doc.getDocument()
				.getDocumentIdentifier().get(0);
		assertEquals(TEST_STUDY_ID, id1.getIdentifier().getExtension());
		assertTrue(id1.getPrimaryIndicator().isValue());
		assertEquals(
				OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER
						.name(),
				id1.getTypeCode().getCode());
		assertEquals(TEST_ORG_ID, id1.getAssigningOrganization()
				.getOrganizationIdentifier().get(0).getIdentifier()
				.getExtension());
		assertTrue(id1.getAssigningOrganization().getOrganizationIdentifier()
				.get(0).getPrimaryIndicator().isValue());
		assertEquals(TEST_CTEP, id1.getAssigningOrganization()
				.getOrganizationIdentifier().get(0).getTypeCode().getCode());

		final DocumentIdentifier id2 = doc.getDocument()
				.getDocumentIdentifier().get(1);
		assertEquals(TEST_STUDY_ID, id2.getIdentifier().getExtension());
		assertFalse(id2.getPrimaryIndicator().isValue());
		assertEquals(
				OrganizationIdentifierTypeEnum.STUDY_FUNDING_SPONSOR.name(),
				id2.getTypeCode().getCode());
		assertEquals(TEST_ORG_ID, id2.getAssigningOrganization()
				.getOrganizationIdentifier().get(0).getIdentifier()
				.getExtension());
		assertTrue(id2.getAssigningOrganization().getOrganizationIdentifier()
				.get(0).getPrimaryIndicator().isValue());
		assertEquals(TEST_CTEP, id2.getAssigningOrganization()
				.getOrganizationIdentifier().get(0).getTypeCode().getCode());

	}

	public void testConvertConsentForSearchByExample() {
		edu.duke.cabig.c3pr.webservice.common.Consent xml = createConsent();
		Consent c = converter.convertConsentForSearchByExample(xml);
		assertTrue(c.getMandatoryIndicator());
		assertEquals(TEST_CONSENT_TITLE, c.getName());
		assertEquals(TEST_VERSION_NUMBER, c.getVersionId());
		assertTrue(CollectionUtils.isEmpty(c.getQuestions()));

		xml.setOfficialTitle(null);
		xml.setVersionNumberText(null);
		c = converter.convertConsentForSearchByExample(xml);
		assertNull(c.getName());
		assertNull(c.getVersionId());

	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject, boolean)}
	 * .
	 */
	public void testConvertSubjectBoolean() {
		Person person = createPerson();
		Subject subject = createSubject(person);
		Participant participant = converter.convert(subject, true, false);

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
				converter.convert(badDataSubject1, true, false);
			}
		}.runTest();

		Person badDataPerson2 = createPerson();
		final Subject badDataSubject2 = createSubject(badDataPerson2);
		badDataSubject2.setStateCode(iso.ST(BAD_STATE_CODE));
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(badDataSubject2, true, false);
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

		converter.convert(participant, subject, false);

		assertParticipant(participant);

		// test exceptions
		final Person badDataPerson1 = createPerson();
		final Subject badDataSubj1 = createSubject(badDataPerson1);
		badDataPerson1.setBirthDate(iso.TSDateTime(BAD_ISO_DATE));
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(participant, badDataSubj1, false);
			}
		}.runTest();

		final Person badDataPerson2 = createPerson();
		final Subject badDataSubj2 = createSubject(badDataPerson2);
		badDataPerson2.setRaceCode(iso.DSETCD(iso.CD(RACE_WHITE), iso.CD(
				BAD_RACE_CODE)));
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(participant, badDataSubj2, false);
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
		assertEquals(
				Arrays.asList(new RaceCodeEnum[] { RaceCodeEnum.White,
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

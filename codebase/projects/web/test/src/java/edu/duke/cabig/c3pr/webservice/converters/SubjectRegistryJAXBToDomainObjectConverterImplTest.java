/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.webservice.converters;

import java.util.Arrays;
import java.util.List;

import org.springframework.test.AssertThrows;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;

import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.domain.StudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.exception.ConversionException;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.helpers.SubjectRegistryRelatedTestCase;

public class SubjectRegistryJAXBToDomainObjectConverterImplTest extends SubjectRegistryRelatedTestCase {

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier)}
	 * .
	 */
	public void testConvertBiologicEntityIdentifier() {
		BiologicEntityIdentifier bioId = createBioEntityId();

		Identifier oaId = converter.convertBiologicIdentifiers(Arrays.asList(new BiologicEntityIdentifier[]{bioId})).get(0);
		assertOrgAssId((OrganizationAssignedIdentifier)oaId);

		final BiologicEntityIdentifier badBioId = createBioEntityId();
		badBioId.setIdentifier(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertBiologicIdentifiers(Arrays.asList(new BiologicEntityIdentifier[]{badBioId}));
			}
		}.runTest();

		final BiologicEntityIdentifier badBioId2 = createBioEntityId();
		badBioId2.setTypeCode(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertBiologicIdentifiers(Arrays.asList(new BiologicEntityIdentifier[]{badBioId2}));
			}
		}.runTest();

		final BiologicEntityIdentifier badBioId3 = createBioEntityId();
		badBioId3.setAssigningOrganization(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertBiologicIdentifiers(Arrays.asList(new BiologicEntityIdentifier[]{badBioId3}));
			}
		}.runTest();

	}
	
	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier)}
	 * .
	 */
	public void testConvertDocumentIdentifier() {
		DocumentIdentifier docId = createDocumentId();

		Identifier oaId = converter.convert(Arrays.asList(new DocumentIdentifier[]{docId})).get(0);
		assertOrgAssIdDoc((OrganizationAssignedIdentifier)oaId);

		final DocumentIdentifier badBioId = createDocumentId();
		badBioId.setIdentifier(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(Arrays.asList(new DocumentIdentifier[]{badBioId}));
			}
		}.runTest();

		final DocumentIdentifier badBioId2 = createDocumentId();
		badBioId2.setTypeCode(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(Arrays.asList(new DocumentIdentifier[]{badBioId2}));
			}
		}.runTest();

		final DocumentIdentifier badBioId3 = createDocumentId();
		badBioId3.setAssigningOrganization(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convert(Arrays.asList(new DocumentIdentifier[]{badBioId3}));
			}
		}.runTest();

	}
	
	public void testConvertHealthcareSitePrimaryIdentifier(){
		Organization organization = createOrganization();
		assertEquals(TEST_ORG_ID , converter.convertHealthcareSitePrimaryIdentifier(organization));
		
		final Organization badOrg1 = createOrganization();
		badOrg1.getOrganizationIdentifier().clear();
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertHealthcareSitePrimaryIdentifier(badOrg1);
			}
		}.runTest();
		
		final Organization badOrg2 = createOrganization();
		badOrg2.getOrganizationIdentifier().get(0).setIdentifier(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertHealthcareSitePrimaryIdentifier(badOrg2);
			}
		}.runTest();
		
		final Organization badOrg3 = createOrganization();
		badOrg3.getOrganizationIdentifier().get(0).getIdentifier().setExtension("");
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertHealthcareSitePrimaryIdentifier(badOrg3);
			}
		}.runTest();
	}
	
	public void testConvertSubjectConsent(){
		List<StudySubjectConsentVersion> subjectConsents = null;
		subjectConsents = getSubjectConsents();
		assertSubjectConsent(converter.convertSubjectConsent(subjectConsents));
		
		final List<StudySubjectConsentVersion> bad1 = getSubjectConsents();
		bad1.get(0).getSubjectConsentAnswer().get(0).getConsentQuestion().getOfficialTitle().setValue("");
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertSubjectConsent(bad1);
			}
		}.runTest();
		
		final List<StudySubjectConsentVersion> bad2 = getSubjectConsents();
		bad2.get(0).getSubjectConsentAnswer().get(0).getConsentQuestion().setOfficialTitle(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertSubjectConsent(bad2);
			}
		}.runTest();
		
		final List<StudySubjectConsentVersion> bad3 = getSubjectConsents();
		bad3.get(0).getSubjectConsentAnswer().get(0).setConsentQuestion(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertSubjectConsent(bad3);
			}
		}.runTest();
		
		final List<StudySubjectConsentVersion> bad4 = getSubjectConsents();
		bad4.get(0).getSubjectConsentAnswer().get(0).getMissedIndicator().setValue(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertSubjectConsent(bad4);
			}
		}.runTest();
		
		final List<StudySubjectConsentVersion> bad5 = getSubjectConsents();
		bad5.get(0).getSubjectConsentAnswer().get(0).setMissedIndicator(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertSubjectConsent(bad5);
			}
		}.runTest();
		
		final List<StudySubjectConsentVersion> bad6 = getSubjectConsents();
		bad6.get(0).getConsent().getOfficialTitle().setValue("");
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertSubjectConsent(bad6);
			}
		}.runTest();
		
		final List<StudySubjectConsentVersion> bad7 = getSubjectConsents();
		bad7.get(0).getConsent().setOfficialTitle(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertSubjectConsent(bad7);
			}
		}.runTest();
		
		final List<StudySubjectConsentVersion> bad8 = getSubjectConsents();
		bad8.get(0).setConsent(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
			  	converter.convertSubjectConsent(bad8);
			}
		}.runTest();
	}
	
	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.subjectmanagement.converters.JAXBToDomainObjectConverterImpl#convert(edu.duke.cabig.c3pr.webservice.subjectmanagement.BiologicEntityIdentifier)}
	 * .
	 */
	public void testConvertSubjectIdentifier() {
		SubjectIdentifier docId = createSubjectId();

		Identifier oaId = converter.convertSubjectIdentifiers(Arrays.asList(new SubjectIdentifier[]{docId})).get(0);
		assertOrgAssIdSub((OrganizationAssignedIdentifier)oaId);

		final SubjectIdentifier badSubId = createSubjectId();
		badSubId.setIdentifier(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertSubjectIdentifiers(Arrays.asList(new SubjectIdentifier[]{badSubId}));
			}
		}.runTest();

		final SubjectIdentifier badSubId2 = createSubjectId();
		badSubId2.setTypeCode(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertSubjectIdentifiers(Arrays.asList(new SubjectIdentifier[]{badSubId2}));
			}
		}.runTest();

		final SubjectIdentifier badSubId3 = createSubjectId();
		badSubId3.setAssigningOrganization(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertSubjectIdentifiers(Arrays.asList(new SubjectIdentifier[]{badSubId3}));
			}
		}.runTest();

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
	
	public void testConvertRegistryStatus(){
		PerformedStudySubjectMilestone status = createStatus();
		StudySubjectRegistryStatus actual = converter.convertRegistryStatus(status);
		assertEquals(TEST_REGISTRYSTATUS_CODE1, actual.getPermissibleStudySubjectRegistryStatus().getRegistryStatus().getCode());
		assertEquals(parseISODate(TEST_REGISTRYSTATUS_DATE1), actual.getEffectiveDate());
		assertEquals(TEST_REGISTRYSTATUS_REASON11 , actual.getReasons().get(0).getCode());
		assertEquals(TEST_REGISTRYSTATUS_REASON12 , actual.getReasons().get(1).getCode());
		
		final PerformedStudySubjectMilestone bad = createStatus();
		bad.setStatusDate(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertRegistryStatus(bad);
			}
		}.runTest();
		bad.getStatusCode().setCode("");
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertRegistryStatus(bad);
			}
		}.runTest();
		bad.setStatusCode(null);
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertRegistryStatus(bad);
			}
		}.runTest();
	}
	
	public void testConvertSubjectDemographics(){
		StudySubjectDemographics studySubjectDemographics = createSubjectDemographics();
		Person actual = converter.convertSubjectDemographics(studySubjectDemographics);
		assertPerson(actual);
	}
	
	public void testConvertToSubjectDemographics(){
		Subject subject = new Subject();
		Person person = createPerson();
		subject.setEntity(person);
		final StudySubjectDemographics actual = new StudySubjectDemographics();
		converter.convertToSubjectDemographics(actual, subject);
		assertSubjectDemographics(actual);
		
		// test exceptions
		
		final Person badDataPerson1 = createPerson();
		final Subject badDataSubj1 = new Subject();
		badDataSubj1.setEntity(badDataPerson1);
		badDataPerson1.setBirthDate(iso.TSDateTime(BAD_ISO_DATE));
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertToSubjectDemographics(actual, badDataSubj1);
			}
		}.runTest();

		final Person badDataPerson2 = createPerson();
		final Subject badDataSubj2 = new Subject();
		badDataSubj2.setEntity(badDataPerson2);
		badDataPerson2.setRaceCode(iso.DSETCD(iso.CD(RACE_WHITE), iso.CD(
				BAD_RACE_CODE)));
		new AssertThrows(ConversionException.class) {
			public void test() {
				converter.convertToSubjectDemographics(actual, badDataSubj2);
			}
		}.runTest();
	}
	
	public void testConvert(){
		StudySubject studySubject = createStudySubjectDomainObject();
		edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject actual = converter.convert(studySubject);
		assertPerson((Person)actual.getEntity());
		assertEquals(TEST_PAYMENT_METHOD, actual.getPaymentMethodCode().getCode());
		assertEquals(TEST_DATA_ENTRY_STATUS, actual.getStatusCode().getCode());
		assertEquals(TEST_STUDYSUBJECT_ID, actual.getSubjectIdentifier().get(0).getIdentifier().getExtension());
		assertEquals(TEST_CONSENT_DELIVERY_DATE1, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(0).getConsentDeliveryDate().getValue());
		assertEquals(TEST_CONSENT_SIGNED_DATE1, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(0).getInformedConsentDate().getValue());
		assertEquals(TEST_CONSENT_PRESENTER1, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(0).getConsentPresenter().getValue());
		assertEquals(TEST_CONSENTING_METHOD1, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(0).getConsentingMethod().getCode());
		assertEquals(TEST_CONSENT_ANS11, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(0).getSubjectConsentAnswer().get(0).getMissedIndicator().isValue());
		assertEquals(TEST_CONSENT_QUES11, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(0).getSubjectConsentAnswer().get(0).getConsentQuestion().getOfficialTitle().getValue());
		assertEquals(TEST_CONSENT_ANS12, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(0).getSubjectConsentAnswer().get(1).getMissedIndicator().isValue());
		assertEquals(TEST_CONSENT_QUES12, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(0).getSubjectConsentAnswer().get(1).getConsentQuestion().getOfficialTitle().getValue());
		assertEquals(TEST_CONSENT_DELIVERY_DATE2, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(1).getConsentDeliveryDate().getValue());
		assertEquals(TEST_CONSENT_SIGNED_DATE2, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(1).getInformedConsentDate().getValue());
		assertEquals(TEST_CONSENT_PRESENTER2, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(1).getConsentPresenter().getValue());
		assertEquals(TEST_CONSENTING_METHOD2, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(1).getConsentingMethod().getCode());
		assertEquals(TEST_CONSENT_ANS21, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(1).getSubjectConsentAnswer().get(0).getMissedIndicator().isValue());
		assertEquals(TEST_CONSENT_QUES21, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(1).getSubjectConsentAnswer().get(0).getConsentQuestion().getOfficialTitle().getValue());
		assertEquals(TEST_CONSENT_ANS22, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(1).getSubjectConsentAnswer().get(1).getMissedIndicator().isValue());
		assertEquals(TEST_CONSENT_QUES22, actual.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().get(1).getSubjectConsentAnswer().get(1).getConsentQuestion().getOfficialTitle().getValue());
		assertEquals(TEST_STUDY_ID, actual.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().get(0).getIdentifier().getExtension());
		assertEquals(TEST_SHORTTITLE, actual.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getOfficialTitle().getValue());
		assertEquals(TEST_LONGTITLE, actual.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getPublicTitle().getValue());
		assertEquals(TEST_DESC, actual.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getPublicDescription().getValue());
		assertEquals(TEST_HEALTHCARESITE_ID, actual.getStudySubjectProtocolVersion().getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier().get(0).getIdentifier().getExtension());
		assertEquals(TEST_REGISTRYSTATUS_CODE1, actual.getStudySubjectStatus().get(0).getStatusCode().getCode());
		assertEquals(TEST_REGISTRYSTATUS_DATE1, actual.getStudySubjectStatus().get(0).getStatusDate().getValue());
		assertEquals(TEST_REGISTRYSTATUS_REASON11, actual.getStudySubjectStatus().get(0).getReasonCode().getItem().get(0).getCode());
		assertEquals(TEST_REGISTRYSTATUS_REASON12, actual.getStudySubjectStatus().get(0).getReasonCode().getItem().get(1).getCode());
		assertEquals(TEST_REGISTRYSTATUS_CODE2, actual.getStudySubjectStatus().get(1).getStatusCode().getCode());
		assertEquals(TEST_REGISTRYSTATUS_DATE2, actual.getStudySubjectStatus().get(1).getStatusDate().getValue());
		assertEquals(TEST_REGISTRYSTATUS_REASON21, actual.getStudySubjectStatus().get(1).getReasonCode().getItem().get(0).getCode());
		assertEquals(TEST_REGISTRYSTATUS_REASON22, actual.getStudySubjectStatus().get(1).getReasonCode().getItem().get(1).getCode());
	}
	
}

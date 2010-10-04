/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.studyutility;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import junit.framework.TestCase;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.webservice.common.Consent;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.RegistryStatus;
import edu.duke.cabig.c3pr.webservice.common.RegistryStatusReason;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.EDText;
import edu.duke.cabig.c3pr.webservice.iso21090.II;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;

/**
 * @author dkrylov
 * 
 */
public class StudyUtilityImplTest extends TestCase {

	public static final String TEST_REGISTRY_STATUS = "Active";
	public static final String TEST_CONSENT_QUESTION_2 = "Question 2";
	public static final String TEST_CONSENT_QUESTION_1 = "Question 1";
	public static final String TEST_CONSENT_TEXT = "Consent text";
	public static final String TEST_CONSENT_TITLE = "Consent";
	public static final String TEST_CONSENT_QUESTION_RELATIONSHIP = "CONSENT_QUESTION";
	public static final String TEST_CONSENT_RELATIONSHIP = "CONSENT";
	public static final String TEST_STUDY_ID = "Study_01";
	public static final String TEST_CTEP = "CTEP";
	public static final String TEST_ORG_ID = "MN026";
	public static final String TEST_VERSION_NUMBER = "1.0";
	public static final String TEST_VERSION_DATE = "20101005000000";
	public static final String TEST_STUDY_DESCR = "Test Study";
	public static final String TEST_TARGET_REG_SYS = "C3PR";

	/**
	 * @param name
	 */
	public StudyUtilityImplTest(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.studyutility.StudyUtilityImpl#advancedQueryStudy(edu.duke.cabig.c3pr.webservice.studyutility.AdvancedQueryStudyRequest)}
	 * .
	 */
	public void testAdvancedQueryStudy() {

	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.studyutility.StudyUtilityImpl#createStudy(edu.duke.cabig.c3pr.webservice.studyutility.CreateStudyRequest)}
	 * .
	 */
	public void testCreateStudy() {

	}

	/**
	 * Test method for
	 * {@link edu.duke.cabig.c3pr.webservice.studyutility.StudyUtilityImpl#updateStudy(edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyRequest)}
	 * .
	 */
	public void testUpdateStudy() {

	}

	public StudyProtocolVersion createStudy() {
		StudyProtocolVersion study = new StudyProtocolVersion();
		study.setTargetRegistrationSystem(new ST(TEST_TARGET_REG_SYS));
		study.setStudyProtocolDocument(createStudyProtocolDocument());
		study.getPermissibleStudySubjectRegistryStatus().add(
				createPermissibleStudySubjectRegistryStatus());
		return study;
	}

	private PermissibleStudySubjectRegistryStatus createPermissibleStudySubjectRegistryStatus() {
		PermissibleStudySubjectRegistryStatus stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus());
		stat.getSecondaryReason().add(createRegistryStatusReason());
		return stat;
	}

	private RegistryStatus createRegistryStatus() {
		RegistryStatus stat = new RegistryStatus();
		stat.setCode(new CD(TEST_REGISTRY_STATUS));
		stat.getPrimaryReason().add(createRegistryStatusReason());
		return stat;
	}

	private RegistryStatusReason createRegistryStatusReason() {
		RegistryStatusReason r = new RegistryStatusReason();
		r.setCode(new CD("OTHER"));
		r.setDescription(new ST("Other"));
		r.setPrimaryIndicator(new BL(false));
		return r;
	}

	private StudyProtocolDocumentVersion createStudyProtocolDocument() {
		StudyProtocolDocumentVersion doc = new StudyProtocolDocumentVersion();
		doc.setOfficialTitle(new ST(TEST_STUDY_DESCR));
		doc.setPublicDescription(new ST(TEST_STUDY_DESCR));
		doc.setPublicTitle(new ST(TEST_STUDY_DESCR));
		doc.setText(new EDText(TEST_STUDY_DESCR));
		doc.setVersionDate(new TSDateTime(TEST_VERSION_DATE));
		doc.setVersionNumberText(new ST(TEST_VERSION_NUMBER));
		doc.setDocument(createStudyDocument());
		doc.getDocumentVersionRelationship().add(createConsentRelationship());
		return doc;
	}

	private DocumentVersionRelationship createConsentRelationship() {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(new CD(TEST_CONSENT_RELATIONSHIP));
		rel.setTarget(createConsent());
		return rel;
	}

	private Consent createConsent() {
		Consent consent = new Consent();
		consent.setMandatoryIndicator(new BL(false));
		consent.setOfficialTitle(new ST(TEST_CONSENT_TITLE));
		consent.setText(new EDText(TEST_CONSENT_TEXT));
		consent.setVersionDate(new TSDateTime(TEST_VERSION_DATE));
		consent.setVersionNumberText(new ST(TEST_VERSION_NUMBER));
		consent.setDocument(new Document());
		consent.getDocumentVersionRelationship().add(
				createConsentQuestionRelationship(TEST_CONSENT_QUESTION_1));
		consent.getDocumentVersionRelationship().add(
				createConsentQuestionRelationship(TEST_CONSENT_QUESTION_2));
		return consent;
	}

	private DocumentVersionRelationship createConsentQuestionRelationship(
			String text) {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(new CD(TEST_CONSENT_QUESTION_RELATIONSHIP));
		rel.setTarget(createConsentQuestion(text));
		return rel;
	}

	private DocumentVersion createConsentQuestion(String text) {
		DocumentVersion q = new DocumentVersion();
		q.setOfficialTitle(new ST(text));
		q.setText(new EDText(text));
		q.setVersionDate(new TSDateTime(TEST_VERSION_DATE));
		q.setVersionNumberText(new ST(TEST_VERSION_NUMBER));
		q.setDocument(new Document());
		return q;
	}

	private Document createStudyDocument() {
		Document doc = new Document();
		doc.getDocumentIdentifier().add(createStudyPrimaryIdentifier());
		doc.getDocumentIdentifier().add(createStudyProtocolAuthIdentifier());
		doc.getDocumentIdentifier().add(createStudyFundingSponsorIdentifier());
		return doc;
	}

	private DocumentIdentifier createStudyFundingSponsorIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(new II(TEST_STUDY_ID));
		docId.setPrimaryIndicator(new BL(false));
		docId.setTypeCode(new CD(
				OrganizationIdentifierTypeEnum.STUDY_FUNDING_SPONSOR.name()));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	private DocumentIdentifier createStudyPrimaryIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(new II(TEST_STUDY_ID));
		docId.setPrimaryIndicator(new BL(true));
		docId.setTypeCode(new CD(
				OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER
						.name()));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	private DocumentIdentifier createStudyProtocolAuthIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(new II(TEST_STUDY_ID));
		docId.setPrimaryIndicator(new BL(false));
		docId.setTypeCode(new CD(
				OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER
						.name()));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	private Organization createOrganization() {
		Organization org = new Organization();
		org.getOrganizationIdentifier().add(createOrganizationIdentifier());
		return org;
	}

	private OrganizationIdentifier createOrganizationIdentifier() {
		OrganizationIdentifier orgId = new OrganizationIdentifier();
		orgId.setIdentifier(new II(TEST_ORG_ID));
		orgId.setPrimaryIndicator(new BL(true));
		orgId.setTypeCode(new CD(TEST_CTEP));
		return orgId;
	}

	public static void main(String[] args) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(StudyProtocolVersion.class);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT , true);
		final StudyProtocolVersion study = new StudyUtilityImplTest("")
				.createStudy();
		JAXBElement<StudyProtocolVersion> jaxbElement = new JAXBElement<StudyProtocolVersion>(
				new QName("http://enterpriseservices.nci.nih.gov/Common",
						"study"), StudyProtocolVersion.class, study);
		m.marshal(jaxbElement, System.out);
	}

}

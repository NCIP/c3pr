/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;

import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETPerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETStudySubjectConsentVersion;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InitiateStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySiteProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubjectProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectRegistry;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectRegistryService;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectConsentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryStatusHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryStatusRequest;

/**
 * This test will run C3PR in embedded Tomcat and test Subject Registry web
 * service against it. <br>
 * 
 * @author Kruttik Aggarwal
 * @version 1.0
 */
public class SubjectRegistryWebServiceTest extends C3PREmbeddedTomcatTestBase {

	private static final String BAD_RACE_CODE = "invalid race code";
	private static final String BAD_ISO_DATE = "1990-01-01";	
	private static final String TS_DATETIME_PATTERN = "yyyyMMddHHmmss";
	private static final String TEST_FAX = "000-111-2222";
	private static final String TEST_FAX_ISO = "x-text-fax:" + TEST_FAX;
	private static final String TEST_PHONE = "333-444-5555";
	private static final String TEST_PHONE_ISO = "tel:" + TEST_PHONE;
	private static final String TEST_EMAIL_ADDR = "test@mail.com";
	private static final String TEST_EMAIL_ADDR_ISO = "mailto:"
			+ TEST_EMAIL_ADDR;
	private static final String RACE_ASIAN = "Asian";
	private static final String RACE_WHITE = "White";
	private static final String TEST_COUNTRY = "USA";
	private static final String TEST_ZIP_CODE = "22203-5555";
	private static final String TEST_STATE_CODE = "VA";
	private static final String TEST_CITY_NAME = "Arlington";
	private static final String TEST_STREET_ADDRESS = "1029 N Stuart St Unit 999";
	private static final String TEST_LAST_NAME = "Clooney";
	private static final String TEST_MID_NAME = "Z";
	private static final String TEST_FIRST_NAME = "Rudolph";
	private static final String MARITAL_STATUS_SINGLE = "Single";
	private static final String ETHNIC_CODE_NOT_REPORTED = "Not Reported";
	private static final String TEST_BIRTH_DATE_ISO = "19800101000000";
	private static final Date TEST_BIRTH_DATE = parseISODate(TEST_BIRTH_DATE_ISO);
	private static final String GENDER_MALE = "Male";
	private static final String STATE_ACTIVE = "ACTIVE";
	private static final String STATE_INACTIVE = "INACTIVE";
	private static final String ORG_ID_TYPE_MRN = "MRN";
	private static final String ORG_ID_TYPE_STUDY = "COORDINATING_CENTER_IDENTIFIER";
	private static final String ORG_ID_TYPE_STUDYSUBJECT = "COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER";
	private static final String ORG_ID_TYPE_STUDYSUBJECT_MODIFIED = "STUDY_SUBJECT_IDENTIFIER";
	private static final String TEST_BIO_ID = "test_subject_id";
	private static final Boolean TEST_BIO_ID_PRIMARYINDICATOR=true;
	private static final String TEST_STUDYSUBJECT_ID = "002";
	private static final String TEST_STUDYSUBJECT_ID_MODIFIED = "003";
	private static final Boolean TEST_STUDYSUBJECT_ID_PRIMARYINDICATOR=true;
	private static final String TEST_STUDY_ID = "test_study_id";
	private static final Boolean TEST_STUDY_ID_PRIMARYINDICATOR=true;
	private static final String TEST_HEALTHCARESITE_ID = "MN026";
	private static final Boolean TEST_HEALTHCARESITE_ID_PRIMARYINDICATOR=false;
	private static final String ORG_ID_TYPE_CTEP = "CTEP";
	private static final String TEST_ORG_ID = "MN026";
	private static final String TEST_CONSENT_DELIVERY_DATE1 = "20090101000000";
	private static final String TEST_CONSENT_SIGNED_DATE1 = "20100101000000";
	private static final String TEST_CONSENT_PRESENTER1 = "John Doe";
	private static final String TEST_CONSENTING_METHOD1 = "Written";
	private static final String TEST_CONSENT_NAME1 = "General1";
	private static final String TEST_CONSENT_VERSION1 = "1.0";
	private static final Boolean TEST_CONSENT_ANS11=true;
	private static final Boolean TEST_CONSENT_ANS12=false;
	private static final String TEST_CONSENT_QUES11="Q11";
	private static final String TEST_CONSENT_QUES12="Q12";
	private static final String TEST_CONSENT_DELIVERY_DATE2 = "20060101000000";
	private static final String TEST_CONSENT_SIGNED_DATE2 = "20070101000000";
	private static final String TEST_CONSENT_PRESENTER2 = "Deep Singh";
	private static final String TEST_CONSENTING_METHOD2 = "Verbal";
	private static final String TEST_CONSENT_NAME2 = "General2";
	private static final String TEST_CONSENT_VERSION2 = "2.0";
	private static final Boolean TEST_CONSENT_ANS21=true;
	private static final Boolean TEST_CONSENT_ANS22=false;
	private static final String TEST_CONSENT_QUES21="Q21";
	private static final String TEST_CONSENT_QUES22="Q22";
	private static final String TEST_REGISTRYSTATUS_CODE1="Screen Failed";
	private static final String TEST_REGISTRYSTATUS_DATE1 = "20080101000000";
	private static final String TEST_REGISTRYSTATUS_REASON11 = "FAILED INCLUSION";
	private static final String TEST_REGISTRYSTATUS_REASON12 = "Lab_Out_Of_Range1";
	private static final String TEST_REGISTRYSTATUS_CODE2="Withdrawn";
	private static final String TEST_REGISTRYSTATUS_DATE2 = "20070101000000";
	private static final String TEST_REGISTRYSTATUS_REASON21 = "UNWILLING";
	private static final String TEST_REGISTRYSTATUS_REASON22 = "Distance";
	private static final String TEST_PAYMENT_METHOD = "private insurance";
	private static final String TEST_PAYMENT_METHOD_MODIFIED = "private insurance";
	private static final String TEST_DATA_ENTRY_STATUS = "Complete";
	private static final String TEST_DATA_ENTRY_STATUS_MODIFIED = "Incomplete";
	private static final String TEST_SHORTTITLE = "short_title_text";
	private static final String TEST_LONGTITLE = "long_title";
	private static final String TEST_DESC = "description";
	private static final String TEST_VALUE2 = "v2";
	private static final String TEST_VALUE1 = "v1";
	private static final String TEST_PREDICATE = "=";
	private static final String TEST_OBJ_NAME = "edu.duke.cabig.c3pr.domain.Identifier";
	private static final String TEST_OBJ_CTX_NAME = "StudySubject";
	private static final String TEST_ATTRIBUTE_NAME = "value";
	
	private static final String STATUS_INACTIVE = "INACTIVE";
	private static final String STATUS_ACTIVE = "ACTIVE";

	private static final String SQL_CONSENT_QUESTIONS = "SELECT * FROM consent_questions WHERE EXISTS (SELECT id from consents where consents.id=consent_questions.con_id AND EXISTS (SELECT Id FROM study_versions where study_versions.id=consents.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}'))))";
	private static final String SQL_CONSENTS = "SELECT * FROM consents WHERE EXISTS (SELECT Id FROM study_versions where study_versions.id=consents.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')))";
	private static final String SQL_PERM_REG_STATS = "SELECT * FROM permissible_reg_stats WHERE EXISTS (SELECT Id FROM studies where studies.id=permissible_reg_stats.study_id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')) ORDER BY id";
	private static final String SQL_STUDY_ORGS = "SELECT * FROM study_organizations WHERE EXISTS (SELECT Id FROM studies where studies.id=study_organizations.study_id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')) ORDER BY id";
	private static final String SQL_STUDY_VERSIONS = "SELECT * FROM study_versions WHERE EXISTS (SELECT Id FROM studies where studies.id=study_versions.study_id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}'))";
	private static final String SQL_STUDIES = "SELECT * FROM studies WHERE EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')";
	private static final String SQL_IDENTIFIERS = "SELECT type, dtype FROM identifiers WHERE value='${STUDY_ID}' ORDER BY id";

	private static final String DBUNIT_DATASET_PREFIX = "/edu/duke/cabig/c3pr/webservice/integration/testdata/StudyUtilityWebServiceTest_";
		
	private static final QName SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/SubjectRegistryService",
			"SubjectRegistryService");
	private static final long TIMEOUT = 1000 * 60 * 10;
	private static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/SubjectRegistry";
	private static final String UPDATE_DISCRIMINATOR = " UPDATED";

	private final String STUDY_ID = RandomStringUtils.randomAlphanumeric(16);

	private URL endpointURL;

	private URL wsdlLocation;
	
	protected static final ISO21090Helper iso = new ISO21090Helper();
	
	/**
	 * Set this JVM property to true if this test should not bring up an
	 * instance of embedded Tomcat and use one already running locally at
	 * <b>https://localhost:8443/c3pr.
	 */
	private boolean noEmbeddedTomcat = Boolean.valueOf(System.getProperty(
			"noEmbeddedTomcat", "false"));

	@Override
	protected void setUp() throws Exception {
		if (noEmbeddedTomcat) {
			endpointURL = new URL(
					"https://localhost:8443/c3pr/services/services/SubjectRegistry");
			initDataSourceFile();
		} else {
			super.setUp();
			endpointURL = new URL("https://"
					+ InetAddress.getLocalHost().getHostName() + ":" + sslPort
					+ C3PR_CONTEXT + WS_ENDPOINT_SERVLET_PATH);
		}
		wsdlLocation = new URL(endpointURL.toString() + "?wsdl");

		logger.info("endpointURL: " + endpointURL);
		logger.info("wsdlLocation: " + wsdlLocation);

		// just to make sure we don't lock ourselves out on I/O to service
		// calls.
		System.setProperty("sun.net.client.defaultConnectTimeout", "" + TIMEOUT);
		System.setProperty("sun.net.client.defaultReadTimeout", "" + TIMEOUT);

	}

	@Override
	protected void tearDown() throws Exception {
		if (!noEmbeddedTomcat) {
			super.tearDown();
		}
	}

	/**
	 * @throws InterruptedException
	 * @throws IOException
	 * 
	 */
	public void testSubjectRegistryUtility() throws InterruptedException, IOException {

		try {
			executeInitiateStudySubjectTest();
			executeUpdateStudySubjectConsentTest();
			executeUpdateStudySubjectRegistryStatusTest();
			executeUpdateStudySubjectRegistryStatusHistoryTest();
			executeUpdatetudySubjectTest();
			executeQuerySubjectRegistryTest();
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			fail(ExceptionUtils.getFullStackTrace(e));
		}

	}


	private void executeInitiateStudySubjectTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final InitiateStudySubjectRegistryRequest request = new InitiateStudySubjectRegistryRequest();
		request.setSiteIdentifier(createOrgId());
		request.setStudyIdentifier(createDocumentId());
		request.setSubjectIdentifier(createBioEntityId());
		
		StudySubject studySubject = createStudySubject();
		request.setStudySubject(studySubject);
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		StudySubject createdStudySubject = service.initiateStudySubject(request).getStudySubject();
		assertNotNull(createdStudySubject);

		assertTrue(BeanUtils.deepCompare(createStudySubjectJAXBObject(), createdStudySubject));

	}

	private void executeUpdateStudySubjectConsentTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final UpdateStudySubjectConsentRequest request = new UpdateStudySubjectConsentRequest();
		request.setStudySubjectIdentifier(createSubjectId());
		
		DSETStudySubjectConsentVersion dsetStudySubjectConsentVersion = new DSETStudySubjectConsentVersion();
		dsetStudySubjectConsentVersion.getItem().addAll(getSubjectConsents());
		request.setStudySubjectConsentVersions(dsetStudySubjectConsentVersion);
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		StudySubject createdStudySubject = service.updateStudySubjectConsent(request).getStudySubject();
		assertNotNull(createdStudySubject);

		StudySubject expected = createStudySubjectJAXBObject();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		assertTrue(BeanUtils.deepCompare(expected, createdStudySubject));

	}
	
	private void executeUpdateStudySubjectRegistryStatusTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final UpdateStudySubjectRegistryStatusRequest request = new UpdateStudySubjectRegistryStatusRequest();
		request.setStudySubjectIdentifier(createSubjectId());
		
		request.setStudySubjectStatus(createStatus1());
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		StudySubject createdStudySubject = service.updateStudySubjectRegistryStatus(request).getStudySubject();
		assertNotNull(createdStudySubject);

		StudySubject expected = createStudySubjectJAXBObject();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus1());
		assertTrue(BeanUtils.deepCompare(expected, createdStudySubject));

	}
	
	private void executeUpdateStudySubjectRegistryStatusHistoryTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final UpdateStudySubjectRegistryStatusHistoryRequest request = new UpdateStudySubjectRegistryStatusHistoryRequest();
		request.setStudySubjectIdentifier(createSubjectId());
		
		DSETPerformedStudySubjectMilestone dsetPerformedStudySubjectMilestone = new DSETPerformedStudySubjectMilestone();
		dsetPerformedStudySubjectMilestone.getItem().add(createStatus2());
		request.setStudySubjectRegistryStatusHistory(dsetPerformedStudySubjectMilestone);
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		StudySubject createdStudySubject = service.updateStudySubjectRegistryStatusHistory(request).getStudySubject();
		assertNotNull(createdStudySubject);

		StudySubject expected = createStudySubjectJAXBObject();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus2());
		assertTrue(BeanUtils.deepCompare(expected, createdStudySubject));

	}
	
	private void executeUpdatetudySubjectTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final UpdateStudySubjectRegistryRequest request = new UpdateStudySubjectRegistryRequest();
		request.setStudySubjectIdentifier(createSubjectId());
		
		StudySubject studySubject = createStudySubjectModified();
		request.setStudySubject(studySubject);
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		StudySubject createdStudySubject = service.updateStudySubject(request).getStudySubject();
		assertNotNull(createdStudySubject);
		StudySubject expected = createStudySubjectJAXBObjectModified();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus2());
		assertTrue(BeanUtils.deepCompare(expected, createdStudySubject));

	}
	
	private void executeQuerySubjectRegistryTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final QueryStudySubjectRegistryRequest request = new QueryStudySubjectRegistryRequest();
		DSETAdvanceSearchCriterionParameter dsetAdvanceSearchCriterionParameter = new DSETAdvanceSearchCriterionParameter();
		dsetAdvanceSearchCriterionParameter.getItem().add(createAdvaceSearchParam());
		request.setSearchParameter(dsetAdvanceSearchCriterionParameter);
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		DSETStudySubject studySubjects = service.querySubjectRegistry(request).getStudySubjects();
		assertNotNull(studySubjects);
		assertEquals(1, studySubjects.getItem().size());
		StudySubject expected = createStudySubjectJAXBObjectModified();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus2());
		assertTrue(BeanUtils.deepCompare(expected, studySubjects.getItem().get(0)));

	}
	
	private SubjectRegistry getService() {
		SubjectRegistryService service = new SubjectRegistryService(wsdlLocation,
				SERVICE_NAME);
		SOAPUtils.installSecurityHandler(service);
		SubjectRegistry client = service.getSubjectRegistry();
		return client;
	}


	public edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject createStudySubject() {
		edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject studySubject = new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject();
		studySubject.setPaymentMethodCode(iso.CD(TEST_PAYMENT_METHOD));
		studySubject.setStatusCode(iso.CD(TEST_DATA_ENTRY_STATUS));
		
		studySubject.getSubjectIdentifier().add(createSubjectId());
		return studySubject;
	}
	
	public edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject createStudySubjectModified() {
		edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject studySubject = new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject();
		studySubject.setPaymentMethodCode(iso.CD(TEST_PAYMENT_METHOD_MODIFIED));
		studySubject.setStatusCode(iso.CD(TEST_DATA_ENTRY_STATUS_MODIFIED));
		
		studySubject.getSubjectIdentifier().add(createSubjectIdModified());
		return studySubject;
	}
	
	protected static Date parseISODate(String isoDate) {
		try {
			return DateUtils.parseDate(isoDate,
					new String[] { TS_DATETIME_PATTERN });
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static OrganizationIdentifier createOrgId(){
		OrganizationIdentifier oid = new OrganizationIdentifier();
		oid.setIdentifier(iso.II(TEST_ORG_ID));
		oid.setPrimaryIndicator(iso.BL(true));
		oid.setTypeCode(iso.CD(ORG_ID_TYPE_CTEP));
		return oid;
	}
	
	/**
	 * @return
	 */
	public static BiologicEntityIdentifier createBioEntityId() {
		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setAssigningOrganization(createOrganization());
		bioId.setIdentifier(iso.II(TEST_BIO_ID));
		bioId.setTypeCode(iso.CD(ORG_ID_TYPE_MRN));
		bioId.setEffectiveDateRange(iso.IVLTSDateTime(NullFlavor.NI));
		bioId.setPrimaryIndicator(iso.BL(TEST_BIO_ID_PRIMARYINDICATOR));
		return bioId;
	}
	
	/**
	 * @return
	 */
	public static DocumentIdentifier createDocumentId() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setAssigningOrganization(createOrganization());
		docId.setIdentifier(iso.II(TEST_STUDY_ID));
		docId.setTypeCode(iso.CD(ORG_ID_TYPE_STUDY));
		docId.setPrimaryIndicator(iso.BL(TEST_STUDY_ID_PRIMARYINDICATOR));
		return docId;
	}
	
	/**
	 * @return
	 */
	public static SubjectIdentifier createSubjectId() {
		SubjectIdentifier subId = new SubjectIdentifier();
		subId.setAssigningOrganization(createOrganization());
		subId.setIdentifier(iso.II(TEST_STUDYSUBJECT_ID));
		subId.setTypeCode(iso.CD(ORG_ID_TYPE_STUDYSUBJECT));
		subId.setPrimaryIndicator(iso.BL(TEST_STUDYSUBJECT_ID_PRIMARYINDICATOR));
		return subId;
	}
	
	/**
	 * @return
	 */
	public static SubjectIdentifier createSubjectIdModified() {
		SubjectIdentifier subId = new SubjectIdentifier();
		subId.setAssigningOrganization(createOrganization());
		subId.setIdentifier(iso.II(TEST_STUDYSUBJECT_ID_MODIFIED));
		subId.setTypeCode(iso.CD(ORG_ID_TYPE_STUDYSUBJECT_MODIFIED));
		subId.setPrimaryIndicator(iso.BL(TEST_STUDYSUBJECT_ID_PRIMARYINDICATOR));
		return subId;
	}
	
	public static Organization createOrganization(){
		Organization organization = new Organization();
		OrganizationIdentifier oid = createOrgId();
		organization.getOrganizationIdentifier().add(oid);
		return organization;
	}
	
	public static List<StudySubjectConsentVersion> getSubjectConsents(){
		List<StudySubjectConsentVersion> returnList = new ArrayList<StudySubjectConsentVersion>();
		//add 1st consent
		StudySubjectConsentVersion studySubjectConsentVersion = new StudySubjectConsentVersion();
		studySubjectConsentVersion.setConsentDeliveryDate(iso.TSDateTime(TEST_CONSENT_DELIVERY_DATE1));
		studySubjectConsentVersion.setInformedConsentDate(iso.TSDateTime(TEST_CONSENT_SIGNED_DATE1));
		studySubjectConsentVersion.setConsentingMethod(iso.CD(TEST_CONSENTING_METHOD1));
		studySubjectConsentVersion.setConsentPresenter(iso.ST(TEST_CONSENT_PRESENTER1));
		studySubjectConsentVersion.setConsent(new DocumentVersion());
		studySubjectConsentVersion.getConsent().setOfficialTitle(iso.ST(TEST_CONSENT_NAME1));
		studySubjectConsentVersion.getConsent().setVersionNumberText(iso.ST(TEST_CONSENT_VERSION1));
		
		PerformedStudySubjectMilestone subjectAnswer = new PerformedStudySubjectMilestone();
		subjectAnswer.setMissedIndicator(iso.BL(TEST_CONSENT_ANS11));
		subjectAnswer.setConsentQuestion(new DocumentVersion());
		subjectAnswer.getConsentQuestion().setOfficialTitle(iso.ST(TEST_CONSENT_QUES11));
		studySubjectConsentVersion.getSubjectConsentAnswer().add(subjectAnswer);
		subjectAnswer = new PerformedStudySubjectMilestone();
		subjectAnswer.setMissedIndicator(iso.BL(TEST_CONSENT_ANS12));
		subjectAnswer.setConsentQuestion(new DocumentVersion());
		subjectAnswer.getConsentQuestion().setOfficialTitle(iso.ST(TEST_CONSENT_QUES12));
		studySubjectConsentVersion.getSubjectConsentAnswer().add(subjectAnswer);
		
		returnList.add(studySubjectConsentVersion);
		
		//add 2nd consent
		studySubjectConsentVersion = new StudySubjectConsentVersion();
		studySubjectConsentVersion.setConsentDeliveryDate(iso.TSDateTime(TEST_CONSENT_DELIVERY_DATE2));
		studySubjectConsentVersion.setInformedConsentDate(iso.TSDateTime(TEST_CONSENT_SIGNED_DATE2));
		studySubjectConsentVersion.setConsentingMethod(iso.CD(TEST_CONSENTING_METHOD2));
		studySubjectConsentVersion.setConsentPresenter(iso.ST(TEST_CONSENT_PRESENTER2));
		studySubjectConsentVersion.setConsent(new DocumentVersion());
		studySubjectConsentVersion.getConsent().setOfficialTitle(iso.ST(TEST_CONSENT_NAME2));
		studySubjectConsentVersion.getConsent().setVersionNumberText(iso.ST(TEST_CONSENT_VERSION2));
		
		subjectAnswer = new PerformedStudySubjectMilestone();
		subjectAnswer.setMissedIndicator(iso.BL(TEST_CONSENT_ANS21));
		subjectAnswer.setConsentQuestion(new DocumentVersion());
		subjectAnswer.getConsentQuestion().setOfficialTitle(iso.ST(TEST_CONSENT_QUES21));
		studySubjectConsentVersion.getSubjectConsentAnswer().add(subjectAnswer);
		subjectAnswer = new PerformedStudySubjectMilestone();
		subjectAnswer.setMissedIndicator(iso.BL(TEST_CONSENT_ANS22));
		subjectAnswer.setConsentQuestion(new DocumentVersion());
		subjectAnswer.getConsentQuestion().setOfficialTitle(iso.ST(TEST_CONSENT_QUES22));
		studySubjectConsentVersion.getSubjectConsentAnswer().add(subjectAnswer);
		
		returnList.add(studySubjectConsentVersion);
		
		return returnList;
	}
	
	/**
	 * @return
	 */
	public static AdvanceSearchCriterionParameter createAdvaceSearchParam() {
		AdvanceSearchCriterionParameter param = new AdvanceSearchCriterionParameter();
		param.setAttributeName(iso.ST(TEST_ATTRIBUTE_NAME));
		param.setObjectContextName(iso.ST(TEST_OBJ_CTX_NAME));
		param.setObjectName(iso.ST(TEST_OBJ_NAME));
		param.setPredicate(iso.CD(TEST_PREDICATE));
		param.setValues(iso.DSETST(Arrays.asList(new ST[] {iso.ST(TEST_STUDYSUBJECT_ID_MODIFIED) })));
		return param;
	}
	
	public static PerformedStudySubjectMilestone createStatus1(){
		PerformedStudySubjectMilestone status = new PerformedStudySubjectMilestone();
		status.setStatusCode(iso.CD(TEST_REGISTRYSTATUS_CODE1));
		status.setStatusDate(iso.TSDateTime(TEST_REGISTRYSTATUS_DATE1));
		status.setReasonCode(new DSETCD());
		status.getReasonCode().getItem().add(iso.CD(TEST_REGISTRYSTATUS_REASON11));
		status.getReasonCode().getItem().add(iso.CD(TEST_REGISTRYSTATUS_REASON12));
		return status;
	}
	
	public static PerformedStudySubjectMilestone createStatus2(){
		PerformedStudySubjectMilestone status = new PerformedStudySubjectMilestone();
		status.setStatusCode(iso.CD(TEST_REGISTRYSTATUS_CODE2));
		status.setStatusDate(iso.TSDateTime(TEST_REGISTRYSTATUS_DATE2));
		status.setReasonCode(new DSETCD());
		status.getReasonCode().getItem().add(iso.CD(TEST_REGISTRYSTATUS_REASON21));
		status.getReasonCode().getItem().add(iso.CD(TEST_REGISTRYSTATUS_REASON22));
		return status;
	}
	
	public static edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject createStudySubjectJAXBObject(){
		edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject studySubject = new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject();
		studySubject.setEntity(createPerson());
		studySubject.setPaymentMethodCode(iso.CD(TEST_PAYMENT_METHOD));
		studySubject.setStatusCode(iso.CD(TEST_DATA_ENTRY_STATUS));
		
		studySubject.getSubjectIdentifier().add(createSubjectId());
		
		StudySubjectProtocolVersionRelationship studySubjectProtocolVersion = new StudySubjectProtocolVersionRelationship();
		studySubject.setStudySubjectProtocolVersion(studySubjectProtocolVersion);
		studySubjectProtocolVersion.setStudySiteProtocolVersion(new StudySiteProtocolVersionRelationship());
		
		//setup study
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudyProtocolVersion(new StudyProtocolVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().setStudyProtocolDocument(new StudyProtocolDocumentVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicTitle(iso.ST(TEST_LONGTITLE));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setOfficialTitle(iso.ST(TEST_SHORTTITLE));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicDescription(iso.ST(TEST_DESC));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setDocument(new Document());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().add(createDocumentId());
		
		//setup studysite
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudySite(new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySite());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().setOrganization(new Organization());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier().add(createOrgId());
		
		return studySubject;
	}
	
	public static edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject createStudySubjectJAXBObjectModified(){
		edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject studySubject = new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject();
		studySubject.setEntity(createPerson());
		studySubject.setPaymentMethodCode(iso.CD(TEST_PAYMENT_METHOD_MODIFIED));
		studySubject.setStatusCode(iso.CD(TEST_DATA_ENTRY_STATUS_MODIFIED));
		
		studySubject.getSubjectIdentifier().add(createSubjectIdModified());
		
		StudySubjectProtocolVersionRelationship studySubjectProtocolVersion = new StudySubjectProtocolVersionRelationship();
		studySubject.setStudySubjectProtocolVersion(studySubjectProtocolVersion);
		studySubjectProtocolVersion.setStudySiteProtocolVersion(new StudySiteProtocolVersionRelationship());
		
		//setup study
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudyProtocolVersion(new StudyProtocolVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().setStudyProtocolDocument(new StudyProtocolDocumentVersion());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicTitle(iso.ST(TEST_LONGTITLE));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setOfficialTitle(iso.ST(TEST_SHORTTITLE));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setPublicDescription(iso.ST(TEST_DESC));
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().setDocument(new Document());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudyProtocolVersion().getStudyProtocolDocument().getDocument().getDocumentIdentifier().add(createDocumentId());
		
		//setup studysite
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudySite(new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySite());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().setOrganization(new Organization());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier().add(createOrgId());
		
		return studySubject;
	}
	
	/**
	 * @return
	 */
	public static Person createPerson() {
		Person person = new Person();
		person.getBiologicEntityIdentifier().add(createBioEntityId());
		person.setAdministrativeGenderCode(iso.CD(GENDER_MALE));
		person.setBirthDate(iso.TSDateTime(TEST_BIRTH_DATE_ISO));
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
		person.setTelecomAddress(iso.BAGTEL(iso.TEL(TEST_EMAIL_ADDR_ISO),
				iso.TEL(TEST_PHONE_ISO), iso.TEL(TEST_FAX_ISO)));
		return person;
	}

}

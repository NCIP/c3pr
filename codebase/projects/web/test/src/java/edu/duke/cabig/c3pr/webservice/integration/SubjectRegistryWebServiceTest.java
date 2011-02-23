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

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;

import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Consent;
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.DSETPerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.common.DSETPerson;
import edu.duke.cabig.c3pr.webservice.common.DSETStudySubjectConsentVersion;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.StudySiteProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudySubjectProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.subjectregistry.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.ImportStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.InitiateStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryConsentsByStudySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryByConsentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryByStatusRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.QueryStudySubjectRegistryStatusHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.RetrieveStudySubjectDemographyHistoryRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectRegistry;
import edu.duke.cabig.c3pr.webservice.subjectregistry.SubjectRegistryService;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectConsentRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistry.UpdateStudySubjectRegistryDemographyRequest;
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

	protected static final String TEST_FAX_MODIFIED = "222-111-2222";
	protected static final String TEST_FAX_ISO_MODIFIED = "x-text-fax:" + TEST_FAX_MODIFIED;
	protected static final String TEST_PHONE_MODIFIED = "000-444-5555";
	protected static final String TEST_PHONE_ISO_MODIFIED = "tel:" + TEST_PHONE_MODIFIED;
	protected static final String TEST_EMAIL_ADDR_MODIFIED = "test_modified@mail.com";
	protected static final String TEST_EMAIL_ADDR_ISO_MODIFIED = "mailto:"
			+ TEST_EMAIL_ADDR_MODIFIED;
	protected static final String RACE_ASIAN_MODIFIED = "Black or African American";
	protected static final String RACE_WHITE_MODIFIED = "American Indian or Alaska Native";
	protected static final String TEST_COUNTRY_MODIFIED = "India";
	protected static final String TEST_ZIP_CODE_MODIFIED = "20190";
	protected static final String TEST_STATE_CODE_MODIFIED = "PA";
	protected static final String TEST_CITY_NAME_MODIFIED = "Reston";
	protected static final String TEST_STREET_ADDRESS_MODIFIED = "13921 Park Center Rd STE 420";
	protected static final String TEST_LAST_NAME_MODIFIED = "Davis";
	protected static final String TEST_MID_NAME_MODIFIED = "M";
	protected static final String TEST_FIRST_NAME_MODIFIED = "Geena";
	protected static final String TEST_NAME_PREFIX_MODIFIED = "Sr.";
	protected static final String TEST_NAME_SUFFIX_MODIFIED = "M.D.";
	protected static final String TS_DATETIME_PATTERN = "yyyyMMddHHmmss";
	protected static final String TEST_FAX = "000-111-2222";
	protected static final String TEST_FAX_ISO = "x-text-fax:" + TEST_FAX;
	protected static final String TEST_PHONE = "333-444-5555";
	protected static final String TEST_PHONE_ISO = "tel:" + TEST_PHONE;
	protected static final String TEST_EMAIL_ADDR = "test@mail.com";
	protected static final String TEST_EMAIL_ADDR_ISO = "mailto:"
			+ TEST_EMAIL_ADDR;
	protected static final String RACE_ASIAN = "Asian";
	protected static final String RACE_WHITE = "White";
	protected static final String TEST_COUNTRY = "USA";
	protected static final String TEST_ZIP_CODE = "22203-5555";
	protected static final String TEST_STATE_CODE = "VA";
	protected static final String TEST_CITY_NAME = "Arlington";
	protected static final String TEST_STREET_ADDRESS = "1029 N Stuart St Unit 999";
	protected static final String TEST_LAST_NAME = "Clooney";
	protected static final String TEST_NAME_PREFIX = "Jr.";
	protected static final String TEST_NAME_SUFFIX = "Ph.D.";
	protected static final String TEST_MID_NAME = "Z";
	protected static final String TEST_FIRST_NAME = "Rudolph";
	protected static final String MARITAL_STATUS_SINGLE = "Single";
	protected static final String MARITAL_STATUS_SINGLE_MODIFIED = "Married";
	protected static final String ETHNIC_CODE_NOT_REPORTED = "Not Reported";
	protected static final String ETHNIC_CODE_MODIFIED = "Hispanic or Latino";
	protected static final String TEST_BIRTH_DATE_ISO = "19800101000000";
	protected static final String TEST_BIRTH_DATE_ISO_MODIFIED = "19990205000000";
	protected static final String GENDER_MALE = "Male";
	protected static final String GENDER_MALE_MODIFIED = "Female";
	protected static final String ORG_ID_TYPE_MRN = "MRN";
	protected static final String ORG_ID_TYPE_STUDY = "COORDINATING_CENTER_IDENTIFIER";
	protected static final String ORG_ID_TYPE_STUDYSUBJECT = "COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER";
	protected static final String ORG_ID_TYPE_STUDYSUBJECT_MODIFIED = "STUDY_SUBJECT_IDENTIFIER";
	protected static final String TEST_BIO_ID = "test_subject_id";
	protected static final String TEST_BIO_ID_IMPORT = "test_subject_id_import";
	protected static final Boolean TEST_BIO_ID_PRIMARYINDICATOR=true;
	protected static final String TEST_STUDYSUBJECT_ID = "002";
	protected static final String TEST_STUDYSUBJECT_ID_MODIFIED = "003";
	protected static final String TEST_STUDYSUBJECT_ID_IMPORT = "004";
	protected static final Boolean TEST_STUDYSUBJECT_ID_PRIMARYINDICATOR=true;
	protected static final String TEST_STUDY_ID = "test_study_id";
	protected static final Boolean TEST_STUDY_ID_PRIMARYINDICATOR=true;
	protected static final String ORG_ID_TYPE_CTEP = "CTEP";
	protected static final String TEST_ORG_ID = "MN026";
	protected static final String TEST_CONSENT_DELIVERY_DATE1 = "20090101000000";
	protected static final String TEST_CONSENT_SIGNED_DATE1 = "20100101000000";
	protected static final String TEST_CONSENT_DECLINED_DATE1 = "20100202000000";
	protected static final String TEST_CONSENT_PRESENTER1 = "John Doe";
	protected static final String TEST_CONSENTING_METHOD1 = "Written";
	protected static final String TEST_CONSENT_NAME1 = "General1";
	protected static final String TEST_CONSENT_DESC1 = "Desc1";
	protected static final String TEST_CONSENTING_DOCID1 = "DOC_ID1";
	protected static final String TEST_CONSENT_VERSION1 = "1.0";
	protected static final Boolean TEST_CONSENT_ANS11=true;
	protected static final Boolean TEST_CONSENT_ANS12=false;
	protected static final String TEST_CONSENT_QUES11="Q11";
	protected static final String TEST_CONSENT_QUES12="Q12";
	protected static final String TEST_CONSENT_DELIVERY_DATE2 = "20060101000000";
	protected static final String TEST_CONSENT_SIGNED_DATE2 = "20070101000000";
	protected static final String TEST_CONSENT_DECLINED_DATE2 = "20070202000000";
	protected static final String TEST_CONSENT_PRESENTER2 = "Deep Singh";
	protected static final String TEST_CONSENTING_METHOD2 = "Verbal";
	protected static final String TEST_CONSENT_NAME2 = "General2";
	protected static final String TEST_CONSENT_DESC2 = "Desc2";
	protected static final String TEST_CONSENTING_DOCID2 = "DOC_ID2";
	protected static final String TEST_CONSENT_VERSION2 = "2.0";
	protected static final Boolean TEST_CONSENT_ANS21=true;
	protected static final Boolean TEST_CONSENT_ANS22=false;
	protected static final String TEST_CONSENT_QUES21="Q21";
	protected static final String TEST_CONSENT_QUES22="Q22";
	protected static final String TEST_REGISTRYSTATUS_CODE1="Screen Failed";
	protected static final String TEST_REGISTRYSTATUS_COMMENT1="Some Comment";
	protected static final String TEST_REGISTRYSTATUS_DATE1 = "20080101000000";
	protected static final String TEST_REGISTRYSTATUS_REASON11 = "FAILED INCLUSION";
	protected static final String TEST_REGISTRYSTATUS_REASON12 = "Lab_Out_Of_Range1";
	protected static final String TEST_REGISTRYSTATUS_CODE2="Withdrawn";
	protected static final String TEST_REGISTRYSTATUS_DATE2 = "20070101000000";
	protected static final String TEST_REGISTRYSTATUS_REASON21 = "UNWILLING";
	protected static final String TEST_REGISTRYSTATUS_REASON22 = "Distance";
	protected static final String TEST_PAYMENT_METHOD = "private insurance";
	protected static final String TEST_PAYMENT_METHOD_MODIFIED = "private insurance";
	protected static final String TEST_DATA_ENTRY_STATUS = "Complete";
	protected static final String TEST_DATA_ENTRY_STATUS_MODIFIED = "Incomplete";
	protected static final String TEST_SHORTTITLE = "short_title_text";
	protected static final String TEST_LONGTITLE = "long_title";
	protected static final String TEST_DESC = "description";
	protected static final String TEST_PREDICATE = "=";
	protected static final String TEST_OBJ_NAME = "edu.duke.cabig.c3pr.domain.Identifier";
	protected static final String TEST_OBJ_CTX_NAME = "StudySubject";
	protected static final String TEST_ATTRIBUTE_NAME = "value";
	
	private static final QName SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/SubjectRegistryService",
			"SubjectRegistryService");	
	private static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/SubjectRegistry";

	private URL endpointURL;

	private URL wsdlLocation;
	
	protected static final ISO21090Helper iso = new ISO21090Helper();
	
	/**
	 * Set this JVM property to true if this test should not bring up an
	 * instance of embedded Tomcat and use one already running locally at
	 * <b>https://localhost:8443/c3pr.
	 */
	protected boolean noEmbeddedTomcat = Boolean.valueOf(System.getProperty(
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
	public void testSubjectRegistryUtility() throws InterruptedException, IOException, Exception {

		try {
			executeInitiateStudySubjectTest();
			executeUpdateStudySubjectConsentTest();
			executeUpdateStudySubjectRegistryStatusTest();
			executeUpdateStudySubjectRegistryStatusHistoryTest();
			executeUpdatetudySubjectTest();
			executeQuerySubjectRegistryTest();
			executeUpdateStudySubjectDemographyTest();
			executeRetrieveStudySubjectDemographyHistoryTest();
			executeQueryStudySubjectRegistryStatusHistoryTest();
			executeQuerySubjectRegistryByRegistryStatusTest();
			executeQuerySubjectRegistryByConsentTest();
			executeQueryConsentsByStudySubjectTest();
			executeImportSubjectRegistryTest();
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			fail(ExceptionUtils.getFullStackTrace(e));
		}

	}


	protected void executeInitiateStudySubjectTest() throws SQLException, Exception {
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
		System.out.println();
		StudySubject createdStudySubject = service.initiateStudySubject(request).getStudySubject();
		assertNotNull(createdStudySubject);

		assertTrue(BeanUtils.deepCompare(createExpectedStudySubject(), createdStudySubject));

	}

	protected void executeUpdateStudySubjectConsentTest() throws SQLException, Exception {
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
		System.out.println();
		StudySubject createdStudySubject = service.updateStudySubjectConsent(request).getStudySubject();
		assertNotNull(createdStudySubject);

		StudySubject expected = createExpectedStudySubject();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		assertTrue(BeanUtils.deepCompare(expected, createdStudySubject));

	}
	
	protected void executeUpdateStudySubjectRegistryStatusTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final UpdateStudySubjectRegistryStatusRequest request = new UpdateStudySubjectRegistryStatusRequest();
		request.setStudySubjectIdentifier(createSubjectId());
		
		request.setStudySubjectStatus(createStatus1());
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		StudySubject createdStudySubject = service.updateStudySubjectRegistryStatus(request).getStudySubject();
		assertNotNull(createdStudySubject);

		StudySubject expected = createExpectedStudySubject();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus1());
		assertTrue(BeanUtils.deepCompare(expected, createdStudySubject));

	}
	
	protected void executeUpdateStudySubjectRegistryStatusHistoryTest() throws SQLException, Exception {
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
		System.out.println();
		StudySubject createdStudySubject = service.updateStudySubjectRegistryStatusHistory(request).getStudySubject();
		assertNotNull(createdStudySubject);

		StudySubject expected = createExpectedStudySubject();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus2());
		assertTrue(BeanUtils.deepCompare(expected, createdStudySubject));

	}
	
	protected void executeUpdatetudySubjectTest() throws SQLException, Exception {
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
		System.out.println();
		StudySubject createdStudySubject = service.updateStudySubject(request).getStudySubject();
		assertNotNull(createdStudySubject);
		StudySubject expected = createStudySubjectJAXBObjectModified();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus2());
		assertTrue(BeanUtils.deepCompare(expected, createdStudySubject));

	}
	
	protected void executeQuerySubjectRegistryTest() throws SQLException, Exception {
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
		System.out.println();
		DSETStudySubject studySubjects = service.querySubjectRegistry(request).getStudySubjects();
		assertNotNull(studySubjects);
		assertEquals(1, studySubjects.getItem().size());
		StudySubject expected = createStudySubjectJAXBObjectModified();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus2());
		assertTrue(BeanUtils.deepCompare(expected, studySubjects.getItem().get(0)));

	}
	
	protected void executeUpdateStudySubjectDemographyTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final UpdateStudySubjectRegistryDemographyRequest request = new UpdateStudySubjectRegistryDemographyRequest();
		request.setStudySubjectIdentifier(createSubjectIdModified());
		
		request.setPerson(createPersonModified());
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		StudySubject createdStudySubject = service.updateStudySubjectDemography(request).getStudySubject();
		assertNotNull(createdStudySubject);
		StudySubject expected = createStudySubjectJAXBObjectModified();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus2());
		expected.setEntity(createPersonModified());
		assertTrue(BeanUtils.deepCompare(expected, createdStudySubject));

	}
	
	protected void executeRetrieveStudySubjectDemographyHistoryTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final RetrieveStudySubjectDemographyHistoryRequest request = new RetrieveStudySubjectDemographyHistoryRequest();
		request.setPatientIdentifier(createBioEntityId());
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		DSETPerson subjectDemographies = service.retrieveStudySubjectDemographyHistory(request).getPatients();
		assertNotNull(subjectDemographies);
		assertEquals(1, subjectDemographies.getItem().size());
		Person expected = createPersonModified();
		assertTrue(BeanUtils.deepCompare(expected, subjectDemographies.getItem().get(0)));

	}
	
	protected void executeQueryStudySubjectRegistryStatusHistoryTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final QueryStudySubjectRegistryStatusHistoryRequest request = new QueryStudySubjectRegistryStatusHistoryRequest();
		request.setStudySubjectIdentifier(createSubjectIdModified());
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		DSETPerformedStudySubjectMilestone statusHistory = service.queryStudySubjectRegistryStatusHistory(request).getStudySubjectRegistryStatusHistory();
		assertNotNull(statusHistory);
		assertEquals(1, statusHistory.getItem().size());
		PerformedStudySubjectMilestone expected = createStatus2();
		assertTrue(BeanUtils.deepCompare(expected, statusHistory.getItem().get(0)));

	}
	
	protected void executeQuerySubjectRegistryByRegistryStatusTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final QueryStudySubjectRegistryByStatusRequest request = new QueryStudySubjectRegistryByStatusRequest();
		request.setStudyIdentifier(createDocumentId());
		request.setRegistryStatus(createStatus2());
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		DSETStudySubject studySubjects = service.querySubjectRegistryByRegistryStatus(request).getStudySubjects();
		assertNotNull(studySubjects);
		assertEquals(1, studySubjects.getItem().size());
		StudySubject expected = createStudySubjectJAXBObjectModified();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus2());
		expected.setEntity(createPersonModified());
		assertTrue(BeanUtils.deepCompare(expected, studySubjects.getItem().get(0)));
		
		request.setRegistryStatus(createStatus1());
		studySubjects = service.querySubjectRegistryByRegistryStatus(request).getStudySubjects();
		assertNotNull(studySubjects);
		assertEquals(0, studySubjects.getItem().size());
	}
	
	protected void executeQuerySubjectRegistryByConsentTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final QueryStudySubjectRegistryByConsentRequest request = new QueryStudySubjectRegistryByConsentRequest();
		request.setStudyIdentifier(createDocumentId());
		Consent consent = new Consent();
		consent.setOfficialTitle(iso.ST(TEST_CONSENT_NAME1));
		consent.setText(iso.ED(TEST_CONSENT_DESC1));
		consent.setVersionNumberText(iso.ST(TEST_CONSENT_VERSION1));
		request.setConsent(consent);
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		DSETStudySubject studySubjects = service.querySubjectRegistryByConsent(request).getStudySubjects();
		assertNotNull(studySubjects);
		assertEquals(1, studySubjects.getItem().size());
		StudySubject expected = createStudySubjectJAXBObjectModified();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus2());
		expected.setEntity(createPersonModified());
		assertTrue(BeanUtils.deepCompare(expected, studySubjects.getItem().get(0)));
		
		consent = new Consent();
		consent.setOfficialTitle(iso.ST(TEST_CONSENT_NAME2));
		consent.setText(iso.ED(TEST_CONSENT_DESC2));
		consent.setVersionNumberText(iso.ST(TEST_CONSENT_VERSION2));
		request.setConsent(consent);
		studySubjects = service.querySubjectRegistryByConsent(request).getStudySubjects();
		assertNotNull(studySubjects);
		assertEquals(1, studySubjects.getItem().size());
		expected = createStudySubjectJAXBObjectModified();
		expected.getStudySubjectProtocolVersion().getStudySubjectConsentVersion().addAll(getSubjectConsents());
		expected.getStudySubjectStatus().add(createStatus2());
		expected.setEntity(createPersonModified());
		assertTrue(BeanUtils.deepCompare(expected, studySubjects.getItem().get(0)));
		
		consent = new Consent();
		consent.setOfficialTitle(iso.ST(TEST_CONSENT_NAME2));
		consent.setVersionNumberText(iso.ST("Wrong Version"));
		request.setConsent(consent);
		studySubjects = service.querySubjectRegistryByConsent(request).getStudySubjects();
		assertNotNull(studySubjects);
		assertEquals(0, studySubjects.getItem().size());
	}
	
	protected void executeQueryConsentsByStudySubjectTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final QueryConsentsByStudySubjectRequest request = new QueryConsentsByStudySubjectRequest();
		request.setStudySubjectIdentifier(createSubjectIdModified());
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		DSETStudySubjectConsentVersion subjectConsents = service.queryConsentsByStudySubject(request).getStudySubjectConsents();
		assertNotNull(subjectConsents);
		assertEquals(2, subjectConsents.getItem().size());
		assertTrue(BeanUtils.deepCompare(getSubjectConsents(), subjectConsents.getItem()));

		Consent consent = new Consent();
		consent.setOfficialTitle(iso.ST(TEST_CONSENT_NAME1));
		consent.setText(iso.ED(TEST_CONSENT_DESC1));
		consent.setVersionNumberText(iso.ST(TEST_CONSENT_VERSION1));
		request.setConsent(consent);
		subjectConsents = service.queryConsentsByStudySubject(request).getStudySubjectConsents();
		assertNotNull(subjectConsents);
		assertEquals(1, subjectConsents.getItem().size());
		assertTrue(BeanUtils.deepCompare(getSubjectConsents().get(0), subjectConsents.getItem().get(0)));
		
		consent = new Consent();
		consent.setOfficialTitle(iso.ST(TEST_CONSENT_NAME2));
		consent.setText(iso.ED(TEST_CONSENT_DESC2));
		consent.setVersionNumberText(iso.ST(TEST_CONSENT_VERSION2));
		request.setConsent(consent);
		subjectConsents = service.queryConsentsByStudySubject(request).getStudySubjectConsents();
		assertNotNull(subjectConsents);
		assertEquals(1, subjectConsents.getItem().size());
		assertTrue(BeanUtils.deepCompare(getSubjectConsents().get(1), subjectConsents.getItem().get(0)));
		
		consent = new Consent();
		consent.setOfficialTitle(iso.ST(TEST_CONSENT_NAME2));
		consent.setVersionNumberText(iso.ST("Wrong Version"));
		request.setConsent(consent);
		subjectConsents = service.queryConsentsByStudySubject(request).getStudySubjectConsents();
		assertNotNull(subjectConsents);
		assertEquals(0, subjectConsents.getItem().size());
	}
	
	protected void executeImportSubjectRegistryTest() throws SQLException, Exception {
		SubjectRegistry service = getService();

		// successful creation
		final ImportStudySubjectRegistryRequest request = new ImportStudySubjectRegistryRequest();
		request.setStudySubjects(new DSETStudySubject());
		request.getStudySubjects().getItem().add(createStudySubjectForImport());
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistry");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		DSETStudySubject createdStudySubjects = service.importSubjectRegistry(request).getStudySubjects();
		assertNotNull(createdStudySubjects);
		assertEquals(1, createdStudySubjects.getItem().size());

		assertTrue(BeanUtils.deepCompare(createStudySubjectForImport(), createdStudySubjects.getItem().get(0)));

	}
	
	protected SubjectRegistry getService() {
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
	
	public edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject createStudySubjectForImport() {
		edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject studySubject = new edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject();
		studySubject.setPaymentMethodCode(iso.CD(TEST_PAYMENT_METHOD));
		studySubject.setStatusCode(iso.CD(TEST_DATA_ENTRY_STATUS));
		
		studySubject.getSubjectIdentifier().add(createSubjectIdForImport());
		studySubject.setStudySubjectProtocolVersion(createStudySubjectProtocolVersion());
		studySubject.setEntity(createPersonForImport());
		studySubject.getStudySubjectStatus().add(createStatus1());
		studySubject.getStudySubjectStatus().add(createStatus2());
		
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
	public static BiologicEntityIdentifier createBioEntityIdForImport() {
		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setAssigningOrganization(createOrganization());
		bioId.setIdentifier(iso.II(TEST_BIO_ID_IMPORT));
		bioId.setTypeCode(iso.CD(ORG_ID_TYPE_MRN));
		bioId.setEffectiveDateRange(iso.IVLTSDateTime(NullFlavor.NI));
		bioId.setPrimaryIndicator(iso.BL(TEST_BIO_ID_PRIMARYINDICATOR));
		return bioId;
	}
	
	/**
	 * @return
	 */
	public static BiologicEntityIdentifier createBioEntityIdModified() {
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
	public static SubjectIdentifier createSubjectIdForImport() {
		SubjectIdentifier subId = new SubjectIdentifier();
		subId.setAssigningOrganization(createOrganization());
		subId.setIdentifier(iso.II(TEST_STUDYSUBJECT_ID_IMPORT));
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
		studySubjectConsentVersion.setIdentifier(iso.II(TEST_CONSENTING_DOCID1));
		studySubjectConsentVersion.setInformedConsentDate(iso.TSDateTime(TEST_CONSENT_SIGNED_DATE1));
		studySubjectConsentVersion.setConsentDeclinedDate(iso.TSDateTime(TEST_CONSENT_DECLINED_DATE1));
		studySubjectConsentVersion.setConsentingMethod(iso.CD(TEST_CONSENTING_METHOD1));
		studySubjectConsentVersion.setConsentPresenter(iso.ST(TEST_CONSENT_PRESENTER1));
		studySubjectConsentVersion.setConsent(new DocumentVersion());
		studySubjectConsentVersion.getConsent().setOfficialTitle(iso.ST(TEST_CONSENT_NAME1));
		studySubjectConsentVersion.getConsent().setText(iso.ED(TEST_CONSENT_DESC1));
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
		studySubjectConsentVersion.setIdentifier(iso.II(TEST_CONSENTING_DOCID2));
		studySubjectConsentVersion.setInformedConsentDate(iso.TSDateTime(TEST_CONSENT_SIGNED_DATE2));
		studySubjectConsentVersion.setConsentDeclinedDate(iso.TSDateTime(TEST_CONSENT_DECLINED_DATE2));
		studySubjectConsentVersion.setConsentingMethod(iso.CD(TEST_CONSENTING_METHOD2));
		studySubjectConsentVersion.setConsentPresenter(iso.ST(TEST_CONSENT_PRESENTER2));
		studySubjectConsentVersion.setConsent(new DocumentVersion());
		studySubjectConsentVersion.getConsent().setOfficialTitle(iso.ST(TEST_CONSENT_NAME2));
		studySubjectConsentVersion.getConsent().setText(iso.ED(TEST_CONSENT_DESC2));
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
		status.setComment(iso.ST(TEST_REGISTRYSTATUS_COMMENT1));
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
	
	public edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject createExpectedStudySubject(){
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
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudySite(new edu.duke.cabig.c3pr.webservice.common.StudySite());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().setOrganization(new Organization());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier().add(createOrgId());
		
		return studySubject;
	}
	
	public edu.duke.cabig.c3pr.webservice.subjectregistry.StudySubject createStudySubjectJAXBObjectModified(){
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
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudySite(new edu.duke.cabig.c3pr.webservice.common.StudySite());
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
				EntityNamePartType.FAM),iso.ENXP(TEST_NAME_PREFIX, EntityNamePartType.PFX),
				iso.ENXP(TEST_NAME_SUFFIX, EntityNamePartType.SFX))));
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
	
	/**
	 * @return
	 */
	public static Person createPersonModified() {
		Person person = new Person();
		person.getBiologicEntityIdentifier().add(createBioEntityIdModified());
		person.setAdministrativeGenderCode(iso.CD(GENDER_MALE_MODIFIED));
		person.setBirthDate(iso.TSDateTime(TEST_BIRTH_DATE_ISO_MODIFIED));
		person.setEthnicGroupCode(iso.DSETCD(iso.CD(ETHNIC_CODE_MODIFIED)));
		person.setMaritalStatusCode(iso.CD(MARITAL_STATUS_SINGLE_MODIFIED));
		person.setName(iso.DSETENPN(iso.ENPN(iso.ENXP(TEST_FIRST_NAME_MODIFIED,
				EntityNamePartType.GIV), iso.ENXP(TEST_MID_NAME_MODIFIED,
				EntityNamePartType.GIV), iso.ENXP(TEST_LAST_NAME_MODIFIED,
				EntityNamePartType.FAM), iso.ENXP(TEST_NAME_PREFIX_MODIFIED,
				EntityNamePartType.PFX), iso.ENXP(TEST_NAME_SUFFIX_MODIFIED,
				EntityNamePartType.SFX))));
		person.setPostalAddress(iso.DSETAD(iso.AD(iso.ADXP(TEST_STREET_ADDRESS_MODIFIED,
				AddressPartType.SAL), iso.ADXP(TEST_CITY_NAME_MODIFIED,
				AddressPartType.CTY), iso.ADXP(TEST_STATE_CODE_MODIFIED,
				AddressPartType.STA), iso.ADXP(TEST_ZIP_CODE_MODIFIED,
				AddressPartType.ZIP), iso.ADXP(TEST_COUNTRY_MODIFIED,
				AddressPartType.CNT))));
		person.setRaceCode(iso.DSETCD(iso.CD(RACE_WHITE_MODIFIED), iso.CD(RACE_ASIAN_MODIFIED)));
		person.setTelecomAddress(iso.BAGTEL(iso.TEL(TEST_EMAIL_ADDR_ISO_MODIFIED),
				iso.TEL(TEST_PHONE_ISO_MODIFIED), iso.TEL(TEST_FAX_ISO_MODIFIED)));
		return person;
	}
	
	/**
	 * @return
	 */
	public static Person createPersonForImport() {
		Person person = new Person();
		person.getBiologicEntityIdentifier().add(createBioEntityIdForImport());
		person.setAdministrativeGenderCode(iso.CD(GENDER_MALE));
		person.setBirthDate(iso.TSDateTime(TEST_BIRTH_DATE_ISO));
		person.setEthnicGroupCode(iso.DSETCD(iso.CD(ETHNIC_CODE_NOT_REPORTED)));
		person.setMaritalStatusCode(iso.CD(MARITAL_STATUS_SINGLE));
		person.setName(iso.DSETENPN(iso.ENPN(iso.ENXP(TEST_FIRST_NAME,
				EntityNamePartType.GIV), iso.ENXP(TEST_MID_NAME,
				EntityNamePartType.GIV), iso.ENXP(TEST_LAST_NAME,
				EntityNamePartType.FAM),iso.ENXP(TEST_NAME_PREFIX, EntityNamePartType.PFX),
				iso.ENXP(TEST_NAME_SUFFIX, EntityNamePartType.SFX))));
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
	
	private StudySubjectProtocolVersionRelationship createStudySubjectProtocolVersion(){
		StudySubjectProtocolVersionRelationship studySubjectProtocolVersion = new StudySubjectProtocolVersionRelationship();
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
		studySubjectProtocolVersion.getStudySiteProtocolVersion().setStudySite(new edu.duke.cabig.c3pr.webservice.common.StudySite());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().setOrganization(new Organization());
		studySubjectProtocolVersion.getStudySiteProtocolVersion().getStudySite().getOrganization().getOrganizationIdentifier().add(createOrgId());
		
		studySubjectProtocolVersion.getStudySubjectConsentVersion().addAll(getSubjectConsents());
		return studySubjectProtocolVersion;
	}

}

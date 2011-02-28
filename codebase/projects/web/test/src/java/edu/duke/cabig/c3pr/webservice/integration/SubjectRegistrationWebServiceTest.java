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
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.DefinedActivity;
import edu.duke.cabig.c3pr.webservice.common.Document;
import edu.duke.cabig.c3pr.webservice.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.PerformedActivity;
import edu.duke.cabig.c3pr.webservice.common.PerformedStudySubjectMilestone;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.common.StudySiteProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.common.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.webservice.common.SubjectIdentifier;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETAD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.ENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DSETStudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedEligibilityCriterion;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedStratificationCriterion;
import edu.duke.cabig.c3pr.webservice.subjectregistration.DefinedStratificationCriterionPermissibleResult;
import edu.duke.cabig.c3pr.webservice.subjectregistration.Epoch;
import edu.duke.cabig.c3pr.webservice.subjectregistration.HealthcareProvider;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedDiagnosis;
import edu.duke.cabig.c3pr.webservice.subjectregistration.PerformedObservationResult;
import edu.duke.cabig.c3pr.webservice.subjectregistration.QuerySubjectRegistrationRequest;
import edu.duke.cabig.c3pr.webservice.subjectregistration.ScheduledEpoch;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyCondition;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudyInvestigator;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubject;
import edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubjectProtocolVersionRelationship;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistration;
import edu.duke.cabig.c3pr.webservice.subjectregistration.SubjectRegistrationService;

/**
 * This test will run C3PR in embedded Tomcat and test Subject Registry web
 * service against it. <br>
 * 
 * @author Kruttik Aggarwal
 * @version 1.0
 */
public class SubjectRegistrationWebServiceTest extends C3PREmbeddedTomcatTestBase {

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
	protected static final String TEST_MID_NAME = "Z";
	protected static final String TEST_FIRST_NAME = "Rudolph";
	protected static final String MARITAL_STATUS_SINGLE = "Single";
	protected static final String ETHNIC_CODE_NOT_REPORTED = "Not Reported";
	protected static final String TEST_BIRTH_DATE_ISO = "19800101000000";
	protected static final String GENDER_MALE = "Male";
	protected static final String ORG_ID_TYPE_MRN = "MRN";
	protected static final String ORG_ID_TYPE_STUDY = "COORDINATING_CENTER_IDENTIFIER";
	protected static final String ORG_ID_TYPE_STUDYSUBJECT = "COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER";
	protected static final String TEST_BIO_ID = "test_subject_id";
	protected static final String TEST_BIO_ID_IMPORT = "test_subject_id_import";
	protected static final Boolean TEST_BIO_ID_PRIMARYINDICATOR=true;
	protected static final String TEST_STUDYSUBJECT_ID = "002";
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
	protected static final String TEST_PAYMENT_METHOD = "private insurance";
	protected static final String TEST_WORKFLOW_ENTRY_STATUS = "On-Study";
	protected static final String TEST_SHORTTITLE = "short_title_text";
	protected static final String TEST_LONGTITLE = "long_title";
	protected static final String TEST_DESC = "description";
	protected static final String TEST_PREDICATE = "=";
	protected static final String TEST_OBJ_NAME = "edu.duke.cabig.c3pr.domain.Identifier";
	protected static final String TEST_OBJ_CTX_NAME = "StudySubject";
	protected static final String TEST_ATTRIBUTE_NAME = "value";
	
	
	private static final QName SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/SubjectRegistrationService",
			"SubjectRegistrationService");	
	private static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/SubjectRegistration";

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
					"https://localhost:8443/c3pr/services/services/SubjectRegistration");
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
	public void testSubjectRegistrationUtility() throws InterruptedException, IOException, Exception {

		try {
			executeQuerySubjectRegistrationTest();
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			fail(ExceptionUtils.getFullStackTrace(e));
		}
	}


	protected void executeQuerySubjectRegistrationTest() throws SQLException, Exception {
		SubjectRegistration service = getService();

		// successful creation
		final QuerySubjectRegistrationRequest request = new QuerySubjectRegistrationRequest();
		DSETAdvanceSearchCriterionParameter dsetAdvanceSearchCriterionParameter = new DSETAdvanceSearchCriterionParameter();
		dsetAdvanceSearchCriterionParameter.getItem().add(createAdvaceSearchParam());
		request.setParameters(dsetAdvanceSearchCriterionParameter);
		
		JAXBContext context = JAXBContext.newInstance("edu.duke.cabig.c3pr.webservice.subjectregistration");
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal( request , System.out );
		System.out.flush();
		System.out.println();
		DSETStudySubject studySubjects = service.querySubjectRegistration(request).getStudySubjects();
		assertNotNull(studySubjects);
		assertEquals(1, studySubjects.getItem().size());
		StudySubject expected = createExpectedStudySubject();
		assertTrue(BeanUtils.deepCompare(expected, studySubjects.getItem().get(0)));
	}
	
	protected SubjectRegistration getService() {
		SubjectRegistrationService service = new SubjectRegistrationService(wsdlLocation,
				SERVICE_NAME);
		SOAPUtils.installSecurityHandler(service);
		SubjectRegistration client = service.getSubjectRegistration();
		return client;
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
		param.setValues(iso.DSETST(Arrays.asList(new ST[] {iso.ST(TEST_STUDYSUBJECT_ID) })));
		return param;
	}
	
	
	public edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubject createExpectedStudySubject(){
		edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubject studySubject = new edu.duke.cabig.c3pr.webservice.subjectregistration.StudySubject();
		studySubject.setEntity(createPerson());
		studySubject.setPaymentMethodCode(iso.CD(TEST_PAYMENT_METHOD));
		studySubject.setStatusCode(iso.CD(TEST_WORKFLOW_ENTRY_STATUS));
		
		studySubject.getSubjectIdentifier().add(createSubjectDummySystemId());
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
		
		//setup consents
		studySubjectProtocolVersion.getStudySubjectConsentVersion().addAll(getSubjectConsents());
		
		//setup scheduledepochs
		studySubjectProtocolVersion.getScheduledEpoch().addAll(getScheduledEpochs());
		
		//setup disease history
		studySubject.setDiseaseHistory(getDiseaseHistory());
		
		//setup treating physician
		studySubject.setTreatingPhysician(getStudyInvestigator());
		
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
	
	private List<ScheduledEpoch> getScheduledEpochs(){
		List<ScheduledEpoch> schList = new ArrayList<ScheduledEpoch>();
		ScheduledEpoch covertedScheduledEpoch = new ScheduledEpoch();
		covertedScheduledEpoch.setStartDate(iso.TSDateTime(TEST_SCH_START_DATE));
		covertedScheduledEpoch.setOffEpochDate(iso.TSDateTime(TEST_SCH_OFF_DATE));
		covertedScheduledEpoch.setStatus(iso.CD(TEST_SCH_STATUS));
		covertedScheduledEpoch.setStratumGroupNumber(iso.INTPositive(TEST_SCH_STARTUM_NO));
		Epoch convertedEpoch = new Epoch();
		convertedEpoch.setDescription(iso.ST(TEST_EPH_DESC));
		convertedEpoch.setName(iso.ST(TEST_EPH_NAME));
		convertedEpoch.setSequenceNumber(iso.INTPositive(TEST_EPH_SEQ_NO));
		convertedEpoch.setTypeCode(iso.CD(TEST_EPH_TYPE));
		covertedScheduledEpoch.setEpoch(convertedEpoch);
		PerformedActivity convertedArm = new PerformedActivity();
		DefinedActivity arm = new DefinedActivity();
		arm.setNameCode(iso.CD(TEST_ARM_NAME));
		convertedArm.setDefinedActivity(arm);
		covertedScheduledEpoch.setOffEpochReason(new DSETCD());
		covertedScheduledEpoch.getOffEpochReason().getItem().add(iso.CD(TEST_SCH_OFF_REASON1));
		covertedScheduledEpoch.getOffEpochReason().getItem().add(iso.CD(TEST_SCH_OFF_REASON2));
		covertedScheduledEpoch.setScheduledArm(convertedArm);
		
		PerformedObservationResult convertedAnswer = new PerformedObservationResult();
		DefinedEligibilityCriterion eligibility = new DefinedEligibilityCriterion();
		eligibility.setNameCode(iso.CD(TEST_ELG_QS));
		eligibility.setDescription(iso.ST(TEST_ELG_QS));
		eligibility.setRequiredResponse(iso.BL(NullFlavor.NI));
		convertedAnswer.setEligibilityCriterion(eligibility);
		convertedAnswer.setResult(iso.ST(TEST_ELG_ANS));
		covertedScheduledEpoch.getSubjectEligibilityAnswer().add(convertedAnswer);
		
		convertedAnswer = new PerformedObservationResult();
		DefinedStratificationCriterion startification = new DefinedStratificationCriterion();
		startification.setNameCode(iso.CD(TEST_STRAT_QS));
		startification.setDescription(iso.ST(TEST_STRAT_QS));
		convertedAnswer.setStartificationCriterion(startification);
		DefinedStratificationCriterionPermissibleResult result = new DefinedStratificationCriterionPermissibleResult();
		result.setResult(iso.ST(TEST_STRAT_ANS));
		convertedAnswer.setStartificationCriterionPermissibleResult(result);
		covertedScheduledEpoch.getSubjectStartificationAnswer().add(convertedAnswer);
		schList.add(covertedScheduledEpoch);
		return schList;
	}

	protected static final String TEST_SCH_START_DATE="20100101000000";
	protected static final String TEST_SCH_OFF_DATE="20100101000000";
	protected static final String TEST_SCH_STATUS="On-Epoch";
	protected static final Integer TEST_SCH_STARTUM_NO=1;
	protected static final String TEST_SCH_OFF_REASON1="REASON1";
	protected static final String TEST_SCH_OFF_REASON2="REASON2";
	protected static final String TEST_EPH_DESC="treatement desc";
	protected static final String TEST_EPH_NAME="screening";
	protected static final String TEST_EPH_TYPE="Screening";
	protected static final Integer TEST_EPH_SEQ_NO=1;
	protected static final String TEST_ARM_NAME="Arm A";
	protected static final String TEST_ELG_QS="Question";
	protected static final String TEST_ELG_ANS="Yes";
	protected static final String TEST_STRAT_QS="Strat Question";
	protected static final String TEST_STRAT_ANS="Strat Answer";
	protected static final String TEST_DISEASE="Disease";
	protected static final String TEST_ANATIMIC_SITE="Disease Site";
	protected static final String TEST_INV_ID="INV_ID";
	
	private PerformedDiagnosis getDiseaseHistory(){
		PerformedDiagnosis convertedDiseaseHistory = new PerformedDiagnosis();
		StudyCondition condition = new StudyCondition();
		condition.setConditionCode(iso.CD(TEST_DISEASE));
		convertedDiseaseHistory.setDisease(condition);
		convertedDiseaseHistory.setTargetAnatomicSiteCode(iso.CD(TEST_ANATIMIC_SITE));
		return convertedDiseaseHistory;
	}
	
	private StudyInvestigator getStudyInvestigator(){
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		Person investigator = new Person();
		studyInvestigator.setHealthcareProvider(new HealthcareProvider());
		studyInvestigator.getHealthcareProvider().setPerson(investigator);
		DSETENPN dsetenpn = new DSETENPN();
		ENPN enpn = new ENPN();
		enpn.getPart().add(iso.ENXP(TEST_FIRST_NAME, EntityNamePartType.GIV));
		enpn.getPart().add(iso.ENXP(TEST_LAST_NAME, EntityNamePartType.FAM));
		dsetenpn.getItem().add(enpn);
		investigator.setName(dsetenpn);
		investigator.setTelecomAddress(iso.BAGTEL(iso.TEL(TEST_EMAIL_ADDR_ISO)));
		investigator.setAdministrativeGenderCode(iso.CD(NullFlavor.NI));
		investigator.setBirthDate(iso.TSDateTime(NullFlavor.NI));
		investigator.setEthnicGroupCode(new DSETCD());
		investigator.setMaritalStatusCode(iso.CD(NullFlavor.NI));
		investigator.setPostalAddress(new DSETAD());
		investigator.setRaceCode(new DSETCD());
		studyInvestigator.getHealthcareProvider().setIdentifier(iso.II(TEST_INV_ID));
		return studyInvestigator;
	}
	
	/**
	 * @return
	 */
	public static SubjectIdentifier createSubjectDummySystemId() {
		SubjectIdentifier subId = new SubjectIdentifier();
		subId.setIdentifier(iso.II("dummy"));
		subId.setTypeCode(iso.CD("local"));
		subId.getTypeCode().setCodeSystemName("Dummy");
		subId.setPrimaryIndicator(iso.BL(false));
		return subId;
	}
}

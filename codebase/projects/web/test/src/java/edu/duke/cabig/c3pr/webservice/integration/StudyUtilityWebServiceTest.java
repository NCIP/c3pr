/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.Consent;
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
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
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETST;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.UpdateMode;
import edu.duke.cabig.c3pr.webservice.studyutility.CreateStudyAbstractRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryRegistryStatusRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyAbstractRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyConsentRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.QueryStudyRegistryStatusRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyUtility;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyUtilityFaultMessage;
import edu.duke.cabig.c3pr.webservice.studyutility.StudyUtilityService;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyAbstractRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentQuestionRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyConsentRequest;
import edu.duke.cabig.c3pr.webservice.studyutility.UpdateStudyStatusRequest;

/**
 * This test will run C3PR in embedded Tomcat and test Study Utility web service
 * against it. <br>
 * <br>
 * 
 * @author dkrylov
 * @version 1.0
 */
public class StudyUtilityWebServiceTest extends C3PREmbeddedTomcatTestBase {

	private static final String STATUS_INACTIVE = "INACTIVE";
	private static final String STATUS_ACTIVE = "ACTIVE";
	private static final int STATUS_INACTIVE_ID = 110153;
	private static final int STATUS_ACTIVE_ID = 110152;
	private static final String REGISTRY_STATUS = "REGISTRY_STATUS";

	private static final String SQL_CONSENT_QUESTIONS = "SELECT * FROM consent_questions WHERE EXISTS (SELECT id from consents where consents.id=consent_questions.con_id AND EXISTS (SELECT Id FROM study_versions where study_versions.id=consents.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')))) ORDER BY consent_questions.id";
	private static final String SQL_CONSENTS = "SELECT * FROM consents WHERE EXISTS (SELECT Id FROM study_versions where study_versions.id=consents.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}'))) order by consents.id";
	private static final String SQL_PERM_REG_STATS = "SELECT * FROM permissible_reg_stats WHERE EXISTS (SELECT Id FROM studies where studies.id=permissible_reg_stats.study_id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')) ORDER BY id";
	private static final String SQL_STUDY_ORGS = "SELECT * FROM study_organizations WHERE EXISTS (SELECT Id FROM studies where studies.id=study_organizations.study_id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')) ORDER BY id";
	private static final String SQL_STUDY_VERSIONS = "SELECT * FROM study_versions WHERE EXISTS (SELECT Id FROM studies where studies.id=study_versions.study_id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}'))";
	private static final String SQL_STUDIES = "SELECT * FROM studies WHERE EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')";
	private static final String SQL_IDENTIFIERS = "SELECT type, dtype FROM identifiers WHERE stu_id=(Select stu_id from identifiers where value='${STUDY_ID}' and primary_indicator='1') ORDER BY id";

	private static final String DBUNIT_DATASET_PREFIX = "/edu/duke/cabig/c3pr/webservice/integration/testdata/StudyUtilityWebServiceTest_";

	private static final QName SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/StudyUtilityService",
			"StudyUtilityService");
	private static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/StudyUtility";
	private static final String UPDATE_DISCRIMINATOR = " UPDATED";

	private final String STUDY_ID = RandomStringUtils.randomAlphanumeric(16);
	
	private final String SITE_STUDY_ID = RandomStringUtils.randomAlphanumeric(16);

	private static final ISO21090Helper iso = new ISO21090Helper();

	private URL endpointURL;

	private URL wsdlLocation;

	/**
	 * Set this JVM property to true if this test should not bring up an
	 * instance of embedded Tomcat and use one already running locally at
	 * <b>https://localhost:8443/c3pr.
	 */
	private boolean noEmbeddedTomcat = Boolean.valueOf(System.getProperty(
			"noEmbeddedTomcat", "false"));

	@Override
	protected void setUp() throws Exception {
		initDataSourceFile();
		if (noEmbeddedTomcat) {
			endpointURL = new URL(
					"https://localhost:8443/c3pr/services/services/StudyUtility");
		} else {
			cleanupDatabaseData();
			super.setUp();
			endpointURL = new URL("https://"
					+ InetAddress.getLocalHost().getHostName() + ":" + sslPort
					+ C3PR_CONTEXT + WS_ENDPOINT_SERVLET_PATH);
		}
		finishDatabaseSetup();
		wsdlLocation = new URL(endpointURL.toString() + "?wsdl");

		logger.info("endpointURL: " + endpointURL);
		logger.info("wsdlLocation: " + wsdlLocation);

	}

	/**
	 * Need to do some DELETEs which could not be done via DbUnit.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	private void cleanupDatabaseData() throws SQLException, Exception {
		try {
			Connection conn = getConnection().getConnection();
			Statement st = conn.createStatement();
			st.execute("DELETE FROM identifiers where stu_id is not null");
			st.execute("DELETE FROM reasons where per_reg_st_id is not null");
			st.close();
		} catch (Exception e) {
			logger.severe("cleanupDatabaseData() failed.");
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}

	}

	/**
	 * Inserting data into registry_statuses via DbUnit was problematic; hence
	 * this method with raw SQL.
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	private void finishDatabaseSetup() throws SQLException, Exception {
		Connection conn = getConnection().getConnection();
		Statement st = conn.createStatement();
		boolean containsActive = st.executeQuery(
				"SELECT id FROM registry_statuses WHERE code='" + STATUS_ACTIVE
						+ "'").next();
		boolean containsInactive = st.executeQuery(
				"SELECT id FROM registry_statuses WHERE code='"
						+ STATUS_INACTIVE + "'").next();
		if (!containsActive){
			st.execute("INSERT INTO registry_statuses(id, version, grid_id, code, description, retired_indicator) VALUES ("
					+ STATUS_ACTIVE_ID
					+ ",0,'"
					+ System.currentTimeMillis()
					+ "','"
					+ STATUS_ACTIVE
					+ "','"
					+ STATUS_ACTIVE
					+ "','false')");
			st.execute("insert into reasons(id,version,code,description,dtype,primary_indicator,registry_st_id,retired_indicator) VALUES ("
						+ STATUS_ACTIVE_ID
						+ ",0,'"
						+ TEST_PRIMARY_REASON_CODE
						+ "','"
						+ TEST_PRIMARY_REASON_DESCR
						+ "','"
						+ REGISTRY_STATUS
						+ "','"
						+ 1
						+ "','"
						+STATUS_ACTIVE_ID
						+ "','false')");
		}
		if (!containsInactive){
			st.execute("INSERT INTO registry_statuses(id, version, grid_id, code, description, retired_indicator) VALUES ("
					+ STATUS_INACTIVE_ID
					+ ",0,'"
					+ System.currentTimeMillis()
					+ "','"
					+ STATUS_INACTIVE
					+ "','"
					+ STATUS_INACTIVE
					+ "','false')");
			st.execute("insert into reasons(id,version,code,description,dtype,primary_indicator,registry_st_id,retired_indicator) VALUES ("
					+ STATUS_INACTIVE_ID
					+ ",0,'"
					+ TEST_PRIMARY_REASON_CODE
					+ "','"
					+ TEST_PRIMARY_REASON_DESCR
					+ "','"
					+ REGISTRY_STATUS
					+ "','"
					+ 1
					+ "','"
					+STATUS_INACTIVE_ID
					+ "','false')");
		}
		st.close();
	}

	@Override
	protected void tearDown() throws Exception {
		if (!noEmbeddedTomcat) {
			cleanupDatabaseData();
			super.tearDown();
		}
	}

	/**
	 * @throws InterruptedException
	 * @throws IOException
	 * 
	 */
	public void testStudyUtility() throws InterruptedException, IOException {

		try {
			executeCreateStudyTest();
			executeQueryStudyTest();
			executeQueryConsentTest();
			executeUpdateStudyTest();
			executeUpdateStudyStatusTest();
			executeUpdateConsentTest();
			executeQueryRegistryStatusTest();
			executeQueryStudyRegistryStatusTest();
			executeUpdateConsentQuestionTest();
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			fail(ExceptionUtils.getFullStackTrace(e));
		}

	}

	private void executeQueryStudyRegistryStatusTest()
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		StudyUtility service = getService();

		// return study status
		QueryStudyRegistryStatusRequest request = new QueryStudyRegistryStatusRequest();
		final DocumentIdentifier studyId = createStudyPrimaryIdentifier();
		request.setStudyIdentifier(studyId);
		List<PermissibleStudySubjectRegistryStatus> list = service
				.queryStudyRegistryStatus(request).getStatuses().getItem();
		assertEquals(1, list.size());
		assertEquals(STATUS_ACTIVE, list.get(0).getRegistryStatus().getCode()
				.getCode());

		// study does not exist.
		studyId.getIdentifier().setExtension(
				RandomStringUtils.randomAlphanumeric(32));
		try {
			service.queryStudyRegistryStatus(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Unexistent study creation passed.");
		}

	}

	private void executeQueryRegistryStatusTest()
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		StudyUtility service = getService();

		// all statuses
		QueryRegistryStatusRequest request = new QueryRegistryStatusRequest();
		List<RegistryStatus> list = service.queryRegistryStatus(request)
				.getRegistryStatuses().getItem();
		assertEquals(2, list.size());
		assertEquals(STATUS_ACTIVE, list.get(0).getCode().getCode());
		assertEquals(STATUS_INACTIVE, list.get(1).getCode().getCode());

		// no statuses
		request.setStatusCode(iso.CD("DOES NOT EXIST"));
		list = service.queryRegistryStatus(request).getRegistryStatuses()
				.getItem();
		assertEquals(0, list.size());

		// one status
		request.setStatusCode(iso.CD(STATUS_ACTIVE));
		list = service.queryRegistryStatus(request).getRegistryStatuses()
				.getItem();
		assertEquals(1, list.size());
		assertEquals(STATUS_ACTIVE, list.get(0).getCode().getCode());
	}

	private void executeUpdateStudyStatusTest() throws DataSetException,
			IOException, SQLException, DatabaseUnitException, Exception {
		StudyUtility service = getService();

		// successful add
		UpdateStudyStatusRequest request = new UpdateStudyStatusRequest();
		final DocumentIdentifier studyId = createStudyPrimaryIdentifier();
		request.setStudyIdentifier(studyId);
		PermissibleStudySubjectRegistryStatus status = createPermissibleStudySubjectRegistryStatus();
		status.getRegistryStatus().getCode().setCode(STATUS_INACTIVE);
		status.getSecondaryReason().get(0).setCode(iso.CD(TEST_SECONDARY_REASON_CODE+"-to be updated"));
		status.getSecondaryReason().get(0).setDescription(iso.ST(TEST_SECONDARY_REASON_DESCR+"-to be updated"));
		request.setStatus(status);
		request.setUpdateMode(UpdateMode.A);
		PermissibleStudySubjectRegistryStatus updatedStatus = service
				.updateStudyStatus(request).getStatus();
		assertEquals(STATUS_INACTIVE, updatedStatus.getRegistryStatus()
				.getCode().getCode());
		assertEquals(TEST_SECONDARY_REASON_CODE+"-to be updated", updatedStatus.getSecondaryReason().get(0).getCode().getCode());
		assertEquals(TEST_SECONDARY_REASON_DESCR+"-to be updated", updatedStatus.getSecondaryReason().get(0).getDescription().getValue());
		
		// successful update
		status = createPermissibleStudySubjectRegistryStatus();
		status.getRegistryStatus().getCode().setCode(STATUS_INACTIVE);
		request.setStatus(status);
		request.setUpdateMode(UpdateMode.U);
		updatedStatus = service
				.updateStudyStatus(request).getStatus();
		assertEquals(STATUS_INACTIVE, updatedStatus.getRegistryStatus()
				.getCode().getCode());
		assertEquals(TEST_SECONDARY_REASON_CODE, updatedStatus.getSecondaryReason().get(0).getCode().getCode());
		assertEquals(TEST_SECONDARY_REASON_DESCR, updatedStatus.getSecondaryReason().get(0).getDescription().getValue());
		
		// successful delete
		request.setUpdateMode(UpdateMode.D);
		updatedStatus = service
				.updateStudyStatus(request).getStatus();
		assertEquals(STATUS_INACTIVE, updatedStatus.getRegistryStatus()
				.getCode().getCode());
		assertEquals(TEST_SECONDARY_REASON_CODE, updatedStatus.getSecondaryReason().get(0).getCode().getCode());
		assertEquals(TEST_SECONDARY_REASON_DESCR, updatedStatus.getSecondaryReason().get(0).getDescription().getValue());
		
		// invalid status code
		status.getRegistryStatus().getCode().setCode("WRONG");
		try {
			service.updateStudyStatus(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Invalid status code testing passed.");
		}

		// study does not exist.
		studyId.getIdentifier().setExtension(
				RandomStringUtils.randomAlphanumeric(32));
		try {
			service.updateStudyStatus(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Unexistent study creation passed.");
		}

	}

	private void executeUpdateConsentTest() throws DataSetException,
			IOException, SQLException, DatabaseUnitException, Exception {
		StudyUtility service = getService();

		// setting updateMode in request explicitly to Replace:R, it should have same name and versionId as the one to be replaced
		final Consent consent = createConsent(TEST_CONSENT_TITLE,UPDATE_DISCRIMINATOR);
		UpdateStudyConsentRequest request = new UpdateStudyConsentRequest();
		final DocumentIdentifier studyId = createStudyPrimaryIdentifier();
		request.setStudyIdentifier(studyId);
		request.setConsent(consent);
		request.setUpdateMode(UpdateMode.R);
		Consent updatedConsent = service.updateStudyConsent(request)
				.getConsent();
		assertTrue(BeanUtils.deepCompare(consent, updatedConsent));
		// check database data
		verifyData("Consents_ReplaceConsent", "", "consents", SQL_CONSENTS);
		verifyData("ConsentQuestions_ReplaceConsent", "", "consent_questions",
				SQL_CONSENT_QUESTIONS);

		//add consent
		final Consent addConsent = createConsent(TEST_CONSENT_TITLE + UPDATE_DISCRIMINATOR,"");
		request.setUpdateMode(UpdateMode.AU);
		request.setStudyIdentifier(studyId);
		request.setConsent(addConsent);
		updatedConsent = service.updateStudyConsent(request)
				.getConsent();
		addConsent.getVersionNumberText().setUpdateMode(null);
		assertTrue(BeanUtils.deepCompare(addConsent, updatedConsent));
		// check database data
		verifyData("Consents_AddConsent", "", "consents", SQL_CONSENTS);
		verifyData("ConsentQuestions_AddConsent", "", "consent_questions",
				SQL_CONSENT_QUESTIONS);
		
		//update consent
		// Consent name and version id uniquely determine so, consent name cannot change
		final Consent updateConsent = createConsent(TEST_CONSENT_TITLE,UPDATE_DISCRIMINATOR + " 2");
		updateConsent.setOfficialTitle(iso.ST(TEST_CONSENT_TITLE));
		updateConsent.setVersionNumberText(iso.ST(TEST_VERSION_NUMBER));
		request.setUpdateMode(UpdateMode.U);
		request.setStudyIdentifier(studyId);
		request.setConsent(updateConsent);
		updatedConsent = service.updateStudyConsent(request)
				.getConsent();
		assertTrue(BeanUtils.deepCompare(updateConsent, updatedConsent));
		// check database data
		verifyData("Consents_UpdateConsent", "", "consents", SQL_CONSENTS);
		verifyData("ConsentQuestions_UpdateConsent", "", "consent_questions",
				SQL_CONSENT_QUESTIONS);
		
		//update consent
		final Consent retireConsent = createConsent(TEST_CONSENT_TITLE,UPDATE_DISCRIMINATOR + " 2");
		request.setUpdateMode(UpdateMode.D);
		request.setStudyIdentifier(studyId);
		request.setConsent(retireConsent);
		updatedConsent = service.updateStudyConsent(request)
				.getConsent();
	//	assertTrue(BeanUtils.deepCompare(retireConsent, updatedConsent));
		// check database data
		verifyData("Consents_RetireConsent", "", "consents", SQL_CONSENTS);
		
		//test using query
		// all consents of the study
		final QueryStudyConsentRequest request1 = new QueryStudyConsentRequest();
		request1.setStudyIdentifier(studyId);
		List<Consent> list = service.queryStudyConsent(request1).getConsents()
				.getItem();
		assertEquals(2, list.size());
		
		// study does not exist.
		studyId.getIdentifier().setExtension(
				RandomStringUtils.randomAlphanumeric(32));
		try {
			service.updateStudyConsent(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Unexistent study creation passed.");
		}
		
	}
	
	private void executeUpdateConsentQuestionTest() throws DataSetException,
	IOException, SQLException, DatabaseUnitException, Exception {
		StudyUtility service = getService();
		
		final Consent consent = createConsent(TEST_CONSENT_TITLE,"");
		consent.setOfficialTitle(iso.ST(TEST_CONSENT_TITLE + UPDATE_DISCRIMINATOR));
		UpdateStudyConsentQuestionRequest request = new UpdateStudyConsentQuestionRequest();
		final DocumentIdentifier studyId = createStudyPrimaryIdentifier();
		request.setStudyIdentifier(studyId);
		request.setConsentName(consent.getOfficialTitle());
		request.setConsentVersion(consent.getVersionNumberText());
		// replace consent question
		request.setUpdateMode(UpdateMode.R);
		// set deliberately wrong question question code, the service should throw a fault
		request.setConsentQuestion(createConsentQuestion(TEST_WRONG_CONSENT_QUESTION_1));
		Consent updatedConsent = null;
		try {
			updatedConsent = service.updateStudyConsentQuestion(request)
					.getConsent();
			fail();
		} catch (Exception e) {
			logger.info("Replace non-existent consent question test passed.");
		}
		
		
		request.setConsentQuestion(createConsentQuestion(TEST_CONSENT_QUESTION_1));
		// replace the old consent question with another question that has different question text
		request.getConsentQuestion().setText(iso.ED(TEST_REPLACED_CONSNET_QUESTION_1));
		consent.getDocumentVersionRelationship().get(0).getTarget().setText(iso.ED(TEST_REPLACED_CONSNET_QUESTION_1));
		updatedConsent = service.updateStudyConsentQuestion(request)
					.getConsent();
		sortByConsentQuestionByOfficialTitle(consent);
		sortByConsentQuestionByOfficialTitle(updatedConsent);
		assertTrue(BeanUtils.deepCompare(consent, updatedConsent));
		
		// test adding new consent question
		request.setUpdateMode(UpdateMode.A);
		
		DocumentVersionRelationship newConsentQuestionRelationship = createConsentQuestionRelationship(TEST_NEW_CONSENT_QUESTION_1);
		consent.getDocumentVersionRelationship().add(newConsentQuestionRelationship);
		request.setConsentQuestion(createConsentQuestion(TEST_NEW_CONSENT_QUESTION_1));
		updatedConsent = service.updateStudyConsentQuestion(request).getConsent();
		sortByConsentQuestionByOfficialTitle(consent);
		sortByConsentQuestionByOfficialTitle(updatedConsent);
		assertTrue(BeanUtils.deepCompare(consent, updatedConsent));
		
		// test retiring 
		request.setUpdateMode(UpdateMode.D);
		request.setConsentName(consent.getOfficialTitle());
		request.setConsentVersion(consent.getVersionNumberText());
		DocumentVersion retiredConsentQuestion = createConsentQuestion(TEST_NEW_CONSENT_QUESTION_1);
		request.setConsentQuestion(retiredConsentQuestion);
		updatedConsent = service.updateStudyConsentQuestion(request).getConsent();
		sortByConsentQuestionByOfficialTitle(consent);
		sortByConsentQuestionByOfficialTitle(updatedConsent);
		
		// Consent question is gotten back from the service without being filtered but with a null flavor 
		// setting null flavor on retired consent before giving it to deep compare 
		consent.getDocumentVersionRelationship().get(0).getTarget().setVersionNumberText(iso.ST("1"));
		consent.getDocumentVersionRelationship().get(0).getTarget().getVersionNumberText().setNullFlavor(NullFlavor.INV);
		assertTrue(BeanUtils.deepCompare(consent, updatedConsent));
		
		// check database data
		/*verifyData("Consents_ReplaceConsent", "", "consents", SQL_CONSENTS);
		verifyData("ConsentQuestions_ReplaceConsent", "", "consent_questions",
				SQL_CONSENT_QUESTIONS);
		
		//add consent
		final Consent addConsent = createConsent(TEST_CONSENT_TITLE + UPDATE_DISCRIMINATOR,"");
		request.setUpdateMode(UpdateMode.AU);
		request.setStudyIdentifier(studyId);
		request.setConsent(addConsent);
		updatedConsent = service.updateStudyConsent(request)
				.getConsent();
		addConsent.getVersionNumberText().setUpdateMode(null);
		assertTrue(BeanUtils.deepCompare(addConsent, updatedConsent));
		// check database data
		verifyData("Consents_AddConsent", "", "consents", SQL_CONSENTS);
		verifyData("ConsentQuestions_AddConsent", "", "consent_questions",
				SQL_CONSENT_QUESTIONS);
		
		//update consent
		// Consent name and version id uniquely determine so, consent name cannot change
		final Consent updateConsent = createConsent(TEST_CONSENT_TITLE,UPDATE_DISCRIMINATOR + " 2");
		updateConsent.setOfficialTitle(iso.ST(TEST_CONSENT_TITLE));
		updateConsent.setVersionNumberText(iso.ST(TEST_VERSION_NUMBER));
		request.setUpdateMode(UpdateMode.U);
		request.setStudyIdentifier(studyId);
		request.setConsent(updateConsent);
		updatedConsent = service.updateStudyConsent(request)
				.getConsent();
		assertTrue(BeanUtils.deepCompare(updateConsent, updatedConsent));
		// check database data
		verifyData("Consents_UpdateConsent", "", "consents", SQL_CONSENTS);
		verifyData("ConsentQuestions_UpdateConsent", "", "consent_questions",
				SQL_CONSENT_QUESTIONS);
		
		//update consent
		final Consent retireConsent = createConsent(TEST_CONSENT_TITLE,UPDATE_DISCRIMINATOR + " 2");
		request.setUpdateMode(UpdateMode.D);
		request.setStudyIdentifier(studyId);
		request.setConsent(retireConsent);
		updatedConsent = service.updateStudyConsent(request)
				.getConsent();
		//	assertTrue(BeanUtils.deepCompare(retireConsent, updatedConsent));
		// check database data
		verifyData("Consents_RetireConsent", "", "consents", SQL_CONSENTS);
		
		//test using query
		// all consents of the study
		final QueryStudyConsentRequest request1 = new QueryStudyConsentRequest();
		request1.setStudyIdentifier(studyId);
		List<Consent> list = service.queryStudyConsent(request1).getConsents()
				.getItem();
		assertEquals(2, list.size());
		
		// study does not exist.
		studyId.getIdentifier().setExtension(
				RandomStringUtils.randomAlphanumeric(32));
		try {
			service.updateStudyConsent(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Unexistent study creation passed.");
		}*/
		
	}

	private void executeQueryConsentTest()
			throws SecurityExceptionFaultMessage, StudyUtilityFaultMessage {
		StudyUtility service = getService();

		// all consents of the study
		final QueryStudyConsentRequest request = new QueryStudyConsentRequest();
		final DocumentIdentifier studyId = createStudyPrimaryIdentifier();
		request.setStudyIdentifier(studyId);
		List<Consent> list = service.queryStudyConsent(request).getConsents()
				.getItem();
		assertEquals(1, list.size());

		// consent with data
		Consent example = createConsent(TEST_CONSENT_TITLE,"");
		example.setMandatoryIndicator(null);
		request.setConsent(example);
		list = service.queryStudyConsent(request).getConsents().getItem();
		assertEquals(1, list.size());
		example = createConsent(TEST_CONSENT_TITLE,"");
		assertTrue(BeanUtils.deepCompare(example, list.get(0)));

		// consent does not exist
		example.setOfficialTitle(iso.ST(RandomStringUtils
				.randomAlphanumeric(256)));
		request.setConsent(example);
		list = service.queryStudyConsent(request).getConsents().getItem();
		assertEquals(0, list.size());

		// study does not exist.
		studyId.getIdentifier().setExtension(
				RandomStringUtils.randomAlphanumeric(32));
		try {
			service.queryStudyConsent(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Unexistent study creation passed.");
		}

	}

	private void executeQueryStudyTest() throws SecurityExceptionFaultMessage,
			StudyUtilityFaultMessage {
		StudyUtility service = getService();
		QueryStudyAbstractRequest request = new QueryStudyAbstractRequest();
		DSETAdvanceSearchCriterionParameter params = new DSETAdvanceSearchCriterionParameter();
		request.setParameters(params);

		AdvanceSearchCriterionParameter param = new AdvanceSearchCriterionParameter();
		params.getItem().add(param);

		ST objName = iso.ST();
		objName.setValue("edu.duke.cabig.c3pr.domain.Identifier");
		param.setObjectName(objName);

		ST attrName = iso.ST();
		attrName.setValue("value");
		param.setAttributeName(attrName);

		ST value = iso.ST();
		value.setValue(STUDY_ID);
		param.setValues(new DSETST());
		param.getValues().getItem().add(value);

		CD pred = new CD();
		pred.setCode("=");
		param.setPredicate(pred);

		ST ctxName = iso.ST();
		ctxName.setValue("Study");
		param.setObjectContextName(ctxName);

		List<StudyProtocolVersion> list = service.queryStudyAbstract(request)
				.getStudies().getItem();
		assertEquals(1, list.size());
		StudyProtocolVersion foundStudy = list.get(0);
		assertTrue(BeanUtils.deepCompare(createStudy(""), foundStudy));

		// nothing found
		value.setValue(RandomStringUtils.randomAlphanumeric(32));
		list = service.queryStudyAbstract(request).getStudies().getItem();
		assertEquals(0, list.size());

	}

	private void executeCreateStudyTest() throws SQLException, Exception {
		StudyUtility service = getService();

		// no organization of the given ID/type combination:
		// http://jira.semanticbits.com/browse/CPR-2232
		final CreateStudyAbstractRequest request = new CreateStudyAbstractRequest();
		StudyProtocolVersion study = createStudy("");
		study.getStudyProtocolDocument().getDocument().getDocumentIdentifier()
				.get(0).getAssigningOrganization().getOrganizationIdentifier()
				.get(0).getTypeCode().setCode("PTRAX");
		request.setStudy(study);
		try {
			service.createStudyAbstract(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			assertTrue(e.getMessage().contains("905:"));
			logger.info("No organization of the given ID/type test passed.");
		}

		// successful creation
		study = createStudy("");
		request.setStudy(study);
		StudyProtocolVersion createdStudy = service
				.createStudyAbstract(request).getStudy();
		assertNotNull(createdStudy);

		// check that the study data exists in the database
		verifyStudyDatabaseData(createdStudy, "");

		// duplicate study
		try {
			service.createStudyAbstract(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Duplicate study creation passed.");
		}

		//another study
		study.getStudyProtocolDocument().getDocument().getDocumentIdentifier()
		.get(0).getIdentifier().setExtension(STUDY_ID+"-001");
		study.getStudyProtocolDocument().getDocument().getDocumentIdentifier()
		.get(1).getIdentifier().setExtension(STUDY_ID+"-001");
		study.getStudyProtocolDocument().getDocument().getDocumentIdentifier()
		.get(2).getIdentifier().setExtension(SITE_STUDY_ID+"-001");
		study.getStudyProtocolDocument().getDocument().getDocumentIdentifier()
		.get(3).getIdentifier().setExtension(SITE_STUDY_ID+"-001");
		createdStudy = service.createStudyAbstract(request).getStudy();
		assertNotNull(createdStudy);
		
		// missing identifiers
		study.getStudyProtocolDocument().getDocument().getDocumentIdentifier()
				.clear();
		try {
			service.createStudyAbstract(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Missing identifiers testing passed.");
		}

		// malformed data
		study = createStudy("");
		study.getStudyProtocolDocument().getVersionDate().setValue("ZZZ");
		request.setStudy(study);
		try {
			service.createStudyAbstract(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Malformed data testing passed.");
		}

	}

	private void executeUpdateStudyTest() throws SQLException, Exception {
		StudyUtility service = getService();

		// successful creation
		final UpdateStudyAbstractRequest request = new UpdateStudyAbstractRequest();
		StudyProtocolVersion study = createStudy(UPDATE_DISCRIMINATOR);
		study.getStudyProtocolDocument().getDocument().getDocumentIdentifier().remove(2);
		study.getStudyProtocolDocument().getDocument().getDocumentIdentifier().add(createSiteIdentifier());
		request.setStudy(study);
		StudyProtocolVersion updatedStudy = service
				.updateStudyAbstract(request).getStudy();
		assertNotNull(updatedStudy);

		// check that the study data exists in the database
		verifyStudyDatabaseData(updatedStudy, "_Updated");

		// missing identifiers
		study.getStudyProtocolDocument().getDocument().getDocumentIdentifier()
				.clear();
		try {
			service.updateStudyAbstract(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Missing identifiers testing passed.");
		}

		// malformed data
		study = createStudy(UPDATE_DISCRIMINATOR);
		study.getStudyProtocolDocument().getVersionDate().setValue("ZZZ");
		request.setStudy(study);
		try {
			service.updateStudyAbstract(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Malformed data testing passed.");
		}

	}

	private void verifyStudyDatabaseData(StudyProtocolVersion study,
			String fileNameAppendix) throws SQLException, Exception {
		verifyData("Identifiers", fileNameAppendix, "identifiers",
				SQL_IDENTIFIERS);
		verifyData("Studies", fileNameAppendix, "studies", SQL_STUDIES);
		verifyData("StudyVersions", fileNameAppendix, "study_versions",
				SQL_STUDY_VERSIONS);
		verifyData("StudyOrganizations", fileNameAppendix,
				"study_organizations", SQL_STUDY_ORGS);
		verifyData("PermissibleRegStats", fileNameAppendix,
				"permissible_reg_stats", SQL_PERM_REG_STATS);
		verifyData("Consents", fileNameAppendix, "consents", SQL_CONSENTS);
		verifyData("ConsentQuestions", fileNameAppendix, "consent_questions",
				SQL_CONSENT_QUESTIONS);

	}

	/**
	 * @param xmlDataSetBaseName
	 * @param tableName
	 * @param querySql
	 * @throws IOException
	 * @throws DataSetException
	 * @throws SQLException
	 * @throws Exception
	 * @throws DatabaseUnitException
	 */
	protected void verifyData(final String xmlDataSetBaseName, String appendix,
			final String tableName, String querySql) throws IOException,
			DataSetException, SQLException, Exception, DatabaseUnitException {
		querySql = StringUtils.replace(querySql, "${STUDY_ID}", STUDY_ID);
		IDataSet expectedDataSet = getExpectedDataSet(xmlDataSetBaseName,
				appendix);
		ITable expectedTable = expectedDataSet.getTable(tableName);
		ITable actualData = getConnection().createQueryTable(tableName,
				querySql);
		ITable filteredTable = DefaultColumnFilter.includedColumnsTable(
				actualData, expectedTable.getTableMetaData().getColumns());
		Assertion.assertEquals(expectedTable, filteredTable);
	}

	/**
	 * @param xmlDataSetBaseName
	 * @return
	 * @throws IOException
	 * @throws DataSetException
	 */
	private IDataSet getExpectedDataSet(final String xmlDataSetBaseName,
			String appendix) throws IOException, DataSetException {
		final String prefix = DBUNIT_DATASET_PREFIX;
		IDataSet expectedDataSet = new FlatXmlDataSet(
				(InputStream) ObjectUtils.defaultIfNull(
						getClass()
								.getResourceAsStream(
										prefix + xmlDataSetBaseName + appendix
												+ ".xml"),
						getClass().getResourceAsStream(
								prefix + xmlDataSetBaseName + ".xml")));
		return expectedDataSet;
	}

	/**
	 * 
	 */
	private StudyUtility getService() {
		StudyUtilityService service = new StudyUtilityService(wsdlLocation,
				SERVICE_NAME);
		SOAPUtils.installSecurityHandler(service);
		StudyUtility client = service.getStudyUtility();
		return client;
	}

	// copy-and-paste from WebServiceRelatedTestCase, unfortunately.
	private static final String TEST_SECONDARY_REASON_DESCR = "Other";
	private static final String TEST_SECONDARY_REASON_CODE = "OTHER";
	private static final String TEST_ORG_ID = "MN026";
	private static final String TEST_ADDED_ORG_ID = "NC010";

	// study utility
	private static final String TEST_REGISTRY_STATUS = STATUS_ACTIVE;
	private static final String TEST_CONSENT_QUESTION_2 = "Question 2";
	private static final String TEST_CONSENT_QUESTION_1 = "Question 1";
	private static final String TEST_WRONG_CONSENT_QUESTION_1 = "Question 1 wrong";
	private static final String TEST_REPLACED_CONSNET_QUESTION_1 = "Replaced Consent Question 1";
	private static final String TEST_CONSENT_TITLE = "Consent";
	private static final String TEST_CONSENT_DESCRIPTION = "Consent Description";
	private static final String TEST_CONSENT_QUESTION_RELATIONSHIP = "CONSENT_QUESTION";
	private static final String TEST_CONSENT_RELATIONSHIP = "CONSENT";
	private static final String TEST_CTEP = "CTEP";
	private static final String TEST_VERSION_NUMBER = "1.0";
	private static final String TEST_VERSION_DATE_ISO = "20101005000000";
	private static final String TEST_STUDY_DESCR = "Test Study";
	public static final String TEST_STUDY_SHORT_TITLE = "Test Study Short Title";
	private static final String TEST_TARGET_REG_SYS = "C3PR";
	private static final String TEST_PRIMARY_REASON_CODE = "OTHER";
	private static final String TEST_PRIMARY_REASON_DESCR = "Other Description";
	private static final String TEST_NEW_CONSENT_QUESTION_1 = "Consent Question new 1";
	

	public StudyProtocolVersion createStudy(String appendix) {
		StudyProtocolVersion study = new StudyProtocolVersion();
		study.setTargetRegistrationSystem(iso
				.ST(TEST_TARGET_REG_SYS + appendix));
		study.setStudyProtocolDocument(createStudyProtocolDocument(appendix));
		study.getPermissibleStudySubjectRegistryStatus().add(
				createPermissibleStudySubjectRegistryStatus());
		return study;
	}

	protected PermissibleStudySubjectRegistryStatus createPermissibleStudySubjectRegistryStatus() {
		PermissibleStudySubjectRegistryStatus stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus());
		RegistryStatusReason primaryReason = createPrimaryRegistryStatusReason();
		stat.getRegistryStatus().getPrimaryReason().add(primaryReason);
		stat.getSecondaryReason().add(createSecondaryRegistryStatusReason(primaryReason));
		return stat;
	}

	protected RegistryStatus createRegistryStatus() {
		RegistryStatus stat = new RegistryStatus();
		stat.setCode(iso.CD(TEST_REGISTRY_STATUS));
		stat.setDescription(iso.ST(TEST_REGISTRY_STATUS));
		return stat;
	}

	protected RegistryStatusReason createSecondaryRegistryStatusReason(RegistryStatusReason primaryReason) {
		RegistryStatusReason r = new RegistryStatusReason();
		r.setCode(iso.CD(TEST_SECONDARY_REASON_CODE));
		r.setDescription(iso.ST(TEST_SECONDARY_REASON_DESCR));
		r.setPrimaryIndicator(iso.BL(false));
		r.setPrimaryReason(primaryReason);
		return r;
	}

	protected RegistryStatusReason createPrimaryRegistryStatusReason() {
		RegistryStatusReason r = new RegistryStatusReason();
		r.setCode(iso.CD(TEST_PRIMARY_REASON_CODE));
		r.setDescription(iso.ST(TEST_PRIMARY_REASON_DESCR));
		r.setPrimaryIndicator(iso.BL(true));
		return r;
	}

	protected StudyProtocolDocumentVersion createStudyProtocolDocument(
			String appendix) {
		StudyProtocolDocumentVersion doc = new StudyProtocolDocumentVersion();
		doc.setOfficialTitle(iso.ST(TEST_STUDY_SHORT_TITLE + appendix));
		doc.setPublicDescription(iso.ST(TEST_STUDY_DESCR + appendix));
		doc.setPublicTitle(iso.ST(TEST_STUDY_DESCR + appendix));
		doc.setText(iso.ED(TEST_STUDY_DESCR + appendix));
		doc.setVersionDate(iso.TSDateTime(TEST_VERSION_DATE_ISO));
		doc.setVersionNumberText(iso.ST(TEST_VERSION_NUMBER));
		doc.setDocument(createStudyDocument(appendix));
		doc.getDocumentVersionRelationship().add(
				createConsentRelationship(appendix));
		return doc;
	}

	protected DocumentVersionRelationship createConsentRelationship(
			String appendix) {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(iso.CD(TEST_CONSENT_RELATIONSHIP));
		rel.setTarget(createConsent(TEST_CONSENT_TITLE,appendix));
		return rel;
	}

	protected Consent createConsent(String name, String appendix) {
		Consent consent = new Consent();
		consent.setMandatoryIndicator(iso.BL(true));
		consent.setOfficialTitle(iso.ST(name));
		consent.setText(iso.ED(TEST_CONSENT_DESCRIPTION + appendix));
		// consent.setVersionDate(new TSDateTime(TEST_VERSION_DATE_ISO));
		consent.setVersionNumberText(iso.ST(TEST_VERSION_NUMBER));
		consent.setDocument(new Document());
		consent.getDocumentVersionRelationship().add(
				createConsentQuestionRelationship(TEST_CONSENT_QUESTION_1
						+ appendix));
		consent.getDocumentVersionRelationship().add(
				createConsentQuestionRelationship(TEST_CONSENT_QUESTION_2
						+ appendix));
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
		// q.setVersionDate(new TSDateTime(TEST_VERSION_DATE_ISO));
		// q.setVersionNumberText(iso.ST(TEST_VERSION_NUMBER));
		q.setDocument(new Document());
		return q;
	}

	protected Document createStudyDocument(String appendix) {
		Document doc = new Document();
		doc.getDocumentIdentifier().add(createStudyPrimaryIdentifier());
		doc.getDocumentIdentifier().add(createStudyProtocolAuthIdentifier());
		doc.getDocumentIdentifier().add(createStudyFundingSponsorIdentifier());
		doc.getDocumentIdentifier().add(createStudySystemIdentifier());
		return doc;
	}

	protected DocumentIdentifier createStudyFundingSponsorIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(false));
		docId.setTypeCode(iso.CD("STUDY_FUNDING_SPONSOR"));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	protected DocumentIdentifier createStudyPrimaryIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(true));
		docId.setTypeCode(iso.CD("COORDINATING_CENTER_IDENTIFIER"));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	protected DocumentIdentifier createStudyProtocolAuthIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(false));
		docId.setTypeCode(iso.CD("PROTOCOL_AUTHORITY_IDENTIFIER"));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}
	
	
	protected DocumentIdentifier createStudySystemIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(false));
		docId.setTypeCode(iso.CD("C3PR_SYSTEM"));
		docId.getTypeCode().setCodeSystemName("MAYO");
		return docId;
	}
	
	protected DocumentIdentifier createSiteIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(iso.II(SITE_STUDY_ID));
		docId.setPrimaryIndicator(iso.BL(false));
		docId.setTypeCode(iso.CD("SITE_IDENTIFIER"));
		
		Organization org = new Organization();
		OrganizationIdentifier orgId = new OrganizationIdentifier();
		orgId.setIdentifier(iso.II(TEST_ADDED_ORG_ID));
		orgId.setPrimaryIndicator(iso.BL(true));
		orgId.setTypeCode(iso.CD(TEST_CTEP));
		org.getOrganizationIdentifier().add(orgId);
		docId.setAssigningOrganization(org);
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
	
	// sorting by consent question official title (or code) before giving it to deep compare method
	private void sortByConsentQuestionByOfficialTitle(Consent consent){
		if(!consent.getDocumentVersionRelationship().isEmpty()){
			List<DocumentVersionRelationship> consentQuestions = consent.getDocumentVersionRelationship();
			Collections.sort(consentQuestions,new Comparator<DocumentVersionRelationship>() {
				public int compare(DocumentVersionRelationship o1,
						DocumentVersionRelationship o2) {
					return o1.getTarget().getOfficialTitle().getValue().compareToIgnoreCase(o2.getTarget().getOfficialTitle().getValue());
				}
			});
		}
	}

}

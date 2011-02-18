/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.AD;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.ADXP;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.BAGTEL;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.BL;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.CD;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.DSETAD;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.DSETCD;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.DSETENPN;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.ENPN;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.ENXP;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.II;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.IVLTSDateTime;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.ST;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.TEL;
import static edu.duke.cabig.c3pr.webservice.integration.ISO21090Helper.TSDateTime;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETST;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.PostalAddressUse;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.iso21090.TelecommunicationAddressUse;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.AdvancedQuerySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidStateTransitionExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidSubjectDataExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.NoSuchSubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectAlreadyExistsExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementService;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UnableToCreateOrUpdateSubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UpdateSubjectStateRequest;

/**
 * This test will run C3PR in embedded Tomcat and test Subject Management web
 * service against it. <br>
 * <br>
 * 
 * @author dkrylov
 * @version 1.0
 */
public class SubjectManagementWebServiceTest extends C3PREmbeddedTomcatTestBase {
	
	private static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/SubjectManagement";
	
	private final String SUBJECT_ID = RandomStringUtils.randomAlphanumeric(16);
	private final String SUBJECT_ID_SECONDARY = RandomStringUtils.randomAlphanumeric(16);
	private final String SUBJECT_SYSTEM_ID = RandomStringUtils.randomAlphanumeric(16);
	private final String SYSTEM_NAME = "MAYO";
	private final String DEFAULT_SUBJECT_SYSTEM_ID_NAME = "C3PR";
	private static final String SYSTEM_ID_TYPE = "SUBJECT_IDENTIFIER";
	private static final String DEFAULT_SUBJECT_SYSTEM_ID_TYPE = "SUBJECT_IDENTIFIER";

	private static final QName SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/SubjectManagementService",
			"SubjectManagementService");

	private static final String DBUNIT_DATASET_PREFIX = "/edu/duke/cabig/c3pr/webservice/integration/testdata/SubjectManagementWebServiceTest_";

	private static final String SQL_IDENTIFIERS = "SELECT type, dtype, primary_indicator FROM identifiers WHERE prt_id=(SELECT prt_id FROM identifiers WHERE value='${SUBJECT_ID}') and dtype<>'SAI' ORDER BY id";
	private static final String SQL_ADDRESSES = "SELECT * FROM addresses WHERE addresses.participant_id IN (SELECT participants.Id FROM participants,identifiers WHERE participants.id=identifiers.prt_id and identifiers.value='${SUBJECT_ID}') ";
	private static final String SQL_CONTACT_MECHANISMS = "SELECT * FROM contact_mechanisms WHERE contact_mechanisms.prt_id IN (SELECT participants.Id FROM participants,identifiers WHERE participants.id=identifiers.prt_id and identifiers.value='${SUBJECT_ID}') order by id";
	private static final String SQL_PARTICIPANTS = "SELECT * FROM participants p WHERE EXISTS (SELECT Id from identifiers i where i.prt_id=p.id and i.value='${SUBJECT_ID}')";
	private static final String SQL_RACE_CODE_ASS = "SELECT * FROM race_code_assocn WHERE race_code_assocn.sub_id IN (SELECT participants.Id FROM participants,identifiers WHERE participants.id=identifiers.prt_id and identifiers.value='${SUBJECT_ID}') order by id";
	private static final String SQL_CONTACT_USE_ASS = "SELECT * FROM contact_use_assocns WHERE contact_use_assocns.cntct_id IN "+ 
														"(SELECT contact_mechanisms.Id FROM participants,contact_mechanisms WHERE contact_mechanisms.prt_id IN "+ 
														"(SELECT participants.Id FROM participants,identifiers WHERE participants.id=identifiers.prt_id and identifiers.value='${SUBJECT_ID}') "+
														") order by id";
	private static final String SQL_ADDRESS_USE_ASS = "SELECT * FROM address_use_assocns WHERE address_use_assocns.add_id IN "+
													"(SELECT addresses.Id FROM participants,addresses WHERE addresses.participant_id IN "+
													"(SELECT participants.Id FROM participants,identifiers WHERE participants.id=identifiers.prt_id and identifiers.value='${SUBJECT_ID}') "+
													") order by id";

	private URL endpointURL;
	private URL wsdlLocation;
	
	private static final ISO21090Helper iso = new ISO21090Helper();

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
					"https://localhost:8443/c3pr"+WS_ENDPOINT_SERVLET_PATH);
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
	public void testSubjectManagement() throws InterruptedException,
			IOException {

		try {
			executeCreateSubjectTest();
			executeQuerySubjectTest();
			executeAdvancedQuerySubjectTest();
			executeUpdateSubjectTest();
			executeUpdateSubjectStateTest();
			executeCreateSubjectTestWithoutSubjectAddress();
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			fail(ExceptionUtils.getFullStackTrace(e));
		}

	}

	private void executeUpdateSubjectStateTest() throws DataSetException,
			IOException, SQLException, DatabaseUnitException, Exception {
		SubjectManagement service = getService();

		// successful subject state update.
		final UpdateSubjectStateRequest request = new UpdateSubjectStateRequest();
		request.setBiologicEntityIdentifier(createBioEntityOrgId(true));
		request.setNewState(ST(STATE_INACTIVE));

		Subject updatedSubject = service.updateSubjectState(request)
				.getSubject();
		assertNotNull(updatedSubject);
		assertEquals(STATE_INACTIVE, updatedSubject.getStateCode().getValue());
		verifySubjectStateDatabaseData(updatedSubject);

		// Unexistent subject.
		BiologicEntityIdentifier id = createBioEntityOrgId(true);
		id.getIdentifier().setExtension(SUBJECT_ID + "zzz");
		request.setBiologicEntityIdentifier(id);
		try {
			service.updateSubjectState(request);
			fail();
		} catch (NoSuchSubjectExceptionFaultMessage e) {
		}

		// wrong state code.
		request.setBiologicEntityIdentifier(createBioEntityOrgId(true));
		request.setNewState(ST(BAD_STATE_CODE));
		try {
			service.updateSubjectState(request);
			fail();
		} catch (InvalidStateTransitionExceptionFaultMessage e) {
		}

	}

	private void executeAdvancedQuerySubjectTest()
			throws InvalidSubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage,
			SubjectAlreadyExistsExceptionFaultMessage,
			UnableToCreateOrUpdateSubjectExceptionFaultMessage {
		SubjectManagement service = getService();

		// find a subject
		final AdvancedQuerySubjectRequest request = new AdvancedQuerySubjectRequest();
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
		value.setValue(SUBJECT_ID);
		param.setValues(new DSETST());
		param.getValues().getItem().add(value);

		CD pred = new CD();
		pred.setCode("=");
		param.setPredicate(pred);

		ST ctxName = iso.ST();
		ctxName.setNullFlavor(NullFlavor.NI);
		param.setObjectContextName(ctxName);

		List<Subject> list = service.advancedQuerySubject(request)
				.getSubjects().getItem();
		assertEquals(1, list.size());
		
		Subject subject = createSubject(false,true);
		
		// If the default c3pr generated system identifier is sent in the request, there is no need to strip it from the created subject
		//system identifiers in response, otherwise we need to remove the default system identifier from response before doing deep compare.
		if(!ifC3PRDefaultSystemIdentifierSent(subject)){
			stripC3PRDefaultSystemIdentifier( list.get(0));
		}
		assertTrue(BeanUtils.deepCompare(subject, list.get(0)));

		// find two subjects
		Subject secondSubj = createSubject(false,true);
		secondSubj.getEntity().getBiologicEntityIdentifier().get(1)
				.getIdentifier().setExtension("X" + SUBJECT_ID + "X");
		CreateSubjectRequest createReq = new CreateSubjectRequest();
		createReq.setSubject(secondSubj);
		service.createSubject(createReq);

		pred.setCode("like");
		value.setValue("%" + SUBJECT_ID + "%");

		list = service.advancedQuerySubject(request).getSubjects().getItem();
		assertEquals(2, list.size());
		
		Subject subject1 = createSubject(false,true);
		// If the default c3pr generated system identifier is sent in the request, there is no need to strip it from the created subject
		//system identifiers in response, otherwise we need to remove the default system identifier from response before doing deep compare.
		if(!ifC3PRDefaultSystemIdentifierSent(subject1)){
			stripC3PRDefaultSystemIdentifier( list.get(0));
		}
		assertTrue(BeanUtils.deepCompare(subject1, list.get(0)));
		
		// If the default c3pr generated system identifier is sent in the request, there is no need to strip it from the created subject
		//system identifiers in response, otherwise we need to remove the default system identifier from response before doing deep compare.
		if(!ifC3PRDefaultSystemIdentifierSent(secondSubj)){
			stripC3PRDefaultSystemIdentifier(list.get(1));
		}
		assertTrue(BeanUtils.deepCompare(secondSubj, list.get(1)));

		// unexistent subject
		value.setValue(SUBJECT_ID + "does not exist");
		list = service.advancedQuerySubject(request).getSubjects().getItem();
		assertEquals(0, list.size());

		// broken query format
		params.getItem().add(new AdvanceSearchCriterionParameter());
		try {
			service.advancedQuerySubject(request);
			fail();
		} catch (SOAPFaultException e) {
		}

	}

	private void executeQuerySubjectTest()
			throws InvalidSubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage {
		SubjectManagement service = getService();

		// find a subject
		final QuerySubjectRequest request = new QuerySubjectRequest();
		Subject subject = createSubject(false,true);
		request.setSubject(subject);
		List<Subject> list = service.querySubject(request).getSubjects()
				.getItem();
		assertEquals(2, list.size());
		
		subject.getEntity().getBiologicEntityIdentifier().clear();
		subject.getEntity().getBiologicEntityIdentifier().add(createBioEntityOrgId(true));
		list = service.querySubject(request).getSubjects()
		.getItem();
		assertEquals(1, list.size());
		
		
		Subject subject1 = createSubject(false,true);
		// If the default c3pr generated system identifier is sent in the request, there is no need to strip it from the created subject
		//system identifiers in response, otherwise we need to remove the default system identifier from response before doing deep compare.
		if(!ifC3PRDefaultSystemIdentifierSent(subject1)){
			stripC3PRDefaultSystemIdentifier(list.get(0));
		}
		assertTrue(BeanUtils.deepCompare(createSubject(false,true), list.get(0)));

		// subject does not exist.
		subject.getEntity().getBiologicEntityIdentifier().get(0)
				.getIdentifier().setExtension(SUBJECT_ID + " unexistent");
		list = service.querySubject(request).getSubjects().getItem();
		assertEquals(0, list.size());

	}

	private void executeUpdateSubjectTest() throws DataSetException,
			IOException, SQLException, DatabaseUnitException, Exception {
		SubjectManagement service = getService();

		// successful subject creation.
		final UpdateSubjectRequest request = new UpdateSubjectRequest();
		Subject subject = createSubject(true,true);
		request.setSubject(subject);
		Subject updatedSubject = service.updateSubject(request).getSubject();
		assertNotNull(updatedSubject);
		
		// If the default c3pr generated system identifier is sent in the request, there is no need to strip it from the created subject
		//system identifiers in response, otherwise we need to remove the default system identifier from response before doing deep compare.
		if(!ifC3PRDefaultSystemIdentifierSent(subject)){
			stripC3PRDefaultSystemIdentifier(updatedSubject);
		}
		assertTrue(BeanUtils.deepCompare(subject, updatedSubject));
//		verifySubjectDatabaseData(updatedSubject, "_Updated");

		// bad subject data
		subject.getEntity().getBirthDate().setValue(BAD_ISO_DATE);
		try {
			service.updateSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("Bad test subject data passed.");
		}

		// unexistent subject
		subject = createSubject(true,true);
		request.setSubject(subject);
		subject.getEntity().getBiologicEntityIdentifier().get(1)
				.getIdentifier().setExtension(SUBJECT_ID + " unexistent");
		try {
			service.updateSubject(request);
			fail();
		} catch (NoSuchSubjectExceptionFaultMessage e) {
			logger.info("Unexistent test subject passed.");
		}

		// missing identifiers
		subject = createSubject(true,true);
		request.setSubject(subject);
		subject.getEntity().getBiologicEntityIdentifier().clear();
		try {
			service.updateSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("Missing identifiers test passed.");
		}

		// Bad subject data, XSD validation.
		subject.getEntity().setAdministrativeGenderCode(null);
		try {
			service.updateSubject(request);
			fail();
		} catch (SOAPFaultException e) {
			logger.info("Bad subject data, XSD validation test passed.");
		}

	}
	
	private void executeCreateSubjectTestWithoutSubjectAddress()
			throws DataSetException, IOException, SQLException,
			DatabaseUnitException, Exception {
		SubjectManagement service = getService();

		// successful subject creation.
		final CreateSubjectRequest request = new CreateSubjectRequest();
		Subject subject = createSubject(false,false);
		request.setSubject(subject);
		Subject createdSubject = service.createSubject(request).getSubject();
		assertNotNull(createdSubject);
		// have to ascertain that the subject's postal address is null in both request as well as in response
		assertNull(((Person)subject.getEntity()).getPostalAddress());
		assertNull(((Person)createdSubject.getEntity()).getPostalAddress());
		
		// If the default c3pr generated system identifier is sent in the request, there is no need to strip it from the created subject
		//system identifiers in response, otherwise we need to remove the default system identifier from response before doing deep compare.
		if(!ifC3PRDefaultSystemIdentifierSent(subject)){
			stripC3PRDefaultSystemIdentifier(createdSubject);
		}
		assertTrue(BeanUtils.deepCompare(subject, createdSubject));
		// not verifying data. The other test case with complete data is already doing that
		// verifySubjectDatabaseData(createdSubject, "");

		// duplicate subject
		try {
			service.createSubject(request);
			fail();
		} catch (SubjectAlreadyExistsExceptionFaultMessage e) {
			logger.info("Duplicate subject creation passed.");
		}

		// missing primary identifier
		subject.getEntity().getBiologicEntityIdentifier().clear();
		subject.getEntity().getBiologicEntityIdentifier()
				.add(createSecondaryBioEntityOrgId(false));
		try {
			service.createSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("missing primary identifier passed.");
		}

		// duplicate secondary identifier
		subject = createSubject(false,true);
		subject.getEntity().getBiologicEntityIdentifier().get(1)
				.getIdentifier()
				.setExtension(RandomStringUtils.randomAlphanumeric(16));
		request.setSubject(subject);
		createdSubject = service.createSubject(request).getSubject();
		assertNotNull(createdSubject);
		// If the default c3pr generated system identifier is sent in the request, there is no need to strip it from the created subject
		//system identifiers in response, otherwise we need to remove the default system identifier from response before doing deep compare.
		if(!ifC3PRDefaultSystemIdentifierSent(subject)){
			stripC3PRDefaultSystemIdentifier(createdSubject);
		}
		assertTrue(BeanUtils.deepCompare(subject, createdSubject));

		// bad subject data
		subject.getEntity().getBirthDate().setValue(BAD_ISO_DATE);
		try {
			service.createSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("Bad subject data test passed.");
		}

		// missing identifiers
		subject = createSubject(false,true);
		request.setSubject(subject);
		subject.getEntity().getBiologicEntityIdentifier().clear();
		try {
			service.createSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("Missing test identifiers passed.");
		}

		// Bad subject data, XSD validation.
		subject.getEntity().setAdministrativeGenderCode(null);
		try {
			service.createSubject(request);
			fail();
		} catch (SOAPFaultException e) {
			logger.info("Bad test subject data, XSD validation passed.");
		}

	}

	private void executeCreateSubjectTest() throws DataSetException,
			IOException, SQLException, DatabaseUnitException, Exception {
		SubjectManagement service = getService();

		// successful subject creation.
		final CreateSubjectRequest request = new CreateSubjectRequest();
		Subject subject = createSubject(false,true);
		request.setSubject(subject);
		Subject createdSubject = service.createSubject(request).getSubject();
		assertNotNull(createdSubject);
		
		// If the default c3pr generated system identifier is sent in the request, there is no need to strip it from the created subject
		//system identifiers in response, otherwise we need to remove the default system identifier from response before doing deep compare.
		if(!ifC3PRDefaultSystemIdentifierSent(subject)){
			stripC3PRDefaultSystemIdentifier(createdSubject);
		}
		assertTrue(BeanUtils.deepCompare(subject, createdSubject));
		verifySubjectDatabaseData(createdSubject, "");

		// duplicate subject
		try {
			service.createSubject(request);
			fail();
		} catch (SubjectAlreadyExistsExceptionFaultMessage e) {
			logger.info("Duplicate subject creation passed.");
		}

		//missing primary identifier
		subject.getEntity().getBiologicEntityIdentifier().clear();
		subject.getEntity().getBiologicEntityIdentifier().add(createSecondaryBioEntityOrgId(false));
		try {
			service.createSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("missing primary identifier passed.");
		}
		
		// duplicate secondary identifier
		subject = createSubject(false,true);
		subject.getEntity().getBiologicEntityIdentifier().get(1).getIdentifier().setExtension(RandomStringUtils.randomAlphanumeric(16));
		request.setSubject(subject);
		createdSubject = service.createSubject(request).getSubject();
		assertNotNull(createdSubject);
		// If the default c3pr generated system identifier is sent in the request, there is no need to strip it from the created subject
		//system identifiers in response, otherwise we need to remove the default system identifier from response before doing deep compare.
		if(!ifC3PRDefaultSystemIdentifierSent(subject)){
			stripC3PRDefaultSystemIdentifier(createdSubject);
		}
		assertTrue(BeanUtils.deepCompare(subject, createdSubject));
		
		// bad subject data
		subject.getEntity().getBirthDate().setValue(BAD_ISO_DATE);
		try {
			service.createSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("Bad subject data test passed.");
		}

		// missing identifiers
		subject = createSubject(false,true);
		request.setSubject(subject);
		subject.getEntity().getBiologicEntityIdentifier().clear();
		try {
			service.createSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("Missing test identifiers passed.");
		}

		// Bad subject data, XSD validation.
		subject.getEntity().setAdministrativeGenderCode(null);
		try {
			service.createSubject(request);
			fail();
		} catch (SOAPFaultException e) {
			logger.info("Bad test subject data, XSD validation passed.");
		}

	}

	private void verifySubjectDatabaseData(Subject subject,
			String fileNameAppendix) throws DataSetException, IOException,
			SQLException, DatabaseUnitException, Exception {
		verifyData("Identifiers", fileNameAppendix, "identifiers",
				SQL_IDENTIFIERS);
		verifyData("Addresses", fileNameAppendix, "addresses", SQL_ADDRESSES);
		verifyData("ContactMechanisms", fileNameAppendix, "contact_mechanisms",
				SQL_CONTACT_MECHANISMS);
		verifyData("Participants", fileNameAppendix, "participants",
				SQL_PARTICIPANTS);
		verifyData("RaceCodeAssocn", fileNameAppendix, "race_code_assocn",
				SQL_RACE_CODE_ASS);
		verifyData("AddressUseAssocn", fileNameAppendix, "address_use_assocns",
				SQL_ADDRESS_USE_ASS);
		verifyData("ContactUseAssocn", fileNameAppendix, "contact_use_assocns",
				SQL_CONTACT_USE_ASS);

	}

	private void verifySubjectStateDatabaseData(Subject subject)
			throws DataSetException, IOException, SQLException,
			DatabaseUnitException, Exception {
		verifyData("Participants", "_StateUpdated", "participants",
				SQL_PARTICIPANTS);
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
		querySql = StringUtils.replace(querySql, "${SUBJECT_ID}", SUBJECT_ID);
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

	private SubjectManagement getService() {
		SubjectManagementService service = new SubjectManagementService(
				wsdlLocation, SERVICE_NAME);
		SOAPUtils.installSecurityHandler(service);
		SubjectManagement client = service.getSubjectManagement();
		return client;
	}

	// copy-and-paste from WebServiceRelatedTestCase, unfortunately. Contact D.
	// Krylov for explanation.
	private static final String BAD_STATE_CODE = "bad state code";
	private static final String BAD_ISO_DATE = "1990-01-01";
	private static final String TS_DATETIME_PATTERN = "yyyyMMddHHmmss";

	private static final String TEST_FAX = "555-555-5555";
	private static final String TEST_FAX_2 = "666-666-6666";
	private static final String TEST_FAX_ISO = "x-text-fax:" + TEST_FAX;
	private static final String TEST_FAX_ISO_2 = "x-text-fax:" + TEST_FAX_2;
	private static final String TEST_PHONE = "555-555-5555";
	private static final String TEST_PHONE_2 = "666-666-6666";
	private static final String TEST_PHONE_ISO = "tel:" + TEST_PHONE;
	private static final String TEST_PHONE_ISO_2 = "tel:" + TEST_PHONE_2;
	private static final String TEST_OTHER_2 = "some_value";
	private static final String TEST_OTHER_ISO_2 = "user-defined:" + TEST_OTHER_2;
	private static final String TEST_EMAIL_ADDR = "johndoe@semanticbits.com";
	private static final String TEST_EMAIL_ADDR_2 = "johndoe2@semanticbits.com";
	private static final String TEST_EMAIL_ADDR_ISO = "mailto:"
			+ TEST_EMAIL_ADDR;
	private static final String TEST_EMAIL_ADDR_ISO_2 = "mailto:"
			+ TEST_EMAIL_ADDR_2;
	private static final String RACE_ASIAN = "Asian";
	private static final String RACE_WHITE = "White";
	private static final String RACE_BLACK = "Black or African American";
	private static final String TEST_COUNTRY = "USA";
	private static final String TEST_COUNTRY_2 = "UK";
	private static final String TEST_ZIP_CODE = "22203-5555";
	private static final String TEST_ZIP_CODE_2 = "22204-4555";
	private static final String TEST_STATE_CODE = "VA";
	private static final String TEST_STATE_CODE_2 = "GA";
	private static final String TEST_CITY_NAME = "Arlington";
	private static final String TEST_CITY_NAME_2 = "Vienna";
	private static final String TEST_STREET_ADDRESS = "1029 N Stuart St Unit 999";
	private static final String TEST_STREET_ADDRESS_2 = "2029 N Stuart St Unit 299";
	private static final String TEST_LAST_NAME = "Doe";
	private static final String TEST_LAST_NAME_2 = "Doe2";
	private static final String TEST_MID_NAME = "Z";
	private static final String TEST_MID_NAME_2 = "A";
	private static final String TEST_FIRST_NAME = "John";
	private static final String TEST_FIRST_NAME_2 = "Johnny";
	private static final String MARITAL_STATUS_SINGLE = "Single";
	private static final String MARITAL_STATUS_MARRIED = "Married";
	private static final String ETHNIC_CODE_NOT_REPORTED = "Not Reported";
	private static final String ETHNIC_CODE_HISPANIC_OR_LATINO = "Hispanic or Latino";
	private static final String TEST_DEATH_DATE_ISO = "19900101000000";
	private static final String TEST_DEATH_DATE_ISO_2 = "19910101000000";
	private static final String TEST_BIRTH_DATE_ISO = "19800101000000";
	private static final String TEST_BIRTH_DATE_ISO_2 = "19810101000000";
	private static final String GENDER_MALE = "Male";
	private static final String GENDER_FEMALE = "Female";
	private static final String STATE_ACTIVE = "ACTIVE";
	private static final String STATE_INACTIVE = "INACTIVE";
	private static final String ORG_ID_TYPE_MRN = "MRN";
	private static final String ORG_ID_TYPE_COOP = "COOPERATIVE_GROUP_IDENTIFIER";
	private static final String ORG_ID_TYPE_CTEP = "CTEP";
	private static final String TEST_ORG_ID = "MN026";
	
	

	protected static Date parseISODate(String isoDate) {
		try {
			return DateUtils.parseDate(isoDate,
					new String[] { TS_DATETIME_PATTERN });
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param person
	 * @return
	 */
	protected Subject createSubject(boolean useAlternativeDataSet, boolean addAddress) {
		Subject s = new Subject();
		s.setEntity(createPerson(useAlternativeDataSet,addAddress));
		s.setStateCode(ST(STATE_ACTIVE));
		return s;
	}

	/**
	 * @return
	 */
	protected Person createPerson(boolean a,boolean addAddress) {
		Person person = new Person();
		// Only 1 identifier can be primary. To avoid duplicate subject exception, we change the primary identifier
		// when we create another subject with addAddress as false.
		if(addAddress){
			person.getBiologicEntityIdentifier().add(createSecondaryBioEntityOrgId(false));
			person.getBiologicEntityIdentifier().add(createBioEntityOrgId(true));
			person.getBiologicEntityIdentifier().add(createBioEntitySystemId(false));
		}else{
			// set the secondary bio identifier as primary
			person.getBiologicEntityIdentifier().add(createTertiaryBioEntityOrgId(true));
			person.getBiologicEntityIdentifier().add(createBioEntityOrgId(false));
			person.getBiologicEntityIdentifier().add(createBioEntitySystemId(false));
		}
		person.setAdministrativeGenderCode(!a ? CD(GENDER_MALE) : CD(
				GENDER_FEMALE));
		person.setBirthDate(!a ? TSDateTime(TEST_BIRTH_DATE_ISO)
				: TSDateTime(TEST_BIRTH_DATE_ISO_2));
		person.setDeathDate(!a ? TSDateTime(TEST_DEATH_DATE_ISO)
				: TSDateTime(TEST_DEATH_DATE_ISO_2));
		person.setDeathIndicator(BL(true));
		person.setEthnicGroupCode(!a ? DSETCD(CD(
				ETHNIC_CODE_NOT_REPORTED)) : DSETCD(CD(
				ETHNIC_CODE_HISPANIC_OR_LATINO)));
		person.setMaritalStatusCode(!a ? CD(MARITAL_STATUS_SINGLE)
				: CD(MARITAL_STATUS_MARRIED));
		person.setName(!a ? DSETENPN(ENPN(ENXP(TEST_FIRST_NAME,
				EntityNamePartType.GIV), ENXP(TEST_MID_NAME,
				EntityNamePartType.GIV), ENXP(TEST_LAST_NAME,
				EntityNamePartType.FAM))) : DSETENPN(ENPN(ENXP(
				TEST_FIRST_NAME_2, EntityNamePartType.GIV), ENXP(
				TEST_MID_NAME_2, EntityNamePartType.GIV), ENXP(
				TEST_LAST_NAME_2, EntityNamePartType.FAM))));
		if(addAddress){
			person.setPostalAddress(!a ? DSETAD(AD(Arrays.asList(PostalAddressUse.H, PostalAddressUse.HV),ADXP(
					TEST_STREET_ADDRESS, AddressPartType.SAL), ADXP(
					TEST_CITY_NAME, AddressPartType.CTY), ADXP(TEST_STATE_CODE,
					AddressPartType.STA), ADXP(TEST_ZIP_CODE,
					AddressPartType.ZIP), ADXP(TEST_COUNTRY,
					AddressPartType.CNT))) : DSETAD(AD(Arrays.asList(PostalAddressUse.ABC, PostalAddressUse.DIR),ADXP(
					TEST_STREET_ADDRESS_2, AddressPartType.SAL), ADXP(
					TEST_CITY_NAME_2, AddressPartType.CTY), ADXP(
					TEST_STATE_CODE_2, AddressPartType.STA), ADXP(
					TEST_ZIP_CODE_2, AddressPartType.ZIP), ADXP(TEST_COUNTRY_2,
					AddressPartType.CNT))));
		}
		person.setRaceCode(!a ? DSETCD(CD(RACE_WHITE), CD(
				RACE_ASIAN)) : DSETCD((CD(RACE_BLACK))));
		person.setTelecomAddress(!a ? BAGTEL(TEL(TEST_EMAIL_ADDR_ISO, Arrays.asList(TelecommunicationAddressUse.H, TelecommunicationAddressUse.HV)),
				TEL(TEST_PHONE_ISO, Arrays.asList(TelecommunicationAddressUse.H, TelecommunicationAddressUse.HV)), 
				TEL(TEST_FAX_ISO, Arrays.asList(TelecommunicationAddressUse.H, TelecommunicationAddressUse.HV))) : BAGTEL(
				TEL(TEST_EMAIL_ADDR_ISO_2, Arrays.asList(TelecommunicationAddressUse.HP, TelecommunicationAddressUse.AS)), 
				TEL(TEST_PHONE_ISO_2, Arrays.asList(TelecommunicationAddressUse.HP, TelecommunicationAddressUse.AS)),
				TEL(TEST_FAX_ISO_2, Arrays.asList(TelecommunicationAddressUse.HP, TelecommunicationAddressUse.AS)),
				TEL(TEST_OTHER_ISO_2, Arrays.asList(TelecommunicationAddressUse.HP, TelecommunicationAddressUse.AS))));
		return person;
	}

	/**
	 * @return
	 */
	protected BiologicEntityIdentifier createBioEntityOrgId(boolean isPrimary) {
		OrganizationIdentifier orgId = new OrganizationIdentifier();
		orgId.setIdentifier(II(TEST_ORG_ID));
		orgId.setPrimaryIndicator(BL(true));
		orgId.setTypeCode(CD(ORG_ID_TYPE_CTEP));

		Organization org = new Organization();
		org.getOrganizationIdentifier().add(orgId);

		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setAssigningOrganization(org);
		bioId.setIdentifier(II(SUBJECT_ID));
		bioId.setTypeCode(CD(ORG_ID_TYPE_MRN));
		bioId.setEffectiveDateRange(IVLTSDateTime(NullFlavor.NI));
		bioId.setPrimaryIndicator(isPrimary? BL(true):BL(false));
		return bioId;
	}
	
	/**
	 * @return
	 */
	protected BiologicEntityIdentifier createSecondaryBioEntityOrgId(boolean isPrimary) {
		OrganizationIdentifier orgId = new OrganizationIdentifier();
		orgId.setIdentifier(II(TEST_ORG_ID));
		orgId.setPrimaryIndicator(BL(true));
		orgId.setTypeCode(CD(ORG_ID_TYPE_CTEP));

		Organization org = new Organization();
		org.getOrganizationIdentifier().add(orgId);

		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setAssigningOrganization(org);
		bioId.setIdentifier(II(SUBJECT_ID_SECONDARY));
		bioId.setTypeCode(CD(ORG_ID_TYPE_COOP));
		bioId.setEffectiveDateRange(IVLTSDateTime(NullFlavor.NI));
		bioId.setPrimaryIndicator(isPrimary? BL(true):BL(false));
		return bioId;
	}
	
	/**
	 * @return
	 */
	protected BiologicEntityIdentifier createBioEntitySystemId(boolean isPrimary) {

		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setIdentifier(II(SUBJECT_SYSTEM_ID));
		bioId.setTypeCode(CD(SYSTEM_ID_TYPE));
		bioId.getTypeCode().setCodeSystemName(SYSTEM_NAME);
		bioId.setEffectiveDateRange(IVLTSDateTime(NullFlavor.NI));
		bioId.setPrimaryIndicator(isPrimary? BL(true):BL(false));
		return bioId;
	}
	
	/**
	 * @return
	 */
	protected BiologicEntityIdentifier createTertiaryBioEntityOrgId(boolean isPrimary) {
		OrganizationIdentifier orgId = new OrganizationIdentifier();
		orgId.setIdentifier(II(TEST_ORG_ID));
		orgId.setPrimaryIndicator(BL(true));
		orgId.setTypeCode(CD(ORG_ID_TYPE_CTEP));

		Organization org = new Organization();
		org.getOrganizationIdentifier().add(orgId);

		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setAssigningOrganization(org);
		bioId.setIdentifier(II(RandomStringUtils.randomAlphanumeric(16)));
		bioId.setTypeCode(CD(ORG_ID_TYPE_COOP));
		bioId.setEffectiveDateRange(IVLTSDateTime(NullFlavor.NI));
		bioId.setPrimaryIndicator(isPrimary? BL(true):BL(false));
		return bioId;
	}
	
	private boolean ifC3PRDefaultSystemIdentifierSent(Subject subject){
		for(BiologicEntityIdentifier bioIdentifier:((Person)subject.getEntity()).getBiologicEntityIdentifier()){
			if(bioIdentifier.getTypeCode().getCodeSystemName() != null && bioIdentifier.getTypeCode().getCodeSystemName().equals(DEFAULT_SUBJECT_SYSTEM_ID_NAME) 
					&& bioIdentifier.getTypeCode().equals(DEFAULT_SUBJECT_SYSTEM_ID_TYPE)){
				return true;
			}
		}
		
		return false;
	}
	
	
	private void stripC3PRDefaultSystemIdentifier(Subject subject){
		for(Iterator<BiologicEntityIdentifier> bioIdIterator = ((Person)(subject.getEntity())).getBiologicEntityIdentifier().iterator();bioIdIterator.hasNext();){
			BiologicEntityIdentifier bioId = bioIdIterator.next();
			if(bioId.getTypeCode().getCodeSystemName() != null && bioId.getTypeCode().getCodeSystemName().equals(DEFAULT_SUBJECT_SYSTEM_ID_NAME) 
					&& bioId.getTypeCode().getCode().equals(DEFAULT_SUBJECT_SYSTEM_ID_TYPE)){
				bioIdIterator.remove();
			}
		}
	}
	
	
	

}

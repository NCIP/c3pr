/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
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
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETST;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.AdvancedQuerySubjectRequest;
import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidStateTransitionExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidSubjectDataExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.NoSuchSubjectExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject;
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

	private static final int TIMEOUT = 1000 * 60 * 3;
	private static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/SubjectManagement";
	
	private final String SUBJECT_ID = RandomStringUtils.randomAlphanumeric(16);

	private static final QName SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/SubjectManagementService",
			"SubjectManagementService");

	private static final String DBUNIT_DATASET_PREFIX = "/edu/duke/cabig/c3pr/webservice/integration/testdata/SubjectManagementWebServiceTest_";

	private static final String SQL_IDENTIFIERS = "SELECT type, dtype FROM identifiers WHERE value='${SUBJECT_ID}' ORDER BY id";
	private static final String SQL_ADDRESSES = "SELECT * FROM addresses WHERE addresses.participant_id IN (SELECT participants.Id FROM participants,identifiers WHERE participants.id=identifiers.prt_id and identifiers.value='${SUBJECT_ID}') ";
	private static final String SQL_CONTACT_MECHANISMS = "SELECT * FROM contact_mechanisms WHERE contact_mechanisms.prt_id IN (SELECT participants.Id FROM participants,identifiers WHERE participants.id=identifiers.prt_id and identifiers.value='${SUBJECT_ID}') order by id";
	private static final String SQL_PARTICIPANTS = "SELECT * FROM participants p WHERE EXISTS (SELECT Id from identifiers i where i.prt_id=p.id and i.value='${SUBJECT_ID}')";
	private static final String SQL_RACE_CODE_ASS = "SELECT * FROM race_code_assocn WHERE race_code_assocn.sub_id IN (SELECT participants.Id FROM participants,identifiers WHERE participants.id=identifiers.prt_id and identifiers.value='${SUBJECT_ID}') order by id";

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
	public void testSubjectManagement() throws InterruptedException,
			IOException {

		try {
			executeCreateSubjectTest();
			executeQuerySubjectTest();
			executeAdvancedQuerySubjectTest();
			executeUpdateSubjectTest();
			executeUpdateSubjectStateTest();
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
		request.setBiologicEntityIdentifier(createBioEntityId());
		request.setNewState(iso.ST(STATE_INACTIVE));

		Subject updatedSubject = service.updateSubjectState(request)
				.getSubject();
		assertNotNull(updatedSubject);
		assertEquals(STATE_INACTIVE, updatedSubject.getStateCode().getValue());
		verifySubjectStateDatabaseData(updatedSubject);

		// Unexistent subject.
		BiologicEntityIdentifier id = createBioEntityId();
		id.getIdentifier().setExtension(SUBJECT_ID + "zzz");
		request.setBiologicEntityIdentifier(id);
		try {
			service.updateSubjectState(request);
			fail();
		} catch (NoSuchSubjectExceptionFaultMessage e) {
		}

		// wrong state code.
		request.setBiologicEntityIdentifier(createBioEntityId());
		request.setNewState(iso.ST(BAD_STATE_CODE));
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
		assertTrue(BeanUtils.deepCompare(createSubject(false), list.get(0)));

		// find two subjects
		Subject secondSubj = createSubject(false);
		secondSubj.getEntity().getBiologicEntityIdentifier().get(0)
				.getIdentifier().setExtension("X" + SUBJECT_ID + "X");
		CreateSubjectRequest createReq = new CreateSubjectRequest();
		createReq.setSubject(secondSubj);
		service.createSubject(createReq);

		pred.setCode("like");
		value.setValue("%" + SUBJECT_ID + "%");

		list = service.advancedQuerySubject(request).getSubjects().getItem();
		assertEquals(2, list.size());
		assertTrue(BeanUtils.deepCompare(createSubject(false), list.get(0)));
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
		Subject subject = createSubject(false);
		request.setSubject(subject);
		List<Subject> list = service.querySubject(request).getSubjects()
				.getItem();
		assertEquals(1, list.size());
		assertTrue(BeanUtils.deepCompare(subject, list.get(0)));

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
		Subject subject = createSubject(true);
		request.setSubject(subject);
		Subject updatedSubject = service.updateSubject(request).getSubject();
		assertNotNull(updatedSubject);
		assertTrue(BeanUtils.deepCompare(subject, updatedSubject));
		verifySubjectDatabaseData(updatedSubject, "_Updated");

		// bad subject data
		subject.getEntity().getBirthDate().setValue(BAD_ISO_DATE);
		try {
			service.updateSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("Bad subject data test passed.");
		}

		// unexistent subject
		subject = createSubject(true);
		request.setSubject(subject);
		subject.getEntity().getBiologicEntityIdentifier().get(0)
				.getIdentifier().setExtension(SUBJECT_ID + " unexistent");
		try {
			service.updateSubject(request);
			fail();
		} catch (NoSuchSubjectExceptionFaultMessage e) {
			logger.info("Unexistent subject test passed.");
		}

		// missing identifiers
		subject = createSubject(true);
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

	private void executeCreateSubjectTest() throws DataSetException,
			IOException, SQLException, DatabaseUnitException, Exception {
		SubjectManagement service = getService();

		// successful subject creation.
		final CreateSubjectRequest request = new CreateSubjectRequest();
		Subject subject = createSubject(false);
		request.setSubject(subject);
		Subject createdSubject = service.createSubject(request).getSubject();
		assertNotNull(createdSubject);
		assertTrue(BeanUtils.deepCompare(subject, createdSubject));
		verifySubjectDatabaseData(createdSubject, "");

		// duplicate subject
		try {
			service.createSubject(request);
			fail();
		} catch (SubjectAlreadyExistsExceptionFaultMessage e) {
			logger.info("Duplicate subject creation passed.");
		}

		// bad subject data
		subject.getEntity().getBirthDate().setValue(BAD_ISO_DATE);
		try {
			service.createSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("Bad subject data test passed.");
		}

		// missing identifiers
		subject = createSubject(false);
		request.setSubject(subject);
		subject.getEntity().getBiologicEntityIdentifier().clear();
		try {
			service.createSubject(request);
			fail();
		} catch (InvalidSubjectDataExceptionFaultMessage e) {
			logger.info("Missing identifiers test passed.");
		}

		// Bad subject data, XSD validation.
		subject.getEntity().setAdministrativeGenderCode(null);
		try {
			service.createSubject(request);
			fail();
		} catch (SOAPFaultException e) {
			logger.info("Bad subject data, XSD validation test passed.");
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
	protected Subject createSubject(boolean useAlternativeDataSet) {
		Subject s = new Subject();
		s.setEntity(createPerson(useAlternativeDataSet));
		s.setStateCode(iso.ST(STATE_ACTIVE));
		return s;
	}

	/**
	 * @return
	 */
	protected Person createPerson(boolean a) {
		Person person = new Person();
		person.getBiologicEntityIdentifier().add(createBioEntityId());
		person.setAdministrativeGenderCode(!a ? iso.CD(GENDER_MALE) : iso.CD(
				GENDER_FEMALE));
		person.setBirthDate(!a ? iso.TSDateTime(TEST_BIRTH_DATE_ISO)
				: iso.TSDateTime(TEST_BIRTH_DATE_ISO_2));
		person.setDeathDate(!a ? iso.TSDateTime(TEST_DEATH_DATE_ISO)
				: iso.TSDateTime(TEST_DEATH_DATE_ISO_2));
		person.setDeathIndicator(iso.BL(true));
		person.setEthnicGroupCode(!a ? iso.DSETCD(iso.CD(
				ETHNIC_CODE_NOT_REPORTED)) : iso.DSETCD(iso.CD(
				ETHNIC_CODE_HISPANIC_OR_LATINO)));
		person.setMaritalStatusCode(!a ? iso.CD(MARITAL_STATUS_SINGLE)
				: iso.CD(MARITAL_STATUS_MARRIED));
		person.setName(!a ? iso.DSETENPN(iso.ENPN(iso.ENXP(TEST_FIRST_NAME,
				EntityNamePartType.GIV), iso.ENXP(TEST_MID_NAME,
				EntityNamePartType.GIV), iso.ENXP(TEST_LAST_NAME,
				EntityNamePartType.FAM))) : iso.DSETENPN(iso.ENPN(iso.ENXP(
				TEST_FIRST_NAME_2, EntityNamePartType.GIV), iso.ENXP(
				TEST_MID_NAME_2, EntityNamePartType.GIV), iso.ENXP(
				TEST_LAST_NAME_2, EntityNamePartType.FAM))));
		person.setPostalAddress(!a ? iso.DSETAD(iso.AD(iso.ADXP(
				TEST_STREET_ADDRESS, AddressPartType.SAL), iso.ADXP(
				TEST_CITY_NAME, AddressPartType.CTY), iso.ADXP(TEST_STATE_CODE,
				AddressPartType.STA), iso.ADXP(TEST_ZIP_CODE,
				AddressPartType.ZIP), iso.ADXP(TEST_COUNTRY,
				AddressPartType.CNT))) : iso.DSETAD(iso.AD(iso.ADXP(
				TEST_STREET_ADDRESS_2, AddressPartType.SAL), iso.ADXP(
				TEST_CITY_NAME_2, AddressPartType.CTY), iso.ADXP(
				TEST_STATE_CODE_2, AddressPartType.STA), iso.ADXP(
				TEST_ZIP_CODE_2, AddressPartType.ZIP), iso.ADXP(TEST_COUNTRY_2,
				AddressPartType.CNT))));
		person.setRaceCode(!a ? iso.DSETCD(iso.CD(RACE_WHITE), iso.CD(
				RACE_ASIAN)) : iso.DSETCD((iso.CD(RACE_BLACK))));
		person.setTelecomAddress(!a ? iso.BAGTEL(iso.TEL(TEST_EMAIL_ADDR_ISO),
				iso.TEL(TEST_PHONE_ISO), iso.TEL(TEST_FAX_ISO)) : iso.BAGTEL(
				iso.TEL(TEST_EMAIL_ADDR_ISO_2), iso.TEL(TEST_PHONE_ISO_2),
				iso.TEL(TEST_FAX_ISO_2)));
		return person;
	}

	/**
	 * @return
	 */
	protected BiologicEntityIdentifier createBioEntityId() {
		OrganizationIdentifier orgId = new OrganizationIdentifier();
		orgId.setIdentifier(iso.II(TEST_ORG_ID));
		orgId.setPrimaryIndicator(iso.BL(true));
		orgId.setTypeCode(iso.CD(ORG_ID_TYPE_CTEP));

		Organization org = new Organization();
		org.getOrganizationIdentifier().add(orgId);

		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setAssigningOrganization(org);
		bioId.setIdentifier(iso.II(SUBJECT_ID));
		bioId.setTypeCode(iso.CD(ORG_ID_TYPE_MRN));
		bioId.setEffectiveDateRange(iso.IVLTSDateTime(NullFlavor.NI));
		bioId.setPrimaryIndicator(iso.BL(true));
		return bioId;
	}

}

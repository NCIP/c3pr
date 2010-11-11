/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.sql.SQLException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.io.IOUtils;
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
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This test will run C3PR in embedded Tomcat and test Study Import web service
 * against it. <br>
 * <br>
 * 
 * @author dkrylov
 * @version 1.0
 */
public class StudyImportWebServiceTest extends C3PREmbeddedTomcatTestBase {

	private static final String TESTDATA_PACKAGE = "/edu/duke/cabig/c3pr/webservice/integration/"
			+ TESTDATA;
	private static final String SERVICE_NS = "http://enterpriseservices.nci.nih.gov/StudyImportService";
	private static final int TIMEOUT = 1000 * 60 * 3;
	private static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/StudyImport";

	private final String STUDY_ID = RandomStringUtils.randomAlphanumeric(16);

	private static final QName SERVICE_NAME = new QName(SERVICE_NS,
			"StudyImportService");
	private static final QName PORT_NAME = new QName(SERVICE_NS, "StudyImport");

	private static final String DBUNIT_DATASET_PREFIX = TESTDATA_PACKAGE
			+ "/StudyImportWebServiceTest_";

	private static final String SQL_IDENTIFIERS = "SELECT identifiers.primary_indicator, identifiers.type, identifiers.dtype, identifiers.system_name, organizations.name FROM identifiers LEFT JOIN organizations on hcs_id=organizations.id WHERE value='${STUDY_ID}' ORDER BY identifiers.id desc";
	private static final String SQL_CONSENT_QUESTIONS = "SELECT * FROM consent_questions WHERE EXISTS (SELECT id from consents where consents.id=consent_questions.con_id AND EXISTS (SELECT Id FROM study_versions where study_versions.id=consents.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')))) ORDER BY consent_questions.code";
	private static final String SQL_CONSENTS = "SELECT * FROM consents WHERE EXISTS (SELECT Id FROM study_versions where study_versions.id=consents.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')))";
	private static final String SQL_STUDY_ORGS = "SELECT study_organizations.type, organizations.name FROM study_organizations INNER JOIN organizations ON study_organizations.hcs_id=organizations.id WHERE EXISTS (SELECT Id FROM studies where studies.id=study_organizations.study_id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')) ORDER BY study_organizations.id";
	private static final String SQL_STUDY_VERSIONS = "SELECT * FROM study_versions WHERE EXISTS (SELECT Id FROM studies where studies.id=study_versions.study_id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}'))";
	private static final String SQL_STUDIES = "SELECT * FROM studies WHERE EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')";
	private static final String SQL_EPOCHS = "SELECT * FROM epochs WHERE EXISTS (SELECT Id FROM study_versions where study_versions.id=epochs.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')))";
	private static final String SQL_ARMS = "SELECT * FROM arms WHERE EXISTS (SELECT id from epochs where epochs.id=arms.eph_id AND EXISTS (SELECT Id FROM study_versions where study_versions.id=epochs.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')))) ORDER BY arms.id";
	private static final String SQL_RANDOMIZATIONS = "select * from randomizations r where r.id in (SELECT epochs.id FROM epochs WHERE EXISTS (SELECT Id FROM study_versions where study_versions.id=epochs.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}'))))";
	private static final String SQL_ELIGIBILITY_CRITERIA = "SELECT * FROM eligibility_criteria WHERE EXISTS (SELECT id from epochs where epochs.id=eligibility_criteria.eph_id AND EXISTS (SELECT Id FROM study_versions where study_versions.id=epochs.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')))) ORDER BY id";
	private static final String SQL_STRAT_CRITERIA = "SELECT * FROM strat_criteria WHERE EXISTS (SELECT id from epochs where epochs.id=strat_criteria.eph_id AND EXISTS (SELECT Id FROM study_versions where study_versions.id=epochs.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')))) ORDER BY id";
	private static final String SQL_STRAT_CRI_PER_ANS = "SELECT * FROM strat_cri_per_ans WHERE strat_cri_per_ans.str_cri_id IN (SELECT Id FROM strat_criteria WHERE EXISTS (SELECT id from epochs where epochs.id=strat_criteria.eph_id AND EXISTS (SELECT Id FROM study_versions where study_versions.id=epochs.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}'))))) ORDER BY ID";
	private static final String SQL_STRATUM_GROUPS = "SELECT * FROM stratum_groups WHERE EXISTS (SELECT id from epochs where epochs.id=stratum_groups.epochs_id AND EXISTS (SELECT Id FROM study_versions where study_versions.id=epochs.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')))) ORDER BY id";
	private static final String SQL_DISEASE_TERMS = "select medra_code from disease_terms t where t.id in (SELECT study_diseases.disease_term_id FROM study_diseases WHERE EXISTS (SELECT Id FROM study_versions where study_versions.id=study_diseases.stu_version_id AND EXISTS (SELECT Id from studies where study_versions.study_id=studies.id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}')))) order by id";
	private static final String SQL_STUDY_INVESTIGATORS = "SELECT study_investigators.role_code, study_investigators.status_code, investigators.assigned_identifier, organizations.name FROM study_investigators INNER JOIN hc_site_investigators ON study_investigators.hsi_id=hc_site_investigators.id INNER JOIN investigators ON investigators.id=hc_site_investigators.inv_id INNER JOIN organizations ON organizations.id=hc_site_investigators.hcs_id WHERE study_investigators.sto_id IN (SELECT Id FROM study_organizations WHERE EXISTS (SELECT Id FROM studies where studies.id=study_organizations.study_id and EXISTS (SELECT Id from Identifiers WHERE Identifiers.stu_id=studies.id and Identifiers.value='${STUDY_ID}'))) ORDER BY role_code";

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
		if (noEmbeddedTomcat) {
			endpointURL = new URL("https://localhost:8443/c3pr"
					+ WS_ENDPOINT_SERVLET_PATH);
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
	public void testStudyImport() throws InterruptedException, IOException {

		try {
			// order of the following calls matters! Sorry.
			doUnopenableStudyCheck();
			doMalformedStudyCheck();
			doSuccessfulImportStudyCheck();
			doDuplicateStudyCheck();
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			fail(ExceptionUtils.getFullStackTrace(e));
		}
	}

	private void doMalformedStudyCheck() throws SOAPException, DOMException,
			IOException, SAXException, ParserConfigurationException {

		Dispatch<SOAPMessage> dispatch = getDispatch();

		// malformed study.
		try {
			dispatch.invoke(prepareRequestEnvelope("Study_Malformed"));
			fail("Malformed study went through.");
		} catch (SOAPFaultException e) {
			logger.info("Malformed study check passed.");
		}

		// malformed study # 2.
		try {
			dispatch.invoke(prepareRequestEnvelope("Study_Malformed2"));
			fail("Malformed study went through.");
		} catch (SOAPFaultException e) {
			logger.info("Malformed study check passed.");
		}

	}

	private void doUnopenableStudyCheck() throws SOAPException, DOMException,
			IOException, SAXException, ParserConfigurationException {
		String xmlFile = "Study_Unopenable";
		Dispatch<SOAPMessage> dispatch = getDispatch();

		// study cannot be opened. biz checks must fail.
		try {
			dispatch.invoke(prepareRequestEnvelope(xmlFile));
			fail("Unopenable study went through.");
		} catch (SOAPFaultException e) {
			logger.info("Unopenable study check passed.");
		}
	}

	private void doDuplicateStudyCheck() throws SOAPException, DOMException,
			IOException, SAXException, ParserConfigurationException {
		String xmlFile = "Study";
		Dispatch<SOAPMessage> dispatch = getDispatch();

		// duplicate study: second request with same study must fail
		try {
			dispatch.invoke(prepareRequestEnvelope(xmlFile));
			fail("Duplicate study went through.");
		} catch (SOAPFaultException e) {
			logger.info("Duplicate study check passed.");
		}
	}

	/**
	 * @throws Exception
	 * @throws DatabaseUnitException
	 * @throws SQLException
	 * @throws DataSetException
	 * 
	 */
	private void doSuccessfulImportStudyCheck() throws DataSetException,
			SQLException, DatabaseUnitException, Exception {
		String xmlFile = "Study";

		// successful study save.
		Dispatch<SOAPMessage> dispatch = getDispatch();
		SOAPMessage reqMsg = prepareRequestEnvelope(xmlFile);
		SOAPMessage respMsg = dispatch.invoke(reqMsg);
		verifySuccessulResponse(respMsg);

		// verify database data.
		verifyStudyDatabaseData("");
	}

	/**
	 * @param xmlFile
	 * @return
	 * @throws SOAPException
	 * @throws DOMException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private SOAPMessage prepareRequestEnvelope(String xmlFile)
			throws SOAPException, DOMException, IOException, SAXException,
			ParserConfigurationException {
		MessageFactory mf = MessageFactory
				.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPMessage reqMsg = mf.createMessage();
		SOAPPart part = reqMsg.getSOAPPart();
		SOAPEnvelope env = part.getEnvelope();
		SOAPBody body = env.getBody();

		SOAPElement operation = body.addChildElement("ImportStudyRequest",
				"stud", SERVICE_NS);
		Element study = (Element) operation.appendChild(env.getOwnerDocument()
				.importNode(getStudyFromXML(xmlFile), true));
		reqMsg.saveChanges();
		return reqMsg;
	}

	private void verifySuccessulResponse(SOAPMessage respMsg)
			throws SOAPException {
		SOAPPart part = respMsg.getSOAPPart();
		SOAPEnvelope env = part.getEnvelope();
		SOAPBody body = env.getBody();
		NodeList nodes = body.getElementsByTagNameNS(SERVICE_NS,
				"ImportStudyResponse");
		assertEquals(1, nodes.getLength());
		// element should be empty
		Element responseEl = (Element) nodes.item(0);
		assertEquals(0, responseEl.getChildNodes().getLength());
	}

	/**
	 * @return
	 */
	private Dispatch<SOAPMessage> getDispatch() {
		Service service = getService();
		Dispatch<SOAPMessage> dispatch = service.createDispatch(PORT_NAME,
				SOAPMessage.class, Service.Mode.MESSAGE);
		return dispatch;
	}

	private Node getStudyFromXML(String xmlBaseFileName) throws IOException,
			SAXException, ParserConfigurationException {
		InputStream is = getResource(null, TESTDATA_PACKAGE + "/"
				+ xmlBaseFileName + ".xml");
		String xmlStr = IOUtils.toString(is);
		xmlStr = xmlStr.replace("${STUDY_ID}", STUDY_ID);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		org.w3c.dom.Document doc = dbf.newDocumentBuilder().parse(
				IOUtils.toInputStream(xmlStr));
		IOUtils.closeQuietly(is);
		return doc.getChildNodes().item(0);
	}

	private void verifyStudyDatabaseData(String fileNameAppendix)
			throws DataSetException, IOException, SQLException,
			DatabaseUnitException, Exception {
		verifyData("Identifiers", fileNameAppendix, "identifiers",
				SQL_IDENTIFIERS);
		verifyData("Studies", fileNameAppendix, "studies", SQL_STUDIES);
		verifyData("StudyVersions", fileNameAppendix, "study_versions",
				SQL_STUDY_VERSIONS);
		verifyData("StudyOrganizations", fileNameAppendix,
				"study_organizations", SQL_STUDY_ORGS);
		verifyData("Consents", fileNameAppendix, "consents", SQL_CONSENTS);
		verifyData("ConsentQuestions", fileNameAppendix, "consent_questions",
				SQL_CONSENT_QUESTIONS);
		verifyData("Epochs", fileNameAppendix, "epochs", SQL_EPOCHS);
		verifyData("Arms", fileNameAppendix, "arms", SQL_ARMS);
		verifyData("Randomizations", fileNameAppendix, "randomizations",
				SQL_RANDOMIZATIONS);
		verifyData("EligibilityCriteria", fileNameAppendix,
				"eligibility_criteria", SQL_ELIGIBILITY_CRITERIA);
		verifyData("StratCriteria", fileNameAppendix, "strat_criteria",
				SQL_STRAT_CRITERIA);
		verifyData("StratCriPerAns", fileNameAppendix, "strat_cri_per_ans",
				SQL_STRAT_CRI_PER_ANS);
		verifyData("StratumGroups", fileNameAppendix, "stratum_groups",
				SQL_STRATUM_GROUPS);
		verifyData("DiseaseTerms", fileNameAppendix, "disease_terms",
				SQL_DISEASE_TERMS);
		verifyData("StudyInvestigators", fileNameAppendix, "study_investigators",
				SQL_STUDY_INVESTIGATORS);		

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

	private Service getService() {
		Service service = Service.create(SERVICE_NAME);
		service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING,
				endpointURL.toString());
		SOAPUtils.installSecurityHandler(service);
		return service;
	}

}

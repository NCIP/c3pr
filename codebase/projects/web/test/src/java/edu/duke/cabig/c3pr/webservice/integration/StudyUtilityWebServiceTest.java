/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.dbunit.operation.DatabaseOperation;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import edu.duke.cabig.c3pr.webservice.testclient.common.Consent;
import edu.duke.cabig.c3pr.webservice.testclient.common.Document;
import edu.duke.cabig.c3pr.webservice.testclient.common.DocumentIdentifier;
import edu.duke.cabig.c3pr.webservice.testclient.common.DocumentVersion;
import edu.duke.cabig.c3pr.webservice.testclient.common.DocumentVersionRelationship;
import edu.duke.cabig.c3pr.webservice.testclient.common.Organization;
import edu.duke.cabig.c3pr.webservice.testclient.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.testclient.common.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.webservice.testclient.common.RegistryStatus;
import edu.duke.cabig.c3pr.webservice.testclient.common.RegistryStatusReason;
import edu.duke.cabig.c3pr.webservice.testclient.common.StudyProtocolDocumentVersion;
import edu.duke.cabig.c3pr.webservice.testclient.common.StudyProtocolVersion;
import edu.duke.cabig.c3pr.webservice.testclient.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.testclient.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.testclient.iso21090.ED;
import edu.duke.cabig.c3pr.webservice.testclient.iso21090.II;
import edu.duke.cabig.c3pr.webservice.testclient.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.testclient.iso21090.TSDateTime;
import edu.duke.cabig.c3pr.webservice.testclient.studyutility.CreateStudyRequest;
import edu.duke.cabig.c3pr.webservice.testclient.studyutility.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.testclient.studyutility.StudyUtility;
import edu.duke.cabig.c3pr.webservice.testclient.studyutility.StudyUtilityFaultMessage;
import edu.duke.cabig.c3pr.webservice.testclient.studyutility.StudyUtilityService;

/**
 * This test will run C3PR in embedded Tomcat and test Study Utility web service
 * against it. <br>
 * <br>
 * 
 * @author dkrylov
 * @version 1.0
 */
public class StudyUtilityWebServiceTest extends C3PREmbeddedTomcatTestBase {

	private static final String WSS_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
	private static final String PATH_TO_SAML_TOKEN = "/edu/duke/cabig/c3pr/webservice/integration/testdata/SAMLToken.xml";
	private static final QName SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/StudyUtilityService",
			"StudyUtilityService");
	private static final long TIMEOUT = 1000 * 60 * 10;
	private static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/StudyUtility";

	private final String STUDY_ID = RandomStringUtils.randomAlphanumeric(16);

	private URL endpointURL;

	private URL wsdlLocation;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		endpointURL = new URL("https://"
				+ InetAddress.getLocalHost().getHostName() + ":" + sslPort
				+ C3PR_CONTEXT + WS_ENDPOINT_SERVLET_PATH);
		/*
		 * endpointURL = new URL(
		 * "https://localhost:8443/c3pr/services/services/StudyUtility");
		 */
		wsdlLocation = new URL(endpointURL.toString() + "?wsdl");

		logger.info("endpointURL: " + endpointURL);
		logger.info("wsdlLocation: " + wsdlLocation);

		System.setProperty("sun.net.client.defaultConnectTimeout", "" + TIMEOUT);
		System.setProperty("sun.net.client.defaultReadTimeout", "" + TIMEOUT);

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * @throws InterruptedException
	 * @throws IOException
	 * 
	 */
	public void testStudyUtility() throws InterruptedException, IOException {

		try {
			executeCreateStudy();
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			fail(ExceptionUtils.getFullStackTrace(e));
		}

	}

	private void executeCreateStudy() throws SecurityExceptionFaultMessage,
			StudyUtilityFaultMessage {
		StudyUtility service = getService();

		// successful creation
		final CreateStudyRequest request = new CreateStudyRequest();
		StudyProtocolVersion study = createStudy();
		request.setStudy(study);
		StudyProtocolVersion createdStudy = service.createStudy(request)
				.getStudy();
		assertNotNull(createdStudy);

		// duplicate study
		try {
			service.createStudy(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Duplicate study creation passed.");
		}

		// missing identifiers
		study.getStudyProtocolDocument().getDocument().getDocumentIdentifier()
				.clear();
		try {
			service.createStudy(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Missing identifiers testing passed.");
		}

		// malformed data
		study = createStudy();
		study.getStudyProtocolDocument().getVersionDate().setValue("ZZZ");
		request.setStudy(study);
		try {
			service.createStudy(request);
			fail();
		} catch (StudyUtilityFaultMessage e) {
			logger.info("Malformed data testing passed.");
		}

	}

	/**
	 * 
	 */
	private StudyUtility getService() {
		StudyUtilityService service = new StudyUtilityService(wsdlLocation,
				SERVICE_NAME);
		service.setHandlerResolver(new HandlerResolver() {
			public List<Handler> getHandlerChain(PortInfo arg0) {
				List<Handler> list = new ArrayList<Handler>();
				list.add(getSecurityHandler());
				return list;
			}
		});
		StudyUtility client = service.getStudyUtility();
		return client;
	}

	/**
	 * @return
	 */
	private SOAPHandler<SOAPMessageContext> getSecurityHandler() {
		return new SOAPHandler<SOAPMessageContext>() {
			public void close(MessageContext arg0) {
			}

			public boolean handleFault(SOAPMessageContext arg0) {
				return true;
			}

			public boolean handleMessage(SOAPMessageContext ctx) {
				// Is this an outbound message, i.e., a request?
				Boolean isOut = (Boolean) ctx
						.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
				if (isOut) {
					SOAPMessage msg = ctx.getMessage();
					try {
						addSAMLToken(msg);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				return true;
			}

			public Set<QName> getHeaders() {
				return new HashSet();
			}
		};
	}

	private void addSAMLToken(SOAPMessage msg) throws SOAPException,
			SAXException, IOException, ParserConfigurationException {
		InputStream xml = getClass().getResourceAsStream(PATH_TO_SAML_TOKEN);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		org.w3c.dom.Document doc = dbf.newDocumentBuilder().parse(xml);
		xml.close();

		Node samlToken = doc.getChildNodes().item(0);

		SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
		SOAPHeader hdr = env.getHeader();
		if (hdr == null) {
			hdr = env.addHeader();
		}

		Name qname = env.createName("Security", "wsse", WSS_NS);
		SOAPHeaderElement security = hdr.addHeaderElement(qname);
		security.appendChild(security.getOwnerDocument().importNode(samlToken,
				true));
		msg.saveChanges();
	}

	// copy-and-paste from WebServiceRelatedTestCase, unfortunately.
	private static final String TEST_SECONDARY_REASON_DESCR = "Other";
	private static final String TEST_SECONDARY_REASON_CODE = "OTHER";
	private static final String TEST_ORG_ID = "MN026";

	// study utility
	private static final String TEST_REGISTRY_STATUS = "ACTIVE";
	private static final String TEST_CONSENT_QUESTION_2 = "Question 2";
	private static final String TEST_CONSENT_QUESTION_1 = "Question 1";
	private static final String TEST_CONSENT_TITLE = "Consent";
	private static final String TEST_CONSENT_QUESTION_RELATIONSHIP = "CONSENT_QUESTION";
	private static final String TEST_CONSENT_RELATIONSHIP = "CONSENT";
	private static final String TEST_CTEP = "CTEP";
	private static final String TEST_VERSION_NUMBER = "1.0";
	private static final String TEST_VERSION_DATE_ISO = "20101005000000";
	private static final String TEST_STUDY_DESCR = "Test Study";
	private static final String TEST_TARGET_REG_SYS = "C3PR";
	private static final String TEST_PRIMARY_REASON_CODE = "OTHER";
	private static final String TEST_PRIMARY_REASON_DESCR = "Other Description";

	public StudyProtocolVersion createStudy() {
		StudyProtocolVersion study = new StudyProtocolVersion();
		study.setTargetRegistrationSystem(new ST(TEST_TARGET_REG_SYS));
		study.setStudyProtocolDocument(createStudyProtocolDocument());
		study.getPermissibleStudySubjectRegistryStatus().add(
				createPermissibleStudySubjectRegistryStatus());
		return study;
	}

	protected PermissibleStudySubjectRegistryStatus createPermissibleStudySubjectRegistryStatus() {
		PermissibleStudySubjectRegistryStatus stat = new PermissibleStudySubjectRegistryStatus();
		stat.setRegistryStatus(createRegistryStatus());
		stat.getSecondaryReason().add(createSecondaryRegistryStatusReason());
		return stat;
	}

	protected RegistryStatus createRegistryStatus() {
		RegistryStatus stat = new RegistryStatus();
		stat.setCode(new CD(TEST_REGISTRY_STATUS));
		stat.setDescription(new ST(TEST_REGISTRY_STATUS));
		stat.getPrimaryReason().add(createPrimaryRegistryStatusReason());
		return stat;
	}

	protected RegistryStatusReason createSecondaryRegistryStatusReason() {
		RegistryStatusReason r = new RegistryStatusReason();
		r.setCode(new CD(TEST_SECONDARY_REASON_CODE));
		r.setDescription(new ST(TEST_SECONDARY_REASON_DESCR));
		r.setPrimaryIndicator(new BL(false));
		return r;
	}

	protected RegistryStatusReason createPrimaryRegistryStatusReason() {
		RegistryStatusReason r = new RegistryStatusReason();
		r.setCode(new CD(TEST_PRIMARY_REASON_CODE));
		r.setDescription(new ST(TEST_PRIMARY_REASON_DESCR));
		r.setPrimaryIndicator(new BL(true));
		return r;
	}

	protected StudyProtocolDocumentVersion createStudyProtocolDocument() {
		StudyProtocolDocumentVersion doc = new StudyProtocolDocumentVersion();
		// doc.setOfficialTitle(new ST(TEST_STUDY_DESCR));
		doc.setPublicDescription(new ST(TEST_STUDY_DESCR));
		doc.setPublicTitle(new ST(TEST_STUDY_DESCR));
		doc.setText(new ED(TEST_STUDY_DESCR));
		doc.setVersionDate(new TSDateTime(TEST_VERSION_DATE_ISO));
		doc.setVersionNumberText(new ST(TEST_VERSION_NUMBER));
		doc.setDocument(createStudyDocument());
		doc.getDocumentVersionRelationship().add(createConsentRelationship());
		return doc;
	}

	protected DocumentVersionRelationship createConsentRelationship() {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(new CD(TEST_CONSENT_RELATIONSHIP));
		rel.setTarget(createConsent());
		return rel;
	}

	protected Consent createConsent() {
		Consent consent = new Consent();
		consent.setMandatoryIndicator(new BL(true));
		consent.setOfficialTitle(new ST(TEST_CONSENT_TITLE));
		// consent.setVersionDate(new TSDateTime(TEST_VERSION_DATE_ISO));
		consent.setVersionNumberText(new ST(TEST_VERSION_NUMBER));
		consent.setDocument(new Document());
		consent.getDocumentVersionRelationship().add(
				createConsentQuestionRelationship(TEST_CONSENT_QUESTION_1));
		consent.getDocumentVersionRelationship().add(
				createConsentQuestionRelationship(TEST_CONSENT_QUESTION_2));
		return consent;
	}

	protected DocumentVersionRelationship createConsentQuestionRelationship(
			String text) {
		DocumentVersionRelationship rel = new DocumentVersionRelationship();
		rel.setTypeCode(new CD(TEST_CONSENT_QUESTION_RELATIONSHIP));
		rel.setTarget(createConsentQuestion(text));
		return rel;
	}

	protected DocumentVersion createConsentQuestion(String text) {
		DocumentVersion q = new DocumentVersion();
		q.setOfficialTitle(new ST(text));
		q.setText(new ED(text));
		// q.setVersionDate(new TSDateTime(TEST_VERSION_DATE_ISO));
		// q.setVersionNumberText(new ST(TEST_VERSION_NUMBER));
		q.setDocument(new Document());
		return q;
	}

	protected Document createStudyDocument() {
		Document doc = new Document();
		doc.getDocumentIdentifier().add(createStudyPrimaryIdentifier());
		doc.getDocumentIdentifier().add(createStudyProtocolAuthIdentifier());
		doc.getDocumentIdentifier().add(createStudyFundingSponsorIdentifier());
		return doc;
	}

	protected DocumentIdentifier createStudyFundingSponsorIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(new II(STUDY_ID));
		docId.setPrimaryIndicator(new BL(false));
		docId.setTypeCode(new CD("STUDY_FUNDING_SPONSOR"));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	protected DocumentIdentifier createStudyPrimaryIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(new II(STUDY_ID));
		docId.setPrimaryIndicator(new BL(true));
		docId.setTypeCode(new CD("COORDINATING_CENTER_IDENTIFIER"));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	protected DocumentIdentifier createStudyProtocolAuthIdentifier() {
		DocumentIdentifier docId = new DocumentIdentifier();
		docId.setIdentifier(new II(STUDY_ID));
		docId.setPrimaryIndicator(new BL(false));
		docId.setTypeCode(new CD("PROTOCOL_AUTHORITY_IDENTIFIER"));
		docId.setAssigningOrganization(createOrganization());
		return docId;
	}

	protected Organization createOrganization() {
		Organization org = new Organization();
		org.getOrganizationIdentifier().add(createOrganizationIdentifier());
		return org;
	}

	protected OrganizationIdentifier createOrganizationIdentifier() {
		OrganizationIdentifier orgId = new OrganizationIdentifier();
		orgId.setIdentifier(new II(TEST_ORG_ID));
		orgId.setPrimaryIndicator(new BL(true));
		orgId.setTypeCode(new CD(TEST_CTEP));
		return orgId;
	}

}

/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.RandomStringUtils;

import edu.duke.cabig.c3pr.web.security.SecureWebServiceHandler;
import edu.duke.cabig.c3pr.webservice.common.AdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.DSETAdvanceSearchCriterionParameter;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETST;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.iso21090.ST;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.AdvancedQuerySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidSubjectDataExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementService;

/**
 * This test will run C3PR in embedded Tomcat and test
 * {@link SecureWebServiceHandler} <br>
 * <br>
 * 
 * @author dkrylov
 * @version 1.0
 */
public class SecurityWebServiceTest extends C3PREmbeddedTomcatTestBase {
	
	private static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/SubjectManagement";

	private static final QName SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/SubjectManagementService",
			"SubjectManagementService");

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
					"https://localhost:8443/c3pr/services/services/SubjectManagement");
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
	 * @throws SecurityExceptionFaultMessage
	 * @throws InvalidSubjectDataExceptionFaultMessage
	 * 
	 */
	public void testSecurity() throws InterruptedException, IOException,
			InvalidSubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage {

		// prepare a request that just needs to go through; does not need to
		// return any subjects actually
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
		value.setValue(RandomStringUtils.randomAlphanumeric(32));
		param.setValues(new DSETST());
		param.getValues().getItem().add(value);

		CD pred = new CD();
		pred.setCode("=");
		param.setPredicate(pred);

		ST ctxName = iso.ST();
		ctxName.setNullFlavor(NullFlavor.NI);
		param.setObjectContextName(ctxName);

		// valid token, should go through.
		SubjectManagement service = getService(SOAPUtils.PATH_TO_SAML_TOKEN);
		List<Subject> list = service.advancedQuerySubject(request)
				.getSubjects().getItem();
		assertNotNull(list);

		// tampered token
		service = getService(SOAPUtils.PATH_TO_TAMPERED_SAML_TOKEN);
		try {
			service.advancedQuerySubject(request);
			fail("Tampered token went through.");
		} catch (SecurityExceptionFaultMessage e) {
			logger.info(e.getMessage());
			assertTrue(e.getMessage().contains(
					"failed to validate signature value"));
		}

		// Inexistent user
		service = getService(SOAPUtils.PATH_TO_UNEXISTENT_USER_SAML_TOKEN);
		try {
			service.advancedQuerySubject(request);
			fail("Unexistent user went through.");
		} catch (SecurityExceptionFaultMessage e) {
			logger.info(e.getMessage());
			assertTrue(e.getMessage().contains("User does not exist"));
		}

		// untrusted STS
		service = getService(SOAPUtils.PATH_TO_UNTRUSTED_SAML_TOKEN);
		try {
			service.advancedQuerySubject(request);
			fail("Untrusted STS went through.");
		} catch (SecurityExceptionFaultMessage e) {
			logger.info(e.getMessage());
			assertTrue(e.getMessage().contains("certificate is not trusted"));
		}

	}

	private SubjectManagement getService(final String pathToSamlToken) {
		SubjectManagementService service = new SubjectManagementService(
				wsdlLocation, SERVICE_NAME);
		SOAPUtils.installSecurityHandler(service, pathToSamlToken);
		SubjectManagement client = service.getSubjectManagement();
		return client;
	}

}

/**
 * 
 */
package edu.duke.cabig.c3pr.webservice.integration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * This test will run C3PR in embedded Tomcat and test Subject Management web
 * service against it. <br>
 * <br>
 * This version of the test operates on the XML level; it sends raw SOAP
 * envelopes and does not use a web service client. This is not a very flexible
 * approach, but it was quicker to implement. In future versions of this class
 * we may refactor to use, for example, JAX-WS client and operate on object
 * level.
 * 
 * @author dkrylov
 * @version 1.0
 */
public class SubjectManagementWebServiceTest extends
		C3PREmbeddedTomcatTestBase {

	public static final int THREE_MINUTES = 1000 * 60 * 3;
	public static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/SubjectManagement";
	private final String CREATE_SUBJECT_ID = RandomStringUtils
			.randomAlphanumeric(16);

	private URL endpointURL;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		endpointURL = new URL("https://"
				+ InetAddress.getLocalHost().getHostName() + ":" + sslPort
				+ C3PR_CONTEXT + WS_ENDPOINT_SERVLET_PATH);

	}

	/**
	 * @throws InterruptedException
	 * @throws IOException
	 * 
	 */
	public void testCreateSubject() throws InterruptedException, IOException {
		logger.info("Testing createSubject with ID " + CREATE_SUBJECT_ID
				+ "; endpoint is at " + endpointURL);
		HttpURLConnection urlConn = null;
		try {
			String soapRequest = getTestSOAPEnvelope("CreateSubjectRequest.xml");
			String expectedSoapResponse = getTestSOAPEnvelope("CreateSubjectResponse.xml");

			urlConn = createEndpointConnection();			
			sendSoapRequest(urlConn, soapRequest);
			String soapResponse = getSoapResponse(urlConn);
			logger.info("SOAP response:\r\n" + soapResponse);

			// response must be successful.
			assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());

			// we're comparing response with the expected result literally. This
			// is
			// fragile because even a minor XML formatting change
			// could break the test. Using XPath queries would probably be a
			// better solution. However, the current approach is extremely
			// simple, and we will keep it for now until we need a more
			// flexible solution.
			assertEquals(expectedSoapResponse, soapResponse);
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			dumpHttpErrorStream(urlConn);
			fail(ExceptionUtils.getFullStackTrace(e));
		}

	}

	/**
	 * @param urlConn
	 * @return
	 * @throws IOException
	 */
	private String getSoapResponse(HttpURLConnection urlConn)
			throws IOException {
		InputStream respStream = urlConn.getInputStream();
		String soapResponse = IOUtils.toString(respStream, "UTF-8");
		IOUtils.closeQuietly(respStream);
		return soapResponse;
	}

	/**
	 * @param urlConn
	 * @param soapRequest
	 * @throws IOException
	 */
	private void sendSoapRequest(HttpURLConnection urlConn, String soapRequest)
			throws IOException {
		OutputStream os = urlConn.getOutputStream();
		IOUtils.copy(new StringReader(soapRequest), os);
		os.flush();
		IOUtils.closeQuietly(os);
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private HttpURLConnection createEndpointConnection() throws IOException,
			ProtocolException {
		HttpURLConnection urlConn;
		urlConn = (HttpURLConnection) endpointURL.openConnection();
		urlConn.setRequestMethod("POST");
		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		urlConn.setUseCaches(false);
		urlConn.setConnectTimeout(THREE_MINUTES);
		urlConn.setReadTimeout(THREE_MINUTES);
		urlConn
				.setRequestProperty("Content-Type",
						"text/xml;charset=UTF-8");
		return urlConn;
	}

	private String getTestSOAPEnvelope(String fileName) throws IOException {
		String soap = IOUtils.toString(SubjectManagementWebServiceTest.class
				.getResourceAsStream(TESTDATA + "/" + fileName));
		soap = StringUtils.replace(soap, "${identifier}", CREATE_SUBJECT_ID);
		return soap;
	}

	private void dumpHttpErrorStream(HttpURLConnection urlConn)
			throws IOException {		
		final InputStream errorStream = urlConn.getErrorStream();
		logger.severe("HTTP Error Page follows:");
		logger.severe(IOUtils.toString(errorStream));
		IOUtils.closeQuietly(errorStream);
	}


}

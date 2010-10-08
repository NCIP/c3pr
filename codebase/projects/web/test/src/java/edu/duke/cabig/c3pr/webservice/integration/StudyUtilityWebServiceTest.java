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
 * This test will run C3PR in embedded Tomcat and test Study Utility web service
 * against it. <br>
 * <br>
 * 
 * @author dkrylov
 * @version 1.0
 */
public class StudyUtilityWebServiceTest extends C3PREmbeddedTomcatTestBase {

	public static final int TIMEOUT = 1000 * 60 * 3;
	public static final String WS_ENDPOINT_SERVLET_PATH = "/services/services/StudyUtility";
	private final String STUDY_ID = RandomStringUtils.randomAlphanumeric(16);

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
	public void testStudyUtility() throws InterruptedException,
			IOException {

		try {
			testCreateStudy();
		} catch (Exception e) {
			logger.severe(ExceptionUtils.getFullStackTrace(e));
			fail(ExceptionUtils.getFullStackTrace(e));
		}

	}

	private void testCreateStudy() {		
		
	}

}

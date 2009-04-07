package edu.duke.cabig.c3pr.utils.web.validators;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.globus.gsi.GlobusCredential;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.duke.cabig.c3pr.accesscontrol.SecurityContextCredentialProvider;
import edu.duke.cabig.c3pr.esb.DelegatedCredential;
import edu.duke.cabig.c3pr.infrastructure.C3PRMailSenderImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.ccts.grid.smoketest.client.SmokeTestServiceClient;

/**
 * @author Himanshu
 * 
 */
public class ValidateConnectionController extends AbstractController {
	private Configuration configuration;
	private SecurityContextCredentialProvider delegatedCredentialProvider;

	public SecurityContextCredentialProvider getDelegatedCredentialProvider() {
		return delegatedCredentialProvider;
	}

	public void setDelegatedCredentialProvider(
			SecurityContextCredentialProvider delegatedCredentialProvider) {
		this.delegatedCredentialProvider = delegatedCredentialProvider;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	private final String TYPE = "type";
	private final String ID = "id";
	private final String VALUE = "value";
	private final String TEST_URL = "testURL";
	private final String TEST_EMAIL_SERVER = "testEmailServer";
	private final String TEST_FILE_LOCATION = "testFileLocation";
	private final String TEST_SMOKE_TEST_URL = "testSmokeTestURL";
	private final String TEST_CAXCHANGE_URL = "testCaXchangeURL";

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String type = request.getParameter(TYPE);
		String value = request.getParameter(VALUE);
		String id = request.getParameter(ID);
		String errorTrace = "";
		Exception exception = null;
		if (TEST_EMAIL_SERVER.equals(type)) {
			exception = testSMTPServerConnection();
		} else if (TEST_URL.equals(type)) {
			exception = testURLConnection(value);
		} else if (TEST_FILE_LOCATION.equals(type)) {
			exception = testFileLocation(value);
		} else if (TEST_SMOKE_TEST_URL.equals(type)) {
			exception = testSmokeTestGridService(value);
		} else if (TEST_CAXCHANGE_URL.equals(type)) {
			exception = testCaXchangeURL(value);
		}
		Map map = new HashMap();
		map.put("exception", exception);
		if (exception != null) {
			errorTrace = exception.toString();
			if (exception.getMessage() != null) {
				errorTrace = errorTrace + " : " + exception.getMessage();
			}
			map.put("errorTrace", errorTrace);
		}
		map.put("type", type);
		map.put("id", id);
		return new ModelAndView("/admin/asynchronous/validationResults", map);
	}

	private Exception testURLConnection(String urlName) {
		try {
			URL url = new URL(urlName);
			URLConnection connection = url.openConnection();
			connection.connect();
		} catch (IOException exception) {
			return exception;
		}
		return null;
	}

	private Exception testSMTPServerConnection() {
		C3PRMailSenderImpl mailSender = new C3PRMailSenderImpl();
		try {
			mailSender.setHost("smtp.gmail.com");
			mailSender.setPassword("semanticbits");
			mailSender.setPort(465);
			mailSender.setProtocol("smtps");
			mailSender.setUsername("c3prproject@gmail.com");
			Properties properties = new Properties();
			mailSender.setJavaMailProperties(properties);
			MimeMessage message = mailSender.createMimeMessage();
			message.setFrom(new InternetAddress(
					"biju.joseph.padupurackal@gmail.com"));
			message.setText("Welcome biju");
			message.setReplyTo(new InternetAddress[] { new InternetAddress(
					"c3prproject@gmail.com") });
			message.setRecipient(RecipientType.TO, new InternetAddress(
					"c3prproject@gmail.com"));
			message.setSubject("My mail via c3prmailsender");
			message.setDescription("Message description");
			message.setSentDate(new Date());
			mailSender.send(message);
		} catch (Exception e) {
			return e;
		}
		return null;
	}

	private Exception testFileLocation(String location) {
		File file = new File(location);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			fis.close();
		} catch (Exception e) {
			return e;
		}
		return null;
	}

	private Exception testSmokeTestGridService(String url) {
		SmokeTestServiceClient client;
		try {
			DelegatedCredential delegatedCredential = delegatedCredentialProvider.provideDelegatedCredentials() ;
			if(delegatedCredential != null){
				GlobusCredential credential = delegatedCredential.getCredential();
				if(credential != null){
					client = new SmokeTestServiceClient(url,credential);
					client.ping();
				}else{
					return new Exception("Credentials not found.");
				}
			}else{
				new Exception("No delegated credential provider found.");
			}
		} catch (Exception exception) {
			return exception;
		}
		return null;
	}

	private Exception testCaXchangeURL(String url) {
		CaXchangeRequestProcessorClient client;
		try {
			DelegatedCredential delegatedCredential = delegatedCredentialProvider.provideDelegatedCredentials() ;
			if(delegatedCredential != null){
				GlobusCredential credential = delegatedCredential.getCredential();
				if(credential != null){
					client = new CaXchangeRequestProcessorClient(url,credential);
					client.getEndpointReference();
				}else{
					return new Exception("Credentials not found.");
				}
			}else{
				new Exception("No delegated credential provider found.");
			}
		} catch (Exception exception) {
			return exception;
		}
		return null;
	}
}

package edu.duke.cabig.c3pr.webservice.subjectmanagement.client;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.duke.cabig.c3pr.webservice.iso21090.BAGTEL;
import edu.duke.cabig.c3pr.webservice.iso21090.BL;
import edu.duke.cabig.c3pr.webservice.iso21090.CD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETAD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETCD;
import edu.duke.cabig.c3pr.webservice.iso21090.DSETENPN;
import edu.duke.cabig.c3pr.webservice.iso21090.TSDateTime;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidSubjectDataExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Person;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.QuerySubjectResponse;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.Subject;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement;

public final class Client {

	private static Log log = LogFactory.getLog(Client.class);

	static {
		// For some reason, disabling SSL checks does not always work, and I still get SSL verification exceptions.
		// May be CXF is re-enabling on its own.
		// In that case, simply import your server's certificates into JDK's truststore, and make sure
		// CN name matches the domain name in the URL.
		disableSSLVerification();
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { System.getProperty("context",
						"applicationContext.xml") });

		SubjectManagement client = (SubjectManagement) context
				.getBean("subjectManagementClient");

		QuerySubjectRequest request = new QuerySubjectRequest();
		Subject subject = new Subject();
		request.setSubject(subject);
		Person person = new Person();
		subject.setEntity(person);

		// We need to set these to empties in order to pass schema validation on the server side
		// Need to revisit this issue and perhaps change the XSD: setting fields to empties each time
		// does not make a lot of sense.
		person.setAdministrativeGenderCode(new CD());
		person.setBirthDate(new TSDateTime());
		person.setDeathDate(new TSDateTime());
		person.setDeathIndicator(new BL());
		person.setEthnicGroupCode(new DSETCD());
		person.setMaritalStatusCode(new CD());
		person.setName(new DSETENPN());
		person.setPostalAddress(new DSETAD());
		person.setRaceCode(new DSETCD());
		person.setTelecomAddress(new BAGTEL());
		
		// make repeated requests in a loop to cache things, reduce swapping.
		for (int i=0;i<2;i++) {
			client.querySubject(request);
		}

		long start = System.currentTimeMillis();
		QuerySubjectResponse response = executeAndGetResponse(client, request);
		long end = System.currentTimeMillis();
		
		for (Subject subj : response.getSubjects().getItem()) {
			log.info("Found subject with ID: "
					+ subj.getEntity().getBiologicEntityIdentifier().get(0)
							.getIdentifier().getExtension());
		}
		log.info("Total subjects: "+response.getSubjects().getItem().size());
		log.info("Processing time: "+((end-start)/1000.0)+" seconds.");

	}

	/**
	 * Isolated the call in this separate method in order to measure with profiler.
	 * @param client
	 * @param request
	 * @return
	 * @throws InvalidSubjectDataExceptionFaultMessage
	 * @throws SecurityExceptionFaultMessage
	 * @throws InterruptedException 
	 */
	public static QuerySubjectResponse executeAndGetResponse(
			SubjectManagement client, QuerySubjectRequest request)
			throws InvalidSubjectDataExceptionFaultMessage,
			SecurityExceptionFaultMessage, InterruptedException {		
		QuerySubjectResponse response = client.querySubject(request);
		return response;
	}

	private static void disableSSLVerification() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}

		com.sun.net.ssl.HostnameVerifier hv = new com.sun.net.ssl.HostnameVerifier() {

			public boolean verify(String urlHostname, String certHostname) {
				return true;
			}
		};
		com.sun.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hv);

		HostnameVerifier hv2 = new HostnameVerifier() {

			public boolean verify(String urlHostName, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv2);

	}

}

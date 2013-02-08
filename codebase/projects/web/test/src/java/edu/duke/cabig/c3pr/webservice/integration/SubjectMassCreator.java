/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.webservice.integration;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;

import org.apache.commons.lang.time.DateUtils;

import edu.duke.cabig.c3pr.webservice.common.BiologicEntityIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Organization;
import edu.duke.cabig.c3pr.webservice.common.OrganizationIdentifier;
import edu.duke.cabig.c3pr.webservice.common.Person;
import edu.duke.cabig.c3pr.webservice.common.Subject;
import edu.duke.cabig.c3pr.webservice.iso21090.AddressPartType;
import edu.duke.cabig.c3pr.webservice.iso21090.EntityNamePartType;
import edu.duke.cabig.c3pr.webservice.iso21090.NullFlavor;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.CreateSubjectRequest;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.InvalidSubjectDataExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SecurityExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectAlreadyExistsExceptionFaultMessage;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagement;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.SubjectManagementService;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.UnableToCreateOrUpdateSubjectExceptionFaultMessage;

/**
 * Creates a bunch of subjects via the web service.
 * 
 * @author dkrylov
 * 
 */
public final class SubjectMassCreator {

	private static final QName SERVICE_NAME = new QName(
			"http://enterpriseservices.nci.nih.gov/SubjectManagementService",
			"SubjectManagementService");

	private static final int NUMBER_OF_SUBJECTS = 100;

	private static URL endpointURL;
	private static URL wsdlLocation;
	
	private static final ISO21090Helper iso = new ISO21090Helper();
	
	private static final long timestamp = System.currentTimeMillis();

	static {
		try {
			endpointURL = new URL(
					"https://localhost:8443/c3pr/services/services/SubjectManagement");
			wsdlLocation = new URL(endpointURL.toString() + "?wsdl");
			disableSSLVerification();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InvalidSubjectDataExceptionFaultMessage, SecurityExceptionFaultMessage, SubjectAlreadyExistsExceptionFaultMessage, UnableToCreateOrUpdateSubjectExceptionFaultMessage {
		SubjectManagement service = getService();
		for (int i=0;i<NUMBER_OF_SUBJECTS;i++) {
			CreateSubjectRequest request = new CreateSubjectRequest();
			Subject subject = createSubject(i);
			request.setSubject(subject);
			service.createSubject(request);
			System.out.println("Created subject #"+i);
		}
	}

	private static SubjectManagement getService() {
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
	private static final String TEST_LAST_NAME = "Muddafaka";
	private static final String TEST_LAST_NAME_2 = "Muddafaka";
	private static final String TEST_MID_NAME = "Z";
	private static final String TEST_MID_NAME_2 = "A";
	private static final String TEST_FIRST_NAME = "Joey";
	private static final String TEST_FIRST_NAME_2 = "Joey";
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
	protected static Subject createSubject(int n) {
		Subject s = new Subject();
		s.setEntity(createPerson(n));
		s.setStateCode(iso.ST(STATE_ACTIVE));
		return s;
	}

	/**
	 * @return
	 */
	protected static Person createPerson(int n) {
		boolean a = false;
		Person person = new Person();
		person.getBiologicEntityIdentifier().add(createBioEntityId(n,"MN026"));
		person.getBiologicEntityIdentifier().add(createBioEntityId(n,"MN023"));
		person.getBiologicEntityIdentifier().add(createBioEntityId(n,"MN072"));
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
	protected static BiologicEntityIdentifier createBioEntityId(int n, String orgCode) {
		OrganizationIdentifier orgId = new OrganizationIdentifier();
		orgId.setIdentifier(iso.II(orgCode));
		orgId.setPrimaryIndicator(iso.BL(true));
		orgId.setTypeCode(iso.CD(ORG_ID_TYPE_CTEP));

		Organization org = new Organization();
		org.getOrganizationIdentifier().add(orgId);

		BiologicEntityIdentifier bioId = new BiologicEntityIdentifier();
		bioId.setAssigningOrganization(org);
		bioId.setIdentifier(iso.II(timestamp+"-"+n));
		bioId.setTypeCode(iso.CD(ORG_ID_TYPE_MRN));
		bioId.setEffectiveDateRange(iso.IVLTSDateTime(NullFlavor.NI));
		bioId.setPrimaryIndicator(iso.BL(true));
		return bioId;
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

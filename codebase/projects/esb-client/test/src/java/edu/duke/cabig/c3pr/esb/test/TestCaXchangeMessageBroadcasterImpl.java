package edu.duke.cabig.c3pr.esb.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import junit.framework.TestCase;
import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ServiceTypeEnum;
import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;

public class TestCaXchangeMessageBroadcasterImpl extends TestCase{

	public static final String idpUrl = "https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian";
	public static final String ifsUrl = "https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian";
	public static final String caXchangeUrl = "https://cbvapp-d1017.nci.nih.gov:28445/wsrf-caxchange/services/cagrid/CaXchangeRequestProcessor";
	
	public static final String username = "ccts@nih.gov";
	public static final String password = "!Ccts@nih.gov1";
	
	private CaXchangeMessageBroadcasterImpl messageBroadcaster = new CaXchangeMessageBroadcasterImpl();
	private HashMap<String, String> messageTypesMapping = new LinkedHashMap<String, String>();

	public TestCaXchangeMessageBroadcasterImpl() {
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		//every entry in the map(from applicationContext-esb.xml) is manually added to this instance
		messageTypesMapping.put("registration", "REGISTER_SUBJECT");
		messageTypesMapping.put("study", "STUDY_CREATION");
		messageTypesMapping.put("Person", "PERSON");
		messageTypesMapping.put("Organization", "ORGANIZATION");
		messageTypesMapping.put("ClinicalResearchStaff", "CLINICAL_RESEARCH_STAFF");
		messageTypesMapping.put("HealthCareProvider", "HEALTH_CARE_PROVIDER");
		messageTypesMapping.put("ClinicalResearchStaffCorrelation", "CLINICAL_RESEARCH_STAFF_CORRELATION");
		messageTypesMapping.put("HealthCareProviderCorrelation", "HEALTH_CARE_PROVIDER_CORRELATION");
		messageTypesMapping.put("HealthCareFacility", "HEALTH_CARE_FACILITY");
		
		TestMultisiteDelegatedCredentialProvider testMultisiteDelegatedCredentialProvider = new TestMultisiteDelegatedCredentialProvider(username, password);
		testMultisiteDelegatedCredentialProvider.setIdpUrl(idpUrl);
		testMultisiteDelegatedCredentialProvider.setIfsUrl(ifsUrl);
		
		messageBroadcaster.setDelegatedCredentialProvider(testMultisiteDelegatedCredentialProvider);
		messageBroadcaster.setCaXchangeURL(caXchangeUrl);
		messageBroadcaster.setMessageTypesMapping(messageTypesMapping);

		//not setting any response handlers, as they are all in core...it'll remain an empty set
	}
	
	
	/**
	 * Gets the payload xml as string for specified file.
	 * 
	 * @param filename the filename
	 * @return the payload for file
	 */
	public String getPayloadForFile(String filename){
		String payloadXml = "";
        BufferedReader fr = null;

        try {
			String file = ClassLoader.getSystemResource(filename).getFile();
			File f = new File(file);
	        System.out.println(f.getAbsolutePath());
	        fr = new BufferedReader(new FileReader(f));
	        
	        String temp = "";
			while ((temp = fr.readLine()) != null) {
				payloadXml += temp;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			fail();
		} catch (IOException e2) {
			e2.printStackTrace();
			fail();
		} catch (Exception e3) {
			e3.printStackTrace();
			fail();
		}
        return payloadXml;
	}
	
	/**
	 * Test broadcast for registration grid service.
	 */
	public void testBroadcast(){
		
		String payloadXml = getPayloadForFile("SampleRegistration_1.xml");
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.NA.getName(), null);
        try {
			messageBroadcaster.broadcast(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		} catch(Exception e){
			e.getStackTrace();
			fail();
		}
	}
	
	
	/**
	 * Test broadcast for COppa Message - search person.
	 */
	public void testBroadcastCoppaMessageForPersonSearch(){

		String payloadXml = getPayloadForFile("PERSON_SEARCH.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.PERSON.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	
	/**
	 * Test broadcast for COppa Message. - getById Person
	 */
	public void testBroadcastCoppaMessageForPersonId(){

		String payloadXml = getPayloadForFile("PERSON_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "extId", ServiceTypeEnum.PERSON.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	
	
	/**
	 * Test broadcast for COppa Message - search Org.
	 */
	public void testBroadcastCoppaMessageForOrganizationSearch(){

		String payloadXml = getPayloadForFile("ORGANIZATION_SEARCH.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.ORGANIZATION.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message. - getById Organization
	 */
	public void testBroadcastCoppaMessageForOrganizationId(){

		String payloadXml = getPayloadForFile("ORGANIZATION_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "extId", ServiceTypeEnum.ORGANIZATION.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	
	/**
	 * Test broadcast for COppa Message - search CLINICAL_RESEARCH_STAFF.
	 */
	public void testBroadcastCoppaMessageForClinicalResearchStaffSearch(){

		String payloadXml = getPayloadForFile("CLINICAL_RESEARCH_STAFF_SEARCH.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.CLINICAL_RESEARCH_STAFF.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message. - getById CLINICAL_RESEARCH_STAFF
	 */
	public void testBroadcastCoppaMessageForClinicalResearchStaffId(){

		String payloadXml = getPayloadForFile("CLINICAL_RESEARCH_STAFF_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "extId", ServiceTypeEnum.CLINICAL_RESEARCH_STAFF.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	
	/**
	 * Test broadcast for COppa Message - search HealthcareFacility.
	 */
	public void testBroadcastCoppaMessageForHealthcareFacilitySearch(){

		String payloadXml = getPayloadForFile("HEALTH_CARE_FACILITY_SEARCH.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.HEALTH_CARE_FACILITY.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message. - getById HealthcareFacility
	 */
	public void testBroadcastCoppaMessageForHealthcareFacilityId(){

		String payloadXml = getPayloadForFile("HEALTH_CARE_FACILITY_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "extId", ServiceTypeEnum.HEALTH_CARE_FACILITY.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	
	/**
	 * Test broadcast for COppa Message - search HealthcareProvider.
	 */
	public void testBroadcastCoppaMessageForHealthcareProviderSearch(){

		String payloadXml = getPayloadForFile("HEALTH_CARE_PROVIDER_SEARCH.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.HEALTH_CARE_PROVIDER.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message. - getById HealthcareProvider
	 */
	public void testBroadcastCoppaMessageForHealthcareProviderId(){

		String payloadXml = getPayloadForFile("HEALTH_CARE_PROVIDER_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "extId", ServiceTypeEnum.HEALTH_CARE_PROVIDER.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	
	/**
	 * Test broadcast for COppa Message - search IDENTIFIED_ORGANIZATION.
	 */
	public void testBroadcastCoppaMessageForIdentifiedOrganizationSearch(){

		String payloadXml = getPayloadForFile("IDENTIFIED_ORGANIZATION_SEARCH.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.IDENTIFIED_ORGANIZATION.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message. - getById IDENTIFIED_ORGANIZATION
	 */
	public void testBroadcastCoppaMessageForIdentifiedOrganizationId(){

		String payloadXml = getPayloadForFile("IDENTIFIED_ORGANIZATION_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "extId", ServiceTypeEnum.IDENTIFIED_ORGANIZATION.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	
	/**
	 * Test broadcast for COppa Message. - getByCtepId PERSON_BUSINESS_SERVICE
	 */
	public void testBroadcastCoppaMessageForPersonByCtepId(){

		String payloadXml = getPayloadForFile("CTEP_ID_PERSON.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getPersonByCTEPId.getName(), "extId", ServiceTypeEnum.PERSON_BUSINESS_SERVICE.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/*	this test fails but with a xml message related exception which means that the connection was successfuly established.
	 * 
	 * public void testConnection() throws MalformedURIException, RemoteException, GlobusCredentialException, FileNotFoundException{
		CaXchangeRequestProcessorClient crpc = new CaXchangeRequestProcessorClient(caXchangeUrl, new GlobusCredential(new FileInputStream("C:\\nci_credential")));
		crpc.processRequestSynchronously(new Message());
	}*/

}

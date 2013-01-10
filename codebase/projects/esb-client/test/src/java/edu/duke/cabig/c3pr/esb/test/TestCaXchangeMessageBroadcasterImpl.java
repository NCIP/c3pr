/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.esb.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import junit.framework.TestCase;
import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ServiceTypeEnum;
import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;
import edu.duke.cabig.c3pr.esb.infrastructure.TestMultisiteDelegatedCredentialProvider;

public class TestCaXchangeMessageBroadcasterImpl extends TestCase{

	public static final String idpUrl = "https://cagrid-dorian-stage.nci.nih.gov:8443/wsrf/services/cagrid/Dorian";
	public static final String ifsUrl = "https://cagrid-dorian-stage.nci.nih.gov:8443/wsrf/services/cagrid/Dorian";
	public static final String caXchangeUrl = "https://ncias-c278-v.nci.nih.gov:58445/wsrf-caxchange/services/cagrid/CaXchangeRequestProcessor";
	
	public static final String username = "cctsdev1";
	public static final String password = "An010101!!";
	
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
	 * Test broadcast for Coppa Message - search person using PO3.1.
	 */
	public void testBroadcastCoppaMessageForGetCorrelationsByPlayerIdsWithEntities(){

		String payloadXml = getPayloadForFile("PERSON_SEARCH.xml");
		List<String> cctsDomainObjectXMLList = new ArrayList<String>();
		cctsDomainObjectXMLList.add(payloadXml);
		
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getCorrelationsByPlayerIdsWithEntities.getName(), "extId", ServiceTypeEnum.PO_BUSINESS.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(cctsDomainObjectXMLList, mData);
        	System.out.println(serviceResponsePayload);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message - search person with offset.
	 */
	public void testBroadcastCoppaMessageForPersonSearchWithOffset(){

		String payloadXml = getPayloadForFile("PERSON_SEARCH.xml");
		String offsetXml = getPayloadForFile("OFFSET.xml");
		
		//search PA takes OFFSET as additional mandatory payload 
		List<String> cctsDomainObjectXMLList = new ArrayList<String>();
		cctsDomainObjectXMLList.add(payloadXml);
		cctsDomainObjectXMLList.add(offsetXml);
		
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.query.getName(), "extId", ServiceTypeEnum.PERSON.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(cctsDomainObjectXMLList, mData);
        	System.out.println(serviceResponsePayload);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message - search person without offset.
	 */
	public void testBroadcastCoppaMessageForPersonSearchWithoutOffset(){
		
		String payloadXml = getPayloadForFile("PERSON_SEARCH.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.PERSON.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payloadXml, mData);
        	System.out.println(serviceResponsePayload);
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
		String offsetXml = getPayloadForFile("OFFSET.xml");
		String serviceResponsePayload = null;
		
		//search takes OFFSET as additional mandatory payload 
		List<String> cctsDomainObjectXMLList = new ArrayList<String>();
		cctsDomainObjectXMLList.add(payloadXml);
		cctsDomainObjectXMLList.add(offsetXml);
		
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.HEALTH_CARE_FACILITY.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(cctsDomainObjectXMLList, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message. - Search ResearchOrganization
	 */
	public void testBroadcastCoppaMessageForResearchOrganizationSearch(){

		String payloadXml = getPayloadForFile("RESEARCH_ORGANIZATION_SEARCH.xml");
		String offsetXml = getPayloadForFile("OFFSET.xml");
		
		//search PA takes OFFSET as additional mandatory payload 
		List<String> cctsDomainObjectXMLList = new ArrayList<String>();
		cctsDomainObjectXMLList.add(payloadXml);
		cctsDomainObjectXMLList.add(offsetXml);
		
		String serviceResponsePayload = null;
		
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.RESEARCH_ORGANIZATION.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(cctsDomainObjectXMLList, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message. - getById ResearchOrganization
	 */
	public void testBroadcastCoppaMessageForResearchOrganizationGetById(){

		String payloadXml = getPayloadForFile("RESEARCH_ORGANIZATION_ID.xml");
		String serviceResponsePayload = null;
		
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "extId", ServiceTypeEnum.RESEARCH_ORGANIZATION.getName());
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
	
	/**
	 * Test broadcast for COppa Message - search Protocol.
	 */
	public void testBroadcastCoppaMessageForSearchProtocol(){

		String payloadXml = getPayloadForFile("STUDY_PROTOCOL_SEARCH.xml");
		String offsetXml = getPayloadForFile("OFFSET.xml");
		
		//search PA takes OFFSET as additional mandatory payload 
		List<String> cctsDomainObjectXMLList = new ArrayList<String>();
		cctsDomainObjectXMLList.add(payloadXml);
		cctsDomainObjectXMLList.add(offsetXml);
		
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.STUDY_PROTOCOL.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(cctsDomainObjectXMLList, mData);
        	System.out.println(serviceResponsePayload);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message - get study Protocol.
	 */
	public void testBroadcastCoppaMessageForGetStudyProtocol(){

		String payloadXml = getPayloadForFile("STUDY_PROTOCOL_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getStudyProtocol.getName(), "extId", ServiceTypeEnum.STUDY_PROTOCOL.getName());
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
	 * Test broadcast for COppa Message - Get StudyParticipation.
	 */
	public void testBroadcastCoppaMessageForGetStudyParticipationByStudyProtocol(){

		String payloadXml = getPayloadForFile("STUDY_PROTOCOL_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getByStudyProtocol.getName(), "extId", ServiceTypeEnum.STUDY_SITE.getName());
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
	 * Test broadcast for COppa Message - Get StudyContact.
	 */
	public void testBroadcastCoppaMessageForGetStudyContactByStudyProtocol(){

		String paPayload = "<ns1:Id root=\"2.16.840.1.113883.3.26.4.3\" identifierName=\"NCI study protocol entity identifier\" extension=\"27525\" xmlns:ns1=\"http://pa.services.coppa.nci.nih.gov\"/>";
		String rolePayload = "<ns1:StudySiteContact xmlns:ns1=\"http://pa.services.coppa.nci.nih.gov\"><ns1:roleCode code=\"Principal Investigator\"/></ns1:StudySiteContact>";
		List<String> payLoads = Arrays.asList(paPayload, rolePayload);
		
		String payloadXml = getPayloadForFile("STUDY_PROTOCOL_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getByStudyProtocolAndRole.getName(), "extId", ServiceTypeEnum.STUDY_CONTACT.getName());
        try {
        	serviceResponsePayload = messageBroadcaster.broadcastCoppaMessage(payLoads, mData);
		} catch (BroadcastException e) {
			e.printStackTrace();
			fail();
		}
		assertNotNull(serviceResponsePayload);
		assertEquals(true, serviceResponsePayload.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast for COppa Message - Get StudyParticipation.
	 */
	public void testBroadcastCoppaMessageForGetByIdStudySite(){

		String payloadXml = getPayloadForFile("STUDY_SITE_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "extId", ServiceTypeEnum.STUDY_SITE.getName());
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
	 * Test broadcast for COppa Message - get Arm.
	 */
	public void testBroadcastCoppaMessageForGetArm(){

		String payloadXml = getPayloadForFile("ARM_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.get.getName(), "extId", ServiceTypeEnum.ARM.getName());
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
	 * Test broadcast for COppa Message - get Arm By StudyProtocol.
	 */
	public void testBroadcastCoppaMessageForGetArmByStudyProtocol(){

		String payloadXml = getPayloadForFile("STUDY_PROTOCOL_ID.xml");
		String serviceResponsePayload = null;
        //build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getByStudyProtocol.getName(), "extId", ServiceTypeEnum.ARM.getName());
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
	 * 
	public void testConnection() throws RemoteException, GlobusCredentialException, FileNotFoundException, org.apache.axis.types.URI.MalformedURIException{
		CaXchangeRequestProcessorClient crpc = new CaXchangeRequestProcessorClient(caXchangeUrl, new GlobusCredential(new FileInputStream("C:\\nci_credential")));
		crpc.processRequestSynchronously(new Message());
	}*/

}

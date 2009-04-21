package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;
import edu.duke.cabig.c3pr.esb.test.TestMultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.ApplicationContextTest;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.Person;

/**
 * The Class RemoteInvestigatorResolverTest.
 * 
 * @author Vinay Gangoli
 */
public class RemoteInvestigatorResolverTest extends ApplicationContextTest{
	
	RemoteInvestigatorResolver remoteInvestigatorResolver = null;
	CaXchangeMessageBroadcasterImpl coppaMessageBroadcaster = null;
	
	public static final String idpUrl = "https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian";
	public static final String ifsUrl = "https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian";
	public static final String caXchangeUrl = "https://cbvapp-d1017.nci.nih.gov:28445/wsrf-caxchange/services/cagrid/CaXchangeRequestProcessor";
	
	public static final String username = "ccts@nih.gov";
	public static final String password = "!Ccts@nih.gov1";
	
	public static final String COPPA_NS_URI = "http://po.coppa.nci.nih.gov";
	public static final String COPPA_WSDD_FILE = "/gov/nih/nci/coppa/services/client/client-config.wsdd";
	
	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		remoteInvestigatorResolver = (RemoteInvestigatorResolver) getApplicationContext().getBean("remoteInvestigatorResolver");
		coppaMessageBroadcaster = (CaXchangeMessageBroadcasterImpl) getApplicationContext().getBean("coppaMessageBroadcaster");
		
		TestMultisiteDelegatedCredentialProvider testMultisiteDelegatedCredentialProvider = new TestMultisiteDelegatedCredentialProvider(username, password);
		testMultisiteDelegatedCredentialProvider.setIdpUrl(idpUrl);
		testMultisiteDelegatedCredentialProvider.setIfsUrl(ifsUrl);
		
		coppaMessageBroadcaster.setDelegatedCredentialProvider(testMultisiteDelegatedCredentialProvider);
		coppaMessageBroadcaster.setCaXchangeURL(caXchangeUrl);
	}
	
	
	public void testGetInvestigatorByName(){
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		
		String personXml = CoppaObjectFactory.getCoppaPersonXml(CoppaObjectFactory.getCoppaPerson("John", "", "Wolfe"));
		String resultXml = "";
		try {
			resultXml = remoteInvestigatorResolver.broadcastPersonSearch(personXml);
		} catch (Exception e) {
			System.out.print(e);
		}
		
		List<String> coppaPersons = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		RemoteInvestigator tempRemoteInvestigator = null;
		Person coppaPerson = null;
		if (coppaPersons != null){
			for(String coppaPersonXml: coppaPersons){
				coppaPerson = CoppaObjectFactory.getCoppaPerson(coppaPersonXml);
				
				HealthCareProvider healthCareProvider = CoppaObjectFactory.getCoppaHealthCareProvider(coppaPerson.getIdentifier());
				String coppaHealthCareProviderXml = CoppaObjectFactory.getCoppaHealthCareProviderXml(healthCareProvider);
				String sRolesXml = "";
				try {
					sRolesXml = remoteInvestigatorResolver.broadcastHealthcareProviderSearch(coppaHealthCareProviderXml);
				} catch (C3PRCodedException e) {
					System.out.print(e);
				}
				List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
				List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList = new ArrayList<gov.nih.nci.coppa.po.Organization>();
				for(String sRole: sRoles){
					String orgResultXml = "";
					HealthCareProvider hcp = CoppaObjectFactory.getCoppaHealthCareProvider(sRole);
					String orgIiXml = CoppaObjectFactory.getCoppaIIXml(hcp.getScoperIdentifier());
					try {
						orgResultXml = remoteInvestigatorResolver.broadcastOrganizationGetById(orgIiXml);
					} catch (Exception e) {
						System.out.print(e);
					}
					List<String> orgResults = XMLUtils.getObjectsFromCoppaResponse(orgResultXml);
					if (orgResults.size() > 0) {
						coppaOrganizationList.add(CoppaObjectFactory.getCoppaOrganization(orgResults.get(0)));
					}
				}
				tempRemoteInvestigator = remoteInvestigatorResolver.populateRemoteInvestigator(coppaPerson, coppaOrganizationList);
				remoteInvestigatorList.add(tempRemoteInvestigator);
			}
		}
		assertTrue(remoteInvestigatorList.size() > 0);
	}
	
	
	/**
	 * Test find.
	 */
	public void testFind(){
		RemoteInvestigator remoteInvestigator = getSampleRemoteInvestigator();
		List objList = remoteInvestigatorResolver.find(remoteInvestigator);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}
	
	
	/**
	 * Test get remote entity by unique id.
	 */
	public void testGetRemoteEntityByUniqueId(){
		Object object = remoteInvestigatorResolver.getRemoteEntityByUniqueId("302751");
		
		assertNotNull(object);
		assertTrue(object instanceof RemoteInvestigator);
	}
		
	
	/**
	 * Gets the sample remote Investigator.
	 * 
	 * @return the sample remote Investigator
	 */
	private RemoteInvestigator getSampleRemoteInvestigator() {
		RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
		remoteInvestigator.setFirstName("John");
		remoteInvestigator.setLastName("Wolfe");
		Address address = new Address();
		address.setCity("");
		address.setCountryCode("");
		remoteInvestigator.setAddress(address);
		return remoteInvestigator;
	}


	/**
	 * Test broadcast organization search.
	 */
	/*public void testBroadcastOrganizationSearch(){

		String resultXml = null;
		RemoteHealthcareSite remoteHealthcareSite = getSampleRemoteInvestigator();
		String payLoad = 
			CoppaObjectFactory.getCoppaOrganizationXml(remoteHealthcareSite.getName(), null, remoteHealthcareSite.getAddress().getCity(),
					null, null, remoteHealthcareSite.getAddress().getCountryCode());
		try {
			resultXml = remoteInvestigatorResolver.broadcastOrganizationSearch(payLoad);
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			fail("Caxchange broadcast Failed");
		}
		
		assertNotNull(resultXml);
		assertTrue(resultXml.contains("SUCCESS"));
	}*/
	
	/**
	 * Test broadcast organization search.
	 */
	/*public void testBroadcastIdentifiedOrganizationSearch(){

		String resultXml = null;
		String payLoad = "<ns1:IdentifiedOrganization xmlns:ns1='http://po.coppa.nci.nih.gov'><ns1:identifier nullFlavor='NI' xmlns:ns1='http://po.coppa.nci.nih.gov'/>" +
		"<ns1:playerIdentifier reliability='ISS' scope='OBJ' displayable='true' identifierName='NCI organization entity identifier' extension='136124' root='2.16.840.1.113883.3.26.4.2' xmlns:ns1='http://po.coppa.nci.nih.gov'/>" +
		"<ns1:scoperIdentifier reliability='ISS' scope='OBJ' displayable='true' identifierName='NCI organization entity identifier' extension='5355' root='2.16.840.1.113883.3.26.4.2' xmlns:ns1='http://po.coppa.nci.nih.gov'/>" +
		"<ns1:assignedId nullFlavor='NI' xmlns:ns1='http://po.coppa.nci.nih.gov'/><ns1:status nullFlavor='NI' xmlns:ns1='http://po.coppa.nci.nih.gov'/></ns1:IdentifiedOrganization>";
		try {
			resultXml = remoteInvestigatorResolver.broadcastIdentifiedOrganizationSearch(payLoad);
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			fail("Caxchange broadcast Failed");
		}
		
		assertNotNull(resultXml);
		assertTrue(resultXml.contains("SUCCESS"));
	}
	*/

	/**
	 * Test broadcast organization search.
	 */
	/*public void testBroadcastIISearch(){
		String resultXml = null;
		String payLoad = "<ns1:Id reliability='ISS' scope='OBJ' displayable='true' " +
				"identifierName='NCI organization entity identifier' extension='717' " +
				"root='UID.for.nci.entity.organization' xmlns:ns1='http://po.coppa.nci.nih.gov'/>";
		try {
			resultXml = remoteInvestigatorResolver.broadcastOrganizationGetById(payLoad);
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			fail("Caxchange broadcast Failed");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caxchange broadcast Failed");
		}
		
		assertNotNull(resultXml);
		assertTrue(resultXml.contains("SUCCESS"));
	}*/
}

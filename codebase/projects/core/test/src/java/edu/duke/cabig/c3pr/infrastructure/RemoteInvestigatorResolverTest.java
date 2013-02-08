/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.infrastructure;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;
import edu.duke.cabig.c3pr.esb.infrastructure.TestMultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.utils.ApplicationContextTest;
import edu.duke.cabig.c3pr.utils.PersonOrganizationResolverUtils;

/**
 * The Class RemoteInvestigatorResolverTest.
 * 
 * @author Vinay Gangoli
 */
public class RemoteInvestigatorResolverTest extends ApplicationContextTest{
	
	RemoteInvestigatorResolver remoteInvestigatorResolver = null;
	CaXchangeMessageBroadcasterImpl coppaMessageBroadcaster = null;
	PersonOrganizationResolverUtils personResolverUtils = null;
	
	public static final String idpUrl = "https://cagrid-dorian-stage.nci.nih.gov:8443/wsrf/services/cagrid/Dorian";
	public static final String ifsUrl = "https://cagrid-dorian-stage.nci.nih.gov:8443/wsrf/services/cagrid/Dorian";
	public static final String caXchangeUrl = "https://ncias-c278-v.nci.nih.gov:58445/wsrf-caxchange/services/cagrid/CaXchangeRequestProcessor";
	
	public static final String username = "cctsdev1";
	public static final String password = "An010101!!";
	
	public static final String COPPA_NS_URI = "http://po.coppa.nci.nih.gov";
	public static final String COPPA_WSDD_FILE = "/gov/nih/nci/coppa/services/client/client-config.wsdd";
	
	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		remoteInvestigatorResolver = (RemoteInvestigatorResolver) getApplicationContext().getBean("remoteInvestigatorResolver");
		coppaMessageBroadcaster = (CaXchangeMessageBroadcasterImpl) getApplicationContext().getBean("coppaMessageBroadcaster");
		personResolverUtils = (PersonOrganizationResolverUtils)getApplicationContext().getBean("personOrganizationResolverUtils");
		
		TestMultisiteDelegatedCredentialProvider testMultisiteDelegatedCredentialProvider = new TestMultisiteDelegatedCredentialProvider(username, password);
		testMultisiteDelegatedCredentialProvider.setIdpUrl(idpUrl);
		testMultisiteDelegatedCredentialProvider.setIfsUrl(ifsUrl);
		
		coppaMessageBroadcaster.setDelegatedCredentialProvider(testMultisiteDelegatedCredentialProvider);
		coppaMessageBroadcaster.setCaXchangeURL(caXchangeUrl);
	}
	
	
	/**
	 * Test find by name.
	 */
	public void testFindByName(){
		RemoteInvestigator remoteInvestigator = getSampleRemoteInvestigatorWithName();
		List<Object> objList = remoteInvestigatorResolver.find(remoteInvestigator);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}
	
	/**
	 * Test find by nci id.
	 
	public void testFindByNciId(){
		RemoteInvestigator remoteInvestigator = getSampleRemoteInvestigatorWithNciId();
		List<Object> objList = remoteInvestigatorResolver.find(remoteInvestigator);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}*/
	
	/**
	 * Test find by org.
	 
	public void testFindByOrganization(){
		RemoteInvestigator remoteInvestigator = getSampleRemoteInvestigatorWithOrganization();
		List<Object> objList = remoteInvestigatorResolver.find(remoteInvestigator);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}*/
	
	/**
	 * Test get remote entity by unique id.
	 
	public void testGetRemoteEntityByUniqueId(){
		Object object = remoteInvestigatorResolver.getRemoteEntityByUniqueId("1801787");
		assertNotNull(object);
		assertTrue(object instanceof RemoteInvestigator);
	}*/
		
	
	public void testHCSIDaoGetBySubnames(){
    	RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
    	HealthcareSiteInvestigator hcsi = new HealthcareSiteInvestigator();
    	
    	HealthcareSite healthcareSiteWithNciId = new LocalHealthcareSite();
    	healthcareSiteWithNciId.setCtepCode("NC010");
    	hcsi.setHealthcareSite(healthcareSiteWithNciId);
    	
    	remoteInvestigator.getHealthcareSiteInvestigators().add(hcsi);
    	
    	List<Object> invList = remoteInvestigatorResolver.find(remoteInvestigator);
		assertNotNull(invList);
	}
	
	
	private RemoteInvestigator getSampleRemoteInvestigatorWithOrganization(){
		RemoteInvestigator remoteInvestigator = new RemoteInvestigator();

		HealthcareSiteInvestigator hcsi = new HealthcareSiteInvestigator();
		HealthcareSite healthcareSite = new LocalHealthcareSite();
		healthcareSite.setCtepCode("MI025");
		hcsi.setHealthcareSite(healthcareSite);
		
		remoteInvestigator.getHealthcareSiteInvestigators().add(hcsi);
		return remoteInvestigator;
	}
	
	
	/**
	 * Gets the sample remote Investigator with Name.
	 * 
	 * @return the sample remote Investigator
	 */
	private RemoteInvestigator getSampleRemoteInvestigatorWithName() {
		RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
		remoteInvestigator.setFirstName("Ja");
		remoteInvestigator.setLastName("long");
		Address address = new Address();
		address.setCity("");
		address.setCountryCode("");
		remoteInvestigator.setAddress(address);
		return remoteInvestigator;
	}

	/**
	 * Gets the sample remote Investigator with nciId.
	 * 
	 * @return the sample remote Investigator
	 */
	private RemoteInvestigator getSampleRemoteInvestigatorWithNciId() {
		RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
		remoteInvestigator.setFirstName("");
		remoteInvestigator.setLastName("");
		Address address = new Address();
		address.setCity("");
		address.setCountryCode("");
		remoteInvestigator.setAddress(address);
		remoteInvestigator.setAssignedIdentifier("54128");
		return remoteInvestigator;
	}
	
}

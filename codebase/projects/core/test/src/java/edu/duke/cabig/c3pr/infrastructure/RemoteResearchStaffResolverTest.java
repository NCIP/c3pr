package edu.duke.cabig.c3pr.infrastructure;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;
import edu.duke.cabig.c3pr.esb.infrastructure.TestMultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.utils.ApplicationContextTest;
import edu.duke.cabig.c3pr.utils.PersonOrganizationResolverUtils;

/**
 * The Class RemoteResearchStaffResolverTest.
 * 
 * @author Vinay Gangoli
 */
public class RemoteResearchStaffResolverTest extends ApplicationContextTest{
	
	RemoteResearchStaffResolver remoteResearchStaffResolver = null;
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
		remoteResearchStaffResolver = (RemoteResearchStaffResolver) getApplicationContext().getBean("remoteResearchStaffResolver");
		coppaMessageBroadcaster = (CaXchangeMessageBroadcasterImpl) getApplicationContext().getBean("coppaMessageBroadcaster");
		personResolverUtils = (PersonOrganizationResolverUtils)getApplicationContext().getBean("personOrganizationResolverUtils");
		
		TestMultisiteDelegatedCredentialProvider testMultisiteDelegatedCredentialProvider = new TestMultisiteDelegatedCredentialProvider(username, password);
		testMultisiteDelegatedCredentialProvider.setIdpUrl(idpUrl);
		testMultisiteDelegatedCredentialProvider.setIfsUrl(ifsUrl);
		
		coppaMessageBroadcaster.setDelegatedCredentialProvider(testMultisiteDelegatedCredentialProvider);
		coppaMessageBroadcaster.setCaXchangeURL(caXchangeUrl);
	}
	
	
	/**
	 * Test find by Name.
	 */
	public void testFindByName(){
		RemoteResearchStaff remoteResearchStaff = getSampleRemoteResearchStaffWithName();
		List<Object> objList = remoteResearchStaffResolver.find(remoteResearchStaff);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}
	
	/**
	 * Test find by Organization.
	 */
	public void testFindByOrganization(){
		RemoteResearchStaff remoteResearchStaff = getSampleRemoteResearchStaffWithOrganization();
		List<Object> objList = remoteResearchStaffResolver.find(remoteResearchStaff);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}
	
	
	/**
	 * Test find by Organization.
	 */
	public void testFindByStaffNciId(){
		RemoteResearchStaff remoteResearchStaff = getSampleRemoteResearchStaffWithNciId();
		List<Object> objList = remoteResearchStaffResolver.find(remoteResearchStaff);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}
	
	/**
	 * Test get remote entity by unique id.
	 */
	public void testGetRemoteEntityByUniqueId(){
		Object object = remoteResearchStaffResolver.getRemoteEntityByUniqueId("302751");
		
		assertNotNull(object);
		assertTrue(object instanceof RemoteResearchStaff);
	}
		
	
	/**
	 * Gets the sample remote ResearchStaff.
	 * 
	 * @return the sample remote ResearchStaff
	 */
	private RemoteResearchStaff getSampleRemoteResearchStaffWithName() {
		RemoteResearchStaff remoteResearchStaff= new RemoteResearchStaff();
		remoteResearchStaff.setFirstName("J");
		remoteResearchStaff.setLastName("Long");
		Address address = new Address();
		address.setCity("");
		address.setCountryCode("");
		remoteResearchStaff.setAddress(address);
		return remoteResearchStaff;
	}
	
	/**
	 * Gets the sample remote ResearchStaff.
	 * 
	 * @return the sample remote ResearchStaff
	 */
	private RemoteResearchStaff getSampleRemoteResearchStaffWithOrganization() {
		RemoteResearchStaff remoteResearchStaff= new RemoteResearchStaff();
		remoteResearchStaff.setFirstName("");
		remoteResearchStaff.setLastName("");
		Address address = new Address();
		address.setCity("");
		address.setCountryCode("");
		remoteResearchStaff.setAddress(address);
		HealthcareSite healthcareSite = new LocalHealthcareSite();
		healthcareSite.setCtepCode("NCIMB");
		remoteResearchStaff.setHealthcareSite(healthcareSite);
		return remoteResearchStaff;
	}


	/**
	 * Gets the sample remote ResearchStaff.
	 * 
	 * @return the sample remote ResearchStaff
	 */
	private RemoteResearchStaff getSampleRemoteResearchStaffWithNciId() {
		RemoteResearchStaff remoteResearchStaff= new RemoteResearchStaff();
		remoteResearchStaff.setFirstName("");
		remoteResearchStaff.setLastName("");
		Address address = new Address();
		address.setCity("");
		address.setCountryCode("");
		remoteResearchStaff.setAddress(address);
		remoteResearchStaff.setAssignedIdentifier("176477");
		return remoteResearchStaff;
	}
}

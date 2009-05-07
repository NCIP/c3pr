package edu.duke.cabig.c3pr.infrastructure;

import java.util.List;

import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;
import edu.duke.cabig.c3pr.esb.infrastructure.TestMultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.ApplicationContextTest;
import edu.duke.cabig.c3pr.utils.PersonResolverUtils;

/**
 * The Class RemoteHealthcareSiteResolverTest.
 * 
 * @author Vinay Gangoli
 */
public class RemoteHealthcareSiteResolverTest extends ApplicationContextTest{
	
	RemoteHealthcareSiteResolver remoteHealthcareSiteResolver = null;
	CaXchangeMessageBroadcasterImpl coppaMessageBroadcaster = null;
	PersonResolverUtils personResolverUtils = null;
	
	public static final String idpUrl = "https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian";
	public static final String ifsUrl = "https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian";
	public static final String caXchangeUrl = "https://cbvapp-d1017.nci.nih.gov:28445/wsrf-caxchange/services/cagrid/CaXchangeRequestProcessor";
	
	public static final String username = "ccts@nih.gov";
	public static final String password = "!Ccts@nih.gov1";
	
	
	@Override
	protected void setUp() throws Exception {
		
		super.setUp();
		remoteHealthcareSiteResolver = (RemoteHealthcareSiteResolver) getApplicationContext().getBean("remoteHealthcareSiteResolver");
		coppaMessageBroadcaster = (CaXchangeMessageBroadcasterImpl) getApplicationContext().getBean("coppaMessageBroadcaster");
		
		TestMultisiteDelegatedCredentialProvider testMultisiteDelegatedCredentialProvider = new TestMultisiteDelegatedCredentialProvider(username, password);
		testMultisiteDelegatedCredentialProvider.setIdpUrl(idpUrl);
		testMultisiteDelegatedCredentialProvider.setIfsUrl(ifsUrl);
		
		coppaMessageBroadcaster.setDelegatedCredentialProvider(testMultisiteDelegatedCredentialProvider);
		coppaMessageBroadcaster.setCaXchangeURL(caXchangeUrl);
	}
	
	
	/**
	 * Test find.
	 */
	public void testFind(){
		RemoteHealthcareSite remoteHealthcareSite = getSampleRemoteHealthcareSite();
		List objList = remoteHealthcareSiteResolver.find(remoteHealthcareSite);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}
	
	
	/**
	 * Test get remote entity by unique id.
	 */
	public void testGetRemoteEntityByUniqueId(){
	
		Object object = remoteHealthcareSiteResolver.getRemoteEntityByUniqueId("717");
		assertNotNull(object);
		assertTrue(object instanceof RemoteHealthcareSite);
	}
		
	
	/**
	 * Gets the sample remote healthcare site.
	 * 
	 * @return the sample remote healthcare site
	 */
	private RemoteHealthcareSite getSampleRemoteHealthcareSite() {
		RemoteHealthcareSite remoteHealthcareSite = new RemoteHealthcareSite();
		remoteHealthcareSite.setName("National Cancer Center");
		Address address = new Address();
		address.setCity("");
		address.setCountryCode("");
		remoteHealthcareSite.setAddress(address);
		return remoteHealthcareSite;
	}


	/**
	 * Test broadcast organization search.
	 */
	public void testBroadcastOrganizationSearch(){

		String resultXml = null;
		RemoteHealthcareSite remoteHealthcareSite = getSampleRemoteHealthcareSite();
		String payLoad = 
			CoppaObjectFactory.getCoppaOrganizationXml(remoteHealthcareSite.getName(), null, remoteHealthcareSite.getAddress().getCity(),
					null, null, remoteHealthcareSite.getAddress().getCountryCode());
		try {
			resultXml = personResolverUtils.broadcastOrganizationSearch(payLoad);
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			fail("Caxchange broadcast Failed");
		}
		
		assertNotNull(resultXml);
		assertTrue(resultXml.contains("SUCCESS"));
	}
	
	/**
	 * Test broadcast organization search.
	 */
	public void testBroadcastIdentifiedOrganizationSearch(){

		String resultXml = null;
		String payLoad = "<ns1:IdentifiedOrganization xmlns:ns1='http://po.coppa.nci.nih.gov'><ns1:identifier nullFlavor='NI' xmlns:ns1='http://po.coppa.nci.nih.gov'/>" +
		"<ns1:playerIdentifier reliability='ISS' scope='OBJ' displayable='true' identifierName='NCI organization entity identifier' extension='136124' root='2.16.840.1.113883.3.26.4.2' xmlns:ns1='http://po.coppa.nci.nih.gov'/>" +
		"<ns1:scoperIdentifier reliability='ISS' scope='OBJ' displayable='true' identifierName='NCI organization entity identifier' extension='5355' root='2.16.840.1.113883.3.26.4.2' xmlns:ns1='http://po.coppa.nci.nih.gov'/>" +
		"<ns1:assignedId nullFlavor='NI' xmlns:ns1='http://po.coppa.nci.nih.gov'/><ns1:status nullFlavor='NI' xmlns:ns1='http://po.coppa.nci.nih.gov'/></ns1:IdentifiedOrganization>";
		try {
			resultXml = personResolverUtils.broadcastIdentifiedOrganizationSearch(payLoad);
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			fail("Caxchange broadcast Failed");
		}
		
		assertNotNull(resultXml);
		assertTrue(resultXml.contains("SUCCESS"));
	}
	

	/**
	 * Test broadcast organization search.
	 */
	public void testBroadcastIISearch(){
		String resultXml = null;
		String payLoad = "<ns1:Id reliability='ISS' scope='OBJ' displayable='true' " +
				"identifierName='NCI organization entity identifier' extension='717' " +
				"root='UID.for.nci.entity.organization' xmlns:ns1='http://po.coppa.nci.nih.gov'/>";
		try {
			resultXml = personResolverUtils.broadcastOrganizationGetById(payLoad);
		} catch (C3PRCodedException e) {
			e.printStackTrace();
			fail("Caxchange broadcast Failed");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Caxchange broadcast Failed");
		}
		
		assertNotNull(resultXml);
		assertTrue(resultXml.contains("SUCCESS"));
	}


	public PersonResolverUtils getPersonResolverUtils() {
		return personResolverUtils;
	}


	public void setPersonResolverUtils(PersonResolverUtils personResolverUtils) {
		this.personResolverUtils = personResolverUtils;
	}
}

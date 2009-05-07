package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;
import edu.duke.cabig.c3pr.esb.infrastructure.TestMultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.ApplicationContextTest;
import edu.duke.cabig.c3pr.utils.PersonResolverUtils;
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
	PersonResolverUtils personResolverUtils = null;
	
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
		personResolverUtils = (PersonResolverUtils)getApplicationContext().getBean("personResolverUtils");
		
		TestMultisiteDelegatedCredentialProvider testMultisiteDelegatedCredentialProvider = new TestMultisiteDelegatedCredentialProvider(username, password);
		testMultisiteDelegatedCredentialProvider.setIdpUrl(idpUrl);
		testMultisiteDelegatedCredentialProvider.setIfsUrl(ifsUrl);
		
		coppaMessageBroadcaster.setDelegatedCredentialProvider(testMultisiteDelegatedCredentialProvider);
		coppaMessageBroadcaster.setCaXchangeURL(caXchangeUrl);
	}
	
	
	public void testGetInvestigatorByName(){
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		
		String personXml = CoppaObjectFactory.getCoppaPersonXml(CoppaObjectFactory.getCoppaPerson("David", "", "L"));
		String resultXml = "";
		try {
			resultXml = personResolverUtils.broadcastPersonSearch(personXml);
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
					sRolesXml = personResolverUtils.broadcastHealthcareProviderSearch(coppaHealthCareProviderXml);
				} catch (C3PRCodedException e) {
					System.out.print(e);
				}
				List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
				List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList = new ArrayList<gov.nih.nci.coppa.po.Organization>();
				if(sRoles != null && sRoles.size() > 0){
					for(String sRole: sRoles){
						String orgResultXml = "";
						HealthCareProvider hcp = CoppaObjectFactory.getCoppaHealthCareProvider(sRole);
						String orgIiXml = CoppaObjectFactory.getCoppaIIXml(hcp.getScoperIdentifier());
						try {
							orgResultXml = personResolverUtils.broadcastOrganizationGetById(orgIiXml);
						} catch (Exception e) {
							System.out.print(e);
						}
						List<String> orgResults = XMLUtils.getObjectsFromCoppaResponse(orgResultXml);
						if (orgResults.size() > 0) {
							coppaOrganizationList.add(CoppaObjectFactory.getCoppaOrganization(orgResults.get(0)));
						}
					}
					tempRemoteInvestigator = remoteInvestigatorResolver.populateRemoteInvestigator(coppaPerson, null, coppaOrganizationList);
					remoteInvestigatorList.add(tempRemoteInvestigator);
				}
			}
		}
		assertTrue(remoteInvestigatorList.size() > 0);
	}
	
	
	/**
	 * Test find by name.
	 */
	public void testFindByName(){
		RemoteInvestigator remoteInvestigator = getSampleRemoteInvestigatorWithName();
		List objList = remoteInvestigatorResolver.find(remoteInvestigator);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}
	
	/**
	 * Test find by nci id.
	 */
	public void testFindByNciId(){
		RemoteInvestigator remoteInvestigator = getSampleRemoteInvestigatorWithNciId();
		List objList = remoteInvestigatorResolver.find(remoteInvestigator);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}
	
	
	
	/**
	 * Test get remote entity by unique id.
	 */
	public void testGetRemoteEntityByUniqueId(){
		Object object = remoteInvestigatorResolver.getRemoteEntityByUniqueId("1801787");
		assertNotNull(object);
		assertTrue(object instanceof RemoteInvestigator);
	}
		
	
	/**
	 * Gets the sample remote Investigator with Name.
	 * 
	 * @return the sample remote Investigator
	 */
	private RemoteInvestigator getSampleRemoteInvestigatorWithName() {
		RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
		remoteInvestigator.setFirstName("J");
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
		remoteInvestigator.setNciIdentifier("54128");
		return remoteInvestigator;
	}
	
}

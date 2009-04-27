package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;
import edu.duke.cabig.c3pr.esb.test.TestMultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.ApplicationContextTest;
import edu.duke.cabig.c3pr.utils.PersonResolverUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.Person;

/**
 * The Class RemoteResearchStaffResolverTest.
 * 
 * @author Vinay Gangoli
 */
public class RemoteResearchStaffResolverTest extends ApplicationContextTest{
	
	RemoteResearchStaffResolver remoteResearchStaffResolver = null;
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
		remoteResearchStaffResolver = (RemoteResearchStaffResolver) getApplicationContext().getBean("remoteResearchStaffResolver");
		coppaMessageBroadcaster = (CaXchangeMessageBroadcasterImpl) getApplicationContext().getBean("coppaMessageBroadcaster");
		personResolverUtils = (PersonResolverUtils)getApplicationContext().getBean("personResolverUtils");
		
		TestMultisiteDelegatedCredentialProvider testMultisiteDelegatedCredentialProvider = new TestMultisiteDelegatedCredentialProvider(username, password);
		testMultisiteDelegatedCredentialProvider.setIdpUrl(idpUrl);
		testMultisiteDelegatedCredentialProvider.setIfsUrl(ifsUrl);
		
		coppaMessageBroadcaster.setDelegatedCredentialProvider(testMultisiteDelegatedCredentialProvider);
		coppaMessageBroadcaster.setCaXchangeURL(caXchangeUrl);
	}
	
	
	public void testGetRemoteResearchStaffByName(){
		List<Object> remoteResearchStaffList = new ArrayList<Object>();;
		
		String personXml = CoppaObjectFactory.getCoppaPersonXml(CoppaObjectFactory.getCoppaPerson("J", "", "long"));
		String resultXml = "";
		try {
			resultXml = personResolverUtils.broadcastPersonSearch(personXml);
		} catch (Exception e) {
			System.out.print(e);
		}
		
		List<String> coppaPersons = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		RemoteResearchStaff tempRemoteResearchStaff= null;
		Person coppaPerson = null;
		if (coppaPersons != null){
			for(String coppaPersonXml: coppaPersons){
				coppaPerson = CoppaObjectFactory.getCoppaPerson(coppaPersonXml);
				
				ClinicalResearchStaff clinicalResearchStaff = CoppaObjectFactory.getCoppaClinicalResearchStaff(coppaPerson.getIdentifier());
				String coppaHealthCareProviderXml = CoppaObjectFactory.getCoppaClinicalResearchStaffXml(clinicalResearchStaff);
				String sRolesXml = "";
				try {
					sRolesXml = personResolverUtils.broadcastClinicalResearchStaffSearch(coppaHealthCareProviderXml);
				} catch (C3PRCodedException e) {
					System.out.print(e);
				}
				List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
				List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList = new ArrayList<gov.nih.nci.coppa.po.Organization>();
				if(sRoles != null && sRoles.size() > 0){
					for(String sRole: sRoles){
						String orgResultXml = "";
						ClinicalResearchStaff crs = CoppaObjectFactory.getCoppaClinicalResearchStaff(sRole);
						String orgIiXml = CoppaObjectFactory.getCoppaIIXml(crs.getScoperIdentifier());
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
					tempRemoteResearchStaff = remoteResearchStaffResolver.populateRemoteResearchStaff(coppaPerson, null, coppaOrganizationList);
					remoteResearchStaffList.add(tempRemoteResearchStaff);
				}
			}
		}
		assertTrue(remoteResearchStaffList.size() > 0);
	}
	
	
	/**
	 * Test find by Name.
	 */
	public void testFindByName(){
		RemoteResearchStaff remoteResearchStaff = getSampleRemoteResearchStaffWithName();
		List objList = remoteResearchStaffResolver.find(remoteResearchStaff);
		
		assertNotNull(objList);

		assertTrue(objList.size() > 0);
	}
	
	/**
	 * Test find by Organization.
	 */
	public void testFindByOrganization(){
		RemoteResearchStaff remoteResearchStaff = getSampleRemoteResearchStaffWithOrganization();
		List objList = remoteResearchStaffResolver.find(remoteResearchStaff);
		
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}
	
	
	/**
	 * Test find by Organization.
	 */
	public void testFindByStaffNciId(){
		RemoteResearchStaff remoteResearchStaff = getSampleRemoteResearchStaffWithNciId();
		List objList = remoteResearchStaffResolver.find(remoteResearchStaff);
		
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
		healthcareSite.setNciInstituteCode("NCIMB");
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
		remoteResearchStaff.setNciIdentifier("176477");
		return remoteResearchStaff;
	}
}

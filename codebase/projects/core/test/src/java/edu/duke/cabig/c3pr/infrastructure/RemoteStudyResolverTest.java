/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.infrastructure;

import java.util.List;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteStudy;
import edu.duke.cabig.c3pr.esb.impl.CaXchangeMessageBroadcasterImpl;
import edu.duke.cabig.c3pr.esb.infrastructure.TestMultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.ApplicationContextTest;
import edu.duke.cabig.c3pr.utils.PersonOrganizationResolverUtils;
import edu.duke.cabig.c3pr.utils.ProtocolAbstractionResolverUtils;

/**
 * The Class RemoteHealthcareSiteResolverTest.
 * 
 * @author Vinay Gangoli
 */
public class RemoteStudyResolverTest extends ApplicationContextTest{
	
	RemoteStudyResolver remoteStudyResolver = null;
	CaXchangeMessageBroadcasterImpl coppaMessageBroadcaster = null;
	PersonOrganizationResolverUtils personResolverUtils = null;
	ProtocolAbstractionResolverUtils protocolAbstractionResolverUtils = null;
	
	public static final String idpUrl = "https://cagrid-dorian-stage.nci.nih.gov:8443/wsrf/services/cagrid/Dorian";
	public static final String ifsUrl = "https://cagrid-dorian-stage.nci.nih.gov:8443/wsrf/services/cagrid/Dorian";
	public static final String caXchangeUrl = "https://ncias-c278-v.nci.nih.gov:58445/wsrf-caxchange/services/cagrid/CaXchangeRequestProcessor";
	
	public static final String username = "cctsdev1";
	public static final String password = "An010101!!";
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		remoteStudyResolver = (RemoteStudyResolver) getApplicationContext().getBean("remoteStudyResolver");
		coppaMessageBroadcaster = (CaXchangeMessageBroadcasterImpl) getApplicationContext().getBean("coppaMessageBroadcaster");
		personResolverUtils = (PersonOrganizationResolverUtils) getApplicationContext().getBean("personOrganizationResolverUtils");
		protocolAbstractionResolverUtils = (ProtocolAbstractionResolverUtils) getApplicationContext().getBean("protocolAbstractionResolverUtils");
		
		TestMultisiteDelegatedCredentialProvider testMultisiteDelegatedCredentialProvider = new TestMultisiteDelegatedCredentialProvider(username, password);
		testMultisiteDelegatedCredentialProvider.setIdpUrl(idpUrl);
		testMultisiteDelegatedCredentialProvider.setIfsUrl(ifsUrl);
		
		coppaMessageBroadcaster.setDelegatedCredentialProvider(testMultisiteDelegatedCredentialProvider);
		coppaMessageBroadcaster.setCaXchangeURL(caXchangeUrl);
	}
	
	/**
	 * Gets the sample remote study.
	 * 
	 * @return the sample remote study
	 */
	private RemoteStudy getSampleRemoteStudy() {
		RemoteStudy remoteStudy = new RemoteStudy();
		OrganizationAssignedIdentifier identifier = new OrganizationAssignedIdentifier();
		identifier.setValue("NCI-2009-00008");
    	//Set the short-title/identifier/status in the example object as we support searches based on these 3 only.
		remoteStudy.setShortTitleText("");
		//NOTE: we dont support searches on long title in our UI but coppa does.
		remoteStudy.setLongTitleText("");
		remoteStudy.getIdentifiers().add(identifier);
		remoteStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		
		return remoteStudy;
	}
	
	
	/**
	 * Test find.
	 */
	public void testFind(){
		RemoteStudy remoteStudy = getSampleRemoteStudy();
		List objList = remoteStudyResolver.find(remoteStudy);
		assertNotNull(objList);
		assertTrue(objList.size() > 0);
	}
	
	
	/**
	 * Test get remote entity by unique id.
	
	public void testGetRemoteEntityByUniqueId(){
		Object object = remoteStudyResolver.getRemoteEntityByUniqueId("3582231");
		assertNotNull(object);
		assertTrue(object instanceof RemoteHealthcareSite);
	} */
		
	
	/**
	 * Test broadcast organization search.
	 
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
	}*/

	public void setPersonResolverUtils(PersonOrganizationResolverUtils personResolverUtils) {
		this.personResolverUtils = personResolverUtils;
	}


	public void setProtocolAbstractionResolverUtils(
			ProtocolAbstractionResolverUtils protocolAbstractionResolverUtils) {
		this.protocolAbstractionResolverUtils = protocolAbstractionResolverUtils;
	}
}

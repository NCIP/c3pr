/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.AbstractTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class HealthcareSiteTest.
 */
public class HealthcareSiteTest extends AbstractTestCase{
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	/**
	 * Test set external organizations.
	 * 
	 * @throws Exception the exception
	 */
	public void testSetExternalOrganizations() throws Exception{
		
		HealthcareSite healthcareSite = new LocalHealthcareSite();
		assertEquals("Unexpected external healthcare sites",0,healthcareSite.getExternalOrganizations().size());
		
		List<HealthcareSite> externalOrganizations = new ArrayList<HealthcareSite>();
		externalOrganizations.add(new LocalHealthcareSite());
		
		healthcareSite.setExternalOrganizations(externalOrganizations);
		assertEquals("Wrong number of healthcare sites",1,healthcareSite.getExternalOrganizations().size());
	}
	
	/**
	 * Test add investigatort group.
	 * 
	 * @throws Exception the exception
	 */
	public void testAddInvestigatortGroup() throws Exception{
		HealthcareSite healthcareSite = new LocalHealthcareSite();
		assertEquals("Unexpected investigator group(s)",0,healthcareSite.getInvestigatorGroups().size());
		
		InvestigatorGroup investigatorGroup = new InvestigatorGroup();
		investigatorGroup.setName("Oncology group");
		healthcareSite.addInvestigatorGroup(investigatorGroup);
		
		assertEquals("Should have found 1 investigator Group",1,healthcareSite.getInvestigatorGroups().size());
		assertEquals("Wrong investigator Group","Oncology group",healthcareSite.getInvestigatorGroups().get(0).getName());
		
	}
	
	/**
	 * Test remove healthcare site investigator.
	 * 
	 * @throws Exception the exception
	 */
	public void testRemoveHealthcareSiteInvestigator() throws Exception{
		HealthcareSite healthcareSite = new LocalHealthcareSite();
		assertEquals("Unexpected healthcareSite investigator(s)",0,healthcareSite.getHealthcareSiteInvestigators().size());
		
		HealthcareSiteInvestigator hcsInv = new HealthcareSiteInvestigator();
		healthcareSite.addHealthcareSiteInvestigator(hcsInv);
		
		assertEquals("Should have found 1 healthcareSite Investigator",1,healthcareSite.getHealthcareSiteInvestigators().size());
		
		healthcareSite.removeHealthcareSiteInvestigator(hcsInv);
		assertEquals("Unexpected heathcareSite Investigator",0,healthcareSite.getHealthcareSiteInvestigators().size());
		
	}
	
	/**
	 * Test add research staff.
	 * 
	 * @throws Exception the exception
	 */
	public void testAddResearchStaff() throws Exception{
		HealthcareSite healthcareSite = new LocalHealthcareSite();
		assertEquals("Unexpected research staff",0,healthcareSite.getPersonUsers().size());
		
		PersonUser rs = new LocalPersonUser();
		rs.setLastName("Jeff Sonas");
		healthcareSite.addPersonUser(rs);
		
		assertEquals("Should have found 1 research staff",1,healthcareSite.getPersonUsers().size());
	}
	
	/**
	 * Test remove research staff.
	 * 
	 * @throws Exception the exception
	 */
	public void testRemoveResearchStaff() throws Exception{
		HealthcareSite healthcareSite = new LocalHealthcareSite();
		PersonUser rs = new LocalPersonUser();
		rs.setLastName("Jeff Sonas");
		healthcareSite.addPersonUser(rs);
		
		assertEquals("Should have found 1 research staff",1,healthcareSite.getPersonUsers().size());
		healthcareSite.removePersonUser(rs);
		assertEquals("Unexpected research staff",0,healthcareSite.getPersonUsers().size());
	}
	
	/**
	 * Test compare to.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo() throws Exception{
		HealthcareSite healthcareSite1 = new LocalHealthcareSite();
		HealthcareSite healthcareSite2 = new LocalHealthcareSite();
		assertEquals("The two healthcareSites should be same",0,healthcareSite1.compareTo(healthcareSite2));
		
		healthcareSite1.setCtepCode("NCI_ORG1");
		healthcareSite1.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
		assertEquals("The two healthcareSites should be different",1,healthcareSite1.compareTo(healthcareSite2));
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		HealthcareSite healthcareSite1 = new LocalHealthcareSite();
		assertEquals("Wrong hash code",31*(31),healthcareSite1.hashCode());
		String nciCode = "NCI_ORG1";
		healthcareSite1.setCtepCode(nciCode);
		healthcareSite1.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
		assertEquals("Wrong hash code",31*(31) + nciCode.hashCode(),healthcareSite1.hashCode());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		HealthcareSite healthcareSite1 = new LocalHealthcareSite();
		HealthcareSite healthcareSite2 = new RemoteHealthcareSite();
		assertFalse("The two healthcareSites cannot be equal",healthcareSite1.equals(healthcareSite2));
		HealthcareSite healthcareSite3 = new LocalHealthcareSite();
		assertTrue("The two healthcareSites should be equal",healthcareSite1.equals(healthcareSite3));
	}
	
	/**
	 * Test equals2.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals2() throws Exception{
		HealthcareSite healthcareSite1 = new LocalHealthcareSite();
		HealthcareSite healthcareSite2 = new LocalHealthcareSite();
		
		healthcareSite2.setCtepCode("NCI_ORG2");
		healthcareSite2.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
		assertFalse("The two healthcareSites cannot be equal",healthcareSite1.equals(healthcareSite2));
		healthcareSite1.setCtepCode("NCI_ORG1");
		healthcareSite1.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
		assertFalse("The two healthcareSites should be equal",healthcareSite1.equals(healthcareSite2));
	}
	
	/**
	 * Test add external organization.
	 * 
	 * @throws Exception the exception
	 */
	public void testAddExternalOrganization() throws Exception{
		
		HealthcareSite healthcareSite = new LocalHealthcareSite();
		assertEquals("Unexpected external organization(s)",0,healthcareSite.getExternalOrganizations().size());
		
		healthcareSite.addExternalOrganization(new RemoteHealthcareSite());
		assertEquals("Wrong number of external organizations",1,healthcareSite.getExternalOrganizations().size());
		
	}
	

}

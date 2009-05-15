package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class OrganizationAssignedIdentifierTest.
 */
public class OrganizationAssignedIdentifierTest extends AbstractTestCase{
	
	/**
	 * Test compare to1.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo1() throws Exception{
		OrganizationAssignedIdentifier orgIdentifier1 = new OrganizationAssignedIdentifier();
		HealthcareSite localHealthcareSite1 = new LocalHealthcareSite();
		localHealthcareSite1.setNciInstituteCode("NCI_ORG1");
		orgIdentifier1.setHealthcareSite(localHealthcareSite1);
		
		OrganizationAssignedIdentifier orgIdentifier2 = new OrganizationAssignedIdentifier();
		assertEquals("The two identifiers cannot be same",1,orgIdentifier1.compareTo(orgIdentifier2));
	}
	
	/**
	 * Test compare to2.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo2() throws Exception{
		OrganizationAssignedIdentifier orgIdentifier1 = new OrganizationAssignedIdentifier();
		HealthcareSite localHealthcareSite1 = new LocalHealthcareSite();
		localHealthcareSite1.setNciInstituteCode("NCI_ORG1");
		orgIdentifier1.setHealthcareSite(localHealthcareSite1);
		
		OrganizationAssignedIdentifier orgIdentifier2 = new OrganizationAssignedIdentifier();
		orgIdentifier2.setHealthcareSite(localHealthcareSite1);
		assertEquals("The two identifiers should be same",0,orgIdentifier1.compareTo(orgIdentifier2));
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		
		Identifier organizationAssignedIdenifier = new OrganizationAssignedIdentifier();
		organizationAssignedIdenifier.setType("MRN");
		HealthcareSite healthcareSite = new LocalHealthcareSite();
		((OrganizationAssignedIdentifier) organizationAssignedIdenifier).setHealthcareSite(healthcareSite);
		assertEquals("Wrong value of hash code",31*(31+"MRN".hashCode())+healthcareSite.hashCode(),organizationAssignedIdenifier.hashCode());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		Identifier orgIdentifier1 = new OrganizationAssignedIdentifier();
		Identifier orgIdentifier2 = new OrganizationAssignedIdentifier();
		
		assertFalse("The 2 objects cannot be equal",orgIdentifier1.equals(null));
		assertTrue("The 2 identifiers have to be equal",orgIdentifier1.equals(orgIdentifier1));
		assertTrue("The 2 identifiers have to be equal",orgIdentifier1.equals(orgIdentifier2));
	}
	
	/**
	 * Test equals2.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals2() throws Exception{
		Identifier orgIdentifier1 = new OrganizationAssignedIdentifier();
		Identifier orgIdentifier2 = new OrganizationAssignedIdentifier();
		orgIdentifier2.setType("MRN");
		
		assertFalse("The 2 identifiers cannot be equal",orgIdentifier1.equals(orgIdentifier2));
		
		orgIdentifier1.setType("Cooperative Group Identifier");
		
		assertFalse("The 2 identifiers cannot be equal",orgIdentifier1.equals(orgIdentifier2));
		
		orgIdentifier1.setType("MRN");
		assertTrue("The 2 identifiers have to be equal",orgIdentifier1.equals(orgIdentifier2));
	}
}


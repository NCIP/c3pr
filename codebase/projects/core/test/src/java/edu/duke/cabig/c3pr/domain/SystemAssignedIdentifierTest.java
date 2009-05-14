package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class SystemAssignedIdentifierTest.
 */
public class SystemAssignedIdentifierTest extends AbstractTestCase{
	
	/**
	 * Test compare to.
	 */
	public void testCompareTo() {
		
		SystemAssignedIdentifier identifier1 = new SystemAssignedIdentifier();
		identifier1.setSystemName("SystemA");
		
		SystemAssignedIdentifier identifier2 = new SystemAssignedIdentifier();
		identifier2.setSystemName("SystemB");
		
		SystemAssignedIdentifier identifier3 = new SystemAssignedIdentifier();
		identifier3.setSystemName("SystemA");
		
		assertEquals("These 2 identifiers cannot be same",1, identifier1.compareTo(identifier2));
		assertEquals("These 2 identifier are same",0, identifier1.compareTo(identifier3));
	}
	
	/**
	 * Test equals.
	 */
	public void testEquals() {
	        
		SystemAssignedIdentifier identifier1 = new SystemAssignedIdentifier();
		identifier1.setSystemName("SystemA");
		
		SystemAssignedIdentifier identifier2 = identifier1 ;
		assertTrue("These two identifiers are equal",identifier1.equals(identifier2));
		
		OrganizationAssignedIdentifier identifier3 = new OrganizationAssignedIdentifier(); 
		assertFalse("These two identifiers are  not equal",identifier1.equals(identifier3));
		
		SystemAssignedIdentifier identifier4 = new SystemAssignedIdentifier();
		assertFalse("These two identifiers cannot be equal",identifier4.equals(identifier1));
		
		SystemAssignedIdentifier identifier5 = new SystemAssignedIdentifier();
		identifier5.setSystemName("SystemA");
		assertTrue("These two identifiers are equal",identifier1.equals(identifier5));
		
		SystemAssignedIdentifier identifier6 = new SystemAssignedIdentifier();
		identifier6.setSystemName("SystemB");
		assertFalse("These two identifiers cannot be equal",identifier1.equals(identifier6));
	}
}

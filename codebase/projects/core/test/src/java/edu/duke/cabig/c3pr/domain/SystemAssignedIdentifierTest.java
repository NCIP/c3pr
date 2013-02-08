/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
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
		
		identifier1.setValue("val1");
		identifier3.setValue("val1");
		identifier2.setValue("val2");
		
		assertEquals("These 2 identifiers cannot be same",1, identifier1.compareTo(identifier2));
		assertEquals("These 2 identifier are same",0, identifier1.compareTo(identifier3));
	}
	
	/**
	 * Test equals.
	 */
	public void testEquals() {
		SystemAssignedIdentifier identifier1 = new SystemAssignedIdentifier();
		identifier1.setSystemName("SystemA");
		identifier1.setValue("val1");
		
		SystemAssignedIdentifier identifier2 = identifier1 ;
		identifier2.setValue("val2");
		assertTrue("These two identifiers are equal",identifier1.equals(identifier2));
		
		OrganizationAssignedIdentifier identifier3 = new OrganizationAssignedIdentifier();
		identifier3.setValue("val3");
		assertFalse("These two identifiers are  not equal",identifier1.equals(identifier3));
		
		SystemAssignedIdentifier identifier4 = new SystemAssignedIdentifier();
		identifier4.setValue("val4");
		assertFalse("These two identifiers cannot be equal",identifier4.equals(identifier1));
		
		SystemAssignedIdentifier identifier5 = new SystemAssignedIdentifier();
		identifier5.setValue("val1");
		identifier1.setValue("val1");
		identifier5.setSystemName("SystemA");
		assertTrue("These two identifiers are equal",identifier1.equals(identifier5));
		
		SystemAssignedIdentifier identifier6 = new SystemAssignedIdentifier();
		identifier6.setSystemName("SystemB");
		identifier6.setValue("val6");
		assertFalse("These two identifiers cannot be equal",identifier1.equals(identifier6));
		
	}
}

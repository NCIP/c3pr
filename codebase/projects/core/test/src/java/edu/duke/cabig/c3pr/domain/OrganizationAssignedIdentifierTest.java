/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;

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
		localHealthcareSite1.setCtepCode("NCI_ORG1");
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
		localHealthcareSite1.setCtepCode("NCI_ORG1");
		orgIdentifier1.setHealthcareSite(localHealthcareSite1);
		
		OrganizationAssignedIdentifier orgIdentifier2 = new OrganizationAssignedIdentifier();
		orgIdentifier2.setHealthcareSite(localHealthcareSite1);
		
		orgIdentifier1.setValue("val1");
		orgIdentifier2.setValue("val1");
		
		
		assertEquals("The two identifiers should be same",0,orgIdentifier1.compareTo(orgIdentifier2));
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		
		OrganizationAssignedIdentifier organizationAssignedIdenifier = new OrganizationAssignedIdentifier();
		organizationAssignedIdenifier.setType(OrganizationIdentifierTypeEnum.MRN);
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
		
		orgIdentifier1.setValue("val1");
		orgIdentifier2.setValue("val1");
		
		
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
		OrganizationAssignedIdentifier orgIdentifier1 = new OrganizationAssignedIdentifier();
		OrganizationAssignedIdentifier orgIdentifier2 = new OrganizationAssignedIdentifier();
		orgIdentifier2.setType(OrganizationIdentifierTypeEnum.MRN);
		
		orgIdentifier1.setValue("val1");
		orgIdentifier2.setValue("val1");
		
		
		assertFalse("The 2 identifiers cannot be equal",orgIdentifier1.equals(orgIdentifier2));
		
		orgIdentifier1.setType(OrganizationIdentifierTypeEnum.COOPERATIVE_GROUP_IDENTIFIER);
		
		assertFalse("The 2 identifiers cannot be equal",orgIdentifier1.equals(orgIdentifier2));
		
		orgIdentifier1.setType(OrganizationIdentifierTypeEnum.MRN);
		assertTrue("The 2 identifiers have to be equal",orgIdentifier1.equals(orgIdentifier2));
	}
}


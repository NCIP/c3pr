package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class IdentifierTest.
 */
public class IdentifierTest extends AbstractTestCase{
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test is primary.
	 * 
	 * @throws Exception the exception
	 */
	public void testIsPrimary() throws Exception{
		Identifier organizationAssignedIdenifier = new OrganizationAssignedIdentifier();
		assertFalse("Should not have been primary identifier",organizationAssignedIdenifier.isPrimary());
		organizationAssignedIdenifier.setPrimaryIndicator(true);
		assertTrue("Should have been primary identifier",organizationAssignedIdenifier.isPrimary());
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		
		OrganizationAssignedIdentifier organizationAssignedIdenifier = new OrganizationAssignedIdentifier();
		organizationAssignedIdenifier.setType(OrganizationIdentifierTypeEnum.MRN);
		assertEquals("Wrong value of hash code",31*(31+"MRN".hashCode()),organizationAssignedIdenifier.hashCode());
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
		OrganizationAssignedIdentifier orgIdentifier1 = new OrganizationAssignedIdentifier();
		OrganizationAssignedIdentifier orgIdentifier2 = new OrganizationAssignedIdentifier();
		orgIdentifier2.setType(OrganizationIdentifierTypeEnum.MRN);
		
		assertFalse("The 2 identifiers cannot be equal",orgIdentifier1.equals(orgIdentifier2));
		
		orgIdentifier1.setType(OrganizationIdentifierTypeEnum.COOPERATIVE_GROUP_IDENTIFIER);
		
		assertFalse("The 2 identifiers cannot be equal",orgIdentifier1.equals(orgIdentifier2));
		
		orgIdentifier1.setType(OrganizationIdentifierTypeEnum.MRN);
		assertTrue("The 2 identifiers have to be equal",orgIdentifier1.equals(orgIdentifier2));
	}


}

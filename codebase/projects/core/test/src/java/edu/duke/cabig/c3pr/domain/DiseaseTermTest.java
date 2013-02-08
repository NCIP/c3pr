/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

public class DiseaseTermTest extends AbstractTestCase{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testHashCode() throws Exception{
		
		DiseaseTerm diseaseTerm1 = new DiseaseTerm();
		assertEquals("Wrong hash code",31,diseaseTerm1.hashCode());
		
		diseaseTerm1.setCtepTerm("ctep_term1");
		assertEquals("Wrong hash code",31 +"ctep_term1".hashCode(),diseaseTerm1.hashCode());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		
		DiseaseTerm diseaseTerm1 = new DiseaseTerm();
		DiseaseTerm diseaseTerm2 = new DiseaseTerm();
		
		assertTrue("The two study diseases terms should have been equal",diseaseTerm1.equals(diseaseTerm2));
	}
	
	public void testEquals2() throws Exception{
		
		DiseaseTerm diseaseTerm1 = new DiseaseTerm();
		DiseaseTerm diseaseTerm2 = new DiseaseTerm();
		
		diseaseTerm2.setCtepTerm("CTEP_TERM1");
		
		assertFalse("The two disease terms should not have been equal",diseaseTerm1.equals(diseaseTerm2));
	}
	
	/**
	 * Test equals3.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals3() throws Exception{
		
		DiseaseTerm diseaseTerm1 = new DiseaseTerm();
		DiseaseTerm diseaseTerm2 = new DiseaseTerm();
		diseaseTerm1.setCtepTerm("CTEP_TERM1");
		diseaseTerm2.setCtepTerm("CTEP_TERM1");
		
		assertTrue("The two disease terms should be equal",diseaseTerm1.equals(diseaseTerm2));
	}
}

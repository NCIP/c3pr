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
 * The Class StudyInvestigatorTest.
 */
public class StudyInvestigatorTest extends AbstractTestCase{
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	/**
	 * Test compare to.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo() throws Exception{
		StudyInvestigator studyInvestigator1 = new StudyInvestigator();
		StudyInvestigator studyInvestigator2 = new StudyInvestigator();
		assertEquals("The two study investigators should be same",0,studyInvestigator1.compareTo(studyInvestigator2));
		
		studyInvestigator1.setRoleCode("PI");
		studyInvestigator2.setRoleCode("Site Investigator");
		assertEquals("The two study investigators should be different",1,studyInvestigator1.compareTo(studyInvestigator2));
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		int prime = 31;
		StudyInvestigator studyInvestigator1 = new StudyInvestigator();
		assertEquals("Wrong hash code",prime*prime*prime,studyInvestigator1.hashCode());
		HealthcareSiteInvestigator hcsInv = new HealthcareSiteInvestigator();
		String roleCode = "Site Investigator";
		
		studyInvestigator1.setHealthcareSiteInvestigator(hcsInv);
		studyInvestigator1.setRoleCode(roleCode);
		
		assertEquals("Wrong hash code",(((prime+hcsInv.hashCode())*prime)+roleCode.hashCode())*prime,studyInvestigator1.hashCode());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		StudyInvestigator studyInvestigator1 = new StudyInvestigator();
		StudyInvestigator studyInvestigator2 = new StudyInvestigator();
		assertTrue("The two study investigators should be equal",studyInvestigator1.equals(studyInvestigator2));
	}
	
	/**
	 * Test equals2.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals2() throws Exception{
		StudyInvestigator studyInvestigator1 = new StudyInvestigator();
		StudyInvestigator studyInvestigator2 = new StudyInvestigator();
		studyInvestigator2.setRoleCode("PI");
		
		assertFalse("The two study investigators cannot be equal",studyInvestigator1.equals(studyInvestigator2));
		studyInvestigator1.setRoleCode("PI");
		assertTrue("The two study investigators should be equal",studyInvestigator1.equals(studyInvestigator2));
	}
	
	/**
	 * Test equals3.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals3() throws Exception{
		StudyInvestigator studyInvestigator1 = new StudyInvestigator();
		StudyInvestigator studyInvestigator2 = new StudyInvestigator();
		HealthcareSiteInvestigator hcsInv = new HealthcareSiteInvestigator();
		studyInvestigator1.setHealthcareSiteInvestigator(hcsInv);
		studyInvestigator2.setHealthcareSiteInvestigator(hcsInv);
		StudySite studySite = new StudySite();
		studyInvestigator1.setStudyOrganization(studySite);
		studyInvestigator2.setStudyOrganization(studySite);
		
		assertTrue("The two study investigators should be equal",studyInvestigator1.equals(studyInvestigator2));
	}
	
}

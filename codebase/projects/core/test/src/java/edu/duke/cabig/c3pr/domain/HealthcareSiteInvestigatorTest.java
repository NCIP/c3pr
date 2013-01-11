/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.ContactMechanismType;

// TODO: Auto-generated Javadoc
/**
 * The Class HealthcareSiteInvestigatorTest.
 */
public class HealthcareSiteInvestigatorTest extends AbstractTestCase {
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test remove study investigator.
	 * 
	 * @throws Exception the exception
	 */
	public void testRemoveStudyInvestigator() throws Exception{
		 HealthcareSiteInvestigator hcsInv= new HealthcareSiteInvestigator();
		 assertEquals("Unexpected study investigator(s)",0,hcsInv.getStudyInvestigators().size());
		 
		 StudyInvestigator studyInvgetigator = new StudyInvestigator();
		 hcsInv.addStudyInvestigator(studyInvgetigator);
		 assertEquals("Wrong number of study investigators",1,hcsInv.getStudyInvestigators().size());
		 
		 hcsInv.removeStudyInvestigator(studyInvgetigator);
		 assertEquals("Unexpected study investigator(s)",0,hcsInv.getStudyInvestigators().size());
	}
	
	/**
	 * Test to string.
	 * 
	 * @throws Exception the exception
	 */
	public void testToString() throws Exception{
		 HealthcareSiteInvestigator hcsInv= new HealthcareSiteInvestigator();
		 LocalInvestigator localInv = new LocalInvestigator();
		 localInv.setFirstName("Justin");
		 localInv.setLastName("Langer");
		 
		 hcsInv.setInvestigator(localInv);
		 
		 assertEquals("Unexpected study investigator(s)","Justin Langer",hcsInv.toString());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		 HealthcareSiteInvestigator hcsInv1= new HealthcareSiteInvestigator();
		 assertFalse("The 2 objects cannot be equal",hcsInv1.equals(null));
		 assertTrue("Wrong implementation of equals",hcsInv1.equals(hcsInv1));
		 assertFalse("Wrong implementation of equals",hcsInv1.equals(new LocalInvestigator()));
	}
	
	/**
	 * Test equals2.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals2() throws Exception{
		 HealthcareSiteInvestigator hcsInv1= new HealthcareSiteInvestigator();
		 
		 HealthcareSiteInvestigator hcsInv2= new HealthcareSiteInvestigator();
		 hcsInv2.setHealthcareSite(new LocalHealthcareSite());
		 
		 assertFalse("Wrong implementation of equals",hcsInv1.equals(hcsInv2));
	}
	
	/**
	 * Test equals3.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals3() throws Exception{
		 HealthcareSiteInvestigator hcsInv1= new HealthcareSiteInvestigator();
		 LocalHealthcareSite healthcareSite1 = new LocalHealthcareSite();
		 healthcareSite1.setCtepCode("ABC");
		 healthcareSite1.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
		 hcsInv1.setHealthcareSite(healthcareSite1);
		 
		 HealthcareSiteInvestigator hcsInv2= new HealthcareSiteInvestigator();
		 LocalHealthcareSite healthcareSite2 = new LocalHealthcareSite();
		 healthcareSite2.setCtepCode("DEF");
		 healthcareSite2.getOrganizationAssignedIdentifiers().get(0).setPrimaryIndicator(true);
		 hcsInv2.setHealthcareSite(healthcareSite2);
		 
		 assertFalse("The two healthcareSite investigators cannot be equal ",hcsInv1.equals(hcsInv2));
	}
	
	/**
	 * Test equals4.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals4() throws Exception{
		 HealthcareSiteInvestigator hcsInv1= new HealthcareSiteInvestigator();
		 
		 HealthcareSiteInvestigator hcsInv2= new HealthcareSiteInvestigator();
		 hcsInv2.setInvestigator(new LocalInvestigator());
		 
		 assertFalse("Wrong implementation of equals",hcsInv1.equals(hcsInv2));
	}
	
	/**
	 * Test equals5.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals5() throws Exception{
		 HealthcareSiteInvestigator hcsInv1= new HealthcareSiteInvestigator();
		 LocalInvestigator investigator1 = new LocalInvestigator();
		 investigator1.setAssignedIdentifier("assignedId1");
			
		 hcsInv1.setInvestigator(investigator1);
		 
		 HealthcareSiteInvestigator hcsInv2= new HealthcareSiteInvestigator();
		 LocalInvestigator investigator2 = new LocalInvestigator();
		 investigator2.setAssignedIdentifier("assignedId2");
		 hcsInv2.setInvestigator(investigator2);
		 
		 assertFalse("The two healthcareSite investigators cannot be equal ",hcsInv1.equals(hcsInv2));
	}
	
	/**
	 * Test equals6.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals6() throws Exception{
		 HealthcareSiteInvestigator hcsInv1= new HealthcareSiteInvestigator();
		 LocalInvestigator investigator = new LocalInvestigator();
		 hcsInv1.setInvestigator(investigator);
		 LocalHealthcareSite healthcareSite = new LocalHealthcareSite();
		 hcsInv1.setHealthcareSite(healthcareSite);
		 
		 HealthcareSiteInvestigator hcsInv2= new HealthcareSiteInvestigator();
		 hcsInv2.setInvestigator(investigator);
		 hcsInv2.setHealthcareSite(healthcareSite);
		 
		 assertTrue("The two healthcareSite investigators should be equal ",hcsInv1.equals(hcsInv2));
	}

}

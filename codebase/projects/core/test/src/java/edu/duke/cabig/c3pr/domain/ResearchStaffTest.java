/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.ContactMechanismType;

// TODO: Auto-generated Javadoc
/**
 * The Class ResearchStaffTest.
 */
public class ResearchStaffTest extends AbstractTestCase{
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	/**
	 * Test set external ResearchStaff.
	 * 
	 * @throws Exception the exception
	 */
	public void testSetExternalResearchStaffs() throws Exception{
		
		PersonUser personUser = new LocalPersonUser();
		assertEquals("Unexpected external research staff",0,personUser.getExternalResearchStaff().size());
		
		List<PersonUser> externalResearchStaffs = new ArrayList<PersonUser>();
		externalResearchStaffs.add(new LocalPersonUser());
		
		personUser.setExternalResearchStaff(externalResearchStaffs);
		assertEquals("Wrong number of research staff",1,personUser.getExternalResearchStaff().size());
	}
	
	/**
	 * Test getLastFirst.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetLastFirst() throws Exception{
		PersonUser researchStaff = new LocalPersonUser();
		researchStaff.setFirstName("first");
		researchStaff.setLastName("last");
		
		assertEquals("ResearchStaff name is incorrect or retrieved in wrong order ","last, first",researchStaff.getLastFirst());
	}
	
	/**
	 * Test compare to.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo() throws Exception{
		PersonUser researchStaff1 = new LocalPersonUser();
		PersonUser researchStaff2 = new LocalPersonUser();
		assertEquals("The two research staff personnel should be same",0,researchStaff1.compareTo(researchStaff2));
		
		researchStaff1.setAssignedIdentifier("testAssignedId");
		researchStaff1.setAssignedIdentifier("seperateAssignedId");
		assertEquals("The two research staff personnel should be different",1,researchStaff1.compareTo(researchStaff2));
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		PersonUser researchStaff1 = new LocalPersonUser();
		assertEquals("Wrong hash code",31,researchStaff1.hashCode());
		String assignedIdentifier = "12298374983";
		researchStaff1.setAssignedIdentifier(assignedIdentifier);
		assertEquals("Wrong hash code",31 + assignedIdentifier.hashCode(),researchStaff1.hashCode());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		PersonUser researchStaff1 = new LocalPersonUser();
		PersonUser researchStaff2 = new RemotePersonUser();
		assertFalse("The two research staff cannot be equal",researchStaff1.equals(researchStaff2));
		PersonUser researchStaff3 = new LocalPersonUser();
		assertTrue("The two research staff should be equal",researchStaff1.equals(researchStaff3));
	}
	
	/**
	 * Test equals2.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals2() throws Exception{
		PersonUser researchStaff1 = new LocalPersonUser();
		PersonUser researchStaff2 = new LocalPersonUser();
		
		researchStaff1.setAssignedIdentifier("testAssignedId");
		
		assertFalse("The two ResearchStaffs cannot be equal",researchStaff1.equals(researchStaff2));
		
		researchStaff2.setAssignedIdentifier("testAssignedId");
		assertTrue("The two ResearchStaffs should be equal",researchStaff1.equals(researchStaff2));
	}
	
	/**
	 * Test add external ResearchStaff.
	 * 
	 * @throws Exception the exception
	 */
	public void testAddExternalResearchStaff() throws Exception{
		
		PersonUser researchStaff = new LocalPersonUser();
		assertEquals("Unexpected external research staff",0,researchStaff.getExternalResearchStaff().size());
		
		researchStaff.addExternalResearchStaff(new RemotePersonUser());
		assertEquals("Wrong number of external research staff",1,researchStaff.getExternalResearchStaff().size());
		
	}
	
	/**
	 * Test add study personnel.
	 * 
	 * @throws Exception the exception
	 */
	public void testAddStudyPersonnel() throws Exception{
		
		PersonUser researchStaff = new LocalPersonUser();
		StudyPersonnel studyPersonnel = new StudyPersonnel();
		studyPersonnel.setRoleCode("Study Coordinator");
		assertEquals("Unexpected study personnel",0,researchStaff.getStudyPersonnels().size());
		
		researchStaff.addStudyPersonnel(studyPersonnel);
		assertEquals("Wrong number of study personnel",1,researchStaff.getStudyPersonnels().size());
		assertEquals("Wrong study personnel",1,researchStaff.getStudyPersonnels().size());
		
	}
	
	

}

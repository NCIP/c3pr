package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.AbstractTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class StudyPersonnelTest.
 */
public class StudyPersonnelTest extends AbstractTestCase{
	
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
		StudyPersonnel studyPersonnel1 = new StudyPersonnel();
		StudyPersonnel studyPersonnel2 = new StudyPersonnel();
		assertEquals("The two study personnel should be same",0,studyPersonnel1.compareTo(studyPersonnel2));
		
		studyPersonnel1.setRoleCode("C3PR Admin");
		studyPersonnel2.setRoleCode("Study Coordinator");
		assertEquals("The two research staff personnel should be different",1,studyPersonnel1.compareTo(studyPersonnel2));
	}
	
	/**
	 * Test hash code.
	 * 
	 * @throws Exception the exception
	 */
	public void testHashCode() throws Exception{
		int prime = 31;
		StudyPersonnel studyPersonnel1 = new StudyPersonnel();
		assertEquals("Wrong hash code",prime*prime*prime,studyPersonnel1.hashCode());
		PersonUser localResearchStaff = new LocalPersonUser();
		String roleCode = "Site Coordinator";
		
		studyPersonnel1.setPersonUser(localResearchStaff);
		studyPersonnel1.setRoleCode(roleCode);
		
		assertEquals("Wrong hash code",(((prime+localResearchStaff.hashCode())*prime)+roleCode.hashCode())*prime,studyPersonnel1.hashCode());
	}
	
	/**
	 * Test equals1.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		StudyPersonnel StudyPersonnel1 = new StudyPersonnel();
		StudyPersonnel StudyPersonnel2 = new StudyPersonnel();
		assertTrue("The two personnel should be equal",StudyPersonnel1.equals(StudyPersonnel2));
	}
	
	/**
	 * Test equals2.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals2() throws Exception{
		StudyPersonnel studyPersonnel1 = new StudyPersonnel();
		StudyPersonnel studyPersonnel2 = new StudyPersonnel();
		studyPersonnel2.setRoleCode("Admin");
		
		assertFalse("The two study personnel cannot be equal",studyPersonnel1.equals(studyPersonnel2));
		studyPersonnel1.setRoleCode("Admin");
		assertTrue("The two study personnel should be equal",studyPersonnel1.equals(studyPersonnel2));
	}
	
	/**
	 * Test equals3.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals3() throws Exception{
		StudyPersonnel studyPersonnel1 = new StudyPersonnel();
		StudyPersonnel studyPersonnel2 = new StudyPersonnel();
		PersonUser localResearchStaff = new LocalPersonUser();
		studyPersonnel1.setPersonUser(localResearchStaff);
		studyPersonnel2.setPersonUser(localResearchStaff);
		
		assertTrue("The two study personnel should be equal",studyPersonnel1.equals(studyPersonnel2));
	}
	
}
